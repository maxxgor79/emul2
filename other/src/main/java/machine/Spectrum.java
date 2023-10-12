// 
// Decompiled by Procyon v0.5.36
// 

package machine;

import java.awt.image.DataBufferInt;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.util.ResourceBundle;
import utilities.Snapshots;
import java.io.File;
import java.util.Arrays;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import configuration.SpectrumType;
import configuration.JSpeccySettingsType;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JLabel;
import utilities.Tape;
import gui.JSpeccyScreen;
import java.util.Timer;
import z80core.Z80;
import utilities.TapeNotify;
import z80core.MemIoOps;

public class Spectrum extends Thread implements MemIoOps, TapeNotify
{
    private Z80 z80;
    private Memory memory;
    private boolean[] contendedRamPage;
    private boolean[] contendedIOPage;
    private int portFE;
    private int earBit;
    private int port7ffd;
    private int port1ffd;
    private int szxTapeMode;
    private long nFrame;
    private long framesByInt;
    private long speedometer;
    private long speed;
    private long prevSpeed;
    private boolean muted;
    private boolean enabledSound;
    private boolean enabledAY;
    private static final byte[] delayTstates;
    public MachineTypes spectrumModel;
    private Timer timerFrame;
    private SpectrumTimer taskFrame;
    private JSpeccyScreen jscr;
    private Keyboard keyboard;
    private Audio audio;
    private AY8912 ay8912;
    public Tape tape;
    private boolean paused;
    private boolean hardResetPending;
    private boolean resetPending;
    private JLabel modelLabel;
    private JLabel speedLabel;
    private JLabel tapeFilename;
    private JRadioButtonMenuItem hardwareMenu16k;
    private JRadioButtonMenuItem hardwareMenu48k;
    private JRadioButtonMenuItem hardwareMenu128k;
    private JRadioButtonMenuItem hardwareMenuPlus2;
    private JRadioButtonMenuItem hardwareMenuPlus2A;
    private JRadioButtonMenuItem hardwareMenuPlus3;
    private JRadioButtonMenuItem joystickNone;
    private JRadioButtonMenuItem joystickKempston;
    private JRadioButtonMenuItem joystickSinclair1;
    private JRadioButtonMenuItem joystickSinclair2;
    private JRadioButtonMenuItem joystickCursor;
    private JRadioButtonMenuItem joystickFuller;
    private JMenuItem insertIF2RomMenu;
    private JMenuItem ejectIF2RomMenu;
    private JMenuItem playTapeMenu;
    private Joystick joystick;
    private JSpeccySettingsType settings;
    private SpectrumType specSettings;
    private boolean ULAplusOn;
    private boolean issue2;
    private boolean multiface;
    private boolean mf128on48k;
    private boolean saveTrap;
    private boolean loadTrap;
    static final int SPEAKER_VOLUME = 11000;
    private int speaker;
    private static final int[] sp_volt;
    private static final int[] Paleta;
    private static final int[] Paper;
    private static final int[] Ink;
    public final int[] scr2attr;
    private final int[] attr2scr;
    private final int[] bufAddr;
    private final int[] repaintTable;
    public final int[] scrAddr;
    private final boolean[] dirtyByte;
    private final int[] states2scr;
    private final int[] states2border;
    public static final int BORDER_WIDTH = 32;
    public static final int SCREEN_WIDTH = 320;
    public static final int SCREEN_HEIGHT = 256;
    private int flash;
    private BufferedImage tvImage;
    private BufferedImage inProgressImage;
    private int[] dataInProgress;
    private Graphics2D gcTvImage;
    private int lastChgBorder;
    private int nBorderChanges;
    private boolean screenDirty;
    private boolean borderDirty;
    private boolean borderChanged;
    private int firstLine;
    private int lastLine;
    private int leftCol;
    private int rightCol;
    private int[][] ULAplus;
    private int paletteGroup;
    private boolean ULAplusMode;
    private int[][] ULAplusPalette;
    
    public Spectrum(final JSpeccySettingsType config) {
        super("SpectrumThread");
        this.contendedRamPage = new boolean[4];
        this.contendedIOPage = new boolean[4];
        this.earBit = 191;
        this.scr2attr = new int[6144];
        this.attr2scr = new int[768];
        this.bufAddr = new int[6144];
        this.repaintTable = new int[6144];
        this.scrAddr = new int[192];
        this.dirtyByte = new boolean[6144];
        this.states2scr = new int[MachineTypes.SPECTRUM128K.tstatesFrame + 100];
        this.states2border = new int[MachineTypes.SPECTRUM128K.tstatesFrame + 100];
        this.flash = 127;
        this.settings = config;
        this.specSettings = this.settings.getSpectrumSettings();
        this.z80 = new Z80(this);
        this.memory = new Memory(this.settings);
        this.initGFX();
        final long n = 0L;
        this.speedometer = n;
        this.nFrame = n;
        this.framesByInt = 1L;
        this.portFE = 0;
        this.port7ffd = 0;
        this.ay8912 = new AY8912();
        this.audio = new Audio(this.settings.getAY8912Settings());
        this.muted = this.specSettings.isMutedSound();
        this.enabledSound = false;
        this.paused = true;
        this.tape = new Tape(this.settings.getTapeSettings(), this.z80, this);
        this.keyboard = new Keyboard();
        switch (this.settings.getKeyboardJoystickSettings().getJoystickModel()) {
            case 1: {
                this.joystick = Joystick.KEMPSTON;
                break;
            }
            case 2: {
                this.joystick = Joystick.SINCLAIR1;
                break;
            }
            case 3: {
                this.joystick = Joystick.SINCLAIR2;
                break;
            }
            case 4: {
                this.joystick = Joystick.CURSOR;
                break;
            }
            case 5: {
                this.joystick = Joystick.FULLER;
                break;
            }
            default: {
                this.joystick = Joystick.NONE;
                break;
            }
        }
        this.setJoystick(this.joystick);
        switch (this.specSettings.getDefaultModel()) {
            case 0: {
                this.selectHardwareModel(MachineTypes.SPECTRUM16K, false);
                break;
            }
            case 2: {
                this.selectHardwareModel(MachineTypes.SPECTRUM128K, false);
                break;
            }
            case 3: {
                this.selectHardwareModel(MachineTypes.SPECTRUMPLUS2, false);
                break;
            }
            case 4: {
                this.selectHardwareModel(MachineTypes.SPECTRUMPLUS2A, false);
                break;
            }
            case 5: {
                this.selectHardwareModel(MachineTypes.SPECTRUMPLUS3, false);
                break;
            }
            default: {
                this.selectHardwareModel(MachineTypes.SPECTRUM48K, false);
                break;
            }
        }
        final boolean b = false;
        this.hardResetPending = b;
        this.resetPending = b;
        this.loadConfigVars();
        this.timerFrame = new Timer("SpectrumClock", true);
    }
    
