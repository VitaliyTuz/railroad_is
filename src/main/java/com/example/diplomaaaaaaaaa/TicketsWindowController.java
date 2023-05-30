package com.example.diplomaaaaaaaaa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class TicketsWindowController {
    @FXML
    TableView tableView;
    @FXML
    ChoiceBox routeIDChoiceB;
    @FXML
    TextField priceTextF;
    @FXML
    CheckBox baggagePlaceUseCheckB;
    @FXML
    CheckBox isStudentCheckB;
    @FXML
    public void addToTable() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddTicketWindow.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Add New Ticket");
            stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
            stage.show();
            AddTicketWindowController controller = fxmlLoader.getController();
            controller.setRouteIDChoiceB();
            controller.onAdd(this);
            // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTable() {
        ObservableList<Ticket> data = tableView.getItems();
        data.clear();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from ticket");
            while (resultSet.next()) {

                Ticket newTicket = new Ticket(
                        resultSet.getInt("id"),
                        resultSet.getInt("routeID"), resultSet.getInt("price"),
                        resultSet.getString("isStudent"), resultSet.getString("baggagePlaceUse"));

                data.add(newTicket);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void deleteTicket() {
        Ticket selectedTicket = (Ticket) tableView.getSelectionModel().getSelectedItem();
        String sql = String.format("DELETE FROM ticket WHERE id='%03d'", selectedTicket.getId());
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        updateTable();
    }

    @FXML
    public void searchTicket() {
        ObservableList<Ticket> data = tableView.getItems();
        data.clear();
//        ArrayList<Employee> temp = new ArrayList<>();
//        Integer experience = 0;
//        Integer age = 0;
//        Integer salary = 0;
        String routeID = "";
        String isStudent = "";
        String baggagePlaceUse = "";
        Integer price = 0;
        try {
            if (isStudentCheckB.isSelected()) {
                isStudent = "Так";
            }
            if (baggagePlaceUseCheckB.isSelected()) {
                baggagePlaceUse = "Так";
            }
            if (!routeIDChoiceB.getValue().equals("Оберіть маршрут")) {
                routeID = String.valueOf(routeIDChoiceB.getValue());
            }
            if (!priceTextF.getText().equals("")) {
                price = Integer.valueOf(priceTextF.getText());
            }

            try {
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
                Statement statement = connection.createStatement();
                String sql = String.format("SELECT * FROM ticket WHERE routeID LIKE '%%%s%%' AND price >= %03d AND" +
                                " isStudent LIKE '%%%s%%' AND baggagePlaceUse LIKE '%%%s%%';",                  // 2 знаки % це просто 1 знак %, бо сам знак % це специфікатор типу, а %% це %
                        routeID, price, isStudent, baggagePlaceUse);
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    Ticket newTicket = new Ticket(
                            resultSet.getInt("id"),
                            resultSet.getInt("routeID"), resultSet.getInt("price"),
                            resultSet.getString("isStudent"), resultSet.getString("baggagePlaceUse"));

                    data.add(newTicket);
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ErrorWindow.fxml"));

                Stage stage = new Stage();
                stage.setTitle("Error");
                stage.setScene(new Scene(fxmlLoader.load(), 200, 90));
                ErrorWindowController controller = fxmlLoader.getController();      // створюємо контролер вікна, щоб передати йому текст, якйи треба записати в лейбл
                controller.setText("Введені неправильні дані!");
                stage.show();
                // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
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
    public void clearFilters() {
        routeIDChoiceB.setValue("Оберіть маршрут");
        isStudentCheckB.setSelected(false);
        baggagePlaceUseCheckB.setSelected(false);
        priceTextF.setText("");
        updateTable();
    }

    public void openRouteInfo() {
        Ticket selectedTicket = (Ticket) tableView.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            Integer routeID = selectedTicket.getRouteID();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("RouteInfoWindow.fxml"));

                Stage stage = new Stage();
                stage.setTitle("Route info");
                stage.setScene(new Scene(fxmlLoader.load(), 380, 650));
                stage.show();
                RouteInfoWindowController controller = fxmlLoader.getController();
                controller.setInfo(routeID);
                // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
