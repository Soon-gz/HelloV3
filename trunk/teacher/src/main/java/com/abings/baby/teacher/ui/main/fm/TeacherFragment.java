package com.abings.baby.teacher.ui.main.fm;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.attend.AttendClassActivity;
import com.abings.baby.teacher.ui.attendanceManager.AttendanceActivity;
import com.abings.baby.teacher.ui.calendar.CalendarActivity;
import com.abings.baby.teacher.ui.class_assistant.ClassAssistantActivity;
import com.abings.baby.teacher.ui.publishteachingplan.TeachingPlanListActivity;
import com.abings.baby.teacher.ui.recipes.RecipesActivity;
import com.abings.baby.teacher.ui.reviews.ReviewsClassActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import org.greenrobot.eventbus.Subscribe;

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
        list.add(new Item("班级考勤", R.drawable.teacher_student_attendance, colorArray[0]));
        list.add(new Item("教学计划", R.drawable.teacher_plan, colorArray[1]));
        list.add(new Item("教师考勤", R.drawable.teacher_teacher_attendance, colorArray[2]));
        list.add(new Item("宝宝评语", R.drawable.teacher_student_reviews, colorArray[3]));
        list.add(new Item("教师食堂", R.drawable.teacher_cookbook, colorArray[4]));
        list.add(new Item("课堂助手", R.drawable.teacher_classhelper, colorArray[5]));
        list.add(new Item("更多", R.drawable.teacher_more, colorArray[6]));
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
                    Intent intent = new Intent(getActivity(), CalendarActivity.class);
                    startActivity(intent);
                } else if (position == 2) {
                    // 教师考勤
//                    showToast("您所在的学校暂未开通此功能");
                    Intent intent = new Intent(getActivity(), AttendanceActivity.class);
                    startActivity(intent);
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
                }  else if (position == 5) {
                    // 课堂助手
                    Intent intent = new Intent(getActivity(), ClassAssistantActivity.class);
                    startActivity(intent);
                }else {
                    showToast("更多功能即将开放");
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

    /**
     * 作者：ShuWen
     * 日期：2017/12/28  9:45
     * 描述：校长端需要在校园界面可选择班级查看动态，这里需要一个图标，由于
     *      标题栏是共享的，所以需要在选择的时候 BaseLibMainActivity选择了界面时
     *      发出通知，让schoolFragment进行更新UI,在其他界面需要异常这个图标
     * @return [返回类型说明]
     */
    @Subscribe
    public void onEvent(String select) {
        if (select.equals(Const.SELECT_OTHER_PAGES) && ZSApp.getInstance().isSchoolMaster()){
            selectSchool.setVisibility(View.GONE);
        }
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
