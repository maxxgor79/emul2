package zxspectrum.emul.machine;

import zxspectrum.emul.io.port.PortIO16k;
import zxspectrum.emul.io.port.PortIO48k;

/**
 * ZXSpectrum48K.
 *
 * @author Maxim Gorin
 */
class ZXSpectrum48K extends CommonZXSpectrum {

  ZXSpectrum48K() {
    super(MachineModel.SPECTRUM48K);
  }

  @Override
  protected void initPortIO() {
    portIO = new PortIO48k(ula);
  }

  @Override
  public void run() {

  }
}
