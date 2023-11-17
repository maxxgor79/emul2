package zxspectrum.emul.cpu.decoder.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import zxspectrum.emul.cpu.Cpu;
import zxspectrum.emul.cpu.reg.Const;
import zxspectrum.emul.cpu.unit.ArithmeticLogical;
import zxspectrum.emul.cpu.unit.CallReturn;
import zxspectrum.emul.cpu.unit.CpuControl;
import zxspectrum.emul.cpu.unit.Jump;
import zxspectrum.emul.cpu.unit.LdIO;
import zxspectrum.emul.io.mem.address.Addressing;

@Slf4j
final class CbIDecoderZ80 extends BaseIDecoderZ80 implements Const {
    private Addressing addressing;

    public CbIDecoderZ80(@NonNull Cpu cpu, @NonNull LdIO ldIO, @NonNull ArithmeticLogical al, @NonNull Jump jump
            , @NonNull CallReturn callReturn, @NonNull CpuControl cpuControl) {
        super(cpu, ldIO, al, jump, callReturn, cpuControl);
    }

    @Override
    protected void execute(final int code) {
        final int subCode = fetch8();
        switch (subCode) {
            case 0x00:
                alU.rlc(cpu.B);
                break;
            case 0x01:
                alU.rlc(cpu.C);
                break;
            case 0x02:
                alU.rlc(cpu.D);
                break;
            case 0x03:
                alU.rlc(cpu.E);
                break;
            case 0x04:
                alU.rlc(cpu.H);
                break;
            case 0x05:
                alU.rlc(cpu.L);
                break;
            case 0x06:
                alU.rlc(addressing.HL);
                break;
            case 0x07:
                alU.rlc(cpu.A);
                break;
            case 0x08:
                alU.rrc(cpu.B);
                break;
            case 0x09:
                alU.rrc(cpu.C);
                break;
            case 0x0A:
                alU.rrc(cpu.D);
                break;
            case 0x0B:
                alU.rrc(cpu.E);
                break;
            case 0x0C:
                alU.rrc(cpu.H);
                break;
            case 0x0D:
                alU.rrc(cpu.L);
                break;
            case 0x0E:
                alU.rrc(addressing.HL);
                break;
            case 0x0F:
                alU.rrc(cpu.A);
                break;
            case 0x10:
                alU.rl(cpu.B);
                break;
            case 0x11:
                alU.rl(cpu.C);
                break;
            case 0x12:
                alU.rl(cpu.D);
                break;
            case 0x13:
                alU.rl(cpu.E);
                break;
            case 0x14:
                alU.rl(cpu.H);
                break;
            case 0x15:
                alU.rl(cpu.L);
                break;
            case 0x16:
                alU.rl(addressing.HL);
                break;
            case 0x17:
                alU.rl(cpu.A);
                break;
            case 0x18:
                alU.rr(cpu.B);
                break;
            case 0x19:
                alU.rr(cpu.C);
                break;
            case 0x1A:
                alU.rr(cpu.D);
                break;
            case 0x1B:
                alU.rr(cpu.E);
                break;
            case 0x1C:
                alU.rr(cpu.H);
                break;
            case 0x1D:
                alU.rr(cpu.L);
                break;
            case 0x1E:
                alU.rr(addressing.HL);
                break;
            case 0x1F:
                alU.rr(cpu.A);
                break;
            case 0x20:
                alU.sla(cpu.B);
                break;
            case 0x21:
                alU.sla(cpu.C);
                break;
            case 0x22:
                alU.sla(cpu.D);
                break;
            case 0x23:
                alU.sla(cpu.E);
                break;
            case 0x24:
                alU.sla(cpu.H);
                break;
            case 0x25:
                alU.sla(cpu.L);
                break;
            case 0x26:
                alU.sla(addressing.HL);
                break;
            case 0x27:
                alU.sla(cpu.A);
                break;
            case 0x28:
                alU.sra(cpu.B);
                break;
            case 0x29:
                alU.sra(cpu.C);
                break;
            case 0x2A:
                alU.sra(cpu.D);
                break;
            case 0x2B:
                alU.sra(cpu.E);
                break;
            case 0x2C:
                alU.sra(cpu.H);
                break;
            case 0x2D:
                alU.sra(cpu.L);
                break;
            case 0x2E:
                alU.sra(addressing.HL);
                break;
            case 0x2F:
                alU.sra(cpu.A);
                break;
            case 0x38:
                alU.srl(cpu.B);
                break;
            case 0x39:
                alU.srl(cpu.C);
                break;
            case 0x3A:
                alU.srl(cpu.D);
                break;
            case 0x3B:
                alU.srl(cpu.E);
                break;
            case 0x3C:
                alU.srl(cpu.H);
                break;
            case 0x3D:
                alU.srl(cpu.L);
                break;
            case 0x3E:
                alU.srl(addressing.HL);
                break;
            case 0x3F:
                alU.srl(cpu.A);
                break;
            case 0x40:
                alU.bit(BIT_0, cpu.B);
                break;
            case 0x41:
                alU.bit(BIT_0, cpu.C);
                break;
            case 0x42:
                alU.bit(BIT_0, cpu.D);
                break;
            case 0x43:
                alU.bit(BIT_0, cpu.E);
                break;
            case 0x44:
                alU.bit(BIT_0, cpu.H);
                break;
            case 0x45:
                alU.bit(BIT_0, cpu.L);
                break;
            case 0x46:
                alU.bit(BIT_0, addressing.HL);
                break;
            case 0x47:
                alU.bit(BIT_0, cpu.A);
                break;
            case 0x48:
                alU.bit(BIT_1, cpu.B);
                break;
            case 0x49:
                alU.bit(BIT_1, cpu.C);
                break;
            case 0x4A:
                alU.bit(BIT_1, cpu.D);
                break;
            case 0x4B:
                alU.bit(BIT_1, cpu.E);
                break;
            case 0x4C:
                alU.bit(BIT_1, cpu.H);
                break;
            case 0x4D:
                alU.bit(BIT_1, cpu.L);
                break;
            case 0x4E:
                alU.bit(BIT_1, addressing.HL);
                break;
            case 0x4F:
                alU.bit(BIT_1, cpu.A);
                break;
            case 0x50:
                alU.bit(BIT_2, cpu.B);
                break;
            case 0x51:
                alU.bit(BIT_2, cpu.C);
                break;
            case 0x52:
                alU.bit(BIT_2, cpu.D);
                break;
            case 0x53:
                alU.bit(BIT_2, cpu.E);
                break;
            case 0x54:
                alU.bit(BIT_2, cpu.H);
                break;
            case 0x55:
                alU.bit(BIT_2, cpu.L);
                break;
            case 0x56:
                alU.bit(BIT_2, addressing.HL);
                break;
            case 0x57:
                alU.bit(BIT_2, cpu.A);
                break;
            case 0x58:
                alU.bit(BIT_3, cpu.B);
                break;
            case 0x59:
                alU.bit(BIT_3, cpu.C);
                break;
            case 0x5A:
                alU.bit(BIT_3, cpu.D);
                break;
            case 0x5B:
                alU.bit(BIT_3, cpu.E);
                break;
            case 0x5C:
                alU.bit(BIT_3, cpu.H);
                break;
            case 0x5D:
                alU.bit(BIT_3, cpu.L);
                break;
            case 0x5E:
                alU.bit(BIT_3, addressing.HL);
                break;
            case 0x5F:
                alU.bit(BIT_3, cpu.A);
                break;
            case 0x60:
                alU.bit(BIT_4, cpu.B);
                break;
            case 0x61:
                alU.bit(BIT_4, cpu.C);
                break;
            case 0x62:
                alU.bit(BIT_4, cpu.D);
                break;
            case 0x63:
                alU.bit(BIT_4, cpu.E);
                break;
            case 0x64:
                alU.bit(BIT_4, cpu.H);
                break;
            case 0x65:
                alU.bit(BIT_4, cpu.L);
                break;
            case 0x66:
                alU.bit(BIT_4, addressing.HL);
                break;
            case 0x67:
                alU.bit(BIT_4, cpu.A);
                break;
            case 0x68:
                alU.bit(BIT_5, cpu.B);
                break;
            case 0x69:
                alU.bit(BIT_5, cpu.C);
                break;
            case 0x6A:
                alU.bit(BIT_5, cpu.D);
                break;
            case 0x6B:
                alU.bit(BIT_5, cpu.E);
                break;
            case 0x6C:
                alU.bit(BIT_5, cpu.H);
                break;
            case 0x6D:
                alU.bit(BIT_5, cpu.L);
                break;
            case 0x6E:
                alU.bit(BIT_5, addressing.HL);
                break;
            case 0x6F:
                alU.bit(BIT_5, cpu.A);
                break;
            case 0x70:
                alU.bit(BIT_6, cpu.B);
                break;
            case 0x71:
                alU.bit(BIT_6, cpu.C);
                break;
            case 0x72:
                alU.bit(BIT_6, cpu.D);
                break;
            case 0x73:
                alU.bit(BIT_6, cpu.E);
                break;
            case 0x74:
                alU.bit(BIT_6, cpu.H);
                break;
            case 0x75:
                alU.bit(BIT_6, cpu.L);
                break;
            case 0x76:
                alU.bit(BIT_6, addressing.HL);
                break;
            case 0x77:
                alU.bit(BIT_6, cpu.A);
                break;
            case 0x78:
                alU.bit(BIT_7, cpu.B);
                break;
            case 0x79:
                alU.bit(BIT_7, cpu.C);
                break;
            case 0x7A:
                alU.bit(BIT_7, cpu.D);
                break;
            case 0x7B:
                alU.bit(BIT_7, cpu.E);
                break;
            case 0x7C:
                alU.bit(BIT_7, cpu.H);
                break;
            case 0x7D:
                alU.bit(BIT_7, cpu.L);
                break;
            case 0x7E:
                alU.bit(BIT_7, addressing.HL);
                break;
            case 0x7F:
                alU.bit(BIT_7, cpu.A);
                break;
            case 0x80:
                alU.res(BIT_0, cpu.B);
                break;
            case 0x81:
                alU.res(BIT_0, cpu.C);
                break;
            case 0x82:
                alU.res(BIT_0, cpu.D);
                break;
            case 0x83:
                alU.res(BIT_0, cpu.E);
                break;
            case 0x84:
                alU.res(BIT_0, cpu.H);
                break;
            case 0x85:
                alU.res(BIT_0, cpu.L);
                break;
            case 0x86:
                alU.res(BIT_0, addressing.HL);
                break;
            case 0x87:
                alU.res(BIT_0, cpu.A);
                break;
            case 0x88:
                alU.res(BIT_1, cpu.B);
                break;
            case 0x89:
                alU.res(BIT_1, cpu.C);
                break;
            case 0x8A:
                alU.res(BIT_1, cpu.D);
                break;
            case 0x8B:
                alU.res(BIT_1, cpu.E);
                break;
            case 0x8C:
                alU.res(BIT_1, cpu.H);
                break;
            case 0x8D:
                alU.res(BIT_1, cpu.L);
                break;
            case 0x8E:
                alU.res(BIT_1, addressing.HL);
                break;
            case 0x8F:
                alU.res(BIT_1, cpu.A);
                break;
            case 0x90:
                alU.res(BIT_2, cpu.B);
                break;
            case 0x91:
                alU.res(BIT_2, cpu.C);
                break;
            case 0x92:
                alU.res(BIT_2, cpu.D);
                break;
            case 0x93:
                alU.res(BIT_2, cpu.E);
                break;
            case 0x94:
                alU.res(BIT_2, cpu.H);
                break;
            case 0x95:
                alU.res(BIT_2, cpu.L);
                break;
            case 0x96:
                alU.res(BIT_2, addressing.HL);
                break;
            case 0x97:
                alU.res(BIT_2, cpu.A);
                break;
            case 0x98:
                alU.res(BIT_3, cpu.B);
                break;
            case 0x99:
                alU.res(BIT_3, cpu.C);
                break;
            case 0x9A:
                alU.res(BIT_3, cpu.D);
                break;
            case 0x9B:
                alU.res(BIT_3, cpu.E);
                break;
            case 0x9C:
                alU.res(BIT_3, cpu.H);
                break;
            case 0x9D:
                alU.res(BIT_3, cpu.L);
                break;
            case 0x9E:
                alU.res(BIT_3, addressing.HL);
                break;
            case 0x9F:
                alU.res(BIT_3, cpu.A);
                break;
            case 0xA0:
                alU.res(BIT_4, cpu.B);
                break;
            case 0xA1:
                alU.res(BIT_4, cpu.C);
                break;
            case 0xA2:
                alU.res(BIT_4, cpu.D);
                break;
            case 0xA3:
                alU.res(BIT_4, cpu.E);
                break;
            case 0xA4:
                alU.res(BIT_4, cpu.H);
                break;
            case 0xA5:
                alU.res(BIT_4, cpu.L);
                break;
            case 0xA6:
                alU.res(BIT_4, addressing.HL);
                break;
            case 0xA7:
                alU.res(BIT_4, cpu.A);
                break;
            case 0xA8:
                alU.res(BIT_5, cpu.B);
                break;
            case 0xA9:
                alU.res(BIT_5, cpu.C);
                break;
            case 0xAA:
                alU.res(BIT_5, cpu.D);
                break;
            case 0xAB:
                alU.res(BIT_5, cpu.E);
                break;
            case 0xAC:
                alU.res(BIT_5, cpu.H);
                break;
            case 0xAD:
                alU.res(BIT_5, cpu.L);
                break;
            case 0xAE:
                alU.res(BIT_5, addressing.HL);
                break;
            case 0xAF:
                alU.res(BIT_5, cpu.A);
                break;
            case 0xB0:
                alU.res(BIT_6, cpu.B);
                break;
            case 0xB1:
                alU.res(BIT_6, cpu.C);
                break;
            case 0xB2:
                alU.res(BIT_6, cpu.D);
                break;
            case 0xB3:
                alU.res(BIT_6, cpu.E);
                break;
            case 0xB4:
                alU.res(BIT_6, cpu.H);
                break;
            case 0xB5:
                alU.res(BIT_6, cpu.L);
                break;
            case 0xB6:
                alU.res(BIT_6, addressing.HL);
                break;
            case 0xB7:
                alU.res(BIT_6, cpu.A);
                break;
            case 0xB8:
                alU.res(BIT_7, cpu.B);
                break;
            case 0xB9:
                alU.res(BIT_7, cpu.C);
                break;
            case 0xBA:
                alU.res(BIT_7, cpu.D);
                break;
            case 0xBB:
                alU.res(BIT_7, cpu.E);
                break;
            case 0xBC:
                alU.res(BIT_7, cpu.H);
                break;
            case 0xBD:
                alU.res(BIT_7, cpu.L);
                break;
            case 0xBE:
                alU.res(BIT_7, addressing.HL);
                break;
            case 0xBF:
                alU.res(BIT_7, cpu.A);
                break;
            case 0xC0:
                alU.set(BIT_0, cpu.B);
                break;
            case 0xC1:
                alU.set(BIT_0, cpu.C);
                break;
            case 0xC2:
                alU.set(BIT_0, cpu.D);
                break;
            case 0xC3:
                alU.set(BIT_0, cpu.E);
                break;
            case 0xC4:
                alU.set(BIT_0, cpu.H);
                break;
            case 0xC5:
                alU.set(BIT_0, cpu.L);
                break;
            case 0xC6:
                alU.set(BIT_0, addressing.HL);
                break;
            case 0xC7:
                alU.set(BIT_0, cpu.A);
                break;
            case 0xC8:
                alU.set(BIT_1, cpu.B);
                break;
            case 0xC9:
                alU.set(BIT_1, cpu.C);
                break;
            case 0xCA:
                alU.set(BIT_1, cpu.D);
                break;
            case 0xCB:
                alU.set(BIT_1, cpu.E);
                break;
            case 0xCC:
                alU.set(BIT_1, cpu.H);
                break;
            case 0xCD:
                alU.set(BIT_1, cpu.L);
                break;
            case 0xCE:
                alU.set(BIT_1, addressing.HL);
                break;
            case 0xCF:
                alU.set(BIT_1, cpu.A);
                break;
            case 0xD0:
                alU.set(BIT_2, cpu.B);
                break;
            case 0xD1:
                alU.set(BIT_2, cpu.C);
                break;
            case 0xD2:
                alU.set(BIT_2, cpu.D);
                break;
            case 0xD3:
                alU.set(BIT_2, cpu.E);
                break;
            case 0xD4:
                alU.set(BIT_2, cpu.H);
                break;
            case 0xD5:
                alU.set(BIT_2, cpu.L);
                break;
            case 0xD6:
                alU.set(BIT_2, addressing.HL);
                break;
            case 0xD7:
                alU.set(BIT_2, cpu.A);
                break;
            case 0xD8:
                alU.set(BIT_3, cpu.B);
                break;
            case 0xD9:
                alU.set(BIT_3, cpu.C);
                break;
            case 0xDA:
                alU.set(BIT_3, cpu.D);
                break;
            case 0xDB:
                alU.set(BIT_3, cpu.E);
                break;
            case 0xDC:
                alU.set(BIT_3, cpu.H);
                break;
            case 0xDD:
                alU.set(BIT_3, cpu.L);
                break;
            case 0xDE:
                alU.set(BIT_3, addressing.HL);
                break;
            case 0xDF:
                alU.set(BIT_3, cpu.A);
                break;
            case 0xE0:
                alU.set(BIT_4, cpu.B);
                break;
            case 0xE1:
                alU.set(BIT_4, cpu.C);
                break;
            case 0xE2:
                alU.set(BIT_4, cpu.D);
                break;
            case 0xE3:
                alU.set(BIT_4, cpu.E);
                break;
            case 0xE4:
                alU.set(BIT_4, cpu.H);
                break;
            case 0xE5:
                alU.set(BIT_4, cpu.L);
                break;
            case 0xE6:
                alU.set(BIT_4, addressing.HL);
                break;
            case 0xE7:
                alU.set(BIT_4, cpu.A);
                break;
            case 0xE8:
                alU.set(BIT_5, cpu.B);
                break;
            case 0xE9:
                alU.set(BIT_5, cpu.C);
                break;
            case 0xEA:
                alU.set(BIT_5, cpu.D);
                break;
            case 0xEB:
                alU.set(BIT_5, cpu.E);
                break;
            case 0xEC:
                alU.set(BIT_5, cpu.H);
                break;
            case 0xED:
                alU.set(BIT_5, cpu.L);
                break;
            case 0xEE:
                alU.set(BIT_5, addressing.HL);
                break;
            case 0xEF:
                alU.set(BIT_5, cpu.A);
                break;
            case 0xF0:
                alU.set(BIT_6, cpu.B);
                break;
            case 0xF1:
                alU.set(BIT_6, cpu.C);
                break;
            case 0xF2:
                alU.set(BIT_6, cpu.D);
                break;
            case 0xF3:
                alU.set(BIT_6, cpu.E);
                break;
            case 0xF4:
                alU.set(BIT_6, cpu.H);
                break;
            case 0xF5:
                alU.set(BIT_6, cpu.L);
                break;
            case 0xF6:
                alU.set(BIT_6, addressing.HL);
                break;
            case 0xF7:
                alU.set(BIT_6, cpu.A);
                break;
            case 0xF8:
                alU.set(BIT_7, cpu.B);
                break;
            case 0xF9:
                alU.set(BIT_7, cpu.C);
                break;
            case 0xFA:
                alU.set(BIT_7, cpu.D);
                break;
            case 0xFB:
                alU.set(BIT_7, cpu.E);
                break;
            case 0xFC:
                alU.set(BIT_7, cpu.H);
                break;
            case 0xFD:
                alU.set(BIT_7, cpu.L);
                break;
            case 0xFE:
                alU.set(BIT_7, addressing.HL);
                break;
            case 0xFF:
                alU.set(BIT_7, cpu.A);
                break;
//-------------------------------------Undocumented----------------------------------
            case 0x30:
                alU.sl1(cpu.B);
                break;
            case 0x31:
                alU.sl1(cpu.C);
                break;
            case 0x32:
                alU.sl1(cpu.D);
                break;
            case 0x33:
                alU.sl1(cpu.E);
                break;
            case 0x34:
                alU.sl1(cpu.H);
                break;
            case 0x35:
                alU.sl1(cpu.L);
                break;
            case 0x36:
                alU.sl1(addressing.HL);
                break;
            case 0x37:
                alU.sl1(cpu.A);
                break;
        }
    }
}
