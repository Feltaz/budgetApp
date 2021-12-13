package com.feltaz.budgetapp.controller;

import java.io.IOException;
import java.sql.Statement;

import com.feltaz.budgetapp.DatabaseConnection;
import com.feltaz.budgetapp.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.net.URL;

import java.nio.charset.*;
import java.security.*;

public class loginController implements Initializable {
    @FXML
    private Button registerButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView lockImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private Button loginButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("assets/img/leftAnchor.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);

        File lockFile = new File("assets/img/lock.png");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);
    }
    @FXML
    public void loginButtonOnAction(ActionEvent event) throws NoSuchAlgorithmException, IOException {

        if(usernameTextField.getText().isBlank()== false && enterPasswordField.getText().isBlank()==false){
            validateLogin();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("HomePage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 986, 732);
            Stage registerStage =new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(scene);
            registerStage.show();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
        } else {
            loginMessageLabel.setText("Please enter username and password");
        }
    }

    @FXML
    protected void registerButtonOnAction(ActionEvent event) {

        createAccountForm();
    }

    public void validateLogin() throws NoSuchAlgorithmException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB=connectNow.getConnection();
        String str = enterPasswordField.getText();
        MessageDigest msg = MessageDigest.getInstance("SHA-256");//call the hasher
        byte[] hash = msg.digest(str.getBytes(StandardCharsets.UTF_8));
        // convert bytes to hexadecimal
        StringBuilder s = new StringBuilder();
        for (byte b : hash) {
            s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        String hashedPassword=s.toString();//hash it into bytes
        String verifyLogin="SELECT count(1) From User WHERE username= '" + usernameTextField.getText() +"' AND hashedPassword='"+hashedPassword+"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1)==1){
                    loginMessageLabel.setText("Congrats!");
                    String connectedUP="UPDATE User SET connected=1 WHERE username='"+usernameTextField.getText()+"'";
                    Statement st = connectDB.createStatement();
                    st.executeUpdate(connectedUP);

                }else{
                    loginMessageLabel.setText("Invalid Login, Please Try Again");
                }

            }
        } catch(Exception e) {
            e.printStackTrace();
            e.getCause();

        }
    }
    public void createAccountForm(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 520, 469);
            Stage registerStage =new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(scene);
            registerStage.show();
        } catch(Exception e) {

        }
    }
}