package com.teste.recycleviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.teste.recycleviewapp.Database.PedidoHelper;

public class PedidoActivity extends AppCompatActivity
{

    ImageView imagePedido;
    ImageButton mais, menos;
    TextView qtdItens, nomeProduto, preco, descPedido;
    Button addtoCart;
    TextView recebeIDProduto;
    int quantidadeItens = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        recebeIDProduto = findViewById(R.id.recebeIDProduto);
        imagePedido = findViewById(R.id.imagePedido);
        mais = findViewById(R.id.addquantity);
        menos = findViewById(R.id.subquantity);
        qtdItens = findViewById(R.id.quantity);
        nomeProduto = findViewById(R.id.nomeItemPedido);
        preco = findViewById(R.id.precoPedido);
        addtoCart = findViewById(R.id.addtoCart);
        descPedido = findViewById(R.id.descPedidoActivity);


        //getExtras
        Intent intent = getIntent();
        String titulo, precoS, descS, idS;
        int img;
        idS = intent.getExtras().getString("ID");
        titulo = intent.getExtras().getString("Titulo");
        precoS = intent.getExtras().getString("Preco");
        img = intent.getExtras().getInt("Imagem");
        descS = intent.getExtras().getString("Desc");


        recebeIDProduto.setText(idS);
        descPedido.setText(descS);
        nomeProduto.setText(titulo);
        preco.setText(precoS);
        imagePedido.setImageResource(img);

        //aqui o onclick do add to cart
        addtoCart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String idItem = recebeIDProduto.getText().toString();
                String nomeItem = nomeProduto.getText().toString();
                String precoItem = preco.getText().toString();
                String qtdItem = qtdItens.getText().toString();
                SaveCart(idItem, nomeItem, precoItem, qtdItem);
                Intent intent = new Intent(PedidoActivity.this, MainActivity.class);
                startActivity(intent);
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void SaveCart(String id, String nome, String preco, String qtd)
    {
        String insert = new PedidoHelper(this).inputPedido(id, nome, preco, qtd);
        Toast.makeText(this, insert, Toast.LENGTH_SHORT).show();
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