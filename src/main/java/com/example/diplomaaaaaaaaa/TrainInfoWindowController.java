package com.example.diplomaaaaaaaaa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;

public class TrainInfoWindowController {
    @FXML
    Button OKButton;
    @FXML
    Label idLabel;
    @FXML
    Label nameLabel;
    @FXML
    Label brigLabel;
    @FXML
    Label currStLabel;

    @FXML
    Label repCountLabel;
    @FXML
    Label releaseDateLabel;
    @FXML
    Label typeLabel;

    public void setInfo(Integer trainID) {
        String sql = String.format("SELECT * from locomotive WHERE id=%03d;", trainID);
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            Locomotive locomotive = null;
            while (resultSet.next()) {
                Date releaseDate = Date.valueOf(resultSet.getString("releaseDate"));
                locomotive = new Locomotive(resultSet.getInt("id"),
                        resultSet.getString("name"), resultSet.getString("brigade"),
                        resultSet.getString("currentStation"), resultSet.getInt("repairCount"),
                        releaseDate, resultSet.getString("type"));
            }
            idLabel.setText(String.valueOf(locomotive.getId()));
            nameLabel.setText(locomotive.getName());
            brigLabel.setText(locomotive.getBrigade());
            currStLabel.setText(locomotive.getCurrentStation());
            repCountLabel.setText(String.valueOf(locomotive.getRepairCount()));
            releaseDateLabel.setText(String.valueOf(locomotive.getReleaseDate()));
            typeLabel.setText(locomotive.getType());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void onOKButtonClick() {
        Stage stage = (Stage) OKButton.getScene().getWindow();
        stage.close();
    }
}
