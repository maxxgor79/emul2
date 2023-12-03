package zxspectrum.emul.cpu;

import lombok.NonNull;
import zxspectrum.emul.cpu.impl.Z80;

/**
 * CpuFactory.
 *
 * @author Maxim Gorin
 */
public final class CpuFactory {

    private CpuFactory() {
    }

    public static Cpu getInstance(@NonNull final CpuType type) {
        return switch (type) {
            case T34VM1, Z80, Z80A, Z80B, Z80H, KM1858VM1, KM1858VM3, Default -> new Z80();
        };
    }
}
