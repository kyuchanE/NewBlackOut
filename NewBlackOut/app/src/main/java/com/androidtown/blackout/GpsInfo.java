package com.androidtown.blackout;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GpsInfo extends Service implements LocationListener {

    //현재 GPS 사용 유무
    boolean isGPSEnabled = false;

    //네트워크 사용 유무
    boolean isNetworkEnabled = false;

    boolean isGetLocation = false;

    Location location;
    double lat;
    double lon;

    ArrayList<String> listLat;
    ArrayList<String> listLng;

    NotificationManager notifManager;
    String channelld = "BO channel";
    String channelName = "Black Out";

    private String saveLat = "", saveLng = "";

    ListDBHelper listDBHelper;

    SimpleDateFormat time, timef;
    Date dt;


    //최소 GPS 정보 업데이트 거리 5미터
    private static final long MIN_DISTANCE = 1;

    //최소 GPS 정보 업데이트 시간
    private static final long MIN_TIME = 1000;

    protected LocationManager locationManager;

    private IBinder mBinder = new LocalBinder();


    public class LocalBinder extends Binder {
        public GpsInfo getService() {
            return GpsInfo.this;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        listLng = new ArrayList<>();
        listLat = new ArrayList<>();
        getLocation();

        listDBHelper = new ListDBHelper(MainActivity.mContext);

        Log.d("test", "서비스의 onCreate");

    }

    public Location getLocation() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission((MainActivity.mContext), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission((MainActivity.mContext), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return null;
        }
        try {
            locationManager = (LocationManager) (MainActivity.mContext).getSystemService(LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            isGetLocation = true;
            if (isNetworkEnabled) {
                Log.e("isNetwork", "@");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                    }
                }
            }
            if (isGPSEnabled) {
                if (location == null) {
                    Log.e("isNetwork", "@");
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        listLat.add(Double.toString(lat));
        listLng.add(Double.toString(lon));
        Log.e("Lat 1st", Double.toString(lat));
        Log.e("Lng 1st", Double.toString(lon));
        Toast.makeText(MainActivity.mContext, "시작" + Double.toString(lat) + "/" + Double.toString(lon), Toast.LENGTH_SHORT).show();
        return location;
    }

    //GPS종료
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GpsInfo.this);
        }
    }

    public double setLatitude() {
        if (location != null) {
            lat = location.getLatitude();
        }
        return lat;
    }

    public double setLongtitude() {
        if (location != null) {
            lon = location.getLongitude();
        }
        return lon;
    }

    public boolean isGetLocation() {
        return isGetLocation;
    }

    public ArrayList<String> getLatList() {
        return listLat;
    }

    public ArrayList<String> getLngList() {
        return listLng;
    }


    @Override
    public void onLocationChanged(Location location) {
        lon = location.getLongitude();
        lat = location.getLatitude();

        listLat.add(Double.toString(lat));
        listLng.add(Double.toString(lon));
        Log.e("Lat", Double.toString(lat));
        Log.e("Lng", Double.toString(lon));
        Toast.makeText(MainActivity.mContext, "위치변화" + Double.toString(lat) + "/" + Double.toString(lon), Toast.LENGTH_SHORT).show();

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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("test", "서비스의 onStratCommand");

        //startForeground(1, new Notification());

        time = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a");
        dt = new Date();

        setNotification();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("test", "서비스의 onDestroy");
        stopUsingGPS();

        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        intent.putStringArrayListExtra("lat", listLat);
        intent.putStringArrayListExtra("lng", listLng);
        intent.putExtra("start", time.format(dt));
        Log.e("@@@gpsDestroy/lat[0]@@@", listLat.get(0));

        makeMapList(listLat, listLng);

        listDBHelper.onSave(time.format(dt), saveLat, saveLng);


        startActivity(intent);
    }

    void setNotification() {
        notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(channelld, channelName, importance);
            notifManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelld);
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        int requestID = (int) System.currentTimeMillis();

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle("Black Out")
                .setContentText("Black Out 실행중 입니다 =)")
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.drunk_1)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.drunk_1))
                .setContentIntent(pendingIntent);

        startForeground(1, builder.build());
    }


    public void makeMapList(ArrayList latList, ArrayList lngList){

        for(int i = 0; i < latList.size(); i++){
            saveLat += latList.get(i) + "/";
        }


        for(int i = 0; i < lngList.size(); i++){
            saveLng += lngList.get(i) + "/";
        }

    }

}