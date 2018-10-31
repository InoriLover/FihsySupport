package fishy.support.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import fishy.support.util.DeviceInfoUtil;

/**
 * Created by DN2017030300 on 2018/5/10.
 */

public class ColorIndicatorProgressBar extends View {

    Paint mBarPaint;
    Paint mTextPaint;
    Paint mBitmapPaint;

    private float mStrokeWidth = 80f;

    //this.view的宽高
    int mWidth;
    int mHeight;

    float scaleFloat;

    Context context;


    public ColorIndicatorProgressBar(Context context) {
        this(context, null);
    }

    public ColorIndicatorProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ColorIndicatorProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.rotate(90, mWidth / 2, mHeight / 2);
        // 第一步：绘制一个圆环
        mBarPaint.setStrokeJoin(Paint.Join.BEVEL);
        mBarPaint.setStrokeCap(Paint.Cap.ROUND);

        RectF oval;
        if (mWidth > mHeight) {
            oval = new RectF(mWidth / 2 - mHeight / 2 + mStrokeWidth / 2, 0 + mStrokeWidth / 2,
                    mWidth / 2 + mHeight / 2 - mStrokeWidth / 2, mHeight - mStrokeWidth / 2);
        } else {
            oval = new RectF(0 + mStrokeWidth / 2, mHeight / 2 - mWidth / 2 + mStrokeWidth / 2,
                    mWidth - mStrokeWidth / 2, mHeight / 2 + mWidth / 2 - mStrokeWidth / 2);
        }
        //默认开口朝下
        float startAngle = 135;



        SweepGradient sweepGradient = new SweepGradient(mWidth / 2, mHeight / 2,
                new int[]{getResources().getColor(R.color.color_indicator_1),
                        getResources().getColor(R.color.color_indicator_2),
                        getResources().getColor(R.color.color_indicator_3),
                        getResources().getColor(R.color.color_indicator_4),
                        getResources().getColor(R.color.color_indicator_5)}
                , null);
        Matrix gradientMatrix = new Matrix();
        gradientMatrix.preRotate(90,mWidth/2,mHeight/2);
        sweepGradient.setLocalMatrix(gradientMatrix);
        mBarPaint.setShader(sweepGradient);

        canvas.drawArc(oval, startAngle, 270, false, mBarPaint);

        //画指示器
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_sword);
//        Rect rectSrc = new Rect(0, 0, 100, 100);
//        Rect rectDst = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//        canvas.drawBitmap(bitmap, null, rectSrc, mBitmapPaint);

        //
        // 第二步：绘制文字
//        String text = ((int) (progress / maxProgress * 100)) + "";
//        String text = hrvValue + "";
//        Rect bounds = new Rect();
//        mTextPaint.getTextBounds(text, 0, text.length(), bounds);
//        canvas.drawText(text, mWidth / 2 - bounds.mWidth() / 2, mHeight / 2 + bounds.mHeight() / 2, mTextPaint);
    }
}
