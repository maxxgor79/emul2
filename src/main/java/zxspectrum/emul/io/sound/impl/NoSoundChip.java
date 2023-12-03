package zxspectrum.emul.io.sound.impl;

import lombok.NonNull;
import zxspectrum.emul.io.port.PortIO;
import zxspectrum.emul.io.sound.SoundChip;

/**
 * Beeper.
 *
 * @author Maxim Gorin
 */
public class NoSoundChip implements SoundChip {

  @Override
  public void reset() {
  }

  @Override
  public void setPortIO(@NonNull final PortIO portIO) {
  }

  @Override
  public void clock() {
  }
}
