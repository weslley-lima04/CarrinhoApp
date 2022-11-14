package com.teste.recycleviewapp;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class Pedido
{


    ArrayList<Produto> itens;
    int idCliente;
    Date dataPedido;
    double precoTotalPedido;

    //Criando portas HTTP
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    public Pedido(ArrayList<Produto> itens, int idCliente, Date dataPedido, double precoTotalPedido)
    {
        this.itens = itens;
        this.idCliente = idCliente;
        this.dataPedido = dataPedido;
        this.precoTotalPedido = precoTotalPedido;
    }


    public ArrayList<Produto> getItens()
    {
        return itens;
    }

    public void setItens(ArrayList<Produto> itens)
    {
        this.itens = itens;
    }

    public int getIdCliente()
    {
        return idCliente;
    }

    public void setIdCliente(int idCliente)
    {
        this.idCliente = idCliente;
    }

    public Date getDataPedido()
    {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido)
    {
        this.dataPedido = dataPedido;
    }

    public double getPrecoTotalPedido()
    {
        return precoTotalPedido;
    }

    public void setPrecoTotalPedido(double precoTotalPedido)
    {
        this.precoTotalPedido = precoTotalPedido;
    }








}
