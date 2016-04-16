package com.example.yggdralisk.kundomondo.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yggdralisk on 14.04.16.
 */
@DatabaseTable(tableName = "runs")
public class Run {
    @DatabaseField(id = true,columnName = "runId")
    int id;
    @DatabaseField(columnName = "distance")
    double distance;
    @DatabaseField(columnName = "caloriesBurned")
    double caloriesBurned;

    public Run()
    {

    }

    public Run(double caloriesBurned, double distance) {
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
}
