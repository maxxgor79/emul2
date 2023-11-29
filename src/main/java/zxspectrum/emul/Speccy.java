package zxspectrum.emul;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;
import zxspectrum.emul.machine.ZXSpectrum;
import zxspectrum.emul.machine.ZXSpectrumFactory;
import zxspectrum.emul.profile.ZxProfiles;

@Slf4j
public class Speccy {

    public static void main(String[] args) {
        try {
            ZxProfiles.getInstance().load();
            ZXSpectrum zxSpectrum = ZXSpectrumFactory.getInstance(ZxProfiles.getInstance().getDefaultProfile());
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(zxSpectrum);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
