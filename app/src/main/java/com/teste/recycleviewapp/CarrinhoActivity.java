package com.teste.recycleviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
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
import com.teste.recycleviewapp.api.Api;
import com.teste.recycleviewapp.api.PerformNetworkRequest;
import com.teste.recycleviewapp.api.RequestHandler;

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

        new getData().start();
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


                System.out.println("SAINDO DO CARRINHO");


                System.out.println("SEU ID DO PEDIDO É " + Pedido.idPedido );

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

            //calculando total
            calculaTotal = calculaTotal + Double.parseDouble(cursor.getString(4));

            //1 ID, 2 titulo, 3 qtd, 4 preco
            Produto produto = new Produto(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), img);
            carrinho.add(produto);
        }


        btnEnviarPedido.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int idCliente = new Cliente().getIdCliente();
                Pedido pedido = new Pedido(idCliente, calculaTotal);
                boolean envio = pedido.enviarPedido();
                if(envio)
                {
                    for (int i = 0; i <=carrinho.size()-1; i++)
                    {
                        //precisa de um segundo parametro, id pedido, vindo da classe Pedido
                        pedido.enviarProdutos(carrinho.get(i));
                    }
                    Snackbar snackbar = Snackbar.make(view, "Pedido realizado com sucesso!", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.rgb(20, 173, 0));
                    snackbar.show();
                }
                else
                {
                    Toast.makeText(CarrinhoActivity.this, "Não foi possível realizar seu pedido...", Toast.LENGTH_SHORT).show();
                }
            }
        });


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