package zxspectrum.emul.io.mem.impl;

/**
 * Memory16K.
 *
 * @author Maxim Gorin
 */

public class Memory16K extends MemoryNotPaged {

  public Memory16K() {
    buf = new byte[0x8000];
    this.lastAddress = 0x7FFF;
  }
}
