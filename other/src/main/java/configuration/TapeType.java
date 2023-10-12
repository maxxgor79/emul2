// 
// Decompiled by Procyon v0.5.36
// 

package configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TapeType", propOrder = { "flashload", "accelerateLoading", "enableSaveTraps", "highSamplingFreq" })
public class TapeType
{
    @XmlElement(defaultValue = "true")
    protected boolean flashload;
    @XmlElement(defaultValue = "true")
    protected boolean accelerateLoading;
    @XmlElement(defaultValue = "true")
    protected boolean enableSaveTraps;
    @XmlElement(defaultValue = "false")
    protected boolean highSamplingFreq;
    
    public boolean isFlashload() {
        return this.flashload;
    }
    
    public void setFlashload(final boolean value) {
        this.flashload = value;
    }
    
    public boolean isAccelerateLoading() {
        return this.accelerateLoading;
    }
    
    public void setAccelerateLoading(final boolean value) {
        this.accelerateLoading = value;
    }
    
    public boolean isEnableSaveTraps() {
        return this.enableSaveTraps;
    }
    
    public void setEnableSaveTraps(final boolean value) {
        this.enableSaveTraps = value;
    }
    
    public boolean isHighSamplingFreq() {
        return this.highSamplingFreq;
    }
    
    public void setHighSamplingFreq(final boolean value) {
        this.highSamplingFreq = value;
    }
}
