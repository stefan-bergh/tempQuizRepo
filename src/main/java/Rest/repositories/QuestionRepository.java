package Rest.repositories;

import dal.context.MySQLQuestionContext;
import domain.Question;

import java.util.List;

public class QuestionRepository {
    private MySQLQuestionContext context;

    public QuestionRepository(){
        this.context = new MySQLQuestionContext();
    }

    public List<Question> getAll() {
        return context.getAll();
    }

    public boolean insert(Question question) {
        return false;
    }

    public void delete(int id) {

    }

    public void update(Question question) {

    }
}
