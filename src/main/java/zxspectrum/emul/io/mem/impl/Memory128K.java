package zxspectrum.emul.io.mem.impl;

import java.util.Arrays;

/**
 * Memory128K.
 *
 * @author Maxim Gorin
 */
public class Memory128K extends MemoryPaged {

  protected static final int SELECTED_PAGE = 0x07;

  protected static final int SHADOW_SCREEN = 0x08;

  protected static final int SELECTED_ROM = 0x10;

  protected static final int DISABLED_PAGE_SELECTING = 0x20;

  protected byte[][] pages;

  protected byte[][] rom;

  protected boolean disabledPageSelecting;

  protected boolean selectedShadowScreen;

  public Memory128K() {
    rom = new byte[0x02][romSize];
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
    value &= 0x3F;
    int pageIndex = value & SELECTED_PAGE;
    selectedShadowScreen = (value & SHADOW_SCREEN) != 0x00;
    int selectedRom = (value & SELECTED_ROM) == 0x00 ? 0x00 : 0x01;
    disabledPageSelecting = (value & DISABLED_PAGE_SELECTING) != 0x00;
    buf[0x00] = rom[selectedRom];
    buf[0x0] = pages[pageIndex];
  }

  protected void defaultPageMapping() {
    buf[0x00] = rom[0x00];
    buf[0x01] = pages[0x05];//screen
    buf[0x02] = pages[0x02];
    buf[0x03] = pages[0x00];
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
  public byte[] flushScreen() {
    return selectedShadowScreen ? Arrays.copyOfRange(pages[0x07], 0x0000, 0x1FFF) :
        Arrays.copyOfRange(pages[0x05], 0x0000, 0x1FFF);
  }
}
