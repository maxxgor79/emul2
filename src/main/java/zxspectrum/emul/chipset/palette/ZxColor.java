package zxspectrum.emul.chipset.palette;

import lombok.Getter;

public enum ZxColor {
    Black(0x00, 0x00, 0x00, 0), DarkBlue(0x00, 0x00, 0xC0, 1),
    Blue(0x00, 0x00, 0xFF, 1), DarkRed(0xC0, 0x00, 0x00, 2),
    Red(0xFF, 0x00, 0x00, 2), DarkMagenta(0xC0, 0x00, 0xC0, 3),
    Magenta(0xFF, 0x00, 0xFF, 3), DarkGreen(0x00, 0xC0, 0x00, 4),
    Green(0x00, 0xFF, 0x00, 4), DarkCyan(0x00, 0xC0, 0xC0, 5),
    Cyan(0x00, 0xFF, 0xFF, 5), DarkYellow(0xC0, 0xC0, 0x00, 6),
    Yellow(0xFF, 0xFF, 0x00, 6), Gray(0xC0, 0xC0, 0xC0, 7),
    White(0xFF, 0xFF, 0xFF, 7);


    ZxColor(int r, int g, int b, int id) {
        this.r = r & 0xFF;
        this.g = g & 0xFF;
        this.b = b & 0xFF;
        this.id = id & 0x07;
        color = r << 16 | g << 8 | b;
    }

    @Getter
    private final int r;

    @Getter
    private final int g;

    @Getter
    private final int b;

    @Getter
    private final int color;

    @Getter
    private int id;

    public static final ZxColor[] darkColor = new ZxColor[8];

    static {
        darkColor[Black.id] = Black;
        darkColor[DarkBlue.id] = DarkBlue;
        darkColor[DarkRed.id] = DarkRed;
        darkColor[DarkMagenta.id] = DarkMagenta;
        darkColor[DarkGreen.id] = DarkGreen;
        darkColor[DarkCyan.id] = DarkCyan;
        darkColor[DarkYellow.id] = DarkYellow;
        darkColor[Gray.id] = Gray;
    }

    public static final ZxColor[] lightColor = new ZxColor[8];

    static {
        lightColor[Black.id] = Black;
        lightColor[Blue.id] = Blue;
        lightColor[Red.id] = Red;
        lightColor[Magenta.id] = Magenta;
        lightColor[Green.id] = Green;
        lightColor[Cyan.id] = Cyan;
        lightColor[Yellow.id] = Yellow;
        lightColor[White.id] = White;
    }
}
