package zxspectrum.emul.cpu.reg;

import lombok.NonNull;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.port.Port;

import java.io.IOException;

public class ArrayProcessor extends Processor implements Const {
    private final Cpu cpu;

    private MemoryControl memory;

    private final Port port;

    public ArrayProcessor(@NonNull final Cpu cpu) {
        this.cpu = cpu;
        this.memory = cpu.getMemory();
        this.port = cpu.getPort();
    }

    public void ldi() {
        int value = this.memory.peek8(cpu.HL);
        this.memory.poke8(cpu.DE, value);
        //this.MemIoImpl.contendedStates(this.getRegDE(), 2);
        cpu.HL.inc();
        cpu.DE.inc();
        cpu.BC.dec();
        value += cpu.A.value;
        cpu.F.value = ((cpu.F.value & 0xC0) | (value & BIT_3));
        if ((value & BIT_1) != 0x00) {
            cpu.F.set5Bit(true);
        }
        if (!cpu.BC.isZero()) {
            cpu.F.setParityOverflow(true);
        }
    }

    public void ldd() {
        int work8 = this.memory.peek8(cpu.HL);
        this.memory.poke8(cpu.DE, work8);
        //this.MemIoImpl.contendedStates(this.getRegDE(), 2);
        cpu.HL.dec();
        cpu.DE.dec();
        cpu.BC.dec();
        work8 += cpu.A.value;
        cpu.F.value = ((this.cpu.F.value & 0xC0) | (work8 & BIT_3));
        if ((work8 & BIT_1) != 0x00) {
            cpu.F.set5Bit(true);
        }
        if (!cpu.BC.isZero()) {
            cpu.F.setParityOverflow(true);
        }
    }

    public void ini() throws IOException {
        //this.MemIoImpl.contendedStates(this.getPairIR(), 1);
        final int work8 = port.read(cpu.AF.getValue());
        memory.poke8(cpu.HL, work8);
        cpu.MEM_PTR.setValue(cpu.BC.getValue() + 1);
        cpu.B.dec();
        cpu.HL.inc();
        cpu.F.value = SZ53PN_ADD_TABLE[cpu.B.value];
        if (work8 > 0x7F) {
            cpu.F.setN(true);
        }
        final int tmp = work8 + (cpu.C.value + 1 & 0xFF);
        if (tmp > 0xFF) {
            cpu.F.setHalfCarry(true);
            cpu.F.setCarry(true);
        } else {
            cpu.F.setCarry(false);
        }
        if ((SZ53PN_ADD_TABLE[(tmp & 0x07) ^ cpu.B.value] & BIT_2) == BIT_2) {
            cpu.F.setParityOverflow(true);
        } else {
            cpu.F.value &= 0xFFFFFFFB;
        }
    }

    public void ind() throws IOException {
        //this.MemIoImpl.contendedStates(this.getPairIR(), 1);
        final int work8 = port.read(cpu.BC.getValue());
        memory.poke8(cpu.HL, work8);
        cpu.MEM_PTR.setValue(cpu.BC.getValue() - 1);
        cpu.B.dec();
        cpu.HL.dec();
        cpu.F.value = SZ53PN_ADD_TABLE[cpu.B.value];
        if (work8 > 0x7F) {
            cpu.F.setN(true);
        }
        final int tmp = work8 + (cpu.C.value - 1 & 0xFF);
        if (tmp > 0xFF) {
            cpu.F.setHalfCarry(true);
            cpu.F.setCarry(true);
        } else {
            cpu.F.setCarry(false);
        }
        if ((SZ53PN_ADD_TABLE[(tmp & 0x07) ^ cpu.B.value] & BIT_2) == BIT_2) {
            cpu.F.setParityOverflow(true);
        } else {
            cpu.F.value &= 0xFFFFFFFB;
        }
    }

    public void outi() throws IOException {
        //this.MemIoImpl.contendedStates(this.getPairIR(), 1);
        cpu.B.dec();
        cpu.MEM_PTR.setValue(cpu.BC.getValue() + 1);
        final int work8 = this.memory.peek8(cpu.HL);
        port.write(cpu.BC.getValue(), work8);
        cpu.HL.inc();
        if (work8 > 0x7F) {
            cpu.F.value = SZ53N_SUB_TABLE[cpu.B.value];
        } else {
            cpu.F.value = SZ53N_ADD_TABLE[cpu.B.value];
        }
        if ((cpu.L.value + work8) > 0xFF) {
            cpu.F.setHalfCarry(true);
            cpu.F.setCarry(true);
        } else {
            cpu.F.setCarry(false);
        }
        if ((SZ53PN_ADD_TABLE[(cpu.L.value + work8 & 0x7) ^ cpu.B.value] & BIT_2) == BIT_2) {
            cpu.F.setParityOverflow(true);
        }
    }

    public void outd() throws IOException {
        //this.MemIoImpl.contendedStates(this.getPairIR(), 1);
        cpu.B.dec();
        cpu.MEM_PTR.setValue(cpu.BC.getValue() - 1);
        final int work8 = memory.peek8(cpu.HL);
        port.write(cpu.BC.getValue(), work8);
        cpu.HL.dec();
        if (work8 > 0x7F) {
            cpu.F.value = SZ53N_SUB_TABLE[cpu.B.value];
        } else {
            cpu.F.value = SZ53N_ADD_TABLE[cpu.B.value];
        }
        if ((cpu.L.value + work8) > 0xFF) {
            cpu.F.setHalfCarry(true);
            cpu.F.setCarry(true);
        } else {
            cpu.F.setCarry(false);
        }
        if ((SZ53PN_ADD_TABLE[(cpu.L.value + work8 & 0x07) ^ cpu.B.value] & BIT_2) == BIT_2) {
            cpu.F.setParityOverflow(true);
        }
    }
}
