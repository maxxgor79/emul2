package zxspectrum.emul.io.mem.address;

import lombok.NonNull;
import zxspectrum.emul.cpu.reg.Reg16;

public class RpRegisteredAddress extends RegisteredAddress {
    RpRegisteredAddress(@NonNull Reg16 address) {
        super(address);
    }
}
