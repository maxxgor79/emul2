// 
// Decompiled by Procyon v0.5.36
// 

package gui;

import javax.xml.bind.JAXBElement;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXB;
import configuration.ObjectFactory;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import javax.swing.AbstractButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Container;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.SwingUtilities;
import java.awt.Frame;
import java.awt.Component;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JDialog;
import configuration.JSpeccySettingsType;
import javax.swing.JPanel;

public class SettingsDialog extends JPanel
{
    private JSpeccySettingsType settings;
    private JDialog settingsDialog;
    private JPanel AY8912Panel;
    private JRadioButton AYABCMode;
    private JRadioButton AYACBMode;
    private JRadioButton AYBACMode;
    private JPanel AYEnabled48k;
    private JRadioButton AYMonoMode;
    private JPanel AYStereoMode;
    private ButtonGroup AYStereoModeButtonGroup;
    private JCheckBox ULAplus;
    private JCheckBox acceleratedLoad;
    private JPanel audioPanel;
    private JPanel buttonPanel;
    private JButton closeButton;
    private JPanel defaultModelPanel;
    private JCheckBox doubleSize;
    private JCheckBox enableSaveTraps;
    private JCheckBox enabledAY48k;
    private JCheckBox flashload;
    private JPanel hardwarePanelTab;
    private JCheckBox hifiSound;
    private JRadioButton highSampling;
    private JPanel highSpeedPanel;
    private JRadioButton issue2;
    private JRadioButton issue3;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JTabbedPane jTabbedPane1;
    private JComboBox joystick;
    private JPanel joystickPanel;
    private JPanel keyboard48kPanel;
    private ButtonGroup keyboardButtonGroup;
    private JPanel keyboardPanelTab;
    private JPanel loadPanel;
    private JCheckBox loadingNoise;
    private JRadioButton lowSampling;
    private JRadioButton multiface128RadioButton;
    private JCheckBox multifaceEnabled;
    private ButtonGroup multifaceModelButtonGroup;
    private JPanel multifaceModelPanel;
    private JRadioButton multifaceOneRadioButton;
    private JPanel multifacePanel;
    private JPanel multifacePanelTab;
    private ButtonGroup samplingButtonGroup;
    private JPanel samplingPanel;
    private JPanel savePanel;
    private JButton saveSettingsButton;
    private JCheckBox soundMuted;
    private JPanel soundPanelTab;
    private JComboBox spectrumModel;
    private JSlider speed;
    private JPanel tapePanelTab;
    private JPanel videoPanel;
    
    public SettingsDialog(final JSpeccySettingsType userSettings) {
        this.initComponents();
        this.settings = userSettings;
    }
    
    private void updateUserSettings() {
        this.spectrumModel.setSelectedIndex(this.settings.getSpectrumSettings().getDefaultModel());
        this.soundMuted.setSelected(this.settings.getSpectrumSettings().isMutedSound());
        this.loadingNoise.setSelected(this.settings.getSpectrumSettings().isLoadingNoise());
        this.hifiSound.setSelected(this.settings.getSpectrumSettings().isHifiSound());
        this.flashload.setSelected(this.settings.getTapeSettings().isFlashload());
        this.acceleratedLoad.setSelected(this.settings.getTapeSettings().isAccelerateLoading());
        this.ULAplus.setSelected(this.settings.getSpectrumSettings().isULAplus());
        this.joystick.setSelectedIndex(this.settings.getKeyboardJoystickSettings().getJoystickModel());
        this.enabledAY48k.setSelected(this.settings.getSpectrumSettings().isAYEnabled48K());
        this.speed.setValue(this.settings.getSpectrumSettings().getFramesInt());
        this.doubleSize.setSelected(this.settings.getSpectrumSettings().isDoubleSize());
        if (this.settings.getSpectrumSettings().isIssue2()) {
            this.issue2.setSelected(true);
        }
        else {
            this.issue3.setSelected(true);
        }
        this.enableSaveTraps.setSelected(this.settings.getTapeSettings().isEnableSaveTraps());
        if (this.settings.getTapeSettings().isHighSamplingFreq()) {
            this.highSampling.setSelected(true);
        }
        else {
            this.lowSampling.setSelected(true);
        }
        switch (this.settings.getAY8912Settings().getSoundMode()) {
            case 1: {
                this.AYABCMode.setSelected(true);
                break;
            }
            case 2: {
                this.AYACBMode.setSelected(true);
                break;
            }
            case 3: {
                this.AYBACMode.setSelected(true);
                break;
            }
            default: {
                this.AYMonoMode.setSelected(true);
                break;
            }
        }
        this.multifaceEnabled.setSelected(this.settings.getSpectrumSettings().isMultifaceEnabled());
        if (this.settings.getSpectrumSettings().isMf128On48K()) {
            this.multiface128RadioButton.setSelected(true);
        }
        else {
            this.multifaceOneRadioButton.setSelected(true);
        }
    }
    
