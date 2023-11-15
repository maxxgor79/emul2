package zxspectrum.emul.io.mem.address;

import lombok.NonNull;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.io.mem.MemoryAccess;

public class RegisteredAddress implements Address {

    protected MemoryAccess memory;

    protected Reg16 address;

    RegisteredAddress(@NonNull Reg16 address) {
        this.address = address;
    }

    @Override
    public RegisteredAddress peek(Reg8 r) {
        memory.peek(address.getValue(), r);
        return this;
    }

    @Override
    public RegisteredAddress peek(Reg16 r) {
        memory.peek(address.getValue(), r);
        return this;
    }

    @Override
    public RegisteredAddress poke(Reg8 r) {
        memory.poke(address.getValue(), r);
        return this;
    }

    @Override
    public RegisteredAddress poke(Reg16 r) {
        memory.poke(address.getValue(), r);
        return this;
    }

    @Override
    public RegisteredAddress setAddress(int value) {
        address.setValue(value);
        return this;
    }

    @Override
    public int getAddress() {
        return address.getValue();
    }

    @Override
    public void setMemory(@NonNull MemoryAccess memory) {
        this.memory = memory;
    }
}
