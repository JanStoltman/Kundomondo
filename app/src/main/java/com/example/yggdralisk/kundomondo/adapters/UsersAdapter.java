package com.example.yggdralisk.kundomondo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yggdralisk.kundomondo.R;
import com.example.yggdralisk.kundomondo.entities.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yggdralisk on 15.04.16.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    List<Person> personList;

    public UsersAdapter(List<Person> personList)
    {
        if(personList!=null)
            this.personList = personList;
        else
            this.personList = new ArrayList<>();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_element, parent, false);
        UserViewHolder vh = new UserViewHolder(v);
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

        public void setData(Person person)
        {
            userNick.setText(person.getNick());
            userBurned.setText(Double.toString(person.getTotalBurnedCalories()));
            userRan.setText(Double.toString(person.getTotalDistance()));
        }

        public UserViewHolder(View itemView) {
            super(itemView);
            userNick = (TextView)itemView.findViewById(R.id.holder_user_nick);
            userBurned = (TextView)itemView.findViewById(R.id.holder_total_burned);
            userRan = (TextView)itemView.findViewById(R.id.holder_total_ran);
        }
    }
}
