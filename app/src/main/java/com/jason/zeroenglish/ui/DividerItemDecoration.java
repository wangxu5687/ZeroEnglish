package com.jason.zeroenglish.ui;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by wx on 2017/7/27.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = DividerItemDecoration.class.getSimpleName();

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private int mOrientation;

    private int size;

    public DividerItemDecoration(int mOrientation, int size) {
        this.mOrientation = mOrientation;
        this.size = size;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //Log.i(TAG, "onDraw: ");
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //Log.e(TAG, "onDrawOver: ");
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        int count = parent.getAdapter().getItemCount();
        Log.e(TAG, "getItemOffsets: " + position + " - " + count);

        if (mOrientation == HORIZONTAL) {
            if (position == 0) {
                outRect.set(size / 2, 0, size, 0);
            } else if (position == count - 1) {
                outRect.set(0, 0, size / 2, 0);
            } else {
                outRect.set(0, 0, size, 0);
            }

        } else if (mOrientation == VERTICAL) {
            if (position == 0) {
                outRect.set(0, size / 2, 0, size);
            } else if (position == count - 1) {
                outRect.set(0, 0, 0, size / 2);
            } else {
                outRect.set(0, 0, 0, size);
            }

        }


    }
}
