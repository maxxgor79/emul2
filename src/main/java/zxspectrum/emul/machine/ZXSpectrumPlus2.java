package zxspectrum.emul.machine;

import lombok.NonNull;
import zxspectrum.emul.io.mem.ram.RamType;
import zxspectrum.emul.io.port.PortIOPlus2;
import zxspectrum.emul.io.sound.SoundChip;
import zxspectrum.emul.io.sound.SoundChipFactory;
import zxspectrum.emul.profile.ZxProfile;

/**
 * ZXSpectrum2Plus.
 *
 * @author Maxim Gorin
 */
class ZXSpectrumPlus2 extends CommonZXSpectrum {
    private PortIOPlus2 portInstance;

    protected SoundChip soundChip;

    ZXSpectrumPlus2(@NonNull final ZxProfile profile) {
        super(profile);
        assert profile.getRamType() == RamType.RamPlus2;
    }

    @Override
    protected void createPortIO() {
        this.portIO = portInstance = new PortIOPlus2();
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
}
