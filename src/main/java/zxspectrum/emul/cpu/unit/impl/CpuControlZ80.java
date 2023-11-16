package zxspectrum.emul.cpu.unit.impl;

import lombok.NonNull;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.reg.RegF;
import zxspectrum.emul.cpu.unit.CpuControl;
import zxspectrum.emul.io.mem.MemoryAccess;
import zxspectrum.emul.io.mem.MemoryControl;

public class CpuControlZ80 implements CpuControl {
    private final Cpu cpu;

    private MemoryAccess memory;

    public CpuControlZ80(@NonNull Cpu cpu) {
        this.cpu = cpu;
        this.memory = cpu.getMemory();
    }

    @Override
    public void ei() {
        cpu.IFF1.setValue(true);
        cpu.IFF2.setValue(true);
    }

    @Override
    public void di() {
        cpu.IFF1.setValue(false);
        cpu.IFF2.setValue(false);
    }

    @Override
    public void im0() {
        //do nothing
    }

    @Override
    public void im1() {
        memory.push(cpu.PC);
        cpu.PC.setValue(0x38);
        cpu.WZ.ld(cpu.PC);
    }

    @Override
    public void im2() {
        memory.push(cpu.PC);
        final int address = cpu.I.getValue() << 8;
        cpu.PC.setValue(memory.peek16(address));
        cpu.WZ.ld(cpu.PC);
    }

    @Override
    public void halt() {
        cpu.HALT.setValue(true);
    }

    @Override
    public int cpl() {
        cpu.A.not();
        cpu.F.and(~RegF.BIT_3 & ~RegF.BIT_5);
        final int a = cpu.A.getValue();
        cpu.F.or(RegF.HALF_CARRY_FLAG | RegF.N_FLAG | (a & RegF.BIT_3) | (a & RegF.BIT_5));
        return a;
    }

    @Override
    public int ccf() {
        cpu.F.and(~RegF.BIT_3 & ~RegF.BIT_5 & ~RegF.N_FLAG);
        int a = cpu.A.getValue();
        cpu.F.or((a & RegF.BIT_3) | (a & RegF.BIT_5));
        if ((cpu.F.getValue() & RegF.CARRY_FLAG) != 0) {
            cpu.F.or(RegF.HALF_CARRY_FLAG);
        } else {
            cpu.F.and(~RegF.HALF_CARRY_FLAG);
        }
        return cpu.F.xor(RegF.CARRY_FLAG);
    }

    @Override
    public int scf() {
        cpu.F.and(~RegF.HALF_CARRY_FLAG & ~RegF.N_FLAG & ~RegF.BIT_3 & ~RegF.BIT_5);
        int a = cpu.A.getValue();
        return cpu.F.or(RegF.CARRY_FLAG | (a & RegF.BIT_3) | (a & RegF.BIT_5));
    }

    @Override
    public void nop() {
    }

    @Override
    public void setMemory(@NonNull MemoryAccess memory) {
        this.memory = memory;
    }
}
