package com.milestns.testtask.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.milestns.testtask.model.Exhibit;

import java.sql.SQLException;

/**
 * Created by alan on 8/3/16.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "bodyo.db";

    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private ExhibitsDao exhibitsDao = null;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Exhibit.class);
        } catch (SQLException e) {
            Log.e(TAG, "error creating DB " + DB_NAME);
            //throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer, int newVer) {

    }

    public ExhibitsDao getExhibitsDao() throws SQLException {
        if (exhibitsDao == null) {
            exhibitsDao = new ExhibitsDao(getConnectionSource(), Exhibit.class);
        }
        return exhibitsDao;
    }

    @Override
    public void close() {
        super.close();
        exhibitsDao = null;
    }
}