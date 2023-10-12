// 
// Decompiled by Procyon v0.5.36
// 

package utilities;

import javax.swing.SwingUtilities;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import machine.Memory;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ResourceBundle;
import java.util.Arrays;
import javax.swing.JLabel;
import configuration.TapeType;
import javax.swing.ListSelectionModel;
import machine.MachineTypes;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import z80core.Z80;

public class Tape
{
    private Z80 cpu;
    private FileInputStream tapeFile;
    private ByteArrayOutputStream record;
    private File filename;
    private String filenameLabel;
    private int[] tapeBuffer;
    private int[] offsetBlocks;
    private int nOffsetBlocks;
    private int idxHeader;
    private int tapePos;
    private int blockLen;
    private int mask;
    private int bitTime;
    private byte byteTmp;
    private boolean pulse;
    private State statePlay;
    private int earBit;
    private long timeout;
    private long timeLastIn;
    private boolean tapeInserted;
    private boolean tapePlaying;
    private boolean tapeRecording;
    private boolean tzxTape;
    private boolean flashload;
    private final int LEADER_LENGHT = 2168;
    private final int SYNC1_LENGHT = 667;
    private final int SYNC2_LENGHT = 735;
    private final int ZERO_LENGHT = 855;
    private final int ONE_LENGHT = 1710;
    private final int HEADER_PULSES = 8063;
    private final int DATA_PULSES = 3223;
    private final int END_BLOCK_PAUSE = 3500000;
    private int leaderLenght;
    private int leaderPulses;
    private int sync1Lenght;
    private int sync2Lenght;
    private int zeroLenght;
    private int oneLenght;
    private int bitsLastByte;
    private int endBlockPause;
    private int nLoops;
    private int loopStart;
    private final TapeNotify tapeNotify;
    private MachineTypes spectrumModel;
    private TapeTableModel tapeTableModel;
    private ListSelectionModel lsm;
    private TapeType settings;
    private JLabel tapeIcon;
    private boolean enabledIcon;
    
    public Tape(final TapeType tapeSettings, final Z80 z80, final TapeNotify notifyObject) {
        this.offsetBlocks = new int[4096];
        this.settings = tapeSettings;
        this.tapeNotify = notifyObject;
        this.cpu = z80;
        this.statePlay = State.STOP;
        final boolean tapeInserted = false;
        this.tapeRecording = tapeInserted;
        this.tapePlaying = tapeInserted;
        this.tapeInserted = tapeInserted;
        final boolean b = false;
        this.tzxTape = b;
        this.pulse = b;
        this.flashload = this.settings.isFlashload();
        this.tapePos = 0;
        final long n = 0L;
        this.timeLastIn = n;
        this.timeout = n;
        this.earBit = 191;
        this.spectrumModel = MachineTypes.SPECTRUM48K;
        this.filenameLabel = null;
        this.nOffsetBlocks = 0;
        this.idxHeader = 0;
        Arrays.fill(this.offsetBlocks, 0);
        this.tapeTableModel = new TapeTableModel(this);
    }
    
    public void setSpectrumModel(final MachineTypes model) {
        this.spectrumModel = model;
    }
    
    public void setListSelectionModel(final ListSelectionModel list) {
        this.lsm = list;
    }
    
    public int getNumBlocks() {
        if (!this.tapeInserted) {
            return 1;
        }
        return this.nOffsetBlocks;
    }
    
    public int getSelectedBlock() {
        return this.idxHeader;
    }
    
    public void setSelectedBlock(final int block) {
        if (!this.tapeInserted || this.isTapePlaying() || block > this.nOffsetBlocks) {
            return;
        }
        this.idxHeader = block;
        this.flashload = this.settings.isFlashload();
    }
    
    public String getCleanMsg(final int offset, final int len) {
        final byte[] msg = new byte[len];
        for (int car = 0; car < len; ++car) {
            if (this.tapeBuffer[offset + car] > 31 && this.tapeBuffer[offset + car] < 128) {
                msg[car] = (byte)this.tapeBuffer[offset + car];
            }
            else {
                msg[car] = 63;
            }
        }
        return new String(msg);
    }
    
    public String getBlockType(final int block) {
        final ResourceBundle bundle = ResourceBundle.getBundle("utilities/Bundle");
        if (!this.tapeInserted) {
            return bundle.getString("NO_TAPE_INSERTED");
        }
        if (!this.tzxTape) {
            return bundle.getString("STD_SPD_DATA");
        }
        final int offset = this.offsetBlocks[block];
        if (this.isTZXHeader(offset)) {
            return "ZXTape!";
        }
        String msg = null;
        switch (this.tapeBuffer[offset]) {
            case 16: {
                msg = bundle.getString("STD_SPD_DATA");
                break;
            }
            case 17: {
                msg = bundle.getString("TURBO_SPD_DATA");
                break;
            }
            case 18: {
                msg = bundle.getString("PURE_TONE");
                break;
            }
            case 19: {
                msg = bundle.getString("PULSE_SEQUENCE");
                break;
            }
            case 20: {
                msg = bundle.getString("PURE_DATA");
                break;
            }
            case 21: {
                msg = bundle.getString("DIRECT_DATA");
                break;
            }
            case 24: {
                msg = bundle.getString("CSW_RECORDING");
                break;
            }
            case 25: {
                msg = bundle.getString("GDB_DATA");
                break;
            }
            case 32: {
                msg = bundle.getString("PAUSE_STOP");
                break;
            }
            case 33: {
                msg = bundle.getString("GROUP_START");
                break;
            }
            case 34: {
                msg = bundle.getString("GROUP_STOP");
                break;
            }
            case 35: {
                msg = bundle.getString("JUMP_TO");
                break;
            }
            case 36: {
                msg = bundle.getString("LOOP_START");
                break;
            }
            case 37: {
                msg = bundle.getString("LOOP_STOP");
                break;
            }
            case 38: {
                msg = bundle.getString("CALL_SEQ");
                break;
            }
            case 39: {
                msg = bundle.getString("RETURN_SEQ");
                break;
            }
            case 40: {
                msg = bundle.getString("SELECT_BLOCK");
                break;
            }
            case 42: {
                msg = bundle.getString("STOP_48K_MODE");
                break;
            }
            case 43: {
                msg = bundle.getString("SET_SIGNAL_LEVEL");
                break;
            }
            case 48: {
                msg = bundle.getString("TEXT_DESC");
                break;
            }
            case 49: {
                msg = bundle.getString("MESSAGE_BLOCK");
                break;
            }
            case 50: {
                msg = bundle.getString("ARCHIVE_INFO");
                break;
            }
            case 51: {
                msg = bundle.getString("HARDWARE_TYPE");
                break;
            }
            case 53: {
                msg = bundle.getString("CUSTOM_INFO");
                break;
            }
            case 90: {
                msg = bundle.getString("GLUE_BLOCK");
                break;
            }
            default: {
                msg = String.format(bundle.getString("UNKN_TZX_BLOCK"), this.tapeBuffer[offset]);
                break;
            }
        }
        return msg;
    }
    
