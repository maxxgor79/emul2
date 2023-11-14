package zxspectrum.emul.cpu.reg;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import zxspectrum.emul.Resettable;

/**
 * Reg16.
 *
 * @author Maxim Gorin
 */
@NoArgsConstructor
public abstract class Reg16 implements Resettable, Const {

    protected Reg8 lo;

    protected Reg8 hi;

    public Reg16(@NonNull Reg8 lo, @NonNull Reg8 hi) {
        this.lo = lo;
        this.hi = hi;
    }

    public void setValue(int value) {
        this.hi.value = (value >>> 8) & 0xFF;
        this.lo.value = (value & 0xFF);
    }

    public void setValue(int lo, int hi) {
        this.lo.value = lo & 0xFF;
        this.hi.value = hi & 0xFF;

    }

    public int getValue() {
        return (hi.value << 8) | lo.value;
    }

    public int getLoValue() {
        return this.lo.value;
    }

    public int getHiValue() {
        return this.hi.value;
    }

    public void ld(@NonNull Reg16 r) {
        if (r.lo != null && r.hi != null) {
            this.lo.value = r.lo.value;
            this.hi.value = r.hi.value;
        } else {
            int value = r.getValue();
            this.lo.value = value & 0xFF;
            this.hi.value = value >>> 8;
        }
    }

    public int inc() {
        ++this.lo.value;
        if (this.lo.value < 0x100) {
            return getValue();
        }
        this.lo.value = 0x00;
        ++this.hi.value;
        if (this.hi.value < 0x100) {
            return getValue();
        }
        this.hi.value = 0x00;
        return getValue();
    }

    public int dec() {
        --this.lo.value;
        if (this.lo.value >= 0x00) {
            return getValue();
        }
        this.lo.value = 0xFF;
        --this.hi.value;
        if (this.hi.value >= 0x00) {
            return getValue();
        }
        this.hi.value = 0xFF;
        return getValue();
    }

    public boolean isZero() {
        return hi.value == 0 && lo.value == 0;
    }

    public void swap(@NonNull Reg16 r) {
        int tmp;
        if (r.lo != null && r.hi != null) {
            tmp = r.lo.value;
            r.lo.value = lo.value;
            lo.value = tmp;
            tmp = r.hi.value;
            r.hi.value = hi.value;
            hi.value = tmp;
        } else {
            tmp = r.getValue();
            r.setValue(getValue());
            lo.value = tmp & 0xFF;
            hi.value = tmp >>> 8;
        }
    }

    @Override
    public void reset() {
        this.lo.reset();
        this.hi.reset();
    }
}
