package zxspectrum.emul.cpu;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import zxspectrum.emul.PortIOSetter;
import zxspectrum.emul.Resettable;
import zxspectrum.emul.cpu.interruption.Trigger;
import zxspectrum.emul.cpu.reg.AtomicReg16;
import zxspectrum.emul.cpu.reg.RegA;
import zxspectrum.emul.cpu.reg.RegAF;
import zxspectrum.emul.cpu.reg.RegB;
import zxspectrum.emul.cpu.reg.RegBC;
import zxspectrum.emul.cpu.reg.RegC;
import zxspectrum.emul.cpu.reg.RegD;
import zxspectrum.emul.cpu.reg.RegDE;
import zxspectrum.emul.cpu.reg.RegE;
import zxspectrum.emul.cpu.reg.RegF;
import zxspectrum.emul.cpu.reg.RegH;
import zxspectrum.emul.cpu.reg.RegHL;
import zxspectrum.emul.cpu.reg.RegI;
import zxspectrum.emul.cpu.reg.RegIR;
import zxspectrum.emul.cpu.reg.RegIX;
import zxspectrum.emul.cpu.reg.RegIXH;
import zxspectrum.emul.cpu.reg.RegIXL;
import zxspectrum.emul.cpu.reg.RegIY;
import zxspectrum.emul.cpu.reg.RegIYH;
import zxspectrum.emul.cpu.reg.RegIYL;
import zxspectrum.emul.cpu.reg.RegL;
import zxspectrum.emul.cpu.reg.RegPC;
import zxspectrum.emul.cpu.reg.RegR;
import zxspectrum.emul.cpu.reg.RegSP;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.port.PortIO;

/**
 * Cpu.
 *
 * @author Maxim Gorin
 */
public abstract class Cpu implements Resettable, PortIOSetter {
    public final RegA A = new RegA();

    public final RegF F = new RegF();

    public final RegAF AF = new RegAF(F, A);

    public final RegA altA = new RegA();

    public final RegF altF = new RegF();

    public final RegAF altAF = new RegAF(altF, altA);

    public final RegB B = new RegB();

    public final RegC C = new RegC();

    public final RegBC BC = new RegBC(C, B);

    public final RegB altB = new RegB();

    public final RegC altC = new RegC();

    public final RegBC altBC = new RegBC(altC, altB);

    public final RegD D = new RegD();

    public final RegE E = new RegE();

    public final RegDE DE = new RegDE(E, D);

    public final RegD altD = new RegD();

    public final RegE altE = new RegE();

    public final RegDE altDE = new RegDE(altE, altD);

    public final RegH H = new RegH();

    public final RegL L = new RegL();

    public final RegHL HL = new RegHL(L, H);

    public final RegH altH = new RegH();

    public final RegL altL = new RegL();

    public final RegHL altHL = new RegHL(altL, altH);

    public final RegI I = new RegI();

    public final RegR R = new RegR();

    public final RegIR IR = new RegIR(R, I);

    public final RegPC PC = new RegPC();

    public final RegIXH IXH = new RegIXH();

    public final RegIXL IXL = new RegIXL();

    public final RegIX IX = new RegIX(IXL, IXH);

    public final RegIYH IYH = new RegIYH();

    public final RegIYL IYL = new RegIYL();

    public final RegIY IY = new RegIY(IYL, IYH);

    public final RegSP SP = new RegSP();

    public final AtomicReg16 MEM_PTR = new AtomicReg16();

    public final AtomicReg16 WZ = MEM_PTR;

    public final Trigger IFF1 = new Trigger();

    public final Trigger IFF2 = new Trigger();

    public final Trigger HALT = new Trigger();

    public final Trigger SIGNAL_NMI = new Trigger();

    public final Trigger SIGNAL_INT = new Trigger();

    public abstract void pendEi();

    @Setter
    @Getter
    @NonNull
    private ImMode im = ImMode.IM0;

    public abstract void setMemory(MemoryControl memory);

    public abstract MemoryControl getMemory();

    public abstract PortIO getPortIO();

    public abstract void clock();
}
