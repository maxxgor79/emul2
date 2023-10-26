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
  protected void createPortIO() {
    portIO = new PortIO48k();
  }

  @Override
  protected void createSoundChip() {

  }

  @Override
  protected void initPortIO() {
    ((PortIO48k) portIO).setUla(ula);
  }

  @Override
  protected void initSoundChip() {

  }

  @Override
  public void run() {

  }
}
