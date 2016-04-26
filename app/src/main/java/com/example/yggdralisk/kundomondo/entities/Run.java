package com.example.yggdralisk.kundomondo.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yggdralisk on 14.04.16.
 */
@DatabaseTable
public class Run {
    @DatabaseField(columnName = "runId",generatedId = true)
    int id;
    @DatabaseField(columnName = "distance")
    double distance;
    @DatabaseField(columnName = "caloriesBurned")
    double caloriesBurned;
    @DatabaseField(columnName = "time")
    String time;
    @DatabaseField(foreign = true)
    protected Person owner;

    public Run()
    {

    }

    public Run(double distance, double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public double getDistance() {
        return distance;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public String getTime(){return time;}
}
