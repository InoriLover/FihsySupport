package fishy.support.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fishy.support.util.DeviceInfoUtil;

/**
 * Created by DN2017030300 on 2018/9/11.
 */

public class VesselView extends View {
    //通用属性
    final String TAG = this.getClass().getSimpleName();
    float scaleFloat;
    Context context;
    List<VesselDes> vesselDes;
    //画笔
    Paint bmpPaint;
    Paint mainTvPaint;
    Paint secondTvPaint;
    //相关的rect
    Rect rectMainTv;
    Rect rectSecondTv;
    //背景图片rect
    RectF bkgRect;
    //指示器rect
    RectF indicatorRect;
    //相关的参数
    //文本颜色
    int mainTvColor;
    int secondTvColor;
    //文本字号
    float mainTvSize;
    float secondTvSize;
    //指示器宽度
    float indicatorWidth;

    //刻度显示位置的对分线的坐标数组
    float[] centerPercentArray = new float[]{0.0975f, 0.2428f, 0.3881f, 0.5335f,
            0.6788f, 0.8241f};
    //宽，高
    int mWidth;
    int mHeight;
    //背景图
    Bitmap bmpBk;
    //指示器
    Bitmap bmpIndicator;
    //等级，0~5
    int level;

    public VesselView(Context context) {
        this(context, null);
    }

    public VesselView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public VesselView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.scaleFloat = DeviceInfoUtil.getDeviceDpiScale(context);
        initAttrs(context, attrs, defStyleAttr);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.VesselView);

            mainTvColor = a.getColor(R.styleable.VesselView_mainTvColor, Color.BLACK);
            secondTvColor = a.getColor(R.styleable.VesselView_secondTvColor, Color.GRAY);
            mainTvSize = a.getDimension(R.styleable.VesselView_mainTvSize, dp2px(16));
            secondTvSize = a.getDimension(R.styleable.VesselView_secondTvSize, dp2px(16));
            indicatorWidth = a.getDimension(R.styleable.VesselView_indicatorWidth, dp2px(16));

        } finally {
            // 注意，别忘了 recycle
            if (a != null) {
                a.recycle();
            }
        }
    }

    private void init() {

        initPaint();
        initRect();
        bmpBk = BitmapFactory.decodeResource(getResources(), R.mipmap.vessel_bkg);
        bmpIndicator = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_arrow);
        level = 0;
    }


    private void initPaint() {
        bmpPaint = new Paint();
        //bitmap画笔
        bmpPaint.setAntiAlias(true);
        //bitmap抗锯齿
        bmpPaint.setFilterBitmap(true);

        mainTvPaint = new Paint();
        mainTvPaint.setColor(mainTvColor);
        mainTvPaint.setTextSize(mainTvSize);
        mainTvPaint.setAntiAlias(true);

        secondTvPaint = new Paint();
        secondTvPaint.setColor(secondTvColor);
        secondTvPaint.setTextSize(secondTvSize);
        secondTvPaint.setAntiAlias(true);
    }

    private void initRect() {
        rectMainTv = new Rect();
        rectSecondTv = new Rect();
        bkgRect = new RectF();
        indicatorRect = new RectF();
    }

    public void setLevel(int level) {
        this.level = level;
        if (level < 0) {
            level = 0;
        }
        if (level > 5) {
            level = 5;
        }
        postInvalidate();
    }

    public int getLevel() {
        return level;
    }

    public void setVesselDes(List<VesselDes> vesselDes) {
        this.vesselDes = vesselDes;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float defaultMarginTop = dp2px(2);
        float scale = bmpBk.getWidth() * 1.0f / bmpBk.getHeight();
        float realHeight = mWidth / scale;
        bkgRect.set(0, (mHeight - realHeight) / 2, mWidth, (mHeight + realHeight) / 2);
        canvas.drawBitmap(bmpBk, null, bkgRect, bmpPaint);

        Paint tmpPaint = new Paint();
        tmpPaint.setStyle(Paint.Style.STROKE);
        tmpPaint.setStrokeWidth(2);
        tmpPaint.setColor(Color.BLUE);
        tmpPaint.setAntiAlias(true);

        if (level >= 0 && level <= 5) {
            float centerX = centerPercentArray[level] * mWidth;
            float bmpHeight = bmpIndicator.getHeight() * indicatorWidth / bmpIndicator.getWidth();
            indicatorRect.set(centerX - indicatorWidth / 2, (mHeight - realHeight) / 2 - bmpHeight,
                    centerX + indicatorWidth / 2, (mHeight - realHeight) / 2);
            canvas.drawBitmap(bmpIndicator, null, indicatorRect, bmpPaint);

            String secondDes = "(" + vesselDes.get(level).getDetail() + ")";
            secondTvPaint.getTextBounds(secondDes, 0, secondDes.length(), rectSecondTv);
            int offseSecond = -rectSecondTv.bottom;
            canvas.drawText(secondDes, centerX - rectSecondTv.width() / 2,
                    (mHeight - realHeight) / 2 - bmpHeight + offseSecond - defaultMarginTop, secondTvPaint);

            String mainDes = vesselDes.get(level).getType();
            mainTvPaint.getTextBounds(mainDes, 0, mainDes.length(), rectMainTv);
            int offsetMain = -rectMainTv.bottom;
            canvas.drawText(mainDes, centerX - rectMainTv.width() / 2,
                    (mHeight - realHeight) / 2 - bmpHeight - rectSecondTv.height() + offsetMain - defaultMarginTop,
                    mainTvPaint);

        }
    }

    /**
     * 简单的pixel转dp或sp
     *
     * @return
     */
    private int px2dp(int pixel) {
        int dp = (int) (pixel / scaleFloat);
        return dp;
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

    public static class VesselDes {
        String type;
        String detail;

        public VesselDes(String type, String detail) {
            this.type = type;
            this.detail = detail;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }
}
