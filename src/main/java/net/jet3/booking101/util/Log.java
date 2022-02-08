package net.jet3.booking101.util;

import java.util.logging.Logger;

public class Log extends Logger
{
    protected Log() {
        super("net.jet3.booking101", "Booking101");
    }

    private static void log(String lvl, Object msg) {
        switch (lvl.toLowerCase()) {
            case "info":
                Log.getLogger("net.jet3.booking101").info(msg.toString());
            case "warning":
                Log.getLogger("net.jet3.booking101").warning(msg.toString());
            case "severe":
                Log.getLogger("net.jet3.booking101").severe(msg.toString());
        }
    }

    public static void INFO(Object msg) {
        log("info", msg);
    }

    public static void warning(Object msg) {
        log("warning", msg);
    }

    public static void severe(Object msg) {
        log("severe", msg);
    }

    public static void error(Object msg) {
        log("severe", msg);
    }
}
