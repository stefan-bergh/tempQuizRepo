package domain;

public class Player extends User {
    private int score = 0;

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void removeScore(int score){
        this.score -= score;
    }

    public Player(String username) {
        super(username);
    }

    @Override
    public String toString() {
        return getUsername() + ": " + score;
    }
}
