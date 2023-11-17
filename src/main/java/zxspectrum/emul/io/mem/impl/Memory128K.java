package zxspectrum.emul.io.mem.impl;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Memory128K.
 *
 * @author Maxim Gorin
 */
@Slf4j
public class Memory128K extends MemoryPaged {

    protected static final int SELECTED_PAGE = 0x07;

    public static final int SHADOW_SCREEN = 0x08;

    public static final int SELECTED_ROM = 0x10;

    public static final int DISABLED_PAGE_SELECTING = 0x20;

    protected byte[][] pages;

    protected byte[][] rom;

    protected boolean disabledPageSelecting;

    protected boolean selectedShadowScreen;

    public Memory128K() {
        rom = new byte[0x02][romSize];
        pages = new byte[0x08][0x4000];
        buf = new byte[0x04][];//64Kb
        defaultPageMapping();
        lastAddress = 0xFFFF;
        lastPageIndex = 0x03;
        pagedAddressShift = 0x10 - lastPageIndex;
        lastPageAddress = 0x3FFF;
    }

    @Override
    public void setPageMapping(int value) {
        value &= 0x3F;
        if (!disabledPageSelecting) {
            selectedShadowScreen = (value & SHADOW_SCREEN) != 0x00;
            disabledPageSelecting = (value & DISABLED_PAGE_SELECTING) != 0x00;
            int pageIndex = value & SELECTED_PAGE;
            int selectedRom = (value & SELECTED_ROM) == 0x00 ? 0x00 : 0x01;
            buf[0x00] = rom[selectedRom];
            buf[0x03] = pages[pageIndex];
        }
    }

    protected void defaultPageMapping() {
        buf[0x00] = rom[0x00];
        buf[0x01] = pages[0x05];//screen
        buf[0x02] = pages[0x02];
        buf[0x03] = pages[0x00];
        romSize = 0x4000;
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
        final Buffer buffer = new Buffer();
        buffer.buf = selectedShadowScreen ? pages[0x07] : pages[0x05];
        buffer.offset = 0;
        buffer.length = 0x1B00;
        return buffer;
    }
}
