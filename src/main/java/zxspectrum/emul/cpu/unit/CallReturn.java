package zxspectrum.emul.cpu.unit;

import zxspectrum.emul.io.mem.MemoryControl;

public interface CallReturn {
    void call(int address);

    boolean callZ(int address);

    boolean callNZ(int address);

    boolean callC(int address);

    boolean callNC(int address);

    boolean callP(int address);

    boolean callM(int address);

    boolean callPE(int address);

    boolean callPO(int address);

    void ret();

    void retI();

    void retN();

    boolean retZ();

    boolean retNZ();

    boolean retC();

    boolean retNC();

    boolean retP();

    boolean retM();

    boolean retPO();

    boolean retPE();

    void rst0();

    void rst8();

    void rst10();

    void rst18();

    void rst20();

    void rst28();

    void rst30();

    void rst38();

    void setMemory(MemoryControl memory);
}
