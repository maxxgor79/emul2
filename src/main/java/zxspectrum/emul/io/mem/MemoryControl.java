package zxspectrum.emul.io.mem;

import lombok.NonNull;
import zxspectrum.emul.Resettable;
import zxspectrum.emul.cpu.reg.RegSP;

/**
 * MemoryControl.
 *
 * @author Maxim Gorin
 */
public interface MemoryControl extends Resettable, MemoryAccess {

  void setPageMapping(int pageMapping);

  void setSP(@NonNull RegSP sp);

  byte[] flushScreen();
}
