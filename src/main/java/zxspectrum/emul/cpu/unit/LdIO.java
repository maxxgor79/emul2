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
import zxspectrum.emul.io.mem.address.AbsouluteAddress;
import zxspectrum.emul.io.mem.address.Address;
import zxspectrum.emul.io.mem.address.IdxAddress;
import zxspectrum.emul.io.mem.address.RpRegisteredAddress;
import zxspectrum.emul.io.port.PortIO;

public interface LdIO {
    void ld(RegI i, RegA a);

    void ld(RegR r, RegA a);

    void ld(RegA a, RegI i);

    void ld(RegA a, RegR r);

    void ld(Reg8 r1, Reg8 r2);

    void ld(Reg8 r, int n);

    void ld(RegA a, AbsouluteAddress address);

    void ld(AbsouluteAddress address, RegA a);

    void ld(RegA a, RpRegisteredAddress address);

    void ld(RpRegisteredAddress address, RegA a);

    void ld(Address AbsouluteAddress, RegBC bc);

    void ld(AbsouluteAddress address, RegDE de);

    void ld(RegBC bc, AbsouluteAddress address);

    void ld(RegDE de, AbsouluteAddress address);

    void ld(Reg8 r, IdxAddress address);

    void ld(IdxAddress address, Reg8 r);

    void ld(IdxAddress address, int n);


    void ld(Address address, Reg16 hl);

    void ld(RegHL hl, Address address);

    void ld(Reg16 r, int n);

    void ld(Reg16 r, Address address);

    void exx();

    void ex(Reg16 r1, Reg16 r2);

    void ex(Address address, Reg16 r);

    void ex(Address address, RegBC bc);

    void ex(Address address, RegDE de);

    void in(Reg8 r, RegBC bc);

    //undocumented
    void in(RegBC bc);

    void in(RegA a, int n);

    void out(RegBC bc, Reg8 r);

    //undocumented
    void out(RegBC bc);

    void out(int n, RegA a);

    void ldd();

    boolean lddr();

    void ldi();

    boolean ldir();

    void outd();

    boolean otdr();

    void outi();

    boolean otir();

    void ind();

    boolean indr();

    void ini();

    boolean inir();

    void setMemory(MemoryAccess memory);

    void setPortIO(PortIO portIO);
}
