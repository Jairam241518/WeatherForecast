package com.jairam.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    final String API_KEY = "866218e7d7e0728da144392f45412464";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";

    final long MIN_TIME = 5000;
    final float MIN_DIS = 1000;

    final int REQUEST_CODE = 101;

    String LocationProvider = LocationManager.GPS_PROVIDER;

    TextView cityName, weatherState, temperature, maxTemp, minTemp, latitude, longitude;
    ImageView weatherImage;

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = findViewById(R.id.location);
        weatherState = findViewById(R.id.weathertype);
        temperature = findViewById(R.id.temperature);
        maxTemp = findViewById(R.id.maxtemp);
        minTemp = findViewById(R.id.mintemp);
        latitude = findViewById(R.id.Latitude);
        longitude = findViewById(R.id.longitude);

        weatherImage = findViewById(R.id.imageView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWeather();
    }

    private void getWeather() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                String latitude = String.valueOf(location.getLatitude());
                String longitude = String.valueOf(location.getLongitude());

                RequestParams params = new RequestParams();
                params.put("lat", latitude);
                params.put("long", longitude);
                params.put("api_id", API_KEY);
                getApiData(params);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras){

            }
            @Override
            public void onProviderEnabled(String provider){

            }
            @Override
            public void onProviderDisabled(String provider){
                Toast.makeText(MainActivity.this, "Not able to fetch location", Toast.LENGTH_SHORT).show();
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        locationManager.requestLocationUpdates(LocationProvider, MIN_TIME, MIN_DIS, locationListener);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                getWeather();
            }
            else {
                Toast.makeText(MainActivity.this, "Get out of the app !", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void getApiData(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Toast.makeText(MainActivity.this, "hey", Toast.LENGTH_SHORT).show();

                apiData apiData = com.jairam.weatherforecast.apiData.fromJson(response);
                updateUi(apiData);
                //super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(MainActivity.this, "hey", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUi(apiData apiData) {

        temperature.setText(apiData.getTemperature());
        cityName.setText(apiData.getCity());
        weatherState.setText(apiData.getWeatherType());
        latitude.setText((CharSequence) latitude);
        longitude.setText((CharSequence) longitude);


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationManager!=null){
            locationManager.removeUpdates(locationListener);
        }
    }
}