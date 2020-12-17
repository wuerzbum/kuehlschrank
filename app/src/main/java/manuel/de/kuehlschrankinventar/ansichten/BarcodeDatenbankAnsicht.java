package manuel.de.kuehlschrankinventar.ansichten;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.activity.MainActivity;

public class BarcodeDatenbankAnsicht extends MyFragmentAnsicht {

    private MainActivity activity;
    private ListView barcodeDatenbankenAnsicht;
    private SearchView searchView;
    //TODO adapter anpassen
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.ansicht_barcode_datenbank, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        activity = ((MainActivity)  getActivity());

        if (activity != null) {
            activity.setTitle(activity.getString(R.string.barcode_datenbank));
        }
        initUI();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_barcode, menu);

        MenuItem filterItem = menu.findItem(R.id.filter);
        SearchManager searchManager = (SearchManager) this.getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = null;
        if (filterItem != null) {
            searchView = (SearchView) filterItem.getActionView();
            if (searchView != null) {
                if (searchManager != null) {
                    searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
                }
                searchView.setSubmitButtonEnabled(false);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        try {
                            adapter.getFilter().filter(s);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                });
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.scanBarcode) {
            //TODO activity.zeigeScanAnsicht();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        //TODO initUI
        try {
            FloatingActionButton produktMitBarcodeErstellen = requireView().findViewById(R.id.fab);
            ListView listenAnzeige = requireView().findViewById(R.id.list);
            requireView().findViewById(R.id.kein_element_vorhanden).setVisibility(View.GONE);

            if (listenAnzeige.getChildCount() == 0) {
                requireView().findViewById(R.id.kein_element_vorhanden).setVisibility(View.VISIBLE);
            }

            produktMitBarcodeErstellen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO siehe Toast Text
                    neuenBarcodeHinzufuegen();
                    Toast.makeText(activity, "Produkt mit Barcode erstellen (zuerst Barcode einscannen und danach Produktdialog mit eingelesenem Barcode starten)", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void initBarcodeListe(){
        //TODO: Methode Programmieren
        /*
            Liste von Barcodes initialisieren
            erhält man durch Main activity getInventar -> getBarcodes -> letzte Funktion muss noch erstellt werden!
            --> Liste in privaten variablen (oben) abspeichern, damit andere Methoden darauf zugreifen können
         */
    }

    private void neuenBarcodeHinzufuegen(){
        //TODO: Methode Programmieren
        /*
            Diese Methode wird durch klick auf den Button ausgelöst
            Es soll ein neues Produkt erstellt werden, bei dem zuerst der Barcode eingescannt wird, bevor weitere Parameter eingestellt werden können.
         */
    }

    private void listenerNeuerBarcode(){
        //TODO: Methode Programmieren
        /*
            Die Liste muss neu geladen werden, wenn ein neues Produkt hinzugefügt wurde
         */
    }

    @Override
    public void update() {
        //TODO update Fragment
        /*
            Alle Listen aktualisieren -> Wenn z.B. aus anderer Ansicht zurück gekehrt wird,
            sollte diese Methode noch aufgerufen werden
         */
    }
}