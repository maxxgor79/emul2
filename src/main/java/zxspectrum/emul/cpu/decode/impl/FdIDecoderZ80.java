package zxspectrum.emul.cpu.decode.impl;

import lombok.NonNull;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.unit.ArithmeticLogical;
import zxspectrum.emul.cpu.unit.CallReturn;
import zxspectrum.emul.cpu.unit.CpuControl;
import zxspectrum.emul.cpu.unit.Jump;
import zxspectrum.emul.cpu.unit.LdIO;
import zxspectrum.emul.io.mem.MemoryAccess;

final class FdIDecoderZ80 extends BaseIDecoderZ80 {
    private final FdCbIDecoderZ80 fdCbIDecoderZ80;

    public FdIDecoderZ80(@NonNull Cpu cpu, @NonNull LdIO ldIO, @NonNull ArithmeticLogical al, @NonNull Jump jump
            , @NonNull CallReturn callReturn, @NonNull CpuControl cpuControl) {
        super(cpu, ldIO, al, jump, callReturn, cpuControl);
        fdCbIDecoderZ80 = new FdCbIDecoderZ80(cpu, ldIO, al, jump, callReturn, cpuControl);
    }

    @Override
    protected void execute(int code) {
        int subCode = fetch8();
        switch (subCode) {
            case 0x09:
                alU.add(cpu.IY, cpu.BC);
                break;
            case 0x19:
                alU.add(cpu.IY, cpu.DE);
                break;
            case 0x21:
                ldIOU.ld(cpu.IY, fetch16());
                break;
            case 0x22:
                ldIOU.ld(addressing.ABS.setAddress(fetch16()), cpu.IY);
                break;
            case 0x23:
                alU.inc(cpu.IY);
                break;
            case 0x29:
                alU.add(cpu.IY, cpu.IY);
                break;
            case 0x2A:
                ldIOU.ld(cpu.IY, addressing.ABS.setAddress(fetch16()));
                break;
            case 0x2B:
                alU.dec(cpu.IY);
                break;
            case 0x34:
                alU.inc(addressing.IY.setOffset(fetch8()));
                break;
            case 0x35:
                alU.dec(addressing.IY.setOffset(fetch8()));
                break;
            case 0x36:
                ldIOU.ld(addressing.IY.setOffset(fetch8()), fetch8());
                break;
            case 0x39:
                alU.add(cpu.IY, cpu.SP);
                break;
            case 0x46:
                ldIOU.ld(cpu.B, addressing.IY.setOffset(fetch8()));
                break;
            case 0x4E:
                ldIOU.ld(cpu.C, addressing.IY.setOffset(fetch8()));
                break;
            case 0x56:
                ldIOU.ld(cpu.D, addressing.IY.setOffset(fetch8()));
                break;
            case 0x5E:
                ldIOU.ld(cpu.E, addressing.IY.setOffset(fetch8()));
                break;
            case 0x66:
                ldIOU.ld(cpu.H, addressing.IY.setOffset(fetch8()));
                break;
            case 0x6E:
                ldIOU.ld(cpu.L, addressing.IY.setOffset(fetch8()));
                break;
            case 0x70:
                ldIOU.ld(addressing.IY.setOffset(fetch8()), cpu.B);
                break;
            case 0x71:
                ldIOU.ld(addressing.IY.setOffset(fetch8()), cpu.C);
                break;
            case 0x72:
                ldIOU.ld(addressing.IY.setOffset(fetch8()), cpu.D);
                break;
            case 0x73:
                ldIOU.ld(addressing.IY.setOffset(fetch8()), cpu.E);
                break;
            case 0x74:
                ldIOU.ld(addressing.IY.setOffset(fetch8()), cpu.H);
                break;
            case 0x75:
                ldIOU.ld(addressing.IY.setOffset(fetch8()), cpu.L);
                break;
            case 0x77:
                ldIOU.ld(addressing.IY.setOffset(fetch8()), cpu.A);
                break;
            case 0x7E:
                ldIOU.ld(cpu.A, addressing.IY.setOffset(fetch8()));
                break;
            case 0x86:
                alU.add(addressing.IY.setOffset(fetch8()));
                break;
            case 0x8E:
                alU.adc(addressing.IY.setOffset(fetch8()));
                break;
            case 0x96:
                alU.sub(addressing.IY.setOffset(fetch8()));
                break;
            case 0x9E:
                alU.sbc(addressing.IY.setOffset(fetch8()));
                break;
            case 0xA6:
                alU.and(addressing.IY.setOffset(fetch8()));
                break;
            case 0xAE:
                alU.xor(addressing.IY.setOffset(fetch8()));
                break;
            case 0xB6:
                alU.or(addressing.IY.setOffset(fetch8()));
                break;
            case 0xBE:
                alU.cp(addressing.IY.setOffset(fetch8()));
                break;
            case 0xCB:
                fdCbIDecoderZ80.execute(subCode);
                break;
            case 0xE1:
                memory.pop(cpu.IY);
                break;
            case 0xE3:
                ldIOU.ex(addressing.SP, cpu.IY);
                break;
            case 0xE5:
                memory.push(cpu.IY);
                break;
            case 0xE9:
                jumpU.jp(addressing.IY.noOffset());
                break;
            case 0xF9:
                cpu.SP.ld(cpu.IY);
                break;
            //-------------------------------------------Undocumented--------------------------------------
            case 0x24:
                alU.inc(cpu.IYH);
                break;
            case 0x25:
                alU.dec(cpu.IYH);
                break;
            case 0x26:
                ldIOU.ld(cpu.IYH, fetch8());
                break;
            case 0x2C:
                alU.inc(cpu.IYL);
                break;
            case 0x2D:
                alU.dec(cpu.IYL);
                break;
            case 0x2E:
                ldIOU.ld(cpu.IYL, fetch8());
                break;
            case 0x44:
                cpu.B.ld(cpu.IYH);
                break;
            case 0x45:
                cpu.B.ld(cpu.IYL);
                break;
            case 0x4C:
                cpu.C.ld(cpu.IYH);
                break;
            case 0x4D:
                cpu.C.ld(cpu.IYL);
                break;
            case 0x54:
                cpu.D.ld(cpu.IYH);
                break;
            case 0x55:
                cpu.D.ld(cpu.IYL);
                break;
            case 0x5C:
                cpu.E.ld(cpu.IYH);
                break;
            case 0x5D:
                cpu.E.ld(cpu.IYL);
                break;
            case 0x60:
                cpu.IYH.ld(cpu.B);
                break;
            case 0x61:
                cpu.IYH.ld(cpu.C);
                break;
            case 0x62:
                cpu.IYH.ld(cpu.D);
                break;
            case 0x63:
                cpu.IYH.ld(cpu.E);
                break;
            case 0x64:
                cpu.IYH.ld(cpu.IYH);
                break;
            case 0x65:
                cpu.IYH.ld(cpu.IYL);
                break;
            case 0x67:
                cpu.IYH.ld(cpu.A);
                break;
            case 0x68:
                cpu.IYL.ld(cpu.B);
                break;
            case 0x69:
                cpu.IYL.ld(cpu.C);
                break;
            case 0x6A:
                cpu.IYL.ld(cpu.D);
                break;
            case 0x6B:
                cpu.IYL.ld(cpu.E);
                break;
            case 0x6C:
                cpu.IYL.ld(cpu.IYH);
                break;
            case 0x6D:
                cpu.IYL.ld(cpu.IYL);
                break;
            case 0x6F:
                cpu.IYL.ld(cpu.A);
                break;
            case 0x7C:
                cpu.A.ld(cpu.IYH);
                break;
            case 0x7D:
                cpu.A.ld(cpu.IYL);
                break;
            case 0x84:
                alU.add(cpu.IYH);
                break;
            case 0x85:
                alU.add(cpu.IYL);
                break;
            case 0x8C:
                alU.adc(cpu.IYH);
                break;
            case 0x8D:
                alU.adc(cpu.IYL);
                break;
            case 0x94:
                alU.sub(cpu.IYH);
                break;
            case 0x95:
                alU.sub(cpu.IYL);
                break;
            case 0x9C:
                alU.sbc(cpu.IYH);
                break;
            case 0x9D:
                alU.sbc(cpu.IYL);
                break;
            case 0xA4:
                alU.and(cpu.IYH);
                break;
            case 0xA5:
                alU.and(cpu.IYL);
                break;
            case 0xAC:
                alU.xor(cpu.IYH);
                break;
            case 0xAD:
                alU.xor(cpu.IYL);
                break;
            case 0xB4:
                alU.or(cpu.IYH);
                break;
            case 0xB5:
                alU.or(cpu.IYL);
                break;
            case 0xBC:
                alU.cp(cpu.IYH);
                break;
            case 0xBD:
                alU.cp(cpu.IYL);
                break;
        }
    }

    @Override
    public void setMemory(@NonNull MemoryAccess memory) {
        super.setMemory(memory);
        fdCbIDecoderZ80.setMemory(memory);
    }
}