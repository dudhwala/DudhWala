package com.diary.android.dudhwala.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE;
import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;

enum ButtonsState {
    GONE,
    VISIBLE
}

public class SwipeController extends ItemTouchHelper.Callback {

    private static final float mDeleteButtonWidth = 250;
    private static final float mEditButtonWidth = 200;

    private boolean mSwipeBack = false;
    private ButtonsState mButtonShowedState = ButtonsState.GONE;
    private RectF mRightButtonInstance = null;
    private RectF mEditButtonInstance = null;
    private RecyclerView.ViewHolder mCurrentItemViewHolder = null;
    private SwipeActionListener mButtonsActionsListener;
    private int buttonCorners = 16;

    public SwipeController(SwipeActionListener buttonsActionsListener) {
        mButtonsActionsListener = buttonsActionsListener;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (mSwipeBack) {
            mSwipeBack = mButtonShowedState != ButtonsState.GONE;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(Canvas c,
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX,
                            float dY,
                            int actionState,
                            boolean isCurrentlyActive) {
        if (actionState == ACTION_STATE_SWIPE) {
            if (mButtonShowedState == ButtonsState.VISIBLE) {
                dX = Math.min(dX, -(mDeleteButtonWidth + mEditButtonWidth));
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            } else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        if (mButtonShowedState == ButtonsState.GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        mCurrentItemViewHolder = viewHolder;
    }

    private void setTouchListener(final Canvas c,
                                  final RecyclerView recyclerView,
                                  final RecyclerView.ViewHolder viewHolder,
                                  final float dX,
                                  final float dY,
                                  final int actionState,
                                  final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener((v, event) -> {
            mSwipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
            if (mSwipeBack) {
                if (dX < -(mDeleteButtonWidth + mEditButtonWidth)) {
                    mButtonShowedState = ButtonsState.VISIBLE;
                }

                if (mButtonShowedState == ButtonsState.VISIBLE) {
                    setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    setItemsClickable(recyclerView, false);
                }
            }
            return false;
        });
    }

    private void setTouchDownListener(final Canvas c,
                                      final RecyclerView recyclerView,
                                      final RecyclerView.ViewHolder viewHolder,
                                      final float dX,
                                      final float dY,
                                      final int actionState,
                                      final boolean isCurrentlyActive) {

        recyclerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
            return false;
        });
    }

    private void setTouchUpListener(final Canvas c,
                                    final RecyclerView recyclerView,
                                    final RecyclerView.ViewHolder viewHolder,
                                    final float dX,
                                    final float dY,
                                    final int actionState,
                                    final boolean isCurrentlyActive) {

        recyclerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                SwipeController.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                recyclerView.setOnTouchListener((v1, event1) -> false);
                setItemsClickable(recyclerView, true);
                mSwipeBack = false;

                if (mButtonsActionsListener != null && mRightButtonInstance != null && mRightButtonInstance.contains(event.getX(), event.getY())) {
                    mButtonsActionsListener.onDeleteClicked(viewHolder.getAdapterPosition());
                } else if (mButtonsActionsListener != null && mEditButtonInstance != null && mEditButtonInstance.contains(event.getX(), event.getY())) {
                    mButtonsActionsListener.onEditClicked(viewHolder.getAdapterPosition());
                }

                mButtonShowedState = ButtonsState.GONE;
                mCurrentItemViewHolder = null;
            }
            return false;
        });
    }

    private void setItemsClickable(RecyclerView recyclerView, boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); ++i) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder) {
        float deleteButtonWidthWithoutPadding = mDeleteButtonWidth - 10;
        float editButtonWidthWithoutPadding = mEditButtonWidth - 10;

        View itemView = viewHolder.itemView;
        Paint p = new Paint();

        RectF editButton = new RectF(itemView.getRight() - deleteButtonWidthWithoutPadding - editButtonWidthWithoutPadding,
                itemView.getTop(), itemView.getRight() - mDeleteButtonWidth, itemView.getBottom());
        p.setColor(Color.BLUE);
        c.drawRoundRect(editButton, buttonCorners, buttonCorners, p);
        drawText("EDIT", c, editButton, p);

        RectF deleteButton = new RectF(itemView.getRight() - deleteButtonWidthWithoutPadding,
                itemView.getTop(), itemView.getRight(), itemView.getBottom());
        p.setColor(Color.RED);
        c.drawRoundRect(deleteButton, buttonCorners, buttonCorners, p);
        drawText("DELETE", c, deleteButton, p);

        mRightButtonInstance = null;
        mEditButtonInstance = null;
        if (mButtonShowedState == ButtonsState.VISIBLE) {
            mRightButtonInstance = deleteButton;
            mEditButtonInstance = editButton;
        }
    }

    private void drawText(String text, Canvas c, RectF button, Paint p) {
        float textSize = 60;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX() - (textWidth / 2), button.centerY() + (textSize / 2), p);
    }

    public void onDraw(Canvas c) {
        if (mCurrentItemViewHolder != null) {
            drawButtons(c, mCurrentItemViewHolder);
        }
    }

    public interface SwipeActionListener {

        void onEditClicked(int position);

        void onDeleteClicked(int position);
    }
}
