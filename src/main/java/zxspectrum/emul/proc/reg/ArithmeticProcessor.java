package zxspectrum.emul.proc.reg;

import lombok.NonNull;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.proc.Z80;

public class ArithmeticProcessor implements Const {
    private final Z80 z80;

    private final MemoryControl memory;

    public ArithmeticProcessor(@NonNull Z80 z80) {
        this.z80 = z80;
        this.memory = z80.getMemory();
    }

    public int inc8(@NonNull Reg8 r) {
        r.value = ++r.value & 0xFF;
        z80.F.value = RegF.SZ53N_ADD_TABLE[r.value];

        if ((r.value & 0x0F) == 0x00) {
            z80.F.value |= RegF.HALF_CARRY_FLAG;
        }
        if (r.value == 0x80) {
            z80.F.value |= RegF.P_V_FLAG;
        }
        return r.value;
    }

    public int dec8(@NonNull Reg8 r) {
        r.value = (--r.value & 0xFF);
        z80.F.value = RegF.SZ53N_SUB_TABLE[r.value];
        if ((r.value & 0x0F) == 0x0F) {
            z80.F.value |= RegF.HALF_CARRY_FLAG;
        }
        if (r.value == 0x7F) {
            z80.F.value |= RegF.P_V_FLAG;
        }
        return r.value;
    }

    public void sub(@NonNull Reg8 r) {
        sub(r.value);
    }

    public void sub(final int value) {
        int res = z80.A.value - value;
        final boolean carryFlag = ((res & 0x100) != 0x00);
        res &= 0xFF;
        z80.F.value = RegF.SZ53N_SUB_TABLE[res];
        if ((res & 0x0F) > (z80.A.value & 0x0F)) {
            z80.F.value |= RegF.HALF_CARRY_FLAG;
        }
        if (((z80.A.value ^ value) & (z80.A.value ^ res)) > 127) {
            z80.F.value |= RegF.P_V_FLAG;
        }
        z80.A.value = res;
        z80.F.setCarry(carryFlag);
    }

    public void sbc(final int value) {
        final int carry = z80.F.isCarry() ? 1 : 0;
        int res = z80.A.value - value - carry;
        final boolean carryFlag = ((res & 0x100) != 0x00);
        res &= 0xFF;
        z80.F.value = RegF.SZ53N_SUB_TABLE[res];
        if (((z80.A.value & 0x0F) - (value & 0x0F) - carry & 0x10) != 0x00) {
            z80.F.value |= RegF.HALF_CARRY_FLAG;
        }
        if (((z80.A.value ^ value) & (z80.A.value ^ res)) > 127) {
            z80.F.value |= RegF.P_V_FLAG;
        }
        z80.A.value = res;
        z80.F.setCarry(carryFlag);
    }

    public void sbc(@NonNull Reg8 r) {
        sbc(r.value);
    }

    public void add(@NonNull Reg8 r) {
        add(r.value);
    }

    public void add(final int value) {
        int res = z80.A.value + value;
        final boolean carryFlag = (res > 255);
        res &= 0xFF;
        z80.F.value = RegF.SZ53N_ADD_TABLE[res];
        if ((res & 0x0F) < (z80.A.value & 0x0F)) {
            z80.F.value |= RegF.HALF_CARRY_FLAG;
        }
        if (((z80.A.value ^ ~value) & (z80.A.value ^ res)) > 127) {
            z80.F.value |= RegF.P_V_FLAG;
        }
        z80.A.value = res;
        z80.F.setCarry(carryFlag);
    }

    public void adc(final int value) {
        final int carry = z80.F.isCarry() ? 1 : 0;
        int res = z80.A.value + value + carry;
        final boolean carryFlag = (res > 255);
        res &= 0xFF;
        z80.F.value = RegF.SZ53N_ADD_TABLE[res];
        if ((z80.A.value & 0x0F) + (value & 0xF) + carry > 15) {
            z80.F.value |= RegF.HALF_CARRY_FLAG;
        }
        if (((z80.A.value ^ ~value) & (z80.A.value ^ res)) > 127) {
            z80.F.value |= RegF.P_V_FLAG;
        }
        z80.A.value = res;
        z80.F.setCarry(carryFlag);
    }

    public void adc(@NonNull Reg8 r) {
        adc(r.value);
    }

    public void daa() {
        int summa = 0;
        boolean carry = z80.F.isCarry();
        if ((z80.F.value & RegF.HALF_CARRY_FLAG) != 0x00 || (z80.A.value & 0x0F) > 0x09) {
            summa = 0x06;
        }
        if (carry || z80.A.value > 153) {
            summa |= 0x60;
        }
        if (z80.A.value > 153) {
            carry = true;
        }
        if ((z80.F.value & RegF.N_FLAG) != 0x00) {
            this.sub(summa);
            z80.F.value = ((z80.F.value & RegF.HALF_CARRY_FLAG) | RegF.SZ53PN_SUB_TABLE[z80.A.value]);
        } else {
            this.add(summa);
            z80.F.value = ((z80.F.value & RegF.HALF_CARRY_FLAG) | RegF.SZ53PN_SUB_TABLE[z80.A.value]);
        }
        z80.F.setCarry(carry);
    }


    public void cp(final int value) {
        int res = z80.A.value - value;
        final boolean carryFlag = ((res & 0x100) != 0x00);
        res &= 0xFF;
        z80.F.value = ((RegF.SZ53N_ADD_TABLE[value] & 0x28) | (RegF.SZ53N_SUB_TABLE[res] & 0xD2));
        if ((res & 0x0F) > (z80.A.value & 0x0F)) {
            z80.F.value |= 0x10;
        }
        if (((z80.A.value ^ value) & (z80.A.value ^ res)) > 127) {
            z80.F.value |= 0x04;
        }
        z80.F.setCarry(carryFlag);
    }
}
