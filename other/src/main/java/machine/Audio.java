// 
// Decompiled by Procyon v0.5.36
// 

package machine;

import java.util.Arrays;
import javax.sound.sampled.LineUnavailableException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.Line;
import javax.sound.sampled.AudioSystem;
import configuration.AY8912Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

class Audio
{
    private int samplingFrequency;
    private SourceDataLine line;
    private DataLine.Info infoDataLine;
    private AudioFormat fmt;
    private SourceDataLine sdl;
    private final byte[] buf;
    private final int[] beeper;
    private final int[] ayBufA;
    private final int[] ayBufB;
    private final int[] ayBufC;
    private int bufp;
    private int level;
    private int lastLevel;
    private int audiotstates;
    private int samplesPerFrame;
    private int frameSize;
    private int bufferSize;
    private int soundMode;
    private float timeRem;
    private float spf;
    private MachineTypes spectrumModel;
    private boolean enabledAY;
    private AY8912Type settings;
    private AY8912 ay;
    
    Audio(final AY8912Type ayConf) {
        this.buf = new byte[4096];
        this.beeper = new int[1024];
        this.ayBufA = new int[1024];
        this.ayBufB = new int[1024];
        this.ayBufC = new int[1024];
        this.settings = ayConf;
        this.line = null;
        this.samplingFrequency = 32000;
    }
    
