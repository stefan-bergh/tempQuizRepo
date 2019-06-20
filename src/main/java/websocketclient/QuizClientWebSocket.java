package websocketclient;

import com.google.gson.Gson;
import domain.MessageOperation;
import domain.User;
import domain.WebsocketMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Observable;
import java.util.logging.Logger;

@ClientEndpoint
public class QuizClientWebSocket extends Observable {

    private final String uri = "ws://localhost:8095/quiz/";
    private Session session;
    private String message;
    private Gson gson = new Gson();
    private boolean isRunning = false;
    private static QuizClientWebSocket instance;

    public static QuizClientWebSocket getInstance(){
        if (instance == null) {
            instance = new QuizClientWebSocket();
        }
        return instance;
    }

    public void start() {
        Logger.getGlobal().info("[WebSocket Client start connection]");
        if (!isRunning) {
            isRunning = true;
            startClient();
        }
    }

    public void stop() {
        Logger.getGlobal().info("[WebSocket Client Stopping..]");
        if (isRunning) {
            isRunning = false;
            stopClient();
        }
    }

    private void startClient() {
        try {
            String usertype = "user";
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(uri + usertype));
        } catch (IOException | URISyntaxException | DeploymentException ex) {
            System.out.println("e");
            ex.printStackTrace();
        }
    }

    private void stopClient() {
        try {
            session.close();
        } catch (IOException ex) {
            Logger.getGlobal().severe(ex.getMessage());
        }
    }

    @OnOpen
    public void onWebSocketConnect(Session session) {
        System.out.println("[WebSocket Client open session] " + session.getRequestURI());
        this.session = session;
    }
    @OnMessage
    public void onWebSocketText(String message) {
        this.message = message;
        System.out.println("[WebSocket Client message received] " + message);
        handleMessageFromServer(message);
    }
    @OnClose
    public void onWebSocketClose(CloseReason reason) {
        System.out.println("[WebSocket Client close session] " + session.getRequestURI());
        System.out.println("For reason " + reason);
        session = null;
    }
    @OnError
    public void onWebSocketError(Throwable cause) {
        System.out.println("[WebSocket Client connection error] " + cause.toString());
    }

    private void sendMessage(WebsocketMessage message){
        session.getAsyncRemote().sendText(gson.toJson(message));
    }

    private void handleMessageFromServer(String message){
        WebsocketMessage wm = new WebsocketMessage();
        wm.setContent(message);
        //wm.setOperation(MessageOperation.ANSWER_QUESTION);
        //WebsocketMessage websocketMessage = gson.fromJson(message, WebsocketMessage.class);
        //String content = websocketMessage.getContent();
        String content = wm.getContent();
        setChanged();
        notifyObservers(content);
    }

    public void submitAnswer(String sAnswer){
        WebsocketMessage message = new WebsocketMessage();
        message.setOperation(MessageOperation.ANSWER_QUESTION);
        message.setContent(sAnswer);
        sendMessage(message);
    }

    public void getNextQuestion(){
        WebsocketMessage message = new WebsocketMessage();
        message.setOperation(MessageOperation.NEXT_QUESTION);
        message.setContent("nextQuestion");
        sendMessage(message);
    }

    public void registerUser(User user){
        WebsocketMessage message = new WebsocketMessage();
        message.setOperation(MessageOperation.NEW_USER);
        String json = new Gson().toJson(user);
        message.setContent(json);
        sendMessage(message);
    }
}
