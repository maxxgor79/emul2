package zxspectrum.emul.cpu;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import zxspectrum.emul.Resettable;

@EqualsAndHashCode
public class Counter implements Resettable {
    @Setter
    @Getter
    private int value;

    public int inc(int d) {
        return value += d;
    }

    public int dec() {
        return --value;
    }

    @Override
    public void reset() {
        value = 0;
    }

    public boolean isZero() {
        return value == 0;
    }
}
