package fishy.support.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import fishy.support.util.DeviceInfoUtil;

/**
 * Created by DN2017030300 on 2018/9/10.
 */

public class HrvRing extends View {
    //通用属性
    final String TAG = this.getClass().getSimpleName();
    float scaleFloat;
    Context context;
    List<HrvScale> data;

    //画笔
    Paint bkgPaint;
    Paint progressPaint;
    Paint scalePaint;
    Paint desTvPaint;
    Paint centerValuePaint;
    Paint pointRingPaint;
    //描述数组
    String[] desArray;
    //刻度中线数组
    Float[] scaleAngleArray;
    //存储所有区域数值的绘制中线角度
    Float[] desAngleArray;
    //描述的rect的数组
    Rect[] desRectArray;

    //当前的数值
    float hrvValue;
    //圆环rect
    RectF ringRect;
    //中心值rect
    Rect centerValueRect;
    Rect centerLargeRect;
    //相关的参数
    //圆环背景色
    int ringBkgColor;
    //圆环进度条颜色
    int ringProgressColor;
    //刻度的颜色
    int scaleColor;
    //区域文本的颜色
    int desTvColor;
    //值的文本的颜色
    int centerValueColor;
    //小尾巴的圆环宽度
    float pointRingWidth;
    //圆环的宽度
    float ringWidth;
    //圆环半径
    float ringRadius;
    //描述文本的半径
    float desRadius;
    //最小的量程
    float minValue;
    //最大的量程
    float maxValue;
    //刻度的宽度
    float scaleWidth;
    //区域文本的尺寸
    float desTvSize;
    //值的文本的尺寸
    float centerValueSize;
    //圆环的起始的角度
    int startAngle = 140;
    //圆环的扫过的角度
    int maxSweepAngle = 260;

    //宽，高相关
    int mWidth;
    int mHeight;
    float tvDefaultRadius;

    public HrvRing(Context context) {
        this(context, null);
    }

    public HrvRing(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HrvRing(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        scaleFloat = DeviceInfoUtil.getDeviceDpiScale(context);
        initAttrs(context, attrs, defStyleAttr);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.HrvRing);

            ringBkgColor = a.getColor(R.styleable.HrvRing_ringBkgColor, Color.GRAY);
            ringProgressColor = a.getColor(R.styleable.HrvRing_ringProgressColor, Color.BLACK);
            pointRingWidth = a.getDimension(R.styleable.HrvRing_pointRingWidth, dp2px(2));
            minValue = a.getFloat(R.styleable.HrvRing_minValue, 0);
            maxValue = a.getFloat(R.styleable.HrvRing_maxValue, 100);
            ringWidth = a.getDimension(R.styleable.HrvRing_ringWidth, dp2px(8));
            ringRadius = a.getDimension(R.styleable.HrvRing_ringRadius, dp2px(40));
            //文字半径是圆环半径+24dp
            tvDefaultRadius = ringRadius + a.getDimension(R.styleable.HrvRing_desTvOffset, dp2px(24));
            scaleWidth = a.getDimension(R.styleable.HrvRing_scaleWidth, dp2px(2));
            scaleColor = a.getColor(R.styleable.HrvRing_scaleColor, Color.WHITE);
            desTvColor = a.getColor(R.styleable.HrvRing_desTvColor, Color.GRAY);
            centerValueColor = a.getColor(R.styleable.HrvRing_centerValueColor, Color.BLACK);
            desTvSize = a.getDimension(R.styleable.HrvRing_desTvSize, dp2px(14));
            centerValueSize = a.getDimension(R.styleable.HrvRing_centerValueSize, dp2px(14));
            hrvValue = a.getFloat(R.styleable.HrvRing_hrvValue, 50);
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

    }

