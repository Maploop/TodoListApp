package net.jet3.booking101.sql;

import net.jet3.booking101.util.Util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDatabase
{
    private static final String DATABASE_FILENAME = "data.db";

    private Connection connection;
    private final File file;

    public SQLDatabase() {
        File file = new File(Util.getAppHome() + "/data", DATABASE_FILENAME);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                Util.exportResource("files/data.db", file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        this.file = file;
    }

    public Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
            if (connection != null) {
                connection.prepareStatement("CREATE TABLE IF NOT EXISTS `actions` (\n" +
                        "\t`id` INT,\n" +
                        "\t`title` TEXT,\n" +
                        "\t`type` TEXT,\n" +
                        "\t`dateToExecute` LONG,\n" +
                        "\t`notify` BOOLEAN,\n" +
                        "\t`description` TEXT\n" +
                        ");").execute();
                return connection;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
