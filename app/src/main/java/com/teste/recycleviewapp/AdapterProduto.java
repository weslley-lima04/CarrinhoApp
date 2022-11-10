package com.teste.recycleviewapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.ViewHolder>
{
    private Context context;
    private List<Produto> produtos;

    public AdapterProduto()
    {}
    public AdapterProduto(Context context, List<Produto> produtos)
    {
        this.context = context;
        this.produtos = produtos;
    }



    @NonNull
    @Override
    public AdapterProduto.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.modelo_produtos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProduto.ViewHolder holder, int position)
    {
        holder.titulo.setText(produtos.get(position).getNomeProduto());
        holder.descricao.setText(produtos.get(position).getDescProduto());
        holder.preco.setText(produtos.get(position).getPrecoProduto());
        holder.qtd.setText((produtos.get(position).getQtdeProduto()));
        holder.img.setImageResource(produtos.get(position).getImgProduto());

        holder.cardmodelo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar snackbar = Snackbar.make(view, "Click funcionando!!", Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(Color.rgb(20, 173, 0));
                snackbar.show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return produtos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardmodelo;
        TextView titulo, descricao, preco, qtd;
        ImageView img;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            cardmodelo = itemView.findViewById(R.id.cardViewt);
            titulo = itemView.findViewById(R.id.nomeProduto);
            descricao = itemView.findViewById(R.id.descProduto);
            preco = itemView.findViewById(R.id.precoProduto);
            qtd = itemView.findViewById(R.id.qtdProduto);
            img = itemView.findViewById(R.id.imgProduto);

        }
    }
}
