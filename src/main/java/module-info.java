module com.example.tictactoes {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tictactoes to javafx.fxml;
    exports com.example.tictactoes;
}