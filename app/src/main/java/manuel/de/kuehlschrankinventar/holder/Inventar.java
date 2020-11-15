package manuel.de.kuehlschrankinventar.holder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.TreeMap;

import manuel.de.kuehlschrankinventar.datenbank.DefPref;

import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.BARCODE_IST_BEREITS_VORHANDEN;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.NAME_IST_BEREITS_VORHANDEN;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.NAME_IST_LEER;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.OK;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticStrings.HINTERLEGTE_PRODUKTE;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticStrings.SPEICHER_VERSION_1;

public class Inventar {

    private DefPref prefs;
    private TreeMap<String, Produkt> barcodes;
    private TreeMap<String, Produkt> produkte;

    //<"Milch", Produkt>
    //<"Hafer", Produkt>
    //<"Käse", Produkt>
    //<"Apfel", Produkt>

    public Inventar(DefPref prefs) {
        this.prefs = prefs;

        produkteInitialisieren();
    }

    public boolean exisitiertProduktBarcode(String barcode) { //CG: public (laut Ausarbeitung private)?
        return barcodes != null && barcodes.containsKey(barcode);
    }

    public Produkt getProduktMitBarcode(String barcode) {
        return barcodes.get(barcode);
    }

    public boolean existiertProduktName(String produktname) { //CG: public (laut Ausarbeitung private)?
        return produkte != null && produkte.containsKey(produktname);
    }

    public Produkt getProduktMitName(String produktname) {
        return produkte.get(produktname);
    }

    private void produkteInitialisieren() {
        try {
            JSONArray loadedProdukte = new JSONArray(prefs.getString(HINTERLEGTE_PRODUKTE, null));

            if (loadedProdukte.length() > 1) {
                switch (loadedProdukte.getString(0)) {
                    case SPEICHER_VERSION_1:
                        produkteLadenVersion1(loadedProdukte);
                        break;
                    default:
                        //TODO default ausführen
                }
            } else {
                //TODO keine Produkte gefunden
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void produkteLadenVersion1(JSONArray arr) throws JSONException { //CG: TODO Methode kommt nicht in Ausarbitung 12.11. vor!
        barcodes = new TreeMap<>();
        produkte = new TreeMap<>();
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
        ArrayList<String> tempList = new ArrayList<>();
        tempList.add(SPEICHER_VERSION_1);
        for (Produkt produkt : produkte.values()) {
            tempList.add(produkt.getSaveString());
        }

        prefs.setString(HINTERLEGTE_PRODUKTE, new JSONArray(tempList).toString());
    }

    public int neuesProdukt(Produkt neuesProdukt) {
        int returnState = OK;
        if (produkte == null) {
            produkte = new TreeMap<>();
        }
        if (neuesProdukt.getName().equals("")) {
            returnState |= NAME_IST_LEER;
        }
        if (existiertProduktName(neuesProdukt.getName())) {
            returnState |= NAME_IST_BEREITS_VORHANDEN;
        }
        if (neuesProdukt.hatBarcode()) {
            if (barcodes == null) {
                barcodes = new TreeMap<>();
            }
            if (exisitiertProduktBarcode(neuesProdukt.getBarcode())) {
                returnState |= BARCODE_IST_BEREITS_VORHANDEN;
            }
        }
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
        return 0; //CG: geändert von void-Funktion zu int laut Ausarbeitung 12.11.
    }

    private boolean produktEntfernen(Produkt produkt) {
        //TODO produkt entfernen
        return false; //CG: geändert von void-Funktion zu boolean laut Ausarbeitung 12.11.
    }
}