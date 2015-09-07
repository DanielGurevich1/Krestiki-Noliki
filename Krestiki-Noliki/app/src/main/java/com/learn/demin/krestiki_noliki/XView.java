package com.learn.demin.krestiki_noliki;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by AleDemin on 14.08.2015.
 */
public class XView extends View {

    Paint p = new Paint();

    public XView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onDraw(Canvas canvas) {
        p.setColor(getResources().getColor(R.color.x_color));
        p.setStrokeWidth(MainActivity.size.y*getFloatValueFromResourses(R.dimen.settings_x_width));
        float size=MainActivity.size.y*getFloatValueFromResourses(R.dimen.settings_x_size);
        float marginX=(MainActivity.size.x/(4+getResources().getInteger(R.integer.choose_symbol_weight))-size)/2;
        float marginY=(MainActivity.size.y/8)-size;


        canvas.drawLine(marginX,marginY,marginX+size,marginY+size,p);
        canvas.drawLine(marginX, marginY+size, marginX+size, marginY, p);
    }

    public float getFloatValueFromResourses(int res) {
        TypedValue outValue = new TypedValue();
        getResources().getValue(res, outValue, true);
        return outValue.getFloat();
    }
}