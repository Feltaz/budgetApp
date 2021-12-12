package com.feltaz.budgetapp;

import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

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
    public void loginButtonOnAction(ActionEvent event) throws NoSuchAlgorithmException{

        if(usernameTextField.getText().isBlank()== false && enterPasswordField.getText().isBlank()==false){
            validateLogin();
        } else {
            loginMessageLabel.setText("Please enter username and password");
        }
    }

    @FXML
    protected void registerButtonOnAction(ActionEvent event) {

        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();
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

                }else{
                    loginMessageLabel.setText("Invalid Login, Please Try Again");
                }

            }
        } catch(Exception e) {
            e.printStackTrace();
            e.getCause();

        }
    }
}