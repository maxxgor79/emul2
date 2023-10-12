package zxspectrum.emul.proc.reg;

import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * RegPC.
 *
 * @author Maxim Gorin
 */
@NoArgsConstructor
public class RegPC extends AtomicReg16 {

  protected RegPC(@NonNull Reg8 lo, @NonNull Reg8 hi) {
    super(lo, hi);
  }

  public void add(int d) {
    this.value += d;
    this.value &= 0xFFFF;
  }
}
