package com.abings.baby.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ui.album.AlbumActivity;
import com.abings.baby.ui.onlytext.TextActivity;
import com.abings.baby.ui.publishvideo.VideoPlayActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.ui.publish.video.BaseVideoPlayActivity;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.List;

import static com.hellobaby.library.Const.NORMAL_ACTIVITY_RESULT;

public class RecyclerViewAdapterSearchIndex extends BaseAdapter<AlbumModel> {

    public RecyclerViewAdapterSearchIndex(Context context, List<AlbumModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, final AlbumModel albumModel) {
        final Bundle b = new Bundle();
        b.putSerializable("AlbumModel", albumModel);
        ImageView imageIcon= (ImageView) holder.getView(R.id.iv_icon);
        ImageView imageView = (ImageView) holder.getView(R.id.image);
        View mark=holder.getView(R.id.mark);
        TextView text=(TextView) holder.getView(R.id.text);
        if (AlbumModel.TYPE_ALBUM.equals(albumModel.getType())) {
            ImageLoader.loadImg(mContext,albumModel.getImageUrlAbs(),imageView);
            imageIcon.setImageResource(com.abings.baby.R.drawable.mainlist_tagalubm);
            text.setText(albumModel.getTitle());
            mark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AlbumActivity.class);
                    intent.putExtras(b);
                    ((BabyIndexSearchActivity)mContext).startActivityForResult(intent, Const.NORMAL_ACTIVITY_RESULT);
                }
            });
        } else if (AlbumModel.TYPE_TEXT.equals(albumModel.getType())) {
            //色块色块
            ImageLoader.loadImg(mContext,null,imageView);
            imageIcon.setImageResource(com.abings.baby.R.drawable.mainlist_tagtext);
            text.setText(albumModel.getTitle());
            mark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, TextActivity.class);
                    intent.putExtras(b);
                    ((BabyIndexSearchActivity)mContext).startActivityForResult(intent, NORMAL_ACTIVITY_RESULT);
                }
            });
        } else if (AlbumModel.TYPE_VIDEO.equals(albumModel.getType())) {
            ImageLoader.loadImg(mContext,albumModel.getVideoImageUrlAbs(),imageView);
            imageIcon.setImageResource(com.abings.baby.R.drawable.mainlist_tagvideo);
            text.setText(albumModel.getContent());
            mark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = VideoPlayActivity.getIntentVideoPlayNet(Const.videoPath, albumModel.getVideoUrlAbs(), albumModel.getVideoUrl(), VideoPlayActivity.MORE_TYPE_ALBUM);
                    intent.putExtras(b);
                    intent.setClass(mContext, BaseVideoPlayActivity.class);
                    ((BabyIndexSearchActivity)mContext).startActivityForResult(intent, NORMAL_ACTIVITY_RESULT);
                }
            });
        }
        mark.setAlpha(0f);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_searchwall;
    }
}
