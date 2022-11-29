package com.teste.recycleviewapp;


import com.teste.recycleviewapp.api.Api;
import com.teste.recycleviewapp.api.PerformNetworkRequest;
import com.teste.recycleviewapp.api.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class Pedido
{

    static int idPedido;
    private int idClientePedido = new Cliente().getIdCliente();
    private String DataPedido;
    private double valorPedido;
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;


    public Pedido()
    {

    }

    public Pedido(int idPedido, int idCliente, String dataPedido, double valorPedido)
    {
        Pedido.idPedido = idPedido;
        this.idClientePedido = idCliente;
        this.DataPedido = dataPedido;
        this.valorPedido = valorPedido;
    }


    //sem id, pra gerar Pedido
    public Pedido(int idCliente, double valorPedido)
    {
        this.idClientePedido = idCliente;
        this.DataPedido = this.getDataPedido();
        this.valorPedido = valorPedido;
    }


    public boolean enviarPedido()
    {
        //Conexão entre o Android e o PHP através do Hash.
        //a chave tem de ser o mesmo nome dos parametros da função
        HashMap<String, String> params = new HashMap<>();
        params.put("IDCliente", String.valueOf(this.idClientePedido));
        params.put("DataPedido", this.getDataPedido());
        params.put("ValorPedido", String.valueOf(this.getValorPedido()));
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_PEDIDO, params, CODE_POST_REQUEST);

        try
        {
            request.execute();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }

    }

    public void enviarProdutos(Produto produto)
    {

        HashMap<String, String> params = new HashMap<>();
        System.out.println("SAINDO VALORES");
        params.put("IDProduto", produto.getIdProduto());
        System.out.println(produto.getIdProduto());
        params.put("QuantidadeVendida", produto.getQtdeProduto());
        System.out.println(produto.getQtdeProduto());
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CADASTRA_ITENS, params, CODE_POST_REQUEST);
        try
        {
            request.execute();
        }
        catch (Exception e)
        {
          //  Toast.makeText(CarrinhoContext, "Não foi possível cadastrar este produto.", Toast.LENGTH_LONG).show();
        }

    }

    public String getDataPedido()
    {
        DateFormat formatter = DateFormat.getDateTimeInstance();
        formatter.setTimeZone(TimeZone.getTimeZone("GMT-3"));
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public double getValorPedido()
    {
        return valorPedido;
    }

    public void setIdPedido(int idPedido)
    {
        Pedido.idPedido = idPedido;
    }

    public int getIdPedido()
    {
        return Pedido.idPedido;
    }

    //tem de vir da API toda vez que um pedido novo é feito

}
