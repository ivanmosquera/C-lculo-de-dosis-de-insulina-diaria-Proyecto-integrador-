package com.example.kleberstevendiazcoello.ui.login;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.kleberstevendiazcoello.ui.R;

import java.util.Calendar;

public class crearcuentasegundo extends AppCompatActivity {
    EditText nombre, contraseña, correo,altura,edad,peso,ciudad,sexo;
    Calendar currentDate;
    String nombre_ob,conta_ob,correo_ob;
    int dia, mes, año;
    Button siguiente,cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearcuentasegundo);
        altura = (EditText) findViewById(R.id.txtaltura);
        edad = (EditText) findViewById(R.id.txtedad);
        peso = (EditText) findViewById(R.id.txtpeso);
        siguiente = (Button) findViewById(R.id.btnsiguienteatercero);
        cancelar = (Button) findViewById(R.id.btnregresarprimero);
        nombre_ob = getIntent().getStringExtra("NombreUser");
        conta_ob = getIntent().getStringExtra("Contrasena");
        correo_ob = getIntent().getStringExtra("Correo");
        currentDate = Calendar.getInstance();
        dia = currentDate.get(Calendar.DAY_OF_MONTH);
        mes = currentDate.get(Calendar.MONTH);
        año = currentDate.get(Calendar.YEAR);
        edad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(crearcuentasegundo.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        edad.setText(dayOfMonth+"/"+month+"/"+year);
                        dia = dayOfMonth;
                        mes = month-1;
                        año = year;
                    }
                },año,mes,dia);
                datePickerDialog.show();
            }
        });


        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edadParaEnviar;
                mes = mes+1;
                edadParaEnviar = año + "-" + mes + "-" + dia;
                Intent intent = new Intent(getApplicationContext(),crearcuentatres.class);
                intent.putExtra("NombreUser",nombre_ob);
                intent.putExtra("Contrasena",conta_ob);
                intent.putExtra("Correo",correo_ob);
                intent.putExtra("Altura",altura.getText().toString());
                intent.putExtra("Edad",edadParaEnviar);
                intent.putExtra("Peso",peso.getText().toString());
                startActivity(intent);

            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),crearcuenta.class);
                startActivity(i);
                finish();
            }
        });
    }

}
