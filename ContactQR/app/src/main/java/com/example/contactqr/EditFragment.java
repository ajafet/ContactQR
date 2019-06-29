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
import java.io.File;
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

        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        LinearLayout btnOpen = (LinearLayout) view.findViewById(R.id.edit_QR);
        btnOpen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            Intent in = new Intent(getActivity(), InputActivity.class);
            startActivityForResult(in, 10001);

            }

        });

        //
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        ImageView image = (ImageView) view.findViewById(R.id.image);

        try {

            if (getInfo() == null)  {

                image.setImageDrawable(getResources().getDrawable(R.drawable.ic_local_hospital_black_24dp));

            }
            else {

                BitMatrix bitMatrix = multiFormatWriter.encode(getInfo(), BarcodeFormat.QR_CODE, 200, 200);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                image.setImageBitmap(bitmap);

            }

        } catch (WriterException e) {

            e.printStackTrace();

        }
        //

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 10001) && (resultCode == Activity.RESULT_OK)) {

            Fragment frg = null;
            frg = getFragmentManager().findFragmentById(R.id.fragment_container);
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(frg);
            ft.attach(frg);
            ft.commit();

        }

    }

    public String getInfo () {

        FileInputStream fis = null;

        try {

            fis = getContext().openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String info_text = br.readLine();
            return info_text;

        }catch (FileNotFoundException e) {

            return null;

        }catch (IOException e) {

            return null;

        }finally {

            if (fis != null) {

                try{

                    fis.close();

                }catch (IOException e) {

                    return null;

                }

            }

        }

    }

}
