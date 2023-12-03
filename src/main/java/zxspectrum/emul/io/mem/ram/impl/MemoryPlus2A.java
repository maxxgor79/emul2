package zxspectrum.emul.io.mem.ram.impl;

/**
 * MemoryPlus2A.
 *
 * @author Maxim Gorin
 */
public class MemoryPlus2A extends MemoryPlus2 {

    protected static final int SELECTED_ROM_LOW = SELECTED_ROM;

    protected static final int SPECIAL_PAGING_MODE = 1;

    protected static final int SPECIAL_ATTRIBUTE = 2;

    protected static final int SPECIAL_SELECTED_ROM_HI = 4;

    public MemoryPlus2A() {
        rom = new byte[4][ROM_SIZE];
        pages = new byte[8][PAGE_SIZE];
        buf = new byte[4][];//64Kb
        lastAddress = 0xFFFF;
        lastPageIndex = 0x03;
        pagedAddressShift = 0x10 - lastPageIndex;
        lastPageAddress = 0x3FFF;
        defaultPageMapping();
    }

    @Override
    public void setPageMapping(int value) {
        if (disabledPageSelecting) {
            return;
        }
        value &= 0x3F;
        int special = (value >>> 0x10) & 0x3F;
        selectedShadowScreen = (value & SHADOW_SCREEN) != 0;
        disabledPageSelecting = (value & DISABLED_PAGE_SELECTING) != 0;
        boolean specialMode = (special & SPECIAL_PAGING_MODE) != 0;

        if (specialMode) {
            int bit1 = (special & SPECIAL_ATTRIBUTE) != 0 ? 1 : 0;
            int bit2 = (special & SPECIAL_SELECTED_ROM_HI) != 0 ? 2 : 0;
            if (bit1 == 0 && bit2 == 0) {
                buf[0] = pages[0];
                buf[1] = pages[1];
                buf[2] = pages[2];
                buf[3] = pages[3];
            } else if (bit1 == 1 && bit2 == 0) {
                buf[0] = pages[4];
                buf[1] = pages[5];
                buf[2] = pages[6];
                buf[3] = pages[7];
            } else if (bit1 == 0 && bit2 == 1) {
                buf[0] = pages[4];
                buf[1] = pages[5];
                buf[2] = pages[6];
                buf[3] = pages[3];
            } else if (bit1 == 1 && bit2 == 1) {
                buf[0] = pages[4];
                buf[1] = pages[7];
                buf[2] = pages[6];
                buf[3] = pages[3];
            }
        } else {
            selectedRom = ((special & SPECIAL_SELECTED_ROM_HI) != 0 ? 2 : 0)
                    | ((value & SELECTED_ROM_LOW) == 0 ? 0 : 1);
            int pageIndex = value & SELECTED_PAGE;
            assert rom[selectedRom] != null : "rom[index] is null";
            buf[0] = rom[selectedRom];
            buf[1] = pages[5];
            buf[2] = pages[2];
            buf[3] = pages[pageIndex];
        }
    }

    @Override
    public int getRomCount() {
        return 4;
    }
}
