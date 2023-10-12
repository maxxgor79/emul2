// 
// Decompiled by Procyon v0.5.36
// 

package z80core;

import java.util.Arrays;

public class Z80
{
    private final MemIoOps MemIoImpl;
    public int tEstados;
    private boolean execDone;
    private int opCode;
    private static final int CARRY_MASK = 1;
    private static final int ADDSUB_MASK = 2;
    private static final int PARITY_MASK = 4;
    private static final int OVERFLOW_MASK = 4;
    private static final int BIT3_MASK = 8;
    private static final int HALFCARRY_MASK = 16;
    private static final int BIT5_MASK = 32;
    private static final int ZERO_MASK = 64;
    private static final int SIGN_MASK = 128;
    private static final int FLAG_53_MASK = 40;
    private static final int FLAG_SZ_MASK = 192;
    private static final int FLAG_SZHN_MASK = 210;
    private static final int FLAG_SZP_MASK = 196;
    private static final int FLAG_SZHP_MASK = 212;
    private int regA;
    private int regB;
    private int regC;
    private int regD;
    private int regE;
    private int regH;
    private int regL;
    private int sz5h3pnFlags;
    private boolean carryFlag;
    private int regAalt;
    private int flagFalt;
    private int regBalt;
    private int regCalt;
    private int regDalt;
    private int regEalt;
    private int regHalt;
    private int regLalt;
    private int regPC;
    private int regIX;
    private int regIY;
    private int regSP;
    private int regI;
    private int regR;
    private boolean regRbit7;
    private boolean ffIFF1;
    private boolean ffIFF2;
    private boolean pendingEI;
    private boolean activeNMI;
    private boolean activeINT;
    private int modeINT;
    public final int IM0 = 0;
    public final int IM1 = 1;
    public final int IM2 = 2;
    private boolean halted;
    private int memptr;
    private static final int[] sz53n_addTable;
    private static final int[] sz53pn_addTable;
    private static final int[] sz53n_subTable;
    private static final int[] sz53pn_subTable;
    
    public Z80(final MemIoOps memoria) {
        this.ffIFF1 = false;
        this.ffIFF2 = false;
        this.pendingEI = false;
        this.activeNMI = false;
        this.activeINT = false;
        this.modeINT = 0;
        this.halted = false;
        this.MemIoImpl = memoria;
        this.tEstados = 0;
        this.execDone = false;
        this.reset();
    }
    
    public final int getRegA() {
        return this.regA;
    }
    
    public final void setRegA(final int valor) {
        this.regA = (valor & 0xFF);
    }
    
    public final int getRegB() {
        return this.regB;
    }
    
    public final void setRegB(final int valor) {
        this.regB = (valor & 0xFF);
    }
    
    public final int getRegC() {
        return this.regC;
    }
    
    public final void setRegC(final int valor) {
        this.regC = (valor & 0xFF);
    }
    
    public final int getRegD() {
        return this.regD;
    }
    
    public final void setRegD(final int valor) {
        this.regD = (valor & 0xFF);
    }
    
    public final int getRegE() {
        return this.regE;
    }
    
    public final void setRegE(final int valor) {
        this.regE = (valor & 0xFF);
    }
    
    public final int getRegH() {
        return this.regH;
    }
    
    public final void setRegH(final int valor) {
        this.regH = (valor & 0xFF);
    }
    
    public final int getRegL() {
        return this.regL;
    }
    
    public final void setRegL(final int valor) {
        this.regL = (valor & 0xFF);
    }
    
    public final int getRegAF() {
        return this.regA << 8 | this.getFlags();
    }
    
    public final void setRegAF(final int valor) {
        this.regA = (valor >>> 8 & 0xFF);
        this.setFlags(valor & 0xFF);
    }
    
    public final int getRegAFalt() {
        return this.regAalt << 8 | this.flagFalt;
    }
    
    public final void setRegAFalt(final int valor) {
        this.regAalt = (valor >>> 8 & 0xFF);
        this.flagFalt = (valor & 0xFF);
    }
    
    public final int getRegBC() {
        return this.regB << 8 | this.regC;
    }
    
    public final void setRegBC(int word) {
        word &= 0xFFFF;
        this.regB = word >>> 8;
        this.regC = (word & 0xFF);
    }
    
    private void incRegBC() {
        ++this.regC;
        if (this.regC < 256) {
            return;
        }
        this.regC = 0;
        ++this.regB;
        if (this.regB < 256) {
            return;
        }
        this.regB = 0;
    }
    
    private void decRegBC() {
        --this.regC;
        if (this.regC >= 0) {
            return;
        }
        this.regC = 255;
        --this.regB;
        if (this.regB >= 0) {
            return;
        }
        this.regB = 255;
    }
    
    public final int getRegBCalt() {
        return this.regBalt << 8 | this.regCalt;
    }
    
    public final void setRegBCalt(int word) {
        word &= 0xFFFF;
        this.regBalt = word >>> 8;
        this.regCalt = (word & 0xFF);
    }
    
    public final int getRegDE() {
        return this.regD << 8 | this.regE;
    }
    
    public final void setRegDE(int word) {
        word &= 0xFFFF;
        this.regD = word >>> 8;
        this.regE = (word & 0xFF);
    }
    
    private void incRegDE() {
        ++this.regE;
        if (this.regE < 256) {
            return;
        }
        this.regE = 0;
        ++this.regD;
        if (this.regD < 256) {
            return;
        }
        this.regD = 0;
    }
    
    private void decRegDE() {
        --this.regE;
        if (this.regE >= 0) {
            return;
        }
        this.regE = 255;
        --this.regD;
        if (this.regD >= 0) {
            return;
        }
        this.regD = 255;
    }
    
    public final int getRegDEalt() {
        return this.regDalt << 8 | this.regEalt;
    }
    
    public final void setRegDEalt(int word) {
        word &= 0xFFFF;
        this.regDalt = word >>> 8;
        this.regEalt = (word & 0xFF);
    }
    
    public final int getRegHL() {
        return this.regH << 8 | this.regL;
    }
    
    public final void setRegHL(int word) {
        word &= 0xFFFF;
        this.regH = word >>> 8;
        this.regL = (word & 0xFF);
    }
    
    private void incRegHL() {
        ++this.regL;
        if (this.regL < 256) {
            return;
        }
        this.regL = 0;
        ++this.regH;
        if (this.regH < 256) {
            return;
        }
        this.regH = 0;
    }
    
    private void decRegHL() {
        --this.regL;
        if (this.regL >= 0) {
            return;
        }
        this.regL = 255;
        --this.regH;
        if (this.regH >= 0) {
            return;
        }
        this.regH = 255;
    }
    
    public final int getRegHLalt() {
        return this.regHalt << 8 | this.regLalt;
    }
    
    public final void setRegHLalt(int word) {
        word &= 0xFFFF;
        this.regHalt = word >>> 8;
        this.regLalt = (word & 0xFF);
    }
    
    public final int getRegPC() {
        return this.regPC;
    }
    
    public final void setRegPC(final int address) {
        this.regPC = (address & 0xFFFF);
    }
    
    public final int getRegSP() {
        return this.regSP;
    }
    
    public final void setRegSP(final int word) {
        this.regSP = (word & 0xFFFF);
    }
    
    public final int getRegIX() {
        return this.regIX;
    }
    
    public final void setRegIX(final int valor) {
        this.regIX = (valor & 0xFFFF);
    }
    
    public final int getRegIY() {
        return this.regIY;
    }
    
    public final void setRegIY(final int valor) {
        this.regIY = (valor & 0xFFFF);
    }
    
    public final int getRegI() {
        return this.regI;
    }
    
    public final void setRegI(final int valor) {
        this.regI = (valor & 0xFF);
    }
    
    public final int getRegR() {
        if (this.regRbit7) {
            return (this.regR & 0x7F) | 0x80;
        }
        return this.regR & 0x7F;
    }
    
    public final void setRegR(int valor) {
        valor &= 0xFF;
        this.regR = valor;
        this.regRbit7 = (valor > 127);
    }
    
    public final int getPairIR() {
        if (this.regRbit7) {
            return this.regI << 8 | ((this.regR & 0x7F) | 0x80);
        }
        return this.regI << 8 | (this.regR & 0x7F);
    }
    
    public final boolean isCarryFlag() {
        return this.carryFlag;
    }
    
    public final void setCarryFlag(final boolean valor) {
        this.carryFlag = valor;
    }
    
    public final boolean isAddSubFlag() {
        return (this.sz5h3pnFlags & 0x2) != 0x0;
    }
    
    public final void setAddSubFlag(final boolean valor) {
        if (valor) {
            this.sz5h3pnFlags |= 0x2;
        }
        else {
            this.sz5h3pnFlags &= 0xFFFFFFFD;
        }
    }
    
    public final boolean isParOverFlag() {
        return (this.sz5h3pnFlags & 0x4) != 0x0;
    }
    
    public final void setParOverFlag(final boolean valor) {
        if (valor) {
            this.sz5h3pnFlags |= 0x4;
        }
        else {
            this.sz5h3pnFlags &= 0xFFFFFFFB;
        }
    }
    
    public final boolean isBit3Flag() {
        return (this.sz5h3pnFlags & 0x8) != 0x0;
    }
    
    public final void setBit3Fag(final boolean valor) {
        if (valor) {
            this.sz5h3pnFlags |= 0x8;
        }
        else {
            this.sz5h3pnFlags &= 0xFFFFFFF7;
        }
    }
    
    public final boolean isHalfCarryFlag() {
        return (this.sz5h3pnFlags & 0x10) != 0x0;
    }
    
    public final void setHalfCarryFlag(final boolean valor) {
        if (valor) {
            this.sz5h3pnFlags |= 0x10;
        }
        else {
            this.sz5h3pnFlags &= 0xFFFFFFEF;
        }
    }
    
    public final boolean isBit5Flag() {
        return (this.sz5h3pnFlags & 0x20) != 0x0;
    }
    
    public final void setBit5Flag(final boolean valor) {
        if (valor) {
            this.sz5h3pnFlags |= 0x20;
        }
        else {
            this.sz5h3pnFlags &= 0xFFFFFFDF;
        }
    }
    
    public final boolean isZeroFlag() {
        return (this.sz5h3pnFlags & 0x40) != 0x0;
    }
    
    public final void setZeroFlag(final boolean valor) {
        if (valor) {
            this.sz5h3pnFlags |= 0x40;
        }
        else {
            this.sz5h3pnFlags &= 0xFFFFFFBF;
        }
    }
    
    public final boolean isSignFlag() {
        return (this.sz5h3pnFlags & 0x80) != 0x0;
    }
    
    public final void setSignFlag(final boolean valor) {
        if (valor) {
            this.sz5h3pnFlags |= 0x80;
        }
        else {
            this.sz5h3pnFlags &= 0xFFFFFF7F;
        }
    }
    
    public final int getFlags() {
        int regF = this.sz5h3pnFlags;
        if (this.carryFlag) {
            regF |= 0x1;
        }
        return regF;
    }
    
    public final void setFlags(final int regF) {
        this.sz5h3pnFlags = (regF & 0xFFFFFFFE);
        if ((regF & 0x1) != 0x0) {
            this.carryFlag = true;
        }
        else {
            this.carryFlag = false;
        }
    }
    
    public final boolean isIFF1() {
        return this.ffIFF1;
    }
    
    public final void setIFF1(final boolean valor) {
        this.ffIFF1 = valor;
    }
    
    public final boolean isIFF2() {
        return this.ffIFF2;
    }
    
    public final void setIFF2(final boolean valor) {
        this.ffIFF2 = valor;
    }
    
    public final void triggerNMI() {
        this.activeNMI = true;
    }
    
    public final void setINTLine(final boolean intLine) {
        this.activeINT = intLine;
    }
    
    public final int getIM() {
        return this.modeINT;
    }
    
    public final void setIM(final int modo) {
        this.modeINT = modo;
    }
    
    public final boolean isHalted() {
        return this.halted;
    }
    
    public void setHalted(final boolean isHalted) {
        this.halted = isHalted;
    }
    
    public final void reset() {
        this.regPC = 0;
        this.regSP = 65535;
        final int n = 255;
        this.regAalt = n;
        this.regA = n;
        final int n2 = 255;
        this.regBalt = n2;
        this.regB = n2;
        final int n3 = 255;
        this.regCalt = n3;
        this.regC = n3;
        final int n4 = 255;
        this.regDalt = n4;
        this.regD = n4;
        final int n5 = 255;
        this.regEalt = n5;
        this.regE = n5;
        final int n6 = 255;
        this.regHalt = n6;
        this.regH = n6;
        final int n7 = 255;
        this.regLalt = n7;
        this.regL = n7;
        this.setFlags(255);
        this.flagFalt = 255;
        this.regIX = 65535;
        this.regIY = 65535;
        this.regI = 255;
        this.regR = 0;
        this.regRbit7 = false;
        this.ffIFF1 = false;
        this.ffIFF2 = false;
        this.pendingEI = false;
        this.activeNMI = false;
        this.activeINT = false;
        this.halted = false;
        this.setIM(0);
        this.memptr = 0;
        this.tEstados = 0;
    }
    
    private int inc8(int valor) {
        valor = (++valor & 0xFF);
        this.sz5h3pnFlags = Z80.sz53n_addTable[valor];
        if ((valor & 0xF) == 0x0) {
            this.sz5h3pnFlags |= 0x10;
        }
        if (valor == 128) {
            this.sz5h3pnFlags |= 0x4;
        }
        return valor;
    }
    
    private int dec8(int valor) {
        valor = (--valor & 0xFF);
        this.sz5h3pnFlags = Z80.sz53n_subTable[valor];
        if ((valor & 0xF) == 0xF) {
            this.sz5h3pnFlags |= 0x10;
        }
        if (valor == 127) {
            this.sz5h3pnFlags |= 0x4;
        }
        return valor;
    }
    
    private int rlc(int aux) {
        this.carryFlag = (aux > 127);
        aux <<= 1;
        if (this.carryFlag) {
            aux |= 0x1;
        }
        aux &= 0xFF;
        this.sz5h3pnFlags = Z80.sz53pn_addTable[aux];
        return aux;
    }
    
    private int rl(int aux) {
        final boolean carry = this.carryFlag;
        this.carryFlag = (aux > 127);
        aux <<= 1;
        if (carry) {
            aux |= 0x1;
        }
        aux &= 0xFF;
        this.sz5h3pnFlags = Z80.sz53pn_addTable[aux];
        return aux;
    }
    
    private int sla(int aux) {
        this.carryFlag = (aux > 127);
        aux <<= 1;
        aux &= 0xFE;
        this.sz5h3pnFlags = Z80.sz53pn_addTable[aux];
        return aux;
    }
    
