package com.feltaz.budgetapp.controller;
import com.feltaz.budgetapp.DatabaseConnection;
import com.feltaz.budgetapp.model.User;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.File;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private ImageView shieldImageView;
    @FXML
    private Label registrationMessageLabel;
    @FXML
    private Button closeButton;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField usernameTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File shieldFile = new File("assets/img/shield.png");
        Image shieldImage = new Image(shieldFile.toURI().toString());
        shieldImageView.setImage(shieldImage);

    }

    public void closeButtonOnAction(ActionEvent event) {

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void registerButtonOnAction(ActionEvent event)throws NoSuchAlgorithmException {

        if(setPasswordField.getText().equals(confirmPasswordField.getText())&&!setPasswordField.getText().isBlank()){
            registerUser();
            confirmPasswordLabel.setText("");

        } else {
            confirmPasswordLabel.setText("Password doesn't match");
        }

    }

    public void registerUser() throws NoSuchAlgorithmException {
        DatabaseConnection connectNow= new DatabaseConnection();
        Connection connectDB=connectNow.getConnection();
        User user=new User();
        user.setName(firstnameTextField.getText());
        user.setLastName(lastnameTextField.getText());
        user.setUsername(usernameTextField.getText());
        user.hashPassword(setPasswordField.getText());//hash the password and set it
        String insertFields="INSERT INTO User(firstName,lastName,username,hashedPassword) Values('";
        String insertValues=user.getName()+"','"+user.getLastName()+"','"+user.getUsername()+"','"+user.getHashedPassword()+"')";
        String insertToRegister=insertFields+insertValues;

        try {
           if(firstnameTextField.getText().isBlank()||lastnameTextField.getText().isBlank()||usernameTextField.getText().isBlank()){
                registrationMessageLabel.setText("please Fill Out the registration");

            }else {
            Statement statement=connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
            registrationMessageLabel.setText("User Registered Successfully");}
        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }



    }

}
