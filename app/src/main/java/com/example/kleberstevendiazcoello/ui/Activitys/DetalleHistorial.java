package com.example.kleberstevendiazcoello.ui.Activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kleberstevendiazcoello.ui.Otros.MyAdapater;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.ViewHolder.DetalleHistorialAdapter;
import com.example.kleberstevendiazcoello.ui.ViewHolder.HistorialAdpater;
import com.example.kleberstevendiazcoello.ui.ViewHolder.RecyclerAdapter;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Detalle;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Detalle_Historial;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Historial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetalleHistorial extends AppCompatActivity {
    TextView txtfecha;
    TextView txttotalcarbs;
    RecyclerView recyclerView;
    ArrayList<Detalle> arrayList = new ArrayList<>();
    DetalleHistorialAdapter adapter;
    RequestQueue requestQueue;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_historial);
        txtfecha = (TextView)findViewById(R.id.historialfecha);
        txttotalcarbs = (TextView)findViewById(R.id.dhistorialtotal);
        txtfecha.setText(getIntent().getStringExtra("fechahistorial"));
        txttotalcarbs.setText(getIntent().getStringExtra("totalcarbs"));
        recyclerView = (RecyclerView)findViewById(R.id.detallehistorialrv);
        Log.d("myTag", "Me cree");
        requestQueue = Volley.newRequestQueue(this);
        int id_h = getIntent().getIntExtra("id_Historial",0);
        Log.d("myTag historial", "Me cree" + id_h);
        final String ids = String.valueOf(id_h);
        Log.d("myTag historial", "Me cree string " + ids);
        try {
            getList(String.valueOf(id_h));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }


    public void  getList(final String ids) throws JSONException {

        String url = "http://www.flexoviteq.com.ec/InsuvidaFolder/getdetalleHistorial.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jObj = new JSONArray(response);

                    int count = 0;
                    while(count<jObj.length()){
                        try {
                            JSONObject object = jObj.getJSONObject(count);
                            Detalle d = new Detalle(object.getInt("id_Comida"),object.getString("Alimento"),object.getString("Medida"),object.getString("Cantidad"));
                            arrayList.add(d);
                            count ++;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }



                    adapter = new DetalleHistorialAdapter(arrayList,getApplicationContext());

                    recyclerView.setAdapter(adapter);



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
                map.put("idhistorial",ids);
                return map;
            }

        };
        requestQueue.add(request);


    }
}
