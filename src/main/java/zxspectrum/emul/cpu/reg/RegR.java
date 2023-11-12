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

    private boolean bit7;

    @Override
    public int setValue(int value) {
        value &= 0xFF;
        this.value = value;
        this.bit7 = (value > 0x7F);
        return value;
    }

    public int getValue() {
        if (this.bit7) {
            return (this.value & 0x7F) | 0x80;
        }
        return this.value & 0x7F;
    }
}
