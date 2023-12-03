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
        return switch (profile.getRamType()) {
            case Ram16k -> new Memory16K(profile);
            case Ram48k, Default -> new Memory48K(profile);
            case Ram128k -> new Memory128K(profile);
            case RamPlus2 -> new MemoryPlus2(profile);
            case RamPlus2A -> new MemoryPlus2A(profile);
            case RamPlus3 -> new MemoryPlus3(profile);
        };
    }
}