    synchronized void open(final MachineTypes model, final AY8912 ay8912, final boolean hasAY, final int freq) {
        this.samplingFrequency = freq;
        this.soundMode = this.settings.getSoundMode();
        if (this.soundMode < 0 || this.soundMode > 3) {
            this.soundMode = 0;
        }
        final int channels = (this.soundMode > 0) ? 2 : 1;
        if (this.line == null) {
            try {
                this.fmt = new AudioFormat((float)this.samplingFrequency, 16, channels, true, false);
                this.infoDataLine = new DataLine.Info(SourceDataLine.class, this.fmt);
                this.sdl = (SourceDataLine)AudioSystem.getLine(this.infoDataLine);
            }
            catch (Exception excpt) {
                Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, excpt);
            }
            this.enabledAY = hasAY;
            this.timeRem = 0.0f;
            this.samplesPerFrame = this.samplingFrequency / 50;
            this.frameSize = this.samplesPerFrame * 2 * channels;
            if (model != this.spectrumModel) {
                ay8912.setSpectrumModel(this.spectrumModel = model);
            }
            this.spf = this.spectrumModel.getTstatesFrame() / (float)this.samplesPerFrame;
            final int n = 0;
            this.lastLevel = n;
            this.level = n;
            this.bufp = n;
            this.audiotstates = n;
            if (this.soundMode > 0) {
                ay8912.setMaxAmplitude(21500);
            }
            else {
                ay8912.setMaxAmplitude(16300);
            }
            this.bufferSize = this.frameSize * 5;
            try {
                this.sdl.open(this.fmt, this.bufferSize);
                this.sdl.start();
                this.line = this.sdl;
            }
            catch (LineUnavailableException ex) {
                Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
            }
            ay8912.setBufferChannels(this.ayBufA, this.ayBufB, this.ayBufC);
            ay8912.setAudioFreq(this.samplingFrequency);
            ay8912.startFrame();
            this.ay = ay8912;
        }
    }
    
    synchronized void close() {
        if (this.line != null) {
            this.line.drain();
            this.line.stop();
            this.line.close();
            this.line = null;
        }
    }
    
    synchronized void updateAudio(int tstates, final int value) {
        tstates -= this.audiotstates;
        this.audiotstates += tstates;
        float time = (float)tstates;
        synchronized (this.beeper) {
            if (time + this.timeRem <= this.spf) {
                this.timeRem += time;
                this.level += (int)(time / this.spf * value);
                return;
            }
            this.level += (int)((this.spf - this.timeRem) / this.spf * value);
            time -= this.spf - this.timeRem;
            this.lastLevel = this.lastLevel + this.level >>> 1;
            this.beeper[this.bufp++] = this.lastLevel;
            while (time > this.spf) {
                this.lastLevel = this.lastLevel + value >>> 1;
                this.beeper[this.bufp++] = this.lastLevel;
                time -= this.spf;
            }
        }
        this.level = (int)(value * (time / this.spf));
        this.timeRem = time;
    }
    
    private void flushBuffer(final int len) {
        if (this.line != null) {
            final int available = this.line.available();
            if (available < this.frameSize >>> 1) {
                return;
            }
            synchronized (this.buf) {
                this.line.write(this.buf, 0, len);
                if (available == this.bufferSize) {
                    this.line.write(this.buf, 0, len);
                }
            }
        }
    }
    
    public void flush() {
        final int n = 0;
        this.level = n;
        this.bufp = n;
        this.timeRem = 0.0f;
        if (this.line != null) {
            this.line.flush();
        }
    }
    
    public void endFrame() {
        if (this.bufp == 0) {
            return;
        }
        int ptr = 0;
        if (this.enabledAY) {
            this.bufp = this.ay.getSampleCount();
            this.ay.endFrame();
        }
        switch (this.soundMode) {
            case 1: {
                ptr = this.endFrameStereoABC();
                break;
            }
            case 2: {
                ptr = this.endFrameStereoACB();
                break;
            }
            case 3: {
                ptr = this.endFrameStereoBAC();
                break;
            }
            default: {
                ptr = this.endFrameMono();
                break;
            }
        }
        this.flushBuffer(ptr);
        this.bufp = 0;
        this.audiotstates -= this.spectrumModel.tstatesFrame;
    }
    
    private int endFrameMono() {
        int ptr = 0;
        if (this.enabledAY) {
            for (int idx = 0; idx < this.bufp; ++idx) {
                final int sample = -32760 + (this.beeper[idx] + this.ayBufA[idx] + this.ayBufB[idx] + this.ayBufC[idx]);
                this.buf[ptr++] = (byte)sample;
                this.buf[ptr++] = (byte)(sample >>> 8);
            }
        }
        else {
            for (int idx = 0; idx < this.bufp; ++idx) {
                final int sample = -32760 + this.beeper[idx];
                this.buf[ptr++] = (byte)sample;
                this.buf[ptr++] = (byte)(sample >>> 8);
            }
        }
        return ptr;
    }
    
    private int endFrameStereoABC() {
        int ptr = 0;
        if (this.enabledAY) {
            for (int idx = 0; idx < this.bufp; ++idx) {
                final int center = (int)(this.ayBufB[idx] * 0.7);
                final int sampleL = -32760 + (this.beeper[idx] + this.ayBufA[idx] + center + this.ayBufC[idx] / 3);
                final int sampleR = -32760 + (this.beeper[idx] + this.ayBufA[idx] / 3 + center + this.ayBufC[idx]);
                this.buf[ptr++] = (byte)sampleL;
                this.buf[ptr++] = (byte)(sampleL >>> 8);
                this.buf[ptr++] = (byte)sampleR;
                this.buf[ptr++] = (byte)(sampleR >>> 8);
            }
        }
        else {
            for (int idx2 = 0; idx2 < this.bufp; ++idx2) {
                final int sample = -32760 + this.beeper[idx2];
                final byte lsb = (byte)sample;
                final byte msb = (byte)(sample >>> 8);
                this.buf[ptr++] = lsb;
                this.buf[ptr++] = msb;
                this.buf[ptr++] = lsb;
                this.buf[ptr++] = msb;
            }
        }
        return ptr;
    }
    
    private int endFrameStereoACB() {
        int ptr = 0;
        if (this.enabledAY) {
            for (int idx = 0; idx < this.bufp; ++idx) {
                final int center = (int)(this.ayBufC[idx] * 0.7);
                final int sampleL = -32760 + (this.beeper[idx] + this.ayBufA[idx] + center + this.ayBufB[idx] / 3);
                final int sampleR = -32760 + (this.beeper[idx] + this.ayBufA[idx] / 3 + center + this.ayBufB[idx]);
                this.buf[ptr++] = (byte)sampleL;
                this.buf[ptr++] = (byte)(sampleL >>> 8);
                this.buf[ptr++] = (byte)sampleR;
                this.buf[ptr++] = (byte)(sampleR >>> 8);
            }
        }
        else {
            for (int idx2 = 0; idx2 < this.bufp; ++idx2) {
                final int sample = -32760 + this.beeper[idx2];
                final byte lsb = (byte)sample;
                final byte msb = (byte)(sample >>> 8);
                this.buf[ptr++] = lsb;
                this.buf[ptr++] = msb;
                this.buf[ptr++] = lsb;
                this.buf[ptr++] = msb;
            }
        }
        return ptr;
    }
    
    private int endFrameStereoBAC() {
        int ptr = 0;
        if (this.enabledAY) {
            for (int idx = 0; idx < this.bufp; ++idx) {
                final int center = (int)(this.ayBufA[idx] * 0.7);
                final int sampleL = -32760 + (this.beeper[idx] + this.ayBufB[idx] + center + this.ayBufC[idx] / 3);
                final int sampleR = -32760 + (this.beeper[idx] + this.ayBufB[idx] / 3 + center + this.ayBufC[idx]);
                this.buf[ptr++] = (byte)sampleL;
                this.buf[ptr++] = (byte)(sampleL >>> 8);
                this.buf[ptr++] = (byte)sampleR;
                this.buf[ptr++] = (byte)(sampleR >>> 8);
            }
        }
        else {
            for (int idx2 = 0; idx2 < this.bufp; ++idx2) {
                final int sample = -32760 + this.beeper[idx2];
                final byte lsb = (byte)sample;
                final byte msb = (byte)(sample >>> 8);
                this.buf[ptr++] = lsb;
                this.buf[ptr++] = msb;
                this.buf[ptr++] = lsb;
                this.buf[ptr++] = msb;
            }
        }
        return ptr;
    }
    
    public void reset() {
        this.audiotstates = 0;
        this.bufp = 0;
        Arrays.fill(this.beeper, 0);
        Arrays.fill(this.buf, (byte)0);
    }
}
