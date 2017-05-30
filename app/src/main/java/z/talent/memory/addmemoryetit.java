package z.talent.memory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.yalantis.ucrop.UCrop;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Administrator on 2017/5/14.
 */

public class addmemoryetit extends AppCompatActivity{
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    tv.setText(Html.fromHtml(Html.toHtml(editText.getText()),imageGetter,null));
                    break;
            }
        }
    };

    EditText editText;
    TextView tv;
    Button bt,send;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmemory);
        bt=(Button)findViewById(R.id.bt);
        send=(Button)findViewById(R.id.send);
        editText=(EditText)findViewById(R.id.et);
        editText.setText(Html.fromHtml(" &nbsp "));
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode()==KeyEvent.KEYCODE_ENTER){
                editText.getText().insert(editText.getSelectionStart(),Html.fromHtml(" &nbsp ")) ;
                }
                return false;
            }
        });
        editText.setMovementMethod(new LinkMovementMethod());
       send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                             MyUser myUser= BmobUser.getCurrentUser(MyUser.class);

               bdxz bdxz=new bdxz();
               bdxz.setlb(myUser.getObjectId()+"memory");
               bdxz.setkm(Html.toHtml(editText.getText()));
               //editText.setText(Html.toHtml(editText.getText()).toLowerCase());
               //Document document=new Document(Html.toHtml(editText.getText()).toLowerCase());
              // Elements element=document.getElementsByTag("img");

               Pattern pattern=Pattern.compile("http.*?pic");

               Matcher matcher=pattern.matcher(Html.toHtml(editText.getText()).toLowerCase());
               if (matcher.find()) {
                   bdxz.setlj(Html.toHtml(editText.getText()).toLowerCase().substring(matcher.start(),matcher.end()));
                  // Toast.makeText(addmemoryetit.this,Html.toHtml(editText.getText()).toLowerCase().substring(matcher.start(),matcher.end()),Toast.LENGTH_LONG).show();
               }
               else{
                   bdxz.setlj("nopic");
                  // Toast.makeText(addmemoryetit.this,Html.toHtml(editText.getText()).toLowerCase(),Toast.LENGTH_LONG).show();

               }
               bdxz.save(new SaveListener<String>() {
                   @Override
                   public void done(String s, BmobException e) {
                       if(e==null){
                           finish();
                       }
                   }
               });

           }
       });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addmemoryetit.this, MultiImageSelectorActivity.class);
// whether show camera
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
// max select image amount
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
// select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
// default select images (support array list)
                // intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, ls);
                startActivityForResult(intent, 3);    }
        });


    }
    Html.ImageGetter imageGetter=new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(final String s) {
            Drawable drawable=null;
            if(new File(Environment.getExternalStorageDirectory()+"/zhangtalent/"+s.substring(s.length()-11,s.length())).exists()){
                Bitmap bitmap= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/zhangtalent/"+s.substring(s.length()-11,s.length()));
                drawable= new BitmapDrawable(bitmap);
                int w=getWindow().getWindowManager().getDefaultDisplay().getWidth();
                drawable.setBounds(25,5,w-25,bitmap.getHeight()*(w-25)/bitmap.getWidth());

            }
            else{

                Ion.with(addmemoryetit.this).load(s).write(new File(Environment.getExternalStorageDirectory()+"/zhangtalent/"+s.substring(s.length()-11,s.length()))).setCallback(new FutureCallback<File>() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==3&&resultCode==RESULT_OK) {
            ArrayList<String> ls1 = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            UCrop.of(Uri.fromFile(new File(ls1.get(0))), Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/upload.pic")))



                    .start(addmemoryetit.this);








        }


        else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            //Bitmap bitmap1= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/zhangtalent/upload.pic");
            final   File ff=new File(Environment.getExternalStorageDirectory()+"/upload.pic");
            final BmobFile bmobFile=new BmobFile(ff);
            bmobFile.upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        File fileff=new File(Environment.getExternalStorageDirectory()+"/upload.pic");

                        fileff.renameTo(new File(Environment.getExternalStorageDirectory()+"/zhangtalent/"+bmobFile.getUrl().substring(bmobFile.getUrl().length()-11,bmobFile.getUrl().length())));

                        int ss=editText.getSelectionStart();
                        Editable editable=editText.getText();
                        editable.insert(ss, Html.fromHtml("<br><img src='"+bmobFile.getUrl()+"'</img><br> &nbsp ",imageGetter,null));
                        Toast.makeText(addmemoryetit.this,"载入图片中，稍等。",Toast.LENGTH_SHORT).show();

                        // bt.setText(Html.toHtml(editText.getText()));
                    }
                }
            });
            //themebg3.setImageBitmap(bitmap1);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
