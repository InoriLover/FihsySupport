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
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import fishy.support.util.DeviceInfoUtil;

/**
 * Created by DN2017030300 on 2018/5/10.
 */

public class IndicatorProgressBar extends View {

    final String TAG = this.getClass().getSimpleName();

    Paint mBitmapPaint;
    Paint mAxisPaint;

    Paint mRangePaint;      //值范围的画笔
    Paint mRingValuePaint;  //外环的值的画笔
    Paint mCenterTextPaint; //中央值的画笔
    Paint mCenterSubPaint;  //中央副文字的画笔

    int defaultTextMargin;

    //各种属性
    int mRangeColor;
    int mRingValueColor;
    int mCenterTextColor;
    int mCenterSubColor;

    int mRangeSize;
    int mRingValueSize;
    int mCenterTextSize;
    int mCenterSubSize;

    //当前值
    int value;
    //最小/大值范围
    int valueMin;
    int valueMax;
    //单位文字
    String tvUnit;
    //三个指标文字
    String lowTv;
    String normalTv;
    String highTv;
    //副说明文字
    String subDesTv;
    //是否显示坐标轴辅助线
    boolean isShowAxis;
    //圆环坐标轴辅助线的偏移
    float ringAxisOff;

    //需要去绘制的bitmap,rect等
    Bitmap ringBmp;
    //圆环的rect区域
    RectF ringOval;
    //指示器移动的轨迹圆环
    RectF ringAxis;

    //初始角度
    int totalAngle;
    int startAngle;
    int indicatorStartAngle;
    //this.view的宽高
    int mWidth;
    int mHeight;
    int mRingWidth;
    int mRingHeight;
    //圆环的半径
    int radius;

    float scaleFloat;

    Context context;

    int progress;


    public IndicatorProgressBar(Context context) {
        this(context, null);
    }

    public IndicatorProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public IndicatorProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
        init(context);

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

    /**
     * 其他的初始化
     */
    private void init(Context context) {
        this.context = context;
        scaleFloat = DeviceInfoUtil.getDeviceDpiScale(this.context);

        //给默认值
        //默认灰色，14sp
        mRangeColor = Color.DKGRAY;
        mRingValueColor = Color.DKGRAY;
        mCenterTextColor = Color.DKGRAY;
        mCenterSubColor = Color.DKGRAY;

        mRangeSize = (int) (14 * scaleFloat);
        mRingValueSize = (int) (14 * scaleFloat);
        mCenterTextSize = (int) (14 * scaleFloat);
        mCenterSubSize = (int) (14 * scaleFloat);

        totalAngle = 270;
        startAngle = 135;
        indicatorStartAngle = -45;

        defaultTextMargin = 4;
        valueMin = 0;
        valueMax = 100;
        value = valueMin;
        tvUnit = "";
        isShowAxis = true;
        ringAxisOff = 0;
        lowTv = "";
        normalTv = "";
        highTv = "";
        subDesTv = "";

        isShowAxis = true;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        value = Math.min(Math.max(value, valueMin), valueMax);
        int progress = (value - valueMin) * 100 / (valueMax - valueMin);
        setProgress(progress);
    }

    public int getValueMin() {
        return valueMin;
    }

    public void setValueMin(int valueMin) {
        this.valueMin = valueMin;
    }

    public int getValueMax() {
        return valueMax;
    }

    public void setValueMax(int valueMax) {
        this.valueMax = valueMax;
    }

    public String getTvUnit() {
        return tvUnit;
    }

    public void setTvUnit(String tvUnit) {
        this.tvUnit = tvUnit;
    }

    public String getSubDesTv() {
        return subDesTv;
    }

    public void setSubDesTv(String subDesTv) {
        this.subDesTv = subDesTv;
    }

    public boolean isShowAxis() {
        return isShowAxis;
    }

    public void setShowAxis(boolean showAxis) {
        isShowAxis = showAxis;
    }

    public float getRingAxisOff() {
        return ringAxisOff;
    }

    public void setRingAxisOff(float ringAxisOff) {
        this.ringAxisOff = ringAxisOff;
    }

    public int getRangeColor() {
        return mRangeColor;
    }

    /**
     * 最大值/最小值的文本颜色
     *
     * @param mRangeColor
     */
    public void setRangeColor(@ColorInt int mRangeColor) {
        this.mRangeColor = mRangeColor;
    }

    public int getRingValueColor() {
        return mRingValueColor;
    }

    public void setDefaultTextMargin(int defaultTextMargin) {
        this.defaultTextMargin = defaultTextMargin;
    }

