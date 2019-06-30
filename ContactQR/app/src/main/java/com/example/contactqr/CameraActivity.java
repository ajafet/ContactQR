package com.example.contactqr;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.content.Intent;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CameraActivity extends Activity implements ZXingScannerView.ResultHandler {

    // Used to scan QR Codes
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Creates new ScannerView
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);

        // Creates Result Handler for ScannerView
        mScannerView.setResultHandler(this);

        // Opens Camera
        mScannerView.startCamera();

    }

    /*  onPause() is required when implementing ZXingScannerView.
    *   Pauses the camera.
    */

    @Override
    protected void onPause () {

        super.onPause();
        mScannerView.stopCamera();

    }

    /*  onResume() is required when implementing ZXingScannerView.
     *  Resumes the camera.
     */

    @Override
    protected void onResume () {

        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

    }

    /* handleResult() is executed when ScannerView encounters a valid QR Code.
    *  The camera closes and the handles the result given by the QR Code.
    *  The QR Code text is extracted and split between "name" ":" "phone".
    *
    *  "name" and "phone" are passed to the device contacts app and fills out the information to
    *  create new contact.
    */

    @Override
    public void handleResult (Result result) {

        // Gets QR Code tex and divides text to split
        String resultText = result.getText();
        String [] split = resultText.split(":");

        // split is sent to createContact();
        createContact(split);

        // After Contact is created, Camera is resumed
        mScannerView.resumeCameraPreview(this);

    }

    /*  createContact() is executed when information from QR Code is read. The function creates
    *   a new Intent and passes the "name" and "phone" to the device's contacts app.
    */

    public void createContact (String contact[]) {

        // New Intent to Set Information
        Intent intent = new Intent(Intent.ACTION_INSERT);

        // Intent is set to Open Contacts App
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

        // In labels NAME/PHONE insert "name"/"phone"
        intent.putExtra(ContactsContract.Intents.Insert.NAME, contact[0]);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, contact[1]);

        // Runs intent
        startActivity(intent);

    }

}
