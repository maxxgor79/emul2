package zxspectrum.emul.cpu.reg;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class RegIR extends Reg16 {
    public RegIR(@NonNull Reg8 lo, @NonNull Reg8 hi) {
        super(lo, hi);
    }
}
