package zxspectrum.emul.io.mem.address;

import lombok.NonNull;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.io.mem.MemoryAccess;

final class IndexedAddress extends RegisteredAddress implements IdxAddress {
    private int offset;

    IndexedAddress(@NonNull Reg16 address) {
        super(address);
    }

    @Override
    public IndexedAddress peek(Reg8 r) {
        memory.peek(address.getValue() + offset, r);
        return this;
    }

    @Override
    public IndexedAddress peek(Reg16 r) {
        memory.peek(address.getValue() + offset, r);
        return this;
    }

    @Override
    public IndexedAddress poke(Reg8 r) {
        memory.poke(address.getValue() + offset, r);
        return this;
    }

    @Override
    public IndexedAddress poke(Reg16 r) {
        memory.poke(address.getValue() + offset, r);
        return this;
    }

    @Override
    public IndexedAddress setAddress(int value) {
        address.setValue(value);
        return this;
    }

    @Override
    public int getAddress() {
        return address.getValue() + offset;
    }

    @Override
    public IdxAddress noOffset() {
        offset = 0;
        return this;
    }

    @Override
    public IdxAddress setOffset(int offset) {
        this.offset = (byte) offset;
        return this;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public void setMemory(@NonNull MemoryAccess memory) {
        this.memory = memory;
    }
}
