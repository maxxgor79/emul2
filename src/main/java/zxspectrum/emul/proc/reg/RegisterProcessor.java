package zxspectrum.emul.proc.reg;

import lombok.NonNull;
import zxspectrum.emul.proc.Z80;

public class RegisterProcessor implements Const {

    private final Z80 z80;

    public RegisterProcessor(@NonNull final Z80 z80) {
        this.z80 = z80;
    }

    public void bit(final int mask, @NonNull final Reg8 r) {
        final boolean zeroFlag = (mask & r.value) == 0x00;
        z80.F.value = ((RegF.SZ53N_ADD_TABLE[r.value] & 0xFFFFFF3B) | RegF.HALF_CARRY_FLAG);
        if (zeroFlag) {
            z80.F.value |= RegF.P_V_FLAG | BIT_3 | BIT_5;
        }
        if (mask == BIT_7 && !zeroFlag) {
            z80.F.value |= RegF.SIGN_FLAG;
        }
    }

    public int res(final int mask, @NonNull final Reg8 r) {
        return r.value & ~mask;
    }

    public int set(final int mask, @NonNull final Reg8 r) {
        return r.value | mask;
    }

    public void exx() {
        int tmp = z80.altB.value;
        z80.altB.value = z80.B.value;
        z80.B.value = tmp;

        tmp = z80.altC.value;
        z80.altC.value = z80.C.value;
        z80.C.value = tmp;

        tmp = z80.altD.value;
        z80.altD.value = z80.D.value;
        z80.D.value = tmp;

        tmp = z80.altE.value;
        z80.altE.value = z80.E.value;
        z80.E.value = tmp;

        tmp = z80.altH.value;
        z80.altH.value = z80.H.value;
        z80.H.value = tmp;

        tmp = z80.altL.value;
        z80.altL.value = z80.L.value;
        z80.L.value = tmp;
    }
}
