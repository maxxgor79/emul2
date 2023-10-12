// 
// Decompiled by Procyon v0.5.36
// 

package machine;

import java.util.TimerTask;

public class SpectrumTimer extends TimerTask
{
    private Spectrum spectrum;
    private long now;
    private long diff;
    
    public SpectrumTimer(final Spectrum spectrum) {
        this.spectrum = spectrum;
    }
    
    @Override
    public synchronized void run() {
        this.now = System.currentTimeMillis();
        this.diff = this.now - this.scheduledExecutionTime();
        if (this.diff < 51L) {
            this.spectrum.generateFrame();
            this.spectrum.drawFrame();
        }
    }
}
