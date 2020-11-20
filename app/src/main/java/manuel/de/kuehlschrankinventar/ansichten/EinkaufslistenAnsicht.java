package manuel.de.kuehlschrankinventar.ansichten;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts;
import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.activity.MainActivity;

public class EinkaufslistenAnsicht extends MyFragmentAnsicht {

    private MainActivity activity;
    private ListView einkaufslistenAnsicht;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.ansicht_einkaufslisten, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        activity = ((MainActivity)  getActivity());

        if (activity != null) {
            activity.setTitle(activity.getString(R.string.einkaufsliste));
        }
        initUI();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.scanBarcode) {
            activity.zeigeScanAnsicht();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        //TODO initUI
        try {
            FloatingActionButton produktZurEinkaufslisteHinzufuegen = requireView().findViewById(R.id.fab);
            ListView listenAnzeige = requireView().findViewById(R.id.list);
            requireView().findViewById(R.id.kein_element_vorhanden).setVisibility(View.GONE);

            if (listenAnzeige.getChildCount() == 0) {
                requireView().findViewById(R.id.kein_element_vorhanden).setVisibility(View.VISIBLE);
            }

            produktZurEinkaufslisteHinzufuegen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO siehe Toast Text
                    weiteresProduktHinzufuegen();
                    Toast.makeText(activity, "Produkt zur Einkaufsliste hinzufügen (Auswahl aus vorhandenen Produkten mit ListViewDialog)", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void initEinkaufsliste(){
        //TODO: Methode Programmieren
        /*
            Liste der Produkte erzeugen, die auf der Einkaufsliste stehen
            Hierzu in einer neuen privaten Variable die Liste speichern
            erhält man durch mainActivity -> getEinkaufsliste -> getListe (letzte Funktion schon definiert?)
         */
    }

    private void weiteresProduktHinzufuegen(){
        //TODO: Methode Programmieren
        /*

         */
    }

    @Override
    public void update() {
        //TODO update Fragment
        /*

         */
    }
}