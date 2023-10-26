package zxspectrum.emul.io.port;

import java.io.IOException;
import lombok.NonNull;
import zxspectrum.emul.chipset.Ula;


public class PortIO16k extends BasePortIO {

  private PortIO16k() {

  }

  public PortIO16k(@NonNull Ula ula) {
    this.ula = ula;
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
