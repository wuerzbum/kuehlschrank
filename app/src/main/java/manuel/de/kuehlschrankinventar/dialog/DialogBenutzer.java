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

public class DialogBenutzer extends AlertDialog {

    private Activity activity;
    private Interfaces.benutzerDialogListener listener;
    //private Benutzer alterBenutzer;

    public DialogBenutzer(Activity activity, /*Benutzer benutzer,*/ Interfaces.benutzerDialogListener listener) {
        super(activity, R.style.AppTheme);

        this.activity = activity;
        // TODO this.alterBenutzer = benutzer;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Layout einbinden
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_benutzer);

        //UI initialisieren
        try {
            Button cancel;
            Button save;
            cancel = findViewById(R.id.btnNo);
            save = findViewById(R.id.btnYes);

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
            String title = activity.getString(R.string.benutzer) + activity.getString(R.string.space);

            /*if (alterBenutzer != null) {
                title += activity.getString(R.string.bearbeiten);
                //TODO DialogBenutzer EditTexte ausfüllen
            } else {
                title += activity.getString(R.string.erstellen);
            }*/

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

    private void abbrechen() {
        //TODO methode abbrechen für DialogBenutzer erstellen
        dismiss();
    }

    private void speichern() {
        //TODO methode speichern für DialogBenutzer erstellen
        dismiss(); //dialog beenden
    }
}