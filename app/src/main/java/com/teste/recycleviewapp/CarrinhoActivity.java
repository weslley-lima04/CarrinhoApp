package com.teste.recycleviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
    ArrayList<String> IDsProduto, QtdsProduto;
    int img;

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        carrinho = new ArrayList<>();
        IDsProduto = new ArrayList<>();
        QtdsProduto = new ArrayList<>();
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
                if(enviarPedido(idCliente, gerarData(), total))
                {
                  // enviarProdutos(1, IDsProduto, QtdsProduto);
                }


                Snackbar snackbar = Snackbar.make(view, "Pedido realizado com sucesso!", Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(Color.rgb(20, 173, 0));
                snackbar.show();

            }
        });


        //fazendo o looping para mostrar os dados
        Cursor cursor = new PedidoHelper(this).getPedido();
        while (cursor.moveToNext())
        {
            String titulo = cursor.getString(2);
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

            calculaTotal = calculaTotal + Double.parseDouble(cursor.getString(4));
            IDsProduto.add(cursor.getString(1));
            QtdsProduto.add(cursor.getString(3));
            //1 ID, 2 titulo, 3 qtd, 4 preco
            Produto produto = new Produto(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), img);
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


    private boolean enviarPedido(int idCliente, String dataPedido, String totalPedido)
    {


        //Conexão entre o Android e o PHP através do Hash.
        //a chave tem de ser o mesmo nome dos parametros da função
        HashMap<String, String> params = new HashMap<>();
        params.put("IDCliente", String.valueOf(idCliente));
        params.put("DataPedido", dataPedido);
        params.put("ValorPedido", totalPedido);


        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_PEDIDO, params, CODE_POST_REQUEST);

        try
        {
            request.execute();
            Toast.makeText(this, "Pedido realizado com sucesso!", Toast.LENGTH_LONG).show();
            return true;
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Não foi possível realizar seu pedido...", Toast.LENGTH_SHORT).show();
            return false;
        }


    }

    private void enviarProdutos(int IDPedido, ArrayList<String> idProduto, ArrayList<String> qtdProduto)
    {

       /* for (int i = 0; i < idProduto.size()-1; i++)
        {}*/

        //não funciona

            HashMap<String, String> params = new HashMap<>();
            params.put("IDPedido", String.valueOf(IDPedido));
            params.put("IDProduto", String.valueOf(idProduto.get(0)));
            params.put("QuantidadeVendida", String.valueOf(qtdProduto.get(0)));
            PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CADASTRA_ITENS, params, CODE_POST_REQUEST);
            try
            {
                request.execute();

            }
            catch (Exception e)
            {
                Toast.makeText(this, "Não foi possível cadastrar este produto.", Toast.LENGTH_LONG).show();
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


}