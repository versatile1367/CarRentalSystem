package carRental.controler;

import carRental.MainApp;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.stage.WindowEvent;

import java.awt.event.ActionEvent;

public class Init {

    @FXML
    public void on_btn_init_go(javafx.event.ActionEvent actionEvent) {
        carRental.MainApp.setLoginUi();
    }

    @FXML
    public void on_btn_init_exit(javafx.event.ActionEvent actionEvent) {
        Event.fireEvent(MainApp.getPrimaryStage(),new WindowEvent(MainApp.getPrimaryStage(),WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
