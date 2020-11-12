package manuel.de.kuehlschrankinventar.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.Interfaces;
import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts;
import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.ansichten.*;
import manuel.de.kuehlschrankinventar.datenbank.DefPref;
import manuel.de.kuehlschrankinventar.holder.BenutzerManager;
import manuel.de.kuehlschrankinventar.holder.Einkaufsliste;
import manuel.de.kuehlschrankinventar.holder.Inventar;
import manuel.de.kuehlschrankinventar.ansichten.ScanAnsicht;


public class MainActivity extends AppCompatActivity {

    private FragmentTransaction ft;
    private DefPref prefs;
    private Inventar inventar;
    private Einkaufsliste einkaufsliste;
    private BenutzerManager benutzerManager;
    private int aktuelleAnsicht = StaticInts.AUSGEWAEHLT_INVENTAR_ANSICHT;
    private int vorherigeAnsicht = StaticInts.DEFAULT;

    private BarcodeDatenbankAnsicht barcodeDatenbank;
    private BenutzerAnsicht benutzer;
    private EinkaufslistenAnsicht einkaufslistenansicht;
    private Einstellungen einstellungen;
    private InventarAnsicht inventarAnsicht;
    private ScanAnsicht scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zeigeAnsicht(StaticInts.AUSGEWAEHLT_INVENTAR_ANSICHT);
    }

    public void zeigeAnsicht(int ansicht) {
        if (ansicht == vorherigeAnsicht){
            vorherigeAnsicht = StaticInts.DEFAULT;
        }else {
            vorherigeAnsicht = aktuelleAnsicht;
        }

        switch (ansicht) {
            case StaticInts.AUSGEWAEHLT_SCAN_ANSICHT:
                zeigeScanAnsicht();
                break;

            case StaticInts.AUSGEWAEHLT_BENUTZER_ANSICHT:
                zeigeBenutzerAnsicht();
                break;

            case StaticInts.AUSGEWAEHLT_BARCODE_DATENBANK_ANSICHT:
                zeigeBarcodeDatenbankAnsicht();
                break;

            case StaticInts.AUSGEWAEHLT_EINKAUSLISTEN_ANSICHT:
                zeigeEinkaufslistenAnsicht();
                break;

            case StaticInts.AUSGEWAEHLT_EINSTELLUNGS_ANSICHT:
                zeigeEinstellungen();
                break;

            default:
                zeigeInventarAnsicht();
        }
    }

    private void zeigeInventarAnsicht() {
        if (inventarAnsicht == null) {
            inventarAnsicht = new InventarAnsicht();
        }
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, inventarAnsicht);
        aktuelleAnsicht = StaticInts.AUSGEWAEHLT_INVENTAR_ANSICHT;
        ft.commit();
    }

    private void zeigeScanAnsicht() {
        if (scanner == null) {
            scanner = new ScanAnsicht(new Interfaces.information() {
                @Override
                public void inform(int information) {
                    //TODO Wenn informationen ankommen
                    switch (information) {
                        case StaticInts.KAMERA_FREIGABE:
                            zeigeAnsicht(StaticInts.AUSGEWAEHLT_INVENTAR_ANSICHT);
                            break;
                    }
                }
            });
        }

        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, scanner);
        aktuelleAnsicht = StaticInts.AUSGEWAEHLT_SCAN_ANSICHT;
        ft.commit();
    }

    private void zeigeBenutzerAnsicht(){
        if (benutzer == null) {
            benutzer = new BenutzerAnsicht();
        }
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, benutzer);
        aktuelleAnsicht = StaticInts.AUSGEWAEHLT_BENUTZER_ANSICHT;
        ft.commit();
    }

    private void zeigeBarcodeDatenbankAnsicht(){
        if (barcodeDatenbank == null) {
            barcodeDatenbank = new BarcodeDatenbankAnsicht();
        }
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, barcodeDatenbank);
        aktuelleAnsicht = StaticInts.AUSGEWAEHLT_BARCODE_DATENBANK_ANSICHT;
        ft.commit();
    }

    private void zeigeEinkaufslistenAnsicht(){
        if (einkaufslistenansicht == null) {
            einkaufslistenansicht = new EinkaufslistenAnsicht();
        }
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, einkaufslistenansicht);
        aktuelleAnsicht = StaticInts.AUSGEWAEHLT_EINKAUSLISTEN_ANSICHT;
        ft.commit();
    }

    private void zeigeEinstellungen(){
        if (einstellungen == null) {
            einstellungen = new Einstellungen();
        }
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, einstellungen);
        aktuelleAnsicht = StaticInts.AUSGEWAEHLT_EINSTELLUNGS_ANSICHT;
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (vorherigeAnsicht == StaticInts.DEFAULT){
            super.onBackPressed();
        }else {
            zeigeAnsicht(vorherigeAnsicht);
        }
    }

    public DefPref getPrefs() {
        if (prefs == null) {
            prefs = new DefPref(MainActivity.this);
        }
        return prefs;
    }

    public Inventar getInventar() {
        if (inventar == null) {
            inventar = new Inventar(getPrefs());
        }
        return inventar;
    }

    public Einkaufsliste getEinkaufsliste() {
        if (einkaufsliste == null) {
            einkaufsliste = new Einkaufsliste();
        }
        return einkaufsliste;
    }

    public BenutzerManager getBenutzerManager(){
        if (benutzerManager == null) {
            benutzerManager = new BenutzerManager();
        }
        return benutzerManager;
    }

    public void failedInitUI() {
        //TODO wenn UI nicht geladen werden konnte
    }
}