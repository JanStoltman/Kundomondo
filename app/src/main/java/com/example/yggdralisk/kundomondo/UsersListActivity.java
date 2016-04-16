package com.example.yggdralisk.kundomondo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.yggdralisk.kundomondo.adapters.UsersAdapter;
import com.example.yggdralisk.kundomondo.backEnd.DataGetter;
import com.example.yggdralisk.kundomondo.entities.Person;

import java.sql.SQLException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yggdralisk on 14.04.16.
 */
public class UsersListActivity extends AppCompatActivity {
    @Bind(R.id.user_recycler)
    RecyclerView recyclerView;
    DataGetter dataGetter;
    PopupWindow popupWindow;
    public UsersAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        try {
            dataGetter = new DataGetter(getApplicationContext());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setRecycler();

    }

    public void setRecycler() {
        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            usersAdapter = new UsersAdapter(dataGetter.getUsers(), this, dataGetter);
            recyclerView.setAdapter(usersAdapter);
        } catch (SQLException e) {
            recyclerView.setAdapter(new UsersAdapter(new ArrayList<Person>(), this, dataGetter));
            e.printStackTrace();
        }
    }

    @OnClick(R.id.add_button)
    public void onMainAddPress(Button button) {
        try {
            LayoutInflater inflater = (LayoutInflater) UsersListActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.pop_up_element,
                    (ViewGroup) findViewById(R.id.pop_up));

            popupWindow = new PopupWindow(layout, 450, 400, true);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

            Button add = ButterKnife.findById(layout, R.id.pop_up_add_button);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addPerson(layout);
                }
            });

            Button cancel = ButterKnife.findById(layout, R.id.pop_up_cancel_button);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addPerson(View layout) {
        String nick = "";
        double weight = -1;
        int height = -1;
        try {
            nick = ((EditText) ButterKnife.findById(layout, R.id.pop_up_nick)).getText().toString();
            weight = Double.parseDouble(((EditText) ButterKnife.findById(layout, R.id.pop_up_weight)).getText().toString().trim());
            height = Integer.parseInt(((EditText) ButterKnife.findById(layout, R.id.pop_up_height)).getText().toString().trim());
        } catch (NumberFormatException ex) {
            Toast.makeText(getApplicationContext(), "Wrong data format", Toast.LENGTH_LONG);
        }

        if (nick != "" && weight != -1 && height != -1) {
            popupWindow.dismiss();
            try {
                dataGetter.putPerson(new Person(0, nick, weight, height, 0));
                usersAdapter.addItems(dataGetter.getUsers());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
