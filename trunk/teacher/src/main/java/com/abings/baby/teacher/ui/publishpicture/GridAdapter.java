package com.abings.baby.teacher.ui.publishpicture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.abings.baby.teacher.R;
import com.bumptech.glide.Glide;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.GlideRoundTransform;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    private ArrayList<String> listUrls;
    private LayoutInflater inflater;
    private Context mContext;
    public static String ADD = "add";

    public GridAdapter(Context context, ArrayList<String> listUrls) {
        this.listUrls = listUrls;
        if (listUrls.size() == 10) {
            listUrls.remove(listUrls.size() - 1);
        }
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return listUrls.size();
    }

    @Override
    public String getItem(int position) {
        return listUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.grid_item_image, parent, false);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String path = listUrls.get(position);
        if (path.equals(ADD)) {
            //就一张
            holder.image.setImageResource(R.drawable.publish_add);
        } else {
            ImageLoader.loadRoundCenterCrop(mContext, path, holder.image);
        }
        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
    }
}
