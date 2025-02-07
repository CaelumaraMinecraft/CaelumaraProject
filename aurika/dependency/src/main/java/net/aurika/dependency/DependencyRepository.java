//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.aurika.dependency;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.*;

public enum DependencyRepository {
    MAVEN_CENTRAL("https://repo1.maven.org/maven2/");

    private final String url;

    DependencyRepository(String url) {
        this.url = url;
    }

    public URL getURLFor(Dependency dependency) {
        try {
            return new URL(this.url + dependency.getMavenRepoPath());
        } catch (MalformedURLException var3) {
            MalformedURLException e = var3;
            throw new RuntimeException("Invalid URL for dependency: " + dependency, e);
        }
    }

    protected URLConnection openConnection(Dependency dependency) throws IOException {
        URL dependencyUrl = this.getURLFor(dependency);
        return dependencyUrl.openConnection();
    }

    public String getUrl() {
        return this.url;
    }

    public byte[] downloadRaw(Dependency dependency) throws DependencyDownloadException {
        try {
            URLConnection connection = this.openConnection(dependency);
            InputStream in = connection.getInputStream();

            byte[] var5;
            try {
                byte[] bytes = readBytes(in);
                if (bytes.length == 0) {
                    throw new DependencyDownloadException("Empty stream");
                }

                var5 = bytes;
            } catch (Throwable var7) {
                if (in != null) {
                    try {
                        in.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }
                }

                throw var7;
            }

            if (in != null) {
                in.close();
            }

            return var5;
        } catch (Exception var8) {
            Exception e = var8;
            throw new DependencyDownloadException(e);
        }
    }

    public static byte[] readBytes(InputStream stream) throws IOException {
        List<byte[]> bufs = null;
        byte[] result = null;
        int total = 0;
        int MAX_BUFFER_SIZE = 2147483639;

        int n;
        int remaining;
        do {
            byte[] buf = new byte[8192];

            for (remaining = 0; (n = stream.read(buf, remaining, buf.length - remaining)) > 0; remaining += n) {
            }

            if (remaining > 0) {
                if (2147483639 - total < remaining) {
                    throw new OutOfMemoryError("Required array size too large");
                }

                if (remaining < buf.length) {
                    buf = Arrays.copyOfRange(buf, 0, remaining);
                }

                total += remaining;
                if (result == null) {
                    result = buf;
                } else {
                    if (bufs == null) {
                        bufs = new ArrayList();
                        bufs.add(result);
                    }

                    bufs.add(buf);
                }
            }
        } while (n >= 0);

        if (bufs == null) {
            if (result == null) {
                return new byte[0];
            } else {
                return result.length == total ? result : Arrays.copyOf(result, total);
            }
        } else {
            result = new byte[total];
            int offset = 0;
            remaining = total;

            int count;
            for (Iterator<byte[]> var8 = bufs.iterator(); var8.hasNext(); remaining -= count) {
                byte[] b = (byte[]) var8.next();
                count = Math.min(b.length, remaining);
                System.arraycopy(b, 0, result, offset, count);
                offset += count;
            }

            return result;
        }
    }

    public byte[] download(Dependency dependency) throws DependencyDownloadException {
        byte[] bytes = this.downloadRaw(dependency);
        byte[] hash = Dependency.createDigest().digest(bytes);
        if (!dependency.getDefaultVersion().checksumMatches(hash)) {
            throw new DependencyDownloadException("Downloaded file had an invalid hash. Expected: " + Base64.getEncoder().encodeToString(dependency.getDefaultVersion().getChecksum()) + ' ' + "Actual: " + Base64.getEncoder().encodeToString(hash));
        } else {
            return bytes;
        }
    }

    public void download(Dependency dependency, Path file) throws DependencyDownloadException {
        try {
            Files.write(file, this.download(dependency), new OpenOption[0]);
        } catch (IOException var4) {
            IOException e = var4;
            throw new DependencyDownloadException(e);
        }
    }
}
