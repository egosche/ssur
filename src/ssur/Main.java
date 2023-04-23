package ssur;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application 
{
    public static void main(String[] args) 
    {
        launch(args);
    }

    //Die Home- und Einstellungsscene wird erstellt.
    Scene home, settings;
    
    /**
     * Startmethode
     * Laedt das Startfenster mit Titel, Icon und anderen Voreinstellungen.
     */
    @Override
    public void start(Stage window) throws Exception
    {
        Parent home = FXMLLoader.load(getClass().getResource("Home.fxml"));
        home.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        
        window.setTitle("SSUR");
        window.setScene(new Scene(home));
        window.getIcons().add(new Image("ssur/icons/icon.png"));
        window.setResizable(false);
        window.show();
    }
}