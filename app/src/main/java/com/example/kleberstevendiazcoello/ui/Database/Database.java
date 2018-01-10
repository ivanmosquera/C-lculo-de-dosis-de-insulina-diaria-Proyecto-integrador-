package com.example.kleberstevendiazcoello.ui.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Platos;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by kleberstevendiazcoello on 11/12/17.
 */

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="Insuvida.db";
    private static final int DB_VER = 1;
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public ArrayList<Platos> getListaComida(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect={"FoodID, FoodName, Calorias, Cantidad"};
        String sqlTable="FoodDetail";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);
        final ArrayList<Platos> result = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(new Platos(c.getString(c.getColumnIndex("FoodID")),c.getString(c.getColumnIndex("FoodName")),
                        c.getString(c.getColumnIndex("Calorias")),c.getString(c.getColumnIndex("Cantidad"))));
            }while (c.moveToNext());
        }
        return result;
    }

    public void addPlatos(Platos platos){
        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("INSERT INTO FoodDetail(FoodID, FoodName, Calorias, Cantidad)VALUES ('%s','%s','%s','%s');",platos.getFoodID(),platos.getFoodName(),
                platos.getCalorias(),platos.getCantidad());
        db.execSQL(query);
    }
    public void cleanList(){

        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("DELETE FROM FoodDetail");
        db.execSQL(query);

    }

    public void DeleteItem(String name){
        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("DELETE FROM FoodDetail WHERE FoodName = '%s' ;", name );
        db.execSQL(query);
    }



    public ArrayList<Platos> getListaComidaAuto(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect={"FoodID, FoodName, Calorias, Cantidad"};
        String sqlTable="FoodDetailAuto";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);
        final ArrayList<Platos> result = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(new Platos(c.getString(c.getColumnIndex("FoodID")),c.getString(c.getColumnIndex("FoodName")),
                        c.getString(c.getColumnIndex("Calorias")),c.getString(c.getColumnIndex("Cantidad"))));
            }while (c.moveToNext());
        }
        return result;
    }

    public void addPlatosAuto(Platos platos){
        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("INSERT INTO FoodDetailAuto (FoodID, FoodName, Calorias, Cantidad)VALUES ('%s','%s','%s','%s');",platos.getFoodID(),platos.getFoodName(),
                platos.getCalorias(),platos.getCantidad());
        db.execSQL(query);
    }


    public void DeleteItemAuto(String name){
        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("DELETE FROM FoodDetailAuto  WHERE FoodName = '%s' ;", name );
        db.execSQL(query);
    }
    public void cleanListAuto(){

        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("DELETE FROM FoodDetailAuto");
        db.execSQL(query);

    }
}
