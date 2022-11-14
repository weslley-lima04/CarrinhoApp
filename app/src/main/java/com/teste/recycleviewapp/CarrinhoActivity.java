package com.teste.recycleviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.teste.recycleviewapp.Database.PedidoHelper;
import com.teste.recycleviewapp.Database.PedidoTabela;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoActivity extends AppCompatActivity
{

    RecyclerView lista_teste;
    AdapterCarrinho adapterCarrinho;
    ArrayList<Produto> carrinho;
    Button limpar;
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

        limpar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               new PedidoHelper(CarrinhoActivity.this).limparCarrinho(CarrinhoActivity.this);
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

            Produto produto = new Produto(cursor.getString(1), cursor.getString(2), cursor.getString(3), img);
            carrinho.add(produto);
        }


        lista_teste = findViewById(R.id.testeRecView);
        lista_teste.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        lista_teste.hasFixedSize();
        lista_teste.setAdapter(adapterCarrinho);



    }


}