package zxspectrum.emul.io.sound;

import zxspectrum.emul.ClockExecutor;
import zxspectrum.emul.PortIOSetter;
import zxspectrum.emul.Resettable;

/**
 * SoundChip.
 *
 * @author Maxim Gorin
 */
public interface SoundChip extends Resettable, PortIOSetter, ClockExecutor {

}
