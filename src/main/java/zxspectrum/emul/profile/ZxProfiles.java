package zxspectrum.emul.profile;

import lombok.NonNull;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public final class ZxProfiles {
    protected static final String FILE_NAME = "profiles.properties";

    private static final ZxProfiles instance = new ZxProfiles();

    public static ZxProfiles getInstance() {
        return instance;
    }

    private ZxProfiles() {
    }

    private String defaultProfileName;

    private List<ZxProfile> profiles;

    public List<ZxProfile> load() throws IOException {
        return load("/" + FILE_NAME);
    }

    public List<ZxProfile> load(@NonNull final String fileName) throws IOException {
        Properties props = new Properties();
        props.load(new ByteArrayInputStream(IOUtils.resourceToByteArray(fileName)));
        defaultProfileName = props.getProperty("defaultProfileName");
        final List<ZxProfile> profiles = new LinkedList<>();
        for (int i = 0; i < 256; i++) {
            final String profileName = props.getProperty("profile" + i);
            if (profileName == null) {
                break;
            }
            final ZxProfile profile = new ZxProfile();
            profile.load(new ByteArrayInputStream(IOUtils.resourceToByteArray(profileName)));
            profiles.add(profile);
        }
        defaultProfileName = props.getProperty("defaultProfileName");
        this.profiles = Collections.unmodifiableList(profiles);
        return this.profiles;
    }

    public List<ZxProfile> getProfiles() {
        return profiles == null ? Collections.emptyList() : profiles;
    }

    public ZxProfile getByName(@NonNull final String name) {
        if (name.trim().isEmpty()) {
            return null;
        }
        if (profiles == null) {
            return null;
        }
        for (ZxProfile profile : profiles) {
            if (name.equalsIgnoreCase(profile.getName())) {
                return profile;
            }
        }
        return null;
    }

    public ZxProfile getDefaultProfile() {
        ZxProfile profile = getByName(defaultProfileName);
        if (profile == null && !profiles.isEmpty()) {
            profile = profiles.get(0);
        }
        return profile;
    }
}
