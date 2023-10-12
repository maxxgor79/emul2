package zxspectrum.emul.proc.reg;

import lombok.NonNull;

/**
 * RegAF.
 *
 * @author Maxim Gorin
 */
public class RegAF extends Reg16 {

  public RegAF(@NonNull Reg8 lo, @NonNull Reg8 hi) {
    super(lo, hi);
  }
}
