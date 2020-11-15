package manuel.de.kuehlschrankinventar.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.Interfaces;
import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.holder.Produkt;

import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.OK;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.AUSGEWAEHLT_TASTE_SPEICHERN;

public class DialogEinscannen extends AlertDialog {

    private Activity activity;
    private Interfaces.scanDialogListener listener;
    private EditText name, barcode;
    private String sBarcode;
    private Produkt altesProdukt;

    public DialogEinscannen(Activity activity, Produkt altesProdukt, String barcode, Interfaces.scanDialogListener listener) {
        super(activity, R.style.AppTheme);

        this.activity = activity;
        this.listener = listener;
        this.altesProdukt = altesProdukt;
        this.sBarcode = barcode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Layout einbinden
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_scanned_barcode);

        //UI initialisieren
        Button cancel = findViewById(R.id.btnCancel);
        Button save = findViewById(R.id.btnSave);
        //TODO Barcode unveränderlich machen, wenn mit Scanned Barcode aufgerufen?
        barcode = findViewById(R.id.barcode);
        name = findViewById(R.id.produktName);

        //TODO Titel ändern

        //Wenn Button "Abbrechen" gedrückt wird die Methode performCancel() ausführen
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abbrechen();
            }
        });

        //Wenn außerhalb des Dialogs geklickt wird die Methode performCancel() ausführen
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                abbrechen();
            }
        });

        //Wenn Button "Speichern" geklickt wird die Methode performSave() ausführen
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speichern();
            }
        });

        //Den Barcode im EditText anzeigen

        barcode.setText(sBarcode);

        //den Produktnamen im EditText anzeigen
        //TODO name.setText(altesProdukt.getName());
    }

    /**
     * beendet den Dialog und führt die Funktion aborted() des listeners aus
     */
    private void abbrechen() {
        //gibt über den listener zurück, dass der Dialog abgebrochen wurde
        listener.abbruch();
        //beendet den Dialog
        dismiss();
    }

    /**
     * Wenn die eingegebenen Werte in Ordnung sind, wird der Dialog beendet und die onClicked() Methode des listeners wird ausgeführt
     * Ansonsten wird eine Fehlermeldung angezeigt
     */
    private void speichern() {
        String fehlerBeschreibung = "";


        String barcodeInput = barcode.getText().toString();
        String nameInput = name.getText().toString();

        //TODO in ScannedBarcode auch warnen, wenn der Barcode leer ist?

        listener.onClicked(AUSGEWAEHLT_TASTE_SPEICHERN, nameInput, barcodeInput, new Interfaces.resultObserver() {
            @Override
            public void result(int result) {
                if (result == OK) {
                    dismiss();
                }
            }
        });
    }

    public void setBarcode(String newBarcode) {
        if (barcode.getText().toString().equals(sBarcode)) {
            barcode.setText(newBarcode);
        }
        sBarcode = newBarcode;
    }

    public String getBarcode() {
        return sBarcode;
    }
}