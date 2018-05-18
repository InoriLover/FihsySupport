package fishy.support.util;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by DN2017030300 on 2018/5/18.
 */

public class PalletView extends View {

    public PalletView(Context context) {
        this(context, null);
    }

    public PalletView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PalletView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void initAttrs() {

    }

    void init() {

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }
}
