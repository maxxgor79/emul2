package zxspectrum.emul.machine;

import lombok.NonNull;
import zxspectrum.emul.io.mem.ram.RamType;
import zxspectrum.emul.io.port.PortIO16k;
import zxspectrum.emul.io.port.PortIO48k;
import zxspectrum.emul.io.sound.SoundChip;
import zxspectrum.emul.io.sound.SoundChipFactory;
import zxspectrum.emul.profile.ZxProfile;

/**
 * ZXSpectrum48K.
 *
 * @author Maxim Gorin
 */
class ZXSpectrum48K extends CommonZXSpectrum {
    protected SoundChip soundChip;

    private PortIO48k portInstance;

    ZXSpectrum48K(@NonNull final ZxProfile profile) {
        super(profile);
        assert profile.getRamType() == RamType.Ram48k;
    }

    @Override
    protected void createPortIO() {
        portIO = portInstance = new PortIO48k();
    }

    @Override
    protected void createSoundChip() {
        soundChip = SoundChipFactory.getInstance(profile.getSoundChipType());
    }

    @Override
    protected void initPortIO() {
        portInstance.setUla(ula);
    }

    @Override
    protected void initSoundChip() {

    }

    @Override
    public void run() {

    }
}
