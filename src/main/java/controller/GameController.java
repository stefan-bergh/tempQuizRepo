package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import websocketclient.QuizClientWebSocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class GameController implements Observer {
    @FXML
    private TextField tbUser;
    @FXML
    private PasswordField tbPass;
    @FXML
    private Label lblQuizTopic;
    @FXML
    private Label lblQuizQuestion;
    @FXML
    private Button btnQuizStart;
    @FXML
    private Button btnQuizNextQuestion;
    @FXML
    private Button btnQuizA1;
    @FXML
    private Button btnQuizA2;
    @FXML
    private Button btnQuizA3;
    @FXML
    private Button btnQuizA4;
    @FXML
    private ObservableList<Player> playersList;

    private List<Player> players = new ArrayList<>();

    private User user;

    private Boolean firstQuestion = true;

    public void initialize() {
        QuizClientWebSocket.getInstance().start();
        QuizClientWebSocket.getInstance().addObserver(this);
        manageModButtons(false);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void btnQuizStartClicked(){
        QuizClientWebSocket.getInstance().registerUser(user);
        btnQuizStart.setVisible(false);
    }

    public void btnQuizA1Clicked() {
        answerQuestion("1");
    }

    public void btnQuizA2Clicked(){
        answerQuestion("2");
    }

    public void btnQuizA3Clicked(){
        answerQuestion("3");
    }

    public void btnQuizA4Clicked(){
        answerQuestion("4");
    }

    public void answerQuestion(String answer){
        DisableButtons(true);
        QuizClientWebSocket.getInstance().submitAnswer(answer);
    }

    public void btnQuizNextQuestionClicked() {
        QuizClientWebSocket.getInstance().getNextQuestion();
    }

    public void DisableButtons(boolean b){
        btnQuizA1.setDisable(b);
        btnQuizA2.setDisable(b);
        btnQuizA3.setDisable(b);
        btnQuizA4.setDisable(b);
    }

    private void manageModButtons(boolean b) {
        btnQuizNextQuestion.setDisable(!b);
        btnQuizNextQuestion.setVisible(b);
    }

    private void loadFirstQuestion() {
        if(firstQuestion){
            QuizClientWebSocket.getInstance().getNextQuestion();
            firstQuestion = false;
        }
    }

    private void updatePlayerList(List<Player> players) {
        Platform.runLater(
                () -> {
                    playersList.clear();
                    playersList.addAll(players);
                }
        );
    }

    private void updateQuestion(Question q){
        Platform.runLater(
                () -> {
                    lblQuizTopic.setText(q.getTitle());
                    lblQuizQuestion.setText(q.getContent());
                    btnQuizA1.setText(q.getAnswer1());
                    btnQuizA2.setText(q.getAnswer2());
                    btnQuizA3.setText(q.getAnswer3());
                    btnQuizA4.setText(q.getAnswer4());
                }
        );
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("[UPDATE RECEIVED]");

        Gson gson = new Gson();
        String content = arg.toString();
        WebsocketMessage message = gson.fromJson(content, WebsocketMessage.class);

        if(message.getOperation() == MessageOperation.NEXT_QUESTION){
            Question q = gson.fromJson(message.getContent(), Question.class);
            updateQuestion(q);
            DisableButtons(false);
            return;
        }else if(message.getOperation() == MessageOperation.UPDATE_PLAYERS){
            players = gson.fromJson(message.getContent(), new TypeToken<List<Player>>(){}.getType());
            updatePlayerList(players);
            Player player0 = players.get(0);
            if(player0.getUsername().equals(user.getUsername())){
                manageModButtons(true);
                loadFirstQuestion();
            }
            return;
        }
    }

}
