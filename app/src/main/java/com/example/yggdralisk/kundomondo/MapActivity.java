package com.example.yggdralisk.kundomondo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.yggdralisk.kundomondo.backEnd.DataGetter;
import com.example.yggdralisk.kundomondo.entities.Person;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import java.sql.SQLException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MapActivity extends AppCompatActivity {
    DataGetter dataGetter;
    Person person;
    @Bind(R.id.main_meter)
    TextView mainMeter;

    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        mainMeter.setText("0 km \n 0 kcl");
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.main_map)).getMap();
        try {
            dataGetter = new DataGetter(getApplicationContext());
            person = dataGetter.getUserByNick(getIntent().getStringExtra("UserNick"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
