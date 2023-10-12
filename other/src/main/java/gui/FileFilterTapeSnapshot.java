// 
// Decompiled by Procyon v0.5.36
// 

package gui;

import java.util.ResourceBundle;
import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FileFilterTapeSnapshot extends FileFilter
{
    @Override
    public boolean accept(final File fIn) {
        return fIn.getName().toLowerCase().endsWith(".sna") || fIn.getName().toLowerCase().endsWith(".z80") || fIn.getName().toLowerCase().endsWith(".szx") || fIn.getName().toLowerCase().endsWith(".tap") || fIn.getName().toLowerCase().endsWith(".tzx") || fIn.isDirectory();
    }
    
    @Override
    public String getDescription() {
        return ResourceBundle.getBundle("gui/Bundle").getString("SNAPSHOT_TYPE");
    }
}
