package com.teste.recycleviewapp;

import android.app.ProgressDialog;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class getData extends Thread
{
    String data = "";
    StringBuilder sb = new StringBuilder();
    ArrayList<String> resp = new ArrayList<>();


    @Override
    public void run()
    {

        try
        {
            //mudar url
            URL url = new URL("http://192.168.1.14/CantinaApi/CantinaAPI/includes/getPedidoID.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            //propriedades da conexão
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK)
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String response;
                response = bufferedReader.readLine();

                //em vez de while, use if
                if (response != null)
                {
                    sb.append(response);
                }
            }
            data = sb.toString();

            if(!(data.isEmpty()))
            {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("LastID");
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject ids = jsonArray.getJSONObject(i);
                    String id = ids.getString("IDPedido");
                   // System.out.println("SEU ID É " + id);
                    resp.add(id);
                }
            }


        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }

    }




}
