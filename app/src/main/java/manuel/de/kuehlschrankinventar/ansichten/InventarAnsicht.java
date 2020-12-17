package manuel.de.kuehlschrankinventar.ansichten;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.Interfaces;
import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts;
import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.activity.MainActivity;
import manuel.de.kuehlschrankinventar.adapter.ArrayAdapterInventarAnsicht;
import manuel.de.kuehlschrankinventar.dialog.DialogBenutzer;
import manuel.de.kuehlschrankinventar.dialog.DialogListView;
import manuel.de.kuehlschrankinventar.dialog.DialogProdukt;
import manuel.de.kuehlschrankinventar.holder.Produkt;

public class InventarAnsicht extends MyFragmentAnsicht {

    private MainActivity activity;
    private ListView produktListenAnsicht;
    private SearchView searchView;
    //TODO adapter anpassen
    private ArrayAdapterInventarAnsicht adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.ansicht_inventar, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        activity = ((MainActivity) getActivity());

        if (activity != null) {
            activity.setTitle(activity.getString(R.string.inventar));
        }
        initUI();
        initButtons();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_inventar, menu);

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


        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        //TODO initUI
        try {
            ListView listenAnzeige = requireView().findViewById(R.id.list);
            requireView().findViewById(R.id.kein_element_vorhanden).setVisibility(View.GONE);

            initProduktListe();

            if (listenAnzeige.getAdapter().getCount() == 0) {
                requireView().findViewById(R.id.kein_element_vorhanden).setVisibility(View.VISIBLE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        try {
            FloatingActionButton produktErstellen = requireView().findViewById(R.id.fab);

            produktErstellen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListenerNeuesProdukt(null);
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void initProduktListe(){
        //TODO: Methode Programmieren
        ListView listenAnzeige = requireView().findViewById(R.id.list);
        adapter = new ArrayAdapterInventarAnsicht(activity, activity.getInventar().getProduktArrayList(), new Interfaces.onClickListener() {
            @Override
            public void onClick(Produkt produkt) {

            }

            @Override
            public void onLongClicked(final Produkt produkt) {
                String listViewTitel = "Produkt " + produkt.getName() + " bearbeiten";
                String listViewBeschreibung = "Wählen Sie eine Aktion aus";
                ArrayList<String> itemList = new ArrayList<>();
                itemList.add("Gesamt bearbeiten");
                itemList.add("Eingekauft");
                itemList.add("Verzehrt");
                itemList.add("Barcode hinzufügen");
                itemList.add("Löschen");
                new DialogListView(activity, listViewTitel, listViewBeschreibung, itemList, new Interfaces.listViewDialogListener() {
                    @Override
                    public void selected(int selectedItem) {
                        switch (selectedItem) {
                            case 0:
                                //Gesamt bearbeiten
                                ListenerNeuesProdukt(produkt);
                                break;

                            case 1:
                                //TODO Eingekauft
                                Toast.makeText(activity, "EK", Toast.LENGTH_SHORT).show();
                                break;

                            case 2:
                                //TODO Verzehrt
                                Toast.makeText(activity, "VZ", Toast.LENGTH_SHORT).show();
                                break;

                            case 3:
                                //TODO Barcode hinzufügen
                                break;

                            case 4:
                                //Löschen
                                if (activity.getInventar().produktEntfernen(produkt)) {
                                    initUI();
                                }
                                break;
                        }
                    }
                }).show();
            }
        });

        listenAnzeige.setAdapter(adapter);
    }

    private void neuesProduktHinzufuegen(Produkt produkt){
        if (activity.getInventar().neuesProdukt(produkt) != StaticInts.OK) {
            //TODO Text anpassen
            Toast.makeText(activity, "Konnte nicht gespeichert werden!", Toast.LENGTH_SHORT).show();
        } else {
            initUI();
        }
    }

    private void produktUeberarbeiten(Produkt neuesProdukt, Produkt altesProdukt) {
        if (activity.getInventar().produktBearbeiten(altesProdukt, neuesProdukt) != StaticInts.OK) {
            //TODO Text
            Toast.makeText(activity, "Überarbeitung hat nicht funktioniert", Toast.LENGTH_SHORT).show();
        } else {
            initUI();
        }
    }

    private void ListenerNeuesProdukt(Produkt produkt){
        final AlertDialog dialog = new DialogProdukt(activity, produkt, activity.getInventar(), new Interfaces.produktDialogListener() {
            @Override
            public void produktSpeichern(Produkt neuesProdukt, Produkt altesProdukt) {
                if (altesProdukt == null) {
                    if (neuesProdukt != null) {
                        neuesProduktHinzufuegen(neuesProdukt);
                    }
                } else {
                    produktUeberarbeiten(neuesProdukt, altesProdukt);
                }
            }

            @Override
            public void getBarcodeScan(final Interfaces.getStringListener listener) {
                activity.zeigeScanAnsicht(new Interfaces.getStringListener() {
                    @Override
                    public void getString(String string) {
                        listener.getString(string);
                    }
                });
            }
        });
        dialog.show();
    }

    @Override
    public void update() {
        //TODO update Fragment
    }
}

