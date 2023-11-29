package zxspectrum.emul.chipset;

import zxspectrum.emul.ClockControl;
import zxspectrum.emul.MemorySetter;
import zxspectrum.emul.Resettable;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.port.PortIO;

/**
 * Ula.
 *
 * @author Maxim Gorin
 */
public interface Ula extends Resettable, ClockControl, MemorySetter {
  MemoryControl getMemory();
}
