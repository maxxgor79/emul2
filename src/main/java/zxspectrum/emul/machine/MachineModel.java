package zxspectrum.emul.machine;

import lombok.Getter;
import lombok.NonNull;

/**
 * Model.
 *
 * @author Maxim Gorin
 */
@Getter
public enum MachineModel {
  SPECTRUM16K("16K"),

  SPECTRUM48K("48K"),

  SPECTRUM128K("128K"),

  SPECTRUMPLUS2("Plus2"),

  SPECTRUMPLUS2A("Plus2A"),

  SPECTRUMPLUS3("Plus3");

  MachineModel(@NonNull String code) {
    if ("Spectrum16K".equals(code)) {
      this.name = "ZX-Spectrum 16K";
      this.tStatesFrame = 69888;
      this.tStatesLine = 224;
      this.upBorderWidth = 64;
      this.scanLines = 312;
      this.lengthINT = 32;
      this.firstScrByte = 14336;
      this.firstScrUpdate = 14328;
      this.lastScrUpdate = 57237;
      this.firstBorderUpdate = 32 * this.tStatesLine - 16;
      this.lastBorderUpdate = 288 * this.tStatesLine;
      this.outOffset = 3;
      this.hasAY8912 = false;
      this.hasDisk = false;
    } else if ("Spectrum48K".equals(code)) {
      this.name = "ZX-Spectrum 48K";
      this.tStatesFrame = 69888;
      this.tStatesLine = 224;
      this.upBorderWidth = 64;
      this.scanLines = 312;
      this.lengthINT = 32;
      this.firstScrByte = 14336;
      this.firstScrUpdate = 14328;
      this.lastScrUpdate = 57237;
      this.firstBorderUpdate = 32 * this.tStatesLine - 16;
      this.lastBorderUpdate = 288 * this.tStatesLine;
      this.outOffset = 3;
      this.hasAY8912 = false;
      this.hasDisk = false;
    } else if ("128K".equals(code)) {
      this.name = "ZX-Spectrum 128K";
      this.tStatesFrame = 70908;
      this.tStatesLine = 228;
      this.upBorderWidth = 63;
      this.scanLines = 311;
      this.lengthINT = 36;
      this.firstScrByte = 14361;
      this.firstScrUpdate = 14344;
      this.lastScrUpdate = 58040;
      this.firstBorderUpdate = 31 * this.tStatesLine - 16;
      this.lastBorderUpdate = 288 * this.tStatesLine;
      this.outOffset = 1;
      this.hasAY8912 = true;
      this.hasDisk = false;
    } else if ("Plus2".equals(code)) {
      this.name = "Amstrad ZX-Spectrum +2";
      this.tStatesFrame = 70908;
      this.tStatesLine = 228;
      this.upBorderWidth = 63;
      this.scanLines = 311;
      this.lengthINT = 36;
      this.firstScrByte = 14361;
      this.firstScrUpdate = 14344;
      this.lastScrUpdate = 58040;
      this.firstBorderUpdate = 31 * this.tStatesLine - 16;
      this.lastBorderUpdate = 288 * this.tStatesLine;
      this.outOffset = 1;
      this.hasAY8912 = true;
      this.hasDisk = false;
    } else if ("Plus2A".equals(code)) {
      this.name = "ZX Spectrum +2A";
      this.tStatesFrame = 70908;
      this.tStatesLine = 228;
      this.upBorderWidth = 63;
      this.scanLines = 311;
      this.lengthINT = 36;
      this.firstScrByte = 14364;
      this.firstScrUpdate = 14356;
      this.lastScrUpdate = 58040;
      this.firstBorderUpdate = 31 * this.tStatesLine - 16;
      this.lastBorderUpdate = 288 * this.tStatesLine;
      this.outOffset = 1;
      this.hasAY8912 = true;
      this.hasDisk = false;
    } else if ("Plus3".equals(code)) {
      this.name = "ZX Spectrum +3";
      this.tStatesFrame = 70908;
      this.tStatesLine = 228;
      this.upBorderWidth = 63;
      this.scanLines = 311;
      this.lengthINT = 36;
      this.firstScrByte = 14364;
      this.firstScrUpdate = 14356;
      this.lastScrUpdate = 58040;
      this.firstBorderUpdate = 31 * this.tStatesLine - 16;
      this.lastBorderUpdate = 288 * this.tStatesLine;
      this.outOffset = 1;
      this.hasAY8912 = true;
      this.hasDisk = true;
    }
  }

  private String name;

  public int tStatesFrame;

  public int tStatesLine;

  public int upBorderWidth;

  public int scanLines;

  public int firstScrByte;

  public int firstScrUpdate;

  public int lastScrUpdate;

  public int firstBorderUpdate;

  public int lastBorderUpdate;

  public int outOffset;

  public int lengthINT;

  private boolean hasAY8912;

  private boolean hasDisk;
}
