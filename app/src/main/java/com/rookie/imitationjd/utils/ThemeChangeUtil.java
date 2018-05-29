package com.rookie.imitationjd.utils;

import android.app.Activity;

import com.rookie.imitationjd.R;

/**
 * Created by 暗夜 on 2018/5/28.
 */

public class ThemeChangeUtil {
    public static boolean isChange = false;
    public static void changeTheme(Activity activity){
        if(isChange){
            activity.setTheme(R.style.NightTheme);
        }
    }

}
