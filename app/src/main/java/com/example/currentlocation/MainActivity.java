package com.example.currentlocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    TextView tvLokasiSaya, tvJarak;
    EditText etLatitude, etLongitude;
    Button btJarak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},0);

        tvLokasiSaya = (TextView) findViewById(R.id.lokasiUser);
        tvJarak = (TextView) findViewById(R.id.tvJarak);
        etLatitude = (EditText) findViewById(R.id.etLatitude);
        etLongitude = (EditText) findViewById(R.id.etLongitude);
        btJarak = (Button) findViewById(R.id.button);

        btJarak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetLocation getLocation = new GetLocation(getApplicationContext());
                Location location = getLocation.getLocation();
                if (location!=null){
                    double latitudeSaya = location.getLatitude();
                    double longitudeSaya = location.getLongitude();
                    double latitudeTujuan = Double.valueOf(etLatitude.getText().toString());
                    double longitudeTujuan = Double.valueOf(etLongitude.getText().toString());

                    tvLokasiSaya.setText(latitudeSaya + ", " + longitudeSaya);
                    double jarak = getLocation(latitudeTujuan, longitudeTujuan, latitudeSaya, longitudeSaya);
                    tvJarak.setText(String.format("%.2f KM",jarak));
                }
            }
        });

    }

    private double getLocation(double latitudeTujuan, double longitudeTujuan, double latitudeSaya, double longitudeSaya) {
        Double lat1 = latitudeTujuan;
        Double lon1 = longitudeTujuan;
        Double lat2 = latitudeSaya;
        Double lon2 = longitudeSaya;
        final int R = 6371;

        Double latRad1 = lat1 * (Math.PI / 180);
        Double latRad2 = lat2 * (Math.PI / 180);
        Double deltaLatRad = (lat2 - lat1) * (Math.PI / 180);
        Double deltaLonRad = (lon2 - lon1) * (Math.PI / 180);
        //rumus haversine//
        Double a = Math.sin(deltaLatRad / 2)
                * Math.sin(deltaLatRad / 2)
                + Math.cos(latRad1)
                * Math.cos(latRad2)
                * Math.sin(deltaLonRad / 2)
                * Math.sin(deltaLonRad / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double s = R * c;
        return s;
    }
}
