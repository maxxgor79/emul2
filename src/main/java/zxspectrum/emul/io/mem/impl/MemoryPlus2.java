package zxspectrum.emul.io.mem.impl;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import zxspectrum.emul.io.mem.MemoryAccess;
import zxspectrum.emul.proc.reg.Reg16;
import zxspectrum.emul.proc.reg.Reg8;

/**
 * MemoryPlus2.
 *
 * @author Maxim Gorin
 */
public class MemoryPlus2 extends Memory128K {

  protected static final int SELECTED_ROM_LOW = SELECTED_ROM;

  protected static final int SPECIAL_PAGING_MODE = 0x01;

  protected static final int SPECIAL_ATTRIBUTE = 0x02;

  protected static final int SPECIAL_SELECTED_ROM_HI = 0x04;

  public MemoryPlus2() {
    rom = new byte[0x04][romSize];
    pages = new byte[0x08][0x8000];
    buf = new byte[0x04][];//64Kb
    defaultPageMapping();

    lastAddress = 0xFFFF;
    lastPageIndex = 0x03;
    pagedAddressShift = 0x10 - lastPageIndex;
    lastPageAddress = 0x7FFF;
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
    } else {
      int index = selectedRomHi | selectedRomLo;
      buf[0x00] = rom[index];
      buf[0x01] = pages[0x05];
      buf[0x02] = pages[0x02];
      buf[0x03] = pages[pageIndex];
    }
  }

  @Override
  public void reset() {
    super.reset();
    defaultPageMapping();
  }
}
