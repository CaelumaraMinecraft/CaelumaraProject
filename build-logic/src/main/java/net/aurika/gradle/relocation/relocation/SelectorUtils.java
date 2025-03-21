package net.aurika.gradle.relocation.relocation;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

final class SelectorUtils {
    private static final String PATTERN_HANDLER_PREFIX = "[";
    private static final String PATTERN_HANDLER_SUFFIX = "]";
    private static final String REGEX_HANDLER_PREFIX = "%regex[";
    private static final String ANT_HANDLER_PREFIX = "%ant[";

    private static boolean isAntPrefixedPattern(@NotNull String pattern) {
        return pattern.length() > ANT_HANDLER_PREFIX.length() + PATTERN_HANDLER_SUFFIX.length() + 1 && pattern.startsWith(ANT_HANDLER_PREFIX) && pattern.endsWith(PATTERN_HANDLER_SUFFIX);
    }

    private static boolean separatorPatternStartSlashMismatch(@NotNull String pattern, @NotNull String str, String separator) {
        return str.startsWith(separator) != pattern.startsWith(separator);
    }

    public static boolean matchPath(String pattern, String str, boolean isCaseSensitive) {
        return matchPath(pattern, str, File.separator, isCaseSensitive);
    }

    private static boolean matchPath(String pattern, String str, String separator, boolean isCaseSensitive) {
        if (isRegexPrefixedPattern(pattern)) {
            pattern = pattern.substring(REGEX_HANDLER_PREFIX.length(), pattern.length() - PATTERN_HANDLER_SUFFIX.length());
            return str.matches(pattern);
        } else {
            if (isAntPrefixedPattern(pattern)) {
                pattern = pattern.substring(ANT_HANDLER_PREFIX.length(), pattern.length() - PATTERN_HANDLER_SUFFIX.length());
            }

            return matchAntPathPattern(pattern, str, separator, isCaseSensitive);
        }
    }

    private static boolean isRegexPrefixedPattern(@NotNull String pattern) {
        return pattern.length() > REGEX_HANDLER_PREFIX.length() + PATTERN_HANDLER_SUFFIX.length() + 1 && pattern.startsWith(REGEX_HANDLER_PREFIX) && pattern.endsWith(PATTERN_HANDLER_SUFFIX);
    }

    private static boolean matchAntPathPattern(String pattern, String str, String separator, boolean isCaseSensitive) {
        if (separatorPatternStartSlashMismatch(pattern, str, separator)) {
            return false;
        } else {
            String[] patDirs = tokenizePathToString(pattern, separator);
            String[] strDirs = tokenizePathToString(str, separator);
            return matchAntPathPattern(patDirs, strDirs, isCaseSensitive);
        }
    }

