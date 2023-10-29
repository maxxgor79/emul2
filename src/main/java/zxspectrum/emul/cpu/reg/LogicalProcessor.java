package zxspectrum.emul.cpu.reg;

import lombok.NonNull;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.io.mem.MemoryControl;

public class LogicalProcessor extends Processor implements Const {

  private final Cpu cpu;

  private MemoryControl memory;

  public LogicalProcessor(@NonNull final Cpu cpu) {
    this.cpu = cpu;
    this.memory = cpu.getMemory();
  }

  public int rlc(@NonNull final Reg8 r) {
    final boolean carryFlag = (r.value > 0x7F);
    r.value <<= 1;
    if (carryFlag) {
      r.value |= BIT_0;
    }
    r.value &= 0xFF;
    cpu.F.value = SZ53PN_ADD_TABLE[r.value];
    cpu.F.setCarry(carryFlag);
    return r.value;
  }

  private int rl(@NonNull final Reg8 r) {
    final boolean carry = cpu.F.isCarrySet();
    boolean carryFlag = (r.value > 0x7F);
    r.value <<= 1;
    if (carry) {
      r.value |= BIT_0;
    }
    r.value &= 0xFF;
    cpu.F.value = SZ53PN_ADD_TABLE[r.value];
    cpu.F.setCarry(carryFlag);
    return r.value;
  }

  public int sla(@NonNull final Reg8 r) {
    final boolean carryFlag = r.value > 0x7F;
    r.value <<= 1;
    r.value &= 0xFE;
    cpu.F.value = SZ53PN_ADD_TABLE[r.value];
    cpu.F.setCarry(carryFlag);
    return r.value;
  }

  public int sll(@NonNull final Reg8 r) {
    final boolean carryFlag = (r.value > 0x7F);
    r.value <<= 1;
    r.value |= BIT_0;
    r.value &= 0xFF;
    cpu.F.value = SZ53PN_ADD_TABLE[r.value];
    cpu.F.setCarry(carryFlag);
    return r.value;
  }

  public int rrc(@NonNull final Reg8 r) {
    final boolean carryFlag = ((r.value & BIT_0) != 0x00);
    r.value >>>= 1;
    if (carryFlag) {
      r.value |= BIT_7;
    }
    cpu.F.value = SZ53PN_ADD_TABLE[r.value];
    cpu.F.setCarry(carryFlag);
    return r.value;
  }

  public int rr(@NonNull final Reg8 r) {
    final boolean carry = cpu.F.isCarrySet();
    boolean carryFlag = ((r.value & BIT_0) != 0x00);
    r.value >>>= 1;
    if (carry) {
      r.value |= BIT_7;
    }
    cpu.F.value = SZ53PN_ADD_TABLE[r.value];
    cpu.F.setCarry(carryFlag);
    return r.value;
  }

  public void rrd() {
    final int value = (cpu.A.value & 0x0F) << 4;
    final int memHL = this.memory.peek8(cpu.HL);
    cpu.A.value = ((cpu.A.value & 0xF0) | (memHL & 0x0F));
    //this.MemIoImpl.contendedStates(regHL, 4);
    this.memory.poke8(cpu.HL, memHL >>> 4 | value);
    cpu.F.value = SZ53PN_ADD_TABLE[cpu.A.value];
  }


  public void rld() {
    final int value = cpu.A.value & 0x0F;
    final int memHL = memory.peek8(cpu.HL);
    cpu.A.value = ((cpu.A.value & 0xF0) | memHL >>> 4);
    //this.MemIoImpl.contendedStates(regHL, 4);
    this.memory.poke8(cpu.HL, (memHL << 4 | value) & 0xFF);
    cpu.F.value = SZ53PN_ADD_TABLE[cpu.A.value];
  }

  public int sra(@NonNull final Reg8 r) {
    final int tmp = r.value & BIT_7;
    final boolean carryFlag = ((r.value & BIT_0) != 0x00);
    r.value = (r.value >> 1 | tmp);
    cpu.F.value = SZ53PN_ADD_TABLE[r.value];
    cpu.F.setCarry(carryFlag);
    return r.value;
  }

  public int srl(@NonNull final Reg8 r) {
    final boolean carryFlag = ((r.value & BIT_0) != 0x00);
    r.value >>>= 1;
    cpu.F.value = SZ53PN_ADD_TABLE[r.value];
    cpu.F.setCarry(carryFlag);
    return r.value;
  }

  public void and(@NonNull final Reg8 r) {
    cpu.A.value &= r.value;
    final boolean carryFlag = false;
    cpu.F.value = (SZ53PN_ADD_TABLE[cpu.A.value] | RegF.HALF_CARRY_FLAG);
    cpu.F.setCarry(carryFlag);
  }

  public final void xor(@NonNull final Reg8 r) {
    cpu.A.value ^= r.value;
    final boolean carryFlag = false;
    cpu.F.value = SZ53PN_ADD_TABLE[cpu.A.value];
    cpu.F.setCarry(carryFlag);
  }

  public void or(@NonNull final Reg8 r) {
    cpu.A.value |= r.value;
    final boolean carryFlag = false;
    cpu.F.value = SZ53PN_ADD_TABLE[cpu.A.value];
    cpu.F.setCarry(carryFlag);
  }
}
