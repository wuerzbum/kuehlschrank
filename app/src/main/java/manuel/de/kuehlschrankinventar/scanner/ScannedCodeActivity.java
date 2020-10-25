package manuel.de.kuehlschrankinventar.scanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts;
import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.datenbank.DefPref;
import manuel.de.kuehlschrankinventar.dialog.ScannedBarcodeDialog;
import manuel.de.kuehlschrankinventar.InterfacesAndStatics.Interfaces;
import manuel.de.kuehlschrankinventar.holder.Produkt;
import manuel.de.kuehlschrankinventar.holder.ProduktManager;

import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.BARCODE_IST_BEREITS_VORHANDEN;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.NAME_IST_BEREITS_VORHANDEN;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.NAME_IST_LEER;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.OK;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.REQUEST_CAMERA_PERMISSION;

public class ScannedCodeActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private String intentData = "";
    private ScannedBarcodeDialog dialog;
    private ProduktManager produktManager;
    private DefPref prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_code);

        prefs = new DefPref(ScannedCodeActivity.this);
        produktManager = new ProduktManager(prefs);
        initViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        try {
                            cameraSource.start(surfaceView.getHolder());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
                break;
        }
    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
    }

    private void initialiseDetectorsAndSources() {
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScannedCodeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScannedCodeActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                finish();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    txtBarcodeValue.post(new Runnable() {

                        @Override
                        public void run() {
                                intentData = barcodes.valueAt(0).displayValue;
                                /*TODO Barcodeverarbeiten
                                *  Produkte haben immer denselben Barcode
                                *  Barcode APIs (mit denen der Barcode direkt ausgewertet werden können)
                                *  kosten Geld (pro Zugriff / Monatlich)
                                *  Vorschlag: wenn eine Nummer gescannt wird, wird der Nutzer aufgefordert Daten für dieses Produkt zu hinterlegen
                                *  sollte bereits ein Produkt zu Nummer gespeichert sein, dann wird dieses direkt übernommen
                                *  Notwendig hierfür:
                                *  Eine Liste der Nummern mit dem zugehörigen Produkt
                                *  Diese muss abgespeichert werden
                                */
                                txtBarcodeValue.setText(intentData);
                                String name = "";
                                if (produktManager.exisitiertBarcode(intentData)) {
                                    name = produktManager.getProduktMitBarcode(intentData).getName();
                                }
                                if (dialog == null || !dialog.isShowing()) {
                                    dialog = new ScannedBarcodeDialog(ScannedCodeActivity.this, name, intentData, new Interfaces.scannedBarcodeDialogOnClickListener() {
                                        @Override
                                        public void onClicked(int selectedButton, String name, String barcode, Interfaces.resultObserver resultObserver) {
                                            if (selectedButton == StaticInts.SELECTED_BUTTON_SAVE) {
                                                Produkt produkt = new Produkt(name, barcode);
                                                int result = produktManager.produktHinzufuegen(produkt);

                                                if (result != OK) {
                                                    String resultText = "";
                                                    if ((result & NAME_IST_LEER) == NAME_IST_LEER) {
                                                        resultText += getString(R.string.listingpoint) + getString(R.string.space) + getString(R.string.name_empty);
                                                    }
                                                    if ((result & NAME_IST_BEREITS_VORHANDEN) == NAME_IST_BEREITS_VORHANDEN) {
                                                        if (!resultText.equals("")) {
                                                            resultText += "\n";
                                                        }
                                                        resultText += getString(R.string.listingpoint) + getString(R.string.space) + getString(R.string.name_already_exist);
                                                    }
                                                    if ((result & BARCODE_IST_BEREITS_VORHANDEN) == BARCODE_IST_BEREITS_VORHANDEN) {
                                                        if (!resultText.equals("")) {
                                                            resultText += "\n";
                                                        }
                                                        resultText += getString(R.string.listingpoint) + getString(R.string.space) + getString(R.string.wrong_barcode);
                                                    }
                                                    if (resultText.split("\n").length > 1) {
                                                        resultText = getString(R.string.multiple_fails_exist) + getString(R.string.doublepoint) + "\n" + resultText;
                                                    } else {
                                                        resultText = getString(R.string.one_fail_exist) + getString(R.string.doublepoint) + "\n" + resultText;
                                                    }

                                                    Toast.makeText(ScannedCodeActivity.this, resultText, Toast.LENGTH_SHORT).show();
                                                }

                                                resultObserver.result(result);
                                            }
                                        }

                                        @Override
                                        public void aborted() {

                                        }
                                    });

                                    dialog.show();
                                } else {
                                    if (!dialog.getBarcode().equals(intentData)) {
                                        //TODO Name wieder löschen?
                                        dialog.setBarcode(intentData);
                                        Toast.makeText(ScannedCodeActivity.this, getString(R.string.new_barcode_setted), Toast.LENGTH_SHORT).show();
                                    }
                                }
                        }
                    });
                }
            }
        });
    }
}