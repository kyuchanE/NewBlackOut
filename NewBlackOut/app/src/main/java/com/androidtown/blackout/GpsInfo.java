package com.androidtown.blackout;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class GpsInfo extends Service implements LocationListener {

    private final Context mContext;

    //현재 GPS 사용 유무
    boolean isGPSEnabled = false;

    //네트워크 사용 유무
    boolean isNetworkEnabled = false;

    boolean isGetLocation = false;

    Location location;
    double lat;
    double lon;

    ArrayList<Double> listLat;
    ArrayList<Double> listLng;

    //최소 GPS 정보 업데이트 거리 5미터
    private static final long MIN_DISTANCE = 1;

    //최소 GPS 정보 업데이트 시간
    private static final long MIN_TIME = 1000;

    protected LocationManager locationManager;

    public GpsInfo(Context mContext) {
        this.mContext = mContext;
        listLng = new ArrayList<>();
        listLat = new ArrayList<>();
        getLocation();
    }

    public Location getLocation(){
        if(Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return null;
        }
        try{
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            isGetLocation = true;
            if(isNetworkEnabled){
                Log.e("isNetwork", "@");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                if(locationManager != null){
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if(location != null){
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                    }
                }
            }
            if(isGPSEnabled){
                if(location == null){
                    Log.e("isNetwork", "@");
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                    if(locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if(location != null){
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        listLat.add(lat);
        listLng.add(lon);
        Log.e("Lat 1st", Double.toString(lat));
        Log.e("Lng 1st", Double.toString(lon));
        Toast.makeText((ResultActivity)ResultActivity.mContext, "시작" + Double.toString(lat) + "/" + Double.toString(lon) , Toast.LENGTH_SHORT).show();
        return location;
    }

    //GPS종료
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GpsInfo.this);
        }
    }

    public double getLatitude(){
        if(location != null){
            lat = location.getLatitude();
        }
        return lat;
    }

    public double getLongtitude(){
        if(location != null){
            lon = location.getLongitude();
        }
        return lon;
    }

    public boolean isGetLocation(){
        return isGetLocation;
    }

    public ArrayList<Double> setLatList(){
        return listLat;
    }

    public ArrayList<Double> setLngList(){
        return listLng;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        lon = location.getLongitude();
        lat = location.getLatitude();

        listLat.add(lat);
        listLng.add(lon);
        Log.e("Lat", Double.toString(lat));
        Log.e("Lng", Double.toString(lon));
        Toast.makeText((ResultActivity)ResultActivity.mContext, "위치변화" + Double.toString(lat) + "/" + Double.toString(lon) , Toast.LENGTH_SHORT).show();

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
