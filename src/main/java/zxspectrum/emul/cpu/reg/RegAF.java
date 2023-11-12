package zxspectrum.emul.cpu.reg;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * RegAF.
 *
 * @author Maxim Gorin
 */

@EqualsAndHashCode
@ToString
public class RegAF extends Reg16 {

  public RegAF(@NonNull Reg8 lo, @NonNull Reg8 hi) {
    super(lo, hi);
  }
}
