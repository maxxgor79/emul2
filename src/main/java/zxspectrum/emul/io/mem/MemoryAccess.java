package zxspectrum.emul.io.mem;

import lombok.NonNull;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;

/**
 * Memory.
 *
 * @author Maxim Gorin
 */
public interface MemoryAccess {

  void pop(@NonNull Reg16 r);

  void push(@NonNull Reg16 r);

  void peek(@NonNull Reg16 addr, @NonNull Reg8 r);

  void peek(@NonNull Reg16 addr, @NonNull Reg16 r);

  void peek(int addr, @NonNull Reg8 r);

  void peek(int addr, @NonNull Reg16 r);

  int peek8(int address);

  int peek8(@NonNull Reg16 addr);

  int peek16(int address);

  int peek16(@NonNull Reg16 addr);

  void poke8(@NonNull Reg16 addr, int val);

  void poke(@NonNull Reg16 addr, @NonNull Reg8 r);

  void poke16(@NonNull Reg16 addr, int val);

  void poke(@NonNull Reg16 addr, @NonNull Reg16 r);

  void poke(int address, @NonNull Reg8 r);

  void poke(int address, @NonNull Reg16 r);

  class Buffer {
    public byte[] buf;

    public int offset;

    public int length;
  }
}
