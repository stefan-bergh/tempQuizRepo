package Rest;

import Rest.dto.*;
import com.google.gson.Gson;
import domain.Question;
import domain.User;

import java.util.List;

public class RESTResponseHelper {

    private static final Gson gson = new Gson();

    private RESTResponseHelper() {
        throw new IllegalStateException("ResponseHelper class");
    }

    static String getErrorResponse() {
        BaseResultDTO result = new BaseResultDTO();
        result.setSuccess(false);
        return gson.toJson(result);
    }

    static String getUserResultDTO(User user){
        GetUserRequestDTO result = new GetUserRequestDTO(user);

        return gson.toJson(result);
    }

    static String createUserResultDTO(User user){
        CreateUserResultDTO result = new CreateUserResultDTO(user);

        return gson.toJson(result);
    }

    static String getQuestionsResultDTO(List<Question> questions){
        GetQuestionsResultDTO result = new GetQuestionsResultDTO(questions);

        return gson.toJson(result);
    }


}
