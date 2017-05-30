package z.talent.memory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/5/5.
 */

public class img extends ImageView {

    public img(Context context){
        super(context);
    }
    public img(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }
    public img(Context context, AttributeSet attributeSet,int def){
        super(context,attributeSet,def);

    }

    @Override
    protected void onDraw(Canvas canvas) {

            super.onDraw(canvas);

    }

}
