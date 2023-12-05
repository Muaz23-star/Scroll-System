import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LogInController {
    Stage stage;

    @FXML
    private TextField username;

    @FXML // Add this annotation
    private TextField password;

    @FXML
    private Label warningLabel;

    public void goHome(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Sample.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void checkPassword (ActionEvent e) throws IOException{
        if (User.checkUserLogin(username.getText(),password.getText())){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoggedIn.fxml")));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            warningLabel.setVisible(false);
            stage.show();
        }else{
            warningLabel.setTextFill(Color.RED); // Set the text color to red
            warningLabel.setVisible(true); // Make the label visible

        }
    }
}

