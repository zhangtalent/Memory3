package z.talent.memory;

/**
 * Created by Admin on 2017/3/1.
 */


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.logging.LogRecord;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ListFrangment extends Fragment {
    private RecyclerView mRecyclerView;
    int pos;
    int lastVisibleItem=0;
    List<String> ls1=new ArrayList<String>();
    List<String> ls3=new ArrayList<String>();
    List<String> ls2=new ArrayList<String>();
    List<String> ls4=new ArrayList<String>();
    List<String> ls5=new ArrayList<String>();
    List<String> ls6=new ArrayList<String>();
    List<String> ls7=new ArrayList<String>();
 Handler handler=new Handler(){
     @Override
     public void handleMessage(Message msg) {
         super.handleMessage(msg);
         switch (msg.what){
             case 1:
                 Toast.makeText(getActivity(),"ll",Toast.LENGTH_SHORT).show();
                 BmobQuery<bdxz> bq1=new BmobQuery<bdxz>();
                 bq1.order("-createdAt");
                 bq1.addWhereEqualTo("lb","txlqz");
                 bq1.setSkip(ls1.size());
                 bq1.setLimit(5);
                 bq1.findObjects(new FindListener<bdxz>(){
                     @Override
                     public void done(List<bdxz> p1, BmobException e) {
                         if (e == null) {

                             for(bdxz bdd : p1){

                                 ls3.add(bdd.getkm());
                                 ls1.add(bdd.getlj());
                                 ls2.add(bdd.getlx());
                                 ls4.add(bdd.getxm());
                                 ls5.add(bdd.getdh());
                                 ls6.add(bdd.getObjectId());
                                 ls7.add(bdd.getwx());
                                 //ljj.add(bdd.getlj());
                             }

                             //Toast.makeText(first.this,(String)ls.get(0).get("lj"),Toast.LENGTH_SHORT).show();
                             //Toast.makeText(first.this,(String)ls.get(1).get("ls"),Toast.LENGTH_SHORT).show();
                             LinearLayoutManager lm=new LinearLayoutManager(getActivity());
                             mRecyclerView.setLayoutManager(lm);

                             mRecyclerView.setAdapter(new Recycleradqz(getActivity(),ls1,ls3,ls2,ls4,ls5,ls6,ls7));
                             mRecyclerView.scrollToPosition(pos);

                             swipeRefreshLayout.setRefreshing(false);
//progressDialog.dismiss();
                             // Toast.makeText(getActivity(),"cccc"+(String) ls3.get(0),Toast.LENGTH_SHORT).show();

                             // lv.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.item,ls){});
                         } else {
                             ls1.clear();
                             ls2.clear();
                             ls3.clear();
                             ls4.clear();
                             ls5.clear();
                             ls6.clear();
                             ls7.clear();
                             ls3.add("网络出错"+e.toString());
                             ls2.add("网络出错"+e.toString());
                             ls1.add("网络出错");
                             LinearLayoutManager lm=new LinearLayoutManager(getActivity());
                             mRecyclerView.setLayoutManager(lm);
                             mRecyclerView.setAdapter(new Recyclerad(getActivity(),ls1,ls3,ls2));
                             // Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
                             // progressDialog.dismiss();
                             Toast.makeText(getActivity(),"网络出错"+p1,Toast.LENGTH_SHORT).show();
                             swipeRefreshLayout.setRefreshing(false);
                             // TODO: Implement this method
                         }
                     }});
                 break;

         }
     }
 };
    Button fab;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog progressDialog;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View vv =
                inflater.inflate(R.layout.rec, container, false);
      //  Toast.makeText(getActivity(),"加载中，请稍等",Toast.LENGTH_SHORT).show();
        swipeRefreshLayout = (SwipeRefreshLayout) vv.findViewById(R.id.swipe_container);

        mRecyclerView=(RecyclerView) vv.findViewById(R.id.recycle);
         fab = (Button) vv. findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),selpic.class));
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                       // .setAction("Action", null).show();
            }
        });
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
             /*   lastVisibleItem=recyclerView.getLayoutManager().getItemCount();
                Snackbar.make(recyclerView,"加载更多中..."+lastVisibleItem,Snackbar.LENGTH_SHORT).show();
                if ( lastVisibleItem+1  == ls3.size()) {
                    pos=lastVisibleItem;
                    /// swipeRefreshLayout.setRefreshing(true);

                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
                }*/
                if ( lastVisibleItem+1  == ls3.size()&&recyclerView.canScrollVertically(1)==false&&newState== recyclerView.SCROLL_STATE_IDLE) {
                    pos=lastVisibleItem;
                    /// swipeRefreshLayout.setRefreshing(true);
                    Snackbar.make(recyclerView,"加载更多中...",Snackbar.LENGTH_SHORT).show();
                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem= ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

            }
        });
        BmobQuery<bdxz> bq1=new BmobQuery<bdxz>();
        bq1.order("-createdAt");
        bq1.addWhereEqualTo("lb","txlqz");
        bq1.setLimit(5);
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
                        //ljj.add(bdd.getlj());
                    }

                    //Toast.makeText(first.this,(String)ls.get(0).get("lj"),Toast.LENGTH_SHORT).show();
                    //Toast.makeText(first.this,(String)ls.get(1).get("ls"),Toast.LENGTH_SHORT).show();
                    LinearLayoutManager lm=new LinearLayoutManager(getActivity());
                    mRecyclerView.setLayoutManager(lm);

                    mRecyclerView.setAdapter(new Recycleradqz(getActivity(),ls1,ls3,ls2,ls4,ls5,ls6,ls7));

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
                    LinearLayoutManager lm=new LinearLayoutManager(getActivity());
                    mRecyclerView.setLayoutManager(lm);
                    mRecyclerView.setAdapter(new Recyclerad(getActivity(),ls1,ls3,ls2));
                    // Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
                    // progressDialog.dismiss();
                    Toast.makeText(getActivity(),"网络出错"+p1,Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    // TODO: Implement this method
                }
            }});

       /* progressDialog=new ProgressDialog(getActivity());
progressDialog.setTitle("提示");
        progressDialog.setMessage("加载中，请稍等...");
        progressDialog.show();*/
       // Bmob.initialize(getActivity().getApplicationContext(),"c3bc12088c3accaaa474fa38cad012c9");



         /*   public void onSuccess(List<bdxz> p1)
            {
ls1.clear();
                ls2.clear();
                ls3.clear();
                for(bdxz bdd : p1){

                    ls3.add(bdd.getkm());
                   ls1.add(bdd.getlj());
                    ls2.add(bdd.getlx());
                    //ljj.add(bdd.getlj());
                }

                //Toast.makeText(first.this,(String)ls.get(0).get("lj"),Toast.LENGTH_SHORT).show();
                //Toast.makeText(first.this,(String)ls.get(1).get("ls"),Toast.LENGTH_SHORT).show();
                LinearLayoutManager lm=new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(lm);

                mRecyclerView.setAdapter(new Recyclerad(getActivity(),ls1,ls3,ls2));
//progressDialog.dismiss();
              // Toast.makeText(getActivity(),"cccc"+(String) ls3.get(0),Toast.LENGTH_SHORT).show();

                // lv.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.item,ls){});

                // TODO: Implement this method
            }


            public void onError(int p1, String p2)
            {ls1.clear();
                ls2.clear();
                ls3.clear();
                ls3.add("网络出错");
                ls2.add("网络出错");
                ls1.add("");
                LinearLayoutManager lm=new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(lm);
                mRecyclerView.setAdapter(new Recyclerad(getActivity(),ls1,ls3,ls2));
                // Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
               // progressDialog.dismiss();
                Toast.makeText(getActivity(),"网络出错"+p1,Toast.LENGTH_SHORT).show();
                // TODO: Implement this method
            }

            }*/
         swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimaryDark,
                R.color.colorPrimary,
                R.color.cardview_dark_background,
                R.color.colorAccent
        );

swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {
        BmobQuery<bdxz> bq1=new BmobQuery<bdxz>();
        bq1.order("-createdAt");
        bq1.addWhereEqualTo("lb","txlqz");
        bq1.setLimit(5);
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
                        //ljj.add(bdd.getlj());
                    }

                    //Toast.makeText(first.this,(String)ls.get(0).get("lj"),Toast.LENGTH_SHORT).show();
                    //Toast.makeText(first.this,(String)ls.get(1).get("ls"),Toast.LENGTH_SHORT).show();
                    LinearLayoutManager lm=new LinearLayoutManager(getActivity());
                    mRecyclerView.setLayoutManager(lm);

                    mRecyclerView.setAdapter(new Recycleradqz(getActivity(),ls1,ls3,ls2,ls4,ls5,ls6,ls7));
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
                    LinearLayoutManager lm=new LinearLayoutManager(getActivity());
                    mRecyclerView.setLayoutManager(lm);
                    mRecyclerView.setAdapter(new Recyclerad(getActivity(),ls1,ls3,ls2));
                    // Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
                    // progressDialog.dismiss();
                    Toast.makeText(getActivity(),"网络出错"+p1,Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    // TODO: Implement this method
                }
            }});
    }
});
       swipeRefreshLayout.setRefreshing(true);
        return vv;
    }


}