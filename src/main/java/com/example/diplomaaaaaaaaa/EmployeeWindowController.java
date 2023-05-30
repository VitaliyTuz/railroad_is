package com.example.diplomaaaaaaaaa;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class EmployeeWindowController {
    @FXML
    public Button refreshBtn;

    @FXML
    TableView<Employee> tableView;
    @FXML
    TextField nameSearch;
    @FXML
    TextField experienceSearch;
    @FXML
    TextField genderSearch;
    @FXML
    TextField ageSearch;
    @FXML
    TextField salarySearch;
    @FXML
    TextField departmentSearch;
    @FXML
    TextField brigadeSearch;
    @FXML
    ChoiceBox positionSearch;



    @FXML
    public void updateTable() {
        ObservableList<Employee> data = tableView.getItems();
        data.clear();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from employee");
            while (resultSet.next()) {
                Employee newEmployee = new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"), resultSet.getInt("workExperience"),
                        resultSet.getString("gender"), resultSet.getInt("age"),
                        resultSet.getInt("childCount"), resultSet.getInt("salary"),
                        resultSet.getString("department"), resultSet.getString("position"),
                        resultSet.getString("brigade"));

                data.add(newEmployee);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void addToTable() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddEditEmployeeWindow.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Add New Employee");
            stage.setScene(new Scene(fxmlLoader.load(), 600, 450));
            stage.show();
            AddEditEmployeeWindowController controller = fxmlLoader.getController();
            controller.onAdd(this);
            // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
//        updateTable();
    }

    @FXML
    protected void deleteEmployee() {
        Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
//        String sql = String.format("DELETE FROM employee WHERE name='%s' AND workExperience=%03d AND gender='%s' " +
//                "AND age=%03d AND childCount=%03d AND salary=%03d AND department='%s' AND position='%s' AND brigade='%s'",
//                selectedEmployee.getName(), selectedEmployee.getWorkExperience(), selectedEmployee.getGender(),
//                selectedEmployee.getAge(), selectedEmployee.getChildCount(), selectedEmployee.getSalary(), selectedEmployee.getDepartment(), selectedEmployee.getPosition(), selectedEmployee.getBrigade());
        String sql = String.format("DELETE FROM employee WHERE id='%03d'", selectedEmployee.getId());
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
    protected void editEmployee() {
        Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddEditEmployeeWindow.fxml"));

                Stage stage = new Stage();
                stage.setTitle("Edit Employee");
                stage.setScene(new Scene(fxmlLoader.load(), 600, 450));
                stage.show();
                AddEditEmployeeWindowController controller = fxmlLoader.getController();
                controller.onUpdate(selectedEmployee, this);

                // Hide this current window (if this is what you want)
    //            ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void searchEmployee() {
        ObservableList<Employee> data = tableView.getItems();
        data.clear();
//        ArrayList<Employee> temp = new ArrayList<>();
        Integer experience = 0;
        Integer age = 0;
        Integer salary = 0;
        try {


            if (!experienceSearch.getText().equals("")) {
                experience = Integer.parseInt(experienceSearch.getText());
            }
            if (!ageSearch.getText().equals("")) {
                age = Integer.parseInt(ageSearch.getText());
            }
            if (!salarySearch.getText().equals("")) {
                salary = Integer.parseInt(salarySearch.getText());
            }
            String position = "";
            if (!positionSearch.getValue().equals("Оберіть посаду")) {
                position = positionSearch.getValue().toString();
            }
            try {
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
                Statement statement = connection.createStatement();
                String sql = String.format("SELECT * FROM employee WHERE name LIKE '%%%s%%' AND workExperience >= %03d AND" +
                        " gender LIKE '%%%s%%' AND age >= %03d AND salary >= %03d AND department LIKE '%%%s%%' AND position LIKE '%%%s%%' AND brigade LIKE '%%%s%%';",                  // 2 знаки % це просто 1 знак %, бо сам знак % це специфікатор типу, а %% це %
                        nameSearch.getText(), experience, genderSearch.getText(), age, salary, departmentSearch.getText(), position, brigadeSearch.getText());
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    Employee newEmployee = new Employee(
                            resultSet.getInt("id"),
                            resultSet.getString("name"), resultSet.getInt("workExperience"),
                            resultSet.getString("gender"), resultSet.getInt("age"),
                            resultSet.getInt("childCount"), resultSet.getInt("salary"),
                            resultSet.getString("department"), resultSet.getString("position"),
                            resultSet.getString("brigade"));

                    data.add(newEmployee);
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
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    protected void clearFilters() {
        nameSearch.setText("");
        experienceSearch.setText("");
        genderSearch.setText("");
        ageSearch.setText("");
        salarySearch.setText("");
        departmentSearch.setText("");
        brigadeSearch.setText("");
        positionSearch.setValue("Оберіть посаду");
        updateTable();
    }

//    @FXML
//    protected void onConfirmButtonClick() throws IOException {
////        String selected = choiceBox1.getValue().toString();
//        if (choiceBox1.getValue() == null) {
//            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ErrorWindow.fxml"));
//
//            Stage stage = new Stage();
//            stage.setTitle("Error");
//            stage.setScene(new Scene(fxmlLoader.load(), 204, 100));
//            stage.show();
//        } else {
//            System.out.println(choiceBox1.getValue());
//            VBox vBox = FXMLLoader.load(getClass().getResource("DispatcherTable.fxml"));
//            rootPane.getChildren().setAll(vBox);
//        }
////        confirmBtn.setDisable(true);
//    }
}
