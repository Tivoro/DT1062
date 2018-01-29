package android.tivor.com.uptimerobot;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Tivor on 2017-10-18.
 */

public interface UptimeRequestInterface {
    @FormUrlEncoded
    @POST("/v2/getMonitors")
    Call<UptimeResponse> getMonitors(@Field("api_key") String key, @Field("format") String format);
}
/*
Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.uptimerobot.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UptimeRequestInterface request = retrofit.create(UptimeRequestInterface.class);
        Call<UptimeResponse> call = request.getMonitors(getString(R.string.api_key), "json");
        call.enqueue(new Callback<UptimeResponse>() {
            @Override
            public void onResponse(Call<UptimeResponse> call, retrofit2.Response<UptimeResponse> response) {
                Log.d("db",response.body().getStat());
            }

            @Override
            public void onFailure(Call<UptimeResponse> call, Throwable t) {

            }
        });
 */