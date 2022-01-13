import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;

public class cardSearchRunner extends Application
{
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        // Main Screen load.
        FXMLLoader mainScreen = new FXMLLoader(getClass().getResource
                                    ("resources/fxml/CardSearch.fxml"));
        Parent mainPane = mainScreen.load();
        Scene main = new Scene(mainPane);

        primaryStage.setScene(main);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
