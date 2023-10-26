package zxspectrum.emul.io.port;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import zxspectrum.emul.chipset.Ula;

/**
 * BasePortIO.
 *
 * @author Maxim Gorin
 */

@Getter
@Setter
abstract class BasePortIO implements PortIO {

  @NonNull
  protected Ula ula;

}
