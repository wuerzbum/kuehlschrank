package manuel.de.kuehlschrankinventar.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import manuel.de.kuehlschrankinventar.R;

public class ScannedBarcodeDialog extends AlertDialog {

    private Activity activity;
    private scannedBarcodeDialogOnClickListener listener;
    private EditText name, barcode;
    private String sBarcode;
    private String sName;

    public final static int SELECTED_BUTTON_SAVE = 0, SELECTED_BUTTON_CANCEL = 1;
    public interface scannedBarcodeDialogOnClickListener {
        void onClicked(int selectedButton, String name, String barcode);
        void aborted();
    }

    /**
     * Eingescannter Barcode kann hier mit neuem Produkt hinterlegt werden
     * @param activity Activity übergeben
     * @param listener Listener für Rückgabe, welcher Button gewählt wurde
     */
    public ScannedBarcodeDialog(Activity activity, scannedBarcodeDialogOnClickListener listener) {
        this(activity, "", "", listener);
    }

    /**
     * Eingescannter Barcode kann hier mit neuem Produkt hinterlegt werden
     * @param activity Activity übergeben
     * @param name Name des Produktes
     * @param barcode Barcode des Produktes
     * @param listener Listener für Rückgabe, welcher Button gewählt wurde
     */
    public ScannedBarcodeDialog(Activity activity, String name, String barcode, scannedBarcodeDialogOnClickListener listener) {
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
        listener.aborted();
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

        if (barcodeInput.equals("")) {
            fehlerBeschreibung += activity.getString(R.string.listingpoint) + activity.getString(R.string.space) + activity.getString(R.string.wrong_barcode);
        }
        /*TODO überprüfen, ob der Name bereits vorhanden ist
        if (namevorhanden?) {
            if(!fehlerBeschreibung.equals("")) {
                fehlerBeschreibung += "\n";
            }
            fehlerBeschreibung += activity.getString(R.string.listingpoint) + activity.getString(R.string.space) + activity.getString(R.string.name_already_exist);
        }
        */
        if (nameInput.equals("")) {
            if(!fehlerBeschreibung.equals("")) {
                    fehlerBeschreibung += "\n";
        }
            fehlerBeschreibung += activity.getString(R.string.listingpoint) + activity.getString(R.string.space) + activity.getString(R.string.wrong_barcode);
        }

        if (fehlerBeschreibung.equals("")) {
            listener.onClicked(SELECTED_BUTTON_SAVE, nameInput, barcodeInput);
            dismiss();
            return;
        }

        if (fehlerBeschreibung.split("\n").length > 1) {
            //mehrere Fehler vorhanden
            fehlerBeschreibung = activity.getString(R.string.multiple_fails_exist) + "\n" + fehlerBeschreibung;
        } else {
            //nur ein Fehler vorhanden
            fehlerBeschreibung = activity.getString(R.string.one_fail_exist) + "\n" + fehlerBeschreibung;
        }

        Toast.makeText(activity, fehlerBeschreibung, Toast.LENGTH_SHORT).show();
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