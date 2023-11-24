package zxspectrum.emul.cpu.interruption;

import lombok.Getter;

public class Latch {
    @Getter
    private int counter;

    public void setCounter(int counter) {
        if (counter < 0) {
            throw new IllegalArgumentException("counter is negative");
        }
        this.counter = counter;
    }

    public void countDown() {
        if (counter > 0) {
            counter--;
        }
    }

    public boolean isEnabled() {
        return counter == 0;
    }
}
