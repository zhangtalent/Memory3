package z.talent.memory;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.norbsoft.typefacehelper.TypefaceHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Recycleradmemory extends RecyclerView.Adapter<Recycleradmemory.ViewHolder> {
    String APPID = "c3bc12088c3accaaa474fa38cad012c9";
    private Context mContext;
    List<String> ls1=new ArrayList<String>();//date
    List<String> ls2=new ArrayList<String>();//message
    List<String> ls3=new ArrayList<String>();//url
    List<String> ls5=new ArrayList<String>();//objectid
    public Recycleradmemory(Context mContext, List<String> ls1, List<String> ls2, List<String> ls3,List<String> ls5) {
        this.mContext = mContext;
        this.ls1=ls1;
        this.ls2=ls2;
        this.ls3=ls3;
        this.ls5=ls5;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(mContext).inflate(R.layout.writeitems, parent, false);
        TypefaceHelper.typeface(view);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(ls3.get(position).equals("nopic")){
            holder.img.setVisibility(View.GONE);
        }
        else{
            holder.img.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(ls3.get(position)).error(mContext.getResources().getDrawable(R.drawable.aio_image_fail_round)).into(holder.img);

        }
       String datee=ls1.get(position);
        String mout=datee.substring(5,7);
        String da=datee.substring(8,10);
        holder.mouth.setText(mout);
        holder.day.setText(da);
        holder.message.setText(Html.fromHtml(ls2.get(position).replaceAll("<img.*?>","[图片]")));
       // holder.cardView.setBackgroundColor(Color.parseColor("#00000000"));
        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Bundle bundle =new Bundle();
                bundle.putString("message",ls2.get(position));
               bundle.putString("objid",ls5.get(position));

         Intent intent =new Intent(mContext,showmemory.class);
         intent.putExtras(bundle);
         mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ls2.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

       ImageView img;
        TextView message,mouth,day;


        public ViewHolder(View view) {
            super(view);
           this.img=(ImageView) view.findViewById(R.id.imageView);
            this.mouth=(TextView) view.findViewById(R.id.mouth);
            this.day=(TextView) view.findViewById(R.id.day);
            this.message=(TextView) view.findViewById(R.id.message);

        }

}}