package com.example.kleberstevendiazcoello.ui.mSwipper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.ViewHolder.FilterAdapter;

/**
 * Created by kleberstevendiazcoello on 10/1/18.
 */

public class SwipeHelperFilterRigth extends ItemTouchHelper.SimpleCallback {
    FilterAdapter adapter;
    private Drawable background;
    private Context context;
    private Drawable deleteIcon;
    private int intrinsicWidth;
    private int intrinsicHeight ;

    private int xMarkMargin;
    private boolean initiated;
    private int backgroundColor;
    private int leftcolorCode;
    private String leftSwipeLable;
    public SwipeHelperFilterRigth(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public SwipeHelperFilterRigth(FilterAdapter adapter,Context context) {
        super(ItemTouchHelper.UP |ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT);
        this.adapter = adapter;
        this.context = context;
    }
    private void init() {
        background = new ColorDrawable();
        backgroundColor = Color.parseColor("#f44336");
        deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete);
        intrinsicWidth = deleteIcon.getIntrinsicWidth();
        intrinsicHeight = deleteIcon.getIntrinsicHeight();
        initiated = true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        init();
        int itemHeight = itemView.getBottom() - itemView.getTop();

        // Draw the red delete background

        ((ColorDrawable) background).setColor(Color.parseColor("#f44336"));
        background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        background.draw(c);


        /*
        // Calculate position of delete icon
        int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
        int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
        int deleteIconLeft = itemView.getLeft()- deleteIconMargin - intrinsicWidth;
        int deleteIconRight = itemView.getLeft() - deleteIconMargin;
        int deleteIconBottom = deleteIconTop + intrinsicHeight;

        // Draw the delete icon
        deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
        deleteIcon.draw(c);*/



        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.enviarlabelnodeseado(viewHolder.getAdapterPosition());
    }
}
