package com.example.ricoj.poopapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.R.id.progress;
import static com.example.ricoj.poopapp.R.id.numOfWipes;

public class NewPoop extends AppCompatActivity {

    Spinner type;
    Spinner color;
    SeekBar struggle;
    TextView struggleProgress;
    SeekBar smell;
    EditText numOfWipes;
    EditText duration;
    EditText notes;
    ImageButton infoButton;
    TextView smellProgress;

    Button submit;

    String uID;
    String selectedTypeText;
    String selectedColorText;
    String struggleInt;
    String smellInt;


    private DatabaseReference mDatabaseReference;

    //date and time things
    Calendar calendar;
    DateFormat simpleDateFormat = DateFormat.getDateInstance();
    String date;

    //firebase things
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userID = database.getReference("Users");

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_poop);

        //instantiating date and time
        calendar = Calendar.getInstance();
        DateFormat simpleDateFormat = DateFormat.getDateTimeInstance();
        date = simpleDateFormat.format(calendar.getTime());

        type = (Spinner) findViewById(R.id.spinnerType);
        color = (Spinner) findViewById(R.id.spinnerColor);
        struggle = (SeekBar) findViewById(R.id.seekBarstruggle);
        struggleProgress = (TextView) findViewById(R.id.struggleText);
        smell = (SeekBar) findViewById(R.id.seekBarSmell);
        numOfWipes = (EditText) findViewById(R.id.numWipesText);
        duration = (EditText) findViewById(R.id.editTextDuration);
        notes = (EditText) findViewById(R.id.editTextNotes);
        smellProgress = (TextView) findViewById(R.id.smellProgress);

        submit = (Button) findViewById(R.id.buttonSubmit);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        String[] spinnerItems = new String[] {
                "Type 1",
                "Type 2",
                "Type 3",
                "Type 4",
                "Type 5",
                "Type 6",
                "Type 7",
        };

        String[] poopColor = new String[] {
                "Green",
                "Light Brown",
                "Yellow",
                "Black",
                "Red",
                "Dark Brown"
        };
        //Populating Spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.spinner_layout, spinnerItems);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, R.layout.spinner_layout, poopColor);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        color.setAdapter(adapter2);


        //Get String value of Spinners
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTypeText = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 selectedColorText = (String) parent.getItemAtPosition(position);


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Struggle Seek Bar
        struggle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int c, boolean b) {
                struggleProgress.setText( " " + c);
                struggleInt = String.valueOf(c);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });

        //Smell seek bar
        smell.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                smellProgress.setText( " " + i);
                smellInt = String.valueOf(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });



        //button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uID = user.getUid();

                Log.i("the date", date);
                System.out.print(date);

                Poop poop = new Poop(selectedTypeText, selectedColorText, struggleInt, smellInt, numOfWipes.getText().toString(), duration.getText().toString(), notes.getText().toString(), date);

                DatabaseReference newRef = userID.child(uID).push();
                newRef.setValue(poop);
                finish();
            }
        });

        infoButton = (ImageButton) findViewById(R.id.infoButton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog poopPicture = new Dialog(NewPoop.this);
                poopPicture.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                poopPicture.setContentView(getLayoutInflater().inflate(R.layout.poop_picture, null));
                poopPicture.show();
            }
        });
    }


}
