package zxspectrum.emul.cpu;

import lombok.NonNull;
import zxspectrum.emul.cpu.impl.Z80;
import zxspectrum.emul.machine.MachineModel;

/**
 * CpuFactory.
 *
 * @author Maxim Gorin
 */
public class CpuFactory {

  private static final Z80 CPU = new Z80();

  private CpuFactory() {

  }

  public static Cpu getInstance(@NonNull MachineModel model) {
    return switch (model) {
      case SPECTRUM16K -> CPU;
      case SPECTRUM48K -> CPU;
      case SPECTRUM128K -> CPU;
      case SPECTRUMPLUS2 -> CPU;
      case SPECTRUMPLUS2A -> CPU;
      case SPECTRUMPLUS3 -> CPU;
    };
  }

}
