package zxspectrum.emul.cpu.reg;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Reg8.
 *
 * @author Maxim Gorin
 */
@NoArgsConstructor
public abstract class Reg8 {

    @Getter
    protected volatile int value;

    public void setValue(int value) {
        this.value = value & 0xFF;
    }

    public void ld(@NonNull Reg8 r) {
        this.value = r.value;
    }

    public void dec() {
        value = --value & 0xFF;
    }

    public void inc() {
        value = ++value & 0xFF;
    }
}
