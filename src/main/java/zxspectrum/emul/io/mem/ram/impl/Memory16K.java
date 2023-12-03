package zxspectrum.emul.io.mem.ram.impl;

import lombok.NonNull;
import zxspectrum.emul.profile.ZxProfile;

/**
 * Memory16K.
 *
 * @author Maxim Gorin
 */

public class Memory16K extends MemoryNotPaged {
    protected static final int RAM_SIZE = 0x8000;

    public Memory16K(@NonNull final ZxProfile profile) {
        buf = new byte[RAM_SIZE];
        this.lastAddress = RAM_SIZE - 1;
    }

    @Override
    public int getRomCount() {
        return 1;
    }
}
