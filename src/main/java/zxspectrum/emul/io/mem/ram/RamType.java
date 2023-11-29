package zxspectrum.emul.io.mem.ram;

import lombok.NonNull;

public enum RamType {
    Ram16k, Ram48k, Ram128k, RamPlus2, RamPlus2A, RamPlus3, Default;

    public static RamType getByName(@NonNull final String name) {
        for (RamType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}