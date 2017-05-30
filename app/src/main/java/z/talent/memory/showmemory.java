package z.talent.memory;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/5/15.
 */

public class showmemory extends AppCompatActivity {
    TextView tv;
    String ssjj;
    ImageView bg;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    Html.ImageGetter imageGetter=new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(final String s) {
            Drawable drawable=null;
            if(new File(Environment.getExternalStorageDirectory()+"/zhangtalent/"+s.substring(s.length()-11,s.length())).exists()){
                Bitmap bitmap= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/zhangtalent/"+s.substring(s.length()-11,s.length()));
                drawable= new BitmapDrawable(bitmap);
                int w=getWindow().getWindowManager().getDefaultDisplay().getWidth();
                drawable.setBounds(25,10,w-25,bitmap.getHeight()*(w-25)/bitmap.getWidth());

            }
            else{

                Ion.with(showmemory.this).load(s).write(new File(Environment.getExternalStorageDirectory()+"/zhangtalent/"+s.substring(s.length()-11,s.length()))).setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File file) {
                        Message message=new Message();
                        message.what=1;
                        handler.sendMessage(message);
                    }
                });
            }
            return drawable;
        }
    };
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    tv.setText(Html.fromHtml(ssjj,imageGetter,null));
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showmemory);
        tv=(TextView)findViewById(R.id.tv);
        bg=(ImageView)findViewById(R.id.bg);
        sp=getSharedPreferences("rs",MODE_PRIVATE);
        ed=sp.edit();
        ssjj=getIntent().getStringExtra("message");
        tv.setMovementMethod(new LinkMovementMethod());
        tv.setText(Html.fromHtml(ssjj,imageGetter,null));
        setbackground();

    }
    public void setbackground(){

        File file=new File(Environment.getExternalStorageDirectory()+"/zhangtalent");
        if(!file.exists()){
            file.mkdirs();
        }
        else{

        }
        File file1=new File(Environment.getExternalStorageDirectory()+"/zhangtalent/background.pic");
        if(!file1.exists()){
            try{
                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.loginbg);
                FileOutputStream fileOutputStream=new FileOutputStream(file1);
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, fileOutputStream);
                Bitmap bitmap1= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/zhangtalent/background.pic");

                bg.setImageBitmap(bitmap1);
            }
            catch (Exception e){

            }
        }
        else{
            Bitmap bitmap1= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/zhangtalent/background.pic");
            if(sp.getInt("radius",0)==0){
                bg.setImageBitmap(bitmap1);
            }
            else{  bg.setImageBitmap(blurBitmap(bitmap1));
            }

        }
    }
    public Bitmap blurBitmap(Bitmap bitmap){

        //Let's create an empty bitmap with the same size of the bitmap we want to blur
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        //Instantiate a new Renderscript
        RenderScript rs = RenderScript.create(getApplicationContext());

        //Create an Intrinsic Blur Script using the Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        //Set the radius of the blur: 0 < radius <= 25
        blurScript.setRadius(sp.getInt("radius",1));

        //Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        //Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);

        //recycle the original bitmap
        bitmap.recycle();

        //After finishing everything, we destroy the Renderscript.
        rs.destroy();

        return outBitmap;

    }
}
