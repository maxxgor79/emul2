package zxspectrum.emul.proc.reg;

import java.util.Arrays;

/**
 * RegF.
 *
 * @author Maxim Gorin
 */
public class RegF extends Reg8 {

    private static final int BIT7 = 0b1000_0000;

    private static final int BIT7MASK = 0b0111_1111;

    private static final int BIT6 = 0b0100_0000;

    private static final int BIT6MASK = 0b1011_1111;

    private static final int BIT4 = 0b0001_0000;

    private static final int BIT4MASK = 0b1110_1111;

    private static final int BIT2 = 0b0000_0100;

    private static final int BIT2MASK = 0b1111_1011;

    private static final int BIT1 = 0b0000_0010;

    private static final int BIT1MASK = 0b1111_1101;

    private static final int BIT0 = 0b0000_0001;

    private static final int BIT0MASK = 0b1111_1110;

    private static final int BIT5 = 0b0010_0000;

    private static final int BIT5MASK = 0b1101_1111;


    private static final int BIT3 = 0b0000_1000;

    private static final int BIT3MASK = 0b1111_0111;

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
            value |= BIT7;
        } else {
            value &= BIT7MASK;
        }
    }

    public boolean isSign() {
        return (value & BIT7) != 0;
    }


    public void setZero(final boolean zero) {
        if (zero) {
            value |= BIT6;
        } else {
            value &= BIT6MASK;
        }
    }

    public boolean isZero() {
        return (value & BIT6) != 0;
    }

    public void setHalfCarry(final boolean halfCarry) {
        if (halfCarry) {
            value |= BIT4;
        } else {
            value &= BIT4MASK;
        }
    }

    public boolean isHalfCarry() {
        return (value & BIT4) != 0;
    }

    public void setParityOverflow(final boolean po) {
        if (po) {
            value |= BIT2;
        } else {
            value &= BIT2MASK;
        }
    }

    public boolean isParityOverflow() {
        return (value & BIT2) != 0;
    }

    public void setAddSubstract(boolean n) {
        if (n) {
            value |= BIT1;
        } else {
            value &= BIT1MASK;
        }
    }

    public boolean isAddSubstract() {
        return (value & BIT1) != 0;
    }

    public void setCarry(final boolean carry) {
        if (carry) {
            value |= BIT0;
        } else {
            value &= BIT0MASK;
        }
    }

    public boolean isCarry() {
        return (value & BIT0) != 0;
    }

    public boolean is5Bit() {
        return (value & BIT5) != 0;
    }

    public void set5Bit(final boolean on) {
        if (on) {
            value |= BIT5;
        } else {
            value &= BIT5MASK;
        }
    }

    public boolean is3Bit() {
        return (value & BIT3) != 0;
    }

    public void set3Bit(final boolean on) {
        if (on) {
            value |= BIT3;
        } else {
            value &= BIT3MASK;
        }
    }
}
