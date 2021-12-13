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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;
import org.w3c.dom.Text;

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
    @FXML
    private Label entryAddedLabel;
    @FXML
    private TableView<Record> tableView;
    @FXML
    private TableColumn<Record, Integer > refIdCol;
    @FXML
    private TableColumn<Record,String> accountCol;
    @FXML
    private TableColumn<Record, Double>  amountCol;
    @FXML
    private TableColumn<Record,String> categoryCol;
    @FXML
    private TextField accountTextField;
    @FXML
    private TextField rfidTextField;
    ObservableList<Record> recordObservableList= FXCollections.observableArrayList();

    private User connectedUser=new User();
    private int userId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File cornerFile = new File("assets/img/leftAnchor.png");
        Image cornerImage = new Image(cornerFile.toURI().toString());
        cornerImageView.setImage(cornerImage);
        userLabel.setText("test");
        landOnHome();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String recordViewQuery = "SELECT idRecord,category,account,amount FROM Record WHERE idUser=' "+userId+"'";
        try  {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(recordViewQuery);
            while(queryResult.next()){
                Integer queryRecordId=queryResult.getInt("idRecord");
                String queryRecordAccount=queryResult.getString("account");
                double queryRecordAmount=queryResult.getDouble("amount");
                String queryRecordCategory=queryResult.getString("category");
                recordObservableList.add(new Record(queryRecordId,queryRecordAccount,queryRecordAmount,queryRecordCategory));
                //We are populating the observable list from the query result
            }
            //PropertyValueFactory corresponds to the new ProductSearchModel fields
            //The table column are anotated above @FXML
            refIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            accountCol.setCellValueFactory(new PropertyValueFactory<>("account"));
            amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
            categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

            tableView.setItems(recordObservableList);
        } catch(Exception e){
    e.printStackTrace();
    e.getCause();
        }
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
        userLabel.setText(username);
        entryAddedLabel.setText("");
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    public void addEntryButtonOnAction(){
        Record newRecord= new Record(2,accountTextField.getText(),Double.parseDouble(amountTextField.getText()),(String) categoryChoice.getValue());
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String addRecord="INSERT INTO Record(idRecord,account,amount,category,idUser) VALUES('";
        String valuesRecord=rfidTextField.getText()+"','"+ newRecord.getAccount()+"','"+String.valueOf(newRecord.getAmount())+"','"+newRecord.getCategory()+"','"+userId+"')";
        try  {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(addRecord+valuesRecord);
            entryAddedLabel.setText("Entry added successfully");

        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }



    }

    public void refreshLinkOnAction(ActionEvent event){
        tableView.getItems().clear();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String recordViewQuery = "SELECT idRecord,category,account,amount FROM Record WHERE idUser=' "+userId+"'";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(recordViewQuery);
            while(queryResult.next()){
                Integer queryRecordId=queryResult.getInt("idRecord");
                String queryRecordAccount=queryResult.getString("account");
                double queryRecordAmount=queryResult.getDouble("amount");
                String queryRecordCategory=queryResult.getString("category");
                recordObservableList.add(new Record(queryRecordId,queryRecordAccount,queryRecordAmount,queryRecordCategory));
                //We are populating the observable list from the query result
            }
            //PropertyValueFactory corresponds to the new ProductSearchModel fields
            //The table column are anotated above @FXML
            refIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            accountCol.setCellValueFactory(new PropertyValueFactory<>("account"));
            amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
            categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

            tableView.setItems(recordObservableList);

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
