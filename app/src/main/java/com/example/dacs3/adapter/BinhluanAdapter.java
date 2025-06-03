package com.example.dacs3.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dacs3.R;
import com.example.dacs3.model.Danhgia;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class BinhluanAdapter extends FirebaseRecyclerAdapter<Danhgia, BinhluanAdapter.BlHolder> {
    public BinhluanAdapter(@NonNull FirebaseRecyclerOptions<Danhgia> options) {
        super(options);
    }

    @SuppressLint("RecyclerView")
    @Override
    protected void onBindViewHolder(@NonNull BinhluanAdapter.BlHolder binhluanHolder, int position, @NonNull Danhgia danhgia) {

        binhluanHolder.username.setText(danhgia.getUsername());
        binhluanHolder.binhluan.setText(danhgia.getBinhluan());

    }


    @NonNull
    @Override
    public BinhluanAdapter.BlHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_binhluan,parent,false);

        return new BinhluanAdapter.BlHolder(view);
    }

    class BlHolder extends RecyclerView.ViewHolder{

        TextView username,binhluan;

        public BlHolder(@NonNull View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.usernameBL);
            binhluan = (TextView) itemView.findViewById(R.id.binhluanUser);

        }
    }

}