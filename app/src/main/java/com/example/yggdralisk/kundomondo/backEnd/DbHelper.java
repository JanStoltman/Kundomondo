package com.example.yggdralisk.kundomondo.backEnd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yggdralisk.kundomondo.entities.Person;
import com.example.yggdralisk.kundomondo.entities.Run;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by yggdralisk on 14.04.16.
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "datas.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Person, Integer> personDao;

    public DbHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Run.class);

        } catch (SQLException e) {
            Log.e(DbHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
            try {
                TableUtils.dropTable(connectionSource, Person.class, true);
                onCreate(database, connectionSource);
            } catch (SQLException e) {
                Log.e(DbHelper.class.getName(), "Unable to upgrade database from version " + oldVersion + " to new "
                        + newVersion, e);
            }
    }

    public Dao<Person, Integer> getPersonDao() throws SQLException {
        if (personDao == null) {
            personDao = getDao(Person.class);
        }
        return personDao;
    }
}
