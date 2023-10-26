package zxspectrum.emul.cpu.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.port.Port;
import zxspectrum.emul.cpu.reg.ArithmeticProcessor;
import zxspectrum.emul.cpu.reg.ArrayProcessor;
import zxspectrum.emul.cpu.reg.LogicalProcessor;
import zxspectrum.emul.cpu.reg.RegisterProcessor;

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
  private Port port;

  private ArithmeticProcessor arithmeticProcessor;

  private LogicalProcessor logicalProcessor;

  private RegisterProcessor registerProcessor;

  private ArrayProcessor arrayProcessor;

  public Z80() {
    init();
  }

  private void init() {
    arithmeticProcessor = new ArithmeticProcessor(this);
    logicalProcessor = new LogicalProcessor(this);
    registerProcessor = new RegisterProcessor(this);
    arrayProcessor = new ArrayProcessor(this);
    reset();
  }

  @Override
  public void setMemory(@NonNull MemoryControl memory) {
    this.memory = memory;
    this.memory.setSP(SP);
  }

  @Override
  public void setPort(@NonNull Port port) {
    this.port = port;
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
    if (port != null) {
      port.reset();
    }
  }
}