package com.teste.recycleviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    RecyclerView listaItens;
    ArrayList<Produto> itens;
    FloatingActionButton btnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaItens = findViewById(R.id.listaItens);
        btnCart = findViewById(R.id.btnCart);

        itens = new ArrayList<>();

        //os itens a se adicionar
        itens.add(new Produto("Água", "Água sem gás", "2.50", R.drawable.agua));
        itens.add(new Produto("Bolo", "Chocolate", "6.00", R.drawable.bolo));
        itens.add(new Produto( "Refrigerante", "Coca Cola", "5.00", R.drawable.refri));
        itens.add(new Produto( "Salada", "Alface e tomate", "7.00", R.drawable.salada));
        itens.add(new Produto( "Suco", "Laranja", "5.00", R.drawable.suco));


        //instanciar adapter
        AdapterMain adapterItens = new AdapterMain(getApplicationContext(), itens);

        listaItens.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        listaItens.hasFixedSize();

        //aqui o setadapter
        listaItens.setAdapter(adapterItens);

        btnCart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), CarrinhoActivity.class);
                startActivity(intent);
            }
        });

    }



}