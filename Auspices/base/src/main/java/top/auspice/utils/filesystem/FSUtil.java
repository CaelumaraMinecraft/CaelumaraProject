package top.auspice.utils.filesystem;

import com.google.common.base.Strings;
import org.apache.commons.io.function.IORunnable;
import top.auspice.utils.arrays.ArrayUtils;
import top.auspice.utils.unsafe.Fn;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

@SuppressWarnings("unused")
public final class FSUtil {
    public static final StandardOpenOption[] STD_WRITER;
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final Set<Character> ILLEGAL_CHARACTERS;

    public FSUtil() {
    }

    public static int countEntriesOf(Path folder) {
        if (!Files.isDirectory(folder)) {
            throw new IllegalArgumentException("Path is not a folder: " + folder.toAbsolutePath());
        } else {
            try {
                DirectoryStream<Path> fs = Files.newDirectoryStream(folder);

                int var2;
                try {
                    var2 = ArrayUtils.sizeOfIterator(fs.iterator());
                } catch (Throwable var5) {
                    if (fs != null) {
                        try {
                            fs.close();
                        } catch (Throwable var4) {
                            var5.addSuppressed(var4);
                        }
                    }

                    throw var5;
                }

                //noinspection ConstantValue
                if (fs != null) {
                    fs.close();
                }

                return var2;
            } catch (IOException var6) {
                throw new RuntimeException(var6);
            }
        }
    }

    public static BufferedWriter standardWriter(Path path) throws IOException {
        return Files.newBufferedWriter(path, StandardCharsets.UTF_8, STD_WRITER);
    }

    public static int countEntriesOf(Path folder, Predicate<Path> filter) {
        if (!Files.isDirectory(folder)) {
            throw new IllegalArgumentException("Path is not a folder: " + folder.toAbsolutePath());
        } else {
            try {
                DirectoryStream<Path> fs = Files.newDirectoryStream(folder);

                int var3;
                try {
                    var3 = (int) StreamSupport.stream(fs.spliterator(), false).filter(filter).count();
                } catch (Throwable var6) {
                    if (fs != null) {
                        try {
                            fs.close();
                        } catch (Throwable var5) {
                            var6.addSuppressed(var5);
                        }
                    }

                    throw var6;
                }

                //noinspection ConstantValue
                if (fs != null) {
                    fs.close();
                }

                return var3;
            } catch (IOException var7) {
                throw new RuntimeException(var7);
            }
        }
    }

    public static Path findSlotForCounterFile(Path folder, String prefix, String extension) {
        int counter = 1;

        Path file;
        do {
            file = folder.resolve(prefix + '-' + counter++ + '.' + extension);
        } while (Files.exists(file));

        return file;
    }

    public static Path findSlotForCounterFolder(Path folder, String prefix) {
        int counter = 1;

        Path file;
        do {
            file = folder.resolve(prefix + '-' + counter++);
        } while (Files.exists(file) && Files.isDirectory(file));

        return file;
    }

    public void lockBeforeCopy(Path from, OutputStream to, Runnable beforeWrite) throws IOException {
        FileChannel fileChannel = FileChannel.open(from, StandardOpenOption.READ);

        try {
            FileLock lock = fileChannel.lock(0L, Long.MAX_VALUE, true);

            try {
                WritableByteChannel zsChan = Channels.newChannel(to);
                long maxCount = 67076096L;
                long size = fileChannel.size();
                long position = 0L;
                beforeWrite.run();

                while (size > 0L) {
                    long count = fileChannel.transferTo(position, Math.min(maxCount, size), zsChan);
                    position += count;
                    size -= count;
                }
            } catch (Throwable var17) {
                if (lock != null) {
                    try {
                        lock.close();
                    } catch (Throwable var16) {
                        var17.addSuppressed(var16);
                    }
                }

                throw var17;
            }

            if (lock != null) {
                lock.close();
            }
        } catch (Throwable var18) {
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (Throwable var15) {
                    var18.addSuppressed(var15);
                }
            }

            throw var18;
        }

