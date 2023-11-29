package zxspectrum.emul;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import zxspectrum.emul.cpu.Counter;
import zxspectrum.emul.cpu.unit.LdIO;
import zxspectrum.emul.cpu.unit.impl.LdIOZ80;
import zxspectrum.emul.cpu.impl.Z80;
import zxspectrum.emul.io.mem.address.Addressing;
import zxspectrum.emul.io.mem.ram.impl.Memory48K;
import zxspectrum.emul.io.port.PortIO48k;

/**
 * TestReg.
 *
 * @author Maxim Gorin
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
public class TestReg {
    private final Z80 cpu = new Z80();

    private final Memory48K mem = new Memory48K();

    private final Counter tStatesRemains = new Counter();

    {
        cpu.setMemory(mem);
        cpu.setPortIO(new PortIO48k());
    }

    private final LdIO ldIO = new LdIOZ80(cpu, tStatesRemains);

    private final Addressing addressing = new Addressing(cpu);

    @Test
    void testExx() {
        cpu.HL.setValue(0xFFFF);
        cpu.BC.setValue(0xFFFE);
        cpu.DE.setValue(0xFFFD);
        cpu.altHL.setValue(0x01);
        cpu.altBC.setValue(0x02);
        cpu.altDE.setValue(0x03);
        ldIO.exx();
        Assertions.assertEquals(cpu.HL.getValue(), 0x01);
        Assertions.assertEquals(cpu.BC.getValue(), 0x02);
        Assertions.assertEquals(cpu.DE.getValue(), 0x03);
        Assertions.assertEquals(cpu.altHL.getValue(), 0xFFFF);
        Assertions.assertEquals(cpu.altBC.getValue(), 0xFFFE);
        Assertions.assertEquals(cpu.altDE.getValue(), 0xFFFD);
    }

    @Test
    void testEx() {
        cpu.HL.setValue(11);
        cpu.DE.setValue(22);
        cpu.HL.swap(cpu.DE);
        Assertions.assertEquals(cpu.HL.getValue(), 22);
        Assertions.assertEquals(cpu.DE.getValue(), 11);

        cpu.HL.setValue(0x8000);
        cpu.SP.setValue(33);
        addressing.HL.poke(cpu.SP);
        cpu.DE.setValue(44);
        ldIO.ex(addressing.HL, cpu.DE);
        addressing.HL.peek(cpu.SP);
        Assertions.assertEquals(cpu.SP.getValue(), 44);
        Assertions.assertEquals(cpu.DE.getValue(), 33);
    }
}
