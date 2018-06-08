package com.milestns.testtask.database;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.milestns.testtask.model.Exhibit;

import java.sql.SQLException;

/**
 * Created by alan on 20.10.17.
 */

public class ExhibitsDao extends BaseDaoImpl<Exhibit, Integer> {

    protected ExhibitsDao(ConnectionSource connectionSource, Class<Exhibit> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public void clearAll() {
        try {
            TableUtils.clearTable(getConnectionSource(), Exhibit.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}