package zxspectrum.emul;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import zxspectrum.emul.cpu.impl.Z80;
import zxspectrum.emul.io.mem.address.Addressing;
import zxspectrum.emul.io.mem.ram.impl.Memory48K;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TestInstr {
    private final Z80 cpu = new Z80();

    private final Memory48K memory = new Memory48K();

    private final Addressing addressing = new Addressing(cpu);

    {
        cpu.setMemory(memory);
        addressing.setMemory(memory);
    }

    @Test
    void testAddressingRead() {
        final int ADDR = 30000;
        cpu.A.setValue(0x77);
        memory.poke(ADDR, cpu.A);
        cpu.A.setValue(0x78);
        memory.poke(ADDR + 1, cpu.A);
        cpu.A.setValue(0x79);
        memory.poke(ADDR + 2, cpu.A);

        cpu.HL.setValue(ADDR);
        addressing.HL.peek(cpu.B);
        Assertions.assertEquals(cpu.B.getValue(), 0x77);

        cpu.BC.setValue(ADDR);
        addressing.BC.peek(cpu.L);
        Assertions.assertEquals(cpu.L.getValue(), 0x77);

        cpu.DE.setValue(ADDR);
        addressing.DE.peek(cpu.C);
        Assertions.assertEquals(cpu.C.getValue(), 0x77);

        cpu.IX.setValue(ADDR);
        addressing.IX.peek(cpu.A);
        Assertions.assertEquals(cpu.A.getValue(), 0x77);

        addressing.IX.setOffset(2).peek(cpu.A);
        Assertions.assertEquals(cpu.A.getValue(), 0x79);

        cpu.IY.setValue(ADDR);
        addressing.IY.peek(cpu.A);
        Assertions.assertEquals(cpu.A.getValue(), 0x77);

        addressing.IY.setOffset(2).peek(cpu.A);
        Assertions.assertEquals(cpu.A.getValue(), 0x79);

        final int ADDR2 = 35000;
        cpu.HL.setValue(0x6666);
        memory.poke(ADDR2, cpu.HL);

        cpu.HL.setValue(0x5555);
        memory.poke(ADDR2 + 32, cpu.HL);

        addressing.HL.setAddress(ADDR2).peek(cpu.BC);
        Assertions.assertEquals(cpu.BC.getValue(), 0x6666);

        addressing.BC.setAddress(ADDR2).peek(cpu.HL);
        Assertions.assertEquals(cpu.HL.getValue(), 0x6666);

        addressing.DE.setAddress(ADDR2).peek(cpu.HL);
        Assertions.assertEquals(cpu.HL.getValue(), 0x6666);

        addressing.IX.setAddress(ADDR2).noOffset().peek(cpu.HL);
        Assertions.assertEquals(cpu.HL.getValue(), 0x6666);
        addressing.IX.setAddress(ADDR2).setOffset(32).peek(cpu.HL);
        Assertions.assertEquals(cpu.HL.getValue(), 0x5555);

        addressing.IY.setAddress(ADDR2).noOffset().peek(cpu.HL);
        Assertions.assertEquals(cpu.HL.getValue(), 0x6666);
        addressing.IY.setAddress(ADDR2).setOffset(32).peek(cpu.HL);
        Assertions.assertEquals(cpu.HL.getValue(), 0x5555);
    }

    @Test
    void testAddressingWrite() {
        final int ADDR = 40000;
        cpu.A.setValue(0x88);
        addressing.HL.setAddress(ADDR).poke(cpu.A);
        Assertions.assertEquals(memory.peek8(ADDR), 0x88);

        cpu.A.setValue(0x99);
        addressing.HL.setAddress(ADDR + 32).poke(cpu.A);

        cpu.A.setValue(0xFF);
        addressing.BC.setAddress(ADDR + 1).poke(cpu.A);
        Assertions.assertEquals(memory.peek8(ADDR + 1), 0xFF);

        addressing.DE.setAddress(ADDR + 2).poke(cpu.A);
        Assertions.assertEquals(memory.peek8(ADDR + 2), 0xFF);

        addressing.IX.setAddress(ADDR + 3).poke(cpu.A);
        Assertions.assertEquals(memory.peek8(ADDR + 3), 0xFF);

        addressing.IX.setAddress(ADDR).setOffset(32);
        Assertions.assertEquals(memory.peek8(ADDR + 32), 0x99);

        addressing.IY.setAddress(ADDR + 3).poke(cpu.A);
        Assertions.assertEquals(memory.peek8(ADDR + 3), 0xFF);

        addressing.IY.setAddress(ADDR).setOffset(32);
        Assertions.assertEquals(memory.peek8(ADDR + 32), 0x99);


        cpu.AF.setValue(0x1111);
        addressing.HL.setAddress(ADDR).poke(cpu.AF);
        Assertions.assertEquals(memory.peek16(ADDR), 0x1111);

        addressing.BC.setAddress(ADDR + 1).poke(cpu.AF);
        Assertions.assertEquals(memory.peek16(ADDR + 1), 0x1111);

        addressing.DE.setAddress(ADDR + 2).poke(cpu.AF);
        Assertions.assertEquals(memory.peek16(ADDR + 2), 0x1111);

        addressing.IX.setAddress(ADDR + 3).noOffset().poke(cpu.AF);
        Assertions.assertEquals(memory.peek16(ADDR + 3), 0x1111);

        addressing.IX.setAddress(ADDR + 4).noOffset().poke(cpu.AF);
        Assertions.assertEquals(memory.peek16(ADDR + 4), 0x1111);

        addressing.IX.setAddress(ADDR).setOffset(64).poke(cpu.AF);
        Assertions.assertEquals(memory.peek16(ADDR + 64), 0x1111);

        addressing.IX.setAddress(ADDR).setOffset(66).poke(cpu.AF);
        Assertions.assertEquals(memory.peek16(ADDR + 66), 0x1111);
    }

    @Test
    void testInstr1() {
        //cpu.getIDecoder().execute();
    }

}
