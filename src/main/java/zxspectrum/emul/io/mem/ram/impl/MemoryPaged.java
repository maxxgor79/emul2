package zxspectrum.emul.io.mem.ram.impl;

import java.util.Arrays;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import zxspectrum.emul.io.mem.MemoryControl;
import zxspectrum.emul.cpu.reg.Reg16;
import zxspectrum.emul.cpu.reg.Reg8;
import zxspectrum.emul.cpu.reg.RegSP;

/**
 * MemoryPaged.
 *
 * @author Maxim Gorin
 */
@Slf4j
abstract class MemoryPaged extends RomMemory implements MemoryControl {
    protected byte[][] buf;

    protected int lastAddress;

    protected int romSize = ROM_SIZE;

    protected RegSP sp;

    protected int lastPageIndex;

    protected int pagedAddressShift;

    protected int lastPageAddress;

    @Override
    public void pop(@NonNull Reg16 r) {
        int address = sp.getValue();
        int pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        int lo = buf[pageIndex][address];
        sp.inc();
        address = sp.getValue();
        pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        int hi = buf[pageIndex][address];
        sp.inc();
        r.setValue(lo, hi);
    }

    @Override
    public void push(@NonNull Reg16 r) {
        sp.dec();
        int address = sp.getValue();
        int pageIndex;
        if (address >= romSize) {
            pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
            address &= lastPageAddress;
            buf[pageIndex][address] = (byte) r.getHiValue();
        }

        sp.dec();
        address = sp.getValue();
        if (address >= romSize) {
            pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
            address &= lastPageAddress;
            buf[pageIndex][address] = (byte) r.getLoValue();
        }
    }

    @Override
    public void peek(@NonNull Reg16 addr, @NonNull Reg8 r) {
        int address = addr.getValue();
        int pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        r.setValue(buf[pageIndex][address]);
    }

    @Override
    public void peek(@NonNull Reg16 addr, @NonNull Reg16 r) {
        int address = addr.getValue();
        int pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        int lo = buf[pageIndex][address];

        address++;
        address &= lastAddress;
        pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        int hi = buf[pageIndex][address];
        r.setValue(lo, hi);
    }

    @Override
    public void peek(int address, @NonNull Reg8 r) {
        address &= lastAddress;
        int pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        r.setValue(buf[pageIndex][address]);
    }

    @Override
    public void peek(int address, @NonNull Reg16 r) {
        address &= lastAddress;
        int pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        int lo = buf[pageIndex][address];

        address++;
        address &= lastAddress;
        pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        int hi = buf[pageIndex][address];
        r.setValue(lo, hi);
    }

    @Override
    public int peek8(int address) {
        address &= lastAddress;
        int pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        return buf[pageIndex][address] & 0xFF;
    }

    @Override
    public int peek8(@NonNull Reg16 addr) {
        int address = addr.getValue();
        int pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        return buf[pageIndex][address] & 0xFF;
    }

    @Override
    public int peek16(int address) {
        address &= lastAddress;
        int pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        int lo = buf[pageIndex][address] & 0xFF;

        address++;
        address &= lastAddress;
        pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        int hi = buf[pageIndex][address] & 0xFF;
        return (hi << 8) | lo;
    }

    @Override
    public int peek16(@NonNull Reg16 addr) {
        int address = addr.getValue();
        int pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        int lo = buf[pageIndex][address] & 0xFF;

        address++;
        address &= lastAddress;
        pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        int hi = buf[pageIndex][address] & 0xFF;
        return (hi << 8) | lo;
    }

    @Override
    public void poke8(@NonNull Reg16 addr, int val) {
        int address = addr.getValue();
        if (address < romSize) {
            return;
        }
        int pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        buf[pageIndex][address] = (byte) val;
    }

    @Override
    public void poke(@NonNull Reg16 addr, @NonNull Reg8 r) {
        int address = addr.getValue();
        if (address < romSize) {
            return;
        }
        int pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        buf[pageIndex][address] = (byte) r.getValue();
    }

    @Override
    public void poke16(@NonNull Reg16 addr, int val) {
        int address = addr.getValue();
        if (address < romSize) {
            return;
        }
        int pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        buf[pageIndex][address] = (byte) val;

        address++;
        address &= lastAddress;
        pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        buf[pageIndex][address] = (byte) (val >>> 8);
    }

    @Override
    public void poke(@NonNull Reg16 addr, @NonNull Reg16 r) {
        int address = addr.getValue();
        if (address < romSize) {
            return;
        }
        int val = r.getValue();
        int pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        buf[pageIndex][address] = (byte) val;

        address++;
        address &= lastAddress;
        pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        buf[pageIndex][address] = (byte) (val >>> 8);
    }

    @Override
    public void poke(int address, @NonNull Reg8 r) {
        address &= lastAddress;
        if (address < romSize) {
            return;
        }
        int pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        buf[pageIndex][address] = (byte) r.getValue();
    }

    @Override
    public void poke(int address, @NonNull Reg16 r) {
        address &= lastAddress;
        if (address < romSize) {
            return;
        }
        int pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        buf[pageIndex][address] = (byte) r.getLoValue();

        address++;
        address &= lastAddress;
        pageIndex = (address >>> pagedAddressShift) & lastPageIndex;
        address &= lastPageAddress;
        buf[pageIndex][address] = (byte) r.getHiValue();
    }

    @Override
    public Buffer getVideoBuffer() {
        final Buffer buffer = new Buffer();
        buffer.buf = buf[1];
        buffer.offset = 0;
        buffer.length = VIDEO_BUFFER_SIZE;
        return buffer;
    }

    @Override
    public void setSP(@NonNull RegSP sp) {
        this.sp = sp;
    }

    @Override
    public void reset() {
        for (int i = 1; i < buf.length; i++) {
            Arrays.fill(buf[i], (byte) -1);
        }
    }
}
