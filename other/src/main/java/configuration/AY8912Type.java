// 
// Decompiled by Procyon v0.5.36
// 

package configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AY8912Type", propOrder = { "soundMode" })
public class AY8912Type
{
    @XmlElement(defaultValue = "0")
    protected int soundMode;
    
    public int getSoundMode() {
        return this.soundMode;
    }
    
    public void setSoundMode(final int value) {
        this.soundMode = value;
    }
}
