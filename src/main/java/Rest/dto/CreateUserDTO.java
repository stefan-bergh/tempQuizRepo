package Rest.dto;


import domain.User;

public class CreateUserDTO extends BaseRequestDTO {
    public User getUser() {
        return user;
    }

    private User user;

    public CreateUserDTO(User user){
        this.user = user;
    }
}
