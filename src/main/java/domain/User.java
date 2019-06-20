package domain;

public class User {
    private String username;
    private String password;
    private int userid = 0;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public User(){

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username) {
        this.username = username;
        this.password = "private";
    }
}