    /**
     * 圆环外围的文本的颜色
     *
     * @param mRingValueColor
     */
    public void setRingValueColor(@ColorInt int mRingValueColor) {
        this.mRingValueColor = mRingValueColor;
    }

    public int getCenterTextColor() {
        return mCenterTextColor;
    }

    /**
     * 中央显示值的文本颜色
     *
     * @param mCenterTextColor
     */
    public void setCenterTextColor(@ColorInt int mCenterTextColor) {
        this.mCenterTextColor = mCenterTextColor;
    }

    public int getCenterSubColor() {
        return mCenterSubColor;
    }

    /**
     * 对中央文本说明文本的颜色
     *
     * @param mCenterSubColor
     */
    public void setCenterSubColor(@ColorInt int mCenterSubColor) {
        this.mCenterSubColor = mCenterSubColor;
    }

    public int getRangeSize() {
        return px2dp(mRangeSize);
    }

    /**
     * 最大值/最小值的文本字号
     *
     * @param mRangeSize
     */
    public void setRangeSize(int mRangeSize) {
        this.mRangeSize = dp2px(mRangeSize);
    }

    public int getRingValueSize() {
        return px2dp(mRingValueSize);
    }

    /**
     * 圆环外围的文本的字号
     *
     * @param mRingValueSize
     */
    public void setRingValueSize(int mRingValueSize) {
        this.mRingValueSize = dp2px(mRingValueSize);
    }

    public int getCenterTextSize() {
        return px2dp(mCenterTextSize);
    }

    /**
     * 中央显示值的文本字号
     *
     * @param mCenterTextSize
     */
    public void setCenterTextSize(int mCenterTextSize) {
        this.mCenterTextSize = dp2px(mCenterTextSize);
    }

    public int getCenterSubSize() {
        return px2dp(mCenterSubSize);
    }

    /**
     * 对中央文本说明文本的字号
     *
     * @param mCenterSubSize
     */
    public void setCenterSubSize(int mCenterSubSize) {
        this.mCenterSubSize = dp2px(mCenterSubSize);
    }

    public String getLowTv() {
        return lowTv;
    }

    public void setLowTv(String lowTv) {
        this.lowTv = lowTv;
    }

    public String getNormalTv() {
        return normalTv;
    }

    public void setNormalTv(String normalTv) {
        this.normalTv = normalTv;
    }

    public String getHighTv() {
        return highTv;
    }

    public void setHighTv(String highTv) {
        this.highTv = highTv;
    }

    /**
     * 当开始布局时候调用
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // 获取总的宽高
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        //内部环的大小为整体的60%
        mRingWidth = (int) (mWidth * 0.7f);
        mRingHeight = (int) (mHeight * 0.7f);

        radius = (int) (mRingWidth / 2 - ringAxisOff * scaleFloat);

        // 初始化各种值
//        initValue();

        // 设置圆环画笔
        initPaint();

        // 设置文字画笔
        initTextPaint();

        setupOnDrawRes();

    }

    /**
     * 初始化等待绘制的资源初始化
     */
    private void setupOnDrawRes() {
        ringBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_progressbar_bkg);

