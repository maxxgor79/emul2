package zxspectrum.emul.proc;

import lombok.Getter;
import lombok.NonNull;
import zxspectrum.emul.Resettable;
import zxspectrum.emul.io.mem.Factory;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.machine.MachineModel;
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

  @Getter
  private MemoryControl memory;

  @Getter
  private MachineModel machineModel;

  public Z80(@NonNull MachineModel machineModel) {
    setMachineModel(machineModel);
  }

  public void setMachineModel(MachineModel machineModel) {
    if (this.machineModel == machineModel) {
      return;
    }
    this.machineModel = machineModel;
    memory = Factory.getInstance(machineModel);
    memory.setSP(SP);
  }

  @Override
  public void reset() {
    if (memory != null) {
      memory.reset();
    }
  }
}