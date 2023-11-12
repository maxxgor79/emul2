package zxspectrum.emul.io.mem.address;

import org.checkerframework.checker.units.qual.A;

public interface IAddress extends Address {
    IAddress setOffset(int offset);

    int getOffset();
}
