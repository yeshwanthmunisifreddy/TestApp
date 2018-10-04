package technology.nine.test;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import technology.nine.test.data.DBHelper;

public class ScannerActiivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA = 106;
    private ZXingScannerView scannerView;
    DBHelper helper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermissions()) {

            } else {
                requestPermission();
            }
        }
    }

    private boolean checkPermissions() {
        return (ContextCompat.checkSelfPermission(ScannerActiivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean camereAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (camereAccepted == true) {
                        Toast.makeText(ScannerActiivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ScannerActiivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                 displayAlertMessage("You need to access for both permission", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA);
                                     }
                                 });
                                 return;
                            }
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23){
            if (checkPermissions()){
                if (scannerView == null){
                     scannerView  = new ZXingScannerView(this);

                }
                scannerView.setResultHandler( this);
                scannerView.startCamera();
            }else {
                requestPermission();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(ScannerActiivity.this)
                .setMessage(message)
                .setPositiveButton("Ok",listener)
                .setNegativeButton("Cancel",null)
                .create();
    }

    @Override
    public void handleResult(Result result) {
        String scanResult = result.getText();
        helper = new DBHelper(this);
        helper.insertBarcode(scanResult);
        startActivity(new Intent(ScannerActiivity.this,ResultActvitiy.class).putExtra("Value",scanResult));

    }
}
