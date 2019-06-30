package com.example.contactqr;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.ImageButton;

public class ScanFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Required When Using Fragments
        View view = inflater.inflate(R.layout.fragment_scan, container, false);

        /*  When the ImageButton is clicked, a New Activity Begins Using Intent.
        *   Camera is opened once user clicks button and waits until a valid QR Code is scanned.
        */
        ImageButton btnOpen = (ImageButton) view.findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), CameraActivity.class);
                startActivity(in);

            }

        });

        // Required to Return
        return view;
    }

}
