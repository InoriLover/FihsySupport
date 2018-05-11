package fishy.support.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import fishy.support.util.DeviceInfoUtil;

/**
 * Created by DN2017030300 on 2018/5/10.
 */

public class ColorIndicatorProgressBarCopy extends View {

    final String TAG = this.getClass().getSimpleName();

    Paint mBarPaint;
    Paint mTextPaint;
    Paint mBitmapPaint;
    Paint mAxisPaint;
    Paint mIndicatorRectPaint;

    private float mStrokeWidth = 80f;

    //this.view的宽高
    int mWidth;
    int mHeight;
    int radius;

    float scaleFloat;

    Context context;

    int progress;


    public ColorIndicatorProgressBarCopy(Context context) {
        this(context, null);
    }

    public ColorIndicatorProgressBarCopy(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ColorIndicatorProgressBarCopy(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
        init(context);

    }

    /**
     * 其他的初始化
     */
    private void init(Context context) {
        this.context = context;
        scaleFloat = DeviceInfoUtil.getDeviceDpiScale(this.context);
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mAxisPaint = new Paint();

        mAxisPaint.setAntiAlias(true);
        mAxisPaint.setColor(Color.DKGRAY);
        mAxisPaint.setStyle(Paint.Style.STROKE); // 边框风格
        mAxisPaint.setStrokeWidth(2 * scaleFloat);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = null;
        try {
//            a = context.obtainStyledAttributes(attrs, R.styleable.ColorIndicatorProgressBar);

//            roundColor = a.getColor(R.styleable.StatusRingView_ringColor, getResources().getColor(android.R.color.darker_gray));
//            roundProgressColor = a.getColor(R.styleable.StatusRingView_ringProgressColor, getResources().getColor(android.R.color.holo_red_dark));
//            textColor = a.getColor(R.styleable.StatusRingView_ringTextColor, getResources().getColor(android.R.color.holo_blue_dark));
//            textSize = a.getDimension(R.styleable.StatusRingView_ringTextSize, 22f);
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
     * 当开始布局时候调用
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // 获取总的宽高
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();


        // 初始化各种值
//        initValue();

        // 设置圆环画笔
        setupPaint();

        // 设置文字画笔
        setupTextPaint();

        //设置indicatorPaint的rect画笔
        setupIndicatorPaint();
    }

    private void setupIndicatorPaint() {
        mIndicatorRectPaint = new Paint();
        mIndicatorRectPaint.setAntiAlias(true);
        mIndicatorRectPaint.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mBarPaint.setStyle(Paint.Style.STROKE); // 边框风格
        mBarPaint.setStrokeWidth(1 * scaleFloat);
    }

    /**
     * 设置圆环画笔
     */
    private void setupPaint() {
        // 创建圆环画笔
        mBarPaint = new Paint();
        mBarPaint.setAntiAlias(true);
        mBarPaint.setColor(getResources().getColor(android.R.color.holo_orange_light));
//        mBarPaint.setShader(new LinearGradient());
//        mBarPaint.setShader(new RadialGradient());
        mBarPaint.setStyle(Paint.Style.STROKE); // 边框风格
        mBarPaint.setStrokeWidth(4 * scaleFloat);
    }

    /**
     * 设置文字画笔
     */
    private void setupTextPaint() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(getResources().getColor(android.R.color.holo_blue_dark));
        mTextPaint.setTextSize(14 * scaleFloat);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.rotate(90, mWidth / 2, mHeight / 2);
        //画进度条背景
        Bitmap bitmapBkg = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_progressbar_bkg);
        radius = (mWidth - 120) / 2;
        RectF ovalRect = new RectF(mWidth / 2 - radius, mHeight / 2 - radius, mWidth / 2 + radius, mHeight / 2 + radius);
        canvas.drawBitmap(bitmapBkg, null, ovalRect, mBitmapPaint);


        locateIndicator(progress, 270, 135, -45, canvas);

        //画坐标轴
        canvas.drawLine(0, mHeight / 2, mWidth, mHeight / 2, mAxisPaint);
        canvas.drawLine(mWidth / 2, 0, mWidth / 2, mHeight, mAxisPaint);

        canvas.drawOval(ovalRect, mAxisPaint);
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

        Log.i(TAG,"point");
        Bitmap bitmapIndicator = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_indicator);
        int width = bitmapIndicator.getWidth();
        int height = bitmapIndicator.getHeight();
        float scale = height * 1f / width;
//        RectF rectDst2 = new RectF(indicatorXCenter - 50, (int) (indicatorYCenter - 25 * scale), indicatorXCenter, (int) (indicatorYCenter + 25 * scale));
        RectF rectDst2 = new RectF(mWidth/2-radius-50, mHeight/2-25*scale, mWidth/2-radius, mHeight/2+25*scale);

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
        Log.i(TAG,"readyRotate");
        if (indicatorStartAngle != -1) {
            canvas.save();
            Log.i(TAG, "rotateAngle-->" + rotateAngle+"\n");
            canvas.rotate(rotateAngle, mWidth / 2, mHeight / 2);
//            canvas.drawRect(rectDst2, mIndicatorRectPaint);
            canvas.drawBitmap(bitmapIndicator, null, rectDst2, mBitmapPaint);
            canvas.restore();
        }
    }
}
