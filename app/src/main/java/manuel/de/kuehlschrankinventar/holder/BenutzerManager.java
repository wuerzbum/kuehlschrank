package manuel.de.kuehlschrankinventar.holder;

import java.util.TreeMap;
import manuel.de.kuehlschrankinventar.datenbank.DefPref;

public class BenutzerManager {

    private TreeMap<String, Benutzer> benutzer;

    public void Benutzermanager(DefPref cgPref){ //CG: Funktion ohne Rückgabewert (void)?
        //TODO: Methode Benutzermanager in Klasse BenutzerManager ausarbeiten
    }
    private void benutzerInitialisieren(){
        //TODO: Methode benutzerInitialisieren in Klasse BenutzerMnager ausarbeiten
    }
    private void benutzerSpeichern(){
        //TODO: Methode benutzerSpeichern in Klasse BenutzerMnager ausarbeiten
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
