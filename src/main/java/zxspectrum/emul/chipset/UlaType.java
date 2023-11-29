package zxspectrum.emul.chipset;

import lombok.NonNull;
import zxspectrum.emul.io.mem.ram.RamType;

public enum UlaType {
    Default, UlaStd;

    public static UlaType getByName(@NonNull final String name) {
        for (UlaType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
