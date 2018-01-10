package com.example.kleberstevendiazcoello.ui.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kleberstevendiazcoello.ui.Database.Database;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Platos;

import java.util.ArrayList;

/**
 * Created by kleberstevendiazcoello on 13/12/17.
 */

public class AdapaterAutoHolder extends RecyclerView.Adapter<AdapaterAutoHolder.AutoHolder> {
    private ArrayList<Platos> arrayList = new ArrayList<>();
    Context ctx;
    Database db;

    public AdapaterAutoHolder(ArrayList<Platos> arrayList, Context ctx){
        this.arrayList = arrayList;
        this.ctx = ctx;
    }
    @Override
    public AutoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.platos_ya_selecionadoauto,parent,false);
        AdapaterAutoHolder.AutoHolder autoHolder = new AdapaterAutoHolder.AutoHolder(view,ctx,arrayList);
        return autoHolder;
    }

    @Override
    public void onBindViewHolder(AutoHolder holder, int position) {
        holder.comida.setText(arrayList.get(position).getFoodName());
       // holder.cantidad.setText(arrayList.get(position).getCantidad());
        //holder.Carbohidratos.setText(arrayList.get(position).getCalorias());
        int total = (Integer.parseInt(arrayList.get(position).getCalorias()))*(Integer.parseInt(arrayList.get(position).getCantidad()));
        holder.Carbohidratos.setText(String.valueOf(total));
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

    public static class AutoHolder extends RecyclerView.ViewHolder{
        TextView comida;
        TextView cantidad;
        TextView Carbohidratos;
        ArrayList<Platos> food = new ArrayList<>();
        Context ctx ;
        public AutoHolder(View itemView,Context ctx,ArrayList<Platos> food) {
            super(itemView);
            this.food = food;
            this.ctx = ctx;
            comida = (TextView) itemView.findViewById(R.id.txtcomidaselectauto);
            //cantidad = (TextView) itemView.findViewById(R.id.txtcantidadselectauto);
            Carbohidratos = (TextView) itemView.findViewById(R.id.txtcarbohidratosselectauto);
        }
    }
}
