package zxspectrum.emul.cpu.decode;

import zxspectrum.emul.io.mem.MemoryAccess;

public interface IDecoder {
    int fetch8();

    int fetch16();

    void execute();

    void setMemory(MemoryAccess memory);
}