    private int sll(int aux) {
        this.carryFlag = (aux > 127);
        aux <<= 1;
        aux |= 0x1;
        aux &= 0xFF;
        this.sz5h3pnFlags = Z80.sz53pn_addTable[aux];
        return aux;
    }
    
    private int rrc(int aux) {
        this.carryFlag = ((aux & 0x1) != 0x0);
        aux >>>= 1;
        if (this.carryFlag) {
            aux |= 0x80;
        }
        this.sz5h3pnFlags = Z80.sz53pn_addTable[aux];
        return aux;
    }
    
    private int rr(int aux) {
        final boolean carry = this.carryFlag;
        this.carryFlag = ((aux & 0x1) != 0x0);
        aux >>>= 1;
        if (carry) {
            aux |= 0x80;
        }
        this.sz5h3pnFlags = Z80.sz53pn_addTable[aux];
        return aux;
    }
    
    private void rrd() {
        final int aux = (this.regA & 0xF) << 4;
        final int regHL = this.getRegHL();
        final int memHL = this.MemIoImpl.peek8(regHL);
        this.regA = ((this.regA & 0xF0) | (memHL & 0xF));
        this.MemIoImpl.contendedStates(regHL, 4);
        this.MemIoImpl.poke8(regHL, memHL >>> 4 | aux);
        this.sz5h3pnFlags = Z80.sz53pn_addTable[this.regA];
        this.memptr = (regHL + 1 & 0xFFFF);
    }
    
    private void rld() {
        final int aux = this.regA & 0xF;
        final int regHL = this.getRegHL();
        final int memHL = this.MemIoImpl.peek8(regHL);
        this.regA = ((this.regA & 0xF0) | memHL >>> 4);
        this.MemIoImpl.contendedStates(regHL, 4);
        this.MemIoImpl.poke8(regHL, (memHL << 4 | aux) & 0xFF);
        this.sz5h3pnFlags = Z80.sz53pn_addTable[this.regA];
        this.memptr = (regHL + 1 & 0xFFFF);
    }
    
    private int sra(int aux) {
        final int tmp = aux & 0x80;
        this.carryFlag = ((aux & 0x1) != 0x0);
        aux = (aux >> 1 | tmp);
        this.sz5h3pnFlags = Z80.sz53pn_addTable[aux];
        return aux;
    }
    
    private int srl(int aux) {
        this.carryFlag = ((aux & 0x1) != 0x0);
        aux >>>= 1;
        this.sz5h3pnFlags = Z80.sz53pn_addTable[aux];
        return aux;
    }
    
