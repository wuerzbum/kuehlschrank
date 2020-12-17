package manuel.de.kuehlschrankinventar.holder;

import java.util.ArrayList;
import java.util.TreeMap;

public class Einkaufsliste {
    /*
        Aufbau der Treemap:
            äußere Map:
                Key = Benutzername
                value = zugehörige Treemap zum Benutzer
            innere Map:
                key = Produktname
                value = Menge, wie viel von diesem Produkt gekauft werden soll
     */
    private TreeMap<String, TreeMap<String, Double> > einkaufsprodukte;

    public void produktHinzufuegen(String cgString, int cgInt){
        //TODO: Methode produktHinzufuegen Klasse Einkaufliste ausarbeiten
        /*
            Übergabeparameter müssen angepasst werden
            Man benötigt den Benutzernamen, den Produktnamen und die Menge, wie viel eingekauft werden soll

            Dies dann der Treemap hinzufügen
            neue äußere Treemap erstellen, wenn noch keine vorhanden
            Wenn Produkt bei dem jeweiligen Benutzer nicht vorhanden, dann hinzufügen
            Ansonsten Menge anpassen
         */
    }
    public void produktEntfernen(String cgString){
        //TODO: Methode produktEntfernen Klasse Einkaufliste ausarbeiten
        /*
            Übergabeparameter anpassen
            Benutzername + Produktname benötigt
            anhand dieser Parameter das Produkt aus der Liste löschen
            -> wenn Produkt automatisch hinzugefügt wurde, abfragen, ob mindestbestand geändert werden soll
         */
    }
    public void mengeAnpassen(String cgString, int cgInt){
        //TODO: Methode mengeAnpassen Klasse Einkaufliste ausarbeiten
        /*
            Übergabeparameter anpassen
            Benutzername + Produktname + menge benötigt

            -> in Treemap die Menge anpassen, die eingekauft werden soll
         */
    }
    public void produktAbgehagt(String cgString, boolean cgBoolean){
        //TODO: Methode produktAbgehagt Klasse Einkaufliste ausarbeiten
        /*
            Übergabeparameter anpassen
            Benutzername + Produktname + double (kosten) + boolean (haken gesetzt oder gelöscht)  benötigt

            -> Menge in Produktbestand anpassen (preis auch übergeben) und Benutzer zuordnen
            wenn haken gesetzt, dann addieren, wenn gelöscht, dann subtrahieren!
            Menge anhand Treemap auslesen

            Ausgabe bei Benutzer hinzufügen
         */
    }
}
