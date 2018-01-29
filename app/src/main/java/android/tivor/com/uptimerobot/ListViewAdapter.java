package android.tivor.com.uptimerobot;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tivor on 2017-10-19.
 */

public class ListViewAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<RowItem> itemList;

    public ListViewAdapter(Context context, ArrayList<RowItem> items){
        mContext = context;
        itemList = items;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        RowItemExpanded expanded = itemList.get(groupPosition).getDetails();
        return  expanded;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        RowItemExpanded details = (RowItemExpanded)getChild(groupPosition, childPosition);
        RowItem header = (RowItem)getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_expanded, null);
        }

        TextView type = (TextView)convertView.findViewById(R.id.type);
        TextView ID = (TextView)convertView.findViewById(R.id.id);

        type.setText(details.getType());
        ID.setText(details.getID());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return itemList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return itemList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        RowItem header = (RowItem)getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }
        TextView description = (TextView)convertView.findViewById(R.id.description);
        TextView title = (TextView)convertView.findViewById(R.id.title);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.icon);
        LinearLayout status = (LinearLayout)convertView.findViewById(R.id.status);

        description.setText(header.getDesc());
        title.setText(header.getTitle());
        imageView.setImageResource(R.drawable.ic_expand);
        status.setBackgroundColor(header.getBackground());

        return  convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
