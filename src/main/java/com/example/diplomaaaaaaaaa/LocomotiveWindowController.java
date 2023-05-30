package com.example.diplomaaaaaaaaa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class LocomotiveWindowController {

    @FXML
    TableView<Locomotive> tableView;
    @FXML
    ChoiceBox brigadeSearch;
    @FXML
    TextField nameSearch;
    @FXML
    TextField currStationSearch;
    @FXML
    TextField repairCountSearch;
    @FXML
    TextField ageSearch;
    @FXML
    ChoiceBox typeSearch;

    @FXML
    public void clearFilters() {
        brigadeSearch.setValue("Оберіть бригаду");
        nameSearch.setText("");
        currStationSearch.setText("");
        repairCountSearch.setText("");
        ageSearch.setText("");
        typeSearch.setValue("Оберіть тип");
        updateTable();
    }
    @FXML
    public void updateTable() {
        ObservableList<Locomotive> data = tableView.getItems();
        data.clear();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from locomotive");
            while (resultSet.next()) {

                Date releaseDate = Date.valueOf(resultSet.getString("releaseDate"));
                Locomotive newLocomotive = new Locomotive(
                        resultSet.getInt("id"),
                        resultSet.getString("name"), resultSet.getString("brigade"),
                        resultSet.getString("currentStation"), resultSet.getInt("repairCount"),
                        releaseDate, resultSet.getString("type"));

                data.add(newLocomotive);
                setBrigades();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void editLocomotive() {
        Locomotive selectedLocomotive = tableView.getSelectionModel().getSelectedItem();
        if (selectedLocomotive != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddEditLocomotiveWindow.fxml"));

                Stage stage = new Stage();
                stage.setTitle("Edit Locomotive");
                stage.setScene(new Scene(fxmlLoader.load(), 600, 450));
                stage.show();
                AddEditLocomotiveWindowController controller = fxmlLoader.getController();
                controller.onUpdate(selectedLocomotive, this);
                controller.setBrigades();

                // Hide this current window (if this is what you want)
                //            ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void deleteLocomotive() {
        Locomotive selectedLocomotive = tableView.getSelectionModel().getSelectedItem();
//        String sql = String.format("DELETE FROM employee WHERE name='%s' AND workExperience=%03d AND gender='%s' " +
//                "AND age=%03d AND childCount=%03d AND salary=%03d AND department='%s' AND position='%s' AND brigade='%s'",
//                selectedEmployee.getName(), selectedEmployee.getWorkExperience(), selectedEmployee.getGender(),
//                selectedEmployee.getAge(), selectedEmployee.getChildCount(), selectedEmployee.getSalary(), selectedEmployee.getDepartment(), selectedEmployee.getPosition(), selectedEmployee.getBrigade());
        String sql = String.format("DELETE FROM locomotive WHERE id='%03d'", selectedLocomotive.getId());
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
    public void addToTable() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddEditLocomotiveWindow.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Add New Locomotive");
            stage.setScene(new Scene(fxmlLoader.load(), 600, 450));
            stage.show();
            AddEditLocomotiveWindowController controller = fxmlLoader.getController();
            controller.onAdd(this);
            controller.setBrigades();
            // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void searchLocomotive() {
        ObservableList<Locomotive> data = tableView.getItems();
        data.clear();
//        ArrayList<Employee> temp = new ArrayList<>();
//        Integer experience = 0;
//        Integer age = 0;
//        Integer salary = 0;
        Integer age = 0;
        Integer repairCount = 0;
        try {
            if (!ageSearch.getText().equals("")) {
                age = Integer.parseInt(ageSearch.getText());
            }
            if (!repairCountSearch.getText().equals("")) {
                repairCount = Integer.parseInt(repairCountSearch.getText());
            }

            String brigade = "";
            if (!brigadeSearch.getValue().equals("Оберіть бригаду")) {
                brigade = brigadeSearch.getValue().toString();
            }
            String type = "";
            if (!typeSearch.getValue().equals("Оберіть тип")) {
                type = typeSearch.getValue().toString();
            }
            try {
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
                Statement statement = connection.createStatement();
                String sql = String.format("SELECT * FROM locomotive WHERE name LIKE '%%%s%%' AND brigade LIKE '%%%s%%' AND" +
                                " currentStation LIKE '%%%s%%' AND repairCount >= %03d AND type LIKE '%%%s%%';",                  // 2 знаки % це просто 1 знак %, бо сам знак % це специфікатор типу, а %% це %
                        nameSearch.getText(),brigade, currStationSearch.getText(), repairCount, type);
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    Date releaseDate = Date.valueOf(resultSet.getString("releaseDate"));
                    Locomotive newLocomotive = new Locomotive(
                            resultSet.getInt("id"),
                            resultSet.getString("name"), resultSet.getString("brigade"),
                            resultSet.getString("currentStation"), resultSet.getInt("repairCount"),
                            releaseDate, resultSet.getString("type"));
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.YEAR,  - age);
//                    System.out.println(calendar.getTime());
                    if (releaseDate.before(calendar.getTime())) {
                        data.add(newLocomotive);
                    }
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


    public void setBrigades() {
        String sql = "SELECT DISTINCT brigade from locomotive;";
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
            brigadeSearch.setItems(brigadesObservable);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
