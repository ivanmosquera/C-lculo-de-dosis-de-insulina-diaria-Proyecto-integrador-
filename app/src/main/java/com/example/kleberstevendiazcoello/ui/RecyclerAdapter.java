package com.example.kleberstevendiazcoello.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kleberstevendiazcoello on 4/12/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<Detalle> arrayList = new ArrayList<>();
    public RecyclerAdapter(ArrayList<Detalle> arrayList){
        this.arrayList = arrayList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plato_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.comida.setText(arrayList.get(position).getComida());
        holder.medida.setText(arrayList.get(position).getMedida());
        holder.Carbohidratos.setText(arrayList.get(position).getCarbohidratos());

    }



    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView comida;
        TextView medida;
        TextView Carbohidratos;

        public MyViewHolder(View itemView) {
            super(itemView);
            comida = (TextView) itemView.findViewById(R.id.txtcomida);
            medida = (TextView) itemView.findViewById(R.id.txtmedida);
            Carbohidratos = (TextView) itemView.findViewById(R.id.txtcarbohidratos);
        }
    }
}
