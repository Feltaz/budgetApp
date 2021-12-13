package com.feltaz.budgetapp.controller;
import com.feltaz.budgetapp.DatabaseConnection;
import com.feltaz.budgetapp.HelloApplication;
import com.feltaz.budgetapp.model.User;
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

public class HomePageController implements Initializable {
    @FXML
    private ImageView cornerImageView;
    @FXML
    private Button logoutButton;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File cornerFile = new File("assets/img/leftAnchor.png");
        Image cornerImage = new Image(cornerFile.toURI().toString());
        cornerImageView.setImage(cornerImage);
    }
    public void logoutButtonOnAction(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 520, 400);
            Stage registerStage =new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(scene);
            registerStage.show();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.close();

        } catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}
