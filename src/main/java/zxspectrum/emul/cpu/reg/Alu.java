package zxspectrum.emul.cpu.reg;

import lombok.NonNull;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.io.mem.MemoryAccess;
import zxspectrum.emul.io.port.PortIO;

import java.io.IOException;

public class Alu implements Const, FlagTable {
    private final Cpu cpu;

    private MemoryAccess memory;

    private PortIO portIO;

    public Alu(@NonNull Cpu cpu) {
        this.cpu = cpu;
        this.memory = cpu.getMemory();
        this.portIO = cpu.getPortIO();
    }

    public int add8(final int a, final int b) {
        int result;
        boolean carry;
        result = (a + b);
        carry = a > (0xff - b);
        final int carryIn = result ^ a ^ b;
        final int overflow = (carryIn >>> 7) ^ (carry ? 1 : 0);
        cpu.F.setSign((result >> 7) != 0);
        cpu.F.setZero(result == 0);
        cpu.F.set5Bit((result & BIT_5) != 0);
        cpu.F.setHalfCarry(((carryIn >>> 4) & 0x01) != 0);
        cpu.F.set3Bit((result & BIT_3) != 0);
        cpu.F.setParityOverflow(overflow != 0);
        cpu.F.setN(false);
        cpu.F.setCarry(carry);
        return result;
    }

    public int adc8(final int a, final int b, boolean carryFlag) {
        int result;
        boolean carry;
        if (carryFlag) {
            result = a + b + 1;
            carry = a >= (0xff - b);
        } else {
            result = a + b;
            carry = a > (0xff - b);
        }
        final int carryIn = result ^ a ^ b;
        final int overflow = (carryIn >>> 7) ^ (carry ? 1 : 0);
        cpu.F.setSign((result >> 7) != 0);
        cpu.F.setZero(result == 0);
        cpu.F.set5Bit((result & BIT_5) != 0);
        cpu.F.setHalfCarry(((carryIn >>> 4) & 0x01) != 0);
        cpu.F.set3Bit((result & BIT_3) != 0);
        cpu.F.setParityOverflow(overflow != 0);
        cpu.F.setN(false);
        cpu.F.setCarry(carry);
        return result;
    }

    public int sub8(final int a, final int b) {
        final int result = adc8(a, ~b & 0xff, true);
        cpu.F.value ^= RegF.HALF_CARRY_FLAG | RegF.N_FLAG | RegF.CARRY_FLAG; // invert HNC
        return result;
    }

    public int sbc8(final int a, final int b, final boolean carryFlag) {
        final int result = adc8(a, ~b & 0xff, !carryFlag);
        cpu.F.value ^= RegF.HALF_CARRY_FLAG | RegF.N_FLAG | RegF.CARRY_FLAG; // invert HNC
        return result;
    }

    public int add16(final int a, final int b) {
        final int lowResult = (a & 0xff) + (b & 0xff);
        final boolean lowCarry = (lowResult & 0x100) != 0;
        final int highA = (a >>> 8) & 0xff;
        final int highB = (b >>> 8) & 0xff;
        final int highResult = highA + highB + (lowCarry ? 1 : 0);
        boolean carry;
        if (lowCarry) {
            carry = highA >= (0xff - highB);
        } else {
            carry = highA > (0xff - highB);
        }
        final int carryIn = (highResult & 0xff) ^ highA ^ highB;
        final int result = ((highResult & 0xff) << 8) | (lowResult & 0xff);
        cpu.WZ.value = a + 1;
        cpu.F.set5Bit((highResult & BIT_5) != 0);
        cpu.F.setHalfCarry(((carryIn >> 4) & 0x01) != 0);
        cpu.F.set3Bit((highResult & BIT_3) != 0);
        cpu.F.setN(false);
        cpu.F.setCarry(carry);
        return result;
    }

    public int adc16(final int a, final int b, boolean carryFlag) {
        final int lowResult = (a & 0xff) + (b & 0xff) + (carryFlag ? 1 : 0);
        final boolean lowCarry = (lowResult & 0x100) != 0;
        final int highA = (a >>> 8) & 0xff;
        final int highB = (b >>> 8) & 0xff;
        final int highResult = highA + highB + (lowCarry ? 1 : 0);
        boolean carry;
        if (lowCarry) {
            carry = highA >= (0xff - highB);
        } else {
            carry = highA > (0xff - highB);
        }
        final int carryIn = (highResult & 0xff) ^ highA ^ highB;
        final int overflow = (carryIn >> 7) ^ (carry ? 1 : 0);
        final int result = ((highResult & 0xff) << 8) | (lowResult & 0xff);
        cpu.WZ.value = a + 1;
        cpu.F.setSign((result & 0x8000) != 0);
        cpu.F.setZero(result == 0);
        cpu.F.set5Bit((highResult & BIT_5) != 0);
        cpu.F.setHalfCarry(((carryIn >> 4) & 0x01) != 0);
        cpu.F.set3Bit((highResult & BIT_3) != 0);
        cpu.F.setParityOverflow(overflow != 0);
        cpu.F.setN(false);
        cpu.F.setCarry(carry);
        return result;
    }

