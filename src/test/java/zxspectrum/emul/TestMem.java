package zxspectrum.emul;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import zxspectrum.emul.io.mem.impl.Memory128K;
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
    @Order(1)
    void testMem48k() {
        Z80 z80 = new Z80(MachineModel.SPECTRUM48K);
        z80.A.setValue(150);
        z80.getMemory().poke(0, z80.A);
        int val = z80.getMemory().peek8(0);
        Assertions.assertEquals(val, 0);


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
        val = z80.getMemory().peek8(z80.HL);
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
    @Order(2)
    void testMem128k() {
        Z80 z80 = new Z80(MachineModel.SPECTRUM128K);

        z80.A.setValue(150);
        z80.getMemory().poke(0, z80.A);
        int val = z80.getMemory().peek8(0);
        Assertions.assertEquals(val, 0);

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
        val = z80.getMemory().peek8(z80.HL);
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
    @Order(3)
    void testPaging() {
        Z80 z80 = new Z80(MachineModel.SPECTRUM128K);
        z80.A.setValue(50);
        z80.getMemory().poke(0xf000, z80.A);
        z80.A.setValue(51);
        z80.getMemory().setPageMapping(1);
        z80.getMemory().poke(0xf000, z80.A);
        z80.A.setValue(52);
        z80.getMemory().setPageMapping(2);
        z80.getMemory().poke(0xf000, z80.A);
        z80.A.setValue(53);
        z80.getMemory().setPageMapping(3);
        z80.getMemory().poke(0xf000, z80.A);
        z80.A.setValue(54);
        z80.getMemory().setPageMapping(4);
        z80.getMemory().poke(0xf000, z80.A);
        z80.A.setValue(55);
        z80.getMemory().setPageMapping(5);
        z80.getMemory().poke(0xf000, z80.A);
        z80.A.setValue(56);
        z80.getMemory().setPageMapping(6);
        z80.getMemory().poke(0xf000, z80.A);
        z80.A.setValue(57);
        z80.getMemory().setPageMapping(7);
        z80.getMemory().poke(0xf000, z80.A);

        z80.getMemory().setPageMapping(0);
        int val = z80.getMemory().peek8(0xf000);
        Assertions.assertEquals(val, 50);

        z80.getMemory().setPageMapping(1);
        val = z80.getMemory().peek8(0xf000);
        Assertions.assertEquals(val, 51);

        z80.getMemory().setPageMapping(2);
        val = z80.getMemory().peek8(0xf000);
        Assertions.assertEquals(val, 52);

        z80.getMemory().setPageMapping(3);
        val = z80.getMemory().peek8(0xf000);
        Assertions.assertEquals(val, 53);

        z80.getMemory().setPageMapping(4);
        val = z80.getMemory().peek8(0xf000);
        Assertions.assertEquals(val, 54);

        z80.getMemory().setPageMapping(5);
        val = z80.getMemory().peek8(0xf000);
        Assertions.assertEquals(val, 55);

        z80.getMemory().setPageMapping(6);
        val = z80.getMemory().peek8(0xf000);
        Assertions.assertEquals(val, 56);

        z80.getMemory().setPageMapping(7);
        val = z80.getMemory().peek8(0xf000);
        Assertions.assertEquals(val, 57);
    }

    @Test
    @Order(100)
    void testPaging2() {
        Z80 z80 = new Z80(MachineModel.SPECTRUM128K);
        z80.A.setValue(50);
        z80.getMemory().setPageMapping(0);
        z80.getMemory().poke(0xf000, z80.A);
        z80.getMemory().setPageMapping(Memory128K.DISABLED_PAGE_SELECTING);
        z80.getMemory().setPageMapping(5);
        z80.getMemory().peek(0xf000, z80.B);
        Assertions.assertEquals(z80.B.getValue(), 50);
        z80.getMemory().reset();
    }

    @Test
    @Order(4)
    void testPagingPlus2() {
        Z80 z80 = new Z80(MachineModel.SPECTRUMPLUS2);
        z80.getMemory().setPageMapping((0b0000_0001 << 16));
        z80.A.setValue(190);
        z80.getMemory().poke(0, z80.A);
        z80.getMemory().peek(0, z80.B);
        Assertions.assertEquals(z80.B.getValue(), 190);
        z80.getMemory().setPageMapping(0);
        z80.A.setValue(191);
        z80.getMemory().poke(5, z80.A);
        z80.getMemory().peek(5, z80.B);
        if (z80.B.getValue() == 191) {
            Assertions.fail();
        }

    }
}
