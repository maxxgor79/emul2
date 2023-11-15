package zxspectrum.emul.cpu.reg;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * RegIY.
 *
 * @author Maxim Gorin
 */

@EqualsAndHashCode
@ToString
public class RegIY extends Reg16 {
    public RegIY(@NonNull Reg8 lo, @NonNull Reg8 hi) {
        super(lo, hi);
    }
}
