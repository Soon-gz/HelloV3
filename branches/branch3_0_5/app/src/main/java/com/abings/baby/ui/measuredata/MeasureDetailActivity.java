package com.abings.baby.ui.measuredata;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.adapter.MyAdapter;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.model.MeasureModel;
import com.hellobaby.library.data.model.MessageItem;
import com.hellobaby.library.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.hellobaby.library.Const.NORMAL_ACTIVITY_RESULT;

public class MeasureDetailActivity extends BaseTitleActivity implements LineCharMvpView {
    @Inject
    LineCharPresenter presenter;
    @BindView(R.id.addMeasure_lv)
    ListView listview;
    String isHeight;
    MyAdapter adapter;
    private List<MeasureModel> mDatas;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_measure_detail;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        mDatas = new ArrayList<>();
        presenter.attachView(this);
        setBtnLeftClickFinish();
        Intent intent = getIntent();
        isHeight = intent.getStringExtra("isHeight");
        if (isHeight.equals("1")) {
            setTitleText("历史身高");
            presenter.selectHisHeight(ZSApp.getInstance().getBabyId());
        } else {
            setTitleText("历史体重");
            presenter.selectHisWeight(ZSApp.getInstance().getBabyId());
        }
        adapter = new MyAdapter(this);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Bundle b=new Bundle();
//                b.putSerializable("MeasureModel",mDatas.get(position));
//                MeasureModel measuremodel=mDatas.get(position);
                Intent intent = new Intent(bContext, AddMeasureDataActivity.class);
                intent.putExtra("isHeight", isHeight);
                intent.putExtra("MeasureModel", mDatas.get(position));
                startActivityForResult(intent, NORMAL_ACTIVITY_RESULT);
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater = null;

        private MyAdapter(Context context) {
            //根据context上下文加载布局，这里的是Demo17Activity本身，即this
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
//            convertView = View.inflate(getBaseContext(),R.layout.item_measure,null);
                convertView = mInflater.inflate(R.layout.item_measure, null);
                holder = new ViewHolder();
                /**得到各个控件的对象*/
                holder.title = (TextView) convertView.findViewById(R.id.ItemTitle);
                holder.text = (TextView) convertView.findViewById(R.id.ItemText);
                convertView.setTag(holder);//绑定ViewHolder对象
            } else {
                holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
            }
            if (isHeight.equals("1")) {
                holder.title.setText(mDatas.get(position).getHeight() + "cm");
            } else {
                holder.title.setText(mDatas.get(position).getWeight() + "kg");
            }
            int i = -DateUtil.daysBetween(mDatas.get(position).getInputDate(), ZSApp.getInstance().getBirthday())/30;
            String val = "";
            if (i == 0)
                val = "出生";
            else if (i % 12 == 0) {
                val = i / 12 + "周岁";
            } else if (i < 12) {
                val = i + "个月";
            } else {
                val = i / 12 + "周岁" + i % 12 + "个月";
            }
            holder.text.setText(val);
            return convertView;
        }

        final class ViewHolder {
            public TextView title;
            public TextView text;
        }
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void setLastData(JSONObject jsonObject) {

    }

    @Override
    public void selectHisHeight(List<MeasureModel> models) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas = models;
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        {
            if (isHeight.equals("1")) {
                setTitleText("历史身高");
                presenter.selectHisHeight(ZSApp.getInstance().getBabyId());
            } else {
                setTitleText("历史体重");
                presenter.selectHisWeight(ZSApp.getInstance().getBabyId());
            }
        }
    }
}
