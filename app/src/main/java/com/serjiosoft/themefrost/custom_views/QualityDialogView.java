package com.serjiosoft.themefrost.custom_views;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.themefrost_api.models_api.Files;

/**
 * Created by autoexec on 01.03.2017.
 */

public class QualityDialogView extends FrameLayout {

    protected TextView tv1080;
    protected TextView tv240;
    protected TextView tv360;
    protected TextView tv480;
    protected TextView tv720;


    private Files files = null;
    private IQualityInterface mIQualityInterface;


    public interface IQualityInterface {
        void onQualityClick(String str, int i);
    }


    public QualityDialogView(@NonNull Context context) {
        super(context);

        inflate(context, R.layout.view_quality, this);

        tv240 = (TextView) findViewById(R.id.tv240_VQ);
        tv720 = (TextView) findViewById(R.id.tv720_VQ);
        tv360 = (TextView) findViewById(R.id.tv360_VQ);
        tv1080 = (TextView) findViewById(R.id.tv1080_VQ);
        tv480 = (TextView) findViewById(R.id.tv480_VQ);

        setListener();
    }

    private void setListener() {
        if (tv240 != null){
            tv240.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    QualityDialogView.this.clickQuality(view);
                }
            });
        }
        if (tv360 != null) {
            tv360.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    QualityDialogView.this.clickQuality(view);
                }
            });
        }
        if (tv480 != null) {
            tv480.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    QualityDialogView.this.clickQuality(view);
                }
            });
        }
        if (tv720 != null) {
            tv720.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    QualityDialogView.this.clickQuality(view);
                }
            });
        }
        if (tv1080 != null) {
            tv1080.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    QualityDialogView.this.clickQuality(view);
                }
            });
        }
    }

    private void clickQuality(View viewClicked){

        switch (viewClicked.getId()){

            case R.id.tv240_VQ:
                if (files.mp4_240 != null) {
                    mIQualityInterface.onQualityClick(files.mp4_240, 240);
                    return;
                }
                return;

            case R.id.tv360_VQ:
                if (files.mp4_360 != null) {
                    mIQualityInterface.onQualityClick(files.mp4_360, 360);
                    return;
                }
                return;

            case R.id.tv480_VQ:
                if (files.mp4_480 != null) {
                    mIQualityInterface.onQualityClick(files.mp4_480, 480);
                    return;
                }
                return;

            case R.id.tv720_VQ:
                if (files.mp4_720 != null) {
                    mIQualityInterface.onQualityClick(files.mp4_720, 720);
                    return;
                }
                return;

            case R.id.tv1080_VQ:
                if (files.mp4_1080 != null) {
                    mIQualityInterface.onQualityClick(files.mp4_1080, 1080);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public QualityDialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public QualityDialogView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setFiles(Files files, IQualityInterface iQualityInterface) {
        this.files = files;
        this.mIQualityInterface = iQualityInterface;
        initStates();
    }

    private void initStates() {
        if (files != null) {
            if (files.mp4_240 == null) {
                tv240.setBackgroundColor(getResources().getColor(R.color.theme_all_gray_color));
                tv240.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            }
            if (files.mp4_360 == null) {
                tv360.setBackgroundColor(getResources().getColor(R.color.theme_all_gray_color));
                tv360.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            }
            if (files.mp4_480 == null) {
                tv480.setBackgroundColor(getResources().getColor(R.color.theme_all_gray_color));
                tv480.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            }
            if (files.mp4_720 == null) {
                tv720.setBackgroundColor(getResources().getColor(R.color.theme_all_gray_color));
                tv720.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            }
            if (files.mp4_1080 == null) {
                tv1080.setBackgroundColor(getResources().getColor(R.color.theme_all_gray_color));
                tv1080.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            }
        }
    }

}
