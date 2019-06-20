package Rest;

import Rest.dto.CreateUserDTO;
import Rest.dto.GetUserRequestDTO;
import Rest.repositories.QuestionRepository;
import com.google.gson.Gson;
import domain.Question;
import domain.User;
import Rest.repositories.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/quiz")
public class RESTService {
    private UserRepository userRepository;
    private QuestionRepository questionRepository;
    private Gson gson;

    public RESTService(){
        userRepository = new UserRepository();
        questionRepository = new QuestionRepository();
        gson = new Gson();
    }

    @POST
    @Path("/getUser")
    @Produces("application/json")
    @Consumes("application/json")
    public Response getUser(String input){
        if(input != null){
           GetUserRequestDTO dto = gson.fromJson(input, GetUserRequestDTO.class);
            User u = dto.getUser();
            u = userRepository.getByUser(u);
            //List<Product> products = productRepository.getByTag(dto.getTag());
            String response = RESTResponseHelper.getUserResultDTO(u);
            return Response.status(200).entity(response).build();
        }

        return Response.status(400).entity(RESTResponseHelper.getErrorResponse()).build();
    }

    @POST
    @Path("/createUser")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createUser(String input){
        if(input != null){
            CreateUserDTO dto = gson.fromJson(input, CreateUserDTO.class);
            User u = dto.getUser();
            Boolean result = userRepository.insert(u);
            //List<Product> products = productRepository.getByTag(dto.getTag());
            String response = RESTResponseHelper.getUserResultDTO(u);
            return Response.status(200).entity(result).build();
        }

        return Response.status(400).entity(RESTResponseHelper.getErrorResponse()).build();
    }

    @GET
    @Path("/getallQuestion")
    @Produces("application/json")
    public Response getallQuestion(){
        List<Question> questions = questionRepository.getAll();
        if(questions != null){
            String response = RESTResponseHelper.getQuestionsResultDTO(questions);
            return Response.status(200).entity(response).build();
        }

        return Response.status(400).entity(RESTResponseHelper.getErrorResponse()).build();
    }
}