    public final void selectHardwareModel(final MachineTypes hardwareModel, final boolean ramReset) {
        this.disableSound();
        this.spectrumModel = hardwareModel;
        this.memory.setSpectrumModel(this.spectrumModel);
        if (ramReset) {
            this.memory.ejectIF2Rom();
            this.memory.hardReset();
        }
        this.tape.setSpectrumModel(this.spectrumModel);
        this.enabledAY = this.spectrumModel.hasAY8912();
        this.contendedRamPage[0] = (this.contendedIOPage[0] = false);
        this.contendedRamPage[1] = (this.contendedIOPage[1] = true);
        this.contendedRamPage[2] = (this.contendedIOPage[2] = false);
        this.contendedRamPage[3] = (this.contendedIOPage[3] = false);
        switch (this.spectrumModel.codeModel) {
            case SPECTRUM48K: {
                this.buildScreenTables48k();
                this.enabledAY = this.specSettings.isAYEnabled48K();
                break;
            }
            case SPECTRUM128K: {
                this.buildScreenTables128k();
                break;
            }
            case SPECTRUMPLUS3: {
                this.buildScreenTablesPlus3();
                this.contendedIOPage[1] = false;
                break;
            }
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Spectrum.this.ejectIF2RomMenu.setEnabled(false);
                switch (Spectrum.this.spectrumModel) {
                    case SPECTRUM16K: {
                        Spectrum.this.hardwareMenu16k.setSelected(true);
                        Spectrum.this.insertIF2RomMenu.setEnabled(true);
                        break;
                    }
                    case SPECTRUM48K: {
                        Spectrum.this.hardwareMenu48k.setSelected(true);
                        Spectrum.this.insertIF2RomMenu.setEnabled(true);
                        break;
                    }
                    case SPECTRUM128K: {
                        Spectrum.this.hardwareMenu128k.setSelected(true);
                        Spectrum.this.insertIF2RomMenu.setEnabled(true);
                        break;
                    }
                    case SPECTRUMPLUS2: {
                        Spectrum.this.hardwareMenuPlus2.setSelected(true);
                        Spectrum.this.insertIF2RomMenu.setEnabled(true);
                        break;
                    }
                    case SPECTRUMPLUS2A: {
                        Spectrum.this.hardwareMenuPlus2A.setSelected(true);
                        Spectrum.this.insertIF2RomMenu.setEnabled(false);
                        break;
                    }
                    case SPECTRUMPLUS3: {
                        Spectrum.this.hardwareMenuPlus3.setSelected(true);
                        Spectrum.this.insertIF2RomMenu.setEnabled(false);
                        break;
                    }
                }
                Spectrum.this.modelLabel.setToolTipText(Spectrum.this.spectrumModel.getLongModelName());
                Spectrum.this.modelLabel.setText(Spectrum.this.spectrumModel.getShortModelName());
            }
        });
        this.enableSound();
    }
    
    public final void loadConfigVars() {
        this.ULAplusOn = this.settings.getSpectrumSettings().isULAplus();
        this.issue2 = this.settings.getSpectrumSettings().isIssue2();
        this.multiface = this.settings.getSpectrumSettings().isMultifaceEnabled();
        this.mf128on48k = this.settings.getSpectrumSettings().isMf128On48K();
        this.saveTrap = this.settings.getTapeSettings().isEnableSaveTraps();
        this.loadTrap = this.settings.getTapeSettings().isFlashload();
    }
    
    @Override
    public void run() {
        this.startEmulation();
        try {
            Thread.sleep(Long.MAX_VALUE);
        }
        catch (InterruptedException excpt) {
            Logger.getLogger(Spectrum.class.getName()).log(Level.SEVERE, null, excpt);
        }
    }
    
    public void startEmulation() {
        this.audio.reset();
        this.enableSound();
        this.invalidateScreen(true);
        this.taskFrame = new SpectrumTimer(this);
        this.timerFrame.scheduleAtFixedRate(this.taskFrame, 50L, 20L);
        this.paused = false;
        this.nFrame = 0L;
    }
    
    public synchronized void stopEmulation() {
        this.taskFrame.cancel();
        this.paused = true;
        this.disableSound();
    }
    
    public void reset() {
        this.resetPending = true;
    }
    
    public void doReset() {
        this.z80.reset();
        this.memory.reset();
        this.ay8912.reset();
        this.audio.reset();
        this.keyboard.reset();
        this.nFrame = 0L;
        final int portFE = 0;
        this.port1ffd = portFE;
        this.port7ffd = portFE;
        this.portFE = portFE;
        this.ULAplusMode = false;
        this.paletteGroup = 0;
        this.invalidateScreen(true);
    }
    
    public void hardReset() {
        final boolean b = true;
        this.resetPending = b;
        this.hardResetPending = b;
    }
    
    public void doHardReset() {
        switch (this.specSettings.getDefaultModel()) {
            case 0: {
                this.selectHardwareModel(MachineTypes.SPECTRUM16K, true);
                break;
            }
            case 2: {
                this.selectHardwareModel(MachineTypes.SPECTRUM128K, true);
                break;
            }
            case 3: {
                this.selectHardwareModel(MachineTypes.SPECTRUMPLUS2, true);
                break;
            }
            case 4: {
                this.selectHardwareModel(MachineTypes.SPECTRUMPLUS2A, true);
                break;
            }
            case 5: {
                this.selectHardwareModel(MachineTypes.SPECTRUMPLUS3, true);
                break;
            }
            default: {
                this.selectHardwareModel(MachineTypes.SPECTRUM48K, true);
                break;
            }
        }
        switch (this.settings.getKeyboardJoystickSettings().getJoystickModel()) {
            case 1: {
                this.joystick = Joystick.KEMPSTON;
                break;
            }
            case 2: {
                this.joystick = Joystick.SINCLAIR1;
                break;
            }
            case 3: {
                this.joystick = Joystick.SINCLAIR2;
                break;
            }
            case 4: {
                this.joystick = Joystick.CURSOR;
                break;
            }
            case 5: {
                this.joystick = Joystick.FULLER;
                break;
            }
            default: {
                this.joystick = Joystick.NONE;
                break;
            }
        }
        this.setJoystick(this.joystick);
        this.memory.hardReset();
        this.reset();
    }
    
    public boolean isPaused() {
        return this.paused;
    }
    
    public void triggerNMI() {
        this.z80.triggerNMI();
    }
    
    public Keyboard getKeyboard() {
        return this.keyboard;
    }
    
    public final void setJoystick(final Joystick type) {
        this.joystick = type;
        this.keyboard.setJoystick(type);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                switch (Spectrum.this.joystick) {
                    case NONE: {
                        Spectrum.this.joystickNone.setSelected(true);
                        break;
                    }
                    case KEMPSTON: {
                        Spectrum.this.joystickKempston.setSelected(true);
                        break;
                    }
                    case SINCLAIR1: {
                        Spectrum.this.joystickSinclair1.setSelected(true);
                        break;
                    }
                    case SINCLAIR2: {
                        Spectrum.this.joystickSinclair2.setSelected(true);
                        break;
                    }
                    case CURSOR: {
                        Spectrum.this.joystickCursor.setSelected(true);
                        break;
                    }
                    case FULLER: {
                        Spectrum.this.joystickFuller.setSelected(true);
                        break;
                    }
                }
            }
        });
    }
    
    public void setJoystickMenuItems(final JRadioButtonMenuItem jNone, final JRadioButtonMenuItem jKempston, final JRadioButtonMenuItem jSinclair1, final JRadioButtonMenuItem jSinclair2, final JRadioButtonMenuItem jCursor, final JRadioButtonMenuItem jFuller) {
        this.joystickNone = jNone;
        this.joystickKempston = jKempston;
        this.joystickSinclair1 = jSinclair1;
        this.joystickSinclair2 = jSinclair2;
        this.joystickCursor = jCursor;
        this.joystickFuller = jFuller;
    }
    
    public void setHardwareMenuItems(final JRadioButtonMenuItem hw16k, final JRadioButtonMenuItem hw48k, final JRadioButtonMenuItem hw128k, final JRadioButtonMenuItem hwPlus2, final JRadioButtonMenuItem hwPlus2A, final JRadioButtonMenuItem hwPlus3) {
        this.hardwareMenu16k = hw16k;
        this.hardwareMenu48k = hw48k;
        this.hardwareMenu128k = hw128k;
        this.hardwareMenuPlus2 = hwPlus2;
        this.hardwareMenuPlus2A = hwPlus2A;
        this.hardwareMenuPlus3 = hwPlus3;
    }
    
    public void setMenuItems(final JMenuItem insert, final JMenuItem eject, final JMenuItem play) {
        this.insertIF2RomMenu = insert;
        this.ejectIF2RomMenu = eject;
        this.playTapeMenu = play;
    }
    
    public void setScreenComponent(final JSpeccyScreen jScr) {
        this.jscr = jScr;
    }
    
    public void setInfoLabels(final JLabel nameComponent, final JLabel speedComponent, final JLabel tapeComponent) {
        this.modelLabel = nameComponent;
        this.speedLabel = speedComponent;
        this.tapeFilename = tapeComponent;
    }
    
    public synchronized void generateFrame() {
        if (this.resetPending) {
            if (this.hardResetPending) {
                this.doHardReset();
                this.hardResetPending = false;
            }
            this.doReset();
            this.resetPending = false;
        }
        long counter = this.framesByInt;
        final int n = 0;
        this.lastLine = n;
        this.firstLine = n;
        this.leftCol = 31;
        this.rightCol = 0;
        this.lastChgBorder = this.spectrumModel.firstBorderUpdate;
        do {
            if (this.z80.tEstados < this.spectrumModel.lengthINT) {
                this.z80.setINTLine(true);
                this.z80.execute(this.spectrumModel.lengthINT);
            }
            this.z80.setINTLine(false);
            if (this.z80.tEstados < this.spectrumModel.firstScrByte) {
                this.z80.execute(this.spectrumModel.firstScrByte);
                this.updateScreen(this.spectrumModel.firstScrUpdate, this.z80.tEstados);
            }
            while (this.z80.tEstados < this.spectrumModel.lastScrUpdate) {
                final int fromTstates = this.z80.tEstados + 1;
                this.z80.execute(fromTstates + 7);
                this.updateScreen(fromTstates, this.z80.tEstados);
            }
            this.z80.execute(this.spectrumModel.tstatesFrame);
            if (this.borderChanged) {
                this.updateBorder(this.spectrumModel.lastBorderUpdate);
            }
            if (this.enabledSound) {
                if (this.enabledAY) {
                    this.ay8912.updateAY(this.z80.tEstados);
                }
                this.audio.updateAudio(this.z80.tEstados, this.speaker);
                this.audio.endFrame();
            }
            final Z80 z80 = this.z80;
            z80.tEstados -= this.spectrumModel.tstatesFrame;
            ++this.nFrame;
            if (!this.ULAplusMode && this.nFrame % 16L == 0L) {
                this.toggleFlash();
            }
            if (this.nFrame % 50L == 0L) {
                final long now = System.currentTimeMillis();
                final long diff = now - this.speedometer;
                this.speed = 100000L / diff;
                this.speedometer = now;
                if (this.speed == this.prevSpeed) {
                    continue;
                }
                this.prevSpeed = this.speed;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        Spectrum.this.speedLabel.setText(String.format("%4d%%", Spectrum.this.speed));
                    }
                });
            }
        } while (--counter > 0L);
        if (this.spectrumModel.codeModel == MachineTypes.CodeModel.SPECTRUM128K && this.z80.getRegI() < 128 && this.z80.getRegI() > 63) {
            this.z80.reset();
        }
    }
    
    public synchronized void drawFrame() {
        if (this.borderDirty || this.framesByInt > 1L) {
            if (this.nBorderChanges == 0 && this.framesByInt == 1L) {
                final boolean b = false;
                this.borderChanged = b;
                this.borderDirty = b;
            }
            else {
                this.nBorderChanges = 0;
                this.screenDirty = false;
                this.gcTvImage.drawImage(this.inProgressImage, 0, 0, null);
                this.jscr.repaint();
            }
        }
        if (this.screenDirty) {
            this.screenDirty = false;
            this.gcTvImage.drawImage(this.inProgressImage, 0, 0, null);
            this.firstLine = this.repaintTable[this.firstLine & 0x1FFF];
            this.lastLine = this.repaintTable[this.lastLine & 0x1FFF];
            if (this.jscr.isDoubleSized()) {
                this.jscr.repaint(64 + this.leftCol * 16, (32 + this.firstLine) * 2, (this.rightCol - this.leftCol + 1) * 16, (this.lastLine - this.firstLine + 1) * 2);
            }
            else {
                this.jscr.repaint(32 + this.leftCol * 8, 32 + this.firstLine, (this.rightCol - this.leftCol + 1) * 8, this.lastLine - this.firstLine + 1);
            }
        }
    }
    
    @Override
    public int fetchOpcode(final int address) {
        if (this.contendedRamPage[address >>> 14]) {
            final Z80 z80 = this.z80;
            z80.tEstados += Spectrum.delayTstates[this.z80.tEstados];
        }
        final Z80 z81 = this.z80;
        z81.tEstados += 4;
        if (address == 102 && this.multiface && !this.memory.isPlus3RamMode()) {
            this.memory.setMultifaceLocked(false);
            this.memory.multifacePageIn();
        }
        if (address == 1366 && this.memory.isSpectrumRom() && this.tape.isTapeReady()) {
            if (this.loadTrap) {
                this.tape.flashLoad(this.memory);
                this.invalidateScreen(true);
                return 201;
            }
            this.toggleTape();
        }
        if (address == 1232 && this.saveTrap && this.memory.isSpectrumRom() && this.tape.isTapeReady()) {
            this.tape.saveTapeBlock(this.memory);
            return 201;
        }
        return this.memory.readByte(address) & 0xFF;
    }
    
    @Override
    public int peek8(final int address) {
        if (this.contendedRamPage[address >>> 14]) {
            final Z80 z80 = this.z80;
            z80.tEstados += Spectrum.delayTstates[this.z80.tEstados];
        }
        final Z80 z81 = this.z80;
        z81.tEstados += 3;
        return this.memory.readByte(address) & 0xFF;
    }
    
    @Override
    public void poke8(final int address, final int value) {
        if (this.contendedRamPage[address >>> 14]) {
            final Z80 z80 = this.z80;
            z80.tEstados += Spectrum.delayTstates[this.z80.tEstados];
            if (this.memory.isScreenByte(address)) {
                this.notifyScreenWrite(address);
            }
        }
        final Z80 z81 = this.z80;
        z81.tEstados += 3;
        this.memory.writeByte(address, (byte)value);
    }
    
    @Override
    public int peek16(int address) {
        if (this.contendedRamPage[address >>> 14]) {
            final Z80 z80 = this.z80;
            z80.tEstados += Spectrum.delayTstates[this.z80.tEstados];
        }
        final Z80 z81 = this.z80;
        z81.tEstados += 3;
        final int lsb = this.memory.readByte(address) & 0xFF;
        address = (address + 1 & 0xFFFF);
        if (this.contendedRamPage[address >>> 14]) {
            final Z80 z82 = this.z80;
            z82.tEstados += Spectrum.delayTstates[this.z80.tEstados];
        }
        final Z80 z83 = this.z80;
        z83.tEstados += 3;
        return (this.memory.readByte(address) << 8 & 0xFF00) | lsb;
    }
    
    @Override
    public void poke16(int address, final int word) {
        if (this.contendedRamPage[address >>> 14]) {
            final Z80 z80 = this.z80;
            z80.tEstados += Spectrum.delayTstates[this.z80.tEstados];
            if (this.memory.isScreenByte(address)) {
                this.notifyScreenWrite(address);
            }
        }
        final Z80 z81 = this.z80;
        z81.tEstados += 3;
        this.memory.writeByte(address, (byte)word);
        address = (address + 1 & 0xFFFF);
        if (this.contendedRamPage[address >>> 14]) {
            final Z80 z82 = this.z80;
            z82.tEstados += Spectrum.delayTstates[this.z80.tEstados];
            if (this.memory.isScreenByte(address)) {
                this.notifyScreenWrite(address);
            }
        }
        final Z80 z83 = this.z80;
        z83.tEstados += 3;
        this.memory.writeByte(address, (byte)(word >>> 8));
    }
    
    @Override
    public void contendedStates(final int address, final int tstates) {
        if (this.contendedRamPage[address >>> 14] && this.spectrumModel.codeModel != MachineTypes.CodeModel.SPECTRUMPLUS3) {
            for (int idx = 0; idx < tstates; ++idx) {
                final Z80 z80 = this.z80;
                z80.tEstados += Spectrum.delayTstates[this.z80.tEstados] + 1;
            }
        }
        else {
            final Z80 z81 = this.z80;
            z81.tEstados += tstates;
        }
    }
    
    @Override
    public int inPort(final int port) {
        this.preIO(port);
        this.postIO(port);
        if (this.multiface) {
            switch (this.spectrumModel.codeModel) {
                case SPECTRUM48K: {
                    if (this.mf128on48k) {
                        if ((port & 0xFF) == 0xBF && !this.memory.isMultifaceLocked()) {
                            this.memory.multifacePageIn();
                        }
                        if ((port & 0xFF) == 0x3F && this.memory.isMultifacePaged()) {
                            this.memory.multifacePageOut();
                            break;
                        }
                        break;
                    }
                    else {
                        if ((port & 0xFF) == 0x9F) {
                            this.memory.multifacePageIn();
                        }
                        if ((port & 0xFF) == 0x1F && this.memory.isMultifacePaged()) {
                            this.memory.multifacePageOut();
                            break;
                        }
                        break;
                    }
                    break;
                }
                case SPECTRUM128K: {
                    if ((port & 0xFF) == 0xBF && !this.memory.isMultifaceLocked()) {
                        this.memory.multifacePageIn();
                    }
                    if ((port & 0xFF) == 0x3F && this.memory.isMultifacePaged()) {
                        this.memory.multifacePageOut();
                        break;
                    }
                    break;
                }
                case SPECTRUMPLUS3: {
                    if ((port & 0xFF) == 0xBF && this.memory.isMultifacePaged()) {
                        this.memory.multifacePageOut();
                    }
                    if ((port & 0xFF) != 0x3F) {
                        break;
                    }
                    if ((port & 0xFF00) == 0x7F00 && !this.memory.isMultifaceLocked() && this.memory.isMultifacePaged()) {
                        return this.port7ffd;
                    }
                    if ((port & 0xFF00) == 0x1F00 && !this.memory.isMultifaceLocked() && this.memory.isMultifacePaged()) {
                        return this.port1ffd;
                    }
                    if (!this.memory.isMultifaceLocked()) {
                        this.memory.multifacePageIn();
                        break;
                    }
                    break;
                }
            }
        }
        if ((port & 0x20) == 0x0 && this.joystick == Joystick.KEMPSTON) {
            return this.keyboard.readKempstonPort();
        }
        if ((port & 0xFF) == 0x7F && this.joystick == Joystick.FULLER) {
            return this.keyboard.readFullerPort();
        }
        if ((port & 0x1) == 0x0) {
            return this.keyboard.readKeyboardPort(port) & this.tape.getEarBit();
        }
        if (this.enabledAY) {
            if ((port & 0xC002) == 0xC000) {
                return this.ay8912.readRegister();
            }
            if (this.spectrumModel.codeModel == MachineTypes.CodeModel.SPECTRUMPLUS3 && (port & 0xC002) == 0x8000) {
                return this.ay8912.readRegister();
            }
            if (this.joystick == Joystick.FULLER && (port & 0xFF) == 0x3F) {
                return this.ay8912.readRegister();
            }
        }
        if ((port & 0x4004) != 0x4000 || !this.ULAplusOn) {
            int floatbus = 255;
            if (this.spectrumModel.codeModel != MachineTypes.CodeModel.SPECTRUMPLUS3) {
                int addr = 0;
                final int tstates = this.z80.tEstados;
                if (tstates < this.spectrumModel.firstScrByte || tstates > this.spectrumModel.lastScrUpdate) {
                    return floatbus;
                }
                final int col = tstates % this.spectrumModel.tstatesLine - this.spectrumModel.outOffset;
                if (col > 124) {
                    return floatbus;
                }
                final int row = tstates / this.spectrumModel.tstatesLine - this.spectrumModel.upBorderWidth;
                switch (col % 8) {
                    case 0: {
                        addr = this.scrAddr[row] + col / 4;
                        floatbus = this.memory.readScreenByte(addr);
                        break;
                    }
                    case 1: {
                        addr = this.scr2attr[this.scrAddr[row] + col / 4 & 0x1FFF];
                        floatbus = this.memory.readScreenByte(addr);
                        break;
                    }
                    case 2: {
                        addr = this.scrAddr[row] + col / 4 + 1;
                        floatbus = this.memory.readScreenByte(addr);
                        break;
                    }
                    case 3: {
                        addr = this.scr2attr[this.scrAddr[row] + col / 4 + 1 & 0x1FFF];
                        floatbus = this.memory.readScreenByte(addr);
                        break;
                    }
                }
                if (this.spectrumModel.codeModel == MachineTypes.CodeModel.SPECTRUM128K && !this.memory.isPagingLocked() && (port & 0x8002) == 0x0) {
                    this.memory.setPort7ffd(floatbus);
                    if ((this.port7ffd & 0x8) != (floatbus & 0x8)) {
                        this.invalidateScreen(true);
                    }
                    this.contendedRamPage[3] = (this.contendedIOPage[3] = ((floatbus & 0x1) != 0x0));
                    this.port7ffd = floatbus;
                }
            }
            return floatbus & 0xFF;
        }
        if (this.paletteGroup == 64) {
            return this.ULAplusMode ? 1 : 0;
        }
        return this.ULAplus[this.paletteGroup >>> 4][this.paletteGroup & 0xF];
    }
    
    @Override
    public void outPort(final int port, final int value) {
        this.preIO(port);
        try {
            if (this.multiface && this.memory.isMultifacePaged() && this.z80.getRegPC() < 16384) {
                if ((port & 0xFF) == 0x3F) {
                    this.memory.setMultifaceLocked(true);
                }
                if ((port & 0xFF) == 0xBF) {
                    this.memory.setMultifaceLocked(false);
                }
            }
            if ((port & 0x1) == 0x0) {
                if ((this.portFE & 0x7) != (value & 0x7)) {
                    this.updateBorder(this.z80.tEstados);
                    this.borderChanged = true;
                }
                final int spkMic = Spectrum.sp_volt[value >> 3 & 0x3];
                if (this.enabledSound && spkMic != this.speaker) {
                    this.audio.updateAudio(this.z80.tEstados, this.speaker);
                    this.speaker = spkMic;
                }
                if (!this.tape.isTapePlaying() && this.spectrumModel.codeModel != MachineTypes.CodeModel.SPECTRUMPLUS3) {
                    int issueMask;
                    if (this.spectrumModel.codeModel == MachineTypes.CodeModel.SPECTRUM48K) {
                        issueMask = (this.issue2 ? 24 : 16);
                    }
                    else {
                        issueMask = 16;
                    }
                    if ((value & issueMask) == 0x0) {
                        this.tape.setEarBit(false);
                    }
                    else {
                        this.tape.setEarBit(true);
                    }
                }
                this.portFE = value;
                return;
            }
            if (this.spectrumModel.codeModel == MachineTypes.CodeModel.SPECTRUM128K && (port & 0x8002) == 0x0) {
                this.memory.setPort7ffd(value);
                this.contendedRamPage[3] = (this.contendedIOPage[3] = ((value & 0x1) != 0x0));
                if (((this.port7ffd ^ value) & 0x8) != 0x0) {
                    this.invalidateScreen(true);
                }
                this.port7ffd = value;
                return;
            }
            if (this.spectrumModel.codeModel == MachineTypes.CodeModel.SPECTRUMPLUS3) {
                if ((port & 0xC002) == 0x4000) {
                    this.memory.setPort7ffd(value);
                    this.contendedRamPage[3] = (this.memory.getPlus3HighPage() > 3);
                    if (((this.port7ffd ^ value) & 0x8) != 0x0) {
                        this.invalidateScreen(true);
                    }
                    this.port7ffd = value;
                    return;
                }
                if ((port & 0xF002) == 0x1000) {
                    this.memory.setPort1ffd(value);
                    if (this.memory.isPlus3RamMode()) {
                        switch (value & 0x6) {
                            case 0: {
                                Arrays.fill(this.contendedRamPage, false);
                                break;
                            }
                            case 2: {
                                Arrays.fill(this.contendedRamPage, true);
                                break;
                            }
                            case 4:
                            case 6: {
                                Arrays.fill(this.contendedRamPage, true);
                                this.contendedRamPage[3] = false;
                                break;
                            }
                        }
                    }
                    else {
                        this.contendedRamPage[0] = (this.contendedRamPage[2] = false);
                        this.contendedRamPage[1] = true;
                        this.contendedRamPage[3] = (this.memory.getPlus3HighPage() > 3);
                    }
                    this.port1ffd = value;
                    return;
                }
            }
            if (this.enabledAY && (port & 0x8002) == 0x8000) {
                if ((port & 0x4000) != 0x0) {
                    this.ay8912.setAddressLatch(value);
                }
                else {
                    if (this.enabledSound && this.ay8912.getAddressLatch() < 14) {
                        this.ay8912.updateAY(this.z80.tEstados);
                    }
                    this.ay8912.writeRegister(value);
                }
                return;
            }
            if (this.enabledAY && this.joystick == Joystick.FULLER && this.spectrumModel.codeModel == MachineTypes.CodeModel.SPECTRUM48K) {
                if ((port & 0xFF) == 0x3F) {
                    this.ay8912.setAddressLatch(value);
                    return;
                }
                if ((port & 0xFF) == 0x5F) {
                    if (this.enabledSound && this.ay8912.getAddressLatch() < 14) {
                        this.ay8912.updateAY(this.z80.tEstados);
                    }
                    this.ay8912.writeRegister(value);
                    return;
                }
            }
            if ((port & 0x4) == 0x0 && this.ULAplusOn) {
                if ((port & 0x4000) == 0x0) {
                    if ((value & 0x40) != 0x0) {
                        this.paletteGroup = 64;
                    }
                    else {
                        this.paletteGroup = (value & 0x3F);
                    }
                }
                else if (this.paletteGroup == 64) {
                    this.ULAplusMode = ((value & 0x1) != 0x0);
                    this.invalidateScreen(true);
                }
                else {
                    this.ULAplus[this.paletteGroup >>> 4][this.paletteGroup & 0xF] = value;
                    int blue = (value & 0x3) << 1;
                    if ((value & 0x1) == 0x1) {
                        blue |= 0x1;
                    }
                    blue = (blue << 5 | blue << 2 | (blue & 0x3));
                    int red = (value & 0x1C) >> 2;
                    red = (red << 5 | red << 2 | (red & 0x3));
                    int green = (value & 0xE0) >> 5;
                    green = (green << 5 | green << 2 | (green & 0x3));
                    this.ULAplusPalette[this.paletteGroup >>> 4][this.paletteGroup & 0xF] = (red << 16 | green << 8 | blue);
                    this.invalidateScreen(this.paletteGroup > 7 && this.paletteGroup < 16);
                }
            }
        }
        finally {
            this.postIO(port);
        }
    }
    
    private void postIO(final int port) {
        if ((port & 0x1) != 0x0) {
            if (this.contendedIOPage[port >>> 14]) {
                final Z80 z80 = this.z80;
                z80.tEstados += Spectrum.delayTstates[this.z80.tEstados] + 1;
                final Z80 z81 = this.z80;
                z81.tEstados += Spectrum.delayTstates[this.z80.tEstados] + 1;
                final Z80 z82 = this.z80;
                z82.tEstados += Spectrum.delayTstates[this.z80.tEstados] + 1;
            }
            else {
                final Z80 z83 = this.z80;
                z83.tEstados += 3;
            }
        }
        else {
            final Z80 z84 = this.z80;
            z84.tEstados += Spectrum.delayTstates[this.z80.tEstados] + 3;
        }
    }
    
    private void preIO(final int port) {
        if (this.contendedIOPage[port >>> 14]) {
            final Z80 z80 = this.z80;
            z80.tEstados += Spectrum.delayTstates[this.z80.tEstados];
        }
        final Z80 z81 = this.z80;
        ++z81.tEstados;
    }
    
    @Override
    public void execDone(final int tstates) {
        if (this.tape.isTapeRecording()) {
            this.tape.setPulse((this.portFE & 0x8) != 0x0);
        }
        this.tape.notifyTstates(this.nFrame, this.z80.tEstados);
        if (this.enabledSound && this.specSettings.isLoadingNoise() && this.tape.isTapePlaying()) {
            this.earBit = this.tape.getEarBit();
            final int spkMic = (this.earBit == 191) ? 0 : 4000;
            if (spkMic != this.speaker) {
                this.audio.updateAudio(this.z80.tEstados, this.speaker);
                this.speaker = spkMic;
            }
        }
    }
    
    public void loadSnapshot(final File filename) {
        final Snapshots snap = new Snapshots();
        if (this.memory.isIF2RomEnabled()) {
            this.memory.ejectIF2Rom();
        }
        if (snap.loadSnapshot(filename, this.memory)) {
            switch (snap.getSnapshotModel()) {
                case SPECTRUM16K: {
                    this.selectHardwareModel(MachineTypes.SPECTRUM16K, false);
                    break;
                }
                case SPECTRUM48K: {
                    this.selectHardwareModel(MachineTypes.SPECTRUM48K, false);
                    break;
                }
                case SPECTRUM128K: {
                    this.selectHardwareModel(MachineTypes.SPECTRUM128K, false);
                    break;
                }
                case SPECTRUMPLUS2: {
                    this.selectHardwareModel(MachineTypes.SPECTRUMPLUS2, false);
                    break;
                }
                case SPECTRUMPLUS2A: {
                    this.selectHardwareModel(MachineTypes.SPECTRUMPLUS2A, false);
                    break;
                }
                case SPECTRUMPLUS3: {
                    this.selectHardwareModel(MachineTypes.SPECTRUMPLUS3, false);
                    break;
                }
            }
            this.doReset();
            this.z80.setRegAF(snap.getRegAF());
            this.z80.setRegBC(snap.getRegBC());
            this.z80.setRegDE(snap.getRegDE());
            this.z80.setRegHL(snap.getRegHL());
            this.z80.setRegAFalt(snap.getRegAFalt());
            this.z80.setRegBCalt(snap.getRegBCalt());
            this.z80.setRegDEalt(snap.getRegDEalt());
            this.z80.setRegHLalt(snap.getRegHLalt());
            this.z80.setRegIX(snap.getRegIX());
            this.z80.setRegIY(snap.getRegIY());
            this.z80.setRegSP(snap.getRegSP());
            this.z80.setRegPC(snap.getRegPC());
            this.z80.setRegI(snap.getRegI());
            this.z80.setRegR(snap.getRegR());
            this.z80.setIM(snap.getModeIM());
            this.z80.setIFF1(snap.getIFF1());
            this.z80.setIFF2(snap.getIFF2());
            this.z80.setTEstados(snap.getTstates());
            final int border = snap.getBorder();
            this.portFE &= 0xF8;
            this.portFE |= border;
            this.issue2 = false;
            if (snap.getSnapshotModel().codeModel == MachineTypes.CodeModel.SPECTRUM48K) {
                this.issue2 = snap.isIssue2();
                if (snap.isIF2RomPresent()) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            Spectrum.this.insertIF2RomMenu.setEnabled(false);
                            Spectrum.this.ejectIF2RomMenu.setEnabled(true);
                        }
                    });
                }
                else {
                    this.memory.ejectIF2Rom();
                }
            }
            final Joystick snapJoystick = snap.getJoystick();
            if (snapJoystick != Joystick.NONE) {
                this.setJoystick(this.joystick = snapJoystick);
            }
            if (snap.getSnapshotModel().codeModel != MachineTypes.CodeModel.SPECTRUM48K) {
                this.port7ffd = snap.getPort7ffd();
                this.memory.setPort7ffd(this.port7ffd);
                if (snap.getSnapshotModel().codeModel == MachineTypes.CodeModel.SPECTRUMPLUS3) {
                    this.port1ffd = snap.getPort1ffd();
                    this.memory.setPort1ffd(this.port1ffd);
                    if (this.memory.isPlus3RamMode()) {
                        switch (this.port1ffd & 0x6) {
                            case 0: {
                                Arrays.fill(this.contendedRamPage, false);
                                break;
                            }
                            case 2: {
                                Arrays.fill(this.contendedRamPage, true);
                                break;
                            }
                            case 4:
                            case 6: {
                                Arrays.fill(this.contendedRamPage, true);
                                this.contendedRamPage[3] = false;
                                break;
                            }
                        }
                    }
                    else {
                        this.contendedRamPage[0] = (this.contendedRamPage[2] = false);
                        this.contendedRamPage[1] = true;
                        this.contendedRamPage[3] = (this.memory.getPlus3HighPage() > 3);
                    }
                }
                else {
                    this.contendedRamPage[3] = (this.contendedIOPage[3] = ((this.port7ffd & 0x1) != 0x0));
                }
            }
            if (snap.getEnabledAY() || snap.getSnapshotModel().hasAY8912()) {
                this.enabledAY = true;
                for (int reg = 0; reg < 16; ++reg) {
                    this.ay8912.setAddressLatch(reg);
                    this.ay8912.writeRegister(snap.getPsgReg(reg));
                }
                this.ay8912.setAddressLatch(snap.getPortfffd());
            }
            if (snap.isULAplus()) {
                this.ULAplusOn = true;
                this.ULAplusMode = snap.isULAplusEnabled();
                this.paletteGroup = snap.getULAplusRegister();
                for (int register = 0; register < 64; ++register) {
                    final int color = snap.getULAplusColor(register);
                    this.ULAplus[register >>> 4][register & 0xF] = color;
                    int blue = (color & 0x3) << 1;
                    if ((color & 0x1) == 0x1) {
                        blue |= 0x1;
                    }
                    blue = (blue << 5 | blue << 2 | (blue & 0x3));
                    int red = (color & 0x1C) >> 2;
                    red = (red << 5 | red << 2 | (red & 0x3));
                    int green = (color & 0xE0) >> 5;
                    green = (green << 5 | green << 2 | (green & 0x3));
                    this.ULAplusPalette[register >>> 4][register & 0xF] = (red << 16 | green << 8 | blue);
                }
            }
            if (snap.isMultiface()) {
                this.multiface = true;
                this.mf128on48k = snap.isMF128on48k();
                this.settings.getSpectrumSettings().setMf128On48K(this.mf128on48k);
                if (snap.isMFPagedIn()) {
                    this.memory.multifacePageIn();
                }
                this.memory.setMultifaceLocked(snap.isMFLockout());
            }
            if (snap.isTapeEmbedded()) {
                this.tape.eject();
                this.tape.insertEmbeddedTape(snap.getTapeName(), snap.getTapeExtension(), snap.getTapeData(), snap.getTapeBlock());
                this.playTapeMenu.setEnabled(true);
                this.tapeFilename.setText(snap.getTapeName() + "." + snap.getTapeExtension());
            }
            if (snap.isTapeLinked()) {
                final File tapeLink = new File(snap.getTapeName());
                if (tapeLink.exists()) {
                    this.tape.eject();
                    this.tape.insert(tapeLink);
                    this.tape.setSelectedBlock(snap.getTapeBlock());
                    this.playTapeMenu.setEnabled(true);
                    this.tapeFilename.setText(tapeLink.getName());
                }
            }
            this.invalidateScreen(true);
        }
        else {
            JOptionPane.showMessageDialog(this.jscr.getParent(), snap.getErrorString(), ResourceBundle.getBundle("machine/Bundle").getString("SNAPSHOT_LOAD_ERROR"), 0);
        }
    }
    
    public void saveSnapshot(final File filename) {
        final Snapshots snap = new Snapshots();
        snap.setSnapshotModel(this.spectrumModel);
        snap.setRegAF(this.z80.getRegAF());
        snap.setRegBC(this.z80.getRegBC());
        snap.setRegDE(this.z80.getRegDE());
        snap.setRegHL(this.z80.getRegHL());
        snap.setRegAFalt(this.z80.getRegAFalt());
        snap.setRegBCalt(this.z80.getRegBCalt());
        snap.setRegDEalt(this.z80.getRegDEalt());
        snap.setRegHLalt(this.z80.getRegHLalt());
        snap.setRegIX(this.z80.getRegIX());
        snap.setRegIY(this.z80.getRegIY());
        snap.setRegSP(this.z80.getRegSP());
        snap.setRegPC(this.z80.getRegPC());
        snap.setRegI(this.z80.getRegI());
        snap.setRegR(this.z80.getRegR());
        snap.setIFF1(this.z80.isIFF1());
        snap.setIFF2(this.z80.isIFF2());
        snap.setModeIM(this.z80.getIM());
        snap.setBorder(this.portFE & 0x7);
        snap.setTstates(this.z80.getTEstados());
        snap.setJoystick(this.joystick);
        snap.setIssue2(this.issue2);
        if (this.spectrumModel.codeModel != MachineTypes.CodeModel.SPECTRUM48K) {
            snap.setPort7ffd(this.port7ffd);
            if (this.spectrumModel.codeModel == MachineTypes.CodeModel.SPECTRUMPLUS3) {
                snap.setPort1ffd(this.port1ffd);
            }
        }
        if (this.enabledAY) {
            snap.setEnabledAY(true);
            final int ayLatch = this.ay8912.getAddressLatch();
            snap.setPortfffd(ayLatch);
            for (int reg = 0; reg < 16; ++reg) {
                this.ay8912.setAddressLatch(reg);
                snap.setPsgReg(reg, this.ay8912.readRegister());
            }
            this.ay8912.setAddressLatch(ayLatch);
        }
        if (this.ULAplusOn) {
            snap.setULAplus(true);
            snap.setULAplusEnabled(this.ULAplusMode);
            snap.setULAplusRegister(this.paletteGroup);
            for (int color = 0; color < 64; ++color) {
                snap.setULAplusColor(color, this.ULAplus[color >>> 4][color & 0xF]);
            }
        }
        if (this.multiface) {
            snap.setMultiface(true);
            snap.setMF128on48k(this.mf128on48k);
        }
        if (this.szxTapeMode != 0) {
            snap.setTapeName(this.tape.getTapeName().getAbsolutePath());
            snap.setTapeBlock(this.tape.getSelectedBlock());
            if (this.szxTapeMode == 1) {
                snap.setTapeLinked(true);
            }
            else {
                snap.setTapeEmbedded(true);
            }
        }
        if (snap.saveSnapshot(filename, this.memory)) {
            System.out.println(ResourceBundle.getBundle("machine/Bundle").getString("SNAPSHOT_SAVED"));
        }
        else {
            JOptionPane.showMessageDialog(this.jscr.getParent(), snap.getErrorString(), ResourceBundle.getBundle("machine/Bundle").getString("SNAPSHOT_SAVE_ERROR"), 0);
        }
    }
    
    public void saveImage(final File filename) {
        if (filename.getName().toLowerCase().endsWith(".scr")) {
            try {
                final BufferedOutputStream fOut = new BufferedOutputStream(new FileOutputStream(filename));
                for (int addr = 0; addr < 6912; ++addr) {
                    fOut.write(this.memory.readScreenByte(addr));
                }
                if (this.ULAplusMode) {
                    for (int palette = 0; palette < 4; ++palette) {
                        for (int color = 0; color < 16; ++color) {
                            fOut.write(this.ULAplus[palette][color]);
                        }
                    }
                }
                fOut.close();
            }
            catch (FileNotFoundException excpt) {
                Logger.getLogger(Spectrum.class.getName()).log(Level.SEVERE, null, excpt);
            }
            catch (IOException ioExcpt) {
                Logger.getLogger(Spectrum.class.getName()).log(Level.SEVERE, null, ioExcpt);
            }
            return;
        }
        if (filename.getName().toLowerCase().endsWith(".png")) {
            try {
                ImageIO.write(this.tvImage, "png", filename);
            }
            catch (IOException ioExcpt) {
                Logger.getLogger(Spectrum.class.getName()).log(Level.SEVERE, null, ioExcpt);
            }
        }
    }
    
    public boolean isMuteSound() {
        return this.muted;
    }
    
    public void muteSound(final boolean state) {
        this.muted = state;
        if (this.muted) {
            this.disableSound();
        }
        else {
            this.enableSound();
        }
    }
    
    private void enableSound() {
        if (this.muted || this.enabledSound) {
            return;
        }
        this.audio.open(this.spectrumModel, this.ay8912, this.enabledAY, this.settings.getSpectrumSettings().isHifiSound() ? 48000 : 32000);
        this.enabledSound = true;
    }
    
    private void disableSound() {
        if (!this.enabledSound) {
            return;
        }
        this.enabledSound = false;
        this.audio.endFrame();
        this.audio.close();
    }
    
    public void changeSpeed(final int speed) {
        if (speed > 1) {
            this.disableSound();
            this.framesByInt = speed;
        }
        else {
            this.framesByInt = 1L;
            this.invalidateScreen(true);
            this.enableSound();
        }
    }
    
    public void toggleTape() {
        if (this.tape.isTapeReady()) {
            this.tape.play();
        }
        else {
            this.tape.stop();
        }
    }
    
    static void setvol() {
        Spectrum.sp_volt[0] = 0;
        Spectrum.sp_volt[1] = 0;
        Spectrum.sp_volt[2] = 11000;
        Spectrum.sp_volt[3] = 16500;
    }
    
    private void initGFX() {
        this.tvImage = new BufferedImage(320, 256, 1);
        this.gcTvImage = this.tvImage.createGraphics();
        this.inProgressImage = new BufferedImage(320, 256, 1);
        this.dataInProgress = ((DataBufferInt)this.inProgressImage.getRaster().getDataBuffer()).getBankData()[0];
        this.lastChgBorder = 0;
        Arrays.fill(this.dirtyByte, true);
        final boolean b = false;
        this.borderDirty = b;
        this.screenDirty = b;
        this.borderChanged = true;
        this.ULAplus = new int[4][16];
        this.ULAplusPalette = new int[4][16];
        this.ULAplusMode = false;
        this.paletteGroup = 0;
        for (int linea = 0; linea < 24; ++linea) {
            final int lsb = (linea & 0x7) << 5;
            final int msb = linea & 0x18;
            int addr = (msb << 8) + lsb;
            final int idx = linea << 3;
            for (int scan = 0; scan < 8; ++scan, addr += 256) {
                this.scrAddr[scan + idx] = 16384 + addr;
            }
        }
        for (int address = 16384; address < 22528; ++address) {
            final int row = (address & 0xE0) >>> 5 | (address & 0x1800) >>> 8;
            final int col = address & 0x1F;
            final int scan = (address & 0x700) >>> 8;
            this.repaintTable[address & 0x1FFF] = row * 2048 + scan * 256 + col * 8 >>> 8;
            this.bufAddr[address & 0x1FFF] = row * 2560 + (scan + 32) * 320 + col * 8 + 32;
            this.scr2attr[address & 0x1FFF] = 22528 + row * 32 + col;
        }
        for (int address = 22528; address < 23296; ++address) {
            this.attr2scr[address & 0x3FF] = (0x4000 | (address & 0x300) << 3 | (address & 0xFF));
        }
    }
    
    public BufferedImage getTvImage() {
        return this.tvImage;
    }
    
    public synchronized void toggleFlash() {
        this.flash = ((this.flash == 127) ? 255 : 127);
        for (int addrAttr = 22528; addrAttr < 23296; ++addrAttr) {
            if (this.memory.readScreenByte(addrAttr) < 0) {
                this.notifyScreenWrite(addrAttr);
            }
        }
    }
    
    private int tStatesToScrPix48k(int tstates) {
        tstates %= this.spectrumModel.tstatesFrame;
        int row = tstates / this.spectrumModel.tstatesLine;
        int col = tstates % this.spectrumModel.tstatesLine;
        if (row < 31 || row > 287) {
            return -255151942;
        }
        if (row == 31 && col < 208) {
            return -255151942;
        }
        if (row == 287 && col > 143) {
            return -255151942;
        }
        if (col > 143 && col < 208) {
            return -255151942;
        }
        if (row > 63 && row < 256 && col < 128) {
            return -255151942;
        }
        if (col > 176) {
            ++row;
            col -= 208;
        }
        else {
            col += 16;
        }
        row -= 32;
        return row * 320 + col * 2;
    }
    
    private int tStatesToScrPix128k(int tstates) {
        tstates %= this.spectrumModel.tstatesFrame;
        int row = tstates / this.spectrumModel.tstatesLine;
        int col = tstates % this.spectrumModel.tstatesLine;
        if (row < 30 || row > 286) {
            return -255151942;
        }
        if (row == 30 && col < 212) {
            return -255151942;
        }
        if (row == 286 && col > 143) {
            return -255151942;
        }
        if (col > 143 && col < 212) {
            return -255151942;
        }
        if (row > 62 && row < 255 && col < 128) {
            return -255151942;
        }
        if (col > 176) {
            ++row;
            col -= 212;
        }
        else {
            col += 16;
        }
        row -= 31;
        return row * 320 + col * 2;
    }
    
    public void updateBorder(int tstates) {
        int nowColor = Spectrum.Paleta[this.portFE & 0x7];
        if (this.ULAplusMode) {
            nowColor = this.ULAplusPalette[0][(this.portFE & 0x7) | 0x8];
        }
        else {
            nowColor = Spectrum.Paleta[this.portFE & 0x7];
        }
        if (tstates < this.lastChgBorder) {
            return;
        }
        tstates -= 4;
        while (this.lastChgBorder <= tstates) {
            final int idxColor = this.states2border[this.lastChgBorder];
            this.lastChgBorder += 4;
            if (idxColor != -255151942) {
                if (nowColor == this.dataInProgress[idxColor]) {
                    continue;
                }
                this.dataInProgress[idxColor] = nowColor;
                this.dataInProgress[idxColor + 1] = nowColor;
                this.dataInProgress[idxColor + 2] = nowColor;
                this.dataInProgress[idxColor + 3] = nowColor;
                this.dataInProgress[idxColor + 4] = nowColor;
                this.dataInProgress[idxColor + 5] = nowColor;
                this.dataInProgress[idxColor + 6] = nowColor;
                this.dataInProgress[idxColor + 7] = nowColor;
                ++this.nBorderChanges;
                this.borderDirty = true;
            }
        }
    }
    
    public void updateScreen(int fromTstates, final int toTstates) {
        while (fromTstates % 4 != 0) {
            ++fromTstates;
        }
        while (fromTstates <= toTstates) {
            int fromAddr = this.states2scr[fromTstates];
            if (fromAddr == -1 || !this.dirtyByte[fromAddr & 0x1FFF]) {
                fromTstates += 4;
            }
            else {
                if (this.firstLine == 0) {
                    final int n = fromAddr;
                    this.lastLine = n;
                    this.firstLine = n;
                }
                else {
                    this.lastLine = fromAddr;
                }
                final int column = fromAddr & 0x1F;
                if (column < this.leftCol) {
                    this.leftCol = column;
                }
                if (column > this.rightCol) {
                    this.rightCol = column;
                }
                final byte scrByte = this.memory.readScreenByte(fromAddr);
                fromAddr &= 0x1FFF;
                int attr = this.memory.readScreenByte(this.scr2attr[fromAddr]) & 0xFF;
                int addrBuf = this.bufAddr[fromAddr];
                int ink;
                int paper;
                if (this.ULAplusMode) {
                    ink = this.ULAplusPalette[attr >>> 6][attr & 0x7];
                    paper = this.ULAplusPalette[attr >>> 6][(attr & 0x38) >>> 3 | 0x8];
                }
                else {
                    if (attr > 127) {
                        attr &= this.flash;
                    }
                    ink = Spectrum.Ink[attr];
                    paper = Spectrum.Paper[attr];
                }
                for (int mask = 128; mask != 0; mask >>= 1) {
                    if ((scrByte & mask) != 0x0) {
                        this.dataInProgress[addrBuf++] = ink;
                    }
                    else {
                        this.dataInProgress[addrBuf++] = paper;
                    }
                }
                this.dirtyByte[fromAddr] = false;
                this.screenDirty = true;
                fromTstates += 4;
            }
        }
    }
    
    public void notifyScreenWrite(int address) {
        address &= 0x1FFF;
        if (address < 6144) {
            this.dirtyByte[address] = true;
        }
        else {
            final int addr = this.attr2scr[address & 0x3FF] & 0x1FFF;
            this.dirtyByte[addr] = true;
            this.dirtyByte[addr + 256] = true;
            this.dirtyByte[addr + 512] = true;
            this.dirtyByte[addr + 768] = true;
            this.dirtyByte[addr + 1024] = true;
            this.dirtyByte[addr + 1280] = true;
            this.dirtyByte[addr + 1536] = true;
            this.dirtyByte[addr + 1792] = true;
        }
    }
    
    public void invalidateScreen(final boolean invalidateBorder) {
        this.borderChanged = invalidateBorder;
        Arrays.fill(this.dirtyByte, true);
    }
    
    private void buildScreenTables48k() {
        Arrays.fill(this.states2scr, -1);
        for (int tstates = 14336; tstates < 57344; tstates += 4) {
            final int col = tstates % 224 / 4;
            if (col <= 31) {
                final int scan = tstates / 224 - 64;
                this.states2scr[tstates - 8] = this.scrAddr[scan] + col;
            }
        }
        Arrays.fill(this.states2border, -255151942);
        for (int tstates = this.spectrumModel.firstBorderUpdate; tstates < this.spectrumModel.lastBorderUpdate; tstates += 4) {
            this.states2border[tstates] = this.tStatesToScrPix48k(tstates);
            this.states2border[tstates + 1] = this.states2border[tstates];
            this.states2border[tstates + 2] = this.states2border[tstates];
            this.states2border[tstates + 3] = this.states2border[tstates];
        }
        Arrays.fill(Spectrum.delayTstates, (byte)0);
        for (int idx = 14335; idx < 57343; idx += 224) {
            for (int ndx = 0; ndx < 128; ndx += 8) {
                int frame = idx + ndx;
                Spectrum.delayTstates[frame++] = 6;
                Spectrum.delayTstates[frame++] = 5;
                Spectrum.delayTstates[frame++] = 4;
                Spectrum.delayTstates[frame++] = 3;
                Spectrum.delayTstates[frame++] = 2;
                Spectrum.delayTstates[frame++] = 1;
                Spectrum.delayTstates[frame++] = 0;
                Spectrum.delayTstates[frame++] = 0;
            }
        }
    }
    
    private void buildScreenTables128k() {
        Arrays.fill(this.states2scr, -1);
        for (int tstates = 14364; tstates < 58140; tstates += 4) {
            final int col = tstates % 228 / 4;
            if (col <= 31) {
                final int scan = tstates / 228 - 63;
                this.states2scr[tstates - 12] = this.scrAddr[scan] + col;
            }
        }
        Arrays.fill(this.states2border, -255151942);
        for (int tstates = 0; tstates < this.spectrumModel.tstatesFrame; tstates += 4) {
            this.states2border[tstates] = this.tStatesToScrPix128k(tstates);
            this.states2border[tstates + 1] = this.states2border[tstates];
            this.states2border[tstates + 2] = this.states2border[tstates];
            this.states2border[tstates + 3] = this.states2border[tstates];
        }
        Arrays.fill(Spectrum.delayTstates, (byte)0);
        for (int idx = 14361; idx < 58040; idx += 228) {
            for (int ndx = 0; ndx < 128; ndx += 8) {
                int frame = idx + ndx;
                Spectrum.delayTstates[frame++] = 6;
                Spectrum.delayTstates[frame++] = 5;
                Spectrum.delayTstates[frame++] = 4;
                Spectrum.delayTstates[frame++] = 3;
                Spectrum.delayTstates[frame++] = 2;
                Spectrum.delayTstates[frame++] = 1;
                Spectrum.delayTstates[frame++] = 0;
                Spectrum.delayTstates[frame++] = 0;
            }
        }
    }
    
    private void buildScreenTablesPlus3() {
        Arrays.fill(this.states2scr, -1);
        for (int tstates = 14364; tstates < 58140; tstates += 4) {
            final int col = tstates % 228 / 4;
            if (col <= 31) {
                final int scan = tstates / 228 - 63;
                this.states2scr[tstates - 8] = this.scrAddr[scan] + col;
            }
        }
        Arrays.fill(this.states2border, -255151942);
        for (int tstates = 0; tstates < this.spectrumModel.tstatesFrame; tstates += 4) {
            this.states2border[tstates] = this.tStatesToScrPix128k(tstates);
            this.states2border[tstates + 1] = this.states2border[tstates];
            this.states2border[tstates + 2] = this.states2border[tstates];
            this.states2border[tstates + 3] = this.states2border[tstates];
        }
        Arrays.fill(Spectrum.delayTstates, (byte)0);
        for (int idx = 14361; idx < 58040; idx += 228) {
            for (int ndx = 0; ndx < 128; ndx += 8) {
                int frame = idx + ndx;
                Spectrum.delayTstates[frame++] = 1;
                Spectrum.delayTstates[frame++] = 0;
                Spectrum.delayTstates[frame++] = 7;
                Spectrum.delayTstates[frame++] = 6;
                Spectrum.delayTstates[frame++] = 5;
                Spectrum.delayTstates[frame++] = 4;
                Spectrum.delayTstates[frame++] = 3;
                Spectrum.delayTstates[frame++] = 2;
            }
        }
    }
    
    @Override
    public void tapeStart() {
        if (this.settings.getTapeSettings().isAccelerateLoading()) {
            this.stopEmulation();
            this.framesByInt = 25L;
            new Thread() {
                @Override
                public void run() {
                    while (Spectrum.this.tape.isTapePlaying()) {
                        Spectrum.this.generateFrame();
                        Spectrum.this.drawFrame();
                    }
                    Spectrum.this.invalidateScreen(true);
                    Spectrum.this.framesByInt = 1L;
                    Spectrum.this.startEmulation();
                }
            }.start();
        }
    }
    
    @Override
    public void tapeStop() {
    }
    
    public boolean startRecording() {
        if (!this.tape.isTapeReady()) {
            return false;
        }
        if (!this.tape.startRecording()) {
            return false;
        }
        this.z80.setExecDone(true);
        return true;
    }
    
    public boolean stopRecording() {
        this.z80.setExecDone(false);
        this.tape.stopRecording();
        return true;
    }
    
    public boolean insertIF2Rom(final File filename) {
        return this.memory.insertIF2Rom(filename);
    }
    
    public void ejectIF2Rom() {
        this.memory.ejectIF2Rom();
    }
    
    public void setSzxTapeMode(final int mode) {
        this.szxTapeMode = mode;
    }
    
    static {
        delayTstates = new byte[MachineTypes.SPECTRUM128K.tstatesFrame + 100];
        sp_volt = new int[4];
        setvol();
        Paleta = new int[] { 0, 192, 12582912, 12583104, 49152, 49344, 12632064, 12632256, 0, 255, 16711680, 16711935, 65280, 65535, 16776960, 16777215 };
        Paper = new int[256];
        Ink = new int[256];
        for (int idx = 0; idx < 256; ++idx) {
            final int ink = (idx & 0x7) | (((idx & 0x40) != 0x0) ? 8 : 0);
            final int paper = (idx >>> 3 & 0x7) | (((idx & 0x40) != 0x0) ? 8 : 0);
            if (idx < 128) {
                Spectrum.Ink[idx] = Spectrum.Paleta[ink];
                Spectrum.Paper[idx] = Spectrum.Paleta[paper];
            }
            else {
                Spectrum.Ink[idx] = Spectrum.Paleta[paper];
                Spectrum.Paper[idx] = Spectrum.Paleta[ink];
            }
        }
    }
    
    public enum Joystick
    {
        NONE, 
        KEMPSTON, 
        SINCLAIR1, 
        SINCLAIR2, 
        CURSOR, 
        FULLER;
    }
}
