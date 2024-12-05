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

public class LoginScreen extends StackPane {

    public LoginScreen(Stage primaryStage) {
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            HomeScreen homeScreen = new HomeScreen(primaryStage);
            Scene homeScene = new Scene(homeScreen, 400, 300);
            primaryStage.setScene(homeScene);
        });

        this.getChildren().add(loginButton);
    }
}
