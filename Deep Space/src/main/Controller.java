package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Controller {
    PreGame setup;

    @FXML private TextField fileInput;

    @FXML
    private void helpMenu() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("help.fxml"));
        Main.getPrimaryStage().setScene(new Scene(root));
    }

    @FXML
    private void startGame() throws IOException {
        String fileName = fileInput.getText();
        if(setup.start(fileName)){
            Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
            Main.getPrimaryStage().setScene(new Scene(root));
        }
    }

    @FXML
    private void mainMenu() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Main.getPrimaryStage().setScene(new Scene(root));
    }

    @FXML
    public void initialize(){
        setup = new PreGame();
    }

}
