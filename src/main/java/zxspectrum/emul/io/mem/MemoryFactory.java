package zxspectrum.emul.io.mem;

import lombok.NonNull;
import zxspectrum.emul.io.mem.impl.Memory128K;
import zxspectrum.emul.io.mem.impl.Memory16K;
import zxspectrum.emul.io.mem.impl.Memory48K;
import zxspectrum.emul.io.mem.impl.MemoryDummy;
import zxspectrum.emul.io.mem.impl.MemoryPlus2;
import zxspectrum.emul.io.mem.impl.MemoryPlus2A;
import zxspectrum.emul.io.mem.impl.MemoryPlus3;
import zxspectrum.emul.machine.MachineModel;

/**
 * Factory.
 *
 * @author Maxim Gorin
 */
public final class MemoryFactory {

  private static final Memory16K M16K = new Memory16K();

  private static final Memory48K M48K = new Memory48K();

  private static final Memory128K M128K = new Memory128K();

  private static final MemoryPlus2 MPLUS2 = new MemoryPlus2();

  private static final MemoryPlus2A MPLUS2A = new MemoryPlus2A();

  private static final MemoryPlus3 MPLUS3 = new MemoryPlus3();

  private static final MemoryDummy DUMMY = new MemoryDummy();

  private MemoryFactory() {

  }

  public static MemoryControl getInstance(@NonNull MachineModel machineModel) {
    switch (machineModel) {
      case SPECTRUM16K -> {
        return M16K;
      }
      case SPECTRUM48K -> {
        return M48K;
      }
      case SPECTRUM128K -> {
        return M128K;
      }
      case SPECTRUMPLUS2 -> {
        return MPLUS2;
      }
      case SPECTRUMPLUS2A -> {
        return MPLUS2A;
      }
      case SPECTRUMPLUS3 -> {
        return MPLUS3;
      }
    }
    return DUMMY;
  }

}
