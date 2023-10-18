package zxspectrum.emul.proc;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import zxspectrum.emul.Resettable;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.port.Port;
import zxspectrum.emul.machine.MachineModel;
import zxspectrum.emul.proc.reg.ArithmeticProcessor;
import zxspectrum.emul.proc.reg.ArrayProcessor;
import zxspectrum.emul.proc.reg.LogicalProcessor;
import zxspectrum.emul.proc.reg.RegA;
import zxspectrum.emul.proc.reg.RegAF;
import zxspectrum.emul.proc.reg.RegB;
import zxspectrum.emul.proc.reg.RegBC;
import zxspectrum.emul.proc.reg.RegC;
import zxspectrum.emul.proc.reg.RegD;
import zxspectrum.emul.proc.reg.RegDE;
import zxspectrum.emul.proc.reg.RegE;
import zxspectrum.emul.proc.reg.RegF;
import zxspectrum.emul.proc.reg.RegH;
import zxspectrum.emul.proc.reg.RegHL;
import zxspectrum.emul.proc.reg.RegI;
import zxspectrum.emul.proc.reg.RegIX;
import zxspectrum.emul.proc.reg.RegIY;
import zxspectrum.emul.proc.reg.RegL;
import zxspectrum.emul.proc.reg.RegPC;
import zxspectrum.emul.proc.reg.RegR;
import zxspectrum.emul.proc.reg.RegSP;
import zxspectrum.emul.proc.reg.RegisterProcessor;

public class Z80 implements Resettable {

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

    public final RegPC PC = new RegPC();

    public final RegIX IX = new RegIX();

    public final RegIY IY = new RegIY();

    public final RegSP SP = new RegSP();

    public boolean IFF1 = false;

    public boolean IFF2 = false;

    @Getter
    @Setter
    private boolean halt;

    @Getter
    private MemoryControl memory;

    @Getter
    private Port port;

    @Getter
    private MachineModel machineModel;

    private ArithmeticProcessor arithmeticProcessor;

    private LogicalProcessor logicalProcessor;

    private RegisterProcessor registerProcessor;

    private ArrayProcessor arrayProcessor;

    public Z80() {
        setMachineModel(MachineModel.SPECTRUM48K);
        init();
    }

    public Z80(@NonNull MachineModel machineModel) {
        setMachineModel(machineModel);
        init();
    }

    private void init() {
        reset();
    }

    public void setMachineModel(@NonNull MachineModel machineModel) {
        if (this.machineModel == machineModel) {
            return;
        }
        this.machineModel = machineModel;
        arithmeticProcessor = new ArithmeticProcessor(this);
        logicalProcessor = new LogicalProcessor(this);
        registerProcessor = new RegisterProcessor(this);
        arrayProcessor = new ArrayProcessor(this);
        reset();
    }

    public void setMemory(@NonNull MemoryControl memory) {
        this.memory = memory;
        this.memory.setSP(SP);
    }

    public void setPort(@NonNull Port port) {
        this.port = port;
    }

    @Override
    public void reset() {
        IFF1 = false;
        IFF2 = false;
        PC.setValue(0);
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
        R.setValue(0);
        I.setValue(0xFF);
        halt = false;
        if (memory != null) {
            memory.reset();
        }
        if (port != null) {
            port.reset();
        }
    }
}