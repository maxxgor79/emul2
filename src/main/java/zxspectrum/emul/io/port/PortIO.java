package zxspectrum.emul.io.port;

import zxspectrum.emul.Resettable;

import java.io.IOException;

public interface PortIO extends Resettable {
    int read(int port);

    void write(int port, int value);
}
