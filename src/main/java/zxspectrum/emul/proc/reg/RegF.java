package zxspectrum.emul.proc.reg;

/**
 * RegF.
 *
 * @author Maxim Gorin
 */
public class RegF extends Reg8 {

  private static final int BIT7 = 0b1000_0000;

  private static final int BIT7MASK = 0b0111_1111;

  private static final int BIT6 = 0b0100_0000;

  private static final int BIT6MASK = 0b1011_1111;

  private static final int BIT4 = 0b0001_0000;

  private static final int BIT4MASK = 0b1110_1111;

  private static final int BIT2 = 0b0000_0100;

  private static final int BIT2MASK = 0b1111_1011;

  private static final int BIT1 = 0b0000_0010;

  private static final int BIT1MASK = 0b1111_1101;

  private static final int BIT0 = 0b0000_0001;

  private static final int BIT0MASK = 0b1111_1110;


  public void setSign(final boolean sign) {
    if (sign) {
      value |= BIT7;
    } else {
      value &= BIT7MASK;
    }
  }

  public boolean isSign() {
    return (value & BIT7) != 0;
  }


  public void setZero(final boolean zero) {
    if (zero) {
      value |= BIT6;
    } else {
      value &= BIT6MASK;
    }
  }

  public boolean isZero() {
    return (value & BIT6) != 0;
  }

  public void setHalfCarry(final boolean halfCarry) {
    if (halfCarry) {
      value |= BIT4;
    } else {
      value &= BIT4MASK;
    }
  }

  public boolean isHalfCarry() {
    return (value & BIT4) != 0;
  }

  public void setParityOverflow(final boolean po) {
    if (po) {
      value |= BIT2;
    } else {
      value &= BIT2MASK;
    }
  }

  public boolean isParityOverflow() {
    return (value & BIT2) != 0;
  }

  public void setAddSubstract(boolean n) {
    if (n) {
      value |= BIT1;
    } else {
      value &= BIT1MASK;
    }
  }

  public boolean isAddSubstract() {
    return (value & BIT1) != 0;
  }

  public void setCarry(final boolean carry) {
    if (carry) {
      value |= BIT0;
    } else {
      value &= BIT0MASK;
    }
  }

  public boolean isCarry() {
    return (value & BIT0) != 0;
  }
}
