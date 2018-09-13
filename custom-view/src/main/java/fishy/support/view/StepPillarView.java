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
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fishy.support.util.DeviceInfoUtil;

/**
 * Created by DN2017030300 on 2018/9/7.
 */

public class StepPillarView extends View {
    //通用属性
    final String TAG = this.getClass().getSimpleName();
    float scaleFloat;
    Context context;
    List<StepPillarView.ArrowIndicator> indicators;
    //画笔
    Paint mPaint;
    Paint textPaint;
    Paint bmpPaint;
    //颜色
    int[] areaColorArray;
    //所有的区域的起始X的坐标数组
    float[] pathStartXArray;
    //用于区域描述的文本数组
    String[] hintTextArrray;
    //所有级别的图标
    Bitmap[] levelBmpArray;

    //宽，高
    int mWidth;
    int mHeight;
    //台阶的最底部，最顶部对应的坐标
    float stepMinY;
    float stepMaxY;
    float stepWidth;
    float stepFullHeight;
    //台阶的区域所占总高的缩放比
    float stepContentScale;
    //相关的参数
    //底部的区域描述文字尺寸
    float hintTextSize;
    //底部的区域描述文字的颜色
    int hintTextColor;
    //等级，对应分级区域
    int level;
    //对应级别的图标尺寸
    float levelIconSize;
    //对应级别的图标底边距
    float levelIcMargin;

    //等级图标的绘制矩形
    RectF rectLevelIc;

    public StepPillarView(Context context) {
        this(context, null);
    }

    public StepPillarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public StepPillarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scaleFloat = DeviceInfoUtil.getDeviceDpiScale(context);
        this.context = context;
        initAttrs(context, attrs, defStyleAttr);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = null;
        try {

            a = context.obtainStyledAttributes(attrs, R.styleable.StepPillarView);

            hintTextSize = a.getDimension(R.styleable.StepPillarView_hintTextSize, dp2px(16));
            hintTextColor = a.getColor(R.styleable.StepPillarView_hintTextColor, Color.DKGRAY);
            levelIconSize = a.getDimension(R.styleable.StepPillarView_levelIcSize, dp2px(28));
            levelIcMargin = a.getDimension(R.styleable.StepPillarView_levelIcMargin, dp2px(4));
        } finally {
            // 注意，别忘了 recycle
            if (a != null) {
                a.recycle();
            }
        }
    }

    private void init() {
        indicators = new ArrayList<>();

        mPaint = createStandPaint();
        textPaint = createStandTextPaint();
        bmpPaint = createBmpPaint();
        rectLevelIc = new RectF();
        level = -1;

        stepContentScale = 0.85f;
    }

    /**
     * 设置当前的区域等级
     *
     * @param level
     */
    public void setCurrentLevel(int level) {
        level = Math.min(indicators.size() - 1, Math.max(level, 0));
        this.level = level;
    }

    /**
     * 设置当前显示用的数据
     */
    public void setData(List<StepPillarView.ArrowIndicator> indicators) {
        this.indicators = indicators;
        //重新开数组
        int size = indicators.size();
        areaColorArray = new int[size];
        pathStartXArray = new float[size];
        hintTextArrray = new String[size];
        levelBmpArray = new Bitmap[size];
        //配置图片资源
        for (int i = 0; i < indicators.size(); i++) {
            levelBmpArray[i] = BitmapFactory.decodeResource(
                    getResources(), indicators.get(i).getLevelIcRes());
        }
        //更新
        this.postInvalidate();
    }

    /**
     * 配置画笔
     */
    Paint createStandPaint() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        stepWidth = mWidth / indicators.size();
        stepFullHeight = mHeight * stepContentScale;
        stepMinY = (mHeight + stepFullHeight) / 2;
        stepMaxY = (mHeight - stepFullHeight) / 2;

        //todo
        //更新每个台阶区域的宽度
        mPaint.setStrokeWidth(stepWidth);
        //绘制出所有的区域的path，待绘制
        int size = indicators.size();
        for (int i = 0; i < indicators.size(); i++) {
            areaColorArray[i] = indicators.get(i).getColor();
            hintTextArrray[i] = indicators.get(i).getHintText();
            pathStartXArray[i] = mWidth * i / size;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < indicators.size(); i++) {
            mPaint.setColor(areaColorArray[i]);
            mPaint.setStrokeWidth(stepWidth);
            float currentHeight = stepMinY - stepFullHeight * (i + 1) / indicators.size();
            canvas.drawLine(pathStartXArray[i] + stepWidth / 2, stepMinY,
                    pathStartXArray[i] + stepWidth / 2, currentHeight, mPaint);

            Rect rect = new Rect();
            textPaint.getTextBounds(hintTextArrray[i], 0, hintTextArrray[i].length(), rect);
            int width = rect.width();
            float textY = stepMinY + rect.height();
            canvas.drawText(hintTextArrray[i], pathStartXArray[i] + stepWidth / 2 - width / 2
                    , textY, textPaint);

            //需要判断是否为选中的level
            if (level != -1 && level == i) {
                float left = pathStartXArray[level] + stepWidth / 2 - levelIconSize / 2;
                float bottom = currentHeight - levelIcMargin;
                float right = left + levelIconSize;
                float top = bottom - levelIconSize;

                rectLevelIc.set(left, top, right, bottom);
                canvas.drawBitmap(levelBmpArray[level], null, rectLevelIc, bmpPaint);
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

    /**
     * 填充指示器的部分数据
     */
    public static class ArrowIndicator {
        int color;
        String hintText;
        int levelIcRes;

        public ArrowIndicator(@ColorInt int color, String hintText, @DrawableRes int levelIcRes) {
            this.color = color;
            this.hintText = hintText;
            this.levelIcRes = levelIcRes;
        }

        public int getLevelIcRes() {
            return levelIcRes;
        }

        public void setLevelIcRes(int levelIcRes) {
            this.levelIcRes = levelIcRes;
        }

        public int getColor() {
            return color;
        }

        public void setColor(@ColorInt int color) {
            this.color = color;
        }

        public String getHintText() {
            return hintText;
        }

        public void setHintText(String hintText) {
            this.hintText = hintText;
        }
    }
}