        ringOval = new RectF((mWidth - mRingWidth) / 2, (mHeight - mRingHeight) / 2,
                (mWidth + mRingWidth) / 2, (mHeight + mRingHeight) / 2);
        ringAxis = new RectF(mWidth / 2 - radius, mHeight / 2 - radius,
                mWidth / 2 + radius, mHeight / 2 + radius);
    }

    /**
     * 设置圆环画笔
     */
    private void initPaint() {
        //bitmap画笔
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        //bitmap抗锯齿
        mBitmapPaint.setFilterBitmap(true);
        //坐标轴画笔
        mAxisPaint = new Paint();
        mAxisPaint.setAntiAlias(true);
        mAxisPaint.setColor(Color.DKGRAY);
        mAxisPaint.setStyle(Paint.Style.STROKE); // 边框风格
        mAxisPaint.setStrokeWidth(1 * scaleFloat);

    }

    /**
     * 设置文字画笔
     */
    private void initTextPaint() {

        mRangePaint = new Paint();
        mRingValuePaint = new Paint();
        mCenterTextPaint = new Paint();
        mCenterSubPaint = new Paint();
        mRangePaint.setAntiAlias(true);
        mRangePaint.setColor(mRangeColor);
        mRangePaint.setTextSize(mRangeSize);
        mRingValuePaint.setAntiAlias(true);
        mRingValuePaint.setColor(mRingValueColor);
        mRingValuePaint.setTextSize(mRingValueSize);
        mCenterTextPaint.setAntiAlias(true);
        mCenterTextPaint.setColor(mCenterTextColor);
        mCenterTextPaint.setTextSize(mCenterTextSize);
        mCenterSubPaint.setAntiAlias(true);
        mCenterSubPaint.setColor(mCenterSubColor);
        mCenterSubPaint.setTextSize(mCenterSubSize);
    }

    private void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画进度条背景
        canvas.drawBitmap(ringBmp, null, ringOval, mBitmapPaint);

        //画坐标轴
        if (isShowAxis) {
            canvas.drawOval(ringAxis, mAxisPaint);
            canvas.drawLine(0, mHeight / 2, mWidth, mHeight / 2, mAxisPaint);
            canvas.drawLine(mWidth / 2, 0, mWidth / 2, mHeight, mAxisPaint);
            canvas.drawLine(0, 0, mWidth, mHeight, mAxisPaint);
            canvas.drawLine(0, mHeight, mWidth, 0, mAxisPaint);
            canvas.drawRect(mWidth / 2 - mRingWidth / 2, mHeight / 2 - mRingHeight / 2,
                    mWidth / 2 + mRingWidth / 2, mHeight / 2 + mRingHeight / 2, mAxisPaint);
        }

        //画值的提示区间
        Rect tvRectLow = new Rect();
        Rect tvRectNor = new Rect();
        Rect tvRectHigh = new Rect();
        mRingValuePaint.getTextBounds(lowTv, 0, lowTv.length(), tvRectLow);
        mRingValuePaint.getTextBounds(normalTv, 0, normalTv.length(), tvRectNor);
        mRingValuePaint.getTextBounds(highTv, 0, highTv.length(), tvRectHigh);
        float xLow = (mWidth - mRingWidth) / 2 - tvRectLow.width() - defaultTextMargin * scaleFloat;
        float yLow = mHeight / 2 + tvRectLow.height() / 2;
        float xNor = (mWidth - tvRectNor.width()) / 2;
        float yNor = (mHeight - mRingHeight) / 2 - defaultTextMargin * scaleFloat;
        float xHigh = (mWidth + mRingWidth) / 2 + defaultTextMargin * scaleFloat;
        float yHigh = mHeight / 2 + tvRectHigh.height() / 2;
        canvas.drawText(lowTv, xLow, yLow, mRingValuePaint);
        canvas.drawText(normalTv, xNor, yNor, mRingValuePaint);
        canvas.drawText(highTv, xHigh, yHigh, mRingValuePaint);

        //画值和值描述
        Rect rectValue = new Rect();
        Rect rectSubDes = new Rect();
        mCenterTextPaint.getTextBounds(value + tvUnit, 0, (value + tvUnit).length(), rectValue);
        mCenterSubPaint.getTextBounds(subDesTv, 0, subDesTv.length(), rectSubDes);
        float xValue = (mWidth - rectValue.width()) / 2;
        float yValue = mHeight / 2 - defaultTextMargin * scaleFloat;
        float xSub = (mWidth - rectSubDes.width()) / 2;
        float ySub = mHeight / 2 + rectSubDes.height() + defaultTextMargin * scaleFloat;
        canvas.drawText(value + tvUnit, xValue, yValue, mCenterTextPaint);
        canvas.drawText(subDesTv, xSub, ySub, mCenterSubPaint);

        //画最大值和最小值
        Rect rectMax = new Rect();
        Rect rectMin = new Rect();
        mRangePaint.getTextBounds(valueMax + tvUnit, 0, (valueMax + tvUnit).length(), rectMax);
        mRangePaint.getTextBounds(valueMin + tvUnit, 0, (valueMin + tvUnit).length(), rectMin);
        float xMin = (float) (Math.cos(2 * Math.PI * startAngle / 360) * radius)
                + mWidth / 2 - rectMin.width() / 2;
        float yMin = mHeight / 2 + mRingHeight / 2 + rectMin.height() / 2 + defaultTextMargin * scaleFloat;
        float xMax = (float) (Math.cos(2 * Math.PI * (startAngle - (360 - totalAngle)) / 360) * radius)
                + mWidth / 2 - rectMax.width() / 2;
        float yMax = mHeight / 2 + mRingHeight / 2 + rectMax.height() / 2 + defaultTextMargin * scaleFloat;
        canvas.drawText(valueMin + tvUnit, xMin, yMin, mRangePaint);
        canvas.drawText(valueMax + tvUnit, xMax, yMax, mRangePaint);

        //定位指示器以及旋转角
        locateIndicator(progress, totalAngle, startAngle, indicatorStartAngle, canvas);
//        canvas.save();
//        canvas.rotate(45,mWidth/2,mHeight/2);
//        canvas.drawOval(ovalRect, mAxisPaint);
//        canvas.restore();

