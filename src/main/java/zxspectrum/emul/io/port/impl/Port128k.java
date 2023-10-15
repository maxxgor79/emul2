package zxspectrum.emul.io.port.impl;

import zxspectrum.emul.io.port.Port;

import java.io.IOException;

public class Port128k implements Port {
    @Override
    public int read(int port) throws IOException {
        return 0;
    }

    @Override
    public void write(int port, int value) throws IOException {

    }

    @Override
    public void reset() {

    }
}
