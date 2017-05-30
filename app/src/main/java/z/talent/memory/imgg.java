package z.talent.memory;

/**
 * Created by 张天才 on 2017/4/8.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class imgg extends ImageView{
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    /**位图对象*/
    private Bitmap bitmap = null;
    /** 屏幕的分辨率*/
    private DisplayMetrics dm;

    /** 最小缩放比例*/
    float minScaleR = 1.0f;

    /** 最大缩放比例*/
    static final float MAX_SCALE = 15f;

    /** 初始状态*/
    static final int NONE = 0;
    /** 拖动*/
    static final int DRAG = 1;
    /** 缩放*/
    static final int ZOOM = 2;

    /** 当前模式*/
    int mode = NONE;

    /** 存储float类型的x，y值，就是你点下的坐标的X和Y*/
    PointF prev = new PointF();
    PointF mid = new PointF();
    float dist = 1f;

    public imgg(Context context) {
        super(context);


      //  setupView();
    }

    public imgg(Context context, AttributeSet attrs) {
        super(context, attrs);
      //  setupView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

    }
/**
     * 横向、纵向居中
     */

}