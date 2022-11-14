package com.teste.recycleviewapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder>
{

    private List<Produto> itens;
    private Context context;


    public AdapterMain()
    {}
    public AdapterMain(Context context, List<Produto> itens)
    {
        this.context = context;
        this.itens = itens;
    }

    @NonNull
    @Override
    public AdapterMain.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.modelo_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMain.ViewHolder holder, int position)
    {
        holder.titulo.setText(itens.get(position).getNomeProduto());
        holder.desc.setText(itens.get(position).getDescProduto());
        holder.preco.setText(itens.get(position).getPrecoProduto());
        holder.img.setImageResource(itens.get(position).getImgProduto());


        holder.addCart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               Intent intent = new Intent(context, PedidoActivity.class);

                intent.putExtra("Titulo", itens.get(position).getNomeProduto());
                intent.putExtra("Preco", itens.get(position).getPrecoProduto());
                intent.putExtra("Imagem", itens.get(position).getImgProduto());
                intent.putExtra("Desc", itens.get(position).getDescProduto());
                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

               // Toast.makeText(context, "Funciona", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return itens.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView modelofeed;
        TextView titulo, desc, preco;
        ImageView img;
        Button addCart;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            modelofeed = itemView.findViewById(R.id.modelofeed);
            titulo = itemView.findViewById(R.id.tituloItem);
            desc = itemView.findViewById(R.id.nomeItemPedido);
            preco = itemView.findViewById(R.id.feedPreco);
            img = itemView.findViewById(R.id.imgItem);
            addCart = itemView.findViewById(R.id.addCart);

        }
    }
}
