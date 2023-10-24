package zxspectrum.emul.proc.reg;

import zxspectrum.emul.Resettable;

import java.util.Arrays;

/**
 * RegF.
 *
 * @author Maxim Gorin
 */
public class RegF extends Reg8 implements Const, Resettable {

    public static final int SIGN_FLAG = BIT_7;

    public static final int ZERO_FLAG = BIT_6;

    public static final int HALF_CARRY_FLAG = BIT_4;

    public static final int P_V_FLAG = BIT_2;

    public static final int N_FLAG = BIT_1;

    public static final int CARRY_FLAG = BIT_0;


    public void setSign(final boolean sign) {
        if (sign) {
            value |= SIGN_FLAG;
        } else {
            value &= ~SIGN_FLAG;
        }
    }

    public boolean isSign() {
        return (value & SIGN_FLAG) != 0;
    }


    public void setZero(final boolean zero) {
        if (zero) {
            value |= ZERO_FLAG;
        } else {
            value &= ~ZERO_FLAG;
        }
    }

    public boolean isZero() {
        return (value & ZERO_FLAG) != 0;
    }

    public void setHalfCarry(final boolean halfCarry) {
        if (halfCarry) {
            value |= HALF_CARRY_FLAG;
        } else {
            value &= ~HALF_CARRY_FLAG;
        }
    }

    public boolean isHalfCarry() {
        return (value & HALF_CARRY_FLAG) != 0;
    }

    public void setParityOverflow(final boolean po) {
        if (po) {
            value |= P_V_FLAG;
        } else {
            value &= ~P_V_FLAG;
        }
    }

    public boolean isParityOverflow() {
        return (value & P_V_FLAG) != 0;
    }

    public void setAddSubstract(boolean n) {
        if (n) {
            value |= N_FLAG;
        } else {
            value &= ~N_FLAG;
        }
    }

    public boolean isAddSubstract() {
        return (value & N_FLAG) != 0;
    }

    public void setN(boolean n) {
        if (n) {
            value |= N_FLAG;
        } else {
            value &= ~N_FLAG;
        }
    }

    public boolean isN() {
        return (value & N_FLAG) != 0;
    }

    public void setCarry(final boolean carry) {
        if (carry) {
            value |= CARRY_FLAG;
        } else {
            value &= ~CARRY_FLAG;
        }
    }

    public boolean isCarry() {
        return (value & CARRY_FLAG) != 0;
    }

    public boolean is5Bit() {
        return (value & BIT_5) != 0;
    }

    public void set5Bit(final boolean on) {
        if (on) {
            value |= BIT_5;
        } else {
            value &= ~BIT_5;
        }
    }

    public boolean is3Bit() {
        return (value & BIT_3) != 0;
    }

    public void set3Bit(final boolean on) {
        if (on) {
            value |= BIT_3;
        } else {
            value &= ~BIT_3;
        }
    }

    @Override
    public void reset() {
        value = 0;
    }
}
