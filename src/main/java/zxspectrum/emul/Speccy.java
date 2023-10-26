package zxspectrum.emul;

import lombok.Getter;
import lombok.NonNull;
import zxspectrum.emul.chipset.ChipsetFactory;
import zxspectrum.emul.chipset.Ula;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.CpuFactory;
import zxspectrum.emul.io.mem.Memory;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.port.Port;
import zxspectrum.emul.io.port.Ports;
import zxspectrum.emul.machine.MachineModel;

public class Speccy implements Resettable {

  @Getter
  private Cpu cpu;

  @Getter
  private MemoryControl memory;

  @Getter
  private Port port;

  @Getter
  private MachineModel machineModel;

  @Getter
  private Ula ula;

  public Speccy() {
    setMachineModel(MachineModel.SPECTRUM48K);
  }

  @Override
  public void reset() {
    cpu.reset();
    memory.reset();
    port.reset();
    ula.reset();
  }

  public void setMachineModel(@NonNull MachineModel model) {
    this.machineModel = model;
    initMemory();
    initPort();
    initUla();
    initCpu();
  }

  private void initMemory() {
    this.memory = Memory.getInstance(machineModel);
  }

  private void initCpu() {
    this.cpu = CpuFactory.getInstance(machineModel);
    this.cpu.setMemory(this.memory);
    this.cpu.setPort(this.port);
  }

  private void initUla() {
    this.ula = ChipsetFactory.getInstance(machineModel);
    this.ula.setMemory(this.memory);
    this.ula.setPort(this.port);
  }

  private void initPort() {
    this.port = Ports.getInstance(machineModel);
  }
}
