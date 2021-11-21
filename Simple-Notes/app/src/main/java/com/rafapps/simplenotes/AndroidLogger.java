package com.rafapps.simplenotes;

import android.util.Log;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class AndroidLogger extends Handler {
    public static void reset(Handler rootHandler) {
        Logger rootLogger = LogManager.getLogManager().getLogger("");

    }

    @Override
    public void publish(LogRecord logRecord) {
        if(!super.isLoggable(logRecord)) {
            return;
        }

        String name = logRecord.getLoggerName();
        int maxLength = 30;
        String tag = name.length() > maxLength ? name.substring(name.length() - maxLength) : name;

        //try-catch block
        try {
            int level = getAndroidLevelVersion(logRecord.getLevel());
            Log.println(level, tag, logRecord.getMessage());
            if(logRecord.getThrown() != null) {
                Log.println(level, tag, Log.getStackTraceString(logRecord.getThrown()));
            }
        } catch(RuntimeException ex) {
            Log.e("AndroidloggingHandler: ","Err logging message: " ,ex);
        }


    }

    static int getAndroidLevelVersion(Level level) {
        int value = level.intValue();
        if(value >= Level.SEVERE.intValue()) {
            return Log.ERROR;
        } else if (value >= Level.WARNING.intValue()) {
            return Log.WARN;
        } else if(value >= Level.INFO.intValue()) {
            return Log.INFO;
        } else {
            return Log.DEBUG;
        }
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
