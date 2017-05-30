package z.talent.memory;

import android.app.Application;
import android.graphics.Typeface;

import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2017/5/4.
 */

public class appica extends Application {

    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "c3bc12088c3accaaa474fa38cad012c9");
        TypefaceCollection typeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "fonts/ziti.ttf"))
                .create();
        TypefaceHelper.init(typeface);
    }


    // set方法

}
