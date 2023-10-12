// 
// Decompiled by Procyon v0.5.36
// 

package configuration;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _JSpeccySettings_QNAME;
    
    public JSpeccySettingsType createJSpeccySettingsType() {
        return new JSpeccySettingsType();
    }
    
    public RecentFilesType createRecentFilesType() {
        return new RecentFilesType();
    }
    
    public KeyboardJoystickType createKeyboardJoystickType() {
        return new KeyboardJoystickType();
    }
    
    public MemoryType createMemoryType() {
        return new MemoryType();
    }
    
    public TapeType createTapeType() {
        return new TapeType();
    }
    
    public SpectrumType createSpectrumType() {
        return new SpectrumType();
    }
    
    public AY8912Type createAY8912Type() {
        return new AY8912Type();
    }
    
    @XmlElementDecl(namespace = "http://xml.netbeans.org/schema/JSpeccy", name = "JSpeccySettings")
    public JAXBElement<JSpeccySettingsType> createJSpeccySettings(final JSpeccySettingsType value) {
        return (JAXBElement<JSpeccySettingsType>)new JAXBElement(ObjectFactory._JSpeccySettings_QNAME, (Class)JSpeccySettingsType.class, (Class)null, (Object)value);
    }
    
    static {
        _JSpeccySettings_QNAME = new QName("http://xml.netbeans.org/schema/JSpeccy", "JSpeccySettings");
    }
}
