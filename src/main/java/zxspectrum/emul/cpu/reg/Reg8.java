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

    public int setValue(int value) {
        this.value = value & 0xFF;
        return value;
    }

    public void reset() {
        this.value = 0;
    }

    public int ld(@NonNull Reg8 r) {
        this.value = r.value;
        return value;
    }

    public int dec() {
        value = --value & 0xFF;
        return value;
    }

    public int inc() {
        value = ++value & 0xFF;
        return value;
    }

    public int not() {
        value = ~value & 0xFF;
        return value;
    }

    public int and(int n) {
        this.value &= n & 0xFF;
        return value;
    }

    public int and(@NonNull Reg8 r) {
        this.value &= r.value;
        return value;
    }

    public int or(int val) {
        val &= 0xFF;
        this.value |= val;
        return value;
    }

    public int or(@NonNull Reg8 r) {
        this.value |= r.value;
        return value;
    }

    public int xor(int n) {
        this.value ^= n & 0xFF;
        return value;
    }

    public int xor(@NonNull Reg8 r) {
        this.value ^= r.value;
        return value;
    }

    public int rShift() {
        return value >>>= 1;
    }

    public int lShift() {
        value <<= 1;
        value &= 0xFF;
        return value;
    }
}
