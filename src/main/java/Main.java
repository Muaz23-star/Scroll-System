import  javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
public class Main extends Application implements EventHandler<ActionEvent> {
    Button button;
    Pane pane;


    public static void main ( String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       try {
           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
           Parent root = fxmlLoader.load();

           primaryStage.setTitle("Testing");
           primaryStage.setScene(new Scene(root, 600, 600, Color.LIGHTSKYBLUE));
           primaryStage.show();
       }catch(Exception e){
           e.printStackTrace();
       }




//        pane = new Pane();
//        button = new Button();
//
//        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTSKYBLUE, null, null);
//        Background background = new Background(backgroundFill);
//        pane.setBackground(background);
//
//        button.setText("Click me");
//        button.setOnAction(this);
//        button.setLayoutX(300);
//        button.setLayoutY(300);
//
//        pane.getChildren().add(button);
//        Scene scene = new Scene(pane,600,600, Color.LIGHTSKYBLUE);
//        primaryStage.setScene(scene);
//
//        primaryStage.show();


    }


    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == button) {

            Text text = new Text();
            text.setText("Button Clicked!");
            text.setFill(Color.BLACK);
            text.setLayoutX(300);  // Set X position
            text.setLayoutY(350);  // Set Y position (slightly below the button)
            pane.getChildren().add(text);
        }
    }

}
