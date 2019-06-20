package kr.hs.dgsw.hatomuapp.setting;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import kr.hs.dgsw.hatomuapp.R;

//Theme 적용
public class HatomuTheme {

    public static final int THEME_DARK_MODE = 1;
    public static final int THEME_WHITE_MODE = 2;

    public static final String KEY_THEME_PREFERENCE = "HatomuTheme";

    public static void applyTheme(AppCompatActivity activity) {
        SharedPreferences prefs = activity.getSharedPreferences("Hatomu", Context.MODE_PRIVATE);
        int mode = prefs.getInt(KEY_THEME_PREFERENCE, -1);
        if (mode == -1) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(KEY_THEME_PREFERENCE, THEME_WHITE_MODE);
            editor.commit();
            activity.setTheme(R.style.AppTheme_White);
        } else if (mode == THEME_WHITE_MODE) {
            activity.setTheme(R.style.AppTheme_White);
        } else {
            activity.setTheme(R.style.AppTheme_Dark);
        }
    }

    public static void toggleTheme(ContextWrapper wrapper) {
        SharedPreferences prefs = wrapper.getSharedPreferences("Hatomu", Context.MODE_PRIVATE);
        int mode = prefs.getInt(KEY_THEME_PREFERENCE, -1);
        SharedPreferences.Editor editor = prefs.edit();
        if (mode == -1) {
            editor.putInt(KEY_THEME_PREFERENCE, THEME_DARK_MODE);
        } else if (mode == THEME_WHITE_MODE) {
            editor.putInt(KEY_THEME_PREFERENCE, THEME_DARK_MODE);
        } else {
            editor.putInt(KEY_THEME_PREFERENCE, THEME_WHITE_MODE);
        }
        editor.commit();
    }

    public static void saveThemeMode(ContextWrapper wrapper, final int mode) {
        SharedPreferences prefs = wrapper.getSharedPreferences("Hatomu", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (mode == THEME_WHITE_MODE || mode == THEME_DARK_MODE) {
            editor.putInt(KEY_THEME_PREFERENCE, mode);
        } else {
            editor.putInt(KEY_THEME_PREFERENCE, THEME_WHITE_MODE);
        }
        editor.commit();
    }
}
