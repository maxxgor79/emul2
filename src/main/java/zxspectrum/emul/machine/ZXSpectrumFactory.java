package zxspectrum.emul.machine;

import lombok.NonNull;

/**
 * ZXSpectrumFactory.
 *
 * @author Maxim Gorin
 */
public final class ZXSpectrumFactory {

  private static final ZXSpectrum16K ZX_SPECTRUM_16_K = new ZXSpectrum16K();

  private static final ZXSpectrum48K ZX_SPECTRUM_48_K = new ZXSpectrum48K();

  private static final ZXSpectrum128K ZX_SPECTRUM_128_K = new ZXSpectrum128K();

  private static final ZXSpectrum2Plus ZX_SPECTRUM_2_PLUS = new ZXSpectrum2Plus();

  private static final ZXSpectrum2APlus ZX_SPECTRUM_2_A_PLUS = new ZXSpectrum2APlus();

  private static final ZXSpectrum3Plus ZX_SPECTRUM_3_PLUS = new ZXSpectrum3Plus();

  private ZXSpectrumFactory() {

  }

  public static ZXSpectrum getInstance(@NonNull MachineModel model) {
    return switch (model) {
      case SPECTRUM16K -> ZX_SPECTRUM_16_K;
      case SPECTRUM48K -> ZX_SPECTRUM_48_K;
      case SPECTRUM128K -> ZX_SPECTRUM_128_K;
      case SPECTRUMPLUS2 -> ZX_SPECTRUM_2_PLUS;
      case SPECTRUMPLUS2A -> ZX_SPECTRUM_2_A_PLUS;
      case SPECTRUMPLUS3 -> ZX_SPECTRUM_3_PLUS;
    };
  }
}
