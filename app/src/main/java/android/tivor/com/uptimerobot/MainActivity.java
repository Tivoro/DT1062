package android.tivor.com.uptimerobot;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, service_monitorRequests.class);
        intent.putExtra("command", "start");
        getApplicationContext().startService(intent);

        welcome = (TextView)findViewById(R.id.welcomeMessage);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawer = (DrawerLayout)findViewById(R.id.drawerLayout);
        drawerToggle = setupDrawerToggle();

        navDrawer = (NavigationView)findViewById(R.id.navView);
        setupDrawerContent(navDrawer);
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                });
    }
    private ActionBarDrawerToggle setupDrawerToggle(){
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }
    private void selectDrawerItem(MenuItem item){
        if (!welcome.getText().equals(""))
            welcome.setText("");

        Fragment frag = null;
        Class fragClass;
        switch (item.getItemId()){
            case R.id.nav_viewMonitors:
                fragClass = fragment_viewMonitors.class;
                break;
            case R.id.nav_settings:
                fragClass = fragment_viewSettings.class;
                break;
            case R.id.nav_about:
                fragClass = fragment_viewAbout.class;
                break;
            default:
                fragClass = fragment_viewMonitors.class;
        }

        try{
            frag = (Fragment)fragClass.newInstance();
        } catch (Exception err){
            err.printStackTrace();
        }

        FragmentManager fragManager = getSupportFragmentManager();
        fragManager.beginTransaction().replace(R.id.flContent, frag).commit();
        item.setChecked(true);
        setTitle(item.getTitle());
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
