package z.talent.memory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/5/13.
 */

public class talkactivity extends AppCompatActivity {
   String id;
    private RecyclerView mRecyclerView;
    int pos;
    EditText et;
    Button bt;
    SwipeRefreshLayout swipeRefreshLayout;
    int lastVisibleItem=0;
    List<String> ls1=new ArrayList<String>();
    List<String> ls3=new ArrayList<String>();
    List<String> ls2=new ArrayList<String>();
    List<String> ls4=new ArrayList<String>();
    List<String> ls5=new ArrayList<String>();
    List<String> ls6=new ArrayList<String>();
    List<String> ls7=new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.talk);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        mRecyclerView=(RecyclerView)findViewById(R.id.recview);
bt=(Button)findViewById(R.id.bt);
        et=(EditText)findViewById(R.id.et);

        id=getIntent().getStringExtra("id");



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata();
            }
        });
        getdata();
bt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(et.getText().toString()!=null){
            Toast.makeText(talkactivity.this,"发送中",Toast.LENGTH_SHORT).show();

            bdxz bb =new bdxz();
            bb.setkm(et.getText().toString());
            bb.setlb(id);
            bb.setlj("分割线");

            MyUser mm= BmobUser.getCurrentUser(MyUser.class);
            bb.setwx("0");
            bb.setqq(mm.getObjectId());
            bb.setxm(mm.getUsername());
            bb.setdh(mm.getIcon());
            bb.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e!=null){
                        Toast.makeText(talkactivity.this,"发送失败",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(talkactivity.this,"ok了",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
        else{
            Toast.makeText(talkactivity.this,"你什么也没说",Toast.LENGTH_SHORT).show();

        }
    }
});
    }
    public void getdata(){
        BmobQuery<bdxz> bq1=new BmobQuery<bdxz>();
        bq1.order("-createdAt");
        bq1.addWhereEqualTo("lb",id);
        bq1.setLimit(1000);
        bq1.findObjects(new FindListener<bdxz>(){
            @Override
            public void done(List<bdxz> p1, BmobException e) {
                if (e == null) {
                    ls1.clear();
                    ls2.clear();
                    ls3.clear();
                    ls4.clear();
                    ls5.clear();
                    ls6.clear();
                    ls7.clear();
                    for(bdxz bdd : p1){

                        ls3.add(bdd.getkm());
                        ls1.add(bdd.getlj());
                        ls2.add(bdd.getlx());
                        ls4.add(bdd.getxm());
                        ls5.add(bdd.getdh());
                        ls6.add(bdd.getObjectId());
                        ls7.add(bdd.getwx());

                    }

                    //Toast.makeText(first.this,(String)ls.get(0).get("lj"),Toast.LENGTH_SHORT).show();
                    //Toast.makeText(first.this,(String)ls.get(1).get("ls"),Toast.LENGTH_SHORT).show();
                    LinearLayoutManager lm=new LinearLayoutManager(talkactivity.this);
                    mRecyclerView.setLayoutManager(lm);

                    mRecyclerView.setAdapter(new Recycleradqzpl(talkactivity.this,ls1,ls3,ls2,ls4,ls5,ls6,ls7));


                    swipeRefreshLayout.scrollTo(0,0);
                    swipeRefreshLayout.setRefreshing(false);
//progressDialog.dismiss();
                    // Toast.makeText(getActivity(),"cccc"+(String) ls3.get(0),Toast.LENGTH_SHORT).show();

                    // lv.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.item,ls){});
                } else {
                    ls1.clear();
                    ls2.clear();
                    ls3.clear();
                    ls3.add("网络出错"+e.toString());
                    ls2.add("网络出错"+e.toString());
                    ls1.add("网络出错");
                    LinearLayoutManager lm=new LinearLayoutManager(talkactivity.this);
                    mRecyclerView.setLayoutManager(lm);
                    mRecyclerView.setAdapter(new Recyclerad(talkactivity.this,ls1,ls3,ls2));
                    // Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
                    // progressDialog.dismiss();
                    Toast.makeText(talkactivity.this,"网络出错"+p1,Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    // TODO: Implement this method
                }
            }});
    }
}
