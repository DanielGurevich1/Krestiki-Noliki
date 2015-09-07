package com.learn.demin.krestiki_noliki;

/**
 * Created by AleDemin on 14.08.2015.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class OView extends View {

    Paint p = new Paint();

    public OView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onDraw(Canvas canvas) {
        p.setColor(getResources().getColor(R.color.o_color));
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(MainActivity.size.y*getFloatValueFromResourses(R.dimen.settings_x_width));
        float size=MainActivity.size.y*getFloatValueFromResourses(R.dimen.settings_x_size)/2;
        float marginX=(MainActivity.size.x/(4+getResources().getInteger(R.integer.choose_symbol_weight))-size)/2;
        float marginY=(MainActivity.size.y/8)-size;

        canvas.drawCircle(marginX,marginY,size,p);

    }

    public float getFloatValueFromResourses(int res) {
        float value;
        TypedValue outValue = new TypedValue();
        getResources().getValue(res, outValue, true);
        return value = outValue.getFloat();
    }
}

