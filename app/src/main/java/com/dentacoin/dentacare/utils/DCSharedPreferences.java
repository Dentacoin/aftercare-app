package com.dentacoin.dentacare.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Atanas Chervarov on 8/16/17.
 */

public class DCSharedPreferences {

    public enum DCSharedKey {
        AUTH_TOKEN("AUTH_TOKEN"),
        USER("USER"),
        WELCOME_SCREEN("WELCOME_SCREEN"),
        DEFAULT_WALLET("DEFAULT_WALLET"),
        DASHBOARD("DASHBOARD"),
        GOALS("GOALS"),
        GOALS_REACHED("GOALS_REACHED"),
        SHOWED_TUTORIALS("SHOWED_TUTORIALS"),
        FEMALE_VOICE("FEMALE_VOICE"),
        LAST_MESSAGE_TIME("LAST_MESSAGE_TIME"),
        SOUND_ENABLED("SOUND_ENABLED"),
        MUSIC_ENABLED("MUSIC_ENABLED"),
        //Notification keys
        DAILY_BRUSHING("DAILY_BRUSHING"),
        CHANGE_BRUSH("CHANGE_BRUSH"),
        VISIT_DENTIST("VISIT_DENTIST"),
        COLLECT_DENTACOIN("COLLECT_DENTACOIN"),
        REMINDER_TO_VISIT("REMINDER_TO_VISIT"),
        HEALTHY_HABIT("HEALTHY_HABIT"),
        FIRST_LOGIN_DATE("FIRST_LOGIN_DATE"),
        SEEN_ONBOARDING("SEEN_ONBOARDING"),
        SHOW_EMAIL_VERIFICATION("SHOW_EMAIL_VERIFICATION"),
        ROUTINES("ROUTINES"),
        LAST_ROUTINE_TIME("LAST_ROUTINE_TIME");
        
        private String key;

        DCSharedKey(String key) {
            this.key = key;
        }

        public String getKey() { return key; }
    }

    private static DCSharedPreferences instance;
    private SharedPreferences preferences;
    private int notificationId = 0;

    private static DCSharedPreferences getInstance() {
        if (instance == null)
            instance = new DCSharedPreferences();
        return instance;
    }

    public static int getNotificationId() {
        return ++getInstance().notificationId;
    }

    public static void initialize(Context context) {
        getInstance().preferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static boolean saveBoolean(DCSharedKey key, boolean value) {
        return getInstance().preferences.edit().putBoolean(key.getKey(), value).commit();
    }

    public static boolean getBoolean(DCSharedKey key, boolean defaultValue) {
        return getInstance().preferences.getBoolean(key.getKey(), defaultValue);
    }

    public static boolean saveString(DCSharedKey key, String value) {
        return getInstance().preferences.edit().putString(key.getKey(), value).commit();
    }

    public static String loadString(DCSharedKey key) {
        return getInstance().preferences.getString(key.getKey(), null);
    }

    public static boolean removeKey(DCSharedKey key) {
        return  getInstance().preferences.edit().remove(key.getKey()).commit();
    }

    public static int loadInt(DCSharedKey key, int defaultValue) {
        return getInstance().preferences.getInt(key.getKey(), defaultValue);
    }
    public static int loadInt(DCSharedKey key) {
        return loadInt(key, 0);
    }

    public static boolean saveInt(DCSharedKey key, int value) {
        return getInstance().preferences.edit().putInt(key.getKey(), value).commit();
    }

    public static Date loadDate(DCSharedKey key) {
        long milis = getInstance().preferences.getLong(key.getKey(), -1);
        if (milis > 0) {
            return new Date(milis);
        }
        return null;
    }

    public static boolean saveDate(DCSharedKey key, long milis) {
        return getInstance().preferences.edit().putLong(key.getKey(), milis).commit();
    }

    public static Set<String> getShownTutorials() {
        return getInstance().preferences.getStringSet(DCSharedKey.SHOWED_TUTORIALS.getKey(), new HashSet<String>());
    }

    public static void setShownTutorial(Tutorial tutorial, boolean shown) {
        Set<String> shownTutorials = getShownTutorials();
        HashSet<String> tutorials = new HashSet<>();
        tutorials.addAll(shownTutorials);

        if (shown) {
            tutorials.add(tutorial.name());
        } else {
            tutorials.remove(tutorial.name());
        }

        getInstance().preferences.edit().putStringSet(DCSharedKey.SHOWED_TUTORIALS.getKey(), tutorials).apply();
    }

    /**
     * Cleans all saved user data
     */
    public static void clean() {
        removeKey(DCSharedKey.AUTH_TOKEN);
        removeKey(DCSharedKey.USER);
        removeKey(DCSharedKey.WELCOME_SCREEN);
        removeKey(DCSharedKey.DEFAULT_WALLET);
        removeKey(DCSharedKey.DASHBOARD);
        removeKey(DCSharedKey.ROUTINES);
        removeKey(DCSharedKey.GOALS_REACHED);
        removeKey(DCSharedKey.FEMALE_VOICE);
        removeKey(DCSharedKey.LAST_MESSAGE_TIME);
        removeKey(DCSharedKey.SOUND_ENABLED);
        removeKey(DCSharedKey.MUSIC_ENABLED);
        removeKey(DCSharedKey.FIRST_LOGIN_DATE);
        removeKey(DCSharedKey.DAILY_BRUSHING);
        removeKey(DCSharedKey.CHANGE_BRUSH);
        removeKey(DCSharedKey.VISIT_DENTIST);
        removeKey(DCSharedKey.COLLECT_DENTACOIN);
        removeKey(DCSharedKey.REMINDER_TO_VISIT);
        removeKey(DCSharedKey.HEALTHY_HABIT);
        removeKey(DCSharedKey.FIRST_LOGIN_DATE);
        removeKey(DCSharedKey.SHOW_EMAIL_VERIFICATION);
    }

    /**
     * Partially clean user prefs when switching to child accounts
     */
    public static void partialClean() {
        removeKey(DCSharedKey.AUTH_TOKEN);
        removeKey(DCSharedKey.USER);
        removeKey(DCSharedKey.GOALS_REACHED);
        removeKey(DCSharedKey.ROUTINES);
    }
}
