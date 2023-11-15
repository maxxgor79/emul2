package zxspectrum.emul.machine;

import zxspectrum.emul.io.port.PortIOPlus2;
import zxspectrum.emul.io.port.PortIOPlus2A;
import zxspectrum.emul.io.sound.SoundChip;
import zxspectrum.emul.io.sound.SoundChipFactory;

/**
 * ZXSpectrum2APlus.
 *
 * @author Maxim Gorin
 */
class ZXSpectrum2APlus extends CommonZXSpectrum {

    protected SoundChip soundChip;

    private PortIOPlus2A portInstance;

    ZXSpectrum2APlus() {
        super(MachineModel.SPECTRUMPLUS2A);
    }

    @Override
    protected void createPortIO() {
        this.portIO = portInstance = new PortIOPlus2A();
    }

    @Override
    protected void createSoundChip() {
        soundChip = SoundChipFactory.getInstance(machineModel);
    }

    @Override
    protected void initPortIO() {
        portInstance.setUla(ula);
        portInstance.setMemory(memory);
        portInstance.setSoundChip(soundChip);
    }

    @Override
    protected void initSoundChip() {
    }

    @Override
    public void reset() {
        super.reset();
        soundChip.reset();
    }

    @Override
    public void run() {

    }
}
