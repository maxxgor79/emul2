package zxspectrum.emul;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import zxspectrum.emul.cpu.unit.ArithmeticLogical;
import zxspectrum.emul.cpu.unit.impl.ArithmeticLogicalZ80;
import zxspectrum.emul.cpu.impl.Z80;
import zxspectrum.emul.cpu.reg.Const;
import zxspectrum.emul.io.mem.address.Addressing;
import zxspectrum.emul.io.mem.impl.Memory48K;
import zxspectrum.emul.io.port.PortIO48k;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TestAL {
    private final Z80 cpu1 = new Z80();

    private final Memory48K mem1 = new Memory48K();

    {
        cpu1.setMemory(mem1);
        cpu1.setPortIO(new PortIO48k());
    }


    private ArithmeticLogical arithmeticLogical = new ArithmeticLogicalZ80(cpu1);

    private Addressing addressing = new Addressing(cpu1);

    @Test
    void testAdd() {
        cpu1.A.setValue(0x44);
        cpu1.C.setValue(0x11);
        cpu1.getArithmeticLogical().add(cpu1.C);
        Assertions.assertEquals(cpu1.A.getValue(), 0x55);

        cpu1.A.setValue(0x23);
        arithmeticLogical.add(0x33);
        Assertions.assertEquals(cpu1.A.getValue(), 0x56);

        addressing.HL.setAddress(0x8000);
        cpu1.B.setValue(0x08);
        addressing.HL.poke(cpu1.B);
        cpu1.A.setValue(0xA0);
        arithmeticLogical.add(addressing.HL);
        Assertions.assertEquals(cpu1.A.getValue(), 0xA8);

        cpu1.A.setValue(0x11);
        addressing.IX.setAddress(0x5000);
        addressing.IX.setOffset(0x05);
        cpu1.B.setValue(0x22);
        addressing.IX.poke(cpu1.B);
        arithmeticLogical.add(addressing.IX);
        Assertions.assertEquals(cpu1.A.getValue(), 0x33);
    }

    @Test
    void testAdc() {
        cpu1.F.setCarry(false);
        cpu1.A.setValue(0x20);
        cpu1.C.setValue(0x20);
        arithmeticLogical.adc(cpu1.C);
        Assertions.assertEquals(cpu1.A.getValue(), 0x40);

        cpu1.A.setValue(0x16);
        cpu1.F.setCarry(true);
        addressing.HL.setAddress(0x6666);
        cpu1.B.setValue(0x10);
        addressing.HL.poke(cpu1.B);
        arithmeticLogical.adc(addressing.HL);
        Assertions.assertEquals(cpu1.A.getValue(), 0x27);
    }

    @Test
    void testSub() {
        addressing.HL.setAddress(0x8000);
        cpu1.B.setValue(0x08);
        addressing.HL.poke(cpu1.B);
        cpu1.A.setValue(0x09);
        arithmeticLogical.sub(addressing.HL);
        Assertions.assertEquals(cpu1.A.getValue(), 0x01);


        cpu1.A.setValue(0x29);
        cpu1.D.setValue(0x11);
        arithmeticLogical.sub(cpu1.D);
        Assertions.assertEquals(cpu1.A.getValue(), 0x18);
    }

    @Test
    void testSbc() {
        cpu1.F.setCarry(true);
        cpu1.A.setValue(0x16);
        addressing.HL.setAddress(0x5000);
        cpu1.B.setValue(0x05);
        addressing.HL.poke(cpu1.B);
        arithmeticLogical.sbc(addressing.HL);
        Assertions.assertEquals(cpu1.A.getValue(), 0x10);
    }

    @Test
    void testNeg() {
        cpu1.A.setValue(0b1001_1000);
        arithmeticLogical.neg();
        Assertions.assertEquals(cpu1.A.getValue(), 0b0110_1000);
    }

    @Test
    void testAnd() {
        cpu1.A.setValue(0xC3);
        cpu1.B.setValue(0x7B);
        addressing.HL.setAddress(0x8000);
        addressing.HL.poke(cpu1.B);
        arithmeticLogical.and(addressing.HL);
        Assertions.assertEquals(cpu1.A.getValue(), 0x43);


        cpu1.A.setValue(0xC3);
        cpu1.B.setValue(0x7B);
        arithmeticLogical.and(cpu1.B);
        Assertions.assertEquals(cpu1.A.getValue(), 0x43);
    }

    @Test
    void testOr() {
        cpu1.A.setValue(0x0F);
        cpu1.B.setValue(0xF0);
        addressing.HL.setAddress(0x8000);
        addressing.HL.poke(cpu1.B);
        arithmeticLogical.or(addressing.HL);
        Assertions.assertEquals(cpu1.A.getValue(), 0xFF);

        cpu1.A.setValue(0x12);
        cpu1.H.setValue(0x48);
        arithmeticLogical.or(cpu1.H);
        Assertions.assertEquals(cpu1.A.getValue(), 0x5A);

        cpu1.A.setValue(0x12);
        arithmeticLogical.or(0x48);
        Assertions.assertEquals(cpu1.A.getValue(), 0x5A);
    }

    @Test
    void testXor() {
        cpu1.A.setValue(0x96);
        arithmeticLogical.xor(0x5D);
        Assertions.assertEquals(cpu1.A.getValue(), 0xCB);

        cpu1.A.setValue(0x96);
        cpu1.B.setValue(0x5D);
        addressing.HL.setAddress(0x8000);
        addressing.HL.poke(cpu1.B);
        arithmeticLogical.xor(addressing.HL);
        Assertions.assertEquals(cpu1.A.getValue(), 0xCB);
    }

    @Test
    void testCp() {
        cpu1.F.reset();
        cpu1.A.setValue(0x63);
        cpu1.B.setValue(0x60);
        addressing.HL.setAddress(0x8000);
        addressing.HL.poke(cpu1.B);
        arithmeticLogical.cp(addressing.HL);
        Assertions.assertEquals(cpu1.F.isNSet(), true);
        Assertions.assertEquals(cpu1.F.isParityOverflowSet(), false);

        cpu1.F.reset();
        cpu1.A.setValue(0x63);
        cpu1.E.setValue(0x60);
        arithmeticLogical.cp(cpu1.E);
        Assertions.assertEquals(cpu1.F.isNSet(), true);
        Assertions.assertEquals(cpu1.F.isParityOverflowSet(), false);

        cpu1.F.reset();
        cpu1.A.setValue(0x63);
        arithmeticLogical.cp(0x60);
        Assertions.assertEquals(cpu1.F.isNSet(), true);
        Assertions.assertEquals(cpu1.F.isParityOverflowSet(), false);
    }

    @Test
    void testInc() {
        cpu1.D.setValue(0x28);
        arithmeticLogical.inc(cpu1.D);
        Assertions.assertEquals(cpu1.D.getValue(), 0x29);

        cpu1.B.setValue(0x82);
        addressing.HL.setAddress(0x8001);
        addressing.HL.poke(cpu1.B);
        arithmeticLogical.inc(addressing.HL);
        addressing.HL.peek(cpu1.A);
        Assertions.assertEquals(cpu1.A.getValue(), 0x83);

    }

    @Test
    void testDec() {
        cpu1.D.setValue(0x2A);
        arithmeticLogical.dec(cpu1.D);
        Assertions.assertEquals(cpu1.D.getValue(), 0x29);

        cpu1.B.setValue(0x84);
        addressing.HL.setAddress(0x8002);
        addressing.HL.poke(cpu1.B);
        arithmeticLogical.dec(addressing.HL);
        addressing.HL.peek(cpu1.A);
        Assertions.assertEquals(cpu1.A.getValue(), 0x83);
    }

    @Test
    void testDaa() {
        cpu1.A.setValue(10);
        arithmeticLogical.add(5);
        arithmeticLogical.daa();
        Assertions.assertEquals(cpu1.A.getValue(), 0b0001_0101);
    }

    @Test
    void testAdd16() {
        cpu1.HL.setValue(0x4242);
        cpu1.DE.setValue(0x1111);
        arithmeticLogical.add(cpu1.HL, cpu1.DE);
        Assertions.assertEquals(cpu1.HL.getValue(), 0x5353);
    }

    @Test
    void testAdc16() {
        cpu1.BC.setValue(0x2222);
        cpu1.HL.setValue(0x5437);
        cpu1.F.setCarry(true);
        arithmeticLogical.adc(cpu1.HL, cpu1.BC);
        Assertions.assertEquals(cpu1.HL.getValue(), 0x765A);
    }

    @Test
    void testSbc16() {
        cpu1.F.setCarry(true);
        cpu1.HL.setValue(0x9999);
        cpu1.DE.setValue(0x1111);
        arithmeticLogical.sbc(cpu1.HL, cpu1.DE);
        Assertions.assertEquals(cpu1.HL.getValue(), 0x8887);
    }

    @Test
    void testAddIXIY() {
        cpu1.IX.setValue(0x3333);
        cpu1.BC.setValue(0x5555);
        arithmeticLogical.add(cpu1.IX, cpu1.BC);
        Assertions.assertEquals(cpu1.IX.getValue(), 0x8888);
        cpu1.IY.setValue(0x2222);
        cpu1.BC.setValue(0x5555);
        arithmeticLogical.add(cpu1.IY, cpu1.BC);
        Assertions.assertEquals(cpu1.IY.getValue(), 0x7777);
    }

    @Test
    void testInc16() {
        cpu1.HL.setValue(0x1000);
        arithmeticLogical.inc(cpu1.HL);
        Assertions.assertEquals(cpu1.HL.getValue(), 0x1001);

        cpu1.IX.setValue(0x2000);
        arithmeticLogical.inc(cpu1.IX);
        Assertions.assertEquals(cpu1.IX.getValue(), 0x2001);
    }

    @Test
    void testDec16() {
        cpu1.HL.setValue(0x1001);
        arithmeticLogical.dec(cpu1.HL);
        Assertions.assertEquals(cpu1.HL.getValue(), 0x1000);

        cpu1.IX.setValue(0x2001);
        arithmeticLogical.dec(cpu1.IX);
        Assertions.assertEquals(cpu1.IX.getValue(), 0x2000);
    }

    @Test
    void testRlc() {
        cpu1.F.reset();
        cpu1.A.setValue(0b1000_1000);
        arithmeticLogical.rlc(cpu1.A);
        Assertions.assertEquals(cpu1.A.getValue(), 0b0001_0001);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);

        cpu1.F.reset();
        cpu1.A.setValue(0b1000_1000);
        addressing.HL.setAddress(0x8003);
        addressing.HL.poke(cpu1.A);
        arithmeticLogical.rlc(addressing.HL);
        addressing.HL.peek(cpu1.B);
        Assertions.assertEquals(cpu1.B.getValue(), 0b0001_0001);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);
    }

    @Test
    void testRl() {
        cpu1.F.setCarry(true);
        cpu1.A.setValue(0b0111_0110);
        arithmeticLogical.rl(cpu1.A);
        Assertions.assertEquals(cpu1.A.getValue(), 0b1110_1101);
        Assertions.assertEquals(cpu1.F.isCarrySet(), false);

        cpu1.F.setCarry(true);
        cpu1.A.setValue(0b0111_0110);
        addressing.HL.setAddress(0x8004);
        addressing.HL.poke(cpu1.A);
        arithmeticLogical.rl(addressing.HL);
        addressing.HL.peek(cpu1.B);
        Assertions.assertEquals(cpu1.B.getValue(), 0b1110_1101);
        Assertions.assertEquals(cpu1.F.isCarrySet(), false);
    }

    @Test
    void testRrc() {
        cpu1.F.reset();
        cpu1.A.setValue(0b0001_0001);
        arithmeticLogical.rrc(cpu1.A);
        Assertions.assertEquals(cpu1.A.getValue(), 0b1000_1000);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);

        cpu1.F.reset();
        cpu1.A.setValue(0b0001_0001);
        addressing.HL.setAddress(0x8005);
        addressing.HL.poke(cpu1.A);
        arithmeticLogical.rrc(addressing.HL);
        addressing.HL.peek(cpu1.B);
        Assertions.assertEquals(cpu1.B.getValue(), 0b1000_1000);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);
    }

    @Test
    void testRr() {
        cpu1.F.setCarry(false);
        cpu1.A.setValue(0b1110_0001);
        arithmeticLogical.rr(cpu1.A);
        Assertions.assertEquals(cpu1.A.getValue(), 0b0111_0000);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);

        cpu1.F.setCarry(false);
        cpu1.A.setValue(0b1101_1101);
        addressing.HL.setAddress(0x8005);
        addressing.HL.poke(cpu1.A);
        arithmeticLogical.rr(addressing.HL);
        addressing.HL.peek(cpu1.B);
        Assertions.assertEquals(cpu1.B.getValue(), 0b0110_1110);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);
    }

    @Test
    void testSla() {
        cpu1.F.reset();
        cpu1.L.setValue(0b1011_0001);
        arithmeticLogical.sla(cpu1.L);
        Assertions.assertEquals(cpu1.L.getValue(), 0b0110_0010);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);

        cpu1.F.reset();
        cpu1.A.setValue(0b1011_0001);
        addressing.HL.setAddress(0x8006);
        addressing.HL.poke(cpu1.A);
        arithmeticLogical.sla(addressing.HL);
        addressing.HL.peek(cpu1.B);
        Assertions.assertEquals(cpu1.B.getValue(), 0b0110_0010);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);
    }

    @Test
    void testSra() {
        cpu1.F.reset();
        cpu1.L.setValue(0b1011_1000);
        arithmeticLogical.sra(cpu1.L);
        Assertions.assertEquals(cpu1.L.getValue(), 0b1101_1100);
        Assertions.assertEquals(cpu1.F.isCarrySet(), false);

        cpu1.F.reset();
        cpu1.A.setValue(0b1011_1000);
        addressing.IX.setAddress(0x8000).setOffset(7).poke(cpu1.A);
        arithmeticLogical.sra(addressing.IX);
        addressing.IX.peek(cpu1.B);
        Assertions.assertEquals(cpu1.B.getValue(), 0b1101_1100);
        Assertions.assertEquals(cpu1.F.isCarrySet(), false);
    }

    @Test
    void testSrl() {
        cpu1.F.reset();
        cpu1.B.setValue(0b1000_1111);
        arithmeticLogical.srl(cpu1.B);
        Assertions.assertEquals(cpu1.B.getValue(), 0b0100_0111);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);

        cpu1.F.reset();
        cpu1.A.setValue(0b1000_1111);
        addressing.IX.setAddress(0x8000).setOffset(8).poke(cpu1.A);
        arithmeticLogical.srl(addressing.IX);
        addressing.IX.peek(cpu1.B);
        Assertions.assertEquals(cpu1.B.getValue(), 0b0100_0111);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);
    }

    @Test
    void testRld() {
        cpu1.A.setValue(0b0111_1010);
        cpu1.B.setValue(0b0011_0001);
        addressing.HL.setAddress(0x8500);
        addressing.HL.poke(cpu1.B);
        arithmeticLogical.rld();
        addressing.HL.peek(cpu1.B);
        Assertions.assertEquals(cpu1.A.getValue(), 0b0111_0011);
        Assertions.assertEquals(cpu1.B.getValue(), 0b0001_1010);
    }

    @Test
    void testRrd() {
        cpu1.A.setValue(0b1000_0100);
        cpu1.B.setValue(0b0010_0000);
        addressing.HL.setAddress(0x8600);
        addressing.HL.poke(cpu1.B);
        arithmeticLogical.rrd();
        addressing.HL.peek(cpu1.B);
        Assertions.assertEquals(cpu1.A.getValue(), 0b1000_0000);
        Assertions.assertEquals(cpu1.B.getValue(), 0b0100_0010);
    }

    @Test
    void testBit() {
        cpu1.F.reset();
        cpu1.B.setValue(0b1111_1011);
        arithmeticLogical.bit(Const.BIT_2, cpu1.B);
        Assertions.assertEquals(cpu1.F.isZeroSet(), true);
        Assertions.assertEquals(cpu1.F.isHalfCarrySet(), true);
        Assertions.assertEquals(cpu1.F.isNSet(), false);


        cpu1.F.reset();
        cpu1.B.setValue(0b1000_0000);
        addressing.HL.setAddress(0x8010).poke(cpu1.B);
        arithmeticLogical.bit(Const.BIT_7, addressing.HL);

        Assertions.assertEquals(cpu1.F.isZeroSet(), false);
        Assertions.assertEquals(cpu1.F.isHalfCarrySet(), true);
        Assertions.assertEquals(cpu1.F.isNSet(), false);
    }

    @Test
    void testSet() {
        cpu1.L.setValue(0b0000_0000);
        arithmeticLogical.set(Const.BIT_2, cpu1.L);
        Assertions.assertEquals(cpu1.L.getValue(), 0b0000_0100);

        cpu1.B.setValue(0b0000_0000);
        addressing.HL.setAddress(0x8011).poke(cpu1.B);
        arithmeticLogical.set(Const.BIT_5, addressing.HL);
        addressing.HL.peek(cpu1.L);
        Assertions.assertEquals(cpu1.L.getValue(), 0b0010_0000);
    }

    @Test
    void testRes() {
        cpu1.L.setValue(0xFF);
        arithmeticLogical.res(Const.BIT_4, cpu1.L);
        Assertions.assertEquals(cpu1.L.getValue(), 0b1110_1111);

        cpu1.B.setValue(0xFF);
        addressing.HL.setAddress(0x8011).poke(cpu1.B);
        arithmeticLogical.res(Const.BIT_3, addressing.HL);
        addressing.HL.peek(cpu1.L);
        Assertions.assertEquals(cpu1.L.getValue(), 0b1111_0111);
    }

    @Test
    void testCpi() {
        cpu1.A.setValue(0x3B);
        addressing.HL.setAddress(0x6000).poke(cpu1.A);
        cpu1.HL.setValue(0x6000);
        cpu1.BC.setValue(1);
        arithmeticLogical.cpi();
        Assertions.assertEquals(cpu1.HL.getValue(), 0x6001);
        Assertions.assertEquals(cpu1.BC.getValue(), 0);
        Assertions.assertEquals(cpu1.F.isZeroSet(), true);
        Assertions.assertEquals(cpu1.F.isParityOverflowSet(), false);
    }

    @Test
    void testCpd() {
        cpu1.A.setValue(0x2B);
        addressing.HL.setAddress(0x6001).poke(cpu1.A);
        cpu1.HL.setValue(0x6001);
        cpu1.BC.setValue(1);
        arithmeticLogical.cpd();
        Assertions.assertEquals(cpu1.HL.getValue(), 0x6000);
        Assertions.assertEquals(cpu1.BC.getValue(), 0);
        Assertions.assertEquals(cpu1.F.isZeroSet(), true);
        Assertions.assertEquals(cpu1.F.isParityOverflowSet(), false);
    }
}
