package com.example.diplomaaaaaaaaa;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DispatcherTableController {

    @FXML
    private VBox rootPane;
    @FXML
    public Button backBtn;

    public void onBackButtonClick() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("EmployeeWindow.fxml"));
        rootPane.getChildren().clear();
        rootPane.getChildren().setAll(vBox);
//        confirmBtn.setDisable(false);
    }

}
