package manuel.de.kuehlschrankinventar.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.Interfaces;
import manuel.de.kuehlschrankinventar.R;

public class DialogListView extends AlertDialog {

    private Activity activity;
    private Interfaces.listViewDialogListener listener;
    private String sTitel, sBeschreibung;
    private ArrayList<String> listenInhalt;

    public DialogListView(Activity activity, String titel, String beschreibung, @NonNull ArrayList<String> listInhalt, Interfaces.listViewDialogListener listener) {
        super(activity);

        this.activity = activity;
        this.listener = listener;
        this.sTitel = titel;
        this.sBeschreibung = beschreibung;
        this.listenInhalt = listInhalt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Layout einbinden
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_list_view);

        //Buttons initialisieren
        try {
            Button cancel = findViewById(R.id.btnCancel);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abbrechen();
                }
            });
            setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    abbrechen();
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
            abbrechen();
        }

        try {
            TextView titelTV = findViewById(R.id.title);
            TextView beschreibungTV = findViewById(R.id.beschreibung);

            if (sTitel != null) {
                titelTV.setVisibility(View.VISIBLE);
                titelTV.setText(sTitel);
            } else {
                titelTV.setVisibility(View.GONE);
            }

            if (sBeschreibung != null) {
                beschreibungTV.setVisibility(View.VISIBLE);
                beschreibungTV.setText(sBeschreibung);
            } else {
                beschreibungTV.setVisibility(View.GONE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            abbrechen();
        }

        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, R.layout.listrow_ein_text, listenInhalt);
            ListView listView = findViewById(R.id.list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ausgewaehlt(position);
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
            abbrechen();
        }
    }

    private void abbrechen() {
        dismiss();
    }

    private void ausgewaehlt(int ausgewaehltePosition) {
        listener.selected(ausgewaehltePosition);
        dismiss();
    }
}