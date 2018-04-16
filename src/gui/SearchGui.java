package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import sun.plugin.javascript.navig.Anchor;

public class SearchGui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        try {
            // 这里的root从FXML文件中加载进行初始化，这里FXMLLoader类用于加载FXML文件
            FlowPane root =  (FlowPane)FXMLLoader.load(getClass().getResource("SearchGui.fxml"));
            Scene scene = new Scene(root, 648, 400);
            primaryStage.setScene(scene);
            primaryStage.setTitle("SearchDir");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
