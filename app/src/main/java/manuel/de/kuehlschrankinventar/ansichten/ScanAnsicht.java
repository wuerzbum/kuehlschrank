package manuel.de.kuehlschrankinventar.ansichten;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts;
import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticStrings;
import manuel.de.kuehlschrankinventar.R;

import static manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts.DEFAULT;

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