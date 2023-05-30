package com.example.diplomaaaaaaaaa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AddEditRouteWindowController {
    private Route selectedRoute;
    RouteWindowController routeWindowController;
    @FXML
    ChoiceBox trainIDChoiceB;
    @FXML
    DatePicker departureDateDateP;
    @FXML
    TextField departureHourTextF;
    @FXML
    TextField departureMinuteTextF;
    @FXML
    DatePicker arriveDateDateP;
    @FXML
    TextField arriveHourTextF;
    @FXML
    TextField arriveMinuteTextF;
    @FXML
    TextField stationsTextF;
    @FXML
    TextField ticketCostTextF;

    @FXML
    ChoiceBox statusChoiceB;
    @FXML
    TextField reasonTextF;
    @FXML
    ChoiceBox categoryChoiceB;
    @FXML
    TextField seatsNumberTextF;
    @FXML
    Button cancelButton;

    private boolean updating = false;           // змінна, щоб розрізняти редагування і додавання нового запису в таблицю
    @FXML
    public void onSaveButtonClick() {
        try {
            Integer trainID = Integer.valueOf(String.valueOf(trainIDChoiceB.getValue()));
            String departure = departureDateDateP.getValue() + " " + departureHourTextF.getText() + ":" + departureMinuteTextF.getText();
            String arrive = arriveDateDateP.getValue() + " " + arriveHourTextF.getText() + ":" + arriveMinuteTextF.getText();
            Integer ticketCost = Integer.valueOf(ticketCostTextF.getText());
            String status = String.valueOf(statusChoiceB.getValue());
            String category = String.valueOf(categoryChoiceB.getValue());
            Integer seatsNumber = Integer.valueOf(seatsNumberTextF.getText());

            if ( trainIDChoiceB.getValue().equals("Оберіть номер") || statusChoiceB.getValue().equals("Оберіть статус") || categoryChoiceB.getValue().equals("Оберіть категорію")) {
                throw new IOException();
            }

//            if (!validateDate(releaseDate)) {
////                System.out.println("date");
//                throw new DateFormatException();
//            }



            String sql = "";

            if (updating) {
//                sql = String.format("UPDATE employee SET name='%s', workExperience=%03d, gender='%s', " +
//                        "age=%03d, childCount=%03d, salary=%03d, department='%s', position='%s', brigade='%s' " +
//                        "WHERE " +
//                        "name='%s' AND workExperience=%03d AND gender='%s' " +
//                        "AND age=%03d AND childCount=%03d AND salary=%03d AND department='%s' AND position='%s' AND brigade='%s'",
//                        name, workExp, gender, age, childCount, salary, department, position, brigade,
//                        selectedEmp.getName(), selectedEmp.getWorkExperience(), selectedEmp.getGender(), selectedEmp.getAge(), selectedEmp.getChildCount(),
//                        selectedEmp.getSalary(), selectedEmp.getDepartment(), selectedEmp.getPosition(), selectedEmp.getBrigade());
                sql = String.format("UPDATE route SET trainID = %03d, departure = '%s', arrive = '%s', stations = '%s', ticketCost = %03d, status = '%s', reason = '%s', category ='%s', seatsNumber = %03d" +
                        " WHERE id='%03d';", trainID, departure, arrive, stationsTextF.getText(), ticketCost, status, reasonTextF.getText(), category, seatsNumber, selectedRoute.getId());
            } else {
                sql = String.format("INSERT INTO route (trainID, departure, arrive, stations, ticketCost, status, reason, category, seatsNumber)" +
                        " VALUES (%03d, '%s', '%s', '%s', %03d, '%s', '%s', '%s', %03d);", trainID, departure, arrive, stationsTextF.getText(), ticketCost, status, reasonTextF.getText(), category, seatsNumber);
            }

            try {
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
//            FXMLLoader emplWind = new FXMLLoader(Main.class.getResource("EmployeeWindow.fxml"));        // оновляти таблицю після закриття вікна редагування і додавання
//            EmployeeWindowController controller = emplWind.getController();
//            controller.updateTable();

            routeWindowController.updateTable();

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

    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void reasonTextFDis() {
        Boolean bool = true;
        if (!statusChoiceB.getValue().equals("Активний") || statusChoiceB.getValue().equals("Оберіть статус")) {
            bool = false;
        }
        reasonTextF.setDisable(bool);
    }

    public void onAdd(RouteWindowController routeWindowController) {
        this.routeWindowController = routeWindowController;
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

    public void onUpdate(Route route, RouteWindowController routeWindowController) {
        trainIDChoiceB.setValue(route.getTrainID());
        String[] splitDepDate = route.getDeparture().split(" ");
        String[] splitDepHour = splitDepDate[1].split(":");
//        System.out.println(splitDepDate[0]);
        departureDateDateP.setValue(LocalDate.parse(splitDepDate[0]));
        departureHourTextF.setText(splitDepHour[0]);
        departureMinuteTextF.setText(splitDepHour[1]);
        String[] splitArrDate = route.getArrive().split(" ");
        String[] splitArrHour = splitArrDate[1].split(":");
        arriveDateDateP.setValue(LocalDate.parse(splitArrDate[0]));
        arriveHourTextF.setText(splitArrHour[0]);
        arriveMinuteTextF.setText(splitArrHour[1]);
        stationsTextF.setText(route.getStations());
        ticketCostTextF.setText(String.valueOf(route.getTicketCost()));
        statusChoiceB.setValue(route.getStatus());
        reasonTextF.setText(route.getReason());
        categoryChoiceB.setValue(route.getCategory());
        seatsNumberTextF.setText(String.valueOf(route.getSeatsNumber()));
        updating = true;
        selectedRoute = route;
        this.routeWindowController = routeWindowController;
    }
}
