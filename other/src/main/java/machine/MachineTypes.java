// 
// Decompiled by Procyon v0.5.36
// 

package machine;

public enum MachineTypes
{
    SPECTRUM16K(0), 
    SPECTRUM48K(1), 
    SPECTRUM128K(2), 
    SPECTRUMPLUS2(3), 
    SPECTRUMPLUS2A(4), 
    SPECTRUMPLUS3(5);
    
    public CodeModel codeModel;
    private String longModelName;
    private String shortModelName;
    public int tstatesFrame;
    public int tstatesLine;
    public int upBorderWidth;
    public int scanLines;
    public int firstScrByte;
    public int firstScrUpdate;
    public int lastScrUpdate;
    public int firstBorderUpdate;
    public int lastBorderUpdate;
    public int outOffset;
    public int lengthINT;
    private boolean hasAY8912;
    private boolean hasDisk;
    
    private MachineTypes(final int model) {
        switch (model) {
            case 0: {
                this.longModelName = "ZX Spectrum 16K";
                this.shortModelName = "16k";
                this.tstatesFrame = 69888;
                this.tstatesLine = 224;
                this.upBorderWidth = 64;
                this.scanLines = 312;
                this.lengthINT = 32;
                this.firstScrByte = 14336;
                this.firstScrUpdate = 14328;
                this.lastScrUpdate = 57237;
                this.firstBorderUpdate = 32 * this.tstatesLine - 16;
                this.lastBorderUpdate = 288 * this.tstatesLine;
                this.outOffset = 3;
                this.hasAY8912 = false;
                this.hasDisk = false;
                this.codeModel = CodeModel.SPECTRUM48K;
                break;
            }
            case 1: {
                this.longModelName = "ZX Spectrum 48K";
                this.shortModelName = "48k";
                this.tstatesFrame = 69888;
                this.tstatesLine = 224;
                this.upBorderWidth = 64;
                this.scanLines = 312;
                this.lengthINT = 32;
                this.firstScrByte = 14336;
                this.firstScrUpdate = 14328;
                this.lastScrUpdate = 57237;
                this.firstBorderUpdate = 32 * this.tstatesLine - 16;
                this.lastBorderUpdate = 288 * this.tstatesLine;
                this.outOffset = 3;
                this.hasAY8912 = false;
                this.hasDisk = false;
                this.codeModel = CodeModel.SPECTRUM48K;
                break;
            }
            case 2: {
                this.longModelName = "ZX Spectrum 128K";
                this.shortModelName = "128";
                this.tstatesFrame = 70908;
                this.tstatesLine = 228;
                this.upBorderWidth = 63;
                this.scanLines = 311;
                this.lengthINT = 36;
                this.firstScrByte = 14361;
                this.firstScrUpdate = 14344;
                this.lastScrUpdate = 58040;
                this.firstBorderUpdate = 31 * this.tstatesLine - 16;
                this.lastBorderUpdate = 288 * this.tstatesLine;
                this.outOffset = 1;
                this.hasAY8912 = true;
                this.hasDisk = false;
                this.codeModel = CodeModel.SPECTRUM128K;
                break;
            }
            case 3: {
                this.longModelName = "Amstrad ZX Spectrum +2";
                this.shortModelName = " +2";
                this.tstatesFrame = 70908;
                this.tstatesLine = 228;
                this.upBorderWidth = 63;
                this.scanLines = 311;
                this.lengthINT = 36;
                this.firstScrByte = 14361;
                this.firstScrUpdate = 14344;
                this.lastScrUpdate = 58040;
                this.firstBorderUpdate = 31 * this.tstatesLine - 16;
                this.lastBorderUpdate = 288 * this.tstatesLine;
                this.outOffset = 1;
                this.hasAY8912 = true;
                this.hasDisk = false;
                this.codeModel = CodeModel.SPECTRUM128K;
                break;
            }
            case 4: {
                this.longModelName = "ZX Spectrum +2A";
                this.shortModelName = " +2A";
                this.tstatesFrame = 70908;
                this.tstatesLine = 228;
                this.upBorderWidth = 63;
                this.scanLines = 311;
                this.lengthINT = 36;
                this.firstScrByte = 14364;
                this.firstScrUpdate = 14356;
                this.lastScrUpdate = 58040;
                this.firstBorderUpdate = 31 * this.tstatesLine - 16;
                this.lastBorderUpdate = 288 * this.tstatesLine;
                this.outOffset = 1;
                this.hasAY8912 = true;
                this.hasDisk = false;
                this.codeModel = CodeModel.SPECTRUMPLUS3;
                break;
            }
            case 5: {
                this.longModelName = "ZX Spectrum +3";
                this.shortModelName = " +3";
                this.tstatesFrame = 70908;
                this.tstatesLine = 228;
                this.upBorderWidth = 63;
                this.scanLines = 311;
                this.lengthINT = 36;
                this.firstScrByte = 14364;
                this.firstScrUpdate = 14356;
                this.lastScrUpdate = 58040;
                this.firstBorderUpdate = 31 * this.tstatesLine - 16;
                this.lastBorderUpdate = 288 * this.tstatesLine;
                this.outOffset = 1;
                this.hasAY8912 = true;
                this.hasDisk = true;
                this.codeModel = CodeModel.SPECTRUMPLUS3;
                break;
            }
        }
    }
    
    public String getLongModelName() {
        return this.longModelName;
    }
    
    public String getShortModelName() {
        return this.shortModelName;
    }
    
    public int getTstatesFrame() {
        return this.tstatesFrame;
    }
    
    public int getTstatesLine() {
        return this.tstatesLine;
    }
    
    public int getBorderLines() {
        return this.upBorderWidth;
    }
    
    public int getScanLines() {
        return this.scanLines;
    }
    
    public int getTstatesINT() {
        return this.lengthINT;
    }
    
    public boolean hasAY8912() {
        return this.hasAY8912;
    }
    
    public boolean hasDisk() {
        return this.hasDisk;
    }
    
    public enum CodeModel
    {
        SPECTRUM48K, 
        SPECTRUM128K, 
        SPECTRUMPLUS3;
    }
}
