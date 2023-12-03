package zxspectrum.emul.chipset.impl;

import lombok.Getter;
import lombok.NonNull;
import zxspectrum.emul.chipset.Ula;
import zxspectrum.emul.chipset.palette.ZxColor;
import zxspectrum.emul.io.mem.MemoryAccess;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.port.PortIO;

/**
 * ULA5C102E.
 *
 * @author Maxim Gorin
 */
public class UlaStd implements Ula {
    @Getter
    private ZxColor borderColor;

    private MemoryAccess memory;

    private PortIO portIO;

    @Getter
    private long clockCounter;

    @Override
    public void reset() {
        clockCounter = 0;
        borderColor = ZxColor.White;
    }

    @Override
    public void clock() {

        clockCounter++;
    }

    @Override
    public void setMemory(@NonNull MemoryAccess memory) {
        this.memory = memory;
    }

    @Override
    public void setPortIO(@NonNull PortIO portIO) {
        this.portIO = portIO;
    }
}
