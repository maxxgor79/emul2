package zxspectrum.emul;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import zxspectrum.emul.machine.MachineModel;
import zxspectrum.emul.machine.ZXSpectrum;
import zxspectrum.emul.machine.ZXSpectrumFactory;

public class Speccy {

  public static void main(String[] args) {
    ZXSpectrum zxSpectrum = ZXSpectrumFactory.getInstance(MachineModel.SPECTRUM48K);
    ExecutorService executor = Executors.newFixedThreadPool(1);
    executor.execute(zxSpectrum);
  }
}
