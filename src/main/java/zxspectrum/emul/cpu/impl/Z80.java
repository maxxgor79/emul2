package zxspectrum.emul.cpu.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.decoder.IDecoder;
import zxspectrum.emul.cpu.decoder.impl.IDecoderZ80;
import zxspectrum.emul.cpu.unit.CallReturn;
import zxspectrum.emul.cpu.unit.CpuControl;
import zxspectrum.emul.cpu.unit.Jump;
import zxspectrum.emul.cpu.unit.LdIO;
import zxspectrum.emul.cpu.unit.ArithmeticLogical;
import zxspectrum.emul.cpu.unit.impl.ArithmeticLogicalZ80;
import zxspectrum.emul.cpu.unit.impl.CallReturnZ80;
import zxspectrum.emul.cpu.unit.impl.CpuControlZ80;
import zxspectrum.emul.cpu.unit.impl.JumpZ80;
import zxspectrum.emul.cpu.unit.impl.LdIOZ80;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.port.PortIO;

@Slf4j
public class Z80 extends Cpu {

    private MemoryControl memory;

    private PortIO portIO;

    @Getter
    private final ArithmeticLogical arithmeticLogical;

    @Getter
    private final LdIO ldIO;

    @Getter
    private final CpuControl cpuControl;

    @Getter
    private final Jump jump;

    @Getter
    private final CallReturn callReturn;

    @Getter
    private final IDecoder iDecoder;

    public Z80() {
        arithmeticLogical = new ArithmeticLogicalZ80(this);
        ldIO = new LdIOZ80(this);
        cpuControl = new CpuControlZ80(this);
        jump = new JumpZ80(this);
        callReturn = new CallReturnZ80(this);
        iDecoder = new IDecoderZ80(this, ldIO, arithmeticLogical, jump, callReturn, cpuControl);
        init();
    }

    private void init() {
        reset();
    }

    @Override
    public void setMemory(@NonNull MemoryControl memory) {
        this.memory = memory;
        this.memory.setSP(SP);
        ldIO.setMemory(memory);
        arithmeticLogical.setMemory(memory);
        callReturn.setMemory(memory);
        cpuControl.setMemory(memory);
        iDecoder.setMemory(memory);
    }

    @Override
    public MemoryControl getMemory() {
        return memory;
    }

    @Override
    public void setPortIO(@NonNull PortIO portIO) {
        this.portIO = portIO;
        ldIO.setPortIO(portIO);
    }

    @Override
    public PortIO getPortIO() {
        return portIO;
    }

    @Override
    public void reset() {
        IFF1.setValue(false);
        IFF2.setValue(false);
        PC.setValue(0);
        MEM_PTR.setValue(0);
        SP.setValue(0xFFFF);
        A.setValue(0xFF);
        altA.setValue(0xFF);
        B.setValue(0xFF);
        altB.setValue(0xFF);
        C.setValue(0xFF);
        altC.setValue(0xFF);
        D.setValue(0xFF);
        E.setValue(0xFF);
        H.setValue(0xFF);
        altH.setValue(0xFF);
        L.setValue(0xFF);
        altL.setValue(0xFF);
        F.setValue(0xFF);
        altF.setValue(0xFF);
        IX.setValue(0xFFFF);
        IY.setValue(0xFFFF);
        IR.setValue(0xFF00);
        SIGNAL_INT.setValue(false);
        SIGNAL_NMI.setValue(false);
        HALT.setValue(false);
        if (memory != null) {
            memory.reset();
        }
        if (portIO != null) {
            portIO.reset();
        }
    }
}