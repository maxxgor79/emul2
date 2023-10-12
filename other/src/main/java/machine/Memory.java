// 
// Decompiled by Procyon v0.5.36
// 

package machine;

import java.io.FileNotFoundException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ResourceBundle;
import configuration.MemoryType;
import java.io.File;
import java.util.Arrays;
import java.util.Random;
import configuration.JSpeccySettingsType;

public final class Memory
{
    private final int PAGE_SIZE = 8192;
    private byte[][] Rom48k;
    private byte[][] IF2Rom;
    private byte[][] Rom128k;
    private byte[][] RomPlus2;
    private byte[][] RomPlus3;
    private byte[][] Ram;
    private byte[] fakeROM;
    private byte[][] readPages;
    private byte[][] writePages;
    private byte[][] mfROM;
    private byte[] mfRAM;
    private int screenPage;
    private int highPage;
    private int bankM;
    private int bankP;
    private boolean IF2RomEnabled;
    private boolean model128k;
    private boolean pagingLocked;
    private boolean plus3RamMode;
    private boolean mfPagedIn;
    private boolean mfLocked;
    private MachineTypes spectrumModel;
    private JSpeccySettingsType settings;
    private Random random;
    
    public Memory(final JSpeccySettingsType memSettings) {
        this.Rom48k = new byte[2][8192];
        this.IF2Rom = new byte[2][8192];
        this.Rom128k = new byte[4][8192];
        this.RomPlus2 = new byte[4][8192];
        this.RomPlus3 = new byte[8][8192];
        this.Ram = new byte[16][8192];
        this.fakeROM = new byte[8192];
        this.readPages = new byte[8][];
        this.writePages = new byte[8][];
        this.mfROM = new byte[3][8192];
        this.mfRAM = new byte[8192];
        this.spectrumModel = MachineTypes.SPECTRUM48K;
        this.settings = memSettings;
        this.random = new Random();
        this.hardReset();
        this.loadRoms();
        this.IF2RomEnabled = false;
    }
    
    public void setSpectrumModel(final MachineTypes model) {
        this.spectrumModel = model;
        this.reset();
    }
    
    public byte readScreenByte(final int address) {
        return this.Ram[this.screenPage][address & 0x1FFF];
    }
    
    public byte readByte(final int address) {
        return this.readPages[address >>> 13][address & 0x1FFF];
    }
    
    public void writeByte(final int address, final byte value) {
        this.writePages[address >>> 13][address & 0x1FFF] = value;
    }
    
    public byte readByte(int page, final int address) {
        page <<= 1;
        if (address < 8192) {
            return this.Ram[page][address];
        }
        return this.Ram[page + 1][address & 0x1FFF];
    }
    
    public void writeByte(int page, final int address, final byte value) {
        page <<= 1;
        if (address < 8192) {
            this.Ram[page][address] = value;
        }
        else {
            this.Ram[page + 1][address & 0x1FFF] = value;
        }
    }
    
    public void loadPage(int page, final byte[] buffer) {
        page <<= 1;
        byte[] target = this.Ram[page];
        System.arraycopy(buffer, 0, target, 0, 8192);
        target = this.Ram[page + 1];
        System.arraycopy(buffer, 8192, target, 0, 8192);
    }
    
    public void savePage(int page, final byte[] buffer) {
        page <<= 1;
        byte[] source = this.Ram[page];
        System.arraycopy(source, 0, buffer, 0, 8192);
        source = this.Ram[page + 1];
        System.arraycopy(source, 0, buffer, 8192, 8192);
    }
    
    public void loadMFRam(final byte[] buffer) {
        System.arraycopy(buffer, 0, this.mfRAM, 0, this.mfRAM.length);
    }
    
    public void saveMFRam(final byte[] buffer) {
        System.arraycopy(this.mfRAM, 0, buffer, 0, buffer.length);
    }
    
    public void saveIF2Rom(final byte[] buffer) {
        System.arraycopy(this.IF2Rom[0], 0, buffer, 0, this.IF2Rom[0].length);
        System.arraycopy(this.IF2Rom[1], 0, buffer, 8192, this.IF2Rom[1].length);
    }
    
