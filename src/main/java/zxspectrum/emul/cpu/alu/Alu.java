package zxspectrum.emul.cpu.alu;

import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.mem.address.Address;
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

    int dec(Reg8 r);

    int srl(Reg8 r);

    int sra(Reg8 r);

    int rr(Reg8 r);

    int rrc(Reg8 r);

    int sll(Reg8 r);

    int sla(Reg8 r);

    int rl(Reg8 r);

    int rlc(Reg8 r);

    int rlc(Address address);

    int rld();

    int rrd();

    int neg();

    void cp(Reg8 r);

    void cp(int n);

    int or(Reg8 r);

    int xor(Reg8 r);

    int and(Reg8 r);

    int sbc(Reg8 r);

    int sbc(int n);

    int sbc(Reg16 r1, Reg16 r2);

    int sub(Reg8 r);

    int sub8(int a, int b);

    int adc(Reg8 r);

    int adc(int n);

    int adc(Reg16 r1, Reg16 r2);

    int add(Reg16 r1, Reg16 r2);

    int add(Reg8 r);

    int add(int n);

    int add(Address address);

    void bit(final int mask, Reg8 r);

    void bit(final int mask, Address address);

    int res(final int mask, Reg8 r);

    int set(final int mask, Reg8 r);

    int set(final int mask, Address address);

    void cpd();

    void cpi();

    void setMemory(MemoryControl memory);
}
