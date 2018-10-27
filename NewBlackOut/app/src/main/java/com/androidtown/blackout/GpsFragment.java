package com.androidtown.blackout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class GpsFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    private MapFragment mapFrag;

    private ArrayList<String> listLat;
    private ArrayList<String> listLng;

    PolylineOptions polyline;

    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listLng = new ArrayList<>();
        listLat = new ArrayList<>();

        polyline = new PolylineOptions();

        Bundle bundle = getArguments();
        listLat = bundle.getStringArrayList("lat");
        listLng = bundle.getStringArrayList("lng");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try{
             view = inflater.inflate(R.layout.fragment_gps, container, false);

            mapFrag = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
            mapFrag.getMapAsync(this);
        }catch (InflateException e){

        }
        return view;
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
        Toast.makeText(getActivity(), "몇번" + String.valueOf(listLat.size()), Toast.LENGTH_SHORT).show();


        return;

    }
}
