package zxspectrum.emul;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import zxspectrum.emul.cpu.alu.Alu;
import zxspectrum.emul.cpu.alu.impl.AluZ80;
import zxspectrum.emul.cpu.impl.Z80;
import zxspectrum.emul.cpu.reg.RegF;
import zxspectrum.emul.io.mem.impl.Memory48K;
import zxspectrum.emul.io.port.PortIO48k;

import java.io.IOException;
import java.util.Random;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TestAlu {
    private final Z80 cpu1 = new Z80();

    private final Memory48K mem1 = new Memory48K();
    {
        cpu1.setMemory(mem1);
        cpu1.setPortIO(new PortIO48k());
    }

    private final Z80 cpu2 = new Z80();

    private final Memory48K mem2 = new Memory48K();
    {
        cpu2.setMemory(mem2);
        cpu2.setPortIO(new PortIO48k());
    }


    private Alu alu = new AluZ80(cpu1);

    void init() {
    }


    //@Test
    void testAdd8() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10000; i++) {
            int a = random.nextInt(255) & 0xFF;
            int b = random.nextInt(255) & 0xFF;
            cpu1.A.setValue(a);
            cpu1.B.setValue(b);
            int result = alu.add(cpu1.B);
            cpu2.A.setValue(a);
            Assertions.assertEquals(result, cpu2.A.getValue());
            Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
            Assertions.assertEquals(cpu1.F.isSignSet(), cpu2.F.isSignSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }
    }

    //@Test
    void testAdc8() {
        Random random = new Random(System.currentTimeMillis());
        cpu1.F.setCarry(true);
        cpu2.F.setCarry(true);
        for (int i = 0; i < 10000; i++) {
            int a = random.nextInt(255) & 0xFF;
            int b = random.nextInt(255) & 0xFF;
            cpu1.A.setValue(a);
            cpu1.B.setValue(b);
            int result = alu.adc(cpu1.B);
            cpu2.A.setValue(a);
            Assertions.assertEquals(result, cpu2.A.getValue());
            Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
            Assertions.assertEquals(cpu1.F.isSignSet(), cpu2.F.isSignSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }
    }

    //@Test
    //fail
    void testAdd16() {
        Random random = new Random(System.currentTimeMillis());
        cpu1.F.setCarry(true);
        cpu2.F.setCarry(true);
        for (int i = 0; i < 10000; i++) {
            int a = random.nextInt(0xFFFF) & 0xFFFF;
            int b = random.nextInt(0xFFFF) & 0xFFFF;
            cpu1.HL.setValue(a);
            cpu1.BC.setValue(b);
            int result = alu.adc(cpu1.HL, cpu2.BC);
            cpu2.HL.setValue(a);
            cpu2.BC.setValue(b);
            Assertions.assertEquals(result, cpu2.HL.getValue());
            Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
            Assertions.assertEquals(cpu1.F.isSignSet(), cpu2.F.isSignSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }
    }

    //@Test
    void daa() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10000; i++) {
            int a = random.nextInt(255) & 0xFF;
            cpu1.A.setValue(a);
            cpu2.A.setValue(a);
            alu.daa();
            Assertions.assertEquals(cpu1.A.getValue(), cpu2.A.getValue());
            Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
            Assertions.assertEquals(cpu1.F.isSignSet(), cpu2.F.isSignSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }
    }

    //@Test
    void testSub8() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10000; i++) {
            int a = random.nextInt(255) & 0xFF;
            int b = random.nextInt(255) & 0xFF;
            int result = alu.sub8(a, b);
            cpu2.A.setValue(a);
            Assertions.assertEquals(result, cpu2.A.getValue());
            Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
            Assertions.assertEquals(cpu1.F.isSignSet(), cpu2.F.isSignSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }
    }

    //@Test
    //fail
    void testSub16() {
        Random random = new Random(System.currentTimeMillis());
        cpu1.F.setCarry(true);
        cpu2.F.setCarry(true);
        for (int i = 0; i < 10000; i++) {
            int a = random.nextInt(0xFFFF) & 0xFFFF;
            int b = random.nextInt(0xFFFF) & 0xFFFF;
            cpu1.HL.setValue(a);
            cpu1.BC.setValue(b);
            int result = alu.sbc(cpu1.HL, cpu1.BC);
            cpu2.HL.setValue(a);
            cpu2.BC.setValue(b);
            Assertions.assertEquals(result, cpu2.HL.getValue());
            Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
            Assertions.assertEquals(cpu1.F.isSignSet(), cpu2.F.isSignSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }
    }

    //@Test
    void testAnd() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10000; i++) {
            int a = random.nextInt(255) & 0xFF;
            int b = random.nextInt(255) & 0xFF;
            cpu1.A.setValue(a);
            cpu1.B.setValue(b);
            int result = alu.and(cpu1.B);
            cpu2.A.setValue(a);
            cpu2.B.setValue(b);
            //System.out.println("a="+a+",b="+b);
            //System.out.println("cpu1 result="+result+", cpu2 result="+cpu2.A.getValue());
            Assertions.assertEquals(result, cpu2.A.getValue());
            Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
            Assertions.assertEquals(cpu1.F.isSignSet(), cpu2.F.isSignSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }
    }

    //@Test
    void testOr() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10000; i++) {
            int a = random.nextInt(255) & 0xFF;
            int b = random.nextInt(255) & 0xFF;
            cpu1.A.setValue(a);
            cpu1.B.setValue(b);
            int result = alu.or(cpu1.B);
            cpu2.A.setValue(a);
            cpu2.B.setValue(b);
            //System.out.println("a="+a+",b="+b);
            //System.out.println("cpu1 result="+result+", cpu2 result="+cpu2.A.getValue());
            Assertions.assertEquals(result, cpu2.A.getValue());
            Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
            Assertions.assertEquals(cpu1.F.isSignSet(), cpu2.F.isSignSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }
    }

    //@Test
    void testXor() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10000; i++) {
            int a = random.nextInt(255) & 0xFF;
            int b = random.nextInt(255) & 0xFF;
            cpu1.A.setValue(a);
            cpu1.B.setValue(b);
            int result = alu.xor(cpu1.B);
            cpu2.A.setValue(a);
            cpu2.B.setValue(b);
            //System.out.println("a="+a+",b="+b);
            //System.out.println("cpu1 result="+result+", cpu2 result="+cpu2.A.getValue());
            Assertions.assertEquals(result, cpu2.A.getValue());
            Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
            Assertions.assertEquals(cpu1.F.isSignSet(), cpu2.F.isSignSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }
    }

    //@Test
    void testCp() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10000; i++) {
            int a = random.nextInt(255) & 0xFF;
            int b = random.nextInt(255) & 0xFF;
            cpu1.A.setValue(a);
            cpu1.B.setValue(b);
            alu.cp(cpu1.B);
            cpu2.A.setValue(a);
            cpu2.B.setValue(b);
            //System.out.println("a="+a+",b="+b);
            //System.out.println("cpu1 result="+result+", cpu2 result="+cpu2.A.getValue());
            Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
            Assertions.assertEquals(cpu1.F.isSignSet(), cpu2.F.isSignSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }
    }

    //@Test
    void testInc8() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10000; i++) {
            int a = random.nextInt(255) & 0xFF;
            cpu1.A.setValue(a);
            alu.inc(cpu1.A);
            cpu2.A.setValue(a);
            //System.out.println("a="+a+",b="+b);
            //System.out.println("cpu1 result="+result+", cpu2 result="+cpu2.A.getValue());
            Assertions.assertEquals(cpu1.A, cpu2.A);
            Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
            Assertions.assertEquals(cpu1.F.isSignSet(), cpu2.F.isSignSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }
    }


    //@Test
    void testDec8() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10000; i++) {
            int a = random.nextInt(255) & 0xFF;
            cpu1.A.setValue(a);
            alu.dec(cpu1.A);
            cpu2.A.setValue(a);
            //System.out.println("a="+a+",b="+b);
            //System.out.println("cpu1 result="+result+", cpu2 result="+cpu2.A.getValue());
            Assertions.assertEquals(cpu1.A, cpu2.A);
            Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
            Assertions.assertEquals(cpu1.F.isSignSet(), cpu2.F.isSignSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }
    }

    //@Test
    void testFlags() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 500; i++) {
            int a = random.nextInt(255) & 0xFF;
            cpu1.A.setValue(a);
            alu.scf();
            cpu2.A.setValue(a);
            Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
            Assertions.assertEquals(cpu1.F.is3BitSet(), cpu2.F.is3BitSet());
            Assertions.assertEquals(cpu1.F.is5BitSet(), cpu2.F.is5BitSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
        }

        for (int i = 0; i < 500; i++) {
            int a = random.nextInt(255) & 0xFF;
            cpu1.A.setValue(a);
            alu.ccf();
            cpu2.A.setValue(a);
            Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
            Assertions.assertEquals(cpu1.F.is3BitSet(), cpu2.F.is3BitSet());
            Assertions.assertEquals(cpu1.F.is5BitSet(), cpu2.F.is5BitSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
        }

        for (int i = 0; i < 500; i++) {
            int a = random.nextInt(255) & 0xFF;
            cpu1.A.setValue(a);
            alu.cpl();
            cpu2.A.setValue(a);
            Assertions.assertEquals(cpu1.A, cpu2.A);
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.is3BitSet(), cpu2.F.is3BitSet());
            Assertions.assertEquals(cpu1.F.is5BitSet(), cpu2.F.is5BitSet());
        }

    }

    //@Test
    void testCPID() {
        cpu1.HL.setValue(20000);
        cpu1.BC.setValue(1000);

        cpu2.HL.setValue(20000);
        cpu2.BC.setValue(1000);
        for (int i = 0; i < 1000; i++) {
            cpu1.getAlu().cpi();
            Assertions.assertEquals(cpu1.HL, cpu2.HL);
            Assertions.assertEquals(cpu1.BC, cpu2.BC);
            Assertions.assertEquals(cpu1.F.isSignSet(), cpu2.F.isSignSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }

        cpu1.HL.setValue(40000);
        cpu1.BC.setValue(1000);

        cpu2.HL.setValue(40000);
        cpu2.BC.setValue(1000);

        for (int i = 0; i < 10000; i++) {
            cpu1.getAlu().cpd();
            Assertions.assertEquals(cpu1.HL, cpu2.HL);
            Assertions.assertEquals(cpu1.BC, cpu2.BC);
            Assertions.assertEquals(cpu1.F.isSignSet(), cpu2.F.isSignSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }
    }

    //@Test
    void testLDID() {
        cpu1.HL.setValue(20000);
        cpu1.DE.setValue(40000);
        cpu1.BC.setValue(1000);

        cpu2.HL.setValue(20000);
        cpu2.DE.setValue(40000);
        cpu2.BC.setValue(1000);
        for (int i = 0; i < 1200; i++) {
            cpu1.getCpuU().ldi();
            Assertions.assertEquals(cpu1.DE, cpu2.DE);
            Assertions.assertEquals(cpu1.HL, cpu2.HL);
            Assertions.assertEquals(cpu1.BC, cpu2.BC);
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }

        cpu1.HL.setValue(30000);
        cpu1.DE.setValue(50000);
        cpu1.BC.setValue(1000);

        cpu2.HL.setValue(30000);
        cpu2.DE.setValue(50000);
        cpu2.BC.setValue(1000);
        for (int i = 0; i < 1200; i++) {
            cpu1.getCpuU().ldd();
            Assertions.assertEquals(cpu1.DE, cpu2.DE);
            Assertions.assertEquals(cpu1.HL, cpu2.HL);
            Assertions.assertEquals(cpu1.BC, cpu2.BC);
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }
    }

    //@Test
    //failed
    void testBit() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 500; i++) {
            int a = random.nextInt(255) & 0xFF;
            cpu1.A.setValue(a);
            alu.bit(RegF.BIT_0, cpu1.A);
            cpu2.A.setValue(a);
            System.out.println("A1=" + cpu1.A.getValue());
            System.out.println("A2=" + cpu2.A.getValue());
            Assertions.assertEquals(cpu1.F.isSignSet(), cpu2.F.isSignSet());
            //Assertions.assertEquals(cpu1.F.is3BitSet(), cpu2.F.is3BitSet());
            //Assertions.assertEquals(cpu1.F.is5BitSet(), cpu2.F.is5BitSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            System.out.println("PV1=" + cpu1.F.isParityOverflowSet() + ", PV2=" + cpu2.F.isParityOverflowSet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }
        for (int i = 0; i < 500; i++) {
            int a = random.nextInt(255) & 0xFF;
            cpu1.A.setValue(a);
            alu.bit(RegF.BIT_1, cpu1.A);
            cpu2.A.setValue(a);
            Assertions.assertEquals(cpu1.F.isSignSet(), cpu2.F.isSignSet());
            //Assertions.assertEquals(cpu1.F.is3BitSet(), cpu2.F.is3BitSet());
            //Assertions.assertEquals(cpu1.F.is5BitSet(), cpu2.F.is5BitSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }
        for (int i = 0; i < 500; i++) {
            int a = random.nextInt(255) & 0xFF;
            cpu1.A.setValue(a);
            alu.bit(RegF.BIT_2, cpu1.A);
            cpu2.A.setValue(a);
            Assertions.assertEquals(cpu1.F.isSignSet(), cpu2.F.isSignSet());
            //Assertions.assertEquals(cpu1.F.is3BitSet(), cpu2.F.is3BitSet());
            //Assertions.assertEquals(cpu1.F.is5BitSet(), cpu2.F.is5BitSet());
            Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
            Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
            Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        }
    }

    //@Test
    void testIni() throws IOException {
        cpu1.HL.setValue(20000);
        cpu1.BC.setValue(256);
        cpu1.getCpuU().ini();

        cpu2.HL.setValue(20000);
        cpu2.BC.setValue(256);
        Assertions.assertEquals(cpu1.HL, cpu2.HL);
        Assertions.assertEquals(cpu1.BC, cpu2.BC);
        Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
        Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
        Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
        Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
        Assertions.assertEquals(cpu1.F.is3BitSet(), cpu2.F.is3BitSet());
        Assertions.assertEquals(cpu1.F.is5BitSet(), cpu2.F.is5BitSet());
    }

    //@Test
    void testInd() throws IOException {
        cpu1.HL.setValue(20000);
        cpu1.BC.setValue(256);
        cpu1.getCpuU().ind();

        cpu2.HL.setValue(20000);
        cpu2.BC.setValue(256);
        Assertions.assertEquals(cpu1.HL, cpu2.HL);
        Assertions.assertEquals(cpu1.BC, cpu2.BC);
        Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
        Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
        Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
        Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
        Assertions.assertEquals(cpu1.F.is3BitSet(), cpu2.F.is3BitSet());
        Assertions.assertEquals(cpu1.F.is5BitSet(), cpu2.F.is5BitSet());
    }

    //@Test
    void testOuti() throws IOException {
        cpu1.HL.setValue(20000);
        cpu1.BC.setValue(256);
        cpu1.getCpuU().outi();

        cpu2.HL.setValue(20000);
        cpu2.BC.setValue(256);
        Assertions.assertEquals(cpu1.HL, cpu2.HL);
        Assertions.assertEquals(cpu1.BC, cpu2.BC);
        Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
        Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
        Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
        Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
        Assertions.assertEquals(cpu1.F.is3BitSet(), cpu2.F.is3BitSet());
        Assertions.assertEquals(cpu1.F.is5BitSet(), cpu2.F.is5BitSet());
    }

    //@Test
    void testOutd() throws IOException {
        cpu1.HL.setValue(20000);
        cpu1.BC.setValue(256);
        cpu1.getCpuU().outd();

        cpu2.HL.setValue(20000);
        cpu2.BC.setValue(256);
        Assertions.assertEquals(cpu1.HL, cpu2.HL);
        Assertions.assertEquals(cpu1.BC, cpu2.BC);
        Assertions.assertEquals(cpu1.F.isNSet(), cpu2.F.isNSet());
        Assertions.assertEquals(cpu1.F.isHalfCarrySet(), cpu2.F.isHalfCarrySet());
        Assertions.assertEquals(cpu1.F.isParityOverflowSet(), cpu2.F.isParityOverflowSet());
        Assertions.assertEquals(cpu1.F.isZeroSet(), cpu2.F.isZeroSet());
        Assertions.assertEquals(cpu1.F.isCarrySet(), cpu2.F.isCarrySet());
        Assertions.assertEquals(cpu1.F.is3BitSet(), cpu2.F.is3BitSet());
        Assertions.assertEquals(cpu1.F.is5BitSet(), cpu2.F.is5BitSet());
    }
}
