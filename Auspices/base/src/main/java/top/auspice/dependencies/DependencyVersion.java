package top.auspice.dependencies;

import java.util.Arrays;
import java.util.Base64;

public class DependencyVersion {
    private final String version;
    private final byte[] checksum;

    private DependencyVersion(String version, String checksum) {
        this.version = version;
        this.checksum = Base64.getDecoder().decode(checksum);
    }

    public static DependencyVersion of(String version, String checksum) {
        return new DependencyVersion(version, checksum);
    }

    public String getVersion() {
        return this.version;
    }

    public boolean checksumMatches(byte[] hash) {
        return Arrays.equals(this.checksum, hash);
    }

    public byte[] getChecksum() {
        return this.checksum;
    }
}
