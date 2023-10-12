package zxspectrum.emul.proc.reg;

import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Reg16.
 *
 * @author Maxim Gorin
 */
@NoArgsConstructor
public abstract class Reg16 {

  protected Reg8 lo;

  protected Reg8 hi;

  public Reg16(@NonNull Reg8 lo, @NonNull Reg8 hi) {
    this.lo = lo;
    this.hi = hi;
  }

  public void setValue(int value) {
    this.hi.value = (value >>> 8) & 0xFF;
    this.lo.value = (value & 0xFF);
  }

  public void setValue(int lo, int hi) {
    this.lo.value = lo & 0xFF;
    this.hi.value = hi & 0xFF;

  }

  public int getValue() {
    return (hi.value << 8) | lo.value;
  }

  public int getLoValue() {
    return this.lo.value;
  }

  public int getHiValue() {
    return this.hi.value;
  }

  public void ld(@NonNull Reg16 r) {
    this.lo.value = r.lo.value;
    this.hi.value = r.hi.value;
  }

  public void inc() {
    ++this.lo.value;
    if (this.lo.value < 0x100) {
      return;
    }
    this.lo.value = 0x00;
    ++this.hi.value;
    if (this.hi.value < 0x100) {
      return;
    }
    this.hi.value = 0x00;
  }

  public void dec() {
    --this.lo.value;
    if (this.lo.value >= 0x00) {
      return;
    }
    this.lo.value = 0xFF;
    --this.hi.value;
    if (this.hi.value >= 0x00) {
      return;
    }
    this.hi.value = 0xFF;
  }

}
