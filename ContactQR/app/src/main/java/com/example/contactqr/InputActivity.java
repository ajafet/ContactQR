package com.example.contactqr;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputActivity extends AppCompatActivity {

    private static final String FILE_NAME = "info.txt";

    // Declaring xml components.
    private EditText name;
    private EditText phone;
    private Button gen_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // Linking activity_input.xml components.
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        gen_btn = (Button) findViewById(R.id.gen_btn);

        // Function Used to Load "info.txt" information to corresponding text fields.
        loadInfo();

        // Text fields are given listeners to make sure no blank information is stored.
        name.addTextChangedListener(textWatcher);
        phone.addTextChangedListener(textWatcher);

        // Disable gen_btn button if "info.txt" information is blank.
        checkFields ();

        /*  When gen_btn() is pressed: information on text fields is saved to the device, activity
        *   result is set to RESULT_OK, and the InputActivity is closed.
        */
        gen_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            // Called to save information to "info.txt" file.
            saveInfo();

            // Sets activity to RESULT_OK causing Edit Fragment to reload.
            setResult(Activity.RESULT_OK);

            // Terminates Input Activity, returning to Edit Fragment.
            finish();

            }
        });

    }

    /*  TextWatcher is executed when the value of a text field is changed. When declared the
    *   following three functions have to be initialized, but we only needed onTextChanged().
    *   When text in name/phone is changed, onTextChanged() is called.
    */

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFields ();
        }

        @Override
        public void afterTextChanged(Editable editable) {}

    };

    /*  checkFields() is executed when onTextChanged() is executed and at the start of Input
    *   Activity. This function disables the gen_btn if both or either name/phone text fields
    *   are empty.
    */

    private void checkFields (){

        // Gets values from activity_input.xml
        Button submit = (Button) findViewById(R.id.gen_btn);
        String n = name.getText().toString();
        String p = phone.getText().toString();

        // Disables button if either name/phone equal to blank or both
        if(n.equals("") && p.equals("")) {
            submit.setEnabled(false);
        }
        else if(!n.equals("") && p.equals("")) {
            submit.setEnabled(false);
        }
        else if(!p.equals("") && n.equals("")) {
            submit.setEnabled(false);
        }
        else {
            submit.setEnabled(true);
        }

    }

    /*  saveInfo() is executed when gen_btn is pressed. This function saves the information in
    *   name/phone text fields to the "info.txt" file. The information is saved as "name:phone", so
    *   when we scan a QR Code we know how to handle the information to create a Contact.
    */

    public void saveInfo () {

        // Creating Stream
        FileOutputStream text_file = null;

        try {

            // Gathers data from name/phone text fields
            String text2QR = name.getText().toString().trim() + ":" + phone.getText().toString().trim();

            // Opening File
            text_file = openFileOutput(FILE_NAME, MODE_PRIVATE);

            // Writing txt2QR into file
            text_file.write(text2QR.getBytes());

            // Displays Message to user notifying data has been "Saved"
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

            // Closing File
            text_file.close();

        }catch (FileNotFoundException e) {

            e.printStackTrace();

        }catch (IOException e) {

            e.printStackTrace();

        }

    }

    /*  loadInfo() is executed when InputActivity is created. This function reads information from
    *   "info.txt" file and sets it to the corresponding name/phone text fields. Since the
    *   information is stored as "name:phone", the information read in is split between ":".
    */

    public void loadInfo () {

        //Creating Stream
        FileInputStream text_file = null;

        try {

            // Opening File
            text_file = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(text_file);
            BufferedReader br = new BufferedReader(isr);

            // Reads in Information and Stores in info
            String info = br.readLine();

            // Info is split between ":"
            String [] split = info.split(":");

            // split[0] value is placed in name text field
            name.setText(split[0]);

            // split[1] value is placed in phone text field
            phone.setText(split[1]);

        }catch (FileNotFoundException e) {

            e.printStackTrace();

        }catch (IOException e) {

            e.printStackTrace();

        }finally {

            // Closes the text_file stream

            if (text_file != null) {

                try{

                    text_file.close();

                }catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }

    }

}
