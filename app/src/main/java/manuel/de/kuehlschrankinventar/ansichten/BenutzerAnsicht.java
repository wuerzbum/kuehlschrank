package manuel.de.kuehlschrankinventar.ansichten;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts;
import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.activity.MainActivity;

public class BenutzerAnsicht extends MyFragmentAnsicht {

    private MainActivity activity;
    private ListView benutzerListenAnsicht;

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
                    Toast.makeText(activity, "Benutzer erstellen (Benutzererstellungsdialog starten)", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void initBenutzerListe(){
        //TODO: Methode Programmieren
    }

    private void neuenBenutzerHinzufuegen(){
        //TODO: Methode Programmieren
    }

    private void listenerBenutzer(){
        //TODO: Methode Programmieren
    }

    @Override
    public void update() {
        //TODO update Fragment
    }
}