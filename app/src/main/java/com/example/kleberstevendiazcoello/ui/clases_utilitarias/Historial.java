package com.example.kleberstevendiazcoello.ui.clases_utilitarias;

/**
 * Created by kleberstevendiazcoello on 2/1/18.
 */

public class Historial {
    private int id_historial;
    private String insulina;
    private  String fecha;
    private String carbs;

    public Historial(int id_historial, String insulina, String fecha, String carbs) {
        this.id_historial = id_historial;
        this.insulina = insulina;
        this.fecha = fecha;
        this.carbs = carbs;
    }

    public int getId_historial() {
        return id_historial;
    }

    public void setId_historial(int id_historial) {
        this.id_historial = id_historial;
    }

    public String getInsulina() {
        return insulina;
    }

    public void setInsulina(String insulina) {
        this.insulina = insulina;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }
}
