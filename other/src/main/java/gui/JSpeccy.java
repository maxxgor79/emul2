// 
// Decompiled by Procyon v0.5.36
// 

package gui;

import java.awt.EventQueue;
import machine.MachineTypes;
import javax.swing.plaf.basic.BasicFileChooserUI;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.KeyStroke;
import java.awt.Dimension;
import javax.swing.border.SoftBevelBorder;
import java.awt.Container;
import javax.swing.BoxLayout;
import java.awt.Toolkit;
import javax.swing.AbstractButton;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.util.ResourceBundle;
import java.awt.Frame;
import java.awt.event.KeyListener;
import java.awt.Component;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.xml.bind.JAXB;
import configuration.ObjectFactory;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBContext;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileOutputStream;
import javax.swing.JToolBar;
import javax.swing.JTable;
import javax.swing.JDialog;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import configuration.JSpeccySettingsType;
import javax.swing.ListSelectionModel;
import javax.swing.JFileChooser;
import java.io.File;
import machine.Spectrum;
import javax.swing.JFrame;

public class JSpeccy extends JFrame
{
    Spectrum spectrum;
    JSpeccyScreen jscr;
    File currentDirSnapshot;
    File currentDirSaveSnapshot;
    File currentDirTape;
    File currentDirSaveImage;
    File currentDirRom;
    JFileChooser openSnapshotDlg;
    JFileChooser saveSnapshotDlg;
    JFileChooser openTapeDlg;
    JFileChooser saveImageDlg;
    JFileChooser IF2RomDlg;
    String lastSnapshotDir;
    String lastTapeDir;
    File[] recentFile;
    ListSelectionModel lsm;
    JSpeccySettingsType settings;
    SettingsDialog settingsDialog;
    private JMenu IF2MediaMenu;
    private JMenuItem aboutHelpMenu;
    private JMenuItem browserTapeMediaMenu;
    private JMenuItem clearTapeMediaMenu;
    private JButton closeKeyboardHelper;
    private JButton closeTapeBrowserButton;
    private JMenuItem createTapeMediaMenu;
    private JRadioButtonMenuItem cursorJoystick;
    private JCheckBoxMenuItem doubleSizeOption;
    private JToggleButton doubleSizeToggleButton;
    private JMenuItem ejectIF2RomMediaMenu;
    private JRadioButton embeddedRadioButton;
    private JToggleButton fastEmulationToggleButton;
    private JMenu fileMenu;
    private JRadioButtonMenuItem fullerJoystick;
    private JMenuItem hardResetMachineMenu;
    private JButton hardResetSpectrumButton;
    private ButtonGroup hardwareButtonGroup;
    private JMenu hardwareMachineMenu;
    private JMenu helpMenu;
    private JRadioButton ignoreRadioButton;
    private JMenuItem imageHelpMenu;
    private JMenuItem insertIF2RomMediaMenu;
    private JLabel jLabel1;
    private JMenuBar jMenuBar1;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JPopupMenu.Separator jSeparator3;
    private JPopupMenu.Separator jSeparator4;
    private JPopupMenu.Separator jSeparator5;
    private JPopupMenu.Separator jSeparator6;
    private JPopupMenu.Separator jSeparator7;
    private ButtonGroup joystickButtonGroup;
    private JMenu joystickOptionMenu;
    private JRadioButtonMenuItem kempstonJoystick;
    private JDialog keyboardHelper;
    private JLabel keyboardImage;
    private JRadioButton linkedRadioButton;
    private JMenu machineMenu;
    private JMenu mediaMenu;
    private JLabel modelLabel;
    private JMenuItem nmiMachineMenu;
    private JRadioButtonMenuItem noneJoystick;
    private JMenuItem openSnapshot;
    private JButton openSnapshotButton;
    private JMenuItem openTapeMediaMenu;
    private JMenu optionsMenu;
    private JCheckBoxMenuItem pauseMachineMenu;
    private JToggleButton pauseToggleButton;
    private JMenuItem playTapeMediaMenu;
    private JMenuItem recentFileMenu0;
    private JMenuItem recentFileMenu1;
    private JMenuItem recentFileMenu2;
    private JMenuItem recentFileMenu3;
    private JMenuItem recentFileMenu4;
    private JMenu recentFilesMenu;
    private JMenuItem recordStartTapeMediaMenu;
    private JMenuItem recordStopTapeMediaMenu;
    private JMenuItem resetMachineMenu;
    private JButton resetSpectrumButton;
    private JMenuItem rewindTapeMediaMenu;
    private JMenuItem saveScreenShot;
    private JMenuItem saveSnapshot;
    private ButtonGroup saveSzxButtonGroup;
    private JPanel saveSzxChoosePanel;
    private JButton saveSzxCloseButton;
    private JDialog saveSzxTape;
    private JMenuItem settingsOptionsMenu;
    private JCheckBoxMenuItem silenceMachineMenu;
    private JToggleButton silenceSoundToggleButton;
    private JRadioButtonMenuItem sinclair1Joystick;
    private JRadioButtonMenuItem sinclair2Joystick;
    private JRadioButtonMenuItem spec128kHardware;
    private JRadioButtonMenuItem spec16kHardware;
    private JRadioButtonMenuItem spec48kHardware;
    private JRadioButtonMenuItem specPlus2AHardware;
    private JRadioButtonMenuItem specPlus2Hardware;
    private JRadioButtonMenuItem specPlus3Hardware;
    private JLabel speedLabel;
    private JDialog tapeBrowserDialog;
    private JTable tapeCatalog;
    private JLabel tapeFilename;
    private JLabel tapeFilenameLabel;
    private JLabel tapeLabel;
    private JMenu tapeMediaMenu;
    private JLabel tapeMessageInfo;
    private JMenuItem thisIsTheEndMyFriend;
    private JToolBar toolbarMenu;
    
    public JSpeccy() {
        this.recentFile = new File[5];
        this.initComponents();
        this.initEmulator();
    }
    
    private void verifyConfigFile(final boolean deleteFile) {
        final File file = new File("JSpeccy.xml");
        if (file.exists() && !deleteFile) {
            return;
        }
        if (deleteFile) {
            file.delete();
        }
        try {
            final InputStream input = Spectrum.class.getResourceAsStream("/schema/JSpeccy.xml");
            final FileOutputStream output = new FileOutputStream("JSpeccy.xml");
            for (int value = input.read(); value != -1; value = input.read()) {
                output.write(value & 0xFF);
            }
            input.close();
            output.close();
        }
        catch (FileNotFoundException notFoundExcpt) {
            Logger.getLogger(JSpeccy.class.getName()).log(Level.SEVERE, null, notFoundExcpt);
        }
        catch (IOException ioExcpt) {
            Logger.getLogger(JSpeccy.class.getName()).log(Level.SEVERE, null, ioExcpt);
        }
    }
    
    private void readSettingsFile() {
        this.verifyConfigFile(false);
        boolean readed = true;
        try {
            final JAXBContext jc = JAXBContext.newInstance("configuration");
            final Unmarshaller unmsh = jc.createUnmarshaller();
            final JAXBElement<?> settingsElement = (JAXBElement<?>)unmsh.unmarshal((InputStream)new FileInputStream("JSpeccy.xml"));
            this.settings = (JSpeccySettingsType)settingsElement.getValue();
        }
        catch (JAXBException jexcpt) {
            System.out.println("Something during unmarshalling go very bad!");
            readed = false;
        }
        catch (IOException ioexcpt) {
            System.out.println("Can't open the JSpeccy.xml configuration file");
        }
        if (readed) {
            return;
        }
        System.out.println("Trying to create a new one JSpeccy.xml for you");
        this.verifyConfigFile(true);
        try {
            final JAXBContext jc = JAXBContext.newInstance("configuration");
            final Unmarshaller unmsh = jc.createUnmarshaller();
            final JAXBElement<?> settingsElement = (JAXBElement<?>)unmsh.unmarshal((InputStream)new FileInputStream("JSpeccy.xml"));
            this.settings = (JSpeccySettingsType)settingsElement.getValue();
        }
        catch (JAXBException jexcpt) {
            System.out.println("Something during unmarshalling go very very bad!");
            readed = false;
        }
        catch (IOException ioexcpt) {
            System.out.println("Can't open the JSpeccy.xml configuration file anyway");
            System.exit(0);
        }
    }
    
