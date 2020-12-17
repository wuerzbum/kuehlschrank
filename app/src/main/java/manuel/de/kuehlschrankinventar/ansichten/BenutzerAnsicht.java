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

public class BenutzerAnsicht extends MyFragmentAnsicht {

    private MainActivity activity;
    private ListView benutzerListenAnsicht;
    private SearchView searchView;
    //TODO adapter anpassen
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.ansicht_benutzer, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        activity = ((MainActivity)  getActivity());

        if (activity != null) {
            activity.setTitle(activity.getString(R.string.benutzer_verwaltung));
        }
        initUI();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_benutzer, menu);

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
            activity.zeigeScanAnsicht();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        //TODO InitUI
        try {
            FloatingActionButton neuenBenutzerAnlegen = requireView().findViewById(R.id.fab);
            ListView listenAnzeige = requireView().findViewById(R.id.list);
            requireView().findViewById(R.id.kein_element_vorhanden).setVisibility(View.GONE);

            if (listenAnzeige.getChildCount() == 0) {
                requireView().findViewById(R.id.kein_element_vorhanden).setVisibility(View.VISIBLE);
            }

            neuenBenutzerAnlegen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO siehe Toast Text
                    neuenBenutzerHinzufuegen();
                    Toast.makeText(activity, "Benutzer erstellen (Benutzererstellungsdialog starten)", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void initBenutzerListe(){
        //TODO: Methode Programmieren
        /*
            Die Liste der Benutzer initialisieren und in einer privaten variablen abspeichern.
            Erhältlich durch mainActivity -> BenutzerManager -> getBenutzerListe (letzte Funktion fehlt noch)
         */
    }

    private void neuenBenutzerHinzufuegen(){
        //TODO: Methode Programmieren
        /*
            Dialog starten, in dem ein neuer Benutzer hinzugefügt werden kann
         */
    }

    private void listenerBenutzer(){
        //TODO: Methode Programmieren
        /*
            Wenn neuer Benutzer hinzugefügt wurde, dann das Layout aktualisieren -> evtl. ersetzbar durch update() Funktion
         */
    }

    @Override
    public void update() {
        //TODO update Fragment
        /*
            Alle Listen aktualisieren
         */
    }
}