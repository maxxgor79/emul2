package zxspectrum.emul.cpu;


import zxspectrum.emul.Resettable;
import zxspectrum.emul.cpu.interruption.IFF;
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
import zxspectrum.emul.cpu.reg.RegIY;
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
public abstract class Cpu implements Resettable {

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

    public final RegIX IX = new RegIX();

    public final RegIY IY = new RegIY();

    public final RegSP SP = new RegSP();

    public final AtomicReg16 MEM_PTR = new AtomicReg16();

    public final AtomicReg16 WZ = MEM_PTR;

    public final IFF IFF1 = new IFF();

    public final IFF IFF2 = new IFF();

    public abstract void setMemory(MemoryControl memory);

    public abstract MemoryControl getMemory();

    public abstract void setPortIO(PortIO port);

    public abstract PortIO getPortIO();
}
