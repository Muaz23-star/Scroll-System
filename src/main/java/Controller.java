import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    Stage stage;
    @FXML
    public void signup(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void login(ActionEvent e){
        System.out.println("Log in");
    }

}
