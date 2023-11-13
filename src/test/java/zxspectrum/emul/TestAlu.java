package zxspectrum.emul;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import zxspectrum.emul.cpu.alu.Alu;
import zxspectrum.emul.cpu.alu.impl.AluZ80;
import zxspectrum.emul.cpu.impl.Z80;
import zxspectrum.emul.io.mem.address.Addressing;
import zxspectrum.emul.io.mem.impl.Memory48K;
import zxspectrum.emul.io.port.PortIO48k;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TestAlu {
    private final Z80 cpu1 = new Z80();

    private final Memory48K mem1 = new Memory48K();

    {
        cpu1.setMemory(mem1);
        cpu1.setPortIO(new PortIO48k());
    }


    private Alu alu = new AluZ80(cpu1);

    private Addressing addressing = new Addressing(cpu1);

    @Test
    void testAdd() {
        cpu1.A.setValue(0x44);
        cpu1.C.setValue(0x11);
        cpu1.getAlu().add(cpu1.C);
        Assertions.assertEquals(cpu1.A.getValue(), 0x55);

        cpu1.A.setValue(0x23);
        cpu1.getAlu().add(0x33);
        Assertions.assertEquals(cpu1.A.getValue(), 0x56);

        addressing.HL.setAddress(0x8000);
        cpu1.B.setValue(0x08);
        addressing.HL.poke(cpu1.B);
        cpu1.A.setValue(0xA0);
        cpu1.getAlu().add(addressing.HL);
        Assertions.assertEquals(cpu1.A.getValue(), 0xA8);

        cpu1.A.setValue(0x11);
        addressing.IX.setAddress(0x5000);
        addressing.IX.setOffset(0x05);
        cpu1.B.setValue(0x22);
        addressing.IX.poke(cpu1.B);
        cpu1.getAlu().add(addressing.IX);
        Assertions.assertEquals(cpu1.A.getValue(), 0x33);
    }

    @Test
    void testAdc() {
        cpu1.F.setCarry(false);
        cpu1.A.setValue(0x20);
        cpu1.C.setValue(0x20);
        cpu1.getAlu().adc(cpu1.C);
        Assertions.assertEquals(cpu1.A.getValue(), 0x40);

        cpu1.A.setValue(0x16);
        cpu1.F.setCarry(true);
        addressing.HL.setAddress(0x6666);
        cpu1.B.setValue(0x10);
        addressing.HL.poke(cpu1.B);
        cpu1.getAlu().adc(addressing.HL);
        Assertions.assertEquals(cpu1.A.getValue(), 0x27);
    }

    @Test
    void testSub() {
        addressing.HL.setAddress(0x8000);
        cpu1.B.setValue(0x08);
        addressing.HL.poke(cpu1.B);
        cpu1.A.setValue(0x09);
        cpu1.getAlu().sub(addressing.HL);
        Assertions.assertEquals(cpu1.A.getValue(), 0x01);


        cpu1.A.setValue(0x29);
        cpu1.D.setValue(0x11);
        cpu1.getAlu().sub(cpu1.D);
        Assertions.assertEquals(cpu1.A.getValue(), 0x18);
    }

    @Test
    void testSbc() {
        cpu1.F.setCarry(true);
        cpu1.A.setValue(0x16);
        addressing.HL.setAddress(0x5000);
        cpu1.B.setValue(0x05);
        addressing.HL.poke(cpu1.B);
        cpu1.getAlu().sbc(addressing.HL);
        Assertions.assertEquals(cpu1.A.getValue(), 0x10);
    }

    @Test
    void testNeg() {
        cpu1.A.setValue(0b1001_1000);
        cpu1.getAlu().neg();
        Assertions.assertEquals(cpu1.A.getValue(), 0b0110_1000);
    }

    @Test
    void testAnd() {
        cpu1.A.setValue(0xC3);
        cpu1.B.setValue(0x7B);
        addressing.HL.setAddress(0x8000);
        addressing.HL.poke(cpu1.B);
        cpu1.getAlu().and(addressing.HL);
        Assertions.assertEquals(cpu1.A.getValue(), 0x43);


        cpu1.A.setValue(0xC3);
        cpu1.B.setValue(0x7B);
        cpu1.getAlu().and(cpu1.B);
        Assertions.assertEquals(cpu1.A.getValue(), 0x43);
    }

    @Test
    void testOr() {
        cpu1.A.setValue(0x0F);
        cpu1.B.setValue(0xF0);
        addressing.HL.setAddress(0x8000);
        addressing.HL.poke(cpu1.B);
        cpu1.getAlu().or(addressing.HL);
        Assertions.assertEquals(cpu1.A.getValue(), 0xFF);

        cpu1.A.setValue(0x12);
        cpu1.H.setValue(0x48);
        cpu1.getAlu().or(cpu1.H);
        Assertions.assertEquals(cpu1.A.getValue(), 0x5A);

        cpu1.A.setValue(0x12);
        cpu1.getAlu().or(0x48);
        Assertions.assertEquals(cpu1.A.getValue(), 0x5A);
    }

    @Test
    void testXor() {
        cpu1.A.setValue(0x96);
        cpu1.getAlu().xor(0x5D);
        Assertions.assertEquals(cpu1.A.getValue(), 0xCB);

        cpu1.A.setValue(0x96);
        cpu1.B.setValue(0x5D);
        addressing.HL.setAddress(0x8000);
        addressing.HL.poke(cpu1.B);
        cpu1.getAlu().xor(addressing.HL);
        Assertions.assertEquals(cpu1.A.getValue(), 0xCB);
    }

    @Test
    void testCp() {
        cpu1.F.reset();
        cpu1.A.setValue(0x63);
        cpu1.B.setValue(0x60);
        addressing.HL.setAddress(0x8000);
        addressing.HL.poke(cpu1.B);
        cpu1.getAlu().cp(addressing.HL);
        Assertions.assertEquals(cpu1.F.isNSet(), true);
        Assertions.assertEquals(cpu1.F.isParityOverflowSet(), false);

        cpu1.F.reset();
        cpu1.A.setValue(0x63);
        cpu1.E.setValue(0x60);
        cpu1.getAlu().cp(cpu1.E);
        Assertions.assertEquals(cpu1.F.isNSet(), true);
        Assertions.assertEquals(cpu1.F.isParityOverflowSet(), false);

        cpu1.F.reset();
        cpu1.A.setValue(0x63);
        cpu1.getAlu().cp(0x60);
        Assertions.assertEquals(cpu1.F.isNSet(), true);
        Assertions.assertEquals(cpu1.F.isParityOverflowSet(), false);
    }

    @Test
    void testInc() {
        cpu1.D.setValue(0x28);
        cpu1.getAlu().inc(cpu1.D);
        Assertions.assertEquals(cpu1.D.getValue(), 0x29);

        cpu1.B.setValue(0x82);
        addressing.HL.setAddress(0x8001);
        addressing.HL.poke(cpu1.B);
        cpu1.getAlu().inc(addressing.HL);
        addressing.HL.peek(cpu1.A);
        Assertions.assertEquals(cpu1.A.getValue(), 0x83);

    }

    @Test
    void testDec() {
        cpu1.D.setValue(0x2A);
        cpu1.getAlu().dec(cpu1.D);
        Assertions.assertEquals(cpu1.D.getValue(), 0x29);

        cpu1.B.setValue(0x84);
        addressing.HL.setAddress(0x8002);
        addressing.HL.poke(cpu1.B);
        cpu1.getAlu().dec(addressing.HL);
        addressing.HL.peek(cpu1.A);
        Assertions.assertEquals(cpu1.A.getValue(), 0x83);
    }

    @Test
    void testDaa() {
        cpu1.A.setValue(10);
        cpu1.getAlu().add(5);
        cpu1.getAlu().daa();
        Assertions.assertEquals(cpu1.A.getValue(), 0b0001_0101);
    }

    @Test
    void testCpl() {
        cpu1.A.setValue(0b1011_0100);
        cpu1.getAlu().cpl();
        Assertions.assertEquals(cpu1.A.getValue(), 0b0100_1011);
    }

    @Test
    void testCcf() {
        cpu1.F.setCarry(true);
        cpu1.getAlu().ccf();
        Assertions.assertEquals(cpu1.F.isCarrySet(), false);
    }

    @Test
    void testScf() {
        cpu1.F.setCarry(false);
        cpu1.getAlu().scf();
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);
    }

    @Test
    void testAdd16() {
        cpu1.HL.setValue(0x4242);
        cpu1.DE.setValue(0x1111);
        cpu1.getAlu().add(cpu1.HL, cpu1.DE);
        Assertions.assertEquals(cpu1.HL.getValue(), 0x5353);
    }

    @Test
    void testAdc16() {
        cpu1.BC.setValue(0x2222);
        cpu1.HL.setValue(0x5437);
        cpu1.F.setCarry(true);
        cpu1.getAlu().adc(cpu1.HL, cpu1.BC);
        Assertions.assertEquals(cpu1.HL.getValue(), 0x765A);
    }

    @Test
    void testSbc16() {
        cpu1.F.setCarry(true);
        cpu1.HL.setValue(0x9999);
        cpu1.DE.setValue(0x1111);
        cpu1.getAlu().sbc(cpu1.HL, cpu1.DE);
        Assertions.assertEquals(cpu1.HL.getValue(), 0x8887);
    }

    @Test
    void testAddIXIY() {
        cpu1.IX.setValue(0x3333);
        cpu1.BC.setValue(0x5555);
        cpu1.getAlu().add(cpu1.IX, cpu1.BC);
        Assertions.assertEquals(cpu1.IX.getValue(), 0x8888);
        cpu1.IY.setValue(0x2222);
        cpu1.BC.setValue(0x5555);
        cpu1.getAlu().add(cpu1.IY, cpu1.BC);
        Assertions.assertEquals(cpu1.IY.getValue(), 0x7777);
    }

    @Test
    void testInc16() {
        cpu1.HL.setValue(0x1000);
        cpu1.getAlu().inc(cpu1.HL);
        Assertions.assertEquals(cpu1.HL.getValue(), 0x1001);

        cpu1.IX.setValue(0x2000);
        cpu1.getAlu().inc(cpu1.IX);
        Assertions.assertEquals(cpu1.IX.getValue(), 0x2001);
    }

    @Test
    void testDec16() {
        cpu1.HL.setValue(0x1001);
        cpu1.getAlu().dec(cpu1.HL);
        Assertions.assertEquals(cpu1.HL.getValue(), 0x1000);

        cpu1.IX.setValue(0x2001);
        cpu1.getAlu().dec(cpu1.IX);
        Assertions.assertEquals(cpu1.IX.getValue(), 0x2000);
    }

    @Test
    void testRlc() {
        cpu1.F.reset();
        cpu1.A.setValue(0b1000_1000);
        cpu1.getAlu().rlc(cpu1.A);
        Assertions.assertEquals(cpu1.A.getValue(), 0b0001_0001);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);

        cpu1.F.reset();
        cpu1.A.setValue(0b1000_1000);
        addressing.HL.setAddress(0x8003);
        addressing.HL.poke(cpu1.A);
        cpu1.getAlu().rlc(addressing.HL);
        addressing.HL.peek(cpu1.B);
        Assertions.assertEquals(cpu1.B.getValue(), 0b0001_0001);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);
    }

    @Test
    void testRl() {
        cpu1.F.setCarry(true);
        cpu1.A.setValue(0b0111_0110);
        cpu1.getAlu().rl(cpu1.A);
        Assertions.assertEquals(cpu1.A.getValue(), 0b1110_1101);
        Assertions.assertEquals(cpu1.F.isCarrySet(), false);

        cpu1.F.setCarry(true);
        cpu1.A.setValue(0b0111_0110);
        addressing.HL.setAddress(0x8004);
        addressing.HL.poke(cpu1.A);
        cpu1.getAlu().rl(addressing.HL);
        addressing.HL.peek(cpu1.B);
        Assertions.assertEquals(cpu1.B.getValue(), 0b1110_1101);
        Assertions.assertEquals(cpu1.F.isCarrySet(), false);
    }

    @Test
    void testRrc() {
        cpu1.F.reset();
        cpu1.A.setValue(0b0001_0001);
        cpu1.getAlu().rrc(cpu1.A);
        Assertions.assertEquals(cpu1.A.getValue(), 0b1000_1000);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);

        cpu1.F.reset();
        cpu1.A.setValue(0b0001_0001);
        addressing.HL.setAddress(0x8005);
        addressing.HL.poke(cpu1.A);
        cpu1.getAlu().rrc(addressing.HL);
        addressing.HL.peek(cpu1.B);
        Assertions.assertEquals(cpu1.B.getValue(), 0b1000_1000);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);
    }

    @Test
    void testRr() {
        cpu1.F.setCarry(false);
        cpu1.A.setValue(0b1110_0001);
        cpu1.getAlu().rr(cpu1.A);
        Assertions.assertEquals(cpu1.A.getValue(), 0b0111_0000);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);

        cpu1.F.setCarry(false);
        cpu1.A.setValue(0b1101_1101);
        addressing.HL.setAddress(0x8005);
        addressing.HL.poke(cpu1.A);
        cpu1.getAlu().rr(addressing.HL);
        addressing.HL.peek(cpu1.B);
        Assertions.assertEquals(cpu1.B.getValue(), 0b0110_1110);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);
    }

    @Test
    void testSla() {
        cpu1.F.reset();
        cpu1.L.setValue(0b1011_0001);
        cpu1.getAlu().sla(cpu1.L);
        Assertions.assertEquals(cpu1.L.getValue(), 0b0110_0010);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);

        cpu1.F.reset();
        cpu1.A.setValue(0b1011_0001);
        addressing.HL.setAddress(0x8006);
        addressing.HL.poke(cpu1.A);
        cpu1.getAlu().sla(addressing.HL);
        addressing.HL.peek(cpu1.B);
        Assertions.assertEquals(cpu1.B.getValue(), 0b0110_0010);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);
    }

    @Test
    void testSra() {
        cpu1.F.reset();
        cpu1.L.setValue(0b1011_1000);
        cpu1.getAlu().sra(cpu1.L);
        Assertions.assertEquals(cpu1.L.getValue(), 0b1101_1100);
        Assertions.assertEquals(cpu1.F.isCarrySet(), false);

        cpu1.F.reset();
        cpu1.A.setValue(0b1011_1000);
        addressing.IX.setAddress(0x8000).setOffset(7).poke(cpu1.A);
        cpu1.getAlu().sra(addressing.IX);
        addressing.IX.peek(cpu1.B);
        Assertions.assertEquals(cpu1.B.getValue(), 0b1101_1100);
        Assertions.assertEquals(cpu1.F.isCarrySet(), false);
    }

    @Test
    void testSrl() {
        cpu1.F.reset();
        cpu1.B.setValue(0b1000_1111);
        cpu1.getAlu().srl(cpu1.B);
        Assertions.assertEquals(cpu1.B.getValue(), 0b0100_0111);
        Assertions.assertEquals(cpu1.F.isCarrySet(), true);

        cpu1.F.reset();
        cpu1.A.setValue(0b1000_1111);
        addressing.IX.setAddress(0x8000).setOffset(8).poke(cpu1.A);
        cpu1.getAlu().srl(addressing.IX);
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
        cpu1.getAlu().rld();
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
        cpu1.getAlu().rrd();
        addressing.HL.peek(cpu1.B);
        Assertions.assertEquals(cpu1.A.getValue(), 0b1000_0000);
        Assertions.assertEquals(cpu1.B.getValue(), 0b0100_0010);
    }
}
