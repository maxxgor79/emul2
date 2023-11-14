package zxspectrum.emul.cpu.unit.impl;

import lombok.NonNull;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.reg.AtomicReg16;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.unit.Jump;
import zxspectrum.emul.io.mem.address.Address;

public class JumpZ80 implements Jump {
    private final Cpu cpu;

    private final Reg16 tmpReg = new AtomicReg16();

    public JumpZ80(@NonNull final Cpu cpu) {
        this.cpu = cpu;
    }

    @Override
    public void jp(final int address) {
        cpu.PC.setValue(address);
        cpu.WZ.ld(cpu.PC);
    }

    @Override
    public void jp(@NonNull final Address address) {
        address.peek(tmpReg);
        cpu.PC.setValue(tmpReg.getValue());
        cpu.WZ.ld(cpu.PC);
    }

    @Override
    public boolean jpZ(final int address) {
        if (cpu.F.isZeroSet()) {
            cpu.PC.setValue(address);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean jpNZ(final int address) {
        if (!cpu.F.isZeroSet()) {
            cpu.PC.setValue(address);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean jpC(final int address) {
        if (cpu.F.isCarrySet()) {
            cpu.PC.setValue(address);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean jpNC(final int address) {
        if (!cpu.F.isCarrySet()) {
            cpu.PC.setValue(address);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean jpP(final int address) {
        if (!cpu.F.isSignSet()) {
            cpu.PC.setValue(address);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean jpM(final int address) {
        if (cpu.F.isSignSet()) {
            cpu.PC.setValue(address);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean jpPO(final int address) {
        if (!cpu.F.isParityOverflowSet()) {
            cpu.PC.setValue(address);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean jpPE(final int address) {
        if (cpu.F.isParityOverflowSet()) {
            cpu.PC.setValue(address);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean djnz(int offset) {
        offset = (byte) offset;
        int b = cpu.B.dec();
        if (b != 0) {
            cpu.PC.add(offset);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public void jr(int offset) {
        offset = (byte) offset;
        cpu.PC.add(offset);
        cpu.WZ.ld(cpu.PC);
    }

    @Override
    public boolean jrZ(int offset) {
        offset = (byte) offset;
        if (cpu.F.isZeroSet()) {
            cpu.PC.add(offset);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean jrNZ(int offset) {
        offset = (byte) offset;
        if (!cpu.F.isZeroSet()) {
            cpu.PC.add(offset);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean jrC(int offset) {
        offset = (byte) offset;
        if (cpu.F.isCarrySet()) {
            cpu.PC.add(offset);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean jrNC(int offset) {
        offset = (byte) offset;
        if (!cpu.F.isCarrySet()) {
            cpu.PC.add(offset);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean jrP(int offset) {
        offset = (byte) offset;
        if (!cpu.F.isSignSet()) {
            cpu.PC.add(offset);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean jrM(int offset) {
        offset = (byte) offset;
        if (cpu.F.isSignSet()) {
            cpu.PC.add(offset);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean jrPO(int offset) {
        offset = (byte) offset;
        if (!cpu.F.isParityOverflowSet()) {
            cpu.PC.add(offset);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }

    @Override
    public boolean jrPE(int offset) {
        offset = (byte) offset;
        if (cpu.F.isParityOverflowSet()) {
            cpu.PC.add(offset);
            cpu.WZ.ld(cpu.PC);
            return true;
        }
        return false;
    }
}
