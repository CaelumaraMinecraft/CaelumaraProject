//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package top.auspice.dependencies.relocation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

final class SelectorUtils {
    private static final String PATTERN_HANDLER_PREFIX = "[";
    private static final String PATTERN_HANDLER_SUFFIX = "]";
    private static final String REGEX_HANDLER_PREFIX = "%regex[";
    private static final String ANT_HANDLER_PREFIX = "%ant[";

    private static boolean isAntPrefixedPattern(String pattern) {
        return pattern.length() > "%ant[".length() + "]".length() + 1 && pattern.startsWith("%ant[") && pattern.endsWith("]");
    }

    private static boolean separatorPatternStartSlashMismatch(String pattern, String str, String separator) {
        return str.startsWith(separator) != pattern.startsWith(separator);
    }

    public static boolean matchPath(String pattern, String str, boolean isCaseSensitive) {
        return matchPath(pattern, str, File.separator, isCaseSensitive);
    }

    private static boolean matchPath(String pattern, String str, String separator, boolean isCaseSensitive) {
        if (isRegexPrefixedPattern(pattern)) {
            pattern = pattern.substring("%regex[".length(), pattern.length() - "]".length());
            return str.matches(pattern);
        } else {
            if (isAntPrefixedPattern(pattern)) {
                pattern = pattern.substring("%ant[".length(), pattern.length() - "]".length());
            }

            return matchAntPathPattern(pattern, str, separator, isCaseSensitive);
        }
    }

    private static boolean isRegexPrefixedPattern(String pattern) {
        return pattern.length() > "%regex[".length() + "]".length() + 1 && pattern.startsWith("%regex[") && pattern.endsWith("]");
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

    private static boolean matchAntPathPattern(String[] patDirs, String[] strDirs, boolean isCaseSensitive) {
        int patIdxStart = 0;
        int patIdxEnd = patDirs.length - 1;
        int strIdxStart = 0;

        int strIdxEnd;
        String patDir;
        for (strIdxEnd = strDirs.length - 1; patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd; ++strIdxStart) {
            patDir = patDirs[patIdxStart];
            if (patDir.equals("**")) {
                break;
            }

            if (!match(patDir, strDirs[strIdxStart], isCaseSensitive)) {
                return false;
            }

            ++patIdxStart;
        }

        int patIdxTmp;
        if (strIdxStart > strIdxEnd) {
            for (patIdxTmp = patIdxStart; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
                if (!patDirs[patIdxTmp].equals("**")) {
                    return false;
                }
            }

            return true;
        } else if (patIdxStart > patIdxEnd) {
            return false;
        } else {
            while (patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd) {
                patDir = patDirs[patIdxEnd];
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
                for (patIdxTmp = patIdxStart; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
                    if (!patDirs[patIdxTmp].equals("**")) {
                        return false;
                    }
                }

                return true;
            } else {
                while (patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
                    patIdxTmp = -1;

                    int patLength;
                    for (patLength = patIdxStart + 1; patLength <= patIdxEnd; ++patLength) {
                        if (patDirs[patLength].equals("**")) {
                            patIdxTmp = patLength;
                            break;
                        }
                    }

                    if (patIdxTmp == patIdxStart + 1) {
                        ++patIdxStart;
                    } else {
                        patLength = patIdxTmp - patIdxStart - 1;
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

                for (patIdxTmp = patIdxStart; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
                    if (!patDirs[patIdxTmp].equals("**")) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    private static boolean match(String pattern, String str, boolean isCaseSensitive) {
        char[] patArr = pattern.toCharArray();
        char[] strArr = str.toCharArray();
        return match(patArr, strArr, isCaseSensitive);
    }

    private static boolean match(char[] patArr, char[] strArr, boolean isCaseSensitive) {
        int patIdxStart = 0;
        int patIdxEnd = patArr.length - 1;
        int strIdxStart = 0;
        int strIdxEnd = strArr.length - 1;
        boolean containsStar = false;
        int patLength = patArr.length;

        int strLength;
        int foundIdx;
        for (strLength = 0; strLength < patLength; ++strLength) {
            foundIdx = patArr[strLength];
            if (foundIdx == 42) {
                containsStar = true;
                break;
            }
        }

        char ch;
        int patIdxTmp;
        if (!containsStar) {
            if (patIdxEnd != strIdxEnd) {
                return false;
            } else {
                for (patIdxTmp = 0; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
                    ch = patArr[patIdxTmp];
                    if (ch != '?' && !equals(ch, strArr[patIdxTmp], isCaseSensitive)) {
                        return false;
                    }
                }

                return true;
            }
        } else if (patIdxEnd == 0) {
            return true;
        } else {
            while ((ch = patArr[patIdxStart]) != '*' && strIdxStart <= strIdxEnd) {
                if (ch != '?' && !equals(ch, strArr[strIdxStart], isCaseSensitive)) {
                    return false;
                }

                ++patIdxStart;
                ++strIdxStart;
            }

            if (strIdxStart > strIdxEnd) {
                for (patIdxTmp = patIdxStart; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
                    if (patArr[patIdxTmp] != '*') {
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
                    for (patIdxTmp = patIdxStart; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
                        if (patArr[patIdxTmp] != '*') {
                            return false;
                        }
                    }

                    return true;
                } else {
                    while (patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
                        patIdxTmp = -1;

                        for (patLength = patIdxStart + 1; patLength <= patIdxEnd; ++patLength) {
                            if (patArr[patLength] == '*') {
                                patIdxTmp = patLength;
                                break;
                            }
                        }

                        if (patIdxTmp == patIdxStart + 1) {
                            ++patIdxStart;
                        } else {
                            patLength = patIdxTmp - patIdxStart - 1;
                            strLength = strIdxEnd - strIdxStart + 1;
                            foundIdx = -1;
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

                    for (patIdxTmp = patIdxStart; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
                        if (patArr[patIdxTmp] != '*') {
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

    private static String[] tokenizePathToString(String path, String separator) {
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
