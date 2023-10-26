package zxspectrum.emul.cpu.reg;

import lombok.NonNull;

/**
 * RegHL.
 *
 * @author Maxim Gorin
 */
public class RegHL extends Reg16 {

  public RegHL(@NonNull Reg8 lo, @NonNull Reg8 hi) {
    super(lo, hi);
  }
}
