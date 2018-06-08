package com.milestns.testtask.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alan on 08.06.2018.
 */

@DatabaseTable
public class Exhibit extends BaseDaoObject {
    @DatabaseField
    private String title;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private ArrayList<String> images;

    public String getTitle() {
        return title;
    }

    public List<String> getImages() {
        return images;
    }
}
