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
import android.util.Log;
import android.view.View;

import fishy.support.util.DeviceInfoUtil;

/**
 * Created by DN2017030300 on 2018/5/14.
 */

public class CountDownProgressBar extends View {
    final String TAG = this.getClass().getSimpleName();

    Paint mBitmapPaint;
    Paint mRingLinePaint;
    Paint mProgressLinePaint;
    Paint mPointPaint;
    Paint mIndicatorInside;
    Paint mIndicatorOutside;
    Paint mTextPaint;

    //参数配置
    int countTextColor;
    int countTextSize;
    int ringLineColor;
    int progressLineWidth;
    int progressLineColor;
    int indicatorColorIn;
    int indicatorColorOut;
    int indicatorRadius;
    int indicatorRingWidth;

    //bitmap，待绘制的rect等资源
    Bitmap bubbleBitmap;
    RectF rectBubble;
    RectF rectRingBar;
    RectF rectPoint;
    //总时长,second
    int totalTime;
    int remainSecond;
    //是否显示计时
    boolean countFlag;
    //宽，高
    int mWidth;
    int mHeight;
    //气泡宽,高
    int mBubbleWidth;
    int mBubbleHeight;
    //进度环宽，高
    int mRingWidth;
    int mRingHeight;

    //基本通用配置
    float scaleFloat;
    Context context;
    int progress;

    final String textStart = "开始";
    final String secondUnit = "S";

    public CountDownProgressBar(Context context) {
        this(context, null);
    }

    public CountDownProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CountDownProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;

        scaleFloat = DeviceInfoUtil.getDeviceDpiScale(this.context);

        //初始化默认颜色,字号
//        int countTextSize;
        countTextColor = Color.DKGRAY;
        countTextSize = (int) (22 * scaleFloat);
        ringLineColor = Color.DKGRAY;
        progressLineColor = Color.DKGRAY;
        progressLineWidth = (int) (2 * scaleFloat);
        indicatorColorIn = Color.DKGRAY;
        indicatorColorOut = Color.DKGRAY;

        indicatorRadius = (int) (8 * scaleFloat);
        indicatorRingWidth = (int) (3 * scaleFloat);

