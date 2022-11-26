package com.teste.recycleviewapp.api;



import android.os.AsyncTask;
import android.util.Log;

import com.teste.recycleviewapp.Pedido;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class PerformNetworkRequest extends AsyncTask<Void, Void, String>
{
    String url;
    HashMap<String, String> params;
    int requestCode;
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    public PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode)
    {
        this.url = url;
        this.params = params;
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
               // refreshHeroList(object.getJSONArray("pedido"));
            }
        }
       catch (JSONException e)
        {

            Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"] "+ s);
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

