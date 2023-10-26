package zxspectrum.emul.chipset.impl;

import lombok.NonNull;
import zxspectrum.emul.chipset.Ula;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.port.PortIO;

/**
 * ULA5C102E.
 *
 * @author Maxim Gorin
 */
public class Ula5C102E implements Ula {

  @Override
  public void reset() {

  }

  @Override
  public void setMemory(@NonNull MemoryControl memory) {

  }

  @Override
  public MemoryControl getMemory() {
    return null;
  }

}
