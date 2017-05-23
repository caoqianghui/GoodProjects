package com.go.cqh.goodprojects.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import com.go.cqh.goodprojects.R;

/**
 * Created by caoqianghui on 2017/3/14.
 */

public class ImTextView extends TextView {
    private static  String TAG = ImTextView.class.getSimpleName();
    private ImBubbleDrawable mBubbleDrawable;

    private float mArrowWidth;
    private float mArrowHeight;
    private float mRadius;
    private float mArrowOffset;
    private int mBubbleColor;
    private ImBubbleDrawable.ArrowDirection mArrowDirection;
    private boolean mArrowCenter;

    public ImTextView(Context context) {
        this(context,null);
    }

    public ImTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ImTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.JooyerBubble);
        mArrowWidth = array.getDimension(R.styleable.JooyerBubble_jooyer_bubble_arrow_width,
                ImBubbleDrawable.Builder.DEFAULT_ARROW_WIDTH);
        mArrowHeight = array.getDimension(R.styleable.JooyerBubble_jooyer_bubble_arrow_width,
                ImBubbleDrawable.Builder.DEFAULT_ARROW_HEIGHT);
        mArrowOffset = array.getDimension(R.styleable.JooyerBubble_jooyer_bubble_arrow_width,
                ImBubbleDrawable.Builder.DEFAULT_ARROW_OFFSET);
        mRadius = array.getDimension(R.styleable.JooyerBubble_jooyer_bubble_arrow_width,
                ImBubbleDrawable.Builder.DEFAULT_RADIUS);
        mBubbleColor = array.getColor(R.styleable.JooyerBubble_jooyer_bubble_arrow_color,
                ImBubbleDrawable.Builder.DEFAULT_BUBBLE_COLOR);
        mArrowCenter = array.getBoolean(R.styleable.JooyerBubble_jooyer_bubble_arrow_center,
                false);
        int direction = array.getInt(R.styleable.JooyerBubble_jooyer_bubble_arrow_direction,
                0);
        mArrowDirection = ImBubbleDrawable.ArrowDirection.mapIntToValue(direction);
        array.recycle();
        setPadding();
    }

    /**
     *  由于箭头的问题,当有 padding 时我们需要再加三角箭头的尺寸
     */
    private void setPadding() {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();
        switch (mArrowDirection){
            case LEFT:
                left += mArrowWidth;
                break;
            case TOP:
                top += mArrowHeight;
                break;
            case RIGHT:
                right += mArrowWidth;
                break;
            case BOTTOM:
                bottom += mArrowHeight;
                break;

        }
        setPadding(left,top,right,bottom);
    }

    /**
     *  当大小发生改变时,我们需要重绘
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            reset(w, h);
        }
    }
    /**
     *  当位置发生改变时,我们需要重绘
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        reset(getWidth(),getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制背景
        if (null != mBubbleDrawable){
            mBubbleDrawable.draw(canvas);
        }
        super.onDraw(canvas);
    }

    private void reset(int width, int height) {
        reset(0,0,width,height);
    }

    private void reset(int left, int top, int right, int bottom) {
        RectF rectF = new RectF(left,top,right,bottom);
        mBubbleDrawable = new ImBubbleDrawable.Builder()
                .rect(rectF)
                .arrowWidth(mArrowWidth)
                .arrowHeight(mArrowHeight)
                .radius(mRadius)
                .arrowOffset(mArrowOffset)
                .arrowDirection(mArrowDirection)
                .arrowCenter(mArrowCenter)
                .bubbleColor(mBubbleColor)
                .build();

    }
}
