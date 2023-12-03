package zxspectrum.emul.io.mem;

public interface RomControl {
    int ROM_SIZE = 0x4000;

    void uploadRom(int n, byte[] buf, int offset, int length);

    void upload(byte[]... buf);

    int getRomCount();
}
