package zxspectrum.emul.io.mem.impl;

import lombok.NonNull;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.cpu.reg.RegSP;

/**
 * MemoryDummy.
 *
 * @author Maxim Gorin
 */
public class MemoryDummy implements MemoryControl {

  @Override
  public void reset() {

  }

  @Override
  public void pop(@NonNull Reg16 r) {

  }

  @Override
  public void push(@NonNull Reg16 r) {

  }

  @Override
  public void peek(@NonNull Reg16 addr, @NonNull Reg8 r) {

  }

  @Override
  public void peek(@NonNull Reg16 addr, @NonNull Reg16 r) {

  }

  @Override
  public void peek(int addr, @NonNull Reg8 r) {

  }

  @Override
  public void peek(int addr, @NonNull Reg16 r) {

  }

  @Override
  public int peek8(int address) {
    return 0;
  }

  @Override
  public int peek8(@NonNull Reg16 addr) {
    return 0;
  }

  @Override
  public int peek16(int address) {
    return 0;
  }

  @Override
  public int peek16(@NonNull Reg16 addr) {
    return 0;
  }

  @Override
  public void poke8(@NonNull Reg16 addr, int val) {

  }

  @Override
  public void poke(@NonNull Reg16 addr, @NonNull Reg8 r) {

  }

  @Override
  public void poke16(@NonNull Reg16 addr, int val) {

  }

  @Override
  public void poke(@NonNull Reg16 addr, @NonNull Reg16 r) {

  }

  @Override
  public void poke(int address, @NonNull Reg8 r) {

  }

  @Override
  public void poke(int address, @NonNull Reg16 r) {

  }

  @Override
  public void setPageMapping(int pageMapping) {

  }

  @Override
  public void setSP(@NonNull RegSP sp) {

  }

  @Override
  public Buffer getVideoBuffer() {
    Buffer buffer = new Buffer();
    return buffer;
  }
}
