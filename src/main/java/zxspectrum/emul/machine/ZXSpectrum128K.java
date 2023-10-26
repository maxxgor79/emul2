package zxspectrum.emul.machine;

import zxspectrum.emul.io.port.PortIO128k;
import zxspectrum.emul.io.sound.SoundChip;
import zxspectrum.emul.io.sound.SoundChipFactory;

/**
 * ZXSpectrum128K.
 *
 * @author Maxim Gorin
 */
class ZXSpectrum128K extends CommonZXSpectrum {

  protected SoundChip soundChip;

  ZXSpectrum128K() {
    super(MachineModel.SPECTRUM128K);
  }

  @Override
  protected void createPortIO() {
    portIO = new PortIO128k();
  }

  @Override
  protected void createSoundChip() {
    soundChip = SoundChipFactory.getInstance(machineModel);
  }

  @Override
  protected void initPortIO() {
    ((PortIO128k) portIO).setUla(ula);
    ((PortIO128k) portIO).setMemory(memory);
    ((PortIO128k) portIO).setSoundChip(soundChip);
  }

  @Override
  protected void initSoundChip() {
  }

  @Override
  public void reset() {
    super.reset();
    soundChip.reset();
  }

  @Override
  public void run() {

  }
}
