package com.example.kleberstevendiazcoello.ui.clases_utilitarias;

/**
 * Created by kleberstevendiazcoello on 2/1/18.
 */

public class Historial {
    private int id_historial;
    private String insulina;
    private  String fecha;
    private String carbs;
    private  String hora;
    private String glucoaa;
    private String glucosao;

    public Historial(int id_historial, String insulina, String fecha, String carbs, String hora, String glucoaa, String glucosao) {
        this.id_historial = id_historial;
        this.insulina = insulina;
        this.fecha = fecha;
        this.carbs = carbs;
        this.hora = hora;
        this.glucoaa = glucoaa;
        this.glucosao = glucosao;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getGlucoaa() {
        return glucoaa;
    }

    public void setGlucoaa(String glucoaa) {
        this.glucoaa = glucoaa;
    }

    public String getGlucosao() {
        return glucosao;
    }

    public void setGlucosao(String glucosao) {
        this.glucosao = glucosao;
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
