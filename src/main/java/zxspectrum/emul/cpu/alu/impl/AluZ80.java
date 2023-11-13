package zxspectrum.emul.cpu.alu.impl;

import lombok.NonNull;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.alu.Alu;
import zxspectrum.emul.cpu.reg.FlagTable;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.cpu.reg.RegA;
import zxspectrum.emul.cpu.reg.RegB;
import zxspectrum.emul.cpu.reg.RegC;
import zxspectrum.emul.cpu.reg.RegD;
import zxspectrum.emul.cpu.reg.RegE;
import zxspectrum.emul.cpu.reg.RegF;
import zxspectrum.emul.cpu.reg.RegH;
import zxspectrum.emul.cpu.reg.RegL;
import zxspectrum.emul.io.mem.MemoryAccess;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.mem.address.Address;
import zxspectrum.emul.io.mem.address.IAddress;

public class AluZ80 implements Alu, FlagTable {
    private Cpu cpu;

    private MemoryAccess memory;

    private final Reg8 tmpReg = new RegA();

    public AluZ80(@NonNull Cpu cpu) {
        initTables();
        this.cpu = cpu;
        this.memory = cpu.getMemory();
    }

    @Override
    public int daa() {
        int a = cpu.A.getValue();
        if (cpu.F.isCarrySet()) {
            a |= 1 << 8;
        }
        if (cpu.F.isHalfCarrySet()) {
            a |= 1 << 9;
        }
        if (cpu.F.isNSet()) {
            a |= 1 << 10;
        }
        int value = DAA[a];
        cpu.A.setValue(value >>> 8);
        cpu.F.setValue(value);
        return cpu.A.getValue();
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
    public int inc(@NonNull Reg16 r) {
        return r.inc();
    }

    @Override
    public int dec(@NonNull Reg16 r) {
        return r.dec();
    }

    @Override
    public int inc(@NonNull Reg8 r) {
        int result = r.inc();
        cpu.F.setValue(SZ53N_ADD_TABLE[result]);
        if ((result & 0x0F) == 0x00) {
            cpu.F.or(RegF.HALF_CARRY_FLAG);
        }
        if (result == 0x80) {
            cpu.F.or(RegF.P_V_FLAG);
        }
        return result;
    }

    @Override
    public int inc(@NonNull Address address) {
        address.peek(tmpReg);
        int result = inc(tmpReg);
        address.poke(tmpReg);
        return result;
    }

    @Override
    public int inc(IAddress address) {
        int result = inc(address);
        cpu.WZ.setValue(address.getAddress());
        return result;
    }

    @Override
    public int dec(@NonNull Reg8 r) {
        int result = r.dec();
        cpu.F.setValue(SZ53N_SUB_TABLE[result]);
        if ((result & 0x0F) == 0x0F) {
            cpu.F.or(RegF.HALF_CARRY_FLAG);
        }
        if (result == 0x7F) {
            cpu.F.or(RegF.P_V_FLAG);
        }
        return result;
    }

    @Override
    public int srl(@NonNull Reg8 r) {
        final boolean carryFlag = ((r.getValue() & 0x01) != 0x00);
        int result = r.rShift();
        cpu.F.setValue(SZ53PN_ADD_TABLE[result]);
        cpu.F.setCarry(carryFlag);
        return result;
    }

    //undocumented
    @Override
    public int srl(Reg8 r, @NonNull IAddress address) {
        address.peek(r);
        int result = srl(r);
        address.poke(r);
        return result;
    }

    @Override
    public int sra(@NonNull Reg8 r) {
        int result = r.getValue();
        final int tmp = result & 0x80;
        final boolean carryFlag = ((result & 0x01) != 0x00);
        r.rShift();
        result = r.or(tmp);
        cpu.F.setValue(SZ53PN_ADD_TABLE[result]);
        cpu.F.setCarry(carryFlag);
        return result;
    }

    //undocumented
    @Override
    public int sra(Reg8 r, @NonNull IAddress address) {
        address.peek(r);
        int result = sra(r);
        address.poke(r);
        return result;
    }

    @Override
    public int rr(@NonNull Reg8 r) {
        final boolean carry = cpu.F.isCarrySet();
        final boolean carryFlag = ((r.getValue() & RegF.CARRY_FLAG) != 0x00);
        int result = r.rShift();
        if (carry) {
            r.or(RegF.SIGN_FLAG);
        }
        cpu.F.setValue(SZ53PN_ADD_TABLE[result]);
        cpu.F.setCarry(carryFlag);
        return result;
    }

    //undocumented
    @Override
    public int rr(Reg8 r, @NonNull IAddress address) {
        address.peek(r);
        int result = rr(r);
        address.poke(r);
        return result;
    }

    @Override
    public int rrc(@NonNull Reg8 r) {
        final boolean carryFlag = ((r.getValue() & 0x01) != RegF.CARRY_FLAG);
        int result = r.rShift();
        if (carryFlag) {
            result = r.or(RegF.SIGN_FLAG);
        }
        cpu.F.setValue(SZ53PN_ADD_TABLE[result]);
        cpu.F.setCarry(carryFlag);
        return result;
    }

    //undocumented
    @Override
    public int rrc(Reg8 r, @NonNull IAddress address) {
        address.peek(r);
        int result = rrc(r);
        address.poke(r);
        return result;
    }

    @Override
    public int sll(@NonNull Reg8 r) {
        final boolean carryFlag = (r.getValue() > 127);
        r.lShift();
        int result = r.or(0x01);
        cpu.F.setValue(SZ53PN_ADD_TABLE[result]);
        cpu.F.setCarry(carryFlag);
        return result;
    }

    //undocumented ssl(HL) #CB#36
    @Override
    public int sll(Reg8 r, @NonNull Address address) {
        address.peek(r);
        int result = sll(r);
        address.poke(r);
        return result;
    }

    //undocumented
    @Override
    public int sll(Reg8 r, @NonNull IAddress address) {
        address.peek(r);
        int result = sll(r);
        address.poke(r);
        return result;
    }

    @Override
    public int sla(@NonNull Reg8 r) {
        final boolean carryFlag = (r.getValue() > 127);
        r.lShift();
        int result = r.and(0xFE);
        cpu.F.setValue(SZ53PN_ADD_TABLE[result]);
        cpu.F.setCarry(carryFlag);
        return result;
    }

    //undocumented
    @Override
    public int sla(Reg8 r, @NonNull IAddress address) {
        address.peek(r);
        int result = sla(r);
        address.poke(r);
        return result;
    }

    //undocumented
    @Override
    public int sl1(@NonNull Reg8 r) {
        final boolean carryFlag = (r.getValue() > 127);
        r.lShift();
        int result = r.or(0x01);
        cpu.F.setValue(SZ53PN_ADD_TABLE[result]);
        cpu.F.setCarry(carryFlag);
        return result;
    }

    //undocumented
    @Override
    public int sl1(Reg8 r, @NonNull IAddress address) {
        address.peek(r);
        int result = sl1(r);
        address.poke(r);
        return result;
    }

    @Override
    public int rl(@NonNull Reg8 r) {
        final boolean carry = cpu.F.isCarrySet();
        final boolean carryFlag = (r.getValue() > 127);
        int result = r.lShift();
        if (carry) {
            result = r.or(0x01);
        }
        cpu.F.setValue(SZ53PN_ADD_TABLE[result]);
        cpu.F.setCarry(carryFlag);
        return result;
    }

    //undocumented
    @Override
    public int rl(Reg8 r, @NonNull IAddress address) {
        address.peek(r);
        int result = rl(r);
        address.poke(r);
        return result;
    }

    @Override
    public int rlc(@NonNull Reg8 r) {
        final boolean carryFlag = (r.getValue() > 127);
        int result = r.lShift();
        if (carryFlag) {
            result = r.or(0x01);
        }
        cpu.F.setValue(SZ53PN_ADD_TABLE[result]);
        cpu.F.setCarry(carryFlag);
        return result;
    }

    @Override
    public int rlc(@NonNull Address address) {
        address.peek(tmpReg);
        int result = rlc(tmpReg);
        address.poke(tmpReg);
        return result;
    }

    @Override
    public int rlc(@NonNull IAddress address) {
        cpu.WZ.setValue(address.getAddress());
        return rlc(address);
    }

    //undocumented
    @Override
    public int rlc(Reg8 r, @NonNull IAddress address) {
        address.peek(r);
        int result = rlc(r);
        address.peek(r);
        return result;
    }

    @Override
    public int rld() {
        int a = cpu.A.getValue();
        final int val = a & 0x0F;
        final int memHL = memory.peek8(cpu.HL);
        a = cpu.A.setValue((a & 0xF0) | memHL >>> 4);
        memory.poke8(cpu.HL, (memHL << 4 | val) & 0xFF);
        cpu.F.setValue(SZ53PN_ADD_TABLE[a]);
        cpu.WZ.setValue(cpu.HL.getValue() + 1);
        return a;
    }

    @Override
    public int rrd() {
        int a = cpu.A.getValue();
        final int val = (a & 0x0F) << 4;
        final int memHL = memory.peek8(cpu.HL);
        a = cpu.A.setValue((a & 0xF0) | (memHL & 0x0F));
        memory.poke8(cpu.HL, memHL >>> 4 | val);
        cpu.F.setValue(SZ53PN_ADD_TABLE[a]);
        cpu.WZ.setValue(cpu.HL.getValue() + 1);
        return a;
    }

    @Override
    public int neg() {
        return cpu.A.setValue(sub8(0, cpu.A.getValue()));
    }

    @Override
    public void cp(@NonNull Reg8 r) {
        cp(r.getValue());
    }

    @Override
    public void cp(int n) {
        n &= 0xFF;
        int a = cpu.A.getValue();
        int res = a - n;
        final boolean carryFlag = ((res & 0x100) != 0x00);
        res &= 0xFF;
        cpu.F.setValue((SZ53N_ADD_TABLE[n] & 0x28) | (SZ53N_SUB_TABLE[res] & 0xD2));
        if ((res & 0x0F) > (a & 0x0F)) {
            cpu.F.or(RegF.HALF_CARRY_FLAG);
        }
        if (((a ^ n) & (a ^ res)) > 0x7F) {
            cpu.F.or(RegF.P_V_FLAG);
        }
        cpu.F.setCarry(carryFlag);
    }

    @Override
    public int or(@NonNull Reg8 r2) {
        int result = cpu.A.or(r2);
        cpu.F.setValue(SZP_FLAGS[result]);
        return result;
    }

    @Override
    public int xor(@NonNull Reg8 r) {
        int result = cpu.A.xor(r);
        cpu.F.setValue(SZP_FLAGS[result]);
        return result;
    }

    @Override
    public int and(@NonNull Reg8 r) {
        int result = cpu.A.and(r);
        cpu.F.setValue(RegF.HALF_CARRY_FLAG | SZP_FLAGS[result]);
        return result;
    }

    @Override
    public int sbc(@NonNull Reg8 r) {
        return sbc(r.getValue());
    }

    @Override
    public int sbc(int n) {
        final int carry = cpu.F.isCarrySet() ? 1 : 0;
        final int a = cpu.A.getValue();
        int res = a - n - carry;
        final boolean carryFlag = ((res & 0x100) != 0x00);
        res &= 0xFF;
        cpu.F.setValue(SZ53N_SUB_TABLE[res]);
        if (((a & 0x0F) - (n & 0x0F) - carry & RegF.BIT_4) != 0x00) {
            cpu.F.or(RegF.HALF_CARRY_FLAG);
        }
        if (((a ^ n) & (a ^ res)) > 0x7F) {
            cpu.F.or(RegF.P_V_FLAG);
        }
        cpu.A.setValue(res);
        cpu.F.setCarry(carryFlag);
        return res;
    }

    @Override
    public int sbc(@NonNull Reg16 r1, @NonNull Reg16 r2) {
        final int carry = cpu.F.isCarrySet() ? 1 : 0;
        final int arg1 = r1.getValue();
        final int arg2 = r2.getValue();
        int res = arg1 - arg2 - carry;
        final boolean carryFlag = ((res & 0x10000) != 0x00);
        res &= 0xFFFF;
        r1.setValue(res);
        cpu.F.setValue(SZ53N_SUB_TABLE[r1.getHiValue()]);
        if (res != 0) {
            cpu.F.and(0xFFFFFFBF);
        }
        if (((arg1 & 0xFFF) - (arg2 & 0xFFF) - carry & 0x1000) != 0x00) {
            cpu.F.or(RegF.HALF_CARRY_FLAG);
        }
        if (((arg1 ^ arg2) & (arg1 ^ res)) > 0x7FFF) {
            cpu.F.or(RegF.P_V_FLAG);
        }
        r1.setValue(res);
        cpu.F.setCarry(carryFlag);
        return res;
    }

    @Override
    public int sub(@NonNull Reg8 r) {
        return cpu.A.setValue(sub8(cpu.A.getValue(), r.getValue()));
    }

    @Override
    public int sub8(int a, int b) {
        int res = a - b;
        final boolean carryFlag = ((res & 0x100) != 0x00);
        res &= 0xFF;
        cpu.F.setValue(SZ53N_SUB_TABLE[res]);
        if ((res & 0x0F) > (a & 0x0F)) {
            cpu.F.or(RegF.HALF_CARRY_FLAG);
        }
        if (((a ^ b) & (a ^ res)) > 0x7F) {
            cpu.F.or(RegF.P_V_FLAG);
        }
        cpu.F.setCarry(carryFlag);
        return res;
    }

    @Override
    public int adc(@NonNull Reg8 r) {
        return adc(r.getValue());
    }

    @Override
    public int adc(int n) {
        n &= 0xFF;
        final int carry = cpu.F.isCarrySet() ? 1 : 0;
        final int a = cpu.A.getValue();
        int res = a + n + carry;
        final boolean carryFlag = (res > 0xFF);
        res &= 0xFF;
        cpu.F.setValue(SZ53N_ADD_TABLE[res]);
        if ((a & 0x0F) + (n & 0x0F) + carry > 0x0F) {
            cpu.F.or(RegF.HALF_CARRY_FLAG);
        }
        if (((a ^ ~n) & (a ^ res)) > 0x7F) {
            cpu.F.or(RegF.P_V_FLAG);
        }
        cpu.A.setValue(res);
        cpu.F.setCarry(carryFlag);
        return res;
    }

    @Override
    public int adc(@NonNull Reg16 r1, @NonNull Reg16 r2) {
        final int carry = cpu.F.isCarrySet() ? 1 : 0;
        final int arg1 = r1.getValue();
        final int arg2 = r2.getValue();
        int res = arg1 + arg2 + carry;
        final boolean carryFlag = (res > 0xFFFF);
        res &= 0xFFFF;
        r1.setValue(res);
        cpu.F.setValue(SZ53PN_ADD_TABLE[r1.getHiValue()]);
        if (res != 0) {
            cpu.F.and(0xFFFFFFBF);
        }
        if ((arg1 & 0xFFF) + (arg2 & 0xFFF) + carry > 4095) {
            cpu.F.or(RegF.HALF_CARRY_FLAG);
        }
        if (((arg1 ^ ~arg2) & (arg1 ^ res)) > 0x7FFF) {
            cpu.F.or(RegF.P_V_FLAG);
        }
        r1.setValue(res);
        cpu.F.setCarry(carryFlag);
        return res;
    }

    @Override
    public int add(@NonNull Reg16 r1, @NonNull Reg16 r2) {
        int arg1 = r1.getValue();
        int res = arg1 + r2.getValue();
        cpu.F.setCarry(res > 65535);
        cpu.F.setValue((cpu.F.getValue() & 0xC4) | (res >>> 8 & 0x28));
        res &= 0xFFFF;
        if ((res & 0xFFF) < (arg1 & 0xFFF)) {
            cpu.F.or(RegF.HALF_CARRY_FLAG);
        }
        r1.setValue(res);
        cpu.WZ.setValue(arg1 + 1);
        cpu.WZ.inc();
        return res;
    }

    @Override
    public int add(@NonNull Reg8 r) {
        return add(r.getValue());
    }

    @Override
    public int add(int n) {
        n &= 0xFF;
        final int a = cpu.A.getValue();
        int res = a + n;
        final boolean carryFlag = (res > 0xFF);
        res &= 0xFF;
        cpu.F.setValue(SZ53N_ADD_TABLE[res]);
        if ((res & 0x0F) < (a & 0x0F)) {
            cpu.F.or(RegF.HALF_CARRY_FLAG);
        }
        if (((a ^ ~n) & (a ^ res)) > 0x7F) {
            cpu.F.or(RegF.P_V_FLAG);
        }
        cpu.A.setValue(res);
        cpu.F.setCarry(carryFlag);
        return res;
    }

    @Override
    public int add(@NonNull Address address) {
        address.peek(tmpReg);
        int result = add(tmpReg);
        address.poke(tmpReg);
        return result;
    }

    @Override
    public void bit(int mask, @NonNull final Reg8 r) {
        int result = r.getValue();
        final boolean zeroFlag = (mask & result) == 0x00;
        cpu.F.setValue((SZ53N_ADD_TABLE[result] & 0xFFFFFF3B) | RegF.HALF_CARRY_FLAG);
        if (zeroFlag) {
            cpu.F.or(RegF.ZERO_FLAG | RegF.P_V_FLAG);
        } else {
            if (mask == RegF.BIT_7) {
                cpu.F.or(RegF.BIT_7);
            }
        }
    }

    @Override
    public void bit(int mask, @NonNull Address address) {
        address.peek(tmpReg);
        bit(mask, tmpReg);
    }

    @Override
    public void bit(int mask, IAddress address) {
        cpu.WZ.setValue(address.getAddress());
        bit(mask, address);
    }

    @Override
    public int res(int mask, @NonNull final Reg8 r) {
        return r.and(~mask);
    }

    //undocumented
    @Override
    public int res(Reg8 r, int mask, @NonNull IAddress address) {
        address.peek(r);
        int result = res(mask, r);
        address.poke(r);
        return result;
    }

    @Override
    public int set(int mask, @NonNull final Reg8 r) {
        return r.or(mask);
    }

    @Override
    public int set(int mask, @NonNull Address address) {
        address.peek(tmpReg);
        int result = set(mask, tmpReg);
        address.poke(tmpReg);
        return result;
    }

    @Override
    public int set(int mask, IAddress address) {
        cpu.WZ.setValue(address.getAddress());
        return set(mask, address);
    }

    //undocumented
    @Override
    public int set(Reg8 r, int mask, @NonNull IAddress address) {
        cpu.WZ.setValue(address.getAddress());
        address.peek(r);
        int result = set(mask, r);
        address.poke(r);
        return result;
    }

    @Override
    public void cpd() {
        int memHL = this.memory.peek8(cpu.HL);
        final boolean carry = cpu.F.isCarrySet();
        cp(memHL);
        cpu.F.setCarry(carry);
        cpu.HL.dec();
        cpu.BC.dec();
        memHL = cpu.A.getValue() - memHL - (cpu.F.isHalfCarrySet() ? 1 : 0);
        int flagValue = (cpu.F.getValue() & 0xD2) | (memHL & RegF.BIT_3);
        if ((memHL & RegF.BIT_1) != 0x00) {
            flagValue |= RegF.BIT_5;
        }
        if (!cpu.BC.isZero()) {
            flagValue |= RegF.P_V_FLAG;
        }
        cpu.F.setValue(flagValue);
        cpu.WZ.dec();
    }

    @Override
    public void cpi() {
        int memHL = this.memory.peek8(cpu.HL);
        final boolean carry = cpu.F.isCarrySet();
        cp(memHL);
        cpu.F.setCarry(carry);
        cpu.HL.inc();
        cpu.BC.dec();
        memHL = cpu.A.getValue() - memHL - (cpu.F.isHalfCarrySet() ? 1 : 0);
        int flagValue = (cpu.F.getValue() & 0xD2) | (memHL & RegF.BIT_3);
        if ((memHL & RegF.BIT_1) != 0x00) {
            flagValue |= RegF.BIT_5;
        }
        if (!cpu.BC.isZero()) {
            flagValue |= RegF.P_V_FLAG;
        }
        cpu.F.setValue(flagValue);
        cpu.WZ.inc();
    }

    @Override
    public void setMemory(@NonNull MemoryControl memory) {
        this.memory = memory;
    }
}
