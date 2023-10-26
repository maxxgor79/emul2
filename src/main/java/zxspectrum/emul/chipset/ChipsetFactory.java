package zxspectrum.emul.chipset;

import lombok.NonNull;
import zxspectrum.emul.chipset.impl.Ula5C102E;
import zxspectrum.emul.machine.MachineModel;

/**
 * ChipsetFactory.
 *
 * @author Maxim Gorin
 */
public class ChipsetFactory {

  private static final Ula ULA_5_C_102_E = new Ula5C102E();

  private ChipsetFactory() {

  }

  public static Ula getInstance(@NonNull MachineModel machineModel) {
    return switch (machineModel) {
      case SPECTRUM16K -> ULA_5_C_102_E;
      case SPECTRUM48K -> ULA_5_C_102_E;
      case SPECTRUM128K -> ULA_5_C_102_E;
      case SPECTRUMPLUS2 -> ULA_5_C_102_E;
      case SPECTRUMPLUS2A -> ULA_5_C_102_E;
      case SPECTRUMPLUS3 -> ULA_5_C_102_E;
    };
  }
}
