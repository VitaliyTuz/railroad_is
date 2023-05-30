package com.example.diplomaaaaaaaaa;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorWindowController {
    @FXML
    Button OKBtn;

    @FXML
    Label label1;
    @FXML
    protected void onOKButtonClick() {
        Stage stage = (Stage) OKBtn.getScene().getWindow();
        stage.close();
    }

    public void setText(String text) {
        label1.setText(text);
    }
}
