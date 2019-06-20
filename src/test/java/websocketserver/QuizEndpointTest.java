package websocketserver;

import domain.Player;
import domain.Question;
import domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QuizEndpointTest {
    QuizEndpoint QE = new QuizEndpoint();

    @BeforeEach
    void setUp() {
        QE.questions.add(new Question("t1", "c1", "a1", true, "a2", false, "a3", false, "a4", false));
        QE.questions.add(new Question("t2", "c2", "a1", false, "a2", true, "a3", false, "a4", false));
        QE.questions.add(new Question("t3", "c3", "a1", false, "a2", false, "a3", true, "a4", false));
        QE.players.add(new Player("player1"));
        QE.players.add(new Player("player2"));
        QE.players.add(new Player("player3"));

    }

    @AfterEach
    void tearDown() {
        QE.questions.clear();
        QE.players.clear();
    }

    @Test
    void answerQuestion_CorrectAnswer_ScoreIncrease() {
        QE.questionNr = 1;
        QE.AnswerQuestion(0, "1");
        int newscore = QE.players.get(0).getScore();
        assertEquals(100, newscore);
    }

    @Test
    void answerQuestion_WrongAnswer_DefaultScore() {
        QE.questionNr = 1;
        QE.AnswerQuestion(0, "2");
        int newscore = QE.players.get(0).getScore();
        assertEquals(0, newscore);
    }

    @Test
    void answerQuestion_NoPlayer_NoScoreChange(){
        QE.questionNr = 1;
        QE.AnswerQuestion( null, "2");
        int newscore = QE.players.get(0).getScore();
        assertEquals(0, newscore);
    }

    @Test
    void answerQuestion_NoAnswer_NoScoreChange(){
        QE.questionNr = 1;
        QE.AnswerQuestion(0, null);
        int newscore = QE.players.get(0).getScore();
        assertEquals(0, newscore);
    }

    @Test
    void registerNewUser_AddPlayer_FourthPlayerInList() {
        QE.registerNewUser(new User("Test", "Test"));
        int playercount = QE.players.size();
        assertEquals(4, playercount);
    }

    @Test
    void registerNewUser_AddIncorrectPlayer_ThreePlayersInList() {
        QE.registerNewUser(new User());
        int playercount = QE.players.size();
        assertEquals(3, playercount);
    }
}