    private int add16(final int reg16, final int oper16) {
        int res = reg16 + oper16;
        this.carryFlag = (res > 65535);
        this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xC4) | (res >>> 8 & 0x28));
        res &= 0xFFFF;
        if ((res & 0xFFF) < (reg16 & 0xFFF)) {
            this.sz5h3pnFlags |= 0x10;
        }
        this.memptr = (reg16 + 1 & 0xFFFF);
        return res;
    }
    
    private void add(final int valor) {
        int res = this.regA + valor;
        this.carryFlag = (res > 255);
        res &= 0xFF;
        this.sz5h3pnFlags = Z80.sz53n_addTable[res];
        if ((res & 0xF) < (this.regA & 0xF)) {
            this.sz5h3pnFlags |= 0x10;
        }
        if (((this.regA ^ ~valor) & (this.regA ^ res)) > 127) {
            this.sz5h3pnFlags |= 0x4;
        }
        this.regA = res;
    }
    
    private void adc(final int valor) {
        final int carry = this.carryFlag ? 1 : 0;
        int res = this.regA + valor + carry;
        this.carryFlag = (res > 255);
        res &= 0xFF;
        this.sz5h3pnFlags = Z80.sz53n_addTable[res];
        if ((this.regA & 0xF) + (valor & 0xF) + carry > 15) {
            this.sz5h3pnFlags |= 0x10;
        }
        if (((this.regA ^ ~valor) & (this.regA ^ res)) > 127) {
            this.sz5h3pnFlags |= 0x4;
        }
        this.regA = res;
    }
    
    private void adc16(final int valor) {
        final int carry = this.carryFlag ? 1 : 0;
        final int regHL = this.getRegHL();
        this.memptr = (regHL + 1 & 0xFFFF);
        int res = regHL + valor + carry;
        this.carryFlag = (res > 65535);
        res &= 0xFFFF;
        this.setRegHL(res);
        this.sz5h3pnFlags = Z80.sz53n_addTable[this.regH];
        if (res != 0) {
            this.sz5h3pnFlags &= 0xFFFFFFBF;
        }
        if ((regHL & 0xFFF) + (valor & 0xFFF) + carry > 4095) {
            this.sz5h3pnFlags |= 0x10;
        }
        if (((regHL ^ ~valor) & (regHL ^ res)) > 32767) {
            this.sz5h3pnFlags |= 0x4;
        }
    }
    
    private void sub(final int valor) {
        int res = this.regA - valor;
        this.carryFlag = ((res & 0x100) != 0x0);
        res &= 0xFF;
        this.sz5h3pnFlags = Z80.sz53n_subTable[res];
        if ((res & 0xF) > (this.regA & 0xF)) {
            this.sz5h3pnFlags |= 0x10;
        }
        if (((this.regA ^ valor) & (this.regA ^ res)) > 127) {
            this.sz5h3pnFlags |= 0x4;
        }
        this.regA = res;
    }
    
    private void sbc(final int valor) {
        final int carry = this.carryFlag ? 1 : 0;
        int res = this.regA - valor - carry;
        this.carryFlag = ((res & 0x100) != 0x0);
        res &= 0xFF;
        this.sz5h3pnFlags = Z80.sz53n_subTable[res];
        if (((this.regA & 0xF) - (valor & 0xF) - carry & 0x10) != 0x0) {
            this.sz5h3pnFlags |= 0x10;
        }
        if (((this.regA ^ valor) & (this.regA ^ res)) > 127) {
            this.sz5h3pnFlags |= 0x4;
        }
        this.regA = res;
    }
    
    private void sbc16(final int valor) {
        final int carry = this.carryFlag ? 1 : 0;
        final int regHL = this.getRegHL();
        this.memptr = (regHL + 1 & 0xFFFF);
        int res = regHL - valor - carry;
        this.carryFlag = ((res & 0x10000) != 0x0);
        res &= 0xFFFF;
        this.setRegHL(res);
        this.sz5h3pnFlags = Z80.sz53n_subTable[this.regH];
        if (res != 0) {
            this.sz5h3pnFlags &= 0xFFFFFFBF;
        }
        if (((regHL & 0xFFF) - (valor & 0xFFF) - carry & 0x1000) != 0x0) {
            this.sz5h3pnFlags |= 0x10;
        }
        if (((regHL ^ valor) & (regHL ^ res)) > 32767) {
            this.sz5h3pnFlags |= 0x4;
        }
    }
    
    private void and(final int valor) {
        this.regA &= valor;
        this.carryFlag = false;
        this.sz5h3pnFlags = (Z80.sz53pn_addTable[this.regA] | 0x10);
    }
    
    public final void xor(final int valor) {
        this.regA ^= valor;
        this.carryFlag = false;
        this.sz5h3pnFlags = Z80.sz53pn_addTable[this.regA];
    }
    
    private void or(final int valor) {
        this.regA |= valor;
        this.carryFlag = false;
        this.sz5h3pnFlags = Z80.sz53pn_addTable[this.regA];
    }
    
    public final void cp(final int valor) {
        int res = this.regA - valor;
        this.carryFlag = ((res & 0x100) != 0x0);
        res &= 0xFF;
        this.sz5h3pnFlags = ((Z80.sz53n_addTable[valor] & 0x28) | (Z80.sz53n_subTable[res] & 0xD2));
        if ((res & 0xF) > (this.regA & 0xF)) {
            this.sz5h3pnFlags |= 0x10;
        }
        if (((this.regA ^ valor) & (this.regA ^ res)) > 127) {
            this.sz5h3pnFlags |= 0x4;
        }
    }
    
    private void daa() {
        int suma = 0;
        boolean carry = this.carryFlag;
        if ((this.sz5h3pnFlags & 0x10) != 0x0 || (this.regA & 0xF) > 9) {
            suma = 6;
        }
        if (carry || this.regA > 153) {
            suma |= 0x60;
        }
        if (this.regA > 153) {
            carry = true;
        }
        if ((this.sz5h3pnFlags & 0x2) != 0x0) {
            this.sub(suma);
            this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0x10) | Z80.sz53pn_subTable[this.regA]);
        }
        else {
            this.add(suma);
            this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0x10) | Z80.sz53pn_addTable[this.regA]);
        }
        this.carryFlag = carry;
    }
    
    private int pop() {
        final int word = this.MemIoImpl.peek16(this.regSP);
        this.regSP = (this.regSP + 2 & 0xFFFF);
        return word;
    }
    
    private void push(final int valor) {
        final int msb = valor >>> 8 & 0xFF;
        --this.regSP;
        this.MemIoImpl.poke8(this.regSP & 0xFFFF, msb);
        this.regSP = (this.regSP - 1 & 0xFFFF);
        this.MemIoImpl.poke8(this.regSP, valor & 0xFF);
    }
    
    private void push(final int msb, final int lsb) {
        --this.regSP;
        this.MemIoImpl.poke8(this.regSP & 0xFFFF, msb);
        this.regSP = (this.regSP - 1 & 0xFFFF);
        this.MemIoImpl.poke8(this.regSP, lsb);
    }
    
    private void ldi() {
        int work8 = this.MemIoImpl.peek8(this.getRegHL());
        this.MemIoImpl.poke8(this.getRegDE(), work8);
        this.MemIoImpl.contendedStates(this.getRegDE(), 2);
        this.incRegHL();
        this.incRegDE();
        this.decRegBC();
        work8 += this.regA;
        this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xC0) | (work8 & 0x8));
        if ((work8 & 0x2) != 0x0) {
            this.sz5h3pnFlags |= 0x20;
        }
        if (this.getRegBC() != 0) {
            this.sz5h3pnFlags |= 0x4;
        }
    }
    
    private void ldd() {
        int work8 = this.MemIoImpl.peek8(this.getRegHL());
        this.MemIoImpl.poke8(this.getRegDE(), work8);
        this.MemIoImpl.contendedStates(this.getRegDE(), 2);
        this.decRegHL();
        this.decRegDE();
        this.decRegBC();
        work8 += this.regA;
        this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xC0) | (work8 & 0x8));
        if ((work8 & 0x2) != 0x0) {
            this.sz5h3pnFlags |= 0x20;
        }
        if (this.getRegBC() != 0) {
            this.sz5h3pnFlags |= 0x4;
        }
    }
    
    private void cpi() {
        final int regHL = this.getRegHL();
        int memHL = this.MemIoImpl.peek8(regHL);
        final boolean carry = this.carryFlag;
        this.cp(memHL);
        this.MemIoImpl.contendedStates(regHL, 5);
        this.carryFlag = carry;
        this.incRegHL();
        this.decRegBC();
        memHL = this.regA - memHL - (((this.sz5h3pnFlags & 0x10) != 0x0) ? 1 : 0);
        this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD2) | (memHL & 0x8));
        if ((memHL & 0x2) != 0x0) {
            this.sz5h3pnFlags |= 0x20;
        }
        if (this.getRegBC() != 0) {
            this.sz5h3pnFlags |= 0x4;
        }
        this.memptr = (this.memptr + 1 & 0xFFFF);
    }
    
    private void cpd() {
        final int regHL = this.getRegHL();
        int memHL = this.MemIoImpl.peek8(regHL);
        final boolean carry = this.carryFlag;
        this.cp(memHL);
        this.MemIoImpl.contendedStates(regHL, 5);
        this.carryFlag = carry;
        this.decRegHL();
        this.decRegBC();
        memHL = this.regA - memHL - (((this.sz5h3pnFlags & 0x10) != 0x0) ? 1 : 0);
        this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD2) | (memHL & 0x8));
        if ((memHL & 0x2) != 0x0) {
            this.sz5h3pnFlags |= 0x20;
        }
        if (this.getRegBC() != 0) {
            this.sz5h3pnFlags |= 0x4;
        }
        this.memptr = (this.memptr - 1 & 0xFFFF);
    }
    
    private void ini() {
        this.MemIoImpl.contendedStates(this.getPairIR(), 1);
        final int work8 = this.MemIoImpl.inPort(this.getRegBC());
        this.MemIoImpl.poke8(this.getRegHL(), work8);
        this.memptr = (this.getRegBC() + 1 & 0xFFFF);
        --this.regB;
        this.regB &= 0xFF;
        this.incRegHL();
        this.sz5h3pnFlags = Z80.sz53pn_addTable[this.regB];
        if (work8 > 127) {
            this.sz5h3pnFlags |= 0x2;
        }
        this.carryFlag = false;
        final int tmp = work8 + (this.regC + 1 & 0xFF);
        if (tmp > 255) {
            this.sz5h3pnFlags |= 0x10;
            this.carryFlag = true;
        }
        if ((Z80.sz53pn_addTable[(tmp & 0x7) ^ this.regB] & 0x4) == 0x4) {
            this.sz5h3pnFlags |= 0x4;
        }
        else {
            this.sz5h3pnFlags &= 0xFFFFFFFB;
        }
    }
    
    private void ind() {
        this.MemIoImpl.contendedStates(this.getPairIR(), 1);
        final int work8 = this.MemIoImpl.inPort(this.getRegBC());
        this.MemIoImpl.poke8(this.getRegHL(), work8);
        this.memptr = (this.getRegBC() - 1 & 0xFFFF);
        --this.regB;
        this.regB &= 0xFF;
        this.decRegHL();
        this.sz5h3pnFlags = Z80.sz53pn_addTable[this.regB];
        if (work8 > 127) {
            this.sz5h3pnFlags |= 0x2;
        }
        this.carryFlag = false;
        final int tmp = work8 + (this.regC - 1 & 0xFF);
        if (tmp > 255) {
            this.sz5h3pnFlags |= 0x10;
            this.carryFlag = true;
        }
        if ((Z80.sz53pn_addTable[(tmp & 0x7) ^ this.regB] & 0x4) == 0x4) {
            this.sz5h3pnFlags |= 0x4;
        }
        else {
            this.sz5h3pnFlags &= 0xFFFFFFFB;
        }
    }
    
    private void outi() {
        this.MemIoImpl.contendedStates(this.getPairIR(), 1);
        --this.regB;
        this.regB &= 0xFF;
        this.memptr = (this.getRegBC() + 1 & 0xFFFF);
        final int work8 = this.MemIoImpl.peek8(this.getRegHL());
        this.MemIoImpl.outPort(this.getRegBC(), work8);
        this.incRegHL();
        this.carryFlag = false;
        if (work8 > 127) {
            this.sz5h3pnFlags = Z80.sz53n_subTable[this.regB];
        }
        else {
            this.sz5h3pnFlags = Z80.sz53n_addTable[this.regB];
        }
        if (this.regL + work8 > 255) {
            this.sz5h3pnFlags |= 0x10;
            this.carryFlag = true;
        }
        if ((Z80.sz53pn_addTable[(this.regL + work8 & 0x7) ^ this.regB] & 0x4) == 0x4) {
            this.sz5h3pnFlags |= 0x4;
        }
    }
    
    private void outd() {
        this.MemIoImpl.contendedStates(this.getPairIR(), 1);
        --this.regB;
        this.regB &= 0xFF;
        this.memptr = (this.getRegBC() - 1 & 0xFFFF);
        final int work8 = this.MemIoImpl.peek8(this.getRegHL());
        this.MemIoImpl.outPort(this.getRegBC(), work8);
        this.decRegHL();
        this.carryFlag = false;
        if (work8 > 127) {
            this.sz5h3pnFlags = Z80.sz53n_subTable[this.regB];
        }
        else {
            this.sz5h3pnFlags = Z80.sz53n_addTable[this.regB];
        }
        if (this.regL + work8 > 255) {
            this.sz5h3pnFlags |= 0x10;
            this.carryFlag = true;
        }
        if ((Z80.sz53pn_addTable[(this.regL + work8 & 0x7) ^ this.regB] & 0x4) == 0x4) {
            this.sz5h3pnFlags |= 0x4;
        }
    }
    
    private void exx() {
        int work8 = this.regB;
        this.regB = this.regBalt;
        this.regBalt = work8;
        work8 = this.regC;
        this.regC = this.regCalt;
        this.regCalt = work8;
        work8 = this.regD;
        this.regD = this.regDalt;
        this.regDalt = work8;
        work8 = this.regE;
        this.regE = this.regEalt;
        this.regEalt = work8;
        work8 = this.regH;
        this.regH = this.regHalt;
        this.regHalt = work8;
        work8 = this.regL;
        this.regL = this.regLalt;
        this.regLalt = work8;
    }
    
    private void bit(final int mask, final int reg) {
        final boolean zeroFlag = (mask & reg) == 0x0;
        this.sz5h3pnFlags = ((Z80.sz53n_addTable[reg] & 0xFFFFFF3B) | 0x10);
        if (zeroFlag) {
            this.sz5h3pnFlags |= 0x44;
        }
        if (mask == 128 && !zeroFlag) {
            this.sz5h3pnFlags |= 0x80;
        }
    }
    
    private int res(final int mask, final int reg) {
        return reg & ~mask;
    }
    
    private int set(final int mask, final int reg) {
        return reg | mask;
    }
    
    public final void interruption() {
        if (this.halted) {
            this.halted = false;
            this.regPC = (this.regPC + 1 & 0xFFFF);
        }
        this.tEstados += 7;
        ++this.regR;
        this.ffIFF1 = false;
        this.ffIFF2 = false;
        this.push(this.regPC);
        if (this.modeINT == 2) {
            this.regPC = this.MemIoImpl.peek16(this.regI << 8 | 0xFF);
        }
        else {
            this.regPC = 56;
        }
        this.memptr = this.regPC;
    }
    
    private void nmi() {
        this.MemIoImpl.fetchOpcode(this.regPC);
        ++this.tEstados;
        if (this.halted) {
            this.halted = false;
            this.regPC = (this.regPC + 1 & 0xFFFF);
        }
        ++this.regR;
        this.ffIFF1 = false;
        this.push(this.regPC);
        final int n = 102;
        this.memptr = n;
        this.regPC = n;
    }
    
    public final int getTEstados() {
        return this.tEstados;
    }
    
    public final void setTEstados(final int tstates) {
        this.tEstados = tstates;
    }
    
    public final void addTEstados(final int delay) {
        this.tEstados += delay;
    }
    
    public final void setExecDone(final boolean notify) {
        this.execDone = notify;
    }
    
    public final int execute(final int statesLimit) {
        while (this.tEstados < statesLimit) {
            final int tmp = this.tEstados;
            if (this.activeNMI) {
                this.activeNMI = false;
                this.nmi();
            }
            else {
                if (this.activeINT && this.ffIFF1 && !this.pendingEI) {
                    this.interruption();
                }
                ++this.regR;
                this.opCode = this.MemIoImpl.fetchOpcode(this.regPC);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                this.decodeOpcode(this.opCode);
                if (this.pendingEI && this.opCode != 251) {
                    this.pendingEI = false;
                }
                if (!this.execDone) {
                    continue;
                }
                this.MemIoImpl.execDone(this.tEstados - tmp);
            }
        }
        return this.tEstados;
    }
    
    private void decodeOpcode(final int opCode) {
        switch (opCode) {
            case 1: {
                this.setRegBC(this.MemIoImpl.peek16(this.regPC));
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 2: {
                this.memptr = this.getRegBC();
                this.MemIoImpl.poke8(this.memptr, this.regA);
                this.memptr = (this.regA << 8 | (this.memptr + 1 & 0xFF));
                break;
            }
            case 3: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 2);
                this.incRegBC();
                break;
            }
            case 4: {
                this.regB = this.inc8(this.regB);
                break;
            }
            case 5: {
                this.regB = this.dec8(this.regB);
                break;
            }
            case 6: {
                this.regB = this.MemIoImpl.peek8(this.regPC);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 7: {
                this.carryFlag = (this.regA > 127);
                this.regA <<= 1;
                if (this.carryFlag) {
                    this.regA |= 0x1;
                }
                this.regA &= 0xFF;
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xC4) | (this.regA & 0x28));
                break;
            }
            case 8: {
                int work8 = this.regA;
                this.regA = this.regAalt;
                this.regAalt = work8;
                work8 = this.getFlags();
                this.setFlags(this.flagFalt);
                this.flagFalt = work8;
                break;
            }
            case 9: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 7);
                this.setRegHL(this.add16(this.getRegHL(), this.getRegBC()));
                break;
            }
            case 10: {
                this.memptr = this.getRegBC();
                this.regA = this.MemIoImpl.peek8(this.memptr);
                this.memptr = (this.memptr + 1 & 0xFFFF);
                break;
            }
            case 11: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 2);
                this.decRegBC();
                break;
            }
            case 12: {
                this.regC = this.inc8(this.regC);
                break;
            }
            case 13: {
                this.regC = this.dec8(this.regC);
                break;
            }
            case 14: {
                this.regC = this.MemIoImpl.peek8(this.regPC);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 15: {
                this.carryFlag = ((this.regA & 0x1) != 0x0);
                this.regA >>>= 1;
                if (this.carryFlag) {
                    this.regA |= 0x80;
                }
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xC4) | (this.regA & 0x28));
                break;
            }
            case 16: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                final byte salto = (byte)this.MemIoImpl.peek8(this.regPC);
                --this.regB;
                this.regB &= 0xFF;
                if (this.regB != 0) {
                    this.MemIoImpl.contendedStates(this.regPC, 5);
                    this.memptr = (this.regPC + salto + 1 & 0xFFFF);
                    this.regPC = this.memptr;
                    break;
                }
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 17: {
                this.setRegDE(this.MemIoImpl.peek16(this.regPC));
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 18: {
                this.memptr = this.getRegDE();
                this.MemIoImpl.poke8(this.memptr, this.regA);
                this.memptr = (this.regA << 8 | (this.memptr + 1 & 0xFF));
                break;
            }
            case 19: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 2);
                this.incRegDE();
                break;
            }
            case 20: {
                this.regD = this.inc8(this.regD);
                break;
            }
            case 21: {
                this.regD = this.dec8(this.regD);
                break;
            }
            case 22: {
                this.regD = this.MemIoImpl.peek8(this.regPC);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 23: {
                final boolean oldCarry = this.carryFlag;
                this.carryFlag = (this.regA > 127);
                this.regA <<= 1;
                if (oldCarry) {
                    this.regA |= 0x1;
                }
                this.regA &= 0xFF;
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xC4) | (this.regA & 0x28));
                break;
            }
            case 24: {
                final byte salto = (byte)this.MemIoImpl.peek8(this.regPC);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.regPC = (this.regPC + salto + 1 & 0xFFFF);
                this.memptr = this.regPC;
                break;
            }
            case 25: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 7);
                this.setRegHL(this.add16(this.getRegHL(), this.getRegDE()));
                break;
            }
            case 26: {
                this.memptr = this.getRegDE();
                this.regA = this.MemIoImpl.peek8(this.memptr);
                this.memptr = (this.memptr + 1 & 0xFFFF);
                break;
            }
            case 27: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 2);
                this.decRegDE();
                break;
            }
            case 28: {
                this.regE = this.inc8(this.regE);
                break;
            }
            case 29: {
                this.regE = this.dec8(this.regE);
                break;
            }
            case 30: {
                this.regE = this.MemIoImpl.peek8(this.regPC);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 31: {
                final boolean oldCarry = this.carryFlag;
                this.carryFlag = ((this.regA & 0x1) != 0x0);
                this.regA >>>= 1;
                if (oldCarry) {
                    this.regA |= 0x80;
                }
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xC4) | (this.regA & 0x28));
                break;
            }
            case 32: {
                final byte salto = (byte)this.MemIoImpl.peek8(this.regPC);
                if ((this.sz5h3pnFlags & 0x40) == 0x0) {
                    this.MemIoImpl.contendedStates(this.regPC, 5);
                    this.regPC += salto;
                }
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 33: {
                this.setRegHL(this.MemIoImpl.peek16(this.regPC));
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 34: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                this.MemIoImpl.poke16(this.memptr, this.getRegHL());
                this.memptr = (this.memptr + 1 & 0xFFFF);
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 35: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 2);
                this.incRegHL();
                break;
            }
            case 36: {
                this.regH = this.inc8(this.regH);
                break;
            }
            case 37: {
                this.regH = this.dec8(this.regH);
                break;
            }
            case 38: {
                this.regH = this.MemIoImpl.peek8(this.regPC);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 39: {
                this.daa();
                break;
            }
            case 40: {
                final byte salto = (byte)this.MemIoImpl.peek8(this.regPC);
                if ((this.sz5h3pnFlags & 0x40) != 0x0) {
                    this.MemIoImpl.contendedStates(this.regPC, 5);
                    this.regPC += salto;
                }
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 41: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 7);
                final int work9 = this.getRegHL();
                this.setRegHL(this.add16(work9, work9));
                break;
            }
            case 42: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                this.setRegHL(this.MemIoImpl.peek16(this.memptr));
                this.memptr = (this.memptr + 1 & 0xFFFF);
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 43: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 2);
                this.decRegHL();
                break;
            }
            case 44: {
                this.regL = this.inc8(this.regL);
                break;
            }
            case 45: {
                this.regL = this.dec8(this.regL);
                break;
            }
            case 46: {
                this.regL = this.MemIoImpl.peek8(this.regPC);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 47: {
                this.regA = ((this.regA ^ 0xFF) & 0xFF);
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xC4) | 0x10 | (this.regA & 0x28) | 0x2);
                break;
            }
            case 48: {
                final byte salto = (byte)this.MemIoImpl.peek8(this.regPC);
                if (!this.carryFlag) {
                    this.MemIoImpl.contendedStates(this.regPC, 5);
                    this.regPC += salto;
                }
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 49: {
                this.regSP = this.MemIoImpl.peek16(this.regPC);
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 50: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                this.MemIoImpl.poke8(this.memptr, this.regA);
                this.memptr = (this.regA << 8 | (this.memptr + 1 & 0xFF));
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 51: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 2);
                this.regSP = (this.regSP + 1 & 0xFFFF);
                break;
            }
            case 52: {
                final int work9 = this.getRegHL();
                this.MemIoImpl.contendedStates(work9, 1);
                final int work8 = this.inc8(this.MemIoImpl.peek8(work9));
                this.MemIoImpl.poke8(work9, work8);
                break;
            }
            case 53: {
                final int work9 = this.getRegHL();
                this.MemIoImpl.contendedStates(work9, 1);
                final int work8 = this.dec8(this.MemIoImpl.peek8(work9));
                this.MemIoImpl.poke8(work9, work8);
                break;
            }
            case 54: {
                this.MemIoImpl.poke8(this.getRegHL(), this.MemIoImpl.peek8(this.regPC));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 55: {
                this.carryFlag = true;
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xC4) | (this.regA & 0x28));
                break;
            }
            case 56: {
                final byte salto = (byte)this.MemIoImpl.peek8(this.regPC);
                if (this.carryFlag) {
                    this.MemIoImpl.contendedStates(this.regPC, 5);
                    this.regPC += salto;
                }
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 57: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 7);
                this.setRegHL(this.add16(this.getRegHL(), this.regSP));
                break;
            }
            case 58: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                this.regA = this.MemIoImpl.peek8(this.memptr);
                this.memptr = (this.memptr + 1 & 0xFFFF);
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 59: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 2);
                this.regSP = (this.regSP - 1 & 0xFFFF);
                break;
            }
            case 60: {
                this.regA = this.inc8(this.regA);
                break;
            }
            case 61: {
                this.regA = this.dec8(this.regA);
                break;
            }
            case 62: {
                this.regA = this.MemIoImpl.peek8(this.regPC);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 63: {
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xC4) | (this.regA & 0x28));
                if (this.carryFlag) {
                    this.sz5h3pnFlags |= 0x10;
                }
                this.carryFlag = !this.carryFlag;
            }
            case 65: {
                this.regB = this.regC;
                break;
            }
            case 66: {
                this.regB = this.regD;
                break;
            }
            case 67: {
                this.regB = this.regE;
                break;
            }
            case 68: {
                this.regB = this.regH;
                break;
            }
            case 69: {
                this.regB = this.regL;
                break;
            }
            case 70: {
                this.regB = this.MemIoImpl.peek8(this.getRegHL());
                break;
            }
            case 71: {
                this.regB = this.regA;
                break;
            }
            case 72: {
                this.regC = this.regB;
            }
            case 74: {
                this.regC = this.regD;
                break;
            }
            case 75: {
                this.regC = this.regE;
                break;
            }
            case 76: {
                this.regC = this.regH;
                break;
            }
            case 77: {
                this.regC = this.regL;
                break;
            }
            case 78: {
                this.regC = this.MemIoImpl.peek8(this.getRegHL());
                break;
            }
            case 79: {
                this.regC = this.regA;
                break;
            }
            case 80: {
                this.regD = this.regB;
                break;
            }
            case 81: {
                this.regD = this.regC;
            }
            case 83: {
                this.regD = this.regE;
                break;
            }
            case 84: {
                this.regD = this.regH;
                break;
            }
            case 85: {
                this.regD = this.regL;
                break;
            }
            case 86: {
                this.regD = this.MemIoImpl.peek8(this.getRegHL());
                break;
            }
            case 87: {
                this.regD = this.regA;
                break;
            }
            case 88: {
                this.regE = this.regB;
                break;
            }
            case 89: {
                this.regE = this.regC;
                break;
            }
            case 90: {
                this.regE = this.regD;
            }
            case 92: {
                this.regE = this.regH;
                break;
            }
            case 93: {
                this.regE = this.regL;
                break;
            }
            case 94: {
                this.regE = this.MemIoImpl.peek8(this.getRegHL());
                break;
            }
            case 95: {
                this.regE = this.regA;
                break;
            }
            case 96: {
                this.regH = this.regB;
                break;
            }
            case 97: {
                this.regH = this.regC;
                break;
            }
            case 98: {
                this.regH = this.regD;
                break;
            }
            case 99: {
                this.regH = this.regE;
            }
            case 101: {
                this.regH = this.regL;
                break;
            }
            case 102: {
                this.regH = this.MemIoImpl.peek8(this.getRegHL());
                break;
            }
            case 103: {
                this.regH = this.regA;
                break;
            }
            case 104: {
                this.regL = this.regB;
                break;
            }
            case 105: {
                this.regL = this.regC;
                break;
            }
            case 106: {
                this.regL = this.regD;
                break;
            }
            case 107: {
                this.regL = this.regE;
                break;
            }
            case 108: {
                this.regL = this.regH;
            }
            case 110: {
                this.regL = this.MemIoImpl.peek8(this.getRegHL());
                break;
            }
            case 111: {
                this.regL = this.regA;
                break;
            }
            case 112: {
                this.MemIoImpl.poke8(this.getRegHL(), this.regB);
                break;
            }
            case 113: {
                this.MemIoImpl.poke8(this.getRegHL(), this.regC);
                break;
            }
            case 114: {
                this.MemIoImpl.poke8(this.getRegHL(), this.regD);
                break;
            }
            case 115: {
                this.MemIoImpl.poke8(this.getRegHL(), this.regE);
                break;
            }
            case 116: {
                this.MemIoImpl.poke8(this.getRegHL(), this.regH);
                break;
            }
            case 117: {
                this.MemIoImpl.poke8(this.getRegHL(), this.regL);
                break;
            }
            case 118: {
                this.regPC = (this.regPC - 1 & 0xFFFF);
                this.halted = true;
                break;
            }
            case 119: {
                this.MemIoImpl.poke8(this.getRegHL(), this.regA);
                break;
            }
            case 120: {
                this.regA = this.regB;
                break;
            }
            case 121: {
                this.regA = this.regC;
                break;
            }
            case 122: {
                this.regA = this.regD;
                break;
            }
            case 123: {
                this.regA = this.regE;
                break;
            }
            case 124: {
                this.regA = this.regH;
                break;
            }
            case 125: {
                this.regA = this.regL;
                break;
            }
            case 126: {
                this.regA = this.MemIoImpl.peek8(this.getRegHL());
            }
            case 128: {
                this.add(this.regB);
                break;
            }
            case 129: {
                this.add(this.regC);
                break;
            }
            case 130: {
                this.add(this.regD);
                break;
            }
            case 131: {
                this.add(this.regE);
                break;
            }
            case 132: {
                this.add(this.regH);
                break;
            }
            case 133: {
                this.add(this.regL);
                break;
            }
            case 134: {
                this.add(this.MemIoImpl.peek8(this.getRegHL()));
                break;
            }
            case 135: {
                this.add(this.regA);
                break;
            }
            case 136: {
                this.adc(this.regB);
                break;
            }
            case 137: {
                this.adc(this.regC);
                break;
            }
            case 138: {
                this.adc(this.regD);
                break;
            }
            case 139: {
                this.adc(this.regE);
                break;
            }
            case 140: {
                this.adc(this.regH);
                break;
            }
            case 141: {
                this.adc(this.regL);
                break;
            }
            case 142: {
                this.adc(this.MemIoImpl.peek8(this.getRegHL()));
                break;
            }
            case 143: {
                this.adc(this.regA);
                break;
            }
            case 144: {
                this.sub(this.regB);
                break;
            }
            case 145: {
                this.sub(this.regC);
                break;
            }
            case 146: {
                this.sub(this.regD);
                break;
            }
            case 147: {
                this.sub(this.regE);
                break;
            }
            case 148: {
                this.sub(this.regH);
                break;
            }
            case 149: {
                this.sub(this.regL);
                break;
            }
            case 150: {
                this.sub(this.MemIoImpl.peek8(this.getRegHL()));
                break;
            }
            case 151: {
                this.sub(this.regA);
                break;
            }
            case 152: {
                this.sbc(this.regB);
                break;
            }
            case 153: {
                this.sbc(this.regC);
                break;
            }
            case 154: {
                this.sbc(this.regD);
                break;
            }
            case 155: {
                this.sbc(this.regE);
                break;
            }
            case 156: {
                this.sbc(this.regH);
                break;
            }
            case 157: {
                this.sbc(this.regL);
                break;
            }
            case 158: {
                this.sbc(this.MemIoImpl.peek8(this.getRegHL()));
                break;
            }
            case 159: {
                this.sbc(this.regA);
                break;
            }
            case 160: {
                this.and(this.regB);
                break;
            }
            case 161: {
                this.and(this.regC);
                break;
            }
            case 162: {
                this.and(this.regD);
                break;
            }
            case 163: {
                this.and(this.regE);
                break;
            }
            case 164: {
                this.and(this.regH);
                break;
            }
            case 165: {
                this.and(this.regL);
                break;
            }
            case 166: {
                this.and(this.MemIoImpl.peek8(this.getRegHL()));
                break;
            }
            case 167: {
                this.and(this.regA);
                break;
            }
            case 168: {
                this.xor(this.regB);
                break;
            }
            case 169: {
                this.xor(this.regC);
                break;
            }
            case 170: {
                this.xor(this.regD);
                break;
            }
            case 171: {
                this.xor(this.regE);
                break;
            }
            case 172: {
                this.xor(this.regH);
                break;
            }
            case 173: {
                this.xor(this.regL);
                break;
            }
            case 174: {
                this.xor(this.MemIoImpl.peek8(this.getRegHL()));
                break;
            }
            case 175: {
                this.xor(this.regA);
                break;
            }
            case 176: {
                this.or(this.regB);
                break;
            }
            case 177: {
                this.or(this.regC);
                break;
            }
            case 178: {
                this.or(this.regD);
                break;
            }
            case 179: {
                this.or(this.regE);
                break;
            }
            case 180: {
                this.or(this.regH);
                break;
            }
            case 181: {
                this.or(this.regL);
                break;
            }
            case 182: {
                this.or(this.MemIoImpl.peek8(this.getRegHL()));
                break;
            }
            case 183: {
                this.or(this.regA);
                break;
            }
            case 184: {
                this.cp(this.regB);
                break;
            }
            case 185: {
                this.cp(this.regC);
                break;
            }
            case 186: {
                this.cp(this.regD);
                break;
            }
            case 187: {
                this.cp(this.regE);
                break;
            }
            case 188: {
                this.cp(this.regH);
                break;
            }
            case 189: {
                this.cp(this.regL);
                break;
            }
            case 190: {
                this.cp(this.MemIoImpl.peek8(this.getRegHL()));
                break;
            }
            case 191: {
                this.cp(this.regA);
                break;
            }
            case 192: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                if ((this.sz5h3pnFlags & 0x40) == 0x0) {
                    final int pop = this.pop();
                    this.memptr = pop;
                    this.regPC = pop;
                    break;
                }
                break;
            }
            case 193: {
                this.setRegBC(this.pop());
                break;
            }
            case 194: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                if ((this.sz5h3pnFlags & 0x40) == 0x0) {
                    this.regPC = this.memptr;
                    break;
                }
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 195: {
                final int peek16 = this.MemIoImpl.peek16(this.regPC);
                this.regPC = peek16;
                this.memptr = peek16;
                break;
            }
            case 196: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                if ((this.sz5h3pnFlags & 0x40) == 0x0) {
                    this.MemIoImpl.contendedStates(this.regPC + 1 & 0xFFFF, 1);
                    this.push(this.regPC + 2);
                    this.regPC = this.memptr;
                    break;
                }
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 197: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                this.push(this.regB, this.regC);
                break;
            }
            case 198: {
                this.add(this.MemIoImpl.peek8(this.regPC));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 199: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                this.push(this.regPC);
                final int n = 0;
                this.memptr = n;
                this.regPC = n;
                break;
            }
            case 200: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                if ((this.sz5h3pnFlags & 0x40) != 0x0) {
                    final int pop2 = this.pop();
                    this.memptr = pop2;
                    this.regPC = pop2;
                    break;
                }
                break;
            }
            case 201: {
                final int pop3 = this.pop();
                this.memptr = pop3;
                this.regPC = pop3;
                break;
            }
            case 202: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                if ((this.sz5h3pnFlags & 0x40) != 0x0) {
                    this.regPC = this.memptr;
                    break;
                }
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 203: {
                this.decodeCB();
                break;
            }
            case 204: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                if ((this.sz5h3pnFlags & 0x40) != 0x0) {
                    this.MemIoImpl.contendedStates(this.regPC + 1 & 0xFFFF, 1);
                    this.push(this.regPC + 2);
                    this.regPC = this.memptr;
                    break;
                }
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 205: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                this.MemIoImpl.contendedStates(this.regPC + 1 & 0xFFFF, 1);
                this.push(this.regPC + 2);
                this.regPC = this.memptr;
                break;
            }
            case 206: {
                this.adc(this.MemIoImpl.peek8(this.regPC));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 207: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                this.push(this.regPC);
                final int n2 = 8;
                this.memptr = n2;
                this.regPC = n2;
                break;
            }
            case 208: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                if (!this.carryFlag) {
                    final int pop4 = this.pop();
                    this.memptr = pop4;
                    this.regPC = pop4;
                    break;
                }
                break;
            }
            case 209: {
                this.setRegDE(this.pop());
                break;
            }
            case 210: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                if (!this.carryFlag) {
                    this.regPC = this.memptr;
                    break;
                }
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 211: {
                final int work8 = this.MemIoImpl.peek8(this.regPC);
                this.MemIoImpl.outPort(this.regA << 8 | work8, this.regA);
                this.memptr = (this.regA << 8 | work8 + 1);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 212: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                if (!this.carryFlag) {
                    this.MemIoImpl.contendedStates(this.regPC + 1 & 0xFFFF, 1);
                    this.push(this.regPC + 2);
                    this.regPC = this.memptr;
                    break;
                }
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 213: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                this.push(this.regD, this.regE);
                break;
            }
            case 214: {
                this.sub(this.MemIoImpl.peek8(this.regPC));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 215: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                this.push(this.regPC);
                final int n3 = 16;
                this.memptr = n3;
                this.regPC = n3;
                break;
            }
            case 216: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                if (this.carryFlag) {
                    final int pop5 = this.pop();
                    this.memptr = pop5;
                    this.regPC = pop5;
                    break;
                }
                break;
            }
            case 217: {
                this.exx();
                break;
            }
            case 218: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                if (this.carryFlag) {
                    this.regPC = this.memptr;
                    break;
                }
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 219: {
                final int work8 = this.MemIoImpl.peek8(this.regPC);
                this.memptr = (this.regA << 8) + work8 + 1;
                this.regA = this.MemIoImpl.inPort(this.regA << 8 | work8);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 220: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                if (this.carryFlag) {
                    this.MemIoImpl.contendedStates(this.regPC + 1 & 0xFFFF, 1);
                    this.push(this.regPC + 2);
                    this.regPC = this.memptr;
                    break;
                }
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 221: {
                this.regIX = this.decodeDDFD(this.regIX);
                break;
            }
            case 222: {
                this.sbc(this.MemIoImpl.peek8(this.regPC));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 223: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                this.push(this.regPC);
                final int n4 = 24;
                this.memptr = n4;
                this.regPC = n4;
                break;
            }
            case 224: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                if ((this.sz5h3pnFlags & 0x4) == 0x0) {
                    final int pop6 = this.pop();
                    this.memptr = pop6;
                    this.regPC = pop6;
                    break;
                }
                break;
            }
            case 225: {
                this.setRegHL(this.pop());
                break;
            }
            case 226: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                if ((this.sz5h3pnFlags & 0x4) == 0x0) {
                    this.regPC = this.memptr;
                    break;
                }
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 227: {
                final int work9 = this.getRegHL();
                this.setRegHL(this.MemIoImpl.peek16(this.regSP));
                this.MemIoImpl.contendedStates(this.regSP + 1 & 0xFFFF, 1);
                this.MemIoImpl.poke8(this.regSP + 1 & 0xFFFF, work9 >>> 8 & 0xFF);
                this.MemIoImpl.poke8(this.regSP, work9 & 0xFF);
                this.MemIoImpl.contendedStates(this.regSP, 2);
                this.memptr = this.getRegHL();
                break;
            }
            case 228: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                if ((this.sz5h3pnFlags & 0x4) == 0x0) {
                    this.MemIoImpl.contendedStates(this.regPC + 1 & 0xFFFF, 1);
                    this.push(this.regPC + 2);
                    this.regPC = this.memptr;
                    break;
                }
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 229: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                this.push(this.regH, this.regL);
                break;
            }
            case 230: {
                this.and(this.MemIoImpl.peek8(this.regPC));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 231: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                this.push(this.regPC);
                final int n5 = 32;
                this.memptr = n5;
                this.regPC = n5;
                break;
            }
            case 232: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                if ((this.sz5h3pnFlags & 0x4) != 0x0) {
                    final int pop7 = this.pop();
                    this.memptr = pop7;
                    this.regPC = pop7;
                    break;
                }
                break;
            }
            case 233: {
                this.regPC = this.getRegHL();
                break;
            }
            case 234: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                if ((this.sz5h3pnFlags & 0x4) != 0x0) {
                    this.regPC = this.memptr;
                    break;
                }
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 235: {
                int work8 = this.regH;
                this.regH = this.regD;
                this.regD = work8;
                work8 = this.regL;
                this.regL = this.regE;
                this.regE = work8;
                break;
            }
            case 236: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                if ((this.sz5h3pnFlags & 0x4) != 0x0) {
                    this.MemIoImpl.contendedStates(this.regPC + 1 & 0xFFFF, 1);
                    this.push(this.regPC + 2);
                    this.regPC = this.memptr;
                    break;
                }
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 237: {
                this.decodeED();
                break;
            }
            case 238: {
                this.xor(this.MemIoImpl.peek8(this.regPC));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 239: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                this.push(this.regPC);
                final int n6 = 40;
                this.memptr = n6;
                this.regPC = n6;
                break;
            }
            case 240: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                if (this.sz5h3pnFlags < 128) {
                    final int pop8 = this.pop();
                    this.memptr = pop8;
                    this.regPC = pop8;
                    break;
                }
                break;
            }
            case 241: {
                final int work9 = this.pop();
                this.regA = work9 >>> 8;
                this.setFlags(work9 & 0xFF);
                break;
            }
            case 242: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                if (this.sz5h3pnFlags < 128) {
                    this.regPC = this.memptr;
                    break;
                }
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 243: {
                this.ffIFF1 = false;
                this.ffIFF2 = false;
                break;
            }
            case 244: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                if (this.sz5h3pnFlags < 128) {
                    this.MemIoImpl.contendedStates(this.regPC + 1 & 0xFFFF, 1);
                    this.push(this.regPC + 2);
                    this.regPC = this.memptr;
                    break;
                }
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 245: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                this.push(this.regA, this.getFlags());
                break;
            }
            case 246: {
                this.or(this.MemIoImpl.peek8(this.regPC));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 247: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                this.push(this.regPC);
                final int n7 = 48;
                this.memptr = n7;
                this.regPC = n7;
                break;
            }
            case 248: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                if (this.sz5h3pnFlags > 127) {
                    final int pop9 = this.pop();
                    this.memptr = pop9;
                    this.regPC = pop9;
                    break;
                }
                break;
            }
            case 249: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 2);
                this.regSP = this.getRegHL();
                break;
            }
            case 250: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                if (this.sz5h3pnFlags > 127) {
                    this.regPC = this.memptr;
                    break;
                }
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 251: {
                this.ffIFF1 = true;
                this.ffIFF2 = true;
                this.pendingEI = true;
                break;
            }
            case 252: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                if (this.sz5h3pnFlags > 127) {
                    this.MemIoImpl.contendedStates(this.regPC + 1 & 0xFFFF, 1);
                    this.push(this.regPC + 2);
                    this.regPC = this.memptr;
                    break;
                }
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 253: {
                this.regIY = this.decodeDDFD(this.regIY);
                break;
            }
            case 254: {
                this.cp(this.MemIoImpl.peek8(this.regPC));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 255: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                this.push(this.regPC);
                final int n8 = 56;
                this.memptr = n8;
                this.regPC = n8;
                break;
            }
        }
    }
    
    private void decodeCB() {
        ++this.regR;
        this.opCode = this.MemIoImpl.fetchOpcode(this.regPC);
        this.regPC = (this.regPC + 1 & 0xFFFF);
        switch (this.opCode) {
            case 0: {
                this.regB = this.rlc(this.regB);
                break;
            }
            case 1: {
                this.regC = this.rlc(this.regC);
                break;
            }
            case 2: {
                this.regD = this.rlc(this.regD);
                break;
            }
            case 3: {
                this.regE = this.rlc(this.regE);
                break;
            }
            case 4: {
                this.regH = this.rlc(this.regH);
                break;
            }
            case 5: {
                this.regL = this.rlc(this.regL);
                break;
            }
            case 6: {
                final int work16 = this.getRegHL();
                final int work17 = this.rlc(this.MemIoImpl.peek8(work16));
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, work17);
                break;
            }
            case 7: {
                this.regA = this.rlc(this.regA);
                break;
            }
            case 8: {
                this.regB = this.rrc(this.regB);
                break;
            }
            case 9: {
                this.regC = this.rrc(this.regC);
                break;
            }
            case 10: {
                this.regD = this.rrc(this.regD);
                break;
            }
            case 11: {
                this.regE = this.rrc(this.regE);
                break;
            }
            case 12: {
                this.regH = this.rrc(this.regH);
                break;
            }
            case 13: {
                this.regL = this.rrc(this.regL);
                break;
            }
            case 14: {
                final int work16 = this.getRegHL();
                final int work17 = this.rrc(this.MemIoImpl.peek8(work16));
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, work17);
                break;
            }
            case 15: {
                this.regA = this.rrc(this.regA);
                break;
            }
            case 16: {
                this.regB = this.rl(this.regB);
                break;
            }
            case 17: {
                this.regC = this.rl(this.regC);
                break;
            }
            case 18: {
                this.regD = this.rl(this.regD);
                break;
            }
            case 19: {
                this.regE = this.rl(this.regE);
                break;
            }
            case 20: {
                this.regH = this.rl(this.regH);
                break;
            }
            case 21: {
                this.regL = this.rl(this.regL);
                break;
            }
            case 22: {
                final int work16 = this.getRegHL();
                final int work17 = this.rl(this.MemIoImpl.peek8(work16));
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, work17);
                break;
            }
            case 23: {
                this.regA = this.rl(this.regA);
                break;
            }
            case 24: {
                this.regB = this.rr(this.regB);
                break;
            }
            case 25: {
                this.regC = this.rr(this.regC);
                break;
            }
            case 26: {
                this.regD = this.rr(this.regD);
                break;
            }
            case 27: {
                this.regE = this.rr(this.regE);
                break;
            }
            case 28: {
                this.regH = this.rr(this.regH);
                break;
            }
            case 29: {
                this.regL = this.rr(this.regL);
                break;
            }
            case 30: {
                final int work16 = this.getRegHL();
                final int work17 = this.rr(this.MemIoImpl.peek8(work16));
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, work17);
                break;
            }
            case 31: {
                this.regA = this.rr(this.regA);
                break;
            }
            case 32: {
                this.regB = this.sla(this.regB);
                break;
            }
            case 33: {
                this.regC = this.sla(this.regC);
                break;
            }
            case 34: {
                this.regD = this.sla(this.regD);
                break;
            }
            case 35: {
                this.regE = this.sla(this.regE);
                break;
            }
            case 36: {
                this.regH = this.sla(this.regH);
                break;
            }
            case 37: {
                this.regL = this.sla(this.regL);
                break;
            }
            case 38: {
                final int work16 = this.getRegHL();
                final int work17 = this.sla(this.MemIoImpl.peek8(work16));
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, work17);
                break;
            }
            case 39: {
                this.regA = this.sla(this.regA);
                break;
            }
            case 40: {
                this.regB = this.sra(this.regB);
                break;
            }
            case 41: {
                this.regC = this.sra(this.regC);
                break;
            }
            case 42: {
                this.regD = this.sra(this.regD);
                break;
            }
            case 43: {
                this.regE = this.sra(this.regE);
                break;
            }
            case 44: {
                this.regH = this.sra(this.regH);
                break;
            }
            case 45: {
                this.regL = this.sra(this.regL);
                break;
            }
            case 46: {
                final int work16 = this.getRegHL();
                final int work17 = this.sra(this.MemIoImpl.peek8(work16));
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, work17);
                break;
            }
            case 47: {
                this.regA = this.sra(this.regA);
                break;
            }
            case 48: {
                this.regB = this.sll(this.regB);
                break;
            }
            case 49: {
                this.regC = this.sll(this.regC);
                break;
            }
            case 50: {
                this.regD = this.sll(this.regD);
                break;
            }
            case 51: {
                this.regE = this.sll(this.regE);
                break;
            }
            case 52: {
                this.regH = this.sll(this.regH);
                break;
            }
            case 53: {
                this.regL = this.sll(this.regL);
                break;
            }
            case 54: {
                final int work16 = this.getRegHL();
                final int work17 = this.sll(this.MemIoImpl.peek8(work16));
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, work17);
                break;
            }
            case 55: {
                this.regA = this.sll(this.regA);
                break;
            }
            case 56: {
                this.regB = this.srl(this.regB);
                break;
            }
            case 57: {
                this.regC = this.srl(this.regC);
                break;
            }
            case 58: {
                this.regD = this.srl(this.regD);
                break;
            }
            case 59: {
                this.regE = this.srl(this.regE);
                break;
            }
            case 60: {
                this.regH = this.srl(this.regH);
                break;
            }
            case 61: {
                this.regL = this.srl(this.regL);
                break;
            }
            case 62: {
                final int work16 = this.getRegHL();
                final int work17 = this.srl(this.MemIoImpl.peek8(work16));
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, work17);
                break;
            }
            case 63: {
                this.regA = this.srl(this.regA);
                break;
            }
            case 64: {
                this.bit(1, this.regB);
                break;
            }
            case 65: {
                this.bit(1, this.regC);
                break;
            }
            case 66: {
                this.bit(1, this.regD);
                break;
            }
            case 67: {
                this.bit(1, this.regE);
                break;
            }
            case 68: {
                this.bit(1, this.regH);
                break;
            }
            case 69: {
                this.bit(1, this.regL);
                break;
            }
            case 70: {
                final int work16 = this.getRegHL();
                this.bit(1, this.MemIoImpl.peek8(work16));
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD4) | (this.memptr >>> 8 & 0x28));
                this.MemIoImpl.contendedStates(work16, 1);
                break;
            }
            case 71: {
                this.bit(1, this.regA);
                break;
            }
            case 72: {
                this.bit(2, this.regB);
                break;
            }
            case 73: {
                this.bit(2, this.regC);
                break;
            }
            case 74: {
                this.bit(2, this.regD);
                break;
            }
            case 75: {
                this.bit(2, this.regE);
                break;
            }
            case 76: {
                this.bit(2, this.regH);
                break;
            }
            case 77: {
                this.bit(2, this.regL);
                break;
            }
            case 78: {
                final int work16 = this.getRegHL();
                this.bit(2, this.MemIoImpl.peek8(work16));
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD4) | (this.memptr >>> 8 & 0x28));
                this.MemIoImpl.contendedStates(work16, 1);
                break;
            }
            case 79: {
                this.bit(2, this.regA);
                break;
            }
            case 80: {
                this.bit(4, this.regB);
                break;
            }
            case 81: {
                this.bit(4, this.regC);
                break;
            }
            case 82: {
                this.bit(4, this.regD);
                break;
            }
            case 83: {
                this.bit(4, this.regE);
                break;
            }
            case 84: {
                this.bit(4, this.regH);
                break;
            }
            case 85: {
                this.bit(4, this.regL);
                break;
            }
            case 86: {
                final int work16 = this.getRegHL();
                this.bit(4, this.MemIoImpl.peek8(work16));
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD4) | (this.memptr >>> 8 & 0x28));
                this.MemIoImpl.contendedStates(work16, 1);
                break;
            }
            case 87: {
                this.bit(4, this.regA);
                break;
            }
            case 88: {
                this.bit(8, this.regB);
                break;
            }
            case 89: {
                this.bit(8, this.regC);
                break;
            }
            case 90: {
                this.bit(8, this.regD);
                break;
            }
            case 91: {
                this.bit(8, this.regE);
                break;
            }
            case 92: {
                this.bit(8, this.regH);
                break;
            }
            case 93: {
                this.bit(8, this.regL);
                break;
            }
            case 94: {
                final int work16 = this.getRegHL();
                this.bit(8, this.MemIoImpl.peek8(work16));
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD4) | (this.memptr >>> 8 & 0x28));
                this.MemIoImpl.contendedStates(work16, 1);
                break;
            }
            case 95: {
                this.bit(8, this.regA);
                break;
            }
            case 96: {
                this.bit(16, this.regB);
                break;
            }
            case 97: {
                this.bit(16, this.regC);
                break;
            }
            case 98: {
                this.bit(16, this.regD);
                break;
            }
            case 99: {
                this.bit(16, this.regE);
                break;
            }
            case 100: {
                this.bit(16, this.regH);
                break;
            }
            case 101: {
                this.bit(16, this.regL);
                break;
            }
            case 102: {
                final int work16 = this.getRegHL();
                this.bit(16, this.MemIoImpl.peek8(work16));
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD4) | (this.memptr >>> 8 & 0x28));
                this.MemIoImpl.contendedStates(work16, 1);
                break;
            }
            case 103: {
                this.bit(16, this.regA);
                break;
            }
            case 104: {
                this.bit(32, this.regB);
                break;
            }
            case 105: {
                this.bit(32, this.regC);
                break;
            }
            case 106: {
                this.bit(32, this.regD);
                break;
            }
            case 107: {
                this.bit(32, this.regE);
                break;
            }
            case 108: {
                this.bit(32, this.regH);
                break;
            }
            case 109: {
                this.bit(32, this.regL);
                break;
            }
            case 110: {
                final int work16 = this.getRegHL();
                this.bit(32, this.MemIoImpl.peek8(work16));
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD4) | (this.memptr >>> 8 & 0x28));
                this.MemIoImpl.contendedStates(work16, 1);
                break;
            }
            case 111: {
                this.bit(32, this.regA);
                break;
            }
            case 112: {
                this.bit(64, this.regB);
                break;
            }
            case 113: {
                this.bit(64, this.regC);
                break;
            }
            case 114: {
                this.bit(64, this.regD);
                break;
            }
            case 115: {
                this.bit(64, this.regE);
                break;
            }
            case 116: {
                this.bit(64, this.regH);
                break;
            }
            case 117: {
                this.bit(64, this.regL);
                break;
            }
            case 118: {
                final int work16 = this.getRegHL();
                this.bit(64, this.MemIoImpl.peek8(work16));
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD4) | (this.memptr >>> 8 & 0x28));
                this.MemIoImpl.contendedStates(work16, 1);
                break;
            }
            case 119: {
                this.bit(64, this.regA);
                break;
            }
            case 120: {
                this.bit(128, this.regB);
                break;
            }
            case 121: {
                this.bit(128, this.regC);
                break;
            }
            case 122: {
                this.bit(128, this.regD);
                break;
            }
            case 123: {
                this.bit(128, this.regE);
                break;
            }
            case 124: {
                this.bit(128, this.regH);
                break;
            }
            case 125: {
                this.bit(128, this.regL);
                break;
            }
            case 126: {
                final int work16 = this.getRegHL();
                this.bit(128, this.MemIoImpl.peek8(work16));
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD4) | (this.memptr >>> 8 & 0x28));
                this.MemIoImpl.contendedStates(work16, 1);
                break;
            }
            case 127: {
                this.bit(128, this.regA);
                break;
            }
            case 128: {
                this.regB = this.res(1, this.regB);
                break;
            }
            case 129: {
                this.regC = this.res(1, this.regC);
                break;
            }
            case 130: {
                this.regD = this.res(1, this.regD);
                break;
            }
            case 131: {
                this.regE = this.res(1, this.regE);
                break;
            }
            case 132: {
                this.regH = this.res(1, this.regH);
                break;
            }
            case 133: {
                this.regL = this.res(1, this.regL);
                break;
            }
            case 134: {
                final int work16 = this.getRegHL();
                final int work17 = this.MemIoImpl.peek8(work16);
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, this.res(1, work17));
                break;
            }
            case 135: {
                this.regA = this.res(1, this.regA);
                break;
            }
            case 136: {
                this.regB = this.res(2, this.regB);
                break;
            }
            case 137: {
                this.regC = this.res(2, this.regC);
                break;
            }
            case 138: {
                this.regD = this.res(2, this.regD);
                break;
            }
            case 139: {
                this.regE = this.res(2, this.regE);
                break;
            }
            case 140: {
                this.regH = this.res(2, this.regH);
                break;
            }
            case 141: {
                this.regL = this.res(2, this.regL);
                break;
            }
            case 142: {
                final int work16 = this.getRegHL();
                final int work17 = this.MemIoImpl.peek8(work16);
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, this.res(2, work17));
                break;
            }
            case 143: {
                this.regA = this.res(2, this.regA);
                break;
            }
            case 144: {
                this.regB = this.res(4, this.regB);
                break;
            }
            case 145: {
                this.regC = this.res(4, this.regC);
                break;
            }
            case 146: {
                this.regD = this.res(4, this.regD);
                break;
            }
            case 147: {
                this.regE = this.res(4, this.regE);
                break;
            }
            case 148: {
                this.regH = this.res(4, this.regH);
                break;
            }
            case 149: {
                this.regL = this.res(4, this.regL);
                break;
            }
            case 150: {
                final int work16 = this.getRegHL();
                final int work17 = this.MemIoImpl.peek8(work16);
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, this.res(4, work17));
                break;
            }
            case 151: {
                this.regA = this.res(4, this.regA);
                break;
            }
            case 152: {
                this.regB = this.res(8, this.regB);
                break;
            }
            case 153: {
                this.regC = this.res(8, this.regC);
                break;
            }
            case 154: {
                this.regD = this.res(8, this.regD);
                break;
            }
            case 155: {
                this.regE = this.res(8, this.regE);
                break;
            }
            case 156: {
                this.regH = this.res(8, this.regH);
                break;
            }
            case 157: {
                this.regL = this.res(8, this.regL);
                break;
            }
            case 158: {
                final int work16 = this.getRegHL();
                final int work17 = this.MemIoImpl.peek8(work16);
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, this.res(8, work17));
                break;
            }
            case 159: {
                this.regA = this.res(8, this.regA);
                break;
            }
            case 160: {
                this.regB = this.res(16, this.regB);
                break;
            }
            case 161: {
                this.regC = this.res(16, this.regC);
                break;
            }
            case 162: {
                this.regD = this.res(16, this.regD);
                break;
            }
            case 163: {
                this.regE = this.res(16, this.regE);
                break;
            }
            case 164: {
                this.regH = this.res(16, this.regH);
                break;
            }
            case 165: {
                this.regL = this.res(16, this.regL);
                break;
            }
            case 166: {
                final int work16 = this.getRegHL();
                final int work17 = this.MemIoImpl.peek8(work16);
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, this.res(16, work17));
                break;
            }
            case 167: {
                this.regA = this.res(16, this.regA);
                break;
            }
            case 168: {
                this.regB = this.res(32, this.regB);
                break;
            }
            case 169: {
                this.regC = this.res(32, this.regC);
                break;
            }
            case 170: {
                this.regD = this.res(32, this.regD);
                break;
            }
            case 171: {
                this.regE = this.res(32, this.regE);
                break;
            }
            case 172: {
                this.regH = this.res(32, this.regH);
                break;
            }
            case 173: {
                this.regL = this.res(32, this.regL);
                break;
            }
            case 174: {
                final int work16 = this.getRegHL();
                final int work17 = this.MemIoImpl.peek8(work16);
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, this.res(32, work17));
                break;
            }
            case 175: {
                this.regA = this.res(32, this.regA);
                break;
            }
            case 176: {
                this.regB = this.res(64, this.regB);
                break;
            }
            case 177: {
                this.regC = this.res(64, this.regC);
                break;
            }
            case 178: {
                this.regD = this.res(64, this.regD);
                break;
            }
            case 179: {
                this.regE = this.res(64, this.regE);
                break;
            }
            case 180: {
                this.regH = this.res(64, this.regH);
                break;
            }
            case 181: {
                this.regL = this.res(64, this.regL);
                break;
            }
            case 182: {
                final int work16 = this.getRegHL();
                final int work17 = this.MemIoImpl.peek8(work16);
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, this.res(64, work17));
                break;
            }
            case 183: {
                this.regA = this.res(64, this.regA);
                break;
            }
            case 184: {
                this.regB = this.res(128, this.regB);
                break;
            }
            case 185: {
                this.regC = this.res(128, this.regC);
                break;
            }
            case 186: {
                this.regD = this.res(128, this.regD);
                break;
            }
            case 187: {
                this.regE = this.res(128, this.regE);
                break;
            }
            case 188: {
                this.regH = this.res(128, this.regH);
                break;
            }
            case 189: {
                this.regL = this.res(128, this.regL);
                break;
            }
            case 190: {
                final int work16 = this.getRegHL();
                final int work17 = this.MemIoImpl.peek8(work16);
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, this.res(128, work17));
                break;
            }
            case 191: {
                this.regA = this.res(128, this.regA);
                break;
            }
            case 192: {
                this.regB = this.set(1, this.regB);
                break;
            }
            case 193: {
                this.regC = this.set(1, this.regC);
                break;
            }
            case 194: {
                this.regD = this.set(1, this.regD);
                break;
            }
            case 195: {
                this.regE = this.set(1, this.regE);
                break;
            }
            case 196: {
                this.regH = this.set(1, this.regH);
                break;
            }
            case 197: {
                this.regL = this.set(1, this.regL);
                break;
            }
            case 198: {
                final int work16 = this.getRegHL();
                final int work17 = this.MemIoImpl.peek8(work16);
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, this.set(1, work17));
                break;
            }
            case 199: {
                this.regA = this.set(1, this.regA);
                break;
            }
            case 200: {
                this.regB = this.set(2, this.regB);
                break;
            }
            case 201: {
                this.regC = this.set(2, this.regC);
                break;
            }
            case 202: {
                this.regD = this.set(2, this.regD);
                break;
            }
            case 203: {
                this.regE = this.set(2, this.regE);
                break;
            }
            case 204: {
                this.regH = this.set(2, this.regH);
                break;
            }
            case 205: {
                this.regL = this.set(2, this.regL);
                break;
            }
            case 206: {
                final int work16 = this.getRegHL();
                final int work17 = this.MemIoImpl.peek8(work16);
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, this.set(2, work17));
                break;
            }
            case 207: {
                this.regA = this.set(2, this.regA);
                break;
            }
            case 208: {
                this.regB = this.set(4, this.regB);
                break;
            }
            case 209: {
                this.regC = this.set(4, this.regC);
                break;
            }
            case 210: {
                this.regD = this.set(4, this.regD);
                break;
            }
            case 211: {
                this.regE = this.set(4, this.regE);
                break;
            }
            case 212: {
                this.regH = this.set(4, this.regH);
                break;
            }
            case 213: {
                this.regL = this.set(4, this.regL);
                break;
            }
            case 214: {
                final int work16 = this.getRegHL();
                final int work17 = this.MemIoImpl.peek8(work16);
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, this.set(4, work17));
                break;
            }
            case 215: {
                this.regA = this.set(4, this.regA);
                break;
            }
            case 216: {
                this.regB = this.set(8, this.regB);
                break;
            }
            case 217: {
                this.regC = this.set(8, this.regC);
                break;
            }
            case 218: {
                this.regD = this.set(8, this.regD);
                break;
            }
            case 219: {
                this.regE = this.set(8, this.regE);
                break;
            }
            case 220: {
                this.regH = this.set(8, this.regH);
                break;
            }
            case 221: {
                this.regL = this.set(8, this.regL);
                break;
            }
            case 222: {
                final int work16 = this.getRegHL();
                final int work17 = this.MemIoImpl.peek8(work16);
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, this.set(8, work17));
                break;
            }
            case 223: {
                this.regA = this.set(8, this.regA);
                break;
            }
            case 224: {
                this.regB = this.set(16, this.regB);
                break;
            }
            case 225: {
                this.regC = this.set(16, this.regC);
                break;
            }
            case 226: {
                this.regD = this.set(16, this.regD);
                break;
            }
            case 227: {
                this.regE = this.set(16, this.regE);
                break;
            }
            case 228: {
                this.regH = this.set(16, this.regH);
                break;
            }
            case 229: {
                this.regL = this.set(16, this.regL);
                break;
            }
            case 230: {
                final int work16 = this.getRegHL();
                final int work17 = this.MemIoImpl.peek8(work16);
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, this.set(16, work17));
                break;
            }
            case 231: {
                this.regA = this.set(16, this.regA);
                break;
            }
            case 232: {
                this.regB = this.set(32, this.regB);
                break;
            }
            case 233: {
                this.regC = this.set(32, this.regC);
                break;
            }
            case 234: {
                this.regD = this.set(32, this.regD);
                break;
            }
            case 235: {
                this.regE = this.set(32, this.regE);
                break;
            }
            case 236: {
                this.regH = this.set(32, this.regH);
                break;
            }
            case 237: {
                this.regL = this.set(32, this.regL);
                break;
            }
            case 238: {
                final int work16 = this.getRegHL();
                final int work17 = this.MemIoImpl.peek8(work16);
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, this.set(32, work17));
                break;
            }
            case 239: {
                this.regA = this.set(32, this.regA);
                break;
            }
            case 240: {
                this.regB = this.set(64, this.regB);
                break;
            }
            case 241: {
                this.regC = this.set(64, this.regC);
                break;
            }
            case 242: {
                this.regD = this.set(64, this.regD);
                break;
            }
            case 243: {
                this.regE = this.set(64, this.regE);
                break;
            }
            case 244: {
                this.regH = this.set(64, this.regH);
                break;
            }
            case 245: {
                this.regL = this.set(64, this.regL);
                break;
            }
            case 246: {
                final int work16 = this.getRegHL();
                final int work17 = this.MemIoImpl.peek8(work16);
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, this.set(64, work17));
                break;
            }
            case 247: {
                this.regA = this.set(64, this.regA);
                break;
            }
            case 248: {
                this.regB = this.set(128, this.regB);
                break;
            }
            case 249: {
                this.regC = this.set(128, this.regC);
                break;
            }
            case 250: {
                this.regD = this.set(128, this.regD);
                break;
            }
            case 251: {
                this.regE = this.set(128, this.regE);
                break;
            }
            case 252: {
                this.regH = this.set(128, this.regH);
                break;
            }
            case 253: {
                this.regL = this.set(128, this.regL);
                break;
            }
            case 254: {
                final int work16 = this.getRegHL();
                final int work17 = this.MemIoImpl.peek8(work16);
                this.MemIoImpl.contendedStates(work16, 1);
                this.MemIoImpl.poke8(work16, this.set(128, work17));
                break;
            }
            case 255: {
                this.regA = this.set(128, this.regA);
                break;
            }
        }
    }
    
    private int decodeDDFD(int regIXY) {
        int work8 = this.tEstados;
        ++this.regR;
        this.opCode = this.MemIoImpl.fetchOpcode(this.regPC);
        this.regPC = (this.regPC + 1 & 0xFFFF);
        switch (this.opCode) {
            case 9: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 7);
                regIXY = this.add16(regIXY, this.getRegBC());
                break;
            }
            case 25: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 7);
                regIXY = this.add16(regIXY, this.getRegDE());
                break;
            }
            case 33: {
                regIXY = this.MemIoImpl.peek16(this.regPC);
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 34: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                this.MemIoImpl.poke16(this.memptr, regIXY);
                this.memptr = (this.memptr + 1 & 0xFFFF);
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 35: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 2);
                regIXY = (regIXY + 1 & 0xFFFF);
                break;
            }
            case 36: {
                regIXY = (this.inc8(regIXY >>> 8) << 8 | (regIXY & 0xFF));
                break;
            }
            case 37: {
                regIXY = (this.dec8(regIXY >>> 8) << 8 | (regIXY & 0xFF));
                break;
            }
            case 38: {
                regIXY = (this.MemIoImpl.peek8(this.regPC) << 8 | (regIXY & 0xFF));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 41: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 7);
                regIXY = this.add16(regIXY, regIXY);
                break;
            }
            case 42: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                regIXY = this.MemIoImpl.peek16(this.memptr);
                this.memptr = (this.memptr + 1 & 0xFFFF);
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 43: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 2);
                regIXY = (regIXY - 1 & 0xFFFF);
                break;
            }
            case 44: {
                regIXY = ((regIXY & 0xFF00) | this.inc8(regIXY & 0xFF));
                break;
            }
            case 45: {
                regIXY = ((regIXY & 0xFF00) | this.dec8(regIXY & 0xFF));
                break;
            }
            case 46: {
                regIXY = ((regIXY & 0xFF00) | this.MemIoImpl.peek8(this.regPC));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 52: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                work8 = this.MemIoImpl.peek8(this.memptr);
                this.MemIoImpl.contendedStates(this.memptr, 1);
                this.MemIoImpl.poke8(this.memptr, this.inc8(work8));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 53: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                work8 = this.MemIoImpl.peek8(this.memptr);
                this.MemIoImpl.contendedStates(this.memptr, 1);
                this.MemIoImpl.poke8(this.memptr, this.dec8(work8));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 54: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                work8 = this.MemIoImpl.peek8(this.regPC);
                this.MemIoImpl.contendedStates(this.regPC, 2);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                this.MemIoImpl.poke8(this.memptr, work8);
                break;
            }
            case 57: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 7);
                regIXY = this.add16(regIXY, this.regSP);
                break;
            }
            case 68: {
                this.regB = regIXY >>> 8;
                break;
            }
            case 69: {
                this.regB = (regIXY & 0xFF);
                break;
            }
            case 70: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.regB = this.MemIoImpl.peek8(this.memptr);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 76: {
                this.regC = regIXY >>> 8;
                break;
            }
            case 77: {
                this.regC = (regIXY & 0xFF);
                break;
            }
            case 78: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.regC = this.MemIoImpl.peek8(this.memptr);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 84: {
                this.regD = regIXY >>> 8;
                break;
            }
            case 85: {
                this.regD = (regIXY & 0xFF);
                break;
            }
            case 86: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.regD = this.MemIoImpl.peek8(this.memptr);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 92: {
                this.regE = regIXY >>> 8;
                break;
            }
            case 93: {
                this.regE = (regIXY & 0xFF);
                break;
            }
            case 94: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.regE = this.MemIoImpl.peek8(this.memptr);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 96: {
                regIXY = ((regIXY & 0xFF) | this.regB << 8);
                break;
            }
            case 97: {
                regIXY = ((regIXY & 0xFF) | this.regC << 8);
                break;
            }
            case 98: {
                regIXY = ((regIXY & 0xFF) | this.regD << 8);
                break;
            }
            case 99: {
                regIXY = ((regIXY & 0xFF) | this.regE << 8);
                break;
            }
            case 100: {
                break;
            }
            case 101: {
                work8 = regIXY;
                regIXY = ((regIXY & 0xFF) | (regIXY & 0xFF) << 8);
                break;
            }
            case 102: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.regH = this.MemIoImpl.peek8(this.memptr);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 103: {
                regIXY = ((regIXY & 0xFF) | this.regA << 8);
                break;
            }
            case 104: {
                regIXY = ((regIXY & 0xFF00) | this.regB);
                break;
            }
            case 105: {
                regIXY = ((regIXY & 0xFF00) | this.regC);
                break;
            }
            case 106: {
                regIXY = ((regIXY & 0xFF00) | this.regD);
                break;
            }
            case 107: {
                regIXY = ((regIXY & 0xFF00) | this.regE);
                break;
            }
            case 108: {
                work8 = regIXY;
                regIXY = ((regIXY & 0xFF00) | regIXY >>> 8);
                break;
            }
            case 109: {
                break;
            }
            case 110: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.regL = this.MemIoImpl.peek8(this.memptr);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 111: {
                regIXY = ((regIXY & 0xFF00) | this.regA);
                break;
            }
            case 112: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.MemIoImpl.poke8(this.memptr, this.regB);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 113: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.MemIoImpl.poke8(this.memptr, this.regC);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 114: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.MemIoImpl.poke8(this.memptr, this.regD);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 115: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.MemIoImpl.poke8(this.memptr, this.regE);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 116: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.MemIoImpl.poke8(this.memptr, this.regH);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 117: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.MemIoImpl.poke8(this.memptr, this.regL);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 119: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.MemIoImpl.poke8(this.memptr, this.regA);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 124: {
                this.regA = regIXY >>> 8;
                break;
            }
            case 125: {
                this.regA = (regIXY & 0xFF);
                break;
            }
            case 126: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.regA = this.MemIoImpl.peek8(this.memptr);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 132: {
                this.add(regIXY >>> 8);
                break;
            }
            case 133: {
                this.add(regIXY & 0xFF);
                break;
            }
            case 134: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.add(this.MemIoImpl.peek8(this.memptr));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 140: {
                this.adc(regIXY >>> 8);
                break;
            }
            case 141: {
                this.adc(regIXY & 0xFF);
                break;
            }
            case 142: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.adc(this.MemIoImpl.peek8(this.memptr));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 148: {
                this.sub(regIXY >>> 8);
                break;
            }
            case 149: {
                this.sub(regIXY & 0xFF);
                break;
            }
            case 150: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.sub(this.MemIoImpl.peek8(this.memptr));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 156: {
                this.sbc(regIXY >>> 8);
                break;
            }
            case 157: {
                this.sbc(regIXY & 0xFF);
                break;
            }
            case 158: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.sbc(this.MemIoImpl.peek8(this.memptr));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 164: {
                this.and(regIXY >>> 8);
                break;
            }
            case 165: {
                this.and(regIXY & 0xFF);
                break;
            }
            case 166: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.and(this.MemIoImpl.peek8(this.memptr));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 172: {
                this.xor(regIXY >>> 8);
                break;
            }
            case 173: {
                this.xor(regIXY & 0xFF);
                break;
            }
            case 174: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.xor(this.MemIoImpl.peek8(this.memptr));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 180: {
                this.or(regIXY >>> 8);
                break;
            }
            case 181: {
                this.or(regIXY & 0xFF);
                break;
            }
            case 182: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.or(this.MemIoImpl.peek8(this.memptr));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 188: {
                this.cp(regIXY >>> 8);
                break;
            }
            case 189: {
                this.cp(regIXY & 0xFF);
                break;
            }
            case 190: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.MemIoImpl.contendedStates(this.regPC, 5);
                this.cp(this.MemIoImpl.peek8(this.memptr));
                this.regPC = (this.regPC + 1 & 0xFFFF);
                break;
            }
            case 203: {
                this.memptr = (regIXY + (byte)this.MemIoImpl.peek8(this.regPC) & 0xFFFF);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                this.opCode = this.MemIoImpl.peek8(this.regPC);
                this.MemIoImpl.contendedStates(this.regPC, 2);
                this.regPC = (this.regPC + 1 & 0xFFFF);
                if (this.opCode < 128) {
                    this.decodeDDFDCBto7F(this.opCode, this.memptr);
                    break;
                }
                this.decodeDDFDCBtoFF(this.opCode, this.memptr);
                break;
            }
            case 225: {
                regIXY = this.pop();
                break;
            }
            case 227: {
                final int work9 = regIXY;
                regIXY = this.MemIoImpl.peek16(this.regSP);
                this.MemIoImpl.contendedStates(this.regSP + 1 & 0xFFFF, 1);
                this.MemIoImpl.poke8(this.regSP + 1 & 0xFFFF, work9 >>> 8 & 0xFF);
                this.MemIoImpl.poke8(this.regSP, work9 & 0xFF);
                this.MemIoImpl.contendedStates(this.regSP, 2);
                this.memptr = regIXY;
                break;
            }
            case 229: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                this.push(regIXY);
                break;
            }
            case 233: {
                this.regPC = regIXY;
                break;
            }
            case 249: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 2);
                this.regSP = regIXY;
                break;
            }
            default: {
                this.tEstados = work8;
                --this.regR;
                this.regPC = (this.regPC - 1 & 0xFFFF);
                break;
            }
        }
        return regIXY;
    }
    
    private void decodeDDFDCBto7F(final int opCode, final int address) {
        switch (opCode) {
            case 0: {
                this.regB = this.rlc(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 1: {
                this.regC = this.rlc(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 2: {
                this.regD = this.rlc(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 3: {
                this.regE = this.rlc(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 4: {
                this.regH = this.rlc(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 5: {
                this.regL = this.rlc(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 6: {
                final int work8 = this.rlc(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, work8);
                break;
            }
            case 7: {
                this.regA = this.rlc(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 8: {
                this.regB = this.rrc(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 9: {
                this.regC = this.rrc(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 10: {
                this.regD = this.rrc(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 11: {
                this.regE = this.rrc(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 12: {
                this.regH = this.rrc(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 13: {
                this.regL = this.rrc(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 14: {
                final int work8 = this.rrc(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, work8);
                break;
            }
            case 15: {
                this.regA = this.rrc(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 16: {
                this.regB = this.rl(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 17: {
                this.regC = this.rl(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 18: {
                this.regD = this.rl(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 19: {
                this.regE = this.rl(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 20: {
                this.regH = this.rl(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 21: {
                this.regL = this.rl(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 22: {
                final int work8 = this.rl(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, work8);
                break;
            }
            case 23: {
                this.regA = this.rl(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 24: {
                this.regB = this.rr(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 25: {
                this.regC = this.rr(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 26: {
                this.regD = this.rr(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 27: {
                this.regE = this.rr(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 28: {
                this.regH = this.rr(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 29: {
                this.regL = this.rr(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 30: {
                final int work8 = this.rr(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, work8);
                break;
            }
            case 31: {
                this.regA = this.rr(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 32: {
                this.regB = this.sla(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 33: {
                this.regC = this.sla(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 34: {
                this.regD = this.sla(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 35: {
                this.regE = this.sla(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 36: {
                this.regH = this.sla(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 37: {
                this.regL = this.sla(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 38: {
                final int work8 = this.sla(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, work8);
                break;
            }
            case 39: {
                this.regA = this.sla(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 40: {
                this.regB = this.sra(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 41: {
                this.regC = this.sra(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 42: {
                this.regD = this.sra(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 43: {
                this.regE = this.sra(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 44: {
                this.regH = this.sra(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 45: {
                this.regL = this.sra(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 46: {
                final int work8 = this.sra(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, work8);
                break;
            }
            case 47: {
                this.regA = this.sra(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 48: {
                this.regB = this.sll(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 49: {
                this.regC = this.sll(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 50: {
                this.regD = this.sll(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 51: {
                this.regE = this.sll(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 52: {
                this.regH = this.sll(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 53: {
                this.regL = this.sll(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 54: {
                final int work8 = this.sll(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, work8);
                break;
            }
            case 55: {
                this.regA = this.sll(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 56: {
                this.regB = this.srl(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 57: {
                this.regC = this.srl(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 58: {
                this.regD = this.srl(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 59: {
                this.regE = this.srl(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 60: {
                this.regH = this.srl(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 61: {
                this.regL = this.srl(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 62: {
                final int work8 = this.srl(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, work8);
                break;
            }
            case 63: {
                this.regA = this.srl(this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71: {
                this.bit(1, this.MemIoImpl.peek8(address));
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD4) | (address >>> 8 & 0x28));
                this.MemIoImpl.contendedStates(address, 1);
                break;
            }
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79: {
                this.bit(2, this.MemIoImpl.peek8(address));
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD4) | (address >>> 8 & 0x28));
                this.MemIoImpl.contendedStates(address, 1);
                break;
            }
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87: {
                this.bit(4, this.MemIoImpl.peek8(address));
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD4) | (address >>> 8 & 0x28));
                this.MemIoImpl.contendedStates(address, 1);
                break;
            }
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95: {
                this.bit(8, this.MemIoImpl.peek8(address));
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD4) | (address >>> 8 & 0x28));
                this.MemIoImpl.contendedStates(address, 1);
                break;
            }
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103: {
                this.bit(16, this.MemIoImpl.peek8(address));
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD4) | (address >>> 8 & 0x28));
                this.MemIoImpl.contendedStates(address, 1);
                break;
            }
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111: {
                this.bit(32, this.MemIoImpl.peek8(address));
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD4) | (address >>> 8 & 0x28));
                this.MemIoImpl.contendedStates(address, 1);
                break;
            }
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119: {
                this.bit(64, this.MemIoImpl.peek8(address));
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD4) | (address >>> 8 & 0x28));
                this.MemIoImpl.contendedStates(address, 1);
                break;
            }
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127: {
                this.bit(128, this.MemIoImpl.peek8(address));
                this.sz5h3pnFlags = ((this.sz5h3pnFlags & 0xD4) | (address >>> 8 & 0x28));
                this.MemIoImpl.contendedStates(address, 1);
                break;
            }
        }
    }
    
    private void decodeDDFDCBtoFF(final int opCode, final int address) {
        switch (opCode) {
            case 128: {
                this.regB = this.res(1, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 129: {
                this.regC = this.res(1, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 130: {
                this.regD = this.res(1, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 131: {
                this.regE = this.res(1, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 132: {
                this.regH = this.res(1, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 133: {
                this.regL = this.res(1, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 134: {
                final int work8 = this.MemIoImpl.peek8(address);
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.res(1, work8));
                break;
            }
            case 135: {
                this.regA = this.res(1, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 136: {
                this.regB = this.res(2, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 137: {
                this.regC = this.res(2, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 138: {
                this.regD = this.res(2, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 139: {
                this.regE = this.res(2, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 140: {
                this.regH = this.res(2, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 141: {
                this.regL = this.res(2, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 142: {
                final int work8 = this.MemIoImpl.peek8(address);
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.res(2, work8));
                break;
            }
            case 143: {
                this.regA = this.res(2, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 144: {
                this.regB = this.res(4, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 145: {
                this.regC = this.res(4, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 146: {
                this.regD = this.res(4, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 147: {
                this.regE = this.res(4, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 148: {
                this.regH = this.res(4, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 149: {
                this.regL = this.res(4, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 150: {
                final int work8 = this.MemIoImpl.peek8(address);
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.res(4, work8));
                break;
            }
            case 151: {
                this.regA = this.res(4, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 152: {
                this.regB = this.res(8, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 153: {
                this.regC = this.res(8, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 154: {
                this.regD = this.res(8, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 155: {
                this.regE = this.res(8, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 156: {
                this.regH = this.res(8, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 157: {
                this.regL = this.res(8, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 158: {
                final int work8 = this.MemIoImpl.peek8(address);
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.res(8, work8));
                break;
            }
            case 159: {
                this.regA = this.res(8, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 160: {
                this.regB = this.res(16, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 161: {
                this.regC = this.res(16, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 162: {
                this.regD = this.res(16, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 163: {
                this.regE = this.res(16, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 164: {
                this.regH = this.res(16, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 165: {
                this.regL = this.res(16, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 166: {
                final int work8 = this.MemIoImpl.peek8(address);
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.res(16, work8));
                break;
            }
            case 167: {
                this.regA = this.res(16, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 168: {
                this.regB = this.res(32, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 169: {
                this.regC = this.res(32, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 170: {
                this.regD = this.res(32, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 171: {
                this.regE = this.res(32, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 172: {
                this.regH = this.res(32, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 173: {
                this.regL = this.res(32, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 174: {
                final int work8 = this.MemIoImpl.peek8(address);
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.res(32, work8));
                break;
            }
            case 175: {
                this.regA = this.res(32, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 176: {
                this.regB = this.res(64, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 177: {
                this.regC = this.res(64, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 178: {
                this.regD = this.res(64, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 179: {
                this.regE = this.res(64, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 180: {
                this.regH = this.res(64, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 181: {
                this.regL = this.res(64, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 182: {
                final int work8 = this.MemIoImpl.peek8(address);
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.res(64, work8));
                break;
            }
            case 183: {
                this.regA = this.res(64, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 184: {
                this.regB = this.res(128, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 185: {
                this.regC = this.res(128, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 186: {
                this.regD = this.res(128, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 187: {
                this.regE = this.res(128, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 188: {
                this.regH = this.res(128, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 189: {
                this.regL = this.res(128, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 190: {
                final int work8 = this.MemIoImpl.peek8(address);
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.res(128, work8));
                break;
            }
            case 191: {
                this.regA = this.res(128, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 192: {
                this.regB = this.set(1, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 193: {
                this.regC = this.set(1, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 194: {
                this.regD = this.set(1, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 195: {
                this.regE = this.set(1, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 196: {
                this.regH = this.set(1, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 197: {
                this.regL = this.set(1, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 198: {
                final int work8 = this.MemIoImpl.peek8(address);
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.set(1, work8));
                break;
            }
            case 199: {
                this.regA = this.set(1, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 200: {
                this.regB = this.set(2, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 201: {
                this.regC = this.set(2, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 202: {
                this.regD = this.set(2, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 203: {
                this.regE = this.set(2, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 204: {
                this.regH = this.set(2, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 205: {
                this.regL = this.set(2, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 206: {
                final int work8 = this.MemIoImpl.peek8(address);
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.set(2, work8));
                break;
            }
            case 207: {
                this.regA = this.set(2, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 208: {
                this.regB = this.set(4, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 209: {
                this.regC = this.set(4, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 210: {
                this.regD = this.set(4, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 211: {
                this.regE = this.set(4, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 212: {
                this.regH = this.set(4, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 213: {
                this.regL = this.set(4, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 214: {
                final int work8 = this.MemIoImpl.peek8(address);
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.set(4, work8));
                break;
            }
            case 215: {
                this.regA = this.set(4, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 216: {
                this.regB = this.set(8, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 217: {
                this.regC = this.set(8, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 218: {
                this.regD = this.set(8, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 219: {
                this.regE = this.set(8, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 220: {
                this.regH = this.set(8, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 221: {
                this.regL = this.set(8, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 222: {
                final int work8 = this.MemIoImpl.peek8(address);
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.set(8, work8));
                break;
            }
            case 223: {
                this.regA = this.set(8, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 224: {
                this.regB = this.set(16, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 225: {
                this.regC = this.set(16, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 226: {
                this.regD = this.set(16, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 227: {
                this.regE = this.set(16, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 228: {
                this.regH = this.set(16, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 229: {
                this.regL = this.set(16, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 230: {
                final int work8 = this.MemIoImpl.peek8(address);
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.set(16, work8));
                break;
            }
            case 231: {
                this.regA = this.set(16, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 232: {
                this.regB = this.set(32, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 233: {
                this.regC = this.set(32, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 234: {
                this.regD = this.set(32, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 235: {
                this.regE = this.set(32, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 236: {
                this.regH = this.set(32, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 237: {
                this.regL = this.set(32, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 238: {
                final int work8 = this.MemIoImpl.peek8(address);
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.set(32, work8));
                break;
            }
            case 239: {
                this.regA = this.set(32, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 240: {
                this.regB = this.set(64, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 241: {
                this.regC = this.set(64, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 242: {
                this.regD = this.set(64, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 243: {
                this.regE = this.set(64, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 244: {
                this.regH = this.set(64, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 245: {
                this.regL = this.set(64, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 246: {
                final int work8 = this.MemIoImpl.peek8(address);
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.set(64, work8));
                break;
            }
            case 247: {
                this.regA = this.set(64, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
            case 248: {
                this.regB = this.set(128, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regB);
                break;
            }
            case 249: {
                this.regC = this.set(128, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regC);
                break;
            }
            case 250: {
                this.regD = this.set(128, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regD);
                break;
            }
            case 251: {
                this.regE = this.set(128, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regE);
                break;
            }
            case 252: {
                this.regH = this.set(128, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regH);
                break;
            }
            case 253: {
                this.regL = this.set(128, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regL);
                break;
            }
            case 254: {
                final int work8 = this.MemIoImpl.peek8(address);
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.set(128, work8));
                break;
            }
            case 255: {
                this.regA = this.set(128, this.MemIoImpl.peek8(address));
                this.MemIoImpl.contendedStates(address, 1);
                this.MemIoImpl.poke8(address, this.regA);
                break;
            }
        }
    }
    
    private void decodeED() {
        ++this.regR;
        this.opCode = this.MemIoImpl.fetchOpcode(this.regPC);
        this.regPC = (this.regPC + 1 & 0xFFFF);
        switch (this.opCode) {
            case 64: {
                this.regB = this.MemIoImpl.inPort(this.getRegBC());
                this.sz5h3pnFlags = Z80.sz53pn_addTable[this.regB];
                break;
            }
            case 65: {
                this.MemIoImpl.outPort(this.getRegBC(), this.regB);
                break;
            }
            case 66: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 7);
                this.sbc16(this.getRegBC());
                break;
            }
            case 67: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                this.MemIoImpl.poke16(this.memptr, this.getRegBC());
                this.memptr = (this.memptr + 1 & 0xFFFF);
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 68:
            case 76:
            case 84:
            case 92:
            case 100:
            case 108:
            case 116:
            case 124: {
                final int aux = this.regA;
                this.regA = 0;
                this.sub(aux);
                break;
            }
            case 69:
            case 85:
            case 93:
            case 101:
            case 109:
            case 117:
            case 125: {
                this.ffIFF1 = this.ffIFF2;
                final int pop = this.pop();
                this.memptr = pop;
                this.regPC = pop;
                break;
            }
            case 70:
            case 78:
            case 102:
            case 110: {
                this.setIM(0);
                break;
            }
            case 71: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                this.regI = this.regA;
                break;
            }
            case 72: {
                this.regC = this.MemIoImpl.inPort(this.getRegBC());
                this.sz5h3pnFlags = Z80.sz53pn_addTable[this.regC];
                break;
            }
            case 73: {
                this.MemIoImpl.outPort(this.getRegBC(), this.regC);
                break;
            }
            case 74: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 7);
                this.adc16(this.getRegBC());
                break;
            }
            case 75: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                this.setRegBC(this.MemIoImpl.peek16(this.memptr));
                this.memptr = (this.memptr + 1 & 0xFFFF);
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 77: {
                final int pop2 = this.pop();
                this.memptr = pop2;
                this.regPC = pop2;
                break;
            }
            case 79: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                this.setRegR(this.regA);
                break;
            }
            case 80: {
                this.regD = this.MemIoImpl.inPort(this.getRegBC());
                this.sz5h3pnFlags = Z80.sz53pn_addTable[this.regD];
                break;
            }
            case 81: {
                this.MemIoImpl.outPort(this.getRegBC(), this.regD);
                break;
            }
            case 82: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 7);
                this.sbc16(this.getRegDE());
                break;
            }
            case 83: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                this.MemIoImpl.poke16(this.memptr, this.getRegDE());
                this.memptr = (this.memptr + 1 & 0xFFFF);
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 86:
            case 118: {
                this.setIM(1);
                break;
            }
            case 87: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                this.regA = this.regI;
                this.sz5h3pnFlags = Z80.sz53n_addTable[this.regA];
                if (this.ffIFF2) {
                    this.sz5h3pnFlags |= 0x4;
                    break;
                }
                break;
            }
            case 88: {
                this.regE = this.MemIoImpl.inPort(this.getRegBC());
                this.sz5h3pnFlags = Z80.sz53pn_addTable[this.regE];
                break;
            }
            case 89: {
                this.MemIoImpl.outPort(this.getRegBC(), this.regE);
                break;
            }
            case 90: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 7);
                this.adc16(this.getRegDE());
                break;
            }
            case 91: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                this.setRegDE(this.MemIoImpl.peek16(this.memptr));
                this.memptr = (this.memptr + 1 & 0xFFFF);
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 94:
            case 126: {
                this.setIM(2);
                break;
            }
            case 95: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 1);
                this.regA = this.getRegR();
                this.sz5h3pnFlags = Z80.sz53n_addTable[this.regA];
                if (this.ffIFF2) {
                    this.sz5h3pnFlags |= 0x4;
                    break;
                }
                break;
            }
            case 96: {
                this.regH = this.MemIoImpl.inPort(this.getRegBC());
                this.sz5h3pnFlags = Z80.sz53pn_addTable[this.regH];
                break;
            }
            case 97: {
                this.MemIoImpl.outPort(this.getRegBC(), this.regH);
                break;
            }
            case 98: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 7);
                this.sbc16(this.getRegHL());
                break;
            }
            case 99: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                this.MemIoImpl.poke16(this.memptr, this.getRegHL());
                this.memptr = (this.memptr + 1 & 0xFFFF);
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 103: {
                this.rrd();
                break;
            }
            case 104: {
                this.regL = this.MemIoImpl.inPort(this.getRegBC());
                this.sz5h3pnFlags = Z80.sz53pn_addTable[this.regL];
                break;
            }
            case 105: {
                this.MemIoImpl.outPort(this.getRegBC(), this.regL);
                break;
            }
            case 106: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 7);
                this.adc16(this.getRegHL());
                break;
            }
            case 107: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                this.setRegHL(this.MemIoImpl.peek16(this.memptr));
                this.memptr = (this.memptr + 1 & 0xFFFF);
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 111: {
                this.rld();
                break;
            }
            case 112: {
                final int inPort = this.MemIoImpl.inPort(this.getRegBC());
                this.sz5h3pnFlags = Z80.sz53pn_addTable[inPort];
                break;
            }
            case 113: {
                this.MemIoImpl.outPort(this.getRegBC(), 0);
                break;
            }
            case 114: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 7);
                this.sbc16(this.regSP);
                break;
            }
            case 115: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                this.MemIoImpl.poke16(this.memptr, this.regSP);
                this.memptr = (this.memptr + 1 & 0xFFFF);
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 120: {
                this.regA = this.MemIoImpl.inPort(this.getRegBC());
                this.sz5h3pnFlags = Z80.sz53pn_addTable[this.regA];
                this.memptr = (this.getRegBC() + 1 & 0xFFFF);
                break;
            }
            case 121: {
                this.memptr = this.getRegBC();
                this.MemIoImpl.outPort(this.memptr, this.regA);
                this.memptr = (this.memptr + 1 & 0xFFFF);
                break;
            }
            case 122: {
                this.MemIoImpl.contendedStates(this.getPairIR(), 7);
                this.adc16(this.regSP);
                break;
            }
            case 123: {
                this.memptr = this.MemIoImpl.peek16(this.regPC);
                this.regSP = this.MemIoImpl.peek16(this.memptr);
                this.memptr = (this.memptr + 1 & 0xFFFF);
                this.regPC = (this.regPC + 2 & 0xFFFF);
                break;
            }
            case 160: {
                this.ldi();
                break;
            }
            case 161: {
                this.cpi();
                break;
            }
            case 162: {
                this.ini();
                break;
            }
            case 163: {
                this.outi();
                break;
            }
            case 168: {
                this.ldd();
                break;
            }
            case 169: {
                this.cpd();
                break;
            }
            case 170: {
                this.ind();
                break;
            }
            case 171: {
                this.outd();
                break;
            }
            case 176: {
                this.ldi();
                if ((this.sz5h3pnFlags & 0x4) == 0x4) {
                    this.regPC = (this.regPC - 2 & 0xFFFF);
                    this.memptr = (this.regPC + 1 & 0xFFFF);
                    this.MemIoImpl.contendedStates(this.getRegDE() - 1 & 0xFFFF, 5);
                    break;
                }
                break;
            }
            case 177: {
                this.cpi();
                if ((this.sz5h3pnFlags & 0x4) == 0x4 && (this.sz5h3pnFlags & 0x40) == 0x0) {
                    this.regPC = (this.regPC - 2 & 0xFFFF);
                    this.memptr = (this.regPC + 1 & 0xFFFF);
                    this.MemIoImpl.contendedStates(this.getRegHL() - 1 & 0xFFFF, 5);
                    break;
                }
                break;
            }
            case 178: {
                this.ini();
                if (this.regB != 0) {
                    this.regPC = (this.regPC - 2 & 0xFFFF);
                    this.MemIoImpl.contendedStates(this.getRegHL() - 1 & 0xFFFF, 5);
                    break;
                }
                break;
            }
            case 179: {
                this.outi();
                if (this.regB != 0) {
                    this.regPC = (this.regPC - 2 & 0xFFFF);
                    this.MemIoImpl.contendedStates(this.getRegBC(), 5);
                    break;
                }
                break;
            }
            case 184: {
                this.ldd();
                if ((this.sz5h3pnFlags & 0x4) == 0x4) {
                    this.regPC = (this.regPC - 2 & 0xFFFF);
                    this.memptr = (this.regPC + 1 & 0xFFFF);
                    this.MemIoImpl.contendedStates(this.getRegDE() + 1 & 0xFFFF, 5);
                    break;
                }
                break;
            }
            case 185: {
                this.cpd();
                if ((this.sz5h3pnFlags & 0x4) == 0x4 && (this.sz5h3pnFlags & 0x40) == 0x0) {
                    this.regPC = (this.regPC - 2 & 0xFFFF);
                    this.memptr = (this.regPC + 1 & 0xFFFF);
                    this.MemIoImpl.contendedStates(this.getRegHL() + 1 & 0xFFFF, 5);
                    break;
                }
                break;
            }
            case 186: {
                this.ind();
                if (this.regB != 0) {
                    this.regPC = (this.regPC - 2 & 0xFFFF);
                    this.MemIoImpl.contendedStates(this.getRegHL() + 1 & 0xFFFF, 5);
                    break;
                }
                break;
            }
            case 187: {
                this.outd();
                if (this.regB != 0) {
                    this.regPC = (this.regPC - 2 & 0xFFFF);
                    this.MemIoImpl.contendedStates(this.getRegBC(), 5);
                    break;
                }
                break;
            }
        }
    }
    
    static {
        sz53n_addTable = new int[256];
        sz53pn_addTable = new int[256];
        sz53n_subTable = new int[256];
        sz53pn_subTable = new int[256];
        Arrays.fill(Z80.sz53n_addTable, 0);
        Arrays.fill(Z80.sz53n_subTable, 0);
        Arrays.fill(Z80.sz53pn_addTable, 0);
        Arrays.fill(Z80.sz53pn_subTable, 0);
        for (int idx = 0; idx < 256; ++idx) {
            if (idx > 127) {
                final int[] sz53n_addTable2 = Z80.sz53n_addTable;
                final int n = idx;
                sz53n_addTable2[n] |= 0x80;
            }
            boolean evenBits = true;
            for (int mask = 1; mask < 256; mask <<= 1) {
                if ((idx & mask) != 0x0) {
                    evenBits = !evenBits;
                }
            }
            final int[] sz53n_addTable3 = Z80.sz53n_addTable;
            final int n2 = idx;
            sz53n_addTable3[n2] |= (idx & 0x28);
            Z80.sz53n_subTable[idx] = (Z80.sz53n_addTable[idx] | 0x2);
            if (evenBits) {
                Z80.sz53pn_addTable[idx] = (Z80.sz53n_addTable[idx] | 0x4);
                Z80.sz53pn_subTable[idx] = (Z80.sz53n_subTable[idx] | 0x4);
            }
            else {
                Z80.sz53pn_addTable[idx] = Z80.sz53n_addTable[idx];
                Z80.sz53pn_subTable[idx] = Z80.sz53n_subTable[idx];
            }
        }
        final int[] sz53n_addTable4 = Z80.sz53n_addTable;
        final int n3 = 0;
        sz53n_addTable4[n3] |= 0x40;
        final int[] sz53pn_addTable2 = Z80.sz53pn_addTable;
        final int n4 = 0;
        sz53pn_addTable2[n4] |= 0x40;
        final int[] sz53n_subTable2 = Z80.sz53n_subTable;
        final int n5 = 0;
        sz53n_subTable2[n5] |= 0x40;
        final int[] sz53pn_subTable2 = Z80.sz53pn_subTable;
        final int n6 = 0;
        sz53pn_subTable2[n6] |= 0x40;
    }
}
