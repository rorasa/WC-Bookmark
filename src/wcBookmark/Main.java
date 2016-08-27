package wcBookmark;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();

//        DbManager db = new DbManager("wcdb");

//        Controller controller = loader.getController();
//        controller.setDatabase(db);

        primaryStage.setTitle("Bookmark Collection | Wattanit's Collection");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
 //               db.closeConnection();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
