package com.example.kleberstevendiazcoello.ui.mSwipper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.kleberstevendiazcoello.ui.ViewHolder.Apter_carrito_paltos;

/**
 * Created by kleberstevendiazcoello on 2/1/18.
 */

public class SwipeHelper extends ItemTouchHelper.SimpleCallback {

    Apter_carrito_paltos adapter;

    public SwipeHelper(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public SwipeHelper(Apter_carrito_paltos adapter) {
        super(ItemTouchHelper.UP |ItemTouchHelper.DOWN,ItemTouchHelper.LEFT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        adapter.dismissFood(viewHolder.getAdapterPosition());
    }
}
