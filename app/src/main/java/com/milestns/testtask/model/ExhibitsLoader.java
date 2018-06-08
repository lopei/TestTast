package com.milestns.testtask.model;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by alan on 08.06.2018.
 */

public interface ExhibitsLoader {

    @GET("/u-android/41ade05b6ae1133e7e86e9dfd55f1794/raw/bab1c383b0384d1a4c889b399ad7b13ae05634fa/ios%20challenge%20json")
    Observable<ExhibitsListResponse> getExhibits();

    class ExhibitsListResponse {
        private List<Exhibit> list;

        public List<Exhibit> getList() {
            return list;
        }
    }

}
