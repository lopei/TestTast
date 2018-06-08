package com.milestns.testtask;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.milestns.testtask.database.HelperFactory;
import com.milestns.testtask.loader.FileExhibitsLoader;
import com.milestns.testtask.model.ExhibitsLoader;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        HelperFactory.setHelper(getApplicationContext());

        clearAllRecords();

        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HelperFactory.releaseHelper();
    }

    private void loadData() {
        swipeRefreshLayout.setRefreshing(true);
        FileExhibitsLoader.getInstance().getExhibitsLoader()
                .getExhibits()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ExhibitsLoader.ExhibitsListResponse>() {
                    @Override
                    public void accept(ExhibitsLoader.ExhibitsListResponse exhibitsListResponse) throws Exception {
                        swipeRefreshLayout.setRefreshing(false);
                        HelperFactory.getHelper().getExhibitsDao().create(exhibitsListResponse.getList());
                        initList();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        swipeRefreshLayout.setRefreshing(true);
                        throwable.printStackTrace();
                    }
                });
    }

    private void initList() {
        try {
            ExhibitsAdapter adapter = new ExhibitsAdapter(HelperFactory.getHelper().getExhibitsDao().queryForAll());
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearAllRecords() {
        try {
            HelperFactory.getHelper().getExhibitsDao().clearAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
