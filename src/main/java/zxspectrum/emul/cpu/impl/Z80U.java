package zxspectrum.emul.cpu.impl;

import lombok.NonNull;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.CpuU;
import zxspectrum.emul.cpu.reg.AtomicReg16;
import zxspectrum.emul.cpu.reg.FlagTable;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.cpu.reg.RegA;
import zxspectrum.emul.cpu.reg.RegBC;
import zxspectrum.emul.cpu.reg.RegDE;
import zxspectrum.emul.cpu.reg.RegF;
import zxspectrum.emul.cpu.reg.RegHL;
import zxspectrum.emul.cpu.reg.RegI;
import zxspectrum.emul.cpu.reg.RegR;
import zxspectrum.emul.io.mem.MemoryAccess;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.mem.address.Address;
import zxspectrum.emul.io.mem.address.IAddress;
import zxspectrum.emul.io.mem.address.RpAddress;
import zxspectrum.emul.io.port.PortIO;

import java.io.IOException;

public class Z80U implements CpuU, FlagTable {
    private final Cpu cpu;

    private MemoryAccess memory;

    private PortIO portIO;

    private final Reg16 tmpReg = new AtomicReg16();

    public Z80U(@NonNull Cpu cpu) {
        this.cpu = cpu;
        this.memory = cpu.getMemory();
        this.portIO = cpu.getPortIO();
    }

    @Override
    public void ld(@NonNull final RegI i, @NonNull final RegA a) {
        i.setValue(a.getValue());
    }

    @Override
    public void ld(@NonNull final RegR r, @NonNull final RegA a) {
        r.setValue(a.getValue());
    }

    @Override
    public void ld(@NonNull final RegA a, @NonNull final RegI i) {
        final int value = i.getValue();
        a.setValue(value);
        int flagValue = 0;
        flagValue |= (value & RegF.SIGN_FLAG) | (value & RegF.BIT_5) | (value & RegF.BIT_3);
        if (value == 0) {
            flagValue |= RegF.ZERO_FLAG;
        }
        if (cpu.IFF2.isValue()) {
            flagValue |= RegF.P_V_FLAG;
        }
        cpu.F.setValue(flagValue);
    }

    @Override
    public void ld(@NonNull final RegA a, @NonNull final RegR r) {
        final int value = cpu.R.getValue();
        cpu.A.setValue(value);
        int flagValue = 0;
        flagValue |= (value & RegF.SIGN_FLAG) | (value & RegF.BIT_5) | (value & RegF.BIT_3);
        if (value == 0) {
            flagValue |= RegF.ZERO_FLAG;
        }
        if (cpu.IFF2.isValue()) {
            flagValue |= RegF.P_V_FLAG;
        }
        cpu.F.setValue(flagValue);
    }

    @Override
    public void ld(@NonNull final Reg8 r1, final Reg8 r2) {
        r1.ld(r2);
    }

    @Override
    public void ld(@NonNull final Reg8 r, final int n) {
        r.setValue(n);
    }

    @Override
    public void ld(final RegA a, @NonNull final Address address) {
        cpu.WZ.setValue(address.getAddress() + 1);
        address.peek(a);
    }

    @Override
    public void ld(@NonNull final Address address, final RegA a) {
        cpu.WZ.setValue(((address.getAddress() + 1) & 0xFF) | (a.getValue() << 8));
        address.poke(a);
    }

    @Override
    public void ld(RegA a, @NonNull RpAddress address) {
        cpu.WZ.setValue(address.getAddress() + 1);
        address.peek(a);
    }

    @Override
    public void ld(@NonNull RpAddress address, RegA a) {
        cpu.WZ.setValue(((address.getAddress() + 1) & 0xFF) | (a.getValue() << 8));
        address.poke(a);
    }

    @Override
    public void ld(@NonNull Address address, RegBC bc) {
        cpu.WZ.setValue(address.getAddress() + 1);
        address.poke(bc);
    }

    @Override
    public void ld(@NonNull Address address, RegDE de) {
        cpu.WZ.setValue(address.getAddress() + 1);
        address.poke(de);
    }

    @Override
    public void ld(@NonNull RegBC bc, Address address) {
        cpu.WZ.setValue(address.getAddress() + 1);
        address.peek(bc);
    }

    @Override
    public void ld(@NonNull RegDE de, Address address) {
        cpu.WZ.setValue(address.getAddress() + 1);
        address.peek(de);
    }

    @Override
    public void ld(Reg8 r, @NonNull IAddress address) {
        cpu.WZ.setValue(address.getAddress());
        address.peek(r);
    }

