package fishy.support.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fishy.support.util.DeviceInfoUtil;

/**
 * Created by DN2017030300 on 2018/5/15.
 */

public class GradientScaleBar extends View {
    final String TAG = this.getClass().getSimpleName();

    //通用属性
    float scaleFloat;
    Context context;
    int progress;

    //宽，高
    int mWidth;
    int mHeight;
    int mBarWidth;

    // 最大值/最小值的Y轴偏移
    int valueYOff;
    //区间描述的Y轴偏移
    int desYOff;

    //画笔
    Paint bkgBarPaint;
    Paint gradientBarPaint;
    Paint valuePaint;
    Paint desPaint;
    Paint scalePaint;

    //配置的属性
    int valueTvColor;       //值颜色
    int desTvColor;         //描述颜色
    int bkgBarColor;        //进度条背景色
    int gradientStartColor; //进度条开始色
    int gradientEndColor;   //进度条结束色

    int valueTvSize;        //值字号
    int desTvSize;          //描述字号

    int barHeight;      //进度条高度
    int scaleWidth;     //刻度宽度

    int value;
    int valueMin;
    int valueMax;

    //刻度组
    List<Integer> scaleArray;
    //描述组
    List<String> desArray;

    public GradientScaleBar(Context context) {
        this(context, null);
    }

    public GradientScaleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public GradientScaleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

    void init(Context context) {
        this.context = context;

        scaleFloat = DeviceInfoUtil.getDeviceDpiScale(context);

        valueTvColor = Color.DKGRAY;
        desTvColor = Color.DKGRAY;
        bkgBarColor = Color.DKGRAY;
        gradientStartColor = Color.BLACK;
        gradientEndColor = Color.BLACK;
        valueTvSize = (int) (12 * scaleFloat);
        desTvSize = (int) (14 * scaleFloat);
        barHeight = (int) (20 * scaleFloat);
        scaleWidth = (int) (4 * scaleFloat);

        valueYOff=0;
        desYOff=0;

        valueMin = 0;
        valueMax = 100;
        scaleArray = new ArrayList<>();
        desArray = new ArrayList<>();
    }

