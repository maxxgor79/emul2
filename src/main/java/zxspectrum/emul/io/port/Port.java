package zxspectrum.emul.io.port;

import zxspectrum.emul.Resettable;

import java.io.IOException;

public interface Port extends Resettable {
    int read(int port) throws IOException;

    void write(int port, int value) throws IOException;
}
