/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Louis Lau
 */
import javafx.stage.Stage;

public class Logout {

    public static void logout(Stage primaryStage) {
        // In a real application, you would clear any user session data here

        // Switch to the Login screen
        LoginScreen loginScreen = new LoginScreen(primaryStage);
        Scene loginScene = new Scene(loginScreen, 300, 250);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login Screen");
    }
}