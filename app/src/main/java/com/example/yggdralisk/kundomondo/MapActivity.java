package com.example.yggdralisk.kundomondo;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yggdralisk.kundomondo.adapters.RunsAdapter;
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
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.left_drawer_recycler)
    RecyclerView recyclerView;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        try {
            dataGetter = new DataGetter(getApplicationContext());
            person = dataGetter.getUserByNick(getIntent().getStringExtra("UserNick"));
            recyclerView.setAdapter(new RunsAdapter(dataGetter.getRunsByNick(person.getNick()),this,dataGetter));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mainMeter.setText("0 km \n 0 kcl");
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.main_map)).getMap();
    }
}
