package zxspectrum.emul;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import zxspectrum.emul.machine.MachineModel;
import zxspectrum.emul.proc.Z80;

/**
 * TestReg.
 *
 * @author Maxim Gorin
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
public class TestReg {

  @Test
  void testFlag() {
    Z80 z80 = new Z80(MachineModel.SPECTRUM48K);
    z80.F.setCarry(true);
    Assertions.assertEquals(z80.F.isCarry(), true);
    Assertions.assertEquals(z80.F.getValue() & 0b0000_0001, 0b0000_0001);
    z80.F.setSign(true);
    Assertions.assertEquals(z80.F.isSign(), true);
    Assertions.assertEquals(z80.F.getValue() & 0b1000_0000, 0b1000_0000);
    z80.F.setAddSubstract(true);
    Assertions.assertEquals(z80.F.isAddSubstract(), true);
    Assertions.assertEquals(z80.F.getValue() & 0b0000_0010, 0b0000_0010);
    z80.F.setHalfCarry(true);
    Assertions.assertEquals(z80.F.isHalfCarry(), true);
    Assertions.assertEquals(z80.F.getValue() & 0b0001_0000, 0b0001_0000);
    z80.F.setParityOverflow(true);
    Assertions.assertEquals(z80.F.isParityOverflow(), true);
    Assertions.assertEquals(z80.F.getValue() & 0b0000_0100, 0b0000_0100);
    z80.F.setZero(true);
    Assertions.assertEquals(z80.F.isZero(), true);
    Assertions.assertEquals(z80.F.getValue() & 0b0100_0000, 0b0100_0000);
  }

  @Test
  void testReg8() {
    Z80 z80 = new Z80(MachineModel.SPECTRUM48K);
    z80.A.setValue(0x88);
    Assertions.assertEquals(z80.A.getValue(), 0x88);
  }

  @Test
  void testReg16() {
    Z80 z80 = new Z80(MachineModel.SPECTRUM48K);
    z80.BC.setValue(0xFFFF);
    Assertions.assertEquals(z80.BC.getValue(), 0xFFFF);
    z80.BC.inc();
    Assertions.assertEquals(z80.BC.getValue(), 0x0000);
    z80.BC.dec();
    Assertions.assertEquals(z80.BC.getValue(), 0xFFFF);
    z80.SP.setValue(0xFFFF);
    z80.SP.inc();
    Assertions.assertEquals(z80.SP.getValue(), 0x0000);
    z80.SP.dec();
    Assertions.assertEquals(z80.SP.getValue(), 0xFFFF);
  }

}
