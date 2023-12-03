package zxspectrum.emul.io.mem.ram.impl;

import lombok.NonNull;
import zxspectrum.emul.io.mem.RomControl;

import java.util.Arrays;

abstract class RomMemory implements RomControl {
    protected byte[][] rom;

    RomMemory() {
        rom = new byte[getRomCount()][];
    }

    @Override
    public void uploadRom(int n, @NonNull final byte[] buf, int offset, int length) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset=" + offset);
        }
        if (length < 0) {
            throw new IllegalArgumentException("length=" + length);
        }
        if (n >= getRomCount()) {
            throw new IndexOutOfBoundsException("n=" + n);
        }
        if ((offset + length) > buf.length) {
            throw new IndexOutOfBoundsException("n=" + n);
        }
        if (length != ROM_SIZE) {
            throw new IllegalArgumentException("length != ROM_SIZE");
        }
        rom[n] = Arrays.copyOfRange(buf, offset, length);
    }

    @Override
    public void upload(@NonNull final byte[]... uploadRom) {
        int romCount = uploadRom.length < getRomCount() ? uploadRom.length : getRomCount();
        for (int i = 0; i < romCount; i++) {
            if (uploadRom[i] == null) {
                throw new NullPointerException("rom[" + i + "]");
            }
            if (uploadRom[i].length != ROM_SIZE) {
                throw new IllegalArgumentException("uploadRom[" + i + "] != ROM_SIZE");
            }
            rom[i] = uploadRom[i];
        }
    }

    @Override
    public abstract int getRomCount();
}
