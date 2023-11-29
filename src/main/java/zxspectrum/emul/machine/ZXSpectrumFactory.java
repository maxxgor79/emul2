package zxspectrum.emul.machine;

import lombok.NonNull;
import zxspectrum.emul.profile.ZxProfile;

/**
 * ZXSpectrumFactory.
 *
 * @author Maxim Gorin
 */
public final class ZXSpectrumFactory {

    private ZXSpectrumFactory() {

    }

    public static ZXSpectrum getInstance(@NonNull final ZxProfile profile) {
        return switch (profile.getRamType()) {
            case Ram16k -> new ZXSpectrum16K(profile);
            case Ram48k, Default -> new ZXSpectrum48K(profile);
            case Ram128k -> new ZXSpectrum128K(profile);
            case RamPlus2 -> new ZXSpectrumPlus2(profile);
            case RamPlus2A -> new ZXSpectrumPlus2A(profile);
            case RamPlus3 -> new ZXSpectrumPlus3(profile);
        };
    }
}