    @Override
    public void ld(@NonNull IAddress address, Reg8 r) {
        cpu.WZ.setValue(address.getAddress());
        address.poke(r);
    }

    @Override
    public void ld(@NonNull IAddress address, int n) {
        cpu.WZ.setValue(address.getAddress());
        tmpReg.setValue(n);
        address.poke(tmpReg);
    }

    @Override
    public void ld(@NonNull Address address, final Reg16 r) {
        address.poke(r);
    }

    @Override
    public void ld(final RegHL hl, @NonNull final Address address) {
        address.peek(hl);
    }

    @Override
    public void ld(final Reg16 r, final int n) {
        r.setValue(n);
    }

    @Override
    public void ld(final Reg16 r, @NonNull final Address address) {
        address.peek(r);
    }

    @Override
    public void exx() {
        cpu.HL.swap(cpu.altHL);
        cpu.DE.swap(cpu.altDE);
        cpu.BC.swap(cpu.altBC);
    }

    @Override
    public void ex(@NonNull Reg16 r1, @NonNull Reg16 r2) {
        r1.swap(r2);
    }

    @Override
    public void ex(@NonNull final Address address, @NonNull final Reg16 r) {
        address.peek(tmpReg);
        address.poke(r);
        r.ld(tmpReg);
    }

    @Override
    public void ex(@NonNull Address address, RegBC bc) {
        address.peek(tmpReg);
        address.poke(bc);
        bc.ld(tmpReg);
        cpu.WZ.setValue(address.getAddress());
    }

    @Override
    public void ex(@NonNull Address address, RegDE de) {
        address.peek(tmpReg);
        address.poke(de);
        de.ld(tmpReg);
        cpu.WZ.setValue(address.getAddress());
    }

    @Override
    public void in(@NonNull final RegA a, int n) throws IOException {
        n &= 0xFF;
        int a1 = a.getValue();
        cpu.WZ.setValue((a1 << 8) + n + 1);
        final int port = n | (a1 << 8);
        cpu.A.setValue(portIO.read(port));
    }

    @Override
    public void in(@NonNull final Reg8 r, @NonNull final RegBC bc) throws IOException {
        int value = portIO.read(bc.getValue());
        cpu.F.setValue(SZP_FLAGS[value] | (cpu.F.getValue() & RegF.CARRY_FLAG));
        r.setValue(value);
        cpu.WZ.setValue(bc.getValue() + 1);
    }

    @Override
    public void out(@NonNull final RegBC bc, @NonNull final Reg8 r) throws IOException {
        portIO.write(bc.getValue(), r.getValue());
        cpu.WZ.setValue(bc.getValue() + 1);
    }

    @Override
    public void out(int n, @NonNull final RegA a) throws IOException {
        n &= 0xFF;
        final int a1 = a.getValue();
        final int port = n | (a1 << 8);
        cpu.WZ.setValue(((port + 1) & 0xFF) | (a1 << 8));
        portIO.write(port, a1);
    }

    @Override
    public void ldd() {
        int b = memory.peek8(cpu.HL);
        this.memory.poke8(cpu.DE, b);
        cpu.HL.dec();
        cpu.DE.dec();
        int bcValue = cpu.BC.dec();
        b += cpu.A.getValue();
        int flagValue = (cpu.F.getValue() & 0xC0) | (b & RegF.BIT_3);
        if ((b & RegF.BIT_1) != 0x00) {
            flagValue |= RegF.BIT_5;
        }
        if (bcValue != 0) {
            flagValue |= RegF.P_V_FLAG;
        }
        if (bcValue != 1) {
            cpu.WZ.setValue(cpu.PC.getValue() + 1);
        }
        cpu.F.setValue(flagValue);
    }

    @Override
    public void ldi() {
        int b = this.memory.peek8(cpu.HL);
        this.memory.poke8(cpu.DE, b);
        cpu.HL.inc();
        cpu.DE.inc();
        int bcValue = cpu.BC.dec();
        b += cpu.A.getValue();
        int flagValue = (cpu.F.getValue() & 0xC0) | (b & RegF.BIT_3);
        if ((b & RegF.BIT_1) != 0x00) {
            flagValue |= RegF.BIT_5;
        }
        if (bcValue != 0) {
            flagValue |= RegF.P_V_FLAG;
        }
        if (bcValue != 1) {
            cpu.WZ.setValue(cpu.PC.getValue() + 1);
        }
        cpu.F.setValue(flagValue);
    }

