package z.talent.memory;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class Recycleradqzpl extends RecyclerView.Adapter<Recycleradqzpl.ViewHolder> {
    String APPID = "c3bc12088c3accaaa474fa38cad012c9";
    private Context mContext;
    List<String> ls1=new ArrayList<String>();
    List<String> ls2=new ArrayList<String>();
    List<String> ls3=new ArrayList<String>();
    List<String> ls4=new ArrayList<String>();
    List<String> ls5=new ArrayList<String>();
    List<String> ls6=new ArrayList<String>();//objectid
    List<String> ls7=new ArrayList<String>();//zannumber
    public Recycleradqzpl(Context mContext, List<String> ls1, List<String> ls3, List<String> ls2, List<String> ls4, List<String> ls5, List<String> ls6, List<String> ls7) {
        this.mContext = mContext;
        this.ls1=ls1;
        this.ls2=ls2;
        this.ls3=ls3;
        this.ls4=ls4;
        this.ls5=ls5;
        this.ls6=ls6;
        this.ls7=ls7;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(mContext).inflate(R.layout.det, parent, false);
        return new ViewHolder(view);
}

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String[] ssg=ls1.get(position).split("分割线");
        final  List<String> ls=new ArrayList<String>();
        for(String str:ssg){
            ls.add(str);


        }

        if(ls.size()==1){
            holder.gv.setNumColumns(1);
            holder.gv.setVisibility(View.VISIBLE);
            holder.gv.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return 1;
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
                    convertView=LayoutInflater.from(mContext).inflate(R.layout.gvimg,null);
                    ImageView imgg=(ImageView) convertView.findViewById(R.id.gim);
                    Picasso.with(mContext).load(ls.get(position)).error(mContext.getResources().getDrawable(R.drawable.icon_user_head)).into(imgg);


                    return convertView;
                }
            });

        }
else if(ls.size()==0){
            holder.gv.setNumColumns(1);
            holder.gv.setVisibility(View.VISIBLE);

        }
        else if(ls.size()==2){
            holder.gv.setNumColumns(2);
            holder.gv.setVisibility(View.VISIBLE);
            holder.gv.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return 2;
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
                    convertView=LayoutInflater.from(mContext).inflate(R.layout.gvimg,null);
                    ImageView imgg=(ImageView) convertView.findViewById(R.id.gim);
                    Picasso.with(mContext).load(ls.get(position)).error(mContext.getResources().getDrawable(R.drawable.icon_user_head)).into(imgg);
                    return convertView;
                }
            });


        }
        else if(ls.size()>=3){


            holder.gv.setNumColumns(3);
            holder.gv.setVisibility(View.VISIBLE);
            holder.gv.setAdapter(new BaseAdapter() {
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
                    convertView=LayoutInflater.from(mContext).inflate(R.layout.gvimg,null);
                    ImageView imgg=(ImageView) convertView.findViewById(R.id.gim);

                    Picasso.with(mContext).load(ls.get(position)).placeholder(mContext.getResources().getDrawable(R.drawable.aio_image_default_round)).error(mContext.getResources().getDrawable(R.drawable.aio_image_fail_round)).into(imgg);
                    return convertView;
                }
            });
        }
holder.gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle=new Bundle();
        bundle.putString("lj",ls.get(position));
        Intent intent=new Intent(mContext,showpic.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
});
holder.pinglun.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

    }
});
        holder.zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.zan.setBackground(mContext.getResources().getDrawable(R.drawable.fop));

                if (!ls7.get(position).equals("")&&ls7.get(position)!=null){
                   holder.zannumber.setText(""+(Integer.parseInt(ls7.get(position))+1));
                    bdxz    bdxz=new bdxz();
                    bdxz.setwx(""+(Integer.parseInt(ls7.get(position))+1));
                    bdxz.update(ls6.get(position), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                }
                else{
                    holder.zannumber.setText(""+1);

                    bdxz    bdxz=new bdxz();
                    bdxz.setwx(""+1);
                    bdxz.update(ls6.get(position), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                }

            }
        });
        holder.pinglun.setVisibility(View.GONE);
holder.tv.setText(ls3.get(position));
        holder.zannumber.setText(ls7.get(position));
        holder.user.setText(ls4.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Picasso.with(mContext).load(ls5.get(position)).resize(250,250).placeholder(mContext.getResources().getDrawable(R.drawable.icon_user_head)).error(mContext.getResources().getDrawable(R.drawable.icon_user_head)).into(holder.iv);

    }

    @Override
    public int getItemCount() {
        return ls1.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
GridView gv;
       ImageViewPlus img,iv;
        TextView tv,user,zannumber;
CardView cardView;
Button pinglun,zan;
        public ViewHolder(View view) {
            super(view);
            this.iv=(ImageViewPlus) view.findViewById(R.id.iv);
           this.gv=(GridView) view.findViewById(R.id.gv);
            this.tv=(TextView) view.findViewById(R.id.tv);
            this.zannumber=(TextView) view.findViewById(R.id.zannumber);
            this.user=(TextView) view.findViewById(R.id.user);
this.cardView=(CardView)view.findViewById(R.id.card);
            this.pinglun=(Button) view.findViewById(R.id.pinglun);
            this.zan=(Button) view.findViewById(R.id.zan);
        }

}}