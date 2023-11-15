package zxspectrum.emul.io.mem.address;

import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.io.mem.MemoryAccess;

public interface Address {
    Address peek(Reg8 r);

    Address peek(Reg16 r);

    Address poke(Reg8 r);

    Address poke(Reg16 r);

    Address setAddress(int value);

    int getAddress();

    void setMemory(MemoryAccess memory);
}
