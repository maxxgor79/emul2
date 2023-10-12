// 
// Decompiled by Procyon v0.5.36
// 

package utilities;

import java.util.zip.DeflaterOutputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.InflaterInputStream;
import java.io.ByteArrayInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;
import machine.Memory;
import java.io.File;
import java.util.ResourceBundle;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import machine.Spectrum;
import machine.MachineTypes;

public class Snapshots
{
    private int regAF;
    private int regBC;
    private int regDE;
    private int regHL;
    private int regAFalt;
    private int regBCalt;
    private int regDEalt;
    private int regHLalt;
    private int regIX;
    private int regIY;
    private int regPC;
    private int regSP;
    private int regI;
    private int regR;
    private int modeIM;
    private boolean iff1;
    private boolean iff2;
    private int last7ffd;
    private int last1ffd;
    private boolean enabledAY;
    private int lastfffd;
    private int[] psgRegs;
    private boolean multiface;
    private boolean mfPagedIn;
    private boolean mf128on48k;
    private boolean mfLockout;
    private boolean ULAplus;
    private boolean ULAplusEnabled;
    private int ULAplusRegister;
    private int[] ULAplusPalette;
    private boolean IF2RomPresent;
    private boolean tapeEmbedded;
    private boolean tapeLinked;
    private byte[] tapeData;
    private int tapeBlock;
    private String tapeName;
    private String tapeExtension;
    private MachineTypes snapshotModel;
    private int border;
    private int tstates;
    private Spectrum.Joystick joystick;
    private boolean issue2;
    private BufferedInputStream fIn;
    private BufferedOutputStream fOut;
    private boolean snapLoaded;
    private int error;
    private final String[] errorString;
    private static final int ZXST_HEADER = 1414748250;
    private static final int ZXSTMID_16K = 0;
    private static final int ZXSTMID_48K = 1;
    private static final int ZXSTMID_128K = 2;
    private static final int ZXSTMID_PLUS2 = 3;
    private static final int ZXSTMID_PLUS2A = 4;
    private static final int ZXSTMID_PLUS3 = 5;
    private static final int ZXSTMID_PLUS3E = 6;
    private static final int ZXSTMID_PENTAGON128 = 7;
    private static final int ZXSTMID_TC2048 = 8;
    private static final int ZXSTMID_TC2068 = 9;
    private static final int ZXSTMID_SCORPION = 10;
    private static final int ZXSTMID_SE = 11;
    private static final int ZXSTMID_TS2068 = 12;
    private static final int ZXSTMID_PENTAGON512 = 13;
    private static final int ZXSTMID_PENTAGON1024 = 14;
    private static final int ZXSTBID_ZXATASP = 1413568602;
    private static final int ZXSTAF_UPLOADJUMPER = 1;
    private static final int ZXSTAF_WRITEPROTECT = 2;
    private static final int ZXSTBID_ATARAM = 1347572801;
    private static final int ZXSTAF_COMPRESSED = 1;
    private static final int ZXSTBID_AY = 22849;
    private static final int ZXSTAYF_FULLERBOX = 1;
    private static final int ZXSTAYF_128AY = 2;
    private static final int ZXSTBID_ZXCF = 1178818650;
    private static final int ZXSTCF_UPLOADJUMPER = 1;
    private static final int ZXSTBID_CFRAM = 1347569219;
    private static final int ZXSTCRF_COMPRESSED = 1;
    private static final int ZXSTBID_COVOX = 1482051395;
    private static final int ZXSTBID_BETA128 = 942813506;
    private static final int ZXSTBETAF_CONNECTED = 1;
    private static final int ZXSTBETAF_CUSTOMROM = 2;
    private static final int ZXSTBETAF_PAGED = 4;
    private static final int ZXSTBETAF_AUTOBOOT = 8;
    private static final int ZXSTBETAF_SEEKLOWER = 16;
    private static final int ZXSTBETAF_COMPRESSED = 32;
    private static final int ZXSTBID_BETADISK = 1263748162;
    private static final int ZXSTBID_CREATOR = 1381257795;
    private static final int ZXSTBID_DOCK = 1262702404;
    private static final int ZXSTBID_DSKFILE = 4936516;
    private static final int ZXSTDSKF_COMPRESSED = 1;
    private static final int ZXSTDSKF_EMBEDDED = 2;
    private static final int ZXSTBID_GS = 21319;
    private static final int ZXSTBID_GSRAMPAGE = 1347572551;
    private static final int ZXSTBID_KEYBOARD = 1113146699;
    private static final int ZXSTKF_ISSUE2 = 1;
    private static final int ZXSKJT_KEMPSTON = 0;
    private static final int ZXSKJT_FULLER = 1;
    private static final int ZXSKJT_CURSOR = 2;
    private static final int ZXSKJT_SINCLAIR1 = 3;
    private static final int ZXSKJT_SINCLAIR2 = 4;
    private static final int ZXSKJT_SPECTRUMPLUS = 5;
    private static final int ZXSKJT_TIMEX1 = 6;
    private static final int ZXSKJT_TIMEX2 = 7;
    private static final int ZXSKJT_NONE = 8;
    private static final int ZXSTBID_IF1 = 3229257;
    private static final int ZXSTIF1F_ENABLED = 1;
    private static final int ZXSTIF1F_COMPRESSED = 2;
    private static final int ZXSTIF1F_PAGED = 4;
    private static final int ZXSTBID_IF2ROM = 1379026505;
    private static final int ZXSTBID_JOYSTICK = 5853002;
    private static final int ZXSTJT_KEMPSTON = 0;
    private static final int ZXSTJT_FULLER = 1;
    private static final int ZXSTJT_CURSOR = 2;
    private static final int ZXSTJT_SINCLAIR1 = 3;
    private static final int ZXSTJT_SINCLAIR2 = 4;
    private static final int ZXSTJT_COMCOM = 5;
    private static final int ZXSTJT_TIMEX1 = 6;
    private static final int ZXSTJT_TIMEX2 = 7;
    private static final int ZXSTJT_DISABLED = 8;
    private static final int ZXSTBID_MICRODRIVE = 1448232013;
    private static final int ZXSTMDF_COMPRESSED = 1;
    private static final int ZXSTMDF_EMBEDDED = 2;
    private static final int ZXSTBID_MOUSE = 1297632577;
    private static final int ZXSTBID_MULTIFACE = 1162036813;
    private static final int ZXSTMFM_1 = 0;
    private static final int ZXSTMFM_128 = 1;
    private static final int ZXSTMF_PAGEDIN = 1;
    private static final int ZXSTMF_COMPRESSED = 2;
    private static final int ZXSTMF_SOFTWARELOCKOUT = 4;
    private static final int ZXSTMF_REDBUTTONDISABLED = 8;
    private static final int ZXSTMF_DISABLED = 16;
    private static final int ZXSTMF_16KRAMMODE = 32;
    private static final int ZXSTBID_RAMPAGE = 1347240274;
    private static final int ZXSTRF_COMPRESSED = 1;
    private static final int ZXSTBID_PLUS3DISK = 13099;
    private static final int ZXSTBID_PLUSD = 1146309712;
    private static final int ZXSTBID_PLUSDDISK = 1263748176;
    private static final int ZXSTBID_ROM = 5066578;
    private static final int ZXSTBID_TIMEXREGS = 1145848659;
    private static final int ZXSTBID_SIMPLEIDE = 1162103123;
    private static final int ZXSTBID_SPECDRUM = 1297437252;
    private static final int ZXSTBID_SPECREGS = 1380143187;
    private static final int ZXSTBID_ZXTAPE = 1162887508;
    private static final int ZXSTTP_EMBEDDED = 1;
    private static final int ZXSTTP_COMPRESSED = 2;
    private static final int ZXSTBID_USPEECH = 1162892117;
    private static final int ZXSTBID_ZXPRINTER = 1380997210;
    private static final int ZXSTBID_Z80REGS = 1378891866;
    private static final int ZXSTZF_EILAST = 1;
    private static final int ZXSTZF_HALTED = 2;
    private static final int ZXSTBID_PALETTE = 1414810704;
    private static final int ZXSTPALETTE_DISABLED = 0;
    private static final int ZXSTPALETTE_ENABLED = 1;
    
    public Snapshots() {
        this.psgRegs = new int[16];
        this.ULAplusPalette = new int[64];
        this.errorString = new String[] { "OPERATION_OK", "NOT_SNAPSHOT_FILE", "OPEN_FILE_ERROR", "FILE_SIZE_ERROR", "FILE_READ_ERROR", "UNSUPPORTED_SNAPSHOT", "FILE_WRITE_ERROR", "SNA_REGSP_ERROR", "SNAP_EXTENSION_ERROR", "SNA_DONT_SUPPORT_PLUS3", "SZX_RAMP_SIZE_ERROR" };
        this.snapLoaded = false;
        this.error = 0;
    }
    
    public boolean isSnapLoaded() {
        return this.snapLoaded;
    }
    