    private void setMemoryMap16k() {
        if (this.IF2RomEnabled) {
            this.readPages[0] = this.IF2Rom[0];
            this.readPages[1] = this.IF2Rom[1];
        }
        else {
            this.readPages[0] = this.Rom48k[0];
            this.readPages[1] = this.Rom48k[1];
        }
        this.writePages[0] = (this.writePages[1] = this.fakeROM);
        this.readPages[2] = (this.writePages[2] = this.Ram[10]);
        this.readPages[3] = (this.writePages[3] = this.Ram[11]);
        this.readPages[4] = (this.readPages[5] = this.Ram[4]);
        this.readPages[6] = (this.readPages[7] = this.Ram[4]);
        this.writePages[4] = (this.writePages[5] = this.fakeROM);
        this.writePages[6] = (this.writePages[7] = this.fakeROM);
        Arrays.fill(this.Ram[4], (byte)(-1));
        this.screenPage = 10;
        this.model128k = false;
        this.mfLocked = true;
    }
    
    private void setMemoryMap48k() {
        if (this.IF2RomEnabled) {
            this.readPages[0] = this.IF2Rom[0];
            this.readPages[1] = this.IF2Rom[1];
        }
        else {
            this.readPages[0] = this.Rom48k[0];
            this.readPages[1] = this.Rom48k[1];
        }
        this.writePages[0] = (this.writePages[1] = this.fakeROM);
        this.readPages[2] = (this.writePages[2] = this.Ram[10]);
        this.readPages[3] = (this.writePages[3] = this.Ram[11]);
        this.readPages[4] = (this.writePages[4] = this.Ram[4]);
        this.readPages[5] = (this.writePages[5] = this.Ram[5]);
        this.readPages[6] = (this.writePages[6] = this.Ram[0]);
        this.readPages[7] = (this.writePages[7] = this.Ram[1]);
        this.screenPage = 10;
        this.model128k = false;
        this.mfLocked = true;
    }
    
    private void setMemoryMap128k() {
        if (this.IF2RomEnabled) {
            this.readPages[0] = this.IF2Rom[0];
            this.readPages[1] = this.IF2Rom[1];
        }
        else {
            this.readPages[0] = this.Rom128k[0];
            this.readPages[1] = this.Rom128k[1];
        }
        this.writePages[0] = (this.writePages[1] = this.fakeROM);
        this.readPages[2] = (this.writePages[2] = this.Ram[10]);
        this.readPages[3] = (this.writePages[3] = this.Ram[11]);
        this.readPages[4] = (this.writePages[4] = this.Ram[4]);
        this.readPages[5] = (this.writePages[5] = this.Ram[5]);
        this.readPages[6] = (this.writePages[6] = this.Ram[0]);
        this.readPages[7] = (this.writePages[7] = this.Ram[1]);
        this.screenPage = 10;
        this.highPage = 0;
        this.model128k = true;
        final boolean b = false;
        this.plus3RamMode = b;
        this.pagingLocked = b;
        this.bankM = 0;
        this.mfLocked = true;
    }
    
    private void setMemoryMapPlus2() {
        if (this.IF2RomEnabled) {
            this.readPages[0] = this.IF2Rom[0];
            this.readPages[1] = this.IF2Rom[1];
        }
        else {
            this.readPages[0] = this.RomPlus2[0];
            this.readPages[1] = this.RomPlus2[1];
        }
        this.writePages[0] = (this.writePages[1] = this.fakeROM);
        this.readPages[2] = (this.writePages[2] = this.Ram[10]);
        this.readPages[3] = (this.writePages[3] = this.Ram[11]);
        this.readPages[4] = (this.writePages[4] = this.Ram[4]);
        this.readPages[5] = (this.writePages[5] = this.Ram[5]);
        this.readPages[6] = (this.writePages[6] = this.Ram[0]);
        this.readPages[7] = (this.writePages[7] = this.Ram[1]);
        this.screenPage = 10;
        this.highPage = 0;
        this.model128k = true;
        final boolean b = false;
        this.plus3RamMode = b;
        this.pagingLocked = b;
        this.bankM = 0;
        this.mfLocked = true;
    }
    
