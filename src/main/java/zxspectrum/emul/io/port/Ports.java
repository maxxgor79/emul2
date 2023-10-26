package zxspectrum.emul.io.port;

import lombok.NonNull;
import zxspectrum.emul.Speccy;
import zxspectrum.emul.io.port.impl.Port128k;
import zxspectrum.emul.io.port.impl.Port16k;
import zxspectrum.emul.io.port.impl.Port48k;
import zxspectrum.emul.io.port.impl.PortPlus2;
import zxspectrum.emul.io.port.impl.PortPlus2A;
import zxspectrum.emul.io.port.impl.PortPlus3;
import zxspectrum.emul.machine.MachineModel;

public class Ports {
    private Ports() {

    }

    private final static Port16k PORT_16_K = new Port16k();

    private final static Port48k PORT_48_K = new Port48k();

    private final static Port128k PORT_128_K = new Port128k();

    private final static PortPlus2 PORT_PLUS_2 = new PortPlus2();

    private final static PortPlus2A PORT_PLUS_2_A = new PortPlus2A();

    private final static PortPlus3 PORT_PLUS_3 = new PortPlus3();


    public static final Port getInstance(@NonNull MachineModel model) {
        return switch (model) {
            case SPECTRUM16K -> PORT_16_K;

            case SPECTRUM48K -> PORT_48_K;

            case SPECTRUM128K -> PORT_128_K;

            case SPECTRUMPLUS2 -> PORT_PLUS_2;

            case SPECTRUMPLUS2A -> PORT_PLUS_2_A;

            case SPECTRUMPLUS3 -> PORT_PLUS_3;
        };
    }
}
