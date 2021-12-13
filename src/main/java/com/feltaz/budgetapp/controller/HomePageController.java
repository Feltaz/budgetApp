package com.feltaz.budgetapp.controller;
import com.feltaz.budgetapp.DatabaseConnection;
import com.feltaz.budgetapp.HelloApplication;
import com.feltaz.budgetapp.model.Categories;
import com.feltaz.budgetapp.model.Currencies;
import com.feltaz.budgetapp.model.Record;
import com.feltaz.budgetapp.model.User;

import java.sql.SQLException;
import java.sql.Statement;

import com.feltaz.budgetapp.DatabaseConnection;
import com.feltaz.budgetapp.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.concurrent.ExecutionException;

public class HomePageController implements Initializable {
    @FXML
    private ImageView cornerImageView;
    @FXML
    private Button logoutButton;
    @FXML
    private Label userLabel;
    @FXML
    private Hyperlink refreshLink;
    @FXML
    private TextField amountTextField;
    @FXML
    private ChoiceBox categoryChoice;


    private User connectedUser=new User();
    private Record record = new Record();
    private int userId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File cornerFile = new File("assets/img/leftAnchor.png");
        Image cornerImage = new Image(cornerFile.toURI().toString());
        cornerImageView.setImage(cornerImage);
        userLabel.setText("test");
        landOnHome();
    }

    public void landOnHome(){
        try{DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        Statement statement = connectDB.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM User WHERE connected=1");
        String username = null;
        while (rs.next()) {
            username =rs.getString("username");
            userId=rs.getInt("idUser");

        }
        userLabel.setText(username);}
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    public void addEntryButtonOnAction(){
        Record newRecord= new Record();
        newRecord.setAmount(Double.parseDouble(amountTextField.getText()));
        newRecord.setCategory((String) categoryChoice.getValue());
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String addRecord="INSERT INTO Record(Amount,Category,idUser) VALUES(' ";
        String valuesRecord= String.valueOf(newRecord.getAmount())+"','"+newRecord.getCategory()+"','"+userId+"')";
        try  {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(addRecord+valuesRecord);

        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }



    }

    public void refreshLinkOnAction(ActionEvent event){
        try{
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Record WHERE idUser="+connectedUser.getId());
            while(rs.next()){
                record.setId(rs.getInt("idRecord"));
                record.setAmount(rs.getDouble("amount"));
                record.setCategory((rs.getString("category")));
                record.setCurrency(Currencies.valueOf(rs.getString("currency")));

            }
        }catch(Exception e) {

            e.printStackTrace();
            e.getCause();
        }
    }

    public void logoutButtonOnAction(){
        try {
            DatabaseConnection connectNow= new DatabaseConnection();
            Connection connectDB=connectNow.getConnection();
            String connectedDown="UPDATE User SET connected=0 WHERE connected=1";
            Statement st = connectDB.createStatement();
            st.executeUpdate(connectedDown);
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
