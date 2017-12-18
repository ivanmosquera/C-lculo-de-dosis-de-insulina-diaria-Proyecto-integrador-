package com.example.kleberstevendiazcoello.ui.ViewHolder;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

/**
 * Created by kleberstevendiazcoello on 13/12/17.
 */

public class RadapterAutoPlatos extends RecyclerView.Adapter<RadapterAutoPlatos.ReciclerViewHolderAuto> {

    private ArrayList<Detalle> arrayList = new ArrayList<>();
    private ItemClickListener itemClickListener;
    Context ctx;

    public RadapterAutoPlatos(ArrayList<Detalle> arrayList, Context ctx){
        this.arrayList = arrayList;
        this.ctx = ctx;
    }
    @Override
    public ReciclerViewHolderAuto onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.platos_elegidos_modeloauto,parent,false);
        RadapterAutoPlatos.ReciclerViewHolderAuto reciclerViewHolderAuto = new RadapterAutoPlatos.ReciclerViewHolderAuto(view,ctx,arrayList);
        return reciclerViewHolderAuto;
    }

    @Override
    public void onBindViewHolder(ReciclerViewHolderAuto holder, int position) {
        holder.comida.setText(arrayList.get(position).getComida());
        holder.medida.setText(arrayList.get(position).getMedida());
        holder.Carbohidratos.setText(arrayList.get(position).getCarbohidratos());

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
            int position = getAdapterPosition();
            Detalle detalle = this.food.get(position);
            Intent intent = new Intent(this.ctx,Detalle_Plato_Auto.class);
            intent.putExtra("Nombre",detalle.getComida());
            intent.putExtra("Caloria",detalle.getCarbohidratos());
            this.ctx.startActivity(intent);
        }
        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;


        }
    }
}
