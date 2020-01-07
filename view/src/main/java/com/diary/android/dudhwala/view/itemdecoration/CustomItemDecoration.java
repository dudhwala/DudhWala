package com.diary.android.dudhwala.view.itemdecoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.diary.android.dudhwala.view.SwipeController;

public class CustomItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private final SwipeController swipeController;

    private Drawable divider;

    private final int verticalSpaceHeight;

    /**
     * Default divider will be used
     */
    public CustomItemDecoration(Context context, SwipeController swipeController, int verticalSpaceHeight) {
        final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
        divider = styledAttributes.getDrawable(0);
        styledAttributes.recycle();
        this.verticalSpaceHeight = verticalSpaceHeight;
        this.swipeController = swipeController;
    }

    /**
     * Custom divider will be used
     */
    public CustomItemDecoration(Context context, int resId, int verticalSpaceHeight, SwipeController swipeController) {
        divider = ContextCompat.getDrawable(context, resId);
        this.verticalSpaceHeight = verticalSpaceHeight;
        this.swipeController = swipeController;
    }

    @Override
    public void onDraw(@NonNull Canvas c, RecyclerView parent, @NonNull RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();

            divider.setBounds(left, top, right, bottom);
            //divider.draw(c);
        }
        swipeController.onDraw(c);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeight;
    }
}