package zxspectrum.emul.io.mem.rom;

import lombok.Getter;
import lombok.NonNull;

/**
 * RomType.
 *
 * @author Maxim Gorin
 */
@Getter
public enum RomType {
  Rom48k("spectrum.rom"), Rom128K0("128-0.rom"), Rom128K1("128-1.rom"), RomPlus20(
      "plus2-0.rom"), RomPlus21("plus2-1.rom"), RomPlus30("plus3-0.rom"), RomPlus31(
      "plus3-1.rom"), RomPlus32("plus3-2.rom"), RomPlus33("plus3-3.rom");

  RomType(@NonNull String name) {
    this.name = name;
  }

  private final String name;
}
