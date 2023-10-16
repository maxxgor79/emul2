package zxspectrum.emul.proc.reg;

import lombok.NonNull;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.proc.Z80;

public class LogicalProcessor implements Const {
    private final Z80 z80;

    private MemoryControl memory;

    public LogicalProcessor(@NonNull final Z80 z80) {
        this.z80 = z80;
        this.memory = z80.getMemory();
    }

    public int rlc(@NonNull final Reg8 r) {
        final boolean carryFlag = (r.value > 0x7F);
        r.value <<= 1;
        if (carryFlag) {
            r.value |= BIT_0;
        }
        r.value &= 0xFF;
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[r.value];
        z80.F.setCarry(carryFlag);
        return r.value;
    }

    private int rl(@NonNull final Reg8 r) {
        final boolean carry = z80.F.isCarry();
        boolean carryFlag = (r.value > 0x7F);
        r.value <<= 1;
        if (carry) {
            r.value |= BIT_0;
        }
        r.value &= 0xFF;
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[r.value];
        z80.F.setCarry(carryFlag);
        return r.value;
    }

    public int sla(@NonNull final Reg8 r) {
        final boolean carryFlag = r.value > 0x7F;
        r.value <<= 1;
        r.value &= 0xFE;
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[r.value];
        z80.F.setCarry(carryFlag);
        return r.value;
    }

    public int sll(@NonNull final Reg8 r) {
        final boolean carryFlag = (r.value > 0x7F);
        r.value <<= 1;
        r.value |= BIT_0;
        r.value &= 0xFF;
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[r.value];
        z80.F.setCarry(carryFlag);
        return r.value;
    }

    public int rrc(@NonNull final Reg8 r) {
        final boolean carryFlag = ((r.value & BIT_0) != 0x00);
        r.value >>>= 1;
        if (carryFlag) {
            r.value |= BIT_7;
        }
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[r.value];
        z80.F.setCarry(carryFlag);
        return r.value;
    }

    public int rr(@NonNull final Reg8 r) {
        final boolean carry = z80.F.isCarry();
        boolean carryFlag = ((r.value & BIT_0) != 0x00);
        r.value >>>= 1;
        if (carry) {
            r.value |= BIT_7;
        }
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[r.value];
        z80.F.setCarry(carryFlag);
        return r.value;
    }

    public void rrd() {
        final int value = (z80.A.value & 0x0F) << 4;
        final int memHL = this.memory.peek8(z80.HL);
        z80.A.value = ((z80.A.value & 0xF0) | (memHL & 0x0F));
        //this.MemIoImpl.contendedStates(regHL, 4);
        this.memory.poke8(z80.HL, memHL >>> 4 | value);
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[z80.A.value];
    }


    public void rld() {
        final int value = z80.A.value & 0x0F;
        final int memHL = memory.peek8(z80.HL);
        z80.A.value = ((z80.A.value & 0xF0) | memHL >>> 4);
        //this.MemIoImpl.contendedStates(regHL, 4);
        this.memory.poke8(z80.HL, (memHL << 4 | value) & 0xFF);
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[z80.A.value];
    }

    public int sra(@NonNull final Reg8 r) {
        final int tmp = r.value & BIT_7;
        final boolean carryFlag = ((r.value & BIT_0) != 0x00);
        r.value = (r.value >> 1 | tmp);
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[r.value];
        z80.F.setCarry(carryFlag);
        return r.value;
    }

    public int srl(@NonNull final Reg8 r) {
        final boolean carryFlag = ((r.value & BIT_0) != 0x00);
        r.value >>>= 1;
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[r.value];
        z80.F.setCarry(carryFlag);
        return r.value;
    }

    public void and(@NonNull final Reg8 r) {
        z80.A.value &= r.value;
        final boolean carryFlag = false;
        z80.F.value = (z80.F.SZ53PN_ADD_TABLE[z80.A.value] | RegF.HALF_CARRY_FLAG);
        z80.F.setCarry(carryFlag);
    }

    public final void xor(@NonNull final Reg8 r) {
        z80.A.value ^= r.value;
        final boolean carryFlag = false;
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[z80.A.value];
        z80.F.setCarry(carryFlag);
    }

    public void or(@NonNull final Reg8 r) {
        z80.A.value |= r.value;
        final boolean carryFlag = false;
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[z80.A.value];
        z80.F.setCarry(carryFlag);
    }
}
