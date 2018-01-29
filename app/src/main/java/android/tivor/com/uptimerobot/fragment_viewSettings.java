package android.tivor.com.uptimerobot;

import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Tivor on 2017-10-19.
 */

public class fragment_viewSettings extends PreferenceFragmentCompat {

    private EditTextPreference colorUp, colorDown, delay;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_viewsettings, rootKey);

        colorUp = (EditTextPreference)findPreference("colorUp");
        colorDown = (EditTextPreference)findPreference("colorDown");
        delay = (EditTextPreference)findPreference("updateInterval");

        delay.setOnPreferenceChangeListener(preferenceFormattingListner);
        colorUp.setOnPreferenceChangeListener(preferenceFormattingListner);
        colorDown.setOnPreferenceChangeListener(preferenceFormattingListner);
    }

    private boolean numberCheck(Object newValue){
        if(!newValue.toString().equals("") && newValue.toString().matches("\\d*"))
            return true;
        else
            return false;
    }
    private boolean hexCheck(Object newValue){
        if(!newValue.toString().equals("") && newValue.toString().matches("[A-Fa-f0-9]{6}"))
            return true;
        else
            return false;
    }

    Preference.OnPreferenceChangeListener preferenceFormattingListner = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if(preference.getKey().equals("colorUp"))
                return hexCheck(newValue);
            else if(preference.getKey().equals("colorDown"))
                return hexCheck(newValue);
            else if(preference.getKey().equals("updateInterval"))
                return numberCheck(newValue);
            else
                return true;
        }
    };
}
