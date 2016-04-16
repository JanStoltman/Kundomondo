package com.example.yggdralisk.kundomondo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.yggdralisk.kundomondo.adapters.UsersAdapter;
import com.example.yggdralisk.kundomondo.backEnd.DataGetter;
import com.example.yggdralisk.kundomondo.entities.Person;
import com.example.yggdralisk.kundomondo.entities.Run;
import com.j256.ormlite.dao.EagerForeignCollection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yggdralisk on 14.04.16.
 */
public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.user_recycler)
    RecyclerView recyclerView;
    DataGetter dataGetter;

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

    private void setRecycler() {
        try {
            recyclerView.setAdapter(new UsersAdapter(dataGetter.getUsers()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.add_button)
    public void sayHi(Button button) {
        try {
            dataGetter.putPerson(new Person(122,"dupa",20,2533,566));
            setRecycler();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
