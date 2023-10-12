package zxspectrum.emul;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import zxspectrum.emul.machine.MachineModel;
import zxspectrum.emul.proc.Z80;

/**
 * TestMem.
 *
 * @author Maxim Gorin
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
public class TestMem {

  @Test
  void testMem48k() {
    Z80 z80 = new Z80(MachineModel.SPECTRUM48K);
    z80.BC.setValue(0x1234);
    z80.getMemory().push(z80.BC);
    z80.getMemory().pop(z80.HL);
    Assertions.assertEquals(z80.HL.getValue(), 0x1234);
    z80.A.setValue(0x80);
    z80.getMemory().poke(0xFF00, z80.A);
    //z80.getMemory().peek();
  }
}
