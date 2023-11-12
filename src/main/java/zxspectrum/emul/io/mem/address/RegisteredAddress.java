package zxspectrum.emul.io.mem.address;

import lombok.NonNull;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.io.mem.MemoryAccess;

class RegisteredAddress implements Address {

    protected MemoryAccess memoryAccess;

    protected Reg16 address;

    RegisteredAddress(@NonNull MemoryAccess memoryAccess, @NonNull Reg16 address) {
        this.memoryAccess = memoryAccess;
        this.address = address;
    }

    @Override
    public Address peek(Reg8 r) {
        memoryAccess.peek(address.getValue(), r);
        return this;
    }

    @Override
    public Address peek(Reg16 r) {
        memoryAccess.peek(address.getValue(), r);
        return this;
    }

    @Override
    public Address poke(Reg8 r) {
        memoryAccess.poke(address.getValue(), r);
        return this;
    }

    @Override
    public Address poke(Reg16 r) {
        memoryAccess.poke(address.getValue(), r);
        return this;
    }

    @Override
    public Address setAddress(int value) {
        address.setValue(value);
        return this;
    }

    @Override
    public int getAddress() {
        return address.getValue();
    }
}
