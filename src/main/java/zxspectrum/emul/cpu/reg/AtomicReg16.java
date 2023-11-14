package zxspectrum.emul.cpu.reg;

import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * AtomicReg16.
 *
 * @author Maxim Gorin
 */
@NoArgsConstructor
public class AtomicReg16 extends Reg16 {

    protected int value;

    protected AtomicReg16(@NonNull Reg8 lo, @NonNull Reg8 hi) {
    }

    @Override
    public void setValue(int value) {
        this.value = value & 0xFFFF;
    }

    @Override
    public void setValue(int lo, int hi) {
        this.value = ((hi & 0xFF) << 8) | (lo & 0xFF);
    }

    @NonNull
    public int getValue() {
        return value;
    }

    @Override
    public int getLoValue() {
        return value & 0xFF;
    }

    @Override
    public int getHiValue() {
        return (value >>> 8) & 0xFF;
    }

    @Override
    public int inc() {
        this.value++;
        this.value &= 0xFFFF;
        return value;
    }

    @Override
    public int dec() {
        this.value--;
        this.value &= 0xFFFF;
        return value;
    }

    @Override
    public void ld(@NonNull Reg16 r) {
        if (r instanceof AtomicReg16) {
            this.value = ((AtomicReg16) r).value;
        } else {
            this.value = r.getValue();
        }
    }

    @Override
    public void swap(@NonNull Reg16 r) {
        int tmp;
        if (r instanceof AtomicReg16) {
            tmp = ((AtomicReg16) r).value;
            ((AtomicReg16) r).value = value;
        } else {
            tmp = r.getValue();
            r.setValue(value);
        }
        value = tmp;
    }

    @Override
    public void reset() {
        this.value = 0;
    }
}
