package fishy.support.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fishy.support.util.DeviceInfoUtil;

/**
 * Created by DN2017030300 on 2018/9/6.
 */

public class SixEdgeArrow extends View {
    //通用属性
    final String TAG = this.getClass().getSimpleName();
    float scaleFloat;
    Context context;
    List<ArrowIndicator> indicators;
    //画笔
    Paint mPaint;
    Paint textPaint;
    Paint bmpPaint;
    //颜色和绘制路径
    int[] areaColorArray;
    Path[] areaPathArray;
    //所有的区域的中心X的坐标数组
    float[] pathCenterXArray;
    //用于区域描述的文本数组
    String[] hintTextArrray;
    //所有级别的图标
    Bitmap[] levelBmpArray;

    //宽，高
    int mWidth;
    int mHeight;

    //相关的参数
    //箭头的高
    float arrowHeight;
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
    //箭头的突出比
    int scaleArrowHead = 3;
    //箭尾的缩进比
    int scaleArrowTail = 6;


    public SixEdgeArrow(Context context) {
        this(context, null);
    }

    public SixEdgeArrow(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SixEdgeArrow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scaleFloat = DeviceInfoUtil.getDeviceDpiScale(context);
        this.context = context;
        initAttrs(context, attrs, defStyleAttr);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = null;
        try {

            a = context.obtainStyledAttributes(attrs, R.styleable.SixEdgeArrow);

            arrowHeight = a.getDimension(R.styleable.SixEdgeArrow_arrowHeight, dp2px(20));
            hintTextSize = a.getDimension(R.styleable.SixEdgeArrow_hintTextSize, dp2px(16));
            hintTextColor = a.getColor(R.styleable.SixEdgeArrow_hintTextColor, Color.DKGRAY);
            levelIconSize = a.getDimension(R.styleable.SixEdgeArrow_levelIcSize, dp2px(28));
            levelIcMargin = a.getDimension(R.styleable.SixEdgeArrow_levelIcMargin, dp2px(4));
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
     * @return
     */
    public int getCurrentLevel() {
        return level;
    }

    /**
     * 设置当前显示用的数据
     */
    public void setData(List<ArrowIndicator> indicators) {
        this.indicators = indicators;
        //重新开数组
        int size = indicators.size();
        areaColorArray = new int[size];
        areaPathArray = new Path[size];
        pathCenterXArray = new float[size];
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


    @Override
    protected void
    onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        //绘制出所有的区域的path，待绘制
        int size = indicators.size();
        for (int i = 0; i < indicators.size(); i++) {
            areaColorArray[i] = indicators.get(i).getColor();
            hintTextArrray[i] = indicators.get(i).getHintText();
            float start = mWidth * i / size;
            float end = mWidth * (i + 1) / size;
            float middle = end / scaleArrowHead + start * (scaleArrowHead - 1) / scaleArrowHead;
            pathCenterXArray[i] = middle;
            Log.i(TAG, middle + "");
            areaPathArray[i] = createArrowPath(start, end);
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
            canvas.drawPath(areaPathArray[i], mPaint);

            Rect rect = new Rect();
            textPaint.getTextBounds(hintTextArrray[i], 0, hintTextArrray[i].length(), rect);
            int width = rect.width();
            float textY = ((mHeight + arrowHeight) / 2) + rect.height();
            canvas.drawText(hintTextArrray[i], pathCenterXArray[i] - (width / 2)
                    , textY, textPaint);
        }
        if (level != -1) {
            float left = pathCenterXArray[level] - (levelIconSize / 2);
            float bottom = (mHeight - arrowHeight) / 2 - levelIcMargin;
            float right = left + levelIconSize;
            float top = bottom - levelIconSize;

            rectLevelIc.set(left, top, right, bottom);
            canvas.drawBitmap(levelBmpArray[level], null, rectLevelIc, bmpPaint);
        }
    }

    /**
     * 配置画笔
     */
    Paint createStandPaint() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
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

    Path createArrowPath(float start, float end) {
        float arrowWidth = end - start;
        Path path = new Path();
        path.moveTo(start, (mHeight - arrowHeight) / 2);
        //前箭头为1/3
        path.lineTo(end - arrowWidth / scaleArrowHead, (mHeight - arrowHeight) / 2);
        path.lineTo(end, mHeight / 2);
        path.lineTo(end - arrowWidth / scaleArrowHead, (mHeight + arrowHeight) / 2);
        path.lineTo(start, (mHeight + arrowHeight) / 2);
        //后尾为1/6
        path.lineTo(start + arrowWidth / scaleArrowTail, mHeight / 2);
        path.close();
        Log.i(TAG, start + ":" + (end - arrowWidth / scaleArrowHead));
        return path;
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
