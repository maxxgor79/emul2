package zxspectrum.emul.cpu.reg;

import lombok.NonNull;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.io.mem.MemoryControl;

public class ArithmeticProcessor extends Processor implements Const {

  private final Cpu cpu;

  private final MemoryControl memory;

  public ArithmeticProcessor(@NonNull final Cpu cpu) {
    this.cpu = cpu;
    this.memory = cpu.getMemory();
  }

  public int inc8(@NonNull final Reg8 r) {
    r.value = ++r.value & 0xFF;
    cpu.F.value = SZ53N_ADD_TABLE[r.value];

    if ((r.value & 0x0F) == 0x00) {
      cpu.F.setHalfCarry(true);
    }
    if (r.value == BIT_7) {
      cpu.F.setParityOverflow(true);
    }
    return r.value;
  }

  public int dec8(@NonNull final Reg8 r) {
    r.value = (--r.value & 0xFF);
    cpu.F.value = SZ53N_SUB_TABLE[r.value];
    if ((r.value & 0x0F) == 0x0F) {
      cpu.F.setHalfCarry(true);
    }
    if (r.value == 0x7F) {
      cpu.F.setParityOverflow(true);
    }
    return r.value;
  }

  public void sub(@NonNull final Reg8 r) {
    sub(r.value);
  }

  public void sub(final int value) {
    int res = cpu.A.value - value;
    final boolean carryFlag = ((res & 0x100) != 0x00);
    res &= 0xFF;
    cpu.F.value = SZ53N_SUB_TABLE[res];
    if ((res & 0x0F) > (cpu.A.value & 0x0F)) {
      cpu.F.setHalfCarry(true);
    }
    if (((cpu.A.value ^ value) & (cpu.A.value ^ res)) > 0x7F) {
      cpu.F.setParityOverflow(true);
    }
    cpu.A.value = res;
    cpu.F.setCarry(carryFlag);
  }

  public void sbc(final int value) {
    final int carry = cpu.F.isCarry() ? 1 : 0;
    int res = cpu.A.value - value - carry;
    final boolean carryFlag = ((res & 0x100) != 0x00);
    res &= 0xFF;
    cpu.F.value = SZ53N_SUB_TABLE[res];
    if (((cpu.A.value & 0x0F) - (value & 0x0F) - carry & BIT_4) != 0x00) {
      cpu.F.setHalfCarry(true);
    }
    if (((cpu.A.value ^ value) & (cpu.A.value ^ res)) > 0x7F) {
      cpu.F.setParityOverflow(true);
    }
    cpu.A.value = res;
    cpu.F.setCarry(carryFlag);
  }

  public void sbc(@NonNull final Reg8 r) {
    sbc(r.value);
  }

  public void sbc16(@NonNull final Reg16 r) {
    final int carry = cpu.F.isCarry() ? 1 : 0;
    final int regHL = cpu.HL.getValue();
    final int value = r.getValue();
    int res = regHL - value - carry;
    final boolean carryFlag = ((res & 0x10000) != 0x00);
    res &= 0xFFFF;
    cpu.HL.setValue(res);
    cpu.F.value = SZ53N_SUB_TABLE[cpu.H.value];
    if (res != 0) {
      cpu.F.value &= 0xFFFFFFBF;
    }
    if (((regHL & 0xFFF) - (value & 0xFFF) - carry & 0x1000) != 0x00) {
      cpu.F.setHalfCarry(true);
    }
    if (((regHL ^ value) & (regHL ^ res)) > 0x7FFF) {
      cpu.F.setParityOverflow(true);
    }
    cpu.F.setCarry(carryFlag);
  }

  public void add(@NonNull final Reg8 r) {
    add(r.value);
  }

  public void add(final int value) {
    int res = cpu.A.value + value;
    final boolean carryFlag = (res > 0xFF);
    res &= 0xFF;
    cpu.F.value = SZ53N_ADD_TABLE[res];
    if ((res & 0x0F) < (cpu.A.value & 0x0F)) {
      cpu.F.setHalfCarry(true);
    }
    if (((cpu.A.value ^ ~value) & (cpu.A.value ^ res)) > 0x7F) {
      cpu.F.setParityOverflow(true);
    }
    cpu.A.value = res;
    cpu.F.setCarry(carryFlag);
  }

