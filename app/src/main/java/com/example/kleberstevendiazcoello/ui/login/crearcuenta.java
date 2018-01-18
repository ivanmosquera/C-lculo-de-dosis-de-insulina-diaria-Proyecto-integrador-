package com.example.kleberstevendiazcoello.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.kleberstevendiazcoello.ui.login.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kleberstevendiazcoello on 28/11/17.
 */

public class crearcuenta extends AppCompatActivity {
    EditText nombre, contraseña, correo,altura,edad,peso,ciudad,sexo;
    Button enviar,cancelar;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crearcuenta);
        nombre = (EditText) findViewById(R.id.txtusuario);
        contraseña = (EditText) findViewById(R.id.txtpasswordi);
        correo = (EditText) findViewById(R.id.txtcorreoi);
        altura = (EditText) findViewById(R.id.txtaltura);
        edad = (EditText) findViewById(R.id.txtedad);
        peso = (EditText) findViewById(R.id.txtpeso);
        ciudad = (EditText) findViewById(R.id.txtciudad);
        sexo = (EditText) findViewById(R.id.txtsexo);
        enviar = (Button) findViewById(R.id.btn_crear_ok);
        cancelar = (Button) findViewById(R.id.btn_crear_cancelar);
        requestQueue = Volley.newRequestQueue(this);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarDatos();

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
                map.put("nombre",nombre.getText().toString());
                map.put("contrasena",contraseña.getText().toString());
                map.put("correo",correo.getText().toString());
                map.put("altura",altura.getText().toString());
                map.put("edad",edad.getText().toString());
                map.put("peso",peso.getText().toString());
                map.put("ciudad",ciudad.getText().toString());
                map.put("sexo",sexo.getText().toString());

                return map;
            }

        };
        requestQueue.add(request);
    }
}
