package fishy.support.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;

import fishy.support.util.DeviceInfoUtil;


/**
 * Created by DN2017030300 on 2018/5/25.
 * 消息戴点控件
 */

public class PointImageView extends FrameLayout {
    final String TAG = this.getClass().getSimpleName();

    ImageView imgContent;
    ImageView imgPoint;
    //各种属性
    float layoutPadding;
    int pointSize;
    int pointColor;
    int imgResId;   //图片的资源ID
    int pointMarginX;   //在X轴上的点的外边距
    int pointMarginY;   //在Y轴上的点的外边距

    int mWidth;
    int mHeight;
    int imgWidth;
    int imgHeight;
    Context context;
    float scaleF;


    public PointImageView(@NonNull Context context) {
        this(context, null);
    }

    public PointImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PointImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(attrs);
        initView();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.PointImageView);
            //标记点的颜色
            pointColor = a.getColor(R.styleable.PointImageView_pointColor, Color.DKGRAY);
            //标记点的尺寸
            pointSize = (int) a.getDimension(R.styleable.PointImageView_pointSize, 8);
            //图片对于父视图的内边距
            layoutPadding = a.getDimension(R.styleable.PointImageView_layoutPadding, 2);
            //图片的资源Id
            imgResId = a.getResourceId(R.styleable.PointImageView_imgResource, R.mipmap.ic_launcher);
            pointMarginX = (int) a.getDimension(R.styleable.PointImageView_pointMarginX, 0);
            pointMarginY = (int) a.getDimension(R.styleable.PointImageView_pointMarginY, 0);
        } finally {
            // 注意，别忘了 recycle
            a.recycle();
        }
    }

    private void initView() {
        scaleF = DeviceInfoUtil.getDeviceDpiScale(context);
        //add ImageView
        imgContent = new ImageView(context);
        LayoutParams params = new LayoutParams(mWidth,
                mHeight);
        params.gravity = Gravity.CENTER;
        imgContent.setLayoutParams(params);
        imgContent.setImageResource(imgResId);

        ImageView imgP = new ImageView(context);
        LayoutParams paramsP = new LayoutParams(pointSize,
                pointSize);
        imgP.setLayoutParams(paramsP);
        imgPoint = imgP;
        imgPoint.setImageDrawable(createOvalDrawable(pointColor));

        addView(imgContent);
        addView(imgPoint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        imgWidth = (int) (mWidth - 2 * layoutPadding);
        imgHeight = (int) (mHeight - 2 * layoutPadding);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //每次测量重设宽，高
        LayoutParams params = (LayoutParams) imgContent.getLayoutParams();
        params.width = imgWidth;
        params.height = imgHeight;
        imgContent.setLayoutParams(params);

        int leftP = mWidth - pointSize - pointMarginX;
        int topP = pointMarginY;
        imgPoint.layout(leftP, topP, leftP + pointSize, topP + pointSize);
    }

    /**
     * 设置内部imageview的资源id
     *
     * @param resId
     */
    public void setImageRes(@DrawableRes int resId) {
        this.imgResId = resId;
        if (imgContent != null) {
            imgContent.setImageResource(resId);
        }
    }

    /**
     * 显示标记点
     */
    public void openPoint() {
        if (imgPoint != null) {
            imgPoint.setVisibility(VISIBLE);
        }
    }

    /**
     * 关闭标记点
     */
    public void closePoint() {
        if (imgPoint != null) {
            imgPoint.setVisibility(GONE);
        }
    }

    /**
     * 设置标记点的颜色
     */
    public void setPointColor(int color) {
        this.pointColor = color;
        imgPoint.setImageDrawable(createOvalDrawable(color));
    }

    public void setLayoutPadding(float layoutPadding) {
        this.layoutPadding = layoutPadding;
    }

    /**
     * 标记点是否显示
     *
     * @return
     */
    public boolean isPointShow() {
        if (imgPoint.getVisibility() == VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 创建一个新的Drawable
     */
    private Drawable createOvalDrawable(int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(color);
        drawable.setSize(pointSize, pointSize);
        return drawable;
    }
}
