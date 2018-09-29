package com.xuancao.programframes.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Set;


public class SharePreferUtils {

    private static final SharePreferUtils mSPHelper = new SharePreferUtils();

    public static final String SIGN_KEY = "sign";

    private static Context context;
    private SharedPreferences mSP;
    private SharedPreferences.Editor mEditor;
    private boolean hasFirstVersion;

    public static void initialize(Context ctx) {
        context = ctx;
    }

    public static SharePreferUtils getInstance() {
        return mSPHelper;
    }

    public void init() {
        open();
    }

    public void init(Context ctx) {
        context = ctx;
        open(context);
    }

    private void open(Context context) {
        if (mSP == null) {
            mSP = PreferenceManager.getDefaultSharedPreferences(context);
            mEditor = mSP.edit();
            hasFirstVersion = mSP.getAll().size() == 0;
        }
    }

    private void open() {
        if (mSP == null) {
            mSP = PreferenceManager.getDefaultSharedPreferences(context);
            mEditor = mSP.edit();
            hasFirstVersion = mSP.getAll().size() == 0;
        }
    }

    public synchronized String getString(String key, String defValue) {
        open();
        return mSP.getString(key, defValue);
    }

    public synchronized void setString(String key, String value) {
        open();
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public synchronized int getInt(String key, int defValue) {
        open();
        return mSP.getInt(key, defValue);
    }

    public synchronized void setInt(String key, int value) {
        open();
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public synchronized void remove(String key) {
        open();
        mEditor.remove(key);
        mEditor.commit();
    }

    public synchronized long getLong(String key, long defValue) {
        open();
        return mSP.getLong(key, defValue);
    }

    public synchronized void setLong(String key, long value) {
        open();
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public synchronized float getFloat(String key, float defValue) {
        open();
        return mSP.getFloat(key, defValue);
    }

    public synchronized void seFloat(String key, float value) {
        open();
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    public synchronized boolean getBoolean(String key, boolean defValue) {
        open();
        if (!mSP.contains(key)) {
            setBoolean(key, defValue);
        }
        return mSP.getBoolean(key, defValue);
    }

    public synchronized void setBoolean(String key, boolean value) {
        open();
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public boolean hasEmpty() {
        return hasFirstVersion;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SharePreferUtils putStringSet(String key, Set<String> value) {
        mEditor.putStringSet(key, value);
        mEditor.commit();
        return this;
    }

    public void putObject(String key, Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            mEditor.putString(key, objectVal);
            mEditor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public <T> T getObject(String key, Class<T> clazz) {
        if (mSP.contains(key)) {
            String objectVal = mSP.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void clear() {
        mEditor.clear();
        mEditor.commit();
    }
}
