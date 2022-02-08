package net.jet3.booking101.sql;

import net.jet3.booking101.ManagementYaar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLActionsData
{
    private final static ManagementYaar main = ManagementYaar.getInstance();

    private final String SELECT = "SELECT * FROM `actions` WHERE `title`=?";
    private final String SELECT_ID = "SELECT * FROM `actions` WHERE `id`=?";
    private final String INSERT = "INSERT INTO `actions` (`id`, `title`, `type`, `dateToExecute`, `notify`, `description`) VALUES (?, ?, ?, ?, ?, ?)";
    private final String COUNT = "SELECT COUNT(*) AS rows FROM `actions`";
    private final String UPDATE = "UPDATE `actions` SET `%s`=? WHERE `id`=?";
    private final String DELETE = "DELETE FROM `actions` WHERE `id`=?";

    public boolean exists(int id) {
        try (Connection connection = main.sql.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ID);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public int getIdFromTitle(String title) {
        try (Connection connection = main.sql.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT);
            statement.setString(1, title);
            ResultSet result = statement.executeQuery();
            result.next();
            int i = result.getInt("id");
            result.close();
            return i;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public String getTitle(int id) {
        try (Connection connection = main.sql.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ID);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            result.next();
            String s = result.getString("title");
            result.close();
            return s;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getType(int id) {
        try (Connection connection = main.sql.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ID);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            result.next();
            String s = result.getString("type");
            result.close();
            return s;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public long getDateToExecute(int id) {
        try (Connection connection = main.sql.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ID);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            result.next();
            long s = result.getLong("dateToExecute");
            result.close();
            return s;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public boolean getNotify(int id) {
        try (Connection connection = main.sql.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ID);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            result.next();
            boolean s = result.getBoolean("notify");
            result.close();
            return s;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public String getDescription(int id) {
        try (Connection connection = main.sql.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ID);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            result.next();
            String s = result.getString("description");
            result.close();
            return s;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void set(int id, String key, Object value) {
        try (Connection connection = main.sql.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(String.format(UPDATE, key));
            statement.setObject(1, value);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void create(int id, String title, String type, long dateToExecute, boolean notify, String desc) {
        try (Connection connection = main.sql.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setInt(1, id);
            ps.setString(2, title);
            ps.setString(3, type);
            ps.setLong(4, dateToExecute);
            ps.setBoolean(5, notify);
            ps.setString(6, desc);
            ps.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int count() {
        try (Connection connection = main.sql.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(COUNT);
            ResultSet result = statement.executeQuery();
            result.next();
            int s = result.getInt("rows");
            result.close();
            return s;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
