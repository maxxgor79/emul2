// 
// Decompiled by Procyon v0.5.36
// 

package machine;

import java.util.Arrays;

public final class AY8912
{
    private static final int FineToneA = 0;
    private static final int CoarseToneA = 1;
    private static final int FineToneB = 2;
    private static final int CoarseToneB = 3;
    private static final int FineToneC = 4;
    private static final int CoarseToneC = 5;
    private static final int NoisePeriod = 6;
    private static final int Mixer = 7;
    private static final int AmplitudeA = 8;
    private static final int AmplitudeB = 9;
    private static final int AmplitudeC = 10;
    private static final int FineEnvelope = 11;
    private static final int CoarseEnvelope = 12;
    private static final int EnvelopeShapeCycle = 13;
    private static final int IOPortA = 14;
    private static final int IOPortB = 15;
    private static final int TONE_A = 1;
    private static final int TONE_B = 2;
    private static final int TONE_C = 4;
    private static final int NOISE_A = 8;
    private static final int NOISE_B = 16;
    private static final int NOISE_C = 32;
    private static final int ENVELOPE = 16;
    private static final int HOLD = 1;
    private static final int ALTERNATE = 2;
    private static final int ATTACK = 4;
    private static final int CONTINUE = 8;
    private int periodA;
    private int periodB;
    private int periodC;
    private int periodN;
    private int counterA;
    private int counterB;
    private int counterC;
    private int amplitudeA;
    private int amplitudeB;
    private int amplitudeC;
    private int counterN;
    private int rng;
    private int envelopePeriod;
    private int envelopeCounter;
    private boolean Continue;
    private boolean Attack;
    private int amplitudeEnv;
    private int maxAmplitude;
    private int addressLatch;
    private int[] regAY;
    private static final double[] volumeRate;
    private int[] volumeLevel;
    private int[] bufA;
    private int[] bufB;
    private int[] bufC;
    private int pbuf;
    private int FREQ;
    private double step;
    private double stepCounter;
    private int nSteps;
    private boolean toneA;
    private boolean toneB;
    private boolean toneC;
    private boolean toneN;
    private boolean disableToneA;
    private boolean disableToneB;
    private boolean disableToneC;
    private boolean disableNoiseA;
    private boolean disableNoiseB;
    private boolean disableNoiseC;
    private boolean envA;
    private boolean envB;
    private boolean envC;
    private int volumeA;
    private int volumeB;
    private int volumeC;
    private int lastA;
    private int lastB;
    private int lastC;
    private int audiotstates;
    private int samplesPerFrame;
    private MachineTypes spectrumModel;
    
    AY8912() {
        this.regAY = new int[16];
        this.volumeLevel = new int[16];
        this.maxAmplitude = 16384;
        for (int idx = 0; idx < this.volumeLevel.length; ++idx) {
            this.volumeLevel[idx] = (int)(this.maxAmplitude * AY8912.volumeRate[idx]);
        }
    }
    
    public void setSpectrumModel(final MachineTypes model) {
        this.spectrumModel = model;
        if (this.samplesPerFrame != 0) {
            this.step = this.spectrumModel.tstatesFrame / (double)this.samplesPerFrame;
        }
        this.reset();
    }
    
    public void setMaxAmplitude(final int amplitude) {
        this.maxAmplitude = amplitude;
        for (int idx = 0; idx < this.volumeLevel.length; ++idx) {
            this.volumeLevel[idx] = (int)(this.maxAmplitude * AY8912.volumeRate[idx]);
        }
    }
    
    public void setAudioFreq(final int freq) {
        this.FREQ = freq;
        this.samplesPerFrame = this.FREQ / 50;
        this.step = this.spectrumModel.tstatesFrame / (double)this.samplesPerFrame;
    }
    
    public int getAddressLatch() {
        return this.addressLatch;
    }
    
    public void setAddressLatch(final int value) {
        this.addressLatch = (value & 0xF);
    }
    
    public int readRegister() {
        if (this.addressLatch >= 14 && (this.regAY[7] >> this.addressLatch - 8 & 0x1) == 0x0) {
            return 255;
        }
        return this.regAY[this.addressLatch];
    }
    
