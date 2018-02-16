package com.example.kleberstevendiazcoello.ui.login;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.login.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kleberstevendiazcoello on 28/11/17.
 */

public class crearcuenta extends AppCompatActivity {
    EditText nombre, contraseña, correo,altura,edad,peso,ciudad,sexo;
    Calendar currentDate;
    int dia, mes, año;
    Button siguiente,cancelar;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crearcuenta);
        nombre = (EditText) findViewById(R.id.txtusuario);
        contraseña = (EditText) findViewById(R.id.txtpasswordi);
        correo = (EditText) findViewById(R.id.txtcorreoi);
        siguiente = (Button) findViewById(R.id.btnsiguiente);
        cancelar = (Button) findViewById(R.id.btnyatengocuenta);

        //edad.setInputType();

        //mes = mes + 1;
        //edad.setText(dia+"/"+mes+"/"+año);

        /*
        edad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    DatePickerDialog datePickerDialog = new DatePickerDialog(crearcuenta.this, new DatePickerDialog.OnDateSetListener() {
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
            }

        });*/
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombre.getText().toString().equals("")||contraseña.getText().toString().equals("")||correo.getText().toString().equals("")){
                    Snackbar.make(view, "LLENAR TODOS LOS CAMPOS", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }else{
                Intent intent = new Intent(getApplicationContext(),crearcuentasegundo.class);
                intent.putExtra("NombreUser",nombre.getText().toString());
                intent.putExtra("Contrasena",contraseña.getText().toString());
                intent.putExtra("Correo",correo.getText().toString());
                startActivity(intent);
                }

            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }


}
