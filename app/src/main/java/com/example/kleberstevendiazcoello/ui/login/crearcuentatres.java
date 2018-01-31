package com.example.kleberstevendiazcoello.ui.login;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.ConnectionDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class crearcuentatres extends AppCompatActivity {
    EditText nombre, contraseña, correo,altura,edad,peso,ciudad,sexo,telefono;Calendar currentDate;
    Button siguiente,cancelar;
    RequestQueue requestQueue;
    String nombre_ob,conta_ob,correo_ob,altura_ob,edad_ob,peso_ob;
    ConnectionDetector connectionDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearcuentatres);
        nombre_ob = getIntent().getStringExtra("NombreUser");
        conta_ob = getIntent().getStringExtra("Contrasena");
        correo_ob = getIntent().getStringExtra("Correo");
        altura_ob = getIntent().getStringExtra("Altura");
        edad_ob =  getIntent().getStringExtra("Edad");
        peso_ob = getIntent().getStringExtra("Peso");
        Log.d("MYTAG :",nombre_ob);
        Log.d("MYTAG :",conta_ob);
        Log.d("MYTAG :",correo_ob);
        Log.d("MYTAG :",altura_ob);
        Log.d("MYTAG :",edad_ob);
        Log.d("MYTAG :",peso_ob);
        ciudad = (EditText) findViewById(R.id.txtciudad);
        sexo = (EditText) findViewById(R.id.txtsexo);
        telefono = (EditText)findViewById(R.id.txttelefono);
        siguiente = (Button) findViewById(R.id.btnconfirmarcuenta);
        cancelar = (Button) findViewById(R.id.btnregresarados);

        requestQueue = Volley.newRequestQueue(this);
        connectionDetector = new ConnectionDetector(getApplicationContext());

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(connectionDetector.isConnected()) {
                enviarDatos();
                }else {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "NO TIENE CONEXIÓN A INTERNET", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }

            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),crearcuentasegundo.class);
                startActivity(i);
                finish();
            }
        });
    }


    public void enviarDatos(){
        String url = "http://www.flexoviteq.com.ec/InsuvidaFolder/register.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {


                        Toast.makeText(getApplicationContext(), "Se ha Registrado Exitosamente", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(
                                getApplicationContext(),
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("nombre",nombre_ob);
                map.put("contrasena",conta_ob);
                map.put("correo",correo_ob);
                map.put("altura",altura_ob);
                map.put("edad",edad_ob);
                map.put("peso",peso_ob);
                map.put("ciudad",ciudad.getText().toString());
                map.put("sexo",sexo.getText().toString());
                map.put("telefono",telefono.getText().toString());

                return map;
            }

        };
        requestQueue.add(request);
    }
}
