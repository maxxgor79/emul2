package zxspectrum.emul.io.mem.ram.impl;

import lombok.NonNull;
import zxspectrum.emul.profile.ZxProfile;

/**
 * Memory48K.
 *
 * @author Maxim Gorin
 */
public class Memory48K extends MemoryNotPaged {
    protected static final int RAM_SIZE = 0x10000;

    public Memory48K() {
        buf = new byte[RAM_SIZE];
        this.lastAddress = RAM_SIZE - 1;
    }

    @Override
    public int getRomCount() {
        return 1;
    }
}
