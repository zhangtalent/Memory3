package z.talent.memory;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;


import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import net.bither.util.NativeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by 张天才 on 2017/4/8.
 */

public class selpic extends AppCompatActivity {
    GridView gv;
    ProgressDialog progressDialog;
    ArrayList<String> ls=new ArrayList<String>();
    Button bt,fb;
    EditText et;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getpic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gv=(GridView)findViewById(R.id.gv);
        bt=(Button)findViewById(R.id.bt);
        fb=(Button)findViewById(R.id.fb);
        et=(EditText)findViewById(R.id.et);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog=new ProgressDialog(selpic.this);
                progressDialog.setMessage("上传中，请稍等...");
if(ls.size()<1){
    bdxz bb =new bdxz();
    bb.setkm(et.getText().toString());
    bb.setlb("txlqz");
    bb.setlj("分割线");

    MyUser mm=BmobUser.getCurrentUser(MyUser.class);
    bb.setwx("0");
    bb.setqq(mm.getObjectId());
    bb.setxm(mm.getUsername());
    bb.setdh(mm.getIcon());
    progressDialog.show();
    bb.save(new SaveListener<String>() {
        @Override
        public void done(String s, BmobException e) {
            if(e==null){
                progressDialog.dismiss();
                Toast.makeText(selpic.this,"发布成功",Toast.LENGTH_LONG).show();
                finish();

            }
            else{
                progressDialog.dismiss();
                Toast.makeText(selpic.this,"发表失败"+e.toString(),Toast.LENGTH_LONG).show();
            }
        }
    });
}
                else{
    progressDialog.show();
             final   String[] ss=new String[ls.size()];
                for(int i=0;i<ls.size();i++){
                    Bitmap bb=BitmapFactory.decodeFile(ls.get(i));
                    String savapath= Environment.getExternalStorageDirectory()+"/test"+i+".pic";
                    NativeUtil.compressBitmap(bb, savapath);
                    ss[i]=savapath;

                }
                BmobFile.uploadBatch(ss, new UploadBatchListener() {

                    @Override
                    public void onSuccess(List<BmobFile> files, List<String> urls) {
                        //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                        //2、urls-上传文件的完整url地址
                        if(urls.size()==ss.length){//如果数量相等，则代表文件全部上传完成
                            //do something
                            bdxz bb =new bdxz();
                            bb.setkm(et.getText().toString());
                            bb.setlb("txlqz");
                            String totalurl="";

                            for(int i=0;i<urls.size();i++){
                               totalurl=totalurl+ urls.get(i)+"分割线";

                            }
                            bb.setlj(totalurl);
                            MyUser mm=BmobUser.getCurrentUser(MyUser.class);
                            bb.setwx("0");
                            bb.setqq(mm.getObjectId());
                            bb.setxm(mm.getUsername());
                            bb.setdh(mm.getIcon());
                            bb.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e==null){
                                        progressDialog.dismiss();
                                        Toast.makeText(selpic.this,"发布成功",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                    else{
                                        progressDialog.dismiss();
                                        Toast.makeText(selpic.this,"发布失败",Toast.LENGTH_LONG).show();
                                      //  finish();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int statuscode, String errormsg) {
                   //     ShowToast("错误码"+statuscode +",错误描述："+errormsg);
                        Toast.makeText(selpic.this,""+errormsg,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
                        //1、curIndex--表示当前第几个文件正在上传
                        //2、curPercent--表示当前上传文件的进度值（百分比）
                        //3、total--表示总的上传文件数
                        //4、totalPercent--表示总的上传进度（百分比）
                    }
                });}
            }
        });
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {

                new AlertDialog.Builder(selpic.this).setTitle("编辑").setNeutralButton("更换图片", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(selpic.this, MultiImageSelectorActivity.class);
// whether show camera
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
// max select image amount
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
// select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
// default select images (support array list)
                        intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, ls);
                        startActivityForResult(intent, 3);
                    }
                }).setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ls.remove(position);
                        gv.setAdapter(new BaseAdapter() {

                            @Override
                            public int getCount() {
                                return ls.size();
                            }

                            @Override
                            public Object getItem(int position) {
                                return position;
                            }

                            @Override
                            public long getItemId(int position) {
                                return position;
                            }



                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                convertView= LayoutInflater.from(selpic.this).inflate(R.layout.gvimg,null);
                                ImageView gvim=(ImageView)convertView.findViewById(R.id.gim);
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inJustDecodeBounds = false;
                                options.inSampleSize=4;
                                Bitmap mm= BitmapFactory.decodeFile(ls.get(position),options);
                                gvim.setImageBitmap(mm);




                                return convertView;
                            }



                            @Override
                            public int getViewTypeCount() {
                                return 1;
                            }


                        });
                    }
                }).show();

            }
        });
        gv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(selpic.this, MultiImageSelectorActivity.class);
// whether show camera
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
// max select image amount
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
// select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
// default select images (support array list)
                intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, ls);
                startActivityForResult(intent, 3);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(requestCode==3){
           if(resultCode==RESULT_OK) {
               String str = "";
               ArrayList<String> ls1 = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);

               ls.clear();
               ls = ls1;
               gv.setAdapter(new BaseAdapter() {

                   @Override
                   public int getCount() {
                       return ls.size();
                   }

                   @Override
                   public Object getItem(int position) {
                       return position;
                   }

                   @Override
                   public long getItemId(int position) {
                       return position;
                   }



                   @Override
                   public View getView(int position, View convertView, ViewGroup parent) {
                       convertView= LayoutInflater.from(selpic.this).inflate(R.layout.gvimg,null);
                       ImageView gvim=(ImageView)convertView.findViewById(R.id.gim);
                       BitmapFactory.Options options = new BitmapFactory.Options();
                       options.inJustDecodeBounds = false;
                       options.inSampleSize=4;
                       Bitmap mm= BitmapFactory.decodeFile(ls.get(position),options);
                     gvim.setImageBitmap(mm);




                       return convertView;
                   }



                   @Override
                   public int getViewTypeCount() {
                       return 1;
                   }


               });

           }}
        super.onActivityResult(requestCode, resultCode, data);
    }
}
