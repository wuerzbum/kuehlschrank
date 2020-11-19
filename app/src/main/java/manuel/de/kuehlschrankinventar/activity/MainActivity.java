package manuel.de.kuehlschrankinventar.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts;
import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticStrings;
import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.ansichten.BarcodeDatenbankAnsicht;
import manuel.de.kuehlschrankinventar.ansichten.BenutzerAnsicht;
import manuel.de.kuehlschrankinventar.ansichten.EinkaufslistenAnsicht;
import manuel.de.kuehlschrankinventar.ansichten.Einstellungen;
import manuel.de.kuehlschrankinventar.ansichten.InventarAnsicht;
import manuel.de.kuehlschrankinventar.ansichten.ScanAnsicht;
import manuel.de.kuehlschrankinventar.datenbank.DefPref;
import manuel.de.kuehlschrankinventar.holder.BenutzerManager;
import manuel.de.kuehlschrankinventar.holder.Einkaufsliste;
import manuel.de.kuehlschrankinventar.holder.Inventar;

import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.*;


public class MainActivity extends AppCompatActivity {

    private FragmentTransaction ft;
    private DefPref prefs;
    private Inventar inventar;
    private Einkaufsliste einkaufsliste;
    private BenutzerManager benutzerManager;
    private int aktuelleAnsicht = AUSGEWAEHLT_INVENTAR_ANSICHT;
    private int vorherigeAnsicht = DEFAULT;

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

        zeigeAnsicht(AUSGEWAEHLT_INVENTAR_ANSICHT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ANFRAGE_KAMERA_BERECHTIGUNG:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        //if (aktuelleAnsicht == AUSGEWAEHLT_SCAN_ANSICHT) {
                            MainActivity.this.zeigeScanAnsicht();
                        //}
                    }
                } else {
                    zeigeAnsicht(aktuelleAnsicht);
                    Toast.makeText(this, "Für die ScanAnsicht wird die Kamera Berechtigung benötigt!", Toast.LENGTH_SHORT).show(); // TODO Text
                }
                break;
        }
    }

    public void zeigeAnsicht(int ansicht) {
        if (ansicht == vorherigeAnsicht){
            vorherigeAnsicht = DEFAULT;
        }else {
            vorherigeAnsicht = aktuelleAnsicht;
        }

        switch (ansicht) {
            case AUSGEWAEHLT_SCAN_ANSICHT:
                zeigeScanAnsicht();
                break;

            case AUSGEWAEHLT_BENUTZER_ANSICHT:
                zeigeBenutzerAnsicht();
                break;

            case AUSGEWAEHLT_BARCODE_DATENBANK_ANSICHT:
                zeigeBarcodeDatenbankAnsicht();
                break;

            case AUSGEWAEHLT_EINKAUSLISTEN_ANSICHT:
                zeigeEinkaufslistenAnsicht();
                break;

            case AUSGEWAEHLT_EINSTELLUNGS_ANSICHT:
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
        aktuelleAnsicht = AUSGEWAEHLT_INVENTAR_ANSICHT;
        ft.commit();
    }

    private void zeigeScanAnsicht() {
        /*
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
        ft.commit();*/
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, ANFRAGE_KAMERA_BERECHTIGUNG);
        } else {
            Intent startScanAnsicht = new Intent(MainActivity.this, ScanAnsicht.class);
            startActivityForResult(startScanAnsicht, AUSGEWAEHLT_SCAN_ANSICHT);
        }
    }

    private void zeigeBenutzerAnsicht(){
        if (benutzer == null) {
            benutzer = new BenutzerAnsicht();
        }
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, benutzer);
        aktuelleAnsicht = AUSGEWAEHLT_BENUTZER_ANSICHT;
        ft.commit();
    }

    private void zeigeBarcodeDatenbankAnsicht(){
        if (barcodeDatenbank == null) {
            barcodeDatenbank = new BarcodeDatenbankAnsicht();
        }
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, barcodeDatenbank);
        aktuelleAnsicht = AUSGEWAEHLT_BARCODE_DATENBANK_ANSICHT;
        ft.commit();
    }

    private void zeigeEinkaufslistenAnsicht(){
        if (einkaufslistenansicht == null) {
            einkaufslistenansicht = new EinkaufslistenAnsicht();
        }
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, einkaufslistenansicht);
        aktuelleAnsicht = AUSGEWAEHLT_EINKAUSLISTEN_ANSICHT;
        ft.commit();
    }

    private void zeigeEinstellungen(){
        if (einstellungen == null) {
            einstellungen = new Einstellungen();
        }
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, einstellungen);
        aktuelleAnsicht = AUSGEWAEHLT_EINSTELLUNGS_ANSICHT;
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (vorherigeAnsicht == DEFAULT){
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
            benutzerManager = new BenutzerManager(prefs);
        }
        return benutzerManager;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case AUSGEWAEHLT_SCAN_ANSICHT:
                //TODO data auswerten --> gescannter Code
                if (resultCode == StaticInts.RESULT_OK) {
                    try {
                        if (data != null) {
                            Toast.makeText(this, data.getStringExtra(StaticStrings.BARCODE), Toast.LENGTH_SHORT).show();
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        //kein Barcode gefunden
                    }
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void failedInitUI() {
        //TODO wenn UI nicht geladen werden konnte
    }
}