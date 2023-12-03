package zxspectrum.emul.machine;

import lombok.NonNull;
import zxspectrum.emul.chipset.UlaFactory;
import zxspectrum.emul.chipset.Ula;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.CpuFactory;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.mem.ram.MemoryFactory;
import zxspectrum.emul.io.port.PortIO;
import zxspectrum.emul.profile.ZxProfile;

/**
 * AbstractZXSpectrum.
 *
 * @author Maxim Gorin
 */
abstract class CommonZXSpectrum implements ZXSpectrum {
    protected final ZxProfile profile;

    protected Cpu cpu;

    protected MemoryControl memory;

    protected PortIO portIO;

    protected Ula ula;

    protected CommonZXSpectrum(@NonNull ZxProfile profile) {
        this.profile = profile;
        create();
        init();
    }

    protected void create() {
        createMemory();
        createPortIO();
        createUla();
        createCpu();
        createSoundChip();
    }

    protected void createMemory() {
        this.memory = MemoryFactory.getInstance(profile);
    }

    protected abstract void createPortIO();

    protected void createUla() {
        this.ula = UlaFactory.getInstance(profile.getUlaType());
    }

    protected void createCpu() {
        this.cpu = CpuFactory.getInstance(profile.getCpuType());
    }

    protected abstract void createSoundChip();


    protected void init() {
        initMemory();
        initPortIO();
        initUla();
        initCpu();
        initSoundChip();
    }

    protected void initMemory() {
        if (profile.getRoms().size() < memory.getRomCount()) {
            throw new IllegalArgumentException(memory.getRomCount() + " rom(s) required");
        }
        for (int i = 0; i < memory.getRomCount(); i++) {
            memory.upload(profile.getRoms().get(i));
        }
    }

    protected abstract void initPortIO();

    protected void initUla() {
        this.ula.setMemory(this.memory);
    }

    protected void initCpu() {
        this.cpu.setMemory(this.memory);
        this.cpu.setPortIO(this.portIO);
    }

    protected abstract void initSoundChip();

    @Override
    public String getName() {
        return profile.getName();
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

    @Override
    public void run() {

    }
}
