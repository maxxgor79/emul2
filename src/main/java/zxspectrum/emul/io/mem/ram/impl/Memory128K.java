package zxspectrum.emul.io.mem.ram.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import zxspectrum.emul.profile.ZxProfile;

import java.util.Arrays;

/**
 * Memory128K.
 *
 * @author Maxim Gorin
 */
@Slf4j
public class Memory128K extends MemoryPaged {
    protected static final int PAGE_SIZE = 0x4000;

    protected static final int SELECTED_PAGE = 0x07;

    protected static final int SHADOW_SCREEN = 0x08;

    protected static final int SELECTED_ROM = 0x10;

    public static final int DISABLED_PAGE_SELECTING = 0x20;

    protected byte[][] pages;

    protected boolean disabledPageSelecting;

    protected boolean selectedShadowScreen;

    protected int selectedRom;

    protected final Buffer buffer = new Buffer();

    public Memory128K() {
        pages = new byte[8][PAGE_SIZE];
        buf = new byte[4][];//64Kb
        lastAddress = 0xFFFF;
        lastPageIndex = 0x03;
        pagedAddressShift = 0x10 - lastPageIndex;
        lastPageAddress = 0x3FFF;
        romSize = ROM_SIZE;
        defaultPageMapping();
    }

    @Override
    public void setPageMapping(int value) {
        if (disabledPageSelecting) {
            return;
        }
        value &= 0x3F;
        selectedShadowScreen = (value & SHADOW_SCREEN) != 0;
        disabledPageSelecting = (value & DISABLED_PAGE_SELECTING) != 0;
        int pageIndex = value & SELECTED_PAGE;
        selectedRom = (value & SELECTED_ROM) == 0 ? 0 : 1;
        assert rom[selectedRom] != null;
        buf[0] = rom[selectedRom];
        buf[3] = pages[pageIndex];
    }

    protected void defaultPageMapping() {
        buf[0] = rom[selectedRom];//rom
        buf[1] = pages[5];//screen
        buf[2] = pages[2];
        buf[3] = pages[0];
    }

    @Override
    public void reset() {
        super.reset();
        disabledPageSelecting = false;
        selectedShadowScreen = false;
        selectedRom = 0;
        defaultPageMapping();
        for (int i = 0; i < pages.length; i++) {
            Arrays.fill(pages[i], (byte) -1);
        }
    }

    @Override
    public Buffer getVideoBuffer() {
        buffer.buf = selectedShadowScreen ? pages[7] : pages[5];
        buffer.offset = 0;
        buffer.length = VIDEO_BUFFER_SIZE;
        return buffer;
    }

    @Override
    public int getRomCount() {
        return 2;
    }

    @Override
    protected void initRom() {
        buf[0] = rom[selectedRom];
    }
}
