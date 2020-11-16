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

public class InventarAnsicht extends Fragment {

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

        activity.setTitle(activity.getString(R.string.inventar));
        initUI();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //TODO falls doch gewünscht, dass man fotografieren kann das folgende wieder "entkommentieren"
            /*case R.id.takePicture:
                startActivity(new Intent(MainActivity.this, PictureCodeActivity.class));
                break;*/

            case R.id.scanBarcode:
                /*
                Intent i = new Intent(activity, ScannedCodeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);*/
                activity.zeigeAnsicht(StaticInts.AUSGEWAEHLT_SCAN_ANSICHT);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        String abc = "abcdefghijklmnopqrstuvwxyz";
        int i = 5;
        try {
            //TextView testTV = getView().findViewById(R.id.testTextView);
            //testTV.setText("Es sind noch keine Lebensmittel vorhanden!");
            Button ansicht_benutzer = getView().findViewById(R.id.benutzer);
            Button ansicht_barcode_datenbank = getView().findViewById(R.id.barcode_datenbank);
            Button ansicht_einkaufsliste = getView().findViewById(R.id.einkaufsliste);
            Button ansicht_einstellungen = getView().findViewById(R.id.einstellungen);
            ansicht_benutzer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.zeigeAnsicht(StaticInts.AUSGEWAEHLT_BENUTZER_ANSICHT);
                }
            });
            ansicht_barcode_datenbank.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    activity.zeigeAnsicht(StaticInts.AUSGEWAEHLT_BARCODE_DATENBANK_ANSICHT);
                    return true;
                }
            });
            ansicht_einkaufsliste.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.zeigeAnsicht(StaticInts.AUSGEWAEHLT_EINKAUSLISTEN_ANSICHT);
                }
            });
            ansicht_einstellungen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.zeigeAnsicht(StaticInts.AUSGEWAEHLT_EINSTELLUNGS_ANSICHT);
                }
            });

        } catch (NullPointerException e) {
            e.printStackTrace();
            activity.failedInitUI();
        }
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

}

