package zxspectrum.emul.chipset;

import zxspectrum.emul.ClockExecutor;
import zxspectrum.emul.MemorySetter;
import zxspectrum.emul.PortIOSetter;
import zxspectrum.emul.Resettable;

/**
 * Ula.
 *
 * @author Maxim Gorin
 */
public interface Ula extends Resettable, ClockExecutor, MemorySetter, PortIOSetter {
}
