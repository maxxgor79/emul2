package zxspectrum.emul.machine;

import zxspectrum.emul.io.port.PortIO16k;

/**
 * ZXSpectrum16K.
 *
 * @author Maxim Gorin
 */
class ZXSpectrum16K extends CommonZXSpectrum {

  ZXSpectrum16K() {
    super(MachineModel.SPECTRUM16K);
  }

  @Override
  public void run() {

  }

  @Override
  protected void initPortIO() {
    portIO = new PortIO16k(ula);
  }
}
