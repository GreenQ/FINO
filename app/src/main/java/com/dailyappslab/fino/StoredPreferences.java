package com.dailyappslab.fino;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by GreenQ on 23.03.2015.
 */
public class StoredPreferences extends Activity {
    SharedPreferences preferences;
    SharedPreferences preferencesOpenedLetters;
    SharedPreferences preferencesGold;
    SharedPreferences preferencesLastVisit;
    SharedPreferences preferencesSeqVisitAm;
    String Gold = "GOLD";
    String Lvl = "LVL";
    String LastVisitDate = "LAST_VISIT_DATE";
    String SequencialVisitsAmount = "SEQUENCIAL_VISIT_AMOUNT";
    long DefaultLastVisitDate;
    int DefaultLevel;
    int DefaultGold;
    int DefaultSeqVisAm;

    StoredPreferences(Context context, int DefaultLevel, int DefaultGold)
    {
        preferences = context.getSharedPreferences(Lvl, Context.MODE_PRIVATE);
        preferencesGold = context.getSharedPreferences(Gold, Context.MODE_PRIVATE);
        preferencesLastVisit = context.getSharedPreferences(LastVisitDate, Context.MODE_PRIVATE);
        preferencesSeqVisitAm = context.getSharedPreferences(SequencialVisitsAmount, Context.MODE_PRIVATE);
        this.DefaultLevel = DefaultLevel;
        this.DefaultGold = DefaultGold;
    }

    StoredPreferences(Context context, String LastVisit)
    {

    }

    private boolean CheckGoldRange(int i)
    {
        if(i >= 0 && i <= 99999)
            return true;
        else
            return false;
    }
    private boolean CheckLevelRange(int i)
    {
        if(i > 0 && i <9)
            return true;
        else
            return false;
    }

    /*public String[] GetOpenedLettersForLevel(int lvl)
    {
        String[] openedLetters = preferencesOpenedLetters.getStringSet(preferencesOpenedLetters, openedLetters)

    }*/

    public int GetSequencialVisitsAmount()
    {
        int temp = preferences.getInt(SequencialVisitsAmount, 0);
        return temp;
    }

    public long GetLastVisitDate()
    {
        long temp = preferences.getLong(LastVisitDate, System.currentTimeMillis());
        return temp;
    }

    public int GetGoldAmount()
    {
        int temp = preferences.getInt(Gold, 200);

        if (CheckGoldRange(temp))
            return temp;
        else
            return DefaultGold;
    }

    public int GetCurrentLevel()
    {
        int temp = preferences.getInt(Lvl, 1) ;

        if (CheckLevelRange(temp))
            return temp;
        else
            return DefaultLevel;
        //return preferences.getInt(curLevel, 1);
    }

    private void EditOpenedLettersForLevel(int lvl)
    {
        SharedPreferences.Editor editor = preferencesOpenedLetters.edit();

    }

    public void EditSeqvencialVisitAmount(int seqVisAm)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SequencialVisitsAmount, seqVisAm);
        editor.commit();
        DefaultSeqVisAm = seqVisAm;
    }

    public void EditLastVisitDate(long lastVisitDate)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(LastVisitDate, lastVisitDate);
        editor.commit();
        DefaultLastVisitDate = lastVisitDate;
    }

    public void EditGoldAmount(int i)
    {
        if(!CheckGoldRange(i))
            return;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Gold, i);
        editor.commit();
        DefaultGold = i;
    }

    public void EditLevel(int i)
    {
        if (!CheckLevelRange(i))
            return;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Lvl, i);
        editor.commit();
        DefaultLevel = i;
    }
}
