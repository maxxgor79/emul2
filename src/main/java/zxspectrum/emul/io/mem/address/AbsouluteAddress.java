package zxspectrum.emul.io.mem.address;

import lombok.NonNull;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.io.mem.MemoryAccess;

public class AbsouluteAddress implements Address {
    private MemoryAccess memory;

    private int address;

    public AbsouluteAddress(int address) {
        this.address = address;
    }

    @Override
    public AbsouluteAddress peek(Reg8 r) {
        memory.peek(address, r);
        return this;
    }

    @Override
    public AbsouluteAddress peek(Reg16 r) {
        memory.peek(address, r);
        return this;
    }

    @Override
    public AbsouluteAddress poke(Reg8 r) {
        memory.poke(address, r);
        return this;
    }

    @Override
    public AbsouluteAddress poke(Reg16 r) {
        memory.poke(address, r);
        return this;
    }

    @Override
    public AbsouluteAddress setAddress(int value) {
        this.address = value & 0xFFFF;
        return this;
    }

    @Override
    public int getAddress() {
        return address;
    }

    @Override
    public void setMemory(@NonNull MemoryAccess memory) {
        this.memory = memory;
    }
}
