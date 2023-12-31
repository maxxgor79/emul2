package zxspectrum.emul.cpu.unit.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import zxspectrum.emul.cpu.Counter;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.unit.LdIO;
import zxspectrum.emul.cpu.reg.AtomicReg16;
import zxspectrum.emul.cpu.reg.FlagTable;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.cpu.reg.RegA;
import zxspectrum.emul.cpu.reg.RegBC;
import zxspectrum.emul.cpu.reg.RegDE;
import zxspectrum.emul.cpu.reg.RegF;
import zxspectrum.emul.cpu.reg.RegI;
import zxspectrum.emul.cpu.reg.RegR;
import zxspectrum.emul.io.mem.MemoryAccess;
import zxspectrum.emul.io.mem.address.AbsouluteAddress;
import zxspectrum.emul.io.mem.address.Address;
import zxspectrum.emul.io.mem.address.IdxAddress;
import zxspectrum.emul.io.mem.address.RpRegisteredAddress;
import zxspectrum.emul.io.port.PortIO;

@Slf4j
public class LdIOZ80 implements LdIO, FlagTable {
    private final Cpu cpu;

    private MemoryAccess memory;

    private PortIO portIO;

    private final Reg16 tmpReg = new AtomicReg16();

    private final Counter tStatesRemains;

