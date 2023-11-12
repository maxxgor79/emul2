package zxspectrum.emul.io.mem.address;

import lombok.NonNull;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.io.mem.MemoryAccess;

class AbsouluteAddress implements Address {
    private MemoryAccess memory;

    private int address;

    public AbsouluteAddress(@NonNull MemoryAccess memory, int address) {
        this.memory = memory;
        this.address = address;
    }

    @Override
    public Address peek(Reg8 r) {
        memory.peek(address, r);
        return this;
    }

    @Override
    public Address peek(Reg16 r) {
        memory.peek(address, r);
        return this;
    }

    @Override
    public Address poke(Reg8 r) {
        memory.poke(address, r);
        return this;
    }

    @Override
    public Address poke(Reg16 r) {
        memory.poke(address, r);
        return this;
    }

    @Override
    public Address setAddress(int value) {
        this.address = value & 0xFFFF;
        return this;
    }

    @Override
    public int getAddress() {
        return address;
    }
}
