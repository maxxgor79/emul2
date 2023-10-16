package zxspectrum.emul.proc.reg;

import lombok.NonNull;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.proc.Z80;

public class LogicalProcessor implements Const {
    private final Z80 z80;

    private MemoryControl memory;

    public LogicalProcessor(@NonNull Z80 z80) {
        this.z80 = z80;
        this.memory = z80.getMemory();
    }

    public int rlc(@NonNull Reg8 r) {
        final boolean carryFlag = (r.value > 127);
        r.value <<= 1;
        if (carryFlag) {
            r.value |= 0x01;
        }
        r.value &= 0xFF;
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[r.value];
        z80.F.setCarry(carryFlag);
        return r.value;
    }

    private int rl(@NonNull Reg8 r) {
        final boolean carry = z80.F.isCarry();
        boolean carryFlag = (r.value > 127);
        r.value <<= 1;
        if (carry) {
            r.value |= 0x01;
        }
        r.value &= 0xFF;
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[r.value];
        z80.F.setCarry(carryFlag);
        return r.value;
    }

    public int sla(@NonNull Reg8 r) {
        final boolean carryFlag = r.value > 127;
        r.value <<= 1;
        r.value &= 0xFE;
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[r.value];
        z80.F.setCarry(carryFlag);
        return r.value;
    }

    public int sll(@NonNull Reg8 r) {
        final boolean carryFlag = (r.value > 127);
        r.value <<= 1;
        r.value |= BIT_0;
        r.value &= 0xFF;
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[r.value];
        z80.F.setCarry(carryFlag);
        return r.value;
    }

    public int rrc(@NonNull Reg8 r) {
        final boolean carryFlag = ((r.value & 0x01) != 0x00);
        r.value >>>= 1;
        if (carryFlag) {
            r.value |= BIT_7;
        }
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[r.value];
        z80.F.setCarry(carryFlag);
        return r.value;
    }

    public int rr(@NonNull Reg8 r) {
        final boolean carry = z80.F.isCarry();
        boolean carryFlag = ((r.value & 0x1) != 0x0);
        r.value >>>= 1;
        if (carry) {
            r.value |= BIT_7;
        }
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[r.value];
        z80.F.setCarry(carryFlag);
        return r.value;
    }

    /*
        public void rrd() {
            final int aux = (z80.A.value & 0xF) << 4;
            final int regHL = z80.HL.getValue();
            final int memHL = this.memory.peek8(regHL);
            z80.A.value = ((z80.A.value & 0xF0) | (memHL & 0x0F));
            this.MemIoImpl.contendedStates(regHL, 4);
            this.memory.poke8(regHL, memHL >>> 4 | aux);
            this.sz5h3pnFlags = Z80.sz53pn_addTable[this.regA];
            this.memptr = (regHL + 1 & 0xFFFF);
        }

        private void rld() {
            final int aux = this.regA & 0xF;
            final int regHL = this.getRegHL();
            final int memHL = this.MemIoImpl.peek8(regHL);
            this.regA = ((this.regA & 0xF0) | memHL >>> 4);
            this.MemIoImpl.contendedStates(regHL, 4);
            this.MemIoImpl.poke8(regHL, (memHL << 4 | aux) & 0xFF);
            this.sz5h3pnFlags = Z80.sz53pn_addTable[this.regA];
            this.memptr = (regHL + 1 & 0xFFFF);
        }

     */
    public int sra(@NonNull Reg8 r) {
        final int tmp = r.value & BIT_7;
        final boolean carryFlag = ((r.value & BIT_0) != 0x00);
        r.value = (r.value >> 1 | tmp);
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[r.value];
        z80.F.setCarry(carryFlag);
        return r.value;
    }

    public int srl(@NonNull Reg8 r) {
        final boolean carryFlag = ((r.value & BIT_0) != 0x00);
        r.value >>>= 1;
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[r.value];
        z80.F.setCarry(carryFlag);
        return r.value;
    }

    public void and(@NonNull Reg8 r) {
        z80.A.value &= r.value;
        final boolean carryFlag = false;
        z80.F.value = (z80.F.SZ53PN_ADD_TABLE[z80.A.value] | RegF.HALF_CARRY_FLAG);
        z80.F.setCarry(carryFlag);
    }

    public final void xor(@NonNull Reg8 r) {
        z80.A.value ^= r.value;
        final boolean carryFlag = false;
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[z80.A.value];
        z80.F.setCarry(carryFlag);
    }

    public void or(@NonNull Reg8 r) {
        z80.A.value |= r.value;
        final boolean carryFlag = false;
        z80.F.value = z80.F.SZ53PN_ADD_TABLE[z80.A.value];
        z80.F.setCarry(carryFlag);
    }
}
