package z.talent.memory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by 张天才 on 2017/4/18.
 */

public class recyc extends RecyclerView {
public recyc(Context context){
    super(context);
}
    public recyc(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec,MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST) );
    }
}
