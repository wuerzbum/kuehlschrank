package manuel.de.kuehlschrankinventar.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.Interfaces;
import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.holder.Inventar;
import manuel.de.kuehlschrankinventar.holder.Produkt;

import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.*;

public class DialogProdukt extends AlertDialog {

    private Activity activity;
    private Interfaces.produktDialogListener listener;
    private Inventar inventar;
    private Produkt altesProdukt;
    private EditText produktName;
    private EditText produktMenge;
    private EditText produktVerfallsdatum;
    private EditText produktPreisProEinheit;
    private EditText produktBarcode;
    private Spinner einheit;
    private SimpleDateFormat sDF = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);

    /**
     * Dialog um Produkt zu erstellen oder bearbeiten
     * @param activity Activity übergeben
     * @param altesProdukt Altes Produkt übergeben, wenn bearbeitet wird. Ansonten "null" übergeben
     * @param listener Listener für Rückgabe
     */
    public DialogProdukt(Activity activity, Produkt altesProdukt, Inventar inventar, Interfaces.produktDialogListener listener) {
        super(activity);

        this.activity = activity;
        this.altesProdukt = altesProdukt;
        this.inventar = inventar;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Layout einbinden
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_produkt);

        //UI initialisieren
        initUI();

        //Texte initialisieren
        initTexte();
    }

    private void initUI() {
        //Buttons initialisieren
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

        //EditTexte initialisieren
        try {
            produktName = findViewById(R.id.produktName);
            produktMenge = findViewById(R.id.produktMenge);
            produktVerfallsdatum = findViewById(R.id.verfallsdatum);
            produktPreisProEinheit = findViewById(R.id.preis_pro_einheit);
            produktBarcode = findViewById(R.id.barcode);
            einheit = findViewById(R.id.einheit);

            String[] items = activity.getResources().getStringArray(R.array.einheit_array);
            ArrayAdapter<String> einheitAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, items);
            einheit.setAdapter(einheitAdapter);

            produktBarcode.setKeyListener(null);
            produktVerfallsdatum.setKeyListener(null);

            produktBarcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (v.getId() == produktBarcode.getId()) {
                        if (hasFocus) {
                            if (v.getTag() != null) {
                                //Wenn zuvor ein Longclick ausgeführt wurde, dann den Fokus löschen.
                                v.setTag(null);
                                v.clearFocus();
                                return;
                            }
                            produktBarcode.clearFocus();
                            listener.getBarcodeScan(new Interfaces.getStringListener() {
                                @Override
                                public void getString(String barcode) {
                                    if (barcode != null) {
                                        produktBarcode.setText(barcode);
                                    }
                                }
                            });
                        }
                    }
                }
            });

            produktBarcode.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    produktBarcode.setText("");
                    //Tag setzen, damit der focuschangelistener nicht ausgeführt wird.
                    produktBarcode.setTag("longClickPerformed");
                    return true;
                }
            });

            produktVerfallsdatum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (v.getId() == produktVerfallsdatum.getId()) {
                        if (hasFocus) {
                            if (v.getTag() != null) {
                                //Wenn zuvor ein Longclick ausgeführt wurde, dann den Fokus löschen.
                                v.setTag(null);
                                v.clearFocus();
                                return;
                            }
                            //Wenn kein Longclick ausgeführt wurde, dann einen Kalender öffnen, um ein Datum auszuwählen
                            produktVerfallsdatum.clearFocus();
                            Date oldDate = null;
                            try {
                                oldDate = sDF.parse(produktVerfallsdatum.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            new DialogDateSelector(activity, new Interfaces.changeListenerDatePicker() {
                                @Override
                                public void changed(Date newDate) {
                                    if (newDate == null) {
                                        produktVerfallsdatum.setText("");
                                    } else {
                                        produktVerfallsdatum.setText(sDF.format(newDate));
                                    }
                                }
                            }, oldDate).show();
                        }
                    }
                }
            });

            produktVerfallsdatum.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    produktVerfallsdatum.setText("");
                    //Tag setzen, damit der focuschangelistener nicht ausgeführt wird.
                    produktVerfallsdatum.setTag("longClickPerformed");
                    return true;
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
            abbrechen();
            Toast.makeText(activity, "Beim initialisieren der Edittexte ist etwas schief gegangen", Toast.LENGTH_SHORT).show();
        }

        //Wenn außerhalb des Dialogs geklickt wird die Methode performCancel() ausführen
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                abbrechen();
            }
        });
    }

    private void initTexte() {
        try {
            //Dialog titel setzen
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

            //Wenn altes Produkt nicht "null" ist, dann die Werte dieser Klasse setzen
            produktName.setText(altesProdukt.getName());
            produktMenge.setText(String.valueOf(altesProdukt.getMenge()));
            produktVerfallsdatum.setText(sDF.format(altesProdukt.getVerfallsdatum()));
            produktPreisProEinheit.setText(String.valueOf(altesProdukt.getPreis()));
            produktBarcode.setText(altesProdukt.getBarcode());
            String einheitString = altesProdukt.getEinheit();
            int arrayItem = 0;
            String[] einheitsArray = activity.getResources().getStringArray(R.array.einheit_array);
            for (; arrayItem < einheitsArray.length; arrayItem++) {
                if (einheitString.equals(einheitsArray[arrayItem])) {
                    break;
                }
            }
            einheit.setSelection(arrayItem);
        } catch (NullPointerException e) {
            e.printStackTrace();
            abbrechen();
        }
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
        //String name, double menge, Date verfallsdatum, double preis, TreeMap <String, Double>besitzer, TreeMap <String, Double> minBestand, String barcode, String einheit
        try {
            String produktNameString = produktName.getText().toString();
            double mengeDouble = Double.parseDouble(produktMenge.getText().toString());
            Date datumString = null;
            try {
                datumString = sDF.parse(produktVerfallsdatum.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            double preisProEinheit = Double.parseDouble(produktPreisProEinheit.getText().toString())/(Double.parseDouble(produktMenge.getText().toString())*1.0);
            String barcodeString = produktBarcode.getText().toString();
            String einheitString = einheit.getSelectedItem().toString();
            Produkt produkt = new Produkt(produktNameString,
                    mengeDouble,
                    datumString,
                    preisProEinheit,
                    null,
                    null,
                    barcodeString,
                    einheitString);
            if (altesProdukt == null) {
                int result = inventar.checkProduktExisitiert(produkt);
                if (result == OK) {
                    listener.produktSpeichern(produkt, null);
                } else {
                    ArrayList<String> fehlerListe = new ArrayList<>();
                    //TODO Texte
                    if ((result & NAME_IST_LEER) == NAME_IST_LEER) {
                        fehlerListe.add("• Der Produktname ist leer");
                    }
                    if ((result & NAME_IST_BEREITS_VORHANDEN) == NAME_IST_BEREITS_VORHANDEN) {
                        fehlerListe.add("• Der Produktname ist bereits vorhanden");
                    }
                    if ((result & BARCODE_IST_BEREITS_VORHANDEN) == BARCODE_IST_BEREITS_VORHANDEN) {
                        fehlerListe.add("• Der Barcode ist bereits vorhanden");
                    }
                    StringBuilder toastString = new StringBuilder(fehlerListe.size() > 1 ? "Folgende Fehler sind vorhanden:\n" : "Folgender fehler ist vorhanden:\n");
                    for (String fehler : fehlerListe) {
                        toastString.append(fehler).append("\n");
                    }
                    Toast.makeText(activity, toastString, Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                listener.produktSpeichern(produkt, altesProdukt);
            }
            dismiss(); //dialog beenden
        } catch (Exception e) {
            e.printStackTrace();
            //TODO Text
            Toast.makeText(activity, "min. eine Eingabe war unzulässig", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * fügt weiteren Besitzer dem Produkt hinzu
     */
    private void weitererBesitzer() {
        //TODO weitererBesitzer bei Produkterstellungsdialog
    }
}