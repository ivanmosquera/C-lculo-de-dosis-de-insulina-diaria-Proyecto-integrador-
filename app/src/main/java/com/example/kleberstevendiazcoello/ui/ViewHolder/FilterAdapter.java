package com.example.kleberstevendiazcoello.ui.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kleberstevendiazcoello.ui.Activitys.DetalleHistorial;
import com.example.kleberstevendiazcoello.ui.Database.Database;
import com.example.kleberstevendiazcoello.ui.Otros.ItemClickListener;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Detalle;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Historial;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Platos;
import com.example.kleberstevendiazcoello.ui.login.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kleberstevendiazcoello on 2/1/18.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterviewHolder> {
    private ArrayList<Platos> arrayList = new ArrayList<>();
    private ItemClickListener itemClickListener;
    Context ctx;
    Database db;
    RequestQueue requestQueue;

    public FilterAdapter(ArrayList<Platos> arrayList, Context ctx) {
        this.arrayList = arrayList;
        this.ctx = ctx;
    }

    @Override
    public FilterviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alimentosfiltradoscard,parent,false);
        FilterAdapter.FilterviewHolder historialviewHolder = new FilterAdapter.FilterviewHolder(view,ctx,arrayList);
        return historialviewHolder;
    }

    @Override
    public void onBindViewHolder(FilterviewHolder holder, int position) {
        holder.alimento.setText(arrayList.get(position).getFoodName());
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    //Dismiss
    public void dismissFood(int pos){
        String item =  arrayList.get(pos).getFoodName();
        db = new Database(ctx);
        db.DeleteItemAuto(item);
        arrayList.remove(pos);
        this.notifyItemRemoved(pos);

    }


    public void enviarlabelnodeseado(int pos){
        String item =  arrayList.get(pos).getFoodName();
        requestQueue = Volley.newRequestQueue(ctx);
        enviarDatos(item);
        db = new Database(ctx);
        db.DeleteItemAuto(item);
        arrayList.remove(pos);
        this.notifyItemRemoved(pos);

    }

    private void enviarDatos(final String item){
        String url = "http://www.flexoviteq.com.ec/InsuvidaFolder/nonecesarios.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ctx, "Mandado a label no deseados",Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("Item",item);
                return map;
            }

        };
        requestQueue.add(request);


    }

    public static class FilterviewHolder extends RecyclerView.ViewHolder {
        TextView alimento;
        ArrayList<Platos> food = new ArrayList<>();
        Context ctx ;
        private ItemClickListener itemClickListener;
        public FilterviewHolder(View itemView, Context ctx, ArrayList<Platos> food) {
            super(itemView);
            this.food = food;
            this.ctx = ctx;
            alimento = (TextView) itemView.findViewById(R.id.alimentosfilter);
            //itemView.setOnClickListener(this);
        }

        /*@Override
        public void onClick(View view) {
            this.itemClickListener.OnClick(view,getLayoutPosition());
        }
        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;


        }*/
    }
}