    private void setMemoryMapPlus3() {
        this.readPages[0] = this.RomPlus3[0];
        this.readPages[1] = this.RomPlus3[1];
        this.writePages[0] = (this.writePages[1] = this.fakeROM);
        this.readPages[2] = (this.writePages[2] = this.Ram[10]);
        this.readPages[3] = (this.writePages[3] = this.Ram[11]);
        this.readPages[4] = (this.writePages[4] = this.Ram[4]);
        this.readPages[5] = (this.writePages[5] = this.Ram[5]);
        this.readPages[6] = (this.writePages[6] = this.Ram[0]);
        this.readPages[7] = (this.writePages[7] = this.Ram[1]);
        this.screenPage = 10;
        this.highPage = 0;
        this.model128k = true;
        final boolean b = false;
        this.plus3RamMode = b;
        this.pagingLocked = b;
        final int n = 0;
        this.bankP = n;
        this.bankM = n;
        this.mfLocked = true;
    }
    
    public void setPort7ffd(int port7ffd) {
        port7ffd &= 0xFF;
        if (this.pagingLocked) {
            return;
        }
        this.bankM = port7ffd;
        this.pagingLocked = ((port7ffd & 0x20) != 0x0);
        this.screenPage = (((port7ffd & 0x8) == 0x0) ? 10 : 14);
        if (this.plus3RamMode) {
            return;
        }
        this.highPage = (port7ffd & 0x7);
        this.readPages[6] = (this.writePages[6] = this.Ram[this.highPage * 2]);
        this.readPages[7] = (this.writePages[7] = this.Ram[this.highPage * 2 + 1]);
        if (this.mfPagedIn) {
            return;
        }
        switch (this.spectrumModel) {
            case SPECTRUM128K: {
                if (this.IF2RomEnabled) {
                    this.readPages[0] = this.IF2Rom[0];
                    this.readPages[1] = this.IF2Rom[1];
                    break;
                }
                if ((port7ffd & 0x10) == 0x0) {
                    this.readPages[0] = this.Rom128k[0];
                    this.readPages[1] = this.Rom128k[1];
                    break;
                }
                this.readPages[0] = this.Rom128k[2];
                this.readPages[1] = this.Rom128k[3];
                break;
            }
            case SPECTRUMPLUS2: {
                if (this.IF2RomEnabled) {
                    break;
                }
                if ((port7ffd & 0x10) == 0x0) {
                    this.readPages[0] = this.RomPlus2[0];
                    this.readPages[1] = this.RomPlus2[1];
                    break;
                }
                this.readPages[0] = this.RomPlus2[2];
                this.readPages[1] = this.RomPlus2[3];
                break;
            }
            case SPECTRUMPLUS2A:
            case SPECTRUMPLUS3: {
                this.doPagingPlus3();
                break;
            }
        }
    }
    
    public void setPort1ffd(int port1ffd) {
        port1ffd &= 0x7;
        if (this.pagingLocked) {
            return;
        }
        this.bankP = port1ffd;
        this.doPagingPlus3();
    }
    
