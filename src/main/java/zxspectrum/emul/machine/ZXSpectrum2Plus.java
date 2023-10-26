package zxspectrum.emul.machine;

import zxspectrum.emul.io.port.PortIO128k;
import zxspectrum.emul.io.port.PortIOPlus2;
import zxspectrum.emul.io.sound.SoundChip;
import zxspectrum.emul.io.sound.SoundChipFactory;

/**
 * ZXSpectrum2Plus.
 *
 * @author Maxim Gorin
 */
class ZXSpectrum2Plus extends CommonZXSpectrum {

  protected SoundChip soundChip;

  ZXSpectrum2Plus() {
    super(MachineModel.SPECTRUMPLUS2);
  }

  @Override
  protected void createPortIO() {
    this.portIO = new PortIOPlus2();
  }

  @Override
  protected void createSoundChip() {
    soundChip = SoundChipFactory.getInstance(machineModel);
  }

  @Override
  protected void initPortIO() {
    ((PortIOPlus2) portIO).setUla(ula);
    ((PortIOPlus2) portIO).setMemory(memory);
    ((PortIOPlus2) portIO).setSoundChip(soundChip);
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
