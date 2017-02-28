package com.serjiosoft.themefrost.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.serjiosoft.themefrost.R;

/**
 * Created by autoexec on 22.02.2017.
 */

public class BaseShadowView extends View {

    protected float DEFAULT_AREA_ANGELABLE_VALUE;
    protected int DEFAULT_COLOR_AREA_SHADOW = 0;
    protected int DEFAULT_PERCENTAGE_BASE_POINT;
    protected int mAreaColor;
    protected int mAreaColorDarkness;
    protected Paint mAreaPaint;
    protected float mHeightAreaAngelable;
    protected int mPercentageBasePoint;

    public BaseShadowView(Context context) {
        super(context);
        beforeConfigs(null, 0);
    }

    public BaseShadowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        beforeConfigs(attrs, 0);
    }

    public BaseShadowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        beforeConfigs(attrs, defStyleAttr);
    }

    private void beforeConfigs(AttributeSet attrs, int defStyle){
        DEFAULT_PERCENTAGE_BASE_POINT = getResources().getInteger(R.integer.defaultPercentageBasePoint);
        DEFAULT_AREA_ANGELABLE_VALUE = getResources().getDimension(R.dimen.defalutAreableHeight);
        if (attrs == null) {
            mPercentageBasePoint = DEFAULT_PERCENTAGE_BASE_POINT;
            mHeightAreaAngelable = DEFAULT_AREA_ANGELABLE_VALUE;
            mAreaColor = DEFAULT_COLOR_AREA_SHADOW;
            mAreaColorDarkness = DEFAULT_COLOR_AREA_SHADOW;
        }else {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BaseShadowView, defStyle, 0);
            this.mPercentageBasePoint = a.getInteger(R.styleable.BaseShadowView_percentageBasePoint, this.DEFAULT_PERCENTAGE_BASE_POINT);
            this.mHeightAreaAngelable = a.getDimension(R.styleable.BaseShadowView_heightAreaAngleable, this.DEFAULT_AREA_ANGELABLE_VALUE);
            this.mAreaColor = a.getColor(R.styleable.BaseShadowView_colorAreaShadow, this.DEFAULT_COLOR_AREA_SHADOW);
            this.mAreaColorDarkness = a.getColor(R.styleable.BaseShadowView_colorAreaShadowDarkness, this.DEFAULT_COLOR_AREA_SHADOW);
            a.recycle();
        }
        preparePaint();
    }

    private void preparePaint() {
        mAreaPaint = new Paint(1);
        mAreaPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(getDrawPath(), this.mAreaPaint);
    }

    protected Path getDrawPath() {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.lineTo((float) getWidth(), 0.0f);
        path.lineTo((float) getWidth(), ((float) getHeight()) - this.mHeightAreaAngelable);
        path.lineTo((((float) getWidth()) * ((float) this.mPercentageBasePoint)) / 100.0f, (float) getHeight());
        path.lineTo(0.0f, ((float) getHeight()) - this.mHeightAreaAngelable);
        path.close();
        this.mAreaPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, (float) getHeight(), this.mAreaColor, this.mAreaColorDarkness, Shader.TileMode.MIRROR));
        return path;
    }
}
