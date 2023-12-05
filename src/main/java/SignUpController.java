import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SignUpController {
    Stage stage;

    @FXML
    private TextField username;

    @FXML // Add this annotation
    private TextField password;

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
            stage.show();
        }else{
            System.out.println(username.getText());
            System.out.println(password.getText());
            System.out.println("WRONG CREDENTIALS");
        }
    }
}

