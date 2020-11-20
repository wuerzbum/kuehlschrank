package manuel.de.kuehlschrankinventar.holder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.TreeMap;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts;
import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticStrings;
import manuel.de.kuehlschrankinventar.datenbank.DefPref;

public class BenutzerManager {

    private DefPref prefs;
    /*
        Treemapaufbau:
        Key = String = Name eines Benutzers
        Value = Benutzer = angelegter Benutzer mit dem Namen im key
     */
    private TreeMap<String, Benutzer> benutzer;

    public BenutzerManager(DefPref cgPref){
        this.prefs = cgPref;
        benutzerInitialisieren();
    }

    private void benutzerInitialisieren(){
        // Benutzer aus Datenbank laden
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
        //laden der Benutzer mit der SpeicherVersion 1
        benutzer = new TreeMap<>();
        for (int counter = 1; counter < array.length(); counter++) {
            Benutzer tempBenutzer = new Benutzer(array.getString(counter));
            benutzer.put(tempBenutzer.getName(), tempBenutzer);
        }
    }

    private void benutzerSpeichern(){
        //Abspeichern der vorhandenen Benutzer
        JSONArray speicherArray = new JSONArray();
        speicherArray.put(StaticInts.SPEICHER_VERSION_1);
        for (Benutzer aBenutzer : benutzer.values()) {
            speicherArray.put(aBenutzer.getSaveString());
        }
        prefs.setString(StaticStrings.BENUTZER, speicherArray.toString());

    }
    private boolean existiertBenutzeName(String cgString){
        //TODO: Methode existiertBenutzerName in Klasse BenutzerMnager ausarbeiten
        /*
            Überprüfen, ob Benutername (übergabeparameter) bereits exisitiert  (Treemap)
            -> wenn ja: return true, sonst return false
            -> überprüfen, ob Treemap existiert!
         */
        return false;
    }
    public Benutzer getBenutzerMitName(String cgString){
        //TODO: Methode getBenutzerMitName in Klasse BenutzerMnager ausarbeiten
        /*
            Benutzer anhand des Namens aus der Treemap suchen und zurück geben
            überprüfen, ob Treemap existiert!
         */
        Benutzer cgBenutzer = null;
        return cgBenutzer;
    }
    public int benutzerAnlegen(Benutzer cgBenutzer){
        //TODO: Methode benutzerAnlegen Klasse BenutzerMnager ausarbeiten
        /*
            übergebener Benutzer der Treemap hinzufügen, falls dieser nicht bereits existiert!
            -> Wenn Treemap noch nicht existiert, dann eine neue erstellen.
            -> Wenn Benutzer hinzugefügt wird, dann die Benutzer in der Datenbank speichern (Methode aufrufen)
         */
        return 0;
    }
    public int benutzerBearbeiten(Benutzer cgBenutzer1, Benutzer cgBenutzer2){
        //TODO: Methode benutzerBearbeiten Klasse BenutzerMnager ausarbeiten
        /*
            1 Parameter ist der Alte Benutzer
            1 Parameter ist der geänderte Benutzer

            anhand des alten Benutzernamens den zugehörigen Benuter in der Treemap austauschen
            wenn ausgetauscht, dann in der Datenbank speichern (Methode aufrufen)
            überprüfen, ob in der Einkaufsliste auch geändert werden muss (nur nötig, wenn Benutzername abweicht)
            überprüfen, ob Zuordnung von Produkt zu Benutzer angepasst werden muss (nur nötig, wenn Benutzername abweicht)
         */
        return 0;
    }
    public boolean benutzerEntfernen(Benutzer cgBenutzer){
        //TODO: Methode benutzerEntfernen Klasse BenutzerMnager ausarbeiten
        /*
            Anhand des Benutzernamens diesen Benutzer aus der Treemap löschen
            Wenn ohne Fehler gelöscht, dann in der Datenbank speichern (Methode aufrufen)
            überprüfen, ob in der Einkaufsliste auch gelöscht werden muss (nur nötig, wenn Benutzername abweicht)
            überprüfen, ob Zuordnung von Produkt zu Benutzer gelöscht werden muss (nur nötig, wenn Benutzername abweicht)
            -> bei löschen aus Produktzuordnung: dieses dem Benutzer allgemein hinzufügen? oder ohne zuordnung lassen?
         */
        return false;
    }
}
