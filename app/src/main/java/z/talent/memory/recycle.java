package z.talent.memory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/4.
 */

public class recycle extends RecyclerView.Adapter<recycle.viewdolder> {
    Context context;
    ArrayList<HashMap<String,Object>> items=new ArrayList<HashMap<String, Object>>();
    public recycle(Context context,ArrayList<HashMap<String,Object>> items){
this.context=context;
        this.items=items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(viewdolder holder, int position) {
holder.day.setText((String)items.get(position).get("day"));
        holder.mouth.setText((String)items.get(position).get("mouth"));
        holder.message.setText((String)items.get(position).get("message"));
        Picasso.with(context).load((String)items.get(position).get("img")).resize(300,180).into(holder.imageView);

    }

    @Override
    public viewdolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.writeitems,parent,false);
        viewdolder viewdolder=new viewdolder(v);

        return viewdolder;
    }

    public  class viewdolder extends RecyclerView.ViewHolder{
        TextView mouth,day,message;
        ImageView imageView;
        public viewdolder(View v){
            super(v);
            mouth=(TextView) v.findViewById(R.id.mouth);
            day=(TextView) v.findViewById(R.id.day);
            message=(TextView) v.findViewById(R.id.message);
            imageView=(ImageView) v.findViewById(R.id.imageView);

        }
    }
}
