package fishy.support.util;

import android.app.Application;
import android.util.Log;

import fishy.support.BuildConfig;


/**
 * <strong>Created</strong> by Fishy on 2017/04/07.
 * <p><strong>Edited</strong> by Fishy on 2017/04/10</p>
 * 自定义的log，只会在debug模式下输出
 * 同时可以自定义开/关输出
 */
@Deprecated
public class SmartLog {
    /**
     * 备注参考{@link Log#ASSERT}
     */
    public static final int ASSERT = Log.ASSERT;
    /**
     * 备注参考{@link Log#DEBUG}
     */
    public static final int DEBUG = Log.DEBUG;
    /**
     * 备注参考{@link Log#ERROR}
     */
    public static final int ERROR = Log.ERROR;
    /**
     * 备注参考{@link Log#INFO}
     */
    public static final int INFO = Log.INFO;
    /**
     * 备注参考{@link Log#VERBOSE}
     */
    public static final int VERBOSE = Log.VERBOSE;
    /**
     * 备注参考{@link Log#WARN}
     */
    public static final int WARN = Log.WARN;

    /**
     * 是否开启log
     */
    private static boolean isEnabled = true;

    /**
     * 开/关Log
     * 推荐在{@link Application#onCreate()}中执行，全局化
     * @param isEnable
     *
     */
    public static void enableLog(boolean isEnable) {
        isEnabled = isEnable;
    }

    /**
     * 得到当前log的开/关状态
     *
     * @return
     */
    public static boolean getEnabledState() {
        return isEnabled;
    }

    /**
     * 备注参考{@link Log#i(String, String)}
     */
    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG && isEnabled) {
            Log.i(tag, msg);
        }
    }

    /**
     * 备注参考{@link Log#i(String, String, Throwable)}
     */
    public static void i(String tag, String msg, Throwable throwable) {
        if (BuildConfig.DEBUG && isEnabled) {
            Log.i(tag, msg, throwable);
        }
    }

    /**
     * 备注参考{@link Log#d(String, String)}
     */
    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG && isEnabled) {
            Log.d(tag, msg);
        }
    }

    /**
     * 备注参考{@link Log#d(String, String, Throwable)}
     */
    public static void d(String tag, String msg, Throwable throwable) {
        if (BuildConfig.DEBUG && isEnabled) {
            Log.d(tag, msg, throwable);
        }
    }

    /**
     * 备注参考{@link Log#e(String, String)}
     */
    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG && isEnabled) {
            Log.e(tag, msg);
        }
    }

    /**
     * 备注参考{@link Log#e(String, String, Throwable)}
     */
    public static void e(String tag, String msg, Throwable throwable) {
        if (BuildConfig.DEBUG && isEnabled) {
            Log.e(tag, msg, throwable);
        }
    }

    /**
     * 备注参考{@link Log#v(String, String)}
     */
    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG && isEnabled) {
            Log.v(tag, msg);
        }
    }

    /**
     * 备注参考{@link Log#v(String, String, Throwable)}
     */
    public static void v(String tag, String msg, Throwable throwable) {
        if (BuildConfig.DEBUG && isEnabled) {
            Log.v(tag, msg, throwable);
        }
    }

    /**
     * 备注参考{@link Log#w(String, String)}
     */
    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG && isEnabled) {
            Log.w(tag, msg);
        }
    }

    /**
     * 备注参考{@link Log#w(String, String, Throwable)}
     */
    public static void w(String tag, String msg, Throwable throwable) {
        if (BuildConfig.DEBUG && isEnabled) {
            Log.w(tag, msg, throwable);
        }
    }

    /**
     * 备注参考{@link Log#w(String, Throwable)}
     */
    public static void w(String tag, Throwable throwable) {
        if (BuildConfig.DEBUG && isEnabled) {
            Log.w(tag, throwable);
        }
    }

    /**
     * 备注参考{@link Log#wtf(String, String)}
     */
    public static void wtf(String tag, String msg) {
        if (BuildConfig.DEBUG && isEnabled) {
            Log.wtf(tag, msg);
        }
    }

    /**
     * 备注参考{@link Log#wtf(String, String, Throwable)}
     */
    public static void wtf(String tag, String msg, Throwable throwable) {
        if (BuildConfig.DEBUG && isEnabled) {
            Log.wtf(tag, msg, throwable);
        }
    }

    /**
     * 备注参考{@link Log#wtf(String, Throwable)}
     */
    public static void wtf(String tag, Throwable throwable) {
        if (BuildConfig.DEBUG && isEnabled) {
            Log.wtf(tag, throwable);
        }
    }

    /**
     * 备注参考{@link Log#getStackTraceString(Throwable)}
     */
    public static void getStackTraceString(Throwable throwable) {
        if (BuildConfig.DEBUG && isEnabled) {
            Log.getStackTraceString(throwable);
        }
    }

    /**
     * 备注参考{@link Log#isLoggable(String, int)}
     */
    public static void isLoggable(String tag, int level) {
        if (BuildConfig.DEBUG && isEnabled) {
            Log.isLoggable(tag, level);
        }
    }

    /**
     * 备注参考{@link Log#println(int, String, String)}
     */
    public static void println(int priority, String tag, String msg) {
        if (BuildConfig.DEBUG && isEnabled) {
            Log.println(priority, tag, msg);
        }
    }
}
