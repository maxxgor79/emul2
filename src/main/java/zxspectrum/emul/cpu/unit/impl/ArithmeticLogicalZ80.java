package zxspectrum.emul.cpu.unit.impl;

import lombok.NonNull;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.reg.FlagTable;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.cpu.reg.RegA;
import zxspectrum.emul.cpu.reg.RegF;
import zxspectrum.emul.cpu.unit.ArithmeticLogical;
import zxspectrum.emul.io.mem.MemoryAccess;
import zxspectrum.emul.io.mem.address.Address;
import zxspectrum.emul.io.mem.address.IdxAddress;

public class ArithmeticLogicalZ80 implements ArithmeticLogical, FlagTable {
    private final Cpu cpu;

    private MemoryAccess memory;

    private final Reg8 tmpReg = new RegA();

    public ArithmeticLogicalZ80(@NonNull Cpu cpu) {
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
    public int inc(@NonNull final Reg8 r) {
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
    public int inc(@NonNull final Address address) {
        address.peek(tmpReg);
        int result = inc(tmpReg);
        address.poke(tmpReg);
        return result;
    }

    @Override
    public int inc(final IdxAddress address) {
        int result = inc(address);
        cpu.WZ.setValue(address.getAddress());
        return result;
    }

    @Override
    public int dec(@NonNull final Reg8 r) {
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
    public int dec(@NonNull final Address address) {
        address.peek(tmpReg);
        int result = dec(tmpReg);
        address.poke(tmpReg);
        return result;
    }

    @Override
    public int srl(@NonNull final Reg8 r) {
        final boolean carryFlag = ((r.getValue() & 0x01) != 0x00);
        int result = r.rShift();
        cpu.F.setValue(SZ53PN_ADD_TABLE[result]);
        cpu.F.setCarry(carryFlag);
        return result;
    }

    @Override
    public int srl(@NonNull final Address address) {
        address.peek(tmpReg);
        int result = srl(tmpReg);
        address.poke(tmpReg);
        return result;
    }

    //undocumented
    @Override
    public int srl(Reg8 r, @NonNull final IdxAddress address) {
        address.peek(r);
        int result = srl(r);
        address.poke(r);
        return result;
    }

    @Override
    public int sra(@NonNull final Reg8 r) {
        int result = r.getValue();
        final int tmp = result & 0x80;
        final boolean carryFlag = ((result & 0x01) != 0x00);
        r.rShift();
        result = r.or(tmp);
        cpu.F.setValue(SZ53PN_ADD_TABLE[result]);
        cpu.F.setCarry(carryFlag);
        return result;
    }

    @Override
    public int sra(@NonNull final Address address) {
        address.peek(tmpReg);
        int result = sra(tmpReg);
        address.poke(tmpReg);
        return result;
    }

    //undocumented
    @Override
    public int sra(Reg8 r, @NonNull final IdxAddress address) {
        address.peek(r);
        int result = sra(r);
        address.poke(r);
        return result;
    }

    @Override
    public int rr(@NonNull final Reg8 r) {
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

    @Override
    public int rr(@NonNull final Address address) {
        address.peek(tmpReg);
        int result = rr(tmpReg);
        address.poke(tmpReg);
        return result;
    }

    //undocumented
    @Override
    public int rr(Reg8 r, @NonNull final IdxAddress address) {
        address.peek(r);
        int result = rr(r);
        address.poke(r);
        return result;
    }

    @Override
    public int rrc(@NonNull final Reg8 r) {
        final boolean carryFlag = ((r.getValue() & 0x01) != 0);
        int result = r.rShift();
        if (carryFlag) {
            result = r.or(RegF.SIGN_FLAG);
        }
        cpu.F.setValue(SZ53PN_ADD_TABLE[result]);
        cpu.F.setCarry(carryFlag);
        return result;
    }

    @Override
    public int rrc(@NonNull final Address address) {
        address.peek(tmpReg);
        int result = rrc(tmpReg);
        address.poke(tmpReg);
        return result;
    }

    //undocumented
    @Override
    public int rrc(Reg8 r, @NonNull final IdxAddress address) {
        address.peek(r);
        int result = rrc(r);
        address.poke(r);
        return result;
    }

    @Override
    public int sll(@NonNull final Reg8 r) {
        final boolean carryFlag = (r.getValue() > 127);
        r.lShift();
        int result = r.or(0x01);
        cpu.F.setValue(SZ53PN_ADD_TABLE[result]);
        cpu.F.setCarry(carryFlag);
        return result;
    }

    //undocumented ssl(HL) #CB#36
    @Override
    public int sll(Reg8 r, @NonNull final Address address) {
        address.peek(r);
        int result = sll(r);
        address.poke(r);
        return result;
    }

    //undocumented
    @Override
    public int sll(Reg8 r, @NonNull final IdxAddress address) {
        address.peek(r);
        int result = sll(r);
        address.poke(r);
        return result;
    }

    @Override
    public int sla(@NonNull final Reg8 r) {
        final boolean carryFlag = (r.getValue() > 127);
        r.lShift();
        int result = r.and(0xFE);
        cpu.F.setValue(SZ53PN_ADD_TABLE[result]);
        cpu.F.setCarry(carryFlag);
        return result;
    }

    @Override
    public int sla(@NonNull final Address address) {
        address.peek(tmpReg);
        int result = sla(tmpReg);
        address.poke(tmpReg);
        return result;
    }

    //undocumented
    @Override
    public int sla(Reg8 r, @NonNull final IdxAddress address) {
        address.peek(r);
        int result = sla(r);
        address.poke(r);
        return result;
    }

    //undocumented
    @Override
    public int sl1(@NonNull final Reg8 r) {
        final boolean carryFlag = (r.getValue() > 127);
        r.lShift();
        int result = r.or(0x01);
        cpu.F.setValue(SZ53PN_ADD_TABLE[result]);
        cpu.F.setCarry(carryFlag);
        return result;
    }

    @Override
    public int sl1(@NonNull Address address) {
        address.peek(tmpReg);
        int result = sl1(tmpReg);
        address.poke(tmpReg);
        return result;
    }

    //undocumented
    @Override
    public int sl1(Reg8 r, @NonNull final IdxAddress address) {
        address.peek(r);
        int result = sl1(r);
        address.poke(r);
        return result;
    }

    @Override
    public int rl(@NonNull final Reg8 r) {
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

    @Override
    public int rl(@NonNull final Address address) {
        address.peek(tmpReg);
        int result = rl(tmpReg);
        address.poke(tmpReg);
        return result;
    }

    //undocumented
    @Override
    public int rl(Reg8 r, @NonNull final IdxAddress address) {
        address.peek(r);
        int result = rl(r);
        address.poke(r);
        return result;
    }

    @Override
    public int rlc(@NonNull final Reg8 r) {
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
    public int rlc(@NonNull final Address address) {
        address.peek(tmpReg);
        int result = rlc(tmpReg);
        address.poke(tmpReg);
        return result;
    }

    @Override
    public int rlc(@NonNull final IdxAddress address) {
        cpu.WZ.setValue(address.getAddress());
        return rlc(address);
    }

    //undocumented
    @Override
    public int rlc(Reg8 r, @NonNull final IdxAddress address) {
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
        final int value = cpu.A.getValue();
        cpu.A.setValue(0);
        return sub8(value);
    }

    @Override
    public void cp(@NonNull final Reg8 r) {
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
    public void cp(@NonNull final Address address) {
        address.peek(tmpReg);
        cp(tmpReg.getValue());
    }

    @Override
    public int or(@NonNull final Reg8 r) {
        return or(r.getValue());
    }

    @Override
    public int or(final int n) {
        int result = cpu.A.or(n);
        cpu.F.setValue(SZP_FLAGS[result]);
        return result;
    }

    @Override
    public int or(@NonNull final Address address) {
        address.peek(tmpReg);
        return or(tmpReg.getValue());
    }

    @Override
    public int xor(@NonNull final Reg8 r) {
        return xor(r.getValue());
    }

    @Override
    public int xor(final int n) {
        int result = cpu.A.xor(n);
        cpu.F.setValue(SZP_FLAGS[result]);
        return result;
    }

    @Override
    public int xor(@NonNull final Address address) {
        address.peek(tmpReg);
        return xor(tmpReg.getValue());
    }

    @Override
    public int and(@NonNull final Reg8 r) {
        return and(r.getValue());
    }

    @Override
    public int and(final int n) {
        int result = cpu.A.and(n);
        cpu.F.setValue(RegF.HALF_CARRY_FLAG | SZP_FLAGS[result]);
        return result;
    }

    @Override
    public int and(@NonNull final Address address) {
        address.peek(tmpReg);
        return and(tmpReg);
    }

    @Override
    public int sbc(@NonNull final Reg8 r) {
        return sbc(r.getValue());
    }

    @Override
    public int sbc(int n) {
        n &= 0xFF;
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
    public int sbc(@NonNull final Address address) {
        address.peek(tmpReg);
        return sbc(tmpReg.getValue());
    }

    @Override
    public int sbc(@NonNull final Reg16 r1, @NonNull final Reg16 r2) {
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
        cpu.WZ.setValue(arg1 + 1);
        return res;
    }

    @Override
    public int sub(@NonNull final Reg8 r) {
        return cpu.A.setValue(sub8(r.getValue()));
    }

    @Override
    public int sub(@NonNull final Address address) {
        address.peek(tmpReg);
        return sub8(tmpReg.getValue());
    }

    @Override
    public int sub8(int b) {
        b &= 0xFF;
        final int a = cpu.A.getValue();
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
        cpu.A.setValue(res);
        return res;
    }

    @Override
    public int adc(@NonNull final Reg8 r) {
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
    public int adc(@NonNull final Address address) {
        address.peek(tmpReg);
        return adc(tmpReg.getValue());
    }

    @Override
    public int adc(@NonNull final Reg16 r1, @NonNull final Reg16 r2) {
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
        cpu.WZ.setValue(arg1 + 1);
        return res;
    }

    @Override
    public int add(@NonNull final Reg16 r1, @NonNull final Reg16 r2) {
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
        return res;
    }

    @Override
    public int add(@NonNull final Reg8 r) {
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
    public int add(@NonNull final Address address) {
        address.peek(tmpReg);
        return add(tmpReg.getValue());
    }

    @Override
    public void bit(final int mask, @NonNull final Reg8 r) {
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
    public void bit(final int mask, @NonNull final Address address) {
        address.peek(tmpReg);
        bit(mask, tmpReg);
    }

    @Override
    public void bit(final int mask, final IdxAddress address) {
        bit(mask, address);
        cpu.WZ.setValue(address.getAddress());
    }

    @Override
    public int res(final int mask, @NonNull final Reg8 r) {
        return r.and(~mask);
    }

    @Override
    public int res(final int mask, @NonNull final Address address) {
        address.peek(tmpReg);
        int result = res(mask, tmpReg);
        address.poke(tmpReg);
        return result;
    }

    //undocumented
    @Override
    public int res(final Reg8 r, final int mask, @NonNull final IdxAddress address) {
        address.peek(r);
        int result = res(mask, r);
        address.poke(r);
        return result;
    }

    @Override
    public int set(final int mask, @NonNull final Reg8 r) {
        return r.or(mask);
    }

    @Override
    public int set(final int mask, @NonNull final Address address) {
        address.peek(tmpReg);
        int result = set(mask, tmpReg);
        address.poke(tmpReg);
        return result;
    }

    @Override
    public int set(final int mask, final IdxAddress address) {
        int result = set(mask, address);
        cpu.WZ.setValue(address.getAddress());
        return result;
    }

    //undocumented
    @Override
    public int set(final Reg8 r, final int mask, @NonNull final IdxAddress address) {
        address.peek(r);
        int result = set(mask, r);
        address.poke(r);
        cpu.WZ.setValue(address.getAddress());
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
    public boolean cpdr() {
        cpd();
        if (!cpu.BC.isZero() && !cpu.F.isZero()) {
            cpu.PC.add(-2);
            return true;
        }
        return false;
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
    public boolean cpir() {
        cpi();
        if (!cpu.BC.isZero() && !cpu.F.isZero()) {
            cpu.PC.add(-2);
            return true;
        }
        return false;
    }

    @Override
    public void setMemory(@NonNull MemoryAccess memory) {
        this.memory = memory;
    }
}
