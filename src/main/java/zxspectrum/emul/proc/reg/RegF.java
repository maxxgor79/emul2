package zxspectrum.emul.proc.reg;

import java.util.Arrays;

/**
 * RegF.
 *
 * @author Maxim Gorin
 */
public class RegF extends Reg8 implements Const {

    public static final int SIGN_FLAG = BIT_7;

    public static final int ZERO_FLAG = BIT_6;

    public static final int HALF_CARRY_FLAG = BIT_4;

    public static final int P_V_FLAG = BIT_2;

    public static final int N_FLAG = BIT_1;

    public static final int CARRY_FLAG = BIT_0;

    private static final int BIT_5 = 0b0010_0000;

    private static final int BIT_3 = 0b0000_1000;

    public static int[] SZ53N_ADD_TABLE;

    public static int[] SZ53PN_ADD_TABLE;

    public static int[] SZ53N_SUB_TABLE;

    public static int[] SZ53PN_SUB_TABLE;

    static {
        SZ53N_ADD_TABLE = new int[256];
        SZ53PN_ADD_TABLE = new int[256];
        SZ53N_SUB_TABLE = new int[256];
        SZ53PN_SUB_TABLE = new int[256];
        Arrays.fill(SZ53N_ADD_TABLE, 0);
        Arrays.fill(SZ53N_SUB_TABLE, 0);
        Arrays.fill(SZ53PN_ADD_TABLE, 0);
        Arrays.fill(SZ53PN_SUB_TABLE, 0);
        for (int idx = 0; idx < 256; ++idx) {
            if (idx > 127) {
                final int[] sz53nAddTable2 = SZ53N_ADD_TABLE;
                final int n = idx;
                sz53nAddTable2[n] |= 0x80;
            }
            boolean evenBits = true;
            for (int mask = 1; mask < 256; mask <<= 1) {
                if ((idx & mask) != 0x00) {
                    evenBits = !evenBits;
                }
            }
            final int[] sz53nAddTable3 = SZ53N_ADD_TABLE;
            final int n2 = idx;
            sz53nAddTable3[n2] |= (idx & 0x28);
            SZ53N_SUB_TABLE[idx] = (SZ53N_ADD_TABLE[idx] | 0x02);
            if (evenBits) {
                SZ53PN_ADD_TABLE[idx] = (SZ53N_ADD_TABLE[idx] | 0x04);
                SZ53PN_SUB_TABLE[idx] = (SZ53N_SUB_TABLE[idx] | 0x04);
            } else {
                SZ53PN_ADD_TABLE[idx] = SZ53N_ADD_TABLE[idx];
                SZ53PN_SUB_TABLE[idx] = SZ53N_SUB_TABLE[idx];
            }
        }
        final int[] sz53nAddTable4 = SZ53N_ADD_TABLE;
        final int n3 = 0x00;
        sz53nAddTable4[n3] |= 0x40;
        final int[] sz53pn_addTable2 = SZ53PN_ADD_TABLE;
        final int n4 = 0x00;
        sz53pn_addTable2[n4] |= 0x40;
        final int[] sz53n_subTable2 = SZ53N_SUB_TABLE;
        final int n5 = 0x00;
        sz53n_subTable2[n5] |= 0x40;
        final int[] sz53pn_subTable2 = SZ53PN_SUB_TABLE;
        final int n6 = 0x00;
        sz53pn_subTable2[n6] |= 0x40;
    }

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
}
