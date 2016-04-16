package com.example.yggdralisk.kundomondo.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yggdralisk.kundomondo.MapActivity;
import com.example.yggdralisk.kundomondo.UsersListActivity;
import com.example.yggdralisk.kundomondo.R;
import com.example.yggdralisk.kundomondo.backEnd.DataGetter;
import com.example.yggdralisk.kundomondo.entities.Person;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yggdralisk on 15.04.16.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    List<Person> personList;
    UsersListActivity upActivity;
    DataGetter dataGetter;

    public UsersAdapter(List<Person> personList, UsersListActivity upActivity, DataGetter dataGetter) {
        if (personList != null)
            this.personList = personList;
        else
            this.personList = new ArrayList<>();

        this.upActivity = upActivity;
        this.dataGetter = dataGetter;
    }

    public void addItems(List<Person> personList) {
        this.personList = personList;
        //this.notifyItemInserted(this.personList.size()-1);
        this.notifyDataSetChanged();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_element, parent, false);
        final UserViewHolder vh = new UserViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(upActivity.getApplicationContext(), MapActivity.class);
                intent.putExtra("UserNick", vh.userNick.getText().toString());
                upActivity.startActivity(intent);
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.setData(personList.get(position));
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userNick;
        TextView userBurned;
        TextView userRan;
        Button removeButton;

        public void setData(final Person person) {
            userNick.setText(person.getNick());
            userBurned.setText(Double.toString(person.getTotalBurnedCalories()));
            userRan.setText(Double.toString(person.getTotalDistance()));

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        dataGetter.removeUser(person.getNick());
                        addItems(dataGetter.getUsers());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public UserViewHolder(View itemView) {
            super(itemView);
            userNick = (TextView) itemView.findViewById(R.id.holder_user_nick);
            userBurned = (TextView) itemView.findViewById(R.id.holder_total_burned);
            userRan = (TextView) itemView.findViewById(R.id.holder_total_ran);
            removeButton = (Button) itemView.findViewById(R.id.remove_user_button);
        }
    }
}
