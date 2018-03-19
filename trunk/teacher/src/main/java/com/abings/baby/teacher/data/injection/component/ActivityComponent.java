package com.abings.baby.teacher.data.injection.component;

import android.content.Context;

import com.abings.baby.teacher.data.injection.module.ActivityModule;
import com.abings.baby.teacher.ui.Information.FavoriteActivity;
import com.abings.baby.teacher.ui.Information.InfoSearchActivity;
import com.abings.baby.teacher.ui.Information.InformationFragment;
import com.abings.baby.teacher.ui.Information.TeacherInfomationChildFg;
import com.abings.baby.teacher.ui.Information.TeacherInfomationFragmentNew;
import com.abings.baby.teacher.ui.Information.WebViewActivity;
import com.abings.baby.teacher.ui.PrizeDraw.Adress.PrizeAdressActivity;
import com.abings.baby.teacher.ui.PrizeDraw.LuckyDrawActivity;
import com.abings.baby.teacher.ui.PrizeDraw.PrizeDetail.PrizeDetailActivity;
import com.abings.baby.teacher.ui.PrizeDraw.history.PrizeHistoryActivity;
import com.abings.baby.teacher.ui.PrizeDraw.history.fm.PrizeFragment;
import com.abings.baby.teacher.ui.PrizeDraw.history.fm.ScoreFragment;
import com.abings.baby.teacher.ui.PrizeDraw.history.prizeDetail.PrizeHistoryDetail;
import com.abings.baby.teacher.ui.aboutapp.AboutAppActivity;
import com.abings.baby.teacher.ui.aboutapp.AdviceActivity;
import com.abings.baby.teacher.ui.alert.AlertActivity;
import com.abings.baby.teacher.ui.attend.AttendClassActivity;
import com.abings.baby.teacher.ui.attend.student.AttendStudentListActivity;
import com.abings.baby.teacher.ui.attendanceManager.AskForLeaveDetailActivity;
import com.abings.baby.teacher.ui.attendanceManager.AskForLeaveHistoryActivity;
import com.abings.baby.teacher.ui.attendanceManager.AskForLeaveManagerActivity;
import com.abings.baby.teacher.ui.attendanceManager.AttenceHistoryActivity;
import com.abings.baby.teacher.ui.attendanceManager.AttendanceActivity;
import com.abings.baby.teacher.ui.attendanceManager.SchoolMasterFragment;
import com.abings.baby.teacher.ui.changephone.ChangePhoneActivity;
import com.abings.baby.teacher.ui.changepwd.ChangePwdActivity;
import com.abings.baby.teacher.ui.calendar.CalendarActivity;
import com.abings.baby.teacher.ui.class_assistant.ClassAssistantActivity;
import com.abings.baby.teacher.ui.class_assistant.ClassAssistantFragment;
import com.abings.baby.teacher.ui.class_assistant.ClassAssistantWebViewActivity;
import com.abings.baby.teacher.ui.class_assistant.search.ClassAssistantSearchActivity;
import com.abings.baby.teacher.ui.contact.school.ContactsSchoolFragment;
import com.abings.baby.teacher.ui.contact.student.ContactsFragment;
import com.abings.baby.teacher.ui.event.EventDetailActivity;
import com.abings.baby.teacher.ui.event.EventJoinDetailActivity;
import com.abings.baby.teacher.ui.event.EventListActivity;
import com.abings.baby.teacher.ui.login.LoginActivity;
import com.abings.baby.teacher.ui.login.forgetpwd.ForgetPwdActivity;
import com.abings.baby.teacher.ui.main.MainActivity;
import com.abings.baby.teacher.ui.main.fm.MeFragment;
import com.abings.baby.teacher.ui.main.fm.SchoolFragment;
import com.abings.baby.teacher.ui.main.fm.aboutme.AboutMeSchoolFragment;
import com.abings.baby.teacher.ui.main.fm.aboutme.AboutMeSettingFragment;
import com.abings.baby.teacher.ui.main.fm.aboutme.AboutMyselfFragment;
import com.abings.baby.teacher.ui.main.fm.school.SchoolAllFragment;
import com.abings.baby.teacher.ui.main.fm.school.SchoolCookbookFragment;
import com.abings.baby.teacher.ui.main.fm.school.SchoolDynamicFragment;
import com.abings.baby.teacher.ui.main.fm.school.SchoolEventFragment;
import com.abings.baby.teacher.ui.main.fm.school.SchoolMasterChooseActivity;
import com.abings.baby.teacher.ui.main.fm.school.SchoolNewsFragment;
import com.abings.baby.teacher.ui.msg.MsgCenterActivity;
import com.abings.baby.teacher.ui.msg.build.MsgBuildActivity;
import com.abings.baby.teacher.ui.msg.detail.MsgDetailActivity;
import com.abings.baby.teacher.ui.msg.fm.InBoxFragment;
import com.abings.baby.teacher.ui.msg.fm.OutBoxFragment;
import com.abings.baby.teacher.ui.msg.fm.UnReadActivity;
import com.abings.baby.teacher.ui.publishevent.PublishEventActivity;
import com.abings.baby.teacher.ui.publishpicture.PublishPictureActivity;
import com.abings.baby.teacher.ui.publishpicture.VideoPlayActivity;
import com.abings.baby.teacher.ui.publishteachingplan.PublishTeachingPlanActivity;
import com.abings.baby.teacher.ui.publishteachingplan.TeachingPlanDetailActivity;
import com.abings.baby.teacher.ui.publishteachingplan.TeachingPlanListActivity;
import com.abings.baby.teacher.ui.recipes.RecipesActivity;
import com.abings.baby.teacher.ui.reviews.ReviewsClassActivity;
import com.abings.baby.teacher.ui.reviews.mark.ReviewsMarkActivity;
import com.abings.baby.teacher.ui.reviews.remark.ReviewsReMarkActivity;
import com.abings.baby.teacher.ui.reviews.remarkalert.ReviewsReMarkAlertActivity;
import com.abings.baby.teacher.ui.reviews.type.ReviewsTypeActivity;
import com.abings.baby.teacher.ui.search.InformationSearchActivity;
import com.abings.baby.teacher.ui.search.SearchActivity;
import com.abings.baby.teacher.ui.splash.SplashActivity;
import com.hellobaby.library.injection.ActivityContext;
import com.hellobaby.library.injection.PerActivity;
import com.hellobaby.library.injection.component.BaseActivityComponent;

