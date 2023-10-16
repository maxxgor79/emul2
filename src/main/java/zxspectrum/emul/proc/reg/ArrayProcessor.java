package zxspectrum.emul.proc.reg;

import lombok.NonNull;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.proc.Z80;

public class ArrayProcessor implements Const {
    private final Z80 z80;

    private MemoryControl memory;

    public ArrayProcessor(@NonNull final Z80 z80) {
        this.z80 = z80;
        this.memory = z80.getMemory();
    }

    public void ldi() {
        int value = this.memory.peek8(z80.HL);
        this.memory.poke8(z80.DE, value);
        //this.MemIoImpl.contendedStates(this.getRegDE(), 2);
        z80.HL.inc();
        z80.DE.inc();
        z80.BC.dec();
        value += z80.A.value;
        z80.F.value = ((z80.F.value & 0xC0) | (value & BIT_3));
        if ((value & BIT_1) != 0x00) {
            z80.F.value |= BIT_5;
        }
        if (!z80.BC.isZero()) {
            z80.F.value |= RegF.P_V_FLAG;
        }
    }

    public void ldd() {
        int work8 = this.memory.peek8(z80.HL);
        this.memory.poke8(z80.DE, work8);
        //this.MemIoImpl.contendedStates(this.getRegDE(), 2);
        z80.HL.dec();
        z80.DE.dec();
        z80.BC.dec();
        work8 += z80.A.value;
        z80.F.value = ((this.z80.F.value & 0xC0) | (work8 & BIT_3));
        if ((work8 & BIT_1) != 0x0) {
            z80.F.value |= BIT_5;
        }
        if (!z80.BC.isZero()) {
            z80.F.value |= RegF.P_V_FLAG;
        }
    }

    /*public void cpi() {
        final int regHL = this.getRegHL();
        int memHL = this.MemIoImpl.peek8(regHL);
        final boolean carry = this.carryFlag;
        this.cp(memHL);
        this.MemIoImpl.contendedStates(regHL, 5);
        this.carryFlag = carry;
        this.incRegHL();
        this.decRegBC();
        memHL = this.regA - memHL - (((this.sz5h3pnFlags & 0x10) != 0x0) ? 1 : 0);
        this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD2) | (memHL & 0x8));
        if ((memHL & 0x2) != 0x0) {
            this.sz5h3pnFlags |= 0x20;
        }
        if (this.getRegBC() != 0) {
            this.sz5h3pnFlags |= 0x4;
        }
        this.memptr = (this.memptr + 1 & 0xFFFF);
    }

    public void cpd() {
        final int regHL = this.getRegHL();
        int memHL = this.MemIoImpl.peek8(regHL);
        final boolean carry = this.carryFlag;
        this.cp(memHL);
        this.MemIoImpl.contendedStates(regHL, 5);
        this.carryFlag = carry;
        this.decRegHL();
        this.decRegBC();
        memHL = this.regA - memHL - (((this.sz5h3pnFlags & 0x10) != 0x0) ? 1 : 0);
        this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD2) | (memHL & 0x8));
        if ((memHL & 0x2) != 0x0) {
            this.sz5h3pnFlags |= 0x20;
        }
        if (this.getRegBC() != 0) {
            this.sz5h3pnFlags |= 0x4;
        }
        this.memptr = (this.memptr - 1 & 0xFFFF);
    }
    */
}
