module com.feltaz.budgetapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires mysql.connector.java;

    opens com.feltaz.budgetapp to javafx.fxml;
    exports com.feltaz.budgetapp;
    exports com.feltaz.budgetapp.controller;
    opens com.feltaz.budgetapp.controller to javafx.fxml;
}