  public void adc(final int value) {
    final int carry = cpu.F.isCarry() ? 1 : 0;
    int res = cpu.A.value + value + carry;
    final boolean carryFlag = (res > 0xFF);
    res &= 0xFF;
    cpu.F.value = SZ53N_ADD_TABLE[res];
    if ((cpu.A.value & 0x0F) + (value & 0x0F) + carry > 0x0F) {
      cpu.F.setHalfCarry(true);
    }
    if (((cpu.A.value ^ ~value) & (cpu.A.value ^ res)) > 0x7F) {
      cpu.F.setParityOverflow(true);
    }
    cpu.A.value = res;
    cpu.F.setCarry(carryFlag);
  }

  public void adc(@NonNull final Reg8 r) {
    adc(r.value);
  }

  public void adc16(@NonNull final Reg16 r) {
    final int carry = cpu.F.isCarry() ? 1 : 0;
    final int regHL = cpu.HL.getValue();
    final int value = r.getValue();
    int res = regHL + value + carry;
    final boolean carryFlag = (res > 0xFFFF);
    res &= 0xFFFF;
    cpu.HL.setValue(res);
    cpu.F.value = SZ53PN_ADD_TABLE[cpu.H.value];
    if (res != 0) {
      cpu.F.value &= 0xFFFFFFBF;
    }
    if ((regHL & 0xFFF) + (value & 0xFFF) + carry > 4095) {
      cpu.F.setHalfCarry(true);
    }
    if (((regHL ^ ~value) & (regHL ^ res)) > 0x7FFF) {
      cpu.F.setParityOverflow(true);
    }
    cpu.F.setCarry(carryFlag);
  }

  public void daa() {
    int summa = 0;
    boolean carry = cpu.F.isCarry();
    if (cpu.F.isHalfCarry() || (cpu.A.value & 0x0F) > 0x09) {
      summa = 0x06;
    }
    if (carry || cpu.A.value > 153) {
      summa |= 0x60;
    }
    if (cpu.A.value > 153) {
      carry = true;
    }
    if (cpu.F.isN()) {
      this.sub(summa);
      cpu.F.value = ((cpu.F.value & RegF.HALF_CARRY_FLAG) | SZ53PN_SUB_TABLE[cpu.A.value]);
    } else {
      this.add(summa);
      cpu.F.value = ((cpu.F.value & RegF.HALF_CARRY_FLAG) | SZ53PN_SUB_TABLE[cpu.A.value]);
    }
    cpu.F.setCarry(carry);
  }

  public void cp(@NonNull final Reg8 r) {
    cp(r.value);
  }

  public void cp(final int value) {
    int res = cpu.A.value - value;
    final boolean carryFlag = ((res & 0x100) != 0x00);
    res &= 0xFF;
    cpu.F.value = ((SZ53N_ADD_TABLE[value] & 0x28) | (SZ53N_SUB_TABLE[res] & 0xD2));
    if ((res & 0x0F) > (cpu.A.value & 0x0F)) {
      cpu.F.setHalfCarry(true);
    }
    if (((cpu.A.value ^ value) & (cpu.A.value ^ res)) > 0x7F) {
      cpu.F.setParityOverflow(true);
    }
    cpu.F.setCarry(carryFlag);
  }

  public void cpi() {
    int memHL = this.memory.peek8(cpu.HL);
    final boolean carry = this.cpu.F.isCarry();
    this.cp(memHL);
    //this.MemIoImpl.contendedStates(regHL, 5);
    cpu.F.setCarry(carry);
    cpu.HL.inc();
    cpu.BC.dec();
    memHL = cpu.A.value - memHL - (cpu.F.isHalfCarry() ? 1 : 0);
    cpu.F.value = ((cpu.F.value & 0xD2) | (memHL & BIT_3));
    if ((memHL & BIT_1) != 0x00) {
      cpu.F.set5Bit(true);
    }
    if (!cpu.BC.isZero()) {
      cpu.F.setParityOverflow(true);
    }
    cpu.MEM_PTR.inc();
  }

  public void cpd() {
    int memHL = this.memory.peek8(cpu.HL);
    final boolean carry = cpu.F.isCarry();
    this.cp(memHL);
    //this.MemIoImpl.contendedStates(regHL, 5);
    cpu.F.setCarry(carry);
    cpu.HL.dec();
    cpu.BC.dec();
    memHL = cpu.A.value - memHL - (cpu.F.isHalfCarry() ? 1 : 0);
    cpu.F.value = ((cpu.F.value & 0xD2) | (memHL & BIT_3));
    if ((memHL & BIT_1) != 0x00) {
      cpu.F.set5Bit(true);
    }
    if (!cpu.BC.isZero()) {
      cpu.F.setParityOverflow(true);
    }
    cpu.MEM_PTR.dec();
  }
}