    public LdIOZ80(@NonNull final Cpu cpu, @NonNull final Counter tStatesRemains) {
        this.cpu = cpu;
        this.tStatesRemains = tStatesRemains;
        this.memory = cpu.getMemory();
        this.portIO = cpu.getPortIO();
        initTables();
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
        if (cpu.IFF2.isOn()) {
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
        if (cpu.IFF2.isOn()) {
            flagValue |= RegF.P_V_FLAG;
        }
        cpu.F.setValue(flagValue);
    }

    @Override
    public void ld(final RegA a, @NonNull final AbsouluteAddress address) {
        address.peek(a);
        cpu.WZ.setValue(address.getAddress() + 1);
    }

    @Override
    public void ld(@NonNull final AbsouluteAddress address, final RegA a) {
        address.poke(a);
        cpu.WZ.setValue(((address.getAddress() + 1) & 0xFF) | (a.getValue() << 8));
    }

    @Override
    public void ld(final RegA a, @NonNull final RpRegisteredAddress address) {
        address.peek(a);
        cpu.WZ.setValue(address.getAddress() + 1);
    }

    @Override
    public void ld(@NonNull final RpRegisteredAddress address, final RegA a) {
        address.poke(a);
        cpu.WZ.setValue(((address.getAddress() + 1) & 0xFF) | (a.getValue() << 8));
    }

    @Override
    public void ld(@NonNull final Address address, final RegBC bc) {
        address.poke(bc);
        cpu.WZ.setValue(address.getAddress() + 1);
    }

    @Override
    public void ld(@NonNull final AbsouluteAddress address, final RegDE de) {
        address.poke(de);
        cpu.WZ.setValue(address.getAddress() + 1);
    }

    @Override
    public void ld(@NonNull final RegBC bc, final AbsouluteAddress address) {
        address.peek(bc);
        cpu.WZ.setValue(address.getAddress() + 1);
    }

    @Override
    public void ld(@NonNull final RegDE de, final AbsouluteAddress address) {
        address.peek(de);
        cpu.WZ.setValue(address.getAddress() + 1);
    }

    @Override
    public void ld(final Reg8 r, @NonNull final IdxAddress address) {
        address.peek(r);
        cpu.WZ.setValue(address.getAddress());
    }

    @Override
    public void ld(@NonNull final IdxAddress address, final Reg8 r) {
        address.poke(r);
        cpu.WZ.setValue(address.getAddress());
    }

    @Override
    public void ld(@NonNull final IdxAddress address, final int n) {
        tmpReg.setValue(n);
        address.poke(tmpReg);
        cpu.WZ.setValue(address.getAddress());
    }

    @Override
    public void ld(@NonNull Address address, int n) {
        tmpReg.setValue(n);
        address.poke(tmpReg);
    }

    @Override
    public void exx() {
        cpu.HL.swap(cpu.altHL);
        cpu.DE.swap(cpu.altDE);
        cpu.BC.swap(cpu.altBC);
    }

    @Override
    public void ex(@NonNull final Address address, @NonNull final Reg16 r) {
        address.peek(tmpReg);
        address.poke(r);
        r.ld(tmpReg);
    }

    @Override
    public void in(@NonNull final RegA a, int n) {
        n &= 0xFF;
        final int a1 = a.getValue();
        final int port = n | (a1 << 8);
        cpu.A.setValue(portIO.read(port));
        cpu.WZ.setValue((a1 << 8) + n + 1);
    }

    @Override
    public void in(@NonNull final Reg8 r, @NonNull final RegBC bc) {
        int value = portIO.read(bc.getValue());
        cpu.F.setValue(SZP_FLAGS[value] | (cpu.F.getValue() & RegF.CARRY_FLAG));
        r.setValue(value);
        cpu.WZ.setValue(bc.getValue() + 1);
    }

    //undocumented
    @Override
    public void in(@NonNull final RegBC bc) {
        int value = portIO.read(bc.getValue());
        cpu.F.setValue(SZP_FLAGS[value] | (cpu.F.getValue() & RegF.CARRY_FLAG));
        cpu.WZ.setValue(bc.getValue() + 1);
    }

    @Override
    public void out(@NonNull final RegBC bc, @NonNull final Reg8 r) {
        portIO.write(bc.getValue(), r.getValue());
        cpu.WZ.setValue(bc.getValue() + 1);
    }

    //undocumented
    @Override
    public void out(@NonNull final RegBC bc) {
        portIO.write(bc.getValue(), 0);
        cpu.WZ.setValue(bc.getValue() + 1);
    }

    @Override
    public void out(int n, @NonNull final RegA a) {
        n &= 0xFF;
        final int a1 = a.getValue();
        final int port = n | (a1 << 8);
        portIO.write(port, a1);
        cpu.WZ.setValue(((port + 1) & 0xFF) | (a1 << 8));
    }

    @Override
    public void ldd() {
        int b = memory.peek8(cpu.HL);
        this.memory.poke8(cpu.DE, b);
        cpu.HL.dec();
        cpu.DE.dec();
        final int bcValue = cpu.BC.dec();
        b += cpu.A.getValue();
        int flagValue = (cpu.F.getValue() & 0xC0) | (b & RegF.BIT_3);
        if ((b & RegF.BIT_1) != 0x00) {
            flagValue |= RegF.BIT_5;
        }
        if (bcValue != 0) {
            flagValue |= RegF.P_V_FLAG;
        }
        cpu.F.setValue(flagValue);
        if (bcValue != 1) {
            cpu.WZ.setValue(cpu.PC.getValue() + 1);
        }
    }

    @Override
    public boolean lddr() {
        ldd();
        if (!cpu.BC.isZero()) {
            cpu.PC.add(-2);
            return true;
        }
        return false;
    }

    @Override
    public void ldi() {
        int b = this.memory.peek8(cpu.HL);
        this.memory.poke8(cpu.DE, b);
        cpu.HL.inc();
        cpu.DE.inc();
        final int bcValue = cpu.BC.dec();
        b += cpu.A.getValue();
        int flagValue = (cpu.F.getValue() & 0xC0) | (b & RegF.BIT_3);
        if ((b & RegF.BIT_1) != 0x00) {
            flagValue |= RegF.BIT_5;
        }
        if (bcValue != 0) {
            flagValue |= RegF.P_V_FLAG;
        }
        cpu.F.setValue(flagValue);
        if (bcValue != 1) {
            cpu.WZ.setValue(cpu.PC.getValue() + 1);
        }
    }

    @Override
    public boolean ldir() {
        ldi();
        if (!cpu.BC.isZero()) {
            cpu.PC.add(-2);
            return true;
        }
        return false;
    }

    @Override
    public void outd() {
        final int bcValue = cpu.BC.getValue();
        final int b = memory.peek8(cpu.HL);
        portIO.write(bcValue, b);
        cpu.B.dec();
        cpu.HL.dec();
        int flagValue;
        final int regL = cpu.L.getValue();
        final int regB = cpu.B.getValue();
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
        cpu.WZ.setValue(bcValue - 1);
    }

    @Override
    public boolean otdr() {
        outd();
        if (!cpu.B.isZero()) {
            cpu.PC.add(-2);
            return true;
        }
        return false;
    }

    @Override
    public void outi() {
        final int bcValue = cpu.BC.getValue();
        final int b = this.memory.peek8(cpu.HL);
        portIO.write(bcValue, b);
        cpu.B.dec();
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
        cpu.WZ.setValue(bcValue + 1);
    }

    @Override
    public boolean otir() {
        outi();
        if (!cpu.B.isZero()) {
            cpu.PC.add(-2);
            return true;
        }
        return false;
    }

    @Override
    public void ind() {
        final int bcValue = cpu.BC.getValue();
        final int b = portIO.read(bcValue);
        memory.poke8(cpu.HL, b);
        cpu.B.dec();
        cpu.HL.dec();
        final int regB = cpu.B.getValue();
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
        cpu.WZ.setValue(bcValue - 1);
    }

    @Override
    public boolean indr() {
        ind();
        if (!cpu.B.isZero()) {
            cpu.PC.add(-2);
            return true;
        }
        return false;
    }

    @Override
    public void ini() {
        final int bcValue = cpu.BC.getValue();
        final int b = portIO.read(bcValue);
        memory.poke8(cpu.HL, b);
        cpu.B.dec();
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
        cpu.WZ.setValue(bcValue + 1);
    }

    @Override
    public boolean inir() {
        ini();
        if (!cpu.B.isZero()) {
            cpu.PC.add(-2);
            return true;
        }
        return false;
    }

    @Override
    public void setMemory(@NonNull final MemoryAccess memory) {
        this.memory = memory;
    }

    @Override
    public void setPortIO(@NonNull final PortIO portIO) {
        this.portIO = portIO;
    }
}
