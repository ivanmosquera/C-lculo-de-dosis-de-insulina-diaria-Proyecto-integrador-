package com.example.kleberstevendiazcoello.ui.Otros;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Detalle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kleberstevendiazcoello on 8/12/17.
 */

public class BackGroundTask {
    Context context;
    ArrayList<Detalle> arrayList = new ArrayList<>();
    String json_url= "http://www.flexoviteq.com.ec/InsuvidaFolder/itemscomida.php";
    public ArrayList<Detalle> getList(){
        JsonArrayRequest jsonArrayRequest =  new JsonArrayRequest(Request.Method.POST, json_url,(String) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = 0;
                        while(count<response.length()){
                            try {
                                JSONObject object = response.getJSONObject(count);
                                arrayList.add(new Detalle(object.getInt("id_Comida"),object.getString("Alimento"),object.getString("Medida"),object.getString("Cantidad")));
                                count ++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getmInstance(context).addToRequestque(jsonArrayRequest);
        return arrayList;
    }
}
