package com.teste.recycleviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    RecyclerView lista_teste;
    List<Produto> produto;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inflar layout?
        lista_teste = findViewById(R.id.testeRecView);

        produto = new ArrayList<>();

        //aqui adicionaria objetos ao array
        produto.add(new Produto("Água sem gás", "Bebida", "Água", "2,50", R.drawable.agua, "2"));
        produto.add(new Produto("Chocolate", "Doces", "Bolo", "6,00", R.drawable.bolo, "1"));

        //aqui instancia o adapter
        AdapterProduto adapterProduto = new AdapterProduto(getApplicationContext(), produto);

        lista_teste.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        lista_teste.hasFixedSize();

        //aqui o setadapter
        lista_teste.setAdapter(adapterProduto);

    }
}