package com.abings.baby.ui.onlytext;

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
 * x消息编辑
 */
public class EditTextActivity extends BaseTitleActivity implements TextMvpView {

    @Inject
    TextPresenter presenter;

    @BindView(R.id.publish_et_title)
    public EditText etTitle;
    @BindView(R.id.publish_et_content)
    public EditText etContent;

    AlbumModel albumModel;

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
        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEmptyEdit();
            }
        });
        albumModel = (AlbumModel) getIntent().getSerializableExtra("AlbumModel");
        //修改的颜色
        etTitle.setTextColor(getResources().getColor(R.color.black));
        etContent.setTextColor(getResources().getColor(R.color.black));
        showEditData(albumModel);
        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!albumModel.getTitle().equals(s.toString())){
                    LoginUtils.setBtnVisibility(s,bIvRight);
                }else{
                    bIvRight.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LoginUtils.setBtnVisibility(s,bIvRight,etTitle);
            }

            @Override
            public void afterTextChanged(Editable s) {}
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
        if (LoginUtils.isEmptyEdit(etTitle)) {//, etContent
            showToast("请输入正确的内容");
            return;
        }
        albumModel.setTitle(etTitle.getText().toString());
        albumModel.setContent(etContent.getText().toString());
        presenter.logUpdate(albumModel);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            isEmptyEdit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //是否编辑
    private void isEmptyEdit() {
        String content = etContent.getText().toString().trim();
        String title = etTitle.getText().toString().trim();
        if (!albumModel.getTitle().equals(title)||!albumModel.getContent().equals(content)) {
            BottomDialogUtils.getBottomExitEditDialog(bContext);
        } else {
            finish();
        }
    }

    @Override
    public void uploadSuccess() {
        setResult(MainActivity.BabyFragmentEditResultCode);
        finish();
    }
}
