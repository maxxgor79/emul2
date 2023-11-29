package zxspectrum.emul.io.sound;

import lombok.NonNull;
import zxspectrum.emul.chipset.UlaType;

/**
 * SoundChipModel.
 *
 * @author Maxim Gorin
 */
public enum SoundChipType {
    AY8910, AY8912, None;

    public static SoundChipType getByName(@NonNull final String name) {
        for (SoundChipType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
