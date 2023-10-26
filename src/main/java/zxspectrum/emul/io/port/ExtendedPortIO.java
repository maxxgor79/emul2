package zxspectrum.emul.io.port;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.sound.SoundChip;

/**
 * ExtendedPortIO.
 *
 * @author Maxim Gorin
 */

@Getter
@Setter
abstract class ExtendedPortIO extends BasePortIO {

  @NonNull
  protected MemoryControl memory;

  @NonNull
  protected SoundChip soundChip;

}
