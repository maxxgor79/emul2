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

    public void inc(int d) {
        value += d;
    }

    @Override
    public void reset() {
        value = 0;
    }
}
