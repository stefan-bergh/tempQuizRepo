package websocketserver;


import Rest.RESTClient;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import domain.MessageOperation;
import domain.Player;
import domain.Question;
import domain.User;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ServerEndpoint(value = "/quiz/{usertype}")
public class QuizEndpoint {

    private static final List<Session> usersessions = new ArrayList<>();
    private RESTClient rest;
    //below is public for testing
    public static final List<Player> players = new ArrayList<>();
    public List<Question> questions = new ArrayList<>();
    public int questionNr = 0;

    private Gson gson = new Gson();

    @OnOpen
    public void onConnect(Session session, @PathParam("usertype") String userType) {
        if(rest == null){
            rest = new RESTClient();
            questions = rest.getallQuestion();
        }
        System.out.println("[Connected] SessionID:" + session.getId());
        switch (userType) {
            case "user":
                usersessions.add(session);
                break;
            case "admin":
                //System.out.println("admin");
                //allow for different functionality, possible future addition
                break;
            default:
                break;
        }
}

    @OnMessage
    public void onText(String message, Session session) {
        System.out.println("[Session ID] : " + session.getId() + " [Received] : " + message);
        handleMessage(message, session);
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        System.out.println("[Session ID] : " + session.getId() + "[Socket Closed: " + reason);
        for (Session s : usersessions) {
            if(s.equals(session)){
                usersessions.remove(s);
            }
        }
    }

    @OnError
    public void onError(Throwable cause, Session session) {
        System.out.println("[Session ID] : " + session.getId() + "[ERROR]: ");
        cause.printStackTrace(System.err);
    }

    public void sendMessage(String message, List<Session> list) {
        System.out.println("[Broadcast] { " + message + " } to:");

        for (Session s : list) {
            try {
                s.getBasicRemote().sendText(message);
                System.out.println("\t\t >> Client associated with server side session ID: " + s.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[End of Broadcast]");
    }

    public void sendMessage(String message, Session session) {
        System.out.println("[Broadcast] { " + message + " } to:");

        try {
            session.getBasicRemote().sendText(message);
            System.out.println("\t\t >> Client associated with server side session ID: " + session.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[End of Broadcast]");
    }

    private void handleMessage(String jsonmessage, Session session) {
        gson = new Gson();
        WebsocketMessage message;
        try {
            message = gson.fromJson(jsonmessage, WebsocketMessage.class);
        } catch (JsonSyntaxException e) {
            Logger.getGlobal().severe("[WebSocket ERROR] : cannot parse Json message : " + jsonmessage);
            sendMessage("Message could not be send", session);
            return;
        }
        /*if (message.getOperation() == MessageOperation.TEST_MESSAGE) {
            sendMessage(message.getContent(), usersessions);
        }*/
        if(message.getOperation() == MessageOperation.NEXT_QUESTION){
            sendMessage(createNextQuestionMessage(), usersessions);
            sendMessage(updatePlayerlist(), usersessions);
        }
        if(message.getOperation() == MessageOperation.NEW_USER){
            registerNewUser(gson.fromJson(message.getContent(), User.class));
            sendMessage(updatePlayerlist(), usersessions);
        }
        if(message.getOperation() == MessageOperation.ANSWER_QUESTION){
            AnswerQuestion(usersessions.indexOf(session), message.getContent());
        }
    }

    private String createNextQuestionMessage(){
        Question q = questions.get(questionNr);
        if(questionNr < questions.size() - 1){
            questionNr++;
        }else{
            questionNr = 0;
        }
        WebsocketMessage message = new WebsocketMessage();
        message.setOperation(MessageOperation.NEXT_QUESTION);
        message.setContent(gson.toJson(q));
        return gson.toJson(message);
    }

    //made public purely for test purposes
    public void AnswerQuestion(Integer iPlayer, String answer) {
        if(iPlayer != null && answer != null) {
            Question q = questions.get(questionNr - 1);
            switch (answer) {
                case "1":
                    if (q.checkAnswer1())
                        players.get(iPlayer).addScore(100);
                    break;
                case "2":
                    if (q.checkAnswer2())
                        players.get(iPlayer).addScore(100);
                    break;
                case "3":
                    if (q.checkAnswer3())
                        players.get(iPlayer).addScore(100);
                    break;
                case "4":
                    if (q.checkAnswer4())
                        players.get(iPlayer).addScore(100);
                    break;
                default:
                    break;
            }
        }
    }

    //made public purely for test purposes
    public void registerNewUser(User u){
        if(u.getUsername() != null && u.getPassword() != null) {
            Player p = new Player(u.getUsername());
            players.add(p);
        }
    }

    private String updatePlayerlist(){
        WebsocketMessage message = new WebsocketMessage();
        message.setOperation(MessageOperation.UPDATE_PLAYERS);
        message.setContent(gson.toJson(players));
        return gson.toJson(message);
    }
}
