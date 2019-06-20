package Rest.repositories;

import dal.context.MySQLUserContext;
import domain.User;

import java.util.List;

public class UserRepository {
    private MySQLUserContext context;

    public UserRepository(){
        this.context = new MySQLUserContext();
    }

    public User getByID(int id) {
        return null;
    }

    public User getByUser(User user){
        return context.getByUser(user);
    }

    public List<User> getAll() {
        return null;
    }

    public boolean insert(User user) {
        return context.insert(user);
    }

    public void delete(int id) {

    }

    public void update(User entity) {

    }
}
