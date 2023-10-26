package zxspectrum.emul.machine;

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
  protected void initPortIO() {
    this.portIO = new PortIOPlus2(ula, memory, soundChip);
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
