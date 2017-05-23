package com.go.cqh.goodprojects.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by caoqianghui on 2017/3/14.
 */

public class ImBubbleDrawable extends Drawable {
    private static final String TAG = ImBubbleDrawable.class.getSimpleName();

    /**
     * 保存坐标(自定义控件的大小)
     */
    private RectF mRect;

    /**
     * 气泡的路径
     */
    private Path mPath = new Path();


    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 箭头宽度
     */
    private float mArrowWidth;

    /**
     * 箭头宽度
     */
    private float mArrowHeight;

    /**
     * 圆弧半径
     */
    private float mRadius;
    /**
     * 箭头所在位置偏移量
     */
    private float mArrowOffset;

    /**
     * 气泡背景色
     */
    private int mBubbleColor;


    /**
     * 三角箭头所在位置
     */
    private ArrowDirection mArrowDirection;


    /**
     * 箭头是否居中
     */
    private boolean mArrowCenter;

    /**
     *  重写此方法,在这里实现和 自定义控件中 onDraw 类似的功能
     */
    @Override
    public void draw(Canvas canvas) {
        mPaint.setColor(mBubbleColor);
        setUpPath(mArrowDirection, mPath);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT; //窗口透明化
    }


    private void setUpPath(ArrowDirection arrowDirection, Path path) {
        switch (arrowDirection) {
            case LEFT:
                setUpLeftPath(mRect, path);
                break;
            case TOP:
                setUpTopPath(mRect, path);
                break;
            case RIGHT:
                setUpRightPath(mRect, path);
                break;
            case BOTTOM:
                setUpBottomPath(mRect,path);
                break;
        }
    }

    /**
     * 箭头朝左
     */
    private void setUpLeftPath(RectF rect, Path path) {
        if (mArrowCenter)
            mArrowOffset = (rect.bottom - rect.top - mArrowWidth) / 2;

        path.moveTo(rect.left + mArrowWidth + mRadius, rect.top);
        path.lineTo(rect.width() - mRadius, rect.top); // 这里的rect.width() 是可以使用rect.right
        Log.i(TAG, "====setUpLeftPath========" + (rect.width() - mRadius) + "======= : " + (rect.right - mRadius));

        path.arcTo(new RectF(rect.right - mRadius, rect.top, rect.right, rect.top + mRadius), 270, 90);
        path.lineTo(rect.right, rect.bottom - mRadius);

        path.arcTo(new RectF(rect.right - mRadius, mRect.bottom - mRadius, rect.right, rect.bottom), 0, 90);
        path.lineTo(rect.left + mArrowWidth + mRadius, rect.bottom);

        path.arcTo(new RectF(rect.left + mArrowWidth, rect.bottom - mRadius, rect.left + mArrowWidth + mRadius, rect.bottom), 90, 90);
        path.lineTo(rect.left + mArrowWidth, mArrowHeight + mArrowOffset);
        path.lineTo(rect.left, mArrowOffset + mArrowHeight / 2);
        path.lineTo(rect.left + mArrowWidth, mArrowOffset);
        path.lineTo(rect.left + mArrowWidth, rect.top + mRadius);

        path.arcTo(new RectF(rect.left + mArrowWidth, mRect.top, rect.left + mArrowWidth + mRadius, rect.top + mRadius), 180, 90);
        path.close();

    }

    /**
     * 箭头朝上
     */
    private void setUpTopPath(RectF rect, Path path) {
        if (mArrowCenter)
            mArrowOffset = (rect.right - rect.left - mArrowWidth) / 2;

        path.moveTo(rect.left + Math.min(mRadius, mArrowOffset), rect.top + mArrowHeight);
        path.lineTo(rect.left + mArrowOffset, rect.top + mArrowHeight);
        path.lineTo(rect.left + mArrowOffset + mArrowWidth / 2, rect.top);
        path.lineTo(rect.left + mArrowOffset + mArrowWidth, rect.top + mArrowHeight);
        path.lineTo(rect.right - mRadius, rect.top + mArrowHeight);

        path.arcTo(new RectF(rect.right - mRadius, rect.top + mArrowHeight, rect.right, rect.top + mArrowHeight + mRadius), 270, 90);
        path.lineTo(rect.right, rect.bottom - mRadius);

        path.arcTo(new RectF(rect.right - mRadius, rect.bottom - mRadius, rect.right, rect.bottom), 0, 90);
        path.lineTo(rect.left + mRadius, rect.bottom);

        path.arcTo(new RectF(rect.left, rect.bottom - mRadius, rect.left + mRadius, rect.bottom), 90, 90);
        path.lineTo(rect.left, rect.top + mArrowHeight + mRadius);

        path.arcTo(new RectF(rect.left, rect.top + mArrowHeight, rect.left + mRadius, rect.top + mArrowHeight + mRadius), 180, 90);
        path.close();
    }

