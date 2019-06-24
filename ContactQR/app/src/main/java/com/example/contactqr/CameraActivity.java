package com.example.contactqr;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.content.Intent;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CameraActivity extends Activity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

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
    protected void onResume () {

        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

    }

    @Override
    public void handleResult (Result result) {

        String resultText = result.getText();
        String [] split = resultText.split(":");

        createContact(split);

        /*
        Log.v("handleResult", result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        */

        mScannerView.resumeCameraPreview(this);

    }

    public void createContact (String contact[]) {

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.NAME, contact[0]);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, contact[1]);

        startActivity(intent);

    }

}
