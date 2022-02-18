package net.jet3.booking101.util;

import java.text.SimpleDateFormat;

public class Log
{

    private static void log(String lvl, Object msg) {
        String format = "[%s] %s > %s";
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());

        switch (lvl.toLowerCase()) {
            case "info":
                System.out.printf((format) + "%n", date, "Information", msg);
                break;
            case "warning":
                System.out.printf((format) + "%n", date, "Warning", msg);
                break;
            case "severe":
                System.err.printf((format) + "%n", date, "Error", msg);
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
