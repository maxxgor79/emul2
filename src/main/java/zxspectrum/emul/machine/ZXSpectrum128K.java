package zxspectrum.emul.machine;

import lombok.NonNull;
import zxspectrum.emul.io.port.PortIO128k;
import zxspectrum.emul.io.sound.SoundChip;
import zxspectrum.emul.io.sound.SoundChipFactory;
import zxspectrum.emul.profile.ZxProfile;

/**
 * ZXSpectrum128K.
 *
 * @author Maxim Gorin
 */
class ZXSpectrum128K extends CommonZXSpectrum {

    protected SoundChip soundChip;

    private PortIO128k portInstance;

    ZXSpectrum128K(@NonNull final ZxProfile profile) {
        super(profile);
    }

    @Override
    protected void createPortIO() {
        portIO = portInstance = new PortIO128k();
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
