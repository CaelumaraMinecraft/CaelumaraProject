package top.auspice.logging;

import top.auspice.logging.debug.Debug;

public interface AuspiceLogger {

    void info(String message);
    void debug(Debug debug, String message);
    void error(String message);
    void error(String message, Throwable e);
    void warn(String message);
    void warn(String message, Throwable e);

}
