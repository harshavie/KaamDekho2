package com.example.kaamdekho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.view.View;



import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Househelp extends AppCompatActivity implements LocationListener {
    public Button buttonhelp;
    EditText txtAge;
    TextView display;
    TextView displayHelp;
    Button btnDisplay;
    int count=0;

    private CheckBox dusting, sweeping, washing ;
    private TextView mResultTextView;
    private ArrayList<String> mResult;
    Button button_location,payButton;
    TextView textView_location;
    LocationManager locationManager;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_househelp);
        buttonhelp= (Button)findViewById(R.id.payBtn) ;
        buttonhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Househelp.this,PaymentActivity.class);
                startActivity(intent);
            }
        });
        displayHelp = findViewById(R.id.tvDisplayHelp);
        txtAge = findViewById(R.id.hours);
        payButton = findViewById(R.id.payBtn);

        dusting= findViewById(R.id.Dusting);
        sweeping = findViewById(R.id.Sweeping);
        washing = findViewById(R.id.Washing);


        mResultTextView=findViewById(R.id.result);
        mResult=new ArrayList<>();
        mResultTextView.setEnabled(false);

        dusting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dusting.isChecked()){
                    mResult.add("Dusting is Chosen");
                    count++;
                }
            }
        });
        sweeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sweeping.isChecked()){
                    mResult.add("Sweeping is Chosen");
                    count++;
                }
            }
        });
        washing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(washing.isChecked()){
                    mResult.add("Washing is Chosen");
                    count++;
                }
            }
        });



        textView_location = findViewById(R.id.text_location);
        button_location = findViewById(R.id.button_location);
        //Runtime permissions
        if (ContextCompat.checkSelfPermission(Househelp.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Househelp.this,new String[]{
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
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtAge.getText().toString().isEmpty()){
                    Toast.makeText(Househelp.this, "Empty Field", Toast.LENGTH_SHORT).show();
                }
                else {
                    String hours = txtAge.getText().toString();
                    int rupees= Integer.parseInt(hours)*50*count;
                    displayHelp.setText("\n Your requirement is  = "+ hours+ "hours and amount is : " + rupees);

                }
            }
        });



    }


    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,Househelp.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(Househelp.this, Locale.getDefault());
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
