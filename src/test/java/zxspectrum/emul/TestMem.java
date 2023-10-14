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

        z80.HL.setValue(0x1235);
        z80.getMemory().poke(0xFF00, z80.HL);
        z80.getMemory().peek(0xFF00, z80.DE);
        Assertions.assertEquals(z80.DE.getValue(), 0x1235);

        z80.A.setValue(0x80);
        z80.getMemory().poke(0xFF01, z80.A);
        z80.getMemory().peek(0xFF01, z80.B);
        Assertions.assertEquals(z80.B.getValue(), 0x80);

        z80.A.setValue(0x1F);
        z80.BC.setValue(0xFF02);
        z80.getMemory().poke(z80.BC, z80.A);
        z80.getMemory().peek(z80.BC, z80.D);
        Assertions.assertEquals(z80.D.getValue(), 0x1F);

        z80.BC.setValue(0xFF04);
        z80.HL.setValue(0x10_00);
        z80.getMemory().poke(z80.BC, z80.HL);
        z80.getMemory().peek(z80.BC, z80.DE);
        Assertions.assertEquals(z80.DE.getValue(), 0x10_00);

        z80.HL.setValue(0x4001);
        z80.getMemory().poke8(z80.HL, 123);
        int val = z80.getMemory().peek8(z80.HL);
        Assertions.assertEquals(val, 123);

        z80.HL.setValue(0x4500);
        z80.getMemory().poke16(z80.HL, 555);
        val = z80.getMemory().peek16(z80.HL);
        Assertions.assertEquals(val, 555);

        z80.A.setValue(77);
        z80.getMemory().poke(20000, z80.A);
        val = z80.getMemory().peek8(20000);
        Assertions.assertEquals(val, 77);
    }

    @Test
    void testMem128k() {
        Z80 z80 = new Z80(MachineModel.SPECTRUM128K);
        z80.BC.setValue(0x1234);
        z80.getMemory().push(z80.BC);
        z80.getMemory().pop(z80.HL);
        Assertions.assertEquals(z80.HL.getValue(), 0x1234);

        z80.HL.setValue(0x1235);
        z80.getMemory().poke(0xFF00, z80.HL);
        z80.getMemory().peek(0xFF00, z80.DE);
        Assertions.assertEquals(z80.DE.getValue(), 0x1235);

        z80.A.setValue(0x80);
        z80.getMemory().poke(0xFF01, z80.A);
        z80.getMemory().peek(0xFF01, z80.B);
        Assertions.assertEquals(z80.B.getValue(), 0x80);

        z80.A.setValue(0x1F);
        z80.BC.setValue(0xFF02);
        z80.getMemory().poke(z80.BC, z80.A);
        z80.getMemory().peek(z80.BC, z80.D);
        Assertions.assertEquals(z80.D.getValue(), 0x1F);

        z80.BC.setValue(0xFF04);
        z80.HL.setValue(0x10_00);
        z80.getMemory().poke(z80.BC, z80.HL);
        z80.getMemory().peek(z80.BC, z80.DE);
        Assertions.assertEquals(z80.DE.getValue(), 0x10_00);

        z80.HL.setValue(0x4001);
        z80.getMemory().poke8(z80.HL, 123);
        int val = z80.getMemory().peek8(z80.HL);
        Assertions.assertEquals(val, 123);

        z80.HL.setValue(0x4500);
        z80.getMemory().poke16(z80.HL, 555);
        val = z80.getMemory().peek16(z80.HL);
        Assertions.assertEquals(val, 555);

        z80.A.setValue(77);
        z80.getMemory().poke(20000, z80.A);
        val = z80.getMemory().peek8(20000);
        Assertions.assertEquals(val, 77);
    }
}
