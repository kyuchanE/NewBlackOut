package com.androidtown.blackout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
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

    String lat, lng, date;

    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle = getArguments();
        lat = bundle.getString("lat");
        lng = bundle.getString("lng");
        date = bundle.getString("date");

        makeArray(lat, lng);

        polyline = new PolylineOptions();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try{
            view = inflater.inflate(R.layout.fragment_listgps, container, false);

            TextView tvListDate  = view.findViewById(R.id.tvListDate);
            tvListDate.setText(date);

            mapFrag = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.listmap);
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

        for(int i = 0; i < mLat.length; i++){
            polyline.add(new LatLng(Double.parseDouble(mLat[i]), Double.parseDouble(mLng[i])));
        }
        mMap.addPolyline(polyline);


        LatLng point = new LatLng(Double.parseDouble(mLat[0]), Double.parseDouble(mLng[0]));
        marker.position(point).title("시작");
        mMap.addMarker(marker);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));

        return;

    }

    public void makeArray(String lat, String lng){

        mLat = lat.split("/");
        mLng = lng.split("/");
        Toast.makeText(getActivity(), String.valueOf(mLat.length), Toast.LENGTH_SHORT).show();


    }

}
