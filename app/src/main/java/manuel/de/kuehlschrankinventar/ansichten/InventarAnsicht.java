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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts;
import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.activity.MainActivity;

public class InventarAnsicht extends MyFragmentAnsicht {

    private MainActivity activity;
    private ListView produktListenAnsicht;




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
    }

    private void initProduktListe(){
        //TODO: Methode Programmieren
    }

    private void neuesProduktHinzufuegen(){
        //TODO: Methode Programmieren
    }

    private void ListenerNeuesProdukt(){
        //TODO: Methode Programmieren
    }

    @Override
    public void update() {
        //TODO update Fragment
    }
}

