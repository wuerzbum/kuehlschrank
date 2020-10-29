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

import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.OK;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.AUSGEWAEHLT_TASTE_SPEICHERN;

public class ScannedBarcodeDialog extends AlertDialog {

    private Activity activity;
    private Interfaces.scannedBarcodeDialogOnClickListener listener;
    private EditText name, barcode;
    private String sBarcode;
    private String sName;

    /**
     * Eingescannter Barcode kann hier mit neuem Produkt hinterlegt werden
     * @param activity Activity übergeben
     * @param listener Listener für Rückgabe, welcher Button gewählt wurde
     */
    public ScannedBarcodeDialog(Activity activity, Interfaces.scannedBarcodeDialogOnClickListener listener) {
        this(activity, "", "", listener);
    }

    /**
     * Eingescannter Barcode kann hier mit neuem Produkt hinterlegt werden
     * @param activity Activity übergeben
     * @param name Name des Produktes
     * @param barcode Barcode des Produktes
     * @param listener Listener für Rückgabe, welcher Button gewählt wurde
     */
    public ScannedBarcodeDialog(Activity activity, String name, String barcode, Interfaces.scannedBarcodeDialogOnClickListener listener) {
        super(activity);

        this.activity = activity;
        this.listener = listener;
        this.sName = name;
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

        //Wenn Button "Abbrechen" gedrückt wird die Methode performCancel() ausführen
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performCancel();
            }
        });

        //Wenn außerhalb des Dialogs geklickt wird die Methode performCancel() ausführen
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                performCancel();
            }
        });

        //Wenn Button "Speichern" geklickt wird die Methode performSave() ausführen
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSave();
            }
        });

        //Den Barcode im EditText anzeigen

        barcode.setText(sBarcode);

        //den Produktnamen im EditText anzeigen
        name.setText(sName);
    }

    /**
     * beendet den Dialog und führt die Funktion aborted() des listeners aus
     */
    private void performCancel() {
        //gibt über den listener zurück, dass der Dialog abgebrochen wurde
        listener.abbruch();
        //beendet den Dialog
        dismiss();
    }

    /**
     * Wenn die eingegebenen Werte in Ordnung sind, wird der Dialog beendet und die onClicked() Methode des listeners wird ausgeführt
     * Ansonsten wird eine Fehlermeldung angezeigt
     */
    private void performSave() {
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