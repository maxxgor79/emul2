package zxspectrum.emul.machine;

import zxspectrum.emul.io.port.PortIO128k;
import zxspectrum.emul.io.port.PortIOPlus2;
import zxspectrum.emul.io.sound.SoundChip;
import zxspectrum.emul.io.sound.SoundChipFactory;

/**
 * ZXSpectrum2Plus.
 *
 * @author Maxim Gorin
 */
class ZXSpectrum2Plus extends CommonZXSpectrum {
    private PortIOPlus2 portInstance;

    protected SoundChip soundChip;

    ZXSpectrum2Plus() {
        super(MachineModel.SPECTRUMPLUS2);
    }

    @Override
    protected void createPortIO() {
        this.portIO = portInstance = new PortIOPlus2();
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
