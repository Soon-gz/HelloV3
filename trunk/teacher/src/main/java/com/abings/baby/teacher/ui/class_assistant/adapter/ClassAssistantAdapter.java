package com.abings.baby.teacher.ui.class_assistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.Information.WebViewActivity;
import com.abings.baby.teacher.ui.class_assistant.ClassAssistantWebViewActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.ClassAssistantModel;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.KeyWordUtils;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.List;

/**
 * Created by zwj on 2017/6/6.
 */

public class ClassAssistantAdapter extends BaseAdapter<ClassAssistantModel> {
    private String keyWord = null;

    public ClassAssistantAdapter(Context context, List<ClassAssistantModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, ClassAssistantModel data) {
        TextView tvTitle = holder.getView(R.id.itemClassAssistant_tv_title);
        ImageView imageView = holder.getView(R.id.itemClassAssistant_iv);
        TextView tvContent = holder.getView(R.id.itemClassAssistant_tv_content);
        TextView tvFrom = holder.getView(R.id.itemClassAssistant_tv_from);
        if (keyWord != null && !keyWord.isEmpty()) {
            tvTitle.setText(KeyWordUtils.matcherSearchTitle(data.getTitle(), keyWord));
        } else {
            tvTitle.setText(data.getTitle());
        }
        //图片加载
        ImageLoader.loadRoundCenterCropClassAssistantImg(mContext,Const.URL_ClassRoom + data.getAssistImageurls().split(",")[0], imageView);
        tvContent.setText(data.getContent());
        tvFrom.setText("来自于" + data.getCreaterName() + " " + data.getCreateTimeFormat());

        setOnItemClickListener(new OnItemClickListeners<ClassAssistantModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, ClassAssistantModel data, int position) {
                Intent intent = new Intent(mContext, ClassAssistantWebViewActivity.class);
                intent.putExtra(ClassAssistantModel.kName, data);
                intent.putExtra(ClassAssistantWebViewActivity.kWebUrl, data.getDetailsUrl());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.recycler_item_class_assistant;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER_VIEW && mDatas.size() < 10) {
            setLoadEndView(R.layout.footer_loadend);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

}
