/**
 * Klasse fuer Fahrzeuge
 * @author egoesche
 */

package ssur;

public class Fahrzeug 
{
	//Variablen
	private float geschwindigkeit; //in km/h
	private String fahrtrichtung;
	private float gewicht; //in kg
	private float startpunkt; //in m
	private String fahrzeugtyp;
	private float impuls;
	private float ekin;
	
	/**
	 * Klassenmethode
	 * Initialisiert ein Fahrzeug mit den Werten 0 bzw. Standardwerten.
	 */
	public Fahrzeug()
	{
		geschwindigkeit = 0;
		fahrtrichtung = "rechts";
		gewicht = 0;
		startpunkt = 0;
		fahrzeugtyp = "PKW";
		impuls = 0;
		ekin = 0;		
	}
	
	
	/**
	 * Klassenmethode
	 * Initialisiert ein Fahrzeug mit den uebergebenen Parametern.
	 * @param geschwindigkeit	Geschwindigkeit in km/h, die das Fahrzeug besitzt
	 * @param fahrtrichtung	Fahrtrichtung (links oder rechts), die das Fahrzeug besitzt
	 * @param gewicht	Fahrzeuggewicht in kg
	 * @param startpunkt	Position, von welcher das Fahrzeug startet in Meter
	 * @param fahrzeugtyp	Art des Fahrzeuges (PKW, LKW, Fahrrad oder E-Scooter)
	 * @param impuls	Impuls in Ns des Fahrzeuges
	 * @param ekin	kinetische Energie in J des Fahrzeuges
	 */	
	public Fahrzeug(float geschwindigkeit, String fahrtrichtung, float gewicht, float startpunkt, String fahrzeugtyp, float impuls, float ekin)
	{
		this.geschwindigkeit = geschwindigkeit;
		this.fahrtrichtung = fahrtrichtung;
		this.gewicht = gewicht;
		this.startpunkt = startpunkt;
		this.fahrzeugtyp = fahrzeugtyp;
		this.impuls = impuls;
		this.ekin = ekin;
	}
	
	
	//Getter und Setter
	public float getGeschwindigkeit() {return geschwindigkeit;}
	public void setGeschwindigkeit(float geschwindigkeit) {this.geschwindigkeit = geschwindigkeit;}
	
	public String getFahrtrichtung() {return fahrtrichtung;}
	public void setFahrtrichtung(String fahrtrichtung) {this.fahrtrichtung = fahrtrichtung;}
	
	public float getGewicht() {return gewicht;}
	public void setGewicht(float gewicht) {this.gewicht = gewicht;}
	
	public float getStartpunkt() {return startpunkt;}
	public void setStartpunkt(float startpunkt) {this.startpunkt = startpunkt;}
	
	public String getFahrzeugtyp() {return fahrzeugtyp;}
	public void setFahrzeugtyp(String fahrzeugtyp) {this.fahrzeugtyp = fahrzeugtyp;}
	
	public float getImpuls() {return impuls;}
	public void setImpuls(float impuls) {this.impuls = impuls;}
	
	public float getEkin() {return ekin;}
	public void setEkin(float ekin) {this.ekin = ekin;}
	
	
}
