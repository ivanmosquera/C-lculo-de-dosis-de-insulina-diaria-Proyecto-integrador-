package com.example.kleberstevendiazcoello.ui.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kleberstevendiazcoello.ui.Activitys.Detalle_Plato_Auto;
import com.example.kleberstevendiazcoello.ui.Activitys.Detalle_Plato_Selec;
import com.example.kleberstevendiazcoello.ui.Otros.ItemClickListener;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.ViewHolder.RecyclerAdapter;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Detalle;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Platos;
import com.example.kleberstevendiazcoello.ui.fragments.Detalle_Pla_Auto;
import com.example.kleberstevendiazcoello.ui.fragments.Detalle_plato;

import java.util.ArrayList;

/**
 * Created by kleberstevendiazcoello on 13/12/17.
 */

public class RadapterAutoPlatos extends RecyclerView.Adapter<RadapterAutoPlatos.ReciclerViewHolderAuto> {

    private ArrayList<Detalle> arrayList = new ArrayList<>();
    private ItemClickListener itemClickListener;
    Context ctx;
    private  ArrayList<Detalle>filterarray = new ArrayList<>();

    public RadapterAutoPlatos(ArrayList<Detalle> arrayList, Context ctx){
        this.arrayList = arrayList;
        this.ctx = ctx;
        filterarray = this.arrayList;
    }
    @Override
    public ReciclerViewHolderAuto onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.platos_elegidos_modeloauto,parent,false);
        RadapterAutoPlatos.ReciclerViewHolderAuto reciclerViewHolderAuto = new RadapterAutoPlatos.ReciclerViewHolderAuto(view,ctx,filterarray);
        return reciclerViewHolderAuto;
    }

    @Override
    public void onBindViewHolder(ReciclerViewHolderAuto holder, int position) {
        holder.comida.setText(filterarray.get(position).getComida());
        holder.medida.setText(filterarray.get(position).getMedida());
        holder.Carbohidratos.setText(filterarray.get(position).getCarbohidratos());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position) {
                //Snackbar.make(view,filterarray.get(position).getComida(),Snackbar.LENGTH_SHORT).show();
                Detalle detalle = filterarray.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id_Comida",detalle.getId());
                bundle.putString("Nombre",detalle.getComida());
                bundle.putString("Caloria",detalle.getCarbohidratos());
                android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity)ctx).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                Detalle_Pla_Auto detalle_plato_auto= new Detalle_Pla_Auto();
                detalle_plato_auto.setArguments(bundle);
                transaction.replace(R.id.content2,detalle_plato_auto).addToBackStack("").commit();
            }
        });

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


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ReciclerViewHolderAuto extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView comida;
        TextView medida;
        TextView Carbohidratos;
        ArrayList<Detalle> food = new ArrayList<>();
        Context ctx ;
        private ItemClickListener itemClickListener;
        public ReciclerViewHolderAuto(View itemView,Context ctx,ArrayList<Detalle> food) {
            super(itemView);
            this.food = food;
            this.ctx = ctx;
            comida = (TextView) itemView.findViewById(R.id.txtcomidaauto);
            medida = (TextView) itemView.findViewById(R.id.txtmedidaauto);
            Carbohidratos = (TextView) itemView.findViewById(R.id.txtcarbohidratosauto);
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
