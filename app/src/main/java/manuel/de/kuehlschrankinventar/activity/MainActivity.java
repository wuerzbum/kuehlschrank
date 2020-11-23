package manuel.de.kuehlschrankinventar.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts;
import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticStrings;
import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.adapter.FragmentPageAdapter;
import manuel.de.kuehlschrankinventar.ansichten.BarcodeDatenbankAnsicht;
import manuel.de.kuehlschrankinventar.ansichten.BenutzerAnsicht;
import manuel.de.kuehlschrankinventar.ansichten.EinkaufslistenAnsicht;
import manuel.de.kuehlschrankinventar.ansichten.Einstellungen;
import manuel.de.kuehlschrankinventar.ansichten.InventarAnsicht;
import manuel.de.kuehlschrankinventar.ansichten.MyFragmentAnsicht;
import manuel.de.kuehlschrankinventar.ansichten.ScanAnsicht;
import manuel.de.kuehlschrankinventar.datenbank.DefPref;
import manuel.de.kuehlschrankinventar.holder.BenutzerManager;
import manuel.de.kuehlschrankinventar.holder.Einkaufsliste;
import manuel.de.kuehlschrankinventar.holder.Inventar;

import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.ANFRAGE_KAMERA_BERECHTIGUNG;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.AUSGEWAEHLT_SCAN_ANSICHT;


public class MainActivity extends AppCompatActivity {

    private DefPref prefs;
    private Inventar inventar;
    private Einkaufsliste einkaufsliste;
    private BenutzerManager benutzerManager;

    private Einstellungen einstellungen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.scanBarcode) {
            zeigeScanAnsicht();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ANFRAGE_KAMERA_BERECHTIGUNG) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    MainActivity.this.zeigeScanAnsicht();
                }
            } else {
                Toast.makeText(this, "Für die ScanAnsicht wird die Kamera Berechtigung benötigt!", Toast.LENGTH_SHORT).show(); // TODO Text
            }
        }
    }

    private void initUI() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        if (tabLayout != null) {
            ArrayList<String> tabNamen = new ArrayList<>();
            tabNamen.add(getString(R.string.ansicht_inventar));
            tabNamen.add(getString(R.string.ansicht_barcode_datenbank));
            tabNamen.add(getString(R.string.ansicht_benutzer));
            tabNamen.add(getString(R.string.ansicht_einkaufsliste));

            for (String tabName : tabNamen) {
                tabLayout.addTab(tabLayout.newTab().setText(tabName));
            }
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            final ViewPager viewPager = findViewById(R.id.placeholder);
            viewPager.removeAllViews();

            ArrayList<MyFragmentAnsicht> fragments = new ArrayList<>();
            fragments.add(new InventarAnsicht());
            fragments.add(new BarcodeDatenbankAnsicht());
            fragments.add(new BenutzerAnsicht());
            fragments.add(new EinkaufslistenAnsicht());

            final FragmentPageAdapter fa = new FragmentPageAdapter(getSupportFragmentManager(), tabNamen, fragments);

            viewPager.setAdapter(fa);
            viewPager.setOffscreenPageLimit(3);

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                    fa.updateFragment(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            Objects.requireNonNull(tabLayout.getTabAt(0)).select();
        }
    }

    public void zeigeScanAnsicht() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, ANFRAGE_KAMERA_BERECHTIGUNG);
        } else {
            Intent startScanAnsicht = new Intent(MainActivity.this, ScanAnsicht.class);
            startActivityForResult(startScanAnsicht, AUSGEWAEHLT_SCAN_ANSICHT);
        }
    }

    private void zeigeEinstellungen(){
        if (einstellungen == null) {
            einstellungen = new Einstellungen();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, einstellungen);
        ft.commit();
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
        if (requestCode == AUSGEWAEHLT_SCAN_ANSICHT) {//TODO data auswerten --> gescannter Code
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
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void failedInitUI() {
        //TODO wenn UI nicht geladen werden konnte
    }
}