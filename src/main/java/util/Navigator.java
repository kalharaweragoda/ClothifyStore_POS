package util;

import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class Navigator {
    private static Stage mainstage;
    @Getter
    @Setter
    private static Injector injector;

    public static void setStage(Stage stage){
        mainstage=stage;
    }

    public static void navigateTo(String fxmlFile){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Navigator.class.getResource("/view/" + fxmlFile));
            fxmlLoader.setControllerFactory(injector::getInstance);
            Scene scene = new Scene(fxmlLoader.load());
            mainstage.setScene(scene);
            mainstage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}
