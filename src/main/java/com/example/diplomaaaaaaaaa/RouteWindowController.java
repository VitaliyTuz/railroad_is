package com.example.diplomaaaaaaaaa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class RouteWindowController {
    @FXML
    TableView tableView;

    @FXML
    ChoiceBox trainIDChoiceB;
    @FXML
    DatePicker departureDateDateP;
    @FXML
    DatePicker arriveDateDateP;
    @FXML
    TextField stationsTextF;
    @FXML
    TextField ticketCostTextF;
    @FXML
    ChoiceBox statusChoiceB;
    @FXML
    ChoiceBox categoryChoiceB;
    @FXML
    TextField seatsNumberTextF;

    public void updateTable() {
        ObservableList<Route> data = tableView.getItems();
        data.clear();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from route");
            while (resultSet.next()) {

                Route newRoute = new Route(
                        resultSet.getInt("id"),
                        resultSet.getInt("trainID"), resultSet.getString("departure"),
                        resultSet.getString("arrive"), resultSet.getString("stations"),
                        resultSet.getInt("ticketCost"), resultSet.getString("status"),
                        resultSet.getString("reason"), resultSet.getString("category"),
                        resultSet.getInt("seatsNumber"));

                data.add(newRoute);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void searchRoute() {
        ObservableList<Route> data = tableView.getItems();
        data.clear();
//        ArrayList<Employee> temp = new ArrayList<>();
//        Integer experience = 0;
//        Integer age = 0;
//        Integer salary = 0;
        String trainID = "";
        String departure = "";
        String arrive = "";
        Integer ticketCost = 0;
        Integer seatsCount = 0;
        String status = "";
        String category = "";
        try {
            if (departureDateDateP.getValue()!=null) {
                departure = String.valueOf(departureDateDateP.getValue());
            }
            if (arriveDateDateP.getValue()!=null) {
                arrive = String.valueOf(arriveDateDateP.getValue());
            }
            if (!ticketCostTextF.getText().equals("")) {
                ticketCost = Integer.parseInt(ticketCostTextF.getText());
            }
            if (!seatsNumberTextF.getText().equals("")) {
                seatsCount = Integer.parseInt(seatsNumberTextF.getText());
            }

            if (!trainIDChoiceB.getValue().equals("Оберіть номер")) {
                trainID = trainIDChoiceB.getValue().toString();
            }
            if (!statusChoiceB.getValue().equals("Оберіть статус")) {
                status = statusChoiceB.getValue().toString();
            }
            if (!categoryChoiceB.getValue().equals("Оберіть категорію")) {
                category = categoryChoiceB.getValue().toString();
            }
            try {
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
                Statement statement = connection.createStatement();
                String sql = String.format("SELECT * FROM route WHERE trainID LIKE '%%%s%%' AND departure LIKE '%%%s%%' AND" +
                                " arrive LIKE '%%%s%%' AND stations LIKE '%%%s%%' AND ticketCost >= %03d AND status LIKE '%%%s%%' " +
                                "AND category LIKE '%%%s%%' AND seatsNumber >= %03d;",                  // 2 знаки % це просто 1 знак %, бо сам знак % це специфікатор типу, а %% це %
                        trainID, departure, arrive, stationsTextF.getText(), ticketCost, status, category, seatsCount);
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    Route newRoute = new Route(
                            resultSet.getInt("id"),
                            resultSet.getInt("trainID"), resultSet.getString("departure"),
                            resultSet.getString("arrive"), resultSet.getString("stations"),
                            resultSet.getInt("ticketCost"), resultSet.getString("status"),
                            resultSet.getString("reason"), resultSet.getString("category"),
                            resultSet.getInt("seatsNumber"));

                    data.add(newRoute);

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

    public void clearFilters() {
        trainIDChoiceB.setValue("Оберіть номер");
        departureDateDateP.setValue(null);
        arriveDateDateP.setValue(null);
        stationsTextF.setText("");
        ticketCostTextF.setText("");
        statusChoiceB.setValue("Оберіть статус");
        categoryChoiceB.setValue("Оберіть категорію");
        seatsNumberTextF.setText("");
        updateTable();
    }

    public void addToTable() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddEditRouteWindow.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Add New Route");
            stage.setScene(new Scene(fxmlLoader.load(), 600, 500));
            stage.show();
            AddEditRouteWindowController controller = fxmlLoader.getController();
            controller.onAdd(this);
            controller.setTrainIDChoiceB();
            // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteRoute() {
        Route selectedRoute = (Route) tableView.getSelectionModel().getSelectedItem();
//        String sql = String.format("DELETE FROM employee WHERE name='%s' AND workExperience=%03d AND gender='%s' " +
//                "AND age=%03d AND childCount=%03d AND salary=%03d AND department='%s' AND position='%s' AND brigade='%s'",
//                selectedEmployee.getName(), selectedEmployee.getWorkExperience(), selectedEmployee.getGender(),
//                selectedEmployee.getAge(), selectedEmployee.getChildCount(), selectedEmployee.getSalary(), selectedEmployee.getDepartment(), selectedEmployee.getPosition(), selectedEmployee.getBrigade());
        String sql = String.format("DELETE FROM route WHERE id='%03d'", selectedRoute.getId());
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

    public void editRoute() {
        Route selectedRoute = (Route) tableView.getSelectionModel().getSelectedItem();
        if (selectedRoute != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddEditRouteWindow.fxml"));

                Stage stage = new Stage();
                stage.setTitle("Edit Route");
                stage.setScene(new Scene(fxmlLoader.load(), 600, 500));
                stage.show();
                AddEditRouteWindowController controller = fxmlLoader.getController();
                controller.onUpdate(selectedRoute, this);
                controller.setTrainIDChoiceB();
                // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setTrainIDChoiceB() {
        String sql = "SELECT id from locomotive;";
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ArrayList<Integer> trainIDList = new ArrayList<>();
            while (resultSet.next()) {
                trainIDList.add(resultSet.getInt("id"));
            }
            ObservableList<Integer> trainIDObservable = FXCollections.observableList(trainIDList);
            trainIDChoiceB.setItems(trainIDObservable);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void openTrainInfo() {
        Route selectedRoute = (Route) tableView.getSelectionModel().getSelectedItem();
        if (selectedRoute != null) {
            Integer trainID = selectedRoute.getTrainID();
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

//    private Calendar convertDate(String str) {
//        String[] str1 = str.split(" ");
//        Date date;
//        date = java.sql.Date.valueOf(str1[0]);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        String[] str2 = str1[1].split(":");
//        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(str2[0]));
//        calendar.set(Calendar.MINUTE, Integer.parseInt(str2[1]));
//        return calendar;
//    }
}
