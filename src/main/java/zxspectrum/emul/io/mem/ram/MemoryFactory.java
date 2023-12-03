package zxspectrum.emul.io.mem.ram;

import lombok.NonNull;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.mem.ram.impl.Memory128K;
import zxspectrum.emul.io.mem.ram.impl.Memory16K;
import zxspectrum.emul.io.mem.ram.impl.Memory48K;
import zxspectrum.emul.io.mem.ram.impl.MemoryPlus2;
import zxspectrum.emul.io.mem.ram.impl.MemoryPlus2A;
import zxspectrum.emul.io.mem.ram.impl.MemoryPlus3;
import zxspectrum.emul.profile.ZxProfile;

/**
 * Factory.
 *
 * @author Maxim Gorin
 */
public final class MemoryFactory {
    private MemoryFactory() {
    }

    public static MemoryControl getInstance(@NonNull final ZxProfile profile) {
        return getInstance(profile.getRamType());
    }

    public static MemoryControl getInstance(@NonNull final RamType type) {
        return switch (type) {
            case Ram16k -> new Memory16K();
            case Ram48k, Default -> new Memory48K();
            case Ram128k -> new Memory128K();
            case RamPlus2 -> new MemoryPlus2();
            case RamPlus2A -> new MemoryPlus2A();
            case RamPlus3 -> new MemoryPlus3();
        };
    }
}
