package zxspectrum.emul.io.port;

import java.io.IOException;
import lombok.NonNull;
import zxspectrum.emul.chipset.Ula;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.sound.SoundChip;


public class PortIO128k extends ExtendedPortIO {

  private PortIO128k() {

  }

  public PortIO128k(@NonNull Ula ula, @NonNull MemoryControl memory, @NonNull SoundChip soundChip) {
    this.ula = ula;
    this.memory = memory;
    this.soundChip = soundChip;
  }

  @Override
  public void reset() {

  }

  @Override
  public int read(int port) throws IOException {
    return 0;
  }

  @Override
  public void write(int port, int value) throws IOException {

  }
}
