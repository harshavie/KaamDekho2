package com.example.kaamdekho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Driver extends AppCompatActivity implements LocationListener {
    EditText txtAge;
    public Button buttondrive;
    TextView display;
    Button payDriver;
    int count =0;

    private RadioButton Bike, Sedan, SUV;
    private Button mWriteResultButton;
    private TextView mResultTextView;
    private ArrayList<String> mResult;
    Button button_location;
    TextView textView_location;
    LocationManager locationManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        buttondrive= (Button)findViewById(R.id.payDriver) ;
        buttondrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Driver.this,PaymentActivity.class);
                startActivity(intent);
            }
        });
        txtAge = findViewById(R.id.hours);
        display=findViewById(R.id.tvDisplay);
        payDriver=findViewById(R.id.payDriver);
        payDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtAge.getText().toString().isEmpty()){
                    Toast.makeText(Driver.this, "Empty Field", Toast.LENGTH_SHORT).show();
                }
                else {
                    String hours = txtAge.getText().toString();
                    display.setText("\n Your requirement is  = "+ hours+ "hours and amount is : " + Integer.parseInt(hours)*50*count);
                }
            }
        });
        Bike= findViewById(R.id.Bike);
        Sedan = findViewById(R.id.Sedan);
        SUV = findViewById(R.id.SUV);


        mResultTextView=findViewById(R.id.result);
        mResult=new ArrayList<>();
        mResultTextView.setEnabled(false);

        Bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Bike.isChecked()){
                    mResult.add("Bike is Chosen");
                    count++;
                }
            }
        });
        Sedan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Sedan.isChecked()){
                    mResult.add("Sedan is Chosen");
                    count++;
                }
            }
        });
        SUV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SUV.isChecked()){
                    mResult.add("SUV is Chosen");
                    count++;
                }
            }
        });


        textView_location = findViewById(R.id.text_location);
        button_location = findViewById(R.id.button_location);
        //Runtime permissions
        if (ContextCompat.checkSelfPermission(Driver.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Driver.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }


        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create method
                getLocation();
            }
        });



    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,Driver.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(Driver.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            textView_location.setText(address);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
