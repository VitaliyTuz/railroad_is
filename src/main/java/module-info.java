module com.example.diplomaaaaaaaaa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.diplomaaaaaaaaa to javafx.fxml;
    exports com.example.diplomaaaaaaaaa;
}