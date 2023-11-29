package zxspectrum.emul;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface Loader {
    void load(InputStream is) throws IOException;

    void load(File file) throws IOException;
}
