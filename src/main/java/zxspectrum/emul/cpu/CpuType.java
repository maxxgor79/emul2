package zxspectrum.emul.cpu;

import lombok.Getter;
import lombok.NonNull;

public enum CpuType {
    Z80("Z80", 2_500_000), Z80A("Z80A", 3_546_900), Z80B("Z80B", 6_000_000)
    , Z80H("Z80H", 8_000_000), T34VM1("Т34ВМ1", 3_546_900)
    , KM1858VM1("КМ1858ВМ1", 3_546_900), KM1858VM3("КМ1858ВМ1", 3_546_900),
    Default("Z80A", 3_546_900);

    CpuType(@NonNull String name, double clockRate) {
        this.name = name;
        this.clockRate = clockRate;
    }

    @Getter
    private final String name;

    @Getter
    private final double clockRate;

    public static CpuType getByName(@NonNull String name) {
        for (CpuType model : values()) {
            if (model.name.equalsIgnoreCase(name)) {
                return model;
            }
        }
        return null;
    }
}
