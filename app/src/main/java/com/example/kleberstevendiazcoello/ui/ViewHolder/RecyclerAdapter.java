package com.example.kleberstevendiazcoello.ui.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Detalle;
import com.example.kleberstevendiazcoello.ui.Activitys.Detalle_food;
import com.example.kleberstevendiazcoello.ui.Otros.ItemClickListener;
import com.example.kleberstevendiazcoello.ui.R;

import java.util.ArrayList;

/**
 * Created by kleberstevendiazcoello on 4/12/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<Detalle> arrayList = new ArrayList<>();
    private ItemClickListener itemClickListener;
    private  ArrayList<Detalle>filterarray = new ArrayList<>();
    Context ctx;
    public RecyclerAdapter(ArrayList<Detalle> arrayList, Context ctx){
        this.arrayList = arrayList;
        this.ctx = ctx;
        filterarray= this.arrayList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plato_item,parent,false);
        return new MyViewHolder(view,ctx,filterarray);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.comida.setText(filterarray.get(position).getComida());
        holder.medida.setText(filterarray.get(position).getMedida());
        holder.Carbohidratos.setText(filterarray.get(position).getCarbohidratos());
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


    public  static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView comida;
        TextView medida;
        TextView Carbohidratos;
        ArrayList<Detalle> food = new ArrayList<>();
        Context ctx ;
        private ItemClickListener itemClickListener;

        public MyViewHolder(View itemView,Context ctx,ArrayList<Detalle> food) {
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
            //this.itemClickListener.OnClick(view,getLayoutPosition());
          /* int position = getAdapterPosition();
            Detalle detalle = this.food.get(position);
            Intent intent = new Intent(this.ctx,Detalle_food.class);
            intent.putExtra("Nombre",detalle.getComida());
            intent.putExtra("Caloria",detalle.getCarbohidratos());
            this.ctx.startActivity(intent);*/


        }
        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;


        }
    }

}
