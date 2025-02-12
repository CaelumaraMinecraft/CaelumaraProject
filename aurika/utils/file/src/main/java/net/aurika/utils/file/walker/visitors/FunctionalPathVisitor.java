package net.aurika.utils.file.walker.visitors;

import net.aurika.checker.Checker;
import org.jetbrains.annotations.NotNull;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class FunctionalPathVisitor implements PathVisitor {

    private final @NotNull Path root;
    private @NotNull List<ConditionalPathVisitor> visitors;

    public FunctionalPathVisitor(@NotNull Path root) {
        Checker.Arg.notNull(root, "root");
        this.root = root;
        this.visitors = new ArrayList<>();
    }

    public @NotNull Path getRoot() {
        return root;
    }

    public @NotNull List<ConditionalPathVisitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(@NotNull List<ConditionalPathVisitor> visitors) {
        Checker.Arg.notNull(visitors, "visitors");
        this.visitors = visitors;
    }

    private Predicate<Path> getPathPredicate(boolean folder, Path resolvablePath) {
        return folder ? new StartsWithPathFilter(resolvablePath) : new ExactPathFilter(resolvablePath);
    }

    private FunctionalPathVisitor stringVisitor(boolean folder, String resolvablePath, boolean visit) {
        Path exatPath = root;
        for (String it : resolvablePath.split("/")) {
            exatPath = root.resolve(it);
        }
        return visitor(folder, getPathPredicate(folder, exatPath), visit);
    }

    private FunctionalPathVisitor pathVisitor(boolean folder, Path path, boolean visit) {
        if (!path.startsWith(root)) {
            throw new IllegalArgumentException("Given path '" + path + "' isn't included in the root path '" + this.root + '\'');
        } else {
            return this.visitor(folder, this.getPathPredicate(folder, path), visit);
        }
    }

    private FunctionalPathVisitor visitor(boolean folder, Predicate<Path> filter, boolean visit) {
        ConditionalPathVisitor handle = new ConditionalPathVisitor(filter, visit ? VisitAll.INSTANCE : SkipAll.INSTANCE);
        visitors.add(handle);
        return this;
    }

    public @NotNull FunctionalPathVisitor onlyIf(boolean condition, @NotNull Consumer<FunctionalPathVisitor> handler) {
        Checker.Arg.notNull(handler, "handler");
        if (condition) {
            handler.accept(this);
        }
        return this;
    }

    public @NotNull FunctionalPathVisitor visitFiles(@NotNull Predicate<Path> filter) {
        Checker.Arg.notNull(filter, "filter");
        return this.visitor(false, filter, true);
    }

    public @NotNull FunctionalPathVisitor skipFiles(@NotNull Predicate<Path> filter) {
        Checker.Arg.notNull(filter, "filter");
        return this.visitor(false, filter, true);
    }

    public @NotNull FunctionalPathVisitor visitFolders(@NotNull Predicate<Path> filter) {
        Checker.Arg.notNull(filter, "filter");
        return this.visitor(true, filter, true);
    }

    public @NotNull FunctionalPathVisitor skipFolders(@NotNull Predicate<Path> filter) {
        Checker.Arg.notNull(filter, "filter");
        return this.visitor(true, filter, true);
    }

    public @NotNull FunctionalPathVisitor visitFile(@NotNull String resolvablePath) {
        Checker.Arg.notNull(resolvablePath, "resolvablePath");
        return this.stringVisitor(false, resolvablePath, true);
    }

    public @NotNull FunctionalPathVisitor skipFile(@NotNull String resolvablePath) {
        Checker.Arg.notNull(resolvablePath, "resolvablePath");
        return this.stringVisitor(false, resolvablePath, false);
    }

    public @NotNull FunctionalPathVisitor visitFolder(@NotNull String resolvablePath) {
        Checker.Arg.notNull(resolvablePath, "resolvablePath");
        return this.stringVisitor(true, resolvablePath, true);
    }

    public @NotNull FunctionalPathVisitor skipFolder(@NotNull String resolvablePath) {
        Checker.Arg.notNull(resolvablePath, "resolvablePath");
        return this.stringVisitor(true, resolvablePath, false);
    }

    public @NotNull FunctionalPathVisitor visitFile(@NotNull Path path) {
        Checker.Arg.notNull(path, "path");
        return this.pathVisitor(false, path, true);
    }

    public @NotNull FunctionalPathVisitor skipFile(@NotNull Path path) {
        Checker.Arg.notNull(path, "path");
        return this.pathVisitor(false, path, false);
    }

    public @NotNull FunctionalPathVisitor visitFolder(@NotNull Path path) {
        Checker.Arg.notNull(path, "path");
        return this.pathVisitor(true, path, true);
    }

    public @NotNull FunctionalPathVisitor skipFolder(@NotNull Path path) {
        Checker.Arg.notNull(path, "path");
        return this.pathVisitor(true, path, false);
    }

    public @NotNull FileVisitResult onVisit(@NotNull PathVisit visit) {
        Checker.Arg.notNull(visit, "visit");
        if (Objects.equals(visit.path(), this.root)) {
            return FileVisitResult.CONTINUE;
        } else {
            for (ConditionalPathVisitor pathVisitHandle : this.visitors) {
                if (pathVisitHandle.getPredicate().test(visit.path())) {
                    return pathVisitHandle.getVisitor().onVisit(visit);
                }
            }

            return FileVisitResult.SKIP_SUBTREE;
        }
    }
}
