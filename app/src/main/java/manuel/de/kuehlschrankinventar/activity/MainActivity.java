package manuel.de.kuehlschrankinventar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.Interfaces;
import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts;
import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.datenbank.DefPref;
import manuel.de.kuehlschrankinventar.holder.Inventar;
import manuel.de.kuehlschrankinventar.holder.ProduktManager;
import manuel.de.kuehlschrankinventar.scanner.ScannedCodeActivity;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction ft;
    private DefPref prefs;
    private ProduktManager pM;
    public int currentScreen = StaticInts.SELECTED_MAIN_SCREEN;

    private ScannedCodeActivity scannedCodeActivity;
    private MainScreenFragment mainScreenFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showUI(StaticInts.SELECTED_MAIN_SCREEN);
    }

    public void showUI(int screen) {
        switch (screen) {
            case StaticInts.SELECTED_MAIN_SCREEN:
                openMainScreen();
                break;

            case StaticInts.SELECTED_SCAN_SCREEN:
                openScannerScreen();
                break;

            default:
                openMainScreen();
        }
    }

    public void openMainScreen() {
        if (mainScreenFragment == null) {
            mainScreenFragment = new MainScreenFragment();
        }
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, mainScreenFragment);
        currentScreen = StaticInts.SELECTED_MAIN_SCREEN;
        ft.commit();
    }

    public void openScannerScreen() {
        if (scannedCodeActivity == null) {
            scannedCodeActivity = new ScannedCodeActivity(new Interfaces.information() {
                @Override
                public void inform(int information) {
                    //TODO Wenn informationen ankommen
                    switch (information) {
                        case StaticInts.CAMERA_RELEASE:
                            showUI(StaticInts.SELECTED_MAIN_SCREEN);
                            break;
                    }
                }
            });
        }

        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, scannedCodeActivity);
        currentScreen = StaticInts.SELECTED_SCAN_SCREEN;
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        switch (currentScreen) {
            case StaticInts.SELECTED_SCAN_SCREEN:
                showUI(StaticInts.SELECTED_MAIN_SCREEN);
                break;
            default:
                super.onBackPressed();
        }
    }

    public DefPref getPrefs() {
        if (prefs == null) {
            prefs = new DefPref(MainActivity.this);
        }
        return prefs;
    }

    public ProduktManager getProduktManager() {
        if (pM == null) {
            pM = new ProduktManager(getPrefs());
        }
        return pM;
    }

    public void failedInitUI() {
        //TODO wenn UI nicht geladen werden konnte
    }
}