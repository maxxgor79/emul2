package zxspectrum.emul.cpu.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.CpuU;
import zxspectrum.emul.cpu.alu.Alu;
import zxspectrum.emul.cpu.alu.impl.AluZ80;
import zxspectrum.emul.cpu.reg.FlagTable;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.cpu.reg.RegA;
import zxspectrum.emul.cpu.reg.RegBC;
import zxspectrum.emul.cpu.reg.RegF;
import zxspectrum.emul.cpu.reg.RegHL;
import zxspectrum.emul.cpu.reg.RegI;
import zxspectrum.emul.cpu.reg.RegR;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.port.PortIO;

import java.io.IOException;

public class Z80 extends Cpu implements FlagTable {

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
    private CpuU cpuU;

    public Z80() {
        init();
    }

    private void init() {
        initTables();
        alu = new AluZ80(this);
        cpuU = new Z80U(this);
        reset();
    }

    @Override
    public void setMemory(@NonNull MemoryControl memory) {
        this.memory = memory;
        this.memory.setSP(SP);
        cpuU.setMemory(memory);
        alu.setMemory(memory);
    }

    @Override
    public void setPortIO(@NonNull PortIO portIO) {
        this.portIO = portIO;
        cpuU.setPortIO(portIO);
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