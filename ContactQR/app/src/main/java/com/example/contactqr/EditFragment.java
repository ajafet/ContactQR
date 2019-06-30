package com.example.contactqr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import android.support.v4.app.FragmentTransaction;
import android.app.Activity;

public class EditFragment extends Fragment {

    private static final String FILE_NAME = "info.txt";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Required When Using Fragments
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        /*  When the Layout is Clicked, a New Activity Begins Using Intent.

        *   startActivityForResult() is used to get a Result from the Intent, so we are
        *   required to send an additional integer argument, requestCode. The requestCode
        *   identifies the intent request and is used to handle the results.
        */
        LinearLayout btnOpen = (LinearLayout) view.findViewById(R.id.edit_QR);
        btnOpen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            Intent in = new Intent(getActivity(), InputActivity.class);
            startActivityForResult(in, 1);

            }

        });

        /*  Initializing the Image in Edit Page.
        *   Notice how we find the xml contents, view is needed when using fragments.
        */
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        ImageView image = (ImageView) view.findViewById(R.id.image);

        /*  Next piece of code determines what image is displayed when Edit Page is loaded.
        *   getInfo() returns null if there is still no data saved in the phone, and sets image to
        *   a default stock Image. If getInfo() does not return null, then the phone has information
        *   stored. The information is pulled from the "into.txt" file, converted to QR Code and
        *   set to image on the Edit Page.
        */
        try {

            if (getInfo() == null)  {

                // Default Image.
                image.setImageDrawable(getResources().getDrawable(R.drawable.ic_local_hospital_black_24dp));

            }
            else {

                // Converting Information to QR Code.
                BitMatrix bitMatrix = multiFormatWriter.encode(getInfo(), BarcodeFormat.QR_CODE, 200, 200);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

                // Converting QR Code to Image.
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                // Setting Image to image on Edit Page.
                image.setImageBitmap(bitmap);

            }

        } catch (WriterException e) {

            e.printStackTrace();

        }

        // Required to Return
        return view;
    }

    /*  onActivityResult() is executed once the user has saved new information into the "info.txt"
    *   file. The function checks the requestCode passed by the intent in onCreateView() and the
    *   result given by the intent, if true then the Edit Page is reloaded causing a new QR Code
    *   to be generated with new information .
    */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Checks data.
        if ((requestCode == 1) && (resultCode == Activity.RESULT_OK)) {

            // Finds Fragment that needs to be reloaded.
            Fragment frg = getFragmentManager().findFragmentById(R.id.fragment_container);
            final FragmentTransaction ft = getFragmentManager().beginTransaction();

            // Removes current fragment.
            ft.detach(frg);

            // Reloads the same fragment.
            ft.attach(frg);

            // Executes change of fragment.
            ft.commit();

        }

    }

    /*  getInfo() function checks if there is data stored in "info.txt" file. If the file has not
    *   not been created or there is a problem reading/closing the file, the function returns null.
    *   If there are no problems reading the information, function returns the information.
    */

    public String getInfo () {

        // Creating Stream
        FileInputStream text_file = null;

        try {

            // Opening File
            text_file = getContext().openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(text_file);
            BufferedReader br = new BufferedReader(isr);

            // Gets Information & Returns Information
            return br.readLine();

        }catch (FileNotFoundException e) {

            return null;

        }catch (IOException e) {

            return null;

        }finally {

            // Closes the text_file stream

            if (text_file != null) {

                try{

                    text_file.close();

                }catch (IOException e) {

                    return null;

                }

            }

        }

    }

}
