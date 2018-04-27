package fishy.support.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by DN2017030300 on 2018/4/27.
 */

public class NoPasteEditText extends AppCompatEditText {
    public NoPasteEditText(Context context) {
        this(context, null);
    }

    public NoPasteEditText(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public NoPasteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
