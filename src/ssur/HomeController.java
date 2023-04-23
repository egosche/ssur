package ssur;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeController
{
	//Items in der Menueleiste
    @FXML private Label lb_programmname;
    @FXML private RadioButton rbtn_simulation;
    @FXML private RadioButton rbtn_rekonstruktion;
    @FXML private Button btn_start;
    @FXML private Button btn_einstellungen;
    @FXML private Label lb_version;
    
    //Items in der Parameterleiste
    @FXML private Label lb_parametertitel;
    @FXML private TextField tf_gewicht;
    @FXML private Label lb_geschwindigkeit;
    @FXML private TextField tf_geschwindigkeit;
    @FXML private Label lb_impuls;
    @FXML private TextField tf_impuls;
    @FXML private Label lb_energie;
    @FXML private TextField tf_energie;
    @FXML private Label lb_fahrzeugtyp;
    @FXML private ChoiceBox<String> cb_fahrzeugtyp;
    @FXML private Label lb_fahrtrichtung;
    @FXML private ChoiceBox<String> cb_fahrtrichtung;
    @FXML private Label lb_startpunkt;
    @FXML private TextField tf_startpunkt;
    @FXML private Label lb_untergrund;
    @FXML private ChoiceBox<String> cb_untergrund;
    @FXML private Button btn_parameterspeichern;
    @FXML private Button btn_zuruecksetzen;
    
    //Items im Pane
    @FXML private Label lb_modus;
    @FXML private ToggleButton tbtn_fahrzeug1;
    @FXML private ToggleButton tbtn_fahrzeug2;
    @FXML private ImageView iv_fahrzeug1;
    @FXML private ImageView iv_fahrzeug2;
    @FXML private Label lb_rekonstruktion;
    @FXML private Label lb_aufprallort;
    @FXML private Label lb_aufprallzeitpunkt;
    @FXML private Label lb_anhalteweg;
    
    //ObservableListen fuer ChoiceBoxen
    ObservableList<String> fahrzeugtypen = FXCollections.observableArrayList("PKW", "LKW", "E-Scooter","Fahrrad");
    ObservableList<String> fahrtrichtungen = FXCollections.observableArrayList("rechts", "links");
    ObservableList<String> untergrunde = FXCollections.observableArrayList("Asphalt", "Beton", "Schotter", "Kopfsteinpflaster");
    
	//Die zu kollidierenen Fahrzeuge werden erstellt
	Fahrzeug f1 = new Fahrzeug();
	Fahrzeug f2 = new Fahrzeug();
	
	//Bilder fuer die Fahrzeuge
	Image auto_s = new Image("ssur/icons/auto_s.png");
	Image auto_s_r = new Image("ssur/icons/auto_s_r.png");
	Image lkw_s = new Image("ssur/icons/lkw_s.png");
	Image lkw_s_r = new Image("ssur/icons/lkw_s_r.png");
	Image escooter_s = new Image("ssur/icons/escooter_s.png");
	Image escooter_s_r = new Image("ssur/icons/escooter_s_r.png");
	Image fahrrad_s = new Image("ssur/icons/fahrrad_s.png");
	Image fahrrad_s_r = new Image("ssur/icons/fahrrad_s_r.png");
	
	//String Untergrund wird initialisiert
	private String untergrund = "Asphalt";
	
	//String Modus wird initialisiert
	private String modus = "Simulation";
	
	
    /**
     * Initialisierungsmethode
     * In dieser Methode werden die passenden Items den ChoiceBoxen zugeordnet, Tooltipps werden erstellt und die ersten Parameter werden.
     * in die TextFielder und ChoiceBoxen geladen.
     */
    @FXML
    public void initialize()
    {
    	//ChoiceBoxen in der Parameterleiste werden gefuellt
    	cb_fahrzeugtyp.setItems(fahrzeugtypen);
    	cb_fahrtrichtung.setItems(fahrtrichtungen);
    	cb_untergrund.setItems(untergrunde);
    	
    	//Tooltipps werden erstellt
    	tf_energie.setTooltip(new Tooltip("Geben Sie die kinetische Energie des aktuellen Fahrzeuges an."));
    	tf_geschwindigkeit.setTooltip(new Tooltip("Geben Sie die Geschwindigkeit des aktuellen Fahrzeuges an."));
    	tf_gewicht.setTooltip(new Tooltip("Geben Sie das Gewicht des aktuellen Fahrzeuges an."));
    	tf_impuls.setTooltip(new Tooltip("Geben Sie den Impuls des aktuellen Fahrzeuges an."));
    	tf_startpunkt.setTooltip(new Tooltip("Geben Sie an, an welchem Punkt das aktuelle Fahrzeug startet. Bitte verwenden Sie eine gedachte X-Achse, welche im obrigen Schema ganz links ihren Ursprung besitzt."));
    	cb_fahrzeugtyp.setTooltip(new Tooltip("Waehlen Sie den Typ des aktuellen Fahrzeuges."));
    	cb_fahrtrichtung.setTooltip(new Tooltip("Waehlen Sie die Fahrtrichtung des aktuellen Fahrzeuges."));
    	cb_untergrund.setTooltip(new Tooltip("Waehlen Sie den Untergrund, auf dem die Fahrzeuge fahren."));
    	btn_zuruecksetzen.setTooltip(new Tooltip("Klicken Sie hier, um alle Parameter zurueckzusetzen."));
    	btn_parameterspeichern.setTooltip(new Tooltip("Klicken Sie hier, um Ihre Parameter des ausgewaehlten Fahrzeuges zu speichern."));
    	tbtn_fahrzeug1.setTooltip(new Tooltip("Klicken Sie hier, um die Parameter des Fahrzeuges 1 anzuzeigen."));
    	tbtn_fahrzeug2.setTooltip(new Tooltip("Klicken Sie hier, um die Parameter des Fahrzeuges 2 anzuzeigen."));


    	
    	
    	//Textlabel fuer die Berechnungen werden versteckt
    	lb_aufprallort.setVisible(false);
    	lb_aufprallzeitpunkt.setVisible(false);
    	lb_anhalteweg.setVisible(false);
    	lb_rekonstruktion.setVisible(false);
    	
    	//Parameter es ersten Fahrzeuges laden (standardmaessig)
    	ladeParameter(f1);
    	
    	//Untergrund wird geladen (standardmaessig)
    	cb_untergrund.setValue("Asphalt");
    }
    
    
    /**
     * Zur Einstellungs-Scene wechseln
     * Methode fuer den Wechsel zur Scene Einstellungen.
     * @throws IOException 
     */
    @FXML
    public void goToSettings(ActionEvent event) throws IOException
    {
    	Parent settingsParent = FXMLLoader.load(getClass().getResource("Settings.fxml"));
        settingsParent.getStylesheets().add(getClass().getResource("Style.css").toExternalForm()); 
        Scene settings = new Scene(settingsParent);
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	window.setScene(settings);
    	window.setResizable(false);
    	//window.getIcons().add(new Image(this.getClass().getResource("login.png").toString()));
    	window.show();
    }
    
    
    /**
     * Wechsel in den Rekonstruktionsmodus
     * Fahrzeugicons werden in die Position fuer einen Zusammenstoss gebracht, Label werden entsprechend gesetzt und alle Parameter
     * werden zurueckgesetzt.
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    public void goToRekonstruktion(ActionEvent event) throws IOException
    {
    	//Fahrzeuge werden in Ausgangsposition gebracht
		Path path1 = new Path();
		path1.getElements().add(new MoveTo(76, 75));  //Startpunkt der Animation	
		path1.getElements().add(new LineTo(275, 75));
	
		Path path2 = new Path();
		path2.getElements().add(new MoveTo(76, 75));  //Startpunkt der Animation	
		path2.getElements().add(new LineTo(-109, 75));
	
		PathTransition ptr1 = new PathTransition();
		ptr1.setDuration(Duration.seconds(0.5));
		ptr1.setPath(path1);
		ptr1.setNode(iv_fahrzeug1);
		ptr1.setAutoReverse(false);
		
		PathTransition ptr2 = new PathTransition();
		ptr2.setDuration(Duration.seconds(0.5));
		ptr2.setPath(path2);
		ptr2.setNode(iv_fahrzeug2);
		ptr2.setAutoReverse(false);
		
		ptr1.play();
		ptr2.play();
		
		//Label fuer aktuellem Modus wird gewechselt
		lb_modus.setText("Rekonstruktionsmodus");
		lb_startpunkt.setText("Anhalteweg (in m)");
		
		//Tooltipps werden erstellt
    	tf_startpunkt.setTooltip(new Tooltip("Der Anhalteweg ist fuer beide Fahrzeuge gleich gross."));
    	
    	//Alle Parameter der Fahrzeuge werden zurueckgesetzt
    	f1.setEkin(0);
    	f1.setFahrtrichtung("rechts");
    	f1.setFahrzeugtyp("PKW");
    	f1.setGeschwindigkeit(0);
    	f1.setGewicht(0);
    	f1.setImpuls(0);
    	f1.setStartpunkt(0);
    	
    	f2.setEkin(0);
    	f2.setFahrtrichtung("links");
    	f2.setFahrzeugtyp("PKW");
    	f2.setGeschwindigkeit(0);
    	f2.setGewicht(0);
    	f2.setImpuls(0);
    	f2.setStartpunkt(0);
    	
    	cb_untergrund.setValue("Asphalt");
    	untergrund = "Asphalt";
    	
		//Label fuer die letzte Berechnung werden ausgeblendet
		lb_aufprallort.setVisible(false);
		lb_aufprallzeitpunkt.setVisible(false);
		lb_anhalteweg.setVisible(false);
    	
    	//Parameter werden neugeladen
    	tbtn_fahrzeug1.setSelected(true);
    	ladeParameter(f1);
    	
    	//Modus String wird angepasst
    	modus = "Rekonstruktion";
    }
    
    
    /**
     * Wechsel in den Simulationsmodus
     * Fahrzeugicons werden in die Ausgangsposition gebracht, Label werden entsprechend gesetzt und alle Parameter
     * werden zurueckgesetzt.
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    public void goToSimulation(ActionEvent event) throws IOException
    {
    	//Fahrzeuge werden in Ausgangsposition gebracht
		Path path1 = new Path();
		path1.getElements().add(new MoveTo(275, 75));  //Startpunkt der Animation	
		path1.getElements().add(new LineTo(76, 75));
	
		Path path2 = new Path();
		path2.getElements().add(new MoveTo(-109, 75));  //Startpunkt der Animation	
		path2.getElements().add(new LineTo(76, 75));
	
		PathTransition ptr1 = new PathTransition();
		ptr1.setDuration(Duration.seconds(0.5));
		ptr1.setPath(path1);
		ptr1.setNode(iv_fahrzeug1);
		ptr1.setAutoReverse(false);
		
		PathTransition ptr2 = new PathTransition();
		ptr2.setDuration(Duration.seconds(0.5));
		ptr2.setPath(path2);
		ptr2.setNode(iv_fahrzeug2);
		ptr2.setAutoReverse(false);
		
		ptr1.play();
		ptr2.play();
		
		//Label fuer aktuellem Modus wird gewechselt
		lb_modus.setText("Simulationsmodus");
		lb_startpunkt.setText("Startpunkt (in m)");
		
		//Tooltipps werden erstellt
    	tf_startpunkt.setTooltip(new Tooltip("Geben Sie an, an welchem Punkt das aktuelle Fahrzeug startet."));
    	
    	//Alle Parameter der Fahrzeuge werden zurueckgesetzt
    	f1.setEkin(0);
    	f1.setFahrtrichtung("rechts");
    	f1.setFahrzeugtyp("PKW");
    	f1.setGeschwindigkeit(0);
    	f1.setGewicht(0);
    	f1.setImpuls(0);
    	f1.setStartpunkt(0);
    	
    	f2.setEkin(0);
    	f2.setFahrtrichtung("links");
    	f2.setFahrzeugtyp("PKW");
    	f2.setGeschwindigkeit(0);
    	f2.setGewicht(0);
    	f2.setImpuls(0);
    	f2.setStartpunkt(0);
    	
    	cb_untergrund.setValue("Asphalt");
    	untergrund = "Asphalt";
    	
		//Label fuer die letzte Berechnung werden ausgeblendet
		lb_aufprallort.setVisible(false);
		lb_aufprallzeitpunkt.setVisible(false);
		lb_anhalteweg.setVisible(false);
    	
    	//Parameter werden neugeladen
    	ladeParameter(f1);
    	tbtn_fahrzeug1.setSelected(true);
    	
    	//Modus String wird angepasst
    	modus = "Simulation";
    }
    
    /**
     * Parameter speichern
     * Methode fuer das Speichern der eingegebenen Parameter. Je nachdem welches Fahrzeug ausgewaehlt wurde werden die Eingaben.
     * auf dieses gespeichert. Ist kein Fahrzeug ausgewaehlt, wird eine Fehlermeldung ausgegeben.
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    public void speichereParameter(ActionEvent event) throws IOException
    {
    	try
    	{
    		if (tbtn_fahrzeug1.isSelected())
    		{
    			//Lade alle Parameter von Fahrzeug 1 in die Kontrollelemente
    			f1.setGewicht(Float.parseFloat(tf_gewicht.getText()));
    			f1.setGeschwindigkeit(Float.parseFloat(tf_geschwindigkeit.getText()));
    			f1.setImpuls(Float.parseFloat(tf_impuls.getText()));
    			f1.setEkin(Float.parseFloat(tf_energie.getText()));
    			f1.setFahrzeugtyp(cb_fahrzeugtyp.getValue());
    			f1.setFahrtrichtung(cb_fahrtrichtung.getValue());
    			if (modus == "Simulation") {f1.setStartpunkt(Float.parseFloat(tf_startpunkt.getText()));} //Im Rekonstruktionsmodus wird in diesem TextField der Anhalteweg eingetragen
    			if (f1.getImpuls() == 0.0f) {f1.setImpuls(berechneImpuls());} //Falls der Impuls nicht eingetragen wurde, wird er berechnet
    			if (f1.getEkin() == 0.0f) {f1.setEkin(berechneEkin());} //Fals die Ekin nicht eingetragen wurde, wird sie berechnet
    			untergrund = cb_untergrund.getValue();
    			
    			ladeParameter(f1); //Parameter werden neu in die Felder geladen, da Berechnungen stattgefunden haben koennten
    			
    			//Setze das Icon fuer das Fahrzeug
    			switch(f1.getFahrzeugtyp())
    			{
    			case "PKW":
    				if (f1.getFahrtrichtung() == "rechts") {iv_fahrzeug1.setImage(auto_s);}
    				else {iv_fahrzeug1.setImage(auto_s_r);}
    				break;
    			
    			case "LKW":
    				if (f1.getFahrtrichtung() == "rechts") {iv_fahrzeug1.setImage(lkw_s);}
    				else {iv_fahrzeug1.setImage(lkw_s_r);}
    				break;
    				
    			case "E-Scooter":
    				if (f1.getFahrtrichtung() == "rechts") {iv_fahrzeug1.setImage(escooter_s);}
    				else {iv_fahrzeug1.setImage(escooter_s_r);}
    				break;
    			
    			case "Fahrrad":
    				if (f1.getFahrtrichtung() == "rechts") {iv_fahrzeug1.setImage(fahrrad_s);}
    				else {iv_fahrzeug1.setImage(fahrrad_s_r);}
    				break;
    			
    			default:
    				System.out.println("Fehler beim Wechseln des Fahrzeugicons!");
    				break;
    			}
    		}
    	
    		else if (tbtn_fahrzeug2.isSelected())
    		{
    			//Lade alle Parameter von Fahrzeug 2 in die Kontrollelemente
    			f2.setGewicht(Float.parseFloat(tf_gewicht.getText()));
    			f2.setGeschwindigkeit(Float.parseFloat(tf_geschwindigkeit.getText()));
    			f2.setImpuls(Float.parseFloat(tf_impuls.getText()));
    			f2.setEkin(Float.parseFloat(tf_energie.getText()));
    			f2.setFahrzeugtyp(cb_fahrzeugtyp.getValue());
    			f2.setFahrtrichtung(cb_fahrtrichtung.getValue());
    			if (modus == "Simulation") {f2.setStartpunkt(Float.parseFloat(tf_startpunkt.getText()));} //Im Rekonstruktionsmodus wird in diesem TextField der Anhalteweg eingetragen
    			if (f2.getImpuls() == 0.0f) {f2.setImpuls(berechneImpuls());} //Falls der Impuls nicht eingetragen wurde, wird er berechnet
    			if (f2.getEkin() == 0.0f) {f2.setEkin(berechneEkin());} //Fals die Ekin nicht eingetragen wurde, wird sie berechnet
    			untergrund = cb_untergrund.getValue();
    			
    			ladeParameter(f2); //Parameter werden neu in die Felder geladen, da Berechnungen stattgefunden haben koennten
    			
    			//Setze das Icon fuer das Fahrzeug
    			switch(f2.getFahrzeugtyp())
    			{
    			case "PKW":
    				if (f2.getFahrtrichtung() == "rechts") {iv_fahrzeug2.setImage(auto_s);}
    				else {iv_fahrzeug2.setImage(auto_s_r);}
    				break;
    			case "LKW":
    				if (f2.getFahrtrichtung() == "rechts") {iv_fahrzeug2.setImage(lkw_s);}
    				else {iv_fahrzeug2.setImage(lkw_s_r);}
    				break;
    			case "E-Scooter":
    				if (f2.getFahrtrichtung() == "rechts") {iv_fahrzeug2.setImage(escooter_s);}
    				else {iv_fahrzeug2.setImage(escooter_s_r);}
    				break;
    			case "Fahrrad":
    				if (f2.getFahrtrichtung() == "rechts") {iv_fahrzeug2.setImage(fahrrad_s);}
    				else {iv_fahrzeug2.setImage(fahrrad_s_r);}
    				break;
    			default:
    				System.out.println("Fehler beim Wechseln des Fahrzeugicons!");
    				break;
    			}
    		}
    		else
    		{
    			//Falls kein Fahrzeug gewaehlt wurde wird ein Fehler ausgegeben
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Fehlermeldung");
    			alert.setHeaderText("Fehler beim Speichern");
    			alert.setContentText("Bitte waehlen Sie zuerst ein Fahrzeug!");
    			alert.showAndWait();
    		}
    	}
    	catch (Exception e)
    	{
    		createError(e);
    	}
    }
    
    
    /**
     * FXML Event-Methode um die Parameter des jeweiligen Fahrzeuges in die TextFelder bzw. in die ChoiceBoxen zu laden.
     * @param event ActionEvent
     */
    @FXML
    public void ladeParameter(MouseEvent event)
    {
    	ToggleButton tbtn = (ToggleButton) event.getSource();
    	if (tbtn == tbtn_fahrzeug1) {ladeParameter(f1);}
    	if (tbtn == tbtn_fahrzeug2) {ladeParameter(f2);}
    }
    
    
    @FXML
    public void berechnungZuruecksetzen(ActionEvent event)
    {
    	if (lb_aufprallort.isVisible()) //Wenn dieses Label sichtbar ist, fand bereits eine Berechnung statt
    	{
    		//Fahrzeuge werden in Ausgangsposition gebracht
    		Path path1 = new Path();
    		path1.getElements().add(new MoveTo(275, 75));  //Startpunkt der Animation	
    		path1.getElements().add(new LineTo(76, 75));
    	
    		Path path2 = new Path();
    		path2.getElements().add(new MoveTo(-109, 75));  //Startpunkt der Animation	
    		path2.getElements().add(new LineTo(76, 75));
    	
    		PathTransition ptr1 = new PathTransition();
    		ptr1.setDuration(Duration.seconds(0.5));
    		ptr1.setPath(path1);
    		ptr1.setNode(iv_fahrzeug1);
    		ptr1.setAutoReverse(false);
    		
    		PathTransition ptr2 = new PathTransition();
    		ptr2.setDuration(Duration.seconds(0.5));
    		ptr2.setPath(path2);
    		ptr2.setNode(iv_fahrzeug2);
    		ptr2.setAutoReverse(false);
    		
    		ptr1.play();
    		ptr2.play();
        
    		//Label fuer die letzte Berechnung werden ausgeblendet
    		lb_aufprallort.setVisible(false);
    		lb_aufprallzeitpunkt.setVisible(false);
    		lb_anhalteweg.setVisible(false);
    		lb_rekonstruktion.setVisible(false);
    	}
        
    	//Alle Parameter der Fahrzeuge werden zurueckgesetzt
    	f1.setEkin(0);
    	f1.setFahrtrichtung("rechts");
    	f1.setFahrzeugtyp("PKW");
    	f1.setGeschwindigkeit(0);
    	f1.setGewicht(0);
    	f1.setImpuls(0);
    	f1.setStartpunkt(0);
    	
    	f2.setEkin(0);
    	f2.setFahrtrichtung("links");
    	f2.setFahrzeugtyp("PKW");
    	f2.setGeschwindigkeit(0);
    	f2.setGewicht(0);
    	f2.setImpuls(0);
    	f2.setStartpunkt(0);
    	
    	cb_untergrund.setValue("Asphalt");
    	untergrund = "Asphalt";
    	
    	tf_startpunkt.setText("0.0"); //TextField muss manuell auf 0 gesetzt werden, da es sonst in ladeParameter zu Komplikationen kommt
    	ladeParameter(f1); //Parameter werden neugeladen
    	tbtn_fahrzeug1.setSelected(true); //Nach Berechnung werden Fahrzeuge nicht mehr selektiert, weshalb neu selektiert werden muss
    }
    
    
    /**
     * Methode zur Berechnung aller wichtigen Parameter eines Stosses.
     * Diese Methode berechnet den Aufprallort, Aufprallzeitpunkt, Aufprallgeschwindigkeit und den Anhalteweg eines Stosses.
     * Die hierfuer benoetigten Parameter erhaelt die Methode aus den zwei Fahrzeugobjekten. Ausserdem werden Fahrzeugicons
     * der Berechnung getrau animiert. Im Rekonstruktionsmodus entscheidet die Methode unter einer 5-prozentigen Toleranz, ob
     * ein Stoss unter den gegebenen Werten moeglich sein kann.
     * @param event ActionEvent
     */
    @FXML
    public void starteBerechnung(ActionEvent event)
    {
    	try
    	{
    		System.out.println("Neue Berechnung gestartet. \n");
    		
    		float aufprallort = berechneAufprallort(f1.getGeschwindigkeit(), f2.getGeschwindigkeit(), f1.getFahrtrichtung(), f2.getFahrtrichtung(), f1.getStartpunkt(), f2.getStartpunkt());
    		float aufprallzeitpunkt = berechneAufprallzeitpunkt(f1.getGeschwindigkeit(), f2.getGeschwindigkeit(), f1.getFahrtrichtung(), f2.getFahrtrichtung(), f1.getStartpunkt(), f2.getStartpunkt());
    		System.out.println("Aufprallort: " + rundeFloat(aufprallort) + "\n" + "Aufprallzeitpunkt: " + rundeFloat(aufprallzeitpunkt));
    		
    		if (aufprallort != -1.0 && aufprallzeitpunkt != -1.0) //Abfrage, ob es zu einem Zusammenstoss kommen kann
    		{
    			if (modus == "Simulation")
    			{
    				//Unnoetige Einblendung des Aufprallortes
    				FadeTransition fadeInaufprallort = new FadeTransition(Duration.millis(1000));
    				fadeInaufprallort.setNode(lb_aufprallort);
    				fadeInaufprallort.setFromValue(0.0);
    				fadeInaufprallort.setToValue(1.0);
    				fadeInaufprallort.setCycleCount(1);
    				fadeInaufprallort.setAutoReverse(false);
    				lb_aufprallort.setVisible(true);
    				fadeInaufprallort.playFromStart();
    				
    				//Unnoetige Einblendung des Aufprallzeitpunktes
    				FadeTransition fadeInaufprallzeitpunkt = new FadeTransition(Duration.millis(1000));
    				fadeInaufprallzeitpunkt.setNode(lb_aufprallzeitpunkt);
    				fadeInaufprallzeitpunkt.setFromValue(0.0);
    				fadeInaufprallzeitpunkt.setToValue(1.0);
    				fadeInaufprallzeitpunkt.setCycleCount(1);
    				fadeInaufprallzeitpunkt.setAutoReverse(false);
    				lb_aufprallzeitpunkt.setVisible(true);
    				fadeInaufprallzeitpunkt.playFromStart();
    				
    				//Unnoetige Einblendung des Anhalteweges
    				FadeTransition fadeInanhalteweg = new FadeTransition(Duration.millis(1000));
    				fadeInanhalteweg.setNode(lb_anhalteweg);
    				fadeInanhalteweg.setFromValue(0.0);
    				fadeInanhalteweg.setToValue(1.0);
    				fadeInanhalteweg.setCycleCount(1);
    				fadeInanhalteweg.setAutoReverse(false);
    				lb_anhalteweg.setVisible(true);
    				fadeInanhalteweg.playFromStart();
    				
    				//Erste Berechnungen werden in die Label geschrieben
    				lb_aufprallort.setText("Aufprallort: " + rundeFloat(aufprallort) + "m");
    				lb_aufprallzeitpunkt.setText("Aufprallzeitpunkt: " + rundeFloat(aufprallzeitpunkt) + "s");
    			
    				//Alle Fahrzeuge werden nicht mehr selektiert
    				tbtn_fahrzeug1.setSelected(false);
    				tbtn_fahrzeug2.setSelected(false);
    				
    				//vektorielle Geschwindigkeit nach dem Stoss wird berechnet
    				float nachstossgeschwindgkeit = (f1.getImpuls() + f2.getImpuls()) / (f1.getGewicht() + f2.getGewicht());
    				System.out.println("Geschwindigkeit nach dem Stoss: " + rundeFloat(nachstossgeschwindgkeit) + "m/s");
    				
    				//Anhalteweg nach dem Stoss wird berechnet
    				float gesamtgewicht = rundeFloat(f1.getGewicht() + f2.getGewicht());
    				float nachstossstrecke = (nachstossgeschwindgkeit * nachstossgeschwindgkeit) / (2 * ((getCr() * gesamtgewicht * 9.81f) / gesamtgewicht));
    				System.out.println("Anhalteweg: " + rundeFloat(nachstossstrecke) + "m");
    				
    				//Berechnung wird in das Label geschrieben
    				lb_anhalteweg.setText("Anhalteweg: " + rundeFloat(nachstossstrecke) + "m");
    				
    				starteAnimation(nachstossgeschwindgkeit); //Animation fuer beide Fahrzeuge wird gestartet
				}
				else if (modus == "Rekonstruktion")
				{
					//vektorielle Geschwindigkeit nach dem Stoss wird berechnet
					float nachstossgeschwindgkeit = (f1.getImpuls() + f2.getImpuls()) / (f1.getGewicht() + f2.getGewicht());
					System.out.println("Geschwindigkeit nach dem Stoss: " + rundeFloat(nachstossgeschwindgkeit) + "m/s");
					
					//Anhalteweg nach dem Stoss wird berechnet
					float gesamtgewicht = rundeFloat(f1.getGewicht() + f2.getGewicht());
					float nachstossstrecke = (nachstossgeschwindgkeit * nachstossgeschwindgkeit) / (2 * ((getCr() * gesamtgewicht * 9.81f) / gesamtgewicht));
					System.out.println("Berechneter Anhalteweg: " + rundeFloat(nachstossstrecke) + "m");
					
					lb_rekonstruktion.setVisible(true);
					
					//Falls berechneter Anhalteweg mit angegebenen Anhalteweg uerbeinstimmt (mit 5% Toleranz)
					if (nachstossstrecke < (Float.parseFloat(tf_startpunkt.getText()) + Float.parseFloat(tf_startpunkt.getText()) * 0.05f) && nachstossstrecke > (Float.parseFloat(tf_startpunkt.getText())) - Float.parseFloat(tf_startpunkt.getText()) * 0.05f)
					{
						lb_rekonstruktion.setText("Der Stoss ist unter den eingegebenen Parametern moeglich.");
					}
					else
					{
						lb_rekonstruktion.setText("Der Stoss ist unter den eingegebenen Parametern nicht moeglich.");
					}
				}
				else
				{
	    			//Falls es einen Fehler bei der Moduswahl gibt
	    			Alert alert = new Alert(AlertType.ERROR);
	    			alert.setTitle("Fehlermeldung");
	    			alert.setHeaderText("Fehler beim Moduswechsel");
	    			alert.setContentText("Versuchen Sie es spaeter erneut!");
	    			alert.showAndWait();
				}
			}
			else
    		{
    			//Falls es zu keinem Zusammenstoss kommen kann
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Fehlermeldung");
    			alert.setHeaderText("Fehler bei der Berechnung");
    			alert.setContentText("Es kann zu keinem Zusammenstoss der Fahrzeuge kommen!");
    			alert.showAndWait();
    		}
    	}
    	catch (Exception e)
    	{
    		createError(e);
    	}
    }
    
    
    /**
     * Parameter des jeweiligen Fahrzeuges werden in die TextFelder bzw. in die ChoiceBoxen geladen.
     * @param fahrzeug Fahrzeug, fuer welches die Parameter geladen werden sollen
     */
    public void ladeParameter(Fahrzeug fahrzeug)
    {
 	   setTextField(tf_gewicht, Float.toString(fahrzeug.getGewicht()));
 	   setTextField(tf_geschwindigkeit, Float.toString(fahrzeug.getGeschwindigkeit()));
 	   setChoiceBox(cb_fahrtrichtung, fahrzeug.getFahrtrichtung());
 	   setTextField(tf_impuls, Float.toString(fahrzeug.getImpuls()));
 	   setTextField(tf_energie, Float.toString(fahrzeug.getEkin()));
 	   setChoiceBox(cb_fahrzeugtyp, fahrzeug.getFahrzeugtyp());
 	   if (modus == "Simulation") {setTextField(tf_startpunkt, Float.toString(fahrzeug.getStartpunkt()));}
    }
    
    
    /**
     * Animation fuer die Bewegung beider Fahrzeuge.
     * Beide Fahrzeuge fahren aufeinander zu und bewegen sich nach dem Stoss je nach der nachstossgeschwindigkeit unterschiedlich weiter.
     * Ist diese postiv, bewegen sie sich nach rechts, ist sie negativ, bewegen sie sich nach links. Betraegt sie null m/s, so bewegen
     * sich beide Fahrzeuge nicht weiter.
     * @param nachstossgeschwindigkeit Geschwindigkeit nach dem Stoss, mit welcher die Aninmation gestartet wird
     */
    public void starteAnimation(float nachstossgeschwindigkeit)
    {
    	Path path1 = new Path();
    	Path path2 = new Path();
    	
    	path1.getElements().add(new MoveTo(76, 75));  //Startpunkt der Animation	
    	path1.getElements().add(new LineTo(275, 75));

    	path2.getElements().add(new MoveTo(76, 75));  //Startpunkt der Animation	
    	path2.getElements().add(new LineTo(-109, 75));
    	
    	if (nachstossgeschwindigkeit < 0)
     	{
    		//Fahrzeuge bewegen sich gemeinsam nach links
        	path1.getElements().add(new LineTo(175, 75));
        	path2.getElements().add(new LineTo(-209, 75));
     	}
   
     	else if (nachstossgeschwindigkeit > 0)
     	{	
     		//Fahrzeuge bewegen sich gemeinsam nach rechts
        	path1.getElements().add(new LineTo(375, 75));	
        	path2.getElements().add(new LineTo(-9, 75));
     	}
    	
    	PathTransition ptr1 = new PathTransition();
    	ptr1.setDuration(Duration.seconds(2));
        ptr1.setPath(path1);
        ptr1.setNode(iv_fahrzeug1);
        ptr1.setAutoReverse(false);
        
        PathTransition ptr2 = new PathTransition();
        ptr2.setDuration(Duration.seconds(2));
        ptr2.setPath(path2);
        ptr2.setNode(iv_fahrzeug2);
        ptr2.setAutoReverse(false);
        
        ptr1.play();
        ptr2.play();        
    }
 
    
    /**
     * Float runden
     * Methode zum Runden einer Floatzahl
     * @param eingabe Float, welcher gerundet werden soll
     * @return
     */
    public float rundeFloat(float eingabe)
    {
    	return (Math.round(100.0f * eingabe) / 100.0f);
    }
    
    
    /**
     * Impuls berechnen
     * Methode zum Berechnen des Impulses des ausgewaehlten Fahrzueges aus den aktuellen TextFields
     * @return
     */
    public float berechneImpuls()
    {
    	if (cb_fahrtrichtung.getValue() == "links") {return rundeFloat(((Float.parseFloat(tf_geschwindigkeit.getText()) / 3.6f) * Float.parseFloat(tf_gewicht.getText()))) * -1;} //Falls sich das Fahrzeug nach links bewegt, ist die Geschwundigkeit negativ
    	return rundeFloat((Float.parseFloat(tf_geschwindigkeit.getText()) / 3.6f) * Float.parseFloat(tf_gewicht.getText()));
    }
    
    
    /**
     * Kinetische Energie berechnen
     * Methode zum Berechnen der kinetischen Energie des ausgewaehlten Fahrzeuges aus den aktuellen TextFields
     * @return
     */
    public float berechneEkin()
    {
    	return rundeFloat((Float.parseFloat(tf_gewicht.getText()) / 2.0f) * (Float.parseFloat(tf_geschwindigkeit.getText()) * Float.parseFloat(tf_geschwindigkeit.getText())));
    }
    
    
    /**
     * Methode um passenden Rollreibungskoeffizenten zu erhalten
     * Je nach dem welcher Untergrund ausgewaehlt wurde, wird der dazugehoerige Rollreibungskoeffizent zurueckgegeben.
     * @return
     */
    public float getCr()
    {
    	switch (untergrund) 
    	{
		case "Asphalt":
			return 0.013f;
		case "Beton":
			return 0.015f;
		case "Schotter":
			return 0.02f;
		case "Kopfsteinpflaster":
			return 0.02f;
		default:
			System.out.println("Fehler bei der Auswahl des Untergrundes!");
			return -1;
		}
    }
    
    
    /**
     * createError ist eine Methode, welche aufgerufen wird, wenn es zu einem unbekannten Fehler in der Laufzeit kommt. Dabei wird
     * der aufgetretene Fehler dieser Methode uebergeben und dann in einem Fehlerfenster angezeigt.
     * @param ex Fehler, welcher behandelt werden soll
     */
    public void createError(Exception ex)
    {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Fehlermeldung");
    	alert.setHeaderText("Unbekannter Fehler");
    	alert.setContentText("Bitte versuchen Sie es spaeter erneut!");

    	// Ausfahrbare Fehlermeldung.
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	ex.printStackTrace(pw);
    	String exceptionText = sw.toString();

    	Label label = new Label("Fehlerursache:");

    	TextArea textArea = new TextArea(exceptionText);
    	textArea.setEditable(false);
    	textArea.setWrapText(true);

    	textArea.setMaxWidth(Double.MAX_VALUE);
    	textArea.setMaxHeight(Double.MAX_VALUE);
    	GridPane.setVgrow(textArea, Priority.ALWAYS);
    	GridPane.setHgrow(textArea, Priority.ALWAYS);

    	GridPane expContent = new GridPane();
    	expContent.setMaxWidth(Double.MAX_VALUE);
    	expContent.add(label, 0, 0);
    	expContent.add(textArea, 0, 1);

    	alert.getDialogPane().setExpandableContent(expContent); //Fehlermeldung wird in eine Pane gesetzt
    	alert.showAndWait();
    }
    
    
    /**
     * Methode um Inhalt fuer eine ChoiceBox setzen zu koennen
     * @param choiceBox	ChoiceBox, welche geaendert werden soll
     * @param value String, welcher gesetzt werden soll
     */
    public void setChoiceBox(ChoiceBox<String> choiceBox, String value)
    {
    	choiceBox.setValue(value);
    }
     
    
    /**
     * Methode um Inhalt fuer ein TextField setzen zu koennen
     * @param textField	TextField, welches geaendert werden soll
     * @param value String, welcher gesetzt werden soll
     */
    public void setTextField(TextField textField, String value)
    {
    	textField.setText(value);
    }
    
    
    /**
     * Berechnung Aufprallzeitpunkt
     * Methode zur Berechnung des Aufprallzeitpunktes zweier Fahrzeuge. Kommt es zu keinem Aufprall, wird ein Fehler ausgegeben
     * und der Wert -1 zurueckgegeben.
     * @param geschw1	Gewicht des ersten Fahrzeuges
     * @param geschw2	Gewicht des zweiten Fahrzeuges
     * @param fahrtricht1	Fahrtrichtung des ersten Fahrzeuges
     * @param fahrtricht2	Fahrtrichtung des zweiten Fahrzeuges
     * @param startp1	Startpunkt des ersten Fahrzeuges
     * @param startp2	Startpunkt des zweiten Fahrzeuges
     * @return
     */
    public float berechneAufprallzeitpunkt(float geschw1, float geschw2, String fahrtricht1, String fahrtricht2,
    		float startp1, float startp2)
    {
    	if ((fahrtricht1 == "links" && fahrtricht2 == "rechts") && (startp1 != startp2)) //Fahrzeuge fahren auseinander
    	{
    		System.out.println("Fehler! Fahrzeuge treffen sich nie.");
    		return -1;
    	}
    	
    	else if ((geschw1 == 0  && fahrtricht2 == "rechts") || (geschw2 == 0  && fahrtricht1 == "links")) //Ein Fahrzeug steht und das andere faehrt vom stehenden weg
    	{
    		System.out.println("Fehler! Fahrzeuge treffen sich nie.");
    		return -1;
    	}
    	
    	else if (geschw1 == geschw2 && fahrtricht1 == fahrtricht2) //Fahrzeuge fahren gleichschnell in selbe Richtung
    	{
    		System.out.println("Fehler! Fahrzeuge treffen sich nie.");
    		return -1;
    	}
    	
    	else if (geschw1 == 0 && geschw2 == 0) //Fahrzeuge stehen beide still
    	{
    		System.out.println("Fehler! Fahrzeuge treffen sich nie.");
    		return -1;
    	}
    			
    	else if (geschw1 == 0 ^ geschw2 == 0) //Eines der beiden Fahrzeuge steht still, aber nicht beide
    	{
    		if (geschw1 == 0) {return (Math.abs(startp1 - startp2) / (geschw2 / 3.6f));}
    		else {return (Math.abs(startp1 - startp2) / (geschw1 / 3.6f));}
    	}
    	
    	else if (fahrtricht1 != fahrtricht2) //Die Fahrzeuge fahren sich entgegen
    	{
    	   	float geschwindigkeit = Math.abs((geschw1 / 3.6f)) + Math.abs((geschw2 / 3.6f));
    	   	float strecke = Math.abs(startp1) + Math.abs(startp2);
    	   	return (strecke / geschwindigkeit);
    	}
    	else //Die Fahrzeuge fahren in die selbe Richtung
    	{
    		return Math.abs((startp1 - startp2) / Math.abs((geschw1 / 3.6f) - (geschw2 / 3.6f)));
    	}
    }
    
    
    /**
     * Berechnung Aufprallort
     * Methode zur Berechnung des Aufprallortes zweier Fahrzeuge. Kommt es zu keinem Aufprall, wird ein Fehler ausgegeben
     * und der Wert -1 zurueckgegeben.
     * @param geschw1	Gewicht des ersten Fahrzeuges
     * @param geschw2	Gewicht des zweiten Fahrzeuges
     * @param fahrtricht1	Fahrtrichtung des ersten Fahrzeuges
     * @param fahrtricht2	Fahrtrichtung des zweiten Fahrzeuges
     * @param startp1	Startpunkt des ersten Fahrzeuges
     * @param startp2	Startpunkt des zweiten Fahrzeuges
     * @return
     */
    public float berechneAufprallort(float geschw1, float geschw2, String fahrtricht1, String fahrtricht2,
    		float startp1, float startp2)
    {
    	if ((fahrtricht1 == "links" && fahrtricht2 == "rechts") && (startp1 != startp2)) //Fahrzeuge fahren auseinander
    	{
    		System.out.println("Fehler! Fahrzeuge treffen sich nie.");
    		return -1;
    	}
    	
    	else if ((geschw1 == 0  && fahrtricht2 == "rechts") || (geschw2 == 0  && fahrtricht1 == "links")) //Ein Fahrzeug steht und das andere faehrt vom stehenden weg
    	{
    		System.out.println("Fehler! Fahrzeuge treffen sich nie.");
    		return -1;
    	}
    	
    	else if (geschw1 == geschw2 && fahrtricht1 == fahrtricht2) //Fahrzeuge fahren gleichschnell in selbe Richtung
    	{
    		System.out.println("Fehler! Fahrzeuge treffen sich nie.");
    		return -1;
    	}
    	
    	else if (geschw1 == 0 && geschw2 == 0) //Fahrzeuge stehen beide still
    	{
    		System.out.println("Fehler! Fahrzeuge treffen sich nie.");
    		return -1;
    	}
    			
    	else if (geschw1 == 0 ^ geschw2 == 0) //Eines der beiden Fahrzeuge steht still, aber nicht beide
    	{
    		if (geschw1 == 0) {return startp1;}
    		else {return startp2;}
    	}
    		
    	else if (fahrtricht1 != fahrtricht2) //Die Fahrzeuge fahren sich entgegen
    	{
    		float geschwindigkeit = Math.abs((geschw1 / 3.6f)) + Math.abs((geschw2 / 3.6f));
    	    float strecke = Math.abs(startp1) + Math.abs(startp2);
    	    float aufprallzeitpunkt = (strecke / geschwindigkeit);
    	    	
    	    return (aufprallzeitpunkt * (geschw1 / 3.6f));
    	}
    	else //Die Fahrzeuge fahren in die selbe Richtung
    	{
    		float aufprallzeitpunkt = Math.abs((startp1 - startp2) / Math.abs((geschw1 / 3.6f) - (geschw2 / 3.6f)));
    			
    		return ((geschw1 / 3.6f) * aufprallzeitpunkt);
    	}
    }
}