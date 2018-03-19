package com.abings.baby.teacher.ui.main.fm;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.attend.AttendClassActivity;
import com.abings.baby.teacher.ui.publishteachingplan.TeachingPlanListActivity;
import com.abings.baby.teacher.ui.recipes.RecipesActivity;
import com.abings.baby.teacher.ui.reviews.ReviewsClassActivity;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;
import com.hellobaby.library.widget.custom.ShareBottomDialog;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2016/11/28.
 * description :
 */

public class TeacherFragment extends BaseMainTitleFragment {


    RecyclerView rv;

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_teacher;
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        rv = (RecyclerView) flContent.findViewById(R.id.fmTeacher_rv);
        int[] colorArray = getContext().getResources().getIntArray(R.array.teachColorArray);
        List<Item> list = new ArrayList<>();
        list.add(new Item("班级点名", R.drawable.teacher_student_attendance, colorArray[0]));
        list.add(new Item("教学计划", R.drawable.teacher_plan, colorArray[1]));
        list.add(new Item("教师考勤", R.drawable.teacher_teacher_attendance, colorArray[2]));
        list.add(new Item("宝宝评语", R.drawable.teacher_student_reviews, colorArray[3]));
        list.add(new Item("教师食堂", R.drawable.teacher_cookbook, colorArray[4]));
        list.add(new Item("更多", R.drawable.teacher_more, colorArray[5]));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        BaseAdapter<Item> baseAdapter = new BaseAdapter<Item>(getContext(), list, false) {
            @Override
            protected void convert(ViewHolder holder, Item data) {
                holder.setText(R.id.itemFmTeacher_tv_name, data.name);
                holder.setBgColor(R.id.itemFmTeacher_ll_root, data.bgColor);
                holder.setImageRes(R.id.itemFmTeacher_iv_icon, data.icon);
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.recycler_item_fmteacher;
            }
        };
        rv.setAdapter(baseAdapter);
        baseAdapter.setOnItemClickListener(new OnItemClickListeners<Item>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, Item data, int position) {
                if (position == 0) {
                    //考勤
                    Intent intent = new Intent(getContext(), AttendClassActivity.class);
                    startActivity(intent);
                } else if (position == 1) {
                    // 教学计划
                    Intent intent = new Intent(getContext(), TeachingPlanListActivity.class);
                    startActivity(intent);
                } else if (position == 2) {
                    // 教师考勤
                    showToast("您所在的学校暂未开通此服务");
//                    ArrayList<MyDialogMenuItem> items = new ArrayList();
//                    MyDialogMenuItem item1 = new MyDialogMenuItem("男孩", 0, R.color.gray6c);
//                    MyDialogMenuItem item2 = new MyDialogMenuItem("女孩", 0, R.color.video_green);
//                    items.add(item1);
//                    items.add(item2);
//                    BottomDialogUtils.getBottomListDialog(getActivity(), items, null);
//                    BottomDialogUtils.getBottomRelDialog(getActivity(),null);
                } else if (position == 3) {
                    //宝宝评语
                    Intent intent = new Intent(getActivity(), ReviewsClassActivity.class);
                    startActivity(intent);
                } else if (position == 4) {
                    // 教师食堂
                    Intent intent = new Intent(getActivity(), RecipesActivity.class);
                    startActivity(intent);
                } else {
//                    showSharePopup();
                    showToast("等待更多功能的开放");
                }
            }
        });
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showError(String err) {

    }


    private class Item {
        public String name;
        public int icon;
        public int bgColor;

        public Item(String name, int icon, int bgColor) {
            this.name = name;
            this.icon = icon;
            this.bgColor = bgColor;
        }
    }
}