    public void writeRegister(final int value) {
        this.regAY[this.addressLatch] = (value & 0xFF);
        switch (this.addressLatch) {
            case 0:
            case 1: {
                final int[] regAY = this.regAY;
                final int n = 1;
                regAY[n] &= 0xF;
                this.periodA = this.regAY[1] * 256 + this.regAY[0];
                break;
            }
            case 2:
            case 3: {
                final int[] regAY2 = this.regAY;
                final int n2 = 3;
                regAY2[n2] &= 0xF;
                this.periodB = this.regAY[3] * 256 + this.regAY[2];
                break;
            }
            case 4:
            case 5: {
                final int[] regAY3 = this.regAY;
                final int n3 = 5;
                regAY3[n3] &= 0xF;
                this.periodC = this.regAY[5] * 256 + this.regAY[4];
                break;
            }
            case 6: {
                final int[] regAY4 = this.regAY;
                final int addressLatch = this.addressLatch;
                regAY4[addressLatch] &= 0x1F;
                break;
            }
            case 7: {
                this.disableToneA = ((this.regAY[7] & 0x1) != 0x0);
                this.disableToneB = ((this.regAY[7] & 0x2) != 0x0);
                this.disableToneC = ((this.regAY[7] & 0x4) != 0x0);
                this.disableNoiseA = ((this.regAY[7] & 0x8) != 0x0);
                this.disableNoiseB = ((this.regAY[7] & 0x10) != 0x0);
                this.disableNoiseC = ((this.regAY[7] & 0x20) != 0x0);
                break;
            }
            case 8: {
                final int[] regAY5 = this.regAY;
                final int addressLatch2 = this.addressLatch;
                regAY5[addressLatch2] &= 0x1F;
                this.amplitudeA = this.volumeLevel[value & 0xF];
                this.envA = ((this.regAY[8] & 0x10) != 0x0);
                break;
            }
            case 9: {
                final int[] regAY6 = this.regAY;
                final int addressLatch3 = this.addressLatch;
                regAY6[addressLatch3] &= 0x1F;
                this.amplitudeB = this.volumeLevel[value & 0xF];
                this.envB = ((this.regAY[9] & 0x10) != 0x0);
                break;
            }
            case 10: {
                final int[] regAY7 = this.regAY;
                final int addressLatch4 = this.addressLatch;
                regAY7[addressLatch4] &= 0x1F;
                this.amplitudeC = this.volumeLevel[value & 0xF];
                this.envC = ((this.regAY[10] & 0x10) != 0x0);
                break;
            }
            case 11:
            case 12: {
                this.envelopePeriod = this.regAY[12] * 256 + this.regAY[11];
                if (this.envelopePeriod == 0) {
                    this.envelopePeriod = 1;
                    break;
                }
                this.envelopePeriod <<= 1;
                break;
            }
            case 13: {
                final int[] regAY8 = this.regAY;
                final int addressLatch5 = this.addressLatch;
                regAY8[addressLatch5] &= 0xF;
                this.envelopeCounter = 0;
                if ((value & 0x4) != 0x0) {
                    this.amplitudeEnv = 0;
                    this.Attack = true;
                }
                else {
                    this.amplitudeEnv = 15;
                    this.Attack = false;
                }
                this.Continue = false;
                break;
            }
        }
    }
    
