package android.tivor.com.uptimerobot;

import android.graphics.drawable.GradientDrawable;

import java.util.ArrayList;

/**
 * Created by Tivor on 2017-10-19.
 */

public class RowItem {
    private int mImage;
    private String mTitle;
    private String mDesc;
    private int mBackground;

    private RowItemExpanded mExpandedData;

    public RowItem(int image, String title, String desc, int background, RowItemExpanded expandedData){
        mImage = image;
        mTitle = title;
        mDesc = desc;
        mBackground = background;
        mExpandedData = expandedData;
    }

    public int getImage(){ return mImage; }
    public void setImage(int image){ mImage = image; }

    public String getTitle(){ return mTitle; }
    public void setTitle(String title){ mTitle = title; }

    public String getDesc(){ return mDesc; }
    public void setDesc(String desc){ mDesc = desc; }

    public int getBackground(){ return mBackground; }
    public void setBackground(int background){ mBackground = background; }

    public RowItemExpanded getDetails(){ return mExpandedData; }
    public void setDetails(RowItemExpanded expanded){ mExpandedData = expanded; }
}
