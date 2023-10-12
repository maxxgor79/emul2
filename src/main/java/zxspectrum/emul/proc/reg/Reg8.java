package zxspectrum.emul.proc.reg;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Reg8.
 *
 * @author Maxim Gorin
 */
@NoArgsConstructor
public abstract class Reg8 {

  @Getter
  protected volatile int value;

  public void setValue(int value) {
    this.value = value & 0xFF;
  }

  public void ld(@NonNull Reg8 r) {
    this.value = r.value;
  }
}
