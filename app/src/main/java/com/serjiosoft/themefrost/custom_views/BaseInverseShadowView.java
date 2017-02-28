package com.serjiosoft.themefrost.custom_views;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by autoexec on 24.02.2017.
 */

public class BaseInverseShadowView extends BaseShadowView {

    public BaseInverseShadowView(Context context) {
        super(context);
    }

    public BaseInverseShadowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseInverseShadowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected Path getDrawPath() {
        Path path = new Path();
        path.moveTo(0.0f, (float) getHeight());
        path.lineTo((float) getWidth(), (float) getHeight());
        path.lineTo((float) getWidth(), this.mHeightAreaAngelable);
        path.lineTo((((float) getWidth()) * ((float) this.mPercentageBasePoint)) / 100.0f, 0.0f);
        path.lineTo(0.0f, this.mHeightAreaAngelable);
        path.close();
        this.mAreaPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, (float) getHeight(), this.mAreaColor, this.mAreaColorDarkness, Shader.TileMode.MIRROR));
        return path;
    }

}
