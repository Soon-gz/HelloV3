package com.abings.baby.ui.main.fm;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.adapter.HeaderAndFooterRecyclerViewAdapter;
import com.abings.baby.adapter.MyAdapter;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.util.RecyclerViewUtils;
import com.abings.baby.widget.custom.zstitlebar.IZSMainTitleBarCloseOrOpenListener;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.utils.DeviceUtils;
import com.hellobaby.library.utils.GravitySnapHelper;
import com.hellobaby.library.widget.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by zwj on 2016/10/10.
 * description :
 */

public class BabyFragment extends BaseMainTitleFragment implements BabyFragmentMvpView {
    @Inject
    BabyFragmentPresenter presenter;
    private RecyclerView recyclerView;

    private int item_normal_height;
    private int item_max_height;
    private float item_normal_alpha;
    private float item_max_alpha;
    private float ic_normal_alpha;
    private float ic_max_alpha;
    private float alpha_d;
    private float alpha_c;
    private float item_normal_font_size;
    private float item_max_font_size;
    private float font_size_d;

    private List<AlbumModel> walls;
    private MyAdapter myAdapter;

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent((((BaseLibActivity) getActivity()).getActivityComponent()), getActivity()).inject(this);
    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_baby;
    }

    @Override
    protected void changeBaby() {
        //清空宝宝的首页先
        EventBus.getDefault().post("reflushSchool");
        refershIndexCommon(new ArrayList<AlbumModel>());
        presenter.getIndexCommon();
    }

    private FragmentPagerAdapter mFragmentPagerAdapter;

    public void setMainActivityViewPagerAdapter(FragmentPagerAdapter fragmentPagerAdapter) {
        mFragmentPagerAdapter = fragmentPagerAdapter;
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        zsMainTitleBar.setfromMain(true);
        zsMainTitleBar.setListenerCloseOrOpen(new IZSMainTitleBarCloseOrOpenListener() {
            @Override
            public void titleBarCloseFinish() {
                if (isChangeBaby) {
                    mFragmentPagerAdapter.notifyDataSetChanged();
                }
                isChangeBaby = false;
            }

            @Override
            public void titleBarOpenFinish() {

            }
        });
        recyclerView = (RecyclerView) flContent.findViewById(R.id.recyclerView);
        item_max_height = (int) getResources().getDimension(R.dimen.item_max_height);
        item_normal_height = (int) getResources().getDimension(R.dimen.item_normal_height);
        item_normal_font_size = getResources().getDimension(R.dimen.item_normal_font_size);
        item_max_font_size = getResources().getDimension(R.dimen.item_max_font_size);
        item_normal_alpha = getResources().getFraction(R.fraction.item_normal_mask_alpha, 1, 1);
        item_max_alpha = getResources().getFraction(R.fraction.item_max_mask_alpha, 1, 1);
        ic_normal_alpha = getResources().getFraction(R.fraction.ic_normal_mask_alpha, 1, 1);
        ic_max_alpha = getResources().getFraction(R.fraction.ic_max_mask_alpha, 1, 1);
        font_size_d = item_max_font_size - item_normal_font_size;
        alpha_c = ic_max_alpha - ic_normal_alpha;
        alpha_d = item_max_alpha - item_normal_alpha;
        recyclerView.setLayoutManager(new MyLinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        walls = new ArrayList<>();
        presenter.attachView(this);
        presenter.getIndexCommon();
        myAdapter = new MyAdapter(this.getActivity(), walls);
        HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(myAdapter);
        GravitySnapHelper snapHelper = new GravitySnapHelper(Gravity.TOP);
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        View moreView = getActivity().getLayoutInflater().inflate(R.layout.footer_more, null);
        TextView more = (TextView) moreView.findViewById(R.id.more);
        more.getLayoutParams().height = DeviceUtils.getScreenHeight(getContext()) -
                item_max_height;
        RecyclerViewUtils.setFooterView(recyclerView, moreView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                RecyclerView.ViewHolder beginViewHolder = recyclerView
                        .findViewHolderForLayoutPosition(0);
                RecyclerView.ViewHolder firstViewHolder = recyclerView
                        .findViewHolderForLayoutPosition(linearLayoutManager.findFirstVisibleItemPosition());
                RecyclerView.ViewHolder secondViewHolder = recyclerView
                        .findViewHolderForLayoutPosition(linearLayoutManager.findFirstCompletelyVisibleItemPosition());
                RecyclerView.ViewHolder threeViewHolder = recyclerView
                        .findViewHolderForLayoutPosition(linearLayoutManager.findFirstCompletelyVisibleItemPosition() + 1);
                RecyclerView.ViewHolder fourViewHolder = recyclerView
                        .findViewHolderForLayoutPosition(linearLayoutManager.findFirstCompletelyVisibleItemPosition() + 2);
                RecyclerView.ViewHolder fiveViewHolder = recyclerView
                        .findViewHolderForLayoutPosition(linearLayoutManager.findFirstCompletelyVisibleItemPosition() + 3);
                RecyclerView.ViewHolder lastViewHolder = recyclerView
                        .findViewHolderForLayoutPosition(linearLayoutManager.findLastVisibleItemPosition());
                if (beginViewHolder != null && beginViewHolder instanceof MyAdapter.ViewHolder) {
                    MyAdapter.ViewHolder viewHolder = (MyAdapter.ViewHolder) beginViewHolder;
                    viewHolder.itemView.getLayoutParams().height = item_max_height;
                    viewHolder.mark.setAlpha(0);
                    viewHolder.imageIcon.setAlpha(1.0f);
                    viewHolder.text.setTextSize(item_normal_font_size);
                    viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
                }
                if (firstViewHolder != null && firstViewHolder instanceof MyAdapter.ViewHolder && (firstViewHolder != beginViewHolder)) {
                    MyAdapter.ViewHolder viewHolder = (MyAdapter.ViewHolder) firstViewHolder;
                    if (viewHolder.itemView.getLayoutParams().height - dy <= item_max_height
                            && viewHolder.itemView.getLayoutParams().height - dy >= item_normal_height) {
                        viewHolder.itemView.getLayoutParams().height = viewHolder.itemView.getLayoutParams().height - dy;
                        viewHolder.imageIcon.setAlpha(viewHolder.imageIcon.getAlpha() - dy * alpha_c / item_normal_height);
                        viewHolder.mark.setAlpha(viewHolder.mark.getAlpha() - dy * alpha_d / item_normal_height);
                        viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                viewHolder.text.getTextSize() - dy * font_size_d / item_normal_height);
                        viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
                    }
                }
                if (secondViewHolder != null && secondViewHolder instanceof MyAdapter.ViewHolder) {
                    MyAdapter.ViewHolder viewHolder = (MyAdapter.ViewHolder) secondViewHolder;
                    if (viewHolder.itemView.getLayoutParams().height + dy <= item_max_height
                            && viewHolder.itemView.getLayoutParams().height + dy >= item_normal_height) {
                        viewHolder.itemView.getLayoutParams().height = viewHolder.itemView.getLayoutParams().height + dy;
                        viewHolder.mark.setAlpha(viewHolder.mark.getAlpha() + dy * alpha_d / item_normal_height);
                        viewHolder.imageIcon.setAlpha(viewHolder.imageIcon.getAlpha() + dy * alpha_c / item_normal_height);
                        viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                viewHolder.text.getTextSize() + dy * font_size_d / item_normal_height);
                        viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
                    }
                }
                if ((threeViewHolder != null && threeViewHolder instanceof MyAdapter.ViewHolder) || (fourViewHolder != null && fourViewHolder instanceof MyAdapter.ViewHolder) || (fiveViewHolder != null && fiveViewHolder instanceof MyAdapter.ViewHolder)) {
                    MyAdapter.ViewHolder viewHolder = (MyAdapter.ViewHolder) threeViewHolder;
                    if (null != viewHolder) {
                        viewHolder.mark.setAlpha(item_normal_alpha);
                        viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_normal_font_size);
                        viewHolder.imageIcon.setAlpha(0f);
                        viewHolder.itemView.getLayoutParams().height = item_normal_height;
                        viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
                    }
                }
                if (lastViewHolder != null && lastViewHolder instanceof MyAdapter.ViewHolder) {
                    MyAdapter.ViewHolder viewHolder = (MyAdapter.ViewHolder) lastViewHolder;
                    viewHolder.mark.setAlpha(item_normal_alpha);
                    viewHolder.imageIcon.setAlpha(0f);
                    viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_normal_font_size);
                    viewHolder.itemView.getLayoutParams().height = item_normal_height;
                    viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == SCROLL_STATE_IDLE && walls.size() != 0) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    RecyclerView.ViewHolder firstViewHolder = recyclerView
                            .findViewHolderForLayoutPosition(linearLayoutManager.findFirstVisibleItemPosition());
                    if(firstViewHolder instanceof MyAdapter.ViewHolder) {
                        MyAdapter.ViewHolder viewHolder = (MyAdapter.ViewHolder) firstViewHolder;
                        viewHolder.itemView.getLayoutParams().height = item_max_height;
                        viewHolder.mark.setAlpha(0f);
                        viewHolder.imageIcon.setAlpha(1.0f);
                        viewHolder.text.setTextSize(item_normal_font_size / 3 * 2);
                        viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
                    }
                } else {
                    super.onScrollStateChanged(recyclerView, newState);
                }
            }
        });
    }


    @Override
    public void showData(Object o) {

    }


    @Override
    public void refershIndexCommon(List<AlbumModel> albumModels) {
        if (walls == null)
            walls = new ArrayList<>();
        walls.clear();
        walls.addAll(albumModels);
        if(myAdapter==null){
            myAdapter= new MyAdapter(this.getActivity(), walls);
        }
//        walls.clear();
        if(walls!=null&&walls.size()<=0){
            String commonId = String.valueOf(Integer.MAX_VALUE);
            String content = "来自哈喽宝贝";
            AlbumModel a1 = new AlbumModel();
            a1.setUserId(commonId);
            a1.setCommonId(commonId);
            a1.setTitle("成长记录");
            a1.setContent(content);
            a1.setLastmodifyTime("2017-01-01 12:00:00");
            a1.setImageUrl("common_growth_record.png");
            a1.setImageNames("common_growth_record.png,");
            a1.setImageIds(commonId+",");
            a1.setType(AlbumModel.TYPE_ALBUM);
            walls.add(a1);

            AlbumModel a2 = new AlbumModel();
            a2.setUserId(commonId);
            a2.setCommonId(commonId);
            a2.setTitle("校园动态");
            a2.setContent(content);
            a2.setLastmodifyTime("2017-01-01 12:00:00");
            a2.setImageUrl("common_school_dynamics.png");
            a2.setImageNames("common_school_dynamics.png,");
            a2.setImageIds(commonId+",");
            a2.setType(AlbumModel.TYPE_ALBUM);
            walls.add(a2);

            AlbumModel a3 = new AlbumModel();
            a3.setUserId(commonId);
            a3.setCommonId(commonId);
            a3.setTitle("育儿经验");
            a3.setContent(content);
            a3.setLastmodifyTime("2017-01-01 12:00:00");
            a3.setImageUrl("common_parenting_information.png");
            a3.setImageNames("common_parenting_information.png,");
            a3.setImageIds(commonId+",");
            a3.setType(AlbumModel.TYPE_ALBUM);
            walls.add(a3);
        }
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void refershIndexDate(JSONArray jsonArray) {

    }

    public class MyLinearLayoutManager extends LinearLayoutManager {
        public MyLinearLayoutManager(Context context) {
            super(context);
        }

        public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    //主界面请求刷新
    public void requestIndexCommon() {
        refershIndexCommon(new ArrayList<AlbumModel>());
        presenter.getIndexCommon();
        if(walls.size()!=0)
        recyclerView.smoothScrollToPosition(0);
    }

}
