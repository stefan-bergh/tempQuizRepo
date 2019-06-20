package Rest.dto;


import domain.Question;
import domain.User;

import java.util.List;

public class GetQuestionsResultDTO {
    public List<Question> getQuestions() {
        return questions;
    }

    private List<Question> questions;

    public GetQuestionsResultDTO(List<Question> questions){
        this.questions = questions;
    }
}
