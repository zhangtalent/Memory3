package z.talent.memory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/5/5.
 */

public class theme extends AppCompatActivity {
    ImageView themebg1,themebg3,themecover1,themecover3;
    FrameLayout moren,zidingyi;
    Button mohu,mohuoff;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme);
        themecover3=(ImageView)findViewById(R.id.themecover3);
        themecover1=(ImageView)findViewById(R.id.themecover1);
        mohu=(Button) findViewById(R.id.mohu);
        mohuoff=(Button) findViewById(R.id.mohuoff);
        themebg3=(ImageView)findViewById(R.id.themebg3);
        themebg1=(ImageView)findViewById(R.id.themebg1);
        moren=(FrameLayout)findViewById(R.id.moren);
        zidingyi=(FrameLayout)findViewById(R.id.zidingyi);
        sp=getSharedPreferences("rs",MODE_PRIVATE);
        ed=sp.edit();
        Bitmap bitmap1= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/zhangtalent/background.pic");

        themebg3.setImageBitmap(bitmap1);
        mohu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed.putInt("radius",18);
                ed.commit();
            }
        });
        mohuoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed.putInt("radius",0);
                ed.commit();
            }
        });
        moren.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           File file1=new File(Environment.getExternalStorageDirectory()+"/zhangtalent/background.pic");

           try{
               Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.loginbg);
               FileOutputStream fileOutputStream=new FileOutputStream(file1);
               bitmap.compress(Bitmap.CompressFormat.PNG, 80, fileOutputStream);
               Bitmap bitmap1= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/zhangtalent/background.pic");


           }
           catch (Exception e){

           }
       }
   });
        zidingyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setType("image/*");
                startActivityForResult(intent, 200);
            }
        });
    }

    @Override
    public void onBackPressed() {
        theme.this.setResult(233);
        super.onBackPressed();




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200&&resultCode==RESULT_OK){
           // Toast.makeText(theme.this,data.getData().toString(),Toast.LENGTH_SHORT).show();
            UCrop.of(Uri.parse(data.getData().toString()), Uri.parse(Environment.getExternalStorageDirectory()+"/zhangtalent/background.pic"))
                    .withAspectRatio(sp.getInt("width",480),sp.getInt("height",800) )
                    .withMaxResultSize(sp.getInt("width",480),sp.getInt("height",800))
                    .start(theme.this);
        }
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            Bitmap bitmap1= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/zhangtalent/background.pic");

            themebg3.setImageBitmap(bitmap1);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }
}
