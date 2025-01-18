package top.auspice.api.annotations.bookmark;

/**
 * Used only for {@link Bookmark}.
 */
public enum BookmarkType {
    /**
     * A {@code TODO} that is expected to be handled
     * in the near future. Another alternative is {@code AuspiceLogger.todo()}
     * which is kept during compilation of course.
     */
    REMINDER,

    /**
     * Indicates that the code is kept for archive and study purposes
     * and should not be used within the project.
     */
    ARCHIVE,

    /**
     * 代表代码将要在不久之后完成.
     * 对于更长时间的用 {@link #REMINDER}.
     */
    UNFINISHED,

    /**
     * Having doubts about whether the code, usually a special fix, is actually fixing
     * something or working as intended or not.
     */
    QUESTIONABLE,

    /**
     * 脏乱差代码, 需要重新整理它的结构.
     */
    CLEANUP,

    /**
     * 表面代码是较新的实验性代码, 且在将来会有很多变化.
     */
    EXPERIMENTAL,

    /**
     * 代表代码可能会造成糟糕的性能问题.
     */
    PERFORMANCE,

    /**
     * Indicates that the code is considered dangerous and care
     * should be taken when modifying it, or it should be entirely
     * replaced by a safer code in the future.
     * It can also indicate that this object is not safe to access
     * for normal usages.
     */
    UNSAFE,

    /**
     * 待开发的代码, 它的特性可能会被服务器内的玩家滥用.
     * <p>
     * A special type of unsafe code which has the potential to be
     * abused by players in the server.
     */
    EXPLOITABLE;
}
