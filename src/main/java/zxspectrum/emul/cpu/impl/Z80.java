package zxspectrum.emul.cpu.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import zxspectrum.emul.cpu.Counter;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.ImMode;
import zxspectrum.emul.cpu.decoder.InstDecoder;
import zxspectrum.emul.cpu.decoder.impl.InstDecoderZ80;
import zxspectrum.emul.cpu.interruption.Latch;
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
    private static final int NMI_ADDRESS = 0x66;

    private static final int INT1_ADDRESS = 0x38;

    private final Counter tStatesRemains = new Counter();

    private MemoryControl memory;

    private PortIO portIO;

    @Getter
    private final ArithmeticLogical aLU;

    @Getter
    private final LdIO ldIOU;

    @Getter
    private final CpuControl cpuCtlU;

    @Getter
    private final Jump jmpU;

    @Getter
    private final CallReturn callRetU;

    @Getter
    private final InstDecoder instDecoder;

    @Getter
    private long clockCounter;

    private final Latch intLatch = new Latch();

    public Z80() {
        aLU = new ArithmeticLogicalZ80(this, tStatesRemains);
        ldIOU = new LdIOZ80(this, tStatesRemains);
        cpuCtlU = new CpuControlZ80(this, tStatesRemains);
        jmpU = new JumpZ80(this, tStatesRemains);
        callRetU = new CallReturnZ80(this, tStatesRemains);
        instDecoder = new InstDecoderZ80(this, tStatesRemains, ldIOU, aLU, jmpU, callRetU, cpuCtlU);
        init();
    }

    private void init() {
        reset();
    }

    @Override
    public void setMemory(@NonNull MemoryControl memory) {
        this.memory = memory;
        this.memory.setSP(SP);
        ldIOU.setMemory(memory);
        aLU.setMemory(memory);
        callRetU.setMemory(memory);
        cpuCtlU.setMemory(memory);
        instDecoder.setMemory(memory);
    }

    @Override
    public MemoryControl getMemory() {
        return memory;
    }

    @Override
    public void setPortIO(@NonNull PortIO portIO) {
        this.portIO = portIO;
        ldIOU.setPortIO(portIO);
    }

    @Override
    public PortIO getPortIO() {
        return portIO;
    }

    @Override
    public void reset() {
        IFF1.setOn(false);
        IFF2.setOn(false);
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
        SIGNAL_INT.setOn(false);
        SIGNAL_NMI.setOn(false);
        setIm(ImMode.IM0);
        HALT.setOn(false);
        tStatesRemains.reset();
        if (memory != null) {
            memory.reset();
        }
        if (portIO != null) {
            portIO.reset();
        }
        clockCounter = 0L;
    }

    @Override
    public void clock() {
        if (tStatesRemains.isZero()) {
            if (SIGNAL_NMI.isOn()) {
                execNmi();
            } else {
                if (SIGNAL_INT.isOn() && IFF1.isOn() && intLatch.isEnabled()) {
                    execInt();
                }
                instDecoder.execute();
                intLatch.countDown();
            }
        }
        tStatesRemains.dec();
        clockCounter++;
    }

    protected void execNmi() {
        SIGNAL_NMI.setOn(false);
        HALT.setOn(false);
        IFF1.setOn(false);
        memory.push(PC);
        PC.setValue(NMI_ADDRESS);
        WZ.ld(PC);
    }

    protected void execInt() {
        HALT.setOn(false);
        IFF1.setOn(false);
        IFF2.setOn(false);
        memory.push(PC);
        switch (getIm()) {
            case IM0 -> {
                //ignored
            }

            case IM1 -> {
                PC.setValue(INT1_ADDRESS);
            }

            case IM2 -> {
                PC.setValue((R.getValue() << 8) | 0xFF);
            }
        }
        WZ.ld(PC);
    }

    @Override
    public void pendEi() {
        intLatch.setCounter(2);
    }
}