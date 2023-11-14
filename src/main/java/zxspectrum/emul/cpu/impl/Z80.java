package zxspectrum.emul.cpu.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.unit.CallReturn;
import zxspectrum.emul.cpu.unit.CpuControl;
import zxspectrum.emul.cpu.unit.Jump;
import zxspectrum.emul.cpu.unit.LoadIO;
import zxspectrum.emul.cpu.unit.Alu;
import zxspectrum.emul.cpu.unit.impl.AluZ80;
import zxspectrum.emul.cpu.unit.impl.CallReturnZ80;
import zxspectrum.emul.cpu.unit.impl.CpuControlZ80;
import zxspectrum.emul.cpu.unit.impl.JumpZ80;
import zxspectrum.emul.cpu.unit.impl.LoadIOZ80;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.port.PortIO;

public class Z80 extends Cpu {

    @Getter
    private boolean signalNMI;

    @Getter
    private boolean signalINT;

    @Getter
    @Setter
    private boolean halt;

    @Getter
    private MemoryControl memory;

    @Getter
    private PortIO portIO;

    @Getter
    private Alu alu;

    @Getter
    private LoadIO loadIO;

    @Getter
    private CpuControl cpuControl;

    @Getter
    private Jump jump;

    @Getter
    private CallReturn callReturn;

    public Z80() {
        init();
    }

    private void init() {
        alu = new AluZ80(this);
        loadIO = new LoadIOZ80(this);
        cpuControl = new CpuControlZ80(this);
        jump = new JumpZ80(this);
        callReturn = new CallReturnZ80(this);
        reset();
    }

    @Override
    public void setMemory(@NonNull MemoryControl memory) {
        this.memory = memory;
        this.memory.setSP(SP);
        loadIO.setMemory(memory);
        alu.setMemory(memory);
    }

    @Override
    public void setPortIO(@NonNull PortIO portIO) {
        this.portIO = portIO;
        loadIO.setPortIO(portIO);
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
        signalINT = false;
        signalNMI = false;
        halt = false;
        if (memory != null) {
            memory.reset();
        }
        if (portIO != null) {
            portIO.reset();
        }
    }
}