    public void updateAY(final int tstates) {
        while (this.audiotstates < tstates) {
            this.audiotstates += 16;
            if (++this.counterA >= this.periodA) {
                this.toneA = !this.toneA;
                this.counterA = 0;
            }
            if (++this.counterB >= this.periodB) {
                this.toneB = !this.toneB;
                this.counterB = 0;
            }
            if (++this.counterC >= this.periodC) {
                this.toneC = !this.toneC;
                this.counterC = 0;
            }
            if (++this.counterN >= this.periodN) {
                this.counterN = 0;
                this.periodN = this.regAY[6];
                if (this.periodN == 0) {
                    this.periodN = 1;
                }
                this.periodN <<= 1;
                if ((this.rng + 1 & 0x2) != 0x0) {
                    this.toneN = !this.toneN;
                }
                if ((this.rng & 0x1) != 0x0) {
                    this.rng ^= 0x24000;
                }
                this.rng >>>= 1;
            }
            if (++this.envelopeCounter >= this.envelopePeriod) {
                this.envelopeCounter = 0;
                if (!this.Continue) {
                    if (this.Attack) {
                        ++this.amplitudeEnv;
                    }
                    else {
                        --this.amplitudeEnv;
                    }
                }
                if (this.amplitudeEnv < 0 || this.amplitudeEnv > 15) {
                    if ((this.regAY[13] & 0x8) == 0x0) {
                        this.amplitudeEnv = 0;
                        this.Continue = true;
                    }
                    else {
                        if ((this.regAY[13] & 0x2) != 0x0) {
                            this.Attack = !this.Attack;
                        }
                        if ((this.regAY[13] & 0x1) != 0x0) {
                            this.amplitudeEnv = (this.Attack ? 15 : 0);
                            this.Continue = true;
                        }
                        else {
                            this.amplitudeEnv = (this.Attack ? 0 : 15);
                        }
                    }
                }
                if (this.envA) {
                    this.amplitudeA = this.volumeLevel[this.amplitudeEnv];
                }
                if (this.envB) {
                    this.amplitudeB = this.volumeLevel[this.amplitudeEnv];
                }
                if (this.envC) {
                    this.amplitudeC = this.volumeLevel[this.amplitudeEnv];
                }
            }
            final boolean outA = (this.toneA || this.disableToneA) && (this.toneN || this.disableNoiseA);
            final boolean outB = (this.toneB || this.disableToneB) && (this.toneN || this.disableNoiseB);
            final boolean outC = (this.toneC || this.disableToneC) && (this.toneN || this.disableNoiseC);
            this.volumeA += (outA ? this.amplitudeA : 0);
            this.volumeB += (outB ? this.amplitudeB : 0);
            this.volumeC += (outC ? this.amplitudeC : 0);
            ++this.nSteps;
            this.stepCounter += 16.0;
            if (this.stepCounter >= this.step) {
                this.stepCounter -= this.step;
                this.volumeA /= this.nSteps;
                this.volumeB /= this.nSteps;
                this.volumeC /= this.nSteps;
                this.bufA[this.pbuf] = this.lastA + this.volumeA >>> 1;
                this.bufB[this.pbuf] = this.lastB + this.volumeB >>> 1;
                this.bufC[this.pbuf] = this.lastC + this.volumeC >>> 1;
                ++this.pbuf;
                this.lastA = this.volumeA;
                this.lastB = this.volumeB;
                this.lastC = this.volumeC;
                final int n = 0;
                this.nSteps = n;
                this.volumeC = n;
                this.volumeB = n;
                this.volumeA = n;
            }
        }
    }
    
    public void endFrame() {
        if (this.pbuf == 0) {
            return;
        }
        this.pbuf = 0;
        this.audiotstates -= this.spectrumModel.tstatesFrame;
    }
    
    public void reset() {
        final int n = 1;
        this.periodN = n;
        this.periodC = n;
        this.periodB = n;
        this.periodA = n;
        final int n2 = 0;
        this.counterN = n2;
        this.counterC = n2;
        this.counterB = n2;
        this.counterA = n2;
        final int n3 = 0;
        this.amplitudeEnv = n3;
        this.amplitudeC = n3;
        this.amplitudeB = n3;
        this.amplitudeA = n3;
        final int n4 = 0;
        this.nSteps = n4;
        this.volumeC = n4;
        this.volumeB = n4;
        this.volumeA = n4;
        this.envelopePeriod = 0;
        this.addressLatch = 0;
        final boolean b = false;
        this.toneN = b;
        this.toneC = b;
        this.toneB = b;
        this.toneA = b;
        this.rng = 1;
        Arrays.fill(this.regAY, 0);
        this.regAY[7] = 255;
        this.Continue = false;
        this.Attack = true;
    }
    
    public void startFrame() {
        final int n = 0;
        this.pbuf = n;
        this.audiotstates = n;
        this.stepCounter = 0.0;
        this.nSteps = 0;
        if (this.bufA != null) {
            Arrays.fill(this.bufA, 0);
        }
        if (this.bufB != null) {
            Arrays.fill(this.bufB, 0);
        }
        if (this.bufC != null) {
            Arrays.fill(this.bufC, 0);
        }
    }
    
    public void setBufferChannels(final int[] bChanA, final int[] bChanB, final int[] bChanC) {
        this.bufA = bChanA;
        this.bufB = bChanB;
        this.bufC = bChanC;
    }
    
    public int getSampleCount() {
        return this.pbuf;
    }
    
    static {
        volumeRate = new double[] { 0.0, 0.0137, 0.0205, 0.0291, 0.0423, 0.0618, 0.0847, 0.1369, 0.1691, 0.2647, 0.3527, 0.4499, 0.5704, 0.6873, 0.8482, 1.0 };
    }
}
