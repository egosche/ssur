package ssur;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SettingsController 
{
	//Items im Pane
	@FXML private Label lb_titel;
	@FXML private Label lb_beschreibung;
	@FXML private Label lb_autor;
	@FXML private Label lb_version;
	@FXML private Button btn_zurueck;
	
	/**
     * Methode fuer den Wechsel zur Scene Home
     * Das Stylesheet und andere Parameter werden fuer die Scene home festgelegt.
     * @throws IOException 
     */
    @FXML
    public void goToHome(ActionEvent event) throws IOException
    {
    	Parent homeParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
        homeParent.getStylesheets().add(getClass().getResource("Style.css").toExternalForm()); 
        Scene home = new Scene(homeParent);
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	window.setScene(home);
    	window.setResizable(false);
    	window.show();
    }
}
