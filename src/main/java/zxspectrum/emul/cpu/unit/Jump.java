package zxspectrum.emul.cpu.unit;

import zxspectrum.emul.io.mem.address.Address;

public interface Jump {
    void jp(int address);

    void jp(Address address); //jp(hl), jp(ix), jp(iy);

    boolean jpZ(int address);

    boolean jpNZ(int address);

    boolean jpC(int address);

    boolean jpNC(int address);

    boolean jpP(int address);

    boolean jpM(int address);

    boolean jpPO(int address);

    boolean jpPE(int address);

    boolean djnz(int offset);

    void jr(int offset);

    boolean jrZ(int offset);

    boolean jrNZ(int offset);

    boolean jrC(int offset);

    boolean jrNC(int offset);

    boolean jrP(int offset);

    boolean jrM(int offset);

    boolean jrPO(int offset);

    boolean jrPE(int offset);
}
