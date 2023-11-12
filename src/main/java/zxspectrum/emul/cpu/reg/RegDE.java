package zxspectrum.emul.cpu.reg;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * RegDE.
 *
 * @author Maxim Gorin
 */

@EqualsAndHashCode
@ToString
public class RegDE extends Reg16 {

  public RegDE(@NonNull Reg8 lo, @NonNull Reg8 hi) {
    super(lo, hi);
  }
}
