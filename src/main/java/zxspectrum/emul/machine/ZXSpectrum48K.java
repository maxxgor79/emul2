package zxspectrum.emul.machine;

import zxspectrum.emul.io.port.PortIO16k;
import zxspectrum.emul.io.port.PortIO48k;

/**
 * ZXSpectrum48K.
 *
 * @author Maxim Gorin
 */
class ZXSpectrum48K extends CommonZXSpectrum {

    private PortIO48k portInstance;

    ZXSpectrum48K() {
        super(MachineModel.SPECTRUM48K);
    }

    @Override
    protected void createPortIO() {
        portIO = portInstance = new PortIO48k();
    }

    @Override
    protected void createSoundChip() {

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
