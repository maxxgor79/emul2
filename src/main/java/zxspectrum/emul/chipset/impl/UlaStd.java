package zxspectrum.emul.chipset.impl;

import lombok.Getter;
import lombok.NonNull;
import zxspectrum.emul.chipset.Ula;
import zxspectrum.emul.chipset.palette.ZxColor;
import zxspectrum.emul.io.mem.MemoryAccess;
import zxspectrum.emul.io.mem.MemoryControl;

/**
 * ULA5C102E.
 *
 * @author Maxim Gorin
 */
public class UlaStd implements Ula {
    @Getter
    private ZxColor borderColor;

    @Override
    public void reset() {

    }

    @Override
    public MemoryControl getMemory() {
        return null;
    }

    @Override
    public void clock() {

    }

    @Override
    public void setMemory(@NonNull MemoryAccess memory) {

    }
}
