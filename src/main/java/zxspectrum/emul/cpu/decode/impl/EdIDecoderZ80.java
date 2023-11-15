package zxspectrum.emul.cpu.decode.impl;

import lombok.NonNull;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.unit.ArithmeticLogical;
import zxspectrum.emul.cpu.unit.CallReturn;
import zxspectrum.emul.cpu.unit.CpuControl;
import zxspectrum.emul.cpu.unit.Jump;
import zxspectrum.emul.cpu.unit.LdIO;

final class EdIDecoderZ80 extends BaseIDecoderZ80 {

    public EdIDecoderZ80(@NonNull Cpu cpu, @NonNull LdIO ldIO, @NonNull ArithmeticLogical al, @NonNull Jump jump, @NonNull CallReturn callReturn, @NonNull CpuControl cpuControl) {
        super(cpu, ldIO, al, jump, callReturn, cpuControl);
    }

    @Override
    protected void execute(int code) {
        int subCode = fetch8();
        switch (subCode) {
            case 0x40:
                ldIOU.in(cpu.B, cpu.BC);
                break;
            case 0x41:
                ldIOU.out(cpu.BC, cpu.B);
                break;
            case 0x42:
                alU.sbc(cpu.HL, cpu.BC);
                break;
            case 0x43:
                ldIOU.ld(addressing.ABS.setAddress(fetch16()), cpu.BC);
                break;
            case 0x44:
                alU.neg();
                break;
            case 0x45:
                callReturnU.retN();
                break;
            case 0x46:
                cpuControlU.im0();
                break;
            case 0x47:
                ldIOU.ld(cpu.I, cpu.A);
                break;
            case 0x48:
                ldIOU.in(cpu.C, cpu.BC);
                break;
            case 0x49:
                ldIOU.out(cpu.BC, cpu.C);
                break;
            case 0x4A:
                alU.adc(cpu.HL, cpu.BC);
                break;
            case 0x4B:
                ldIOU.ld(cpu.BC, addressing.ABS.setAddress(fetch16()));
                break;
            case 0x4D:
                callReturnU.retI();
                break;
            case 0x4F:
                ldIOU.ld(cpu.R, cpu.A);
                break;
            case 0x50:
                ldIOU.in(cpu.D, cpu.BC);
                break;
            case 0x51:
                ldIOU.out(cpu.BC, cpu.D);
                break;
            case 0x52:
                alU.sbc(cpu.HL, cpu.DE);
                break;
            case 0x53:
                ldIOU.ld(addressing.ABS.setAddress(fetch16()), cpu.DE);
                break;
            case 0x56:
                cpuControlU.im1();
                break;
            case 0x57:
                ldIOU.ld(cpu.A, cpu.I);
                break;
            case 0x58:
                ldIOU.in(cpu.E, cpu.BC);
                break;
            case 0x59:
                ldIOU.out(cpu.BC, cpu.E);
                break;
            case 0x5A:
                alU.adc(cpu.HL, cpu.DE);
                break;
            case 0x5B:
                ldIOU.ld(cpu.DE, addressing.ABS.setAddress(fetch16()));
                break;
            case 0x5E:
                cpuControlU.im2();
                break;
            case 0x5F:
                ldIOU.ld(cpu.A, cpu.R);
                break;
            case 0x60:
                ldIOU.in(cpu.H, cpu.BC);
                break;
            case 0x61:
                ldIOU.out(cpu.BC, cpu.H);
                break;
            case 0x62:
                alU.sbc(cpu.HL, cpu.HL);
                break;
            case 0x67:
                alU.rrd();
                break;
            case 0x68:
                ldIOU.in(cpu.L, cpu.BC);
                break;
            case 0x69:
                ldIOU.out(cpu.BC, cpu.L);
                break;
            case 0x6A:
                alU.adc(cpu.HL, cpu.HL);
                break;
            case 0x6F:
                alU.rld();
                break;
            case 0x70:
                ldIOU.in(cpu.F, cpu.BC);//undocumented
                break;
            case 0x71:
                ldIOU.out(cpu.BC);
                break; //undocumented
            case 0x72:
                alU.sbc(cpu.HL, cpu.SP);
                break;
            case 0x73:
                ldIOU.ld(addressing.ABS.setAddress(fetch16()), cpu.SP);
                break;
            case 0x78:
                ldIOU.in(cpu.A, cpu.BC);
                break;
            case 0x79:
                ldIOU.out(cpu.BC, cpu.A);
                break;
            case 0x7A:
                alU.adc(cpu.HL, cpu.SP);
                break;
            case 0x7B:
                ldIOU.ld(cpu.SP, addressing.ABS.setAddress(fetch16()));
                break;
            case 0xA0:
                ldIOU.ldi();
                break;
            case 0xA1:
                alU.cpi();
                break;
            case 0xA2:
                ldIOU.ini();
                break;
            case 0xA3:
                ldIOU.outi();
                break;
            case 0xA8:
                ldIOU.ldd();
                break;
            case 0xA9:
                alU.cpd();
                break;
            case 0xAA:
                ldIOU.ind();
                break;
            case 0xAB:
                ldIOU.outd();
                break;
            case 0xB0:
                ldIOU.ldir();
                break;
            case 0xB1:
                alU.cpir();
                break;
            case 0xB2:
                ldIOU.inir();
                break;
            case 0xB3:
                ldIOU.otir();
                break;
            case 0xB8:
                ldIOU.lddr();
                break;
            case 0xB9:
                alU.cpdr();
                break;
            case 0xBA:
                ldIOU.indr();
                break;
            case 0xBB:
                ldIOU.otdr();
                break;
        }
    }
}
