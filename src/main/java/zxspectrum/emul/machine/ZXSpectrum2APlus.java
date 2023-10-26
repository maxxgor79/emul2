package zxspectrum.emul.machine;

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
  protected void initPortIO() {
    this.portIO = new PortIOPlus2A(ula, memory, soundChip);
  }

  @Override
  protected void initSoundChip() {
    soundChip = SoundChipFactory.getInstance(machineModel);
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
