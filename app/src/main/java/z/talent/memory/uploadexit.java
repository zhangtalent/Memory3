package z.talent.memory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Administrator on 2017/5/14.
 */

public class uploadexit extends AppCompatActivity
{ Handler handler=new Handler(){
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
    EditText editText;
    TextView tv;
    Button bt;
    String ssjj="\n<img src='http://p0.ifengimg.com/pmop/2017/0514/EFDE36224751BB78450B90BC5AF6606D77A5F0C4_size292_w600_h830.jpeg'/>\n<img src='https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3773409313,3264394378&fm=111&gp=0.jpg'/>\n &nbsp Hello world\n<img src='https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1722017966,2225519822&fm=23&gp=0.jpg'/>\n<img src=\"http://pic004.cnblogs.com/news/201211/20121108_091749_1.jpg\"/>\nfirst Pictrue"+"总结一下我进行的错误示范实验：\n" +
            "\n" +
            "        一是子线程1加载，子线程2显示；\n" +
            "\n" +
            "        二是子线程1加载，子线程1显示；\n" +
            "        三、四是主线程handler的Runnable加载和显示；\n" +
            "\n" +
            "        当然，直接在主线程中直接写这句直接加载和显示肯定是会抛错的。\n" +
            "\n" +
            "\n" +
            "\n" +
            "可见，这个问题其实就是一层“窗户纸”的问题：\n" +
            "\n" +
            "子线程网络图片加载（imageGetter必须在子线程run方法中），主线程显示（需用handler）\n" +
            "\n" +
            "\n" +
            "\n" +
            "PS.网上看见很多人解决这个方法需要重写一个类进行下载、解析，或者着重点在对imageGetter的自写上，代码很多，比较繁琐；而在我的试验中觉得上面的才是核心点，只要抓住，几行代码就能搞定；\n" +
            "\n" +
            "当然，可能我的理解不够深，不能窥测大牛们的繁琐步骤可能有更远见性的考虑，还望多多指正、批评！\n" +
            "\n" +
            "\n"+"<img src='http://img.my.csdn.net/uploads/201301/28/1359349749_4520.png'/>";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
setContentView(R.layout.edittext);

        bt=(Button)findViewById(R.id.bt);
        editText=(EditText)findViewById(R.id.et);
        tv=(TextView) findViewById(R.id.tv);
        tv.setMovementMethod(new LinkMovementMethod());
tv.setText(Html.fromHtml(ssjj,imageGetter,null));
        editText.setMovementMethod(new LinkMovementMethod());
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
public void onClick(View view) {
                Intent intent = new Intent(uploadexit.this, MultiImageSelectorActivity.class);
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
                Bitmap bitmap=BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/zhangtalent/"+s.substring(s.length()-11,s.length()));
                drawable= new BitmapDrawable(bitmap);
                int w=getWindow().getWindowManager().getDefaultDisplay().getWidth();
                drawable.setBounds(25,25,w-25,bitmap.getHeight()*(w-25)/bitmap.getWidth());
tv.invalidate();
                tv.setText(tv.getText());
            }
            else{

               Ion.with(uploadexit.this).load(s).write(new File(Environment.getExternalStorageDirectory()+"/zhangtalent/"+s.substring(s.length()-11,s.length()))).setCallback(new FutureCallback<File>() {
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



                        .start(uploadexit.this);








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
                        editable.insert(ss, Html.fromHtml("<img src='"+bmobFile.getUrl()+"'/>",imageGetter,null));
                        bt.setText(Html.toHtml(editText.getText()));
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
