package zxspectrum.emul.io.port;

import java.io.IOException;


public class PortIO128k extends ExtendedPortIO {

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