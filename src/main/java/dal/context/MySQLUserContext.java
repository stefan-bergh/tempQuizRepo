package dal.context;

import dal.DBConnection;
import dal.interfaces.IContext;
import domain.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MySQLUserContext implements IContext<User> {
    @Override
    public User getByID(int id) {
        return null;
    }

    public User getByUser(User user){
        String query = "CALL `QUIZ_USER_GET_NAMEPASS`(?, ?)";

        try (Connection conn = DBConnection.getConnection();
             CallableStatement statement = conn.prepareCall(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeQuery();
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    user.setUserid(result.getInt(1));
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return user;
        }
        return user;
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public boolean insert(User user) {
        String query = "CALL `QUIZ_USER_INSERT_NAMEPASS`(?, ?)";

        try (Connection conn = DBConnection.getConnection();
             CallableStatement statement = conn.prepareCall(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeQuery();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(int id) {

    }
}
