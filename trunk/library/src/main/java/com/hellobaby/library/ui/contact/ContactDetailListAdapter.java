package com.hellobaby.library.ui.contact;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.UserModel;

import java.util.List;

public class ContactDetailListAdapter extends BaseAdapter {


    public class MyViewHoler {
        ImageView photo;
        TextView relation;
        TextView phone;
        ImageView call;
    }

    private Context context = null;
    private List<UserModel> userModels = null;
    private String selectBabyName;

    @SuppressLint("UseSparseArrays")
    public ContactDetailListAdapter(Context context, List<UserModel> userModels, String selectBabyName) {
        this.context = context;
        this.userModels = userModels;
        this.selectBabyName = selectBabyName;
    }

    @Override
    public int getCount() {
        return userModels.size();
    }

    @Override
    public Object getItem(int arg0) {
        return userModels.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup arg2) {
        MyViewHoler holder = null;

        if (view == null) {
            holder = new MyViewHoler();
            view = LayoutInflater.from(context).inflate(R.layout.dialog_contactlist_item, null);
            holder.photo = (ImageView) view.findViewById(R.id.photo);
            holder.call = (ImageView) view.findViewById(R.id.call);
            holder.relation = (TextView) view.findViewById(R.id.relation);
            holder.phone = (TextView) view.findViewById(R.id.phone);
            view.setTag(holder);

        }
        holder = (MyViewHoler) view.getTag();
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + userModels.get(position).getPhoneNum());
                intent.setData(data);
                context.startActivity(intent);
            }
        });
        boolean bool =  userModels.get(position).isPublic();
        boolean isTeacher = ((Activity)context).getApplication().getPackageName().contains(".teacher");
        if(bool||isTeacher){
            holder.phone.setText(userModels.get(position).getPhoneNum());
        }else {
            holder.call.setEnabled(false);
            holder.phone.setText("非公开");
        }
        String rel = userModels.get(position).getRelation();
            if(rel==null){
                rel = "";
            }
            holder.relation.setText(selectBabyName + "的" + rel);

        Glide.with(context).load( userModels.get(position).getHeadImageurlAbs()).placeholder(R.drawable.main_babynormal_icon).error(R.drawable.main_babynormal_icon).dontAnimate().thumbnail
                (0.1f).into(holder.photo);
        return view;
    }


}
