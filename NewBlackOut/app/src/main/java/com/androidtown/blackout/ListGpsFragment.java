package com.androidtown.blackout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Text;

public class ListGpsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapFragment mapFrag;

    PolylineOptions polyline;

    String mLat[], mLng[];

    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_listgps, container, false);

        polyline = new PolylineOptions();

        String lat = getArguments().getString("lat");
        String lng = getArguments().getString("lng");
        String date = getArguments().getString("date");

        TextView tvListDate  = view.findViewById(R.id.tvListDate);
        tvListDate.setText(date);

        makeArray(lat, lng);


        mapFrag = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.listmap);
        mapFrag.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        MarkerOptions marker = new MarkerOptions();

        polyline.geodesic(true);

        for(int i = 0; i< mLat.length; i++){
            polyline.add(new LatLng(Double.valueOf(mLat[i]), Double.valueOf(mLng[i])));
        }
        mMap.addPolyline(polyline);


        LatLng point = new LatLng(Double.valueOf(mLat[0]), Double.valueOf(mLng[0]));
        marker.position(point).title("시작");
        mMap.addMarker(marker);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));

    }

    public void makeArray(String lat, String lng){

        mLat = lat.split("/");
        mLng = lng.split("/");


    }

}
