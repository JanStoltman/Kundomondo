package com.example.yggdralisk.kundomondo.backEnd;

import android.content.Context;

import com.example.yggdralisk.kundomondo.entities.Person;
import com.example.yggdralisk.kundomondo.entities.Run;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yggdralisk on 14.04.16.
 */

public class DataGetter {

    private Dao<Person, Integer> peopleDao;
    private static DbHelper dbHelper;

    public DataGetter(Context context) throws SQLException {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(context, DbHelper.class);
        }

        peopleDao =  dbHelper.getPersonDao();
    }
    public List<Person> getUsers() throws SQLException {
       return peopleDao.queryForAll();
    }

    public Person getUserById(int userId) throws SQLException {
        QueryBuilder<Person,Integer> builder = peopleDao.queryBuilder();
        builder.where().eq("personId",userId);

        return builder.query().get(0);
    }

    public Person getUserByNick(String nick) throws SQLException {
        QueryBuilder<Person,Integer> builder = peopleDao.queryBuilder();
        builder.where().eq("nick", nick);

        return builder.query().get(0);
    }

    public void putPerson(Person person) throws SQLException {
        peopleDao.createOrUpdate(person);
    }

    public Person addPersonRun(Person person, Run run) throws SQLException {
        QueryBuilder<Person,Integer> builder = peopleDao.queryBuilder();
        builder.where().eq("nick", person.getNick());
        Person temp = builder.queryForFirst();
        temp.addRun(run);
        peopleDao.createOrUpdate(temp);
        return person;
    }
}
