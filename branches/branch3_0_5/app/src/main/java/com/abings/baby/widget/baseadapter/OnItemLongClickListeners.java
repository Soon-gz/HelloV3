package com.abings.baby.widget.baseadapter;


public interface OnItemLongClickListeners<T> {
    boolean onItemLongClick(ViewHolder viewHolder, T data, int position);
}
