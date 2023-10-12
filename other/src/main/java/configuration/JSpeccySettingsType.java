// 
// Decompiled by Procyon v0.5.36
// 

package configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JSpeccySettingsType", propOrder = { "spectrumSettings", "memorySettings", "tapeSettings", "keyboardJoystickSettings", "ay8912Settings", "recentFilesSettings" })
public class JSpeccySettingsType
{
    @XmlElement(name = "SpectrumSettings", required = true)
    protected SpectrumType spectrumSettings;
    @XmlElement(name = "MemorySettings", required = true)
    protected MemoryType memorySettings;
    @XmlElement(name = "TapeSettings", required = true)
    protected TapeType tapeSettings;
    @XmlElement(name = "KeyboardJoystickSettings", required = true)
    protected KeyboardJoystickType keyboardJoystickSettings;
    @XmlElement(name = "AY8912Settings", required = true)
    protected AY8912Type ay8912Settings;
    @XmlElement(name = "RecentFilesSettings", required = true)
    protected RecentFilesType recentFilesSettings;
    
    public SpectrumType getSpectrumSettings() {
        return this.spectrumSettings;
    }
    
    public void setSpectrumSettings(final SpectrumType value) {
        this.spectrumSettings = value;
    }
    
    public MemoryType getMemorySettings() {
        return this.memorySettings;
    }
    
    public void setMemorySettings(final MemoryType value) {
        this.memorySettings = value;
    }
    
    public TapeType getTapeSettings() {
        return this.tapeSettings;
    }
    
    public void setTapeSettings(final TapeType value) {
        this.tapeSettings = value;
    }
    
    public KeyboardJoystickType getKeyboardJoystickSettings() {
        return this.keyboardJoystickSettings;
    }
    
    public void setKeyboardJoystickSettings(final KeyboardJoystickType value) {
        this.keyboardJoystickSettings = value;
    }
    
    public AY8912Type getAY8912Settings() {
        return this.ay8912Settings;
    }
    
    public void setAY8912Settings(final AY8912Type value) {
        this.ay8912Settings = value;
    }
    
    public RecentFilesType getRecentFilesSettings() {
        return this.recentFilesSettings;
    }
    
    public void setRecentFilesSettings(final RecentFilesType value) {
        this.recentFilesSettings = value;
    }
}
