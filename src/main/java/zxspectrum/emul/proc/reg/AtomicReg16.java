package zxspectrum.emul.proc.reg;

import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * AtomicReg16.
 *
 * @author Maxim Gorin
 */
@NoArgsConstructor
public class AtomicReg16 extends Reg16 {

  protected volatile int value;

  protected AtomicReg16(@NonNull Reg8 lo, @NonNull Reg8 hi) {
  }

  @Override
  public void setValue(int value) {
    this.value = value & 0xFFFF;
  }

  @Override
  public void setValue(int lo, int hi) {
    this.value = ((hi & 0xFF) << 8) | (lo & 0xFF);
  }

  @NonNull
  public int getValue() {
    return value;
  }

  @Override
  public int getLoValue() {
    return value & 0xFF;
  }

  @Override
  public int getHiValue() {
    return (value >>> 8) & 0xFF;
  }

  @Override
  public void inc() {
    this.value++;
    this.value &= 0xFFFF;
  }

  @Override
  public void dec() {
    this.value--;
    this.value &= 0xFFFF;
  }

  @Override
  public void ld(@NonNull Reg16 r) {
    this.value = r.getValue();
  }
}