    public MachineTypes getSnapshotModel() {
        return this.snapshotModel;
    }
    
    public void setSnapshotModel(final MachineTypes model) {
        this.snapshotModel = model;
    }
    
    public int getRegAF() {
        return this.regAF;
    }
    
    public void setRegAF(final int value) {
        this.regAF = value;
    }
    
    public int getRegBC() {
        return this.regBC;
    }
    
    public void setRegBC(final int value) {
        this.regBC = value;
    }
    
    public int getRegDE() {
        return this.regDE;
    }
    
    public void setRegDE(final int value) {
        this.regDE = value;
    }
    
    public int getRegHL() {
        return this.regHL;
    }
    
    public void setRegHL(final int value) {
        this.regHL = value;
    }
    
    public int getRegAFalt() {
        return this.regAFalt;
    }
    
    public void setRegAFalt(final int value) {
        this.regAFalt = value;
    }
    
    public int getRegBCalt() {
        return this.regBCalt;
    }
    
    public void setRegBCalt(final int value) {
        this.regBCalt = value;
    }
    
    public int getRegDEalt() {
        return this.regDEalt;
    }
    
    public void setRegDEalt(final int value) {
        this.regDEalt = value;
    }
    
    public int getRegHLalt() {
        return this.regHLalt;
    }
    
    public void setRegHLalt(final int value) {
        this.regHLalt = value;
    }
    
    public int getRegIX() {
        return this.regIX;
    }
    
    public void setRegIX(final int value) {
        this.regIX = value;
    }
    
    public int getRegIY() {
        return this.regIY;
    }
    
    public void setRegIY(final int value) {
        this.regIY = value;
    }
    
    public int getRegSP() {
        return this.regSP;
    }
    
    public void setRegSP(final int value) {
        this.regSP = value;
    }
    
    public int getRegPC() {
        return this.regPC;
    }
    
    public void setRegPC(final int value) {
        this.regPC = value;
    }
    
    public int getRegI() {
        return this.regI;
    }
    
    public void setRegI(final int value) {
        this.regI = value;
    }
    
    public int getRegR() {
        return this.regR;
    }
    
    public void setRegR(final int value) {
        this.regR = value;
    }
    
    public int getModeIM() {
        return this.modeIM;
    }
    
    public void setModeIM(final int value) {
        this.modeIM = value;
    }
    
    public boolean getIFF1() {
        return this.iff1;
    }
    
    public void setIFF1(final boolean value) {
        this.iff1 = value;
    }
    
    public boolean getIFF2() {
        return this.iff2;
    }
    
    public void setIFF2(final boolean value) {
        this.iff2 = value;
    }
    
    public int getPort7ffd() {
        return this.last7ffd;
    }
    
    public void setPort7ffd(final int port7ffd) {
        this.last7ffd = port7ffd;
    }
    
    public int getPort1ffd() {
        return this.last1ffd;
    }
    
    public void setPort1ffd(final int port1ffd) {
        this.last1ffd = port1ffd;
    }
    
    public int getPortfffd() {
        return this.lastfffd;
    }
    
    public void setPortfffd(final int portfffd) {
        this.lastfffd = portfffd;
    }
    
    public int getPsgReg(final int reg) {
        return this.psgRegs[reg];
    }
    
    public void setPsgReg(final int reg, final int value) {
        this.psgRegs[reg] = value;
    }
    
    public boolean getEnabledAY() {
        return this.enabledAY;
    }
    
    public void setEnabledAY(final boolean ayEnabled) {
        this.enabledAY = ayEnabled;
    }
    
    public int getBorder() {
        return this.border;
    }
    
    public void setBorder(final int value) {
        this.border = value;
    }
    
    public int getTstates() {
        return this.tstates;
    }
    
    public void setTstates(final int value) {
        this.tstates = value;
    }
    
    public Spectrum.Joystick getJoystick() {
        return this.joystick;
    }
    
    public void setJoystick(final Spectrum.Joystick model) {
        this.joystick = model;
    }
    
    public boolean isIssue2() {
        return this.issue2;
    }
    
    public void setIssue2(final boolean version) {
        this.issue2 = version;
    }
    
    public boolean isMultiface() {
        return this.multiface;
    }
    
    public void setMultiface(final boolean haveMF) {
        this.multiface = haveMF;
    }
    
    public boolean isMFPagedIn() {
        return this.mfPagedIn;
    }
    
    public void setMFPagedIn(final boolean mf) {
        this.mfPagedIn = mf;
    }
    
    public boolean isMF128on48k() {
        return this.mf128on48k;
    }
    
    public void setMF128on48k(final boolean mf128) {
        this.mf128on48k = mf128;
    }
    
    public boolean isMFLockout() {
        return this.mfLockout;
    }
    
    public void setMFlockout(final boolean mf) {
        this.mfLockout = mf;
    }
    
    public boolean isULAplus() {
        return this.ULAplus;
    }
    
    public void setULAplus(final boolean ulaplus) {
        this.ULAplus = ulaplus;
    }
    
    public boolean isULAplusEnabled() {
        return this.ULAplusEnabled;
    }
    
    public void setULAplusEnabled(final boolean state) {
        this.ULAplusEnabled = state;
    }
    
    public int getULAplusRegister() {
        return this.ULAplusRegister;
    }
    
    public void setULAplusRegister(final int register) {
        this.ULAplusRegister = register;
    }
    
    public int getULAplusColor(final int register) {
        return this.ULAplusPalette[register];
    }
    
    public void setULAplusColor(final int register, final int color) {
        this.ULAplusPalette[register] = color;
    }
    
    public boolean isIF2RomPresent() {
        return this.IF2RomPresent;
    }
    
    public void setIF2RomPresent(final boolean state) {
        this.IF2RomPresent = state;
    }
    
    public boolean isTapeEmbedded() {
        return this.tapeEmbedded;
    }
    
    public void setTapeEmbedded(final boolean state) {
        this.tapeEmbedded = state;
    }
    
    public boolean isTapeLinked() {
        return this.tapeLinked;
    }
    
    public void setTapeLinked(final boolean state) {
        this.tapeLinked = state;
    }
    
    public byte[] getTapeData() {
        return this.tapeData;
    }
    
    public int getTapeBlock() {
        return this.tapeBlock;
    }
    
    public void setTapeBlock(final int block) {
        this.tapeBlock = block;
    }
    
    public String getTapeName() {
        return this.tapeName;
    }
    
    public void setTapeName(final String filename) {
        this.tapeName = filename;
    }
    
    public String getTapeExtension() {
        return this.tapeExtension;
    }
    
    public String getErrorString() {
        return ResourceBundle.getBundle("utilities/Bundle").getString(this.errorString[this.error]);
    }
    
    public boolean loadSnapshot(final File filename, final Memory memory) {
        if (filename.getName().toLowerCase().endsWith(".sna")) {
            return this.loadSNA(filename, memory);
        }
        if (filename.getName().toLowerCase().endsWith(".z80")) {
            return this.loadZ80(filename, memory);
        }
        if (filename.getName().toLowerCase().endsWith(".szx")) {
            return this.loadSZX(filename, memory);
        }
        this.error = 1;
        return false;
    }
    
    public boolean saveSnapshot(final File filename, final Memory memory) {
        if (filename.getName().toLowerCase().endsWith(".sna")) {
            return this.saveSNA(filename, memory);
        }
        if (filename.getName().toLowerCase().endsWith(".z80")) {
            return this.saveZ80(filename, memory);
        }
        if (filename.getName().toLowerCase().endsWith(".szx")) {
            return this.saveSZX(filename, memory);
        }
        this.error = 8;
        return false;
    }
    
