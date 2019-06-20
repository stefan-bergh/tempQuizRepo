package dal.context;

import dal.DBConnection;
import dal.interfaces.IContext;
import domain.Question;
import domain.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MySQLQuestionContext implements IContext<Question> {
    @Override
    public Question getByID(int id) {
        return null;
    }

    /*public User getByUser(User user){

    }*/

    @Override
    public List<Question> getAll() {
        List<Question> questions = new ArrayList<>();

        String query = "CALL `QUIZ_QUESTION_GET_ALL`";

        try (Connection conn = DBConnection.getConnection();
            CallableStatement statement = conn.prepareCall(query)) {
            statement.executeQuery();
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Question q = new Question(result.getString(2), result.getString(3), result.getString(4), result.getBoolean(5), result.getString(6), result.getBoolean(7), result.getString(8), result.getBoolean(9), result.getString(10), result.getBoolean(11));
                    questions.add(q);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public boolean insert(Question question) {
        /*String query = "CALL `QUIZ_USER_INSERT_NAMEPASS`(?, ?)";

        try (Connection conn = DBConnection.getConnection();
             CallableStatement statement = conn.prepareCall(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeQuery();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }*/
        return false;
    }

    @Override
    public void update(Question question) {

    }

    @Override
    public void delete(int id) {

    }
}
