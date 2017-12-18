package com.example.kleberstevendiazcoello.ui.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kleberstevendiazcoello.ui.Activitys.Detalle_Plato_Selec;
import com.example.kleberstevendiazcoello.ui.Activitys.Detalle_food;
import com.example.kleberstevendiazcoello.ui.Otros.ItemClickListener;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Detalle;

import java.util.ArrayList;

/**
 * Created by kleberstevendiazcoello on 11/12/17.
 */

public class RAdapterSPlatos extends RecyclerView.Adapter<RAdapterSPlatos.ReciclerViewHolder>{
    private ArrayList<Detalle> arrayList = new ArrayList<>();
    private ItemClickListener itemClickListener;
    private  ArrayList<Detalle>filterarray = new ArrayList<>();
    Context ctx;

    public RAdapterSPlatos(ArrayList<Detalle> arrayList, Context ctx){
        this.arrayList = arrayList;
        this.ctx = ctx;
        filterarray = this.arrayList;
    }
    @Override
    public ReciclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.platos_elegidos_modelo,parent,false);
        ReciclerViewHolder reciclerViewHolder = new ReciclerViewHolder(view,ctx,filterarray);
        return reciclerViewHolder;
    }

    @Override
    public void onBindViewHolder(ReciclerViewHolder holder, int position) {
        holder.comida.setText(filterarray.get(position).getComida());
        holder.medida.setText(filterarray.get(position).getMedida());
        holder.Carbohidratos.setText(filterarray.get(position).getCarbohidratos());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position) {
                //Snackbar.make(view,filterarray.get(position).getComida(),Snackbar.LENGTH_SHORT).show();
                Detalle detalle = filterarray.get(position);
                Intent intent = new Intent(ctx,Detalle_Plato_Selec.class);
                intent.putExtra("Nombre",detalle.getComida());
                intent.putExtra("Caloria",detalle.getCarbohidratos());
                ctx.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return filterarray.size();
    }

    public void filter(String query) {
        filterarray = new ArrayList<>();
        for(Detalle pl: arrayList ){
            if(pl.getComida().toLowerCase().contains(query.toLowerCase())){
                filterarray.add(pl);
            }
        }
        notifyDataSetChanged();
    }


    public static class ReciclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView comida;
        TextView medida;
        TextView Carbohidratos;
        ArrayList<Detalle> food = new ArrayList<>();
        Context ctx ;
        private ItemClickListener itemClickListener;
        public ReciclerViewHolder(View itemView,Context ctx,ArrayList<Detalle> food) {
            super(itemView);
            this.food = food;
            this.ctx = ctx;
            comida = (TextView) itemView.findViewById(R.id.txtcomida);
            medida = (TextView) itemView.findViewById(R.id.txtmedida);
            Carbohidratos = (TextView) itemView.findViewById(R.id.txtcarbohidratos);
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