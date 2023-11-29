package zxspectrum.emul.chipset;

import lombok.NonNull;
import zxspectrum.emul.chipset.impl.UlaStd;

/**
 * ChipsetFactory.
 *
 * @author Maxim Gorin
 */
public final class UlaFactory {

    private UlaFactory() {

    }

    public static Ula getInstance(@NonNull UlaType type) {
        return switch (type) {
            case Default, UlaStd -> new UlaStd();
        };
    }
}
