package com.example.myproject.Day2;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat mGestureDetector;
    private RecyclerView recyclerView;

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener());
    }

    //处理事件拦截
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    //处理点击事件
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    //处理事件冲突，不用管
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    //OnGestureListener主要回调各种单击事件，而OnDoubleTapListener回调各种双击事件
    //sdk 还提供了一个外部类SimpleOnGestureListener，这个类实现了上面两个接口的所有方法。
    // 但全都是空实现，函数体里什么也没写，其中就是把上面两个接口合并一下，给出默认的空实现。
    // 这样继承SimpleOnGestureListener的时候就不用实现每一个方法了。
    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        //单击
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                int position = recyclerView.getChildLayoutPosition(child);
                onItemClick(vh,position);
            }
            return true;
        }

        //双击
        public boolean onDoubleTap(MotionEvent e){
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                int position = recyclerView.getChildLayoutPosition(child);
                onItemDoubleClick(vh,position);
            }
            return true;
        }

        //长按
        public void onLongPress(MotionEvent e){
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                int position = recyclerView.getChildLayoutPosition(child);
                onItemLongClick(vh, position);
            }
        }
    }

    public abstract void onItemClick(RecyclerView.ViewHolder vh,int position);
    public abstract void onItemDoubleClick(RecyclerView.ViewHolder vh, int position);
    public abstract void onItemLongClick(RecyclerView.ViewHolder vh, int position);
}
