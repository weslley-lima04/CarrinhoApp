package com.teste.recycleviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.teste.recycleviewapp.Database.PedidoHelper;
import com.teste.recycleviewapp.Database.PedidoTabela;

public class PedidoActivity extends AppCompatActivity
{

    ImageView imagePedido;
    ImageButton mais, menos;
    TextView qtdItens, nomeProduto, preco;
    Button addtoCart;
    int quantidadeItens = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        imagePedido = findViewById(R.id.imagePedido);
        mais = findViewById(R.id.addquantity);
        menos = findViewById(R.id.subquantity);
        qtdItens = findViewById(R.id.quantity);
        nomeProduto = findViewById(R.id.nomeItemPedido);
        preco = findViewById(R.id.precoPedido);
        addtoCart = findViewById(R.id.addtoCart);


        //getExtras
        Intent intent = getIntent();
        String titulo, precoS;
        int img;
        titulo = intent.getExtras().getString("Titulo");
        precoS = intent.getExtras().getString("Preco");
        img = intent.getExtras().getInt("Imagem");

        nomeProduto.setText(titulo);
        preco.setText(precoS);
        imagePedido.setImageResource(img);

        //aqui o onclick do add to cart
        addtoCart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                //Intent intent = new Intent(PedidoActivity.this, CarrinhoActivity.class);
               //startActivity(intent);
                String nomeItem = nomeProduto.getText().toString();
                String precoItem = preco.getText().toString();
                String qtdItem = qtdItens.getText().toString();
                SaveCart(nomeItem, precoItem, qtdItem);

            }
        });

        String precoToString = preco.getText().toString();
        double precoBase = Double.parseDouble(precoToString);
        mais.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                quantidadeItens++;
                qtdItens.setText(String.valueOf(quantidadeItens));
                double precofinal = precoBase * quantidadeItens;
                String setnovoPreco = String.valueOf(precofinal);
                preco.setText(setnovoPreco);
            }
        });

        menos.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if(quantidadeItens>0)
                {
                    quantidadeItens--;
                    qtdItens.setText(String.valueOf(quantidadeItens));
                    double precofinal = precoBase * quantidadeItens;
                    String setnovoPreco = String.valueOf(precofinal);
                    preco.setText(setnovoPreco);
                }
                else
                {
                    Toast.makeText(PedidoActivity.this, "Esta é a quantidade mínima de itens", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    private void SaveCart(String nome, String preco, String qtd)
    {
        String insert = new PedidoHelper(this).inputPedido(nome, preco, qtd);
        Toast.makeText(this, insert, Toast.LENGTH_SHORT).show();
    }

}