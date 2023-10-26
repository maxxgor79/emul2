package zxspectrum.emul.cpu.reg;

/**
 * RegR.
 *
 * @author Maxim Gorin
 */
public class RegR extends Reg8 {

    private boolean bit7;

    @Override
    public void setValue(int value) {
        value &= 0xFF;
        this.value = value;
        this.bit7 = (value > 0x7F);
    }

    public int getValue() {
        if (this.bit7) {
            return (this.value & 0x7F) | 0x80;
        }
        return this.value & 0x7F;
    }
}
