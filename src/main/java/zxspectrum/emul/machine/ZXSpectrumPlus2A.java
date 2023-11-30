package zxspectrum.emul.machine;

import lombok.NonNull;
import zxspectrum.emul.io.mem.ram.RamType;
import zxspectrum.emul.io.port.PortIOPlus2A;
import zxspectrum.emul.io.sound.SoundChip;
import zxspectrum.emul.io.sound.SoundChipFactory;
import zxspectrum.emul.profile.ZxProfile;

/**
 * ZXSpectrum2APlus.
 *
 * @author Maxim Gorin
 */
class ZXSpectrumPlus2A extends CommonZXSpectrum {

    protected SoundChip soundChip;

    private PortIOPlus2A portInstance;

    ZXSpectrumPlus2A(@NonNull final ZxProfile profile) {
        super(profile);
        assert profile.getRamType() == RamType.RamPlus2A;
    }

    @Override
    protected void createPortIO() {
        this.portIO = portInstance = new PortIOPlus2A();
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
