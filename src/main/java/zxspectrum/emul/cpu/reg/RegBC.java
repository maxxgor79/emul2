package zxspectrum.emul.cpu.reg;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * RegBC.
 *
 * @author Maxim Gorin
 */

@EqualsAndHashCode
@ToString
public class RegBC extends Reg16 {

  public RegBC(@NonNull Reg8 lo, @NonNull Reg8 hi) {
    super(lo, hi);
  }
}