        //noinspection ConstantValue
        if (fileChannel != null) {
            fileChannel.close();
        }
    }

    private static boolean isInvalidFileNameChar(char ch) {
        return Character.isISOControl(ch) || ILLEGAL_CHARACTERS.contains(ch);
    }

    public static boolean isValidPath(String path) {
        if (Strings.isNullOrEmpty(path)) {
            return false;
        } else {
            try {
                Paths.get(path);
                return true;
            } catch (InvalidPathException var2) {
                return false;
            }
        }
    }

    public static boolean isValidFileName(String name) {
        char[] var1 = name.toCharArray();
        int var2 = var1.length;

        for (char ch : var1) {
            if (isInvalidFileNameChar(ch)) {
                return false;
            }
        }

        return true;
    }

    public static String removeInvalidFileChars(String name, String replaceWith) {
        StringBuilder builder = new StringBuilder();
        char[] var3 = name.toCharArray();
        int var4 = var3.length;

        for (char ch : var3) {
            if (isInvalidFileNameChar(ch)) {
                builder.append(replaceWith);
            } else {
                builder.append(ch);
            }
        }

        return builder.toString();
    }

    public static String oneOfValidFileNames(String... names) {
        int var2 = names.length;

        for (String name : names) {
            if (isValidFileName(name)) {
                return name;
            }
        }

        throw new IllegalArgumentException("None of the file names are valid: " + Arrays.toString(names));
    }

    public static boolean isFolderEmpty(Path folder) {
        return countEntriesOf(folder) == 0;
    }

    public static void deleteFolder(Path folder) {
        deleteFolder(folder, Fn.alwaysFalse());
    }

    public static void deleteFolder(Path folder, Predicate<Path> ignore) {
        if (Files.exists(folder)) {
            try {
                AtomicBoolean errored = new AtomicBoolean();
                Files.list(folder).forEach((path) -> {
                    try {
                        if (folder.equals(path)) {
                            return;
                        }

                        if (ignore.test(path)) {
                            return;
                        }

                        if (Files.isDirectory(path)) {
                            deleteFolder(path);
                        } else {
                            Files.delete(path);
                        }
                    } catch (IOException var5) {
                        errored.set(true);
                        var5.printStackTrace();
                    }
                });
                if (!errored.get()) {
                    Files.delete(folder);
                }
            } catch (IOException var3) {
                throw new RuntimeException(var3);
            }
        }
    }

    public static void deleteAllFileTypes(Path folder, String type) {
        try {
            Files.list(folder).forEach((path) -> {
                try {
                    if (folder.equals(path)) {
                        return;
                    }

                    if (!path.toString().endsWith(type)) {
                        return;
                    }

                    if (Files.isDirectory(path)) {
                        return;
                    }

                    Files.delete(path);
                } catch (IOException var4) {
                    var4.printStackTrace();
                }
            });
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    public static List<Path> getFiles(Path folder) {
        List<Path> files = new ArrayList<>();
        PathIterator iterator = new PathIterator(null, (p, attrs) -> {
            if (attrs.isRegularFile()) {
                files.add(p);
            }
        });

        try {
            Files.walkFileTree(folder, iterator);
            return files;
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }
    }

    public static InputStream stringToInputStream(String string) {
        return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
    }

    public static void transfer(InputStream in, OutputStream out) throws IOException {
        Objects.requireNonNull(in, "in");
        Objects.requireNonNull(out, "out");
        byte[] buffer = new byte[8192];

        int read;
        while ((read = in.read(buffer, 0, 8192)) >= 0) {
            out.write(buffer, 0, read);
        }
    }

    public static void copyFolder(Path source, Path destination) {
        try {
            Files.walkFileTree(source, new CopyFileVisitor(source, destination));
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static void lockAndTransfer(Path file, OutputStream transferTo, IORunnable beforeTransfer) throws IOException {
        FileChannel fileChannel = FileChannel.open(file, StandardOpenOption.READ);

        try {
            FileLock lock = fileChannel.lock(0L, Long.MAX_VALUE, true);

            try {
                WritableByteChannel zsChan = Channels.newChannel(transferTo);
                long maxCount = 67076096L;
                long size = fileChannel.size();
                long position = 0L;
                beforeTransfer.run();

                while (size > 0L) {
                    long count = fileChannel.transferTo(position, Math.min(maxCount, size), zsChan);
                    position += count;
                    size -= count;
                }
            } catch (Throwable var16) {
                if (lock != null) {
                    try {
                        lock.close();
                    } catch (Throwable var15) {
                        var16.addSuppressed(var15);
                    }
                }

                throw var16;
            }

            if (lock != null) {
                lock.close();
            }
        } catch (Throwable var17) {
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (Throwable var14) {
                    var17.addSuppressed(var14);
                }
            }

            throw var17;
        }

        //noinspection ConstantValue
        if (fileChannel != null) {
            fileChannel.close();
        }
    }

    static {
        STD_WRITER = new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING};
        ILLEGAL_CHARACTERS = new HashSet<>(Arrays.asList('/', '\\', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '<', '>', '|', '"', ':', '\u0000', '"', '*', '<', '>', '?', '|'));
    }

    private static final class PathIterator extends SimpleFileVisitor<Path> {
        public final BiConsumer<Path, BasicFileAttributes> visitor;
        private final BiPredicate<Path, BasicFileAttributes> filter;

        private PathIterator(BiPredicate<Path, BasicFileAttributes> filter, BiConsumer<Path, BasicFileAttributes> consumer) {
            this.visitor = consumer;
            this.filter = filter;
        }

        private FileVisitResult visit(Path path, BasicFileAttributes attrs) {
            if (this.filter != null && !this.filter.test(path, attrs)) {
                return FileVisitResult.SKIP_SUBTREE;
            } else {
                this.visitor.accept(path, attrs);
                return FileVisitResult.CONTINUE;
            }
        }

        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            return this.visit(dir, attrs);
        }

        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            return this.visit(file, attrs);
        }
    }

    private static final class CopyFileVisitor extends SimpleFileVisitor<Path> {
        private final Path sourcePath;
        private final Path targetPath;

        public CopyFileVisitor(Path sourcePath, Path targetPath) {
            this.sourcePath = sourcePath;
            this.targetPath = targetPath;
        }

        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            Files.createDirectories(this.targetPath.resolve(this.sourcePath.relativize(dir)));
            return FileVisitResult.CONTINUE;
        }

        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.copy(file, this.targetPath.resolve(this.sourcePath.relativize(file)));
            return FileVisitResult.CONTINUE;
        }
    }
}
