package com.example.kleberstevendiazcoello.ui.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.Activitys.botton_menu;
import com.example.kleberstevendiazcoello.ui.ViewHolder.RecyclerAdapter;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Detalle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btncrearcuenta, btningresar;
    EditText correo, contrasena;
    private ProgressDialog pDialog;
    RequestQueue requestQueue;
    public static final String ID_data = "iduser";
    public static final String Mail_data = "email";
    public static final String User_data = "user";
    public static final String Peso_data = "peso";
    public static final String Altura_data = "altura";
    public static final String Edad_data = "edad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btncrearcuenta = (Button) findViewById(R.id.btncrearcuenta);
        btningresar = (Button) findViewById(R.id.btn_crear_ok);
        correo = (EditText) findViewById(R.id.txtcorreolog);
        contrasena  = (EditText) findViewById(R.id.txtpasswordlog);
        requestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        btncrearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent i = new Intent(getApplicationContext(),crearcuenta.class);
                startActivity(i);
                finish();


            }
        });
         btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = correo.getText().toString();
                String pass = contrasena.getText().toString();
                enviarDatos(mail,pass);


            }
        });
    }



    public void enviarDatos(final String mail, final String contra){
        String url = "http://www.flexoviteq.com.ec/InsuvidaFolder/login.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        //getdatosuser(mail);
                        Intent intent = new Intent(getApplicationContext(),botton_menu.class);
                        startActivity(intent);
                        finish();

                    }else {

                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),errorMsg, Toast.LENGTH_LONG).show();

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
                map.put("correo",mail);
                map.put("contrasena",contra);
                return map;
            }

        };
        requestQueue.add(request);
    }



    public void getdatosuser(final String mail){

        String url = "http://www.flexoviteq.com.ec/InsuvidaFolder/datosuser.php";
        final JSONObject obj = new JSONObject();

        JsonArrayRequest jsonArrayRequest =  new JsonArrayRequest(com.android.volley.Request.Method.POST, url,(String) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = 0;
                        while(count<response.length()){
                            try {
                                JSONObject object = response.getJSONObject(count);
                                SharedPreferences sharedPref = MainActivity.this.getSharedPreferences(
                                        "userinfo", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString(Mail_data, mail);
                                editor.putInt(ID_data,object.getInt("id"));
                                editor.putString(User_data,object.getString("Usuario"));
                                editor.putString(Peso_data,String.valueOf(object.getString("Peso")));
                                editor.putString(Altura_data,String.valueOf(object.getString("Altura")));
                                editor.putInt(Edad_data,object.getInt("Edad"));
                                editor.commit();
                                count ++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("correo",mail);

                return map;
            }

        };
        requestQueue.add(jsonArrayRequest);


    }
}
