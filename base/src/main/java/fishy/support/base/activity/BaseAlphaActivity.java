package fishy.support.base.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

import fishy.support.base.R;

/**
 * 透明沉浸式状态栏抽象类
 * <h3>
 * 编辑记录
 * </h3>
 * <p>
 * 第一次完成<br/>
 * edited by shallowFish on 2018/4/27
 * </p>
 *
 * @author ShallowFish
 * @version 1.0.0
 */

public abstract class BaseAlphaActivity extends AppCompatActivity {

    private ViewGroup root;
    private View content;
    private Mode alphaMode;
    private DrawerLayout drawerLayout;
    private View fakeStatusBarView;
    private View colorStatusBarView;
    private View maskView;
    int statusBarColor;
    int initViewFlag;

    boolean isCreated;
    //默认不带蒙版效果
    int maskAlpha;

    boolean isUseLightIcon;

    protected enum Mode {
        /**
         * 普通的颜色bar
         */
        NORMAL_COLOR,
        /**
         * 头部含图片,透明效果
         */
        IMAGE_TOOLBAR,
        /**
         * 带抽屉布局
         */
        DRAWER_LAYOUT
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = createRootLayout(this);
        //初始化时获得mode
        alphaMode = chooseAlphaMode();
        statusBarColor = getResources().getColor(android.R.color.darker_gray);
        //默认不使用白色状态栏图标
        isUseLightIcon = false;
        //默认没有mask效果
        this.maskAlpha = setTransparentViewAlpha(0);
        //初始的systemUIVisibility
        initViewFlag = getWindow().getDecorView().getSystemUiVisibility();
        //未创建相关status
        isCreated = false;
        super.setContentView(root);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        createAlphaStatusBar(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = View.inflate(this, layoutResID, null);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        //当为普通颜色设置并在4.4版本的情况下，需要设置paddingTop来适应
        if (chooseAlphaMode() == Mode.NORMAL_COLOR && Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(this),
                    view.getPaddingRight(), view.getPaddingBottom());
        }
        root.addView(view, params);
    }

