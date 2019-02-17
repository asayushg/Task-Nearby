package saini.ayush.nearbytask;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import saini.ayush.nearbytask.model.DatabaseHelper;

public class NewtaskActivity extends AppCompatActivity implements OnMapReadyCallback {
    private DatabaseHelper db;
    private EditText newTask;
    private EditText titleView;
    private  String Title;
    private  String Task;
    MapView mapView;
    private  Double longitude=77.10;
    private Double latitude=28.7;
    private FusedLocationProviderClient client;
    GoogleMap mMap;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtask);
        db = new DatabaseHelper(this);
        newTask = (EditText) findViewById(R.id.taskdetail);
        titleView = (EditText) findViewById(R.id.title);
        mapView = findViewById (R.id.mapView);

        initGoogleMap(savedInstanceState);

        client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(NewtaskActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    longitude=location.getLongitude();
                    latitude= location.getLatitude();
                    Toast.makeText(NewtaskActivity.this,latitude.toString()+" "+ longitude.toString(),Toast.LENGTH_LONG).show();
                    setMapView();
                }
                else Toast.makeText(NewtaskActivity.this,"Unable to get location",Toast.LENGTH_LONG).show();
            }
        });


    }

    public void initGoogleMap(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if(savedInstanceState !=null){
            mapViewBundle = savedInstanceState.getBundle ("MapViewBundleKey");
        }
        mapView.onCreate (mapViewBundle);
        mapView.getMapAsync (this);
    }


    public void saveTask(View view){
        Title= titleView.getText().toString();
        Task = newTask.getText().toString();
        if(Title.isEmpty()){
            Toast.makeText(this,"Title cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        boolean isInserted = db.insertData(Title,Task);
        if(isInserted) {
            Toast.makeText(this,"Task Added",Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this,"Failed to add Task",Toast.LENGTH_SHORT).show();

        super.onBackPressed ();
    }


    public void BackButton(View v){
        super.onBackPressed ();
    }

                       ////     GOOGLE API KEY INCLUDED      //////

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState (outState);
        Bundle mapViewBundle = outState.getBundle ("MapViewBundleKey");
        if(mapViewBundle == null){
            mapViewBundle = new Bundle();
            outState.putBundle ("MapViewBundleKey",mapViewBundle);
        }
        mapView.onSaveInstanceState (mapViewBundle);

    }

    @Override
    public void onMapReady ( GoogleMap googleMap ) {

    mMap = googleMap;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    public void setMapView(){
        LatLng coordinate = new LatLng (latitude,longitude);

        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(coordinate);

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title("You");

        // Clears the previously touched position
        mMap.clear();
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(coordinate)
                .zoom(12).build();

        // Animating to the touched position
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        // Placing a marker on the touched position
        mMap.addMarker(markerOptions);
    }

}
