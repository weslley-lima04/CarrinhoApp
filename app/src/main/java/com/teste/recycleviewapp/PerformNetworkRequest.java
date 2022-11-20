package com.teste.recycleviewapp;


import android.content.ContentValues;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PerformNetworkRequest extends AsyncTask<Void, Void, String>
{
    String url;
    HashMap<String, String> params;
    ContentValues values;
    int requestCode;
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode)
    {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
    }

    PerformNetworkRequest(String url, ContentValues values, int requestCode)
    {
        this.url = url;
        this.values = values;
        this.requestCode = requestCode;
    }



    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        // progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
        //     progressBar.setVisibility(GONE);
        try
        {
            JSONObject object = new JSONObject(s);
            if (!object.getBoolean("error"))
            {
                //Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
               // refreshHeroList(object.getJSONArray("pedido"));
                System.out.println("Erro!");
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(Void... voids)
    {
        RequestHandler requestHandler = new RequestHandler();

        if (requestCode == CODE_POST_REQUEST)
            return requestHandler.sendPostRequest(url, params);


        if (requestCode == CODE_GET_REQUEST)
            return requestHandler.sendGetRequest(url);

        return null;
    }
}

