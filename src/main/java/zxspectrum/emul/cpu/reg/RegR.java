package zxspectrum.emul.cpu.reg;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * RegR.
 *
 * @author Maxim Gorin
 */

@EqualsAndHashCode
@ToString
public class RegR extends Reg8 {

    private int bit7Mask;

    @Override
    public int setValue(int value) {
        this.value = value & 0xFF;
        bit7Mask = value & BIT_7;
        return this.value;
    }

    @Override
    public int inc() {
        value = ((value + 1) & 0x7F) | bit7Mask;
        return value;
    }

    @Override
    public int dec() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int not() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int and(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int and(Reg8 r) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int or(int val) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int or(Reg8 r) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int xor(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int xor(Reg8 r) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int rShift() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lShift() {
        throw new UnsupportedOperationException();
    }
}
