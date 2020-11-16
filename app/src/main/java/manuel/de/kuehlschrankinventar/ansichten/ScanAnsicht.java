package manuel.de.kuehlschrankinventar.ansichten;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
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
import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.activity.MainActivity;
import manuel.de.kuehlschrankinventar.dialog.ScannedBarcodeDialog;
import manuel.de.kuehlschrankinventar.InterfacesAndStatics.Interfaces;
import manuel.de.kuehlschrankinventar.holder.Produkt;

import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.BARCODE_IST_BEREITS_VORHANDEN;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.NAME_IST_BEREITS_VORHANDEN;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.NAME_IST_LEER;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.OK;
import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.ANFRAGE_KAMERA_BERECHTIGUNG;

public class ScanAnsicht extends Fragment {

    private SurfaceView kameraAnsicht;
    private TextView gelesenerBarcodeAnzeigen;
    private MainActivity activity;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private String gelesenerBarcode = "";
    private ScannedBarcodeDialog produktDialog;
    private Interfaces.information informationListener;

    public ScanAnsicht(@NonNull Interfaces.information informationListener) {
        this.informationListener = informationListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ansicht_scan, container, false);
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_code);

        prefs = new DefPref(ScannedCodeActivity.this);
        produktManager = new ProduktManager(prefs);
        initViews();
    }
     */

    @Override
    public void onPause() {
        cameraSource.release();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        activity = ((MainActivity) getActivity());

        try {
            assert activity != null;
            activity.setTitle(activity.getString(R.string.scan_barcode));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        initAnsichten();
        initDetectorUndQuelle();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ANFRAGE_KAMERA_BERECHTIGUNG:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        try {
                            cameraSource.start(kameraAnsicht.getHolder());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
                break;
        }
    }

    private void initAnsichten() {
        try {
            gelesenerBarcodeAnzeigen = getView().findViewById(R.id.txtBarcodeValue);
            kameraAnsicht = getView().findViewById(R.id.surfaceView);
        } catch (NullPointerException e) {
            e.printStackTrace();
            activity.failedInitUI();
        }
    }

    private void initDetectorUndQuelle() {
        barcodeDetector = new BarcodeDetector.Builder(activity)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(activity, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        kameraAnsicht.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(kameraAnsicht.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, ANFRAGE_KAMERA_BERECHTIGUNG);
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
                informationListener.inform(StaticInts.KAMERA_FREIGABE);
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
                                String name = "";
                                if (activity.getInventar().exisitiertBarcode(gelesenerBarcode)) {
                                    name = activity.getInventar().getProduktMitBarcode(gelesenerBarcode).getName();
                                }
                                if (produktDialog == null || !produktDialog.isShowing()) {
                                    produktDialog = new ScannedBarcodeDialog(activity, name, gelesenerBarcode, new Interfaces.scannedBarcodeDialogOnClickListener() {
                                        @Override
                                        public void onClicked(int selectedButton, String name, String barcode, Interfaces.resultObserver resultObserver) {
                                            //TODO warnen, wenn der Barcode leer ist?
                                            if (selectedButton == StaticInts.AUSGEWAEHLT_TASTE_SPEICHERN) {
                                                Produkt produkt = new Produkt(name, barcode);
                                                int result = activity.getInventar().produktHinzufuegen(produkt);

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
                                            }
                                        }

                                        @Override
                                        public void abbruch() {

                                        }
                                    });

                                    produktDialog.show();
                                } else {
                                    if (!produktDialog.getBarcode().equals(gelesenerBarcode)) {
                                        //TODO Name wieder löschen?
                                        produktDialog.setBarcode(gelesenerBarcode);
                                        Toast.makeText(activity, getString(R.string.new_barcode_setted), Toast.LENGTH_SHORT).show();
                                    }
                                }
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