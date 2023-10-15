package zxspectrum.emul.proc.reg;

import lombok.NonNull;
import zxspectrum.emul.proc.Z80;

public class RegisterProcessor {
    private final Z80 z80;

    public RegisterProcessor(@NonNull Z80 z80) {
        this.z80 = z80;
    }

    public void bit(final int mask, @NonNull Reg8 r) {
        bit(mask, r.value);
    }

    public void bit(final int mask, final int reg) {
        final boolean zeroFlag = (mask & reg) == 0x00;
        z80.F.value = ((RegF.SZ53N_ADD_TABLE[reg] & 0xFFFFFF3B) | 0x10);
        if (zeroFlag) {
            z80.F.value |= 0x44;
        }
        if (mask == 128 && !zeroFlag) {
            z80.F.value |= 0x80;
        }
    }

    public int res(final int mask, final int reg) {
        return reg & ~mask;
    }

    public int res(final int mask, @NonNull Reg8 r) {
        return r.value & ~mask;
    }

    public int set(final int mask, final int reg) {
        return reg | mask;
    }

    public int set(final int mask, @NonNull Reg8 r) {
        return r.value | mask;
    }
}
