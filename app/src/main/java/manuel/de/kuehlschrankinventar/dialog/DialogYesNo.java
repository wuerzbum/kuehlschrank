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

import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.*;

public class DialogYesNo extends AlertDialog {

    private Interfaces.resultObserver listener;
    private String title, beschreibung;

    /**
     * Dialog für Ja / Nein Fragen
     * @param activity Activity übergeben
     * @param listener Listener für Rückgabe, welcher Button gewählt wurde
     */
    public DialogYesNo(Activity activity, String title, String beschreibung, Interfaces.resultObserver listener) {
        super(activity);

        this.title = title;
        this.beschreibung = beschreibung;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Layout einbinden
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_yes_no);

        //UI initialisieren
        Button no;
        Button yes;
        try {
            no = findViewById(R.id.btnNo);
            yes = findViewById(R.id.btnYes);

            //Wenn Button "Nein" gedrückt wird die Methode int Nein übergeben ausführen
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nein();
                }
            });

            //Wenn Button "Speichern" geklickt wird die Methode performSave() ausführen
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ja();
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
            nein();
        }

        //Texte initialisieren
        TextView titleTV;
        TextView beschreibungTV;
        try {
            titleTV = findViewById(R.id.title);
            beschreibungTV = findViewById(R.id.message);

            if (title != null) {
                titleTV.setText(title);
            } else {
                titleTV.setVisibility(View.GONE);
            }

            if (beschreibung != null) {
                beschreibungTV.setText(beschreibung);
            } else {
                beschreibungTV.setVisibility(View.GONE);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
            nein();
        }

        //Wenn außerhalb des Dialogs geklickt wird die Methode performCancel() ausführen
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                nein();
            }
        });
    }

    /**
     * beendet den Dialog und führt die Funktion aborted() des listeners aus
     */
    private void nein() {
        //gibt über den listener zurück, dass der Dialog abgebrochen wurde
        listener.result(AUSGEWAEHLT_NEIN);
        //beendet den Dialog
        dismiss();
    }

    /**
     * Wenn die eingegebenen Werte in Ordnung sind, wird der Dialog beendet und die onClicked() Methode des listeners wird ausgeführt
     * Ansonsten wird eine Fehlermeldung angezeigt
     */
    private void ja() {
        listener.result(AUSGEWAEHLT_JA); //über listener den Integer für Ja zurückgeben
        dismiss(); //dialog beenden
    }
}