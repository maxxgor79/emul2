package zxspectrum.emul.cpu.decoder;

import zxspectrum.emul.MemorySetter;

public interface IDecoder extends MemorySetter {
    int fetch8();

    int fetch16();

    void execute();
}
