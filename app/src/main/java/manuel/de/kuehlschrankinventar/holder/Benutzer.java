package manuel.de.kuehlschrankinventar.holder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticStrings.*;

public class Benutzer {
    private String name;
    private ArrayList<Produkt> einkaufsliste;
    private TreeMap<Date, Double> ausgaben;
    private double budget;

    public Benutzer(String cgString, double cgDouble){
        //TODO: Methode Benutzer in Klasse Benutzer ausarbeiten
        /*
            String ist der Benutzername -> dies der privaten Variablen zuordnen
            double ist das Budget -> privater Variablen zurodnen

            einkaufsliste und ausgaben gibt es bei einem neuen Benutzer noch nicht, daher muss das nicht initialisiert werden.
         */
    }

    public Benutzer(String JSONObjectString) {
        if (!JSONObjectString.equals("")) {
            try {
                JSONObject object = new JSONObject(JSONObjectString);
                loadBenutzer(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public void neuerEinkauf(double cgDouble) {
        //TODO: Methode neuerEinkauf in Klasse Benutzer ausarbeiten
        /*
            Einkaufsliste kontrollieren und Produkte, deren Menge nun größer sind als der Mindestbestand von der Liste Streichen
            übergebener double soll der Betrag sein, welcher ausgegeben wurde. Hier der Treemap ausgaben mit neuem Datum als key einfügen.
            -> ist das Datum schon vorhanden (Einkauf am selben Tag) Ausgabewert von dem Tag anpassen
         */
    }

    private void loadBenutzer(JSONObject savedObject) throws JSONException {
        this.name = savedObject.getString(BENUTZERNAME);
        //TODO laden erweitern
    }

    public String getSaveString() {
        try {
            JSONObject saveObj = new JSONObject();
            saveObj.put(BENUTZERNAME, name);
            //TODO saveString erweitern
            return saveObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