    private static boolean matchAntPathPattern(String @NotNull [] patDirs, String @NotNull [] strDirs, boolean isCaseSensitive) {
        int patIdxStart = 0;
        int patIdxEnd = patDirs.length - 1;
        int strIdxStart = 0;

        int strIdxEnd;
        for (strIdxEnd = strDirs.length - 1; patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd; ++strIdxStart) {
            String patDir = patDirs[patIdxStart];
            if (patDir.equals("**")) {
                break;
            }

            if (!match(patDir, strDirs[strIdxStart], isCaseSensitive)) {
                return false;
            }

            ++patIdxStart;
        }

        if (strIdxStart > strIdxEnd) {
            for (int i = patIdxStart; i <= patIdxEnd; ++i) {
                if (!patDirs[i].equals("**")) {
                    return false;
                }
            }

            return true;
        } else if (patIdxStart > patIdxEnd) {
            return false;
        } else {
            while (patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd) {
                String patDir = patDirs[patIdxEnd];
                if (patDir.equals("**")) {
                    break;
                }

                if (!match(patDir, strDirs[strIdxEnd], isCaseSensitive)) {
                    return false;
                }

                --patIdxEnd;
                --strIdxEnd;
            }

            if (strIdxStart > strIdxEnd) {
                for (int i = patIdxStart; i <= patIdxEnd; ++i) {
                    if (!patDirs[i].equals("**")) {
                        return false;
                    }
                }

                return true;
            } else {
                while (patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
                    int patIdxTmp = -1;

                    for (int i = patIdxStart + 1; i <= patIdxEnd; ++i) {
                        if (patDirs[i].equals("**")) {
                            patIdxTmp = i;
                            break;
                        }
                    }

                    if (patIdxTmp == patIdxStart + 1) {
                        ++patIdxStart;
                    } else {
                        int patLength = patIdxTmp - patIdxStart - 1;
                        int strLength = strIdxEnd - strIdxStart + 1;
                        int foundIdx = -1;
                        int i = 0;

                        label106:
                        while (i <= strLength - patLength) {
                            for (int j = 0; j < patLength; ++j) {
                                String subPat = patDirs[patIdxStart + j + 1];
                                String subStr = strDirs[strIdxStart + i + j];
                                if (!match(subPat, subStr, isCaseSensitive)) {
                                    ++i;
                                    continue label106;
                                }
                            }

                            foundIdx = strIdxStart + i;
                            break;
                        }

                        if (foundIdx == -1) {
                            return false;
                        }

                        patIdxStart = patIdxTmp;
                        strIdxStart = foundIdx + patLength;
                    }
                }

                for (int i = patIdxStart; i <= patIdxEnd; ++i) {
                    if (!patDirs[i].equals("**")) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    private static boolean match(@NotNull String pattern, @NotNull String str, boolean isCaseSensitive) {
        char[] patArr = pattern.toCharArray();
        char[] strArr = str.toCharArray();
        return match(patArr, strArr, isCaseSensitive);
    }

    private static boolean match(char @NotNull [] patArr, char @NotNull [] strArr, boolean isCaseSensitive) {
        int patIdxStart = 0;
        int patIdxEnd = patArr.length - 1;
        int strIdxStart = 0;
        int strIdxEnd = strArr.length - 1;
        boolean containsStar = false;

        for (char aPatArr : patArr) {
            if (aPatArr == '*') {
                containsStar = true;
                break;
            }
        }

        if (!containsStar) {
            if (patIdxEnd != strIdxEnd) {
                return false;
            } else {
                for (int i = 0; i <= patIdxEnd; ++i) {
                    char ch = patArr[i];
                    if (ch != '?' && !equals(ch, strArr[i], isCaseSensitive)) {
                        return false;
                    }
                }

                return true;
            }
        } else if (patIdxEnd == 0) {
            return true;
        } else {
            char ch;
            while ((ch = patArr[patIdxStart]) != '*' && strIdxStart <= strIdxEnd) {
                if (ch != '?' && !equals(ch, strArr[strIdxStart], isCaseSensitive)) {
                    return false;
                }

                ++patIdxStart;
                ++strIdxStart;
            }

            if (strIdxStart > strIdxEnd) {
                for (int i = patIdxStart; i <= patIdxEnd; ++i) {
                    if (patArr[i] != '*') {
                        return false;
                    }
                }

                return true;
            } else {
                while ((ch = patArr[patIdxEnd]) != '*' && strIdxStart <= strIdxEnd) {
                    if (ch != '?' && !equals(ch, strArr[strIdxEnd], isCaseSensitive)) {
                        return false;
                    }

                    --patIdxEnd;
                    --strIdxEnd;
                }

                if (strIdxStart > strIdxEnd) {
                    for (int i = patIdxStart; i <= patIdxEnd; ++i) {
                        if (patArr[i] != '*') {
                            return false;
                        }
                    }

                    return true;
                } else {
                    while (patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
                        int patIdxTmp = -1;

                        for (int i = patIdxStart + 1; i <= patIdxEnd; ++i) {
                            if (patArr[i] == '*') {
                                patIdxTmp = i;
                                break;
                            }
                        }

                        if (patIdxTmp == patIdxStart + 1) {
                            ++patIdxStart;
                        } else {
                            int patLength = patIdxTmp - patIdxStart - 1;
                            int strLength = strIdxEnd - strIdxStart + 1;
                            int foundIdx = -1;
                            int i = 0;

                            label132:
                            while (i <= strLength - patLength) {
                                for (int j = 0; j < patLength; ++j) {
                                    ch = patArr[patIdxStart + j + 1];
                                    if (ch != '?' && !equals(ch, strArr[strIdxStart + i + j], isCaseSensitive)) {
                                        ++i;
                                        continue label132;
                                    }
                                }

                                foundIdx = strIdxStart + i;
                                break;
                            }

                            if (foundIdx == -1) {
                                return false;
                            }

                            patIdxStart = patIdxTmp;
                            strIdxStart = foundIdx + patLength;
                        }
                    }

                    for (int i = patIdxStart; i <= patIdxEnd; ++i) {
                        if (patArr[i] != '*') {
                            return false;
                        }
                    }

                    return true;
                }
            }
        }
    }

    private static boolean equals(char c1, char c2, boolean isCaseSensitive) {
        if (c1 == c2) {
            return true;
        } else {
            return !isCaseSensitive && (Character.toUpperCase(c1) == Character.toUpperCase(c2) || Character.toLowerCase(c1) == Character.toLowerCase(c2));
        }
    }

    private static String @NotNull [] tokenizePathToString(String path, String separator) {
        List<String> ret = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(path, separator);

        while (st.hasMoreTokens()) {
            ret.add(st.nextToken());
        }

        return ret.toArray(new String[ret.size()]);
    }

    private SelectorUtils() {
    }
}
