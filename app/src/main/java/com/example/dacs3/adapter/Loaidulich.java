package com.example.dacs3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dacs3.R;
import com.example.dacs3.model.Loaidl;

import java.util.List;

public class Loaidulich extends BaseAdapter {
    List<Loaidl> array;
    Context context;

    public Loaidulich(Context context, List<Loaidl> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView texttenldl;
        ImageView imghinhanh;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.loaidulich_view, null);

            viewHolder.texttenldl = view.findViewById(R.id.item_tenloai);
            viewHolder.imghinhanh = view.findViewById(R.id.item_image);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.texttenldl.setText(array.get(i).getTenloai());
            Glide.with(context).load(array.get(i).getHinhanhloaidl()).into(viewHolder.imghinhanh);
        }


        return view;
    }
}
