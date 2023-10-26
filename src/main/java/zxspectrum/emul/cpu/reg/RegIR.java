package zxspectrum.emul.cpu.reg;

import lombok.NonNull;

public class RegIR extends Reg16 {
    public RegIR(@NonNull Reg8 lo, @NonNull Reg8 hi) {
        super(lo, hi);
    }
}
