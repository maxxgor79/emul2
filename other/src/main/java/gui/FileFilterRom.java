// 
// Decompiled by Procyon v0.5.36
// 

package gui;

import java.util.ResourceBundle;
import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FileFilterRom extends FileFilter
{
    @Override
    public boolean accept(final File fIn) {
        return fIn.getName().toLowerCase().endsWith(".rom") || fIn.isDirectory();
    }
    
    @Override
    public String getDescription() {
        return ResourceBundle.getBundle("gui/Bundle").getString("ROM_TYPE");
    }
}
