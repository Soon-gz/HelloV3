package com.abings.baby.teacher.data.injection.component;

import android.content.Context;

import com.abings.baby.teacher.data.injection.module.ActivityModule;
import com.abings.baby.teacher.ui.Information.FavoriteActivity;
import com.abings.baby.teacher.ui.Information.InformationFragment;
import com.abings.baby.teacher.ui.Information.WebViewActivity;
import com.abings.baby.teacher.ui.alert.AlertActivity;
import com.abings.baby.teacher.ui.attend.AttendClassActivity;
import com.abings.baby.teacher.ui.attend.student.AttendStudentListActivity;
import com.abings.baby.teacher.ui.changepwd.ChangePwdActivity;
import com.abings.baby.teacher.ui.contact.school.ContactsSchoolFragment;
import com.abings.baby.teacher.ui.contact.student.ContactsFragment;
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
import com.abings.baby.teacher.ui.main.fm.school.SchoolNewsFragment;
import com.abings.baby.teacher.ui.msg.MsgCenterActivity;
import com.abings.baby.teacher.ui.msg.build.MsgBuildActivity;
import com.abings.baby.teacher.ui.msg.fm.InBoxFragment;
import com.abings.baby.teacher.ui.msg.fm.OutBoxFragment;
import com.abings.baby.teacher.ui.msg.fm.UnReadActivity;
import com.abings.baby.teacher.ui.publishevent.PublishEventActivity;
import com.abings.baby.teacher.ui.publishpicture.PublishPictureActivity;
import com.abings.baby.teacher.ui.publishpicture.VideoPlayActivity;
import com.abings.baby.teacher.ui.publishteachingplan.PublishTeachingPlanActivity;
import com.abings.baby.teacher.ui.publishteachingplan.TeachingPlanListActivity;
import com.abings.baby.teacher.ui.recipes.RecipesActivity;
import com.abings.baby.teacher.ui.reviews.ReviewsClassActivity;
import com.abings.baby.teacher.ui.reviews.mark.ReviewsMarkActivity;
import com.abings.baby.teacher.ui.reviews.remark.ReviewsReMarkActivity;
import com.abings.baby.teacher.ui.reviews.remarkalert.ReviewsReMarkAlertActivity;
import com.abings.baby.teacher.ui.reviews.type.ReviewsTypeActivity;
import com.abings.baby.teacher.ui.search.InformationSearchActivity;
import com.abings.baby.teacher.ui.search.SearchActivity;
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

    void inject(MainActivity mainActivity);

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
}
