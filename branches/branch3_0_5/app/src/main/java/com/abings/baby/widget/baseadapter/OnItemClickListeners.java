package com.abings.baby.widget.baseadapter;


public interface OnItemClickListeners<T> {
    void onItemClick(ViewHolder viewHolder, T data, int position);
}
