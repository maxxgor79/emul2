package zxspectrum.emul.cpu.decoder.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import zxspectrum.emul.cpu.Counter;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.reg.Const;
import zxspectrum.emul.cpu.unit.ArithmeticLogical;
import zxspectrum.emul.cpu.unit.CallReturn;
import zxspectrum.emul.cpu.unit.CpuControl;
import zxspectrum.emul.cpu.unit.Jump;
import zxspectrum.emul.cpu.unit.LdIO;
import zxspectrum.emul.io.mem.address.IdxAddress;

@Slf4j
final class FdCbInstDecoderZ80 extends BaseInstDecoderZ80 implements Const {
    private final Counter tStatesRemains;

    FdCbInstDecoderZ80(@NonNull Cpu cpu, @NonNull final Counter tStatesRemains, @NonNull LdIO ldIO
            , @NonNull ArithmeticLogical al, @NonNull Jump jump, @NonNull CallReturn callReturn
            , @NonNull CpuControl cpuControl) {
        super(cpu, ldIO, al, jump, callReturn, cpuControl);
        this.tStatesRemains = tStatesRemains;
    }

    @Override
    void execute(final int code) {
        final IdxAddress address = addressing.IY.setOffset(fetch8());
        final int subCode = fetch8();
        tStatesRemains.inc(3 + 4);
        switch (subCode) {
            case 0x06:
                alU.rlc(address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0x0E:
                alU.rrc(address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0x16:
                alU.rl(address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0x1E:
                alU.rr(address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0x26:
                alU.sla(address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0x2E:
                alU.sra(address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0x3E:
                alU.srl(address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0x46:
                alU.bit(BIT_0, address);
                tStatesRemains.inc(5);
                break;
            case 0x4E:
                alU.bit(BIT_1, address);
                tStatesRemains.inc(5);
                break;
            case 0x56:
                alU.bit(BIT_2, address);
                tStatesRemains.inc(5);
                break;
            case 0x5E:
                alU.bit(BIT_3, address);
                tStatesRemains.inc(5);
                break;
            case 0x66:
                alU.bit(BIT_4, address);
                tStatesRemains.inc(5);
                break;
            case 0x6E:
                alU.bit(BIT_5, address);
                tStatesRemains.inc(5);
                break;
            case 0x76:
                alU.bit(BIT_6, address);
                tStatesRemains.inc(5);
                break;
            case 0x7E:
                alU.bit(BIT_7, address);
                tStatesRemains.inc(5);
                break;
            case 0x86:
                alU.res(BIT_0, address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0x8E:
                alU.res(BIT_1, address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0x96:
                alU.res(BIT_2, address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0x9E:
                alU.res(BIT_3, address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0xA6:
                alU.res(BIT_4, address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0xAE:
                alU.res(BIT_5, address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0xB6:
                alU.res(BIT_6, address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0xBE:
                alU.res(BIT_7, address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0xC6:
                alU.set(BIT_0, address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0xCE:
                alU.set(BIT_1, address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0xD6:
                alU.set(BIT_2, address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0xDE:
                alU.set(BIT_3, address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0xE6:
                alU.set(BIT_4, address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0xEE:
                alU.set(BIT_5, address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0xF6:
                alU.set(BIT_6, address);
                tStatesRemains.inc(5 + 3);
                break;
            case 0xFE:
                alU.set(BIT_7, address);
                tStatesRemains.inc(5 + 3);
                break;
//------------------------------Undocumented---------------------------------
            case 0x00:
                alU.rlc(cpu.B, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x01:
                alU.rlc(cpu.C, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x02:
                alU.rlc(cpu.D, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x03:
                alU.rlc(cpu.E, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x04:
                alU.rlc(cpu.H, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x05:
                alU.rlc(cpu.L, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x07:
                alU.rlc(cpu.A, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x08:
                alU.rrc(cpu.B, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x10:
                alU.rl(cpu.B, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x18:
                alU.rr(cpu.B, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x20:
                alU.sla(cpu.B, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x28:
                alU.sra(cpu.B, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x30:
                alU.sll(cpu.B, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x36:
                alU.sl1(address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x38:
                alU.srl(cpu.B, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x80:
                alU.res(cpu.B, BIT_0, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x88:
                alU.res(cpu.B, BIT_1, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x90:
                alU.res(cpu.B, BIT_2, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0x98:
                alU.res(cpu.B, BIT_3, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0xA0:
                alU.res(cpu.B, BIT_4, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0xA8:
                alU.res(cpu.B, BIT_5, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0xB0:
                alU.res(cpu.B, BIT_6, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0xB8:
                alU.res(cpu.B, BIT_7, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0xC0:
                alU.set(cpu.B, BIT_0, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0xC8:
                alU.set(cpu.B, BIT_1, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0xD0:
                alU.set(cpu.B, BIT_2, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0xD8:
                alU.set(cpu.B, BIT_3, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0xE0:
                alU.set(cpu.B, BIT_4, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0xE8:
                alU.set(cpu.B, BIT_5, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0xF0:
                alU.set(cpu.B, BIT_6, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
            case 0xF8:
                alU.set(cpu.B, BIT_7, address);
                tStatesRemains.inc(5 + 3 + 5 + 3);
                break;
        }
    }
}
