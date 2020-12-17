package manuel.de.kuehlschrankinventar.holder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.TreeMap;

import manuel.de.kuehlschrankinventar.datenbank.DefPref;

import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.*;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticStrings.*;

public class Inventar {

    private DefPref prefs;
    /*
        Aufbau Treemap:
            key = String = Barcode des Produktes
            value = zum Barcode hinterlegtes Produkt
     */
    private TreeMap<String, Produkt> barcodes;
    /*
        Aufbau Treemap:
            key = String = Name des Produktes
            value = zum Namen hinterlegtes Produkt
     */
    private TreeMap<String, Produkt> produkte;

    public Inventar(DefPref prefs) {
        this.prefs = prefs;

        produkteInitialisieren();
    }

    public boolean exisitiertProduktBarcode(String barcode) {
        /*
            überprüfen, ob der Barcode bereits existiert
         */
        return barcodes != null && barcodes.containsKey(barcode);
    }

    public Produkt getProduktMitBarcode(String barcode) {
        /*
            Produkt anhand des Barcodes zurückgeben
            -> Treemap barcodes
         */
        return barcodes.get(barcode);
    }

    public boolean existiertProduktName(String produktname) {
        /*
            überprüfen, ob der Produktname bereits existiert
         */
        return produkte != null && produkte.containsKey(produktname);
    }

    public Produkt getProduktMitName(String produktname) {
        /*
            Produkt anhand des Produktnamens zurück geben
            -> Treemap produkte
         */
        return produkte.get(produktname);
    }

    private void produkteInitialisieren() {
        // Produkte aus der Datenbank laden
        try {
            String savedString = prefs.getString(HINTERLEGTE_PRODUKTE, null);
            if (savedString != null) {
                JSONArray loadedProdukte = new JSONArray(savedString);

                switch (loadedProdukte.getInt(0)) {
                    case SPEICHER_VERSION_1:
                        produkteLadenVersion1(loadedProdukte);
                        break;
                    default:
                        //TODO default ausführen
                }
            } else {
                //TODO keine Produkte gefunden
            }
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void produkteLadenVersion1(JSONArray arr) throws JSONException {
        // Ladeversion 1
        barcodes = new TreeMap<>();
        produkte = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 1; i<arr.length(); i++) {
            Produkt tempProdukt = new Produkt(arr.getString(i));
            produkte.put(tempProdukt.getName(), tempProdukt);
            if (tempProdukt.hatBarcode()) {
                barcodes.put(tempProdukt.getBarcode(), tempProdukt);
            }
        }

        if (barcodes.size() == 0) {
            barcodes = null;
        }
    }

    private void produkteSpeichern() {
        //Produkte in Datenbank speichern
        JSONArray tempList = new JSONArray();
        tempList.put(SPEICHER_VERSION_1);
        for (Produkt produkt : produkte.values()) {
            tempList.put(produkt.getSaveString());
        }

        prefs.setString(HINTERLEGTE_PRODUKTE, tempList.toString());
    }

    public int checkProduktExisitiert(Produkt produkt) {
        int returnState = OK;
        if (produkt.getName().equals("")) {
            returnState |= NAME_IST_LEER;
        }
        if (existiertProduktName(produkt.getName())) {
            returnState |= NAME_IST_BEREITS_VORHANDEN;
        }
        if (produkt.hatBarcode()) {
            if (exisitiertProduktBarcode(produkt.getBarcode())) {
                returnState |= BARCODE_IST_BEREITS_VORHANDEN;
            }
        }
        if (returnState != OK) {
            return returnState;
        }

        return returnState;
    }

    public int neuesProdukt(Produkt neuesProdukt) {
        /*
            neues Produkt der Treemap hinzufügen
            Wenn kein barcode, dann nur der Map "produkte" ansonsten auch der Map "barcodes"
            am ende speichern!
         */
        if (produkte == null) {
            produkte = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        }
        if (barcodes == null) {
            barcodes = new TreeMap<>();
        }

        int returnState = checkProduktExisitiert(neuesProdukt);
        if (returnState != OK) {
            return returnState;
        }

        produkte.put(neuesProdukt.getName(), neuesProdukt);
        if (neuesProdukt.hatBarcode()) {
            barcodes.put(neuesProdukt.getBarcode(), neuesProdukt);
        }

        produkteSpeichern();

        return returnState;
    }

    private int produktBearbeiten(Produkt altesProdukt, Produkt neuesProdukt) {
        //TODO produkt bearbeiten
        /*
            Parameter: altesProdukt + neuesProdukt
            anhand des alten Produkts die zwei Maps (barcodes und produkte) anpassen
            wenn barcode gelöscht wurde, dann aus der treemap barcodes löschen
            wenn name geändert, dann in Einkaufsliste anpassen

            -> speichern!
         */
        return 0; //CG: geändert von void-Funktion zu int laut Ausarbeitung 12.11.
    }

    private boolean produktEntfernen(Produkt produkt) {
        //TODO produkt entfernen
        /*
            Produkt aus beiden Treemaps löschen
            Ebenso aus der Einkaufsliste löschen (für alle Benutzer)

            -> speichern!
         */
        return false;
    }

    public ArrayList<Produkt> getProduktArrayList() {
        ArrayList<Produkt> produktListe = new ArrayList<>();
        if (produkte != null) {
            produktListe.addAll(produkte.values());
        }
        return produktListe;
    }
}