package z.talent.memory;



import android.animation.Animator;
        import android.animation.AnimatorListenerAdapter;
        import android.animation.ObjectAnimator;
        import android.annotation.TargetApi;
        import android.content.Context;
        import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class Recyclerad  extends RecyclerView.Adapter<Recyclerad.ViewHolder> {
    String APPID = "c3bc12088c3accaaa474fa38cad012c9";
    private Context mContext;
    List<String> ls1=new ArrayList<String>();
    List<String> ls2=new ArrayList<String>();
    List<String> ls3=new ArrayList<String>();
    public Recyclerad(Context mContext, List<String> ls1, List<String> ls3,List<String> ls2) {
        this.mContext = mContext;
        this.ls1=ls1;
        this.ls2=ls2;
        this.ls3=ls3;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(mContext).inflate(R.layout.list_item_card_main, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.with(mContext).load(ls1.get(position)).error(mContext.getResources().getDrawable(R.drawable.icon_user_head)).into(holder.img);

        holder.tv.setText(ls3.get(position));
        holder.tv.setGravity(Gravity.CENTER);
        holder.cardView.setBackgroundColor(Color.parseColor("#00000000"));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Bundle bundle =new Bundle();
                //bundle.putString("lj",ls1.get(position));
               // bundle.putString("objid",ls2.get(position));

         // Intent intent =new Intent(mContext,xqt.class);
                //intent.putExtras(bundle);
               // mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ls1.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

       ImageView img;
        TextView tv;
CardView cardView;

        public ViewHolder(View view) {
            super(view);
           this.img=(ImageView) view.findViewById(R.id.img);
            this.tv=(TextView) view.findViewById(R.id.tv);
this.cardView=(CardView)view.findViewById(R.id.card);
        }

}}