    public int sub16(final int a, final int b) {
        final int result = add16(a, (~b + 1) & 0xffff);
        cpu.F.value ^= RegF.HALF_CARRY_FLAG | RegF.N_FLAG | RegF.CARRY_FLAG; // invert HNC
        return result;
    }

    public int sbc16(final int a, int b, boolean carryFlag) {
        final int result = adc16(a, ~b & 0xffff, !carryFlag);
        cpu.F.value ^= RegF.HALF_CARRY_FLAG | RegF.N_FLAG | RegF.CARRY_FLAG; // invert HNC
        return result;
    }

    public int and(final int a, final int b) {
        int result = a & b;
        cpu.F.value = RegF.HALF_CARRY_FLAG | SZP_FLAGS[result];
        return result;
    }

    public int xor(final int a, final int b) {
        int result = a ^ b;
        cpu.F.value = SZP_FLAGS[result];
        return result;
    }

    public int or(final int a, final int b) {
        int result = a | b;
        cpu.F.value = SZP_FLAGS[result];
        return result;
    }

    public void cp(final int a, final int b) {
        final int result = adc8(a, ~b & 0xff, true);
        cpu.F.value ^= RegF.HALF_CARRY_FLAG | RegF.N_FLAG | RegF.CARRY_FLAG;
        cpu.F.set3Bit((b & BIT_3) != 0);
        cpu.F.set5Bit((b & BIT_5) != 0);
    }

    public void ldi() {
        final int n = memory.peek8(cpu.HL);
        cpu.HL.inc();
        memory.poke8(cpu.HL, n);
        cpu.DE.inc();
        cpu.BC.dec();
        int an = (n + cpu.A.value) & 0xff;
        cpu.F.set5Bit((an & (1 << 1)) != 0);
        cpu.F.setHalfCarry(false);
        cpu.F.set3Bit((an & (1 << 3)) != 0);
        cpu.F.setN(false);
        cpu.F.setParityOverflow(!cpu.BC.isZero());
    }

    public void ldd() {
        final int n = memory.peek8(cpu.HL);
        cpu.HL.dec();
        memory.poke8(cpu.HL, n);
        cpu.DE.dec();
        cpu.BC.dec();
        int an = (n + cpu.A.value) & 0xff;
        cpu.F.set5Bit((an & (1 << 1)) != 0);
        cpu.F.setHalfCarry(false);
        cpu.F.set3Bit((an & (1 << 3)) != 0);
        cpu.F.setN(false);
        cpu.F.setParityOverflow(!cpu.BC.isZero());
    }

    public void cpi() {
        final boolean c = cpu.F.isCarrySet();
        final int a = cpu.A.value;
        final int b = memory.peek8(cpu.HL);
        cpu.HL.inc();
        int n = sub8(a, b);
        n -= cpu.F.isHalfCarrySet() ? 1 : 0; // Use HF set by sub8
        cpu.F.set5Bit((n & (1 << 1)) != 0);
        cpu.F.set3Bit((n & (1 << 3)) != 0);
        cpu.BC.dec();
        cpu.F.setParityOverflow(!cpu.BC.isZero());
        cpu.F.setCarry(c);
        cpu.WZ.inc();
    }

    public void cpd() {
        final boolean c = cpu.F.isCarrySet();
        final int a = cpu.A.value;
        final int b = memory.peek8(cpu.HL);
        cpu.HL.dec();
        int n = sub8(a, b);
        n -= cpu.F.isHalfCarrySet() ? 1 : 0; // Use HF set by sub8
        cpu.F.set5Bit((n & (1 << 1)) != 0);
        cpu.F.set3Bit((n & (1 << 3)) != 0);
        cpu.BC.dec();
        cpu.F.setParityOverflow(!cpu.BC.isZero());
        cpu.F.setCarry(c);
        cpu.WZ.dec();
    }

    public void neg() {
        cpu.A.setValue(sub8(0, cpu.A.value));
    }

