package fishy.support.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fishy.support.util.DeviceInfoUtil;

/**
 * Created by DN2017030300 on 2018/10/9.
 */

public class SimpleAreaBar extends View {
    //通用属性
    final String TAG = this.getClass().getSimpleName();
    float scaleFloat;
    Context context;
    List<AreaPiece> areas;

    float hintTextSize;
    int hintTextColor;
    float indicatorWidth;
    float barHeight;
    float startMargin;

    int mWidth;
    int mHeight;
    int barWidth;

    Paint mPaint;
    Paint tvPaint;
    Paint bmpPaint;
    //指示器
    Bitmap bmpIndicator;
    Rect rectIndicator;

    float min;
    float max;
    float value;


    public SimpleAreaBar(Context context) {
        this(context, null);
    }

    public SimpleAreaBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SimpleAreaBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scaleFloat = DeviceInfoUtil.getDeviceDpiScale(context);
        this.context = context;
        initAttrs(context, attrs, defStyleAttr);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = null;
        try {

            a = context.obtainStyledAttributes(attrs, R.styleable.SimpleAreaBar);
            hintTextSize = a.getDimension(R.styleable.SimpleAreaBar_hintTextSize, dp2px(16));
            hintTextColor = a.getColor(R.styleable.SimpleAreaBar_hintTextColor, Color.DKGRAY);
            indicatorWidth = a.getDimension(R.styleable.SimpleAreaBar_indicatorWidth, dp2px(20));
            barHeight = a.getDimension(R.styleable.SimpleAreaBar_barHeight, dp2px(16));
            startMargin = a.getDimension(R.styleable.SimpleAreaBar_startMargin, dp2px(12));
        } finally {
            // 注意，别忘了 recycle
            if (a != null) {
                a.recycle();
            }
        }
    }

    private void init() {
        areas = new ArrayList<>();
        mPaint = createStandPaint();
        tvPaint = createStandTextPaint();
        bmpPaint = createBmpPaint();
        bmpIndicator = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_arrow);

        min = 0;
        max = 100;
    }

    public void setValue(float value) {
        this.value = value;
        postInvalidate();
    }

    public void setAreas(List<AreaPiece> areas) {
        this.areas = areas;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        barWidth = (int) (mWidth - 2 * startMargin);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float lastEndX = startMargin;
        float defaultMarginTop = dp2px(4);
        for (int i = 0; i < areas.size(); i++) {
            AreaPiece piece = areas.get(i);
            mPaint.setColor(piece.color);
            float areaWidth = barWidth * piece.percent;
            canvas.drawLine(lastEndX, mHeight / 2, lastEndX + areaWidth, mHeight / 2, mPaint);
            float xCenter = lastEndX + areaWidth / 2;
            Rect rect = new Rect();
            Rect rect2 = new Rect();
            tvPaint.getTextBounds(piece.hintText, 0, piece.hintText.length(), rect);
            tvPaint.getTextBounds(piece.detailText, 0, piece.detailText.length(), rect2);
            canvas.drawText(piece.hintText, xCenter - rect.width() / 2,
                    mHeight / 2 + rect.height() + defaultMarginTop, tvPaint);
            canvas.drawText(piece.detailText, xCenter - rect2.width() / 2,
                    mHeight / 2 + rect.height() + rect2.height() + defaultMarginTop, tvPaint);
            lastEndX += areaWidth;
        }
        float scale = bmpIndicator.getWidth() * 1.0f / bmpIndicator.getHeight();
        float realHeight = mWidth / scale;
        float percent = (value - min) * 1.0f / (max - min);
        float x = percent * barWidth + startMargin;
        rectIndicator.set((int) (x - indicatorWidth / 2), (int) (mHeight / 2 - barHeight / 2 - realHeight),
                (int) (x + indicatorWidth / 2), (int) (mHeight / 2 - barHeight / 2));
        canvas.drawBitmap(bmpIndicator, null, rectIndicator, bmpPaint);
    }

    /**
     * 简单的dp或sp转pixel
     *
     * @return
     */
    private int dp2px(int dp) {
        int pixel = (int) (dp * scaleFloat);
        return pixel;
    }


    /**
     * 配置画笔
     */
    Paint createStandPaint() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(barHeight);
        return paint;
    }

    Paint createStandTextPaint() {
        Paint paint = new Paint();
        paint.setTextSize(hintTextSize);
        paint.setAntiAlias(true);
        paint.setColor(hintTextColor);
        return paint;
    }

    Paint createBmpPaint() {
        Paint paint = new Paint();
        //bitmap画笔
        paint.setAntiAlias(true);
        //bitmap抗锯齿
        paint.setFilterBitmap(true);
        return paint;
    }

    public static class AreaPiece {
        int color;
        String hintText;
        String detailText;
        float percent;

        public AreaPiece(int color, String hintText, String detailText, float percent) {
            this.color = color;
            this.hintText = hintText;
            this.detailText = "(" + detailText + ")";
            this.percent = percent;
        }

        public void setColor(int color) {
            this.color = color;
        }


        public void setHintText(String hintText) {
            this.hintText = hintText;
        }

        public void setPercent(float percent) {
            this.percent = percent;
        }
    }
}
