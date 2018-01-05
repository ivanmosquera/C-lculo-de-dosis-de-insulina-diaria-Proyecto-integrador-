package com.example.kleberstevendiazcoello.ui.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kleberstevendiazcoello.ui.Activitys.DetalleHistorial;
import com.example.kleberstevendiazcoello.ui.Activitys.Detalle_Plato_Selec;
import com.example.kleberstevendiazcoello.ui.Otros.ItemClickListener;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Detalle;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Historial;

import java.util.ArrayList;

/**
 * Created by kleberstevendiazcoello on 4/1/18.
 */

public class DetalleHistorialAdapter extends RecyclerView.Adapter<DetalleHistorialAdapter.DetalleHistorialviewHolder> {
    private ArrayList<Detalle> arrayList = new ArrayList<>();
    private ItemClickListener itemClickListener;
    private  ArrayList<Detalle>filterarray = new ArrayList<>();
    Context ctx;

    public DetalleHistorialAdapter(ArrayList<Detalle> arrayList, Context ctx) {
        this.arrayList = arrayList;
        this.ctx = ctx;
        filterarray = this.arrayList;
    }

    @Override
    public DetalleHistorialAdapter.DetalleHistorialviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detalle_del_historial_view,parent,false);
        DetalleHistorialAdapter.DetalleHistorialviewHolder dhistorialviewHolder = new DetalleHistorialAdapter.DetalleHistorialviewHolder(view,ctx,filterarray);
        return dhistorialviewHolder;
    }

    @Override
    public void onBindViewHolder(DetalleHistorialviewHolder holder, int position) {
        holder.foodname.setText(filterarray.get(position).getComida());
        holder.carbs.setText(filterarray.get(position).getCarbohidratos());

    }




    @Override
    public int getItemCount() {
        return filterarray.size();
    }

    public static class DetalleHistorialviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView carbs;
        TextView foodname;
        ArrayList<Detalle> food = new ArrayList<>();
        Context ctx ;
        private ItemClickListener itemClickListener;
        public DetalleHistorialviewHolder(View itemView, Context ctx, ArrayList<Detalle> food) {
            super(itemView);
            this.food = food;
            this.ctx = ctx;
            foodname = (TextView) itemView.findViewById(R.id.detallehcomida);
            carbs = (TextView) itemView.findViewById(R.id.detallahcarbs);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.OnClick(view,getLayoutPosition());
        }
        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;


        }
    }
}
