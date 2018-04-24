package ll.zhao.triptoyou.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by zhaopengfei on 2018/04/24.
 */

public class HLLButton extends Button {

    private boolean isCost = true;

    public HLLButton(Context context) {
        super(context);
    }

    public HLLButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    setAlpha(0.7f);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    setAlpha(1.0f);

                }
                return isCost;
            }
        });
    }

    public HLLButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs,defStyle);

    }


    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        isCost = false;
    }
}
