package Rest.dto;


import domain.User;

import java.util.List;

public class GetUserRequestDTO extends BaseRequestDTO {
    public User getUser() {
        return user;
    }

    private User user;

    public GetUserRequestDTO(User user){
        this.user = user;
    }
}
