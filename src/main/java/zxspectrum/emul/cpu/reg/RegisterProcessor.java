package zxspectrum.emul.cpu.reg;

import lombok.NonNull;
import zxspectrum.emul.cpu.Cpu;

public class RegisterProcessor extends Processor implements Const {

  private final Cpu cpu;

  public RegisterProcessor(@NonNull final Cpu cpu) {
    this.cpu = cpu;
  }

  public void bit(final int mask, @NonNull final Reg8 r) {
    final boolean zeroFlag = (mask & r.value) == 0x00;
    cpu.F.value = ((SZ53N_ADD_TABLE[r.value] & 0xFFFFFF3B) | RegF.HALF_CARRY_FLAG);
    if (zeroFlag) {
      cpu.F.value |= RegF.P_V_FLAG | BIT_3 | BIT_5;
    } else {
      if (mask == BIT_7) {
        cpu.F.setSign(true);
      }
    }
  }

  public int res(final int mask, @NonNull final Reg8 r) {
    return r.value & ~mask;
  }

  public int set(final int mask, @NonNull final Reg8 r) {
    return r.value | mask;
  }

  public void exx() {
    int tmp = cpu.altB.value;
    cpu.altB.value = cpu.B.value;
    cpu.B.value = tmp;

    tmp = cpu.altC.value;
    cpu.altC.value = cpu.C.value;
    cpu.C.value = tmp;

    tmp = cpu.altD.value;
    cpu.altD.value = cpu.D.value;
    cpu.D.value = tmp;

    tmp = cpu.altE.value;
    cpu.altE.value = cpu.E.value;
    cpu.E.value = tmp;

    tmp = cpu.altH.value;
    cpu.altH.value = cpu.H.value;
    cpu.H.value = tmp;

    tmp = cpu.altL.value;
    cpu.altL.value = cpu.L.value;
    cpu.L.value = tmp;
  }
}
