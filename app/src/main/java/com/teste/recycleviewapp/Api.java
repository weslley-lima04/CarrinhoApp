package com.teste.recycleviewapp;

public class Api
{
    private static final String ROOT_URL = "http://127.0.0.1/api/CantinaAPI/v1/Api.php?apicall=";

    public static final String URL_CREATE_HERO = ROOT_URL + "createProdutos";
    public static final String URL_READ_HEROES = ROOT_URL + "getProdutos";
    public static final String URL_UPDATE_HERO = ROOT_URL + "updateProdutos";
    public static final String URL_DELETE_HERO = ROOT_URL + "deleteProdutos&IDProduto=";
    public static final String URL_CREATE_PEDIDO = ROOT_URL + "createPedido";

}
