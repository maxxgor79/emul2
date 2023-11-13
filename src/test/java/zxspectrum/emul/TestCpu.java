package zxspectrum.emul;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import zxspectrum.emul.cpu.CpuU;
import zxspectrum.emul.cpu.alu.Alu;
import zxspectrum.emul.cpu.alu.impl.AluZ80;
import zxspectrum.emul.cpu.impl.Z80;
import zxspectrum.emul.cpu.impl.Z80U;
import zxspectrum.emul.io.mem.address.Addressing;
import zxspectrum.emul.io.mem.impl.Memory48K;
import zxspectrum.emul.io.port.PortIO48k;

import java.io.IOException;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TestCpu {
    private final Z80 cpu = new Z80();

    private final Memory48K mem = new Memory48K();

    {
        cpu.setMemory(mem);
        cpu.setPortIO(new PortIO48k() {
            @Override
            public int read(int port) throws IOException {
                inPort = port;
                return inValue;
            }

            @Override
            public void write(int port, int value) throws IOException {
                outValue = value;
                outPort = port;
            }
        });
    }

    int inValue;

    int inPort;

    int outValue;

    int outPort;

    private final CpuU cpuU = new Z80U(cpu);
    private final Addressing addressing = new Addressing(cpu);

    @Test
    void testIni() throws IOException {
        inValue = 0x7B;
        cpu.C.setValue(0x07);
        cpu.B.setValue(0x10);
        cpu.HL.setValue(0x9000);
        cpuU.ini();
        Assertions.assertEquals(cpu.HL.getValue(), 0x9001);
        addressing.HL.setAddress(0x9000).peek(cpu.E);
        Assertions.assertEquals(cpu.E.getValue(), 0x7B);
        Assertions.assertEquals(cpu.B.getValue(), 0x0F);
        Assertions.assertEquals(inPort, (0x10 << 8) | 0x07);
    }

    @Test
    void testInd() throws IOException {
        inValue = 0x8B;
        cpu.C.setValue(0x08);//port
        cpu.B.setValue(0x10);
        cpu.HL.setValue(0x9100);
        cpuU.ind();
        Assertions.assertEquals(cpu.HL.getValue(), 0x90FF);
        addressing.HL.setAddress(0x9100).peek(cpu.E);
        Assertions.assertEquals(cpu.E.getValue(), 0x8B);
        Assertions.assertEquals(cpu.B.getValue(), 0x0F);
        Assertions.assertEquals(inPort, (0x10 << 8) | 0x08);
    }

    @Test
    void testOuti() throws IOException {
        cpu.C.setValue(0x07);
        cpu.B.setValue(0x10);
        cpu.HL.setValue(0x9101);
        cpu.E.setValue(0x59);
        addressing.HL.setAddress(0x9101).poke(cpu.E);
        cpuU.outi();
        Assertions.assertEquals(cpu.HL.getValue(), 0x9102);
        Assertions.assertEquals(outValue, 0x59);
        Assertions.assertEquals(cpu.B.getValue(), 0x0F);
        Assertions.assertEquals(outPort, (0x10 << 8) | 0x07);

    }

    @Test
    void testOutd() throws IOException {
        cpu.C.setValue(0x07);
        cpu.B.setValue(0x10);
        cpu.HL.setValue(0x9101);
        cpu.E.setValue(0x59);
        addressing.HL.setAddress(0x9201).poke(cpu.E);
        cpuU.outd();
        Assertions.assertEquals(cpu.HL.getValue(), 0x9200);
        Assertions.assertEquals(outValue, 0x59);
        Assertions.assertEquals(cpu.B.getValue(), 0x0F);
        Assertions.assertEquals(outPort, (0x10 << 8) | 0x07);
    }

    @Test
    void testLdd() {
        cpu.A.setValue(0x88);
        cpu.HL.setValue(0x7000);
        addressing.HL.poke(cpu.A);
        cpu.A.setValue(0x66);
        cpu.DE.setValue(0x6000);
        addressing.DE.poke(cpu.A);
        cpu.BC.setValue(0x07);
        cpuU.ldd();
        Assertions.assertEquals(cpu.HL.getValue(), 0x6FFF);
        Assertions.assertEquals(cpu.DE.getValue(), 0x5FFF);
        addressing.DE.setAddress(0x6000).peek(cpu.A);
        Assertions.assertEquals(cpu.A.getValue(), 0x88);
        Assertions.assertEquals(cpu.BC.getValue(), 0x06);
    }

    @Test
    void testLdi() {
        cpu.A.setValue(0x88);
        cpu.HL.setValue(0x7000);
        addressing.HL.poke(cpu.A);
        cpu.A.setValue(0x66);
        cpu.DE.setValue(0x6000);
        addressing.DE.poke(cpu.A);
        cpu.BC.setValue(0x07);
        cpuU.ldi();
        Assertions.assertEquals(cpu.HL.getValue(), 0x7001);
        Assertions.assertEquals(cpu.DE.getValue(), 0x6001);
        addressing.DE.setAddress(0x6000).peek(cpu.A);
        Assertions.assertEquals(cpu.A.getValue(), 0x88);
        Assertions.assertEquals(cpu.BC.getValue(), 0x06);
    }
}