    private boolean loadSNA(final File filename, final Memory memory) {
        try {
            try {
                this.fIn = new BufferedInputStream(new FileInputStream(filename));
            }
            catch (FileNotFoundException ex) {
                this.error = 2;
                return false;
            }
            final int snaLen = this.fIn.available();
            switch (snaLen) {
                case 49179: {
                    this.snapshotModel = MachineTypes.SPECTRUM48K;
                    memory.setSpectrumModel(MachineTypes.SPECTRUM48K);
                    memory.reset();
                    break;
                }
                case 131103:
                case 147487: {
                    this.snapshotModel = MachineTypes.SPECTRUM128K;
                    memory.setSpectrumModel(MachineTypes.SPECTRUM128K);
                    memory.reset();
                    break;
                }
                default: {
                    this.error = 3;
                    this.fIn.close();
                    return false;
                }
            }
            byte[] snaHeader;
            int count;
            for (snaHeader = new byte[27], count = 0; count != -1 && count < snaHeader.length; count += this.fIn.read(snaHeader, count, snaHeader.length - count)) {}
            if (count != snaHeader.length) {
                this.error = 4;
                this.fIn.close();
                return false;
            }
            this.regI = (snaHeader[0] & 0xFF);
            this.regHLalt = ((snaHeader[1] & 0xFF) | (snaHeader[2] << 8 & 0xFFFF));
            this.regDEalt = ((snaHeader[3] & 0xFF) | (snaHeader[4] << 8 & 0xFFFF));
            this.regBCalt = ((snaHeader[5] & 0xFF) | (snaHeader[6] << 8 & 0xFFFF));
            this.regAFalt = ((snaHeader[7] & 0xFF) | (snaHeader[8] << 8 & 0xFFFF));
            this.regHL = ((snaHeader[9] & 0xFF) | (snaHeader[10] << 8 & 0xFFFF));
            this.regDE = ((snaHeader[11] & 0xFF) | (snaHeader[12] << 8 & 0xFFFF));
            this.regBC = ((snaHeader[13] & 0xFF) | (snaHeader[14] << 8 & 0xFFFF));
            this.regIY = ((snaHeader[15] & 0xFF) | (snaHeader[16] << 8 & 0xFFFF));
            this.regIX = ((snaHeader[17] & 0xFF) | (snaHeader[18] << 8 & 0xFFFF));
            final boolean b = false;
            this.iff2 = b;
            this.iff1 = b;
            if ((snaHeader[19] & 0x4) != 0x0) {
                this.iff2 = true;
            }
            if ((snaHeader[19] & 0x2) != 0x0) {
                this.iff1 = true;
            }
            this.regR = (snaHeader[20] & 0xFF);
            this.regAF = ((snaHeader[21] & 0xFF) | (snaHeader[22] << 8 & 0xFFFF));
            this.regSP = ((snaHeader[23] & 0xFF) | (snaHeader[24] << 8 & 0xFFFF));
            this.modeIM = (snaHeader[25] & 0xFF);
            this.border = (snaHeader[26] & 0x7);
            byte[] buffer;
            for (buffer = new byte[16384], count = 0; count != -1 && count < 16384; count += this.fIn.read(buffer, count, 16384 - count)) {}
            if (count != 16384) {
                this.error = 4;
                this.fIn.close();
                return false;
            }
            memory.loadPage(5, buffer);
            for (count = 0; count != -1 && count < 16384; count += this.fIn.read(buffer, count, 16384 - count)) {}
            if (count != 16384) {
                this.error = 4;
                this.fIn.close();
                return false;
            }
            memory.loadPage(2, buffer);
            if (snaLen == 49179) {
                for (count = 0; count != -1 && count < 16384; count += this.fIn.read(buffer, count, 16384 - count)) {}
                if (count != 16384) {
                    this.error = 4;
                    this.fIn.close();
                    return false;
                }
                memory.loadPage(0, buffer);
                this.regPC = 114;
            }
            else {
                final boolean[] loaded = new boolean[8];
                for (count = 0; count != -1 && count < 16384; count += this.fIn.read(buffer, count, 16384 - count)) {}
                if (count != 16384) {
                    this.error = 4;
                    this.fIn.close();
                    return false;
                }
                loaded[2] = (loaded[5] = true);
                this.regPC = (this.fIn.read() | (this.fIn.read() << 8 & 0xFFFF));
                this.last7ffd = (this.fIn.read() & 0xFF);
                int page = this.last7ffd & 0x7;
                if (page != 2 && page != 5) {
                    memory.loadPage(page, buffer);
                    loaded[page] = true;
                }
                final int trDos = this.fIn.read();
                if (trDos == 1) {
                    this.error = 1;
                    this.fIn.close();
                    return false;
                }
                for (page = 0; page < 8; ++page) {
                    if (!loaded[page]) {
                        for (count = 0; count != -1 && count < 16384; count += this.fIn.read(buffer, count, 16384 - count)) {}
                        if (count != 16384) {
                            this.error = 4;
                            this.fIn.close();
                            return false;
                        }
                        memory.loadPage(page, buffer);
                    }
                    Arrays.fill(this.psgRegs, 0);
                    this.lastfffd = 0;
                }
            }
            this.fIn.close();
            this.issue2 = false;
            this.joystick = Spectrum.Joystick.NONE;
            this.tstates = 0;
            final boolean b2 = false;
            this.mf128on48k = b2;
            this.mfPagedIn = b2;
        }
        catch (IOException ex2) {
            this.error = 4;
            return false;
        }
        return true;
    }
    
    private boolean saveSNA(final File filename, final Memory memory) {
        if (this.snapshotModel == MachineTypes.SPECTRUM48K && this.regSP < 16386) {
            this.error = 7;
            return false;
        }
        if (this.snapshotModel.codeModel == MachineTypes.CodeModel.SPECTRUMPLUS3) {
            this.error = 9;
            return false;
        }
        try {
            try {
                this.fOut = new BufferedOutputStream(new FileOutputStream(filename));
            }
            catch (FileNotFoundException ex) {
                this.error = 2;
                return false;
            }
            final byte[] snaHeader = { (byte)this.regI, (byte)this.regHLalt, (byte)(this.regHLalt >>> 8), (byte)this.regDEalt, (byte)(this.regDEalt >>> 8), (byte)this.regBCalt, (byte)(this.regBCalt >>> 8), (byte)this.regAFalt, (byte)(this.regAFalt >>> 8), (byte)this.regHL, (byte)(this.regHL >>> 8), (byte)this.regDE, (byte)(this.regDE >>> 8), (byte)this.regBC, (byte)(this.regBC >>> 8), (byte)this.regIY, (byte)(this.regIY >>> 8), (byte)this.regIX, (byte)(this.regIX >>> 8), 0, 0, 0, 0, 0, 0, 0, 0 };
            if (this.iff1) {
                final byte[] array = snaHeader;
                final int n = 19;
                array[n] |= 0x2;
            }
            if (this.iff2) {
                final byte[] array2 = snaHeader;
                final int n2 = 19;
                array2[n2] |= 0x4;
            }
            snaHeader[20] = (byte)this.regR;
            snaHeader[21] = (byte)this.regAF;
            snaHeader[22] = (byte)(this.regAF >>> 8);
            if (this.snapshotModel.codeModel == MachineTypes.CodeModel.SPECTRUM48K) {
                memory.writeByte(this.regSP = (this.regSP - 1 & 0xFFFF), (byte)(this.regPC >>> 8));
                memory.writeByte(this.regSP = (this.regSP - 1 & 0xFFFF), (byte)this.regPC);
            }
            snaHeader[23] = (byte)this.regSP;
            snaHeader[24] = (byte)(this.regSP >>> 8);
            snaHeader[25] = (byte)this.modeIM;
            snaHeader[26] = (byte)this.border;
            this.fOut.write(snaHeader, 0, snaHeader.length);
            final byte[] buffer = new byte[16384];
            memory.savePage(5, buffer);
            this.fOut.write(buffer, 0, buffer.length);
            memory.savePage(2, buffer);
            this.fOut.write(buffer, 0, buffer.length);
            if (this.snapshotModel.codeModel == MachineTypes.CodeModel.SPECTRUM48K) {
                memory.savePage(0, buffer);
                this.fOut.write(buffer, 0, buffer.length);
            }
            else {
                memory.savePage(this.last7ffd & 0x7, buffer);
                this.fOut.write(buffer, 0, buffer.length);
                final boolean[] saved = new boolean[8];
                saved[2] = (saved[5] = true);
                this.fOut.write(this.regPC);
                this.fOut.write(this.regPC >>> 8);
                this.fOut.write(this.last7ffd);
                this.fOut.write(0);
                saved[this.last7ffd & 0x7] = true;
                for (int page = 0; page < 8; ++page) {
                    if (!saved[page]) {
                        memory.savePage(page, buffer);
                        this.fOut.write(buffer, 0, buffer.length);
                    }
                }
            }
            this.fOut.close();
        }
        catch (IOException ex2) {
            this.error = 6;
            return false;
        }
        return true;
    }
    
