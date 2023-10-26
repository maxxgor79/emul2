package zxspectrum.emul.io.port;

import lombok.NonNull;
import zxspectrum.emul.chipset.Ula;

public class PortIO48k extends PortIO16k {

  public PortIO48k(@NonNull Ula ula) {
    super(ula);
  }
}