    private void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }

    public void setValue(int value) {
        this.value = Math.min(Math.max(valueMin, value), valueMax);
        int progress = (value - valueMin) * 100 / (valueMax - valueMin);
        setProgress(progress);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mBarWidth = (int) (mWidth * 0.9f);

        initPaint();
        initTextPaint();
    }

    public int getValueYOff() {
        return px2dp(valueYOff);
    }

    /**
     * 最大值/最小值的Y轴偏移
     * @param valueYOff
     */
    public void setValueYOff(int valueYOff) {
        this.valueYOff = dp2px(valueYOff);
    }

    public int getDesYOff() {
        return px2dp(desYOff);
    }

    /**
     * 区间描述的Y轴偏移
     * @param desYOff
     */
    public void setDesYOff(int desYOff) {
        this.desYOff = dp2px(desYOff);
    }

    public int getValueTvColor() {
        return valueTvColor;
    }

    public void setValueTvColor(int valueTvColor) {
        this.valueTvColor = valueTvColor;
    }

    public int getDesTvColor() {
        return desTvColor;
    }

    public void setDesTvColor(int desTvColor) {
        this.desTvColor = desTvColor;
    }

    public int getBkgBarColor() {
        return bkgBarColor;
    }

    public void setBkgBarColor(int bkgBarColor) {
        this.bkgBarColor = bkgBarColor;
    }

    public int getGradientStartColor() {
        return gradientStartColor;
    }

    public void setGradientStartColor(int gradientStartColor) {
        this.gradientStartColor = gradientStartColor;
    }

    public int getGradientEndColor() {
        return gradientEndColor;
    }

    public void setGradientEndColor(int gradientEndColor) {
        this.gradientEndColor = gradientEndColor;
    }

    public int getValueTvSize() {
        return px2dp(valueTvSize);
    }

    public void setValueTvSize(int valueTvSize) {
        this.valueTvSize = dp2px(valueTvSize);
    }

    public int getDesTvSize() {
        return px2dp(desTvSize);
    }

    public void setDesTvSize(int desTvSize) {
        this.desTvSize = dp2px(desTvSize);
    }

    public int getBarHeight() {
        return px2dp(barHeight);
    }

    public void setBarHeight(int barHeight) {
        this.barHeight = dp2px(barHeight);
    }

    public int getScaleWidth() {
        return px2dp(scaleWidth);
    }

    public void setScaleWidth(int scaleWidth) {
        this.scaleWidth = dp2px(scaleWidth);
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

    public void setScaleArray(List<Integer> scaleArray) {
        this.scaleArray = scaleArray;
    }

    public void setDesArray(List<String> desArray) {
        this.desArray = desArray;
    }

    private void initTextPaint() {
        //值区间画笔
        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setColor(valueTvColor);
        valuePaint.setTextSize(valueTvSize);

        //值区间描述画笔
        desPaint = new Paint();
        desPaint.setAntiAlias(true);
        desPaint.setColor(desTvColor);
        desPaint.setTextSize(desTvSize);
    }

    private void initPaint() {
        //背景bar画笔
        bkgBarPaint = new Paint();
        bkgBarPaint.setColor(bkgBarColor);
        bkgBarPaint.setAntiAlias(true);
        //设置笔头
        bkgBarPaint.setStrokeJoin(Paint.Join.BEVEL);
        bkgBarPaint.setStrokeCap(Paint.Cap.ROUND);
        bkgBarPaint.setStyle(Paint.Style.STROKE);
        bkgBarPaint.setStrokeWidth(barHeight);

        //渐变的progressBar画笔
        gradientBarPaint = new Paint();
        gradientBarPaint.setAntiAlias(true);
        gradientBarPaint.setStyle(Paint.Style.STROKE);
        gradientBarPaint.setStrokeJoin(Paint.Join.BEVEL);
        gradientBarPaint.setStrokeCap(Paint.Cap.ROUND);
        gradientBarPaint.setStrokeWidth(barHeight);

        //初始化刻度的paint
        scalePaint = new Paint();
        scalePaint.setAntiAlias(true);
        scalePaint.setColor(Color.WHITE);
        scalePaint.setStyle(Paint.Style.STROKE);
        scalePaint.setStrokeWidth(scaleWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int scaleSize = scaleArray.size();
        int desSize = desArray.size();
        //说明有配置过
        if (scaleSize != 0 && desSize != 0) {
            //描述size必须比刻度size大1
            if (desSize - scaleSize != 1) {
                throw new IllegalArgumentException("scale size must equal des size +1 !");
            }
        }
        float startX = mWidth / 2 - mBarWidth / 2;
        float startY = mHeight / 2;
        float endX = mWidth / 2 + mBarWidth / 2;
        float endY = startY;
        canvas.drawLine(startX, startY, endX, endY, bkgBarPaint);

        float progressBarEndX = (endX - startX) * progress / 100 + startX;
        //重新设置gradient
        LinearGradient gradient = new LinearGradient(startX, startY, progressBarEndX, endY,
                gradientStartColor, gradientEndColor, Shader.TileMode.CLAMP);
        gradientBarPaint.setShader(gradient);
        canvas.drawLine(startX, startY, progressBarEndX, endY, gradientBarPaint);

        //画最大,最小值
        Rect rectMin = new Rect();
        Rect rectMax = new Rect();
        String minStr = valueMin + "";
        String maxStr = valueMax + "";
        valuePaint.getTextBounds(minStr, 0, minStr.length(), rectMin);
        valuePaint.getTextBounds(maxStr, 0, maxStr.length(), rectMax);
        int minX = mWidth / 2 - mBarWidth / 2;
        int minY = (int) (mHeight / 2 - barHeight / 2 - valueYOff * scaleFloat);
        int maxX = mWidth / 2 + mBarWidth / 2 - rectMax.width();
        int maxY = minY;
        canvas.drawText(minStr, minX, minY, valuePaint);
        canvas.drawText(maxStr, maxX, maxY, valuePaint);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);

        //画刻度，画区间描述
        for (int i = 0; i < scaleSize; i++) {
            int scaleValue = scaleArray.get(i);
            float scaleX =
                    (scaleValue - valueMin) * mBarWidth / (valueMax - valueMin) + startX;
            //画刻度
            canvas.drawLine(scaleX, startY - barHeight / 2, scaleX, startY + barHeight / 2, scalePaint);
            //画区间描述
            if (i == scaleSize - 1) {
                //最后一个刻度的时候
                //画两个描述
                float lastX;
                if (i == 0) {
                    lastX = startX;
                } else {
                    int lastValue = scaleArray.get(i - 1);
                    lastX =
                            (lastValue - valueMin) * mBarWidth / (valueMax - valueMin) + startX;
                }
                float center = (scaleX + lastX) / 2;
//                canvas.drawLine(lastX, startY - barHeight / 2, lastX, startY + barHeight / 2, paint);
//                canvas.drawLine(scaleX, startY - barHeight / 2, scaleX, startY + barHeight / 2, paint);
//                canvas.drawLine(center, startY - barHeight / 2, center, startY + barHeight / 2, paint);
                String desText = desArray.get(i);
                Rect rect = new Rect();
                desPaint.getTextBounds(desText, 0, desText.length(), rect);
                //绘制，MarginTop
                canvas.drawText(desText, center - rect.width() / 2,
                        mHeight / 2 + barHeight / 2 + rect.height() + desYOff * scaleFloat, desPaint);
                //再画刻度到最大值之间的
                float lastX2 = endX;
                float center2 = (scaleX + lastX2) / 2;
                String desText2 = desArray.get(i + 1);
                Rect rect2 = new Rect();
                desPaint.getTextBounds(desText2, 0, desText2.length(), rect2);
                canvas.drawText(desText2, center2 - rect2.width() / 2,
                        mHeight / 2 + barHeight / 2 + rect2.height() + desYOff * scaleFloat, desPaint);
            } else if (i == 0) {   //画0~刻度的描述
                float last = startX;
                float center = (scaleX + last) / 2;
                String desText = desArray.get(i);
                Rect rect = new Rect();
                desPaint.getTextBounds(desText, 0, desText.length(), rect);
                //绘制，MarginTop
                canvas.drawText(desText, center - rect.width() / 2,
                        mHeight / 2 + barHeight / 2 + rect.height() + desYOff * scaleFloat, desPaint);
            } else {
                //画普通的刻度之间的描述
                int lastValue = scaleArray.get(i - 1);
                float lastX =
                        (lastValue - valueMin) * mBarWidth / (valueMax - valueMin) + startX;

                float center = (scaleX + lastX) / 2;
                String desText = desArray.get(i);
                Rect rect = new Rect();
                desPaint.getTextBounds(desText, 0, desText.length(), rect);
                //绘制，MarginTop
                canvas.drawText(desText, center - rect.width() / 2,
                        mHeight / 2 + barHeight / 2 + rect.height() + desYOff * scaleFloat, desPaint);
            }

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
}
