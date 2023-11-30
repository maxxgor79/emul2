package zxspectrum.emul.profile;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import zxspectrum.emul.Loader;
import zxspectrum.emul.chipset.UlaType;
import zxspectrum.emul.cpu.CpuType;
import zxspectrum.emul.io.mem.ram.RamType;
import zxspectrum.emul.io.mem.rom.RomType;
import zxspectrum.emul.io.sound.SoundChipType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Slf4j
@EqualsAndHashCode
public class ZxProfile implements Loader {
    protected static final String ROM_DIR = "rom";

    protected static final String SKIN_DIR = "skin";


    @Getter
    @Setter
    @NonNull
    private String name;

    @Getter
    @Setter
    private String description;

    @Setter
    @Getter
    private int screenWidth;

    @Setter
    @Getter
    private int screenHeight;

    @Getter
    @Setter
    private int leftBorderWidth;

    @Getter
    @Setter
    private int rightBorderWidth;

    @Getter
    @Setter
    private int topBorderHeight;

    @Getter
    @Setter
    private int bottomBorderHeight;

    @Getter
    @Setter
    private int tStatesPerLine;

    @Getter
    @Setter
    private int intLength;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private int scanLines;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private SoundChipType soundChipType;

    @Getter
    @Setter
    private boolean beeper;

    @Getter
    @Setter
    private boolean disk;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private int tStatesPerFrame;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private double intRate;

    @Getter
    @Setter
    @NonNull
    private CpuType cpuType;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    @NonNull
    private RamType ramType;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    @NonNull
    private UlaType ulaType;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    @NonNull
    private List<byte[]> roms;



    public boolean hasDisk() {
        return disk;
    }

    public boolean hasBeeper() {
        return beeper;
    }

    protected static int getInt(@NonNull final Properties props, @NonNull final String property) {
        try {
            return Integer.parseInt(props.getProperty(property, "0"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    protected static boolean getBoolean(@NonNull final Properties props, @NonNull final String property) {
        try {
            return Boolean.parseBoolean(props.getProperty(property, "false"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public void load(@NonNull final InputStream is) throws IOException {
        Properties props = new Properties();
        props.load(is);
        setName(props.getProperty("name"));
        setDescription(props.getProperty("description"));
        setScreenWidth(getInt(props, "screenWidth"));
        setScreenHeight(getInt(props, "screenHeight"));
        setLeftBorderWidth(getInt(props, "leftBorderWidth"));
        setRightBorderWidth(getInt(props, "rightBorderWidth"));
        setTopBorderHeight(getInt(props, "topBorderHeight"));
        setBottomBorderHeight(getInt(props, "bottomBorderHeight"));
        setTStatesPerLine(getInt(props, "tStatesPerLine"));
        setIntLength(getInt(props, "intLength"));
        setCpuType(CpuType.getByName(props.getProperty("cpuType")));
        setRamType(RamType.getByName(props.getProperty("ramType")));
        setUlaType(UlaType.getByName(props.getProperty("ulaType")));
        setDisk(getBoolean(props, "disk"));
        setSoundChipType(SoundChipType.getByName(props.getProperty("soundChipType")));
        setBeeper(getBoolean(props, "beeper"));
        final String romTypes = props.getProperty("romType");
        if (romTypes != null) {
            setRoms(parseRomType(romTypes));
        } else {
            final String romFileNames = props.getProperty("romFileName");
            setRoms(parseRomFileName(romFileNames));
        }
        setScanLines(getTopBorderHeight() + getScreenHeight() + getBottomBorderHeight());
        setTStatesPerFrame(getScanLines() * getTStatesPerLine());
        setIntRate(cpuType.getClockRate() / getTStatesPerFrame());
    }

    protected static List<byte[]> parseRomFileName(@NonNull final String romFileName) throws IOException {
        final String[] fileNames = romFileName.split(",");
        final List<byte[]> roms = new ArrayList<>(fileNames.length);
        for (String fileName : fileNames) {
            fileName = fileName.trim();
            roms.add(loadRom(fileName));
        }
        return Collections.unmodifiableList(roms);
    }


    protected static List<byte[]> parseRomType(@NonNull final String romTypes) throws IOException {
        final String[] types = romTypes.split(",");
        final List<byte[]> roms = new ArrayList<>(types.length);
        for (String type : types) {
            type = type.trim();
            RomType romType = RomType.getByName(type);
            if (romType == null) {
                throw new IllegalArgumentException("" + type);
            }
            roms.add(loadRom(romType.getName()));
        }
        return Collections.unmodifiableList(roms);
    }

    protected static byte[] loadRom(@NonNull final String name) throws IOException {
        return IOUtils.resourceToByteArray("/" + ROM_DIR + "/" + name);
    }

    @Override
    public void load(@NonNull final File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            load(is);
        }
    }
}