import dagger.Component;

/**
 * Created by zwj on 2016/9/27.
 * description :
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent extends BaseActivityComponent {
    @ActivityContext
    Context context();

    void inject(SchoolMasterChooseActivity schoolMasterChooseActivity);

    void inject(AttendanceActivity attendanceActivity);

    void inject(SchoolMasterFragment schoolMasterFragment);

    void inject(AskForLeaveHistoryActivity askForLeaveHistoryActivity);

    void inject(AskForLeaveManagerActivity askForLeaveManagerActivity);

    void inject(AskForLeaveDetailActivity askForLeaveDetailActivity);

    void inject(PrizeHistoryDetail prizeHistoryDetail);

    void inject(AttenceHistoryActivity attenceHistoryActivity);

    void inject(PrizeFragment prizeFragment);

    void inject(ScoreFragment scoreFragment);

    void inject(PrizeHistoryActivity prizeHistoryActivity);

    void inject(AboutAppActivity appActivity);

    void inject(PrizeDetailActivity prizeDetailActivity);

    void inject(PrizeAdressActivity prizeAdressActivity);

    void inject(LuckyDrawActivity luckyDrawActivity);

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(AdviceActivity adviceActivity);

    void inject(MeFragment mainActivity);

    void inject(AboutMeSchoolFragment mainActivity);

    void inject(AboutMyselfFragment mainActivity);

    void inject(AboutMeSettingFragment mainActivity);

    void inject(PublishPictureActivity mainActivity);

    void inject(PublishEventActivity mainActivity);

    void inject(SchoolFragment schoolFragment);

    void inject(SchoolAllFragment schoolAllFragment);

    void inject(SchoolNewsFragment newsFragment);

    void inject(SchoolDynamicFragment dynamicFragment);

    void inject(SchoolCookbookFragment cookbookFragment);

    void inject(SchoolEventFragment eventFragment);

    void inject(ContactsFragment contactsFragment);

    void inject(ContactsSchoolFragment schoolFragment);

    void inject(TeachingPlanListActivity schoolFragment);

    void inject(PublishTeachingPlanActivity schoolFragment);

    void inject(EventListActivity eventListActivity);

    void inject(LoginActivity loginActivity);

    void inject(AttendClassActivity loginActivity);

    void inject(AttendStudentListActivity loginActivity);

    void inject(ReviewsClassActivity loginActivity);

    void inject(ReviewsMarkActivity loginActivity);

    void inject(ReviewsReMarkActivity loginActivity);

    void inject(ReviewsReMarkAlertActivity loginActivity);

    void inject(ReviewsTypeActivity loginActivity);

    void inject(RecipesActivity loginActivity);

    void inject(MsgBuildActivity loginActivity);

    void inject(InBoxFragment loginActivity);

    void inject(OutBoxFragment loginActivity);

    void inject(MsgCenterActivity loginActivity);

    void inject(VideoPlayActivity videoPlayActivity);

    void inject(AlertActivity videoPlayActivity);

    void inject(ForgetPwdActivity forgetPwdActivity);

    void inject(ChangePwdActivity changePwdActivity);

    void inject(InformationFragment changePwdActivity);

    void inject(FavoriteActivity changePwdActivity);

    void inject(WebViewActivity changePwdActivity);

    void inject(UnReadActivity changePwdActivity);

    void inject(SearchActivity changePwdActivity);

    void inject(InformationSearchActivity changePwdActivity);

    void inject(MsgDetailActivity changePwdActivity);

    void inject(EventDetailActivity changePwdActivity);

    void inject(EventJoinDetailActivity changePwdActivity);

    void inject(TeachingPlanDetailActivity changePwdActivity);

    void inject(ChangePhoneActivity changePwdActivity);

    void inject(CalendarActivity changePwdActivity);

    void inject(TeacherInfomationFragmentNew changePwdActivity);

    void inject(TeacherInfomationChildFg changePwdActivity);

    void inject(ClassAssistantActivity classAssistantActivity);

    void inject(ClassAssistantFragment classAssistantFragment);

    void inject(ClassAssistantSearchActivity classAssistantSearchActivity);

    void inject(ClassAssistantWebViewActivity classAssistantWebViewActivity);

    void inject(InfoSearchActivity classAssistantWebViewActivity);
}
