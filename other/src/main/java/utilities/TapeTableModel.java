// 
// Decompiled by Procyon v0.5.36
// 

package utilities;

import java.util.ResourceBundle;
import javax.swing.table.AbstractTableModel;

public class TapeTableModel extends AbstractTableModel
{
    private Tape tape;
    
    public TapeTableModel(final Tape device) {
        this.tape = device;
    }
    
    @Override
    public int getRowCount() {
        return this.tape.getNumBlocks();
    }
    
    @Override
    public int getColumnCount() {
        return 3;
    }
    
    @Override
    public Object getValueAt(final int row, final int col) {
        String msg = null;
        switch (col) {
            case 0: {
                return String.format("%4d", row + 1);
            }
            case 1: {
                msg = this.tape.getBlockType(row);
                break;
            }
            case 2: {
                msg = this.tape.getBlockInfo(row);
                break;
            }
            default: {
                return "NOT EXISTANT COLUMN!";
            }
        }
        return msg;
    }
    
    @Override
    public String getColumnName(final int col) {
        final ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle");
        String msg = null;
        switch (col) {
            case 0: {
                msg = bundle.getString("JSpeccy.tapeCatalog.columnModel.title0");
                break;
            }
            case 1: {
                msg = bundle.getString("JSpeccy.tapeCatalog.columnModel.title1");
                break;
            }
            case 2: {
                msg = bundle.getString("JSpeccy.tapeCatalog.columnModel.title2");
                break;
            }
            default: {
                msg = "COLUMN ERROR!";
                break;
            }
        }
        return msg;
    }
}
