package zxspectrum.emul.cpu.reg;

import java.util.Arrays;

abstract class Processor {
    protected static int[] SZ53N_ADD_TABLE;

    protected static int[] SZ53PN_ADD_TABLE;

    protected static int[] SZ53N_SUB_TABLE;

    protected static int[] SZ53PN_SUB_TABLE;

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
}
