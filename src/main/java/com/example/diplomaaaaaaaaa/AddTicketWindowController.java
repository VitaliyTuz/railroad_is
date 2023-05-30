package com.example.diplomaaaaaaaaa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.channels.InterruptedByTimeoutException;
import java.sql.*;
import java.util.ArrayList;

public class AddTicketWindowController {

    Integer ticketCost = 0;
    @FXML
    Button cancelButton;

    @FXML
    Label stationsLabel;

    @FXML
    ChoiceBox routeIDChoiceB;

    @FXML
    Label priceLabel;

    @FXML
    CheckBox isStudentCheckB;
    @FXML
    CheckBox baggagePlaceUseCheckB;

    TicketsWindowController ticketsWindowController;
    public void onSaveButtonClick() {
        try {
            Integer routeID = Integer.valueOf(String.valueOf(routeIDChoiceB.getValue()));
            String isStudent = "Ні";
            String baggagePlaceUse = "Ні";
            if (routeIDChoiceB.getValue().equals("Оберіть маршрут")) {
                throw new IOException();
            }
            if (isStudentCheckB.isSelected()) {
                isStudent = "Так";
            }
            if (baggagePlaceUseCheckB.isSelected()) {
                baggagePlaceUse = "Так";
            }

            String sql = String.format("INSERT INTO ticket (routeID, price, isStudent, baggagePlaceUse)" +
                        " VALUES (%03d, %03d, '%s', '%s');", routeID, Integer.valueOf(priceLabel.getText()), isStudent, baggagePlaceUse);

            try {
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            ticketsWindowController.updateTable();

            onCancelButtonClick();
        }

        catch (Exception e) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ErrorWindow.fxml"));

                Stage stage = new Stage();
                stage.setTitle("Error");
                stage.setScene(new Scene(fxmlLoader.load(), 200, 90));
                ErrorWindowController controller = fxmlLoader.getController();
                controller.setText("Заповніть необхідні поля!");
                stage.show();
                // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void onAdd(TicketsWindowController ticketsWindowController) {
        this.ticketsWindowController = ticketsWindowController;
    }
    @FXML
    public void onStudentCheckB() {
        Integer price = Integer.valueOf(priceLabel.getText());
        if (isStudentCheckB.isSelected()) {
            priceLabel.setText(String.valueOf(price / 2));
        } else {
            priceLabel.setText(String.valueOf(ticketCost));
        }
    }

    public void setRouteIDChoiceB() {
        String sql = "SELECT id from route;";

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ArrayList<Integer> routeIDList = new ArrayList<>();
            while (resultSet.next()) {
                routeIDList.add(resultSet.getInt("id"));
            }
            ObservableList<Integer> trainIDObservable = FXCollections.observableList(routeIDList);
            routeIDChoiceB.setItems(trainIDObservable);         // задати id


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void setStationsLabel() {
        Integer routeID = Integer.valueOf(String.valueOf(routeIDChoiceB.getValue()));
        String sql = String.format("SELECT stations, ticketCost from route WHERE id=%03d;", routeID);
        String stations = "";

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                stations = resultSet.getString("stations");
                ticketCost = resultSet.getInt("ticketCost");
                priceLabel.setText(String.valueOf(ticketCost));     // задати ціну
            }
            String[] splitStations = stations.split(" ");
            stationsLabel.setText(splitStations[0] + " - " + splitStations[splitStations.length-1]); // задати маршрут

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
