package zxspectrum.emul.cpu.reg;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 * RegPC.
 *
 * @author Maxim Gorin
 */

@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class RegPC extends AtomicReg16 {

  protected RegPC(@NonNull Reg8 lo, @NonNull Reg8 hi) {
    super(lo, hi);
  }

  public void add(int d) {
    this.value = (this.value + d) & 0xFFFF;
  }
}
