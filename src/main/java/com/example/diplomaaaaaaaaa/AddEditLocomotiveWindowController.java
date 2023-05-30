package com.example.diplomaaaaaaaaa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

//class DateFormatException extends Exception{}       // ексцепшн дати, який кидається при неправильному форматі дати

public class AddEditLocomotiveWindowController {
    @FXML
    Button cancelButton;
    @FXML
    ChoiceBox brigadeChoiceBox;
    @FXML
    TextField currentStationTextB;
    @FXML
    TextField nameTextB;
    @FXML
    TextField repairCountTextB;
    @FXML
    DatePicker releaseDateDateP;
    @FXML
    ChoiceBox typeChoiceBox;

    private boolean updating = false;           // змінна, щоб розрізняти редагування і додавання нового запису в таблицю

    LocomotiveWindowController locomotiveWindowController;

    private Locomotive selectedLoc;
    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onSaveButtonClick() {
        try {
            String name = nameTextB.getText();
            String brigade = String.valueOf(brigadeChoiceBox.getValue());
            String currStation = currentStationTextB.getText();
            Integer repairCount = Integer.valueOf(repairCountTextB.getText());
            String releaseDate = String.valueOf(releaseDateDateP.getValue());
            String type = String.valueOf(typeChoiceBox.getValue());

//            if (!validateDate(releaseDate)) {
////                System.out.println("date");
//                throw new DateFormatException();
//            }

            if (brigade.equals("Оберіть бригаду")) {
                throw new IOException();
            }

            if (type.equals("Оберіть тип")) {
                throw new IOException();
            }

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
                sql = String.format("UPDATE locomotive SET name='%s', brigade='%s', currentStation='%s', " +
                        "repairCount=%03d, releaseDate='%s', type='%s'" +
                        "WHERE id='%03d';", name, brigade, currStation, repairCount, releaseDate, type, selectedLoc.getId());
            } else {
                sql = String.format("INSERT INTO locomotive (name, brigade, currentStation, repairCount, releaseDate, type)" +
                        " VALUES ('%s', '%s', '%s', %03d, '%s', '%s');", name, brigade, currStation, repairCount, releaseDate, type);
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

            locomotiveWindowController.updateTable();

            onCancelButtonClick();
        }
//        catch (DateFormatException e) {
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ErrorWindow.fxml"));
//
//                Stage stage = new Stage();
//                stage.setTitle("Error");
//                stage.setScene(new Scene(fxmlLoader.load(), 200, 90));
//                ErrorWindowController controller = fxmlLoader.getController();
//                controller.setText("Неправильний формат дати!");
//                stage.show();
//                // Hide this current window (if this is what you want)
////            ((Node)(event.getSource())).getScene().getWindow().hide();
//            }
//            catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
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

    public void onAdd(LocomotiveWindowController locomotiveWindowController) {
        this.locomotiveWindowController = locomotiveWindowController;
    }

    public void onUpdate(Locomotive locomotive, LocomotiveWindowController locomotiveWindowController) {
        nameTextB.setText(locomotive.getName());
        brigadeChoiceBox.setValue(locomotive.getBrigade());
        currentStationTextB.setText(locomotive.getCurrentStation());
        repairCountTextB.setText(String.valueOf(locomotive.getRepairCount()));
//        System.out.println(String.valueOf(locomotive.getReleaseDate()));
        releaseDateDateP.setValue(LocalDate.parse(String.valueOf(locomotive.getReleaseDate())));
        typeChoiceBox.setValue(locomotive.getType());
        updating = true;
        selectedLoc = locomotive;
        this.locomotiveWindowController = locomotiveWindowController;
    }

//    private boolean validateDate(String strDate)
//    {
//        /* Check if date is 'null' */
//        if (strDate.trim().equals(""))
//        {
//            return true;
//        }
//        /* Date is not 'null' */
//        else
//        {
//            /*
//             * Set preferred date format,
//             * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
//            SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
//            sdfrmt.setLenient(false);
//            /* Create Date object
//             * parse the string into date
//             */
//            try
//            {
//                Date javaDate = sdfrmt.parse(strDate);
////                System.out.println(strDate+" is valid date format");
//            }
//            /* Date format is invalid */
//            catch (ParseException e)
//            {
////                System.out.println(strDate+" is Invalid Date format");
//                return false;
//            }
//            /* Return true if date format is valid */
//            return true;
//        }
//    }

    public void setBrigades() {
        String sql = "SELECT DISTINCT brigade from employee;";
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ArrayList<String> brigades = new ArrayList<>();
            while (resultSet.next()) {
                brigades.add(resultSet.getString("brigade"));
            }
            ObservableList<String> brigadesObservable = FXCollections.observableList(brigades);
            brigadeChoiceBox.setItems(brigadesObservable);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
