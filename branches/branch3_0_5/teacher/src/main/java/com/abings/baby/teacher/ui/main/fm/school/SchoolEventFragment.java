package com.abings.baby.teacher.ui.main.fm.school;

import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.event.EventDetailActivity;
import com.abings.baby.teacher.ui.event.EventListMvpView;
import com.abings.baby.teacher.ui.event.EventListPresenter;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.main.fm.school.SchoolItem;
import com.hellobaby.library.ui.main.fm.school.SchoolRecyclerViewFragment;
import com.hellobaby.library.utils.DateUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by zwj on 2016/12/20.
 * description :
 */

public class SchoolEventFragment extends SchoolRecyclerViewFragment implements EventListMvpView{
    @Inject
    EventListPresenter presenter;
    String eventstring = "活动地点：%s\n活动时间：%s";
    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(((BaseLibActivity)getActivity()).getActivityComponent(),getActivity()).inject(this);
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        presenter.attachView(this);
//        EventItem data=new EventItem("方特欢乐世界两日游","方特欢乐世界","800元/人","30/60人","2017年4月3日","2017年4月2号","李老师","方特欢乐世界是华强方特集团首个拥有完全自有知识产权的主题乐园，它是以科幻和互动体验为特色，采用国际一流的理念和技术精心打造，可媲美西方最先进的主题乐园的东方梦幻乐园，被誉为“东方梦幻乐园”、“亚洲科幻神奇”。","0");
//        EventItem data2=new EventItem("台州博物馆一日游","台州博物馆","50元/人","30/60人","2017年5月16日","2017年5月15日","李老师","博物馆是征集、典藏、陈列和研究代表自然和人类文化遗产的实物的场所，并对那些博物馆有科学性、历史性或者艺术价值的物品进行分类，为公众提供知识、教育和欣赏的文化教育的机构、建筑物、地点或者社会公共机构。博物馆是非营利的永久性机构，对公众开放，为社会发展提供服务，以学习、教育、娱乐为目的。","0");
//        EventItem data3=new EventItem("金泉农庄烧烤亲子一日游","金泉农庄","家长45/人，幼儿15/人","30/60人","2017年12月19日","2017年12月18日","李老师","金泉农庄是一家集高科技农业和休闲观光农业为主体的都市商务休闲农庄。整体布局按照“人、自然、和谐、健康、教育、发展”设计理念规划。目前是浙江省内唯一靠海临江的生态农庄、台州市唯一一家省级农业高新示范园区、台州市区唯一面积最大的生态休闲农庄。要求：必须家长陪同一起，12月19日早上在庄园门口集合，希望家长们踊跃参加！","0");
//        bListData.add(new SchoolItem(event, "方特欢乐世界两日游",String.format(eventstring, "方特欢乐世界", "2017年4月3日"),data,"teacher0102.png"));
//        bListData.add(new SchoolItem(event, "台州博物馆一日游",String.format(eventstring, "台州博物馆", "2017年5月16日"),data2,"teacher0102.png"));
//        bListData.add(new SchoolItem(event, "金泉农庄烧烤亲子一日游",String.format(eventstring, "金泉农庄", "2017年4月3日"),data3,"teacher0102.png"));
//        bAdapter.notifyDataSetChanged();
        presenter.selectEventOnTeacher(Const.SCHOOL_EVENT);
    }

    @Override
    public void initAdapter() {
        bAdapter=new SchoolRVAdapter(getContext(),bListData, EventDetailActivity.class);
    }

    @Override
    public void showEventData(List<EventModel> list) {
        for(EventModel event:list){
            SchoolItem schoolitem=new SchoolItem(SchoolItem.TYPE_EVENT, event.getEventTitle(),String.format(eventstring, event.getEventAddress(), DateUtil.getFormatTimeFromTimestamp(event.getEventStartTime(),"yyyy年MM月dd日")),event,event.getHeadImageurl());
            schoolitem.setFromname(event.getTeacherName());
            schoolitem.setPublish(DateUtil.getDescriptionTimeFromTimestamp(event.getCreateTime()));
            bListData.add(schoolitem);
        }
        bAdapter.notifyDataSetChanged();
    }

    @Override
    public void showListData(JSONArray jsonArray) {

    }

    @Override
    public void showData(Object o) {

    }
}
