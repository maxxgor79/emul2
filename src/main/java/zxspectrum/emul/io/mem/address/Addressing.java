package zxspectrum.emul.io.mem.address;

import lombok.NonNull;
import org.checkerframework.checker.units.qual.A;
import zxspectrum.emul.MemorySetter;
import zxspectrum.emul.Resettable;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.io.mem.MemoryAccess;

public class Addressing implements MemorySetter, Resettable {
    public final RegisteredAddress HL;

    public final RpRegisteredAddress BC;

    public final RpRegisteredAddress DE;

    public final RegisteredAddress SP;

    public final IdxAddress IX;

    public final IdxAddress IY;

    public final AbsouluteAddress ABS;

    public Addressing(@NonNull Cpu cpu) {
        MemoryAccess memory = cpu.getMemory();
        HL = new RegisteredAddress(cpu.HL);
        BC = new RpRegisteredAddress(cpu.BC);
        DE = new RpRegisteredAddress(cpu.DE);
        SP = new RegisteredAddress(cpu.SP);
        IX = new IndexedAddress(cpu.IX);
        IY = new IndexedAddress(cpu.IY);
        ABS = new AbsouluteAddress(0);
        if (memory != null) {
            setMemory(memory);
        }
    }

    @Override
    public void setMemory(@NonNull MemoryAccess memory) {
        HL.setMemory(memory);
        BC.setMemory(memory);
        DE.setMemory(memory);
        SP.setMemory(memory);
        IX.setMemory(memory);
        IY.setMemory(memory);
        ABS.setMemory(memory);
    }

    @Override
    public void reset() {
        HL.setAddress(0);
        BC.setAddress(0);
        DE.setAddress(0);
        SP.setAddress(0);
        IX.setAddress(0);
        IX.setOffset(0);
        IY.setAddress(0);
        IY.setOffset(0);
        ABS.setAddress(0);
    }
}
