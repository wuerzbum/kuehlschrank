package manuel.de.kuehlschrankinventar.ansichten;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts;
import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticStrings;
import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.activity.MainActivity;
import manuel.de.kuehlschrankinventar.dialog.DialogEinscannen;
import manuel.de.kuehlschrankinventar.InterfacesAndStatics.Interfaces;
import manuel.de.kuehlschrankinventar.holder.Produkt;

import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.BARCODE_IST_BEREITS_VORHANDEN;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.DEFAULT;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.NAME_IST_BEREITS_VORHANDEN;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.NAME_IST_LEER;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.OK;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.ANFRAGE_KAMERA_BERECHTIGUNG;

public class ScanAnsicht extends AppCompatActivity {

    private SurfaceView kameraAnsicht;
    private TextView gelesenerBarcodeAnzeigen;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private String gelesenerBarcode = "";
    private DialogEinscannen produktDialog;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.ansicht_scan);
    }

    @Override
    public void onPause() {
        if (cameraSource != null) {
            cameraSource.release();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        setTitle(getString(R.string.scan_barcode));

        initAnsichten();
        initDetectorUndQuelle();
    }

    private void initAnsichten() {
        try {
            gelesenerBarcodeAnzeigen = findViewById(R.id.txtBarcodeValue);
            kameraAnsicht = findViewById(R.id.surfaceView);
        } catch (NullPointerException e) {
            e.printStackTrace();
            setResult(StaticInts.DEFAULT);
            finish();
        }
    }

    private void initDetectorUndQuelle() {
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        kameraAnsicht.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScanAnsicht.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        setResult(DEFAULT);
                        finish();
                    }
                    cameraSource.start(kameraAnsicht.getHolder());
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
                // TODO ? informationListener.inform(StaticInts.KAMERA_FREIGABE);
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    gelesenerBarcodeAnzeigen.post(new Runnable() {

                        @Override
                        public void run() {
                                gelesenerBarcode = barcodes.valueAt(0).displayValue;
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
                                gelesenerBarcodeAnzeigen.setText(gelesenerBarcode);
                                Intent result = new Intent();
                                result.putExtra(StaticStrings.BARCODE, gelesenerBarcode);
                                setResult(StaticInts.RESULT_OK, result);
                                finish();
                                //TODO Ok Button um Barcode zu übernehmen?
                                /*String name = "";
                                if (activity.getInventar().exisitiertProduktBarcode(gelesenerBarcode)) {
                                    name = activity.getInventar().getProduktMitBarcode(gelesenerBarcode).getName();
                                }
                                if (produktDialog == null || !produktDialog.isShowing()) {
                                    /*TODO produktDialog = new DialogEinscannen(activity, name, gelesenerBarcode, new Interfaces.scanDialogListener() {
                                        @Override
                                        public void onClicked(int selectedButton, String name, String barcode, Interfaces.resultObserver resultObserver) {
                                            //TODO warnen, wenn der Barcode leer ist?
                                            if (selectedButton == StaticInts.AUSGEWAEHLT_TASTE_SPEICHERN) {
                                                //TODO Produkt produkt = new Produkt(name, barcode);
                                                //int result = activity.getInventar().neuesProdukt(produkt);
/*
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
                                                        resultText += getString(R.string.listingpoint) + getString(R.string.space) + getString(R.string.barcode_exist);
                                                    }
                                                    if (resultText.split("\n").length > 1) {
                                                        resultText = getString(R.string.multiple_fails_exist) + getString(R.string.doublepoint) + "\n" + resultText;
                                                    } else {
                                                        resultText = getString(R.string.one_fail_exist) + getString(R.string.doublepoint) + "\n" + resultText;
                                                    }

                                                    Toast.makeText(activity, resultText, Toast.LENGTH_SHORT).show();
                                                }
                                               resultObserver.result(result);

                                                resultObserver.result(0);
                                            }
                                        }

                                        @Override
                                        public void abbruch() {

                                        }
                                    });

                                    produktDialog.show();/
                                } else {
                                    if (!produktDialog.getBarcode().equals(gelesenerBarcode)) {
                                        //TODO Name wieder löschen?
                                        produktDialog.setBarcode(gelesenerBarcode);
                                        Toast.makeText(activity, getString(R.string.new_barcode_setted), Toast.LENGTH_SHORT).show();
                                    }
                                }*/
                        }
                    });
                }
            }
        });
    }

    private void starteProduktDialog(){
        //TODO: Methode Programmieren
    }
}