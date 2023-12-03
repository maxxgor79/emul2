package zxspectrum.emul.io.sound;

import lombok.NonNull;
import zxspectrum.emul.io.sound.impl.AY8910;
import zxspectrum.emul.io.sound.impl.AY8912;
import zxspectrum.emul.io.sound.impl.NoSoundChip;

/**
 * SoundChipFactory.
 *
 * @author Maxim Gorin
 */
public final class SoundChipFactory {
    private SoundChipFactory() {
    }

    public static SoundChip getInstance(@NonNull final SoundChipType type) {
        return switch (type) {
            case None -> new NoSoundChip();
            case AY8910 -> new AY8910();
            case AY8912 -> new AY8912();
        };
    }
}

