package zxspectrum.emul.cpu.reg;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * RegHL.
 *
 * @author Maxim Gorin
 */

@EqualsAndHashCode
@ToString
public class RegHL extends Reg16 {

  public RegHL(@NonNull Reg8 lo, @NonNull Reg8 hi) {
    super(lo, hi);
  }
}
