package com.example.yggdralisk.kundomondo;

import android.content.Context;
import android.location.Location;
import android.os.PowerManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.yggdralisk.kundomondo.adapters.RunsAdapter;
import com.example.yggdralisk.kundomondo.backEnd.DataGetter;
import com.example.yggdralisk.kundomondo.entities.Person;
import com.example.yggdralisk.kundomondo.entities.Run;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MapActivity extends AppCompatActivity {
    DataGetter dataGetter;
    Person person;
    @Bind(R.id.main_meter)
    TextView mainMeter;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.left_drawer_recycler)
    RecyclerView recyclerView;
    GoogleMap googleMap;

    Double km = 0.0;
    Double kcl = 0.0;

    LatLng oldPosition;
    LatLng newPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        try {
            dataGetter = new DataGetter(getApplicationContext());
            person = dataGetter.getUserByNick(getIntent().getStringExtra("UserNick"));
            recyclerView.setAdapter(new RunsAdapter(dataGetter.getRunsByNick(person.getNick()), this, dataGetter));
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mainMeter.setText(String.format(Locale.ROOT, "Km %f\nCal %f", km, kcl));

        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.main_map)).getMap();
        }

        if(googleMap != null) {
            googleMap.setMyLocationEnabled(true);

            googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location arg0) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arg0.getLatitude(), arg0.getLongitude()), 17));

                    MarkerOptions marker = new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title(person.getNick());
                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.papu_head));
                    googleMap.addMarker(marker);

                    if (oldPosition == null)
                        oldPosition = new LatLng(arg0.getLatitude(), arg0.getLongitude());

                    newPosition = new LatLng(arg0.getLatitude(), arg0.getLongitude());

                    int tempDist = (int) CalcDistance(oldPosition, newPosition);
                    km += tempDist/1000;

                    kcl += (int)person.buredCalories(tempDist);

                    mainMeter.setText(String.format(Locale.ROOT, "Km %.3f\nCal %.0f", km, kcl));
                    oldPosition = newPosition;
                }
            });
        }

        PowerManager mgr = (PowerManager)getApplicationContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyWakeLock");
        wakeLock.acquire();
    }

    public double CalcDistance(LatLng oldPosition, LatLng newPosition)
    {
        float[] results = new float[1];
        Location.distanceBetween(oldPosition.latitude, oldPosition.longitude,
                newPosition.latitude, newPosition.longitude, results);

        return results[0];
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return valueResult;
    }


    @Override
    public void onBackPressed() {
        try {
            if(km>0 && kcl > 0)
            dataGetter.addPersonRun(person,new Run(km, kcl));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        try {
            if(km>0 && kcl > 0)
            dataGetter.addPersonRun(person,new Run(km, kcl));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onStop();
    }
}