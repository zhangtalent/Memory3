package z.talent.memory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.builder.Builders;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/5/5.
 */

public class usrfragmeent extends Fragment implements View.OnClickListener {
    ImageView icon;
    TextView name,vip;
    Button change,buyvip,theme,fonts,report,logout;
    SwipeRefreshLayout swipe;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user, null);
        TypefaceHelper.typeface(view);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);

        icon=(ImageView)view.findViewById(R.id.icon);
        name=(TextView)view.findViewById(R.id.name);
        vip=(TextView)view.findViewById(R.id.vip);
        change=(Button)view.findViewById(R.id.change);
        theme=(Button)view.findViewById(R.id.theme);
        buyvip=(Button)view.findViewById(R.id.buyvip);
        fonts=(Button)view.findViewById(R.id.fonts);
        report=(Button)view.findViewById(R.id.report);
        logout=(Button)view.findViewById(R.id.logout);
icon.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        startActivityForResult(intent, 200);
    }
});
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivityForResult(new Intent(getActivity(),theme.class),870);

            }
        });
        buyvip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        fonts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(),fk.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             BmobUser.logOut();
                MyUser myUser=BmobUser.getCurrentUser(MyUser.class);
getActivity().startActivity(new Intent(getActivity(),login.class));
                getActivity().finish();
            }
        });
        final MyUser myUser=BmobUser.getCurrentUser(MyUser.class);
        name.setText(myUser.getUsername());
        vip.setText(myUser.getVip());
        Picasso.with(getActivity()).load(myUser.getIcon()).resize(350,350).error(getResources().getDrawable(R.drawable.icon_user_head)).into(icon);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final MyUser myUser=BmobUser.getCurrentUser(MyUser.class);
                name.setText(myUser.getUsername());
                vip.setText(myUser.getVip());
                Picasso.with(getActivity()).load(myUser.getIcon()).resize(350,350).error(getResources().getDrawable(R.drawable.icon_user_head)).into(icon);
                swipe.setRefreshing(false);
            }
        });

        return view;
        }

    @Override
    public void onClick(View view) {


    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200&&resultCode==RESULT_OK){
            // Toast.makeText(theme.this,data.getData().toString(),Toast.LENGTH_SHORT).show();
            UCrop.Options options = new UCrop.Options();
            //开始设置
            //设置最大缩放比例
            //设置裁剪窗口是否为椭圆

            //设置是否展示矩形裁剪框

            //结束设置

            UCrop.of(Uri.parse(data.getData().toString()), Uri.parse(Environment.getExternalStorageDirectory()+"/icon.pic"))
.withAspectRatio(1,1)
.withMaxResultSize(250,250)


                    .start(getActivity());
        }

    }
}