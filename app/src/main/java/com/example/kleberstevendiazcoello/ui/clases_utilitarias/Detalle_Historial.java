package com.example.kleberstevendiazcoello.ui.clases_utilitarias;

/**
 * Created by kleberstevendiazcoello on 5/1/18.
 */

public class Detalle_Historial {
    private String Comida;
    private String Carbohidratos;

    public Detalle_Historial(String comida, String carbohidratos) {
        Comida = comida;
        Carbohidratos = carbohidratos;
    }

    public String getComida() {
        return Comida;
    }

    public void setComida(String comida) {
        Comida = comida;
    }

    public String getCarbohidratos() {
        return Carbohidratos;
    }

    public void setCarbohidratos(String carbohidratos) {
        Carbohidratos = carbohidratos;
    }
}