    /**
     * 配置画笔
     */
    private void initPaint() {
        //背景色的圆环
        bkgPaint = new Paint();
        bkgPaint.setColor(ringBkgColor);
        bkgPaint.setStrokeWidth(ringWidth);
        bkgPaint.setStyle(Paint.Style.STROKE);
        bkgPaint.setAntiAlias(true);

        //进度条的圆环
        progressPaint = new Paint();
        progressPaint.setColor(ringProgressColor);
        progressPaint.setStrokeWidth(ringWidth);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setAntiAlias(true);

        //刻度的画笔
        scalePaint = new Paint();
        scalePaint.setColor(scaleColor);
        scalePaint.setStrokeWidth(scaleWidth);
        scalePaint.setStyle(Paint.Style.FILL);
        scalePaint.setAntiAlias(true);

        //区域描述的画笔
        desTvPaint = new Paint();
        desTvPaint.setColor(desTvColor);
        desTvPaint.setTextSize(desTvSize);
        desTvPaint.setStyle(Paint.Style.STROKE);
        desTvPaint.setAntiAlias(true);

        //值的画笔
        centerValuePaint = new Paint();
        centerValuePaint.setColor(centerValueColor);
        centerValuePaint.setTextSize(centerValueSize);
        centerValuePaint.setAntiAlias(true);

        pointRingPaint = new Paint();
        pointRingPaint.setStyle(Paint.Style.FILL);
        pointRingPaint.setAntiAlias(true);
    }

    /**
     * 初始化绘制区域rect
     */
    private void initRect() {
        ringRect = new RectF();
        centerValueRect = new Rect();
        centerLargeRect = new Rect();
    }

    public void setHrvValue(float hrvValue) {
        this.hrvValue = hrvValue;
        postInvalidate();
    }

    public float getHrvValue() {
        return hrvValue;
    }

    public void setData(List<HrvScale> data) {
        this.data = data;
        desArray = new String[data.size()];
        scaleAngleArray = new Float[data.size() - 1];
        desAngleArray = new Float[data.size()];
        desRectArray = new Rect[data.size()];
        for (int i = 0; i < data.size(); i++) {
            desRectArray[i] = new Rect();
        }
        postInvalidate();
    }

    public int getRingBkgColor() {
        return ringBkgColor;
    }

    public void setRingBkgColor(int ringBkgColor) {
        this.ringBkgColor = ringBkgColor;
    }

    public int getRingProgressColor() {
        return ringProgressColor;
    }

    public void setRingProgressColor(int ringProgressColor) {
        this.ringProgressColor = ringProgressColor;
    }

    public int getScaleColor() {
        return scaleColor;
    }

    public void setScaleColor(int scaleColor) {
        this.scaleColor = scaleColor;
    }

    public int getDesTvColor() {
        return desTvColor;
    }

    public void setDesTvColor(int desTvColor) {
        this.desTvColor = desTvColor;
    }

    public int getCenterValueColor() {
        return centerValueColor;
    }

    public void setCenterValueColor(int centerValueColor) {
        this.centerValueColor = centerValueColor;
    }

    public float getPointRingWidth() {
        return pointRingWidth;
    }

    public void setPointRingWidth(float pointRingWidth) {
        this.pointRingWidth = pointRingWidth;
    }

    public float getRingWidth() {
        return ringWidth;
    }

