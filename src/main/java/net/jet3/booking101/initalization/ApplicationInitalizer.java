package net.jet3.booking101.initalization;

import com.sun.corba.se.spi.orbutil.threadpool.Work;
import net.jet3.booking101.data.Workspace;
import net.jet3.booking101.object.Property;
import net.jet3.booking101.util.Util;
import net.jet3.booking101.ManagementYaar;

import java.io.File;
import java.util.List;

public class ApplicationInitalizer
{
    public static File installPath;
    public static File configFile;
    public static File dataFile;
    public static File logFile;
    public static File lang;
    public static File tempFile;
    public static File workspacesFolder;

    public static void init() {
        installPath = new File(Util.getAppHome().getAbsolutePath());
        configFile = new File(installPath, "config");
        dataFile = new File(installPath, "data");
        logFile = new File(installPath, "log");
        tempFile = new File(installPath, "temp");
        lang = new File(installPath, "lang.json");
        workspacesFolder = new File(installPath, "workspaces");

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
        if (!workspacesFolder.exists()) {
            workspacesFolder.mkdirs();
        }
        ManagementYaar.WORKSPACE = Workspace.getWorkspace(Util.getString(configFile, "lastProject"));
        ManagementYaar.WIDTH = Util.getInt(configFile, "width");
        ManagementYaar.HEIGHT = Util.getInt(configFile, "height");
        ManagementYaar.MAXIMIZED = Util.getBoolean(configFile, "maximized");
        ManagementYaar.DEVELOPER_MODE = Util.getBoolean(configFile, "developerMode");
        ManagementYaar.RECENTS = (List<String>) Util.get(configFile, "recents");

        for (Property property : Property.getAllActions()) {
            if (property.getWorkspace().equals(ManagementYaar.WORKSPACE.name)) {
                ManagementYaar.WORKSPACE.properties.add(property);
            }
        }
    }

    public static void updateWorkspace() {
        for (Property property : Property.getAllActions()) {
            if (property.getWorkspace().equals(ManagementYaar.WORKSPACE.name)) {
                ManagementYaar.WORKSPACE.properties.add(property);
            }
        }
    }
}
