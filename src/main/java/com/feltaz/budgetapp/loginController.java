package com.feltaz.budgetapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class loginController {
    @FXML
    private Button registerButton;

    @FXML
    protected void registerButtonOnAction(ActionEvent event) {

        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();
    }
}