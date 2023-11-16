package zxspectrum.emul.cpu.unit.impl;

import lombok.NonNull;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.unit.CallReturn;
import zxspectrum.emul.io.mem.MemoryAccess;
import zxspectrum.emul.io.mem.MemoryControl;

public class CallReturnZ80 implements CallReturn {
    private final Cpu cpu;

    private MemoryAccess memory;

    public CallReturnZ80(@NonNull final Cpu cpu) {
        this.cpu = cpu;
        this.memory = cpu.getMemory();
    }

    @Override
    public void call(final int address) {
        memory.push(cpu.PC);
        cpu.PC.setValue(address);
        cpu.WZ.ld(cpu.PC);
    }

    @Override
    public boolean callZ(final int address) {
        if (cpu.F.isZeroSet()) {
            memory.push(cpu.PC);
            cpu.PC.setValue(address);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean callNZ(final int address) {
        if (!cpu.F.isZeroSet()) {
            memory.push(cpu.PC);
            cpu.PC.setValue(address);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean callC(final int address) {
        if (cpu.F.isCarrySet()) {
            memory.push(cpu.PC);
            cpu.PC.setValue(address);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean callNC(final int address) {
        if (!cpu.F.isCarrySet()) {
            memory.push(cpu.PC);
            cpu.PC.setValue(address);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean callP(final int address) {
        if (!cpu.F.isSignSet()) {
            memory.push(cpu.PC);
            cpu.PC.setValue(address);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean callM(final int address) {
        if (cpu.F.isSignSet()) {
            memory.push(cpu.PC);
            cpu.PC.setValue(address);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean callPE(final int address) {
        if (cpu.F.isParityOverflowSet()) {
            memory.push(cpu.PC);
            cpu.PC.setValue(address);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean callPO(final int address) {
        if (!cpu.F.isParityOverflowSet()) {
            memory.push(cpu.PC);
            cpu.PC.setValue(address);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public void ret() {
        memory.pop(cpu.PC);
        cpu.WZ.ld(cpu.PC);
    }

    @Override
    public void retI() {
        cpu.IFF1.setValue(cpu.IFF2.isValue());
        memory.pop(cpu.PC);
        cpu.WZ.ld(cpu.PC);
    }

    @Override
    public void retN() {
        cpu.IFF1.setValue(cpu.IFF2.isValue());
        memory.pop(cpu.PC);
        cpu.WZ.ld(cpu.PC);
    }

    @Override
    public boolean retZ() {
        if (cpu.F.isZeroSet()) {
            memory.pop(cpu.PC);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean retNZ() {
        if (!cpu.F.isZeroSet()) {
            memory.pop(cpu.PC);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean retC() {
        if (cpu.F.isCarrySet()) {
            memory.pop(cpu.PC);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean retNC() {
        if (!cpu.F.isCarrySet()) {
            memory.pop(cpu.PC);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean retP() {
        if (!cpu.F.isSignSet()) {
            memory.pop(cpu.PC);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean retM() {
        if (cpu.F.isSignSet()) {
            memory.pop(cpu.PC);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean retPO() {
        if (!cpu.F.isParityOverflowSet()) {
            memory.pop(cpu.PC);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean retPE() {
        if (cpu.F.isParityOverflowSet()) {
            memory.pop(cpu.PC);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public void rst0() {
        call(0x00);
    }

    @Override
    public void rst8() {
        call(0x08);
    }

    @Override
    public void rst10() {
        call(0x10);
    }

    @Override
    public void rst18() {
        call(0x18);
    }

    @Override
    public void rst20() {
        call(0x20);
    }

    @Override
    public void rst28() {
        call(0x28);
    }

    @Override
    public void rst30() {
        call(0x30);
    }

    @Override
    public void rst38() {
        call(0x38);
    }

    @Override
    public void setMemory(@NonNull MemoryAccess memory) {
        this.memory = memory;
    }
}