    private void doPagingPlus3() {
        if ((this.bankP & 0x1) == 0x0) {
            if (this.mfPagedIn) {
                return;
            }
            final int rom = (this.bankM & 0x10) >>> 3 | (this.bankP & 0x4);
            this.readPages[0] = this.RomPlus3[rom];
            this.readPages[1] = this.RomPlus3[rom + 1];
            this.writePages[0] = (this.writePages[1] = this.fakeROM);
            if (this.plus3RamMode) {
                this.readPages[2] = (this.writePages[2] = this.Ram[10]);
                this.readPages[3] = (this.writePages[3] = this.Ram[11]);
                this.readPages[4] = (this.writePages[4] = this.Ram[4]);
                this.readPages[5] = (this.writePages[5] = this.Ram[5]);
                this.highPage = (this.bankM & 0x7);
                this.readPages[6] = (this.writePages[6] = this.Ram[this.highPage * 2]);
                this.readPages[7] = (this.writePages[7] = this.Ram[this.highPage * 2 + 1]);
                this.plus3RamMode = false;
            }
        }
        else {
            this.plus3RamMode = true;
            switch (this.bankP & 0x6) {
                case 0: {
                    this.readPages[0] = (this.writePages[0] = this.Ram[0]);
                    this.readPages[1] = (this.writePages[1] = this.Ram[1]);
                    this.readPages[2] = (this.writePages[2] = this.Ram[2]);
                    this.readPages[3] = (this.writePages[3] = this.Ram[3]);
                    this.readPages[4] = (this.writePages[4] = this.Ram[4]);
                    this.readPages[5] = (this.writePages[5] = this.Ram[5]);
                    this.readPages[6] = (this.writePages[6] = this.Ram[6]);
                    this.readPages[7] = (this.writePages[7] = this.Ram[7]);
                    this.highPage = 3;
                    break;
                }
                case 2: {
                    this.readPages[0] = (this.writePages[0] = this.Ram[8]);
                    this.readPages[1] = (this.writePages[1] = this.Ram[9]);
                    this.readPages[2] = (this.writePages[2] = this.Ram[10]);
                    this.readPages[3] = (this.writePages[3] = this.Ram[11]);
                    this.readPages[4] = (this.writePages[4] = this.Ram[12]);
                    this.readPages[5] = (this.writePages[5] = this.Ram[13]);
                    this.readPages[6] = (this.writePages[6] = this.Ram[14]);
                    this.readPages[7] = (this.writePages[7] = this.Ram[15]);
                    this.highPage = 7;
                    break;
                }
                case 4: {
                    this.readPages[0] = (this.writePages[0] = this.Ram[8]);
                    this.readPages[1] = (this.writePages[1] = this.Ram[9]);
                    this.readPages[2] = (this.writePages[2] = this.Ram[10]);
                    this.readPages[3] = (this.writePages[3] = this.Ram[11]);
                    this.readPages[4] = (this.writePages[4] = this.Ram[12]);
                    this.readPages[5] = (this.writePages[5] = this.Ram[13]);
                    this.readPages[6] = (this.writePages[6] = this.Ram[6]);
                    this.readPages[7] = (this.writePages[7] = this.Ram[7]);
                    this.highPage = 3;
                    break;
                }
                case 6: {
                    this.readPages[0] = (this.writePages[0] = this.Ram[8]);
                    this.readPages[1] = (this.writePages[1] = this.Ram[9]);
                    this.readPages[2] = (this.writePages[2] = this.Ram[14]);
                    this.readPages[3] = (this.writePages[3] = this.Ram[15]);
                    this.readPages[4] = (this.writePages[4] = this.Ram[12]);
                    this.readPages[5] = (this.writePages[5] = this.Ram[13]);
                    this.readPages[6] = (this.writePages[6] = this.Ram[6]);
                    this.readPages[7] = (this.writePages[7] = this.Ram[7]);
                    this.highPage = 3;
                    break;
                }
            }
        }
    }
    
    public int getPlus3HighPage() {
        return this.highPage;
    }
    
    public boolean isPlus3RamMode() {
        return this.plus3RamMode;
    }
    
    public boolean isPagingLocked() {
        return this.pagingLocked;
    }
    
    public boolean isSpectrumRom() {
        if (this.mfPagedIn) {
            return false;
        }
        boolean res = false;
        switch (this.spectrumModel.codeModel) {
            case SPECTRUM48K: {
                res = !this.IF2RomEnabled;
                break;
            }
            case SPECTRUM128K: {
                res = ((this.bankM & 0x10) != 0x0 && !this.IF2RomEnabled);
                break;
            }
            case SPECTRUMPLUS3: {
                if (!this.plus3RamMode) {
                    res = (((this.bankM & 0x10) >>> 3 | (this.bankP & 0x4)) == 0x6);
                    break;
                }
                break;
            }
        }
        return res;
    }
    