    public String getBlockInfo(final int block) {
        final ResourceBundle bundle = ResourceBundle.getBundle("utilities/Bundle");
        if (!this.tapeInserted) {
            return bundle.getString("NO_TAPE_INSERTED");
        }
        String msg = null;
        if (!this.tzxTape) {
            final int offset = this.offsetBlocks[block];
            final int len = this.tapeBuffer[offset] + (this.tapeBuffer[offset + 1] << 8);
            if (this.tapeBuffer[offset + 2] == 0) {
                switch (this.tapeBuffer[offset + 3]) {
                    case 0: {
                        msg = String.format(bundle.getString("PROGRAM_HEADER"), this.getCleanMsg(offset + 4, 10));
                        break;
                    }
                    case 1: {
                        msg = bundle.getString("NUMBER_ARRAY_HEADER");
                        break;
                    }
                    case 2: {
                        msg = bundle.getString("CHAR_ARRAY_HEADER");
                        break;
                    }
                    case 3: {
                        msg = String.format(bundle.getString("BYTES_HEADER"), this.getCleanMsg(offset + 4, 10));
                        break;
                    }
                    default: {
                        msg = "";
                        break;
                    }
                }
            }
            else {
                msg = String.format(bundle.getString("BYTES_MESSAGE"), len);
            }
            return msg;
        }
        int offset = this.offsetBlocks[block];
        if (this.isTZXHeader(offset)) {
            return bundle.getString("TZX_HEADER");
        }
        Label_1657: {
            switch (this.tapeBuffer[offset++]) {
                case 16: {
                    final int len = this.tapeBuffer[offset + 2] + (this.tapeBuffer[offset + 3] << 8);
                    if (this.tapeBuffer[offset + 4] != 0) {
                        msg = String.format(bundle.getString("BYTES_MESSAGE"), len);
                        break;
                    }
                    switch (this.tapeBuffer[offset + 5]) {
                        case 0: {
                            msg = String.format(bundle.getString("PROGRAM_HEADER"), this.getCleanMsg(offset + 6, 10));
                            break Label_1657;
                        }
                        case 1: {
                            msg = bundle.getString("NUMBER_ARRAY_HEADER");
                            break Label_1657;
                        }
                        case 2: {
                            msg = bundle.getString("CHAR_ARRAY_HEADER");
                            break Label_1657;
                        }
                        case 3: {
                            msg = String.format(bundle.getString("BYTES_HEADER"), this.getCleanMsg(offset + 6, 10));
                            break Label_1657;
                        }
                        default: {
                            msg = String.format(bundle.getString("UNKN_HEADER_ID"), this.tapeBuffer[offset + 5]);
                            break Label_1657;
                        }
                    }
                    break;
                }
                case 17: {
                    final int len = this.tapeBuffer[offset + 15] + (this.tapeBuffer[offset + 16] << 8) + (this.tapeBuffer[offset + 17] << 16);
                    msg = String.format(bundle.getString("BYTES_MESSAGE"), len);
                    break;
                }
                case 18: {
                    final int len = this.tapeBuffer[offset] + (this.tapeBuffer[offset + 1] << 8);
                    final int num = this.tapeBuffer[offset + 2] + (this.tapeBuffer[offset + 3] << 8);
                    msg = String.format(bundle.getString("PURE_TONE_MESSAGE"), num, len);
                    break;
                }
                case 19: {
                    final int len = this.tapeBuffer[offset];
                    msg = String.format(bundle.getString("PULSE_SEQ_MESSAGE"), len);
                    break;
                }
                case 20: {
                    final int len = this.tapeBuffer[offset + 7] + (this.tapeBuffer[offset + 8] << 8) + (this.tapeBuffer[offset + 9] << 16);
                    msg = String.format(bundle.getString("BYTES_MESSAGE"), len);
                    break;
                }
                case 21: {
                    final int len = this.tapeBuffer[offset + 5] + (this.tapeBuffer[offset + 6] << 8) + (this.tapeBuffer[offset + 7] << 16);
                    msg = String.format(bundle.getString("BYTES_MESSAGE"), len);
                    break;
                }
                case 24: {
                    final int len = this.tapeBuffer[offset] + (this.tapeBuffer[offset + 1] << 8) + (this.tapeBuffer[offset + 2] << 16) + (this.tapeBuffer[offset + 3] << 24);
                    msg = String.format(bundle.getString("BYTES_MESSAGE"), len);
                    break;
                }
                case 25: {
                    final int len = this.tapeBuffer[offset] + (this.tapeBuffer[offset + 1] << 8) + (this.tapeBuffer[offset + 2] << 16) + (this.tapeBuffer[offset + 3] << 24);
                    msg = String.format(bundle.getString("BYTES_MESSAGE"), len);
                    break;
                }
                case 32: {
                    final int len = this.tapeBuffer[offset] + (this.tapeBuffer[offset + 1] << 8);
                    if (len == 0) {
                        msg = bundle.getString("STOP_THE_TAPE");
                        break;
                    }
                    msg = String.format(bundle.getString("PAUSE_MS"), len);
                    break;
                }
                case 33: {
                    final int len = this.tapeBuffer[offset];
                    msg = this.getCleanMsg(offset + 1, len);
                    break;
                }
                case 34: {
                    msg = "";
                    break;
                }
                case 35: {
                    final byte disp = (byte)this.tapeBuffer[offset];
                    msg = String.format(bundle.getString("NUMBER_OF_BLOCKS"), disp);
                    break;
                }
                case 36: {
                    final int len = this.tapeBuffer[offset] + (this.tapeBuffer[offset + 1] << 8);
                    msg = String.format(bundle.getString("NUMBER_OF_ITER"), len);
                    break;
                }
                case 37: {
                    msg = "";
                    break;
                }
                case 38: {
                    final int len = this.tapeBuffer[offset] + (this.tapeBuffer[offset + 1] << 8);
                    msg = String.format(bundle.getString("NUMBER_OF_CALLS"), len);
                    break;
                }
                case 39: {
                    msg = "";
                    break;
                }
                case 40: {
                    final int len = this.tapeBuffer[offset + 2];
                    msg = String.format(bundle.getString("NUMBER_OF_SELS"), len);
                    break;
                }
                case 42: {
                    msg = "";
                    break;
                }
                case 43: {
                    final int len = this.tapeBuffer[offset + 2];
                    msg = String.format(bundle.getString("SIGNAL_TO_LEVEL"), len);
                    break;
                }
                case 48: {
                    final int len = this.tapeBuffer[offset];
                    msg = this.getCleanMsg(offset + 1, len);
                    break;
                }
                case 49: {
                    final int len = this.tapeBuffer[offset + 1];
                    msg = this.getCleanMsg(offset + 2, len);
                    break;
                }
                case 50: {
                    final int len = this.tapeBuffer[offset + 2];
                    msg = String.format(bundle.getString("NUMBER_OF_STRINGS"), len);
                    break;
                }
                case 51: {
                    msg = "";
                    break;
                }
                case 53: {
                    msg = this.getCleanMsg(offset, 10);
                    break;
                }
                case 90: {
                    msg = "";
                    break;
                }
                default: {
                    msg = "";
                    break;
                }
            }
        }
        return msg;
    }
    
