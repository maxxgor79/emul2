package zxspectrum.emul.io.mem.impl;

import java.util.Arrays;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.cpu.reg.RegSP;

/**
 * MemoryNotPaged.
 *
 * @author Maxim Gorin
 */
@Slf4j
abstract class MemoryNotPaged implements MemoryControl {
    protected byte[] buf;

    protected RegSP sp;

    protected int lastAddress;

    protected int romSize = 0x4000;

    @Override
    public void pop(@NonNull Reg16 r) {
        int lo = buf[sp.getValue()];
        sp.inc();
        int hi = buf[sp.getValue()];
        sp.inc();
        r.setValue(lo, hi);
    }

    @Override
    public void push(@NonNull Reg16 r) {
        sp.dec();
        int address = sp.getValue();
        if (address >= romSize) {
            buf[address] = (byte) r.getHiValue();
        }
        sp.dec();
        address = sp.getValue();
        if (address >= romSize) {
            buf[address] = (byte) r.getLoValue();
        }
    }

    @Override
    public void peek(@NonNull Reg16 addr, @NonNull Reg8 r) {
        r.setValue(buf[addr.getValue()]);
    }

    @Override
    public void peek(@NonNull Reg16 addr, @NonNull Reg16 r) {
        int address = addr.getValue();
        int lo = buf[address];
        address++;
        address &= lastAddress;
        int hi = buf[address];
        r.setValue(lo, hi);
    }

    @Override
    public void peek(int address, @NonNull Reg8 r) {
        address &= lastAddress;
        r.setValue(buf[address]);
    }

    @Override
    public void peek(int address, @NonNull Reg16 r) {
        address &= lastAddress;
        int lo = buf[address];
        address++;
        address &= lastAddress;
        int hi = buf[address];
        r.setValue(lo, hi);
    }

    @Override
    public int peek8(int address) {
        return buf[address & lastAddress] & 0xFF;
    }

    @Override
    public int peek8(@NonNull Reg16 addr) {
        return buf[addr.getValue()] & 0xFF;
    }

    @Override
    public int peek16(int address) {
        address &= lastAddress;
        int lo = buf[address] & 0xFF;
        address++;
        address &= lastAddress;
        int hi = buf[address] & 0xFF;
        return hi << 8 | lo;
    }

    @Override
    public int peek16(@NonNull Reg16 addr) {
        int address = addr.getValue();
        int lo = buf[address] & 0xFF;
        address++;
        address &= lastAddress;
        int hi = buf[address] & 0xFF;
        return (hi << 8) | lo;
    }

    @Override
    public void poke8(@NonNull Reg16 addr, int val) {
        int address = addr.getValue();
        if (address < romSize) {
            return;
        }
        buf[address] = (byte) val;
    }

    @Override
    public void poke(@NonNull Reg16 addr, @NonNull Reg8 r) {
        int address = addr.getValue();
        if (address < romSize) {
            return;
        }
        buf[address] = (byte) r.getValue();
    }

    @Override
    public void poke16(@NonNull Reg16 addr, int val) {
        int address = addr.getValue();
        if (address < romSize) {
            return;
        }
        buf[address] = (byte) val;
        address++;
        address &= lastAddress;
        buf[address] = (byte) (val >>> 8);
    }

    @Override
    public void poke(@NonNull Reg16 addr, @NonNull Reg16 r) {
        int address = addr.getValue();
        if (address < romSize) {
            return;
        }
        int val = r.getValue();
        buf[address] = (byte) val;
        address++;
        address &= lastAddress;
        if (address < romSize) {
            return;
        }
        buf[address] = (byte) (val >>> 8);
    }

    @Override
    public void poke(int address, @NonNull Reg8 r) {
        address &= lastAddress;
        if (address < romSize) {
            return;
        }
        buf[address] = (byte) r.getValue();
    }

    @Override
    public void poke(int address, @NonNull Reg16 r) {
        address &= lastAddress;
        if (address < romSize) {
            return;
        }
        buf[address] = (byte) r.getLoValue();
        address++;
        address &= lastAddress;
        buf[address] = (byte) r.getHiValue();
    }

    @Override
    public Buffer getVideoBuffer() {
        final Buffer buffer = new Buffer();
        buffer.buf = buf;
        buffer.offset = 0x4000;
        buffer.length = 0x1B00;
        return buffer;
    }

    @Override
    public void setPageMapping(int pageMapping) {
    }

    @Override
    public void setSP(@NonNull RegSP sp) {
        this.sp = sp;
    }

    @Override
    public void reset() {
        Arrays.fill(buf, 0x4000, lastAddress, (byte) -1);
    }
}