    public boolean isScreenByte(final int addr) {
        if (this.plus3RamMode) {
            switch (this.bankP & 0x6) {
                case 0: {
                    return false;
                }
                case 4: {
                    return addr > 16383 && addr < 23296 && this.screenPage == 10;
                }
                case 6: {
                    return addr > 16383 && addr < 23296 && this.screenPage == 14;
                }
            }
        }
        switch (addr >>> 13) {
            case 2: {
                if (addr < 23296 && this.screenPage == 10) {
                    return true;
                }
                break;
            }
            case 6: {
                if (this.model128k && addr < 56064 && this.highPage << 1 == this.screenPage) {
                    return true;
                }
                break;
            }
        }
        return false;
    }
    
    public void reset() {
        this.mfPagedIn = false;
        switch (this.spectrumModel) {
            case SPECTRUM16K: {
                this.setMemoryMap16k();
                break;
            }
            case SPECTRUM48K: {
                this.setMemoryMap48k();
                break;
            }
            case SPECTRUM128K: {
                this.setMemoryMap128k();
                break;
            }
            case SPECTRUMPLUS2: {
                this.setMemoryMapPlus2();
                break;
            }
            case SPECTRUMPLUS2A:
            case SPECTRUMPLUS3: {
                this.setMemoryMapPlus3();
                break;
            }
        }
    }
    
    public void hardReset() {
        this.reset();
        for (int page = 0; page < this.Ram.length; ++page) {
            this.random.nextBytes(this.Ram[page]);
        }
        this.random.nextBytes(this.mfRAM);
    }
    
    public void multifacePageIn() {
        if (this.mfPagedIn || this.plus3RamMode) {
            return;
        }
        this.mfPagedIn = true;
        switch (this.spectrumModel.codeModel) {
            case SPECTRUM48K: {
                if (this.settings.getSpectrumSettings().isMf128On48K()) {
                    this.readPages[0] = this.mfROM[1];
                    break;
                }
                this.readPages[0] = this.mfROM[0];
                break;
            }
            case SPECTRUM128K: {
                this.readPages[0] = this.mfROM[1];
                break;
            }
            case SPECTRUMPLUS3: {
                this.readPages[0] = this.mfROM[2];
                break;
            }
        }
        this.readPages[1] = (this.writePages[1] = this.mfRAM);
    }
    
    public void multifacePageOut() {
        if (!this.mfPagedIn) {
            return;
        }
        this.mfPagedIn = false;
        switch (this.spectrumModel) {
            case SPECTRUM16K:
            case SPECTRUM48K: {
                if (this.IF2RomEnabled) {
                    this.readPages[0] = this.IF2Rom[0];
                    this.readPages[1] = this.IF2Rom[1];
                }
                else {
                    this.readPages[0] = this.Rom48k[0];
                    this.readPages[1] = this.Rom48k[1];
                }
                this.writePages[1] = this.fakeROM;
                break;
            }
            case SPECTRUM128K: {
                this.writePages[1] = this.fakeROM;
                if (this.pagingLocked) {
                    this.readPages[0] = this.Rom128k[2];
                    this.readPages[1] = this.Rom128k[3];
                    break;
                }
                this.setPort7ffd(this.bankM);
                break;
            }
            case SPECTRUMPLUS2: {
                this.writePages[1] = this.fakeROM;
                if (this.pagingLocked) {
                    this.readPages[0] = this.RomPlus2[2];
                    this.readPages[1] = this.RomPlus2[3];
                    break;
                }
                this.setPort7ffd(this.bankM);
                break;
            }
            case SPECTRUMPLUS2A:
            case SPECTRUMPLUS3: {
                this.writePages[1] = this.fakeROM;
                if (this.pagingLocked) {
                    this.readPages[0] = this.RomPlus3[6];
                    this.readPages[1] = this.RomPlus3[7];
                    break;
                }
                this.setPort7ffd(this.bankM);
                break;
            }
        }
    }
    
    public boolean isMultifacePaged() {
        return this.mfPagedIn;
    }
    
    public void setMultifaceLocked(final boolean state) {
        this.mfLocked = state;
    }
    
    public boolean isMultifaceLocked() {
        return this.mfLocked;
    }
    
    public boolean insertIF2Rom(final File filename) {
        return this.loadIF2Rom(filename) && (this.IF2RomEnabled = true);
    }
    
