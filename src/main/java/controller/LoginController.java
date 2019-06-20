package controller;

import Rest.RESTClient;
import domain.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Rest.repositories.UserRepository;
import javafx.stage.StageStyle;
//import websocket.QuizClientWebSocket;

import java.io.IOException;


public class LoginController {
    @FXML
    private TextField tbUser;
    @FXML
    private PasswordField tbPass;
    @FXML
    private Label lError;

    private RESTClient rest;

    private User newuser;

    public void initialize() {
        rest = new RESTClient();
    }

    public void btnLoginClicked(){
        String username = tbUser.getText();
        String password = tbPass.getText();
        if(checkUserinput(username, password)) {
            newuser = new User(username, password);
            System.out.println("User " + username + ", with password " + password + " is trying to login.");
            if (checkLogin(newuser)) {
                System.out.println("Login successful: " + newuser.getUsername());
                navigateToPage("GameUI.fxml");
            } else {
                System.out.println("Login unsuccessful: " + newuser.getUsername());
                lError.setVisible(true);
            }
        }
    }

    public boolean checkUserinput(String username, String password) {
        if(username != null && password!= null){
            return true;
        }else{
            return false;
        }
    }

    public void btnCreateClicked(){
        String username = tbUser.getText();
        String password = tbPass.getText();
        User newuser = new User(username, password);
        System.out.println("User " + username + ", with password " + password + " is trying to create account.");
        if(rest.createUser(newuser)){
            System.out.println("Account creation succesful: " + newuser.getUsername());
            //navigateToPage("GameUI.fxml");
        }else{
            System.out.println("Account creation failed: " + newuser.getUsername());
            //  lError.setVisible(true);
        }
    }

    private Boolean checkLogin(User user){
        user = rest.getUser(user);
        if(user.getUserid() != 0){
            System.out.println("returned true for user " + user.getUsername());
            return true;
        }else{
            System.out.println("returned false for user " + user.getUsername());
            return false;
        }
    }

    private void navigateToPage(String page) {
        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource("/GameUI.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(
                    new Scene(
                            (Pane) root.load()
                    )
            );
            GameController controller = root.getController();
            controller.setUser(newuser);

            //Open and show the RegisterUser window
            stage.show();

            //Close the current window
            Stage thisStage = (Stage)lError.getScene().getWindow();
            thisStage.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
