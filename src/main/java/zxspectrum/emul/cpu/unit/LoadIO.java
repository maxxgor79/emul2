package zxspectrum.emul.cpu.unit;

import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.cpu.reg.RegA;
import zxspectrum.emul.cpu.reg.RegBC;
import zxspectrum.emul.cpu.reg.RegDE;
import zxspectrum.emul.cpu.reg.RegHL;
import zxspectrum.emul.cpu.reg.RegI;
import zxspectrum.emul.cpu.reg.RegR;
import zxspectrum.emul.io.mem.MemoryAccess;
import zxspectrum.emul.io.mem.address.Address;
import zxspectrum.emul.io.mem.address.IAddress;
import zxspectrum.emul.io.mem.address.RpAddress;
import zxspectrum.emul.io.port.PortIO;

import java.io.IOException;

public interface LoadIO {
    void ld(RegI i, RegA a);

    void ld(RegR r, RegA a);

    void ld(RegA a, RegI i);

    void ld(RegA a, RegR r);

    void ld(Reg8 r1, Reg8 r2);

    void ld(Reg8 r, int n);

    void ld(RegA a, Address address);

    void ld(Address address, RegA a);

    void ld(RegA a, RpAddress address);

    void ld(RpAddress address, RegA a);

    void ld(Address address, RegBC bc);

    void ld(Address address, RegDE de);

    void ld(RegBC bc, Address address);

    void ld(RegDE de, Address address);

    void ld(Reg8 r, IAddress address);

    void ld(IAddress address, Reg8 r);

    void ld(IAddress address, int n);


    void ld(Address address, Reg16 hl);

    void ld(RegHL hl, Address address);

    void ld(Reg16 r, int n);

    void ld(Reg16 r, Address address);

    void exx();

    void ex(Reg16 r1, Reg16 r2);

    void ex(Address address, Reg16 r);

    void ex(Address address, RegBC bc);

    void ex(Address address, RegDE de);

    void in(Reg8 r, RegBC bc) throws IOException;

    //undocumented
    void in(RegBC bc) throws IOException;

    void in(RegA a, int n) throws IOException;

    void out(RegBC bc, Reg8 r) throws IOException;

    //undocumented
    void out(RegBC bc) throws IOException;

    void out(int n, RegA a) throws IOException;

    void ldd();

    void ldi();

    void outd() throws IOException;

    void outi() throws IOException;

    void ind() throws IOException;

    void ini() throws IOException;

    void setMemory(MemoryAccess memory);

    void setPortIO(PortIO portIO);
}
