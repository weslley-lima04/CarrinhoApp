package com.teste.recycleviewapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class PedidoHelper extends SQLiteOpenHelper
{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pedido.db";


    public PedidoHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String SQL_TABLE = "CREATE TABLE " + PedidoTabela.EntradaPedido.TABLE_NAME + "("
                + PedidoTabela.EntradaPedido._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +  PedidoTabela.EntradaPedido.COLUMN_NAME + " TEXT NOT NULL, "
                +  PedidoTabela.EntradaPedido.COLUMN_QUANTITY + " TEXT NOT NULL, "
                +  PedidoTabela.EntradaPedido.COLUMN_PRICE + " TEXT NOT NULL);";

                db.execSQL(SQL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }

    //criando novos métodos de input e leitura
    public String inputPedido(String titulo, String preco, String qtd)
    {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();
      values.put(PedidoTabela.EntradaPedido.COLUMN_NAME, titulo);
      values.put(PedidoTabela.EntradaPedido.COLUMN_PRICE, preco);
      values.put(PedidoTabela.EntradaPedido.COLUMN_QUANTITY, qtd);
      float insert = db.insert(PedidoTabela.EntradaPedido.TABLE_NAME, null, values);
      if(insert==-1)
      {
          return "Falha ao adicionar ao carrinho.";

      }
     else
      {
          return "Sucesso ao adicionar ao carrinho!";
      }

    }

    public Cursor getPedido()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + PedidoTabela.EntradaPedido.TABLE_NAME + " ORDER BY " + PedidoTabela.EntradaPedido._ID;
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    public void limparCarrinho(Context context)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " + PedidoTabela.EntradaPedido.TABLE_NAME;
        try
        {
            db.execSQL(sql);
            Toast.makeText(context, "Carrinho esvaziado com sucesso!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(context, "Não foi possível limpar seu carrinho.", Toast.LENGTH_SHORT).show();
        }


    }


}
