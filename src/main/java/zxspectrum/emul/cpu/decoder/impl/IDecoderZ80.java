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
import zxspectrum.emul.io.mem.MemoryAccess;

@Slf4j
public class IDecoderZ80 extends BaseIDecoderZ80 {

    private final CbIDecoderZ80 cbIDecoder;

    private final EdIDecoderZ80 edIDecoder;

    private final FdIDecoderZ80 fdIDecoder;

    private final DdIDecoderZ80 ddIDecoder;

    private final Counter tStatesRemains;


    public IDecoderZ80(@NonNull final Cpu cpu, @NonNull final Counter tStatesRemains, @NonNull final LdIO ldIO
            , @NonNull final ArithmeticLogical al, @NonNull final Jump jump, @NonNull final CallReturn callReturn
            , @NonNull final CpuControl cpuControl) {
        super(cpu, ldIO, al, jump, callReturn, cpuControl);
        this.tStatesRemains = tStatesRemains;
        cbIDecoder = new CbIDecoderZ80(cpu, ldIO, al, jump, callReturn, cpuControl);
        edIDecoder = new EdIDecoderZ80(cpu, tStatesRemains, ldIO, al, jump, callReturn, cpuControl);
        fdIDecoder = new FdIDecoderZ80(cpu, ldIO, al, jump, callReturn, cpuControl);
        ddIDecoder = new DdIDecoderZ80(cpu, ldIO, al, jump, callReturn, cpuControl);
    }

    @Override
    public void execute() {
        if (cpu.HALT.isValue()) {
            cpuCrlU.nop();
        } else {
            final int code = fetch8();
            tStatesRemains.inc(4);
            switch (code) {

                case 0xCB:
                    cbIDecoder.execute(code);
                    break;

                case 0xED:
                    edIDecoder.execute(code);
                    break;

                case 0xDD:
                    ddIDecoder.execute(code);
                    break;

                case 0xFD:
                    fdIDecoder.execute(code);
                    break;

                default:
                    execute(code);
                    break;
            }
        }
        cpu.R.inc();
    }