    private void saveRecentFiles() {
        try {
            final JAXBContext jc = JAXBContext.newInstance("configuration");
            final Unmarshaller unmsh = jc.createUnmarshaller();
            final JAXBElement<?> settingsElement = (JAXBElement<?>)unmsh.unmarshal((InputStream)new FileInputStream("JSpeccy.xml"));
            this.settings = (JSpeccySettingsType)settingsElement.getValue();
        }
        catch (JAXBException jexcpt) {
            System.out.println("Something during unmarshalling go very bad!");
        }
        catch (IOException ioexcpt) {
            System.out.println("Can't open the JSpeccy.xml configuration file");
        }
        if (this.recentFile[0] != null) {
            this.settings.getRecentFilesSettings().setRecentFile0(this.recentFile[0].getAbsolutePath());
        }
        if (this.recentFile[1] != null) {
            this.settings.getRecentFilesSettings().setRecentFile1(this.recentFile[1].getAbsolutePath());
        }
        if (this.recentFile[2] != null) {
            this.settings.getRecentFilesSettings().setRecentFile2(this.recentFile[2].getAbsolutePath());
        }
        if (this.recentFile[3] != null) {
            this.settings.getRecentFilesSettings().setRecentFile3(this.recentFile[3].getAbsolutePath());
        }
        if (this.recentFile[4] != null) {
            this.settings.getRecentFilesSettings().setRecentFile4(this.recentFile[4].getAbsolutePath());
        }
        this.settings.getRecentFilesSettings().setLastSnapshotDir(this.lastSnapshotDir);
        this.settings.getRecentFilesSettings().setLastTapeDir(this.lastTapeDir);
        try {
            final BufferedOutputStream fOut = new BufferedOutputStream(new FileOutputStream("JSpeccy.xml"));
            final JAXBElement<JSpeccySettingsType> confElement = new ObjectFactory().createJSpeccySettings(this.settings);
            JAXB.marshal((Object)confElement, (OutputStream)fOut);
            try {
                fOut.close();
            }
            catch (IOException ex) {
                Logger.getLogger(SettingsDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch (FileNotFoundException ex2) {
            Logger.getLogger(SettingsDialog.class.getName()).log(Level.SEVERE, null, ex2);
        }
    }
    
    private void initEmulator() {
        this.readSettingsFile();
        this.spectrum = new Spectrum(this.settings);
        this.jscr = new JSpeccyScreen();
        this.spectrum.setScreenComponent(this.jscr);
        this.jscr.setTvImage(this.spectrum.getTvImage());
        this.spectrum.setInfoLabels(this.modelLabel, this.speedLabel, this.tapeFilename);
        this.spectrum.setHardwareMenuItems(this.spec16kHardware, this.spec48kHardware, this.spec128kHardware, this.specPlus2Hardware, this.specPlus2AHardware, this.specPlus3Hardware);
        this.spectrum.setJoystickMenuItems(this.noneJoystick, this.kempstonJoystick, this.sinclair1Joystick, this.sinclair2Joystick, this.cursorJoystick, this.fullerJoystick);
        this.spectrum.setMenuItems(this.insertIF2RomMediaMenu, this.ejectIF2RomMediaMenu, this.playTapeMediaMenu);
        this.spectrum.tape.setTapeIcon(this.tapeLabel);
        this.tapeCatalog.setModel(this.spectrum.tape.getTapeTableModel());
        this.tapeCatalog.getColumnModel().getColumn(0).setMaxWidth(150);
        (this.lsm = this.tapeCatalog.getSelectionModel()).addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(final ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && event.getLastIndex() != -1) {
                    JSpeccy.this.spectrum.tape.setSelectedBlock(JSpeccy.this.lsm.getLeadSelectionIndex());
                }
            }
        });
        this.spectrum.tape.setListSelectionModel(this.lsm);
        this.getContentPane().add(this.jscr, "Center");
        this.pack();
        this.addKeyListener(this.spectrum.getKeyboard());
        switch (this.settings.getSpectrumSettings().getDefaultModel()) {
            case 0: {
                this.spec16kHardware.setSelected(true);
                break;
            }
            case 2: {
                this.spec128kHardware.setSelected(true);
                break;
            }
            case 3: {
                this.specPlus2Hardware.setSelected(true);
                break;
            }
            case 4: {
                this.specPlus2AHardware.setSelected(true);
                break;
            }
            case 5: {
                this.specPlus3Hardware.setSelected(true);
                break;
            }
            default: {
                this.spec48kHardware.setSelected(true);
                break;
            }
        }
        switch (this.settings.getKeyboardJoystickSettings().getJoystickModel()) {
            case 1: {
                this.kempstonJoystick.setSelected(true);
                break;
            }
            case 2: {
                this.sinclair1Joystick.setSelected(true);
                break;
            }
            case 3: {
                this.sinclair2Joystick.setSelected(true);
                break;
            }
            case 4: {
                this.cursorJoystick.setSelected(true);
                break;
            }
            default: {
                this.noneJoystick.setSelected(true);
                break;
            }
        }
        if (this.settings.getSpectrumSettings().isMutedSound()) {
            this.silenceMachineMenu.setSelected(true);
            this.silenceSoundToggleButton.setSelected(true);
        }
        if (this.settings.getSpectrumSettings().isDoubleSize()) {
            this.jscr.toggleDoubleSize();
            this.doubleSizeOption.setSelected(true);
            this.doubleSizeToggleButton.setSelected(true);
            this.pack();
        }
        if (this.settings.getRecentFilesSettings().getRecentFile0() != null && !this.settings.getRecentFilesSettings().getRecentFile0().isEmpty()) {
            this.recentFile[0] = new File(this.settings.getRecentFilesSettings().getRecentFile0());
            this.recentFileMenu0.setText(this.recentFile[0].getName());
            this.recentFileMenu0.setToolTipText(this.recentFile[0].getAbsolutePath());
            this.recentFileMenu0.setEnabled(true);
        }
        if (this.settings.getRecentFilesSettings().getRecentFile1() != null && !this.settings.getRecentFilesSettings().getRecentFile1().isEmpty()) {
            this.recentFile[1] = new File(this.settings.getRecentFilesSettings().getRecentFile1());
            this.recentFileMenu1.setText(this.recentFile[1].getName());
            this.recentFileMenu1.setToolTipText(this.recentFile[1].getAbsolutePath());
            this.recentFileMenu1.setEnabled(true);
        }
        if (this.settings.getRecentFilesSettings().getRecentFile2() != null && !this.settings.getRecentFilesSettings().getRecentFile2().isEmpty()) {
            this.recentFile[2] = new File(this.settings.getRecentFilesSettings().getRecentFile2());
            this.recentFileMenu2.setText(this.recentFile[2].getName());
            this.recentFileMenu2.setToolTipText(this.recentFile[2].getAbsolutePath());
            this.recentFileMenu2.setEnabled(true);
        }
        if (this.settings.getRecentFilesSettings().getRecentFile3() != null && !this.settings.getRecentFilesSettings().getRecentFile3().isEmpty()) {
            this.recentFile[3] = new File(this.settings.getRecentFilesSettings().getRecentFile3());
            this.recentFileMenu3.setText(this.recentFile[3].getName());
            this.recentFileMenu3.setToolTipText(this.recentFile[3].getAbsolutePath());
            this.recentFileMenu3.setEnabled(true);
        }
        if (this.settings.getRecentFilesSettings().getRecentFile4() != null && !this.settings.getRecentFilesSettings().getRecentFile4().isEmpty()) {
            this.recentFile[4] = new File(this.settings.getRecentFilesSettings().getRecentFile4());
            this.recentFileMenu4.setText(this.recentFile[4].getName());
            this.recentFileMenu4.setToolTipText(this.recentFile[4].getAbsolutePath());
            this.recentFileMenu4.setEnabled(true);
        }
        if (!this.settings.getRecentFilesSettings().getLastSnapshotDir().isEmpty()) {
            this.lastSnapshotDir = this.settings.getRecentFilesSettings().getLastSnapshotDir();
        }
        else {
            this.lastSnapshotDir = "/home/jsanchez/Spectrum";
        }
        if (!this.settings.getRecentFilesSettings().getLastTapeDir().isEmpty()) {
            this.lastTapeDir = this.settings.getRecentFilesSettings().getLastTapeDir();
        }
        else {
            this.lastTapeDir = "/home/jsanchez/Spectrum";
        }
        this.settingsDialog = new SettingsDialog(this.settings);
        this.spectrum.start();
    }
    
    private void rotateRecentFile(final File lastname) {
        this.recentFile[4] = this.recentFile[3];
        this.recentFile[3] = this.recentFile[2];
        this.recentFile[2] = this.recentFile[1];
        this.recentFile[1] = this.recentFile[0];
        this.recentFile[0] = lastname;
        if (this.recentFile[0] != null && !this.recentFile[0].getName().isEmpty()) {
            this.recentFileMenu0.setText(this.recentFile[0].getName());
            this.recentFileMenu0.setToolTipText(this.recentFile[0].getAbsolutePath());
            this.recentFileMenu0.setEnabled(true);
            this.settings.getRecentFilesSettings().setRecentFile0(this.recentFile[0].getAbsolutePath());
        }
        if (this.recentFile[1] != null && !this.recentFile[1].getName().isEmpty()) {
            this.recentFileMenu1.setText(this.recentFile[1].getName());
            this.recentFileMenu1.setToolTipText(this.recentFile[1].getAbsolutePath());
            this.recentFileMenu1.setEnabled(true);
            this.settings.getRecentFilesSettings().setRecentFile1(this.recentFile[1].getAbsolutePath());
        }
        if (this.recentFile[2] != null && !this.recentFile[2].getName().isEmpty()) {
            this.recentFileMenu2.setText(this.recentFile[2].getName());
            this.recentFileMenu2.setToolTipText(this.recentFile[2].getAbsolutePath());
            this.recentFileMenu2.setEnabled(true);
            this.settings.getRecentFilesSettings().setRecentFile2(this.recentFile[2].getAbsolutePath());
        }
        if (this.recentFile[3] != null && !this.recentFile[3].getName().isEmpty()) {
            this.recentFileMenu3.setText(this.recentFile[3].getName());
            this.recentFileMenu3.setToolTipText(this.recentFile[3].getAbsolutePath());
            this.recentFileMenu3.setEnabled(true);
            this.settings.getRecentFilesSettings().setRecentFile3(this.recentFile[3].getAbsolutePath());
        }
        if (this.recentFile[4] != null && !this.recentFile[4].getName().isEmpty()) {
            this.recentFileMenu4.setText(this.recentFile[4].getName());
            this.recentFileMenu4.setToolTipText(this.recentFile[4].getAbsolutePath());
            this.recentFileMenu4.setEnabled(true);
            this.settings.getRecentFilesSettings().setRecentFile4(this.recentFile[4].getAbsolutePath());
        }
    }
    
