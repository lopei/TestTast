package com.milestns.testtask.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by alan on 08.06.2018.
 */

public class BaseDaoObject {
    @DatabaseField(generatedId = true)
    private int id;
}
