package com.wenjing.yinfutong.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by ${luoyingtao} on 2018\4\27 0027.
 */

public class DotImageView extends android.support.v7.widget.AppCompatImageView {

    private boolean isShown = false;

    //红点的半径
    private float radius;

    //右边和上边内边距
    private int paddingRight;
    private int paddingTop;

    public DotImageView(Context context) {
        super(context);
    }

    public DotImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DotImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isShown) return;

        radius = getWidth() / 6 ;

        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xffff4444);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(getWidth() - radius - paddingRight / 2 , radius + paddingTop / 2 , radius ,paint);
    }

    public void showDot(boolean isShown){
        this.isShown = isShown;
        postInvalidate();
    }

}
