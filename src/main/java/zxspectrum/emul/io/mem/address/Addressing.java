package zxspectrum.emul.io.mem.address;

import lombok.NonNull;
import zxspectrum.emul.cpu.Cpu;

public class Addressing {

    public final Address HL;

    public final RpAddress BC;

    public final RpAddress DE;

    public final Address SP;

    public final IAddress IX;

    public final IAddress IY;

    public final Address ABS;

    public Addressing(@NonNull Cpu cpu) {
        HL = new RegisteredAddress(cpu.getMemory(), cpu.HL);
        BC = new RpRegisteredAddress(cpu.getMemory(), cpu.BC);
        DE = new RpRegisteredAddress(cpu.getMemory(), cpu.DE);
        SP = new RegisteredAddress(cpu.getMemory(), cpu.SP);
        IX = new IndexedAddress(cpu.getMemory(), cpu.IX);
        IY = new IndexedAddress(cpu.getMemory(), cpu.IY);
        ABS = new AbsouluteAddress(cpu.getMemory(), 0);
    }
}