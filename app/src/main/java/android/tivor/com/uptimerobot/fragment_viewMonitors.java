package android.tivor.com.uptimerobot;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tivor on 2017-10-18.
 */

public class fragment_viewMonitors extends Fragment implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener{
    public static final String mBroadcastResponse = "android.tivor.com.uptimerobot.response";
    private SharedPreferences prefs;
    private UptimeResponse mResponse;
    private IntentFilter mIntentFilter;
    private ExpandableListView mMonitorList;
    private ArrayList<RowItem> rowItems;
    private ListViewAdapter listViewAdapter;
    boolean[] expandedGroups;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(mBroadcastResponse);

        if(savedInstanceState != null) {
            expandedGroups = savedInstanceState.getBooleanArray("expanded_groups");
            Log.d("db", Arrays.toString(expandedGroups));
        }

        ((TextView)getActivity().findViewById(R.id.welcomeMessage)).setText("");

        return inflater.inflate(R.layout.fragment_viewmonitors, container, false);
    }

    private void fillListView(){
        int groupCount = 0;
        if(listViewAdapter != null) {
            groupCount = listViewAdapter.getGroupCount();
            expandedGroups = new boolean[groupCount];
            for (int i = 0; i < groupCount; i++)
                expandedGroups[i] = mMonitorList.isGroupExpanded(i);
        }

        mMonitorList = (ExpandableListView)getActivity().findViewById(R.id.list_monitors);
        rowItems = new ArrayList<RowItem>();

        List<UptimeResponse.Monitor> monitors = mResponse.getMonitors();
        for(int i = 0; i < monitors.size(); i++){
            int color;
            if(monitors.get(i).getStatus() == 2)
                if (prefs.getString("colorUp", "0").equals("0"))
                    color = ContextCompat.getColor(getContext(), R.color.colorUp);
                else
                    color = Color.parseColor("#"+prefs.getString("colorUp", "0"));
            else
                if (prefs.getString("colorDown", "0").equals("0"))
                    color = ContextCompat.getColor(getContext(), R.color.colorDown);
                else
                    color = Color.parseColor("#"+prefs.getString("colorDown", "0"));

            RowItem item = new RowItem(R.drawable.ic_expand, monitors.get(i).getFriendlyName(), monitors.get(i).getUrl(), color,
                    new RowItemExpanded("", monitors.get(i).getId().toString(), monitors.get(i).getType().toString()));
            rowItems.add(item);
        }

        listViewAdapter = new ListViewAdapter(getContext(), rowItems);
        mMonitorList.setAdapter(listViewAdapter);
        mMonitorList.setOnChildClickListener(this);
        mMonitorList.setOnGroupClickListener(this);

        if(expandedGroups != null)
            for(int i = 0; i < expandedGroups.length; i++)
                if(expandedGroups[i])
                    mMonitorList.expandGroup(i);
    }

    private BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(mBroadcastResponse)){
                mResponse = (UptimeResponse)intent.getSerializableExtra("response");

                if(mResponse != null)
                    if(mResponse.getStat().equals("ok"))
                        fillListView();
                    else
                        Toast.makeText(getContext(), "HTTP request faliure\nMaybe might be an invalid API key", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("db", "Service bound");
            mResponse = ((service_monitorRequests.MyBinder) service).getResponse();

            if(mResponse != null)
                if(mResponse.getStat().equals("ok"))
                    fillListView();
                else
                    Toast.makeText(getContext(), "HTTP request faliure", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "Requesting API", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("db", "Service unbound");
        }
    };

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(mReciever);
        getActivity().unbindService(mConnection);
        Log.d("db", "Service unbound");

        super.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();

        getActivity().bindService(new Intent(getContext(), service_monitorRequests.class), mConnection, Context.BIND_AUTO_CREATE);
        getActivity().registerReceiver(mReciever, mIntentFilter);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(listViewAdapter != null) {
            int groupCount = listViewAdapter.getGroupCount();
            expandedGroups = new boolean[groupCount];
            for (int i = 0; i < groupCount; i++)
                expandedGroups[i] = mMonitorList.isGroupExpanded(i);
        }

        outState.putBooleanArray("expanded_groups", expandedGroups);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }
    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return false;
    }
}
