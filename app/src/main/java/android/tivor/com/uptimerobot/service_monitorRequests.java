package android.tivor.com.uptimerobot;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tivor on 2017-10-18.
 * To allow the program to send API requests while the user has the app in the background/is running.
 */

public class service_monitorRequests extends Service {
    public UptimeResponse mResponse;
    private boolean running = false;

    private MyBinder binder = new MyBinder();

    private Handler handler = new Handler();
    private Runnable requestAPI = new Runnable() {
        @Override
        public synchronized void run() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String API_KEY = prefs.getString("API_KEY", "u411613-48e0676a472daaf5e1cbadb1");
            int updateInterval = Integer.parseInt(prefs.getString("updateInterval", "5"))*1000;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.uptimerobot.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            UptimeRequestInterface request = retrofit.create(UptimeRequestInterface.class);
            Call<UptimeResponse> call = request.getMonitors(API_KEY, "json");
            call.enqueue(new Callback<UptimeResponse>() {
                @Override
                public void onResponse(Call<UptimeResponse> call, retrofit2.Response<UptimeResponse> response) {
                    //Log.d("db","API response status: " + response.body().getStat());
                    mResponse = response.body();
                }

                @Override
                public void onFailure(Call<UptimeResponse> call, Throwable t) {
                    Log.d("db", "No internet?");
                    mResponse = new UptimeResponse();
                    mResponse.setStat("RequestFaliure");
                }
            });

            // For constant updates fragment_viewMonitors
            Intent broadcastIntent = new Intent();
            broadcastIntent.putExtra("response", (Serializable)mResponse);
            broadcastIntent.setAction(fragment_viewMonitors.mBroadcastResponse);
            sendBroadcast(broadcastIntent);

            handler.postDelayed(requestAPI, updateInterval);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String command = intent.getStringExtra("command");


        if(command.equals("start") && !running) {
            running = true;
            requestAPI.run();
        }
        else if (command.equals("stop") && running) {
            running = false;
            handler.removeCallbacks(requestAPI);
        }

        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    public class MyBinder extends Binder{
        public UptimeResponse getResponse(){
            return mResponse;
        }
    }
}