    /**
     * 箭头朝右
     */
    private void setUpRightPath(RectF rect, Path path) {
        if (mArrowCenter)
            mArrowOffset = (rect.bottom - rect.top - mArrowWidth) / 2;

        path.moveTo(rect.left + mRadius, rect.top);
        path.lineTo(rect.right - mRadius - mArrowWidth, rect.top);

        path.arcTo(new RectF(rect.right - mArrowWidth - mRadius, rect.top, rect.right - mArrowWidth, rect.top + mRadius), 270, 90);
        path.lineTo(rect.right - mArrowWidth, rect.top + mArrowOffset);
        path.lineTo(rect.right, rect.top + mArrowOffset + mArrowHeight / 2);
        path.lineTo(rect.right - mArrowWidth, rect.top + mArrowOffset + mArrowHeight);
        path.lineTo(rect.right - mArrowWidth, rect.bottom - mRadius);

        path.arcTo(new RectF(rect.right - mArrowWidth - mRadius, rect.bottom - mRadius, rect.right - mArrowWidth, rect.bottom), 0, 90);
        path.lineTo(rect.right - mArrowWidth - mRadius, rect.bottom);

        path.arcTo(new RectF(rect.left, rect.bottom - mRadius, rect.left + mRadius, rect.bottom), 90, 90);
        path.lineTo(rect.left, rect.top + mRadius);

        path.arcTo(new RectF(rect.left, rect.top, rect.left + mRadius, rect.top + mRadius), 180, 90);
        path.close();
    }

    /**
     * 箭头朝下
     */
    private void setUpBottomPath(RectF rect, Path path) {
        if (mArrowCenter)
            mArrowOffset = (rect.right - rect.left - mArrowWidth) / 2;

        path.moveTo(rect.left + mRadius, rect.top);
        path.lineTo(rect.right - mRadius, rect.top);

        path.arcTo(new RectF(rect.right - mRadius, rect.top, rect.right, rect.top + mRadius), 270, 90);
        path.lineTo(rect.right, rect.bottom - mArrowHeight - mRadius);

        path.arcTo(new RectF(rect.right - mRadius, rect.bottom - mArrowHeight - mRadius, rect.right, rect.bottom - mArrowHeight), 0, 90);
        path.lineTo(rect.left + mArrowOffset + mArrowWidth, rect.bottom - mArrowHeight);
        path.lineTo(rect.left + mArrowOffset + mArrowWidth / 2, rect.bottom);
        path.lineTo(rect.left + mArrowOffset, rect.bottom - mArrowHeight);
        path.lineTo(rect.left + mRadius, rect.bottom - mArrowHeight);

        path.arcTo(new RectF(rect.left, rect.bottom - mArrowHeight - mRadius, rect.left + mRadius, rect.bottom - mArrowHeight), 90, 90);
        path.lineTo(rect.left, rect.top + mRadius);

        path.arcTo(new RectF(rect.left, rect.top,rect.left + mRadius,rect.top + mRadius),180,90);
        path.close();
    }

    private ImBubbleDrawable(Builder builder) {
        this.mRect = builder.mRectF;
        this.mRadius = builder.mRadius;
        this.mArrowWidth = builder.mArrowWidth;
        this.mArrowHeight = builder.mArrowHeight;
        this.mArrowOffset = builder.mArrowOffset;
        this.mBubbleColor = builder.mBubbleColor;
        this.mArrowDirection = builder.mArrowDirection;
        this.mArrowCenter = builder.mArrowCenter;
    }


    /**
     * 建造者模式
     */
    public static class Builder {
        /**
         * 箭头默认宽度
         */
        public static float DEFAULT_ARROW_WIDTH = 25;
        /**
         * 箭头默认高度
         */
        public static float DEFAULT_ARROW_HEIGHT = 25;
        /**
         * 默认圆角半径
         */
        public static float DEFAULT_RADIUS = 20;
        /**
         * 默认箭头偏移量
         */
        public static float DEFAULT_ARROW_OFFSET = 50;
        /**
         * 气泡默认背景颜色
         */
        public static int DEFAULT_BUBBLE_COLOR = Color.RED;

        private RectF mRectF;
        private float mArrowWidth = DEFAULT_ARROW_WIDTH;
        private float mArrowHeight = DEFAULT_ARROW_HEIGHT;
        private float mRadius = DEFAULT_RADIUS;
        private float mArrowOffset = DEFAULT_ARROW_OFFSET;

        private int mBubbleColor = DEFAULT_BUBBLE_COLOR;
        private ArrowDirection mArrowDirection = ArrowDirection.LEFT;
        private boolean mArrowCenter;

        public Builder rect(RectF rect) {
            this.mRectF = rect;
            return this;
        }

        public Builder arrowWidth(float width) {
            this.mArrowWidth = width;
            return this;
        }

        public Builder arrowHeight(float height) {
            this.mArrowHeight = height;
            return this;
        }

        public Builder radius(float angle) {
            this.mRadius = angle; //TODO
            return this;
        }

        public Builder arrowOffset(float position) {
            this.mArrowOffset = position;
            return this;
        }

        public Builder bubbleColor(int color) {
            this.mBubbleColor = color;
            return this;
        }


        public Builder arrowDirection(ArrowDirection direction) {
            this.mArrowDirection = direction;
            return this;
        }

        public Builder arrowCenter(boolean arrowCenter) {
            this.mArrowCenter = arrowCenter;
            return this;
        }

        public ImBubbleDrawable build() {
            if (null == mRectF) {
                throw new IllegalArgumentException("BubbleDrawable RectF can not be null");
            }
            return new ImBubbleDrawable(this);
        }
    }




    /**
     * 箭头位置
     */
    public enum ArrowDirection {
        LEFT(0x00),
        TOP(0x01),
        RIGHT(0x02),
        BOTTOM(0x03);

        private int mValue;

        ArrowDirection(int value) {
            mValue = value;
        }

        private int getIntValue() {
            return mValue;
        }

        public static ArrowDirection getDefault() {
            return LEFT;
        }

        public static ArrowDirection mapIntToValue(int stateInt) {
            for (ArrowDirection value : ArrowDirection.values()) {
                if (stateInt == value.getIntValue()) {
                    return value;
                }
            }
            return getDefault();
        }
    }
}