    public void insertIF2RomFromSZX(final byte[] rom) {
        System.arraycopy(rom, 0, this.IF2Rom[0], 0, this.IF2Rom[0].length);
        System.arraycopy(rom, 8192, this.IF2Rom[1], 0, this.IF2Rom[1].length);
        this.IF2RomEnabled = true;
    }
    
    public void ejectIF2Rom() {
        this.IF2RomEnabled = false;
        this.reset();
    }
    
    public boolean isIF2RomEnabled() {
        return this.IF2RomEnabled;
    }
    
    public void loadRoms() {
        final MemoryType conf = this.settings.getMemorySettings();
        String romsDirectory = conf.getRomsDirectory();
        if (!romsDirectory.isEmpty() && !romsDirectory.endsWith("/")) {
            romsDirectory += "/";
        }
        if (!this.loadRomAsFile(romsDirectory + conf.getRom48K(), this.Rom48k, 0, 16384)) {
            this.loadRomAsResource("/roms/spectrum.rom", this.Rom48k, 0, 16384);
        }
        if (!this.loadRomAsFile(romsDirectory + conf.getRom128K0(), this.Rom128k, 0, 16384)) {
            this.loadRomAsResource("/roms/128-0.rom", this.Rom128k, 0, 16384);
        }
        if (!this.loadRomAsFile(romsDirectory + conf.getRom128K1(), this.Rom128k, 2, 16384)) {
            this.loadRomAsResource("/roms/128-1.rom", this.Rom128k, 2, 16384);
        }
        if (!this.loadRomAsFile(romsDirectory + conf.getRomPlus20(), this.RomPlus2, 0, 16384)) {
            this.loadRomAsResource("/roms/plus2-0.rom", this.RomPlus2, 0, 16384);
        }
        if (!this.loadRomAsFile(romsDirectory + conf.getRomPlus21(), this.RomPlus2, 2, 16384)) {
            this.loadRomAsResource("/roms/plus2-1.rom", this.RomPlus2, 2, 16384);
        }
        if (!this.loadRomAsFile(romsDirectory + conf.getRomPlus30(), this.RomPlus3, 0, 16384)) {
            this.loadRomAsResource("/roms/plus3-0.rom", this.RomPlus3, 0, 16384);
        }
        if (!this.loadRomAsFile(romsDirectory + conf.getRomPlus31(), this.RomPlus3, 2, 16384)) {
            this.loadRomAsResource("/roms/plus3-1.rom", this.RomPlus3, 2, 16384);
        }
        if (!this.loadRomAsFile(romsDirectory + conf.getRomPlus32(), this.RomPlus3, 4, 16384)) {
            this.loadRomAsResource("/roms/plus3-2.rom", this.RomPlus3, 4, 16384);
        }
        if (!this.loadRomAsFile(romsDirectory + conf.getRomPlus33(), this.RomPlus3, 6, 16384)) {
            this.loadRomAsResource("/roms/plus3-3.rom", this.RomPlus3, 6, 16384);
        }
        if (!this.loadRomAsFile(romsDirectory + conf.getRomMF1(), this.mfROM, 0, 8192)) {
            this.loadRomAsResource("/roms/mf1.rom", this.mfROM, 0, 8192);
        }
        if (!this.loadRomAsFile(romsDirectory + conf.getRomMF128(), this.mfROM, 1, 8192)) {
            this.loadRomAsResource("/roms/mf128.rom", this.mfROM, 1, 8192);
        }
        if (!this.loadRomAsFile(romsDirectory + conf.getRomMFPlus3(), this.mfROM, 2, 8192)) {
            this.loadRomAsResource("/roms/mfplus3.rom", this.mfROM, 2, 8192);
        }
    }
    
