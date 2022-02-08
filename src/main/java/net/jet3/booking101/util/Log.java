package net.jet3.booking101.util;

import java.util.logging.Logger;

public class Log
{

    private static void log(String lvl, Object msg) {
        switch (lvl.toLowerCase()) {
            case "info":
                System.out.println("[INFO] " + msg);
                break;
            case "warning":
                System.out.println("[WARNING] " + msg);
                break;
            case "severe":
                System.err.println("[SEVERE] " + msg);
                break;
        }
    }

    public static void info(Object msg) {
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
