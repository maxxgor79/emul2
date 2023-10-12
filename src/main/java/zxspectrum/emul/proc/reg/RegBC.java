package zxspectrum.emul.proc.reg;

import lombok.NonNull;

/**
 * RegBC.
 *
 * @author Maxim Gorin
 */
public class RegBC extends Reg16 {

  public RegBC(@NonNull Reg8 lo, @NonNull Reg8 hi) {
    super(lo, hi);
  }
}
