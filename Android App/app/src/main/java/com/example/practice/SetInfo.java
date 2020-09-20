package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.fitness.result.DataReadResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetInfo extends AppCompatActivity {

    private final Executor executor = new Executor() {
        @Override
        public void execute(Runnable runnable) {
            new Thread(runnable).start();
        }
    };
    private static boolean shareLocation = false;
    private static boolean shareHealth = false;
    private EditText et;
    private FusedLocationProviderClient fusedLocationClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_info);

        //Create a checkbox object
        CheckBox c1 = (CheckBox) findViewById(R.id.LocationBox);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Is the view now checked?
                boolean checked = ((CheckBox) view).isChecked();
                SetInfo.shareLocation = checked;
            }
        } );

        CheckBox c2 = (CheckBox) findViewById(R.id.HealthBox);
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Is the view now checked?
                boolean checked = ((CheckBox) view).isChecked();
                SetInfo.shareHealth = checked;
            }
        } );

        //Create EditText object to accept user input
        EditText nameText = (EditText) findViewById(R.id.nameText);
        nameText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                //Check if they clicked the done button
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //If so then hide the keyboard
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    //Get the text they entered and store it as name
                    SharedPreferences prefs = getSharedPreferences("UserSettings", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("name", v.toString());

                    //Set the text in the box to nothing
                    v.setText("");
                    return true;
                }
                return false;

            }
        });
        //Create EditText object to accept user input
        EditText genderText = (EditText) findViewById(R.id.genderText);
        genderText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                //Check if they clicked the done button
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //If so then hide the keyboard
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    //Get the text they entered and store it as name
                    SharedPreferences prefs = getSharedPreferences("UserSettings", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("gender", v.toString());

                    //Set the text in the box to nothing
                    v.setText("");
                    return true;
                }
                return false;

            }
        });
        //Create EditText object to accept user input
        EditText ageText = (EditText) findViewById(R.id.ageText);
        ageText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                //Check if they clicked the done button
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //If so then hide the keyboard
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    //Get the text they entered and store it as name
                    SharedPreferences prefs = getSharedPreferences("UserSettings", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("age", v.toString());

                    //Set the text in the box to nothing
                    v.setText("");
                    return true;
                }
                return false;

            }
        });
        //Create EditText object to accept user input
        EditText heightText = (EditText) findViewById(R.id.heightText);
        heightText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                //Check if they clicked the done button
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //If so then hide the keyboard
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    //Get the text they entered and store it as name
                    SharedPreferences prefs = getSharedPreferences("UserSettings", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("height", v.toString());

                    //Set the text in the box to nothing
                    v.setText("");
                    return true;
                }
                return false;

            }
        });
        //Create EditText object to accept user input
        EditText weightText = (EditText) findViewById(R.id.weightText);
        weightText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                //Check if they clicked the done button
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //If so then hide the keyboard
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    //Get the text they entered and store it as name
                    SharedPreferences prefs = getSharedPreferences("UserSettings", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("weight", v.toString());

                    //Set the text in the box to nothing
                    v.setText("");
                    return true;
                }
                return false;

            }
        });

        //Calls nextPage() on button click
        final Button button = findViewById(R.id.NextButton2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((SetInfo) v.getContext()).nextPage();
            }
        });
    }

    public void nextPage() {
        //Save user variables
        if (et != null) {
            String name = et.getText().toString();
            SharedPreferences prefs = getSharedPreferences("UserSettings", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Name", name);
            editor.apply();
        }
        //Enables location
        if (shareLocation) {
            statusCheck();
            //A client which can be used to get the location
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            //Check if the permissions are now appropriate
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Creates a client which can fetch location
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    SetInfo.interpretLocation(location);
                                }
                            }
                        });
            }
        }
        //Goes to the menu page
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
    public static void interpretLocation(Location location){

        //Convert to latitude and longitude
        Double lat = location.getLatitude();
        Double lon = location.getLongitude();

        //Convert these to address form
        /*
        ReverseGeocoder rg = new ReverseGeocoder();
        try{
            Map<String, String> addressMap = rg.ReverseGeocode(Double.toString(lat), Double.toString(lon));
            UserData.locationMap = addressMap;
            UserData.country_code = addressMap.get("country_code");
        }
        catch(Exception e){
        }
         */
    }

    //Checks if the location services are enables
    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    //Creates dialogue box to prompt location services
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}