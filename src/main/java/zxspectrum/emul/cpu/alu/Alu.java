package zxspectrum.emul.cpu.alu;

import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.cpu.reg.RegA;
import zxspectrum.emul.cpu.reg.RegB;
import zxspectrum.emul.cpu.reg.RegC;
import zxspectrum.emul.cpu.reg.RegD;
import zxspectrum.emul.cpu.reg.RegE;
import zxspectrum.emul.cpu.reg.RegF;
import zxspectrum.emul.cpu.reg.RegH;
import zxspectrum.emul.cpu.reg.RegL;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.mem.address.Address;
import zxspectrum.emul.io.mem.address.IAddress;
import zxspectrum.emul.io.port.PortIO;

public interface Alu {
    int daa();

    int cpl();

    int ccf();

    int scf();

    int inc(Reg16 r);

    int dec(Reg16 r);

    int inc(Reg8 r);

    int inc(Address address);

    int inc(IAddress address);

    int dec(Reg8 r);

    int dec(Address address);

    int srl(Reg8 r);

    int srl(Address address);

    int srl(Reg8 r, IAddress address);//undocumented

    int sra(Reg8 r);

    int sra(Address address);

    //undocumented
    int sra(Reg8 r, IAddress address);

    int rr(Reg8 r);

    int rr(Address address);

    int rr(Reg8 r, IAddress address);//undocumented

    int rrc(Reg8 r);

    int rrc(Address address);

    //undocumented
    int rrc(Reg8 r, IAddress address);


    int sll(Reg8 r);

    //undocumented
    int sll(Reg8 r, Address address);

    //undocumented
    int sll(Reg8 r, IAddress address);

    int sla(Reg8 r);

    int sla(Address address);

    //undocumented
    int sla(Reg8 r, IAddress address);

    //undocumented
    int sl1(Reg8 r);

    //undocumented
    int sl1(Reg8 r, IAddress address);

    int rl(Reg8 r);

    int rl(Address address);

    int rl(Reg8 r, IAddress address); //undocumented

    int rlc(Reg8 r);

    int rlc(Address address);

    int rlc(IAddress address);

    //undocumented
    int rlc(Reg8 r, IAddress address);

    int rld();

    int rrd();

    int neg();

    void cp(Reg8 r);

    void cp(int n);

    void cp(Address address);

    int or(Reg8 r);

    int or(int n);

    int or(Address address);

    int xor(Reg8 r);

    int xor(int n);

    int xor(Address address);

    int and(Reg8 r);

    int and(int n);

    int and(Address address);

    int sbc(Reg8 r);

    int sbc(int n);

    int sbc(Address address);

    int sbc(Reg16 r1, Reg16 r2);

    int sub(Reg8 r);

    int sub(Address address);

    int sub8(int b);

    int adc(Reg8 r);

    int adc(int n);

    int adc(Address address);

    int adc(Reg16 r1, Reg16 r2);

    int add(Reg16 r1, Reg16 r2);

    int add(Reg8 r);

    int add(int n);

    int add(Address address);

    void bit(int mask, Reg8 r);

    void bit(int mask, Address address);

    void bit(int mask, IAddress address);

    int res(int mask, Reg8 r);

    int res(int mask, Address address);

    int res(Reg8 r, int mask, IAddress address);//undocumented

    int set(int mask, Reg8 r);

    int set(int mask, Address address);

    int set(int mask, IAddress address);

    //undocumented
    int set(Reg8 r, int mask, IAddress address);

    void cpd();

    void cpi();

    void setMemory(MemoryControl memory);
}