package manuel.de.kuehlschrankinventar.holder;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
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
    /*
        TreeMap aufbau:
            key = String = Benutzername
            value = double = menge, die der Benutzer besitzt
     */
    private TreeMap<String, Double> besitzermenge;
    /*
        TreeMap aufbau:
            key = String = Benutzername
            value = double = menge, die der Benutzer als mindestbestand definiert hat
     */
    private TreeMap<String, Double>minBestand;
    private String barcode;
    private String einheit;
    private SimpleDateFormat sDF = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);


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
         //neues Produkt erstekkeb
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
         // Produkt anhand der Datenbank laden
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
     * Gesamtmenge ändern
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
            this.verfallsdatum = newVerfallsdatum;
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
            this.preis = newPreis;
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
            this.barcode = "";
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

    public boolean neuerEinkauf(){
        /*
            benötigte Parameter:
            menge, benutzername, preis
            -> gesamtmenge anpassen
            -> menge von Besitzer anpassen
            -> minPreis, maxPreis, durchschnittsPreis anpassen

            Methode vermutlich besser in Inventar geeignet mit weiterem Übergabeparameter "Produktname"
            -> danach speichern
         */
       setMenge(this.menge + 1.0);
        //TODO: Methode neuerEinauf in Klasse Produkt ausarbeiten
        return false;
    }

    public boolean verbrauchen(){
        /*
            benötigte Parameter:
            menge, benutzername
            -> gesamtmenge anpassen
            -> menge von Besitzer anpassen

            Methode vermutlich besser in Inventar geeignet mit weiterem Übergabeparameter "Produktname"
            -> danach speichern
         */
        if (this.menge != 0) {
            setMenge(this.menge - 1.0);
            return true;
        } else {
            return false;
        }
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
        // save String für Datenbank
        try {
            JSONObject saveObj = new JSONObject();

            if (name != null) {
                saveObj.put(PRODUKTNAME, name);
            }
            if (barcode != null) {
                saveObj.put(BARCODE, barcode);
            }
            if (menge != 0) {
                saveObj.put(MENGE, menge);
            }
            if (verfallsdatum != null) {
                saveObj.put(VERFALLSDATUM, sDF.format(verfallsdatum));
            }
            if (preis != 0) {
                saveObj.put(PREIS, preis);
            }
            if (einheit != null) {
                saveObj.put(EINHEIT, einheit);
            }
            if (besitzermenge != null) {
                JSONObject tempJSONObject = new JSONObject();
                for (String key : besitzermenge.keySet()) {
                    tempJSONObject.put(key, besitzermenge.get(key));
                }
                if (tempJSONObject.length() > 0) {
                    saveObj.put(BESITERMENGE, tempJSONObject.toString());
                }
            }
            if (minBestand != null) {
                JSONObject tempJSONObject = new JSONObject();
                for (String key : minBestand.keySet()) {
                    tempJSONObject.put(key, minBestand.get(key));
                }
                if (tempJSONObject.length() > 0) {
                    saveObj.put(MINDEST_BESTANDS_MENGE_JE_BESITZER, tempJSONObject.toString());
                }
            }
            return saveObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void mitJSONStringLaden(String jsonString) throws JSONException {
        // laden mit String aus Datenbank
        JSONObject object = new JSONObject(jsonString);
        try {
            setName(object.getString(PRODUKTNAME));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            setBarcode(object.getString(BARCODE));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            setMenge(object.getDouble(MENGE));
        } catch (NullPointerException e) {
            e.printStackTrace();
            setMenge(0);
        }
        try {
            setVerfallsdatum(sDF.parse(object.getString(VERFALLSDATUM)));
        } catch (NullPointerException | ParseException e) {
            e.printStackTrace();
            setVerfallsdatum(null);
        }
        try {
            setPreis(object.getDouble(PREIS));
        } catch (NullPointerException e) {
            e.printStackTrace();
            setPreis(0);
        }
        try {
            setEinheit(object.getString(EINHEIT));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            JSONObject tempJsonObject = new JSONObject(object.getString(BESITERMENGE));
            Iterator<String> keys = tempJsonObject.keys();
            besitzermenge = new TreeMap<>();
            while (keys.hasNext()) {
                String key = keys.next();
                besitzermenge.put(key, tempJsonObject.getDouble(key));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            JSONObject tempJsonObject = new JSONObject(object.getString(MINDEST_BESTANDS_MENGE_JE_BESITZER));
            Iterator<String> keys = tempJsonObject.keys();
            minBestand = new TreeMap<>();
            while (keys.hasNext()) {
                String key = keys.next();
                minBestand.put(key, tempJsonObject.getDouble(key));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}