package zxspectrum.emul.machine;

import zxspectrum.emul.io.port.PortIOPlus2A;
import zxspectrum.emul.io.port.PortIOPlus3;
import zxspectrum.emul.io.sound.SoundChip;
import zxspectrum.emul.io.sound.SoundChipFactory;

/**
 * ZXSpectrum3Plus.
 *
 * @author Maxim Gorin
 */
class ZXSpectrum3Plus extends CommonZXSpectrum {

    protected SoundChip soundChip;

    private PortIOPlus3 portInstance;

    ZXSpectrum3Plus() {
        super(MachineModel.SPECTRUMPLUS3);
    }

    @Override
    protected void createPortIO() {
        this.portIO = portInstance = new PortIOPlus3();
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
