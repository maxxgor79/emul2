package zxspectrum.emul.io.mem.ram.impl;

import lombok.NonNull;
import zxspectrum.emul.profile.ZxProfile;

/**
 * MemoryPlus2A.
 *
 * @author Maxim Gorin
 */
public class MemoryPlus2A extends MemoryPlus2 {

    public static final int SELECTED_ROM_LOW = SELECTED_ROM;

    public static final int SPECIAL_PAGING_MODE = 0x01;

    public static final int SPECIAL_ATTRIBUTE = 0x02;

    public static final int SPECIAL_SELECTED_ROM_HI = 0x04;

    public MemoryPlus2A(@NonNull final ZxProfile profile) {
        super(profile);
        rom = new byte[0x04][romSize];
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
        if (disabledPageSelecting) {
            return;
        }
        int normal = value & 0x3F;
        int special = (value >>> 0x10) & 0x3F;

        int pageIndex = normal & SELECTED_PAGE;
        selectedShadowScreen = (normal & SHADOW_SCREEN) != 0x00;
        int selectedRomLo = (normal & SELECTED_ROM_LOW) == 0x00 ? 0x00 : 0x01;
        disabledPageSelecting = (normal & DISABLED_PAGE_SELECTING) != 0x00;

        boolean specialMode = (special & SPECIAL_PAGING_MODE) != 0x00;
        int bit1 = (special & SPECIAL_ATTRIBUTE) != 0x00 ? 0x01 : 0x00;
        int bit2;
        int selectedRomHi = bit2 = (special & SPECIAL_SELECTED_ROM_HI) != 0x00 ? 0x02 : 0x00;

        if (specialMode) {
            if (bit1 == 0x00 && bit2 == 0x00) {
                buf[0x00] = pages[0x00];
                buf[0x01] = pages[0x01];
                buf[0x02] = pages[0x02];
                buf[0x03] = pages[0x03];
            } else if (bit1 == 0x01 && bit2 == 0x00) {
                buf[0x00] = pages[0x04];
                buf[0x01] = pages[0x05];
                buf[0x02] = pages[0x06];
                buf[0x03] = pages[0x07];
            } else if (bit1 == 0x00 && bit2 == 0x01) {
                buf[0x00] = pages[0x04];
                buf[0x01] = pages[0x05];
                buf[0x02] = pages[0x06];
                buf[0x03] = pages[0x03];
            } else if (bit1 == 0x01 && bit2 == 0x01) {
                buf[0x00] = pages[0x04];
                buf[0x01] = pages[0x07];
                buf[0x02] = pages[0x06];
                buf[0x03] = pages[0x03];
            }
            romSize = 0x0000;//TODO ???
        } else {
            int index = selectedRomHi | selectedRomLo;
            assert rom[index] != null : "rom[index] is null";
            buf[0x00] = rom[index];
            buf[0x01] = pages[0x05];
            buf[0x02] = pages[0x02];
            buf[0x03] = pages[pageIndex];
            romSize = ROM_SIZE;
        }
    }

    @Override
    public void reset() {
        super.reset();
        defaultPageMapping();
    }

    @Override
    public int getRomCount() {
        return 4;
    }
}
