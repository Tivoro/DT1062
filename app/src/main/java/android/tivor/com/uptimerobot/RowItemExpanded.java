package android.tivor.com.uptimerobot;

/**
 * Created by Tivor on 2017-10-20.
 */

public class RowItemExpanded {
    private String mRatioUp, mRatioPaused, mRatioDown;
    private String mID;
    private String mType;

    public RowItemExpanded(String ratio, String ID, String type){
        mID = ID;
        mType = type;
    }

    public  String getID(){ return mID; }
    public void setID(String ID){ mID = ID; }

    public  String getType(){
        if(mType.equals("1"))
            return "HTTP(s)";
        else if(mType.equals("2"))
            return "Keyword";
        else if(mType.equals("3"))
            return "Ping";
        else
            return "Port";
    }
    public void setType(String type){ mType = type; }
}
