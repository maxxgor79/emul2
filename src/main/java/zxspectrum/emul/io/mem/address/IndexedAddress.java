package zxspectrum.emul.io.mem.address;

import lombok.NonNull;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.io.mem.MemoryAccess;

class IndexedAddress extends RegisteredAddress implements IAddress {
    private int offset;

    IndexedAddress(@NonNull MemoryAccess memoryAccess, @NonNull Reg16 address) {
        super(memoryAccess, address);
    }

    @Override
    public IAddress peek(Reg8 r) {
        memoryAccess.peek(address.getValue() + offset, r);
        return this;
    }

    @Override
    public IAddress peek(Reg16 r) {
        memoryAccess.peek(address.getValue() + offset, r);
        return this;
    }

    @Override
    public IAddress poke(Reg8 r) {
        memoryAccess.poke(address.getValue() + offset, r);
        return this;
    }

    @Override
    public IAddress poke(Reg16 r) {
        memoryAccess.poke(address.getValue() + offset, r);
        return this;
    }

    @Override
    public IAddress setAddress(int value) {
        address.setValue(value);
        return this;
    }

    @Override
    public int getAddress() {
        return address.getValue() + offset;
    }

    @Override
    public IAddress setOffset(int offset) {
        this.offset = (byte) offset;
        return this;
    }

    @Override
    public int getOffset() {
        return offset;
    }
}
