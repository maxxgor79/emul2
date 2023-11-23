package zxspectrum.emul.cpu.decoder.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import zxspectrum.emul.cpu.Counter;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.unit.ArithmeticLogical;
import zxspectrum.emul.cpu.unit.CallReturn;
import zxspectrum.emul.cpu.unit.CpuControl;
import zxspectrum.emul.cpu.unit.Jump;
import zxspectrum.emul.cpu.unit.LdIO;

@Slf4j
final class EdIDecoderZ80 extends BaseIDecoderZ80 {

    private final Counter tStatesRemains;

    public EdIDecoderZ80(@NonNull Cpu cpu, @NonNull final Counter tStatesRemains, @NonNull LdIO ldIO
            , @NonNull ArithmeticLogical al, @NonNull Jump jump, @NonNull CallReturn callReturn
            , @NonNull CpuControl cpuControl) {
        super(cpu, ldIO, al, jump, callReturn, cpuControl);
        this.tStatesRemains = tStatesRemains;
    }

    @Override
    protected void execute(final int code) {
        final int subCode = fetch8();
        tStatesRemains.inc(4);
        switch (subCode) {
            case 0x40:
                ldIOU.in(cpu.B, cpu.BC);
                tStatesRemains.inc(4);
                break;
            case 0x41:
                ldIOU.out(cpu.BC, cpu.B);
                tStatesRemains.inc(4);
                break;
            case 0x42:
                alU.sbc(cpu.HL, cpu.BC);
                tStatesRemains.inc(4 + 3);
                break;
            case 0x43:
                ldIOU.ld(addressing.ABS.setAddress(fetch16()), cpu.BC);
                tStatesRemains.inc(3 + 3 + 3 + 3);
                break;
            case 0x44:
                alU.neg();
                break;
            case 0x45:
                callRetU.retN();
                tStatesRemains.inc(3 + 3);
                break;
            case 0x46:
                cpuCrlU.im0();
                break;
            case 0x47:
                cpu.I.ld(cpu.A);
                tStatesRemains.inc(1);
                break;
            case 0x48:
                ldIOU.in(cpu.C, cpu.BC);
                tStatesRemains.inc(4);
                break;
            case 0x49:
                ldIOU.out(cpu.BC, cpu.C);
                tStatesRemains.inc(4);
                break;
            case 0x4A:
                alU.adc(cpu.HL, cpu.BC);
                tStatesRemains.inc(4 + 3);
                break;
            case 0x4B:
                ldIOU.ld(cpu.BC, addressing.ABS.setAddress(fetch16()));
                tStatesRemains.inc(3 + 3 + 3 + 3);
                break;
            case 0x4D:
                callRetU.retI();
                tStatesRemains.inc(3 + 3);
                break;
            case 0x4F:
                cpu.R.ld(cpu.A);
                tStatesRemains.inc(1);
                break;
            case 0x50:
                ldIOU.in(cpu.D, cpu.BC);
                tStatesRemains.inc(4);
                break;
            case 0x51:
                ldIOU.out(cpu.BC, cpu.D);
                tStatesRemains.inc(4);
                break;
            case 0x52:
                alU.sbc(cpu.HL, cpu.DE);
                tStatesRemains.inc(4 + 3);
                break;
            case 0x53:
                ldIOU.ld(addressing.ABS.setAddress(fetch16()), cpu.DE);
                tStatesRemains.inc(3 + 3 + 3 + 3);
                break;
            case 0x56:
                cpuCrlU.im1();
                break;
            case 0x57:
                ldIOU.ld(cpu.A, cpu.I);
                tStatesRemains.inc(1);
                break;
            case 0x58:
                ldIOU.in(cpu.E, cpu.BC);
                tStatesRemains.inc(4);
                break;
            case 0x59:
                ldIOU.out(cpu.BC, cpu.E);
                tStatesRemains.inc(4);
                break;
            case 0x5A:
                alU.adc(cpu.HL, cpu.DE);
                tStatesRemains.inc(4 + 3);
                break;
            case 0x5B:
                ldIOU.ld(cpu.DE, addressing.ABS.setAddress(fetch16()));
                tStatesRemains.inc(3 + 3 + 3 + 3);
                break;
            case 0x5E:
                cpuCrlU.im2();
                break;
            case 0x5F:
                ldIOU.ld(cpu.A, cpu.R);
                tStatesRemains.inc(1);
                break;
            case 0x60:
                ldIOU.in(cpu.H, cpu.BC);
                tStatesRemains.inc(4);
                break;
            case 0x61:
                ldIOU.out(cpu.BC, cpu.H);
                tStatesRemains.inc(4);
                break;
            case 0x62:
                alU.sbc(cpu.HL, cpu.HL);
                tStatesRemains.inc(4 + 3);
                break;
            case 0x67:
                alU.rrd();
                tStatesRemains.inc(3 + 4 + 3);
                break;
            case 0x68:
                ldIOU.in(cpu.L, cpu.BC);
                tStatesRemains.inc(4);
                break;
            case 0x69:
                ldIOU.out(cpu.BC, cpu.L);
                tStatesRemains.inc(4);
                break;
            case 0x6A:
                alU.adc(cpu.HL, cpu.HL);
                tStatesRemains.inc(4 + 3);
                break;
            case 0x6F:
                alU.rld();
                tStatesRemains.inc(3 + 4 + 3);
                break;
            case 0x70:
                ldIOU.in(cpu.F, cpu.BC);//undocumented
                tStatesRemains.inc(4);
                break;
            case 0x71:
                ldIOU.out(cpu.BC);
                tStatesRemains.inc(4);
                break; //undocumented
            case 0x72:
                alU.sbc(cpu.HL, cpu.SP);
                tStatesRemains.inc(4 + 4 + 3);
                break;
            case 0x73:
                addressing.ABS.setAddress(fetch16()).poke(cpu.SP);
                tStatesRemains.inc(3 + 3 + 3 + 3);
                break;
            case 0x78:
                ldIOU.in(cpu.A, cpu.BC);
                tStatesRemains.inc(4);
                break;
            case 0x79:
                ldIOU.out(cpu.BC, cpu.A);
                tStatesRemains.inc(4);
                break;
            case 0x7A:
                alU.adc(cpu.HL, cpu.SP);
                tStatesRemains.inc(4 + 3);
                break;
            case 0x7B:
                addressing.ABS.setAddress(fetch16()).peek(cpu.SP);
                tStatesRemains.inc(3 + 3 + 3 + 3);
                break;
            case 0xA0:
                ldIOU.ldi();
                tStatesRemains.inc(3 + 5);
                break;
            case 0xA1:
                alU.cpi();
                tStatesRemains.inc(3 + 5);
                break;
            case 0xA2:
                ldIOU.ini();
                tStatesRemains.inc(1 + 3 + 4);
                break;
            case 0xA3:
                ldIOU.outi();
                tStatesRemains.inc(1 + 3 + 4);
                break;
            case 0xA8:
                ldIOU.ldd();
                tStatesRemains.inc(3 + 5);
                break;
            case 0xA9:
                alU.cpd();
                tStatesRemains.inc(3 + 5);
                break;
            case 0xAA:
                ldIOU.ind();
                tStatesRemains.inc(1 + 3 + 4);
                break;
            case 0xAB:
                ldIOU.outd();
                tStatesRemains.inc(1 + 3 + 4);
                break;
            case 0xB0:
                if (ldIOU.ldir()) {
                    tStatesRemains.inc(3 + 5 + 5);
                } else {
                    tStatesRemains.inc(3 + 5);
                }
                break;
            case 0xB1:
                if (alU.cpir()) {
                    tStatesRemains.inc(3 + 5 + 5);
                } else {
                    tStatesRemains.inc(3 + 5);
                }
                break;
            case 0xB2:
                if (ldIOU.inir()) {
                    tStatesRemains.inc(1 + 3 + 4 + 5);
                } else {
                    tStatesRemains.inc(1 + 3 + 4);
                }
                break;
            case 0xB3:
                if (ldIOU.otir()) {
                    tStatesRemains.inc(1 + 3 + 4 + 5);
                } else {
                    tStatesRemains.inc(1 + 3 + 4);
                }
                break;
            case 0xB8:
                if (ldIOU.lddr()) {
                    tStatesRemains.inc(3 + 5 + 5);
                } else {
                    tStatesRemains.inc(3 + 5);
                }
                break;
            case 0xB9:
                if (alU.cpdr()) {
                    tStatesRemains.inc(3 + 5 + 5);
                } else {
                    tStatesRemains.inc(3 + 5);
                }
                break;
            case 0xBA:
                if (ldIOU.indr()) {
                    tStatesRemains.inc(1 + 3 + 4 + 5);
                } else {
                    tStatesRemains.inc(1 + 3 + 4);
                }
                break;
            case 0xBB:
                if (ldIOU.otdr()) {
                    tStatesRemains.inc(1 + 3 + 4 + 5);
                } else {
                    tStatesRemains.inc(1 + 3 + 4);
                }
                break;
        }
    }
}
