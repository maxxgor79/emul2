package zxspectrum.emul.machine;

import lombok.NonNull;
import zxspectrum.emul.chipset.ChipsetFactory;
import zxspectrum.emul.chipset.Ula;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.CpuFactory;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.mem.MemoryFactory;
import zxspectrum.emul.io.port.PortIO;

/**
 * AbstractZXSpectrum.
 *
 * @author Maxim Gorin
 */
abstract class CommonZXSpectrum implements ZXSpectrum {

  protected Cpu cpu;

  protected MemoryControl memory;

  protected PortIO portIO;

  protected MachineModel machineModel;

  protected Ula ula;

  protected CommonZXSpectrum(@NonNull MachineModel model) {
    this.machineModel = model;
    init();
  }

  protected void init() {
    initMemory();
    initPortIO();
    initUla();
    initCpu();
    initSoundChip();
  }

  protected void initMemory() {
    this.memory = MemoryFactory.getInstance(machineModel);
  }

  protected abstract void initPortIO();

  protected void initUla() {
    this.ula = ChipsetFactory.getInstance(machineModel);
    this.ula.setMemory(this.memory);
    this.ula.setPortIO(this.portIO);
  }

  protected void initCpu() {
    this.cpu = CpuFactory.getInstance(machineModel);
    this.cpu.setMemory(this.memory);
    this.cpu.setPortIO(this.portIO);
  }

  protected void initSoundChip() {
  }

  @Override
  public String getName() {
    return machineModel.getName();
  }

  @Override
  public Cpu getCpu() {
    return cpu;
  }

  @Override
  public MemoryControl getMemory() {
    return memory;
  }

  @Override
  public PortIO getPortIO() {
    return portIO;
  }

  @Override
  public Ula getUla() {
    return ula;
  }

  @Override
  public void reset() {
    cpu.reset();
    memory.reset();
    portIO.reset();
    ula.reset();
  }
}
