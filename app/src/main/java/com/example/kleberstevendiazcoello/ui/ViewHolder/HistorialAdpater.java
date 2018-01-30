package com.example.kleberstevendiazcoello.ui.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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
import com.example.kleberstevendiazcoello.ui.fragments.Labels_obtenidos;
import com.example.kleberstevendiazcoello.ui.fragments.fragment_DetalleHistorial;

import java.util.ArrayList;

/**
 * Created by kleberstevendiazcoello on 2/1/18.
 */

public class HistorialAdpater extends RecyclerView.Adapter<HistorialAdpater.HistorialviewHolder> {
    private ArrayList<Historial> arrayList = new ArrayList<>();
    private ItemClickListener itemClickListener;
    private  ArrayList<Historial>filterarray = new ArrayList<>();
    Context ctx;

    public HistorialAdpater(ArrayList<Historial> arrayList, Context ctx) {
        this.arrayList = arrayList;
        this.ctx = ctx;
        filterarray = this.arrayList;
    }

    @Override
    public HistorialviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historialcardview,parent,false);
        HistorialAdpater.HistorialviewHolder historialviewHolder = new HistorialAdpater.HistorialviewHolder(view,ctx,filterarray);
        return historialviewHolder;
    }

    @Override
    public void onBindViewHolder(HistorialviewHolder holder, int position) {
        holder.fecha.setText(filterarray.get(position).getFecha());
        holder.insulina.setText(filterarray.get(position).getInsulina());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Historial his = filterarray.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id_historial",his.getId_historial());
                bundle.putString("Fecha",his.getFecha());
                bundle.putString("TotalCarb",his.getCarbs());
                bundle.putString("Insulina",his.getInsulina());
                bundle.putString("Hora",his.getHora());
                bundle.putString("glucosao",his.getGlucosao());
                bundle.putString("glucosaa",his.getGlucoaa());
                //bundle.putSerializable("ListaObtenida",listaobtenido);
                android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity)ctx).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                fragment_DetalleHistorial fragment_detalleHistorial = new fragment_DetalleHistorial();

                fragment_detalleHistorial.setArguments(bundle);
                transaction.replace(R.id.content2,fragment_detalleHistorial).addToBackStack("").commit();
                /*Intent intent = new Intent(ctx,DetalleHistorial.class);
                intent.putExtra("id_Historial",his.getId_historial());
                intent.putExtra("fechahistorial",his.getFecha());
                intent.putExtra("totalcarbs",his.getCarbs());*/
            }
        });
    }


    @Override
    public int getItemCount() {
        return filterarray.size();
    }



    public void filter(String query) {
        filterarray = new ArrayList<>();
        for(Historial pl: arrayList ){
            if(pl.getFecha().toLowerCase().contains(query.toLowerCase())){
                filterarray.add(pl);
            }
        }
        notifyDataSetChanged();
    }

    public static class HistorialviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView fecha;
        TextView insulina;

        ArrayList<Historial> food = new ArrayList<>();
        Context ctx ;
        private ItemClickListener itemClickListener;
        public HistorialviewHolder(View itemView, Context ctx, ArrayList<Historial> food) {
            super(itemView);
            this.food = food;
            this.ctx = ctx;
            insulina = (TextView) itemView.findViewById(R.id.htxttotalinsulina);
            fecha= (TextView) itemView.findViewById(R.id.hfechahistorial);
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
