package zxspectrum.emul.io.sound;

import lombok.NonNull;
import zxspectrum.emul.io.sound.impl.AY8910;
import zxspectrum.emul.io.sound.impl.AY8912;
import zxspectrum.emul.io.sound.impl.Beeper;
import zxspectrum.emul.machine.MachineModel;

/**
 * SoundChipFactory.
 *
 * @author Maxim Gorin
 */
public class SoundChipFactory {

  private static final SoundChip CHIP_AY8910 = new AY8910();

  private static final SoundChip CHIP_AY8912 = new AY8912();

  private static final SoundChip BEEPER = new Beeper();


  private SoundChipFactory() {

  }

  public static SoundChip getInstance(@NonNull SoundChipModel model) {
    return switch (model) {
      case AY_8910 -> CHIP_AY8910;
      case AY_8912 -> CHIP_AY8912;
      case Beeper -> BEEPER;
    };
  }

  public static SoundChip getInstance(@NonNull MachineModel model) {
    return switch (model) {
      case SPECTRUM16K, SPECTRUM48K -> BEEPER;
      case SPECTRUM128K -> CHIP_AY8910;
      case SPECTRUMPLUS2, SPECTRUMPLUS2A, SPECTRUMPLUS3 -> CHIP_AY8912;
    };
  }
}

