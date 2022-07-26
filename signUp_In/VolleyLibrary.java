package com.example.portal;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyLibrary {
    private static VolleyLibrary mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCxt;

    private VolleyLibrary(Context context){
        mCxt = context;
        mRequestQueue=getRequestQueue();
    }

    public static synchronized VolleyLibrary getInstance(Context context){
        if(mInstance == null){
            mInstance = new VolleyLibrary(context);
        }
        return mInstance;
    }
    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mCxt.getApplicationContext());
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }
}
