package com.abings.baby.teacher.ui.publishteachingplan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.TeachingPlanModel;
import com.hellobaby.library.ui.slide.SlideActivity;
import com.hellobaby.library.ui.slide.SlideBean;
import com.hellobaby.library.utils.DeviceUtils;
import com.hellobaby.library.utils.GravitySnapHelper;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * 教学计划列表
 */
public class TeachingPlanListActivity extends BaseTitleActivity implements TeachingPlanMvpView{
    @Inject
    TeachingPlanPresenter presenter;
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

    private List<TeachingPlanModel> walls;
    private TeachingPlanAdapter teachingPlanAdapter;

    @Override
    protected int getContentViewLayoutID() {
       return R.layout.activity_teachingplanlist;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);

        setTitleText("教学计划");
        setBtnLeftClickFinish();
        setBtnRightDrawableRes(R.drawable.title_add);
        bIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeachingPlanListActivity.this, PublishTeachingPlanActivity.class);
                startActivityForResult(intent,100);
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.techerplan_rv);
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
        recyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        walls=new ArrayList<TeachingPlanModel>();
        teachingPlanAdapter = new TeachingPlanAdapter(this, walls);
        HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(teachingPlanAdapter);
        GravitySnapHelper snapHelper = new GravitySnapHelper(Gravity.TOP);
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        View moreView = getLayoutInflater().inflate(R.layout.footer_more, null);
        TextView more = (TextView) moreView.findViewById(R.id.more);
        more.getLayoutParams().height = DeviceUtils.getScreenHeight(this) -
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
                if (beginViewHolder != null && beginViewHolder instanceof TeachingPlanAdapter.ViewHolder) {
                    TeachingPlanAdapter.ViewHolder viewHolder = (TeachingPlanAdapter.ViewHolder) beginViewHolder;
                    viewHolder.itemView.getLayoutParams().height = item_max_height;
                    viewHolder.mark.setAlpha(0);
                    viewHolder.text.setTextSize(item_normal_font_size/3*2);
                    viewHolder.timetext.setTextSize(item_normal_font_size/3*2);
                    viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
                }
                if (firstViewHolder != null && firstViewHolder instanceof TeachingPlanAdapter.ViewHolder && (firstViewHolder != beginViewHolder)) {
                    TeachingPlanAdapter.ViewHolder viewHolder = (TeachingPlanAdapter.ViewHolder) firstViewHolder;
                    if (viewHolder.itemView.getLayoutParams().height - dy <= item_max_height
                            && viewHolder.itemView.getLayoutParams().height - dy >= item_normal_height) {
                        viewHolder.itemView.getLayoutParams().height = viewHolder.itemView.getLayoutParams().height - dy;
                        viewHolder.mark.setAlpha(viewHolder.mark.getAlpha() - dy * alpha_d / item_normal_height);
                        viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                viewHolder.text.getTextSize() - dy * font_size_d / item_normal_height);
                        viewHolder.timetext.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                viewHolder.timetext.getTextSize() - dy * font_size_d / item_normal_height);
                        viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
                    }
                }
                if (secondViewHolder != null && secondViewHolder instanceof TeachingPlanAdapter.ViewHolder) {
                    TeachingPlanAdapter.ViewHolder viewHolder = (TeachingPlanAdapter.ViewHolder) secondViewHolder;
                    if (viewHolder.itemView.getLayoutParams().height + dy <= item_max_height
                            && viewHolder.itemView.getLayoutParams().height + dy >= item_normal_height) {
                        viewHolder.itemView.getLayoutParams().height = viewHolder.itemView.getLayoutParams().height + dy;
                        viewHolder.mark.setAlpha(viewHolder.mark.getAlpha() + dy * alpha_d / item_normal_height);
                        viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                viewHolder.text.getTextSize() + dy * font_size_d / item_normal_height);
                        viewHolder.timetext.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                viewHolder.timetext.getTextSize() + dy * font_size_d / item_normal_height);
                        viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
                    }
                }
                if ((threeViewHolder != null && threeViewHolder instanceof TeachingPlanAdapter.ViewHolder)||(fourViewHolder != null && fourViewHolder instanceof TeachingPlanAdapter.ViewHolder)||(fiveViewHolder != null && fiveViewHolder instanceof TeachingPlanAdapter.ViewHolder)){
                    TeachingPlanAdapter.ViewHolder viewHolder = (TeachingPlanAdapter.ViewHolder) threeViewHolder;
                    if (null != viewHolder) {
                        viewHolder.mark.setAlpha(item_normal_alpha);
                        viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_normal_font_size);
                        viewHolder.timetext.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_normal_font_size);
                        viewHolder.itemView.getLayoutParams().height = item_normal_height;
                        viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
                    }
                }
                if (lastViewHolder != null && lastViewHolder instanceof TeachingPlanAdapter.ViewHolder) {
                    TeachingPlanAdapter.ViewHolder viewHolder = (TeachingPlanAdapter.ViewHolder) lastViewHolder;
                    if (null != viewHolder) {
                        viewHolder.mark.setAlpha(item_normal_alpha);
                        viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_normal_font_size);
                        viewHolder.timetext.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_normal_font_size);
                        viewHolder.itemView.getLayoutParams().height = item_normal_height;
                        viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
                    }
                }
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState==SCROLL_STATE_IDLE){
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    RecyclerView.ViewHolder firstViewHolder = recyclerView
                            .findViewHolderForLayoutPosition(linearLayoutManager.findFirstVisibleItemPosition());
                    TeachingPlanAdapter.ViewHolder viewHolder = (TeachingPlanAdapter.ViewHolder) firstViewHolder;
                    viewHolder.itemView.getLayoutParams().height = item_max_height;
                    viewHolder.mark.setAlpha(0);
                    viewHolder.text.setTextSize(item_normal_font_size / 3 * 2);
                    viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
                }else{
                    super.onScrollStateChanged(recyclerView,newState);
                }
            }
        });
        teachingPlanAdapter.setmOnItemLongClickListener(new TeachingPlanAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                final String[] items = {"删除","取消"};
                BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position2, String item, long id) {
                        if (position2 == 0) {
                            presenter.deleteTeachingplanByTeacherId(walls.get(position).getTeachingplanId()+"",position);
                        }
                    }
                });;
            }
        });
        teachingPlanAdapter.setmOnItemClickListener(new TeachingPlanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                List<SlideBean> waterFallItemList = new ArrayList<>();
                SlideBean waterFallItem = new SlideBean();
                waterFallItem.setUrl(walls.get(position).getImageurlAbs());
                waterFallItemList.add(waterFallItem);
                Intent intent = new Intent(bContext, SlideActivity.class);
                intent.putParcelableArrayListExtra(SlideActivity.kDatas, (ArrayList<? extends Parcelable>) waterFallItemList);
                intent.putExtra(SlideActivity.kCurrentPosition, 0);//TODO 这个值需要改成点击图片的值
                bContext.startActivity(intent);
            }
        });
        presenter.selectTeachingplanByTeacherId();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==-1){
            presenter.selectTeachingplanByTeacherId();
        }
    }


    @Override
    public void showData(Object o) {

    }

    @Override
    public void showTeachingPlanList(List list) {

        if (walls==null){
            walls=new ArrayList<TeachingPlanModel>();
        }else{
            walls.clear();
        }
        walls.addAll(list);
        teachingPlanAdapter.notifyDataSetChanged();
    }

    @Override
    public void publishPlanSuccess() {

    }

    @Override
    public void publishDeleteSuccess(int posion) {
        walls.remove(posion);
        teachingPlanAdapter.notifyDataSetChanged();
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

}
