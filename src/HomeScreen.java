/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Louis Lau
 */
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HomeScreen extends StackPane {

    public HomeScreen(Stage primaryStage) {
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            Logout.logout(primaryStage);
        });

        this.getChildren().add(logoutButton);
    }
}
