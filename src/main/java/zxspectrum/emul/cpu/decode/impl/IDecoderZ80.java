package zxspectrum.emul.cpu.decode.impl;

import lombok.NonNull;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.unit.ArithmeticLogical;
import zxspectrum.emul.cpu.unit.CallReturn;
import zxspectrum.emul.cpu.unit.CpuControl;
import zxspectrum.emul.cpu.unit.Jump;
import zxspectrum.emul.cpu.unit.LdIO;
import zxspectrum.emul.io.mem.MemoryAccess;

public class IDecoderZ80 extends BaseIDecoderZ80 {

    private final CbIDecoderZ80 cbIDecoder;

    private final EdIDecoderZ80 edIDecoder;

    private final FdIDecoderZ80 fdIDecoder;

    private final DdIDecoderZ80 ddIDecoder;


    public IDecoderZ80(@NonNull final Cpu cpu, @NonNull final LdIO ldIO, @NonNull final ArithmeticLogical al
            , @NonNull final Jump jump, @NonNull final CallReturn callReturn, @NonNull final CpuControl cpuControl) {
        super(cpu, ldIO, al, jump, callReturn, cpuControl);
        cbIDecoder = new CbIDecoderZ80(cpu, ldIO, al, jump, callReturn, cpuControl);
        edIDecoder = new EdIDecoderZ80(cpu, ldIO, al, jump, callReturn, cpuControl);
        fdIDecoder = new FdIDecoderZ80(cpu, ldIO, al, jump, callReturn, cpuControl);
        ddIDecoder = new DdIDecoderZ80(cpu, ldIO, al, jump, callReturn, cpuControl);
    }

    @Override
    public void execute() {
        final int code = fetch8();
        switch (code) {

            case 0xED:
                edIDecoder.execute(code);
                break;

            case 0xCB:
                cbIDecoder.execute(code);
                break;

            case 0xFD:
                fdIDecoder.execute(code);
                break;

            case 0xDD:
                ddIDecoder.execute(code);
                break;

            default:
                execute(code);
                break;
        }
    }

    //basic instructions
    @Override
    void execute(int code) {
        switch (code) {
            case 0:
                cpuControlU.nop();
                break;
        }
    }

    @Override
    public void setMemory(@NonNull MemoryAccess memory) {
        super.setMemory(memory);
        cbIDecoder.setMemory(memory);
        edIDecoder.setMemory(memory);
        ddIDecoder.setMemory(memory);
        fdIDecoder.setMemory(memory);
    }
}
