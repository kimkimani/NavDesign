package com.example.kim.navdesign.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kim.navdesign.Model.jacport;
import com.example.kim.navdesign.Model.tips;
import com.example.kim.navdesign.R;

import java.util.List;

public class JackportsAdapter extends ArrayAdapter<jacport> {

    private List<jacport> jackportList;
    private Context mCtx;
    private int lastPosition = -1;
    public JackportsAdapter (List <jacport> p, Context c)


    {
        super(c , R.layout.jackportslist,p);
        this.jackportList=p;
        this.mCtx=c;
    }
    private static class ViewHolder {
        TextView timej;

        TextView matchj;
        TextView tipj;
        TextView resultsj;
        TextView noj;




    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View resul;

       ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater= LayoutInflater.from (mCtx  ) ;

            convertView= inflater.inflate (  R.layout.jackportslist,null,true );
            holder= new JackportsAdapter.ViewHolder ();
            holder. noj = (TextView) convertView.findViewById ( R.id.noj);
            holder.timej = (TextView) convertView.findViewById ( R.id.timej);

            holder.matchj = (TextView) convertView.findViewById ( R.id.matchj);

            holder. tipj = (TextView) convertView.findViewById ( R.id.tipj);
            holder.resultsj = (TextView) convertView.findViewById ( R.id.resultsj);

            resul = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (JackportsAdapter.ViewHolder) convertView.getTag();
            resul = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mCtx,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        resul.startAnimation(animation);
        lastPosition = position;

        jacport jacport=jackportList.get(position);

        holder.noj.setText ( jacport.getno () );
        holder.timej.setText( jacport.getTime () );



        holder. matchj.setText ( jacport.getMatch () );
        holder. tipj.setText ( jacport.getTip () );

        holder.resultsj.setText ( jacport.getResults () );




        return convertView;
    }
}
