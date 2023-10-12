// 
// Decompiled by Procyon v0.5.36
// 

package configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpectrumType", propOrder = { "issue2", "ayEnabled48K", "mutedSound", "loadingNoise", "ulAplus", "defaultModel", "framesInt", "doubleSize", "multifaceEnabled", "mf128On48K", "hifiSound" })
public class SpectrumType
{
    @XmlElement(name = "Issue2", defaultValue = "false")
    protected boolean issue2;
    @XmlElement(name = "AYEnabled48k", defaultValue = "false")
    protected boolean ayEnabled48K;
    @XmlElement(defaultValue = "false")
    protected boolean mutedSound;
    @XmlElement(defaultValue = "true")
    protected boolean loadingNoise;
    @XmlElement(name = "ULAplus", defaultValue = "false")
    protected boolean ulAplus;
    @XmlElement(defaultValue = "1")
    protected int defaultModel;
    @XmlElement(defaultValue = "2")
    protected int framesInt;
    @XmlElement(defaultValue = "false")
    protected boolean doubleSize;
    @XmlElement(defaultValue = "false")
    protected boolean multifaceEnabled;
    @XmlElement(name = "mf128on48K", defaultValue = "false")
    protected boolean mf128On48K;
    @XmlElement(defaultValue = "false")
    protected boolean hifiSound;
    
    public boolean isIssue2() {
        return this.issue2;
    }
    
    public void setIssue2(final boolean value) {
        this.issue2 = value;
    }
    
    public boolean isAYEnabled48K() {
        return this.ayEnabled48K;
    }
    
    public void setAYEnabled48K(final boolean value) {
        this.ayEnabled48K = value;
    }
    
    public boolean isMutedSound() {
        return this.mutedSound;
    }
    
    public void setMutedSound(final boolean value) {
        this.mutedSound = value;
    }
    
    public boolean isLoadingNoise() {
        return this.loadingNoise;
    }
    
    public void setLoadingNoise(final boolean value) {
        this.loadingNoise = value;
    }
    
    public boolean isULAplus() {
        return this.ulAplus;
    }
    
    public void setULAplus(final boolean value) {
        this.ulAplus = value;
    }
    
    public int getDefaultModel() {
        return this.defaultModel;
    }
    
    public void setDefaultModel(final int value) {
        this.defaultModel = value;
    }
    
    public int getFramesInt() {
        return this.framesInt;
    }
    
    public void setFramesInt(final int value) {
        this.framesInt = value;
    }
    
    public boolean isDoubleSize() {
        return this.doubleSize;
    }
    
    public void setDoubleSize(final boolean value) {
        this.doubleSize = value;
    }
    
    public boolean isMultifaceEnabled() {
        return this.multifaceEnabled;
    }
    
    public void setMultifaceEnabled(final boolean value) {
        this.multifaceEnabled = value;
    }
    
    public boolean isMf128On48K() {
        return this.mf128On48K;
    }
    
    public void setMf128On48K(final boolean value) {
        this.mf128On48K = value;
    }
    
    public boolean isHifiSound() {
        return this.hifiSound;
    }
    
    public void setHifiSound(final boolean value) {
        this.hifiSound = value;
    }
}
