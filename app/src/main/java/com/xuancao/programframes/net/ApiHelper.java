package com.xuancao.programframes.net;

import android.net.Uri;

public class ApiHelper {


    private static final Uri host = Uri.parse("https://data.estockapp.com");

    public static Uri getMainHost() {
        return host;
    }

}
