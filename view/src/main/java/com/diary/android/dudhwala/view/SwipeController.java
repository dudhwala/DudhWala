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
import static androidx.recyclerview.widget.ItemTouchHelper.RIGHT;

enum ButtonsState {
    GONE,
    LEFT_VISIBLE,
    RIGHT_VISIBLE
}

public class SwipeController extends ItemTouchHelper.Callback {

    private static final float mButtonWidth = 300;
    private static final float mSwipeExtraSpace = 16;
    private boolean mSwipeBack = false;
    private ButtonsState mButtonShowedState = ButtonsState.GONE;
    private RectF mButtonInstance = null;
    private RecyclerView.ViewHolder mCurrentItemViewHolder = null;
    private SwipeActionListener mButtonsActions = null;

    public SwipeController(SwipeActionListener buttonsActions) {
        mButtonsActions = buttonsActions;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT | RIGHT);
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
            if (mButtonShowedState != ButtonsState.GONE) {
                if (mButtonShowedState == ButtonsState.LEFT_VISIBLE) {
                    dX = dX > mButtonWidth + mSwipeExtraSpace ? mButtonWidth + mSwipeExtraSpace : mButtonWidth; //Math.max(dX, mButtonWidth);
                }
                if (mButtonShowedState == ButtonsState.RIGHT_VISIBLE) {
                    dX = dX < -mButtonWidth - mSwipeExtraSpace ? -mButtonWidth - mSwipeExtraSpace : -mButtonWidth; // Math.min(dX, -mButtonWidth);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            } else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        if (mButtonShowedState == ButtonsState.GONE) {
            if (dX >= 0) {
                dX = Math.min(dX, mButtonWidth + mSwipeExtraSpace);
            } else {
                dX = Math.max(dX, -mButtonWidth - mSwipeExtraSpace);
            }
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
                if (dX < -mButtonWidth) {
                    mButtonShowedState = ButtonsState.RIGHT_VISIBLE;
                } else if (dX > mButtonWidth) {
                    mButtonShowedState = ButtonsState.LEFT_VISIBLE;
                }

                if (mButtonShowedState != ButtonsState.GONE) {
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

                if (mButtonsActions != null && mButtonInstance != null && mButtonInstance.contains(event.getX(), event.getY())) {
                    if (mButtonShowedState == ButtonsState.LEFT_VISIBLE) {
                        mButtonsActions.onEditClicked(viewHolder.getAdapterPosition());
                    } else if (mButtonShowedState == ButtonsState.RIGHT_VISIBLE) {
                        mButtonsActions.onDeleteClicked(viewHolder.getAdapterPosition());
                    }
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
        float buttonWidthWithoutPadding = mButtonWidth - 20;
        float corners = 16;

        View itemView = viewHolder.itemView;
        Paint p = new Paint();

        RectF leftButton = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + buttonWidthWithoutPadding, itemView.getBottom());
        p.setColor(Color.BLUE);
        c.drawRoundRect(leftButton, corners, corners, p);
        drawText("EDIT", c, leftButton, p);

        RectF rightButton = new RectF(itemView.getRight() - buttonWidthWithoutPadding, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        p.setColor(Color.RED);
        c.drawRoundRect(rightButton, corners, corners, p);
        drawText("DELETE", c, rightButton, p);

        mButtonInstance = null;
        if (mButtonShowedState == ButtonsState.LEFT_VISIBLE) {
            mButtonInstance = leftButton;
        } else if (mButtonShowedState == ButtonsState.RIGHT_VISIBLE) {
            mButtonInstance = rightButton;
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
