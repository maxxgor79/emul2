package zxspectrum.emul.machine;

import zxspectrum.emul.Resettable;
import zxspectrum.emul.chipset.Ula;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.io.port.PortIO;

/**
 * ZXSpectrumModel.
 *
 * @author Maxim Gorin
 */
public interface ZXSpectrum extends Resettable, Runnable {

  String getName();

  Cpu getCpu();

  MemoryControl getMemory();

  PortIO getPortIO();

  Ula getUla();
}
