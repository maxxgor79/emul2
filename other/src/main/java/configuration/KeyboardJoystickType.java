// 
// Decompiled by Procyon v0.5.36
// 

package configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KeyboardJoystickType", propOrder = { "joystickModel" })
public class KeyboardJoystickType
{
    @XmlElement(name = "JoystickModel", defaultValue = "0")
    protected int joystickModel;
    
    public int getJoystickModel() {
        return this.joystickModel;
    }
    
    public void setJoystickModel(final int value) {
        this.joystickModel = value;
    }
}
