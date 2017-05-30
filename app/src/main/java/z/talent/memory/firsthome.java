package z.talent.memory;

import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2017/5/4.
 */

 public class firsthome extends AppCompatActivity {
    ViewPager vp;
    Button write,union,user;
    ImageView bg;
SharedPreferences sp;
    SharedPreferences.Editor ed;
    ArrayList<Fragment> fragmentitems=new ArrayList<Fragment>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firsthome);
write=(Button)findViewById(R.id.write);
union=(Button)findViewById(R.id.union);
user=(Button)findViewById(R.id.user);
vp=(ViewPager)findViewById(R.id.vp);
bg=(ImageView)findViewById(R.id.bg);
        sp=getSharedPreferences("rs",MODE_PRIVATE);
        ed=sp.edit();
        write.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_conversation_selected));
        union.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_plugin_normal));
        user.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_contact_normal));

        fragmentitems.add(new writefragment());
        fragmentitems.add(new ListFrangment());
        fragmentitems.add(new usrfragmeent());
vp.setAdapter(new fragmentadpter(getSupportFragmentManager(),fragmentitems));
      vp.setPageTransformer(true, new ViewPager.PageTransformer() {

          private static final float MIN_SCALE = 0.75f;
          public void transformPage(View view, float position) {
              int pageWidth = view.getWidth();
              if (position < -1) { // [-Infinity,-1)
                  // This page is way off-screen to the left.
                  view.setAlpha(0);
              } else if (position <= 0) { // [-1,0]
                  // Use the default slide transition when moving to the left page
                  view.setAlpha(1);
                  view.setTranslationX(0);
                  view.setScaleX(1);
                  view.setScaleY(1);
              } else if (position <= 1) { // (0,1]
                  // Fade the page out.
                  view.setAlpha(1 - position);
                  // Counteract the default slide transition
                  view.setTranslationX(pageWidth * -position);
                  // Scale the page down (between MIN_SCALE and 1)
                  float scaleFactor = MIN_SCALE
                          + (1 - MIN_SCALE) * (1 - Math.abs(position));
                  view.setScaleX(scaleFactor);
                  view.setScaleY(scaleFactor);
              } else { // (1,+Infinity]
                  // This page is way off-screen to the right.
                  view.setAlpha(0);
              }
          }
      });
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
          @Override
          public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
              ed.putInt("height",bg.getHeight());
              ed.putInt("width",bg.getWidth());
              ed.commit();

          }

          @Override
          public void onPageSelected(int position) {
switch (position){
    case 0:
        write.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_conversation_selected));
        union.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_plugin_normal));
        user.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_contact_normal));
        break;
    case 1:
        write.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_conversation_normal));
        union.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_plugin_selected));
        user.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_contact_normal));
        break;
    case 2:
        write.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_conversation_normal));
        union.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_plugin_normal));
        user.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_contact_selected));
        break;

}
          }

          @Override
          public void onPageScrollStateChanged(int state) {

          }
      });
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp.setCurrentItem(0);
                write.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_conversation_selected));
                union.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_plugin_normal));
                user.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_contact_normal));

            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              vp.setCurrentItem(2);
                write.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_conversation_normal));
                union.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_plugin_normal));
                user.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_contact_selected));

            }
        });
        union.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp.setCurrentItem(1);
                write.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_conversation_normal));
                union.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_plugin_selected));
                user.setBackground(getResources().getDrawable(R.drawable.skin_tab_icon_contact_normal));

            }
        });
        setbackground();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

 if (resultCode == 233&& requestCode == 870) {
     // Toast.makeText(firsthome.this,"dlld",Toast.LENGTH_LONG).show();

      setbackground();

    //  Toast.makeText(firsthome.this,"dlld00",Toast.LENGTH_LONG).show();

  }
       else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
           // Bitmap bitmap1= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/icon.pic");
     Toast.makeText(firsthome.this,"dlld00",Toast.LENGTH_LONG).show();
       new Thread(new Runnable() {
                 @Override
                 public void run() {
                     final BmobFile bb=new BmobFile(new File(Environment.getExternalStorageDirectory()+"/icon.pic"));
                     bb.upload(new UploadFileListener() {
                         @Override
                         public void done(BmobException e) {
                             if(e==null){
                                 MyUser newUser = new MyUser();
                                 newUser.setIcon(bb.getUrl());
                                 final MyUser bmobUser = BmobUser.getCurrentUser(MyUser.class);

                                 newUser.update(bmobUser.getObjectId(),new UpdateListener() {
                                     @Override
                                     public void done(BmobException e) {
                                         if(e==null){
                                             Toast.makeText(firsthome.this, "OK", Toast.LENGTH_LONG).show();

                                         }else{
                                             Toast.makeText(firsthome.this, "用户异常，无法连接到服务器，请重新登录", Toast.LENGTH_LONG).show();
                                             MyUser.logOut();
                                               //清除缓存用户对象
                                             MyUser bmobUser = BmobUser.getCurrentUser(MyUser.class);

                                             startActivity(new Intent(firsthome.this,login.class));
                                             finish();
                                         }
                                     }
                                 });


                             }
                         }
                     });
                 }
             }).start();

            //icon.setImageBitmap(bitmap1);

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
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
