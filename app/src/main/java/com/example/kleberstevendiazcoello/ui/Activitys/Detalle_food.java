package com.example.kleberstevendiazcoello.ui.Activitys;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.kleberstevendiazcoello.ui.R;

public class Detalle_food extends AppCompatActivity {
    TextView txtnombrecomida;
    TextView txtcarbohidratos;
    TextView txttotal;
    ElegantNumberButton elegantNumberButton;
    FloatingActionButton floatingActionButton;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_food);
        txtnombrecomida = findViewById(R.id.txtNombreComida);
        txtcarbohidratos =  findViewById(R.id.txtcalorias_des);
        txttotal = findViewById(R.id.food_total_carbo);
        elegantNumberButton = (ElegantNumberButton) findViewById(R.id.numbre_button);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.btncarro);
        txtnombrecomida.setText(getIntent().getStringExtra("Nombre"));
        txtcarbohidratos.setText(getIntent().getStringExtra("Caloria"));
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.colapsing);

    }
}
