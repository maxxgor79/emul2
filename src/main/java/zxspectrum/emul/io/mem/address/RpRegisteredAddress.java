package zxspectrum.emul.io.mem.address;

import lombok.NonNull;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.io.mem.MemoryAccess;

public class RpRegisteredAddress extends RegisteredAddress implements RpAddress{
    RpRegisteredAddress(@NonNull MemoryAccess memoryAccess, @NonNull Reg16 address) {
        super(memoryAccess, address);
    }
}
