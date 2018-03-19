package com.abings.baby.ui.publish;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.abings.baby.R;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.abings.baby.ui.main.MainActivity;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.ui.LoginUtils;
import com.hellobaby.library.widget.BottomDialogUtils;

import javax.inject.Inject;

import butterknife.BindView;



/**
 * 发布消息
 */
public class PublishActivity extends BaseTitleActivity implements PublishMvpView {

    @Inject
    PublishPresenter presenter;

    @BindView(R.id.publish_et_title)
    public EditText etTitle;
    @BindView(R.id.publish_et_content)
    public EditText etContent;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_publish;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }


    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        presenter.attachView(this);
        setTitleBackground(R.color.textBgColor);
        setBtnLeftClickFinish();
        setBtnLeftDrawable(getResources().getDrawable(R.drawable.title_x_red));
        setBtnRightDrawable(getResources().getDrawable(R.drawable.title_tick_green));
        bIvRight.setVisibility(View.GONE);
        bTvTitle.setTextColor(getResources().getColor(R.color.black));
        etTitle.setTextColor(getResources().getColor(R.color.black));
        etContent.setTextColor(getResources().getColor(R.color.black));

        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEmptyEdit();
            }
        });

        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (LoginUtils.isEmptyEdit(etTitle)) {
                    bIvRight.setVisibility(View.GONE);
                } else {
                    bIvRight.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void showData(Object o) {
    }
    public void showEditData(AlbumModel albumModel) {
        etTitle.setText(albumModel.getTitle());
        etContent.setText(albumModel.getContent());
    }
    @Override
    protected void btnRightOnClick(View v) {
        super.btnRightOnClick(v);
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();
        if (LoginUtils.isEmptyEdit(etTitle)) {
            showToast("请输入标题");
            return;
        }
        presenter.logCreate(title, content);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && LoginUtils.isInputEdit(etTitle)) {//, etContent
            isEmptyEdit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //是否编辑
    private void isEmptyEdit() {
        boolean isEdit = LoginUtils.isInputEdit(etTitle);//, etContent
        if (isEdit) {
            BottomDialogUtils.getBottomExitEditDialog(bContext);
        } else {
            finish();
        }
    }

    @Override
    public void logCreateFinish() {
        setResult(MainActivity.mainRequestCode);
        finish();
    }
}
