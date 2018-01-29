package com.example.kleberstevendiazcoello.ui.ViewHolder;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.kleberstevendiazcoello.ui.Activitys.Detalle_Plato_Selec;
import com.example.kleberstevendiazcoello.ui.Activitys.Detalle_food;
import com.example.kleberstevendiazcoello.ui.Database.Database;
import com.example.kleberstevendiazcoello.ui.Otros.ItemClickListener;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Detalle;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Platos;
import com.example.kleberstevendiazcoello.ui.fragments.CalcularmanualFragment;
import com.example.kleberstevendiazcoello.ui.fragments.Detalle_plato;
import com.example.kleberstevendiazcoello.ui.fragments.Labels_obtenidos;

import java.util.ArrayList;

/**
 * Created by kleberstevendiazcoello on 11/12/17.
 */

public class RAdapterSPlatos extends RecyclerView.Adapter<RAdapterSPlatos.ReciclerViewHolder>{
    private ArrayList<Detalle> arrayList = new ArrayList<>();
    private ItemClickListener itemClickListener;
    private  ArrayList<Detalle>filterarray = new ArrayList<>();
    Context ctx;
    Dialog popselect;
    ElegantNumberButton elegantNumberButton;
    String cantidad = "1";
    float calculo_total;
    Button btn_agregar,btn_cancelar;

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
            public void OnClick(View view, final int position) {
                final Detalle detalle = filterarray.get(position);
                popselect = new Dialog(ctx);
                popselect.setContentView(R.layout.popup_porciones);
                popselect.show();
                btn_agregar = (Button)popselect.findViewById(R.id.btnguardarplatosmanual);
                btn_cancelar = (Button)popselect.findViewById(R.id.btncancelarmanual);
                elegantNumberButton = (ElegantNumberButton)popselect.findViewById(R.id.numbre_buttons);
                elegantNumberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("myTag", "Cantidad Ingresada : "+elegantNumberButton.getNumber());
                        cantidad = elegantNumberButton.getNumber();
                        calculo_total = Float.parseFloat(detalle.getCarbohidratos())*Integer.parseInt(cantidad);
                    }
                });
                btn_agregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new Database(ctx).addPlatos(new Platos(String.valueOf(detalle.getId()),detalle.getComida(),detalle.getCarbohidratos(),cantidad));
                        Toast.makeText(ctx, "Plato Agregado", Toast.LENGTH_LONG).show();
                        popselect.dismiss();
                    }

                });
                btn_cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popselect.dismiss();
                    }
                });

                //Snackbar.make(view,filterarray.get(position).getComida(),Snackbar.LENGTH_SHORT).show();
                /*Detalle detalle = filterarray.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id_Comida",detalle.getId());
                bundle.putString("Nombre",detalle.getComida());
                bundle.putString("Caloria",detalle.getCarbohidratos());
                android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity)ctx).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                Detalle_plato detalle_plato = new Detalle_plato();
                detalle_plato.setArguments(bundle);
                transaction.replace(R.id.content2,detalle_plato).addToBackStack("").commit();*/

               /* Intent intent = new Intent(ctx,Detalle_Plato_Selec.class);
                intent.putExtra("id_Comida",detalle.getId());
                intent.putExtra("Nombre",detalle.getComida());
                intent.putExtra("Caloria",detalle.getCarbohidratos());
                ctx.startActivity(intent);*/
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