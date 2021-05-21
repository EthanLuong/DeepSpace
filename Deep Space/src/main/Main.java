package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;
    private static void setPrimaryStage(Stage stage){
        Main.primaryStage = stage;
    }
    public static Stage getPrimaryStage(){
        return Main.primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        setPrimaryStage(primaryStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = (Parent)loader.load();
        Controller control = (Controller)loader.getController();

        primaryStage.setTitle("Deep Space");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
