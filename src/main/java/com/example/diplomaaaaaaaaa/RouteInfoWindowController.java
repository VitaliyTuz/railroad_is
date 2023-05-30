package com.example.diplomaaaaaaaaa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class RouteInfoWindowController {
    @FXML
    Label idLabel;
    @FXML
    Label trainIDLabel;
    @FXML
    Label departureLabel;
    @FXML
    Label arriveLabel;
    @FXML
    Label stationsLabel;
    @FXML
    Label ticketCostLabel;
    @FXML
    Label statusLabel;
    @FXML
    Label reasonLabel;
    @FXML
    Label categoryLabel;
    @FXML
    Label seatsNumberLabel;
    @FXML
    Button OKButton;

    public void setInfo(Integer routeID) {
        String sql = String.format("SELECT * from route WHERE id=%03d;", routeID);
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            Route route = null;
            while (resultSet.next()) {
                route = new Route(
                        resultSet.getInt("id"),
                        resultSet.getInt("trainID"), resultSet.getString("departure"),
                        resultSet.getString("arrive"), resultSet.getString("stations"),
                        resultSet.getInt("ticketCost"), resultSet.getString("status"),
                        resultSet.getString("reason"), resultSet.getString("category"),
                        resultSet.getInt("seatsNumber"));
            }
            idLabel.setText(String.valueOf(route.getId()));
            trainIDLabel.setText(String.valueOf(route.getTrainID()));
            departureLabel.setText(route.getDeparture());
            arriveLabel.setText(route.getArrive());
            stationsLabel.setText(route.getStations());
            ticketCostLabel.setText(String.valueOf(route.getTicketCost()));
            statusLabel.setText(route.getStatus());
            reasonLabel.setText(route.getReason());
            categoryLabel.setText(route.getCategory());
            seatsNumberLabel.setText(String.valueOf(route.getSeatsNumber()));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void onOKButtonClick() {
        Stage stage = (Stage) OKButton.getScene().getWindow();
        stage.close();
    }

    public void onTrainInfoButtonClick() {
        Integer trainID = Integer.valueOf(trainIDLabel.getText());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TrainInfoWindow.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Train info");
            stage.setScene(new Scene(fxmlLoader.load(), 380, 380));
            stage.show();
            TrainInfoWindowController controller = fxmlLoader.getController();
            controller.setInfo(trainID);
                // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
