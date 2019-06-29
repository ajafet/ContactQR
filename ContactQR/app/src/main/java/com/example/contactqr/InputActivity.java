package com.example.contactqr;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import android.support.v4.app.Fragment;

import android.app.AlertDialog;



public class InputActivity extends AppCompatActivity {

    private static final String FILE_NAME = "info.txt";
    private EditText name;
    private EditText phone;
    private Button gen_btn;
    private ImageView image;
    private String text2QR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        gen_btn = (Button) findViewById(R.id.gen_btn);
        image = (ImageView) findViewById(R.id.image);

        loadInfo();

        //set listeners
        name.addTextChangedListener(textWatcher);
        phone.addTextChangedListener(textWatcher);

        // run once to disable if empty
        checkFieldsForEmptyValues();

        gen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text2QR = name.getText().toString().trim() + ":" + phone.getText().toString().trim();
                saveInfo();

                setResult(Activity.RESULT_OK);

                finish();

            }
        });

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private void checkFieldsForEmptyValues(){

        Button b = (Button) findViewById(R.id.gen_btn);
        String s1 = name.getText().toString();
        String s2 = phone.getText().toString();

        if(s1.equals("") && s2.equals(""))
        {
            b.setEnabled(false);
        }

        else if(!s1.equals("") && s2.equals("")){
            b.setEnabled(false);
        }

        else if(!s2.equals("") && s1.equals(""))
        {
            b.setEnabled(false);
        }

        else
        {
            b.setEnabled(true);
        }
    }

    public void saveInfo () {

        FileOutputStream fos = null;

        try {

            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text2QR.getBytes());

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            fos.close();

        }catch (FileNotFoundException e) {

            e.printStackTrace();

        }catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void loadInfo () {

        FileInputStream fis = null;

        try {

            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String info = br.readLine();
            String [] split = info.split(":");

            name.setText(split[0]);
            phone.setText(split[1]);

        }catch (FileNotFoundException e) {

            e.printStackTrace();

        }catch (IOException e) {

            e.printStackTrace();

        }finally {

            if (fis != null) {

                try{

                    fis.close();

                }catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }

    }

}
