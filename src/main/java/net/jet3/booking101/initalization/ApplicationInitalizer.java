package net.jet3.booking101.initalization;

import net.jet3.booking101.util.Util;
import net.jet3.booking101.ManagementYaar;

import java.io.File;

public class ApplicationInitalizer
{
    public static File installPath;
    public static File configFile;
    public static File dataFile;
    public static File logFile;
    public static File lang;
    public static File tempFile;

    public static void init() {
        installPath = new File(Util.getAppHome().getAbsolutePath());
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
        ManagementYaar.LAST_EDITED_PROJECT = Util.getString(configFile, "lastProject");
        ManagementYaar.WIDTH = Util.getInt(configFile, "width");
        ManagementYaar.HEIGHT = Util.getInt(configFile, "height");
        ManagementYaar.MAXIMIZED = Util.getBoolean(configFile, "maximized");
        ManagementYaar.DEVELOPER_MODE = Util.getBoolean(configFile, "developerMode");
    }
}
