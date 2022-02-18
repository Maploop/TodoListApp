package net.jet3.booking101.util;

import net.jet3.booking101.ManagementYaar;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class Util
{
    public static File getAppHome() {
        File appdata = new File(System.getProperty("user.home") + "/Appdata/Roaming/Jet3/Management Yaar");
        if (!appdata.exists()) {
            appdata.mkdirs();
        }
        return appdata;
    }

    public static Object get(File file, String key) {
        try {
            FileReader reader = new FileReader(file);
            JSONObject object = (JSONObject) new JSONParser().parse(reader);
            return object.get(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getString(File file, String key) {
        return get(file, key).toString();
    }

    public static int getInt(File file, String key) {
        return Integer.parseInt(getString(file, key));
    }

    public static boolean getBoolean(File file, String key) {
        return Boolean.parseBoolean(getString(file, key));
    }

    public static void set(File file, String key, Object value) throws Exception {
        FileReader reader = new FileReader(file);
        JSONObject object = (JSONObject) new JSONParser().parse(reader);

        object.put(key, value);

        FileWriter writer = new FileWriter(file);
        writer.write(object.toJSONString());
        writer.flush();
        writer.close();
    }

    static public String exportResource(String resource, File file) throws IOException {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        try {
            stream = ManagementYaar.class.getResourceAsStream(resource);
            if(stream == null) {
                throw new IllegalStateException("Failed to get " + resource + "!");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            resStreamOut = new FileOutputStream(file.toString());
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                assert resStreamOut != null;
                stream.close();
                resStreamOut.close();
            } catch (Exception ignored) { }
        }

        return file.toString();
    }

    public static String prettify(String old) {
        if (old.endsWith("1"))
            return old + "st";
        else if (old.endsWith("2"))
            return old + "nd";
        else if (old.endsWith("3"))
            return old + "rd";
        else
            return old + "th";
    }

    public static void runAsync(Runnable run) {
        Thread thread = new Thread(run);
        thread.start();
    }

    public static void delay(Runnable run, long duration) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                run.run();
            }
        }, duration);
    }
}
