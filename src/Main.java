/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Louis Lau
 */
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.time.LocalTime;

public class Main extends Application {
    private static final String FILE_PATH = "C:\\Users\\Louis Lau\\Desktop\\LedgerSystem\\LedgerSystem.csv";  // For storing user data in a file
    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        // Create main login screen layout
        VBox loginLayout = new VBox(10);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setPadding(new javafx.geometry.Insets(20));

        // Create Login UI elements
        Label loginLabel = new Label("Login");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        loginLayout.getChildren().addAll(loginLabel, emailField, passwordField, loginButton, registerButton);

        // Login button action
        loginButton.setOnAction(e -> login(emailField.getText(), passwordField.getText()));

        // Register button action
        registerButton.setOnAction(e -> showRegistrationScreen());

        // Set the Scene for login screen
        Scene loginScene = new Scene(loginLayout, 400, 350);
        stage.setTitle("Ledger System");
        stage.setScene(loginScene);
        stage.show();
    }

    private void login(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] user = line.split(",");
                if (user[1].equals(email)) {
                    String storedHashedPassword = user[2];
                    if (BCrypt.checkpw(password, storedHashedPassword)) {
                        showAlert(Alert.AlertType.INFORMATION, "Login successful", "Welcome back!");
                        // Hide login and registration UI, show time and logout button
                        showTimeScreen();
                        return;
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Login failed", "Incorrect password.");
                        return;
                    }
                }
            }
            showAlert(Alert.AlertType.ERROR, "Login failed", "Email not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showTimeScreen() {
        // Create blank layout for time screen
        VBox timeLayout = new VBox(10);
        timeLayout.setAlignment(Pos.CENTER);
        timeLayout.setPadding(new javafx.geometry.Insets(20));

        // Create Time UI element
        Label timeLabel = new Label();
        timeLabel.setFont(Font.font(48)); // Set large font size
        Button logoutButton = new Button("Logout");

        // Add time and logout button to layout
        timeLayout.getChildren().addAll(timeLabel, logoutButton);

        // Set scene for time screen
        Scene timeScene = new Scene(timeLayout, 600, 400);
        primaryStage.setTitle("Time - Ledger System");
        primaryStage.setScene(timeScene);

        // Start the timeline to update the time
        startTimeUpdater(timeLabel);

        // Logout button action
        logoutButton.setOnAction(e -> logout(timeLabel, logoutButton));
    }

    private void startTimeUpdater(Label timeLabel) {
        // Create a timeline to update the time every second
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLabel.setText("Current Time: " + getCurrentTime());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void logout(Label timeLabel, Button logoutButton) {
        // Hide time and logout button, show login form again
        timeLabel.setVisible(false);
        logoutButton.setVisible(false);
        showLoginScreen();
    }

    private void showLoginScreen() {
        // Create login layout again for reset
        VBox loginLayout = new VBox(10);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setPadding(new javafx.geometry.Insets(20));

        // Create Login UI elements
        Label loginLabel = new Label("Login");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        loginLayout.getChildren().addAll(loginLabel, emailField, passwordField, loginButton, registerButton);

        // Login button action
        loginButton.setOnAction(e -> login(emailField.getText(), passwordField.getText()));

        // Register button action
        registerButton.setOnAction(e -> showRegistrationScreen());

        // Set the Scene for login screen
        Scene loginScene = new Scene(loginLayout, 400, 350);
        primaryStage.setTitle("Ledger System");
        primaryStage.setScene(loginScene);
    }

    private void showRegistrationScreen() {
        // Create registration layout
        VBox registrationLayout = new VBox(10);
        registrationLayout.setAlignment(Pos.CENTER);
        registrationLayout.setPadding(new javafx.geometry.Insets(20));

        // Create Registration UI elements
        Label registerLabel = new Label("Register");
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        Button registerButton = new Button("Register");
        Button backButton = new Button("Back");

        registrationLayout.getChildren().addAll(registerLabel, nameField, emailField, passwordField, confirmPasswordField, registerButton, backButton);

        registerButton.setOnAction(e -> register(nameField.getText(), emailField.getText(), passwordField.getText(), confirmPasswordField.getText()));
        backButton.setOnAction(e -> showLoginScreen());

        // Set the Scene for registration screen
        Scene registrationScene = new Scene(registrationLayout, 400, 400);
        primaryStage.setTitle("Registration - Ledger System");
        primaryStage.setScene(registrationScene);
    }

    private void register(String name, String email, String password, String confirmPassword) {
        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Invalid email", "Please enter a valid email.");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Password mismatch", "Passwords do not match.");
            return;
        }
        if (!isValidPassword(password)) {
            showAlert(Alert.AlertType.ERROR, "Invalid password", "Password must meet the requirements.");
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(name + "," + email + "," + hashedPassword);
            writer.newLine();
            showAlert(Alert.AlertType.INFORMATION, "Registration successful", "You have registered successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String getCurrentTime() {
        // Return current time in HH:mm:ss format (no decimals)
        return LocalTime.now().toString().substring(0, 8);
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isValidPassword(String password) {
        // Modified to include more special characters and improve validation
        return password.length() >= 8 && password.matches(".*[A-Z].*") &&
               password.matches(".*[a-z].*") && password.matches(".*\\d.*") &&
               password.matches(".*[@$!%*?&^#_+=[\\\\]{}|;:',<>,./].*");
    }
}