    @Override
    public void setContentView(View view) {
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        //当为普通颜色设置并在4.4版本的情况下，需要设置paddingTop来适应
        if (chooseAlphaMode() == Mode.NORMAL_COLOR && Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(this),
                    view.getPaddingRight(), view.getPaddingBottom());
        }
        root.addView(view, params);
    }

    private void changeStatusIconStyle(Mode mode) {
        switch (mode) {
            case NORMAL_COLOR:
            case DRAWER_LAYOUT:
                //如果是6.0，检查是否使用白色款图标
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isUseLightIcon) {
                    getWindow()
                            .getDecorView()
                            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    getWindow()
                            .getDecorView()
                            .setSystemUiVisibility(initViewFlag);
                }
                break;
            case IMAGE_TOOLBAR:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isUseLightIcon) {
                    getWindow()
                            .getDecorView()
                            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    getWindow()
                            .getDecorView()
                            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                }
                break;
        }
    }

    /**
     * 设置是否使用白色款图标，6.0以上有效,默认不适用
     *
     * @param useLightIcon
     */
    public void setUseLightIcon(boolean useLightIcon) {
        isUseLightIcon = useLightIcon;
        changeStatusIconStyle(alphaMode);
    }

    /**
     * 设置状态栏的颜色
     *
     * @param color
     */
    protected void setStatusColor(int color) {
        this.statusBarColor = color;
        changeAlphaStatusBarColor(this, statusBarColor);
    }

    /**
     * 设置mask蒙版的透明度
     *
     * @param progress 0~100
     */
    protected void setMaskAlpha(int progress) {
        this.maskAlpha = setTransparentViewAlpha(progress * 1f / 100);
        changeAlphaStatusBarMask(this);
    }

    /**
     * 是否使用白色图标，默认为false，使用白色图标，仅6.0之后有效
     *
     * @return
     */
    public boolean isUseLightIcon() {
        return isUseLightIcon;
    }

    /**
     * 生成根布局
     *
     * @param context
     */
    private ViewGroup createRootLayout(Context context) {
        FrameLayout root = new FrameLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        root.setLayoutParams(params);
//        root.setOrientation(LinearLayout.VERTICAL);
        return root;
    }

    /**
     * 创建透明状态栏
     *
     * @param context
     */
    private void createAlphaStatusBar(Context context) {
        if (isCreated) {
            return;
        }
        switch (alphaMode) {
            case NORMAL_COLOR:
                createColorStatusBar(context, statusBarColor);
                break;
            case IMAGE_TOOLBAR:
                createTransparentStatusBar();
                break;
            case DRAWER_LAYOUT:
                createDrawerLayoutStatusBar(context, statusBarColor);
                break;
        }
        isCreated = true;
//        addTransparentView(context);
    }

    /**
     * 改变状态栏颜色
     *
     * @param context
     * @param color
     */
    private void changeAlphaStatusBarColor(Context context, int color) {
        switch (alphaMode) {
            case NORMAL_COLOR:
                if (isCreated) {
                    changeColorStatusBar(color);
                } else {
                    createColorStatusBar(context, color);
                }
                break;
            case DRAWER_LAYOUT:

                break;
            case IMAGE_TOOLBAR:
                //imageMode下不处理状态栏颜色
                break;
        }
    }

    /**
     * 改变状态栏的蒙版透明度
     *
     * @param context
     */
    private void changeAlphaStatusBarMask(Context context) {
        switch (alphaMode) {
            case NORMAL_COLOR:
                if (isCreated) {
                    changeColorStatusBar(statusBarColor);
                } else {
                    createColorStatusBar(context, statusBarColor);
                }
                break;
            case DRAWER_LAYOUT:

                break;
            case IMAGE_TOOLBAR:
                if (isCreated) {
                    changeTransparentStatusBar();
                } else {
                    createTransparentStatusBar();
                }
                break;
        }
    }


    /**
     * 创建指定颜色的状态栏
     * 对应mode为{@link Mode#NORMAL_COLOR}
     *
     * @param context
     */
    private void createColorStatusBar(Context context, int color) {
        //对于5.0以上和4.4做不同的处理
        //4.3及以下不做处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(calculateStatusColor(color, maskAlpha));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View view = new View(this);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(context)));
            view.setBackgroundColor(color);
            root.addView(view, 0);
            colorStatusBarView = view;
        }
        changeStatusIconStyle(Mode.NORMAL_COLOR);
    }

    /**
     * 修改状态栏的颜色或者蒙版浓度
     * 对应mode为{@link Mode#NORMAL_COLOR}
     *
     * @param color
     */
    private void changeColorStatusBar(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(calculateStatusColor(color, maskAlpha));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            colorStatusBarView.setBackgroundColor(color);
        }
    }

    /**
     * 创建适应图片头部的透明状态栏
     * 对应mode为IMAGE_TOOLBAR
     */
    private void createTransparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        addTransparentView(this);
        changeStatusIconStyle(Mode.IMAGE_TOOLBAR);
    }

    /**
     * 修改透明状态栏的蒙版浓度
     */
    private void changeTransparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            maskView.setBackgroundColor(Color.argb(maskAlpha, 0, 0, 0));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 创建和drawerlayout配合使用的状态栏
     * 对应mode为DRAWER_LAYOUT
     *
     * @param context
     */
    private void createDrawerLayoutStatusBar(Context context, int color) {
        if (drawerLayout == null) {
            throw new IllegalArgumentException("your drawerLayout should not be null!");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(color);
        } else {
            contentLayout.addView(createFakeStatusBarView(context, color), 0);
        }
        // 内容布局不是 LinearLayout 时,设置marginTop
        //用来腾出空间放置fakeStatusbarView
        if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentLayout.getChildAt(1).getLayoutParams();
            params.setMargins(params.leftMargin, params.topMargin + getStatusBarHeight(context),
                    params.rightMargin, params.bottomMargin);
        }
        setDrawerLayoutProperty(drawerLayout, contentLayout);
        changeStatusIconStyle(Mode.IMAGE_TOOLBAR);
    }

    /**
     * 设置 DrawerLayout 属性
     *
     * @param drawerLayout              DrawerLayout
     * @param drawerLayoutContentLayout DrawerLayout 的内容布局
     */
    private static void setDrawerLayoutProperty(DrawerLayout drawerLayout, ViewGroup drawerLayoutContentLayout) {
        ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
        drawerLayout.setFitsSystemWindows(false);
        drawerLayoutContentLayout.setFitsSystemWindows(false);
        drawer.setFitsSystemWindows(false);
    }

    /**
     * 创造伪造的状态栏
     *
     * @param context
     * @param color   状态栏的颜色
     */
    private View createFakeStatusBarView(Context context, @ColorInt int color) {
        View fakeView = new View(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(context));
        fakeView.setLayoutParams(params);
        fakeView.setBackgroundColor(color);
        fakeStatusBarView = fakeView;
        return fakeView;
    }

    /**
     * 计算状态栏颜色
     * 该算法用来计算在当前的颜色基调上加上黑色蒙版的效果
     * 与普通的给颜色设置透明度不一样
     *
     * @param color color值
     * @param alpha alpha值 取值在0~255之间
     * @return 最终的状态栏颜色
     */
    private int calculateStatusColor(@ColorInt int color, int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    //    protected void resetStatusColor(Context context, int color) {
//        if (alphaMode == Mode.NORMAL_COLOR) {
//            this.statusBarColor = color;
//            createColorStatusBar(context);
//        }
//    }


    /**
     * 设置透明状态栏的模式
     * 分三种：普通颜色模式，图片顶部透明模式，和DrawerLayout结合使用模式
     *
     * @return 状态栏的模式
     * @see Mode#NORMAL_COLOR
     * @see Mode#IMAGE_TOOLBAR
     * @see Mode#DRAWER_LAYOUT
     */
    protected abstract Mode chooseAlphaMode();

    /**
     * 设置DrawerLayout
     *
     * @param drawerLayout
     * @return
     */
    protected void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    /**
     * 添加黑色为底的透明蒙版View
     */
    private void addTransparentView(Context context) {
        View transparentView = new View(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(context));
        transparentView.setLayoutParams(params);
        transparentView.setBackgroundColor(Color.argb(maskAlpha, 0, 0, 0));
        //注意FrameLayout的child添加顺序
        root.addView(transparentView);

        maskView = transparentView;
    }

    /**
     * 设置状态栏的黑色蒙版透明度
     *
     * @param percent 0~1f
     */
    protected int setTransparentViewAlpha(float percent) {
        return (int) (0xff * percent + 0.5f);
    }

    /**
     * 得到状态栏的高度
     *
     * @return 状态栏高度，单位：像素
     */
    protected int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
}
