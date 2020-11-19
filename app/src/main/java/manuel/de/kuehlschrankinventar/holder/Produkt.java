package manuel.de.kuehlschrankinventar.holder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.TreeMap;

import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticStrings.*;

public class Produkt {

    private String name;
    //String, weil es meist 13 Stellige Zahlen sind, die nicht als integer gespeichert werden können
    private double menge;
    private Date verfallsdatum;
    private double preis;
    private TreeMap<String, Double> besitzer;
    private TreeMap<Benutzer, Double>minBestand;
    private String barcode;
    private String einheit;

    /**
     * Neues Produkt erstellen
     * @param name Name des Produktes
     * @param barcode Barcode des Produktes
     */
    public Produkt(String name, double cgDouble1, Date cgDate, double cgDouble2, TreeMap <String, Double>cgTreeMap, TreeMap <Benutzer, Double> minBestand, String barcode, String einheit) {

        this.name = name;
        this.barcode = barcode;
    }

    public Produkt(String jsonString) {
        if (!jsonString.equals("")) {
            try {
                mitJSONStringLaden(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Neuen Barcode setzen
     * @param newBarcode neuer Barcode
     */
    public void setBarcode(String newBarcode) {
        this.barcode = newBarcode;
    }

    /**
     * Name des Produkts erhalten
     * @return gibt den Namen des Produktes zurück
     */
    public String getName() {
        return name;
    }

    /**
     * Überprüfen, ob das Produkt den Barcode enthält
     * @param checkBarcode zu überprüfender Barcode
     * @return Falls der Barcode des Produktes "" ist oder nicht mit dem zu überprüfenden Barcode übereinstimmt wird "false" zurück gegeben
     */
    public boolean containsBarcode(String checkBarcode) {
        if (!barcode.equals("")) {
            return barcode.equals(checkBarcode);
        }
        return false;
    }

    /**
     * Überprüft, ob das Produkt einen Barcode enthält
     * @return true, falls Barcode nicht "" ist.
     */
    public boolean hatBarcode() {
        return !barcode.equals("");
    }

    public String getBarcode() {
        return barcode;
    }

    /**
     * Barcode vom Produkt entfernen
     * @param removeBarcode setzt den Barcode des Produktes auf ""
     */
    public void removeBarcode(String removeBarcode) {
        barcode = "";
    }

    public void geringerBestand(){
        //TODO: Methode geringerBestand in Klasse Produkt ausarbeiten
    }

    public double bestandVonBesitzer(String cgString){
        //TODO: Methode bestandVonBesitzer in Klasse Produkt ausarbeiten
        return 0.0;
    }

    public boolean neuerEinkauf(){
        //TODO: Methode neuerEinauf in Klasse Produkt ausarbeiten
        return false;
    }

    public boolean verbrauchen(){
        //TODO: Methode verbrauchen in Klasse Produkt ausarbeiten
        return false;
    }

    public void nutzerBenachrichtigen(){
        //TODO: Methode nutzerBenachrichtigen in Klasse Produkt ausarbeiten
    }

    public String getSaveString() {
        try {
            JSONObject saveObj = new JSONObject();

            saveObj.put(PRODUKTNAME, name);
            saveObj.put(BARCODE, barcode);
            //TODO weitere inhalte einfügen

            return saveObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
        /*
            private String name;
            //String, weil es meist 13 Stellige Zahlen sind, die nicht als integer gespeichert werden können
            private double menge;
            private Date verfallsdatum;
            private double preis;
            private TreeMap<String, Double> besitzer;
            private TreeMap<Benutzer, Double>minBestand;
            private String barcode;
            private String einheit;
         */
    }

    private void mitJSONStringLaden(String jsonString) throws JSONException {
        JSONObject object = new JSONObject(jsonString);
        setName(object.getString(PRODUKTNAME));
        setBarcode(object.getString(BARCODE));
        //TODO weitere inhalte einfügen
    }
}