    //basic instructions
    @Override
    void execute(int code) {
        switch (code) {
            case 0x00:
                cpuCrlU.nop();
                break;
            case 0x01:
                cpu.BC.setValue(fetch16());
                tStatesRemains.inc(3 + 3);
                break;
            case 0x02:
                ldIOU.ld(addressing.BC, cpu.A);
                tStatesRemains.inc(3);
                break;
            case 0x03:
                cpu.BC.inc();
                tStatesRemains.inc(2);
                break;
            case 0x04:
                alU.inc(cpu.B);
                break;
            case 0x05:
                alU.dec(cpu.B);
                break;
            case 0x06:
                cpu.B.setValue(fetch8());
                tStatesRemains.inc(3);
                break;
            case 0x07:
                alU.rlc(cpu.A);
                break;
            case 0x08:
                cpu.AF.swap(cpu.altAF);
                break;
            case 0x09:
                alU.add(cpu.HL, cpu.BC);
                tStatesRemains.inc(4 + 3);
                break;
            case 0x0A:
                ldIOU.ld(cpu.A, addressing.BC);
                tStatesRemains.inc(3);
                break;
            case 0x0B:
                cpu.BC.dec();
                tStatesRemains.inc(2);
                break;
            case 0x0C:
                alU.inc(cpu.C);
                break;
            case 0x0D:
                alU.dec(cpu.C);
                break;
            case 0x0E:
                cpu.C.setValue(fetch8());
                tStatesRemains.inc(3);
                break;
            case 0x0F:
                alU.rrc(cpu.A);
                break;
            case 0x10:
                if (jmpU.djnz(fetch8())) {
                    tStatesRemains.inc(9);
                } else {
                    tStatesRemains.inc(4);
                }
                break;
            case 0x11:
                cpu.DE.setValue(fetch16());
                tStatesRemains.inc(3 + 3);
                break;
            case 0x12:
                ldIOU.ld(addressing.DE, cpu.A);
                tStatesRemains.inc(3);
                break;
            case 0x13:
                cpu.DE.inc();
                tStatesRemains.inc(2);
                break;
            case 0x14:
                alU.inc(cpu.D);
                break;
            case 0x15:
                alU.dec(cpu.D);
                break;
            case 0x16:
                cpu.D.setValue(fetch8());
                tStatesRemains.inc(3);
                break;
            case 0x17:
                alU.rl(cpu.A);
                break;
            case 0x18:
                jmpU.jr(fetch8());
                tStatesRemains.inc(3 + 5);
                break;
            case 0x19:
                alU.add(cpu.HL, cpu.DE);
                tStatesRemains.inc(4 + 3);
                break;
            case 0x1A:
                ldIOU.ld(cpu.A, addressing.DE);
                tStatesRemains.inc(3);
                break;
            case 0x1B:
                cpu.DE.dec();
                tStatesRemains.inc(2);
                break;
            case 0x1C:
                alU.inc(cpu.E);
                break;
            case 0x1D:
                alU.dec(cpu.E);
                break;
            case 0x1E:
                cpu.E.setValue(fetch8());
                tStatesRemains.inc(3);
                break;
            case 0x1F:
                alU.rr(cpu.A);
                break;
            case 0x20:
                if (jmpU.jrNZ(fetch8())) {
                    tStatesRemains.inc(3 + 5);
                } else {
                    tStatesRemains.inc(3);
                }
                break;
            case 0x21:
                cpu.HL.setValue(fetch16());
                tStatesRemains.inc(3 + 3);
                break;
            case 0x22:
                addressing.ABS.setAddress(fetch16()).poke(cpu.HL);//ld(nn), hl
                tStatesRemains.inc(3 + 3 + 3 + 3);
                break;
            case 0x23:
                cpu.HL.inc();
                tStatesRemains.inc(2);
                break;
            case 0x24:
                alU.inc(cpu.H);
                break;
            case 0x25:
                alU.dec(cpu.H);
                break;
            case 0x26:
                cpu.H.setValue(fetch8());
                tStatesRemains.inc(3);
                break;
            case 0x27:
                alU.daa();
                break;
            case 0x28:
                jmpU.jrZ(fetch8());
                tStatesRemains.inc(3 + 5);
                break;
            case 0x29:
                alU.add(cpu.HL, cpu.HL);
                tStatesRemains.inc(4 + 3);
                break;
            case 0x2A:
                addressing.ABS.setAddress(fetch16()).peek(cpu.HL);//ld(HL),nn
                tStatesRemains.inc(3 + 3 + 3 + 3);
                break;
            case 0x2B:
                cpu.HL.dec();
                tStatesRemains.inc(2);
                break;
            case 0x2C:
                alU.inc(cpu.L);
                break;
            case 0x2D:
                alU.dec(cpu.L);
                break;
            case 0x2E:
                cpu.L.setValue(fetch8());
                tStatesRemains.inc(3);
                break;
            case 0x2F:
                cpuCrlU.cpl();
                break;
            case 0x30:
                if (jmpU.jrNC(fetch8())) {
                    tStatesRemains.inc(3 + 5);
                } else {
                    tStatesRemains.inc(3);
                }
                break;
            case 0x31:
                cpu.SP.setValue(fetch16());
                tStatesRemains.inc(3 + 3);
                break;
            case 0x32:
                ldIOU.ld(addressing.ABS.setAddress(fetch16()), cpu.A);
                tStatesRemains.inc(3 + 3 + 3 + 3);
                break;
            case 0x33:
                cpu.SP.inc();
                tStatesRemains.inc(2);
                break;
            case 0x34:
                alU.inc(addressing.HL);
                tStatesRemains.inc(4 + 3);
                break;
            case 0x35:
                alU.dec(addressing.HL);
                tStatesRemains.inc(4 + 3);
                break;
            case 0x36:
                ldIOU.ld(addressing.HL, fetch8());
                tStatesRemains.inc(3 + 3);
                break;
            case 0x37:
                cpuCrlU.scf();
                break;
            case 0x38:
                if (jmpU.jrC(fetch8())) {
                    tStatesRemains.inc(3 + 5);
                } else {
                    tStatesRemains.inc(3);
                }
                break;
            case 0x39:
                alU.add(cpu.HL, cpu.SP);
                tStatesRemains.inc(4 + 3);
                break;
            case 0x3A:
                ldIOU.ld(cpu.A, addressing.ABS.setAddress(fetch16()));
                tStatesRemains.inc(3 + 3 + 3);
                break;
            case 0x3B:
                cpu.SP.dec();
                tStatesRemains.inc(2);
                break;
            case 0x3C:
                alU.inc(cpu.A);
                break;
            case 0x3D:
                alU.dec(cpu.A);
                break;
            case 0x3E:
                cpu.A.setValue(fetch8());
                tStatesRemains.inc(3);
                break;
            case 0x3F:
                cpuCrlU.ccf();
                break;
            case 0x40:
                cpu.B.ld(cpu.B);
                break;
            case 0x41:
                cpu.B.ld(cpu.C);
                break;
            case 0x42:
                cpu.B.ld(cpu.D);
                break;
            case 0x43:
                cpu.B.ld(cpu.E);
                break;
            case 0x44:
                cpu.B.ld(cpu.H);
                break;
            case 0x45:
                cpu.B.ld(cpu.L);
                break;
            case 0x46:
                addressing.HL.peek(cpu.B); //ld b, (hl)
                tStatesRemains.inc(3);
                break;
            case 0x47:
                cpu.B.ld(cpu.A);
                break;
            case 0x48:
                cpu.C.ld(cpu.B);
                break;
            case 0x49:
                cpu.C.ld(cpu.C);
                break;
            case 0x4A:
                cpu.C.ld(cpu.D);
                break;
            case 0x4B:
                cpu.C.ld(cpu.E);
                break;
            case 0x4C:
                cpu.C.ld(cpu.H);
                break;
            case 0x4D:
                cpu.C.ld(cpu.L);
                break;
            case 0x4E:
                addressing.HL.peek(cpu.C);
                tStatesRemains.inc(3);
                break; //ld c, (hl)
            case 0x4F:
                cpu.C.ld(cpu.A);
                break;
            case 0x50:
                cpu.D.ld(cpu.B);
                break;
            case 0x51:
                cpu.D.ld(cpu.C);
                break;
            case 0x52:
                cpu.D.ld(cpu.D);
                break;
            case 0x53:
                cpu.D.ld(cpu.E);
                break;
            case 0x54:
                cpu.D.ld(cpu.H);
                break;
            case 0x55:
                cpu.D.ld(cpu.L);
                break;
            case 0x56:
                addressing.HL.peek(cpu.D);
                tStatesRemains.inc(3);
                break; //ld d, (hl)
            case 0x57:
                cpu.D.ld(cpu.A);
                break;
            case 0x58:
                cpu.E.ld(cpu.B);
                break;
            case 0x59:
                cpu.E.ld(cpu.C);
                break;
            case 0x5A:
                cpu.E.ld(cpu.D);
                break;
            case 0x5B:
                cpu.E.ld(cpu.E);
                break;
            case 0x5C:
                cpu.E.ld(cpu.H);
                break;
            case 0x5D:
                cpu.E.ld(cpu.L);
                break;
            case 0x5E:
                addressing.HL.peek(cpu.E);
                tStatesRemains.inc(3);
                break; //ld e, (hl)
            case 0x5F:
                cpu.E.ld(cpu.A);
                break;
            case 0x60:
                cpu.H.ld(cpu.B);
                break;
            case 0x61:
                cpu.H.ld(cpu.C);
                break;
            case 0x62:
                cpu.H.ld(cpu.D);
                break;
            case 0x63:
                cpu.H.ld(cpu.E);
                break;
            case 0x64:
                cpu.H.ld(cpu.H);
                break;
            case 0x65:
                cpu.H.ld(cpu.L);
                break;
            case 0x66:
                addressing.HL.peek(cpu.H);
                tStatesRemains.inc(3);
                break; //ld h, (hl)
            case 0x67:
                cpu.H.ld(cpu.A);
                break;
            case 0x68:
                cpu.L.ld(cpu.B);
                break;
            case 0x69:
                cpu.L.ld(cpu.C);
                break;
            case 0x6A:
                cpu.L.ld(cpu.D);
                break;
            case 0x6B:
                cpu.L.ld(cpu.E);
                break;
            case 0x6C:
                cpu.L.ld(cpu.H);
                break;
            case 0x6D:
                cpu.L.ld(cpu.L);
                break;
            case 0x6E:
                addressing.HL.peek(cpu.L);
                tStatesRemains.inc(3);
                break; //ld l, (hl)
            case 0x6F:
                cpu.L.ld(cpu.A);
                break;
            case 0x70:
                addressing.HL.poke(cpu.B);
                tStatesRemains.inc(3);
                break; //ld (hl), b
            case 0x71:
                addressing.HL.poke(cpu.C);
                tStatesRemains.inc(3);
                break; //ld (hl), c
            case 0x72:
                addressing.HL.poke(cpu.D);
                tStatesRemains.inc(3);
                break; //ld (hl), d
            case 0x73:
                addressing.HL.poke(cpu.E);
                tStatesRemains.inc(3);
                break; //ld (hl), e
            case 0x74:
                addressing.HL.poke(cpu.H);
                tStatesRemains.inc(3);
                break; //ld (hl), h
            case 0x75:
                addressing.HL.poke(cpu.L);
                tStatesRemains.inc(3);
                break; //ld (hl), l
            case 0x76:
                cpuCrlU.halt();
                break;
            case 0x77:
                addressing.HL.poke(cpu.A);
                tStatesRemains.inc(3);
                break; //ld (hl), a
            case 0x78:
                cpu.A.ld(cpu.B);
                break;
            case 0x79:
                cpu.A.ld(cpu.C);
                break;
            case 0x7A:
                cpu.A.ld(cpu.D);
                break;
            case 0x7B:
                cpu.A.ld(cpu.E);
                break;
            case 0x7C:
                cpu.A.ld(cpu.H);
                break;
            case 0x7D:
                cpu.A.ld(cpu.L);
                break;
            case 0x7E:
                addressing.HL.peek(cpu.A);
                tStatesRemains.inc(3);
                break; //ld a, (hl)
            case 0x7F:
                cpu.A.ld(cpu.A);
                break;
            case 0x80:
                alU.add(cpu.B);
                break;
            case 0x81:
                alU.add(cpu.C);
                break;
            case 0x82:
                alU.add(cpu.D);
                break;
            case 0x83:
                alU.add(cpu.E);
                break;
            case 0x84:
                alU.add(cpu.H);
                break;
            case 0x85:
                alU.add(cpu.L);
                break;
            case 0x86:
                alU.add(addressing.HL);
                tStatesRemains.inc(3);
                break;//add A, (hl)
            case 0x87:
                alU.add(cpu.A);
                break;
            case 0x88:
                alU.adc(cpu.B);
                break;
            case 0x89:
                alU.adc(cpu.C);
                break;
            case 0x8A:
                alU.adc(cpu.D);
                break;
            case 0x8B:
                alU.adc(cpu.E);
                break;
            case 0x8C:
                alU.adc(cpu.H);
                break;
            case 0x8D:
                alU.adc(cpu.L);
                break;
            case 0x8E:
                alU.adc(addressing.HL);
                tStatesRemains.inc(3);
                break; //adc A, (hl)
            case 0x8F:
                alU.adc(cpu.A);
                break;
            case 0x90:
                alU.sub(cpu.B);
                break;
            case 0x91:
                alU.sub(cpu.C);
                break;
            case 0x92:
                alU.sub(cpu.D);
                break;
            case 0x93:
                alU.sub(cpu.E);
                break;
            case 0x94:
                alU.sub(cpu.H);
                break;
            case 0x95:
                alU.sub(cpu.L);
                break;
            case 0x96:
                alU.sub(addressing.HL);
                tStatesRemains.inc(3);
                break;//sub A, (hl)
            case 0x97:
                alU.sub(cpu.A);
                break;
            case 0x98:
                alU.sbc(cpu.B);
                break;
            case 0x99:
                alU.sbc(cpu.C);
                break;
            case 0x9A:
                alU.sbc(cpu.D);
                break;
            case 0x9B:
                alU.sbc(cpu.E);
                break;
            case 0x9C:
                alU.sbc(cpu.H);
                break;
            case 0x9D:
                alU.sbc(cpu.L);
                break;
            case 0x9E:
                alU.sbc(addressing.HL);
                tStatesRemains.inc(3);
                break;//sbc A, (hl)
            case 0x9F:
                alU.sbc(cpu.A);
                break;
            case 0xA0:
                alU.and(cpu.B);
                break;
            case 0xA1:
                alU.and(cpu.C);
                break;
            case 0xA2:
                alU.and(cpu.D);
                break;
            case 0xA3:
                alU.and(cpu.E);
                break;
            case 0xA4:
                alU.and(cpu.H);
                break;
            case 0xA5:
                alU.and(cpu.L);
                break;
            case 0xA6:
                alU.and(addressing.HL);
                tStatesRemains.inc(3);
                break;
            case 0xA7:
                alU.and(cpu.A);
                break;
            case 0xA8:
                alU.xor(cpu.B);
                break;
            case 0xA9:
                alU.xor(cpu.C);
                break;
            case 0xAA:
                alU.xor(cpu.D);
                break;
            case 0xAB:
                alU.xor(cpu.E);
                break;
            case 0xAC:
                alU.xor(cpu.H);
                break;
            case 0xAD:
                alU.xor(cpu.L);
                break;
            case 0xAE:
                alU.xor(addressing.HL);
                tStatesRemains.inc(3);
                break;
            case 0xAF:
                alU.xor(cpu.A);
                break;
            case 0xB0:
                alU.or(cpu.B);
                break;
            case 0xB1:
                alU.or(cpu.C);
                break;
            case 0xB2:
                alU.or(cpu.D);
                break;
            case 0xB3:
                alU.or(cpu.E);
                break;
            case 0xB4:
                alU.or(cpu.H);
                break;
            case 0xB5:
                alU.or(cpu.L);
                break;
            case 0xB6:
                alU.or(addressing.HL);
                tStatesRemains.inc(3);
                break;
            case 0xB7:
                alU.or(cpu.A);
                break;
            case 0xB8:
                alU.cp(cpu.B);
                break;
            case 0xB9:
                alU.cp(cpu.C);
                break;
            case 0xBA:
                alU.cp(cpu.D);
                break;
            case 0xBB:
                alU.cp(cpu.E);
                break;
            case 0xBC:
                alU.cp(cpu.H);
                break;
            case 0xBD:
                alU.cp(cpu.L);
                break;
            case 0xBE:
                alU.cp(addressing.HL);
                tStatesRemains.inc(3);
                break;
            case 0xBF:
                alU.cp(cpu.A);
                break;
            case 0xC0:
                if (callRetU.retNZ()) {
                    tStatesRemains.inc(1 + 3 + 3);
                } else {
                    tStatesRemains.inc(1);
                }
                break;
            case 0xC1:
                memory.pop(cpu.BC);
                tStatesRemains.inc(3 + 3);
                break;
            case 0xC2:
                if (jmpU.jpNZ(fetch16())) {
                    tStatesRemains.inc(3 + 3);
                } else {
                    tStatesRemains.inc(3 + 3);
                }
                break;
            case 0xC3:
                jmpU.jp(fetch16());
                tStatesRemains.inc(3 + 3);
                break;
            case 0xC4:
                if (callRetU.callNZ(fetch16())) {
                    tStatesRemains.inc(3 + 4 + 3 + 3);
                } else {
                    tStatesRemains.inc(3 + 3);
                }
                break;
            case 0xC5:
                memory.push(cpu.BC);
                tStatesRemains.inc(1 + 3 + 3);
                break;
            case 0xC6:
                alU.add(fetch8());
                tStatesRemains.inc(3);
                break;
            case 0xC7:
                callRetU.rst0();
                tStatesRemains.inc(1 + 3 + 3);
                break;
            case 0xC8:
                if (callRetU.retZ()) {
                    tStatesRemains.inc(1 + 3 + 3);
                } else {
                    tStatesRemains.inc(1);
                }
                break;
            case 0xC9:
                callRetU.ret();
                tStatesRemains.inc(3 + 3);
                break;
            case 0xCA:
                if (jmpU.jpZ(fetch16())) {
                    tStatesRemains.inc(3 + 3);
                } else {
                    tStatesRemains.inc(3 + 3);
                }
                break;
            case 0xCC:
                if (callRetU.callZ(fetch16())) {
                    tStatesRemains.inc(3 + 4 + 3 + 3);
                } else {
                    tStatesRemains.inc(3 + 3);
                }
                break;
            case 0xCD:
                callRetU.call(fetch16());
                tStatesRemains.inc(3 + 4 + 3 + 3);
                break;
            case 0xCE:
                tStatesRemains.inc(3);
                break;
            case 0xCF:
                callRetU.rst8();
                tStatesRemains.inc(1 + 3 + 3);
                break;
            case 0xD0:
                if (callRetU.retNC()) {
                    tStatesRemains.inc(1 + 3 + 3);
                } else {
                    tStatesRemains.inc(1);
                }
                break;
            case 0xD1:
                memory.pop(cpu.DE);
                tStatesRemains.inc(3 + 3);
                break;
            case 0xD2:
                if (jmpU.jpNC(fetch16())) {
                    tStatesRemains.inc(3 + 3);
                } else {
                    tStatesRemains.inc(3 + 3);
                }
                break;
            case 0xD3:
                ldIOU.out(fetch8(), cpu.A);
                break;
            case 0xD4:
                if (callRetU.callNC(fetch16())) {
                    tStatesRemains.inc(3 + 4 + 3 + 3);
                } else {
                    tStatesRemains.inc(3 + 3);
                }
                break;
            case 0xD5:
                memory.push(cpu.DE);
                tStatesRemains.inc(1 + 3 + 3);
                break;
            case 0xD6:
                alU.sub8(fetch8());
                tStatesRemains.inc(3);
                break;
            case 0xD7:
                callRetU.rst10();
                tStatesRemains.inc(1 + 3 + 3);
                break;
            case 0xD8:
                if (callRetU.retC()) {
                    tStatesRemains.inc(1 + 3 + 3);
                } else {
                    tStatesRemains.inc(1);
                }
                break;
            case 0xD9:
                ldIOU.exx();
                break;
            case 0xDA:
                if (jmpU.jpC(fetch16())) {
                    tStatesRemains.inc(3 + 3);
                } else {
                    tStatesRemains.inc(3 + 3);
                }
                break;
            case 0xDB:
                ldIOU.in(cpu.A, fetch8());
                tStatesRemains.inc(3 + 4);
                break;
            case 0xDC:
                if (callRetU.callC(fetch16())) {
                    tStatesRemains.inc(3 + 4 + 3 + 3);
                } else {
                    tStatesRemains.inc(3 + 3);
                }
                break;
            case 0xDE:
                alU.sbc(fetch8());
                tStatesRemains.inc(3);
                break;
            case 0xDF:
                callRetU.rst18();
                tStatesRemains.inc(1 + 3 + 3);
                break;
            case 0xE0:
                if (callRetU.retPO()) {
                    tStatesRemains.inc(1 + 3 + 3);
                } else {
                    tStatesRemains.inc(1);
                }
                break;
            case 0xE1:
                memory.pop(cpu.HL);
                tStatesRemains.inc(3 + 3);
                break;
            case 0xE2:
                if (jmpU.jpPO(fetch16())) {
                    tStatesRemains.inc(3 + 3);
                } else {
                    tStatesRemains.inc(3 + 3);
                }
                break;
            case 0xE3:
                ldIOU.ex(addressing.SP, cpu.HL);
                tStatesRemains.inc(3 + 4 + 3 + 5);
                break;
            case 0xE4:
                if (callRetU.callPO(fetch16())) {
                    tStatesRemains.inc(3 + 4 + 3 + 3);
                } else {
                    tStatesRemains.inc(3 + 3);
                }
                break;
            case 0xE5:
                memory.push(cpu.HL);
                tStatesRemains.inc(1 + 3 + 3);
                break;
            case 0xE6:
                alU.and(fetch8());
                tStatesRemains.inc(3);
                break;
            case 0xE7:
                callRetU.rst20();
                tStatesRemains.inc(1 + 3 + 3);
                break;
            case 0xE8:
                if (callRetU.retPE()) {
                    tStatesRemains.inc(1 + 3 + 3);
                } else {
                    tStatesRemains.inc(1);
                }
                break;
            case 0xE9:
                jmpU.jp(addressing.HL);
                break;
            case 0xEA:
                if (jmpU.jpPE(fetch16())) {
                    tStatesRemains.inc(3 + 3);
                } else {
                    tStatesRemains.inc(3 + 3);
                }
                break;
            case 0xEB:
                cpu.DE.swap(cpu.HL);
                break;
            case 0xEC:
                if (callRetU.callPE(fetch16())) {
                    tStatesRemains.inc(3 + 4 + 3 + 3);
                } else {
                    tStatesRemains.inc(3 + 3);
                }
                break;
            case 0xEE:
                alU.xor(fetch8());
                tStatesRemains.inc(3);
                break;
            case 0xEF:
                callRetU.rst28();
                tStatesRemains.inc(1 + 3 + 3);
                break;
            case 0xF0:
                if (callRetU.retP()) {
                    tStatesRemains.inc(1 + 3 + 3);
                } else {
                    tStatesRemains.inc(1);
                }
                break;
            case 0xF1:
                memory.pop(cpu.AF);
                tStatesRemains.inc(3 + 3);
                break;
            case 0xF2:
                if (jmpU.jpP(fetch16())) {
                    tStatesRemains.inc(3 + 3);
                } else {
                    tStatesRemains.inc(3 + 3);
                }
                break;
            case 0xF3:
                cpuCrlU.di();
                break;
            case 0xF4:
                if (callRetU.callP(fetch16())) {
                    tStatesRemains.inc(3 + 4 + 3 + 3);
                } else {
                    tStatesRemains.inc(3 + 3);
                }
                break;
            case 0xF5:
                memory.push(cpu.AF);
                tStatesRemains.inc(1 + 3 + 3);
                break;
            case 0xF6:
                alU.or(fetch8());
                tStatesRemains.inc(3);
                break;
            case 0xF7:
                callRetU.rst30();
                tStatesRemains.inc(1 + 3 + 3);
                break;
            case 0xF8:
                if (callRetU.retM()) {
                    tStatesRemains.inc(1 + 3 + 3);
                } else {
                    tStatesRemains.inc(1);
                }
                break;
            case 0xF9:
                cpu.SP.ld(cpu.HL);
                break;
            case 0xFA:
                if (jmpU.jpM(fetch16())) {
                    tStatesRemains.inc(3 + 3);
                } else {
                    tStatesRemains.inc(3 + 3);
                }
                break;
            case 0xFB:
                cpuCrlU.ei();
                break;
            case 0xFC:
                if (callRetU.callM(fetch16())) {
                    tStatesRemains.inc(3 + 4 + 3 + 3);
                } else {
                    tStatesRemains.inc(3 + 3);
                }
                break;
            case 0xFE:
                alU.cp(fetch8());
                tStatesRemains.inc(3);
                break;
            case 0xFF:
                callRetU.rst38();
                tStatesRemains.inc(1 + 3 + 3);
                break;
        }
    }

    @Override
    public void setMemory(@NonNull MemoryAccess memory) {
        super.setMemory(memory);
        cbIDecoder.setMemory(memory);
        edIDecoder.setMemory(memory);
        ddIDecoder.setMemory(memory);
        fdIDecoder.setMemory(memory);
    }
}
