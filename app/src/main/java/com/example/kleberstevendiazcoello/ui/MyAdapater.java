package com.example.kleberstevendiazcoello.ui;

import android.app.Activity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kleberstevendiazcoello on 8/12/17.
 */


class LoadingViewHolder extends RecyclerView.ViewHolder{

    public ProgressBar progressBar;
    public LoadingViewHolder(View itemView) {
        super(itemView);
        progressBar =(ProgressBar) itemView.findViewById(R.id.progressbar);

    }
}

class itemViewHolder extends RecyclerView.ViewHolder{
    public TextView alimento;
    public  TextView medida;
    public TextView carbohidratos;


    public itemViewHolder(View itemView) {
        super(itemView);
        alimento = (TextView) itemView.findViewById(R.id.txtcomida);
        medida = (TextView) itemView.findViewById(R.id.txtmedida);
        carbohidratos = (TextView) itemView.findViewById(R.id.txtcarbohidratos);
    }
}
public class MyAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TIPE_ITEM = 0, VIEW_TIPE_LOADING = 1;
    Iloadmore iloadmore;
    boolean isloading;
    Activity activity;
    ArrayList<Detalle> arrayList = new ArrayList<>();
    int VisibleThreshold = 5;
    int lastVisibleitem,totalitemcount;

    public MyAdapater(RecyclerView recyclerView,Activity activity, ArrayList<Detalle> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalitemcount = linearLayoutManager.getItemCount();
                 lastVisibleitem = linearLayoutManager.findLastVisibleItemPosition();
                if(!isloading && totalitemcount <-(lastVisibleitem + VisibleThreshold)){
                    if(iloadmore!= null){
                        iloadmore.OnloadMore();
                        isloading = true;
                    }


                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return arrayList.get(position) == null ? VIEW_TIPE_LOADING:VIEW_TIPE_ITEM;
    }

    public void setIloadmore(Iloadmore iloadmore) {
        this.iloadmore = iloadmore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TIPE_ITEM){
            View view = LayoutInflater.from(activity).inflate(R.layout.plato_item,parent,false);
            return new itemViewHolder(view);
        }else if(viewType == VIEW_TIPE_LOADING){
            View view = LayoutInflater.from(activity).inflate(R.layout.item_loading,parent,false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof itemViewHolder){
            Detalle detalle = arrayList.get(position);
            itemViewHolder viewHolder = (itemViewHolder) holder;
            viewHolder.alimento.setText(arrayList.get(position).getComida());
            viewHolder.medida.setText(arrayList.get(position).getMedida());
            viewHolder.carbohidratos.setText(arrayList.get(position).getCarbohidratos());

        }else if(holder instanceof LoadingViewHolder){
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setIsloaded() {
        this.isloading = false;
    }
}
