package com.project.ap.quotestatus;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


import com.android.volley.Request;

public class mySingleTon {

    private static mySingleTon mInstance;
    private RequestQueue requestQueue;
    private static Context mcontex;

    private mySingleTon(Context context) {
        mcontex = context;
        requestQueue = getRequestQueue();

    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mcontex.getApplicationContext());

        }
        return requestQueue;
    }

    public static synchronized mySingleTon getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new mySingleTon(context);

        }
        return mInstance;

    }

    public void addToRequestque(Request request) {
        requestQueue.add(request);
    }
}