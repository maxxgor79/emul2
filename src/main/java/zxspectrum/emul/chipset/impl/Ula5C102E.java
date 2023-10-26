package zxspectrum.emul.chipset.impl;

import lombok.NonNull;
import zxspectrum.emul.chipset.Ula;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.port.Port;

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

  @Override
  public void setPort(@NonNull Port port) {

  }

  @Override
  public Port getPort() {
    return null;
  }
}
