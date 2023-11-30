package zxspectrum.emul;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import zxspectrum.emul.chipset.UlaType;
import zxspectrum.emul.cpu.CpuType;
import zxspectrum.emul.io.mem.ram.RamType;
import zxspectrum.emul.io.sound.SoundChipType;
import zxspectrum.emul.profile.ZxProfile;
import zxspectrum.emul.profile.ZxProfiles;

import java.io.IOException;
import java.util.List;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TestProfile {
    private ZxProfiles profiles = ZxProfiles.getInstance();

    @Test
    void test1() throws IOException {
        profiles.load();
        List<ZxProfile> profileList = profiles.getProfiles();
        Assertions.assertNotNull(profileList);
        Assertions.assertTrue(profileList.size() > 0);
        ZxProfile symbolIk = profiles.getByName("Symbol IK");
        Assertions.assertNotNull(symbolIk);
        Assertions.assertEquals(symbolIk.getScreenWidth(), 256);
        Assertions.assertEquals(symbolIk.getScreenHeight(), 192);
        Assertions.assertEquals(symbolIk.getLeftBorderWidth(), 48);
        Assertions.assertEquals(symbolIk.getRightBorderWidth(), 48);
        Assertions.assertEquals(symbolIk.getTopBorderHeight(), 64);
        Assertions.assertEquals(symbolIk.getBottomBorderHeight(), 56);
        Assertions.assertEquals(symbolIk.getSoundChipType(), SoundChipType.None);
        Assertions.assertEquals(symbolIk.getTStatesPerLine(), 224);
        Assertions.assertEquals(symbolIk.getIntLength(), 32);
        Assertions.assertEquals(symbolIk.hasBeeper(), true);
        Assertions.assertEquals(symbolIk.hasDisk(), false);
        Assertions.assertEquals(symbolIk.getCpuType(), CpuType.Z80A);
        Assertions.assertEquals(symbolIk.getRamType(), RamType.Ram48k);
        Assertions.assertEquals(symbolIk.getUlaType(), UlaType.UlaStd);
        Assertions.assertNotNull(symbolIk.getRoms());
        Assertions.assertEquals(symbolIk.getRoms().size(), 1);
        Assertions.assertEquals(symbolIk.getRoms().get(0).length, 16384);
    }
}
