package zxspectrum.emul.io.port;

import java.io.IOException;

public class PortIOPlus2 extends ExtendedPortIO {

  @Override
  public int read(int port) throws IOException {
    return 0;
  }

  @Override
  public void write(int port, int value) throws IOException {

  }

  @Override
  public void reset() {

  }
}
