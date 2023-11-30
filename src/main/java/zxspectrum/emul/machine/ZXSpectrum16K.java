package zxspectrum.emul.machine;

import lombok.NonNull;
import zxspectrum.emul.io.mem.ram.RamType;
import zxspectrum.emul.io.port.PortIO16k;
import zxspectrum.emul.profile.ZxProfile;

/**
 * ZXSpectrum16K.
 *
 * @author Maxim Gorin
 */
class ZXSpectrum16K extends CommonZXSpectrum {

  private PortIO16k portInstance;

  ZXSpectrum16K(@NonNull final ZxProfile profile) {
    super(profile);
    assert profile.getRamType() == RamType.Ram16k;
  }

  @Override
  public void run() {

  }

  @Override
  protected void createPortIO() {
    portIO = portInstance = new PortIO16k();
  }

  @Override
  protected void createSoundChip() {

  }

  @Override
  protected void initPortIO() {
    portInstance.setUla(ula);
  }

  @Override
  protected void initSoundChip() {

  }
}