        //默认60s
        totalTime = 60;
        countFlag = false;
    }

    public void setRemainSecond(int second) {
        if (countFlag) {
            this.remainSecond = second;
            setProgress(second * 100 / totalTime);
        }
    }

    private void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = null;
        try {
//            a = context.obtainStyledAttributes(attrs, R.styleable.ColorIndicatorProgressBar);

//            roundColor = a.getColor(R.styleable.StatusRingView_ringColor, getResources().getColor(android.R.color.darker_gray));
//            roundProgressColor = a.getColor(R.styleable.StatusRingView_ringProgressColor, getResources().getColor(android.R.color.holo_red_dark));
//            countTextColor = a.getColor(R.styleable.StatusRingView_ringTextColor, getResources().getColor(android.R.color.holo_blue_dark));
//            countTextSize = a.getDimension(R.styleable.StatusRingView_ringTextSize, 22f);
//            roundWidth = a.getDimension(R.styleable.StatusRingView_ringWidth, 10f);
//            sweepAngle = a.getFloat(R.styleable.StatusRingView_sweepAngle, 270f);

        } finally {
            // 注意，别忘了 recycle
            if (a != null) {
                a.recycle();
            }
        }
    }

    public int getCountTextColor() {
        return countTextColor;
    }

    /**
     * 设置倒计时字体颜色
     *
     * @param countTextColor
     */
    public void setCountTextColor(int countTextColor) {
        this.countTextColor = countTextColor;
    }

    public int getCountTextSize() {
        return px2dp(countTextSize);
    }

    /**
     * 设置倒计时字体字号
     *
     * @param countTextSize
     */
    public void setCountTextSize(int countTextSize) {
        this.countTextSize = dp2px(countTextSize);
    }

    public int getRingLineColor() {
        return ringLineColor;
    }

    /**
     * 设置环状线颜色(背景)
     *
     * @param ringLineColor
     */
    public void setRingLineColor(int ringLineColor) {
        this.ringLineColor = ringLineColor;
    }

    public int getProgressLineWidth() {
        return px2dp(progressLineWidth);
    }

    /**
     * 设置环状线宽度
     *
     * @param progressLineWidth
     */
    public void setProgressLineWidth(int progressLineWidth) {
        this.progressLineWidth = dp2px(progressLineWidth);
    }

    public int getProgressLineColor() {
        return progressLineColor;
    }

    /**
     * 设置环状线进度条颜色
     *
     * @param progressLineColor
     */
    public void setProgressLineColor(int progressLineColor) {
        this.progressLineColor = progressLineColor;
    }

    public int getIndicatorColorIn() {
        return indicatorColorIn;
    }

    public void startCount() {
        countFlag = true;
    }

    public void stopCount() {
        countFlag = false;
    }

    /**
     * 设置小环指示器内部颜色
     *
     * @param indicatorColorIn
     */
    public void setIndicatorColorIn(int indicatorColorIn) {
        this.indicatorColorIn = indicatorColorIn;
    }

    public int getIndicatorColorOut() {
        return indicatorColorOut;
    }

    /**
     * 设置小环指示器外部颜色
     *
     * @param indicatorColorOut
     */
    public void setIndicatorColorOut(int indicatorColorOut) {
        this.indicatorColorOut = indicatorColorOut;
    }

    public int getIndicatorRadius() {
        return px2dp(indicatorRadius);
    }

    /**
     * 设置小环指示器总的半径
     *
     * @param indicatorRadius
     */
    public void setIndicatorRadius(int indicatorRadius) {
        this.indicatorRadius = dp2px(indicatorRadius);
    }

    public int getIndicatorRingWidth() {
        return px2dp(indicatorRingWidth);
    }

    /**
     * 设置小环指示器内部的半径
     *
     * @param indicatorRingWidth
     */
    public void setIndicatorRingWidth(int indicatorRingWidth) {
        this.indicatorRingWidth = dp2px(indicatorRingWidth);
    }

    /**
     * 初始化等待绘制的资源初始化
     */
    private void setupOnDrawRes() {
        bubbleBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_mask_btn_bkg);

        rectBubble = new RectF((mWidth - mBubbleWidth) / 2, (mHeight - mBubbleHeight) / 2,
                (mWidth + mBubbleWidth) / 2, (mHeight + mBubbleHeight) / 2);
        rectRingBar = new RectF(mWidth / 2 - mRingWidth / 2, mHeight / 2 - mRingHeight / 2,
                mWidth / 2 + mRingWidth / 2, mHeight / 2 + mRingHeight / 2);

        rectPoint = new RectF(mWidth / 2 - progressLineWidth, (mHeight - mRingHeight) / 2 - progressLineWidth,
                mWidth / 2 + progressLineWidth, (mHeight - mRingHeight) / 2 + progressLineWidth);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mBubbleWidth = (int) (mWidth * 0.8f);
        mBubbleHeight = (int) (mHeight * 0.8f);

        mRingWidth = (int) (mWidth * 0.9f);
        mRingHeight = (int) (mHeight * 0.9f);

        initPaint();
        initTextPaint();

        setupOnDrawRes();

    }

    private void initPaint() {
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        //开启抗锯齿
        mBitmapPaint.setFilterBitmap(true);

        //完整的进度条底色
        mRingLinePaint = new Paint();
        mRingLinePaint.setAntiAlias(true);
        mRingLinePaint.setColor(ringLineColor);
        mRingLinePaint.setStyle(Paint.Style.STROKE);
        mRingLinePaint.setStrokeWidth(progressLineWidth);

        //进度条弧线
        mProgressLinePaint = new Paint();
        mProgressLinePaint.setAntiAlias(true);
        mProgressLinePaint.setColor(progressLineColor);
        mProgressLinePaint.setStyle(Paint.Style.STROKE);
        mProgressLinePaint.setStrokeWidth(progressLineWidth);

        //起始位置的小白点
        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setColor(progressLineColor);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setStrokeWidth(progressLineWidth);

        //进度条的圆环的内部
        mIndicatorInside = new Paint();
        mIndicatorInside.setAntiAlias(true);
        mIndicatorInside.setColor(indicatorColorIn);

        //进度条的圆环的外环
        mIndicatorOutside = new Paint();
        mIndicatorOutside.setAntiAlias(true);
        mIndicatorOutside.setColor(indicatorColorOut);
    }

    private void initTextPaint() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(countTextColor);
        mTextPaint.setTextSize(countTextSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画气泡背景
        canvas.drawBitmap(bubbleBitmap, null, rectBubble, mBitmapPaint);
        //画进度条背景环
        canvas.drawOval(rectRingBar, mRingLinePaint);
        //画点
        canvas.drawOval(rectPoint, mPointPaint);
        //画进度弧线
        int angle = (int) (360 * progress * 1f / 100);
        canvas.drawArc(rectRingBar, -90, angle, false, mProgressLinePaint);
        //画指示器,内外环
        int xOff = (int) (Math.sin(2 * Math.PI * angle / 360) * mRingWidth / 2);
        int yOff = (int) (Math.cos(2 * Math.PI * angle / 360) * mRingWidth / 2);
        int xEnd = mWidth / 2 + xOff;
        int yEnd = mHeight / 2 - yOff;
        RectF rectIndicatorOut = new RectF(xEnd - indicatorRadius, yEnd - indicatorRadius,
                xEnd + indicatorRadius, yEnd + indicatorRadius);
        int inRadius = indicatorRadius - indicatorRingWidth;
        RectF rectIndicatorIn = new RectF(xEnd - inRadius, yEnd - inRadius,
                xEnd + inRadius, yEnd + inRadius);
        canvas.drawOval(rectIndicatorOut, mIndicatorOutside);
        canvas.drawOval(rectIndicatorIn, mIndicatorInside);
        //画倒计时文字
        String waitDrawText;
        if (countFlag) {
            waitDrawText = remainSecond + secondUnit;
        } else {
            waitDrawText = textStart;
        }
        Log.i(TAG,waitDrawText);
        Rect rectCountText = new Rect();
        mTextPaint.getTextBounds(waitDrawText, 0, waitDrawText.length(), rectCountText);
        canvas.drawText(waitDrawText, mWidth / 2 - rectCountText.width() / 2,
                mHeight / 2 + rectCountText.height() / 2, mTextPaint);
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
}
