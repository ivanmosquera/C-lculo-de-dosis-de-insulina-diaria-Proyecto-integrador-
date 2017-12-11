package com.example.kleberstevendiazcoello.ui.clases_utilitarias;

/**
 * Created by kleberstevendiazcoello on 4/12/17.
 */

public class Detalle {
    private int id;
    private String Comida;
    private  String Medida;
    private String Carbohidratos;

    public Detalle(int id, String comida, String medida, String carbohidratos) {
        this.id = id;
        Comida = comida;
        Medida = medida;
        Carbohidratos = carbohidratos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComida() {
        return Comida;
    }

    public void setComida(String comida) {
        Comida = comida;
    }

    public String getMedida() {
        return Medida;
    }

    public void setMedida(String medida) {
        Medida = medida;
    }

    public String getCarbohidratos() {
        return Carbohidratos;
    }

    public void setCarbohidratos(String carbohidratos) {
        Carbohidratos = carbohidratos;
    }
}
