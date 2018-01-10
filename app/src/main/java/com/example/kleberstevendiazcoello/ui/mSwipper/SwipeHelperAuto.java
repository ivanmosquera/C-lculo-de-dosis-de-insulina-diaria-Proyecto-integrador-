package com.example.kleberstevendiazcoello.ui.mSwipper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.kleberstevendiazcoello.ui.ViewHolder.AdapaterAutoHolder;
import com.example.kleberstevendiazcoello.ui.ViewHolder.Apter_carrito_paltos;

/**
 * Created by kleberstevendiazcoello on 2/1/18.
 */

public class SwipeHelperAuto extends ItemTouchHelper.SimpleCallback {

    AdapaterAutoHolder adapter;

    public SwipeHelperAuto(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public SwipeHelperAuto(AdapaterAutoHolder adapter) {
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
