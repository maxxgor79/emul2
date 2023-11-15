package zxspectrum.emul.cpu.decode.impl;

import lombok.NonNull;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.decode.IDecoder;
import zxspectrum.emul.cpu.unit.ArithmeticLogical;
import zxspectrum.emul.cpu.unit.CallReturn;
import zxspectrum.emul.cpu.unit.CpuControl;
import zxspectrum.emul.cpu.unit.Jump;
import zxspectrum.emul.cpu.unit.LdIO;
import zxspectrum.emul.io.mem.MemoryAccess;
import zxspectrum.emul.io.mem.address.Addressing;

abstract class BaseIDecoderZ80 implements IDecoder {
    protected final Cpu cpu;


    protected MemoryAccess memory;

    protected final LdIO ldIOU;

    protected final ArithmeticLogical alU;

    protected final Jump jumpU;

    protected final CallReturn callReturnU;

    protected final CpuControl cpuControlU;

    protected final Addressing addressing;

    BaseIDecoderZ80(@NonNull final Cpu cpu, @NonNull final LdIO ldIO, @NonNull final ArithmeticLogical al
            , @NonNull final Jump jump, @NonNull final CallReturn callReturn, @NonNull final CpuControl cpuControl) {
        this.cpu = cpu;
        this.memory = cpu.getMemory();
        this.ldIOU = ldIO;
        this.alU = al;
        this.jumpU = jump;
        this.callReturnU = callReturn;
        this.cpuControlU = cpuControl;
        this.addressing = new Addressing(cpu);
    }

    @Override
    public int fetch8() {
        int b = memory.peek8(cpu.PC.getValue());
        cpu.PC.inc();
        return b;
    }

    @Override
    public int fetch16() {
        int w = memory.peek16(cpu.PC.getValue());
        cpu.PC.add(2);
        return w;
    }

    public void setMemory(@NonNull MemoryAccess memory) {
        this.memory = memory;
        addressing.setMemory(this.memory);
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException();
    }

    abstract void execute(int code);
}
