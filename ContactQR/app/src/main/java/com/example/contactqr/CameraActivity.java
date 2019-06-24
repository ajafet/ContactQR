package com.example.contactqr;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import com.google.zxing.Result;
import android.util.Log;


import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CameraActivity extends Activity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }

    public void onClick(View v) {

        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

    }

    @Override
    protected void onPause () {

        super.onPause();
        mScannerView.stopCamera();

    }

    @Override
    public void handleResult (Result result) {

        Log.v("handleResult", result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //mScannerView.resumeCameraPreview(this);

    }

}
