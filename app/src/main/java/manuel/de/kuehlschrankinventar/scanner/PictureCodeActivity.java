package manuel.de.kuehlschrankinventar.scanner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.File;
import java.io.FileNotFoundException;

import manuel.de.kuehlschrankinventar.BuildConfig;
import manuel.de.kuehlschrankinventar.R;

public class PictureCodeActivity extends AppCompatActivity {

    public Button btnOpenCamera;
    public TextView txtResultBody;

    private BarcodeDetector detector;
    private Uri imageUri;
    private final static int REQUEST_CAMERA_PERMISSION = 200;
    private final static int CAMERA_REQUEST = 101;
    private final static String TAG = "API123";
    private final static String SAVED_INSTANCE_URI = "uri";
    private final static String SAVED_INSTANCE_RESULT = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_code);

        initViews();

        if (savedInstanceState != null) {
            if (imageUri != null) {
                imageUri = Uri.parse(savedInstanceState.getString(SAVED_INSTANCE_URI));
                txtResultBody.setText(savedInstanceState.getString(SAVED_INSTANCE_RESULT));
            }
        }

        detector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                .build();

        if (!detector.isOperational()) {
            //TODO Text in xml eintragen
            txtResultBody.setText("Detector initialisation failed");
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takeBarcodePicture();
                } else {
                    //TODO Text in xml eintragen
                    Toast.makeText(getApplicationContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            launchMediaScanIntent();
            try {
                Bitmap bitmap = decodeBitmapUri(this, imageUri);
                if (detector.isOperational() && bitmap != null) {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<Barcode> barcodes = detector.detect(frame);
                    for (int index = 0; index < barcodes.size(); index++) {
                        Barcode code = barcodes.valueAt(index);
                        txtResultBody.setText(txtResultBody.getText() + "\n" + code.displayValue + "\n");
                        int type = barcodes.valueAt(index).valueFormat;
                        switch (type) {
                            case Barcode.CONTACT_INFO:
                                Log.i(TAG, code.contactInfo.title);
                                break;
                            case Barcode.EMAIL:
                                Log.i(TAG, code.displayValue);
                                break;
                            case Barcode.ISBN:
                                Log.i(TAG, code.rawValue);
                                break;
                            case Barcode.PHONE:
                                Log.i(TAG, code.phone.number);
                                break;
                            case Barcode.PRODUCT:
                                Log.i(TAG, code.rawValue);
                                break;
                            case Barcode.SMS:
                                Log.i(TAG, code.sms.message);
                                break;
                            case Barcode.TEXT:
                                Log.i(TAG, code.displayValue);
                                break;
                            case Barcode.URL:
                                Log.i(TAG, "url: " + code.displayValue);
                                break;
                            case Barcode.WIFI:
                                Log.i(TAG, code.wifi.ssid);
                                break;
                            case Barcode.GEO:
                                Log.i(TAG, code.geoPoint.lat + ":" + code.geoPoint.lng);
                                break;
                            case Barcode.CALENDAR_EVENT:
                                Log.i(TAG, code.calendarEvent.description);
                                break;
                            case Barcode.DRIVER_LICENSE:
                                Log.i(TAG, code.driverLicense.licenseNumber);
                                break;
                            default:
                                Log.i(TAG, code.rawValue);
                                break;
                        }
                    }
                    if (barcodes.size() == 0) {
                        //TODO text in xml eintragen
                        txtResultBody.setText("No barcode could be detected. Please try again.");
                    }
                } else {
                    //TODO text in xml eintragen
                    txtResultBody.setText("Detector initialisation failed");
                }
            } catch (Exception e) {
                //TODO text in xml eintragen
                Toast.makeText(getApplicationContext(), "Failed to load Image", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (imageUri != null) {
            outState.putString(SAVED_INSTANCE_URI, imageUri.toString());
            outState.putString(SAVED_INSTANCE_RESULT, txtResultBody.getText().toString());
        }
        super.onSaveInstanceState(outState);
    }

    private void initViews() {
        txtResultBody = findViewById(R.id.txtResultsBody);
        btnOpenCamera = findViewById(R.id.btnOpenCamera);
        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(PictureCodeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            }
        });
    }

    private void takeBarcodePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(getApplicationContext().getExternalCacheDir(), "pic.jpg");
        //File photo = new File(Environment.getExternalStorageDirectory(), "pic.jpg");
        imageUri = FileProvider.getUriForFile(PictureCodeActivity.this, BuildConfig.APPLICATION_ID + ".provider", photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    private void launchMediaScanIntent() {
        //TODO deprecated --> https://stackoverflow.com/questions/60203353/action-media-scanner-scan-filestring-is-deprecated
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
        int targetW = 600;
        int targetH = 600;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
    }
}