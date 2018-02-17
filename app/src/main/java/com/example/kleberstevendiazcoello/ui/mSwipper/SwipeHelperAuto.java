package com.example.kleberstevendiazcoello.ui.mSwipper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.ViewHolder.AdapaterAutoHolder;
import com.example.kleberstevendiazcoello.ui.ViewHolder.Apter_carrito_paltos;

/**
 * Created by kleberstevendiazcoello on 2/1/18.
 */

public class SwipeHelperAuto extends ItemTouchHelper.SimpleCallback {

    AdapaterAutoHolder adapter;
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

    public SwipeHelperAuto(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }
    private void init() {
        background = new ColorDrawable();
        backgroundColor = Color.parseColor("#f44336");
        deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete);
        intrinsicWidth = deleteIcon.getIntrinsicWidth();
        intrinsicHeight = deleteIcon.getIntrinsicHeight();
        initiated = true;
    }

    public SwipeHelperAuto(AdapaterAutoHolder adapter,Context context) {
        super(ItemTouchHelper.UP |ItemTouchHelper.DOWN,ItemTouchHelper.LEFT);
        this.adapter = adapter;
        this.context = context;
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



        // Calculate position of delete icon
        int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
        int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
        int deleteIconLeft = itemView.getRight()- deleteIconMargin - intrinsicWidth;
        int deleteIconRight = itemView.getRight() - deleteIconMargin;
        int deleteIconBottom = deleteIconTop + intrinsicHeight;

        // Draw the delete icon
        deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
        deleteIcon.draw(c);



        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        adapter.dismissFood(viewHolder.getAdapterPosition());
    }
}
