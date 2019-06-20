package kr.hs.dgsw.hatomuapp.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceControl {
    private static HatomuService hatomuService;

    public static HatomuService getHatomuService() {
        if (hatomuService != null)
            return hatomuService;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HatomuService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        hatomuService = retrofit.create(HatomuService.class);
        return hatomuService;
    }
}
