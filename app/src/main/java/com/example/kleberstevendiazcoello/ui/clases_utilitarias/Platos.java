package com.example.kleberstevendiazcoello.ui.clases_utilitarias;

/**
 * Created by kleberstevendiazcoello on 11/12/17.
 */

public class Platos {
    String FoodID;
    String FoodName;
    String Calorias;
    String Cantidad;

    public Platos() {
    }

    public Platos(String foodID, String foodName, String calorias, String cantidad) {
        FoodID = foodID;
        FoodName = foodName;
        Calorias = calorias;
        Cantidad = cantidad;
    }

    public String getFoodID() {
        return FoodID;
    }

    public void setFoodID(String foodID) {
        FoodID = foodID;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getCalorias() {
        return Calorias;
    }

    public void setCalorias(String calorias) {
        Calorias = calorias;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }
}
