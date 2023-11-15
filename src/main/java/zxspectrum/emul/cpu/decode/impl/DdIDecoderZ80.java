package zxspectrum.emul.cpu.decode.impl;

import lombok.NonNull;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.unit.ArithmeticLogical;
import zxspectrum.emul.cpu.unit.CallReturn;
import zxspectrum.emul.cpu.unit.CpuControl;
import zxspectrum.emul.cpu.unit.Jump;
import zxspectrum.emul.cpu.unit.LdIO;
import zxspectrum.emul.io.mem.MemoryAccess;

final class DdIDecoderZ80 extends BaseIDecoderZ80 {
    private final DdCbIDecoderZ80 ddCbIDecoderZ80;

    public DdIDecoderZ80(@NonNull Cpu cpu, @NonNull LdIO ldIO, @NonNull ArithmeticLogical al, @NonNull Jump jump
            , @NonNull CallReturn callReturn, @NonNull CpuControl cpuControl) {
        super(cpu, ldIO, al, jump, callReturn, cpuControl);
        ddCbIDecoderZ80 = new DdCbIDecoderZ80(cpu, ldIO, al, jump, callReturn, cpuControl);
    }

    @Override
    protected void execute(int code) {
        int subCode = fetch8();
        switch (subCode) {
            case 0x09:
                alU.add(cpu.IX, cpu.BC);
                break;
            case 0x19:
                alU.add(cpu.IX, cpu.DE);
                break;
            case 0x21:
                ldIOU.ld(cpu.IX, fetch16());
                break;
            case 0x22:
                ldIOU.ld(addressing.ABS.setAddress(fetch16()), cpu.IX);
                break;
            case 0x23:
                alU.inc(cpu.IX);
                break;
            case 0x29:
                alU.add(cpu.IX, cpu.IX);
                break;
            case 0x2A:
                ldIOU.ld(cpu.IX, addressing.ABS.setAddress(fetch16()));
                break;
            case 0x2B:
                alU.dec(cpu.IX);
                break;
            case 0x34:
                alU.inc(addressing.IX.setOffset(fetch8()));
                break;
            case 0x35:
                alU.dec(addressing.IX.setOffset(fetch8()));
                break;
            case 0x36:
                ldIOU.ld(addressing.IX.setOffset(fetch8()), fetch8());
                break;
            case 0x39:
                alU.add(cpu.IX, cpu.SP);
                break;
            case 0x46:
                ldIOU.ld(cpu.B, addressing.IX.setOffset(fetch8()));
                break;
            case 0x4E:
                ldIOU.ld(cpu.C, addressing.IX.setOffset(fetch8()));
                break;
            case 0x56:
                ldIOU.ld(cpu.D, addressing.IX.setOffset(fetch8()));
                break;
            case 0x5E:
                ldIOU.ld(cpu.E, addressing.IX.setOffset(fetch8()));
                break;
            case 0x66:
                ldIOU.ld(cpu.H, addressing.IX.setOffset(fetch8()));
                break;
            case 0x6E:
                ldIOU.ld(cpu.L, addressing.IX.setOffset(fetch8()));
                break;
            case 0x70:
                ldIOU.ld(addressing.IX.setOffset(fetch8()), cpu.B);
                break;
            case 0x71:
                ldIOU.ld(addressing.IX.setOffset(fetch8()), cpu.C);
                break;
            case 0x72:
                ldIOU.ld(addressing.IX.setOffset(fetch8()), cpu.D);
                break;
            case 0x73:
                ldIOU.ld(addressing.IX.setOffset(fetch8()), cpu.E);
                break;
            case 0x74:
                ldIOU.ld(addressing.IX.setOffset(fetch8()), cpu.H);
                break;
            case 0x75:
                ldIOU.ld(addressing.IX.setOffset(fetch8()), cpu.L);
                break;
            case 0x77:
                ldIOU.ld(addressing.IX.setOffset(fetch8()), cpu.A);
                break;
            case 0x7E:
                ldIOU.ld(cpu.A, addressing.IX.setOffset(fetch8()));
                break;
            case 0x86:
                alU.add(addressing.IX.setOffset(fetch8()));
                break;
            case 0x8E:
                alU.adc(addressing.IX.setOffset(fetch8()));
                break;
            case 0x96:
                alU.sub(addressing.IX.setOffset(fetch8()));
                break;
            case 0x9E:
                alU.sbc(addressing.IX.setOffset(fetch8()));
                break;
            case 0xA6:
                alU.and(addressing.IX.setOffset(fetch8()));
                break;
            case 0xAE:
                alU.xor(addressing.IX.setOffset(fetch8()));
                break;
            case 0xB6:
                alU.or(addressing.IX.setOffset(fetch8()));
                break;
            case 0xBE:
                alU.cp(addressing.IX.setOffset(fetch8()));
                break;
            case 0xCB:
                ddCbIDecoderZ80.execute(subCode);
                break;
            case 0xE1:
                memory.pop(cpu.IX);
                break;
            case 0xE3:
                ldIOU.ex(addressing.SP, cpu.IX);
                break;
            case 0xE5:
                memory.push(cpu.IX);
                break;
            case 0xE9:
                jumpU.jp(addressing.IX.noOffset());
                break;
            case 0xF9:
                cpu.SP.ld(cpu.IX);
                break;
//-------------------------------------------Undocumented--------------------------------------
            case 0x24:
                alU.inc(cpu.IXH);
                break;
            case 0x25:
                alU.dec(cpu.IXH);
                break;
            case 0x26:
                ldIOU.ld(cpu.IXH, fetch8());
                break;
            case 0x2C:
                alU.inc(cpu.IXL);
                break;
            case 0x2D:
                alU.dec(cpu.IXL);
                break;
            case 0x2E:
                ldIOU.ld(cpu.IXL, fetch8());
                break;
            case 0x44:
                cpu.B.ld(cpu.IXH);
                break;
            case 0x45:
                cpu.B.ld(cpu.IXL);
                break;
            case 0x4C:
                cpu.C.ld(cpu.IXH);
                break;
            case 0x4D:
                cpu.C.ld(cpu.IXL);
                break;
            case 0x54:
                cpu.D.ld(cpu.IXH);
                break;
            case 0x55:
                cpu.D.ld(cpu.IXL);
                break;
            case 0x5C:
                cpu.E.ld(cpu.IXH);
                break;
            case 0x5D:
                cpu.E.ld(cpu.IXL);
                break;
            case 0x60:
                cpu.IXH.ld(cpu.B);
                break;
            case 0x61:
                cpu.IXH.ld(cpu.C);
                break;
            case 0x62:
                cpu.IXH.ld(cpu.D);
                break;
            case 0x63:
                cpu.IXH.ld(cpu.E);
                break;
            case 0x64:
                cpu.IXH.ld(cpu.IXH);
                break;
            case 0x65:
                cpu.IXH.ld(cpu.IXL);
                break;
            case 0x67:
                cpu.IXH.ld(cpu.A);
                break;
            case 0x68:
                cpu.IXL.ld(cpu.B);
                break;
            case 0x69:
                cpu.IXL.ld(cpu.C);
                break;
            case 0x6A:
                cpu.IXL.ld(cpu.D);
                break;
            case 0x6B:
                cpu.IXL.ld(cpu.E);
                break;
            case 0x6C:
                cpu.IXL.ld(cpu.IXH);
                break;
            case 0x6D:
                cpu.IXL.ld(cpu.IXL);
                break;
            case 0x6F:
                cpu.IXL.ld(cpu.A);
                break;
            case 0x7C:
                cpu.A.ld(cpu.IXH);
                break;
            case 0x7D:
                cpu.A.ld(cpu.IXL);
                break;
            case 0x84:
                alU.add(cpu.IXH);
                break;
            case 0x85:
                alU.add(cpu.IXL);
                break;
            case 0x8C:
                alU.adc(cpu.IXH);
                break;
            case 0x8D:
                alU.adc(cpu.IXL);
                break;
            case 0x94:
                alU.sub(cpu.IXH);
                break;
            case 0x95:
                alU.sub(cpu.IXL);
                break;
            case 0x9C:
                alU.sbc(cpu.IXH);
                break;
            case 0x9D:
                alU.sbc(cpu.IXL);
                break;
            case 0xA4:
                alU.and(cpu.IXH);
                break;
            case 0xA5:
                alU.and(cpu.IXL);
                break;
            case 0xAC:
                alU.xor(cpu.IXH);
                break;
            case 0xAD:
                alU.xor(cpu.IXL);
                break;
            case 0xB4:
                alU.or(cpu.IXH);
                break;
            case 0xB5:
                alU.or(cpu.IXL);
                break;
            case 0xBC:
                alU.cp(cpu.IXH);
                break;
            case 0xBD:
                alU.cp(cpu.IXL);
                break;
        }
    }

    @Override
    public void setMemory(@NonNull MemoryAccess memory) {
        super.setMemory(memory);
        ddCbIDecoderZ80.setMemory(memory);
    }
}
