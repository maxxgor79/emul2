package zxspectrum.emul.machine;

import lombok.NonNull;
import zxspectrum.emul.io.port.PortIOPlus3;
import zxspectrum.emul.io.sound.SoundChip;
import zxspectrum.emul.io.sound.SoundChipFactory;
import zxspectrum.emul.profile.ZxProfile;

/**
 * ZXSpectrum3Plus.
 *
 * @author Maxim Gorin
 */
class ZXSpectrumPlus3 extends CommonZXSpectrum {

    protected SoundChip soundChip;

    private PortIOPlus3 portInstance;

    ZXSpectrumPlus3(@NonNull final ZxProfile profile) {
        super(profile);
    }

    @Override
    protected void createPortIO() {
        this.portIO = portInstance = new PortIOPlus3();
    }

    @Override
    protected void createSoundChip() {
        soundChip = SoundChipFactory.getInstance(profile.getSoundChipType());
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
