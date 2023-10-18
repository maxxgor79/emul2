package zxspectrum.emul;

import lombok.Getter;
import lombok.NonNull;
import zxspectrum.emul.io.mem.Memory;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.port.Port;
import zxspectrum.emul.io.port.Ports;
import zxspectrum.emul.machine.MachineModel;
import zxspectrum.emul.proc.Z80;

public class Speccy implements Resettable {

    @Getter
    private Z80 z80;

    @Getter
    private MemoryControl memory;

    @Getter
    private Port port;

    @Getter
    private MachineModel machineModel;

    public Speccy() {
        setMachineModel(MachineModel.SPECTRUM48K);
    }

    @Override
    public void reset() {
        z80.reset();
        memory.reset();
        port.reset();
    }

    public void setMachineModel(@NonNull MachineModel model) {
        this.machineModel = model;
        this.z80 = new Z80(machineModel);
        this.memory = Memory.getInstance(machineModel);
        this.z80.setMemory(this.memory);
        this.port = Ports.getInstance(this);
        this.z80.setPort(this.port);
    }
}
