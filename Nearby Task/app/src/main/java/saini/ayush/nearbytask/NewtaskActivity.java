package saini.ayush.nearbytask;


import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import saini.ayush.nearbytask.model.DatabaseHelper;

public class NewtaskActivity extends AppCompatActivity implements OnMapReadyCallback {
    private DatabaseHelper db;
    private EditText newTask;
    private EditText titleView;
    private  String Title;
    private  String Task;
    MapView mapView;
    protected LocationManager locationManager;
    private Double lat,lon;
    //private
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtask);
        db = new DatabaseHelper(this);
        newTask = (EditText) findViewById(R.id.taskdetail);
        titleView = (EditText) findViewById(R.id.title);
        mapView = findViewById (R.id.mapView);

        initGoogleMap(savedInstanceState);


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

        LatLng coordinate = new LatLng (26.249819, 78.169722);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 11);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
        googleMap.animateCamera(yourLocation);
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
}
