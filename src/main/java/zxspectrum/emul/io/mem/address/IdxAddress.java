package zxspectrum.emul.io.mem.address;

import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;

public interface IdxAddress extends Address {

    IdxAddress noOffset();

    IdxAddress setOffset(int offset);

    IdxAddress peek(Reg8 r);

    IdxAddress peek(Reg16 r);

    IdxAddress poke(Reg8 r);

    IdxAddress poke(Reg16 r);

    IdxAddress setAddress(int value);

    int getOffset();
}