    private int uncompressZ80(final byte[] buffer, final int length) {
        int address = 0;
        try {
            while (this.fIn.available() > 0 && address < length) {
                final int mem = this.fIn.read() & 0xFF;
                if (mem != 237) {
                    buffer[address++] = (byte)mem;
                }
                else {
                    final int mem2 = this.fIn.read() & 0xFF;
                    if (mem2 != 237) {
                        buffer[address++] = -19;
                        buffer[address++] = (byte)mem2;
                    }
                    else {
                        int nreps = this.fIn.read() & 0xFF;
                        final int value = this.fIn.read() & 0xFF;
                        while (nreps-- > 0) {
                            buffer[address++] = (byte)value;
                        }
                    }
                }
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Snapshots.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return address;
    }
    
    private int countRepeatedByte(final Memory memory, final int page, int address, final byte value) {
        int count;
        for (count = 0; address < 16384 && count < 254 && value == memory.readByte(page, address++); ++count) {}
        return count;
    }
    
    private int compressPageZ80(final Memory memory, final byte[] buffer, final int page) {
        int address = 0;
        int addrDst = 0;
        while (address < 16384) {
            final byte value = memory.readByte(page, address++);
            final int nReps = this.countRepeatedByte(memory, page, address, value);
            if (value == -19) {
                if (nReps == 0) {
                    buffer[addrDst++] = -19;
                    buffer[addrDst++] = memory.readByte(page, address++);
                }
                else {
                    buffer[addrDst++] = -19;
                    buffer[addrDst++] = -19;
                    buffer[addrDst++] = (byte)(nReps + 1);
                    buffer[addrDst++] = -19;
                    address += nReps;
                }
            }
            else if (nReps < 4) {
                buffer[addrDst++] = value;
            }
            else {
                buffer[addrDst++] = -19;
                buffer[addrDst++] = -19;
                buffer[addrDst++] = (byte)(nReps + 1);
                buffer[addrDst++] = value;
                address += nReps;
            }
        }
        return addrDst;
    }
    
    private boolean loadZ80(final File filename, final Memory memory) {
        try {
            try {
                this.fIn = new BufferedInputStream(new FileInputStream(filename));
            }
            catch (FileNotFoundException ex) {
                this.error = 2;
                return false;
            }
            if (this.fIn.available() < 30) {
                this.error = 3;
                this.fIn.close();
                return false;
            }
            byte[] z80Header1;
            int count;
            for (z80Header1 = new byte[30], count = 0; count != -1 && count < z80Header1.length; count += this.fIn.read(z80Header1, count, z80Header1.length - count)) {}
            if (count != z80Header1.length) {
                this.error = 4;
                this.fIn.close();
                return false;
            }
            this.regAF = ((z80Header1[1] & 0xFF) | (z80Header1[0] << 8 & 0xFFFF));
            this.regBC = ((z80Header1[2] & 0xFF) | (z80Header1[3] << 8 & 0xFFFF));
            this.regHL = ((z80Header1[4] & 0xFF) | (z80Header1[5] << 8 & 0xFFFF));
            this.regPC = ((z80Header1[6] & 0xFF) | (z80Header1[7] << 8 & 0xFFFF));
            this.regSP = ((z80Header1[8] & 0xFF) | (z80Header1[9] << 8 & 0xFFFF));
            this.regI = (z80Header1[10] & 0xFF);
            this.regR = (z80Header1[11] & 0x7F);
            if ((z80Header1[12] & 0x1) != 0x0) {
                this.regR |= 0x80;
            }
            this.border = (z80Header1[12] >>> 1 & 0x7);
            this.regDE = ((z80Header1[13] & 0xFF) | (z80Header1[14] << 8 & 0xFFFF));
            this.regBCalt = ((z80Header1[15] & 0xFF) | (z80Header1[16] << 8 & 0xFFFF));
            this.regDEalt = ((z80Header1[17] & 0xFF) | (z80Header1[18] << 8 & 0xFFFF));
            this.regHLalt = ((z80Header1[19] & 0xFF) | (z80Header1[20] << 8 & 0xFFFF));
            this.regAFalt = ((z80Header1[22] & 0xFF) | (z80Header1[21] << 8 & 0xFFFF));
            this.regIY = ((z80Header1[23] & 0xFF) | (z80Header1[24] << 8 & 0xFFFF));
            this.regIX = ((z80Header1[25] & 0xFF) | (z80Header1[26] << 8 & 0xFFFF));
            this.iff1 = (z80Header1[27] != 0);
            this.iff2 = (z80Header1[28] != 0);
            this.modeIM = (z80Header1[29] & 0x3);
            this.issue2 = ((z80Header1[29] & 0x4) != 0x0);
            switch (z80Header1[29] >>> 6) {
                case 0: {
                    this.joystick = Spectrum.Joystick.NONE;
                    break;
                }
                case 1: {
                    this.joystick = Spectrum.Joystick.KEMPSTON;
                    break;
                }
                case 2: {
                    this.joystick = Spectrum.Joystick.SINCLAIR1;
                    break;
                }
                case 3: {
                    this.joystick = Spectrum.Joystick.SINCLAIR2;
                    break;
                }
            }
            if (this.regPC != 0) {
                final byte[] pageBuffer = new byte[16384];
                this.snapshotModel = MachineTypes.SPECTRUM48K;
                memory.setSpectrumModel(MachineTypes.SPECTRUM48K);
                if ((z80Header1[12] & 0x20) == 0x0) {
                    for (count = 0; count != -1 && count < 16384; count += this.fIn.read(pageBuffer, count, 16384 - count)) {}
                    if (count != 16384) {
                        this.error = 4;
                        this.fIn.close();
                        return false;
                    }
                    memory.loadPage(5, pageBuffer);
                    for (count = 0; count != -1 && count < 16384; count += this.fIn.read(pageBuffer, count, 16384 - count)) {}
                    if (count != 16384) {
                        this.error = 4;
                        this.fIn.close();
                        return false;
                    }
                    memory.loadPage(2, pageBuffer);
                    for (count = 0; count != -1 && count < 16384; count += this.fIn.read(pageBuffer, count, 16384 - count)) {}
                    if (count != 16384) {
                        this.error = 4;
                        this.fIn.close();
                        return false;
                    }
                    memory.loadPage(0, pageBuffer);
                }
                else {
                    final byte[] buffer = new byte[49152];
                    final int len = this.uncompressZ80(buffer, buffer.length);
                    if (len != 49152 || this.fIn.available() != 4) {
                        this.error = 4;
                        this.fIn.close();
                        return false;
                    }
                    memory.loadPage(5, buffer);
                    System.arraycopy(buffer, 16384, pageBuffer, 0, 16384);
                    memory.loadPage(2, pageBuffer);
                    System.arraycopy(buffer, 32768, pageBuffer, 0, 16384);
                    memory.loadPage(0, pageBuffer);
                }
            }
            else {
                final int hdrLen = this.fIn.read() | (this.fIn.read() << 8 & 0xFFFF);
                if (hdrLen != 23 && hdrLen != 54 && hdrLen != 55) {
                    this.error = 3;
                    this.fIn.close();
                    return false;
                }
                byte[] z80Header2;
                for (z80Header2 = new byte[hdrLen], count = 0; count != -1 && count < z80Header2.length; count += this.fIn.read(z80Header2, count, z80Header2.length - count)) {}
                if (count != z80Header2.length) {
                    this.error = 4;
                    this.fIn.close();
                    return false;
                }
                this.regPC = ((z80Header2[0] & 0xFF) | (z80Header2[1] << 8 & 0xFFFF));
                final boolean modifiedHW = (z80Header2[5] & 0x80) != 0x0;
                if (hdrLen == 23) {
                    switch (z80Header2[2]) {
                        case 0:
                        case 1: {
                            if (modifiedHW) {
                                this.snapshotModel = MachineTypes.SPECTRUM16K;
                                break;
                            }
                            this.snapshotModel = MachineTypes.SPECTRUM48K;
                            break;
                        }
                        case 3:
                        case 4: {
                            if (modifiedHW) {
                                this.snapshotModel = MachineTypes.SPECTRUMPLUS2;
                                break;
                            }
                            this.snapshotModel = MachineTypes.SPECTRUM128K;
                            break;
                        }
                        case 7: {
                            if (modifiedHW) {
                                this.snapshotModel = MachineTypes.SPECTRUMPLUS2A;
                                break;
                            }
                            this.snapshotModel = MachineTypes.SPECTRUMPLUS3;
                            break;
                        }
                        case 12: {
                            this.snapshotModel = MachineTypes.SPECTRUMPLUS2;
                            break;
                        }
                        case 13: {
                            this.snapshotModel = MachineTypes.SPECTRUMPLUS2A;
                            break;
                        }
                        default: {
                            this.error = 5;
                            this.fIn.close();
                            return false;
                        }
                    }
                }
                else {
                    switch (z80Header2[2]) {
                        case 0:
                        case 1: {
                            if (modifiedHW) {
                                this.snapshotModel = MachineTypes.SPECTRUM16K;
                                break;
                            }
                            this.snapshotModel = MachineTypes.SPECTRUM48K;
                            break;
                        }
                        case 4:
                        case 5: {
                            if (modifiedHW) {
                                this.snapshotModel = MachineTypes.SPECTRUMPLUS2;
                                break;
                            }
                            this.snapshotModel = MachineTypes.SPECTRUM128K;
                            break;
                        }
                        case 7: {
                            if (modifiedHW) {
                                this.snapshotModel = MachineTypes.SPECTRUMPLUS2A;
                                break;
                            }
                            this.snapshotModel = MachineTypes.SPECTRUMPLUS3;
                            break;
                        }
                        case 12: {
                            this.snapshotModel = MachineTypes.SPECTRUMPLUS2;
                            break;
                        }
                        case 13: {
                            this.snapshotModel = MachineTypes.SPECTRUMPLUS2A;
                            break;
                        }
                        default: {
                            this.error = 5;
                            this.fIn.close();
                            return false;
                        }
                    }
                }
                memory.setSpectrumModel(this.snapshotModel);
                this.last7ffd = (z80Header2[3] & 0xFF);
                this.enabledAY = ((z80Header2[5] & 0x4) != 0x0);
                this.lastfffd = (z80Header2[6] & 0xFF);
                for (int idx = 0; idx < 16; ++idx) {
                    this.psgRegs[idx] = (z80Header2[7 + idx] & 0xFF);
                }
                this.last1ffd = 0;
                if (hdrLen == 55) {
                    this.last1ffd = (z80Header2[54] & 0xFF);
                }
                final byte[] buffer2 = new byte[16384];
                while (this.fIn.available() > 0) {
                    final int blockLen = this.fIn.read() | (this.fIn.read() << 8 & 0xFFFF);
                    int ramPage = this.fIn.read() & 0xFF;
                    if (this.snapshotModel.codeModel == MachineTypes.CodeModel.SPECTRUM48K) {
                        switch (ramPage) {
                            case 4: {
                                ramPage = 2;
                                break;
                            }
                            case 5: {
                                ramPage = 0;
                                break;
                            }
                            case 8: {
                                ramPage = 5;
                                break;
                            }
                        }
                    }
                    else {
                        if (ramPage < 3) {
                            continue;
                        }
                        if (ramPage > 10) {
                            continue;
                        }
                        ramPage -= 3;
                    }
                    if (blockLen == 65535) {
                        for (count = 0; count != -1 && count < 16384; count += this.fIn.read(buffer2, count, 16384 - count)) {}
                        if (count != 16384) {
                            this.error = 4;
                            this.fIn.close();
                            return false;
                        }
                        memory.loadPage(ramPage, buffer2);
                    }
                    else {
                        final int len2 = this.uncompressZ80(buffer2, 16384);
                        if (len2 != 16384) {
                            this.error = 4;
                            this.fIn.close();
                            return false;
                        }
                        memory.loadPage(ramPage, buffer2);
                    }
                }
            }
            this.fIn.close();
            this.tstates = 0;
        }
        catch (IOException ex2) {
            this.error = 4;
            return false;
        }
        return true;
    }
    
    private boolean saveZ80(final File filename, final Memory memory) {
        try {
            try {
                this.fOut = new BufferedOutputStream(new FileOutputStream(filename));
            }
            catch (FileNotFoundException ex) {
                this.error = 2;
                return false;
            }
            final byte[] z80HeaderV3 = new byte[87];
            z80HeaderV3[0] = (byte)(this.regAF >>> 8);
            z80HeaderV3[1] = (byte)this.regAF;
            z80HeaderV3[2] = (byte)this.regBC;
            z80HeaderV3[3] = (byte)(this.regBC >>> 8);
            z80HeaderV3[4] = (byte)this.regHL;
            z80HeaderV3[5] = (byte)(this.regHL >>> 8);
            z80HeaderV3[8] = (byte)this.regSP;
            z80HeaderV3[9] = (byte)(this.regSP >>> 8);
            z80HeaderV3[10] = (byte)this.regI;
            z80HeaderV3[11] = (byte)(this.regR & 0x7F);
            z80HeaderV3[12] = (byte)(this.border << 1);
            if (this.regR > 127) {
                final byte[] array = z80HeaderV3;
                final int n = 12;
                array[n] |= 0x1;
            }
            z80HeaderV3[13] = (byte)this.regDE;
            z80HeaderV3[14] = (byte)(this.regDE >>> 8);
            z80HeaderV3[15] = (byte)this.regBCalt;
            z80HeaderV3[16] = (byte)(this.regBCalt >>> 8);
            z80HeaderV3[17] = (byte)this.regDEalt;
            z80HeaderV3[18] = (byte)(this.regDEalt >>> 8);
            z80HeaderV3[19] = (byte)this.regHLalt;
            z80HeaderV3[20] = (byte)(this.regHLalt >>> 8);
            z80HeaderV3[21] = (byte)(this.regAF >>> 8);
            z80HeaderV3[22] = (byte)this.regAF;
            z80HeaderV3[23] = (byte)this.regIY;
            z80HeaderV3[24] = (byte)(this.regIY >>> 8);
            z80HeaderV3[25] = (byte)this.regIX;
            z80HeaderV3[26] = (byte)(this.regIX >>> 8);
            z80HeaderV3[27] = (byte)(this.iff1 ? 1 : 0);
            z80HeaderV3[28] = (byte)(this.iff2 ? 1 : 0);
            z80HeaderV3[29] = (byte)this.modeIM;
            if (!this.issue2) {
                final byte[] array2 = z80HeaderV3;
                final int n2 = 29;
                array2[n2] |= 0x4;
            }
            switch (this.joystick) {
                case KEMPSTON: {
                    final byte[] array3 = z80HeaderV3;
                    final int n3 = 29;
                    array3[n3] |= 0x40;
                    break;
                }
                case SINCLAIR1: {
                    final byte[] array4 = z80HeaderV3;
                    final int n4 = 29;
                    array4[n4] |= (byte)128;
                    break;
                }
                case SINCLAIR2: {
                    final byte[] array5 = z80HeaderV3;
                    final int n5 = 29;
                    array5[n5] |= (byte)192;
                    break;
                }
            }
            z80HeaderV3[30] = 55;
            z80HeaderV3[32] = (byte)this.regPC;
            z80HeaderV3[33] = (byte)(this.regPC >>> 8);
            switch (this.snapshotModel) {
                case SPECTRUM16K: {
                    final byte[] array6 = z80HeaderV3;
                    final int n6 = 37;
                    array6[n6] |= (byte)128;
                }
                case SPECTRUM128K: {
                    z80HeaderV3[34] = 4;
                    break;
                }
                case SPECTRUMPLUS2: {
                    z80HeaderV3[34] = 12;
                    break;
                }
                case SPECTRUMPLUS2A: {
                    z80HeaderV3[34] = 13;
                    break;
                }
                case SPECTRUMPLUS3: {
                    z80HeaderV3[34] = 7;
                    break;
                }
            }
            if (this.snapshotModel.codeModel != MachineTypes.CodeModel.SPECTRUM48K) {
                z80HeaderV3[35] = (byte)this.last7ffd;
            }
            if (this.enabledAY) {
                final byte[] array7 = z80HeaderV3;
                final int n7 = 37;
                array7[n7] |= 0x4;
            }
            z80HeaderV3[38] = (byte)this.lastfffd;
            for (int reg = 0; reg < 16; ++reg) {
                z80HeaderV3[39 + reg] = (byte)this.psgRegs[reg];
            }
            if (this.snapshotModel.codeModel == MachineTypes.CodeModel.SPECTRUMPLUS3) {
                z80HeaderV3[54] = (byte)this.last1ffd;
            }
            this.fOut.write(z80HeaderV3, 0, z80HeaderV3.length);
            final byte[] buffer = new byte[16384];
            if (this.snapshotModel.codeModel == MachineTypes.CodeModel.SPECTRUM48K) {
                int bufLen = this.compressPageZ80(memory, buffer, 5);
                if (bufLen == 16384) {
                    this.fOut.write(255);
                    this.fOut.write(255);
                }
                else {
                    this.fOut.write(bufLen);
                    this.fOut.write(bufLen >>> 8);
                }
                this.fOut.write(8);
                this.fOut.write(buffer, 0, bufLen);
                bufLen = this.compressPageZ80(memory, buffer, 2);
                if (bufLen == 16384) {
                    this.fOut.write(255);
                    this.fOut.write(255);
                }
                else {
                    this.fOut.write(bufLen);
                    this.fOut.write(bufLen >>> 8);
                }
                this.fOut.write(4);
                this.fOut.write(buffer, 0, bufLen);
                bufLen = this.compressPageZ80(memory, buffer, 0);
                if (bufLen == 16384) {
                    this.fOut.write(255);
                    this.fOut.write(255);
                }
                else {
                    this.fOut.write(bufLen);
                    this.fOut.write(bufLen >>> 8);
                }
                this.fOut.write(5);
                this.fOut.write(buffer, 0, bufLen);
            }
            else {
                for (int page = 0; page < 8; ++page) {
                    final int bufLen = this.compressPageZ80(memory, buffer, page);
                    if (bufLen == 16384) {
                        this.fOut.write(255);
                        this.fOut.write(255);
                    }
                    else {
                        this.fOut.write(bufLen);
                        this.fOut.write(bufLen >>> 8);
                    }
                    this.fOut.write(page + 3);
                    this.fOut.write(buffer, 0, bufLen);
                }
            }
            this.fOut.close();
        }
        catch (IOException ex2) {
            this.error = 6;
            return false;
        }
        return true;
    }
    
    private int dwMagicToInt(final byte[] dwMagic) {
        final int value0 = dwMagic[0] & 0xFF;
        final int value2 = dwMagic[1] & 0xFF;
        final int value3 = dwMagic[2] & 0xFF;
        final int value4 = dwMagic[3] & 0xFF;
        return value4 << 24 | value3 << 16 | value2 << 8 | value0;
    }
    
    private boolean loadSZX(final File filename, final Memory memory) {
        final byte[] dwMagic = new byte[4];
        final byte[] dwSize = new byte[4];
        int addr = 0;
        this.joystick = Spectrum.Joystick.NONE;
        try {
            try {
                this.fIn = new BufferedInputStream(new FileInputStream(filename));
            }
            catch (FileNotFoundException ex) {
                this.error = 2;
                return false;
            }
            int readed = this.fIn.read(dwMagic);
            if (this.dwMagicToInt(dwMagic) != 1414748250) {
                this.error = 1;
                this.fIn.close();
                return false;
            }
            readed = this.fIn.read(dwSize);
            switch (dwSize[2] & 0xFF) {
                case 0: {
                    this.snapshotModel = MachineTypes.SPECTRUM16K;
                    break;
                }
                case 1: {
                    this.snapshotModel = MachineTypes.SPECTRUM48K;
                    break;
                }
                case 2: {
                    this.snapshotModel = MachineTypes.SPECTRUM128K;
                    break;
                }
                case 3: {
                    this.snapshotModel = MachineTypes.SPECTRUMPLUS2;
                    break;
                }
                case 4: {
                    this.snapshotModel = MachineTypes.SPECTRUMPLUS2A;
                    break;
                }
                case 5: {
                    this.snapshotModel = MachineTypes.SPECTRUMPLUS3;
                    break;
                }
                default: {
                    this.error = 5;
                    this.fIn.close();
                    return false;
                }
            }
            while (this.fIn.available() > 0) {
                readed = this.fIn.read(dwMagic);
                readed = this.fIn.read(dwSize);
                final int szxId = this.dwMagicToInt(dwMagic);
                int szxLen = this.dwMagicToInt(dwSize);
                if (szxLen < 1) {
                    this.error = 4;
                    this.fIn.close();
                    return false;
                }
                switch (szxId) {
                    case 1381257795: {
                        final byte[] szCreator = new byte[32];
                        readed = this.fIn.read(szCreator);
                        final int majorVersion = this.fIn.read() + this.fIn.read() * 256;
                        final int minorVersion = this.fIn.read() + this.fIn.read() * 256;
                        szxLen -= 36;
                        if (szxLen > 0) {
                            final byte[] chData = new byte[szxLen];
                            readed = this.fIn.read(chData);
                            continue;
                        }
                        continue;
                    }
                    case 1378891866: {
                        if (szxLen != 37) {
                            this.error = 4;
                            this.fIn.close();
                            return false;
                        }
                        final byte[] z80Regs = new byte[szxLen];
                        readed = this.fIn.read(z80Regs);
                        this.regAF = ((z80Regs[0] & 0xFF) | (z80Regs[1] << 8 & 0xFFFF));
                        this.regBC = ((z80Regs[2] & 0xFF) | (z80Regs[3] << 8 & 0xFFFF));
                        this.regDE = ((z80Regs[4] & 0xFF) | (z80Regs[5] << 8 & 0xFFFF));
                        this.regHL = ((z80Regs[6] & 0xFF) | (z80Regs[7] << 8 & 0xFFFF));
                        this.regAFalt = ((z80Regs[8] & 0xFF) | (z80Regs[9] << 8 & 0xFFFF));
                        this.regBCalt = ((z80Regs[10] & 0xFF) | (z80Regs[11] << 8 & 0xFFFF));
                        this.regDEalt = ((z80Regs[12] & 0xFF) | (z80Regs[13] << 8 & 0xFFFF));
                        this.regHLalt = ((z80Regs[14] & 0xFF) | (z80Regs[15] << 8 & 0xFFFF));
                        this.regIX = ((z80Regs[16] & 0xFF) | (z80Regs[17] << 8 & 0xFFFF));
                        this.regIY = ((z80Regs[18] & 0xFF) | (z80Regs[19] << 8 & 0xFFFF));
                        this.regSP = ((z80Regs[20] & 0xFF) | (z80Regs[21] << 8 & 0xFFFF));
                        this.regPC = ((z80Regs[22] & 0xFF) | (z80Regs[23] << 8 & 0xFFFF));
                        this.regI = (z80Regs[24] & 0xFF);
                        this.regR = (z80Regs[25] & 0xFF);
                        this.iff1 = ((z80Regs[26] & 0xFF) != 0x0);
                        this.iff2 = ((z80Regs[27] & 0xFF) != 0x0);
                        this.modeIM = (z80Regs[28] & 0xFF);
                        this.tstates = ((z80Regs[32] & 0xFF) << 24 | (z80Regs[31] & 0xFF) << 16 | (z80Regs[30] & 0xFF) << 8 | (z80Regs[29] & 0xFF));
                        continue;
                    }
                    case 1380143187: {
                        if (szxLen != 8) {
                            this.error = 4;
                            this.fIn.close();
                            return false;
                        }
                        final byte[] specRegs = new byte[szxLen];
                        readed = this.fIn.read(specRegs);
                        this.border = (specRegs[0] & 0x7);
                        this.last7ffd = (specRegs[1] & 0xFF);
                        this.last1ffd = (specRegs[2] & 0xFF);
                        continue;
                    }
                    case 1113146699: {
                        if (szxLen != 5) {
                            this.error = 4;
                            this.fIn.close();
                            return false;
                        }
                        final byte[] keyb = new byte[szxLen];
                        readed = this.fIn.read(keyb);
                        this.issue2 = ((keyb[0] & 0x1) != 0x0);
                        switch (keyb[4] & 0xFF) {
                            case 0: {
                                this.joystick = Spectrum.Joystick.KEMPSTON;
                                continue;
                            }
                            case 1: {
                                this.joystick = Spectrum.Joystick.FULLER;
                                continue;
                            }
                            case 2: {
                                this.joystick = Spectrum.Joystick.CURSOR;
                                continue;
                            }
                            case 3: {
                                this.joystick = Spectrum.Joystick.SINCLAIR1;
                                continue;
                            }
                            case 4: {
                                this.joystick = Spectrum.Joystick.SINCLAIR2;
                                continue;
                            }
                            default: {
                                this.joystick = Spectrum.Joystick.NONE;
                                continue;
                            }
                        }
                        break;
                    }
                    case 22849: {
                        if (szxLen != 18) {
                            this.error = 4;
                            this.fIn.close();
                            return false;
                        }
                        final byte[] ayRegs = new byte[szxLen];
                        readed = this.fIn.read(ayRegs);
                        this.enabledAY = true;
                        if (this.snapshotModel.codeModel == MachineTypes.CodeModel.SPECTRUM48K && ayRegs[0] != 2) {
                            this.enabledAY = false;
                        }
                        this.lastfffd = (ayRegs[1] & 0xFF);
                        for (int idx = 0; idx < 16; ++idx) {
                            this.psgRegs[idx] = (ayRegs[2 + idx] & 0xFF);
                        }
                        continue;
                    }
                    case 1347240274: {
                        final byte[] ramPage = new byte[3];
                        readed = this.fIn.read(ramPage);
                        szxLen -= 3;
                        if (szxLen > 16384) {
                            this.error = 10;
                            this.fIn.close();
                            return false;
                        }
                        final byte[] chData = new byte[szxLen];
                        readed = this.fIn.read(chData);
                        if ((ramPage[0] & 0x1) == 0x0) {
                            memory.loadPage(ramPage[2] & 0xFF, chData);
                            continue;
                        }
                        final ByteArrayInputStream bais = new ByteArrayInputStream(chData);
                        final InflaterInputStream iis = new InflaterInputStream(bais);
                        addr = 0;
                        while (addr < 16384) {
                            final int value = iis.read();
                            if (value == -1) {
                                break;
                            }
                            memory.writeByte(ramPage[2] & 0xFF, addr++, (byte)value);
                        }
                        readed = iis.read();
                        iis.close();
                        if (addr != 16384 || readed != -1) {
                            this.error = 10;
                            this.fIn.close();
                            return false;
                        }
                        continue;
                    }
                    case 1162036813: {
                        final byte[] mf = new byte[2];
                        readed = this.fIn.read(mf);
                        szxLen -= 2;
                        if (szxLen > 16384) {
                            this.fIn.skip(szxLen);
                            continue;
                        }
                        final byte[] chData = new byte[szxLen];
                        readed = this.fIn.read(chData);
                        if ((mf[1] & 0x20) != 0x0) {
                            this.multiface = false;
                            continue;
                        }
                        this.multiface = true;
                        if ((mf[0] & 0x1) != 0x0) {
                            this.mf128on48k = true;
                        }
                        if ((mf[1] & 0x1) != 0x0) {
                            this.mfPagedIn = true;
                        }
                        if ((mf[1] & 0x4) != 0x0) {
                            this.mfLockout = true;
                        }
                        if ((mf[1] & 0x2) == 0x0) {
                            memory.loadMFRam(chData);
                            continue;
                        }
                        final ByteArrayInputStream bais = new ByteArrayInputStream(chData);
                        final InflaterInputStream iis = new InflaterInputStream(bais);
                        byte[] mfRAM;
                        int value2;
                        for (mfRAM = new byte[8192], addr = 0; addr < mfRAM.length; mfRAM[addr++] = (byte)value2) {
                            value2 = iis.read();
                            if (value2 == -1) {
                                break;
                            }
                        }
                        readed = iis.read();
                        iis.close();
                        if (addr != mfRAM.length || readed != -1) {
                            System.out.println("Multiface RAM uncompress error!");
                            this.multiface = false;
                            continue;
                        }
                        memory.loadMFRam(mfRAM);
                        continue;
                    }
                    case 1414810704: {
                        if (szxLen != 66) {
                            this.error = 4;
                            this.fIn.close();
                            return false;
                        }
                        this.ULAplus = true;
                        final byte[] ULAplusRegs = new byte[szxLen];
                        readed = this.fIn.read(ULAplusRegs);
                        if (ULAplusRegs[0] == 1) {
                            this.ULAplusEnabled = true;
                        }
                        this.ULAplusRegister = (ULAplusRegs[1] & 0xFF);
                        for (int reg = 0; reg < 64; ++reg) {
                            this.ULAplusPalette[reg] = (ULAplusRegs[2 + reg] & 0xFF);
                        }
                        continue;
                    }
                    case 1162887508: {
                        final byte[] tape = new byte[4];
                        readed = this.fIn.read(tape);
                        szxLen -= tape.length;
                        final byte[] qword = new byte[4];
                        readed = this.fIn.read(qword);
                        szxLen -= qword.length;
                        final int uSize = this.dwMagicToInt(qword);
                        readed = this.fIn.read(qword);
                        szxLen -= qword.length;
                        final int cSize = this.dwMagicToInt(qword);
                        final byte[] szFileExtension = new byte[16];
                        readed = this.fIn.read(szFileExtension);
                        szxLen -= szFileExtension.length;
                        if ((tape[2] & 0x1) != 0x0) {
                            int nChars;
                            for (nChars = 0; nChars < szFileExtension.length && szFileExtension[nChars] != 0; ++nChars) {}
                            this.tapeExtension = new String(szFileExtension, 0, nChars);
                            this.tapeName = filename.getName();
                            final byte[] chData = new byte[szxLen];
                            readed = this.fIn.read(chData);
                            if ((tape[2] & 0x2) != 0x0) {
                                final ByteArrayInputStream bais = new ByteArrayInputStream(chData);
                                final InflaterInputStream iis = new InflaterInputStream(bais);
                                this.tapeData = new byte[uSize];
                                int value3;
                                for (addr = 0; addr < uSize; this.tapeData[addr++] = (byte)value3) {
                                    value3 = iis.read();
                                    if (value3 == -1) {
                                        break;
                                    }
                                }
                                readed = iis.read();
                                iis.close();
                                if (addr != uSize || readed != -1) {
                                    System.out.println("Tape uncompress error!");
                                    continue;
                                }
                            }
                            else {
                                this.tapeData = chData;
                            }
                            this.tapeEmbedded = true;
                            continue;
                        }
                        final byte[] chData = new byte[szxLen];
                        readed = this.fIn.read(chData);
                        this.tapeName = new String(chData, 0, szxLen - 1);
                        this.tapeLinked = true;
                        continue;
                    }
                    case 1379026505: {
                        final byte[] dwCartSize = new byte[4];
                        readed = this.fIn.read(dwCartSize);
                        final int romLen = this.dwMagicToInt(dwCartSize);
                        if (romLen > 16384) {
                            this.fIn.skip(romLen);
                            continue;
                        }
                        final byte[] chData = new byte[romLen];
                        readed = this.fIn.read(chData);
                        if (romLen == 16384) {
                            memory.insertIF2RomFromSZX(chData);
                            this.IF2RomPresent = true;
                            continue;
                        }
                        final ByteArrayInputStream bais = new ByteArrayInputStream(chData);
                        final InflaterInputStream iis = new InflaterInputStream(bais);
                        byte[] IF2Rom;
                        int value4;
                        for (IF2Rom = new byte[16384], addr = 0; addr < IF2Rom.length; IF2Rom[addr++] = (byte)value4) {
                            value4 = iis.read();
                            if (value4 == -1) {
                                break;
                            }
                        }
                        readed = iis.read();
                        iis.close();
                        if (addr != IF2Rom.length || readed != -1) {
                            System.out.println("Rom uncompress error!");
                            continue;
                        }
                        memory.insertIF2RomFromSZX(IF2Rom);
                        this.IF2RomPresent = true;
                        continue;
                    }
                    case 5853002: {
                        if (szxLen != 6) {
                            this.error = 4;
                            this.fIn.close();
                            return false;
                        }
                        this.fIn.skip(szxLen);
                        continue;
                    }
                    case 13099:
                    case 21319:
                    case 3229257:
                    case 4936516:
                    case 5066578:
                    case 942813506:
                    case 1145848659:
                    case 1146309712:
                    case 1162103123:
                    case 1162892117:
                    case 1178818650:
                    case 1262702404:
                    case 1263748162:
                    case 1263748176:
                    case 1297437252:
                    case 1297632577:
                    case 1347569219:
                    case 1347572551:
                    case 1347572801:
                    case 1380997210:
                    case 1413568602:
                    case 1448232013:
                    case 1482051395: {
                        this.fIn.skip(szxLen);
                        final String blockID = new String(dwMagic);
                        System.out.println(String.format("SZX block ID '%s' readed but not emulated. Skipping...", blockID));
                        continue;
                    }
                    default: {
                        final String header = new String(dwMagic);
                        System.out.println(String.format("Unknown SZX block ID: %s", header));
                        this.fOut.close();
                        this.error = 4;
                        return false;
                    }
                }
            }
        }
        catch (IOException ex2) {
            this.error = 4;
            return false;
        }
        return true;
    }
    
    public boolean saveSZX(final File filename, final Memory memory) {
        try {
            try {
                this.fOut = new BufferedOutputStream(new FileOutputStream(filename));
            }
            catch (FileNotFoundException ex) {
                this.error = 2;
                return false;
            }
            String blockID = "ZXST";
            this.fOut.write(blockID.getBytes("US-ASCII"));
            this.fOut.write(1);
            this.fOut.write(3);
            switch (this.snapshotModel) {
                case SPECTRUM16K: {
                    this.fOut.write(0);
                    break;
                }
                case SPECTRUM48K: {
                    this.fOut.write(1);
                    break;
                }
                case SPECTRUM128K: {
                    this.fOut.write(2);
                    break;
                }
                case SPECTRUMPLUS2: {
                    this.fOut.write(3);
                    break;
                }
                case SPECTRUMPLUS2A: {
                    this.fOut.write(4);
                    break;
                }
                case SPECTRUMPLUS3: {
                    this.fOut.write(5);
                    break;
                }
            }
            this.fOut.write(1);
            blockID = "CRTR";
            this.fOut.write(blockID.getBytes("US-ASCII"));
            this.fOut.write(37);
            this.fOut.write(0);
            this.fOut.write(0);
            this.fOut.write(0);
            blockID = "JSpeccy 0.87";
            final byte[] szCreator = new byte[32];
            System.arraycopy(blockID.getBytes("US-ASCII"), 0, szCreator, 0, blockID.getBytes("US-ASCII").length);
            this.fOut.write(szCreator);
            this.fOut.write(0);
            this.fOut.write(0);
            this.fOut.write(0);
            this.fOut.write(87);
            this.fOut.write(0);
            blockID = "Z80R";
            this.fOut.write(blockID.getBytes("US-ASCII"));
            this.fOut.write(37);
            this.fOut.write(0);
            this.fOut.write(0);
            this.fOut.write(0);
            final byte[] z80r = { (byte)this.regAF, (byte)(this.regAF >> 8), (byte)this.regBC, (byte)(this.regBC >> 8), (byte)this.regDE, (byte)(this.regDE >> 8), (byte)this.regHL, (byte)(this.regHL >> 8), (byte)this.regAFalt, (byte)(this.regAFalt >> 8), (byte)this.regBCalt, (byte)(this.regBCalt >> 8), (byte)this.regDEalt, (byte)(this.regDEalt >> 8), (byte)this.regHLalt, (byte)(this.regHLalt >> 8), (byte)this.regIX, (byte)(this.regIX >> 8), (byte)this.regIY, (byte)(this.regIY >> 8), (byte)this.regSP, (byte)(this.regSP >> 8), (byte)this.regPC, (byte)(this.regPC >> 8), (byte)this.regI, (byte)this.regR, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            if (this.iff1) {
                z80r[26] = 1;
            }
            if (this.iff2) {
                z80r[27] = 1;
            }
            z80r[28] = (byte)this.modeIM;
            z80r[29] = (byte)this.tstates;
            z80r[30] = (byte)(this.tstates >> 8);
            z80r[31] = (byte)(this.tstates >> 16);
            z80r[32] = (byte)(this.tstates >> 24);
            this.fOut.write(z80r);
            blockID = "SPCR";
            this.fOut.write(blockID.getBytes("US-ASCII"));
            this.fOut.write(8);
            this.fOut.write(0);
            this.fOut.write(0);
            this.fOut.write(0);
            final byte[] specr = new byte[8];
            specr[0] = (byte)this.border;
            specr[1] = (byte)this.last7ffd;
            specr[2] = (byte)this.last1ffd;
            this.fOut.write(specr);
            final boolean[] save = new boolean[8];
            switch (this.snapshotModel) {
                case SPECTRUM16K: {
                    save[5] = true;
                    break;
                }
                case SPECTRUM48K: {
                    final boolean[] array = save;
                    final int n = 0;
                    final boolean[] array2 = save;
                    final int n2 = 2;
                    final boolean[] array3 = save;
                    final int n3 = 5;
                    final boolean b = true;
                    array3[n3] = b;
                    array[n] = (array2[n2] = b);
                    break;
                }
                default: {
                    Arrays.fill(save, true);
                    break;
                }
            }
            final byte[] ram = new byte[16384];
            for (int page = 0; page < 8; ++page) {
                if (save[page]) {
                    memory.savePage(page, ram);
                    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    final DeflaterOutputStream dos = new DeflaterOutputStream(baos);
                    dos.write(ram, 0, ram.length);
                    dos.close();
                    blockID = "RAMP";
                    this.fOut.write(blockID.getBytes("US-ASCII"));
                    final int pageLen = baos.size() + 3;
                    this.fOut.write(pageLen);
                    this.fOut.write(pageLen >> 8);
                    this.fOut.write(pageLen >> 16);
                    this.fOut.write(pageLen >> 24);
                    this.fOut.write(1);
                    this.fOut.write(0);
                    this.fOut.write(page);
                    baos.writeTo(this.fOut);
                }
            }
            blockID = "KEYB";
            this.fOut.write(blockID.getBytes("US-ASCII"));
            this.fOut.write(5);
            this.fOut.write(0);
            this.fOut.write(0);
            this.fOut.write(0);
            if (this.issue2) {
                this.fOut.write(1);
            }
            else {
                this.fOut.write(0);
            }
            this.fOut.write(0);
            this.fOut.write(0);
            this.fOut.write(0);
            switch (this.joystick) {
                case NONE: {
                    this.fOut.write(8);
                    break;
                }
                case KEMPSTON: {
                    this.fOut.write(0);
                    break;
                }
                case SINCLAIR1: {
                    this.fOut.write(3);
                    break;
                }
                case SINCLAIR2: {
                    this.fOut.write(4);
                    break;
                }
                case CURSOR: {
                    this.fOut.write(2);
                    break;
                }
                case FULLER: {
                    this.fOut.write(1);
                    break;
                }
            }
            if (this.enabledAY) {
                final byte[] ayID = { 65, 89, 0, 0 };
                this.fOut.write(ayID);
                this.fOut.write(18);
                this.fOut.write(0);
                this.fOut.write(0);
                this.fOut.write(0);
                if (this.snapshotModel.codeModel == MachineTypes.CodeModel.SPECTRUM48K) {
                    this.fOut.write(2);
                }
                else {
                    this.fOut.write(0);
                }
                this.fOut.write(this.lastfffd);
                for (int reg = 0; reg < 16; ++reg) {
                    this.fOut.write(this.psgRegs[reg]);
                }
            }
            if (this.ULAplus) {
                blockID = "PLTT";
                this.fOut.write(blockID.getBytes("US-ASCII"));
                this.fOut.write(66);
                this.fOut.write(0);
                this.fOut.write(0);
                this.fOut.write(0);
                if (this.ULAplusEnabled) {
                    this.fOut.write(1);
                }
                else {
                    this.fOut.write(0);
                }
                this.fOut.write(this.ULAplusRegister);
                for (int color = 0; color < 64; ++color) {
                    this.fOut.write(this.ULAplusPalette[color]);
                }
            }
            if (this.multiface) {
                blockID = "MFCE";
                this.fOut.write(blockID.getBytes("US-ASCII"));
                final byte[] mfRam = new byte[8192];
                memory.saveMFRam(mfRam);
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                final DeflaterOutputStream dos = new DeflaterOutputStream(baos);
                dos.write(mfRam, 0, mfRam.length);
                dos.close();
                final int pageLen = baos.size() + 2;
                this.fOut.write(pageLen);
                this.fOut.write(pageLen >> 8);
                this.fOut.write(pageLen >> 16);
                this.fOut.write(pageLen >> 24);
                if (this.mf128on48k) {
                    this.fOut.write(1);
                }
                else {
                    this.fOut.write(0);
                }
                int mfFlags = 2;
                if (memory.isMultifacePaged()) {
                    mfFlags |= 0x1;
                }
                if (memory.isMultifaceLocked() && (this.snapshotModel.codeModel != MachineTypes.CodeModel.SPECTRUM48K || this.mf128on48k)) {
                    mfFlags |= 0x4;
                }
                this.fOut.write(mfFlags);
                baos.writeTo(this.fOut);
            }
            if (memory.isIF2RomEnabled()) {
                blockID = "IF2R";
                this.fOut.write(blockID.getBytes("US-ASCII"));
                final byte[] if2Rom = new byte[16384];
                memory.saveIF2Rom(if2Rom);
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                final DeflaterOutputStream dos = new DeflaterOutputStream(baos);
                dos.write(if2Rom, 0, if2Rom.length);
                dos.close();
                int pageLen = baos.size() + 4;
                this.fOut.write(pageLen);
                this.fOut.write(pageLen >> 8);
                this.fOut.write(pageLen >> 16);
                this.fOut.write(pageLen >> 24);
                pageLen = baos.size();
                this.fOut.write(pageLen);
                this.fOut.write(pageLen >> 8);
                this.fOut.write(pageLen >> 16);
                this.fOut.write(pageLen >> 24);
                baos.writeTo(this.fOut);
            }
            if (this.tapeLinked && !this.tapeEmbedded) {
                blockID = "TAPE";
                this.fOut.write(blockID.getBytes("US-ASCII"));
                int blockLen = 28 + this.tapeName.length() + 1;
                this.fOut.write(blockLen);
                this.fOut.write(blockLen >> 8);
                this.fOut.write(blockLen >> 16);
                this.fOut.write(blockLen >> 24);
                this.fOut.write(this.tapeBlock);
                this.fOut.write(this.tapeBlock >> 8);
                this.fOut.write(0);
                this.fOut.write(0);
                this.fOut.write(0);
                this.fOut.write(0);
                this.fOut.write(0);
                this.fOut.write(0);
                blockLen = this.tapeName.length() + 1;
                this.fOut.write(blockLen);
                this.fOut.write(blockLen >> 8);
                this.fOut.write(blockLen >> 16);
                this.fOut.write(blockLen >> 24);
                final byte[] szFileExtension = new byte[16];
                this.fOut.write(szFileExtension);
                this.fOut.write(this.tapeName.getBytes("US-ASCII"));
                this.fOut.write(0);
            }
            if (!this.tapeLinked && this.tapeEmbedded) {
                blockID = "TAPE";
                this.fOut.write(blockID.getBytes("US-ASCII"));
                final File tapeFile = new File(this.tapeName);
                this.fIn = new BufferedInputStream(new FileInputStream(tapeFile));
                this.tapeData = new byte[this.fIn.available()];
                this.fIn.read(this.tapeData);
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                final DeflaterOutputStream dos = new DeflaterOutputStream(baos);
                dos.write(this.tapeData, 0, this.tapeData.length);
                dos.close();
                int blockLen2 = 28 + baos.size();
                this.fOut.write(blockLen2);
                this.fOut.write(blockLen2 >> 8);
                this.fOut.write(blockLen2 >> 16);
                this.fOut.write(blockLen2 >> 24);
                this.fOut.write(this.tapeBlock);
                this.fOut.write(this.tapeBlock >> 8);
                this.fOut.write(3);
                this.fOut.write(0);
                this.fOut.write(this.tapeData.length);
                this.fOut.write(this.tapeData.length >> 8);
                this.fOut.write(this.tapeData.length >> 16);
                this.fOut.write(this.tapeData.length >> 24);
                blockLen2 = baos.size();
                this.fOut.write(blockLen2);
                this.fOut.write(blockLen2 >> 8);
                this.fOut.write(blockLen2 >> 16);
                this.fOut.write(blockLen2 >> 24);
                final byte[] szFileExtension2 = new byte[16];
                szFileExtension2[0] = 116;
                if (this.tapeName.toLowerCase().endsWith("tzx")) {
                    szFileExtension2[1] = 122;
                    szFileExtension2[2] = 120;
                }
                else {
                    szFileExtension2[1] = 97;
                    szFileExtension2[2] = 112;
                }
                this.fOut.write(szFileExtension2);
                baos.writeTo(this.fOut);
            }
            this.fOut.close();
        }
        catch (IOException ex2) {
            this.error = 6;
            return false;
        }
        return true;
    }
}
