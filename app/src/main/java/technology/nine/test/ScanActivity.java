package technology.nine.test;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.lang.reflect.Field;

import technology.nine.test.data.DBHelper;


public class ScanActivity extends AppCompatActivity {
    SurfaceView surfaceView;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    DBHelper helper;
    private SharedPreferences permissionStatus;
    private static final int REQUEST_CAMERA_PERMISSION = 105;
    private boolean sentToSettings = false;
    String decodedValue;
    Button historyButton;
    ToggleButton flashBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        surfaceView = findViewById(R.id.surfaceView);
        flashBtn = findViewById(R.id.toggleButton);
        historyButton = findViewById(R.id.scanHistory);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(ScanActivity.this,BarcodeHistoryActivity.class));
            }
        });
        flashBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("Ischecked",isChecked+"");
               if (isChecked){
                   flash(true);
                   flashBtn.setChecked(true);

               }else {
                   flash(false);
                   flashBtn.setChecked(false);
               }

            }
        });
        permissionStatus = this.getSharedPreferences("permissionStatus", MODE_PRIVATE);
    }

    public void initialiseDetectorsAndSources() {
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(2240, 1024)
                .build();


        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    cameraSource.start(surfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcode = detections.getDetectedItems();
                if (barcode.size() != 0) {
                    helper = new DBHelper(ScanActivity.this);
                    helper.insertBarcode(barcode.valueAt(0).displayValue);
                    startActivity(new Intent(ScanActivity.this, ResultActivity.class).putExtra("Value", barcode.valueAt(0).displayValue));
                    Log.e("barcode", barcode.valueAt(0).displayValue + "");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        flashBtn.setChecked(false);
        initialiseDetectorsAndSources();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    private void flash(boolean checked) {
        Log.e("Ischecked",checked +"");
        Camera.Parameters p;
        Camera camera = null;

        Field[] declaredFields = CameraSource.class.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getType() == Camera.class) {
                field.setAccessible(true);
                try {
                    try {
                        camera = (Camera) field.get(cameraSource);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    assert camera != null;
                    p = camera.getParameters();
                    if (checked){
                        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    }else {
                        p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    }
                    camera.setParameters(p);
                    camera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

