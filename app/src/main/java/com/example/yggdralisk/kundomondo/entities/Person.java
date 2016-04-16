package com.example.yggdralisk.kundomondo.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by yggdralisk on 14.04.16.
 */
@DatabaseTable(tableName = "People")
public class Person {
    @DatabaseField(columnName = "totalDistance")
    double totalDistance;
    @DatabaseField(columnName = "nick",id = true)
    String nick;
    @DatabaseField(columnName = "weight")
    double weight;
    @DatabaseField(columnName = "height")
    int height;
    @DatabaseField(columnName = "totalBurnedCalories")
    double totalBurnedCalories;
    @ForeignCollectionField(eager = true,columnName = "personRuns")
    public Collection<Run> runs;

    public double buredCalories(double distance)
    {
        totalBurnedCalories += distance*(0.75* weight *2.20462262);
        return distance*(0.75* weight *2.20462262);
    }

    public Person()
    {

    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public String getNick() {
        return nick;
    }

    public double getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public double getTotalBurnedCalories() {
        return totalBurnedCalories;
    }

    public void addRun(Run run)
    {
        runs.add(run);
    }

    public Person(double totalDistance, String nick, double weight, int height, double totalBurnedCalories, ArrayList<Run> runs) {
        this.totalDistance = totalDistance;
        this.nick = nick;
        this.weight = weight;
        this.height = height;
        this.totalBurnedCalories = totalBurnedCalories;
        this.runs = runs;
    }

    public Person(double totalDistance, String nick, double weight, int height, double totalBurnedCalories) {
        this.totalDistance = totalDistance;
        this.nick = nick;
        this.weight = weight;
        this.height = height;
        this.totalBurnedCalories = totalBurnedCalories;
        runs = new ArrayList<>();
    }
}
