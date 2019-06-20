package Rest;

import Rest.dto.*;
import domain.Question;
import domain.User;

import java.util.List;

public class RESTClient extends RESTClientBase {

    private static final String URL = "http://localhost:8008/quiz";
    @Override
    String getBaseURL() {
        return URL;
    }


    public User getUser(User user){
        GetUserRequestDTO dto = new GetUserRequestDTO(user);
        return executeQueryPost(dto, "/getUser", GetUserRequestDTO.class).getUser();
    }

    public boolean createUser(User user){
        CreateUserDTO dto = new CreateUserDTO(user);
        return executeQueryPost(dto, "/createUser", CreateUserResultDTO.class).isSuccess();
    }

    public List<Question> getallQuestion(){
        return executeQueryGet("/getallQuestion", GetQuestionsResultDTO.class).getQuestions();
    }









}
