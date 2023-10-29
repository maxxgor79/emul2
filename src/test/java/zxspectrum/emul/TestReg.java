package zxspectrum.emul;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import zxspectrum.emul.cpu.impl.Z80;

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
    Z80 cpu = new Z80();
    cpu.F.setCarry(true);
    Assertions.assertEquals(cpu.F.isCarrySet(), true);
    Assertions.assertEquals(cpu.F.getValue() & 0b0000_0001, 0b0000_0001);
    cpu.F.setSign(true);
    Assertions.assertEquals(cpu.F.isSignSet(), true);
    Assertions.assertEquals(cpu.F.getValue() & 0b1000_0000, 0b1000_0000);
    cpu.F.setAddSubstract(true);
    Assertions.assertEquals(cpu.F.isAddSubstractSet(), true);
    Assertions.assertEquals(cpu.F.getValue() & 0b0000_0010, 0b0000_0010);
    cpu.F.setHalfCarry(true);
    Assertions.assertEquals(cpu.F.isHalfCarrySet(), true);
    Assertions.assertEquals(cpu.F.getValue() & 0b0001_0000, 0b0001_0000);
    cpu.F.setParityOverflow(true);
    Assertions.assertEquals(cpu.F.isParityOverflowSet(), true);
    Assertions.assertEquals(cpu.F.getValue() & 0b0000_0100, 0b0000_0100);
    cpu.F.setZero(true);
    Assertions.assertEquals(cpu.F.isZeroSet(), true);
    Assertions.assertEquals(cpu.F.getValue() & 0b0100_0000, 0b0100_0000);
  }

  @Test
  void testReg8() {
    Z80 cpu = new Z80();
    cpu.A.setValue(0x88);
    Assertions.assertEquals(cpu.A.getValue(), 0x88);
  }

  @Test
  void testReg16() {
    Z80 cpu = new Z80();
    cpu.BC.setValue(0xFFFF);
    Assertions.assertEquals(cpu.BC.getValue(), 0xFFFF);
    cpu.BC.inc();
    Assertions.assertEquals(cpu.BC.getValue(), 0x0000);
    cpu.BC.dec();
    Assertions.assertEquals(cpu.BC.getValue(), 0xFFFF);
    cpu.SP.setValue(0xFFFF);
    cpu.SP.inc();
    Assertions.assertEquals(cpu.SP.getValue(), 0x0000);
    cpu.SP.dec();
    Assertions.assertEquals(cpu.SP.getValue(), 0xFFFF);
  }

}
