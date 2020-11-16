package manuel.de.kuehlschrankinventar.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.Interfaces;
import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.holder.Produkt;

public class DialogProdukt extends AlertDialog {

    private Activity activity;
    private Interfaces.produktDialogListener listener;
    private Produkt altesProdukt;

    /**
     * Dialog um Produkt zu erstellen oder bearbeiten
     * @param activity Activity übergeben
     * @param altesProdukt Altes Produkt übergeben, wenn bearbeitet wird. Ansonten "null" übergeben
     * @param listener Listener für Rückgabe
     */
    public DialogProdukt(Activity activity, Produkt altesProdukt, Interfaces.produktDialogListener listener) {
        super(activity, R.style.AppTheme);

        this.activity = activity;
        this.altesProdukt = altesProdukt;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Layout einbinden
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_produkt);

        //UI initialisieren
        try {
            Button save;
            Button cancel;

            cancel = findViewById(R.id.btnCancel);
            save = findViewById(R.id.btnSave);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abbrechen();
                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    speichern();
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
            abbrechen();
        }

        //Texte initialisieren
        try {
            TextView titleTV;

            titleTV = findViewById(R.id.title);
            String title = activity.getString(R.string.produkt) + activity.getString(R.string.space);


            if (altesProdukt != null) {
                title += activity.getString(R.string.bearbeiten);
                //TODO EditTexte ausfüllen
            } else {
                title += activity.getString(R.string.erstellen);
            }

            titleTV.setText(title);

        } catch (NullPointerException e) {
            e.printStackTrace();
            abbrechen();
        }

        //Wenn außerhalb des Dialogs geklickt wird die Methode performCancel() ausführen
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                abbrechen();
            }
        });
    }

    /**
     * bricht die Erstellung oder Bearbeitung ab
     */
    private void abbrechen() {
        //TODO performCancel bei Produkterstellungsdialog
        dismiss();
    }

    /**
     * speichert das erstellte oder bearbeitete Produkt
     */
    private void speichern() {
        //TODO performSave bei Produkterstellungsdialog
        dismiss(); //dialog beenden
    }

    /**
     * fügt weiteren Besitzer dem Produkt hinzu
     */
    private void weitererBesitzer() {
        //TODO weitererBesitzer bei Produkterstellungsdialog
    }
}