package zxspectrum.emul.proc.reg;

/**
 * RegR.
 *
 * @author Maxim Gorin
 */
public class RegR extends Reg8 {

  public void inc() {
    int value7 = (value + 1) & 0x7F;
    value &= 0x80;
    value |= value7;
  }
}
