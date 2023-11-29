package zxspectrum.emul.io.mem.ram.impl;

/**
 * Memory48K.
 *
 * @author Maxim Gorin
 */
public class Memory48K extends MemoryNotPaged {

  public Memory48K() {
    buf = new byte[0x10000];
    this.lastAddress = 0xFFFF;
  }
}
