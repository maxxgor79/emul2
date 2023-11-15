package zxspectrum.emul.cpu.reg;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * RegIX.
 *
 * @author Maxim Gorin
 */

@EqualsAndHashCode
@ToString
public class RegIX extends Reg16 {
    public RegIX(@NonNull Reg8 lo, @NonNull Reg8 hi) {
        super(lo, hi);
    }
}
