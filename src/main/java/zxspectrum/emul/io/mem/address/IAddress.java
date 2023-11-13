package zxspectrum.emul.io.mem.address;

import org.checkerframework.checker.units.qual.A;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;

public interface IAddress extends Address {
    IAddress setOffset(int offset);

    IAddress peek(Reg8 r);

    IAddress peek(Reg16 r);

    IAddress poke(Reg8 r);

    IAddress poke(Reg16 r);

    IAddress setAddress(int value);

    int getOffset();
}
