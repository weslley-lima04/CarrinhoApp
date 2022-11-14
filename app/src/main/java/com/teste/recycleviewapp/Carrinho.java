package com.teste.recycleviewapp;

import java.util.ArrayList;

public class Carrinho
{
    private ArrayList<Produto> carrinho = new ArrayList<>();

    public void adicionaItem(Produto produto)
    {
        this.carrinho.add(produto);
    }


    public ArrayList<Produto> getCarrinho()
    {
        return carrinho;
    }
}
