package zxspectrum.emul.cpu.reg;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import zxspectrum.emul.Resettable;

/**
 * RegF.
 *
 * @author Maxim Gorin
 */

@EqualsAndHashCode
@ToString
public class RegF extends Reg8 implements Const, Resettable {

  public static final int SIGN_FLAG = BIT_7;

  public static final int ZERO_FLAG = BIT_6;

  public static final int HALF_CARRY_FLAG = BIT_4;

  public static final int P_V_FLAG = BIT_2;

  public static final int N_FLAG = BIT_1;

  public static final int CARRY_FLAG = BIT_0;


  public void setSign(final boolean sign) {
    if (sign) {
      value |= SIGN_FLAG;
    } else {
      value &= ~SIGN_FLAG;
    }
  }

  public boolean isSignSet() {
    return (value & SIGN_FLAG) != 0;
  }


  public void setZero(final boolean zero) {
    if (zero) {
      value |= ZERO_FLAG;
    } else {
      value &= ~ZERO_FLAG;
    }
  }

  public boolean isZeroSet() {
    return (value & ZERO_FLAG) != 0;
  }

  public void setHalfCarry(final boolean halfCarry) {
    if (halfCarry) {
      value |= HALF_CARRY_FLAG;
    } else {
      value &= ~HALF_CARRY_FLAG;
    }
  }

  public boolean isHalfCarrySet() {
    return (value & HALF_CARRY_FLAG) != 0;
  }

  public void setParityOverflow(final boolean po) {
    if (po) {
      value |= P_V_FLAG;
    } else {
      value &= ~P_V_FLAG;
    }
  }

  public boolean isParityOverflowSet() {
    return (value & P_V_FLAG) != 0;
  }

  public void setAddSubstract(boolean n) {
    if (n) {
      value |= N_FLAG;
    } else {
      value &= ~N_FLAG;
    }
  }

  public boolean isAddSubstractSet() {
    return (value & N_FLAG) != 0;
  }

  public void setN(boolean n) {
    if (n) {
      value |= N_FLAG;
    } else {
      value &= ~N_FLAG;
    }
  }

  public boolean isNSet() {
    return (value & N_FLAG) != 0;
  }

  public void setCarry(final boolean carry) {
    if (carry) {
      value |= CARRY_FLAG;
    } else {
      value &= ~CARRY_FLAG;
    }
  }

  public boolean isCarrySet() {
    return (value & CARRY_FLAG) != 0;
  }

  public int getIntCarry() {
    return value & CARRY_FLAG;
  }

  public boolean is5BitSet() {
    return (value & BIT_5) != 0;
  }

  public void set5Bit(final boolean on) {
    if (on) {
      value |= BIT_5;
    } else {
      value &= ~BIT_5;
    }
  }

  public boolean is3BitSet() {
    return (value & BIT_3) != 0;
  }

  public void set3Bit(final boolean on) {
    if (on) {
      value |= BIT_3;
    } else {
      value &= ~BIT_3;
    }
  }

  @Override
  public void reset() {
    value = 0;
  }
}
