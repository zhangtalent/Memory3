package z.talent.memory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/5/4.
 */

public class writefragment extends Fragment {
    Button fab;
    private RecyclerView mRecyclerView;
    int pos;
    int lastVisibleItem=0;
    List<String> ls1=new ArrayList<String>();
    List<String> ls3=new ArrayList<String>();
    List<String> ls2=new ArrayList<String>();
    List<String> ls5=new ArrayList<String>();
    SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
   View view = inflater.inflate(R.layout.writefragment,null);
fab=(Button)view.findViewById(R.id.fab);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);

        mRecyclerView=(RecyclerView) view.findViewById(R.id.recycle);
getdata();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(getActivity(),addmemoryetit.class));
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
             getdata();
            }
        });
        return view;

    }
    public void getdata(){
        MyUser myUser= BmobUser.getCurrentUser(MyUser.class);
String findkey=myUser.getObjectId()+"memory";
        BmobQuery<bdxz> bq1=new BmobQuery<bdxz>();
        bq1.order("-createdAt");
        bq1.addWhereEqualTo("lb",findkey);

        bq1.setLimit(500);
        bq1.findObjects(new FindListener<bdxz>(){
            @Override
            public void done(List<bdxz> p1, BmobException e) {
                if (e == null) {
                    ls1.clear();
                    ls2.clear();
                    ls3.clear();
                    ls5.clear();
                    for(bdxz bdd : p1){
                        ls1.add(bdd.getCreatedAt());
                        ls2.add(bdd.getkm());
                        ls3.add(bdd.getlj());
                        ls5.add(bdd.getObjectId());
                    }

                    //Toast.makeText(first.this,(String)ls.get(0).get("lj"),Toast.LENGTH_SHORT).show();
                    //Toast.makeText(first.this,(String)ls.get(1).get("ls"),Toast.LENGTH_SHORT).show();
                    LinearLayoutManager lm=new LinearLayoutManager(getActivity());
                    mRecyclerView.setLayoutManager(lm);

                    mRecyclerView.setAdapter(new Recycleradmemory(getActivity(),ls1,ls2,ls3,ls5));
                    mRecyclerView.scrollToPosition(pos);

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
}
