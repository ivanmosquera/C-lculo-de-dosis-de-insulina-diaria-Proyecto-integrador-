package com.example.kleberstevendiazcoello.ui.mSwipper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.kleberstevendiazcoello.ui.ViewHolder.FilterAdapter;

/**
 * Created by kleberstevendiazcoello on 10/1/18.
 */

public class SwipeHelperFilter extends ItemTouchHelper.SimpleCallback {
    FilterAdapter adapter;
    public SwipeHelperFilter(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public SwipeHelperFilter(FilterAdapter adapter) {
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