    private void initComponents() {
        this.keyboardHelper = new JDialog(this);
        this.keyboardImage = new JLabel();
        this.closeKeyboardHelper = new JButton();
        this.joystickButtonGroup = new ButtonGroup();
        this.hardwareButtonGroup = new ButtonGroup();
        this.tapeBrowserDialog = new JDialog();
        this.jScrollPane1 = new JScrollPane();
        this.tapeCatalog = new JTable();
        this.jPanel2 = new JPanel();
        this.closeTapeBrowserButton = new JButton();
        this.tapeFilename = new JLabel();
        this.saveSzxTape = new JDialog();
        this.jPanel4 = new JPanel();
        this.tapeMessageInfo = new JLabel();
        this.tapeFilenameLabel = new JLabel();
        this.saveSzxChoosePanel = new JPanel();
        this.ignoreRadioButton = new JRadioButton();
        this.linkedRadioButton = new JRadioButton();
        this.embeddedRadioButton = new JRadioButton();
        this.jPanel3 = new JPanel();
        this.saveSzxCloseButton = new JButton();
        this.saveSzxButtonGroup = new ButtonGroup();
        this.jPanel1 = new JPanel();
        this.modelLabel = new JLabel();
        this.jLabel1 = new JLabel();
        this.tapeLabel = new JLabel();
        this.jSeparator2 = new JSeparator();
        this.speedLabel = new JLabel();
        this.toolbarMenu = new JToolBar();
        this.openSnapshotButton = new JButton();
        this.pauseToggleButton = new JToggleButton();
        this.fastEmulationToggleButton = new JToggleButton();
        this.doubleSizeToggleButton = new JToggleButton();
        this.silenceSoundToggleButton = new JToggleButton();
        this.resetSpectrumButton = new JButton();
        this.hardResetSpectrumButton = new JButton();
        this.jMenuBar1 = new JMenuBar();
        this.fileMenu = new JMenu();
        this.openSnapshot = new JMenuItem();
        this.saveSnapshot = new JMenuItem();
        this.jSeparator4 = new JPopupMenu.Separator();
        this.saveScreenShot = new JMenuItem();
        this.jSeparator1 = new JSeparator();
        this.recentFilesMenu = new JMenu();
        this.recentFileMenu0 = new JMenuItem();
        this.recentFileMenu1 = new JMenuItem();
        this.recentFileMenu2 = new JMenuItem();
        this.recentFileMenu3 = new JMenuItem();
        this.recentFileMenu4 = new JMenuItem();
        this.jSeparator7 = new JPopupMenu.Separator();
        this.thisIsTheEndMyFriend = new JMenuItem();
        this.optionsMenu = new JMenu();
        this.doubleSizeOption = new JCheckBoxMenuItem();
        this.joystickOptionMenu = new JMenu();
        this.noneJoystick = new JRadioButtonMenuItem();
        this.kempstonJoystick = new JRadioButtonMenuItem();
        this.sinclair1Joystick = new JRadioButtonMenuItem();
        this.sinclair2Joystick = new JRadioButtonMenuItem();
        this.cursorJoystick = new JRadioButtonMenuItem();
        this.fullerJoystick = new JRadioButtonMenuItem();
        this.settingsOptionsMenu = new JMenuItem();
        this.machineMenu = new JMenu();
        this.pauseMachineMenu = new JCheckBoxMenuItem();
        this.silenceMachineMenu = new JCheckBoxMenuItem();
        this.resetMachineMenu = new JMenuItem();
        this.hardResetMachineMenu = new JMenuItem();
        this.nmiMachineMenu = new JMenuItem();
        this.jSeparator3 = new JPopupMenu.Separator();
        this.hardwareMachineMenu = new JMenu();
        this.spec16kHardware = new JRadioButtonMenuItem();
        this.spec48kHardware = new JRadioButtonMenuItem();
        this.spec128kHardware = new JRadioButtonMenuItem();
        this.specPlus2Hardware = new JRadioButtonMenuItem();
        this.specPlus2AHardware = new JRadioButtonMenuItem();
        this.specPlus3Hardware = new JRadioButtonMenuItem();
        this.mediaMenu = new JMenu();
        this.tapeMediaMenu = new JMenu();
        this.openTapeMediaMenu = new JMenuItem();
        this.playTapeMediaMenu = new JMenuItem();
        this.browserTapeMediaMenu = new JMenuItem();
        this.rewindTapeMediaMenu = new JMenuItem();
        this.jSeparator5 = new JPopupMenu.Separator();
        this.createTapeMediaMenu = new JMenuItem();
        this.clearTapeMediaMenu = new JMenuItem();
        this.jSeparator6 = new JPopupMenu.Separator();
        this.recordStartTapeMediaMenu = new JMenuItem();
        this.recordStopTapeMediaMenu = new JMenuItem();
        this.IF2MediaMenu = new JMenu();
        this.insertIF2RomMediaMenu = new JMenuItem();
        this.ejectIF2RomMediaMenu = new JMenuItem();
        this.helpMenu = new JMenu();
        this.imageHelpMenu = new JMenuItem();
        this.aboutHelpMenu = new JMenuItem();
        final ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle");
        this.keyboardHelper.setTitle(bundle.getString("JSpeccy.keyboardHelper.title"));
        this.keyboardImage.setIcon(new ImageIcon(this.getClass().getResource("/icons/Keyboard48k.png")));
        this.keyboardImage.setText(bundle.getString("JSpeccy.keyboardImage.text"));
        this.keyboardHelper.getContentPane().add(this.keyboardImage, "First");
        this.closeKeyboardHelper.setText(bundle.getString("JSpeccy.closeKeyboardHelper.text"));
        this.closeKeyboardHelper.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.closeKeyboardHelperActionPerformed(evt);
            }
        });
        this.keyboardHelper.getContentPane().add(this.closeKeyboardHelper, "Last");
        this.tapeBrowserDialog.setTitle(bundle.getString("JSpeccy.tapeBrowserDialog.title"));
        this.tapeCatalog.setModel(new DefaultTableModel(new Object[][] { { null, null, null } }, new String[] { "Block Number", "Block Type", "Block information" }) {
            boolean[] canEdit = { false, false, false };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.tapeCatalog.setAutoResizeMode(3);
        this.tapeCatalog.setSelectionMode(0);
        this.tapeCatalog.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane1.setViewportView(this.tapeCatalog);
        this.tapeCatalog.getColumnModel().getSelectionModel().setSelectionMode(0);
        this.tapeCatalog.getColumnModel().getColumn(0).setResizable(false);
        this.tapeCatalog.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("JSpeccy.tapeCatalog.columnModel.title0"));
        this.tapeCatalog.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("JSpeccy.tapeCatalog.columnModel.title1"));
        this.tapeCatalog.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("JSpeccy.tapeCatalog.columnModel.title2"));
        this.tapeBrowserDialog.getContentPane().add(this.jScrollPane1, "Center");
        this.closeTapeBrowserButton.setText(bundle.getString("JSpeccy.closeTapeBrowserButton.text"));
        this.closeTapeBrowserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.closeTapeBrowserButtonActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.closeTapeBrowserButton);
        this.tapeBrowserDialog.getContentPane().add(this.jPanel2, "Last");
        this.tapeFilename.setHorizontalAlignment(0);
        this.tapeFilename.setText(bundle.getString("JSpeccy.tapeFilename.text"));
        this.tapeBrowserDialog.getContentPane().add(this.tapeFilename, "First");
        this.saveSzxTape.setTitle(bundle.getString("JSpeccy.saveSzxTapeDialog.title.text"));
        this.saveSzxTape.setModal(true);
        this.jPanel4.setLayout(new GridLayout(3, 0, 5, 5));
        this.tapeMessageInfo.setHorizontalAlignment(0);
        this.tapeMessageInfo.setText(bundle.getString("JSpeccy.tapeMessageInfo.text"));
        this.jPanel4.add(this.tapeMessageInfo);
        this.tapeFilenameLabel.setForeground(new Color(204, 0, 0));
        this.tapeFilenameLabel.setHorizontalAlignment(0);
        this.jPanel4.add(this.tapeFilenameLabel);
        this.saveSzxChoosePanel.setBorder(BorderFactory.createTitledBorder(bundle.getString("JSpeccy.saveSzxChoosePanel.title")));
        this.saveSzxButtonGroup.add(this.ignoreRadioButton);
        this.ignoreRadioButton.setSelected(true);
        this.ignoreRadioButton.setText(bundle.getString("JSpeccy.ignoreRadioButton.text"));
        this.ignoreRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.ignoreRadioButtonActionPerformed(evt);
            }
        });
        this.saveSzxChoosePanel.add(this.ignoreRadioButton);
        this.saveSzxButtonGroup.add(this.linkedRadioButton);
        this.linkedRadioButton.setText(bundle.getString("JSpeccy.linkedRadioButton.text"));
        this.linkedRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.linkedRadioButtonActionPerformed(evt);
            }
        });
        this.saveSzxChoosePanel.add(this.linkedRadioButton);
        this.saveSzxButtonGroup.add(this.embeddedRadioButton);
        this.embeddedRadioButton.setText(bundle.getString("JSpeccy.embeddedRadioButton.text"));
        this.embeddedRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.embeddedRadioButtonActionPerformed(evt);
            }
        });
        this.saveSzxChoosePanel.add(this.embeddedRadioButton);
        this.jPanel4.add(this.saveSzxChoosePanel);
        this.saveSzxTape.getContentPane().add(this.jPanel4, "Center");
        this.saveSzxCloseButton.setText(bundle.getString("JSpeccy.saveSzxCloseButton.text"));
        this.saveSzxCloseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.saveSzxCloseButtonActionPerformed(evt);
            }
        });
        this.jPanel3.add(this.saveSzxCloseButton);
        this.saveSzxTape.getContentPane().add(this.jPanel3, "Last");
        this.setDefaultCloseOperation(3);
        this.setTitle(bundle.getString("JSpeccy.title"));
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(JSpeccy.class.getResource("/icons/JSpeccy48x48.png")));
        this.setResizable(false);
        this.jPanel1.setBorder(BorderFactory.createEtchedBorder());
        this.jPanel1.setLayout(new BoxLayout(this.jPanel1, 2));
        this.modelLabel.setText(bundle.getString("JSpeccy.modelLabel.text"));
        this.modelLabel.setToolTipText(bundle.getString("JSpeccy.modelLabel.toolTipText"));
        this.modelLabel.setBorder(new SoftBevelBorder(0));
        this.jPanel1.add(this.modelLabel);
        this.jLabel1.setText(bundle.getString("JSpeccy.jLabel1.text"));
        this.jLabel1.setMaximumSize(new Dimension(32767, 16));
        this.jLabel1.setPreferredSize(new Dimension(100, 16));
        this.jPanel1.add(this.jLabel1);
        this.tapeLabel.setHorizontalAlignment(0);
        this.tapeLabel.setIcon(new ImageIcon(this.getClass().getResource("/icons/Akai24x24.png")));
        this.tapeLabel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        this.tapeLabel.setEnabled(false);
        this.tapeLabel.setHorizontalTextPosition(0);
        this.tapeLabel.setPreferredSize(new Dimension(36, 26));
        this.tapeLabel.setRequestFocusEnabled(false);
        this.jPanel1.add(this.tapeLabel);
        this.jSeparator2.setOrientation(1);
        this.jSeparator2.setMaximumSize(new Dimension(5, 32767));
        this.jSeparator2.setMinimumSize(new Dimension(3, 16));
        this.jSeparator2.setPreferredSize(new Dimension(3, 16));
        this.jSeparator2.setRequestFocusEnabled(false);
        this.jPanel1.add(this.jSeparator2);
        this.speedLabel.setHorizontalAlignment(4);
        this.speedLabel.setText(bundle.getString("JSpeccy.speedLabel.text"));
        this.speedLabel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        this.speedLabel.setMaximumSize(new Dimension(75, 18));
        this.speedLabel.setMinimumSize(new Dimension(40, 18));
        this.speedLabel.setPreferredSize(new Dimension(50, 18));
        this.speedLabel.setRequestFocusEnabled(false);
        this.jPanel1.add(this.speedLabel);
        this.getContentPane().add(this.jPanel1, "Last");
        this.toolbarMenu.setRollover(true);
        this.openSnapshotButton.setIcon(new ImageIcon(this.getClass().getResource("/icons/fileopen.png")));
        this.openSnapshotButton.setToolTipText(bundle.getString("JSpeccy.openSnapshotButton.toolTipText"));
        this.openSnapshotButton.setFocusable(false);
        this.openSnapshotButton.setHorizontalTextPosition(0);
        this.openSnapshotButton.setVerticalTextPosition(3);
        this.openSnapshotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.openSnapshotActionPerformed(evt);
            }
        });
        this.toolbarMenu.add(this.openSnapshotButton);
        this.pauseToggleButton.setIcon(new ImageIcon(this.getClass().getResource("/icons/player_pause.png")));
        this.pauseToggleButton.setToolTipText(bundle.getString("JSpeccy.pauseToggleButton.toolTipText"));
        this.pauseToggleButton.setFocusable(false);
        this.pauseToggleButton.setHorizontalTextPosition(0);
        this.pauseToggleButton.setSelectedIcon(new ImageIcon(this.getClass().getResource("/icons/player_play.png")));
        this.pauseToggleButton.setVerticalTextPosition(3);
        this.pauseToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.pauseMachineMenuActionPerformed(evt);
            }
        });
        this.toolbarMenu.add(this.pauseToggleButton);
        this.fastEmulationToggleButton.setIcon(new ImageIcon(this.getClass().getResource("/icons/player_fwd.png")));
        this.fastEmulationToggleButton.setText(bundle.getString("JSpeccy.fastEmulationToggleButton.text"));
        this.fastEmulationToggleButton.setToolTipText(bundle.getString("JSpeccy.fastEmulationToggleButton.toolTipText"));
        this.fastEmulationToggleButton.setFocusable(false);
        this.fastEmulationToggleButton.setHorizontalTextPosition(0);
        this.fastEmulationToggleButton.setVerticalTextPosition(3);
        this.fastEmulationToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.fastEmulationToggleButtonActionPerformed(evt);
            }
        });
        this.toolbarMenu.add(this.fastEmulationToggleButton);
        this.doubleSizeToggleButton.setIcon(new ImageIcon(this.getClass().getResource("/icons/viewmag+.png")));
        this.doubleSizeToggleButton.setToolTipText(bundle.getString("JSpeccy.doubleSizeToggleButton.toolTipText"));
        this.doubleSizeToggleButton.setFocusable(false);
        this.doubleSizeToggleButton.setHorizontalTextPosition(0);
        this.doubleSizeToggleButton.setSelectedIcon(new ImageIcon(this.getClass().getResource("/icons/viewmag-.png")));
        this.doubleSizeToggleButton.setVerticalTextPosition(3);
        this.doubleSizeToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.doubleSizeOptionActionPerformed(evt);
            }
        });
        this.toolbarMenu.add(this.doubleSizeToggleButton);
        this.silenceSoundToggleButton.setIcon(new ImageIcon(this.getClass().getResource("/icons/sound-on-16x16.png")));
        this.silenceSoundToggleButton.setToolTipText(bundle.getString("JSpeccy.silenceSoundToggleButton.toolTipText"));
        this.silenceSoundToggleButton.setFocusable(false);
        this.silenceSoundToggleButton.setHorizontalTextPosition(0);
        this.silenceSoundToggleButton.setSelectedIcon(new ImageIcon(this.getClass().getResource("/icons/sound-off-16x16.png")));
        this.silenceSoundToggleButton.setVerticalTextPosition(3);
        this.silenceSoundToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.silenceSoundToggleButtonActionPerformed(evt);
            }
        });
        this.toolbarMenu.add(this.silenceSoundToggleButton);
        this.resetSpectrumButton.setIcon(new ImageIcon(this.getClass().getResource("/icons/shutdown.png")));
        this.resetSpectrumButton.setToolTipText(bundle.getString("JSpeccy.resetSpectrumButton.toolTipText"));
        this.resetSpectrumButton.setFocusable(false);
        this.resetSpectrumButton.setHorizontalTextPosition(0);
        this.resetSpectrumButton.setVerticalTextPosition(3);
        this.resetSpectrumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.resetMachineMenuActionPerformed(evt);
            }
        });
        this.toolbarMenu.add(this.resetSpectrumButton);
        this.hardResetSpectrumButton.setIcon(new ImageIcon(this.getClass().getResource("/icons/exit.png")));
        this.hardResetSpectrumButton.setToolTipText(bundle.getString("JSpeccy.hardResetSpectrumButton.toolTipText"));
        this.hardResetSpectrumButton.setFocusable(false);
        this.hardResetSpectrumButton.setHorizontalTextPosition(0);
        this.hardResetSpectrumButton.setVerticalTextPosition(3);
        this.hardResetSpectrumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.hardResetSpectrumButtonActionPerformed(evt);
            }
        });
        this.toolbarMenu.add(this.hardResetSpectrumButton);
        this.getContentPane().add(this.toolbarMenu, "First");
        this.fileMenu.setText(bundle.getString("JSpeccy.fileMenu.text"));
        this.openSnapshot.setAccelerator(KeyStroke.getKeyStroke(114, 0));
        this.openSnapshot.setText(bundle.getString("JSpeccy.openSnapshot.text"));
        this.openSnapshot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.openSnapshotActionPerformed(evt);
            }
        });
        this.fileMenu.add(this.openSnapshot);
        this.saveSnapshot.setAccelerator(KeyStroke.getKeyStroke(113, 0));
        this.saveSnapshot.setText(bundle.getString("JSpeccy.saveSnapshot.text"));
        this.saveSnapshot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.saveSnapshotActionPerformed(evt);
            }
        });
        this.fileMenu.add(this.saveSnapshot);
        this.fileMenu.add(this.jSeparator4);
        this.saveScreenShot.setText(bundle.getString("JSpeccy.saveScreenShot.text"));
        this.saveScreenShot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.saveScreenShotActionPerformed(evt);
            }
        });
        this.fileMenu.add(this.saveScreenShot);
        this.fileMenu.add(this.jSeparator1);
        this.recentFilesMenu.setText(bundle.getString("JSpeccy.recentFilesMenu.text"));
        this.recentFileMenu0.setText(bundle.getString("JSpeccy.recentFileMenu0.text"));
        this.recentFileMenu0.setEnabled(false);
        this.recentFileMenu0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.recentFileMenu0ActionPerformed(evt);
            }
        });
        this.recentFilesMenu.add(this.recentFileMenu0);
        this.recentFileMenu1.setText(bundle.getString("JSpeccy.recentFileMenu0.text"));
        this.recentFileMenu1.setEnabled(false);
        this.recentFileMenu1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.recentFileMenu1ActionPerformed(evt);
            }
        });
        this.recentFilesMenu.add(this.recentFileMenu1);
        this.recentFileMenu2.setText(bundle.getString("JSpeccy.recentFileMenu0.text"));
        this.recentFileMenu2.setEnabled(false);
        this.recentFileMenu2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.recentFileMenu2ActionPerformed(evt);
            }
        });
        this.recentFilesMenu.add(this.recentFileMenu2);
        this.recentFileMenu3.setText(bundle.getString("JSpeccy.recentFileMenu0.text"));
        this.recentFileMenu3.setEnabled(false);
        this.recentFileMenu3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.recentFileMenu3ActionPerformed(evt);
            }
        });
        this.recentFilesMenu.add(this.recentFileMenu3);
        this.recentFileMenu4.setText(bundle.getString("JSpeccy.recentFileMenu0.text"));
        this.recentFileMenu4.setEnabled(false);
        this.recentFileMenu4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.recentFileMenu4ActionPerformed(evt);
            }
        });
        this.recentFilesMenu.add(this.recentFileMenu4);
        this.fileMenu.add(this.recentFilesMenu);
        this.fileMenu.add(this.jSeparator7);
        this.thisIsTheEndMyFriend.setText(bundle.getString("JSpeccy.thisIsTheEndMyFriend.text"));
        this.thisIsTheEndMyFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.thisIsTheEndMyFriendActionPerformed(evt);
            }
        });
        this.fileMenu.add(this.thisIsTheEndMyFriend);
        this.jMenuBar1.add(this.fileMenu);
        this.optionsMenu.setText(bundle.getString("JSpeccy.optionsMenu.text"));
        this.doubleSizeOption.setText(bundle.getString("JSpeccy.doubleSizeOption.text"));
        this.doubleSizeOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.doubleSizeOptionActionPerformed(evt);
            }
        });
        this.optionsMenu.add(this.doubleSizeOption);
        this.joystickOptionMenu.setText(bundle.getString("JSpeccy.joystickOptionMenu.text"));
        this.joystickButtonGroup.add(this.noneJoystick);
        this.noneJoystick.setSelected(true);
        this.noneJoystick.setText(bundle.getString("JSpeccy.noneJoystick.text"));
        this.noneJoystick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.noneJoystickActionPerformed(evt);
            }
        });
        this.joystickOptionMenu.add(this.noneJoystick);
        this.joystickButtonGroup.add(this.kempstonJoystick);
        this.kempstonJoystick.setText(bundle.getString("JSpeccy.kempstonJoystick.text"));
        this.kempstonJoystick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.kempstonJoystickActionPerformed(evt);
            }
        });
        this.joystickOptionMenu.add(this.kempstonJoystick);
        this.joystickButtonGroup.add(this.sinclair1Joystick);
        this.sinclair1Joystick.setText(bundle.getString("JSpeccy.sinclair1Joystick.text"));
        this.sinclair1Joystick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.sinclair1JoystickActionPerformed(evt);
            }
        });
        this.joystickOptionMenu.add(this.sinclair1Joystick);
        this.joystickButtonGroup.add(this.sinclair2Joystick);
        this.sinclair2Joystick.setText(bundle.getString("JSpeccy.sinclair2Joystick.text"));
        this.sinclair2Joystick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.sinclair2JoystickActionPerformed(evt);
            }
        });
        this.joystickOptionMenu.add(this.sinclair2Joystick);
        this.joystickButtonGroup.add(this.cursorJoystick);
        this.cursorJoystick.setText(bundle.getString("JSpeccy.cursorJoystick.text"));
        this.cursorJoystick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.cursorJoystickActionPerformed(evt);
            }
        });
        this.joystickOptionMenu.add(this.cursorJoystick);
        this.joystickButtonGroup.add(this.fullerJoystick);
        this.fullerJoystick.setText(bundle.getString("JSpeccy.fullerJoystick.text"));
        this.fullerJoystick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.fullerJoystickActionPerformed(evt);
            }
        });
        this.joystickOptionMenu.add(this.fullerJoystick);
        this.optionsMenu.add(this.joystickOptionMenu);
        this.settingsOptionsMenu.setAccelerator(KeyStroke.getKeyStroke(115, 0));
        this.settingsOptionsMenu.setText(bundle.getString("JSpeccy.settings.text"));
        this.settingsOptionsMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.settingsOptionsMenuActionPerformed(evt);
            }
        });
        this.optionsMenu.add(this.settingsOptionsMenu);
        this.jMenuBar1.add(this.optionsMenu);
        this.machineMenu.setText(bundle.getString("JSpeccy.machineMenu.text"));
        this.machineMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.silenceSoundToggleButtonActionPerformed(evt);
            }
        });
        this.pauseMachineMenu.setAccelerator(KeyStroke.getKeyStroke(19, 0));
        this.pauseMachineMenu.setText(bundle.getString("JSpeccy.pauseMachineMenu.text"));
        this.pauseMachineMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.pauseMachineMenuActionPerformed(evt);
            }
        });
        this.machineMenu.add(this.pauseMachineMenu);
        this.silenceMachineMenu.setText(bundle.getString("JSpeccy.silenceMachineMenu.text_1"));
        this.silenceMachineMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.silenceSoundToggleButtonActionPerformed(evt);
            }
        });
        this.machineMenu.add(this.silenceMachineMenu);
        this.resetMachineMenu.setAccelerator(KeyStroke.getKeyStroke(116, 0));
        this.resetMachineMenu.setText(bundle.getString("JSpeccy.resetMachineMenu.text"));
        this.resetMachineMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.resetMachineMenuActionPerformed(evt);
            }
        });
        this.machineMenu.add(this.resetMachineMenu);
        this.hardResetMachineMenu.setText(bundle.getString("JSpeccy.hardResetMachineMenu.text"));
        this.hardResetMachineMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.hardResetSpectrumButtonActionPerformed(evt);
            }
        });
        this.machineMenu.add(this.hardResetMachineMenu);
        this.nmiMachineMenu.setAccelerator(KeyStroke.getKeyStroke(116, 2));
        this.nmiMachineMenu.setText(bundle.getString("JSpeccy.nmiMachineMenu.text"));
        this.nmiMachineMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.nmiMachineMenuActionPerformed(evt);
            }
        });
        this.machineMenu.add(this.nmiMachineMenu);
        this.machineMenu.add(this.jSeparator3);
        this.hardwareMachineMenu.setText(bundle.getString("JSpeccy.hardwareMachineMenu.text"));
        this.hardwareButtonGroup.add(this.spec16kHardware);
        this.spec16kHardware.setText(bundle.getString("JSpeccy.spec16kHardware.text"));
        this.spec16kHardware.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.spec16kHardwareActionPerformed(evt);
            }
        });
        this.hardwareMachineMenu.add(this.spec16kHardware);
        this.hardwareButtonGroup.add(this.spec48kHardware);
        this.spec48kHardware.setSelected(true);
        this.spec48kHardware.setText(bundle.getString("JSpeccy.spec48kHardware.text"));
        this.spec48kHardware.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.spec48kHardwareActionPerformed(evt);
            }
        });
        this.hardwareMachineMenu.add(this.spec48kHardware);
        this.hardwareButtonGroup.add(this.spec128kHardware);
        this.spec128kHardware.setText(bundle.getString("JSpeccy.spec128kHardware.text"));
        this.spec128kHardware.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.spec128kHardwareActionPerformed(evt);
            }
        });
        this.hardwareMachineMenu.add(this.spec128kHardware);
        this.hardwareButtonGroup.add(this.specPlus2Hardware);
        this.specPlus2Hardware.setText(bundle.getString("JSpeccy.specPlus2Hardware.text"));
        this.specPlus2Hardware.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.specPlus2HardwareActionPerformed(evt);
            }
        });
        this.hardwareMachineMenu.add(this.specPlus2Hardware);
        this.hardwareButtonGroup.add(this.specPlus2AHardware);
        this.specPlus2AHardware.setText(bundle.getString("JSpeccy.specPlus2AHardware.text"));
        this.specPlus2AHardware.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.specPlus2AHardwareActionPerformed(evt);
            }
        });
        this.hardwareMachineMenu.add(this.specPlus2AHardware);
        this.hardwareButtonGroup.add(this.specPlus3Hardware);
        this.specPlus3Hardware.setText(bundle.getString("JSpeccy.specPlus3Hardware.text"));
        this.specPlus3Hardware.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.specPlus3HardwareActionPerformed(evt);
            }
        });
        this.hardwareMachineMenu.add(this.specPlus3Hardware);
        this.machineMenu.add(this.hardwareMachineMenu);
        this.jMenuBar1.add(this.machineMenu);
        this.mediaMenu.setText(bundle.getString("JSpeccy.mediaMenu.text"));
        this.tapeMediaMenu.setText(bundle.getString("JSpeccy.tapeMediaMenu.text"));
        this.openTapeMediaMenu.setAccelerator(KeyStroke.getKeyStroke(118, 0));
        this.openTapeMediaMenu.setText(bundle.getString("JSpeccy.openTapeMediaMenu.text"));
        this.openTapeMediaMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.openTapeMediaMenuActionPerformed(evt);
            }
        });
        this.tapeMediaMenu.add(this.openTapeMediaMenu);
        this.playTapeMediaMenu.setAccelerator(KeyStroke.getKeyStroke(119, 0));
        this.playTapeMediaMenu.setText(bundle.getString("JSpeccy.playTapeMediaMenu.text"));
        this.playTapeMediaMenu.setEnabled(false);
        this.playTapeMediaMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.playTapeMediaMenuActionPerformed(evt);
            }
        });
        this.tapeMediaMenu.add(this.playTapeMediaMenu);
        this.browserTapeMediaMenu.setText(bundle.getString("JSpeccy.browserTapeMediaMenu.text"));
        this.browserTapeMediaMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.browserTapeMediaMenuActionPerformed(evt);
            }
        });
        this.tapeMediaMenu.add(this.browserTapeMediaMenu);
        this.rewindTapeMediaMenu.setText(bundle.getString("JSpeccy.rewindTapeMediaMenu.text"));
        this.rewindTapeMediaMenu.setEnabled(false);
        this.rewindTapeMediaMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.rewindTapeMediaMenuActionPerformed(evt);
            }
        });
        this.tapeMediaMenu.add(this.rewindTapeMediaMenu);
        this.tapeMediaMenu.add(this.jSeparator5);
        this.createTapeMediaMenu.setAccelerator(KeyStroke.getKeyStroke(117, 0));
        this.createTapeMediaMenu.setText(bundle.getString("JSpeccy.createTapeMediaMenu.text"));
        this.createTapeMediaMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.createTapeMediaMenuActionPerformed(evt);
            }
        });
        this.tapeMediaMenu.add(this.createTapeMediaMenu);
        this.clearTapeMediaMenu.setText(bundle.getString("JSpeccy.clearTapeMediaMenu.text"));
        this.clearTapeMediaMenu.setEnabled(false);
        this.clearTapeMediaMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.clearTapeMediaMenuActionPerformed(evt);
            }
        });
        this.tapeMediaMenu.add(this.clearTapeMediaMenu);
        this.tapeMediaMenu.add(this.jSeparator6);
        this.recordStartTapeMediaMenu.setText(bundle.getString("JSpeccy.recordStartTapeMediaMenu.text"));
        this.recordStartTapeMediaMenu.setEnabled(false);
        this.recordStartTapeMediaMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.recordStartTapeMediaMenuActionPerformed(evt);
            }
        });
        this.tapeMediaMenu.add(this.recordStartTapeMediaMenu);
        this.recordStopTapeMediaMenu.setText(bundle.getString("JSpeccy.recordStopTapeMediaMenu.text"));
        this.recordStopTapeMediaMenu.setEnabled(false);
        this.recordStopTapeMediaMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.recordStopTapeMediaMenuActionPerformed(evt);
            }
        });
        this.tapeMediaMenu.add(this.recordStopTapeMediaMenu);
        this.mediaMenu.add(this.tapeMediaMenu);
        this.IF2MediaMenu.setText(bundle.getString("JSpeccy.IF2MediaMenu.text"));
        this.insertIF2RomMediaMenu.setText(bundle.getString("JSpeccy.insertIF2RomMediaMenu.text"));
        this.insertIF2RomMediaMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.insertIF2RomMediaMenuActionPerformed(evt);
            }
        });
        this.IF2MediaMenu.add(this.insertIF2RomMediaMenu);
        this.ejectIF2RomMediaMenu.setText(bundle.getString("JSpeccy.ejectIF2RomMediaMenu.text"));
        this.ejectIF2RomMediaMenu.setEnabled(false);
        this.ejectIF2RomMediaMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.ejectIF2RomMediaMenuActionPerformed(evt);
            }
        });
        this.IF2MediaMenu.add(this.ejectIF2RomMediaMenu);
        this.mediaMenu.add(this.IF2MediaMenu);
        this.jMenuBar1.add(this.mediaMenu);
        this.helpMenu.setText(bundle.getString("JSpeccy.helpMenu.text"));
        this.imageHelpMenu.setAccelerator(KeyStroke.getKeyStroke(112, 0));
        this.imageHelpMenu.setText(bundle.getString("JSpeccy.imageHelpMenu.text"));
        this.imageHelpMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.imageHelpMenuActionPerformed(evt);
            }
        });
        this.helpMenu.add(this.imageHelpMenu);
        this.aboutHelpMenu.setText(bundle.getString("JSpeccy.aboutHelpMenu.text"));
        this.aboutHelpMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                JSpeccy.this.aboutHelpMenuActionPerformed(evt);
            }
        });
        this.helpMenu.add(this.aboutHelpMenu);
        this.jMenuBar1.add(this.helpMenu);
        this.setJMenuBar(this.jMenuBar1);
        this.pack();
    }
    
    private void openSnapshotActionPerformed(final ActionEvent evt) {
        final boolean paused = this.spectrum.isPaused();
        if (this.openSnapshotDlg == null) {
            (this.openSnapshotDlg = new JFileChooser(this.lastSnapshotDir)).setFileFilter(new FileFilterTapeSnapshot());
            this.currentDirSnapshot = this.openSnapshotDlg.getCurrentDirectory();
        }
        else {
            this.openSnapshotDlg.setCurrentDirectory(this.currentDirSnapshot);
        }
        if (!paused) {
            this.spectrum.stopEmulation();
        }
        final int status = this.openSnapshotDlg.showOpenDialog(this.getContentPane());
        if (status == 0) {
            final File selectedFile = this.openSnapshotDlg.getSelectedFile();
            this.settings.getRecentFilesSettings().setLastSnapshotDir(this.lastSnapshotDir);
            if (selectedFile.getName().toLowerCase().endsWith(".sna") || selectedFile.getName().toLowerCase().endsWith(".z80") || selectedFile.getName().toLowerCase().endsWith(".szx")) {
                this.currentDirSnapshot = this.openSnapshotDlg.getCurrentDirectory();
                this.lastSnapshotDir = this.openSnapshotDlg.getCurrentDirectory().getAbsolutePath();
                this.rotateRecentFile(selectedFile);
                this.spectrum.loadSnapshot(selectedFile);
            }
            else {
                this.currentDirTape = this.openSnapshotDlg.getCurrentDirectory();
                this.lastTapeDir = this.openSnapshotDlg.getCurrentDirectory().getAbsolutePath();
                this.lastSnapshotDir = this.lastTapeDir;
                this.settings.getRecentFilesSettings().setLastTapeDir(this.lastTapeDir);
                this.currentDirSnapshot = this.currentDirTape;
                this.spectrum.tape.eject();
                if (this.spectrum.tape.insert(selectedFile)) {
                    this.rotateRecentFile(selectedFile);
                    this.tapeFilename.setText(selectedFile.getName());
                    this.playTapeMediaMenu.setEnabled(true);
                    this.clearTapeMediaMenu.setEnabled(true);
                    this.rewindTapeMediaMenu.setEnabled(true);
                    this.recordStartTapeMediaMenu.setEnabled(true);
                }
                else {
                    final ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle");
                    JOptionPane.showMessageDialog(this, bundle.getString("LOAD_TAPE_ERROR"), bundle.getString("LOAD_TAPE_ERROR_TITLE"), 0);
                }
            }
        }
        if (!paused) {
            this.spectrum.startEmulation();
        }
    }
    
    private void thisIsTheEndMyFriendActionPerformed(final ActionEvent evt) {
        final ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle");
        final int ret = JOptionPane.showConfirmDialog(this.getContentPane(), bundle.getString("ARE_YOU_SURE_QUESTION"), bundle.getString("QUIT_JSPECCY"), 0, 3);
        if (ret == 0) {
            this.spectrum.stopEmulation();
            this.saveRecentFiles();
            System.exit(0);
        }
    }
    
    private void doubleSizeOptionActionPerformed(final ActionEvent evt) {
        final Object source = evt.getSource();
        if (source instanceof JCheckBoxMenuItem) {
            this.doubleSizeToggleButton.setSelected(this.doubleSizeOption.isSelected());
        }
        else {
            this.doubleSizeOption.setSelected(this.doubleSizeToggleButton.isSelected());
        }
        this.jscr.toggleDoubleSize();
        this.pack();
    }
    
    private void pauseMachineMenuActionPerformed(final ActionEvent evt) {
        final Object source = evt.getSource();
        if (source instanceof JCheckBoxMenuItem) {
            this.pauseToggleButton.setSelected(this.pauseMachineMenu.isSelected());
        }
        else {
            this.pauseMachineMenu.setSelected(this.pauseToggleButton.isSelected());
        }
        if (this.pauseMachineMenu.isSelected()) {
            this.spectrum.stopEmulation();
        }
        else {
            this.spectrum.startEmulation();
        }
    }
    
    private void resetMachineMenuActionPerformed(final ActionEvent evt) {
        final ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle");
        final int ret = JOptionPane.showConfirmDialog(this.getContentPane(), bundle.getString("ARE_YOU_SURE_QUESTION"), bundle.getString("RESET_SPECTRUM"), 0, 3);
        if (ret == 0) {
            this.spectrum.reset();
        }
    }
    
    private void silenceSoundToggleButtonActionPerformed(final ActionEvent evt) {
        final Object source = evt.getSource();
        if (source instanceof JToggleButton) {
            this.silenceMachineMenu.setSelected(this.silenceSoundToggleButton.isSelected());
        }
        else {
            this.silenceSoundToggleButton.setSelected(this.silenceMachineMenu.isSelected());
        }
        this.spectrum.muteSound(this.silenceSoundToggleButton.isSelected());
    }
    
    private void playTapeMediaMenuActionPerformed(final ActionEvent evt) {
        this.spectrum.toggleTape();
    }
    
    private void openTapeMediaMenuActionPerformed(final ActionEvent evt) {
        final boolean paused = this.spectrum.isPaused();
        if (this.openTapeDlg == null) {
            (this.openTapeDlg = new JFileChooser(this.lastTapeDir)).setFileFilter(new FileFilterTape());
            this.currentDirTape = this.openTapeDlg.getCurrentDirectory();
        }
        else {
            this.openTapeDlg.setCurrentDirectory(this.currentDirTape);
        }
        if (!paused) {
            this.spectrum.stopEmulation();
        }
        final int status = this.openTapeDlg.showOpenDialog(this.getContentPane());
        if (status == 0) {
            this.currentDirTape = this.openTapeDlg.getCurrentDirectory();
            this.lastTapeDir = this.openTapeDlg.getCurrentDirectory().getAbsolutePath();
            this.settings.getRecentFilesSettings().setLastTapeDir(this.lastTapeDir);
            this.spectrum.tape.eject();
            if (this.spectrum.tape.insert(this.openTapeDlg.getSelectedFile())) {
                this.rotateRecentFile(this.openTapeDlg.getSelectedFile());
                this.tapeFilename.setText(this.openTapeDlg.getSelectedFile().getName());
                this.playTapeMediaMenu.setEnabled(true);
                this.clearTapeMediaMenu.setEnabled(true);
                this.rewindTapeMediaMenu.setEnabled(true);
                this.recordStartTapeMediaMenu.setEnabled(true);
            }
            else {
                final ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle");
                JOptionPane.showMessageDialog(this, bundle.getString("LOAD_TAPE_ERROR"), bundle.getString("LOAD_TAPE_ERROR_TITLE"), 0);
            }
        }
        if (!paused) {
            this.spectrum.startEmulation();
        }
    }
    
    private void rewindTapeMediaMenuActionPerformed(final ActionEvent evt) {
        this.spectrum.tape.rewind();
    }
    
    private void imageHelpMenuActionPerformed(final ActionEvent evt) {
        this.keyboardHelper.setResizable(false);
        this.keyboardHelper.pack();
        this.keyboardHelper.setVisible(true);
    }
    
    private void aboutHelpMenuActionPerformed(final ActionEvent evt) {
        final ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle");
        JOptionPane.showMessageDialog(this.getContentPane(), bundle.getString("ABOUT_MESSAGE"), bundle.getString("ABOUT_TITLE"), 1, new ImageIcon(this.getClass().getResource("/icons/JSpeccy64x64.png")));
    }
    
    private void nmiMachineMenuActionPerformed(final ActionEvent evt) {
        this.spectrum.triggerNMI();
    }
    
    private void closeKeyboardHelperActionPerformed(final ActionEvent evt) {
        this.keyboardHelper.setVisible(false);
    }
    
    private void saveSnapshotActionPerformed(final ActionEvent evt) {
        final boolean paused = this.spectrum.isPaused();
        if (this.saveSnapshotDlg == null) {
            (this.saveSnapshotDlg = new JFileChooser("/home/jsanchez/Spectrum")).setFileFilter(new FileFilterSaveSnapshot());
            this.currentDirSaveSnapshot = this.saveSnapshotDlg.getCurrentDirectory();
        }
        else {
            this.saveSnapshotDlg.setCurrentDirectory(this.currentDirSaveSnapshot);
            final BasicFileChooserUI chooserUI = (BasicFileChooserUI)this.saveSnapshotDlg.getUI();
            chooserUI.setFileName("");
        }
        if (!paused) {
            this.spectrum.stopEmulation();
        }
        final int status = this.saveSnapshotDlg.showSaveDialog(this.getContentPane());
        if (status == 0) {
            this.currentDirSaveSnapshot = this.saveSnapshotDlg.getCurrentDirectory();
            if (this.spectrum.tape.getTapeName() != null && this.saveSnapshotDlg.getSelectedFile().getName().toLowerCase().endsWith("szx")) {
                this.tapeFilenameLabel.setText(this.spectrum.tape.getTapeName().getName());
                this.ignoreRadioButton.setSelected(true);
                this.spectrum.setSzxTapeMode(0);
                this.saveSzxTape.pack();
                this.saveSzxTape.setVisible(true);
            }
            this.spectrum.saveSnapshot(this.saveSnapshotDlg.getSelectedFile());
        }
        if (!paused) {
            this.spectrum.startEmulation();
        }
    }
    
    private void noneJoystickActionPerformed(final ActionEvent evt) {
        this.spectrum.setJoystick(Spectrum.Joystick.NONE);
    }
    
    private void kempstonJoystickActionPerformed(final ActionEvent evt) {
        this.spectrum.setJoystick(Spectrum.Joystick.KEMPSTON);
    }
    
    private void sinclair1JoystickActionPerformed(final ActionEvent evt) {
        this.spectrum.setJoystick(Spectrum.Joystick.SINCLAIR1);
    }
    
    private void sinclair2JoystickActionPerformed(final ActionEvent evt) {
        this.spectrum.setJoystick(Spectrum.Joystick.SINCLAIR2);
    }
    
    private void cursorJoystickActionPerformed(final ActionEvent evt) {
        this.spectrum.setJoystick(Spectrum.Joystick.CURSOR);
    }
    
    private void spec48kHardwareActionPerformed(final ActionEvent evt) {
        this.spectrum.selectHardwareModel(MachineTypes.SPECTRUM48K, true);
        this.spectrum.reset();
    }
    
    private void spec128kHardwareActionPerformed(final ActionEvent evt) {
        this.spectrum.selectHardwareModel(MachineTypes.SPECTRUM128K, true);
        this.spectrum.reset();
    }
    
    private void fastEmulationToggleButtonActionPerformed(final ActionEvent evt) {
        if (this.fastEmulationToggleButton.isSelected()) {
            this.spectrum.changeSpeed(this.settings.getSpectrumSettings().getFramesInt());
        }
        else {
            this.spectrum.changeSpeed(1);
        }
    }
    
    private void browserTapeMediaMenuActionPerformed(final ActionEvent evt) {
        this.tapeBrowserDialog.setVisible(true);
        this.tapeBrowserDialog.pack();
        this.tapeCatalog.doLayout();
    }
    
    private void closeTapeBrowserButtonActionPerformed(final ActionEvent evt) {
        this.tapeBrowserDialog.setVisible(false);
    }
    
    private void specPlus2HardwareActionPerformed(final ActionEvent evt) {
        this.spectrum.selectHardwareModel(MachineTypes.SPECTRUMPLUS2, true);
        this.spectrum.reset();
    }
    
    private void specPlus2AHardwareActionPerformed(final ActionEvent evt) {
        this.spectrum.selectHardwareModel(MachineTypes.SPECTRUMPLUS2A, true);
        this.spectrum.reset();
    }
    
    private void settingsOptionsMenuActionPerformed(final ActionEvent evt) {
        final ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle");
        final int AYsoundMode = this.settings.getAY8912Settings().getSoundMode();
        final boolean hifiSound = this.settings.getSpectrumSettings().isHifiSound();
        this.settingsDialog.showDialog(this, bundle.getString("SETTINGS_DIALOG_TITLE"));
        this.spectrum.loadConfigVars();
        if ((AYsoundMode != this.settings.getAY8912Settings().getSoundMode() || hifiSound != this.settings.getSpectrumSettings().isHifiSound()) && !this.spectrum.isMuteSound()) {
            this.spectrum.muteSound(true);
            this.spectrum.muteSound(false);
        }
    }
    
    private void saveScreenShotActionPerformed(final ActionEvent evt) {
        final boolean paused = this.spectrum.isPaused();
        if (this.saveImageDlg == null) {
            (this.saveImageDlg = new JFileChooser("/home/jsanchez/Spectrum")).setFileFilter(new FileFilterImage());
            this.currentDirSaveImage = this.saveImageDlg.getCurrentDirectory();
        }
        else {
            this.saveImageDlg.setCurrentDirectory(this.currentDirSaveImage);
            final BasicFileChooserUI chooserUI = (BasicFileChooserUI)this.saveImageDlg.getUI();
            chooserUI.setFileName("");
        }
        if (!paused) {
            this.spectrum.stopEmulation();
        }
        final int status = this.saveImageDlg.showSaveDialog(this.getContentPane());
        if (status == 0) {
            this.currentDirSaveImage = this.saveImageDlg.getCurrentDirectory();
            this.spectrum.saveImage(this.saveImageDlg.getSelectedFile());
        }
        if (!paused) {
            this.spectrum.startEmulation();
        }
    }
    
    private void createTapeMediaMenuActionPerformed(final ActionEvent evt) {
        final boolean paused = this.spectrum.isPaused();
        if (this.openTapeDlg == null) {
            (this.openTapeDlg = new JFileChooser("/home/jsanchez/Spectrum")).setFileFilter(new FileFilterTape());
            this.currentDirTape = this.openTapeDlg.getCurrentDirectory();
        }
        else {
            this.openTapeDlg.setCurrentDirectory(this.currentDirTape);
        }
        if (!paused) {
            this.spectrum.stopEmulation();
        }
        final int status = this.openTapeDlg.showOpenDialog(this);
        if (status == 0) {
            this.currentDirTape = this.openTapeDlg.getCurrentDirectory();
            final File filename = new File(this.openTapeDlg.getSelectedFile().getAbsolutePath());
            try {
                filename.createNewFile();
            }
            catch (IOException ex) {
                Logger.getLogger(JSpeccy.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.spectrum.tape.eject();
            if (this.spectrum.tape.insert(filename)) {
                this.tapeFilename.setText(filename.getName());
                this.playTapeMediaMenu.setEnabled(true);
                this.clearTapeMediaMenu.setEnabled(true);
                this.rewindTapeMediaMenu.setEnabled(true);
                this.recordStartTapeMediaMenu.setEnabled(true);
            }
            else {
                final ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle");
                JOptionPane.showMessageDialog(this, bundle.getString("LOAD_TAPE_ERROR"), bundle.getString("LOAD_TAPE_ERROR_TITLE"), 0);
            }
        }
        if (!paused) {
            this.spectrum.startEmulation();
        }
    }
    
    private void hardResetSpectrumButtonActionPerformed(final ActionEvent evt) {
        final ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle");
        final int ret = JOptionPane.showConfirmDialog(this.getContentPane(), bundle.getString("ARE_YOU_SURE_QUESTION"), bundle.getString("HARD_RESET_SPECTRUM"), 0, 3);
        if (ret == 0) {
            this.spectrum.hardReset();
        }
    }
    
    private void clearTapeMediaMenuActionPerformed(final ActionEvent evt) {
        final ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle");
        final int ret = JOptionPane.showConfirmDialog(this.getContentPane(), bundle.getString("ARE_YOU_SURE_QUESTION"), bundle.getString("CLEAR_TAPE"), 0, 3);
        if (ret == 0 && this.spectrum.tape.isTapeReady()) {
            final File filename = new File(this.openTapeDlg.getSelectedFile().getAbsolutePath());
            try {
                filename.delete();
                filename.createNewFile();
            }
            catch (IOException ex) {
                Logger.getLogger(JSpeccy.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.spectrum.tape.eject();
            if (!this.spectrum.tape.insert(filename)) {
                JOptionPane.showMessageDialog(this, bundle.getString("LOAD_TAPE_ERROR"), bundle.getString("LOAD_TAPE_ERROR_TITLE"), 0);
            }
        }
    }
    
    private void spec16kHardwareActionPerformed(final ActionEvent evt) {
        this.spectrum.selectHardwareModel(MachineTypes.SPECTRUM16K, true);
        this.spectrum.reset();
    }
    
    private void specPlus3HardwareActionPerformed(final ActionEvent evt) {
        this.spectrum.selectHardwareModel(MachineTypes.SPECTRUMPLUS3, true);
        this.spectrum.reset();
    }
    
    private void recordStartTapeMediaMenuActionPerformed(final ActionEvent evt) {
        final boolean paused = this.spectrum.isPaused();
        if (!paused) {
            this.spectrum.stopEmulation();
        }
        final ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle");
        if (!this.spectrum.tape.isTapeReady()) {
            JOptionPane.showMessageDialog(this, bundle.getString("RECORD_START_ERROR"), bundle.getString("RECORD_START_TITLE"), 0);
        }
        else if (this.spectrum.startRecording()) {
            this.recordStartTapeMediaMenu.setEnabled(false);
            this.recordStopTapeMediaMenu.setEnabled(true);
        }
        else {
            JOptionPane.showMessageDialog(this, bundle.getString("RECORD_START_FORMAT_ERROR"), bundle.getString("RECORD_START_FORMAT_TITLE"), 0);
        }
        this.playTapeMediaMenu.setSelected(false);
        if (!paused) {
            this.spectrum.startEmulation();
        }
    }
    
    private void recordStopTapeMediaMenuActionPerformed(final ActionEvent evt) {
        this.spectrum.stopRecording();
        this.recordStartTapeMediaMenu.setEnabled(true);
        this.recordStopTapeMediaMenu.setEnabled(false);
        this.playTapeMediaMenu.setSelected(true);
    }
    
    private void loadRecentFile(final int idx) {
        final ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle");
        if (!this.recentFile[idx].exists()) {
            JOptionPane.showMessageDialog(this, bundle.getString("RECENT_FILE_ERROR"), bundle.getString("RECENT_FILE_ERROR_TITLE"), 0);
        }
        else if (this.recentFile[idx].getName().toLowerCase().endsWith(".sna") || this.recentFile[idx].getName().toLowerCase().endsWith(".z80") || this.recentFile[idx].getName().toLowerCase().endsWith(".szx")) {
            final boolean paused = this.spectrum.isPaused();
            if (!paused) {
                this.spectrum.stopEmulation();
            }
            this.spectrum.loadSnapshot(this.recentFile[idx]);
            if (!paused) {
                this.spectrum.startEmulation();
            }
        }
        else {
            this.spectrum.tape.eject();
            if (this.spectrum.tape.insert(this.recentFile[idx])) {
                this.playTapeMediaMenu.setEnabled(true);
                this.clearTapeMediaMenu.setEnabled(true);
                this.rewindTapeMediaMenu.setEnabled(true);
                this.recordStartTapeMediaMenu.setEnabled(true);
            }
            else {
                JOptionPane.showMessageDialog(this, bundle.getString("LOAD_TAPE_ERROR"), bundle.getString("LOAD_TAPE_ERROR_TITLE"), 0);
            }
        }
    }
    
    private void recentFileMenu0ActionPerformed(final ActionEvent evt) {
        this.loadRecentFile(0);
    }
    
    private void recentFileMenu1ActionPerformed(final ActionEvent evt) {
        this.loadRecentFile(1);
    }
    
    private void recentFileMenu2ActionPerformed(final ActionEvent evt) {
        this.loadRecentFile(2);
    }
    
    private void recentFileMenu3ActionPerformed(final ActionEvent evt) {
        this.loadRecentFile(3);
    }
    
    private void recentFileMenu4ActionPerformed(final ActionEvent evt) {
        this.loadRecentFile(4);
    }
    
    private void insertIF2RomMediaMenuActionPerformed(final ActionEvent evt) {
        final boolean paused = this.spectrum.isPaused();
        if (this.IF2RomDlg == null) {
            (this.IF2RomDlg = new JFileChooser("/home/jsanchez/Spectrum")).setFileFilter(new FileFilterRom());
            this.currentDirRom = this.IF2RomDlg.getCurrentDirectory();
        }
        else {
            this.IF2RomDlg.setCurrentDirectory(this.currentDirRom);
        }
        if (!paused) {
            this.spectrum.stopEmulation();
        }
        final int status = this.IF2RomDlg.showOpenDialog(this.getContentPane());
        if (status == 0) {
            this.currentDirRom = this.IF2RomDlg.getCurrentDirectory();
            if (this.spectrum.insertIF2Rom(this.IF2RomDlg.getSelectedFile())) {
                this.insertIF2RomMediaMenu.setEnabled(false);
                this.ejectIF2RomMediaMenu.setEnabled(true);
                this.spectrum.reset();
            }
            else {
                final ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle");
                JOptionPane.showMessageDialog(this, bundle.getString("LOAD_ROM_ERROR"), bundle.getString("LOAD_ROM_ERROR_TITLE"), 0);
            }
        }
        if (!paused) {
            this.spectrum.startEmulation();
        }
    }
    
    private void ejectIF2RomMediaMenuActionPerformed(final ActionEvent evt) {
        this.spectrum.ejectIF2Rom();
        this.insertIF2RomMediaMenu.setEnabled(true);
        this.ejectIF2RomMediaMenu.setEnabled(false);
        this.spectrum.reset();
    }
    
    private void fullerJoystickActionPerformed(final ActionEvent evt) {
        this.spectrum.setJoystick(Spectrum.Joystick.FULLER);
    }
    
    private void saveSzxCloseButtonActionPerformed(final ActionEvent evt) {
        this.saveSzxTape.setVisible(false);
    }
    
    private void linkedRadioButtonActionPerformed(final ActionEvent evt) {
        this.spectrum.setSzxTapeMode(1);
    }
    
    private void embeddedRadioButtonActionPerformed(final ActionEvent evt) {
        this.spectrum.setSzxTapeMode(2);
    }
    
    private void ignoreRadioButtonActionPerformed(final ActionEvent evt) {
        this.spectrum.setSzxTapeMode(0);
    }
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JSpeccy().setVisible(true);
            }
        });
    }
}
