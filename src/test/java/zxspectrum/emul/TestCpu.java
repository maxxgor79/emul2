package zxspectrum.emul;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import zxspectrum.emul.cpu.Counter;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.unit.CpuControl;
import zxspectrum.emul.cpu.unit.LdIO;
import zxspectrum.emul.cpu.impl.Z80;
import zxspectrum.emul.cpu.unit.impl.CpuControlZ80;
import zxspectrum.emul.cpu.unit.impl.LdIOZ80;
import zxspectrum.emul.io.mem.MemoryAccess;
import zxspectrum.emul.io.mem.address.Addressing;
import zxspectrum.emul.io.mem.ram.impl.Memory48K;
import zxspectrum.emul.io.port.PortIO48k;
import zxspectrum.emul.machine.ZXSpectrum;
import zxspectrum.emul.machine.ZXSpectrumFactory;
import zxspectrum.emul.profile.ZxProfile;
import zxspectrum.emul.profile.ZxProfiles;

import java.io.IOException;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TestCpu {
    final static ZxProfiles profiles = ZxProfiles.getInstance();

    private static Cpu cpu;

    private static MemoryAccess mem;

    private final Counter tStatesRemains = new Counter();

    static
    {
        try {
            profiles.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ZxProfile profile = profiles.getByName("ZX Spectrum 48K");
        ZXSpectrum spectrum = ZXSpectrumFactory.getInstance(profile);
        cpu = spectrum.getCpu();
        mem = spectrum.getMemory();
        cpu.setPortIO(new PortIO48k() {
            @Override
            public int read(int port) {
                inPort = port;
                return inValue;
            }

            @Override
            public void write(int port, int value) {
                outValue = value;
                outPort = port;
            }
        });
    }

    static int inValue;

    static int inPort;

    static int outValue;

    static int outPort;

    private final LdIO ldIO = new LdIOZ80(cpu, tStatesRemains);

    private final CpuControl cpuControl = new CpuControlZ80(cpu, tStatesRemains);

    private final Addressing addressing = new Addressing(cpu);

    @Test
    void testIni() throws IOException {
        inValue = 0x7B;
        cpu.C.setValue(0x07);
        cpu.B.setValue(0x10);
        cpu.HL.setValue(0x9000);
        ldIO.ini();
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
        ldIO.ind();
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
        ldIO.outi();
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
        ldIO.outd();
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
        ldIO.ldd();
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
        ldIO.ldi();
        Assertions.assertEquals(cpu.HL.getValue(), 0x7001);
        Assertions.assertEquals(cpu.DE.getValue(), 0x6001);
        addressing.DE.setAddress(0x6000).peek(cpu.A);
        Assertions.assertEquals(cpu.A.getValue(), 0x88);
        Assertions.assertEquals(cpu.BC.getValue(), 0x06);
    }

    @Test
    void testCpl() {
        cpu.A.setValue(0b1011_0100);
        cpuControl.cpl();
        Assertions.assertEquals(cpu.A.getValue(), 0b0100_1011);
    }

    @Test
    void testCcf() {
        cpu.F.setCarry(true);
        cpuControl.ccf();
        Assertions.assertEquals(cpu.F.isCarrySet(), false);
    }

    @Test
    void testScf() {
        cpu.F.setCarry(false);
        cpuControl.scf();
        Assertions.assertEquals(cpu.F.isCarrySet(), true);
    }
}
