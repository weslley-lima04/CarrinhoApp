package com.teste.recycleviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.teste.recycleviewapp.Database.PedidoHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class CarrinhoActivity extends AppCompatActivity
{

    RecyclerView lista_teste;
    AdapterCarrinho adapterCarrinho;
    ArrayList<Produto> carrinho;
    Button limpar, btnEnviarPedido;
    TextView totalPedido;
    double calculaTotal = 0;
    String total;
    int img;

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        carrinho = new ArrayList<>();
        adapterCarrinho = new AdapterCarrinho(getApplicationContext(), carrinho);
        lista_teste = findViewById(R.id.testeRecView);
        limpar = findViewById(R.id.btnLimpar);
        btnEnviarPedido = findViewById(R.id.btnEviarPedido);
        totalPedido = findViewById(R.id.totalPedido);


        limpar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               new PedidoHelper(CarrinhoActivity.this).limparCarrinho(CarrinhoActivity.this);

               //limpando a tela
               finish();
               overridePendingTransition(0, 0);
               startActivity(getIntent());
               overridePendingTransition(0, 0);
            }
        });


        btnEnviarPedido.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int idCliente = new Cliente().getIdCliente();
                enviarPedido(idCliente, gerarData(), total);
                //limpando a tela
                Snackbar snackbar = Snackbar.make(view, "Pedido realizado com sucesso!", Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(Color.rgb(20, 173, 0));
                snackbar.show();

            }
        });






        //fazendo o looping para mostrar os dados
        Cursor cursor = new PedidoHelper(this).getPedido();
        while (cursor.moveToNext())
        {
            String titulo = cursor.getString(1);
            switch (titulo)
            {
                case "Água":
                    img = R.drawable.agua;
                    break;
                case "Bolo":
                    img = R.drawable.bolo;
                    break;
                case "Refrigerante":
                    img = R.drawable.refri;
                    break;
                case "Salada":
                    img = R.drawable.salada;
                    break;
                case "Suco":
                    img = R.drawable.suco;
                    break;
            }

            calculaTotal = calculaTotal + Double.parseDouble(cursor.getString(3));

            Produto produto = new Produto(cursor.getString(1), cursor.getString(2), cursor.getString(3), img);
            carrinho.add(produto);
        }



        total = String.valueOf(calculaTotal);
        totalPedido.setText(total);


        lista_teste = findViewById(R.id.testeRecView);
        lista_teste.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        lista_teste.hasFixedSize();
        lista_teste.setAdapter(adapterCarrinho);

        if(carrinho.size()==0)
        {
            setContentView(R.layout.modelo_emptyactivity);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void enviarPedido(int idCliente, String dataPedido, String totalPedido)
    {


        //Conexão entre o Android e o PHP através do Hash.
        HashMap<String, String> params = new HashMap<>();
        //params.put("itens", this.getItens());
        params.put("IDCliente", String.valueOf(idCliente));
        params.put("DataPedido", dataPedido);
        params.put("ValorPedido", totalPedido);


        //ContentValues values = new ContentValues();
        //values.put("IDCliente", idCliente);
        //values.put("DataPedido", dataPedido);
        //values.put("ValorPedido", totalPedido);


        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_PEDIDO, params, CODE_POST_REQUEST);

        try
        {
            request.execute();
            Toast.makeText(this, "Pedido realizado com sucesso!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Não foi possível realizar seu pedido...", Toast.LENGTH_SHORT).show();
        }


    }



    public String gerarData()
    {

        DateFormat formatter = DateFormat.getDateTimeInstance();
        formatter.setTimeZone(TimeZone.getTimeZone("GMT-3"));
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }


    private class PerformNetworkRequest extends AsyncTask<Void, Void, String>
    {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                 //   refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }


}