package zxspectrum.emul.cpu.unit;

import zxspectrum.emul.io.mem.MemoryControl;

public interface CpuControl {
    void ei();

    void di();

    void im0();

    void im1();

    void im2();

    void halt();

    int cpl();

    int ccf();

    int scf();

    void nop();

    void setMemory(MemoryControl memory);
}
