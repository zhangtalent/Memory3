package z.talent.memory;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import  com.github.chrisbanes.photoview.*;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 张天才 on 2017/4/19.
 */

public class showpic  extends AppCompatActivity {
 float sss=1;
   Bitmap bm=null;
    PhotoView photoView;
    PhotoViewAttacher photoViewAttacher;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showimg);
       photoView= (PhotoView) findViewById(R.id.photo_view);
    //photoViewAttacher =new PhotoViewAttacher(photoView);
photoView.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {
        new AlertDialog.Builder(showpic.this).setTitle("操作").setItems(new String[]{"保存图片"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Ion.with(showpic.this).load(getIntent().getStringExtra("lj"))
                                .write(new File(Environment.getExternalStorageDirectory()+"/"+getIntent().getStringExtra("lj").substring(getIntent().getStringExtra("lj").length()-6,getIntent().getStringExtra("lj").length())+".png")).setCallback(new FutureCallback<File>() {
                            @Override
                            public void onCompleted(Exception e, File file) {
                                if(e==null){
                                    Toast.makeText(showpic.this,"图片已保存至sd卡更目录",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        break;
                }
            }
        }).show();
        return false;
    }
});

       Ion.with(showpic.this).load(getIntent().getStringExtra("lj"))
       .write(new File(Environment.getExternalStorageDirectory()+"/01.png")).setCallback( new FutureCallback<File>() {

            @Override
            public void onCompleted(Exception e, File file) {
                if (e == null) {
                  photoView.setImageURI(Uri.parse(file.getAbsolutePath()));
                } else{

                }
            }
        });



       // Picasso.with(showpic.this).load(getIntent().getStringExtra("lj")).into(photoView);
       //photoView.setImageResource(R.drawable.qq);

        }
    public void getURLimage(String url) {


    }
}