    public TapeTableModel getTapeTableModel() {
        return this.tapeTableModel;
    }
    
    public void notifyTstates(final long frames, final int tstates) {
        final long now = frames * this.spectrumModel.tstatesFrame + tstates;
        this.timeout -= now - this.timeLastIn;
        this.timeLastIn = now;
        if (this.timeout > 0L) {
            return;
        }
        this.timeout = 0L;
        if (this.tapeRecording) {
            this.recordPulse();
        }
        if (this.tapePlaying) {
            this.doPlay();
        }
    }
    
    public boolean insert(final File fileName) {
        if (this.tapeInserted) {
            return false;
        }
        try {
            this.tapeFile = new FileInputStream(fileName);
        }
        catch (FileNotFoundException fex) {
            Logger.getLogger(Tape.class.getName()).log(Level.SEVERE, null, fex);
            return false;
        }
        try {
            this.tapeBuffer = new int[this.tapeFile.available()];
            for (int count = 0; count < this.tapeBuffer.length; this.tapeBuffer[count++] = (this.tapeFile.read() & 0xFF)) {}
            this.tapeFile.close();
            this.filename = fileName;
        }
        catch (IOException ex) {
            Logger.getLogger(Tape.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        final int n = 0;
        this.idxHeader = n;
        this.tapePos = n;
        this.tapeInserted = true;
        this.statePlay = State.STOP;
        final long n2 = 0L;
        this.timeLastIn = n2;
        this.timeout = n2;
        final boolean b = false;
        this.tapeRecording = b;
        this.tapePlaying = b;
        this.tzxTape = this.filename.getName().toLowerCase().endsWith(".tzx");
        if (this.tzxTape) {
            if (this.findTZXOffsetBlocks() == -1) {
                this.nOffsetBlocks = 0;
                return false;
            }
        }
        else {
            this.findTAPOffsetBlocks();
        }
        this.tapeTableModel.fireTableDataChanged();
        this.lsm.setSelectionInterval(0, 0);
        this.cpu.setExecDone(false);
        this.filenameLabel = this.filename.getName();
        this.flashload = this.settings.isFlashload();
        this.updateTapeIcon();
        return true;
    }
    
    public boolean insertEmbeddedTape(final String fileName, final String extension, final byte[] tapeData, final int selectedBlock) {
        if (this.tapeInserted) {
            return false;
        }
        this.tapeBuffer = new int[tapeData.length];
        for (int idx = 0; idx < tapeData.length; ++idx) {
            this.tapeBuffer[idx] = (tapeData[idx] & 0xFF);
        }
        this.filename = new File(fileName);
        final int n = 0;
        this.idxHeader = n;
        this.tapePos = n;
        this.tapeInserted = true;
        this.statePlay = State.STOP;
        final long n2 = 0L;
        this.timeLastIn = n2;
        this.timeout = n2;
        final boolean b = false;
        this.tapeRecording = b;
        this.tapePlaying = b;
        this.tzxTape = extension.startsWith("tzx");
        if (this.tzxTape) {
            if (this.findTZXOffsetBlocks() == -1) {
                this.nOffsetBlocks = 0;
                return false;
            }
        }
        else {
            this.findTAPOffsetBlocks();
        }
        this.tapeTableModel.fireTableDataChanged();
        this.lsm.setSelectionInterval(selectedBlock, selectedBlock);
        this.cpu.setExecDone(false);
        this.filenameLabel = this.filename.getName();
        this.flashload = this.settings.isFlashload();
        this.updateTapeIcon();
        return true;
    }
    
    public void eject() {
        this.tapeInserted = false;
        this.tapeBuffer = null;
        this.filenameLabel = null;
        this.statePlay = State.STOP;
        final boolean b = false;
        this.tapeRecording = b;
        this.tapePlaying = b;
        this.updateTapeIcon();
    }
    
    public int getEarBit() {
        return this.earBit;
    }
    
    public void setEarBit(final boolean earValue) {
        this.earBit = (earValue ? 255 : 191);
    }
    
    public boolean isTapePlaying() {
        return this.tapePlaying;
    }
    
    public boolean isTapeRecording() {
        return this.tapeRecording;
    }
    
    public boolean isFlashLoad() {
        return this.flashload;
    }
    
    public void setFlashLoad(final boolean fastmode) {
        this.flashload = fastmode;
    }
    
    public boolean isTapeInserted() {
        return this.tapeInserted;
    }
    
    public boolean isTapeReady() {
        return this.tapeInserted && !this.tapePlaying && !this.tapeRecording;
    }
    
    public boolean isTzxTape() {
        return this.tzxTape;
    }
    
    public File getTapeName() {
        return this.filename;
    }
    
    public boolean play() {
        if (!this.tapeInserted || this.tapePlaying || this.tapeRecording) {
            return false;
        }
        this.tapePlaying = true;
        this.statePlay = State.START;
        this.tapePos = this.offsetBlocks[this.idxHeader];
        this.timeLastIn = 0L;
        this.cpu.setExecDone(true);
        this.updateTapeIcon();
        this.tapeNotify.tapeStart();
        return true;
    }
    
    public void stop() {
        if (!this.tapeInserted || !this.tapePlaying || this.tapeRecording) {
            return;
        }
        this.statePlay = State.STOP;
        if (this.idxHeader == this.nOffsetBlocks) {
            this.idxHeader = 0;
        }
        this.lsm.setSelectionInterval(this.idxHeader, this.idxHeader);
    }
    
    public boolean rewind() {
        if (!this.tapeInserted || this.tapePlaying || this.tapeRecording) {
            return false;
        }
        this.idxHeader = 0;
        this.lsm.setSelectionInterval(this.idxHeader, this.idxHeader);
        this.flashload = this.settings.isFlashload();
        return true;
    }
    
    private boolean doPlay() {
        if (!this.tapeInserted) {
            return false;
        }
        if (this.tzxTape) {
            return this.playTzx();
        }
        return this.playTap();
    }
    
    private int findTAPOffsetBlocks() {
        this.nOffsetBlocks = 0;
        int offset = 0;
        Arrays.fill(this.offsetBlocks, 0);
        while (offset < this.tapeBuffer.length && this.nOffsetBlocks < this.offsetBlocks.length) {
            this.offsetBlocks[this.nOffsetBlocks++] = offset;
            final int len = this.tapeBuffer[offset] + (this.tapeBuffer[offset + 1] << 8);
            offset += len + 2;
        }
        return this.nOffsetBlocks;
    }
    
    private boolean playTap() {
        switch (this.statePlay) {
            case STOP: {
                this.lsm.setSelectionInterval(this.idxHeader, this.idxHeader);
                this.cpu.setExecDone(false);
                this.tapePlaying = false;
                this.updateTapeIcon();
                this.timeLastIn = 0L;
                this.tapeNotify.tapeStop();
                break;
            }
            case START: {
                if (this.idxHeader == this.nOffsetBlocks) {
                    this.idxHeader = 0;
                }
                this.lsm.setSelectionInterval(this.idxHeader, this.idxHeader);
                this.tapePos = this.offsetBlocks[this.idxHeader];
                this.blockLen = this.tapeBuffer[this.tapePos] + (this.tapeBuffer[this.tapePos + 1] << 8);
                this.tapePos += 2;
                this.leaderPulses = ((this.tapeBuffer[this.tapePos] < 128) ? 8063 : 3223);
                this.earBit = ((this.earBit == 191) ? 255 : 191);
                this.timeout = 2168L;
                this.statePlay = State.LEADER;
                break;
            }
            case LEADER: {
                this.earBit = ((this.earBit == 191) ? 255 : 191);
                final int leaderPulses = this.leaderPulses - 1;
                this.leaderPulses = leaderPulses;
                if (leaderPulses > 0) {
                    this.timeout = 2168L;
                    break;
                }
                this.timeout = 667L;
                this.statePlay = State.SYNC;
                break;
            }
            case SYNC: {
                this.earBit = ((this.earBit == 191) ? 255 : 191);
                this.timeout = 735L;
                this.statePlay = State.NEWBYTE;
                break;
            }
            case NEWBYTE: {
                this.mask = 128;
            }
            case NEWBIT: {
                this.earBit = ((this.earBit == 191) ? 255 : 191);
                if ((this.tapeBuffer[this.tapePos] & this.mask) == 0x0) {
                    this.bitTime = 855;
                }
                else {
                    this.bitTime = 1710;
                }
                this.timeout = this.bitTime;
                this.statePlay = State.HALF2;
                break;
            }
            case HALF2: {
                this.earBit = ((this.earBit == 191) ? 255 : 191);
                this.timeout = this.bitTime;
                this.mask >>>= 1;
                if (this.mask != 0) {
                    this.statePlay = State.NEWBIT;
                    break;
                }
                ++this.tapePos;
                if (--this.blockLen > 0) {
                    this.statePlay = State.NEWBYTE;
                    break;
                }
                this.statePlay = State.PAUSE;
                break;
            }
            case PAUSE: {
                this.earBit = ((this.earBit == 191) ? 255 : 191);
                this.timeout = 3500000L;
                this.statePlay = State.PAUSE_STOP;
                break;
            }
            case PAUSE_STOP: {
                if (this.tapePos == this.tapeBuffer.length) {
                    this.statePlay = State.STOP;
                    this.idxHeader = 0;
                    this.timeout = 1L;
                    this.lsm.setSelectionInterval(this.idxHeader, this.idxHeader);
                    break;
                }
                ++this.idxHeader;
                this.statePlay = State.START;
                break;
            }
        }
        return true;
    }
    
    private boolean isTZXHeader(final int offset) {
        return this.tapeBuffer[offset] == 90 && this.tapeBuffer[offset + 1] == 88 && this.tapeBuffer[offset + 2] == 84 && this.tapeBuffer[offset + 3] == 97 && this.tapeBuffer[offset + 4] == 112 && this.tapeBuffer[offset + 5] == 101 && this.tapeBuffer[offset + 6] == 33;
    }
    
    private int findTZXOffsetBlocks() {
        this.nOffsetBlocks = 0;
        int offset = 0;
        Arrays.fill(this.offsetBlocks, 0);
        while (offset < this.tapeBuffer.length && this.nOffsetBlocks < this.offsetBlocks.length) {
            this.offsetBlocks[this.nOffsetBlocks++] = offset;
            if (this.isTZXHeader(offset)) {
                offset += 10;
            }
            else {
                switch (this.tapeBuffer[offset]) {
                    case 16: {
                        final int len = this.tapeBuffer[offset + 3] + (this.tapeBuffer[offset + 4] << 8);
                        offset += len + 5;
                        continue;
                    }
                    case 17: {
                        final int len = this.tapeBuffer[offset + 16] + (this.tapeBuffer[offset + 17] << 8) + (this.tapeBuffer[offset + 18] << 16);
                        offset += len + 19;
                        continue;
                    }
                    case 18: {
                        offset += 5;
                        continue;
                    }
                    case 19: {
                        final int len = this.tapeBuffer[offset + 1];
                        offset += len * 2 + 2;
                        continue;
                    }
                    case 20: {
                        final int len = this.tapeBuffer[offset + 8] + (this.tapeBuffer[offset + 9] << 8) + (this.tapeBuffer[offset + 10] << 16);
                        offset += len + 11;
                        continue;
                    }
                    case 21: {
                        final int len = this.tapeBuffer[offset + 6] + (this.tapeBuffer[offset + 7] << 8) + (this.tapeBuffer[offset + 8] << 16);
                        offset += len + 9;
                        continue;
                    }
                    case 24:
                    case 25: {
                        final int len = this.tapeBuffer[offset + 1] + (this.tapeBuffer[offset + 2] << 8) + (this.tapeBuffer[offset + 3] << 16) + (this.tapeBuffer[offset + 4] << 24);
                        offset += len + 5;
                        continue;
                    }
                    case 32:
                    case 35:
                    case 36: {
                        offset += 3;
                        continue;
                    }
                    case 33: {
                        final int len = this.tapeBuffer[offset + 1];
                        offset += len + 2;
                        continue;
                    }
                    case 34:
                    case 37:
                    case 39: {
                        ++offset;
                        continue;
                    }
                    case 38: {
                        final int len = this.tapeBuffer[offset + 1] + (this.tapeBuffer[offset + 2] << 8);
                        offset += len * 2 + 3;
                        continue;
                    }
                    case 40:
                    case 50: {
                        final int len = this.tapeBuffer[offset + 1] + (this.tapeBuffer[offset + 2] << 8);
                        offset += len + 3;
                        continue;
                    }
                    case 42: {
                        offset += 5;
                        continue;
                    }
                    case 43: {
                        offset += 6;
                        continue;
                    }
                    case 48: {
                        final int len = this.tapeBuffer[offset + 1];
                        offset += len + 2;
                        continue;
                    }
                    case 49: {
                        final int len = this.tapeBuffer[offset + 2];
                        offset += len + 3;
                        continue;
                    }
                    case 51: {
                        final int len = this.tapeBuffer[offset + 1];
                        offset += len * 3 + 2;
                        continue;
                    }
                    case 53: {
                        final int len = this.tapeBuffer[offset + 17] + (this.tapeBuffer[offset + 18] << 8) + (this.tapeBuffer[offset + 19] << 16) + (this.tapeBuffer[offset + 20] << 24);
                        offset += len + 21;
                        continue;
                    }
                    case 90: {
                        offset += 10;
                        continue;
                    }
                    default: {
                        System.out.println(String.format("Block ID: %02x", this.tapeBuffer[this.tapePos]));
                        return -1;
                    }
                }
            }
        }
        return this.nOffsetBlocks;
    }
    
    private boolean playTzx() {
        boolean repeat;
        do {
            repeat = false;
            switch (this.statePlay) {
                case LEADER: {
                    this.earBit = ((this.earBit == 191) ? 255 : 191);
                }
                case LEADER_NOCHG: {
                    final int leaderPulses = this.leaderPulses - 1;
                    this.leaderPulses = leaderPulses;
                    if (leaderPulses != 0) {
                        this.timeout = this.leaderLenght;
                        this.statePlay = State.LEADER;
                        continue;
                    }
                    this.timeout = this.sync1Lenght;
                    this.statePlay = State.SYNC;
                    continue;
                }
                case HALF2: {
                    this.earBit = ((this.earBit == 191) ? 255 : 191);
                    this.timeout = this.bitTime;
                    this.mask >>>= 1;
                    if (this.blockLen == 1 && this.bitsLastByte < 8 && this.mask == 128 >>> this.bitsLastByte) {
                        this.statePlay = State.PAUSE;
                        ++this.tapePos;
                        continue;
                    }
                    if (this.mask != 0) {
                        this.statePlay = State.NEWBIT;
                        continue;
                    }
                    ++this.tapePos;
                    if (--this.blockLen > 0) {
                        this.statePlay = State.NEWBYTE;
                        continue;
                    }
                    this.statePlay = State.PAUSE;
                    continue;
                }
                case PAUSE: {
                    this.earBit = ((this.earBit == 191) ? 255 : 191);
                    this.statePlay = State.TZX_HEADER;
                    if (this.endBlockPause == 0) {
                        repeat = true;
                        continue;
                    }
                    this.timeout = this.endBlockPause;
                    continue;
                }
                case TZX_HEADER: {
                    if (this.idxHeader == this.nOffsetBlocks) {
                        this.statePlay = State.STOP;
                        this.idxHeader = 0;
                        repeat = true;
                        continue;
                    }
                    this.decodeTzxHeader();
                    continue;
                }
                case NEWBYTE_NOCHG: {
                    this.earBit = ((this.earBit == 191) ? 255 : 191);
                }
                case PURE_TONE: {
                    this.earBit = ((this.earBit == 191) ? 255 : 191);
                }
                case PURE_TONE_NOCHG: {
                    if (this.leaderPulses-- != 0) {
                        this.timeout = this.leaderLenght;
                        this.statePlay = State.PURE_TONE;
                        continue;
                    }
                    this.statePlay = State.TZX_HEADER;
                    repeat = true;
                    continue;
                }
                case PULSE_SEQUENCE: {
                    this.earBit = ((this.earBit == 191) ? 255 : 191);
                }
                case PULSE_SEQUENCE_NOCHG: {
                    if (this.leaderPulses-- != 0) {
                        this.timeout = this.tapeBuffer[this.tapePos] + (this.tapeBuffer[this.tapePos + 1] << 8);
                        this.tapePos += 2;
                        this.statePlay = State.PULSE_SEQUENCE;
                        continue;
                    }
                    this.statePlay = State.TZX_HEADER;
                    repeat = true;
                    continue;
                }
                case NEWDR_BYTE: {
                    this.mask = 128;
                }
                case NEWDR_BIT: {
                    if ((this.tapeBuffer[this.tapePos] & this.mask) == 0x0) {
                        this.earBit = 191;
                    }
                    else {
                        this.earBit = 255;
                    }
                    this.timeout = this.zeroLenght;
                    this.mask >>>= 1;
                    if (this.blockLen == 1 && this.bitsLastByte < 8 && this.mask == 128 >>> this.bitsLastByte) {
                        this.statePlay = State.PAUSE;
                        ++this.tapePos;
                        continue;
                    }
                    if (this.mask != 0) {
                        this.statePlay = State.NEWDR_BIT;
                        continue;
                    }
                    ++this.tapePos;
                    if (--this.blockLen > 0) {
                        this.statePlay = State.NEWDR_BYTE;
                        continue;
                    }
                    this.statePlay = State.PAUSE;
                    continue;
                }
                case PAUSE_STOP: {
                    if (this.endBlockPause == 0) {
                        this.statePlay = State.STOP;
                        repeat = true;
                        continue;
                    }
                    this.timeout = this.endBlockPause;
                    this.statePlay = State.TZX_HEADER;
                    continue;
                }
                default: {
                    continue;
                }
                case STOP: {
                    this.cpu.setExecDone(false);
                    this.tapePlaying = false;
                    this.updateTapeIcon();
                    this.tapeNotify.tapeStop();
                    continue;
                }
                case START: {
                    if (this.idxHeader == this.nOffsetBlocks) {
                        this.idxHeader = 0;
                    }
                    this.tapePos = this.offsetBlocks[this.idxHeader];
                    this.statePlay = State.TZX_HEADER;
                    repeat = true;
                    continue;
                }
                case SYNC: {
                    this.earBit = ((this.earBit == 191) ? 255 : 191);
                    this.timeout = this.sync2Lenght;
                    this.statePlay = State.NEWBYTE;
                    continue;
                }
                case NEWBYTE: {
                    this.mask = 128;
                }
                case NEWBIT: {
                    this.earBit = ((this.earBit == 191) ? 255 : 191);
                    if ((this.tapeBuffer[this.tapePos] & this.mask) == 0x0) {
                        this.bitTime = this.zeroLenght;
                    }
                    else {
                        this.bitTime = this.oneLenght;
                    }
                    this.timeout = this.bitTime;
                    this.statePlay = State.HALF2;
                    continue;
                }
            }
        } while (repeat);
        return true;
    }
    
    private void decodeTzxHeader() {
        boolean repeat = true;
        while (repeat) {
            if (this.idxHeader == this.nOffsetBlocks) {
                return;
            }
            this.lsm.setSelectionInterval(this.idxHeader, this.idxHeader);
            this.tapePos = this.offsetBlocks[this.idxHeader];
            if (this.isTZXHeader(this.tapePos)) {
                ++this.idxHeader;
            }
            else {
                switch (this.tapeBuffer[this.tapePos]) {
                    case 16: {
                        this.leaderLenght = 2168;
                        this.sync1Lenght = 667;
                        this.sync2Lenght = 735;
                        this.zeroLenght = 855;
                        this.oneLenght = 1710;
                        this.bitsLastByte = 8;
                        this.endBlockPause = (this.tapeBuffer[this.tapePos + 1] + (this.tapeBuffer[this.tapePos + 2] << 8)) * 3500;
                        if (this.endBlockPause == 0) {
                            this.endBlockPause = 3500000;
                        }
                        this.blockLen = this.tapeBuffer[this.tapePos + 3] + (this.tapeBuffer[this.tapePos + 4] << 8);
                        this.tapePos += 5;
                        this.leaderPulses = ((this.tapeBuffer[this.tapePos] < 128) ? 8063 : 3223);
                        this.timeout = this.leaderLenght;
                        this.statePlay = State.LEADER_NOCHG;
                        ++this.idxHeader;
                        repeat = false;
                        continue;
                    }
                    case 17: {
                        this.leaderLenght = this.tapeBuffer[this.tapePos + 1] + (this.tapeBuffer[this.tapePos + 2] << 8);
                        this.sync1Lenght = this.tapeBuffer[this.tapePos + 3] + (this.tapeBuffer[this.tapePos + 4] << 8);
                        this.sync2Lenght = this.tapeBuffer[this.tapePos + 5] + (this.tapeBuffer[this.tapePos + 6] << 8);
                        this.zeroLenght = this.tapeBuffer[this.tapePos + 7] + (this.tapeBuffer[this.tapePos + 8] << 8);
                        this.oneLenght = this.tapeBuffer[this.tapePos + 9] + (this.tapeBuffer[this.tapePos + 10] << 8);
                        this.leaderPulses = this.tapeBuffer[this.tapePos + 11] + (this.tapeBuffer[this.tapePos + 12] << 8);
                        this.bitsLastByte = this.tapeBuffer[this.tapePos + 13];
                        this.endBlockPause = (this.tapeBuffer[this.tapePos + 14] + (this.tapeBuffer[this.tapePos + 15] << 8)) * 3500;
                        this.blockLen = this.tapeBuffer[this.tapePos + 16] + (this.tapeBuffer[this.tapePos + 17] << 8) + (this.tapeBuffer[this.tapePos + 18] << 16);
                        this.tapePos += 19;
                        this.timeout = this.leaderLenght;
                        this.statePlay = State.LEADER;
                        ++this.idxHeader;
                        repeat = false;
                        continue;
                    }
                    case 18: {
                        this.leaderLenght = this.tapeBuffer[this.tapePos + 1] + (this.tapeBuffer[this.tapePos + 2] << 8);
                        this.leaderPulses = this.tapeBuffer[this.tapePos + 3] + (this.tapeBuffer[this.tapePos + 4] << 8);
                        this.tapePos += 5;
                        this.statePlay = State.PURE_TONE_NOCHG;
                        ++this.idxHeader;
                        repeat = false;
                        continue;
                    }
                    case 19: {
                        this.leaderPulses = this.tapeBuffer[this.tapePos + 1];
                        this.tapePos += 2;
                        this.statePlay = State.PULSE_SEQUENCE_NOCHG;
                        ++this.idxHeader;
                        repeat = false;
                        continue;
                    }
                    case 20: {
                        this.zeroLenght = this.tapeBuffer[this.tapePos + 1] + (this.tapeBuffer[this.tapePos + 2] << 8);
                        this.oneLenght = this.tapeBuffer[this.tapePos + 3] + (this.tapeBuffer[this.tapePos + 4] << 8);
                        this.bitsLastByte = this.tapeBuffer[this.tapePos + 5];
                        this.endBlockPause = (this.tapeBuffer[this.tapePos + 6] + (this.tapeBuffer[this.tapePos + 7] << 8)) * 3500;
                        this.blockLen = this.tapeBuffer[this.tapePos + 8] + (this.tapeBuffer[this.tapePos + 9] << 8) + (this.tapeBuffer[this.tapePos + 10] << 16);
                        this.tapePos += 11;
                        this.statePlay = State.NEWBYTE_NOCHG;
                        ++this.idxHeader;
                        repeat = false;
                        continue;
                    }
                    case 21: {
                        this.zeroLenght = this.tapeBuffer[this.tapePos + 1] + (this.tapeBuffer[this.tapePos + 2] << 8);
                        this.endBlockPause = (this.tapeBuffer[this.tapePos + 3] + (this.tapeBuffer[this.tapePos + 4] << 8)) * 3500;
                        this.bitsLastByte = this.tapeBuffer[this.tapePos + 5];
                        this.blockLen = this.tapeBuffer[this.tapePos + 6] + (this.tapeBuffer[this.tapePos + 7] << 8) + (this.tapeBuffer[this.tapePos + 8] << 16);
                        this.tapePos += 9;
                        this.statePlay = State.NEWDR_BYTE;
                        ++this.idxHeader;
                        repeat = false;
                        continue;
                    }
                    case 24: {
                        ++this.idxHeader;
                        System.out.println("CSW Block not supported!. Skipping...");
                        continue;
                    }
                    case 25: {
                        ++this.idxHeader;
                        System.out.println("Gen. Data Block not supported!. Skipping...");
                        continue;
                    }
                    case 32: {
                        this.endBlockPause = (this.tapeBuffer[this.tapePos + 1] + (this.tapeBuffer[this.tapePos + 2] << 8)) * 3500;
                        this.tapePos += 3;
                        this.statePlay = State.PAUSE_STOP;
                        ++this.idxHeader;
                        repeat = false;
                        continue;
                    }
                    case 33: {
                        ++this.idxHeader;
                        continue;
                    }
                    case 34: {
                        ++this.idxHeader;
                        continue;
                    }
                    case 35: {
                        final short target = (short)(this.tapeBuffer[this.tapePos + 1] + (this.tapeBuffer[this.tapePos + 2] << 8));
                        this.idxHeader += target;
                        continue;
                    }
                    case 36: {
                        this.nLoops = this.tapeBuffer[this.tapePos + 1] + (this.tapeBuffer[this.tapePos + 2] << 8);
                        this.loopStart = ++this.idxHeader;
                        continue;
                    }
                    case 37: {
                        final int nLoops = this.nLoops - 1;
                        this.nLoops = nLoops;
                        if (nLoops == 0) {
                            ++this.idxHeader;
                            continue;
                        }
                        this.idxHeader = this.loopStart;
                        continue;
                    }
                    case 38: {
                        ++this.idxHeader;
                        continue;
                    }
                    case 39: {
                        ++this.idxHeader;
                        continue;
                    }
                    case 40: {
                        ++this.idxHeader;
                        continue;
                    }
                    case 42: {
                        if (this.spectrumModel.codeModel == MachineTypes.CodeModel.SPECTRUM48K) {
                            this.statePlay = State.STOP;
                            repeat = false;
                        }
                        ++this.idxHeader;
                        continue;
                    }
                    case 43: {
                        this.earBit = ((this.tapeBuffer[this.tapePos + 5] == 0) ? 191 : 255);
                        ++this.idxHeader;
                        continue;
                    }
                    case 48: {
                        ++this.idxHeader;
                        continue;
                    }
                    case 49: {
                        ++this.idxHeader;
                        continue;
                    }
                    case 50: {
                        ++this.idxHeader;
                        continue;
                    }
                    case 51: {
                        ++this.idxHeader;
                        continue;
                    }
                    case 53: {
                        ++this.idxHeader;
                        continue;
                    }
                    case 90: {
                        ++this.idxHeader;
                        continue;
                    }
                    default: {
                        System.out.println(String.format("Block ID: %02x", this.tapeBuffer[this.tapePos]));
                        repeat = false;
                        ++this.idxHeader;
                        continue;
                    }
                }
            }
        }
    }
    
    private void printGDBHeader(final int index) {
        final int blkLenght = this.tapeBuffer[index + 1] + (this.tapeBuffer[index + 2] << 8) + (this.tapeBuffer[index + 3] << 16) + (this.tapeBuffer[index + 4] << 24);
        final int pause = this.tapeBuffer[index + 5] + (this.tapeBuffer[index + 6] << 8);
        final int totp = this.tapeBuffer[index + 7] + (this.tapeBuffer[index + 8] << 8) + (this.tapeBuffer[index + 9] << 16) + (this.tapeBuffer[index + 10] << 24);
        final int npp = this.tapeBuffer[index + 11];
        final int asp = this.tapeBuffer[index + 12];
        final int totd = this.tapeBuffer[index + 13] + (this.tapeBuffer[index + 14] << 8) + (this.tapeBuffer[index + 15] << 16) + (this.tapeBuffer[index + 16] << 24);
        final int npd = this.tapeBuffer[index + 17];
        final int asd = this.tapeBuffer[index + 18];
        System.out.println(String.format("GDB size: %d", blkLenght));
        System.out.println(String.format("Pause: %d ms", pause));
        System.out.println(String.format("TOTP: %d", totp));
        System.out.println(String.format("NPP: %d", npp));
        System.out.println(String.format("ASP: %d", asp));
        System.out.println(String.format("TOTD: %d", totd));
        System.out.println(String.format("NPD: %d", npd));
        System.out.println(String.format("ASD: %d", asd));
    }
    
    public void flashLoad(final Memory memory) {
        if (!this.tapeInserted || this.cpu == null) {
            return;
        }
        if (this.idxHeader == this.nOffsetBlocks) {
            this.flashload = false;
            this.cpu.setCarryFlag(false);
            return;
        }
        if (this.tzxTape) {
            while (this.idxHeader < this.nOffsetBlocks) {
                this.tapePos = this.offsetBlocks[this.idxHeader];
                if (this.tapeBuffer[this.tapePos] == 16) {
                    break;
                }
                if (this.tapeBuffer[this.tapePos] == 17) {
                    break;
                }
                ++this.idxHeader;
            }
            if (this.idxHeader == this.nOffsetBlocks) {
                this.cpu.setCarryFlag(false);
                this.idxHeader = 0;
                this.lsm.setSelectionInterval(this.idxHeader, this.idxHeader);
                return;
            }
            this.lsm.setSelectionInterval(this.idxHeader, this.idxHeader);
            if (this.tapeBuffer[this.tapePos] == 16) {
                this.blockLen = this.tapeBuffer[this.tapePos + 3] + (this.tapeBuffer[this.tapePos + 4] << 8);
                this.tapePos += 5;
            }
            else {
                this.blockLen = this.tapeBuffer[this.tapePos + 16] + (this.tapeBuffer[this.tapePos + 17] << 8) + (this.tapeBuffer[this.tapePos + 18] << 16);
                this.tapePos += 19;
            }
        }
        else {
            this.tapePos = this.offsetBlocks[this.idxHeader];
            this.blockLen = this.tapeBuffer[this.tapePos] + (this.tapeBuffer[this.tapePos + 1] << 8);
            this.tapePos += 2;
        }
        if (this.cpu.getRegA() != this.tapeBuffer[this.tapePos]) {
            this.cpu.xor(this.tapeBuffer[this.tapePos]);
            ++this.idxHeader;
            return;
        }
        this.cpu.setRegA(this.tapeBuffer[this.tapePos]);
        int count = 0;
        int addr = this.cpu.getRegIX();
        int nBytes;
        for (nBytes = this.cpu.getRegDE(); count < nBytes && count < this.blockLen - 1; ++count) {
            memory.writeByte(addr, (byte)this.tapeBuffer[this.tapePos + count + 1]);
            this.cpu.xor(this.tapeBuffer[this.tapePos + count + 1]);
            addr = (addr + 1 & 0xFFFF);
        }
        if (count == nBytes) {
            this.cpu.xor(this.tapeBuffer[this.tapePos + count + 1]);
            this.cpu.cp(1);
        }
        if (count < nBytes) {
            this.cpu.setFlags(80);
        }
        this.cpu.setRegIX(addr);
        this.cpu.setRegDE(nBytes - count);
        ++this.idxHeader;
        this.lsm.setSelectionInterval(this.idxHeader, this.idxHeader);
    }
    
    public void saveTapeBlock(final Memory memory) {
        final int addr = this.cpu.getRegIX();
        final int nBytes = this.cpu.getRegDE();
        BufferedOutputStream fOut = null;
        try {
            fOut = new BufferedOutputStream(new FileOutputStream(this.filename, true));
            if (this.filename.getName().toLowerCase().endsWith("tzx")) {
                if (this.nOffsetBlocks == 0) {
                    fOut.write(90);
                    fOut.write(88);
                    fOut.write(84);
                    fOut.write(97);
                    fOut.write(112);
                    fOut.write(101);
                    fOut.write(33);
                    fOut.write(26);
                    fOut.write(1);
                    fOut.write(32);
                }
                fOut.write(16);
                fOut.write(232);
                fOut.write(3);
            }
            fOut.write(nBytes + 2);
            fOut.write(nBytes + 2 >>> 8);
            int parity = this.cpu.getRegA();
            fOut.write(parity);
            for (int address = addr; address < addr + nBytes; ++address) {
                final int value = memory.readByte(address) & 0xFF;
                fOut.write(value);
                parity ^= value;
            }
            fOut.write(parity);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Tape.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex2) {
            Logger.getLogger(Tape.class.getName()).log(Level.SEVERE, null, ex2);
        }
        finally {
            try {
                fOut.close();
            }
            catch (IOException ex3) {
                Logger.getLogger(Tape.class.getName()).log(Level.SEVERE, null, ex3);
            }
        }
        this.eject();
        this.insert(this.filename);
    }
    
    public boolean startRecording() {
        if (!this.isTapeReady() || !this.filename.getName().toLowerCase().endsWith(".tzx")) {
            return false;
        }
        this.record = new ByteArrayOutputStream();
        this.timeLastIn = 0L;
        this.tapeRecording = true;
        this.timeout = (this.settings.isHighSamplingFreq() ? 79L : 158L);
        this.updateTapeIcon();
        return true;
    }
    
    public boolean stopRecording() {
        if (!this.tapeRecording) {
            return false;
        }
        if (this.bitsLastByte != 0) {
            this.byteTmp <<= (byte)(8 - this.bitsLastByte);
            this.record.write(this.byteTmp);
        }
        BufferedOutputStream fOut = null;
        try {
            fOut = new BufferedOutputStream(new FileOutputStream(this.filename, true));
            if (this.nOffsetBlocks == 0) {
                fOut.write(90);
                fOut.write(88);
                fOut.write(84);
                fOut.write(97);
                fOut.write(112);
                fOut.write(101);
                fOut.write(33);
                fOut.write(26);
                fOut.write(1);
                fOut.write(32);
            }
            fOut.write(21);
            fOut.write(this.settings.isHighSamplingFreq() ? 79 : 158);
            fOut.write(0);
            fOut.write(0);
            fOut.write(0);
            fOut.write(this.bitsLastByte);
            fOut.write(this.record.size());
            fOut.write(this.record.size() >>> 8);
            fOut.write(this.record.size() >>> 16);
            this.record.writeTo(fOut);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Tape.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex2) {
            Logger.getLogger(Tape.class.getName()).log(Level.SEVERE, null, ex2);
        }
        finally {
            try {
                fOut.close();
            }
            catch (IOException ex3) {
                Logger.getLogger(Tape.class.getName()).log(Level.SEVERE, null, ex3);
            }
        }
        this.eject();
        this.insert(this.filename);
        this.tapeRecording = false;
        this.updateTapeIcon();
        return true;
    }
    
    public void setPulse(final boolean bit) {
        this.pulse = bit;
    }
    
    public void recordPulse() {
        this.timeout = (this.settings.isHighSamplingFreq() ? 79L : 158L);
        if (this.bitsLastByte == 8) {
            this.record.write(this.byteTmp);
            this.bitsLastByte = 0;
            this.byteTmp = 0;
        }
        this.byteTmp <<= 1;
        if (this.pulse) {
            this.byteTmp |= 0x1;
        }
        ++this.bitsLastByte;
    }
    
    public void setTapeIcon(final JLabel tapeLabel) {
        this.tapeIcon = tapeLabel;
        this.updateTapeIcon();
    }
    
    private void updateTapeIcon() {
        if (this.tapeIcon == null) {
            return;
        }
        if (!this.tapePlaying && !this.tapeRecording) {
            this.enabledIcon = false;
        }
        else {
            this.enabledIcon = true;
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Tape.this.tapeIcon.setToolTipText(Tape.this.filenameLabel);
                Tape.this.tapeIcon.setEnabled(Tape.this.enabledIcon);
            }
        });
    }
    
    private enum State
    {
        STOP, 
        START, 
        LEADER, 
        LEADER_NOCHG, 
        SYNC, 
        NEWBYTE, 
        NEWBYTE_NOCHG, 
        NEWBIT, 
        HALF2, 
        PAUSE, 
        TZX_HEADER, 
        PURE_TONE, 
        PURE_TONE_NOCHG, 
        PULSE_SEQUENCE, 
        PULSE_SEQUENCE_NOCHG, 
        NEWDR_BYTE, 
        NEWDR_BIT, 
        PAUSE_STOP;
    }
}
