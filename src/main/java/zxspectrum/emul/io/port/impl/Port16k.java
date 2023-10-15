package zxspectrum.emul.io.port.impl;

import zxspectrum.emul.io.port.Port;

import java.io.IOException;

public class Port16k implements Port {
    private int value;
    @Override
    public int read(int port) throws IOException {
        port &= 0xFF;
        return value;
    }

    @Override
    public void write(int port, int value) throws IOException {

    }

    @Override
    public void reset() {

    }
}
