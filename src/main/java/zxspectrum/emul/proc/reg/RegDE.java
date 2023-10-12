package zxspectrum.emul.proc.reg;

import lombok.NonNull;

/**
 * RegDE.
 *
 * @author Maxim Gorin
 */
public class RegDE extends Reg16 {

  public RegDE(@NonNull Reg8 lo, @NonNull Reg8 hi) {
    super(lo, hi);
  }
}
