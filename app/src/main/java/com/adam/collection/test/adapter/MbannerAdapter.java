package com.adam.collection.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adam.collection.test.R;
import com.adam.collection.test.bean.InfoBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * Author: Adam Inc.
 * <br/>轮播图适配器
 * Created at: 2020/10/27 13:35
 * <br/>
 *
 * @since
 */
public  class MbannerAdapter extends BannerAdapter<InfoBean,MbannerAdapter.Mbannerholder> {
    Context context;

    public  MbannerAdapter(Context context, List<InfoBean> datas){
        super(datas);
        this.context=context;
    }


    @Override
    public Mbannerholder onCreateHolder(ViewGroup parent, int viewType) {
        return  new Mbannerholder(LayoutInflater.from(context).inflate(R.layout.mybannerrecycler,parent,false));
    }

    @Override
    public void onBindView(Mbannerholder holder, InfoBean data, int position, int size) {
        holder.imageView.setImageResource(data.getPicture());
        //设置监听
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"第"+(position+1)+"张",Toast.LENGTH_SHORT).show();
            }
        });
    }



    public class  Mbannerholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public Mbannerholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img_banner);
        }
    }
}
