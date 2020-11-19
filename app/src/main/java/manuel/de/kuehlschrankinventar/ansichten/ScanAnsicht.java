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
    private CameraSource cameraSource;
    private String gelesenerBarcode = "";

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
            kameraAnsicht = findViewById(R.id.surfaceView);
        } catch (NullPointerException e) {
            e.printStackTrace();
            setResult(StaticInts.DEFAULT);
            finish();
        }
    }

    private void initDetectorUndQuelle() {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this)
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

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    Intent result = new Intent();
                    String tempBarcode = barcodes.valueAt(0).displayValue;
                    if (tempBarcode.equals(gelesenerBarcode)) {
                        result.putExtra(StaticStrings.BARCODE, tempBarcode);
                        setResult(StaticInts.RESULT_OK, result);
                        finish();
                    } else {
                        gelesenerBarcode = tempBarcode;
                    }
                }
            }
        });
    }
}