    public void setRingWidth(float ringWidth) {
        this.ringWidth = ringWidth;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public float getScaleWidth() {
        return scaleWidth;
    }

    public void setScaleWidth(float scaleWidth) {
        this.scaleWidth = scaleWidth;
    }

    public float getDesTvSize() {
        return desTvSize;
    }

    public void setDesTvSize(float desTvSize) {
        this.desTvSize = desTvSize;
    }

    public float getCenterValueSize() {
        return centerValueSize;
    }

    public void setCenterValueSize(float centerValueSize) {
        this.centerValueSize = centerValueSize;
    }

//    //刻度中线数组
//    int[] scaleAngleArray;
//    //存储所有区域数值的绘制中线角度
//    int[] desAngleArray;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        //前一段结束的位置，检测是否位置错乱
        float endFloat = minValue;
        for (int i = 0; i < data.size(); i++) {
            float value = data.get(i).getValueStart();
            if (value < endFloat) {
                throw new RuntimeException("传入的区间域错乱！");
            }
            endFloat = value;
            //文本处理
            desArray[i] = data.get(i).getDes();
            desTvPaint.getTextBounds(desArray[i], 0, desArray[i].length(), desRectArray[i]);

            //计算字的位置的中线角度
            float midValue = (data.get(i).getValueEnd() + data.get(i).getValueStart()) / 2;
            desAngleArray[i] = startAngle
                    + maxSweepAngle * (midValue - minValue) / (maxValue - minValue);

            //刻度中线数组
            //起始位置的刻度不列入计算列
            if (i != 0) {
                float startValue = data.get(i).getValueStart();
                scaleAngleArray[i - 1] = startAngle
                        + maxSweepAngle * (startValue - minValue) / (maxValue - minValue);
            }

        }

        ringRect.set(mWidth / 2 - ringRadius, mHeight / 2 - ringRadius,
                mWidth / 2 + ringRadius, mHeight / 2 + ringRadius);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(ringRect, startAngle, maxSweepAngle, false, bkgPaint);

        float percent = (hrvValue - minValue) / (maxValue - minValue);
        if (percent != 0) {
            canvas.drawArc(ringRect, startAngle, maxSweepAngle * percent,
                    false, progressPaint);
        }
        //画中心值
        String largeText = (int) hrvValue + "";
        String normalText = "/" + (int) maxValue;

        centerValuePaint.setTextSize(centerValueSize);
        centerValuePaint.getTextBounds(normalText, 0, normalText.length(), centerValueRect);
        canvas.drawText(normalText, mWidth / 2,
                (mHeight + centerValueRect.height()) / 2, centerValuePaint);

        centerValuePaint.setTextSize(centerValueSize * 1.5f);
        centerValuePaint.getTextBounds(largeText, 0, largeText.length(), centerLargeRect);
        //向左偏移2dp
        canvas.drawText(largeText, mWidth / 2 - centerLargeRect.width() - dp2px(2),
                (mHeight + centerValueRect.height()) / 2, centerValuePaint);

        for (int i = 0; i < scaleAngleArray.length; i++) {
            float x0;
            float y0;
            float x1;
            float y1;
            x0 = (float) (Math.cos(2 * Math.PI * scaleAngleArray[i] / 360) * (ringRadius + ringWidth / 2))
                    + mWidth / 2;
            x1 = (float) (Math.cos(2 * Math.PI * scaleAngleArray[i] / 360) * (ringRadius - ringWidth / 2))
                    + mWidth / 2;
            y0 = (float) (Math.sin(2 * Math.PI * scaleAngleArray[i] / 360) * (ringRadius + ringWidth / 2))
                    + mHeight / 2;
            y1 = (float) (Math.sin(2 * Math.PI * scaleAngleArray[i] / 360) * (ringRadius - ringWidth / 2))
                    + mHeight / 2;
            //计算一个点的起始，结束x，y
            canvas.drawLine(x0, y0, x1, y1, scalePaint);
        }

        //计算文本的中心所在的位置
        for (int i = 0; i < desRectArray.length; i++) {
            float centerX;
            float centerY;
            centerX = (float) (Math.cos(2 * Math.PI * desAngleArray[i] / 360) * tvDefaultRadius)
                    + mWidth / 2;
            centerY = (float) (Math.sin(2 * Math.PI * desAngleArray[i] / 360) * tvDefaultRadius)
                    + mHeight / 2;
            canvas.drawText(desArray[i], centerX - desRectArray[i].width() / 2,
                    centerY + desRectArray[i].height() / 2, desTvPaint);
        }
        //画尾巴小点
        float angle = percent * maxSweepAngle + startAngle;
        float pointX = (float) (Math.cos(2 * Math.PI * angle / 360) * ringRadius)
                + mWidth / 2;
        float pointY = (float) (Math.sin(2 * Math.PI * angle / 360) * ringRadius)
                + mHeight / 2;
        pointRingPaint.setColor(ringProgressColor);
        canvas.drawCircle(pointX, pointY, ringWidth / 2, pointRingPaint);
        pointRingPaint.setColor(Color.WHITE);
        canvas.drawCircle(pointX, pointY, ringWidth / 2 - pointRingWidth, pointRingPaint);
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

    /**
     * 记录刻度以及刻度相关的描述
     */
    public static class HrvScale {
        float valueStart;
        float valueEnd;
        String des;

        public HrvScale(float valueStart, float valueEnd, String des) {
            this.valueStart = valueStart;
            this.valueEnd = valueEnd;
            this.des = des;
        }

        public float getValueStart() {
            return valueStart;
        }

        public void setValueStart(float valueStart) {
            this.valueStart = valueStart;
        }

        public float getValueEnd() {
            return valueEnd;
        }

        public void setValueEnd(float valueEnd) {
            this.valueEnd = valueEnd;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
}
