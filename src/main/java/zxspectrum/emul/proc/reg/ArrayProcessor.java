package zxspectrum.emul.proc.reg;

import lombok.NonNull;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.port.Port;
import zxspectrum.emul.proc.Z80;

import java.io.IOException;

public class ArrayProcessor extends Processor implements Const {
    private final Z80 z80;

    private MemoryControl memory;

    private final Port port;

    public ArrayProcessor(@NonNull final Z80 z80) {
        this.z80 = z80;
        this.memory = z80.getMemory();
        this.port = z80.getPort();
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
            z80.F.set5Bit(true);
        }
        if (!z80.BC.isZero()) {
            z80.F.setParityOverflow(true);
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
        if ((work8 & BIT_1) != 0x00) {
            z80.F.set5Bit(true);
        }
        if (!z80.BC.isZero()) {
            z80.F.setParityOverflow(true);
        }
    }

    public void ini() throws IOException {
        //this.MemIoImpl.contendedStates(this.getPairIR(), 1);
        final int work8 = port.read(z80.AF.getValue());
        memory.poke8(z80.HL, work8);
        //this.memptr = (this.getRegBC() + 1 & 0xFFFF);
        z80.B.dec();
        z80.HL.inc();
        z80.F.value = SZ53PN_ADD_TABLE[z80.B.value];
        if (work8 > 127) {
            z80.F.setN(true);
        }
        final int tmp = work8 + (z80.C.value + 1 & 0xFF);
        if (tmp > 255) {
            z80.F.setHalfCarry(true);
            z80.F.setCarry(true);
        } else {
            z80.F.setCarry(false);
        }
        if ((SZ53PN_ADD_TABLE[(tmp & 0x07) ^ z80.B.value] & BIT_2) == BIT_2) {
            z80.F.setParityOverflow(true);
        } else {
            z80.F.value &= 0xFFFFFFFB;
        }
    }

    public void ind() throws IOException {
        //this.MemIoImpl.contendedStates(this.getPairIR(), 1);
        final int work8 = port.read(z80.BC.getValue());
        memory.poke8(z80.HL, work8);
        //this.memptr = (this.getRegBC() - 1 & 0xFFFF);
        z80.B.dec();
        z80.HL.dec();
        z80.F.value = SZ53PN_ADD_TABLE[z80.B.value];
        if (work8 > 127) {
            z80.F.setN(true);
        }
        final int tmp = work8 + (z80.C.value - 1 & 0xFF);
        if (tmp > 255) {
            z80.F.setHalfCarry(true);
            z80.F.setCarry(true);
        } else {
            z80.F.setCarry(false);
        }
        if ((SZ53PN_ADD_TABLE[(tmp & 0x7) ^ z80.B.value] & BIT_2) == BIT_2) {
            z80.F.setParityOverflow(true);
        } else {
            z80.F.value &= 0xFFFFFFFB;
        }
    }

    public void outi() throws IOException {
        //this.MemIoImpl.contendedStates(this.getPairIR(), 1);
        z80.B.dec();
        //this.memptr = (this.getRegBC() + 1 & 0xFFFF);
        final int work8 = this.memory.peek8(z80.HL);
        port.write(z80.BC.getValue(), work8);
        z80.HL.inc();
        if (work8 > 127) {
            z80.F.value = SZ53N_SUB_TABLE[z80.B.value];
        } else {
            z80.F.value = SZ53N_ADD_TABLE[z80.B.value];
        }
        if ((z80.L.value + work8) > 255) {
            z80.F.setHalfCarry(true);
            z80.F.setCarry(true);
        } else {
            z80.F.setCarry(false);
        }
        if ((SZ53PN_ADD_TABLE[(z80.L.value + work8 & 0x7) ^ z80.B.value] & BIT_2) == BIT_2) {
            z80.F.setParityOverflow(true);
        }
    }

    public void outd() throws IOException {
        //this.MemIoImpl.contendedStates(this.getPairIR(), 1);
        z80.B.dec();
        //this.memptr = (this.getRegBC() - 1 & 0xFFFF);
        final int work8 = memory.peek8(z80.HL);
        port.write(z80.BC.getValue(), work8);
        z80.HL.dec();
        if (work8 > 127) {
            z80.F.value = SZ53N_SUB_TABLE[z80.B.value];
        } else {
            z80.F.value = SZ53N_ADD_TABLE[z80.B.value];
        }
        if ((z80.L.value + work8) > 255) {
            z80.F.setHalfCarry(true);
            z80.F.setCarry(true);
        } else {
            z80.F.setCarry(false);
        }
        if ((SZ53PN_ADD_TABLE[(z80.L.value + work8 & 0x07) ^ z80.B.value] & BIT_2) == BIT_2) {
            z80.F.setParityOverflow(true);
        }
    }
}