    @Override
    public void outd() throws IOException {
        cpu.B.dec();
        cpu.WZ.setValue(cpu.BC.getValue() - 1);
        final int b = memory.peek8(cpu.HL);
        portIO.write(cpu.BC.getValue(), b);
        cpu.HL.dec();
        int flagValue;
        int regL = cpu.L.getValue();
        int regB = cpu.B.getValue();
        if (b > 0x7F) {
            flagValue = SZ53N_SUB_TABLE[regB];
        } else {
            flagValue = SZ53N_ADD_TABLE[regB];
        }
        if ((regL + b) > 0xFF) {
            flagValue |= RegF.HALF_CARRY_FLAG | RegF.CARRY_FLAG;
        } else {
            flagValue &= ~RegF.CARRY_FLAG;
        }
        if ((SZ53PN_ADD_TABLE[(regL + b & 0x07) ^ regB] & RegF.BIT_2) == RegF.BIT_2) {
            flagValue |= RegF.P_V_FLAG;
        }
        cpu.F.setValue(flagValue);
    }

    @Override
    public void outi() throws IOException {
        cpu.B.dec();
        cpu.WZ.setValue(cpu.BC.getValue() + 1);
        final int b = this.memory.peek8(cpu.HL);
        portIO.write(cpu.BC.getValue(), b);
        cpu.HL.inc();
        int flagValue;
        int regL = cpu.L.getValue();
        int regB = cpu.B.getValue();
        if (b > 0x7F) {
            flagValue = SZ53N_SUB_TABLE[regB];
        } else {
            flagValue = SZ53N_ADD_TABLE[regB];
        }
        if ((regL + b) > 0xFF) {
            flagValue |= RegF.CARRY_FLAG | RegF.HALF_CARRY_FLAG;
        } else {
            flagValue &= ~RegF.CARRY_FLAG;
        }
        if ((SZ53PN_ADD_TABLE[(regL + b & 0x7) ^ regB] & RegF.BIT_2) == RegF.BIT_2) {
            flagValue |= RegF.P_V_FLAG;
        }
        cpu.F.setValue(flagValue);
    }

    @Override
    public void ind() throws IOException {
        final int b = portIO.read(cpu.BC.getValue());
        memory.poke8(cpu.HL, b);
        cpu.WZ.setValue(cpu.BC.getValue() - 1);
        cpu.B.dec();
        cpu.HL.dec();
        int regB = cpu.B.getValue();
        int flagValue = SZ53PN_ADD_TABLE[regB];
        if (b > 0x7F) {
            flagValue |= RegF.N_FLAG;
        }
        final int tmp = b + (cpu.C.getValue() - 1 & 0xFF);
        if (tmp > 0xFF) {
            flagValue |= RegF.HALF_CARRY_FLAG | RegF.CARRY_FLAG;
        } else {
            flagValue &= ~RegF.CARRY_FLAG;
        }
        if ((SZ53PN_ADD_TABLE[(tmp & 0x07) ^ regB] & RegF.BIT_2) == RegF.BIT_2) {
            flagValue |= RegF.P_V_FLAG;
        } else {
            flagValue &= 0xFFFFFFFB;
        }
        cpu.F.setValue(flagValue);
    }

    @Override
    public void ini() throws IOException {
        final int b = portIO.read(cpu.AF.getValue());
        memory.poke8(cpu.HL, b);
        cpu.WZ.setValue(cpu.BC.getValue() + 1);
        cpu.HL.inc();
        int regB = cpu.B.getValue();
        int flagValue = SZ53PN_ADD_TABLE[regB];
        final int tmp = b + (cpu.C.getValue() + 1 & 0xFF);
        if (tmp > 0xFF) {
            flagValue |= RegF.HALF_CARRY_FLAG | RegF.CARRY_FLAG;
        } else {
            flagValue &= ~RegF.HALF_CARRY_FLAG;
        }
        if (b > 0x7F) {
            flagValue |= RegF.N_FLAG;
        }
        if ((SZ53PN_ADD_TABLE[(tmp & 0x07) ^ regB] & RegF.BIT_2) == RegF.BIT_2) {
            flagValue |= RegF.P_V_FLAG;
        } else {
            flagValue &= 0xFFFFFFFB;
        }
        cpu.F.setValue(flagValue);
    }

    @Override
    public void setMemory(@NonNull MemoryControl memory) {
        this.memory = memory;
    }

    @Override
    public void setPortIO(@NonNull PortIO portIO) {
        this.portIO = portIO;
    }
}
