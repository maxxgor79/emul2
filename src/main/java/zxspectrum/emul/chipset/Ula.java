package zxspectrum.emul.chipset;

import zxspectrum.emul.Resettable;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.port.Port;

/**
 * Ula.
 *
 * @author Maxim Gorin
 */
public interface Ula extends Resettable {

  void setMemory(MemoryControl memory);

  MemoryControl getMemory();

  void setPort(Port port);

  Port getPort();
}
