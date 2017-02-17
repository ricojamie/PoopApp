package com.example.ricoj.poopapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main2 extends AppCompatActivity {
    private SwipeRefreshLayout swipeContainer;

    ListView listOfPoops;
    ArrayList<String> poopAL;
    ArrayList<Poop> poopAL2;

    Button newPoop;

    //firebase user information
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    //trying something out
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userID = database.getReference("Users");
    private String TAG;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Lookup Swipe view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        //Setup refresh listener

       swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               loadPoopList();

            swipeContainer.setRefreshing(false);
           }
       });

        FloatingActionButton newPoop = (FloatingActionButton) findViewById(R.id.newPoopButton);


        newPoop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Main2.this, NewPoop.class));
            }
        });

        // Write a message to the database


        listOfPoops = (ListView) findViewById(R.id.listOfPoops);

        poopAL = new ArrayList<>();
        poopAL2 = new ArrayList<>();


/*
        if (userID.child(uid) != null) {
            loadPoopList();
            Log.d("String", "Poop is loaded");
        } else {

            startActivity(new Intent(Main2.this, SignIn.class));
        }
*/

    }
/*
    protected void onStart() {
        super.onStart();
        if (userID.child(uid) !=null) {
            for (int i=0; i<10; i++) {
                loadPoopList();
            }
        }
    }


    public void onResume() {
        super.onResume();

        if (userID.child(uid) !=null) {

                loadPoopList();

        }
    }
    */

    public void loadPoopList() {
        uid = user.getUid();

        if (userID.child(uid) != null) {
            userID.child(uid).addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            //get children at this level
                                                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                                            for (DataSnapshot child : children) {
                                                                Poop poop = child.getValue(Poop.class);
                                                                if (!poopAL.contains(poop.getDateAndTime())) {
                                                                    poopAL2.add(poop);
                                                                    poopAL.add(poop.getDateAndTime());
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {
                                                            Log.w(TAG, "failed to read value.", databaseError.toException());
                                                        }
                                                    }
            );
        } else {
            Toast.makeText(this, "No poops", Toast.LENGTH_LONG).show();
        }

        //putting the arraylist in the listview
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                poopAL);

        listOfPoops.setAdapter(arrayAdapter);
        listOfPoops.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(Main2.this).create();
                alertDialog.setTitle(poopAL2.get(position).getDateAndTime());
                TextView myMsg = new TextView(getApplicationContext());
                myMsg.setText("Type:  " + poopAL2.get(position).getType() +
                        "\n Color: " + poopAL2.get(position).getColor() +
                        "\n Struggle: " + poopAL2.get(position).getStruggle() +
                        "\n Smell: " + poopAL2.get(position).getSmell() +
                        "\n Wipes: " + poopAL2.get(position).getNumOfWipes() +
                        "\n Duration: " + poopAL2.get(position).getDuration() +
                        "\n Notes: " + poopAL2.get(position).getNotes());
                myMsg.setGravity(Gravity.CENTER);
                myMsg.setTextColor(Color.WHITE);
                myMsg.setTextSize(14);
                alertDialog.setView(myMsg);

                /*
                alertDialog.setMessage("Type:  " + poopAL2.get(position).getType() +
                        "\n Color: " + poopAL2.get(position).getColor() +
                        "\n Struggle: " + poopAL2.get(position).getStruggle() +
                        "\n Smell: " + poopAL2.get(position).getSmell() +
                        "\n Wipes: " + poopAL2.get(position).getNumOfWipes() +
                        "\n Duration: " + poopAL2.get(position).getDuration() +
                        "\n Notes: " + poopAL2.get(position).getNotes());
                        */
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                alertDialog.show();

            }
        });
    }
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Main2.this, SignIn.class));
        finish();
    }

}
