package Rest.dto;


import domain.User;

public class CreateUserResultDTO extends BaseResultDTO {
    public User getUser() {
        return user;
    }

    private User user;

    public CreateUserResultDTO(User user){
        this.user = user;
    }
}
