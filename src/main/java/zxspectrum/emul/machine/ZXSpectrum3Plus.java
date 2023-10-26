package zxspectrum.emul.machine;

import zxspectrum.emul.io.port.PortIOPlus3;
import zxspectrum.emul.io.sound.SoundChip;
import zxspectrum.emul.io.sound.SoundChipFactory;

/**
 * ZXSpectrum3Plus.
 *
 * @author Maxim Gorin
 */
class ZXSpectrum3Plus extends CommonZXSpectrum {

  protected SoundChip soundChip;

  ZXSpectrum3Plus() {
    super(MachineModel.SPECTRUMPLUS3);
  }

  @Override
  protected void initPortIO() {
    this.portIO = new PortIOPlus3(ula, memory, soundChip);
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
