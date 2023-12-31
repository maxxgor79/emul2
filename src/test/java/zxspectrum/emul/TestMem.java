package zxspectrum.emul;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.impl.Z80;
import zxspectrum.emul.io.mem.ram.MemoryFactory;
import zxspectrum.emul.io.mem.ram.RamType;
import zxspectrum.emul.io.mem.ram.impl.Memory128K;
import zxspectrum.emul.machine.ZXSpectrum;
import zxspectrum.emul.machine.ZXSpectrumFactory;
import zxspectrum.emul.profile.ZxProfile;
import zxspectrum.emul.profile.ZxProfiles;

import java.io.IOException;


/**
 * TestMem.
 *
 * @author Maxim Gorin
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
public class TestMem {

    final static ZxProfiles profiles = ZxProfiles.getInstance();


    @BeforeAll
    static void init() throws IOException {
        profiles.load();
    }
    @Test
    void testMem48k() {
        ZxProfile profile = profiles.getByName("ZX Spectrum 48K");
        Assertions.assertNotNull(profile);

        ZXSpectrum spectrum =  ZXSpectrumFactory.getInstance(profile);
        Cpu cpu = spectrum.getCpu();
        cpu.A.setValue(150);
        cpu.getMemory().poke(0, cpu.A);
        int val = cpu.getMemory().peek8(0);
        Assertions.assertNotEquals(val, 150);

        cpu.BC.setValue(0x1234);
        cpu.getMemory().push(cpu.BC);
        cpu.getMemory().pop(cpu.HL);
        Assertions.assertEquals(cpu.HL.getValue(), 0x1234);

        cpu.HL.setValue(0x1235);
        cpu.getMemory().poke(0xFF00, cpu.HL);
        cpu.getMemory().peek(0xFF00, cpu.DE);
        Assertions.assertEquals(cpu.DE.getValue(), 0x1235);

        cpu.A.setValue(0x80);
        cpu.getMemory().poke(0xFF01, cpu.A);
        cpu.getMemory().peek(0xFF01, cpu.B);
        Assertions.assertEquals(cpu.B.getValue(), 0x80);

        cpu.A.setValue(0x1F);
        cpu.BC.setValue(0xFF02);
        cpu.getMemory().poke(cpu.BC, cpu.A);
        cpu.getMemory().peek(cpu.BC, cpu.D);
        Assertions.assertEquals(cpu.D.getValue(), 0x1F);

        cpu.BC.setValue(0xFF04);
        cpu.HL.setValue(0x10_00);
        cpu.getMemory().poke(cpu.BC, cpu.HL);
        cpu.getMemory().peek(cpu.BC, cpu.DE);
        Assertions.assertEquals(cpu.DE.getValue(), 0x10_00);

        cpu.HL.setValue(0x4001);
        cpu.getMemory().poke8(cpu.HL, 123);
        val = cpu.getMemory().peek8(cpu.HL);
        Assertions.assertEquals(val, 123);

        cpu.HL.setValue(0x4500);
        cpu.getMemory().poke16(cpu.HL, 555);
        val = cpu.getMemory().peek16(cpu.HL);
        Assertions.assertEquals(val, 555);

        cpu.A.setValue(77);
        cpu.getMemory().poke(20000, cpu.A);
        val = cpu.getMemory().peek8(20000);
        Assertions.assertEquals(val, 77);

        for (int i = 16384; i < 65535; i++) {
            cpu.A.setValue(i & 0xFF);
            cpu.getMemory().poke(i, cpu.A);
        }
        for (int i = 16384; i < 65535; i++) {
            cpu.A.setValue(i & 0xFF);
            cpu.getMemory().peek(i, cpu.B);
            Assertions.assertEquals(cpu.A.getValue(), cpu.B.getValue());
        }
    }

    @Test
    void testMem128k() {
        ZxProfile profile = profiles.getByName("ZX Spectrum 128K");
        Assertions.assertNotNull(profile);
        ZXSpectrum spectrum =  ZXSpectrumFactory.getInstance(profile);
        Cpu cpu = spectrum.getCpu();
        cpu.A.setValue(150);
        cpu.getMemory().poke(0, cpu.A);
        int val = cpu.getMemory().peek8(0);
        Assertions.assertNotEquals(val, 150);

        cpu.BC.setValue(0x1234);
        cpu.getMemory().push(cpu.BC);
        cpu.getMemory().pop(cpu.HL);
        Assertions.assertEquals(cpu.HL.getValue(), 0x1234);

        cpu.HL.setValue(0x1235);
        cpu.getMemory().poke(0xFF00, cpu.HL);
        cpu.getMemory().peek(0xFF00, cpu.DE);
        Assertions.assertEquals(cpu.DE.getValue(), 0x1235);

        cpu.A.setValue(0x80);
        cpu.getMemory().poke(0xFF01, cpu.A);
        cpu.getMemory().peek(0xFF01, cpu.B);
        Assertions.assertEquals(cpu.B.getValue(), 0x80);

        cpu.A.setValue(0x1F);
        cpu.BC.setValue(0xFF02);
        cpu.getMemory().poke(cpu.BC, cpu.A);
        cpu.getMemory().peek(cpu.BC, cpu.D);
        Assertions.assertEquals(cpu.D.getValue(), 0x1F);

        cpu.BC.setValue(0xFF04);
        cpu.HL.setValue(0x10_00);
        cpu.getMemory().poke(cpu.BC, cpu.HL);
        cpu.getMemory().peek(cpu.BC, cpu.DE);
        Assertions.assertEquals(cpu.DE.getValue(), 0x10_00);

        cpu.HL.setValue(0x4001);
        cpu.getMemory().poke8(cpu.HL, 123);
        val = cpu.getMemory().peek8(cpu.HL);
        Assertions.assertEquals(val, 123);

        cpu.HL.setValue(0x4500);
        cpu.getMemory().poke16(cpu.HL, 555);
        val = cpu.getMemory().peek16(cpu.HL);
        Assertions.assertEquals(val, 555);

        cpu.A.setValue(77);
        cpu.getMemory().poke(20000, cpu.A);
        val = cpu.getMemory().peek8(20000);
        Assertions.assertEquals(val, 77);

        for (int i = 16384; i < 65535; i++) {
            cpu.A.setValue(i & 0xFF);
            cpu.getMemory().poke(i, cpu.A);
        }
        for (int i = 16384; i < 65535; i++) {
            cpu.A.setValue(i & 0xFF);
            cpu.getMemory().peek(i, cpu.B);
            Assertions.assertEquals(cpu.A.getValue(), cpu.B.getValue());
        }
    }

    @Test
    void testPaging() {
        ZxProfile profile = profiles.getByName("ZX Spectrum 128K");
        Assertions.assertNotNull(profile);
        ZXSpectrum spectrum =  ZXSpectrumFactory.getInstance(profile);
        Cpu cpu = spectrum.getCpu();
        cpu.A.setValue(50);
        cpu.getMemory().poke(0xf000, cpu.A);
        cpu.A.setValue(51);
        cpu.getMemory().setPageMapping(1);
        cpu.getMemory().poke(0xf000, cpu.A);
        cpu.A.setValue(52);
        cpu.getMemory().setPageMapping(2);
        cpu.getMemory().poke(0xf000, cpu.A);
        cpu.A.setValue(53);
        cpu.getMemory().setPageMapping(3);
        cpu.getMemory().poke(0xf000, cpu.A);
        cpu.A.setValue(54);
        cpu.getMemory().setPageMapping(4);
        cpu.getMemory().poke(0xf000, cpu.A);
        cpu.A.setValue(55);
        cpu.getMemory().setPageMapping(5);
        cpu.getMemory().poke(0xf000, cpu.A);
        cpu.A.setValue(56);
        cpu.getMemory().setPageMapping(6);
        cpu.getMemory().poke(0xf000, cpu.A);
        cpu.A.setValue(57);
        cpu.getMemory().setPageMapping(7);
        cpu.getMemory().poke(0xf000, cpu.A);

        cpu.getMemory().setPageMapping(0);
        int val = cpu.getMemory().peek8(0xf000);
        Assertions.assertEquals(val, 50);

        cpu.getMemory().setPageMapping(1);
        val = cpu.getMemory().peek8(0xf000);
        Assertions.assertEquals(val, 51);

        cpu.getMemory().setPageMapping(2);
        val = cpu.getMemory().peek8(0xf000);
        Assertions.assertEquals(val, 52);

        cpu.getMemory().setPageMapping(3);
        val = cpu.getMemory().peek8(0xf000);
        Assertions.assertEquals(val, 53);

        cpu.getMemory().setPageMapping(4);
        val = cpu.getMemory().peek8(0xf000);
        Assertions.assertEquals(val, 54);

        cpu.getMemory().setPageMapping(5);
        val = cpu.getMemory().peek8(0xf000);
        Assertions.assertEquals(val, 55);

        cpu.getMemory().setPageMapping(6);
        val = cpu.getMemory().peek8(0xf000);
        Assertions.assertEquals(val, 56);

        cpu.getMemory().setPageMapping(7);
        val = cpu.getMemory().peek8(0xf000);
        Assertions.assertEquals(val, 57);
    }

    @Test
    void testPaging2() {
        ZxProfile profile = profiles.getByName("ZX Spectrum 128K");
        Assertions.assertNotNull(profile);
        ZXSpectrum spectrum =  ZXSpectrumFactory.getInstance(profile);
        Cpu cpu = spectrum.getCpu();
        cpu.A.setValue(50);
        cpu.getMemory().setPageMapping(0);
        cpu.getMemory().poke(0xf000, cpu.A);
        cpu.getMemory().setPageMapping(Memory128K.DISABLED_PAGE_SELECTING);
        cpu.getMemory().setPageMapping(5);
        cpu.getMemory().peek(0xf000, cpu.B);
        Assertions.assertEquals(cpu.B.getValue(), 50);
        cpu.getMemory().reset();
    }

    @Test
    void testPagingPlus2() {
        ZxProfile profile = profiles.getByName("ZX Spectrum +2A");
        Assertions.assertNotNull(profile);
        ZXSpectrum spectrum =  ZXSpectrumFactory.getInstance(profile);
        spectrum.reset();
        Cpu cpu = spectrum.getCpu();

        cpu.getMemory().setPageMapping((0b0000_0001 << 16));
        cpu.A.setValue(190);
        cpu.getMemory().poke(0, cpu.A);
        cpu.getMemory().peek(0, cpu.B);
        Assertions.assertNotEquals(cpu.B.getValue(), 190);
        cpu.getMemory().setPageMapping(0);
        cpu.A.setValue(191);
        cpu.getMemory().poke(5, cpu.A);
        cpu.getMemory().peek(5, cpu.B);
        Assertions.assertNotEquals(cpu.B.getValue(), 191);
    }
}
