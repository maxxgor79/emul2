package zxspectrum.emul.machine;

import zxspectrum.emul.io.port.PortIOPlus2;
import zxspectrum.emul.io.port.PortIOPlus2A;
import zxspectrum.emul.io.sound.SoundChip;
import zxspectrum.emul.io.sound.SoundChipFactory;

/**
 * ZXSpectrum2APlus.
 *
 * @author Maxim Gorin
 */
class ZXSpectrum2APlus extends CommonZXSpectrum {

  protected SoundChip soundChip;

  ZXSpectrum2APlus() {
    super(MachineModel.SPECTRUMPLUS2A);
  }

  @Override
  protected void createPortIO() {
    this.portIO = new PortIOPlus2A();
  }

  @Override
  protected void createSoundChip() {
    soundChip = SoundChipFactory.getInstance(machineModel);
  }

  @Override
  protected void initPortIO() {
    ((PortIOPlus2A) portIO).setUla(ula);
    ((PortIOPlus2A) portIO).setMemory(memory);
    ((PortIOPlus2A) portIO).setSoundChip(soundChip);
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