//        Bitmap bitmapIndicator = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_indicator);
//        int width = bitmapIndicator.getWidth();
//        int height = bitmapIndicator.getHeight();
//        float scale = height * 1f / width;
//        Rect rectDst2 = new Rect(0, mHeight / 2, 50, (int) (mHeight / 2 + 50 * scale));
//        canvas.drawBitmap(bitmapIndicator, null, rectDst2, mBitmapPaint);

        //画指示器
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_sword);
//        Rect rectSrc = new Rect(0, 0, 100, 100);
//        Rect rectDst = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//        canvas.drawBitmap(bitmap, null, rectSrc, mBitmapPaint);

        //
        // 第二步：绘制文字
//        String text = ((int) (progress / maxProgress * 100)) + "";
//        String text = value + "";
//        Rect bounds = new Rect();
//        mTextPaint.getTextBounds(text, 0, text.length(), bounds);
//        canvas.drawText(text, mWidth / 2 - bounds.width() / 2, mHeight / 2 + bounds.height() / 2, mTextPaint);
    }


    /**
     * 重新定位indicator
     *
     * @param progress
     */
    void locateIndicator(int progress, int totalAngle, int startAngle, Canvas canvas) {
        //默认不需要旋转
        this.locateIndicator(progress, totalAngle, startAngle, -1, canvas);
    }


    /**
     * 重新定位indicator
     *
     * @param progress
     * @param totalAngle
     * @param startAngle
     * @param indicatorStartAngle 指示器的起始角度，可以为-1，代表无旋转
     */
    void locateIndicator(int progress, int totalAngle, int startAngle, int indicatorStartAngle
            , Canvas canvas) {

        int locateAngle = startAngle + totalAngle * progress / 100;
        int rotateAngle = indicatorStartAngle + totalAngle * progress / 100;
        Log.i(TAG, "progress:" + progress);
        Log.i(TAG, "angle:" + locateAngle);
        //0~360
        //偏角多偏45°
        locateAngle = (locateAngle + 45) % 360;
        Log.i(TAG, "realAngle:" + locateAngle);
//        Log.i(TAG, "rotateAngle:" + rotateAngle);
        Log.i(TAG, "radius:" + radius);
        Log.i(TAG, "sin-->" + Math.sin(locateAngle) + "\ncos-->" + Math.cos(locateAngle));
        //注意！！！这边的三角函数计算参数是弧度，不是角度
        int yOff = (int) (Math.sin(2 * Math.PI * locateAngle / 360) * radius);
        int xOff = (int) (Math.cos(2 * Math.PI * locateAngle / 360) * radius);
        Log.i(TAG, "xOff-->" + xOff + "\nyOff-->" + yOff);
        //不能小于0
        if (locateAngle < 0) {
            return;
        }
        int indicatorYCenter = mHeight / 2 + yOff;
        int indicatorXCenter = mWidth / 2 + xOff;

        Log.i(TAG, "point");
        Bitmap bitmapIndicator = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_indicator);
        int width = bitmapIndicator.getWidth();
        int height = bitmapIndicator.getHeight();
        float scale = height * 1f / width;
//        RectF rectDst2 = new RectF(indicatorXCenter - 50, (int) (indicatorYCenter - 25 * scale), indicatorXCenter, (int) (indicatorYCenter + 25 * scale));
        RectF rectDst2 = new RectF(mWidth / 2 - radius - 50, mHeight / 2 - 25 * scale, mWidth / 2 - radius, mHeight / 2 + 25 * scale);

        //思路1，旋转rect
        //pass 旋转rect，bitmap依旧不会改变，跟自己计算旋转后的rect坐标效果无异
//        Matrix m = new Matrix();
//        m.setRotate(180, indicatorXCenter , indicatorYCenter);
//        m.mapRect(rectDst2);

        //思路2，旋转Canvas
        //可以试一试

        //思路3，旋转Bitmap

//        canvas.drawRect(rectDst2, mIndicatorRectPaint);

        /**
         * 如果指示器需要旋转
         */
        Log.i(TAG, "readyRotate");
        if (indicatorStartAngle != -1) {
            canvas.save();
            Log.i(TAG, "rotateAngle-->" + rotateAngle + "\n");
            canvas.rotate(rotateAngle, mWidth / 2, mHeight / 2);
//            canvas.drawRect(rectDst2, mIndicatorRectPaint);
            canvas.drawBitmap(bitmapIndicator, null, rectDst2, mBitmapPaint);
            canvas.restore();
        }
    }

    /**
     * 刷新，间接调用postInvalidate
     */
    public void refresh() {
        this.postInvalidate();
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
