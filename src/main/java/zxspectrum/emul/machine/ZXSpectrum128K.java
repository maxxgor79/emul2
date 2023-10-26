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
  protected void initPortIO() {
    portIO = new PortIO128k(ula, memory, soundChip);
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
