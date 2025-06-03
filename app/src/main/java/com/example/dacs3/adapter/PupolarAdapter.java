package com.example.dacs3.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.dacs3.R;
import com.example.dacs3.activity.detailamthuc;
import com.example.dacs3.model.Amthuc;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.DecimalFormat;

public class PupolarAdapter extends FirebaseRecyclerAdapter<Amthuc, PupolarAdapter.Viewholder> {
    public PupolarAdapter(@NonNull FirebaseRecyclerOptions<Amthuc> options) {
        super(options);

    }

    @SuppressLint("RecyclerView")
    @Override
    protected void onBindViewHolder(@NonNull PupolarAdapter.Viewholder viewholder, int position, @NonNull Amthuc amthuc) {



        viewholder.title.setText(amthuc.getTitle());
        viewholder.location.setText(amthuc.getLocation());
        float score = (float) amthuc.getScore();
        DecimalFormat df = new DecimalFormat("#.#");
        String formattedScore = df.format(score);
        viewholder.score.setText(formattedScore);
        Glide.with(viewholder.pic.getContext())
                .load(amthuc.getPic())
                .transform(new CenterCrop(),new GranularRoundedCorners(20,20,20,20))
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(viewholder.pic);

        viewholder.cardMAin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intCard = new Intent(viewholder.title.getContext(), detailamthuc.class );
                intCard.putExtra("pic", amthuc.getPic());
                intCard.putExtra("title", amthuc.getTitle());
                intCard.putExtra("location", amthuc.getLocation());
                intCard.putExtra("description", amthuc.getDescription());
                intCard.putExtra("score",amthuc.getScore());
                viewholder.title.getContext().startActivity(intCard);
            }
        });

    }


    @NonNull
    @Override
    public PupolarAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_khampha,parent,false);

        return new PupolarAdapter.Viewholder(view);
    }


    public class Viewholder extends RecyclerView.ViewHolder{
        TextView title,location,score;
        ImageView pic;
        ConstraintLayout cardMAin;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.titleTxt);
            location=itemView.findViewById(R.id.locationTxt);
            score=itemView.findViewById(R.id.scoreTxt);
            pic=itemView.findViewById(R.id.picImgMain);
            cardMAin = itemView.findViewById(R.id.cardMain);
        }
    }
}
