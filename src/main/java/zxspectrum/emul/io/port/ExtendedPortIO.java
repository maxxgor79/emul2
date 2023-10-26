package zxspectrum.emul.io.port;

import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.sound.SoundChip;

/**
 * ExtendedPortIO.
 *
 * @author Maxim Gorin
 */

abstract class ExtendedPortIO extends BasePortIO {

  protected MemoryControl memory;

  protected SoundChip soundChip;

}
