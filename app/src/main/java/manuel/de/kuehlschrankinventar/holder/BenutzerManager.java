package manuel.de.kuehlschrankinventar.holder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.TreeMap;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts;
import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticStrings;
import manuel.de.kuehlschrankinventar.datenbank.DefPref;

public class BenutzerManager {

    private DefPref prefs;
    private TreeMap<String, Benutzer> benutzer;

    public BenutzerManager(DefPref cgPref){
        //TODO: Methode Benutzermanager in Klasse BenutzerManager ausarbeiten
        this.prefs = cgPref;
        benutzerInitialisieren();
    }

    private void benutzerInitialisieren(){
        //TODO: Methode benutzerInitialisieren in Klasse BenutzerMnager ausarbeiten
        try {
            String savedString = prefs.getString(StaticStrings.BENUTZER, null);
            if (savedString != null) {
                JSONArray gespeichertesArray = new JSONArray(savedString);
                switch (gespeichertesArray.getInt(0)) {
                    case StaticInts.SPEICHER_VERSION_1:
                        loadVersion1(gespeichertesArray);
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadVersion1(JSONArray array) throws JSONException {
        benutzer = new TreeMap<>();
        for (int counter = 1; counter < array.length(); counter++) {
            Benutzer tempBenutzer = new Benutzer(array.getString(counter));
            benutzer.put(tempBenutzer.getName(), tempBenutzer);
        }
    }

    private void benutzerSpeichern(){
        //TODO: Methode benutzerSpeichern in Klasse BenutzerMnager ausarbeiten
        JSONArray speicherArray = new JSONArray();
        speicherArray.put(StaticInts.SPEICHER_VERSION_1);
        for (Benutzer aBenutzer : benutzer.values()) {
            speicherArray.put(aBenutzer.getSaveString());
        }
        prefs.setString(StaticStrings.BENUTZER, speicherArray.toString());

    }
    private boolean existiertBenutzeName(String cgString){
        //TODO: Methode existiertBenutzerName in Klasse BenutzerMnager ausarbeiten
        return false;
    }
    public Benutzer getBenutzerMitName(String cgString){
        //TODO: Methode getBenutzerMitName in Klasse BenutzerMnager ausarbeiten
        Benutzer cgBenutzer = null;
        return cgBenutzer;
    }
    public int benutzerAnlegen(Benutzer cgBenutzer){
        //TODO: Methode benutzerAnlegen Klasse BenutzerMnager ausarbeiten
        return 0;
    }
    public int benutzerBearbeiten(Benutzer cgBenutzer1, Benutzer cgBenutzer2){
        //TODO: Methode benutzerBearbeiten Klasse BenutzerMnager ausarbeiten
        return 0;
    }
    public boolean benutzerEntfernen(Benutzer cgBenutzer){
        //TODO: Methode benutzerEntfernen Klasse BenutzerMnager ausarbeiten
        return false;
    }
}
