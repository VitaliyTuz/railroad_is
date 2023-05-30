package com.example.diplomaaaaaaaaa;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class AddEditEmployeeWindowController {
    @FXML
    TextField nameTextB;
    @FXML
    TextField workExperienceTextB;
    @FXML
    TextField genderTextB;
    @FXML
    TextField ageTextB;
    @FXML
    TextField childCountTextB;
    @FXML
    TextField salaryTextB;
    @FXML
    TextField departmentTextB;
    @FXML
    TextField brigadeTextB;
    @FXML
    ChoiceBox positionChoiceBox;

    @FXML
    Button cancelButton;

    @FXML
    Button saveButton;
    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    EmployeeWindowController employeeWindowController;

    private boolean updating = false;           // змінна, щоб розрізняти редагування і додавання нового запису в таблицю

    private Employee selectedEmp;
    @FXML
    protected void onSaveButtonClick() {
        try {
            String name = nameTextB.getText();
            Integer workExp = Integer.valueOf(workExperienceTextB.getText());
            String gender = genderTextB.getText();
            Integer age = Integer.valueOf(ageTextB.getText());
            Integer childCount = Integer.valueOf(childCountTextB.getText());
            Integer salary = Integer.valueOf(salaryTextB.getText());
            String department = departmentTextB.getText();
            String position = String.valueOf(positionChoiceBox.getValue());
            String brigade = brigadeTextB.getText();

            if (position.equals("Оберіть посаду")) {
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
                sql = String.format("UPDATE employee SET name='%s', workExperience=%03d, gender='%s', " +
                        "age=%03d, childCount=%03d, salary=%03d, department='%s', position='%s', brigade='%s' " +
                        "WHERE id='%03d';", name, workExp, gender, age, childCount, salary, department, position, brigade, selectedEmp.getId());
            } else {
                sql = String.format("INSERT INTO employee (name, workExperience, gender, age, childCount, salary, department, position, brigade)" +
                        " VALUES ('%s', %03d, '%s', %03d, %03d, %03d, '%s', '%s', '%s');", name, workExp, gender, age, childCount, salary, department, position, brigade);
            }

            try {
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Programs\\Java\\DiplomaAAAAAAAA\\railroad.db");
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            employeeWindowController.updateTable();

            onCancelButtonClick();
        } catch (Exception e) {
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

    public void onUpdate(Employee employee, EmployeeWindowController employeeWindowController) {
        nameTextB.setText(employee.getName());
        workExperienceTextB.setText(String.valueOf(employee.getWorkExperience()));
        genderTextB.setText(employee.getGender());
        ageTextB.setText(String.valueOf(employee.getAge()));
        childCountTextB.setText(String.valueOf(employee.getChildCount()));
        salaryTextB.setText(String.valueOf(employee.getSalary()));
        departmentTextB.setText(employee.getDepartment());
        positionChoiceBox.setValue(employee.getPosition());
        brigadeTextB.setText(employee.getBrigade());
        updating = true;
        selectedEmp = employee;
        this.employeeWindowController = employeeWindowController;
    }

    public void onAdd(EmployeeWindowController employeeWindowController) {
        this.employeeWindowController = employeeWindowController;
    }
}
