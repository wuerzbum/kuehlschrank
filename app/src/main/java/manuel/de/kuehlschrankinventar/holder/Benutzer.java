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

    public Benutzer(String cgString, double cgDouble){ //CG: Funktion ohne Rückgabewert (void)?
        //TODO: Methode Benutzer in Klasse Benutzer ausarbeiten
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