    private boolean loadRomAsResource(final String filename, final byte[][] rom, final int page, final int size) {
        final InputStream inRom = Spectrum.class.getResourceAsStream(filename);
        boolean res = false;
        if (inRom == null) {
            final String msg = ResourceBundle.getBundle("machine/Bundle").getString("RESOURCE_ROM_ERROR");
            System.out.println(String.format("%s: %s", msg, filename));
            return false;
        }
        try {
            for (int frag = 0; frag < size / 8192; ++frag) {
                int count;
                for (count = 0; count != -1 && count < 8192; count += inRom.read(rom[page + frag], count, 8192 - count)) {}
                if (count != 8192) {
                    final String msg2 = ResourceBundle.getBundle("machine/Bundle").getString("ROM_SIZE_ERROR");
                    System.out.println(String.format("%s: %s", msg2, filename));
                }
                else {
                    res = true;
                }
            }
        }
        catch (IOException ex) {
            final String msg3 = ResourceBundle.getBundle("machine/Bundle").getString("RESOURCE_ROM_ERROR");
            System.out.println(String.format("%s: %s", msg3, filename));
            Logger.getLogger(Spectrum.class.getName()).log(Level.SEVERE, null, ex);
            try {
                inRom.close();
            }
            catch (IOException ex) {
                Logger.getLogger(Memory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        finally {
            try {
                inRom.close();
            }
            catch (IOException ex2) {
                Logger.getLogger(Memory.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        if (res) {
            final String msg = ResourceBundle.getBundle("machine/Bundle").getString("ROM_RESOURCE_LOADED");
            System.out.println(String.format("%s: %s", msg, filename));
        }
        return res;
    }
    
    private boolean loadRomAsFile(final String filename, final byte[][] rom, final int page, final int size) {
        BufferedInputStream fIn = null;
        boolean res = false;
        try {
            try {
                fIn = new BufferedInputStream(new FileInputStream(filename));
            }
            catch (FileNotFoundException ex3) {
                final String msg = ResourceBundle.getBundle("machine/Bundle").getString("FILE_ROM_ERROR");
                System.out.println(String.format("%s: %s", msg, filename));
                return false;
            }
            for (int frag = 0; frag < size / 8192; ++frag) {
                int count;
                for (count = 0; count != -1 && count < 8192; count += fIn.read(rom[page + frag], count, 8192 - count)) {}
                if (count != 8192) {
                    final String msg2 = ResourceBundle.getBundle("machine/Bundle").getString("ROM_SIZE_ERROR");
                    System.out.println(String.format("%s: %s", msg2, filename));
                }
                else {
                    res = true;
                }
            }
        }
        catch (IOException ex) {
            final String msg = ResourceBundle.getBundle("machine/Bundle").getString("FILE_ROM_ERROR");
            System.out.println(String.format("%s: %s", msg, filename));
            Logger.getLogger(Spectrum.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (fIn != null) {
                    fIn.close();
                }
            }
            catch (IOException ex) {
                Logger.getLogger(Memory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        finally {
            try {
                if (fIn != null) {
                    fIn.close();
                }
            }
            catch (IOException ex2) {
                Logger.getLogger(Memory.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        if (res) {
            final String msg3 = ResourceBundle.getBundle("machine/Bundle").getString("ROM_FILE_LOADED");
            System.out.println(String.format("%s: %s", msg3, filename));
        }
        return res;
    }
    
    private boolean loadIF2Rom(final File filename) {
        BufferedInputStream fIn = null;
        try {
            try {
                fIn = new BufferedInputStream(new FileInputStream(filename));
            }
            catch (FileNotFoundException ex) {
                Logger.getLogger(Memory.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            if (fIn.available() > 16384) {
                return false;
            }
            Arrays.fill(this.IF2Rom[0], (byte)(-1));
            Arrays.fill(this.IF2Rom[1], (byte)(-1));
            int readed = fIn.read(this.IF2Rom[0], 0, 8192);
            if (readed == -1) {
                return false;
            }
            if (readed < 8192) {
                return true;
            }
            if (fIn.available() > 0) {
                readed = fIn.read(this.IF2Rom[1], 0, 8192);
            }
            if (readed == -1) {
                return false;
            }
        }
        catch (IOException ex2) {
            Logger.getLogger(Memory.class.getName()).log(Level.SEVERE, null, ex2);
            try {
                fIn.close();
            }
            catch (IOException ex2) {
                Logger.getLogger(Memory.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        finally {
            try {
                fIn.close();
            }
            catch (IOException ex3) {
                Logger.getLogger(Memory.class.getName()).log(Level.SEVERE, null, ex3);
            }
        }
        return true;
    }
}
