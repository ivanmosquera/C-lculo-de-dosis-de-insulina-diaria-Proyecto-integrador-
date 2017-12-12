package com.example.kleberstevendiazcoello.ui.Activitys;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.kleberstevendiazcoello.ui.Database.Database;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Platos;

public class Detalle_Plato_Selec extends AppCompatActivity {
    TextView txtnombrecomida;
    TextView txtcarbohidratos;
    TextView txttotal;
    ElegantNumberButton elegantNumberButton;
    Button btn_agregar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String calorias,nombre,cantidad;
    float calculo_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__plato__selec);
        txtnombrecomida = findViewById(R.id.txtNombreComidas);
        txtcarbohidratos =  findViewById(R.id.txtcalorias_dess);
        txttotal = findViewById(R.id.food_total_carbos);
        elegantNumberButton = (ElegantNumberButton) findViewById(R.id.numbre_buttons);
        cantidad = elegantNumberButton.getNumber();
        btn_agregar = (Button)findViewById(R.id.btn_agregar_lista);
        txtnombrecomida.setText(getIntent().getStringExtra("Nombre"));
        txtcarbohidratos.setText(getIntent().getStringExtra("Caloria"));
        nombre = getIntent().getStringExtra("Nombre");
        calorias = getIntent().getStringExtra("Caloria");
        elegantNumberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("myTag", "Cantidad Ingresada : "+elegantNumberButton.getNumber());
                cantidad = elegantNumberButton.getNumber();
                calculo_total = Float.parseFloat(calorias)*Integer.parseInt(cantidad);
                txttotal.setText(String.valueOf(calculo_total));


            }
        });

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getBaseContext()).addPlatos(new Platos("A1",nombre,calorias,cantidad));

                Toast.makeText(getApplicationContext(), "Plato Agregado", Toast.LENGTH_LONG).show();
            }

        });
    }


}
