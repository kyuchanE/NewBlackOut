package com.androidtown.blackout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private MapFragment mapFrag;

    private ImageButton btnBack;

    private ArrayList<String> listLat;
    private ArrayList<String> listLng;

    PolylineOptions polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        btnBack = findViewById(R.id.btnBack);

        listLng = new ArrayList<>();
        listLat = new ArrayList<>();

        polyline = new PolylineOptions();

        Intent recive = getIntent();
        listLat = recive.getStringArrayListExtra("lat");
        listLng = recive.getStringArrayListExtra("lng");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        MarkerOptions marker = new MarkerOptions();

        polyline.geodesic(true);

        for(int i = 0; i < listLng.size(); i++){
            polyline.add(new LatLng(Double.parseDouble(listLat.get(i)), Double.parseDouble(listLng.get(i))));
        }
        mMap.addPolyline(polyline);

        LatLng startpoint = new LatLng(Double.parseDouble(listLat.get(0)), Double.parseDouble(listLng.get(0)));
        marker.position(startpoint).title("시작");
        mMap.addMarker(marker);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startpoint, 15));

        Log.e("@@@@@", String.valueOf(listLat.size()));
        Toast.makeText(MapsActivity.this, "몇번" + String.valueOf(listLat.size()), Toast.LENGTH_SHORT).show();


        return;

    }


}
