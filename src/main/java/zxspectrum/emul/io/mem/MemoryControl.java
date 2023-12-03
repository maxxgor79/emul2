package zxspectrum.emul.io.mem;

import lombok.NonNull;
import zxspectrum.emul.Resettable;
import zxspectrum.emul.cpu.reg.RegSP;

/**
 * MemoryControl.
 *
 * @author Maxim Gorin
 */
public interface MemoryControl extends Resettable, MemoryAccess, RomControl {
    int VIDEO_BUFFER_ADDRESS = 0x4000;

    int VIDEO_BUFFER_SIZE = 0x1B00;

    void setPageMapping(int pageMapping);

    void setSP(@NonNull RegSP sp);

    Buffer getVideoBuffer();
}