    public boolean showDialog(final Component parent, final String title) {
        Frame owner = null;
        if (parent instanceof Frame) {
            owner = (Frame)parent;
        }
        else {
            owner = (Frame)SwingUtilities.getAncestorOfClass(Frame.class, parent);
        }
        if (this.settingsDialog == null || this.settingsDialog.getOwner() != owner) {
            owner = null;
            this.settingsDialog = new JDialog(owner, true);
            this.settingsDialog.getContentPane().add(this);
            this.settingsDialog.pack();
        }
        this.updateUserSettings();
        this.settingsDialog.setTitle(title);
        this.settingsDialog.setVisible(true);
        return true;
    }
    
    private void initComponents() {
        this.keyboardButtonGroup = new ButtonGroup();
        this.samplingButtonGroup = new ButtonGroup();
        this.AYStereoModeButtonGroup = new ButtonGroup();
        this.multifaceModelButtonGroup = new ButtonGroup();
        this.buttonPanel = new JPanel();
        this.saveSettingsButton = new JButton();
        this.closeButton = new JButton();
        this.jTabbedPane1 = new JTabbedPane();
        this.hardwarePanelTab = new JPanel();
        this.defaultModelPanel = new JPanel();
        this.spectrumModel = new JComboBox();
        this.videoPanel = new JPanel();
        this.ULAplus = new JCheckBox();
        this.doubleSize = new JCheckBox();
        this.highSpeedPanel = new JPanel();
        this.speed = new JSlider();
        this.soundPanelTab = new JPanel();
        this.audioPanel = new JPanel();
        this.soundMuted = new JCheckBox();
        this.loadingNoise = new JCheckBox();
        this.hifiSound = new JCheckBox();
        this.AY8912Panel = new JPanel();
        this.AYEnabled48k = new JPanel();
        this.enabledAY48k = new JCheckBox();
        this.AYStereoMode = new JPanel();
        this.AYMonoMode = new JRadioButton();
        this.AYABCMode = new JRadioButton();
        this.AYACBMode = new JRadioButton();
        this.AYBACMode = new JRadioButton();
        this.tapePanelTab = new JPanel();
        this.loadPanel = new JPanel();
        this.flashload = new JCheckBox();
        this.acceleratedLoad = new JCheckBox();
        this.savePanel = new JPanel();
        this.enableSaveTraps = new JCheckBox();
        this.samplingPanel = new JPanel();
        this.lowSampling = new JRadioButton();
        this.highSampling = new JRadioButton();
        this.keyboardPanelTab = new JPanel();
        this.keyboard48kPanel = new JPanel();
        this.jPanel3 = new JPanel();
        this.jLabel1 = new JLabel();
        this.jPanel2 = new JPanel();
        this.issue2 = new JRadioButton();
        this.issue3 = new JRadioButton();
        this.joystickPanel = new JPanel();
        this.joystick = new JComboBox();
        this.multifacePanelTab = new JPanel();
        this.multifacePanel = new JPanel();
        this.multifaceEnabled = new JCheckBox();
        this.multifaceModelPanel = new JPanel();
        this.jLabel2 = new JLabel();
        this.jPanel1 = new JPanel();
        this.multifaceOneRadioButton = new JRadioButton();
        this.multiface128RadioButton = new JRadioButton();
        this.setLayout(new BorderLayout());
        final ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle");
        this.saveSettingsButton.setText(bundle.getString("SettingsDialog.saveSettingsButton.text"));
        this.saveSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.saveSettingsButtonActionPerformed(evt);
            }
        });
        this.buttonPanel.add(this.saveSettingsButton);
        this.closeButton.setText(bundle.getString("CLOSE"));
        this.closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.closeButtonActionPerformed(evt);
            }
        });
        this.buttonPanel.add(this.closeButton);
        this.add(this.buttonPanel, "Last");
        this.hardwarePanelTab.setLayout(new BoxLayout(this.hardwarePanelTab, 3));
        this.defaultModelPanel.setBorder(BorderFactory.createTitledBorder(bundle.getString("SettingsDialog.defaultModePanel.border.text")));
        this.spectrumModel.setModel(new DefaultComboBoxModel<String>(new String[] { "Spectrum 16k", "Spectrum 48k", "Spectrum 128k", "Spectrum +2", "Spectrum +2A", "Spectrum +3" }));
        this.spectrumModel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.spectrumModelActionPerformed(evt);
            }
        });
        this.defaultModelPanel.add(this.spectrumModel);
        this.hardwarePanelTab.add(this.defaultModelPanel);
        this.videoPanel.setBorder(BorderFactory.createTitledBorder("Video"));
        this.videoPanel.setLayout(new GridLayout(2, 0));
        this.ULAplus.setText(bundle.getString("SettingsDialog.hardwarePanel.ULAplus.text"));
        this.ULAplus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.ULAplusActionPerformed(evt);
            }
        });
        this.videoPanel.add(this.ULAplus);
        this.doubleSize.setText(bundle.getString("SettingsDialog.hardwarePanel.doubleSize.text"));
        this.doubleSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.doubleSizeActionPerformed(evt);
            }
        });
        this.videoPanel.add(this.doubleSize);
        this.hardwarePanelTab.add(this.videoPanel);
        this.highSpeedPanel.setBorder(BorderFactory.createTitledBorder(bundle.getString("SettingsDialog.highSpeedPanel.border.text")));
        this.speed.setMajorTickSpacing(1);
        this.speed.setMaximum(10);
        this.speed.setMinimum(2);
        this.speed.setPaintLabels(true);
        this.speed.setPaintTicks(true);
        this.speed.setSnapToTicks(true);
        this.speed.setPreferredSize(new Dimension(300, 43));
        this.speed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                SettingsDialog.this.speedStateChanged(evt);
            }
        });
        this.highSpeedPanel.add(this.speed);
        this.hardwarePanelTab.add(this.highSpeedPanel);
        this.jTabbedPane1.addTab(bundle.getString("SettingsDialog.hardwarePanel.TabTitle"), this.hardwarePanelTab);
        this.soundPanelTab.setLayout(new GridLayout(2, 0));
        this.audioPanel.setBorder(BorderFactory.createTitledBorder(bundle.getString("SettingsDialog.audioPanel.border.text")));
        this.audioPanel.setLayout(new BoxLayout(this.audioPanel, 3));
        this.soundMuted.setText(bundle.getString("SettingsDialog.soundPanel.soundMuted.text"));
        this.soundMuted.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.soundMutedActionPerformed(evt);
            }
        });
        this.audioPanel.add(this.soundMuted);
        this.loadingNoise.setText(bundle.getString("SettingsDialog.soundPanel.loadingNoise.text"));
        this.loadingNoise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.loadingNoiseActionPerformed(evt);
            }
        });
        this.audioPanel.add(this.loadingNoise);
        this.hifiSound.setText(bundle.getString("SettingsDialog.audioPanel.hifiSound.text"));
        this.hifiSound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.hifiSoundActionPerformed(evt);
            }
        });
        this.audioPanel.add(this.hifiSound);
        this.soundPanelTab.add(this.audioPanel);
        this.AY8912Panel.setBorder(BorderFactory.createTitledBorder(bundle.getString("SettingsDialog.AY8912Panel.border.text")));
        this.AY8912Panel.setLayout(new GridLayout(1, 2));
        this.AYEnabled48k.setBorder(BorderFactory.createTitledBorder(bundle.getString("SettingsDialog.AYEnabled48kPanel.border.text")));
        this.enabledAY48k.setText(bundle.getString("SettingsDialog.soundPanel.enabledAY48k.text"));
        this.enabledAY48k.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.enabledAY48kActionPerformed(evt);
            }
        });
        this.AYEnabled48k.add(this.enabledAY48k);
        this.AY8912Panel.add(this.AYEnabled48k);
        this.AYStereoMode.setBorder(BorderFactory.createTitledBorder(bundle.getString("SettingsDialog.AYStereoMode.border.text")));
        this.AYStereoMode.setLayout(new GridLayout(4, 0));
        this.AYStereoModeButtonGroup.add(this.AYMonoMode);
        this.AYMonoMode.setSelected(true);
        this.AYMonoMode.setText(bundle.getString("SettingsDialog.AYMonoMode.RadioButton.text"));
        this.AYMonoMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.AYMonoModeActionPerformed(evt);
            }
        });
        this.AYStereoMode.add(this.AYMonoMode);
        this.AYStereoModeButtonGroup.add(this.AYABCMode);
        this.AYABCMode.setText(bundle.getString("SettingsDialog.AYABCMode.RadioButton.text"));
        this.AYABCMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.AYABCModeActionPerformed(evt);
            }
        });
        this.AYStereoMode.add(this.AYABCMode);
        this.AYStereoModeButtonGroup.add(this.AYACBMode);
        this.AYACBMode.setText(bundle.getString("SettingsDialog.AYACBMode.RadioButton.text"));
        this.AYACBMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.AYACBModeActionPerformed(evt);
            }
        });
        this.AYStereoMode.add(this.AYACBMode);
        this.AYStereoModeButtonGroup.add(this.AYBACMode);
        this.AYBACMode.setText(bundle.getString("SettingsDialog.AYBACMode.RadioButton.text"));
        this.AYBACMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.AYBACModeActionPerformed(evt);
            }
        });
        this.AYStereoMode.add(this.AYBACMode);
        this.AY8912Panel.add(this.AYStereoMode);
        this.soundPanelTab.add(this.AY8912Panel);
        this.jTabbedPane1.addTab(bundle.getString("SettingsDialog.soundPanel.TabTitle"), this.soundPanelTab);
        this.tapePanelTab.setLayout(new GridLayout(2, 0));
        this.loadPanel.setBorder(BorderFactory.createTitledBorder(bundle.getString("SettingsDialog.loadPanel.border.text")));
        this.loadPanel.setLayout(new BoxLayout(this.loadPanel, 3));
        this.flashload.setText(bundle.getString("SettingsDialog.tapePanel.flashload.text"));
        this.flashload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.flashloadActionPerformed(evt);
            }
        });
        this.loadPanel.add(this.flashload);
        this.acceleratedLoad.setText(bundle.getString("SettingsDialog.tapePanel.acceleratedLoad.text"));
        this.acceleratedLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.acceleratedLoadActionPerformed(evt);
            }
        });
        this.loadPanel.add(this.acceleratedLoad);
        this.tapePanelTab.add(this.loadPanel);
        this.savePanel.setBorder(BorderFactory.createTitledBorder(bundle.getString("SettingsDialog.savePanel.border.text")));
        this.savePanel.setLayout(new BoxLayout(this.savePanel, 2));
        this.enableSaveTraps.setSelected(true);
        this.enableSaveTraps.setText(bundle.getString("SettingsDialog.savePanel.enableSaveTraps.text"));
        this.enableSaveTraps.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.enableSaveTrapsActionPerformed(evt);
            }
        });
        this.savePanel.add(this.enableSaveTraps);
        this.samplingPanel.setBorder(BorderFactory.createTitledBorder(bundle.getString("SettingsDialog.highSamplingFreq.border.text")));
        this.samplingPanel.setLayout(new BoxLayout(this.samplingPanel, 3));
        this.samplingButtonGroup.add(this.lowSampling);
        this.lowSampling.setSelected(true);
        this.lowSampling.setText("22050 Hz");
        this.lowSampling.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.lowSamplingActionPerformed(evt);
            }
        });
        this.samplingPanel.add(this.lowSampling);
        this.samplingButtonGroup.add(this.highSampling);
        this.highSampling.setText("44100 Hz");
        this.highSampling.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.highSamplingActionPerformed(evt);
            }
        });
        this.samplingPanel.add(this.highSampling);
        this.savePanel.add(this.samplingPanel);
        this.tapePanelTab.add(this.savePanel);
        this.jTabbedPane1.addTab(bundle.getString("SettingsDialog.tapePanel.TabTitle"), this.tapePanelTab);
        this.keyboardPanelTab.setLayout(new BoxLayout(this.keyboardPanelTab, 3));
        this.keyboard48kPanel.setBorder(BorderFactory.createTitledBorder(bundle.getString("SettingsDialog.keyboard48kPanel.title.text")));
        this.keyboard48kPanel.setLayout(new BoxLayout(this.keyboard48kPanel, 3));
        this.jLabel1.setHorizontalAlignment(0);
        this.jLabel1.setText(bundle.getString("SettingsDialog.jlabel1.text"));
        this.jPanel3.add(this.jLabel1);
        this.keyboard48kPanel.add(this.jPanel3);
        this.keyboardButtonGroup.add(this.issue2);
        this.issue2.setText(bundle.getString("SettingsDialog.issue2RadioButton.text"));
        this.issue2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.issue2ActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.issue2);
        this.keyboardButtonGroup.add(this.issue3);
        this.issue3.setSelected(true);
        this.issue3.setText(bundle.getString("SettingsDialog.issue3RadioButton.text"));
        this.issue3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.issue2ActionPerformed(evt);
            }
        });
        this.jPanel2.add(this.issue3);
        this.keyboard48kPanel.add(this.jPanel2);
        this.keyboardPanelTab.add(this.keyboard48kPanel);
        this.joystickPanel.setBorder(BorderFactory.createTitledBorder(bundle.getString("SettingsDialog.joystickPanel.border.text")));
        this.joystick.setModel(new DefaultComboBoxModel<String>(new String[] { "None", "Kempston", "Sinclair 1", "Sinclair 2", "Cursor/AGF/Protek", "Fuller" }));
        this.joystick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.joystickActionPerformed(evt);
            }
        });
        this.joystickPanel.add(this.joystick);
        this.keyboardPanelTab.add(this.joystickPanel);
        this.jTabbedPane1.addTab(bundle.getString("SettingsDialog.keyboardPanel.TabTitle"), this.keyboardPanelTab);
        this.multifacePanelTab.setLayout(new BoxLayout(this.multifacePanelTab, 3));
        this.multifacePanel.setBorder(BorderFactory.createTitledBorder(bundle.getString("SettingsDialog.MultifacePanel.border.text")));
        this.multifaceEnabled.setText(bundle.getString("SettingsDialog.multifacePanel.enabled.text"));
        this.multifaceEnabled.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.multifaceEnabledActionPerformed(evt);
            }
        });
        this.multifacePanel.add(this.multifaceEnabled);
        this.multifacePanelTab.add(this.multifacePanel);
        this.multifaceModelPanel.setBorder(BorderFactory.createTitledBorder(bundle.getString("SettingsDialog.MultifaceModelPanel.border.text")));
        this.multifaceModelPanel.setLayout(new GridLayout(2, 0));
        this.jLabel2.setHorizontalAlignment(0);
        this.jLabel2.setText(bundle.getString("SettingsDialog.MultifaceModelPanel.label.text"));
        this.multifaceModelPanel.add(this.jLabel2);
        this.jPanel1.setBorder(BorderFactory.createTitledBorder(bundle.getString("SettingsDialog.MultifaceModelPanelRadioButton.border.text")));
        this.jPanel1.setLayout(new GridLayout(2, 0));
        this.multifaceModelButtonGroup.add(this.multifaceOneRadioButton);
        this.multifaceOneRadioButton.setSelected(true);
        this.multifaceOneRadioButton.setText(bundle.getString("SettingsDialog.multifaceOne.RadioButton.text"));
        this.multifaceOneRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.multifaceOneRadioButtonActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.multifaceOneRadioButton);
        this.multifaceModelButtonGroup.add(this.multiface128RadioButton);
        this.multiface128RadioButton.setText(bundle.getString("SettingsDialog.multiface128.RadioButton.text"));
        this.multiface128RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SettingsDialog.this.multifaceOneRadioButtonActionPerformed(evt);
            }
        });
        this.jPanel1.add(this.multiface128RadioButton);
        this.multifaceModelPanel.add(this.jPanel1);
        this.multifacePanelTab.add(this.multifaceModelPanel);
        this.jTabbedPane1.addTab(bundle.getString("SettingsDialog.multifacePanel.TabTitle"), this.multifacePanelTab);
        this.add(this.jTabbedPane1, "Center");
    }
    
    private void closeButtonActionPerformed(final ActionEvent evt) {
        this.settingsDialog.setVisible(false);
    }
    
    private void spectrumModelActionPerformed(final ActionEvent evt) {
        this.settings.getSpectrumSettings().setDefaultModel(this.spectrumModel.getSelectedIndex());
    }
    
    private void ULAplusActionPerformed(final ActionEvent evt) {
        this.settings.getSpectrumSettings().setULAplus(this.ULAplus.isSelected());
    }
    
    private void speedStateChanged(final ChangeEvent evt) {
        this.settings.getSpectrumSettings().setFramesInt(this.speed.getValue());
    }
    
    private void issue2ActionPerformed(final ActionEvent evt) {
        this.settings.getSpectrumSettings().setIssue2(this.issue2.isSelected());
    }
    
    private void joystickActionPerformed(final ActionEvent evt) {
        this.settings.getKeyboardJoystickSettings().setJoystickModel(this.joystick.getSelectedIndex());
    }
    
    private void saveSettingsButtonActionPerformed(final ActionEvent evt) {
        final ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle");
        final int ret = JOptionPane.showConfirmDialog(this, bundle.getString("ARE_YOU_SURE_QUESTION"), bundle.getString("SAVE_SETTINGS_QUESTION"), 0, 3);
        if (ret == 1) {
            return;
        }
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
    
    private void soundMutedActionPerformed(final ActionEvent evt) {
        this.settings.getSpectrumSettings().setMutedSound(this.soundMuted.isSelected());
    }
    
    private void loadingNoiseActionPerformed(final ActionEvent evt) {
        this.settings.getSpectrumSettings().setLoadingNoise(this.loadingNoise.isSelected());
    }
    
    private void enabledAY48kActionPerformed(final ActionEvent evt) {
        this.settings.getSpectrumSettings().setAYEnabled48K(this.enabledAY48k.isSelected());
    }
    
    private void flashloadActionPerformed(final ActionEvent evt) {
        this.settings.getTapeSettings().setFlashload(this.flashload.isSelected());
    }
    
    private void acceleratedLoadActionPerformed(final ActionEvent evt) {
        this.settings.getTapeSettings().setAccelerateLoading(this.acceleratedLoad.isSelected());
    }
    
    private void doubleSizeActionPerformed(final ActionEvent evt) {
        this.settings.getSpectrumSettings().setDoubleSize(this.doubleSize.isSelected());
    }
    
    private void enableSaveTrapsActionPerformed(final ActionEvent evt) {
        this.settings.getTapeSettings().setEnableSaveTraps(this.enableSaveTraps.isSelected());
    }
    
    private void lowSamplingActionPerformed(final ActionEvent evt) {
        this.settings.getTapeSettings().setHighSamplingFreq(false);
    }
    
    private void highSamplingActionPerformed(final ActionEvent evt) {
        this.settings.getTapeSettings().setHighSamplingFreq(true);
    }
    
    private void AYMonoModeActionPerformed(final ActionEvent evt) {
        this.settings.getAY8912Settings().setSoundMode(0);
    }
    
    private void AYABCModeActionPerformed(final ActionEvent evt) {
        this.settings.getAY8912Settings().setSoundMode(1);
    }
    
    private void AYACBModeActionPerformed(final ActionEvent evt) {
        this.settings.getAY8912Settings().setSoundMode(2);
    }
    
    private void AYBACModeActionPerformed(final ActionEvent evt) {
        this.settings.getAY8912Settings().setSoundMode(3);
    }
    
    private void multifaceEnabledActionPerformed(final ActionEvent evt) {
        this.settings.getSpectrumSettings().setMultifaceEnabled(this.multifaceEnabled.isSelected());
    }
    
    private void multifaceOneRadioButtonActionPerformed(final ActionEvent evt) {
        this.settings.getSpectrumSettings().setMf128On48K(this.multiface128RadioButton.isSelected());
    }
    
    private void hifiSoundActionPerformed(final ActionEvent evt) {
        this.settings.getSpectrumSettings().setHifiSound(this.hifiSound.isSelected());
    }
}
