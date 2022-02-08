package net.jet3.booking101.initalization;

import net.jet3.booking101.Main;
import net.jet3.booking101.util.Log;
import net.jet3.booking101.util.Util;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ApplicationInitalizer
{
    public static File installPath;
    public static File configFile;
    public static File dataFile;
    public static File logFile;
    public static File lang;
    public static File tempFile;

    public static void init() {
        installPath = new File(Util.getAppdata().getAbsolutePath());
        configFile = new File(installPath, "config");
        dataFile = new File(installPath, "data");
        logFile = new File(installPath, "log");
        tempFile = new File(installPath, "temp");
        lang = new File(installPath, "lang.json");

        if (!installPath.exists()) {
            installPath.mkdirs();
        }
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                Util.exportResource("/files/config.json", configFile);
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        }
        if (!lang.exists()) {
            try {
                configFile.createNewFile();
                Util.exportResource("/files/lang.json", lang);
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        }
        Main.LAST_EDITED_PROJECT = Util.getString(configFile, "lastProject");
        Main.WIDTH = Util.getInt(configFile, "width");
        Main.HEIGHT = Util.getInt(configFile, "height");
        Main.MAXIMIZED = Util.getBoolean(configFile, "maximized");
    }
}
