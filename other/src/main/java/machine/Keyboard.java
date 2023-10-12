// 
// Decompiled by Procyon v0.5.36
// 

package machine;

import java.util.Arrays;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener
{
    private int[] rowKey;
    private boolean shiftPressed;
    private KeyEvent[] keyEventPending;
    private int kempston;
    private int fuller;
    private Spectrum.Joystick joystick;
    private static final int KEY_PRESSED_BIT0 = 254;
    private static final int KEY_PRESSED_BIT1 = 253;
    private static final int KEY_PRESSED_BIT2 = 251;
    private static final int KEY_PRESSED_BIT3 = 247;
    private static final int KEY_PRESSED_BIT4 = 239;
    private static final int KEY_RELEASED_BIT0 = 1;
    private static final int KEY_RELEASED_BIT1 = 2;
    private static final int KEY_RELEASED_BIT2 = 4;
    private static final int KEY_RELEASED_BIT3 = 8;
    private static final int KEY_RELEASED_BIT4 = 16;
    
    public Keyboard() {
        this.rowKey = new int[8];
        this.keyEventPending = new KeyEvent[8];
        this.reset();
    }
    
    public final void reset() {
        Arrays.fill(this.rowKey, 255);
        this.shiftPressed = false;
        this.kempston = 0;
        this.fuller = 255;
        Arrays.fill(this.keyEventPending, null);
    }
    
    public void setJoystick(final Spectrum.Joystick model) {
        this.joystick = model;
        this.kempston = 0;
        this.fuller = 255;
    }
    
    public int readKempstonPort() {
        return this.kempston;
    }
    
    public int readFullerPort() {
        return this.fuller;
    }
    
    public int readKeyboardPort(final int port) {
        int keys = 255;
        int res = port >>> 8;
        switch (res) {
            case 127: {
                return this.rowKey[7];
            }
            case 191: {
                return this.rowKey[6];
            }
            case 223: {
                return this.rowKey[5];
            }
            case 239: {
                return this.rowKey[4];
            }
            case 247: {
                return this.rowKey[3];
            }
            case 251: {
                return this.rowKey[2];
            }
            case 253: {
                return this.rowKey[1];
            }
            case 254: {
                return this.rowKey[0];
            }
            default: {
                res = (~res & 0xFF);
                for (int row = 0, mask = 1; row < 8; ++row, mask <<= 1) {
                    if ((res & mask) != 0x0) {
                        keys &= this.rowKey[row];
                    }
                }
                return keys;
            }
        }
    }
    
    @Override
    public void keyPressed(final KeyEvent evt) {
        final char keychar = evt.getKeyChar();
        if (keychar != '\uffff' && !evt.isAltDown() && this.pressedKeyChar(keychar)) {
            for (int key = 0; key < this.keyEventPending.length; ++key) {
                if (this.keyEventPending[key] == null) {
                    this.keyEventPending[key] = evt;
                    break;
                }
            }
            return;
        }
        int key = evt.getKeyCode();
        Label_2078: {
            switch (key) {
                case 32: {
                    final int[] rowKey = this.rowKey;
                    final int n = 7;
                    rowKey[n] &= 0xFE;
                    break;
                }
                case 18: {
                    final int[] rowKey2 = this.rowKey;
                    final int n2 = 7;
                    rowKey2[n2] &= 0xFD;
                    break;
                }
                case 77: {
                    final int[] rowKey3 = this.rowKey;
                    final int n3 = 7;
                    rowKey3[n3] &= 0xFB;
                    break;
                }
                case 78: {
                    final int[] rowKey4 = this.rowKey;
                    final int n4 = 7;
                    rowKey4[n4] &= 0xF7;
                    break;
                }
                case 66: {
                    final int[] rowKey5 = this.rowKey;
                    final int n5 = 7;
                    rowKey5[n5] &= 0xEF;
                    break;
                }
                case 10: {
                    final int[] rowKey6 = this.rowKey;
                    final int n6 = 6;
                    rowKey6[n6] &= 0xFE;
                    break;
                }
                case 76: {
                    final int[] rowKey7 = this.rowKey;
                    final int n7 = 6;
                    rowKey7[n7] &= 0xFD;
                    break;
                }
                case 75: {
                    final int[] rowKey8 = this.rowKey;
                    final int n8 = 6;
                    rowKey8[n8] &= 0xFB;
                    break;
                }
                case 74: {
                    final int[] rowKey9 = this.rowKey;
                    final int n9 = 6;
                    rowKey9[n9] &= 0xF7;
                    break;
                }
                case 72: {
                    final int[] rowKey10 = this.rowKey;
                    final int n10 = 6;
                    rowKey10[n10] &= 0xEF;
                    break;
                }
                case 80: {
                    final int[] rowKey11 = this.rowKey;
                    final int n11 = 5;
                    rowKey11[n11] &= 0xFE;
                    break;
                }
                case 79: {
                    final int[] rowKey12 = this.rowKey;
                    final int n12 = 5;
                    rowKey12[n12] &= 0xFD;
                    break;
                }
                case 73: {
                    final int[] rowKey13 = this.rowKey;
                    final int n13 = 5;
                    rowKey13[n13] &= 0xFB;
                    break;
                }
                case 85: {
                    final int[] rowKey14 = this.rowKey;
                    final int n14 = 5;
                    rowKey14[n14] &= 0xF7;
                    break;
                }
                case 89: {
                    final int[] rowKey15 = this.rowKey;
                    final int n15 = 5;
                    rowKey15[n15] &= 0xEF;
                    break;
                }
                case 48: {
                    final int[] rowKey16 = this.rowKey;
                    final int n16 = 4;
                    rowKey16[n16] &= 0xFE;
                    break;
                }
                case 57: {
                    final int[] rowKey17 = this.rowKey;
                    final int n17 = 4;
                    rowKey17[n17] &= 0xFD;
                    break;
                }
                case 56: {
                    final int[] rowKey18 = this.rowKey;
                    final int n18 = 4;
                    rowKey18[n18] &= 0xFB;
                    break;
                }
                case 55: {
                    final int[] rowKey19 = this.rowKey;
                    final int n19 = 4;
                    rowKey19[n19] &= 0xF7;
                    break;
                }
                case 54: {
                    final int[] rowKey20 = this.rowKey;
                    final int n20 = 4;
                    rowKey20[n20] &= 0xEF;
                    break;
                }
                case 49: {
                    final int[] rowKey21 = this.rowKey;
                    final int n21 = 3;
                    rowKey21[n21] &= 0xFE;
                    break;
                }
                case 50: {
                    final int[] rowKey22 = this.rowKey;
                    final int n22 = 3;
                    rowKey22[n22] &= 0xFD;
                    break;
                }
                case 51: {
                    final int[] rowKey23 = this.rowKey;
                    final int n23 = 3;
                    rowKey23[n23] &= 0xFB;
                    break;
                }
                case 52: {
                    final int[] rowKey24 = this.rowKey;
                    final int n24 = 3;
                    rowKey24[n24] &= 0xF7;
                    break;
                }
                case 53: {
                    final int[] rowKey25 = this.rowKey;
                    final int n25 = 3;
                    rowKey25[n25] &= 0xEF;
                    break;
                }
                case 81: {
                    final int[] rowKey26 = this.rowKey;
                    final int n26 = 2;
                    rowKey26[n26] &= 0xFE;
                    break;
                }
                case 87: {
                    final int[] rowKey27 = this.rowKey;
                    final int n27 = 2;
                    rowKey27[n27] &= 0xFD;
                    break;
                }
                case 69: {
                    final int[] rowKey28 = this.rowKey;
                    final int n28 = 2;
                    rowKey28[n28] &= 0xFB;
                    break;
                }
                case 82: {
                    final int[] rowKey29 = this.rowKey;
                    final int n29 = 2;
                    rowKey29[n29] &= 0xF7;
                    break;
                }
                case 84: {
                    final int[] rowKey30 = this.rowKey;
                    final int n30 = 2;
                    rowKey30[n30] &= 0xEF;
                    break;
                }
                case 65: {
                    final int[] rowKey31 = this.rowKey;
                    final int n31 = 1;
                    rowKey31[n31] &= 0xFE;
                    break;
                }
                case 83: {
                    final int[] rowKey32 = this.rowKey;
                    final int n32 = 1;
                    rowKey32[n32] &= 0xFD;
                    break;
                }
                case 68: {
                    final int[] rowKey33 = this.rowKey;
                    final int n33 = 1;
                    rowKey33[n33] &= 0xFB;
                    break;
                }
                case 70: {
                    final int[] rowKey34 = this.rowKey;
                    final int n34 = 1;
                    rowKey34[n34] &= 0xF7;
                    break;
                }
                case 71: {
                    final int[] rowKey35 = this.rowKey;
                    final int n35 = 1;
                    rowKey35[n35] &= 0xEF;
                    break;
                }
                case 16: {
                    final int[] rowKey36 = this.rowKey;
                    final int n36 = 0;
                    rowKey36[n36] &= 0xFE;
                    this.shiftPressed = true;
                    break;
                }
                case 90: {
                    final int[] rowKey37 = this.rowKey;
                    final int n37 = 0;
                    rowKey37[n37] &= 0xFD;
                    break;
                }
                case 88: {
                    final int[] rowKey38 = this.rowKey;
                    final int n38 = 0;
                    rowKey38[n38] &= 0xFB;
                    break;
                }
                case 67: {
                    final int[] rowKey39 = this.rowKey;
                    final int n39 = 0;
                    rowKey39[n39] &= 0xF7;
                    break;
                }
                case 86: {
                    final int[] rowKey40 = this.rowKey;
                    final int n40 = 0;
                    rowKey40[n40] &= 0xEF;
                    break;
                }
                case 8: {
                    final int[] rowKey41 = this.rowKey;
                    final int n41 = 0;
                    rowKey41[n41] &= 0xFE;
                    final int[] rowKey42 = this.rowKey;
                    final int n42 = 4;
                    rowKey42[n42] &= 0xFE;
                    break;
                }
                case 44: {
                    final int[] rowKey43 = this.rowKey;
                    final int n43 = 7;
                    rowKey43[n43] &= 0xF5;
                    break;
                }
                case 46: {
                    final int[] rowKey44 = this.rowKey;
                    final int n44 = 7;
                    rowKey44[n44] &= 0xF9;
                    break;
                }
                case 45: {
                    final int[] rowKey45 = this.rowKey;
                    final int n45 = 7;
                    rowKey45[n45] &= 0xFD;
                    final int[] rowKey46 = this.rowKey;
                    final int n46 = 6;
                    rowKey46[n46] &= 0xF7;
                    break;
                }
                case 521: {
                    final int[] rowKey47 = this.rowKey;
                    final int n47 = 7;
                    rowKey47[n47] &= 0xFD;
                    final int[] rowKey48 = this.rowKey;
                    final int n48 = 6;
                    rowKey48[n48] &= 0xFB;
                    break;
                }
                case 61: {
                    final int[] rowKey49 = this.rowKey;
                    final int n49 = 7;
                    rowKey49[n49] &= 0xFD;
                    final int[] rowKey50 = this.rowKey;
                    final int n50 = 6;
                    rowKey50[n50] &= 0xFD;
                    break;
                }
                case 520: {
                    final int[] rowKey51 = this.rowKey;
                    final int n51 = 7;
                    rowKey51[n51] &= 0xFD;
                    final int[] rowKey52 = this.rowKey;
                    final int n52 = 3;
                    rowKey52[n52] &= 0xFB;
                    break;
                }
                case 47: {
                    final int[] rowKey53 = this.rowKey;
                    final int n53 = 7;
                    rowKey53[n53] &= 0xFD;
                    final int[] rowKey54 = this.rowKey;
                    final int n54 = 0;
                    rowKey54[n54] &= 0xEF;
                    break;
                }
                case 59: {
                    final int[] rowKey55 = this.rowKey;
                    final int n55 = 7;
                    rowKey55[n55] &= 0xFD;
                    final int[] rowKey56 = this.rowKey;
                    final int n56 = 5;
                    rowKey56[n56] &= 0xFD;
                    break;
                }
                case 20: {
                    final int[] rowKey57 = this.rowKey;
                    final int n57 = 0;
                    rowKey57[n57] &= 0xFE;
                    final int[] rowKey58 = this.rowKey;
                    final int n58 = 3;
                    rowKey58[n58] &= 0xFD;
                    break;
                }
                case 27: {
                    final int[] rowKey59 = this.rowKey;
                    final int n59 = 0;
                    rowKey59[n59] &= 0xFE;
                    final int[] rowKey60 = this.rowKey;
                    final int n60 = 7;
                    rowKey60[n60] &= 0xFE;
                    break;
                }
                case 37: {
                    switch (this.joystick) {
                        case NONE: {
                            final int[] rowKey61 = this.rowKey;
                            final int n61 = 0;
                            rowKey61[n61] &= 0xFE;
                        }
                        case CURSOR: {
                            final int[] rowKey62 = this.rowKey;
                            final int n62 = 3;
                            rowKey62[n62] &= 0xEF;
                            break;
                        }
                        case KEMPSTON: {
                            this.kempston |= 0x2;
                            break;
                        }
                        case SINCLAIR1: {
                            final int[] rowKey63 = this.rowKey;
                            final int n63 = 4;
                            rowKey63[n63] &= 0xEF;
                            break;
                        }
                        case SINCLAIR2: {
                            final int[] rowKey64 = this.rowKey;
                            final int n64 = 3;
                            rowKey64[n64] &= 0xFE;
                            break;
                        }
                        case FULLER: {
                            this.fuller &= 0xFB;
                            break;
                        }
                    }
                    break;
                }
                case 40: {
                    switch (this.joystick) {
                        case NONE: {
                            final int[] rowKey65 = this.rowKey;
                            final int n65 = 0;
                            rowKey65[n65] &= 0xFE;
                        }
                        case CURSOR: {
                            final int[] rowKey66 = this.rowKey;
                            final int n66 = 4;
                            rowKey66[n66] &= 0xEF;
                            break;
                        }
                        case KEMPSTON: {
                            this.kempston |= 0x4;
                            break;
                        }
                        case SINCLAIR1: {
                            final int[] rowKey67 = this.rowKey;
                            final int n67 = 4;
                            rowKey67[n67] &= 0xFB;
                            break;
                        }
                        case SINCLAIR2: {
                            final int[] rowKey68 = this.rowKey;
                            final int n68 = 3;
                            rowKey68[n68] &= 0xFB;
                            break;
                        }
                        case FULLER: {
                            this.fuller &= 0xFD;
                            break;
                        }
                    }
                    break;
                }
                case 38: {
                    switch (this.joystick) {
                        case NONE: {
                            final int[] rowKey69 = this.rowKey;
                            final int n69 = 0;
                            rowKey69[n69] &= 0xFE;
                        }
                        case CURSOR: {
                            final int[] rowKey70 = this.rowKey;
                            final int n70 = 4;
                            rowKey70[n70] &= 0xF7;
                            break;
                        }
                        case KEMPSTON: {
                            this.kempston |= 0x8;
                            break;
                        }
                        case SINCLAIR1: {
                            final int[] rowKey71 = this.rowKey;
                            final int n71 = 4;
                            rowKey71[n71] &= 0xFD;
                            break;
                        }
                        case SINCLAIR2: {
                            final int[] rowKey72 = this.rowKey;
                            final int n72 = 3;
                            rowKey72[n72] &= 0xF7;
                            break;
                        }
                        case FULLER: {
                            this.fuller &= 0xFE;
                            break;
                        }
                    }
                    break;
                }
                case 39: {
                    switch (this.joystick) {
                        case NONE: {
                            final int[] rowKey73 = this.rowKey;
                            final int n73 = 0;
                            rowKey73[n73] &= 0xFE;
                        }
                        case CURSOR: {
                            final int[] rowKey74 = this.rowKey;
                            final int n74 = 4;
                            rowKey74[n74] &= 0xFB;
                            break;
                        }
                        case KEMPSTON: {
                            this.kempston |= 0x1;
                            break;
                        }
                        case SINCLAIR1: {
                            final int[] rowKey75 = this.rowKey;
                            final int n75 = 4;
                            rowKey75[n75] &= 0xF7;
                            break;
                        }
                        case SINCLAIR2: {
                            final int[] rowKey76 = this.rowKey;
                            final int n76 = 3;
                            rowKey76[n76] &= 0xFD;
                            break;
                        }
                        case FULLER: {
                            this.fuller &= 0xF7;
                            break;
                        }
                    }
                    break;
                }
                case 17: {
                    switch (this.joystick) {
                        case NONE: {
                            break Label_2078;
                        }
                        case KEMPSTON: {
                            this.kempston |= 0x10;
                            break Label_2078;
                        }
                        case CURSOR:
                        case SINCLAIR1: {
                            final int[] rowKey77 = this.rowKey;
                            final int n77 = 4;
                            rowKey77[n77] &= 0xFE;
                            break Label_2078;
                        }
                        case SINCLAIR2: {
                            final int[] rowKey78 = this.rowKey;
                            final int n78 = 3;
                            rowKey78[n78] &= 0xEF;
                            break Label_2078;
                        }
                        case FULLER: {
                            this.fuller &= 0x7F;
                            break Label_2078;
                        }
                    }
                    break;
                }
            }
        }
    }
    
    @Override
    public void keyReleased(final KeyEvent evt) {
        char keychar = evt.getKeyChar();
        if (keychar != '\uffff' && !evt.isAltDown()) {
            for (int key = 0; key < this.keyEventPending.length; ++key) {
                if (this.keyEventPending[key] != null && evt.getKeyCode() == this.keyEventPending[key].getKeyCode()) {
                    keychar = this.keyEventPending[key].getKeyChar();
                    this.keyEventPending[key] = null;
                }
            }
            if (this.releasedKeyChar(keychar)) {
                return;
            }
        }
        int key = evt.getKeyCode();
        Label_1969: {
            switch (key) {
                case 32: {
                    final int[] rowKey = this.rowKey;
                    final int n = 7;
                    rowKey[n] |= 0x1;
                    break;
                }
                case 18: {
                    final int[] rowKey2 = this.rowKey;
                    final int n2 = 7;
                    rowKey2[n2] |= 0x2;
                    break;
                }
                case 77: {
                    final int[] rowKey3 = this.rowKey;
                    final int n3 = 7;
                    rowKey3[n3] |= 0x4;
                    break;
                }
                case 78: {
                    final int[] rowKey4 = this.rowKey;
                    final int n4 = 7;
                    rowKey4[n4] |= 0x8;
                    break;
                }
                case 66: {
                    final int[] rowKey5 = this.rowKey;
                    final int n5 = 7;
                    rowKey5[n5] |= 0x10;
                    break;
                }
                case 10: {
                    final int[] rowKey6 = this.rowKey;
                    final int n6 = 6;
                    rowKey6[n6] |= 0x1;
                    break;
                }
                case 76: {
                    final int[] rowKey7 = this.rowKey;
                    final int n7 = 6;
                    rowKey7[n7] |= 0x2;
                    break;
                }
                case 75: {
                    final int[] rowKey8 = this.rowKey;
                    final int n8 = 6;
                    rowKey8[n8] |= 0x4;
                    break;
                }
                case 74: {
                    final int[] rowKey9 = this.rowKey;
                    final int n9 = 6;
                    rowKey9[n9] |= 0x8;
                    break;
                }
                case 72: {
                    final int[] rowKey10 = this.rowKey;
                    final int n10 = 6;
                    rowKey10[n10] |= 0x10;
                    break;
                }
                case 80: {
                    final int[] rowKey11 = this.rowKey;
                    final int n11 = 5;
                    rowKey11[n11] |= 0x1;
                    break;
                }
                case 79: {
                    final int[] rowKey12 = this.rowKey;
                    final int n12 = 5;
                    rowKey12[n12] |= 0x2;
                    break;
                }
                case 73: {
                    final int[] rowKey13 = this.rowKey;
                    final int n13 = 5;
                    rowKey13[n13] |= 0x4;
                    break;
                }
                case 85: {
                    final int[] rowKey14 = this.rowKey;
                    final int n14 = 5;
                    rowKey14[n14] |= 0x8;
                    break;
                }
                case 89: {
                    final int[] rowKey15 = this.rowKey;
                    final int n15 = 5;
                    rowKey15[n15] |= 0x10;
                    break;
                }
                case 48: {
                    final int[] rowKey16 = this.rowKey;
                    final int n16 = 4;
                    rowKey16[n16] |= 0x1;
                    break;
                }
                case 57: {
                    final int[] rowKey17 = this.rowKey;
                    final int n17 = 4;
                    rowKey17[n17] |= 0x2;
                    break;
                }
                case 56: {
                    final int[] rowKey18 = this.rowKey;
                    final int n18 = 4;
                    rowKey18[n18] |= 0x4;
                    break;
                }
                case 55: {
                    final int[] rowKey19 = this.rowKey;
                    final int n19 = 4;
                    rowKey19[n19] |= 0x8;
                    break;
                }
                case 54: {
                    final int[] rowKey20 = this.rowKey;
                    final int n20 = 4;
                    rowKey20[n20] |= 0x10;
                    break;
                }
                case 49: {
                    final int[] rowKey21 = this.rowKey;
                    final int n21 = 3;
                    rowKey21[n21] |= 0x1;
                    break;
                }
                case 50: {
                    final int[] rowKey22 = this.rowKey;
                    final int n22 = 3;
                    rowKey22[n22] |= 0x2;
                    break;
                }
                case 51: {
                    final int[] rowKey23 = this.rowKey;
                    final int n23 = 3;
                    rowKey23[n23] |= 0x4;
                    break;
                }
                case 52: {
                    final int[] rowKey24 = this.rowKey;
                    final int n24 = 3;
                    rowKey24[n24] |= 0x8;
                    break;
                }
                case 53: {
                    final int[] rowKey25 = this.rowKey;
                    final int n25 = 3;
                    rowKey25[n25] |= 0x10;
                    break;
                }
                case 81: {
                    final int[] rowKey26 = this.rowKey;
                    final int n26 = 2;
                    rowKey26[n26] |= 0x1;
                    break;
                }
                case 87: {
                    final int[] rowKey27 = this.rowKey;
                    final int n27 = 2;
                    rowKey27[n27] |= 0x2;
                    break;
                }
                case 69: {
                    final int[] rowKey28 = this.rowKey;
                    final int n28 = 2;
                    rowKey28[n28] |= 0x4;
                    break;
                }
                case 82: {
                    final int[] rowKey29 = this.rowKey;
                    final int n29 = 2;
                    rowKey29[n29] |= 0x8;
                    break;
                }
                case 84: {
                    final int[] rowKey30 = this.rowKey;
                    final int n30 = 2;
                    rowKey30[n30] |= 0x10;
                    break;
                }
                case 65: {
                    final int[] rowKey31 = this.rowKey;
                    final int n31 = 1;
                    rowKey31[n31] |= 0x1;
                    break;
                }
                case 83: {
                    final int[] rowKey32 = this.rowKey;
                    final int n32 = 1;
                    rowKey32[n32] |= 0x2;
                    break;
                }
                case 68: {
                    final int[] rowKey33 = this.rowKey;
                    final int n33 = 1;
                    rowKey33[n33] |= 0x4;
                    break;
                }
                case 70: {
                    final int[] rowKey34 = this.rowKey;
                    final int n34 = 1;
                    rowKey34[n34] |= 0x8;
                    break;
                }
                case 71: {
                    final int[] rowKey35 = this.rowKey;
                    final int n35 = 1;
                    rowKey35[n35] |= 0x10;
                    break;
                }
                case 16: {
                    final int[] rowKey36 = this.rowKey;
                    final int n36 = 0;
                    rowKey36[n36] |= 0x1;
                    this.shiftPressed = false;
                    break;
                }
                case 90: {
                    final int[] rowKey37 = this.rowKey;
                    final int n37 = 0;
                    rowKey37[n37] |= 0x2;
                    break;
                }
                case 88: {
                    final int[] rowKey38 = this.rowKey;
                    final int n38 = 0;
                    rowKey38[n38] |= 0x4;
                    break;
                }
                case 67: {
                    final int[] rowKey39 = this.rowKey;
                    final int n39 = 0;
                    rowKey39[n39] |= 0x8;
                    break;
                }
                case 86: {
                    final int[] rowKey40 = this.rowKey;
                    final int n40 = 0;
                    rowKey40[n40] |= 0x10;
                    break;
                }
                case 8: {
                    final int[] rowKey41 = this.rowKey;
                    final int n41 = 0;
                    rowKey41[n41] |= 0x1;
                    final int[] rowKey42 = this.rowKey;
                    final int n42 = 4;
                    rowKey42[n42] |= 0x1;
                    break;
                }
                case 44: {
                    final int[] rowKey43 = this.rowKey;
                    final int n43 = 7;
                    rowKey43[n43] |= 0xA;
                    break;
                }
                case 46: {
                    final int[] rowKey44 = this.rowKey;
                    final int n44 = 7;
                    rowKey44[n44] |= 0x6;
                    break;
                }
                case 45: {
                    final int[] rowKey45 = this.rowKey;
                    final int n45 = 7;
                    rowKey45[n45] |= 0x2;
                    final int[] rowKey46 = this.rowKey;
                    final int n46 = 6;
                    rowKey46[n46] |= 0x8;
                    break;
                }
                case 521: {
                    final int[] rowKey47 = this.rowKey;
                    final int n47 = 7;
                    rowKey47[n47] |= 0x2;
                    final int[] rowKey48 = this.rowKey;
                    final int n48 = 6;
                    rowKey48[n48] |= 0x4;
                    break;
                }
                case 61: {
                    final int[] rowKey49 = this.rowKey;
                    final int n49 = 7;
                    rowKey49[n49] |= 0x2;
                    final int[] rowKey50 = this.rowKey;
                    final int n50 = 6;
                    rowKey50[n50] |= 0x2;
                    break;
                }
                case 520: {
                    final int[] rowKey51 = this.rowKey;
                    final int n51 = 7;
                    rowKey51[n51] |= 0x2;
                    final int[] rowKey52 = this.rowKey;
                    final int n52 = 3;
                    rowKey52[n52] |= 0x4;
                    break;
                }
                case 47: {
                    final int[] rowKey53 = this.rowKey;
                    final int n53 = 7;
                    rowKey53[n53] |= 0x2;
                    final int[] rowKey54 = this.rowKey;
                    final int n54 = 0;
                    rowKey54[n54] |= 0x10;
                    break;
                }
                case 59: {
                    final int[] rowKey55 = this.rowKey;
                    final int n55 = 7;
                    rowKey55[n55] |= 0x2;
                    final int[] rowKey56 = this.rowKey;
                    final int n56 = 5;
                    rowKey56[n56] |= 0x2;
                    break;
                }
                case 20: {
                    final int[] rowKey57 = this.rowKey;
                    final int n57 = 0;
                    rowKey57[n57] |= 0x1;
                    final int[] rowKey58 = this.rowKey;
                    final int n58 = 3;
                    rowKey58[n58] |= 0x2;
                    break;
                }
                case 27: {
                    final int[] rowKey59 = this.rowKey;
                    final int n59 = 0;
                    rowKey59[n59] |= 0x1;
                    final int[] rowKey60 = this.rowKey;
                    final int n60 = 7;
                    rowKey60[n60] |= 0x1;
                    break;
                }
                case 37: {
                    switch (this.joystick) {
                        case NONE: {
                            final int[] rowKey61 = this.rowKey;
                            final int n61 = 0;
                            rowKey61[n61] |= 0x1;
                        }
                        case CURSOR: {
                            final int[] rowKey62 = this.rowKey;
                            final int n62 = 3;
                            rowKey62[n62] |= 0x10;
                            break;
                        }
                        case KEMPSTON: {
                            this.kempston &= 0xFD;
                            break;
                        }
                        case SINCLAIR1: {
                            final int[] rowKey63 = this.rowKey;
                            final int n63 = 4;
                            rowKey63[n63] |= 0x10;
                            break;
                        }
                        case SINCLAIR2: {
                            final int[] rowKey64 = this.rowKey;
                            final int n64 = 3;
                            rowKey64[n64] |= 0x1;
                            break;
                        }
                        case FULLER: {
                            this.fuller |= 0x4;
                            break;
                        }
                    }
                    break;
                }
                case 40: {
                    switch (this.joystick) {
                        case NONE: {
                            final int[] rowKey65 = this.rowKey;
                            final int n65 = 0;
                            rowKey65[n65] |= 0x1;
                        }
                        case CURSOR: {
                            final int[] rowKey66 = this.rowKey;
                            final int n66 = 4;
                            rowKey66[n66] |= 0x10;
                            break;
                        }
                        case KEMPSTON: {
                            this.kempston &= 0xFB;
                            break;
                        }
                        case SINCLAIR1: {
                            final int[] rowKey67 = this.rowKey;
                            final int n67 = 4;
                            rowKey67[n67] |= 0x4;
                            break;
                        }
                        case SINCLAIR2: {
                            final int[] rowKey68 = this.rowKey;
                            final int n68 = 3;
                            rowKey68[n68] |= 0x4;
                            break;
                        }
                        case FULLER: {
                            this.fuller |= 0x2;
                            break;
                        }
                    }
                    break;
                }
                case 38: {
                    switch (this.joystick) {
                        case NONE: {
                            final int[] rowKey69 = this.rowKey;
                            final int n69 = 0;
                            rowKey69[n69] |= 0x1;
                        }
                        case CURSOR: {
                            final int[] rowKey70 = this.rowKey;
                            final int n70 = 4;
                            rowKey70[n70] |= 0x8;
                            break;
                        }
                        case KEMPSTON: {
                            this.kempston &= 0xF7;
                            break;
                        }
                        case SINCLAIR1: {
                            final int[] rowKey71 = this.rowKey;
                            final int n71 = 4;
                            rowKey71[n71] |= 0x2;
                            break;
                        }
                        case SINCLAIR2: {
                            final int[] rowKey72 = this.rowKey;
                            final int n72 = 3;
                            rowKey72[n72] |= 0x8;
                            break;
                        }
                        case FULLER: {
                            this.fuller |= 0x1;
                            break;
                        }
                    }
                    break;
                }
                case 39: {
                    switch (this.joystick) {
                        case NONE: {
                            final int[] rowKey73 = this.rowKey;
                            final int n73 = 0;
                            rowKey73[n73] |= 0x1;
                        }
                        case CURSOR: {
                            final int[] rowKey74 = this.rowKey;
                            final int n74 = 4;
                            rowKey74[n74] |= 0x4;
                            break;
                        }
                        case KEMPSTON: {
                            this.kempston &= 0xFE;
                            break;
                        }
                        case SINCLAIR1: {
                            final int[] rowKey75 = this.rowKey;
                            final int n75 = 4;
                            rowKey75[n75] |= 0x8;
                            break;
                        }
                        case SINCLAIR2: {
                            final int[] rowKey76 = this.rowKey;
                            final int n76 = 3;
                            rowKey76[n76] |= 0x2;
                            break;
                        }
                        case FULLER: {
                            this.fuller |= 0x8;
                            break;
                        }
                    }
                    break;
                }
                case 17: {
                    switch (this.joystick) {
                        case NONE: {
                            break Label_1969;
                        }
                        case KEMPSTON: {
                            this.kempston &= 0xEF;
                            break Label_1969;
                        }
                        case CURSOR:
                        case SINCLAIR1: {
                            final int[] rowKey77 = this.rowKey;
                            final int n77 = 4;
                            rowKey77[n77] |= 0x1;
                            break Label_1969;
                        }
                        case SINCLAIR2: {
                            final int[] rowKey78 = this.rowKey;
                            final int n78 = 3;
                            rowKey78[n78] |= 0x10;
                            break Label_1969;
                        }
                        case FULLER: {
                            this.fuller |= 0x80;
                            break Label_1969;
                        }
                    }
                    break;
                }
            }
        }
    }
    
    @Override
    public void keyTyped(final KeyEvent evt) {
    }
    
    private boolean pressedKeyChar(final char keyChar) {
        boolean done = true;
        if (this.shiftPressed) {
            final int[] rowKey = this.rowKey;
            final int n = 0;
            rowKey[n] |= 0x1;
        }
        switch (keyChar) {
            case '!': {
                final int[] rowKey2 = this.rowKey;
                final int n2 = 7;
                rowKey2[n2] &= 0xFD;
                final int[] rowKey3 = this.rowKey;
                final int n3 = 3;
                rowKey3[n3] &= 0xFE;
                break;
            }
            case '\"': {
                final int[] rowKey4 = this.rowKey;
                final int n4 = 7;
                rowKey4[n4] &= 0xFD;
                final int[] rowKey5 = this.rowKey;
                final int n5 = 5;
                rowKey5[n5] &= 0xFE;
                break;
            }
            case '#': {
                final int[] rowKey6 = this.rowKey;
                final int n6 = 7;
                rowKey6[n6] &= 0xFD;
                final int[] rowKey7 = this.rowKey;
                final int n7 = 3;
                rowKey7[n7] &= 0xFB;
                break;
            }
            case '$': {
                final int[] rowKey8 = this.rowKey;
                final int n8 = 7;
                rowKey8[n8] &= 0xFD;
                final int[] rowKey9 = this.rowKey;
                final int n9 = 3;
                rowKey9[n9] &= 0xF7;
                break;
            }
            case '%': {
                final int[] rowKey10 = this.rowKey;
                final int n10 = 7;
                rowKey10[n10] &= 0xFD;
                final int[] rowKey11 = this.rowKey;
                final int n11 = 3;
                rowKey11[n11] &= 0xEF;
                break;
            }
            case '&': {
                final int[] rowKey12 = this.rowKey;
                final int n12 = 7;
                rowKey12[n12] &= 0xFD;
                final int[] rowKey13 = this.rowKey;
                final int n13 = 4;
                rowKey13[n13] &= 0xEF;
                break;
            }
            case '\'': {
                final int[] rowKey14 = this.rowKey;
                final int n14 = 7;
                rowKey14[n14] &= 0xFD;
                final int[] rowKey15 = this.rowKey;
                final int n15 = 4;
                rowKey15[n15] &= 0xF7;
                break;
            }
            case '(': {
                final int[] rowKey16 = this.rowKey;
                final int n16 = 7;
                rowKey16[n16] &= 0xFD;
                final int[] rowKey17 = this.rowKey;
                final int n17 = 4;
                rowKey17[n17] &= 0xFB;
                break;
            }
            case ')': {
                final int[] rowKey18 = this.rowKey;
                final int n18 = 7;
                rowKey18[n18] &= 0xFD;
                final int[] rowKey19 = this.rowKey;
                final int n19 = 4;
                rowKey19[n19] &= 0xFD;
                break;
            }
            case '*': {
                final int[] rowKey20 = this.rowKey;
                final int n20 = 7;
                rowKey20[n20] &= 0xED;
                break;
            }
            case '+': {
                final int[] rowKey21 = this.rowKey;
                final int n21 = 7;
                rowKey21[n21] &= 0xFD;
                final int[] rowKey22 = this.rowKey;
                final int n22 = 6;
                rowKey22[n22] &= 0xFB;
                break;
            }
            case ',': {
                final int[] rowKey23 = this.rowKey;
                final int n23 = 7;
                rowKey23[n23] &= 0xF5;
                break;
            }
            case '-': {
                final int[] rowKey24 = this.rowKey;
                final int n24 = 7;
                rowKey24[n24] &= 0xFD;
                final int[] rowKey25 = this.rowKey;
                final int n25 = 6;
                rowKey25[n25] &= 0xF7;
                break;
            }
            case '.': {
                final int[] rowKey26 = this.rowKey;
                final int n26 = 7;
                rowKey26[n26] &= 0xF9;
                break;
            }
            case '/': {
                final int[] rowKey27 = this.rowKey;
                final int n27 = 7;
                rowKey27[n27] &= 0xFD;
                final int[] rowKey28 = this.rowKey;
                final int n28 = 0;
                rowKey28[n28] &= 0xEF;
                break;
            }
            case '0': {
                final int[] rowKey29 = this.rowKey;
                final int n29 = 4;
                rowKey29[n29] &= 0xFE;
                break;
            }
            case '1': {
                final int[] rowKey30 = this.rowKey;
                final int n30 = 3;
                rowKey30[n30] &= 0xFE;
                break;
            }
            case '2': {
                final int[] rowKey31 = this.rowKey;
                final int n31 = 3;
                rowKey31[n31] &= 0xFD;
                break;
            }
            case '3': {
                final int[] rowKey32 = this.rowKey;
                final int n32 = 3;
                rowKey32[n32] &= 0xFB;
                break;
            }
            case '4': {
                final int[] rowKey33 = this.rowKey;
                final int n33 = 3;
                rowKey33[n33] &= 0xF7;
                break;
            }
            case '5': {
                final int[] rowKey34 = this.rowKey;
                final int n34 = 3;
                rowKey34[n34] &= 0xEF;
                break;
            }
            case '6': {
                final int[] rowKey35 = this.rowKey;
                final int n35 = 4;
                rowKey35[n35] &= 0xEF;
                break;
            }
            case '7': {
                final int[] rowKey36 = this.rowKey;
                final int n36 = 4;
                rowKey36[n36] &= 0xF7;
                break;
            }
            case '8': {
                final int[] rowKey37 = this.rowKey;
                final int n37 = 4;
                rowKey37[n37] &= 0xFB;
                break;
            }
            case '9': {
                final int[] rowKey38 = this.rowKey;
                final int n38 = 4;
                rowKey38[n38] &= 0xFD;
                break;
            }
            case ':': {
                final int[] rowKey39 = this.rowKey;
                final int n39 = 7;
                rowKey39[n39] &= 0xFD;
                final int[] rowKey40 = this.rowKey;
                final int n40 = 0;
                rowKey40[n40] &= 0xFD;
                break;
            }
            case ';': {
                final int[] rowKey41 = this.rowKey;
                final int n41 = 7;
                rowKey41[n41] &= 0xFD;
                final int[] rowKey42 = this.rowKey;
                final int n42 = 5;
                rowKey42[n42] &= 0xFD;
                break;
            }
            case '<': {
                final int[] rowKey43 = this.rowKey;
                final int n43 = 7;
                rowKey43[n43] &= 0xFD;
                final int[] rowKey44 = this.rowKey;
                final int n44 = 2;
                rowKey44[n44] &= 0xF7;
                break;
            }
            case '=': {
                final int[] rowKey45 = this.rowKey;
                final int n45 = 7;
                rowKey45[n45] &= 0xFD;
                final int[] rowKey46 = this.rowKey;
                final int n46 = 6;
                rowKey46[n46] &= 0xFD;
                break;
            }
            case '>': {
                final int[] rowKey47 = this.rowKey;
                final int n47 = 7;
                rowKey47[n47] &= 0xFD;
                final int[] rowKey48 = this.rowKey;
                final int n48 = 2;
                rowKey48[n48] &= 0xEF;
                break;
            }
            case '?': {
                final int[] rowKey49 = this.rowKey;
                final int n49 = 7;
                rowKey49[n49] &= 0xFD;
                final int[] rowKey50 = this.rowKey;
                final int n50 = 0;
                rowKey50[n50] &= 0xF7;
                break;
            }
            case '@': {
                final int[] rowKey51 = this.rowKey;
                final int n51 = 7;
                rowKey51[n51] &= 0xFD;
                final int[] rowKey52 = this.rowKey;
                final int n52 = 3;
                rowKey52[n52] &= 0xFD;
                break;
            }
            case 'A': {
                final int[] rowKey53 = this.rowKey;
                final int n53 = 0;
                rowKey53[n53] &= 0xFE;
                final int[] rowKey54 = this.rowKey;
                final int n54 = 1;
                rowKey54[n54] &= 0xFE;
                break;
            }
            case 'B': {
                final int[] rowKey55 = this.rowKey;
                final int n55 = 0;
                rowKey55[n55] &= 0xFE;
                final int[] rowKey56 = this.rowKey;
                final int n56 = 7;
                rowKey56[n56] &= 0xEF;
                break;
            }
            case 'C': {
                final int[] rowKey57 = this.rowKey;
                final int n57 = 0;
                rowKey57[n57] &= 0xF6;
                break;
            }
            case 'D': {
                final int[] rowKey58 = this.rowKey;
                final int n58 = 0;
                rowKey58[n58] &= 0xFE;
                final int[] rowKey59 = this.rowKey;
                final int n59 = 1;
                rowKey59[n59] &= 0xFB;
                break;
            }
            case 'E': {
                final int[] rowKey60 = this.rowKey;
                final int n60 = 0;
                rowKey60[n60] &= 0xFE;
                final int[] rowKey61 = this.rowKey;
                final int n61 = 2;
                rowKey61[n61] &= 0xFB;
                break;
            }
            case 'F': {
                final int[] rowKey62 = this.rowKey;
                final int n62 = 0;
                rowKey62[n62] &= 0xFE;
                final int[] rowKey63 = this.rowKey;
                final int n63 = 1;
                rowKey63[n63] &= 0xF7;
                break;
            }
            case 'G': {
                final int[] rowKey64 = this.rowKey;
                final int n64 = 0;
                rowKey64[n64] &= 0xFE;
                final int[] rowKey65 = this.rowKey;
                final int n65 = 1;
                rowKey65[n65] &= 0xEF;
                break;
            }
            case 'H': {
                final int[] rowKey66 = this.rowKey;
                final int n66 = 0;
                rowKey66[n66] &= 0xFE;
                final int[] rowKey67 = this.rowKey;
                final int n67 = 6;
                rowKey67[n67] &= 0xEF;
                break;
            }
            case 'I': {
                final int[] rowKey68 = this.rowKey;
                final int n68 = 0;
                rowKey68[n68] &= 0xFE;
                final int[] rowKey69 = this.rowKey;
                final int n69 = 5;
                rowKey69[n69] &= 0xFB;
                break;
            }
            case 'J': {
                final int[] rowKey70 = this.rowKey;
                final int n70 = 0;
                rowKey70[n70] &= 0xFE;
                final int[] rowKey71 = this.rowKey;
                final int n71 = 6;
                rowKey71[n71] &= 0xF7;
                break;
            }
            case 'K': {
                final int[] rowKey72 = this.rowKey;
                final int n72 = 0;
                rowKey72[n72] &= 0xFE;
                final int[] rowKey73 = this.rowKey;
                final int n73 = 6;
                rowKey73[n73] &= 0xFB;
                break;
            }
            case 'L': {
                final int[] rowKey74 = this.rowKey;
                final int n74 = 0;
                rowKey74[n74] &= 0xFE;
                final int[] rowKey75 = this.rowKey;
                final int n75 = 6;
                rowKey75[n75] &= 0xFD;
                break;
            }
            case 'M': {
                final int[] rowKey76 = this.rowKey;
                final int n76 = 0;
                rowKey76[n76] &= 0xFE;
                final int[] rowKey77 = this.rowKey;
                final int n77 = 7;
                rowKey77[n77] &= 0xFB;
                break;
            }
            case 'N': {
                final int[] rowKey78 = this.rowKey;
                final int n78 = 0;
                rowKey78[n78] &= 0xFE;
                final int[] rowKey79 = this.rowKey;
                final int n79 = 7;
                rowKey79[n79] &= 0xF7;
                break;
            }
            case 'O': {
                final int[] rowKey80 = this.rowKey;
                final int n80 = 0;
                rowKey80[n80] &= 0xFE;
                final int[] rowKey81 = this.rowKey;
                final int n81 = 5;
                rowKey81[n81] &= 0xFD;
                break;
            }
            case 'P': {
                final int[] rowKey82 = this.rowKey;
                final int n82 = 0;
                rowKey82[n82] &= 0xFE;
                final int[] rowKey83 = this.rowKey;
                final int n83 = 5;
                rowKey83[n83] &= 0xFE;
                break;
            }
            case 'Q': {
                final int[] rowKey84 = this.rowKey;
                final int n84 = 0;
                rowKey84[n84] &= 0xFE;
                final int[] rowKey85 = this.rowKey;
                final int n85 = 2;
                rowKey85[n85] &= 0xFE;
                break;
            }
            case 'R': {
                final int[] rowKey86 = this.rowKey;
                final int n86 = 0;
                rowKey86[n86] &= 0xFE;
                final int[] rowKey87 = this.rowKey;
                final int n87 = 2;
                rowKey87[n87] &= 0xF7;
                break;
            }
            case 'S': {
                final int[] rowKey88 = this.rowKey;
                final int n88 = 0;
                rowKey88[n88] &= 0xFE;
                final int[] rowKey89 = this.rowKey;
                final int n89 = 1;
                rowKey89[n89] &= 0xFD;
                break;
            }
            case 'T': {
                final int[] rowKey90 = this.rowKey;
                final int n90 = 0;
                rowKey90[n90] &= 0xFE;
                final int[] rowKey91 = this.rowKey;
                final int n91 = 2;
                rowKey91[n91] &= 0xEF;
                break;
            }
            case 'U': {
                final int[] rowKey92 = this.rowKey;
                final int n92 = 0;
                rowKey92[n92] &= 0xFE;
                final int[] rowKey93 = this.rowKey;
                final int n93 = 5;
                rowKey93[n93] &= 0xF7;
                break;
            }
            case 'V': {
                final int[] rowKey94 = this.rowKey;
                final int n94 = 0;
                rowKey94[n94] &= 0xEE;
                break;
            }
            case 'W': {
                final int[] rowKey95 = this.rowKey;
                final int n95 = 0;
                rowKey95[n95] &= 0xFE;
                final int[] rowKey96 = this.rowKey;
                final int n96 = 2;
                rowKey96[n96] &= 0xFD;
                break;
            }
            case 'X': {
                final int[] rowKey97 = this.rowKey;
                final int n97 = 0;
                rowKey97[n97] &= 0xFA;
                break;
            }
            case 'Y': {
                final int[] rowKey98 = this.rowKey;
                final int n98 = 0;
                rowKey98[n98] &= 0xFE;
                final int[] rowKey99 = this.rowKey;
                final int n99 = 5;
                rowKey99[n99] &= 0xEF;
                break;
            }
            case 'Z': {
                final int[] rowKey100 = this.rowKey;
                final int n100 = 0;
                rowKey100[n100] &= 0xFC;
                break;
            }
            case '[': {
                final int[] rowKey101 = this.rowKey;
                final int n101 = 7;
                rowKey101[n101] &= 0xFD;
                final int[] rowKey102 = this.rowKey;
                final int n102 = 5;
                rowKey102[n102] &= 0xEF;
                break;
            }
            case '\\': {
                final int[] rowKey103 = this.rowKey;
                final int n103 = 7;
                rowKey103[n103] &= 0xFD;
                final int[] rowKey104 = this.rowKey;
                final int n104 = 1;
                rowKey104[n104] &= 0xFB;
                break;
            }
            case ']': {
                final int[] rowKey105 = this.rowKey;
                final int n105 = 7;
                rowKey105[n105] &= 0xFD;
                final int[] rowKey106 = this.rowKey;
                final int n106 = 5;
                rowKey106[n106] &= 0xF7;
                break;
            }
            case '_': {
                final int[] rowKey107 = this.rowKey;
                final int n107 = 7;
                rowKey107[n107] &= 0xFD;
                final int[] rowKey108 = this.rowKey;
                final int n108 = 4;
                rowKey108[n108] &= 0xFE;
                break;
            }
            case 'a': {
                final int[] rowKey109 = this.rowKey;
                final int n109 = 1;
                rowKey109[n109] &= 0xFE;
                break;
            }
            case 'b': {
                final int[] rowKey110 = this.rowKey;
                final int n110 = 7;
                rowKey110[n110] &= 0xEF;
                break;
            }
            case 'c': {
                final int[] rowKey111 = this.rowKey;
                final int n111 = 0;
                rowKey111[n111] &= 0xF7;
                break;
            }
            case 'd': {
                final int[] rowKey112 = this.rowKey;
                final int n112 = 1;
                rowKey112[n112] &= 0xFB;
                break;
            }
            case 'e': {
                final int[] rowKey113 = this.rowKey;
                final int n113 = 2;
                rowKey113[n113] &= 0xFB;
                break;
            }
            case 'f': {
                final int[] rowKey114 = this.rowKey;
                final int n114 = 1;
                rowKey114[n114] &= 0xF7;
                break;
            }
            case 'g': {
                final int[] rowKey115 = this.rowKey;
                final int n115 = 1;
                rowKey115[n115] &= 0xEF;
                break;
            }
            case 'h': {
                final int[] rowKey116 = this.rowKey;
                final int n116 = 6;
                rowKey116[n116] &= 0xEF;
                break;
            }
            case 'i': {
                final int[] rowKey117 = this.rowKey;
                final int n117 = 5;
                rowKey117[n117] &= 0xFB;
                break;
            }
            case 'j': {
                final int[] rowKey118 = this.rowKey;
                final int n118 = 6;
                rowKey118[n118] &= 0xF7;
                break;
            }
            case 'k': {
                final int[] rowKey119 = this.rowKey;
                final int n119 = 6;
                rowKey119[n119] &= 0xFB;
                break;
            }
            case 'l': {
                final int[] rowKey120 = this.rowKey;
                final int n120 = 6;
                rowKey120[n120] &= 0xFD;
                break;
            }
            case 'm': {
                final int[] rowKey121 = this.rowKey;
                final int n121 = 7;
                rowKey121[n121] &= 0xFB;
                break;
            }
            case 'n': {
                final int[] rowKey122 = this.rowKey;
                final int n122 = 7;
                rowKey122[n122] &= 0xF7;
                break;
            }
            case 'o': {
                final int[] rowKey123 = this.rowKey;
                final int n123 = 5;
                rowKey123[n123] &= 0xFD;
                break;
            }
            case 'p': {
                final int[] rowKey124 = this.rowKey;
                final int n124 = 5;
                rowKey124[n124] &= 0xFE;
                break;
            }
            case 'q': {
                final int[] rowKey125 = this.rowKey;
                final int n125 = 2;
                rowKey125[n125] &= 0xFE;
                break;
            }
            case 'r': {
                final int[] rowKey126 = this.rowKey;
                final int n126 = 2;
                rowKey126[n126] &= 0xF7;
                break;
            }
            case 's': {
                final int[] rowKey127 = this.rowKey;
                final int n127 = 1;
                rowKey127[n127] &= 0xFD;
                break;
            }
            case 't': {
                final int[] rowKey128 = this.rowKey;
                final int n128 = 2;
                rowKey128[n128] &= 0xEF;
                break;
            }
            case 'u': {
                final int[] rowKey129 = this.rowKey;
                final int n129 = 5;
                rowKey129[n129] &= 0xF7;
                break;
            }
            case 'v': {
                final int[] rowKey130 = this.rowKey;
                final int n130 = 0;
                rowKey130[n130] &= 0xEF;
                break;
            }
            case 'w': {
                final int[] rowKey131 = this.rowKey;
                final int n131 = 2;
                rowKey131[n131] &= 0xFD;
                break;
            }
            case 'x': {
                final int[] rowKey132 = this.rowKey;
                final int n132 = 0;
                rowKey132[n132] &= 0xFB;
                break;
            }
            case 'y': {
                final int[] rowKey133 = this.rowKey;
                final int n133 = 5;
                rowKey133[n133] &= 0xEF;
                break;
            }
            case 'z': {
                final int[] rowKey134 = this.rowKey;
                final int n134 = 0;
                rowKey134[n134] &= 0xFD;
                break;
            }
            case '{': {
                final int[] rowKey135 = this.rowKey;
                final int n135 = 7;
                rowKey135[n135] &= 0xFD;
                final int[] rowKey136 = this.rowKey;
                final int n136 = 1;
                rowKey136[n136] &= 0xF7;
                break;
            }
            case '|':
            case '¦': {
                final int[] rowKey137 = this.rowKey;
                final int n137 = 7;
                rowKey137[n137] &= 0xFD;
                final int[] rowKey138 = this.rowKey;
                final int n138 = 1;
                rowKey138[n138] &= 0xFD;
                break;
            }
            case '}': {
                final int[] rowKey139 = this.rowKey;
                final int n139 = 7;
                rowKey139[n139] &= 0xFD;
                final int[] rowKey140 = this.rowKey;
                final int n140 = 1;
                rowKey140[n140] &= 0xEF;
                break;
            }
            case '~': {
                final int[] rowKey141 = this.rowKey;
                final int n141 = 7;
                rowKey141[n141] &= 0xFD;
                final int[] rowKey142 = this.rowKey;
                final int n142 = 1;
                rowKey142[n142] &= 0xFE;
                break;
            }
            case '©': {
                final int[] rowKey143 = this.rowKey;
                final int n143 = 7;
                rowKey143[n143] &= 0xFD;
                final int[] rowKey144 = this.rowKey;
                final int n144 = 5;
                rowKey144[n144] &= 0xFE;
                break;
            }
            case '`':
            case '¡':
            case '§': {
                final int[] rowKey145 = this.rowKey;
                final int n145 = 0;
                rowKey145[n145] &= 0xFE;
                final int[] rowKey146 = this.rowKey;
                final int n146 = 3;
                rowKey146[n146] &= 0xFE;
                break;
            }
            case '¬':
            case '±':
            case '¿': {
                final int[] rowKey147 = this.rowKey;
                final int n147 = 0;
                rowKey147[n147] &= 0xFE;
                final int[] rowKey148 = this.rowKey;
                final int n148 = 4;
                rowKey148[n148] &= 0xFD;
                break;
            }
            case '£': {
                final int[] rowKey149 = this.rowKey;
                final int n149 = 7;
                rowKey149[n149] &= 0xFD;
                final int[] rowKey150 = this.rowKey;
                final int n150 = 0;
                rowKey150[n150] &= 0xFB;
                break;
            }
            case 'º': {
                final int[] rowKey151 = this.rowKey;
                final int n151 = 0;
                rowKey151[n151] &= 0xFE;
                final int[] rowKey152 = this.rowKey;
                final int n152 = 7;
                rowKey152[n152] &= 0xFD;
                break;
            }
            default: {
                if (this.shiftPressed) {
                    final int[] rowKey153 = this.rowKey;
                    final int n153 = 0;
                    rowKey153[n153] &= 0xFE;
                }
                done = false;
                break;
            }
        }
        return done;
    }
    
    private boolean releasedKeyChar(final char keyChar) {
        boolean done = true;
        switch (keyChar) {
            case '!': {
                final int[] rowKey = this.rowKey;
                final int n = 7;
                rowKey[n] |= 0x2;
                final int[] rowKey2 = this.rowKey;
                final int n2 = 3;
                rowKey2[n2] |= 0x1;
                break;
            }
            case '\"': {
                final int[] rowKey3 = this.rowKey;
                final int n3 = 7;
                rowKey3[n3] |= 0x2;
                final int[] rowKey4 = this.rowKey;
                final int n4 = 5;
                rowKey4[n4] |= 0x1;
                break;
            }
            case '#': {
                final int[] rowKey5 = this.rowKey;
                final int n5 = 7;
                rowKey5[n5] |= 0x2;
                final int[] rowKey6 = this.rowKey;
                final int n6 = 3;
                rowKey6[n6] |= 0x4;
                break;
            }
            case '$': {
                final int[] rowKey7 = this.rowKey;
                final int n7 = 7;
                rowKey7[n7] |= 0x2;
                final int[] rowKey8 = this.rowKey;
                final int n8 = 3;
                rowKey8[n8] |= 0x8;
                break;
            }
            case '%': {
                final int[] rowKey9 = this.rowKey;
                final int n9 = 7;
                rowKey9[n9] |= 0x2;
                final int[] rowKey10 = this.rowKey;
                final int n10 = 3;
                rowKey10[n10] |= 0x10;
                break;
            }
            case '&': {
                final int[] rowKey11 = this.rowKey;
                final int n11 = 7;
                rowKey11[n11] |= 0x2;
                final int[] rowKey12 = this.rowKey;
                final int n12 = 4;
                rowKey12[n12] |= 0x10;
                break;
            }
            case '\'': {
                final int[] rowKey13 = this.rowKey;
                final int n13 = 7;
                rowKey13[n13] |= 0x2;
                final int[] rowKey14 = this.rowKey;
                final int n14 = 4;
                rowKey14[n14] |= 0x8;
                break;
            }
            case '(': {
                final int[] rowKey15 = this.rowKey;
                final int n15 = 7;
                rowKey15[n15] |= 0x2;
                final int[] rowKey16 = this.rowKey;
                final int n16 = 4;
                rowKey16[n16] |= 0x4;
                break;
            }
            case ')': {
                final int[] rowKey17 = this.rowKey;
                final int n17 = 7;
                rowKey17[n17] |= 0x2;
                final int[] rowKey18 = this.rowKey;
                final int n18 = 4;
                rowKey18[n18] |= 0x2;
                break;
            }
            case '*': {
                final int[] rowKey19 = this.rowKey;
                final int n19 = 7;
                rowKey19[n19] |= 0x12;
                break;
            }
            case '+': {
                final int[] rowKey20 = this.rowKey;
                final int n20 = 7;
                rowKey20[n20] |= 0x2;
                final int[] rowKey21 = this.rowKey;
                final int n21 = 6;
                rowKey21[n21] |= 0x4;
                break;
            }
            case ',': {
                final int[] rowKey22 = this.rowKey;
                final int n22 = 7;
                rowKey22[n22] |= 0xA;
                break;
            }
            case '-': {
                final int[] rowKey23 = this.rowKey;
                final int n23 = 7;
                rowKey23[n23] |= 0x2;
                final int[] rowKey24 = this.rowKey;
                final int n24 = 6;
                rowKey24[n24] |= 0x8;
                break;
            }
            case '.': {
                final int[] rowKey25 = this.rowKey;
                final int n25 = 7;
                rowKey25[n25] |= 0x6;
                break;
            }
            case '/': {
                final int[] rowKey26 = this.rowKey;
                final int n26 = 7;
                rowKey26[n26] |= 0x2;
                final int[] rowKey27 = this.rowKey;
                final int n27 = 0;
                rowKey27[n27] |= 0x10;
                break;
            }
            case '0': {
                final int[] rowKey28 = this.rowKey;
                final int n28 = 4;
                rowKey28[n28] |= 0x1;
                break;
            }
            case '1': {
                final int[] rowKey29 = this.rowKey;
                final int n29 = 3;
                rowKey29[n29] |= 0x1;
                break;
            }
            case '2': {
                final int[] rowKey30 = this.rowKey;
                final int n30 = 3;
                rowKey30[n30] |= 0x2;
                break;
            }
            case '3': {
                final int[] rowKey31 = this.rowKey;
                final int n31 = 3;
                rowKey31[n31] |= 0x4;
                break;
            }
            case '4': {
                final int[] rowKey32 = this.rowKey;
                final int n32 = 3;
                rowKey32[n32] |= 0x8;
                break;
            }
            case '5': {
                final int[] rowKey33 = this.rowKey;
                final int n33 = 3;
                rowKey33[n33] |= 0x10;
                break;
            }
            case '6': {
                final int[] rowKey34 = this.rowKey;
                final int n34 = 4;
                rowKey34[n34] |= 0x10;
                break;
            }
            case '7': {
                final int[] rowKey35 = this.rowKey;
                final int n35 = 4;
                rowKey35[n35] |= 0x8;
                break;
            }
            case '8': {
                final int[] rowKey36 = this.rowKey;
                final int n36 = 4;
                rowKey36[n36] |= 0x4;
                break;
            }
            case '9': {
                final int[] rowKey37 = this.rowKey;
                final int n37 = 4;
                rowKey37[n37] |= 0x2;
                break;
            }
            case ':': {
                final int[] rowKey38 = this.rowKey;
                final int n38 = 7;
                rowKey38[n38] |= 0x2;
                final int[] rowKey39 = this.rowKey;
                final int n39 = 0;
                rowKey39[n39] |= 0x2;
                break;
            }
            case ';': {
                final int[] rowKey40 = this.rowKey;
                final int n40 = 7;
                rowKey40[n40] |= 0x2;
                final int[] rowKey41 = this.rowKey;
                final int n41 = 5;
                rowKey41[n41] |= 0x2;
                break;
            }
            case '<': {
                final int[] rowKey42 = this.rowKey;
                final int n42 = 7;
                rowKey42[n42] |= 0x2;
                final int[] rowKey43 = this.rowKey;
                final int n43 = 2;
                rowKey43[n43] |= 0x8;
                break;
            }
            case '=': {
                final int[] rowKey44 = this.rowKey;
                final int n44 = 7;
                rowKey44[n44] |= 0x2;
                final int[] rowKey45 = this.rowKey;
                final int n45 = 6;
                rowKey45[n45] |= 0x2;
                break;
            }
            case '>': {
                final int[] rowKey46 = this.rowKey;
                final int n46 = 7;
                rowKey46[n46] |= 0x2;
                final int[] rowKey47 = this.rowKey;
                final int n47 = 2;
                rowKey47[n47] |= 0x10;
                break;
            }
            case '?': {
                final int[] rowKey48 = this.rowKey;
                final int n48 = 7;
                rowKey48[n48] |= 0x2;
                final int[] rowKey49 = this.rowKey;
                final int n49 = 0;
                rowKey49[n49] |= 0x8;
                break;
            }
            case '@': {
                final int[] rowKey50 = this.rowKey;
                final int n50 = 7;
                rowKey50[n50] |= 0x2;
                final int[] rowKey51 = this.rowKey;
                final int n51 = 3;
                rowKey51[n51] |= 0x2;
                break;
            }
            case 'A': {
                final int[] rowKey52 = this.rowKey;
                final int n52 = 1;
                rowKey52[n52] |= 0x1;
                break;
            }
            case 'B': {
                final int[] rowKey53 = this.rowKey;
                final int n53 = 7;
                rowKey53[n53] |= 0x10;
                break;
            }
            case 'C': {
                final int[] rowKey54 = this.rowKey;
                final int n54 = 0;
                rowKey54[n54] |= 0x8;
                break;
            }
            case 'D': {
                final int[] rowKey55 = this.rowKey;
                final int n55 = 1;
                rowKey55[n55] |= 0x4;
                break;
            }
            case 'E': {
                final int[] rowKey56 = this.rowKey;
                final int n56 = 2;
                rowKey56[n56] |= 0x4;
                break;
            }
            case 'F': {
                final int[] rowKey57 = this.rowKey;
                final int n57 = 1;
                rowKey57[n57] |= 0x8;
                break;
            }
            case 'G': {
                final int[] rowKey58 = this.rowKey;
                final int n58 = 1;
                rowKey58[n58] |= 0x10;
                break;
            }
            case 'H': {
                final int[] rowKey59 = this.rowKey;
                final int n59 = 6;
                rowKey59[n59] |= 0x10;
                break;
            }
            case 'I': {
                final int[] rowKey60 = this.rowKey;
                final int n60 = 5;
                rowKey60[n60] |= 0x4;
                break;
            }
            case 'J': {
                final int[] rowKey61 = this.rowKey;
                final int n61 = 6;
                rowKey61[n61] |= 0x8;
                break;
            }
            case 'K': {
                final int[] rowKey62 = this.rowKey;
                final int n62 = 6;
                rowKey62[n62] |= 0x4;
                break;
            }
            case 'L': {
                final int[] rowKey63 = this.rowKey;
                final int n63 = 6;
                rowKey63[n63] |= 0x2;
                break;
            }
            case 'M': {
                final int[] rowKey64 = this.rowKey;
                final int n64 = 7;
                rowKey64[n64] |= 0x4;
                break;
            }
            case 'N': {
                final int[] rowKey65 = this.rowKey;
                final int n65 = 7;
                rowKey65[n65] |= 0x8;
                break;
            }
            case 'O': {
                final int[] rowKey66 = this.rowKey;
                final int n66 = 5;
                rowKey66[n66] |= 0x2;
                break;
            }
            case 'P': {
                final int[] rowKey67 = this.rowKey;
                final int n67 = 5;
                rowKey67[n67] |= 0x1;
                break;
            }
            case 'Q': {
                final int[] rowKey68 = this.rowKey;
                final int n68 = 2;
                rowKey68[n68] |= 0x1;
                break;
            }
            case 'R': {
                final int[] rowKey69 = this.rowKey;
                final int n69 = 2;
                rowKey69[n69] |= 0x8;
                break;
            }
            case 'S': {
                final int[] rowKey70 = this.rowKey;
                final int n70 = 1;
                rowKey70[n70] |= 0x2;
                break;
            }
            case 'T': {
                final int[] rowKey71 = this.rowKey;
                final int n71 = 2;
                rowKey71[n71] |= 0x10;
                break;
            }
            case 'U': {
                final int[] rowKey72 = this.rowKey;
                final int n72 = 5;
                rowKey72[n72] |= 0x8;
                break;
            }
            case 'V': {
                final int[] rowKey73 = this.rowKey;
                final int n73 = 0;
                rowKey73[n73] |= 0x10;
                break;
            }
            case 'W': {
                final int[] rowKey74 = this.rowKey;
                final int n74 = 2;
                rowKey74[n74] |= 0x2;
                break;
            }
            case 'X': {
                final int[] rowKey75 = this.rowKey;
                final int n75 = 0;
                rowKey75[n75] |= 0x4;
                break;
            }
            case 'Y': {
                final int[] rowKey76 = this.rowKey;
                final int n76 = 5;
                rowKey76[n76] |= 0x10;
                break;
            }
            case 'Z': {
                final int[] rowKey77 = this.rowKey;
                final int n77 = 0;
                rowKey77[n77] |= 0x2;
                break;
            }
            case '[': {
                final int[] rowKey78 = this.rowKey;
                final int n78 = 7;
                rowKey78[n78] |= 0x2;
                final int[] rowKey79 = this.rowKey;
                final int n79 = 5;
                rowKey79[n79] |= 0x10;
                break;
            }
            case '\\': {
                final int[] rowKey80 = this.rowKey;
                final int n80 = 7;
                rowKey80[n80] |= 0x2;
                final int[] rowKey81 = this.rowKey;
                final int n81 = 1;
                rowKey81[n81] |= 0x4;
                break;
            }
            case ']': {
                final int[] rowKey82 = this.rowKey;
                final int n82 = 7;
                rowKey82[n82] |= 0x2;
                final int[] rowKey83 = this.rowKey;
                final int n83 = 5;
                rowKey83[n83] |= 0x8;
                break;
            }
            case '_': {
                final int[] rowKey84 = this.rowKey;
                final int n84 = 7;
                rowKey84[n84] |= 0x2;
                final int[] rowKey85 = this.rowKey;
                final int n85 = 4;
                rowKey85[n85] |= 0x1;
                break;
            }
            case 'a': {
                final int[] rowKey86 = this.rowKey;
                final int n86 = 1;
                rowKey86[n86] |= 0x1;
                break;
            }
            case 'b': {
                final int[] rowKey87 = this.rowKey;
                final int n87 = 7;
                rowKey87[n87] |= 0x10;
                break;
            }
            case 'c': {
                final int[] rowKey88 = this.rowKey;
                final int n88 = 0;
                rowKey88[n88] |= 0x8;
                break;
            }
            case 'd': {
                final int[] rowKey89 = this.rowKey;
                final int n89 = 1;
                rowKey89[n89] |= 0x4;
                break;
            }
            case 'e': {
                final int[] rowKey90 = this.rowKey;
                final int n90 = 2;
                rowKey90[n90] |= 0x4;
                break;
            }
            case 'f': {
                final int[] rowKey91 = this.rowKey;
                final int n91 = 1;
                rowKey91[n91] |= 0x8;
                break;
            }
            case 'g': {
                final int[] rowKey92 = this.rowKey;
                final int n92 = 1;
                rowKey92[n92] |= 0x10;
                break;
            }
            case 'h': {
                final int[] rowKey93 = this.rowKey;
                final int n93 = 6;
                rowKey93[n93] |= 0x10;
                break;
            }
            case 'i': {
                final int[] rowKey94 = this.rowKey;
                final int n94 = 5;
                rowKey94[n94] |= 0x4;
                break;
            }
            case 'j': {
                final int[] rowKey95 = this.rowKey;
                final int n95 = 6;
                rowKey95[n95] |= 0x8;
                break;
            }
            case 'k': {
                final int[] rowKey96 = this.rowKey;
                final int n96 = 6;
                rowKey96[n96] |= 0x4;
                break;
            }
            case 'l': {
                final int[] rowKey97 = this.rowKey;
                final int n97 = 6;
                rowKey97[n97] |= 0x2;
                break;
            }
            case 'm': {
                final int[] rowKey98 = this.rowKey;
                final int n98 = 7;
                rowKey98[n98] |= 0x4;
                break;
            }
            case 'n': {
                final int[] rowKey99 = this.rowKey;
                final int n99 = 7;
                rowKey99[n99] |= 0x8;
                break;
            }
            case 'o': {
                final int[] rowKey100 = this.rowKey;
                final int n100 = 5;
                rowKey100[n100] |= 0x2;
                break;
            }
            case 'p': {
                final int[] rowKey101 = this.rowKey;
                final int n101 = 5;
                rowKey101[n101] |= 0x1;
                break;
            }
            case 'q': {
                final int[] rowKey102 = this.rowKey;
                final int n102 = 2;
                rowKey102[n102] |= 0x1;
                break;
            }
            case 'r': {
                final int[] rowKey103 = this.rowKey;
                final int n103 = 2;
                rowKey103[n103] |= 0x8;
                break;
            }
            case 's': {
                final int[] rowKey104 = this.rowKey;
                final int n104 = 1;
                rowKey104[n104] |= 0x2;
                break;
            }
            case 't': {
                final int[] rowKey105 = this.rowKey;
                final int n105 = 2;
                rowKey105[n105] |= 0x10;
                break;
            }
            case 'u': {
                final int[] rowKey106 = this.rowKey;
                final int n106 = 5;
                rowKey106[n106] |= 0x8;
                break;
            }
            case 'v': {
                final int[] rowKey107 = this.rowKey;
                final int n107 = 0;
                rowKey107[n107] |= 0x10;
                break;
            }
            case 'w': {
                final int[] rowKey108 = this.rowKey;
                final int n108 = 2;
                rowKey108[n108] |= 0x2;
                break;
            }
            case 'x': {
                final int[] rowKey109 = this.rowKey;
                final int n109 = 0;
                rowKey109[n109] |= 0x4;
                break;
            }
            case 'y': {
                final int[] rowKey110 = this.rowKey;
                final int n110 = 5;
                rowKey110[n110] |= 0x10;
                break;
            }
            case 'z': {
                final int[] rowKey111 = this.rowKey;
                final int n111 = 0;
                rowKey111[n111] |= 0x2;
                break;
            }
            case '{': {
                final int[] rowKey112 = this.rowKey;
                final int n112 = 7;
                rowKey112[n112] |= 0x2;
                final int[] rowKey113 = this.rowKey;
                final int n113 = 1;
                rowKey113[n113] |= 0x8;
                break;
            }
            case '|':
            case '¦': {
                final int[] rowKey114 = this.rowKey;
                final int n114 = 7;
                rowKey114[n114] |= 0x2;
                final int[] rowKey115 = this.rowKey;
                final int n115 = 1;
                rowKey115[n115] |= 0x2;
                break;
            }
            case '}': {
                final int[] rowKey116 = this.rowKey;
                final int n116 = 7;
                rowKey116[n116] |= 0x2;
                final int[] rowKey117 = this.rowKey;
                final int n117 = 1;
                rowKey117[n117] |= 0x10;
                break;
            }
            case '~': {
                final int[] rowKey118 = this.rowKey;
                final int n118 = 7;
                rowKey118[n118] |= 0x2;
                final int[] rowKey119 = this.rowKey;
                final int n119 = 1;
                rowKey119[n119] |= 0x1;
                break;
            }
            case '©': {
                final int[] rowKey120 = this.rowKey;
                final int n120 = 7;
                rowKey120[n120] |= 0x2;
                final int[] rowKey121 = this.rowKey;
                final int n121 = 5;
                rowKey121[n121] |= 0x1;
                break;
            }
            case '`':
            case '¡':
            case '§': {
                final int[] rowKey122 = this.rowKey;
                final int n122 = 3;
                rowKey122[n122] |= 0x1;
                break;
            }
            case '¬':
            case '±':
            case '¿': {
                final int[] rowKey123 = this.rowKey;
                final int n123 = 4;
                rowKey123[n123] |= 0x2;
                break;
            }
            case '£': {
                final int[] rowKey124 = this.rowKey;
                final int n124 = 7;
                rowKey124[n124] |= 0x2;
                final int[] rowKey125 = this.rowKey;
                final int n125 = 0;
                rowKey125[n125] |= 0x4;
                break;
            }
            case 'º': {
                final int[] rowKey126 = this.rowKey;
                final int n126 = 0;
                rowKey126[n126] |= 0x1;
                final int[] rowKey127 = this.rowKey;
                final int n127 = 7;
                rowKey127[n127] |= 0x2;
                break;
            }
            default: {
                if (this.shiftPressed) {
                    final int[] rowKey128 = this.rowKey;
                    final int n128 = 0;
                    rowKey128[n128] &= 0xFE;
                }
                done = false;
                break;
            }
        }
        return done;
    }
}
