package manuel.de.kuehlschrankinventar.holder;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONObject;

import java.util.Date;
import java.util.TreeMap;

import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticStrings.BARCODE;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticStrings.PRODUKTNAME;

import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticStrings.*;

public class Produkt {

    private String name;
    //String, weil es meist 13 Stellige Zahlen sind, die nicht als integer gespeichert werden können
    private double menge;
    private Date verfallsdatum;
    private double preis;
    private TreeMap<String, Double> besitzermenge;
    private TreeMap<String, Double>minBestand; // TODO Treemap<Benutzer in TreeMap<Atring geändert
    private String barcode;
    private String einheit;


    /**
     * Neues Produkt erstellen
     * @param name Produktname
     * @param menge Menge
     * @param verfallsdatum Verfallsdatum
     * @param preis Preis
     * @param besitzer Besitzer
     * @param minBestand mindest Bestand
     * @param barcode Barcodenummer
     * @param einheit Einheit
     */
     public Produkt(String name, double menge, Date verfallsdatum, double preis, TreeMap <String, Double>besitzer, TreeMap <String, Double> minBestand, String barcode, String einheit) {
        setName(name);
        setMenge(menge);
        setVerfallsdatum(verfallsdatum);
        setPreis(preis);
        setBesitzer(besitzer);
        setMinBestand(minBestand);
        setBarcode(barcode);
        setEinheit(einheit);
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

    /**
     * Neuen Produktnamen erstellen
     * @param newName neuer Name
     * @return True bei erfolgreicher Übergabe
     */
    public boolean setName( String newName) {
        if (newName == null)
        {
            return false;
        }else{
        this.name = newName;
        return true;}
    }

    /**
     * Menge ändern
     * @param newMenge Menge
     * @return True bei erfolgreicher Übergabe
     */
    public boolean setMenge(double newMenge){
        if (newMenge < 0.0)
        {
            return false;
        }else{
            this.menge = newMenge;
            return true;}
    }

    /**
     * Verfallsdatum eingeben
     * @param newVerfallsdatum neues Verfallsdatum
     * @return True bei erfolgreicher übergabe
     */
    public boolean setVerfallsdatum(Date newVerfallsdatum) {
        if (newVerfallsdatum == null) {
            return false;
        } else {
            this.verfallsdatum = verfallsdatum;
            return true;
        }
    }

    /**
     * Preis ändern
     * @param newPreis neuer Preis
     * @return True bei erfolgreicher Übergabe
     */
    public boolean setPreis(double newPreis){
        if (newPreis < 0.0)
        {
            return false;
        }else{
            this.menge = newPreis;
            return true;}
    }

    /**
     * neuer Besitzer
     * @param newbesitzer neuer Besitzer
     */
    public void setBesitzer(TreeMap<String, Double> newbesitzer){
        this.besitzermenge = newbesitzer;
    }

    /**
     * Mindest Bestand ändern
     * @param newMinBestand neuer mindest Bestand
     */
    public void setMinBestand(TreeMap<String, Double> newMinBestand){
        this.minBestand = newMinBestand;
    }

    /**
     * neuen Barcode erstellen
     * @param newBarcode neuer barcode
     * @return True bei erfolgreicher Übergabe
     */
    public boolean setBarcode(String newBarcode) {
        if (newBarcode == null){
            return  false;
        }else{
            this.barcode = newBarcode;
            return true;
        }
    }

    /**
     * Einheit ändern
     * @param newEinheit neue Einheit
     * @return True bei erfolgreicher übergabe
     */
    public boolean setEinheit(String newEinheit) {
        if (newEinheit == null){
            return  false;
        }else{
            this.einheit = newEinheit;
            return true;
        }
    }
    /**
     * Name des Produkts erhalten
     * @return gibt den Namen des Produktes zurück
     */
    public String getName() {
        return name;
    }

    public double getMenge() {
        return menge;
    }

    public Date getVerfallsdatum() {
        return verfallsdatum;
    }

    public double getPreis() {
        return preis;
    }

    public double getBestandvonBesitzer(String name) {
        if (besitzermenge != null && besitzermenge.containsKey(name)){
        return besitzermenge.get(name);
        }else {return 0.0;}
    }

    public double getMinBestand(String name) {
        if (minBestand != null && minBestand.containsKey(name)){
            return minBestand.get(name);
        }else {return 0.0;}
    }

    public String getBarcode() {
        return barcode;
    }

    public String getEinheit() {
        return einheit;
    }

    /**
     * Geringer Bestand melden
     * @param name Benutzer
     * @return True bei zu geringem Bestand
     */
    public boolean geringerBestand(String name) {
        if (getMinBestand(name) < getBestandvonBesitzer(name))
        {
            return true;
        } else {return false;}

    }
/*    //TODO wird nicht benötigt Getter Besitzemenge macht das schon ?????
    public double bestandVonBesitzer(String name){
        return 0.0;
    }
*/
    public boolean neuerEinkauf(){
       setMenge(this.menge + 1.0);
        //TODO: Methode neuerEinauf in Klasse Produkt ausarbeiten
        return false;
    }

    public boolean verbrauchen(){
      if (this.menge != 0){
        setMenge(this.menge - 1.0);
      return true;
      }else {return false;}
    }

    public void nutzerBenachrichtigen(){

        //TODO: Methode nutzerBenachrichtigen in Klasse Produkt ausarbeiten (Notification)
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
    /**
     * Barcode vom Produkt entfernen
     * @param removeBarcode setzt den Barcode des Produktes auf ""
     */
    public void removeBarcode(String removeBarcode) {
        barcode = "";
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