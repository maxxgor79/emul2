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

    protected final Buffer buffer = new Buffer();

    public Memory128K(@NonNull final ZxProfile profile) {
        pages = new byte[0x08][PAGE_SIZE];
        buf = new byte[0x04][];//64Kb
        lastAddress = 0xFFFF;
        lastPageIndex = 0x03;
        pagedAddressShift = 0x10 - lastPageIndex;
        lastPageAddress = 0x3FFF;
        defaultPageMapping();
    }

    @Override
    public void setPageMapping(int value) {
        value &= 0x3F;
        if (!disabledPageSelecting) {
            selectedShadowScreen = (value & SHADOW_SCREEN) != 0x00;
            disabledPageSelecting = (value & DISABLED_PAGE_SELECTING) != 0x00;
            int pageIndex = value & SELECTED_PAGE;
            int selectedRom = (value & SELECTED_ROM) == 0x00 ? 0x00 : 0x01;
            assert rom[selectedRom] != null;
            buf[0x00] = rom[selectedRom];
            buf[0x03] = pages[pageIndex];
        }
    }

    protected void defaultPageMapping() {
        buf[0x00] = rom[0x00];
        buf[0x01] = pages[0x05];//screen
        buf[0x02] = pages[0x02];
        buf[0x03] = pages[0x00];
        romSize = ROM_SIZE;
    }

    @Override
    public void reset() {
        super.reset();
        disabledPageSelecting = false;
        defaultPageMapping();
        for (int i = 0; i < pages.length; i++) {
            Arrays.fill(pages[i], (byte) -1);
        }
    }

    @Override
    public Buffer getVideoBuffer() {
        buffer.buf = selectedShadowScreen ? pages[0x07] : pages[0x05];
        buffer.offset = 0;
        buffer.length = VIDEO_BUFFER_SIZE;
        return buffer;
    }

    @Override
    public void upload(@NonNull final byte[]... uploadRom) {
        super.upload(uploadRom);
        defaultPageMapping();
    }

    @Override
    public void uploadRom(int n, @NonNull final byte[] buf, int offset, int length) {
        super.uploadRom(n, buf, offset, length);
        defaultPageMapping();
    }

    @Override
    public int getRomCount() {
        return 2;
    }
}
