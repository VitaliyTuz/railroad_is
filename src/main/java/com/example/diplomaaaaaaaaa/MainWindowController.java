package com.example.diplomaaaaaaaaa;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {
    @FXML
    protected void onEmployeeButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("EmployeeWindow.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Employees");
            stage.setScene(new Scene(fxmlLoader.load(), 850, 670));
            stage.show();
            EmployeeWindowController controller = fxmlLoader.getController();
            controller.updateTable();
            // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLocomotiveButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LocomotiveWindow.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Locomotives");
            stage.setScene(new Scene(fxmlLoader.load(), 850, 670));
            stage.show();
            LocomotiveWindowController controller = fxmlLoader.getController();
            controller.updateTable();
            controller.setBrigades();
            // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onRouteButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("RouteWindow.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Routes");
            stage.setScene(new Scene(fxmlLoader.load(), 1200, 700));
            stage.show();
            RouteWindowController controller = fxmlLoader.getController();
            controller.updateTable();
            controller.setTrainIDChoiceB();
            // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onTicketButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TicketsWindow.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Tickets");
            stage.setScene(new Scene(fxmlLoader.load(), 720, 630));
            stage.show();
            TicketsWindowController controller = fxmlLoader.getController();
            controller.updateTable();
            controller.setRouteIDChoiceB();
//            controller.updateTable();
//            controller.setTrainIDChoiceB();
            // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}