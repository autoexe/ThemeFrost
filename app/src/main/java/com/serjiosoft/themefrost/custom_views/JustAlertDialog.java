package com.serjiosoft.themefrost.custom_views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.serjiosoft.themefrost.R;

/**
 * Created by autoexec on 01.03.2017.
 */

public class JustAlertDialog extends Dialog {

    private IResultListener mIResultListener;
    private TextView mTitleView;

    public interface IResultListener {
        void onCancel();

        void onSubmit();
    }

    public JustAlertDialog(Context context, IResultListener iResultListener) {
        super(context);
        this.mIResultListener = iResultListener;
        init();
    }

    public void setView(View viewConteiner) {
        ((FrameLayout) findViewById(R.id.flContainer_JAD)).addView(viewConteiner);
    }

    public void title(String title) {
        if (this.mTitleView != null) {
            this.mTitleView.setText(title);
        }
    }

    private void init() {
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getWindow().requestFeature(1);
        setContentView(R.layout.just_alert_dialog);
        initViews();
    }

    private void initViews() {
        this.mTitleView = (TextView) findViewById(R.id.tvTitle_JAD);
        findViewById(R.id.ivOk_JAD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (JustAlertDialog.this.mIResultListener != null) {
                    JustAlertDialog.this.mIResultListener.onSubmit();
                }
                JustAlertDialog.this.dismiss();
            }
    });
        findViewById(R.id.ivCancel_JAD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (JustAlertDialog.this.mIResultListener != null) {
                    JustAlertDialog.this.mIResultListener.onCancel();
                }
                JustAlertDialog.this.dismiss();
            }
        });

    }
}
