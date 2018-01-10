package com.example.kleberstevendiazcoello.ui.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kleberstevendiazcoello.ui.Activitys.DetalleHistorial;
import com.example.kleberstevendiazcoello.ui.Database.Database;
import com.example.kleberstevendiazcoello.ui.Otros.ItemClickListener;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Historial;

import java.util.ArrayList;

/**
 * Created by kleberstevendiazcoello on 2/1/18.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterviewHolder> {
    private ArrayList<String> arrayList = new ArrayList<>();
    private ItemClickListener itemClickListener;
    private  ArrayList<String>filterarray = new ArrayList<>();
    Context ctx;
    Database db;

    public FilterAdapter(ArrayList<String> arrayList, Context ctx) {
        this.arrayList = arrayList;
        this.ctx = ctx;
        filterarray = this.arrayList;
    }

    @Override
    public FilterviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alimentosfiltradoscard,parent,false);
        FilterAdapter.FilterviewHolder historialviewHolder = new FilterAdapter.FilterviewHolder(view,ctx,filterarray);
        return historialviewHolder;
    }

    @Override
    public void onBindViewHolder(FilterviewHolder holder, int position) {
        holder.alimento.setText(filterarray.get(position).toString());
    }


    @Override
    public int getItemCount() {
        return filterarray.size();
    }

    //Dismiss
    public void dismissFood(int pos){
        String item =  arrayList.get(pos).toString();
        db = new Database(ctx);
        db.DeleteItemAuto(item);
        arrayList.remove(pos);
        this.notifyItemRemoved(pos);

    }

    public static class FilterviewHolder extends RecyclerView.ViewHolder {
        TextView alimento;


        ArrayList<String> food = new ArrayList<>();
        Context ctx ;
        private ItemClickListener itemClickListener;
        public FilterviewHolder(View itemView, Context ctx, ArrayList<String> food) {
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