    public void rrd() {
        final int a = cpu.A.value;
        final int n = memory.peek8(cpu.HL);
        final int res = ((a & 0xf0) | (n & 0x0f));
        cpu.A.value = res;
        memory.poke8(cpu.HL, (a << 4 | n >>> 4));
        cpu.WZ.value = cpu.HL.getValue() + 1;
        cpu.F.value = (cpu.F.value & RegF.CARRY_FLAG) | SZP_FLAGS[res];
    }

    public void rld() {
        final int a = cpu.A.value;
        final int n = memory.peek8(cpu.HL);
        final int res = ((a & 0xf0) | n >>> 4);
        cpu.A.value = res;
        memory.poke8(cpu.HL, (n << 4 | (a & 0x0f)));
        cpu.WZ.value = cpu.HL.getValue() + 1;
        cpu.F.value = (cpu.F.value & RegF.CARRY_FLAG) | SZP_FLAGS[res];
    }

    public void ini() throws IOException {
        cpu.WZ.value = cpu.BC.getValue() + 1;
        final int n = portIO.read(cpu.BC.getValue()); // use BC before decrementing B
        cpu.B.dec();
        final int b = cpu.B.value;
        final int c = cpu.C.value;
        memory.poke8(cpu.HL, n);
        cpu.HL.inc();
        cpu.F.value = (SZP_FLAGS[b] & (RegF.SIGN_FLAG | RegF.ZERO_FLAG | RegF.BIT_5 | RegF.BIT_3)) | ((n >>> (7 - 1))
                & RegF.N_FLAG);
        if (n + ((c + 1) & 0xff) > 0xff) {
            cpu.F.value |= RegF.HALF_CARRY_FLAG | RegF.CARRY_FLAG;
        }
        cpu.F.value |= SZP_FLAGS[((n + ((c + 1) & 0xff)) & 0x07) ^ b] & RegF.P_V_FLAG;
    }

    public void ind() throws IOException {
        cpu.WZ.value = cpu.BC.getValue() - 1;
        final int n = portIO.read(cpu.BC.getValue()); // use BC before decrementing B
        cpu.B.dec();
        final int b = cpu.B.value;
        final int c = cpu.C.value;
        memory.poke8(cpu.HL, n);
        cpu.HL.dec();
        cpu.F.value = (SZP_FLAGS[b] & (RegF.SIGN_FLAG | RegF.ZERO_FLAG | RegF.BIT_5 | RegF.BIT_3)) | ((n >>> (7 - 1))
                & RegF.N_FLAG);

        if (n + ((c - 1) & 0xff) > 0xff) {
            cpu.F.value |= RegF.HALF_CARRY_FLAG | RegF.CARRY_FLAG;
        }
        cpu.F.value |= SZP_FLAGS[((n + ((c - 1) & 0xff)) & 0x07) ^ b] & RegF.P_V_FLAG;
    }

    public void outi() throws IOException {
        final int n = memory.peek8(cpu.HL);
        cpu.HL.inc();
        cpu.B.dec();
        final int b = cpu.B.value;
        portIO.write(cpu.BC.getValue(), n); // use BC after decrementing B
        final int l = cpu.L.value;

        cpu.F.value = (SZP_FLAGS[b] & (RegF.SIGN_FLAG | RegF.ZERO_FLAG | RegF.BIT_5 | RegF.BIT_3)) |
                ((n >>> (7 - 1)) & RegF.N_FLAG);
        if ((n + l) > 0xff) {
            cpu.F.value |= RegF.HALF_CARRY_FLAG | RegF.CARRY_FLAG;
        }
        cpu.F.value |= SZP_FLAGS[((n + l) & 0x07) ^ b] & RegF.P_V_FLAG;
        cpu.WZ.value = cpu.BC.getValue() + 1;
    }

    public void outd() throws IOException {
        final int n = memory.peek8(cpu.HL);
        cpu.HL.dec();
        cpu.B.dec();
        final int b = cpu.B.value;
        portIO.write(cpu.BC.getValue(), n); // use BC after decrementing B
        final int l = cpu.L.value;

        cpu.F.value = (SZP_FLAGS[b] & (RegF.SIGN_FLAG | RegF.ZERO_FLAG | RegF.BIT_5 | RegF.BIT_3)) |
                ((n >>> (7 - 1)) & RegF.N_FLAG);
        if ((n + l) > 0xff) {
            cpu.F.value |= RegF.HALF_CARRY_FLAG | RegF.CARRY_FLAG;
        }
        cpu.F.value |= SZP_FLAGS[((n + l) & 0x07) ^ b] & RegF.P_V_FLAG;
        cpu.WZ.value = cpu.BC.getValue() - 1;
    }
}
